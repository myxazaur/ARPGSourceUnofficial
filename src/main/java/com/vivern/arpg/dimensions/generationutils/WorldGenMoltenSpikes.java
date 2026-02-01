package com.vivern.arpg.dimensions.generationutils;

import com.vivern.arpg.main.BlocksRegister;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenMoltenSpikes implements IWorldGenerator {
   public NoiseGeneratorPerlin perlin;
   public NoiseGeneratorPerlin perlin2;
   public NoiseGeneratorPerlin perlin3;
   public NoiseGeneratorPerlin perlin4;
   private NoiseGeneratorPerlin surfaceNoise;

   public List<BlockPos> getCircle(BlockPos center, int radius) {
      List<BlockPos> list = new ArrayList<>();

      for (double x = -radius; x <= radius; x++) {
         double z1 = Math.sqrt(radius * radius - x * x);
         double z2 = -Math.sqrt(radius * radius - x * x);
         list.add(new BlockPos(center.getX() + x, center.getY(), center.getZ() + z1));
         list.add(new BlockPos(center.getX() + x, center.getY(), center.getZ() + z2));
      }

      return list;
   }

   public List<BlockPos> getCircleRotated(BlockPos center, int radius) {
      List<BlockPos> list = new ArrayList<>();

      for (double z = -radius; z <= radius; z++) {
         double x1 = Math.sqrt(radius * radius - z * z);
         double x2 = -Math.sqrt(radius * radius - z * z);
         list.add(new BlockPos(center.getX() + x1, center.getY(), center.getZ() + z));
         list.add(new BlockPos(center.getX() + x2, center.getY(), center.getZ() + z));
      }

      return list;
   }

   public static void fillWithLava(World worldIn, Random random, BlockPos pos, EnumFacing prev, int samples) {
      if (samples > 0) {
         for (EnumFacing face1 : EnumFacing.VALUES) {
            if (face1 != EnumFacing.UP && face1 != prev) {
               BlockPos pos1 = pos.offset(face1);
               if (worldIn.isAirBlock(pos1)) {
                  worldIn.setBlockState(pos1, Blocks.LAVA.getDefaultState());
                  fillWithLava(worldIn, random, pos1, face1.getOpposite(), --samples);
               }
            }
         }
      }
   }

   public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
      if (-1 == world.provider.getDimension()) {
         for (int i = 0; i < 2; i++) {
            boolean rot = rand.nextBoolean();
            boolean oregen = rand.nextFloat() < 0.4F;
            int x = chunkX * 16 + rand.nextInt(16) + 8;
            int y = 20;
            int z = chunkZ * 16 + rand.nextInt(16) + 8;
            if (world.getBlockState(new BlockPos(x, 31, z)).getBlock() == Blocks.LAVA) {
               int h = y + rand.nextInt(32);
               int r = rand.nextInt(5) + 2;
               float timeNoReduce = 0.0F;
               int oregencount = rand.nextInt(4);
               boolean genStarted = false;
               if (h == 32) {
                  h -= 4;
               }

               for (int ii = y; ii < h; ii++) {
                  BlockPos center = new BlockPos(x, ii, z);

                  for (BlockPos fpos : rot ? this.getCircleRotated(center, r) : this.getCircle(center, r)) {
                     world.setBlockState(fpos, Blocks.NETHERRACK.getDefaultState());
                  }

                  for (BlockPos fpos : rot ? this.getCircle(center, r) : this.getCircleRotated(center, r)) {
                     if (world.isAirBlock(fpos)) {
                        world.setBlockState(fpos, Blocks.MAGMA.getDefaultState());
                     }
                  }

                  if (oregencount > 0 && oregen && (world.getBlockState(center).getBlock() == Blocks.LAVA || genStarted)) {
                     world.setBlockState(center, BlocksRegister.OREMOLTEN.getDefaultState());
                     oregencount--;
                     genStarted = true;
                  }

                  if (rand.nextFloat() < 0.05 + timeNoReduce) {
                     r--;
                     timeNoReduce = 0.0F;
                  } else {
                     timeNoReduce += 0.1F;
                  }

                  if (r < 1) {
                     if (oregen && rand.nextFloat() < 0.3) {
                        world.setBlockState(center.up(), BlocksRegister.OREMOLTEN.getDefaultState());
                        break;
                     }

                     if (oregen && rand.nextFloat() < 0.3) {
                        world.setBlockState(center, BlocksRegister.OREMOLTEN.getDefaultState());
                        break;
                     }

                     if (rand.nextFloat() < 0.15) {
                        world.setBlockState(center.up(), Blocks.FLOWING_LAVA.getDefaultState());
                     } else {
                        fillWithLava(world, rand, center, EnumFacing.UP, 50);
                     }
                     break;
                  }

                  if (ii == h - 1) {
                     fillWithLava(world, rand, rand.nextBoolean() ? center : center.down(), EnumFacing.UP, 50);
                  }
               }
            }
         }
      }
   }
}
