package com.Vivern.Arpg.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface ISeed {
   IBlockState getPlant(IBlockAccess var1, BlockPos var2);

   boolean canGrowAt(IBlockAccess var1, BlockPos var2);
}
