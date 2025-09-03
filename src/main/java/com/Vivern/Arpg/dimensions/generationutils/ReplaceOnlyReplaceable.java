//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.generationutils;

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
