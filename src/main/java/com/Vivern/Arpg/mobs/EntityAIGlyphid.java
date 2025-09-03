//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.entity.AbstractGlyphid;
import com.Vivern.Arpg.events.Debugger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.concurrent.Immutable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityAIGlyphid extends EntityAIBase {
   public AbstractGlyphid entity;
   public static long recalcLastTime = 0L;
   public static long longWayCall = 0L;
   public static int longWayCounter = 0;
   public PathPointGlyphid path;
   public int glyphidAiType = 0;

   public EntityAIGlyphid(AbstractGlyphid entity) {
      this.entity = entity;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public boolean shouldContinueExecuting() {
      return this.entity.getAttackTarget() != null && this.entity.getAttackTarget().isEntityAlive();
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      if (attackTarg != null) {
         if (this.path != null) {
            this.moveOnPath(
               this.entity,
               -this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue(),
               this.path.getX() + 0.5,
               this.path.getY() + (1.0 - this.entity.height) / 2.0,
               this.path.getZ() + 0.5
            );
         }

         int reachDist = (int)this.entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
         double distsq = attackTarg.getDistanceSq(this.entity);
         if (distsq <= reachDist * reachDist) {
            if (distsq > 289.0) {
               longWayCall = this.entity.world.getTotalWorldTime();
            }

            if (this.entity.world.getTotalWorldTime() > recalcLastTime + 20L) {
               recalcLastTime = this.entity.world.getTotalWorldTime();
               if (this.entity.world.getTotalWorldTime() < longWayCall + 10L) {
                  longWayCounter++;
               }

               if (longWayCounter < 6) {
                  this.recalcPathToTarget(this.entity.world, attackTarg, 19, 17 + (int)Debugger.floats[0]);
               } else {
                  this.recalcPathToTarget(this.entity.world, attackTarg, reachDist, 32 + (int)Debugger.floats[1]);
                  longWayCounter = 0;
               }
            }
         }
      }
   }

   public void moveOnPath(AbstractGlyphid entity, double power, double fromX, double fromY, double fromZ) {
      float dist = (float)entity.getDistance(fromX, fromY, fromZ);
      float prunex = (float)((fromX - entity.posX) / dist / 2.0 * power);
      float pruney = (float)((fromY - entity.posY) / dist / 2.0 * power);
      float prunez = (float)((fromZ - entity.posZ) / dist / 2.0 * power);
      entity.motionX += -prunex;
      entity.motionY += -pruney;
      entity.motionZ += -prunez;
      if (Math.abs(entity.motionX) > 0.01 || Math.abs(entity.motionY) > 0.01 || Math.abs(entity.motionZ) > 0.01) {
         double friction = 0.9;
         entity.motionX *= friction;
         entity.motionY *= friction;
         entity.motionZ *= friction;
      }

      if (dist < 0.5) {
         int x1 = this.path.getX();
         int y1 = this.path.getY();
         int z1 = this.path.getZ();
         this.path = this.path.previous;
         if (this.path != null && this.path.previous != null) {
            int x3 = this.path.getX();
            int y3 = this.path.getY();
            int z3 = this.path.getZ();
            int x4 = this.path.previous.getX() - x3;
            int y4 = this.path.previous.getY() - y3;
            int z4 = this.path.previous.getZ() - z3;
            BlockPos poss = new BlockPos(x1 + x4, y1 + y4, z1 + z4);
            if (entity.checkPosition(entity.world, poss)) {
               this.path = this.path.previous;
               entity.currentCost--;
            }
         }

         entity.currentCost--;
      }
   }

   public void recalcPathToTarget(World world, EntityLivingBase target, int reachDist, int exploreCount) {
      AxisAlignedBB axisalignedbb = new AxisAlignedBB(
         target.posX - reachDist,
         target.posY - reachDist,
         target.posZ - reachDist,
         target.posX + reachDist,
         target.posY + reachDist,
         target.posZ + reachDist
      );
      List<AbstractGlyphid> listGlyphids = world.getEntitiesWithinAABB(AbstractGlyphid.class, axisalignedbb);
      List<PathPointGlyphid> explored = new ArrayList<>();
      explored.add(new PathPointGlyphid(target.posX, target.posY, target.posZ, 0).setways(2));
      explored.add(new PathPointGlyphid(target.posX, target.posY + 1.0, target.posZ, 0).setways(1));

      for (int i = 0; i < exploreCount; i++) {
         List<PathPointGlyphid> exploredAdd = new ArrayList<>();

         for (PathPointGlyphid point : explored) {
            if (point.exploredWays != 63) {
               for (EnumFacing face : EnumFacing.VALUES) {
                  if ((point.exploredWays & 1 << face.getIndex()) == 0) {
                     PathPointGlyphid next = point.offset(face);
                     if (this.entity.checkPosition(world, next)) {
                        next.previous = point;
                        next.exploredWays = next.exploredWays | 1 << face.getOpposite().getIndex();
                        PathPointGlyphid alreadyhas1 = alreadyHas(explored, next);
                        PathPointGlyphid alreadyhas2 = alreadyHas(exploredAdd, next);
                        if (alreadyhas1 != null && next.cost < alreadyhas1.cost) {
                           alreadyhas1.previous = point;
                           alreadyhas1.cost = next.cost;
                           alreadyhas1.exploredWays = alreadyhas1.exploredWays | next.exploredWays;
                        }

                        if (alreadyhas2 != null && next.cost < alreadyhas2.cost) {
                           alreadyhas2.previous = point;
                           alreadyhas2.cost = next.cost;
                           alreadyhas2.exploredWays = alreadyhas2.exploredWays | next.exploredWays;
                        }

                        if (alreadyhas1 == null && alreadyhas2 == null) {
                           exploredAdd.add(next);
                        }

                        for (AbstractGlyphid glyphid : listGlyphids) {
                           if (glyphid.isAiCompatible(this)
                              && (glyphid.currentCost > next.cost || glyphid.pathDepecated)
                              && MathHelper.floor(glyphid.posX) == next.getX()
                              && MathHelper.floor(glyphid.posY + glyphid.height / 2.0F) == next.getY()
                              && MathHelper.floor(glyphid.posZ) == next.getZ()) {
                              glyphid.setPath(next);
                           }
                        }
                     }

                     point.exploredWays = point.exploredWays | 1 << face.getIndex();
                  }
               }
            }
         }

         explored.addAll(exploredAdd);
      }
   }

   public static PathPointGlyphid alreadyHas(List<PathPointGlyphid> list, PathPointGlyphid point) {
      for (PathPointGlyphid pointin : list) {
         if (pointin.getX() == point.getX()
            && pointin.getY() == point.getY()
            && pointin.getZ() == point.getZ()) {
            return pointin;
         }
      }

      return null;
   }

   @Immutable
   public static class PathPointGlyphid extends BlockPos {
      public int cost;
      public int exploredWays = 0;
      public PathPointGlyphid previous;

      public PathPointGlyphid(int x, int y, int z, int cost) {
         super(x, y, z);
         this.cost = cost;
      }

      public PathPointGlyphid(double x, double y, double z, int cost) {
         super(x, y, z);
         this.cost = cost;
      }

      public PathPointGlyphid setways(int ways) {
         this.exploredWays = ways;
         return this;
      }

      public PathPointGlyphid offset(EnumFacing facing) {
         return this.offset(facing, 1);
      }

      public PathPointGlyphid offset(EnumFacing facing, int n) {
         return n == 0
            ? this
            : new PathPointGlyphid(
               this.getX() + facing.getXOffset() * n,
               this.getY() + facing.getYOffset() * n,
               this.getZ() + facing.getZOffset() * n,
               this.cost + n
            );
      }
   }
}
