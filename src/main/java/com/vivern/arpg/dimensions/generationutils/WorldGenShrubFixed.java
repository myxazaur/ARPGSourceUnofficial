package com.vivern.arpg.dimensions.generationutils;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenShrubFixed extends WorldGenerator {
   public IBlockState leavesMetadata;
   public IBlockState woodMetadata;

   public WorldGenShrubFixed(IBlockState wood, IBlockState leaves) {
      this.woodMetadata = wood;
      this.leavesMetadata = leaves;
   }

   public boolean generate(World worldIn, Random rand, BlockPos position) {
      IBlockState state = worldIn.getBlockState(position.down());
      this.setBlockAndNotifyAdequately(worldIn, position, this.woodMetadata);

      for (int i = position.getY(); i <= position.getY() + 2; i++) {
         int j = i - position.getY();
         int k = 2 - j;

         for (int l = position.getX() - k; l <= position.getX() + k; l++) {
            int i1 = l - position.getX();

            for (int j1 = position.getZ() - k; j1 <= position.getZ() + k; j1++) {
               int k1 = j1 - position.getZ();
               if (Math.abs(i1) != k || Math.abs(k1) != k || rand.nextInt(2) != 0) {
                  BlockPos blockpos = new BlockPos(l, i, j1);
                  state = worldIn.getBlockState(blockpos);
                  if (state.getBlock().canBeReplacedByLeaves(state, worldIn, blockpos)) {
                     this.setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesMetadata);
                  }
               }
            }
         }
      }

      return true;
   }
}
