package com.vivern.arpg.dimensions.generationutils;

import javax.annotation.Nullable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public abstract class Module {
   public static final Module EMPTY = new Module(null) {
      @Override
      public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      }
   };
   public ModularStructureGenerator generator;
   public int limitValue = 0;
   public boolean checkLimit = false;
   public int queue = 0;
   public boolean canBeQueued = true;
   public boolean addHitboxToCollisionList = false;
   public boolean collisionCheck = false;
   public int boundingBoxId = 0;
   public boolean canDebug = true;
   public float chance = 1.0F;

   public Module(ModularStructureGenerator generator) {
      this.generator = generator;
   }

   public abstract void generate(BlockPos var1, EnumFacing var2, Module var3, int var4);

   @Nullable
   public AxisAlignedBB getBoundingBox(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      return null;
   }
}
