package com.Vivern.Arpg.dimensions.generationutils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ModuleIf extends Module {
   public Module module;
   public IModuleCondition condition;

   public ModuleIf(ModularStructureGenerator generator, Module module, IModuleCondition condition) {
      super(generator);
      this.module = module;
      this.condition = condition;
      this.canDebug = false;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (!this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
         if (this.condition.can(this.generator, sourcePos, facing, sourceModule, age)) {
            this.generator.addToGenerationQueue(this.module, sourcePos, facing, sourceModule, age);
         }

         this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);
      }
   }

   public interface IModuleCondition {
      boolean can(ModularStructureGenerator var1, BlockPos var2, EnumFacing var3, Module var4, int var5);
   }
}
