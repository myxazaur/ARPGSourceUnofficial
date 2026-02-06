package com.Vivern.Arpg.dimensions.generationutils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class BlockAtPos extends BlockPos {
   public IBlockState state;

   public BlockAtPos(BlockPos pos, IBlockState state) {
      super(pos.getX(), pos.getY(), pos.getZ());
      this.state = state;
   }

   public BlockAtPos(int x, int y, int z, IBlockState state) {
      super(x, y, z);
      this.state = state;
   }
}
