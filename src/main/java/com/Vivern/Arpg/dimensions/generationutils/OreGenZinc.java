//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.generationutils;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGenZinc implements IWorldGenerator {
   public IBlockState oreState;
   public int[] dimensions;
   public Predicate<IBlockState> blocksToReplace;
   public int blockAmountMin;
   public int blockAmountMax;
   public float chancesToSpawn;
   public int minHeight;
   public int maxHeight;
   public IBlockState[] falsiveblocks;
   public float falsivechance;
   public boolean usefalsive;

   public OreGenZinc(
      IBlockState oreState,
      int[] dimensions,
      Predicate<IBlockState> blocksToReplace,
      int blockAmountMin,
      int blockAmountMax,
      float chancesToSpawn,
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

   public OreGenZinc(
      IBlockState oreState,
      int dimension,
      Predicate<IBlockState> blocksToReplace,
      int blockAmountMin,
      int blockAmountMax,
      float chancesToSpawn,
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

   public OreGenZinc setFalsiveBlocks(float falsivechance, IBlockState... falsiveblocks) {
      this.falsiveblocks = falsiveblocks;
      this.falsivechance = falsivechance;
      this.usefalsive = true;
      return this;
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

   public void runGenerator(
      IBlockState blockToGen,
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
         int heightdiff = maxHeight - minHeight + 1;
         int boundXP = chunk_X * 16 + 15;
         int boundXM = chunk_X * 16;
         int boundZP = chunk_Z * 16 + 15;
         int boundZM = chunk_Z * 16;

         for (float i = chancesToSpawn; i > 0.0F; i--) {
            if (i < 1.0F && rand.nextFloat() > i) {
               return;
            }

            int x = chunk_X * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightdiff);
            int z = chunk_Z * 16 + rand.nextInt(16);
            BlockPos poss = new BlockPos(x, y, z);

            for (int r = 0; r < blockAmount; r++) {
               if (blockToReplace.apply(world.getBlockState(poss))) {
                  if (this.usefalsive && rand.nextFloat() < this.falsivechance) {
                     IBlockState blockf = this.falsiveblocks[rand.nextInt(this.falsiveblocks.length)];
                     if (rand.nextFloat() < 0.4F) {
                        for (int t = 0; t < 16; t++) {
                           GenerationHelper.setIfReplaciableOregen(
                              world, poss.add(rand.nextGaussian() * 1.8, 0.0, rand.nextGaussian() * 1.8), blockf, 2, blockToReplace
                           );
                        }
                     } else {
                        GenerationHelper.setIfReplaciableOregen(world, poss, blockf, 2, blockToReplace);
                     }
                  } else {
                     GenerationHelper.setIfReplaciableOregen(world, poss, blockToGen, 2, blockToReplace);
                  }
               }

               poss = new BlockPos(
                  MathHelper.clamp(poss.getX() + rand.nextGaussian(), boundXM, boundXP),
                  poss.getY() + rand.nextFloat() * 1.75F,
                  MathHelper.clamp(poss.getZ() + rand.nextGaussian(), boundZM, boundZP)
               );
               if (poss.getY() > maxHeight) {
                  return;
               }
            }
         }
      } else {
         throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
      }
   }
}
