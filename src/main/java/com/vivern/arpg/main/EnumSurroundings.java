package com.vivern.arpg.main;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public enum EnumSurroundings {
   DOWN(0, "down", new Vec3i(0, -1, 0)),
   UP(1, "up", new Vec3i(0, 1, 0)),
   NORTH(2, "north", new Vec3i(0, 0, -1)),
   SOUTH(3, "south", new Vec3i(0, 0, 1)),
   WEST(4, "west", new Vec3i(-1, 0, 0)),
   EAST(5, "east", new Vec3i(1, 0, 0)),
   NORTH_DOWN(6, "north_down", new Vec3i(0, -1, -1)),
   SOUTH_DOWN(7, "south_down", new Vec3i(0, -1, 1)),
   WEST_DOWN(8, "west_down", new Vec3i(-1, -1, 0)),
   EAST_DOWN(9, "east_down", new Vec3i(1, -1, 0)),
   NORTH_UP(10, "north_up", new Vec3i(0, 1, -1)),
   SOUTH_UP(11, "south_up", new Vec3i(0, 1, 1)),
   WEST_UP(12, "west_up", new Vec3i(-1, 1, 0)),
   EAST_UP(13, "east_up", new Vec3i(1, 1, 0)),
   NORTH_EAST(14, "north_east", new Vec3i(1, 0, -1)),
   SOUTH_EAST(15, "south_east", new Vec3i(1, 0, 1)),
   NORTH_WEST(16, "north_west", new Vec3i(-1, 0, -1)),
   SOUTH_WEST(17, "south_west", new Vec3i(-1, 0, 1)),
   NORTH_EAST_UP(18, "north_east_up", new Vec3i(1, 1, -1)),
   SOUTH_EAST_UP(19, "south_east_up", new Vec3i(1, 1, 1)),
   NORTH_WEST_UP(20, "north_west_up", new Vec3i(-1, 1, -1)),
   SOUTH_WEST_UP(21, "south_west_up", new Vec3i(-1, 1, 1)),
   NORTH_EAST_DOWN(22, "north_east_down", new Vec3i(1, -1, -1)),
   SOUTH_EAST_DOWN(23, "south_east_down", new Vec3i(1, -1, 1)),
   NORTH_WEST_DOWN(24, "north_west_down", new Vec3i(-1, -1, -1)),
   SOUTH_WEST_DOWN(25, "south_west_down", new Vec3i(-1, -1, 1));

   private final int index;
   private final String name;
   private final Vec3i directionVec;
   public static final EnumSurroundings[] VALUES = new EnumSurroundings[26];

   private EnumSurroundings(int indexIn, String nameIn, Vec3i directionVecIn) {
      this.index = indexIn;
      this.name = nameIn;
      this.directionVec = directionVecIn;
   }

   public BlockPos offset(BlockPos pos) {
      return pos.add(this.directionVec);
   }

   static {
      for (EnumSurroundings enumfacing : values()) {
         VALUES[enumfacing.index] = enumfacing;
      }
   }
}
