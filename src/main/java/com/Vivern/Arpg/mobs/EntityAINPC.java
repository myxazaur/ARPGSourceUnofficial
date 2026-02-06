package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.blocks.Chair;
import com.Vivern.Arpg.blocks.Table;
import com.Vivern.Arpg.entity.EntityChair;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAINPC extends EntityAIBase {
   public NPCMobsPack.NpcTrader entity;
   public Requirement[] requirements;
   public int cooldown = 60;
   public int findTime = 0;
   public Requirement currentReq;

   public EntityAINPC(NPCMobsPack.NpcTrader entity, Requirement... requirements) {
      this.entity = entity;
      this.requirements = requirements;
      int i = 0;

      while (i < requirements.length) {
         Requirement requirement = requirements[i];
         requirement.shift = i++;
      }
   }

   public boolean shouldExecute() {
      return true;
   }

   public void updateTask() {
      this.cooldown--;
      if (this.cooldown <= 0) {
         if (this.currentReq == null) {
            this.currentReq = this.requirements[this.entity.getRNG().nextInt(this.requirements.length)];
         } else if (this.currentReq.tryFulfill(this)) {
            this.entity.setComfortPoint(this.currentReq.shift, !this.currentReq.isNegative);
            this.cooldown = this.currentReq.getCooldown(this);
            this.currentReq = null;
            this.findTime = 0;
         } else {
            this.findTime++;
            if (this.entity.isRiding()) {
               this.entity.dismountRidingEntity();
            }

            if (this.findTime > this.currentReq.getMaxFindTime(this)) {
               this.entity.setComfortPoint(this.currentReq.shift, this.currentReq.isNegative);
               this.cooldown = this.currentReq.getCooldown(this);
               this.currentReq = null;
               this.findTime = 0;
            }
         }
      }
   }

   public interface IBlockAiRequirement {
      boolean allowed(World var1, IBlockState var2, BlockPos var3);
   }

   public abstract static class Requirement {
      public int shift;
      public boolean isNegative;

      public abstract boolean tryFulfill(EntityAINPC var1);

      public abstract int getCooldown(EntityAINPC var1);

      public abstract int getMaxFindTime(EntityAINPC var1);
   }

   public static class RequirementChairAndTable extends Requirement {
      public int radius = 5;
      public float seeDistanceSq;
      public int tableComfort = -1;
      public boolean isSimpleTable;

      public RequirementChairAndTable(float seeDistance, boolean isSimpleTable, boolean isNegative) {
         this.seeDistanceSq = seeDistance * seeDistance;
         this.isSimpleTable = isSimpleTable;
         this.isNegative = isNegative;
      }

      @Override
      public boolean tryFulfill(EntityAINPC ai) {
         if (ai.entity.ticksExisted % 20 == 0) {
            BlockPos pos = ai.entity.getPosition();
            int min = 999999;
            BlockPos minpos = null;

            for (int x = -this.radius; x <= this.radius; x++) {
               for (int y = -this.radius; y <= this.radius; y++) {
                  for (int z = -this.radius; z <= this.radius; z++) {
                     BlockPos pos2 = pos.add(x, y, z);
                     IBlockState state = ai.entity.world.getBlockState(pos2);
                     if (state.getBlock() instanceof Chair) {
                        int distSq = x * x + y * y + z * z;
                        if (distSq < min) {
                           min = distSq;
                           minpos = pos2;
                        }
                     }
                  }
               }
            }

            if (minpos != null) {
               ai.entity.getNavigator().tryMoveToXYZ(minpos.getX(), minpos.getY(), minpos.getZ(), 1.0);
               Vec3d to = new Vec3d(minpos.getX() + 0.5, minpos.getY() + 0.75, minpos.getZ() + 0.5);
               Vec3d from = ai.entity.getPositionEyes(1.0F);
               ai.entity.getLookHelper().setLookPosition(to.x, to.y, to.z, 50.0F, 50.0F);
               if (from.squareDistanceTo(to) <= this.seeDistanceSq && GetMOP.thereIsNoBlockCollidesBetween(ai.entity.world, from, to, 1.0F, null, false)) {
                  ai.entity.swingArm(EnumHand.MAIN_HAND);
                  if (ai.entity.isRiding()) {
                     ai.entity.dismountRidingEntity();
                     return false;
                  }

                  EntityChair chair = new EntityChair(ai.entity.world);
                  chair.setPositionAndUpdate(minpos.getX() + 0.5, minpos.getY() + 0.3, minpos.getZ() + 0.5);
                  ai.entity.world.spawnEntity(chair);
                  ai.entity.startRiding(chair);
                  this.tableComfort = this.getTableComfort(ai.entity.world, minpos, ai);
                  return this.tableComfort == 2 || this.isSimpleTable && this.tableComfort == 1;
               }
            } else if (ai.entity.getRNG().nextInt(60) == 0) {
               Vec3d vec3d = RandomPositionGenerator.getLandPos(ai.entity, 15, 7);
               if (vec3d != null) {
                  ai.entity.getNavigator().tryMoveToXYZ(vec3d.x, vec3d.y, vec3d.z, 1.0);
               }
            }
         }

         return false;
      }

      @Override
      public int getCooldown(EntityAINPC ai) {
         return 260;
      }

      @Override
      public int getMaxFindTime(EntityAINPC ai) {
         return 220;
      }

      public int getTableComfort(World world, BlockPos chairPos, EntityAINPC ai) {
         if (world.getBlockState(chairPos).getBlock() instanceof Chair) {
            EnumFacing f = (EnumFacing)world.getBlockState(chairPos).getValue(Chair.FACING);
            BlockPos pos = chairPos.offset(f);
            IBlockState st = world.getBlockState(pos);
            if (st.getBlock() instanceof Table) {
               ai.entity.getLookHelper().setLookPosition(pos.getX() + 0.5, pos.getY() + 1.25, pos.getZ() + 0.5, 30.0F, 30.0F);
               return 2;
            } else if (st.isTopSolid()) {
               ai.entity.getLookHelper().setLookPosition(pos.getX() + 0.5, pos.getY() + 1.25, pos.getZ() + 0.5, 20.0F, 20.0F);
               return 1;
            } else {
               return 0;
            }
         } else {
            return 0;
         }
      }
   }

   public static class RequirementLight extends Requirement {
      public int lightMin;
      public int lightMax;

      public RequirementLight(int lightMin, int lightMax, boolean isNegative) {
         this.lightMin = lightMin;
         this.lightMax = lightMax;
         this.isNegative = isNegative;
      }

      @Override
      public boolean tryFulfill(EntityAINPC ai) {
         int light = ai.entity.world.getLightFromNeighbors(ai.entity.getPosition().up());
         return light >= this.lightMin && light <= this.lightMax;
      }

      @Override
      public int getCooldown(EntityAINPC ai) {
         return 150;
      }

      @Override
      public int getMaxFindTime(EntityAINPC ai) {
         return 5;
      }
   }

   public static class RequirementMoveToBlock extends Requirement {
      public IBlockAiRequirement requirement;
      public int cooldown;
      public int findtime;
      public int radius = 5;
      public float seeDistanceSq;
      public boolean swingArm;

      public RequirementMoveToBlock(
         IBlockAiRequirement requirement, int cooldown, int findtime, float seeDistance, boolean swingArm, boolean isNegative
      ) {
         this.requirement = requirement;
         this.cooldown = cooldown;
         this.findtime = findtime;
         this.seeDistanceSq = seeDistance * seeDistance;
         this.swingArm = swingArm;
         this.isNegative = isNegative;
      }

      @Override
      public boolean tryFulfill(EntityAINPC ai) {
         if (ai.entity.ticksExisted % 20 == 0) {
            BlockPos pos = ai.entity.getPosition();
            int min = 999999;
            BlockPos minpos = null;

            for (int x = -this.radius; x <= this.radius; x++) {
               for (int y = -this.radius; y <= this.radius; y++) {
                  for (int z = -this.radius; z <= this.radius; z++) {
                     BlockPos pos2 = pos.add(x, y, z);
                     if (this.requirement.allowed(ai.entity.world, ai.entity.world.getBlockState(pos2), pos2)) {
                        int distSq = x * x + y * y + z * z;
                        if (distSq < min) {
                           min = distSq;
                           minpos = pos2;
                        }
                     }
                  }
               }
            }

            if (minpos != null) {
               BlockPos posToMove = fixPosToMove(ai.entity.world, minpos, ai.entity.getRNG());
               Vec3d to = new Vec3d(minpos.getX() + 0.5, minpos.getY() + 0.5, minpos.getZ() + 0.5);
               Vec3d from = ai.entity.getPositionEyes(1.0F);
               if (ai.entity.getNavigator().tryMoveToXYZ(posToMove.getX(), posToMove.getY(), posToMove.getZ(), 1.0)
                  && from.squareDistanceTo(to) <= this.seeDistanceSq) {
                  ai.entity.getLookHelper().setLookPosition(to.x, to.y, to.z, 50.0F, 50.0F);
                  if (this.swingArm) {
                     ai.entity.swingArm(EnumHand.MAIN_HAND);
                  }

                  return true;
               }
            } else if (ai.entity.getRNG().nextInt(60) == 0) {
               Vec3d vec3d = RandomPositionGenerator.getLandPos(ai.entity, 15, 7);
               if (vec3d != null) {
                  ai.entity.getNavigator().tryMoveToXYZ(vec3d.x, vec3d.y, vec3d.z, 1.0);
               }
            }
         }

         return false;
      }

      public static BlockPos fixPosToMove(World world, BlockPos pos, Random rand) {
         int f = rand.nextInt(4);

         for (int i = 0; i < 4; i++) {
            EnumFacing face = EnumFacing.HORIZONTALS[f];
            f = GetMOP.next(f, 1, 4);
            BlockPos poss = pos.offset(face);
            if (world.getBlockState(poss).getCollisionBoundingBox(world, poss) == null) {
               BlockPos possUp = poss.up();
               if (world.getBlockState(possUp).getCollisionBoundingBox(world, possUp) == null) {
                  BlockPos possDown = poss.down();
                  if (world.getBlockState(possDown).isSideSolid(world, possDown, EnumFacing.UP)) {
                     return poss;
                  }
               }
            }
         }

         return pos;
      }

      @Override
      public int getCooldown(EntityAINPC ai) {
         return this.cooldown;
      }

      @Override
      public int getMaxFindTime(EntityAINPC ai) {
         return this.findtime;
      }
   }
}
