package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class WorldGenCrystalIsland extends WorldGenAdvanced {
   public int smoothlayers = 4;
   public int smoothStartLayer = 2;
   public float unsmoothPlaceChance = 0.75F;
   public float stalactiteChance = 0.25F;
   public int crystalCountMin = 15;
   public int crystalCountRandAdd = 11;
   public int radiusMin = 2;
   public int radiusRandAdd = 10;
   public int lengthMin = 15;
   public int lengthRandAdd = 35;
   public Block[] blockArray = new Block[]{Blocks.STONE, Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE, Blocks.SANDSTONE, Blocks.CLAY};
   public Block blockCrystal = BlocksRegister.DOLERITE;
   public Block blockGrass = BlocksRegister.FULMINIFLORA;
   public NoiseGeneratorPerlin perlin1;
   public NoiseGeneratorPerlin perlin2;
   public NoiseGeneratorPerlin perlin3;

   public boolean generate(World world, Random rand, BlockPos position) {
      this.generateCrystalIsland(rand, new Vec3d(position), 0, 1.0F);
      return true;
   }

   public void generateCrystalIsland(Random rand, Vec3d pos, int crystalType, float scaleFactor) {
      double minX = pos.x;
      double minY = pos.y;
      double minZ = pos.z;
      double maxX = pos.x;
      double maxY = pos.y;
      double maxZ = pos.z;
      Block blockStone = this.blockArray[0];
      if (crystalType == 0) {
         int crystalCount = this.crystalCountMin + rand.nextInt(this.crystalCountRandAdd);

         for (int i = 0; i < crystalCount; i++) {
            Vec3d direction = new Vec3d(rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian()).normalize();
            int radius = (int)((this.radiusMin + rand.nextInt(this.radiusRandAdd)) * scaleFactor);
            Vec3d tip = this.setLargeOctahedronCrystal(
               pos.add(direction.scale(radius)),
               direction,
               blockStone.getDefaultState(),
               radius,
               (int)((this.lengthMin + rand.nextInt(this.lengthRandAdd)) * scaleFactor)
            );
            if (tip.x - radius < minX) {
               minX = tip.x - radius;
            }

            if (tip.y - radius < minY) {
               minY = tip.y - radius;
            }

            if (tip.z - radius < minZ) {
               minZ = tip.z - radius;
            }

            if (tip.x + radius > maxX) {
               maxX = tip.x + radius;
            }

            if (tip.y + radius > maxY) {
               maxY = tip.y + radius;
            }

            if (tip.z + radius > maxZ) {
               maxZ = tip.z + radius;
            }
         }
      }

      if (crystalType == 1) {
         if (this.perlin1 == null || this.perlin2 == null || this.perlin3 == null) {
            this.perlin1 = new NoiseGeneratorPerlin(new Random(this.getWorldSeed()), 4);
            this.perlin2 = new NoiseGeneratorPerlin(new Random(this.getWorldSeed() + 1L), 4);
            this.perlin3 = new NoiseGeneratorPerlin(new Random(this.getWorldSeed() + 2L), 4);
         }

         int crystalCount = this.crystalCountMin + rand.nextInt(this.crystalCountRandAdd);

         for (int i = 0; i < crystalCount; i++) {
            double posScale = scaleFactor * (18.0F + Debugger.floats[4]);
            Vec3d crPos = pos.add(rand.nextGaussian() * posScale, rand.nextGaussian() * posScale, rand.nextGaussian() * posScale);
            double scaledPosX = crPos.x / 50.0;
            double scaledPosZ = crPos.z / 50.0;
            Vec3d directionx = new Vec3d(
                  this.perlin1.getValue(scaledPosX, scaledPosZ),
                  this.perlin2.getValue(scaledPosX, scaledPosZ),
                  this.perlin3.getValue(scaledPosX, scaledPosZ)
               )
               .normalize();
            int radiusx = (int)((this.radiusMin + rand.nextInt(this.radiusRandAdd)) * scaleFactor);
            int length = (int)((this.lengthMin + rand.nextInt(this.lengthRandAdd)) * scaleFactor) * 2;
            Vec3d finalPos = crPos.add(directionx.scale(-length / 2.0));
            Vec3d tipx = this.setLargeOctahedronCrystal(finalPos, directionx, blockStone.getDefaultState(), radiusx, length);
            if (tipx.x - radiusx < minX) {
               minX = tipx.x - radiusx;
            }

            if (tipx.y - radiusx < minY) {
               minY = tipx.y - radiusx;
            }

            if (tipx.z - radiusx < minZ) {
               minZ = tipx.z - radiusx;
            }

            if (tipx.x + radiusx > maxX) {
               maxX = tipx.x + radiusx;
            }

            if (tipx.y + radiusx > maxY) {
               maxY = tipx.y + radiusx;
            }

            if (tipx.z + radiusx > maxZ) {
               maxZ = tipx.z + radiusx;
            }

            if (finalPos.x - radiusx < minX) {
               minX = finalPos.x - radiusx;
            }

            if (finalPos.y - radiusx < minY) {
               minY = finalPos.y - radiusx;
            }

            if (finalPos.z - radiusx < minZ) {
               minZ = finalPos.z - radiusx;
            }

            if (finalPos.x + radiusx > maxX) {
               maxX = finalPos.x + radiusx;
            }

            if (finalPos.y + radiusx > maxY) {
               maxY = finalPos.y + radiusx;
            }

            if (finalPos.z + radiusx > maxZ) {
               maxZ = finalPos.z + radiusx;
            }
         }
      }

      int expand = this.smoothlayers;
      int minX2 = MathHelper.floor(minX - expand);
      int maxX2 = MathHelper.ceil(maxX + expand);
      int minY2 = MathHelper.floor(minY);
      int maxY2 = MathHelper.ceil(maxY);
      int minZ2 = MathHelper.floor(minZ - expand);
      int maxZ2 = MathHelper.ceil(maxZ + expand);
      if (this.getBuilder() != null && this.getBuilder().advancedMode) {
         minX2 = Math.max(minX2, this.getBuilder().getAdvancedModeMinX());
         maxX2 = Math.min(maxX2, this.getBuilder().getAdvancedModeMaxX());
         minZ2 = Math.max(minZ2, this.getBuilder().getAdvancedModeMinZ());
         maxZ2 = Math.min(maxZ2, this.getBuilder().getAdvancedModeMaxZ());
      }

      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

      for (int x = minX2; x < maxX2; x++) {
         for (int z = minZ2; z < maxZ2; z++) {
            boolean prevAir = true;
            boolean canSeeSky = true;

            for (int y = maxY2; y > minY2; y--) {
               blockpos$pooledmutableblockpos.setPos(x, y, z);
               IBlockState state = this.getBlockState(blockpos$pooledmutableblockpos);
               if (state.getBlock() != Blocks.AIR) {
                  if (!prevAir || state.getBlock() == blockStone && !canSeeSky) {
                     if (state.getBlock() == blockStone) {
                        this.setBlockState(blockpos$pooledmutableblockpos, this.blockCrystal.getDefaultState(), 2);
                     } else {
                        this.setBlockState(blockpos$pooledmutableblockpos, blockStone.getDefaultState(), 2);
                     }
                  } else {
                     this.setBlockState(blockpos$pooledmutableblockpos, this.blockGrass.getDefaultState(), 2);
                     this.decorateIsland(blockpos$pooledmutableblockpos.up(), rand);
                  }

                  canSeeSky = false;
                  prevAir = false;
               } else {
                  prevAir = true;
               }
            }
         }
      }

      blockpos$pooledmutableblockpos.release();
   }

   public void setLargeBlockCrystal(Vec3d pos, Vec3d normalizedDirection, IBlockState state, double radius, int length) {
      AxisAlignedBB aabb = GetMOP.newAABB(pos, radius);
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

      for (int i = 0; i < length; i++) {
         int j2 = MathHelper.floor(aabb.minX);
         int k2 = MathHelper.ceil(aabb.maxX);
         int l2 = MathHelper.floor(aabb.minY);
         int i3 = MathHelper.ceil(aabb.maxY);
         int j3 = MathHelper.floor(aabb.minZ);
         int k3 = MathHelper.ceil(aabb.maxZ);

         for (int l3 = j2; l3 < k2; l3++) {
            for (int i4 = l2; i4 < i3; i4++) {
               for (int j4 = j3; j4 < k3; j4++) {
                  this.setBlockState(blockpos$pooledmutableblockpos.setPos(l3, i4, j4), state, 2);
               }
            }
         }

         aabb = aabb.offset(normalizedDirection);
      }

      blockpos$pooledmutableblockpos.release();
   }

   public Vec3d setLargeOctahedronCrystal(Vec3d pos, Vec3d normalizedDirection, IBlockState state, int radius, int length) {
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

      for (int i = 0; i < length; i++) {
         for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
               this.setBlockState(blockpos$pooledmutableblockpos.setPos(pos.x + x, pos.y, pos.z + z), state, 2);
            }
         }

         int radius2 = radius - 1;

         for (int y = 1; radius2 >= 0; y++) {
            for (int x = -radius2; x <= radius2; x++) {
               for (int z = -radius2; z <= radius2; z++) {
                  this.setBlockState(
                     blockpos$pooledmutableblockpos.setPos(pos.x + x, pos.y + y, pos.z + z), state, 2
                  );
               }
            }

            radius2--;
         }

         radius2 = radius - 1;

         for (int y = -1; radius2 >= 0; y--) {
            for (int x = -radius2; x <= radius2; x++) {
               for (int z = -radius2; z <= radius2; z++) {
                  this.setBlockState(
                     blockpos$pooledmutableblockpos.setPos(pos.x + x, pos.y + y, pos.z + z), state, 2
                  );
               }
            }

            radius2--;
         }

         pos = pos.add(normalizedDirection);
      }

      blockpos$pooledmutableblockpos.release();
      return pos;
   }

   public void decorateIsland(BlockPos pos, Random rand) {
      if (rand.nextFloat() < 0.01F) {
         this.setBlockState(pos, BlocksRegister.FULMINIORTUMBONNY.getDefaultState(), 2);
      } else if (rand.nextFloat() < 0.015F) {
         this.setBlockState(pos, BlocksRegister.GLOWBUSH.getDefaultState(), 2);
      } else if (rand.nextFloat() < 0.28F) {
         this.setBlockState(pos, BlocksRegister.FULMINIHERBA.getDefaultState(), 2);
      } else if (rand.nextFloat() < 0.75F && this.perlin1.getValue(pos.getX() / 13.0, pos.getZ() / 13.0) > 4.5) {
         this.setBlockState(pos, BlocksRegister.FULMINIORTUMBULB.getDefaultState(), 2);
      }
   }
}
