package com.Vivern.Arpg.dimensions.generationutils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ModuleArray extends Module {
   public Module[] modules;

   public ModuleArray(ModularStructureGenerator generator, Module... modules) {
      super(generator);
      this.modules = modules;
      this.canBeQueued = false;
      this.canDebug = false;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (!this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
         for (int i = 0; i < this.modules.length; i++) {
            Module m = this.modules[i];
            this.generator.addToGenerationQueue(m, sourcePos, facing, sourceModule, age);
         }

         this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);
      }
   }
}
