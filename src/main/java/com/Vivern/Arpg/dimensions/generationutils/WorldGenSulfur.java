package com.Vivern.Arpg.dimensions.generationutils;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenSulfur implements IWorldGenerator {
   public IBlockState oreState;
   public IBlockState oreState2;
   public int[] dimensions;
   public Predicate<IBlockState> blocksToReplace;
   public int blockAmountMin;
   public int blockAmountMax;
   public float chancesToSpawn;
   public int minHeight;
   public int maxHeight;
   public float emptychance;

   public WorldGenSulfur(
      IBlockState oreState,
      IBlockState oreState2,
      float chanceToAir,
      int[] dimensions,
      Predicate<IBlockState> blocksToReplace,
      int blockAmountMin,
      int blockAmountMax,
      float chancesToSpawn,
      int minHeight,
      int maxHeight
   ) {
      this.oreState = oreState;
      this.oreState2 = oreState2;
      this.dimensions = dimensions;
      this.blocksToReplace = blocksToReplace;
      this.blockAmountMin = blockAmountMin;
      this.blockAmountMax = blockAmountMax;
      this.chancesToSpawn = chancesToSpawn;
      this.minHeight = minHeight;
      this.maxHeight = maxHeight;
      this.emptychance = chanceToAir;
   }

   public WorldGenSulfur(
      IBlockState oreState,
      IBlockState oreState2,
      float chanceToAir,
      int dimension,
      Predicate<IBlockState> blocksToReplace,
      int blockAmountMin,
      int blockAmountMax,
      float chancesToSpawn,
      int minHeight,
      int maxHeight
   ) {
      this(oreState, oreState2, chanceToAir, new int[]{dimension}, blocksToReplace, blockAmountMin, blockAmountMax, chancesToSpawn, minHeight, maxHeight);
   }

   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
      int dim = world.provider.getDimension();

      for (int succdim : this.dimensions) {
         if (succdim == dim) {
            this.runGenerator(
               dim,
               random.nextInt(this.blockAmountMax - this.blockAmountMin + 1) + this.blockAmountMin,
               this.chancesToSpawn,
               this.minHeight,
               this.maxHeight,
               this.blocksToReplace,
               world,
               random,
               chunkX,
               chunkZ
            );
         }
      }
   }

   public void runGenerator(
      int dim,
      int blockAmount,
      float chancesToSpawn,
      int minHeight,
      int maxHeight,
      Predicate<IBlockState> blockToReplace,
      World world,
      Random rand,
      int chunk_X,
      int chunk_Z
   ) {
      if (minHeight >= 0 && maxHeight <= 256 && minHeight <= maxHeight) {
         boolean nethergenic = false;
         if (dim == -1) {
            nethergenic = rand.nextFloat() < 0.5;
         }

         int heightdiff = maxHeight - minHeight + 1;

         for (float i = chancesToSpawn; i > 0.0F; i--) {
            if (i < 1.0F && rand.nextFloat() > i) {
               return;
            }

            int x = chunk_X * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightdiff);
            int z = chunk_Z * 16 + rand.nextInt(16);

            label89:
            for (BlockPos poss : this.generateMinable(world, rand, new BlockPos(x, y, z), this.oreState, blockAmount, blockToReplace)) {
               if (rand.nextFloat() < this.emptychance) {
                  world.setBlockState(poss, Blocks.AIR.getDefaultState(), 2);
               } else if (!collidesWithBlock(world, poss, blockToReplace)) {
                  if (!nethergenic) {
                     world.setBlockState(poss, this.oreState2, 2);
                  } else if (!world.isAirBlock(poss.up(5))) {
                     if (rand.nextFloat() < 0.2) {
                        world.setBlockState(poss, Blocks.AIR.getDefaultState(), 2);
                        world.setBlockState(poss.up(1), Blocks.AIR.getDefaultState(), 2);
                        world.setBlockState(poss.up(2), Blocks.AIR.getDefaultState(), 2);
                        world.setBlockState(poss.up(3), Blocks.AIR.getDefaultState(), 2);
                        world.setBlockState(poss.up(4), Blocks.AIR.getDefaultState(), 2);
                        world.setBlockState(poss.up(5), Blocks.FLOWING_LAVA.getDefaultState(), 2);
                     } else {
                        world.setBlockState(poss, Blocks.AIR.getDefaultState(), 2);
                     }
                  } else {
                     if (rand.nextFloat() < 0.02) {
                        world.setBlockState(poss, this.oreState2, 2);
                     } else {
                        world.setBlockState(poss, Blocks.AIR.getDefaultState(), 2);
                     }

                     for (int yy = 1; yy < 4; yy++) {
                        BlockPos posd = poss.down(yy);
                        IBlockState st = world.getBlockState(posd);
                        if (st.getBlock() == this.oreState.getBlock()) {
                           world.setBlockState(posd, Blocks.AIR.getDefaultState(), 2);
                        } else if (blockToReplace.apply(st)) {
                           world.setBlockState(posd, Blocks.FLOWING_LAVA.getDefaultState(), 2);
                           if (blockToReplace.apply(world.getBlockState(poss.down(1)))) {
                              world.setBlockState(poss.down(1), Blocks.FLOWING_LAVA.getDefaultState(), 2);
                           }

                           if (blockToReplace.apply(world.getBlockState(poss.down(2)))) {
                              world.setBlockState(poss.down(2), Blocks.FLOWING_LAVA.getDefaultState(), 2);
                           }

                           if (blockToReplace.apply(world.getBlockState(poss.down(3)))) {
                              world.setBlockState(poss.down(3), Blocks.FLOWING_LAVA.getDefaultState(), 2);
                           }
                           continue label89;
                        }
                     }

                     return;
                  }
               }
            }
         }
      } else {
         throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
      }
   }

   public List<BlockPos> generateMinable(World worldIn, Random rand, BlockPos position, IBlockState oreBlock, int blockCount, Predicate<IBlockState> predicate) {
      List<BlockPos> list = new ArrayList<>();
      float f = rand.nextFloat() * (float) Math.PI;
      double d0 = position.getX() + 8 + MathHelper.sin(f) * blockCount / 8.0F;
      double d1 = position.getX() + 8 - MathHelper.sin(f) * blockCount / 8.0F;
      double d2 = position.getZ() + 8 + MathHelper.cos(f) * blockCount / 8.0F;
      double d3 = position.getZ() + 8 - MathHelper.cos(f) * blockCount / 8.0F;
      double d4 = position.getY() + rand.nextInt(3) - 2;
      double d5 = position.getY() + rand.nextInt(3) - 2;

      for (int i = 0; i < blockCount; i++) {
         float f1 = (float)i / blockCount;
         double d6 = d0 + (d1 - d0) * f1;
         double d7 = d4 + (d5 - d4) * f1;
         double d8 = d2 + (d3 - d2) * f1;
         double d9 = rand.nextDouble() * blockCount / 16.0;
         double d10 = (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0;
         double d11 = (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0;
         int j = MathHelper.floor(d6 - d10 / 2.0);
         int k = MathHelper.floor(d7 - d11 / 2.0);
         int l = MathHelper.floor(d8 - d10 / 2.0);
         int i1 = MathHelper.floor(d6 + d10 / 2.0);
         int j1 = MathHelper.floor(d7 + d11 / 2.0);
         int k1 = MathHelper.floor(d8 + d10 / 2.0);

         for (int l1 = j; l1 <= i1; l1++) {
            double d12 = (l1 + 0.5 - d6) / (d10 / 2.0);
            if (d12 * d12 < 1.0) {
               for (int i2 = k; i2 <= j1; i2++) {
                  double d13 = (i2 + 0.5 - d7) / (d11 / 2.0);
                  if (d12 * d12 + d13 * d13 < 1.0) {
                     for (int j2 = l; j2 <= k1; j2++) {
                        double d14 = (j2 + 0.5 - d8) / (d10 / 2.0);
                        if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0) {
                           BlockPos blockpos = new BlockPos(l1, i2, j2);
                           IBlockState state = worldIn.getBlockState(blockpos);
                           if (state.getBlock().isReplaceableOreGen(state, worldIn, blockpos, predicate)) {
                              list.add(blockpos);
                              worldIn.setBlockState(blockpos, oreBlock, 2);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return list;
   }

   public static boolean collidesWithBlock(World world, BlockPos pos, Predicate<IBlockState> predicate) {
      return predicate.apply(world.getBlockState(pos.up()))
         || predicate.apply(world.getBlockState(pos.down()))
         || predicate.apply(world.getBlockState(pos.west()))
         || predicate.apply(world.getBlockState(pos.south()))
         || predicate.apply(world.getBlockState(pos.north()))
         || predicate.apply(world.getBlockState(pos.east()));
   }
}
