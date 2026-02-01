package com.vivern.arpg.dimensions.generationutils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class ModuleScatter extends Module {
   public int radiusX;
   public int radiusZ;
   public int height;
   public int formType;
   public Module onScatter = EMPTY;
   public int amountMin;
   public int amountMax;
   public boolean offsetFromSource;

   public ModuleScatter(
      ModularStructureGenerator generator, int radiusX, int radiusZ, int height, int formType, int amountMin, int amountMax, boolean offsetFromSource
   ) {
      super(generator);
      this.radiusX = radiusX;
      this.radiusZ = radiusZ;
      this.height = height;
      this.formType = formType;
      this.amountMin = amountMin;
      this.amountMax = amountMax;
      this.offsetFromSource = offsetFromSource;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (!this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
         int amount = this.amountMin + this.generator.rand.nextInt(this.amountMax - this.amountMin + 1);
         if (this.formType == 0) {
            for (int i = 0; i < amount; i++) {
               if (this.offsetFromSource) {
                  int height2 = MathHelper.floor(this.height / 2.0F);
                  int x = sourcePos.getX() - this.radiusX + this.generator.rand.nextInt(this.radiusX * 2 + 1) + facing.getXOffset() * this.radiusX;
                  int y = sourcePos.getY() - height2 + this.generator.rand.nextInt(this.height) + facing.getYOffset() * (this.height - height2);
                  int z = sourcePos.getZ() - this.radiusZ + this.generator.rand.nextInt(this.radiusZ * 2 + 1) + facing.getZOffset() * this.radiusZ;
                  this.generator.addToGenerationQueue(this.onScatter, new BlockPos(x, y, z), facing, this, age + 1);
               } else {
                  int height2 = MathHelper.floor(this.height / 2.0F);
                  int x = sourcePos.getX() - this.radiusX + this.generator.rand.nextInt(this.radiusX * 2 + 1);
                  int y = sourcePos.getY() - height2 + this.generator.rand.nextInt(this.height - height2);
                  int z = sourcePos.getZ() - this.radiusZ + this.generator.rand.nextInt(this.radiusZ * 2 + 1);
                  this.generator.addToGenerationQueue(this.onScatter, new BlockPos(x, y, z), facing, this, age + 1);
               }
            }
         }

         this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);
      }
   }

   @Override
   public AxisAlignedBB getBoundingBox(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      BlockPos center = sourcePos.offset(
         facing, facing.getAxis() == Axis.X ? this.radiusX + 1 : (facing.getAxis() == Axis.Z ? this.radiusZ + 1 : this.height)
      );
      return new AxisAlignedBB(
         (double)center.getX() - this.radiusX,
         center.getY(),
         (double)center.getZ() - this.radiusZ,
         center.getX() + 1 + this.radiusX,
         center.getY() + this.height,
         center.getZ() + 1 + this.radiusZ
      );
   }
}
