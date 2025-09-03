package com.Vivern.Arpg.elements.animation;

import net.minecraft.util.math.Vec3d;

public class Keyframe {
   public float keyTime;
   public Vec3d vec;
   public AnimationChannel.Interpolations interpolation;

   public Keyframe(float keyTime, Vec3d vec, AnimationChannel.Interpolations interpolation) {
      this.keyTime = keyTime;
      this.vec = vec;
      this.interpolation = interpolation;
   }
}
