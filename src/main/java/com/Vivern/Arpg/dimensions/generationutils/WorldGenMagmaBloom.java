package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.main.GetMOP;
import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenMagmaBloom implements IWorldGenerator {
   public IBlockState oreState;
   public int[] dimensions;
   public Predicate<IBlockState> blocksGround;
   public int blockAmountMin;
   public int blockAmountMax;
   public float chancesToSpawn;
   public int minHeight;
   public int maxHeight;
   public int maxMeta;

   public WorldGenMagmaBloom(
      IBlockState oreState,
      int[] dimensions,
      Predicate<IBlockState> blocksGround,
      int blockAmountMin,
      int blockAmountMax,
      float chancesToSpawn,
      int minHeight,
      int maxHeight,
      int maxMeta
   ) {
      this.oreState = oreState;
      this.dimensions = dimensions;
      this.blocksGround = blocksGround;
      this.blockAmountMin = blockAmountMin;
      this.blockAmountMax = blockAmountMax;
      this.chancesToSpawn = chancesToSpawn;
      this.minHeight = minHeight;
      this.maxHeight = maxHeight;
      this.maxMeta = maxMeta;
   }

   public WorldGenMagmaBloom(
      IBlockState oreState,
      int dimension,
      Predicate<IBlockState> blocksGround,
      int blockAmountMin,
      int blockAmountMax,
      float chancesToSpawn,
      int minHeight,
      int maxHeight,
      int maxMeta
   ) {
      this(oreState, new int[]{dimension}, blocksGround, blockAmountMin, blockAmountMax, chancesToSpawn, minHeight, maxHeight, maxMeta);
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
               world,
               random,
               chunkX,
               chunkZ
            );
         }
      }
   }

   public void runGenerator(
      IBlockState blockToGen, int blockAmount, float chancesToSpawn, int minHeight, int maxHeight, World world, Random rand, int chunk_X, int chunk_Z
   ) {
      if (minHeight >= 0 && maxHeight <= 256 && minHeight <= maxHeight) {
         int heightdiff = maxHeight - minHeight + 1;

         for (float i = chancesToSpawn; i > 0.0F; i--) {
            if (i < 1.0F && rand.nextFloat() > i) {
               return;
            }

            int x = chunk_X * 16 + rand.nextInt(16);
            int z = chunk_Z * 16 + rand.nextInt(16);
            GetMOP.BlockTraceResult res = GetMOP.blockTrace(world, new BlockPos(x, maxHeight, z), EnumFacing.DOWN, maxHeight - minHeight, this.blocksGround);
            if (res != null && this.blocksGround.apply(res.impactState) && world.isAirBlock(res.prevPos)) {
               for (int c = 0; c < blockAmount; c++) {
                  BlockPos fpos = res.prevPos.add(rand.nextGaussian() * 2.0, 0.0, rand.nextGaussian() * 2.0);
                  if (this.blocksGround.apply(world.getBlockState(fpos.down())) && world.isAirBlock(fpos)) {
                     if (this.maxMeta == 0) {
                        world.setBlockState(fpos, blockToGen);
                     } else {
                        world.setBlockState(fpos, blockToGen.getBlock().getStateFromMeta(rand.nextInt(this.maxMeta + 1)));
                     }
                  }
               }
            }
         }
      } else {
         throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
      }
   }
}
