//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class PathNavigateBottom extends PathNavigate {
   public boolean shouldAvoidSun;

   public PathNavigateBottom(EntityLiving entitylivingIn, World worldIn) {
      super(entitylivingIn, worldIn);
   }

   public PathFinder getPathFinder() {
      this.nodeProcessor = new BottomNodeProcessor();
      this.nodeProcessor.setCanEnterDoors(true);
      this.nodeProcessor.setCanSwim(false);
      return new PathFinder(this.nodeProcessor);
   }

   public boolean canNavigate() {
      return this.entity.onGround && this.isInLiquid() || this.entity.isRiding();
   }

   protected Vec3d getEntityPosition() {
      return new Vec3d(this.entity.posX, this.getPathablePosY(), this.entity.posZ);
   }

   public Path getPathToPos(BlockPos pos) {
      if (this.world.getBlockState(pos).getMaterial() == Material.WATER) {
         BlockPos blockpos = pos.down();

         while (blockpos.getY() > 0 && this.world.getBlockState(blockpos).getMaterial() == Material.WATER) {
            blockpos = blockpos.down();
         }

         if (blockpos.getY() > 0) {
            return super.getPathToPos(blockpos.up());
         }

         while (
            blockpos.getY() < this.world.getHeight()
               && this.world.getBlockState(blockpos).getMaterial() == Material.WATER
         ) {
            blockpos = blockpos.up();
         }

         pos = blockpos;
      }

      if (!this.world.getBlockState(pos).getMaterial().isSolid()) {
         return super.getPathToPos(pos);
      } else {
         BlockPos blockpos1 = pos.up();

         while (blockpos1.getY() < this.world.getHeight() && this.world.getBlockState(blockpos1).getMaterial().isSolid()) {
            blockpos1 = blockpos1.up();
         }

         return super.getPathToPos(blockpos1);
      }
   }

   public Path getPathToEntityLiving(Entity entityIn) {
      return this.getPathToPos(new BlockPos(entityIn));
   }

   private int getPathablePosY() {
      if (this.entity.isInWater() && this.getCanSwim()) {
         int i = (int)this.entity.getEntityBoundingBox().minY;
         Block block = this.world
            .getBlockState(
               new BlockPos(MathHelper.floor(this.entity.posX), i, MathHelper.floor(this.entity.posZ))
            )
            .getBlock();
         int j = 0;

         while (block == Blocks.FLOWING_WATER || block == Blocks.WATER) {
            block = this.world
               .getBlockState(
                  new BlockPos(MathHelper.floor(this.entity.posX), ++i, MathHelper.floor(this.entity.posZ))
               )
               .getBlock();
            if (++j > 16) {
               return (int)this.entity.getEntityBoundingBox().minY;
            }
         }

         return i;
      } else {
         return (int)(this.entity.getEntityBoundingBox().minY + 0.5);
      }
   }

   protected void removeSunnyPath() {
      super.removeSunnyPath();
      if (this.shouldAvoidSun) {
         if (this.world
            .canSeeSky(
               new BlockPos(
                  MathHelper.floor(this.entity.posX),
                  (int)(this.entity.getEntityBoundingBox().minY + 0.5),
                  MathHelper.floor(this.entity.posZ)
               )
            )) {
            return;
         }

         for (int i = 0; i < this.currentPath.getCurrentPathLength(); i++) {
            PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
            if (this.world.canSeeSky(new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z))) {
               this.currentPath.setCurrentPathLength(i - 1);
               return;
            }
         }
      }
   }

   protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
      int i = MathHelper.floor(posVec31.x);
      int j = MathHelper.floor(posVec31.z);
      double d0 = posVec32.x - posVec31.x;
      double d1 = posVec32.z - posVec31.z;
      double d2 = d0 * d0 + d1 * d1;
      if (d2 < 1.0E-8) {
         return false;
      } else {
         double d3 = 1.0 / Math.sqrt(d2);
         d0 *= d3;
         d1 *= d3;
         sizeX += 2;
         sizeZ += 2;
         if (!this.isSafeToStandAt(i, (int)posVec31.y, j, sizeX, sizeY, sizeZ, posVec31, d0, d1)) {
            return false;
         } else {
            sizeX -= 2;
            sizeZ -= 2;
            double d4 = 1.0 / Math.abs(d0);
            double d5 = 1.0 / Math.abs(d1);
            double d6 = i - posVec31.x;
            double d7 = j - posVec31.z;
            if (d0 >= 0.0) {
               d6++;
            }

            if (d1 >= 0.0) {
               d7++;
            }

            d6 /= d0;
            d7 /= d1;
            int k = d0 < 0.0 ? -1 : 1;
            int l = d1 < 0.0 ? -1 : 1;
            int i1 = MathHelper.floor(posVec32.x);
            int j1 = MathHelper.floor(posVec32.z);
            int k1 = i1 - i;
            int l1 = j1 - j;

            while (k1 * k > 0 || l1 * l > 0) {
               if (d6 < d7) {
                  d6 += d4;
                  i += k;
                  k1 = i1 - i;
               } else {
                  d7 += d5;
                  j += l;
                  l1 = j1 - j;
               }

               if (!this.isSafeToStandAt(i, (int)posVec31.y, j, sizeX, sizeY, sizeZ, posVec31, d0, d1)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public boolean isSafeToStandAt(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3d vec31, double p_179683_8_, double p_179683_10_) {
      int i = x - sizeX / 2;
      int j = z - sizeZ / 2;
      if (!this.isPositionClear(i, y, j, sizeX, sizeY, sizeZ, vec31, p_179683_8_, p_179683_10_)) {
         return false;
      } else {
         for (int k = i; k < i + sizeX; k++) {
            for (int l = j; l < j + sizeZ; l++) {
               double d0 = k + 0.5 - vec31.x;
               double d1 = l + 0.5 - vec31.z;
               if (d0 * p_179683_8_ + d1 * p_179683_10_ >= 0.0) {
                  PathNodeType pathnodetype = this.nodeProcessor
                     .getPathNodeType(this.world, k, y - 1, l, this.entity, sizeX, sizeY, sizeZ, true, true);
                  if (pathnodetype == PathNodeType.WATER) {
                     return false;
                  }

                  if (pathnodetype == PathNodeType.LAVA) {
                     return false;
                  }

                  if (pathnodetype == PathNodeType.OPEN) {
                     return false;
                  }

                  pathnodetype = this.nodeProcessor.getPathNodeType(this.world, k, y, l, this.entity, sizeX, sizeY, sizeZ, true, true);
                  float f = this.entity.getPathPriority(pathnodetype);
                  if (f < 0.0F || f >= 8.0F) {
                     return false;
                  }

                  if (pathnodetype == PathNodeType.DAMAGE_FIRE || pathnodetype == PathNodeType.DANGER_FIRE || pathnodetype == PathNodeType.DAMAGE_OTHER) {
                     return false;
                  }
               }
            }
         }

         IBlockState stat = this.world.getBlockState(new BlockPos(x, y, z));
         return stat.getBlock() == Blocks.WATER || stat.getBlock() == Blocks.FLOWING_WATER;
      }
   }

   public boolean isPositionClear(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3d p_179692_7_, double p_179692_8_, double p_179692_10_) {
      for (BlockPos blockpos : BlockPos.getAllInBox(new BlockPos(x, y, z), new BlockPos(x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1))) {
         double d0 = blockpos.getX() + 0.5 - p_179692_7_.x;
         double d1 = blockpos.getZ() + 0.5 - p_179692_7_.z;
         if (d0 * p_179692_8_ + d1 * p_179692_10_ >= 0.0) {
            IBlockState stat = this.world.getBlockState(blockpos);
            Block block = stat.getBlock();
            if (!block.isPassable(this.world, blockpos) || stat.getMaterial() != Material.WATER) {
               return false;
            }
         }
      }

      return true;
   }

   public void setBreakDoors(boolean canBreakDoors) {
      this.nodeProcessor.setCanOpenDoors(canBreakDoors);
   }

   public void setEnterDoors(boolean enterDoors) {
      this.nodeProcessor.setCanEnterDoors(enterDoors);
   }

   public boolean getEnterDoors() {
      return this.nodeProcessor.getCanEnterDoors();
   }

   public void setCanSwim(boolean canSwim) {
      this.nodeProcessor.setCanSwim(canSwim);
   }

   public boolean getCanSwim() {
      return this.nodeProcessor.getCanSwim();
   }

   public void setAvoidSun(boolean avoidSun) {
      this.shouldAvoidSun = avoidSun;
   }

   public class BottomNodeProcessor extends WalkNodeProcessor {
      public PathPoint getStart() {
         int i;
         if (this.getCanSwim() && this.entity.isInWater()) {
            i = (int)this.entity.getEntityBoundingBox().minY;
            MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos(
               MathHelper.floor(this.entity.posX), i, MathHelper.floor(this.entity.posZ)
            );

            for (Block block = this.blockaccess.getBlockState(blockpos$mutableblockpos).getBlock();
               block == Blocks.FLOWING_WATER || block == Blocks.WATER;
               block = this.blockaccess.getBlockState(blockpos$mutableblockpos).getBlock()
            ) {
               blockpos$mutableblockpos.setPos(
                  MathHelper.floor(this.entity.posX), ++i, MathHelper.floor(this.entity.posZ)
               );
            }
         } else if (this.entity.onGround) {
            i = MathHelper.floor(this.entity.getEntityBoundingBox().minY + 0.5);
         } else {
            BlockPos blockpos = new BlockPos(this.entity);

            while (
               this.blockaccess.getBlockState(blockpos).getMaterial() == Material.WATER
                  && this.blockaccess.getBlockState(blockpos).getBlock().isPassable(this.blockaccess, blockpos)
                  && blockpos.getY() > 0
            ) {
               blockpos = blockpos.down();
            }

            i = blockpos.up().getY();
         }

         BlockPos blockpos2 = new BlockPos(this.entity);
         PathNodeType pathnodetype1 = this.getPathNodeType(this.entity, blockpos2.getX(), i, blockpos2.getZ());
         if (this.entity.getPathPriority(pathnodetype1) < 0.0F) {
            Set<BlockPos> set = Sets.newHashSet();
            set.add(new BlockPos(this.entity.getEntityBoundingBox().minX, i, this.entity.getEntityBoundingBox().minZ));
            set.add(new BlockPos(this.entity.getEntityBoundingBox().minX, i, this.entity.getEntityBoundingBox().maxZ));
            set.add(new BlockPos(this.entity.getEntityBoundingBox().maxX, i, this.entity.getEntityBoundingBox().minZ));
            set.add(new BlockPos(this.entity.getEntityBoundingBox().maxX, i, this.entity.getEntityBoundingBox().maxZ));

            for (BlockPos blockpos1 : set) {
               PathNodeType pathnodetype = this.getPathNodeType(this.entity, blockpos1);
               if (this.entity.getPathPriority(pathnodetype) >= 0.0F) {
                  return this.openPoint(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
               }
            }
         }

         return this.openPoint(blockpos2.getX(), i, blockpos2.getZ());
      }

      private PathNodeType getPathNodeType(EntityLiving entitylivingIn, BlockPos pos) {
         return this.getPathNodeType(entitylivingIn, pos.getX(), pos.getY(), pos.getZ());
      }

      private PathNodeType getPathNodeType(EntityLiving entitylivingIn, int x, int y, int z) {
         return this.getPathNodeType(
            this.blockaccess,
            x,
            y,
            z,
            entitylivingIn,
            this.entitySizeX,
            this.entitySizeY,
            this.entitySizeZ,
            this.getCanOpenDoors(),
            this.getCanEnterDoors()
         );
      }
   }
}
