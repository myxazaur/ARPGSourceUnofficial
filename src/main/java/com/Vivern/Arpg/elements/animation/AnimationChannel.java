//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.animation;

import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;

public class AnimationChannel {
   public Targets target;
   public Keyframe[] keyframes;
   public ModelRenderer modelRenderer;
   public Vec3d defaultRotation;

   public AnimationChannel(Targets target, Keyframe... keyframes) {
      this.target = target;
      this.keyframes = keyframes;
   }

   public void animate(float time) {
      Keyframe key1 = null;
      Keyframe key2 = null;

      for (int i = 0; i < this.keyframes.length; i++) {
         if (i + 1 >= this.keyframes.length) {
            key1 = this.keyframes[this.keyframes.length - 1];
            key2 = key1;
            break;
         }

         Keyframe k1 = this.keyframes[i];
         if (i == 0 && time < k1.keyTime) {
            key1 = k1;
            key2 = k1;
            break;
         }

         Keyframe k2 = this.keyframes[i + 1];
         if (time >= k1.keyTime && time < k2.keyTime) {
            key1 = k1;
            key2 = k2;
            break;
         }
      }

      if (key1 != null && key2 != null) {
         float ftKey2 = key1.interpolation != Interpolations.CATMULLROM && key2.interpolation != Interpolations.CATMULLROM
            ? GetMOP.getfromto(time, key1.keyTime, key2.keyTime)
            : GetMOP.softfromto(time, key1.keyTime, key2.keyTime);
         float ftKey1 = 1.0F - ftKey2;
         if (this.target == Targets.ROTATION) {
            this.modelRenderer.rotateAngleX = (float)(this.defaultRotation.x + key1.vec.x * ftKey1 + key2.vec.x * ftKey2);
            this.modelRenderer.rotateAngleY = (float)(this.defaultRotation.y + key1.vec.y * ftKey1 + key2.vec.y * ftKey2);
            this.modelRenderer.rotateAngleZ = (float)(this.defaultRotation.z + key1.vec.z * ftKey1 + key2.vec.z * ftKey2);
         }

         if (this.target == Targets.POSITION) {
            this.modelRenderer.offsetX = (float)(key1.vec.x * ftKey1 + key2.vec.x * ftKey2);
            this.modelRenderer.offsetY = (float)(key1.vec.y * ftKey1 + key2.vec.y * ftKey2);
            this.modelRenderer.offsetZ = (float)(key1.vec.z * ftKey1 + key2.vec.z * ftKey2);
         }
      }
   }

   public void returnDefaults() {
      if (this.target == Targets.ROTATION) {
         this.modelRenderer.rotateAngleX = (float)this.defaultRotation.x;
         this.modelRenderer.rotateAngleY = (float)this.defaultRotation.y;
         this.modelRenderer.rotateAngleZ = (float)this.defaultRotation.z;
      }

      if (this.target == Targets.POSITION) {
         this.modelRenderer.offsetX = 0.0F;
         this.modelRenderer.offsetY = 0.0F;
         this.modelRenderer.offsetZ = 0.0F;
      }
   }

   public static enum Interpolations {
      LINEAR,
      CATMULLROM;
   }

   public static enum Targets {
      ROTATION,
      POSITION;
   }
}
