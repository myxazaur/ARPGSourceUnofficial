package com.Vivern.Arpg.renders;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AnimatedGParticle extends GUNParticle {
   public int framecount = 1;
   public int animDelay = 1;
   public boolean disableOnEnd = true;
   public int animCounter = 0;
   public boolean useNormalTime = false;
   public int stopOnFrame = Integer.MAX_VALUE;

   public AnimatedGParticle(
      ResourceLocation texture,
      float scale,
      float gravity,
      int livetime,
      int light,
      World world,
      double x,
      double y,
      double z,
      float speedx,
      float speedy,
      float speedz,
      float Red,
      float Green,
      float Blue,
      boolean opa,
      int rotation
   ) {
      super(texture, scale, gravity, livetime, light, world, x, y, z, speedx, speedy, speedz, Red, Green, Blue, opa, rotation);
   }

   public AnimatedGParticle(
      ResourceLocation texture,
      float scale,
      float gravity,
      int livetime,
      int light,
      World world,
      double x,
      double y,
      double z,
      float speedx,
      float speedy,
      float speedz,
      float Red,
      float Green,
      float Blue,
      boolean opa,
      int rotation,
      boolean collide,
      float frictionMultipl
   ) {
      super(texture, scale, gravity, livetime, light, world, x, y, z, speedx, speedy, speedz, Red, Green, Blue, opa, rotation, collide, frictionMultipl);
   }

   public AnimatedGParticle(
      ResourceLocation texture,
      float scale,
      float gravity,
      int livetime,
      int light,
      World world,
      double x,
      double y,
      double z,
      float speedx,
      float speedy,
      float speedz,
      float Red,
      float Green,
      float Blue,
      boolean opa,
      int rotation,
      boolean collide,
      float frictionMultipl,
      float scaleTickAdding,
      float scaleMax,
      float scaleTickDropAdding
   ) {
      super(
         texture,
         scale,
         gravity,
         livetime,
         light,
         world,
         x,
         y,
         z,
         speedx,
         speedy,
         speedz,
         Red,
         Green,
         Blue,
         opa,
         rotation,
         collide,
         frictionMultipl,
         scaleTickAdding,
         scaleMax,
         scaleTickDropAdding
      );
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.useNormalTime) {
         float time = this.animCounter / this.animDelay;
         if (time < this.stopOnFrame) {
            this.animCounter++;
         }
      }
   }
}
