package com.Vivern.Arpg.weather;

import net.minecraft.util.EnumFacing;

public interface IVoxelCloudInfo {
   boolean hasVoxelAt(VoxelCloudField var1, double var2, double var4, double var6, int var8, int var9, int var10);

   void writeColorInPoint(
      VoxelCloudField var1,
      VoxelCloud var2,
      float[] var3,
      double var4,
      double var6,
      double var8,
      int var10,
      int var11,
      int var12,
      VoxelCloud.EnumCubeVertex var13
   );

   float getLightInFace(VoxelCloudField var1, VoxelCloud var2, int var3, EnumFacing var4);

   default void onPreRender() {
   }
}
