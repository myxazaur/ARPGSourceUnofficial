package com.Vivern.Arpg.dimensions.generationutils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ModuleSetEdges extends Module {
   public Module moduleBlock;
   public int thickness;
   public boolean fillCorners;

   public ModuleSetEdges(ModularStructureGenerator generator, Module moduleBlock) {
      super(generator);
      this.moduleBlock = moduleBlock;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (!this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
         this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);
      }
   }
}
