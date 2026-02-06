package com.Vivern.Arpg.mobs;

import javax.annotation.concurrent.Immutable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

@Immutable
public class GraviballPathNode {
   private final BlockPos pos;
   private final EnumFacing facing;

   public GraviballPathNode(BlockPos pos, EnumFacing facing) {
      this.pos = pos;
      this.facing = facing;
   }

   public GraviballPathNode(int x, int y, int z, EnumFacing facing) {
      this.pos = new BlockPos(x, y, z);
      this.facing = facing;
   }

   public GraviballPathNode(double x, double y, double z, EnumFacing facing) {
      this.pos = new BlockPos(x, y, z);
      this.facing = facing;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public int getPosX() {
      return this.pos.getX();
   }

   public int getPosY() {
      return this.pos.getY();
   }

   public int getPosZ() {
      return this.pos.getZ();
   }

   public EnumFacing getFacing() {
      return this.facing;
   }

   public boolean isFloor() {
      return this.facing == EnumFacing.UP;
   }
}
