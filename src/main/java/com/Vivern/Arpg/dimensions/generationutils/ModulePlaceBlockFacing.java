//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.generationutils;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ModulePlaceBlockFacing extends Module {
   public Block block;
   public boolean opposite;

   public ModulePlaceBlockFacing(ModularStructureGenerator generator, Block block, boolean opposite) {
      super(generator);
      this.block = block;
      this.opposite = opposite;
      this.canBeQueued = false;
      this.canDebug = false;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (!this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
         this.generator
            .setBlockState(sourcePos, this.block.getStateForPlacement(null, sourcePos, this.opposite ? facing.getOpposite() : facing, 0.0F, 0.0F, 0.0F, 0, null), 2);
         this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);
      }
   }
}
