package com.Vivern.Arpg.dimensions.generationutils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class ModuleRectangularRoom extends Module {
   public int radiusX;
   public int radiusZ;
   public int height;
   public int wallThickness;
   public int floorThickness;
   public int roofThickness;
   public Module wallBlockPlacer = EMPTY;
   public Module floorBlockPlacer = EMPTY;
   public Module roofBlockPlacer = EMPTY;
   public Module cornerBlockPlacer = EMPTY;
   public Module air = EMPTY;
   public Module previousRoom = EMPTY;
   public Module nextRoom = EMPTY;
   public float nextRoomChance = 1.0F;
   public Module centerModule = EMPTY;
   public Module onfloorCenterModule = EMPTY;
   public Module onroofCenterModule = EMPTY;
   public Module underFloorModule = EMPTY;
   public int underfloorModuleRadiusAdd = 0;
   public boolean floorOverWall;
   public boolean roofOverWall;
   public boolean onfloorCenterModuleTurnsUP = true;
   public boolean onroofCenterModuleTurnsUP = true;
   public boolean enableOffsetFromSource = true;

   public ModuleRectangularRoom(
      ModularStructureGenerator generator,
      int radiusX,
      int radiusZ,
      int height,
      int wallThickness,
      int floorThickness,
      int roofThickness,
      boolean floorOverWall,
      boolean roofOverWall
   ) {
      super(generator);
      this.radiusX = radiusX;
      this.radiusZ = radiusZ;
      this.height = height;
      this.wallThickness = wallThickness;
      this.floorThickness = floorThickness;
      this.roofThickness = roofThickness;
      this.floorOverWall = floorOverWall;
      this.roofOverWall = roofOverWall;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (!this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
         BlockPos center = this.enableOffsetFromSource
            ? sourcePos.offset(
               facing, facing.getAxis() == Axis.X ? this.radiusX + 1 : (facing.getAxis() == Axis.Z ? this.radiusZ + 1 : this.height)
            )
            : sourcePos;

         for (int x = -this.radiusX; x <= this.radiusX; x++) {
            for (int z = -this.radiusZ; z <= this.radiusZ; z++) {
               for (int y = 0; y < this.height; y++) {
                  BlockPos pos = center.add(x, y, z);
                  if (this.floorOverWall && y < this.floorThickness) {
                     this.generator.addToGenerationQueue(this.floorBlockPlacer, pos, EnumFacing.DOWN, this, age + 1);
                  } else if (this.roofOverWall && y >= this.height - this.roofThickness) {
                     this.generator.addToGenerationQueue(this.roofBlockPlacer, pos, EnumFacing.UP, this, age + 1);
                  } else if (Math.abs(x) == this.radiusX && Math.abs(z) == this.radiusZ) {
                     this.generator.addToGenerationQueue(this.cornerBlockPlacer, pos, EnumFacing.UP, this, age + 1);
                  } else if (x < -this.radiusX + this.wallThickness) {
                     this.generator.addToGenerationQueue(this.wallBlockPlacer, pos, EnumFacing.WEST, this, age + 1);
                  } else if (z < -this.radiusZ + this.wallThickness) {
                     this.generator.addToGenerationQueue(this.wallBlockPlacer, pos, EnumFacing.NORTH, this, age + 1);
                  } else if (x > this.radiusX - this.wallThickness) {
                     this.generator.addToGenerationQueue(this.wallBlockPlacer, pos, EnumFacing.EAST, this, age + 1);
                  } else if (z > this.radiusZ - this.wallThickness) {
                     this.generator.addToGenerationQueue(this.wallBlockPlacer, pos, EnumFacing.SOUTH, this, age + 1);
                  } else if (!this.floorOverWall && y < this.floorThickness) {
                     this.generator.addToGenerationQueue(this.floorBlockPlacer, pos, EnumFacing.DOWN, this, age + 1);
                  } else if (!this.roofOverWall && y >= this.height - this.roofThickness) {
                     this.generator.addToGenerationQueue(this.roofBlockPlacer, pos, EnumFacing.UP, this, age + 1);
                  } else {
                     this.generator.addToGenerationQueue(this.air, pos, EnumFacing.UP, this, age + 1);
                  }
               }
            }
         }

         this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);

         for (EnumFacing direction : EnumFacing.HORIZONTALS) {
            if (direction != facing.getOpposite()) {
               if (this.generator.rand.nextFloat() < this.nextRoomChance) {
                  BlockPos offPos = center.offset(direction, direction.getAxis() == Axis.X ? this.radiusX : this.radiusZ);
                  this.generator.addToGenerationQueue(this.nextRoom, offPos, direction, this, age + 1);
               }
            } else {
               BlockPos offPos = center.offset(direction, direction.getAxis() == Axis.X ? this.radiusX : this.radiusZ);
               this.generator.addToGenerationQueue(this.previousRoom, offPos, direction, this, age + 1);
            }
         }

         this.generator.addToGenerationQueue(this.centerModule, center, EnumFacing.UP, this, age + 1);
         this.generator
            .addToGenerationQueue(
               this.onfloorCenterModule, center.up(this.floorThickness), this.onfloorCenterModuleTurnsUP ? EnumFacing.UP : facing, this, age + 1
            );
         this.generator
            .addToGenerationQueue(
               this.onroofCenterModule, center.up(this.height), this.onroofCenterModuleTurnsUP ? EnumFacing.UP : facing, this, age + 1
            );
         if (this.underFloorModule != EMPTY) {
            for (int x = -this.radiusX - this.underfloorModuleRadiusAdd; x <= this.radiusX + this.underfloorModuleRadiusAdd; x++) {
               for (int z = -this.radiusZ - this.underfloorModuleRadiusAdd; z <= this.radiusZ + this.underfloorModuleRadiusAdd; z++) {
                  BlockPos pos = center.add(x, 0, z);
                  this.generator.addToGenerationQueue(this.underFloorModule, pos, EnumFacing.DOWN, this, age + 1);
               }
            }
         }
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
