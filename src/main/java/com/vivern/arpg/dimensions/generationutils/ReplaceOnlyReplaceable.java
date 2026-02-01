package com.vivern.arpg.dimensions.generationutils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

public class ReplaceOnlyReplaceable implements ITemplateProcessor {
   public static ReplaceOnlyReplaceable instance = new ReplaceOnlyReplaceable();

   public BlockInfo processBlock(World worldIn, BlockPos pos, BlockInfo blockInfoIn) {
      return !worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) ? null : blockInfoIn;
   }
}
