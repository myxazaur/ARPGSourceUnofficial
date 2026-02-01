package com.vivern.arpg.dimensions.generationutils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

public class ReplaceOnlySofter implements ITemplateProcessor {
   public static ReplaceOnlySofter instance = new ReplaceOnlySofter();

   public BlockInfo processBlock(World worldIn, BlockPos pos, BlockInfo blockInfoIn) {
      return worldIn.getBlockState(pos).getBlockHardness(worldIn, pos) > blockInfoIn.blockState.getBlockHardness(worldIn, pos) ? null : blockInfoIn;
   }
}
