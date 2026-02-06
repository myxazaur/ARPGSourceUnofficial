package com.Vivern.Arpg.renders;

import net.minecraft.util.math.MathHelper;

public class LoadedRGBChunk {
   public long[] arrXYZ = new long[196608];
   public int chunkPosX;
   public int chunkPosZ;
   public int updated = 0;

   public LoadedRGBChunk(int chunkPosX, int chunkPosZ) {
      this.chunkPosX = chunkPosX;
      this.chunkPosZ = chunkPosZ;
   }

   public void clear() {
      for (int i = 0; i < 196608; i++) {
         this.arrXYZ[i] = 0L;
      }
   }

   public final long getBakedLight(int bakedCoord) {
      return this.arrXYZ[bakedCoord];
   }

   public static final int getBakedCoordRed(int xx, int yy, int zz) {
      return blockToInchunkCoords(xx) | blockToInchunkCoords(zz) << 4 | MathHelper.clamp(yy, 0, 255) << 8;
   }

   public static final int getBakedCoordGreen(int xx, int yy, int zz) {
      return blockToInchunkCoords(xx) | blockToInchunkCoords(zz) << 4 | MathHelper.clamp(yy, 0, 255) << 8 | 65536;
   }

   public static final int getBakedCoordBlue(int xx, int yy, int zz) {
      return blockToInchunkCoords(xx) | blockToInchunkCoords(zz) << 4 | MathHelper.clamp(yy, 0, 255) << 8 | 131072;
   }

   public final void setLight(int bakedCoord, byte channel, byte value) {
      value = clamp(value, (byte)0, (byte)15);
      long addition = ~(15L << (channel << 2));
      this.arrXYZ[bakedCoord] = this.arrXYZ[bakedCoord] & addition | (long)value << (channel << 2);
   }

   public final void setLight(int bakedCoord, long channel, long value) {
      long addition = ~(15L << (int)(channel << 2));
      this.arrXYZ[bakedCoord] = this.arrXYZ[bakedCoord] & addition | value << (int)(channel << 2);
   }

   public static final byte getLightForChannel(long lightChannels, byte channel) {
      long target = 15L << (channel << 2);
      long result = (lightChannels & target) >>> (channel << 2);
      return (byte)result;
   }

   public static final float finalColorAdditive(long lightChannels) {
      float MAXcolor = 0.0F;

      for (byte channel = 0; channel < 16; channel++) {
         long target = 15L << channel * 4;
         long result = (lightChannels & target) >>> channel * 4;
         float color = (float)result / 15.0F * ((channel + 1) / 16.0F);
         if (color > MAXcolor) {
            MAXcolor = color;
         }
      }

      return MathHelper.clamp(MAXcolor, 0.0F, 1.0F);
   }

   public final void clearOnPos(int bakedCoord) {
      this.arrXYZ[bakedCoord] = 0L;
   }

   public final void clearOnPosAllChannels(int xx, int yy, int zz) {
      int c = blockToInchunkCoords(xx) | blockToInchunkCoords(zz) << 4 | MathHelper.clamp(yy, 0, 255) << 8;
      this.arrXYZ[c] = 0L;
      this.arrXYZ[c | 65536] = 0L;
      this.arrXYZ[c | 131072] = 0L;
   }

   public static final int blockTochunkCoord(int xz) {
      if (xz > 0) {
         return xz / 16;
      } else {
         return xz == 0 ? 0 : xz / 16 - 1;
      }
   }

   public static final int blockToInchunkCoords(int xz) {
      return xz & 15;
   }

   public static final byte clamp(byte num, byte min, byte max) {
      if (num < min) {
         return min;
      } else {
         return num > max ? max : num;
      }
   }
}
