package com.Vivern.Arpg.dimensions.generationutils;

import java.util.ArrayList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ModuleColumn extends Module {
   public ArrayList<IntAndModule> columnBase = new ArrayList<>();
   public ArrayList<IntAndModule> columnTop = new ArrayList<>();
   public Module column;
   public Module center;
   public int seed;
   public int height;
   public int centerOffset;

   public ModuleColumn(ModularStructureGenerator generator, int height, int thickness, Module column) {
      super(generator);
      this.column = column;
      this.height = height;
   }

   public ModuleColumn addBaseLayer(Module module, boolean fillCorners) {
      this.columnBase.add(new IntAndModule(module, fillCorners ? 1 : 0));
      return this;
   }

   public ModuleColumn addTopLayer(Module module, boolean fillCorners) {
      this.columnTop.add(new IntAndModule(module, fillCorners ? 1 : 0));
      return this;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (!this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
         int baseHeight = Math.min(this.columnBase.size(), this.height);

         for (int y = 0; y < baseHeight; y++) {
            BlockPos pos = sourcePos.offset(facing, y);
            this.generator.addToGenerationQueue(this.column, pos, facing, this, age + 1);
            if (y < this.columnBase.size()) {
               this.placeSymmetric(pos, facing, 1, this.columnBase.get(y).module, this.columnBase.get(y).value > 0, age + 1);
            }
         }

         for (int yx = baseHeight; yx < this.height; yx++) {
            BlockPos pos = sourcePos.offset(facing, yx);
            this.generator.addToGenerationQueue(this.column, pos, facing, this, age + 1);
         }

         int indx = this.columnTop.size() - 1;

         for (int yx = this.height - 1; yx >= 0; yx--) {
            BlockPos pos = sourcePos.offset(facing, yx);
            if (indx < 0) {
               break;
            }

            this.placeSymmetric(pos, facing, 1, this.columnTop.get(indx).module, this.columnTop.get(indx).value > 0, age + 1);
            indx--;
         }

         BlockPos posc = sourcePos.offset(facing, this.centerOffset);
         this.generator.addToGenerationQueue(this.center, posc, facing, this, age + 1);
         this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);
      }
   }

   public void placeSymmetric(BlockPos sourcePos, EnumFacing axis, int offset, Module module, boolean fillCorners, int age) {
      for (EnumFacing face : EnumFacing.VALUES) {
         if (face.getAxis() != axis.getAxis()) {
            this.generator.addToGenerationQueue(module, sourcePos.offset(face, offset), face, this, age);
         }
      }
   }
}
