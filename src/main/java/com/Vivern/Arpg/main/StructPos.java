//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import net.minecraft.util.math.BlockPos;

public class StructPos {
   public BlockPos blockpos;
   public int rotation;
   public int size;

   public StructPos(BlockPos blockpos, int rotation) {
      this.blockpos = blockpos;
      this.rotation = rotation;
   }

   public StructPos(double x, double y, double z, int rotation) {
      this.blockpos = new BlockPos(x, y, z);
      this.rotation = rotation;
   }

   public StructPos(BlockPos blockpos, int rotation, int size) {
      this.blockpos = blockpos;
      this.rotation = rotation;
      this.size = size;
   }

   public int getX() {
      return this.blockpos.getX();
   }

   public int getY() {
      return this.blockpos.getY();
   }

   public int getZ() {
      return this.blockpos.getZ();
   }
}
