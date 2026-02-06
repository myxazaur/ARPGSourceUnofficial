package com.Vivern.Arpg.dimensions.generationutils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ModuleStructure extends Module {
   public String structureName;
   public int displace;
   public int Yoffset;

   public ModuleStructure(ModularStructureGenerator generator, String structureName, int size, int Yoffset) {
      super(generator);
      this.structureName = structureName;
      this.displace = size;
      this.Yoffset = Yoffset;
      this.canBeQueued = false;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (!this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
         if (this.generator.worldserver != null) {
            GenerationHelper.placeStruct(
               this.generator.worldserver,
               this.generator,
               sourcePos,
               this.generator.rand,
               this.structureName,
               this.displace,
               this.Yoffset,
               facing.getHorizontalIndex()
            );
            this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);
         } else {
            throw new NullPointerException("this.generator.worldserver must be not null, if you use ModuleStructure!!!");
         }
      }
   }
}
