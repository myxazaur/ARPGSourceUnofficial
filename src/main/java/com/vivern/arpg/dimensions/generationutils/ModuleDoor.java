package com.vivern.arpg.dimensions.generationutils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ModuleDoor extends Module {
   public static int[][] example = new int[][]{{1, 1, 3, 3}, {0, 0, 1, 3}, {0, 0, 0, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}};
   public Module insideWall = EMPTY;
   public Module doorFraming = EMPTY;
   public Module doorFraming4 = EMPTY;
   public Module air = EMPTY;
   public Module module2 = EMPTY;
   public int wallThickness;
   public int yOffset;
   public int[][] doorProfile;

   public ModuleDoor(ModularStructureGenerator generator, int wallThickness, int yOffset, int[][] doorProfile) {
      super(generator);
      this.doorProfile = doorProfile;
      this.wallThickness = wallThickness;
      this.yOffset = yOffset;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (!this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
         int h = 0;

         for (int[] horiz : this.doorProfile) {
            h++;
         }

         EnumFacing right = facing.rotateY();
         EnumFacing left = facing.rotateYCCW();
         BlockPos start = sourcePos.offset(facing.getOpposite(), this.wallThickness - 1);

         for (int i = 0; i < h; i++) {
            int[] horiz = this.doorProfile[h - i - 1];
            BlockPos posH = start.up(i + this.yOffset);

            for (int j = 0; j < horiz.length; j++) {
               int value = horiz[j];
               if (j == 0) {
                  this.genDoorBlocks(posH, facing, value, age + 1);
               }

               BlockPos posR = posH.offset(right, j);
               BlockPos posL = posH.offset(left, j);
               this.genDoorBlocks(posR, facing, value, age + 1);
               this.genDoorBlocks(posL, facing, value, age + 1);
            }
         }

         this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);
      }
   }

   public void genDoorBlocks(BlockPos pos, EnumFacing facing, int value, int age) {
      int wallThickness2 = this.wallThickness * 2;
      if (value != 3) {
         if (value == 0) {
            for (int w = 0; w < wallThickness2; w++) {
               this.generator.addToGenerationQueue(this.air, pos.offset(facing, w), facing, this, age);
            }
         } else if (value == 2) {
            for (int w = 0; w < wallThickness2; w++) {
               this.generator.addToGenerationQueue(this.module2, pos.offset(facing, w), facing, this, age);
            }
         } else {
            for (int w = -1; w <= wallThickness2; w++) {
               if (w == -1) {
                  this.generator
                     .addToGenerationQueue(value == 4 ? this.doorFraming4 : this.doorFraming, pos.offset(facing, w), facing.getOpposite(), this, age);
               } else if (w == wallThickness2) {
                  this.generator.addToGenerationQueue(value == 4 ? this.doorFraming4 : this.doorFraming, pos.offset(facing, w), facing, this, age);
               } else {
                  this.generator.addToGenerationQueue(this.insideWall, pos.offset(facing, w), facing, this, age);
               }
            }
         }
      }
   }
}
