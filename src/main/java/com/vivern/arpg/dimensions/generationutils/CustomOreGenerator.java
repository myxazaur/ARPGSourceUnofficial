package com.vivern.arpg.dimensions.generationutils;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class CustomOreGenerator implements IWorldGenerator {
   public IBlockState oreState;
   public int[] dimensions;
   public Predicate<IBlockState> blocksToReplace;
   public int blockAmountMin;
   public int blockAmountMax;
   public int chancesToSpawn;
   public int minHeight;
   public int maxHeight;

   public CustomOreGenerator(
      IBlockState oreState,
      int[] dimensions,
      Predicate<IBlockState> blocksToReplace,
      int blockAmountMin,
      int blockAmountMax,
      int chancesToSpawn,
      int minHeight,
      int maxHeight
   ) {
      this.oreState = oreState;
      this.dimensions = dimensions;
      this.blocksToReplace = blocksToReplace;
      this.blockAmountMin = blockAmountMin;
      this.blockAmountMax = blockAmountMax;
      this.chancesToSpawn = chancesToSpawn;
      this.minHeight = minHeight;
      this.maxHeight = maxHeight;
   }

   public CustomOreGenerator(
      IBlockState oreState,
      int dimension,
      Predicate<IBlockState> blocksToReplace,
      int blockAmountMin,
      int blockAmountMax,
      int chancesToSpawn,
      int minHeight,
      int maxHeight
   ) {
      this.oreState = oreState;
      this.dimensions = new int[]{dimension};
      this.blocksToReplace = blocksToReplace;
      this.blockAmountMin = blockAmountMin;
      this.blockAmountMax = blockAmountMax;
      this.chancesToSpawn = chancesToSpawn;
      this.minHeight = minHeight;
      this.maxHeight = maxHeight;
   }

   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
      int dim = world.provider.getDimension();

      for (int succdim : this.dimensions) {
         if (succdim == dim) {
            this.runGenerator(
               this.oreState,
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

   private void runGenerator(
      IBlockState blockToGen,
      int blockAmount,
      int chancesToSpawn,
      int minHeight,
      int maxHeight,
      Predicate<IBlockState> blockToReplace,
      World world,
      Random rand,
      int chunk_X,
      int chunk_Z
   ) {
      if (minHeight >= 0 && maxHeight <= 256 && minHeight <= maxHeight) {
         WorldGenMinable generator = new WorldGenMinable(blockToGen, blockAmount, blockToReplace);
         int heightdiff = maxHeight - minHeight + 1;

         for (int i = 0; i < chancesToSpawn; i++) {
            int x = chunk_X * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightdiff);
            int z = chunk_Z * 16 + rand.nextInt(16);
            generator.generate(world, rand, new BlockPos(x, y, z));
         }
      } else {
         throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
      }
   }
}
