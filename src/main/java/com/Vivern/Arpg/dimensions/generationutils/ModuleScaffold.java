package com.Vivern.Arpg.dimensions.generationutils;

import javax.annotation.Nullable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class ModuleScaffold extends Module {
   public int maxLength;
   public Module scaffoldBlockPlacer = EMPTY;
   public Module baseBlockPlacer = EMPTY;
   @Nullable
   public EnumFacing directionReplacement;

   public ModuleScaffold(ModularStructureGenerator generator, int maxLength, @Nullable EnumFacing directionReplacement) {
      super(generator);
      this.maxLength = maxLength;
      this.directionReplacement = directionReplacement;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (!this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
         EnumFacing facingTo = this.directionReplacement != null ? this.directionReplacement : facing;
         BlockPos prevpos = null;

         for (int i = 1; i <= this.maxLength; i++) {
            BlockPos pos = sourcePos.offset(facingTo, i);
            if (!this.generator.isReplaceable(pos)) {
               if (prevpos != null) {
                  this.generator.addToGenerationQueue(this.baseBlockPlacer, prevpos, facingTo, this, age + 1);
               }
               break;
            }

            if (prevpos != null) {
               this.generator.addToGenerationQueue(this.scaffoldBlockPlacer, prevpos, facingTo, this, age + 1);
            }

            prevpos = pos;
         }

         this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);
      }
   }

   @Override
   public AxisAlignedBB getBoundingBox(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      EnumFacing facingTo = this.directionReplacement != null ? this.directionReplacement : facing;
      BlockPos source = sourcePos.offset(facingTo, 1);

      for (int i = 2; i <= this.maxLength; i++) {
         BlockPos pos = sourcePos.offset(facingTo, i);
         if (!this.generator.isReplaceable(pos) || i == this.maxLength) {
            AxisAlignedBB aabb1 = new AxisAlignedBB(source);
            AxisAlignedBB aabb2 = new AxisAlignedBB(sourcePos.offset(facingTo, i - 1));
            return aabb1.union(aabb2);
         }
      }

      return new AxisAlignedBB(source);
   }
}
