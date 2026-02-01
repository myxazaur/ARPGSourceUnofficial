package com.vivern.arpg.dimensions.generationutils;

import com.vivern.arpg.main.GetMOP;
import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGenChromium implements IWorldGenerator {
   public IBlockState oreState;
   public int[] dimensions;
   public Predicate<IBlockState> blocksToReplace;
   public int blockAmountMin;
   public int blockAmountMax;
   public int chancesToSpawn;
   public int minHeight;
   public int maxHeight;
   public boolean geode = false;
   public IBlockState[] geodeblocks;
   public float geodechance = 0.0F;

   public OreGenChromium(
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

   public OreGenChromium(
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

   public OreGenChromium setGeode(float geodechance, IBlockState... geodeblocks) {
      this.geodeblocks = geodeblocks;
      this.geodechance = geodechance;
      this.geode = true;
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
            boolean generated = false;
            if (!world.isAirBlock(new BlockPos(x, y, z))) {
               GetMOP.BlockTraceResult res = GetMOP.blockTrace(world, new BlockPos(x, y, z), EnumFacing.UP, maxHeight - y, GetMOP.AIR_BLOCKS);
               if (res != null) {
                  generated = true;
                  generator.generate(world, rand, res.pos);
                  if (this.geode
                     && rand.nextFloat() < this.geodechance
                     && this.checkblock(world.getBlockState(res.pos.up()))
                     && this.checkblock(world.getBlockState(res.pos.down()))
                     && this.checkblock(world.getBlockState(res.pos.north()))
                     && this.checkblock(world.getBlockState(res.pos.south()))
                     && this.checkblock(world.getBlockState(res.pos.west()))
                     && this.checkblock(world.getBlockState(res.pos.east()))
                     && this.checkblock(world.getBlockState(res.pos))) {
                     world.setBlockState(res.pos.up(), blockToGen, 2);
                     world.setBlockState(res.pos.down(), blockToGen, 2);
                     world.setBlockState(res.pos.north(), blockToGen, 2);
                     world.setBlockState(res.pos.south(), blockToGen, 2);
                     world.setBlockState(res.pos.west(), blockToGen, 2);
                     world.setBlockState(res.pos.east(), blockToGen, 2);
                     world.setBlockState(res.pos, this.geodeblocks[rand.nextInt(this.geodeblocks.length)], 2);
                  }
               }
            }

            if (!generated) {
               y = minHeight + rand.nextInt(heightdiff);
               BlockPos fpos = new BlockPos(x, y, z);
               if (!world.isAirBlock(fpos)) {
                  generator.generate(world, rand, fpos);
                  if (this.geode
                     && rand.nextFloat() < this.geodechance
                     && this.checkblock(world.getBlockState(fpos.up()))
                     && this.checkblock(world.getBlockState(fpos.down()))
                     && this.checkblock(world.getBlockState(fpos.north()))
                     && this.checkblock(world.getBlockState(fpos.south()))
                     && this.checkblock(world.getBlockState(fpos.west()))
                     && this.checkblock(world.getBlockState(fpos.east()))
                     && this.checkblock(world.getBlockState(fpos))) {
                     world.setBlockState(fpos.up(), blockToGen, 2);
                     world.setBlockState(fpos.down(), blockToGen, 2);
                     world.setBlockState(fpos.north(), blockToGen, 2);
                     world.setBlockState(fpos.south(), blockToGen, 2);
                     world.setBlockState(fpos.west(), blockToGen, 2);
                     world.setBlockState(fpos.east(), blockToGen, 2);
                     world.setBlockState(fpos, this.geodeblocks[rand.nextInt(this.geodeblocks.length)], 2);
                  }
               }
            }
         }
      } else {
         throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
      }
   }

   public boolean checkblock(IBlockState block) {
      return this.blocksToReplace.apply(block) || block.getBlock() == this.oreState;
   }
}
