package com.Vivern.Arpg.elements.animation;

import net.minecraft.util.math.Vec3d;

public class KeyframeAnimations {
   public static Vec3d degreeVec(float x, float y, float z) {
      return new Vec3d(x * 0.017453292519943, y * 0.017453292519943, z * 0.017453292519943);
   }

   public static Vec3d posVec(float x, float y, float z) {
      return new Vec3d(x * 0.0625, y * 0.0625, z * 0.0625);
   }
}
