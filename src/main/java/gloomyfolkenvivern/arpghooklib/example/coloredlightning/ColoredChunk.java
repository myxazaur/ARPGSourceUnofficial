package gloomyfolkenvivern.arpghooklib.example.coloredlightning;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo.Color;

public class ColoredChunk {
   public int chunkPosX;
   public int chunkPosZ;
   public byte[] RED = new byte[65536];
   public byte[] GREEN = new byte[65536];
   public byte[] BLUE = new byte[65536];

   public ColoredChunk(int chunkPosX, int chunkPosZ) {
      this.chunkPosX = chunkPosX;
      this.chunkPosZ = chunkPosZ;
   }

   public byte getColorAt(BlockPos pos, Color color) {
      int inChunkX = ColoredLightning.blockToInchunkCoords(pos.getX());
      int inChunkZ = ColoredLightning.blockToInchunkCoords(pos.getZ());
      return this.getColorAt(inChunkX, pos.getY(), inChunkZ, color);
   }

   public byte getColorAt(int inChunkX, int inChunkY, int inChunkZ, Color color) {
      int bakedCoord = this.bakeCoord(inChunkX, inChunkY, inChunkZ);
      if (color == Color.RED) {
         return this.RED[bakedCoord];
      } else if (color == Color.GREEN) {
         return this.GREEN[bakedCoord];
      } else {
         return color == Color.BLUE ? this.BLUE[bakedCoord] : 0;
      }
   }

   public void setColorAt(int inChunkX, int inChunkY, int inChunkZ, Color color, byte colorValue) {
      int bakedCoord = this.bakeCoord(inChunkX, inChunkY, inChunkZ);
      if (color == Color.RED) {
         this.RED[bakedCoord] = colorValue;
      } else if (color == Color.GREEN) {
         this.GREEN[bakedCoord] = colorValue;
      } else if (color == Color.BLUE) {
         this.BLUE[bakedCoord] = colorValue;
      }
   }

   public boolean compareColorAt(int inChunkX, int inChunkY, int inChunkZ, byte lastRedValue, byte lastGreenValue, byte lastBlueValue) {
      int bakedCoord = this.bakeCoord(inChunkX, inChunkY, inChunkZ);
      byte red = this.RED[bakedCoord];
      byte green = this.GREEN[bakedCoord];
      byte blue = this.BLUE[bakedCoord];
      if (ColoredLightning.getPower(lastRedValue) - 1 > ColoredLightning.getPower(red)) {
         this.RED[bakedCoord] = ColoredLightning.bakeColor(ColoredLightning.getSaturation(lastRedValue), ColoredLightning.getPower(lastRedValue) - 1);
      }

      if (ColoredLightning.getPower(lastGreenValue) - 1 > ColoredLightning.getPower(green)) {
         this.GREEN[bakedCoord] = ColoredLightning.bakeColor(ColoredLightning.getSaturation(lastGreenValue), ColoredLightning.getPower(lastGreenValue) - 1);
      }

      if (ColoredLightning.getPower(lastBlueValue) - 1 > ColoredLightning.getPower(blue)) {
         this.BLUE[bakedCoord] = ColoredLightning.bakeColor(ColoredLightning.getSaturation(lastBlueValue), ColoredLightning.getPower(lastBlueValue) - 1);
      }

      return true;
   }

   public void setColorsAt(int inChunkX, int inChunkY, int inChunkZ, byte redValue, byte greenValue, byte blueValue) {
      int bakedCoord = this.bakeCoord(inChunkX, inChunkY, inChunkZ);
      this.RED[bakedCoord] = redValue;
      this.GREEN[bakedCoord] = greenValue;
      this.BLUE[bakedCoord] = blueValue;
   }

   public Vec3d getVec3dColorAt(int inChunkX, int inChunkY, int inChunkZ) {
      int bakedCoord = this.bakeCoord(inChunkX, inChunkY, inChunkZ);
      byte red = this.RED[bakedCoord];
      byte green = this.GREEN[bakedCoord];
      byte blue = this.BLUE[bakedCoord];
      return new Vec3d(
         ColoredLightning.getSaturation(red) / 15.0F * ColoredLightning.getPower(red) / 15.0F,
         ColoredLightning.getSaturation(green) / 15.0F * ColoredLightning.getPower(green) / 15.0F,
         ColoredLightning.getSaturation(blue) / 15.0F * ColoredLightning.getPower(blue) / 15.0F
      );
   }

   public int bakeCoord(int inChunkX, int inChunkY, int inChunkZ) {
      return inChunkX << 12 | MathHelper.clamp(inChunkY, 0, 255) << 4 | inChunkZ;
   }
}
