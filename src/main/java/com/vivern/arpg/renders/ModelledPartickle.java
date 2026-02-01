package com.vivern.arpg.renders;

import javax.annotation.Nullable;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ModelledPartickle extends GUNParticle {
   public ModelBase model;
   @Nullable
   public Entity modelEntityLink;
   public int frameDelay = 1;
   @Nullable
   public ResourceLocation[] texturesAnimation;
   public boolean noCullface = false;
   public boolean isEntityLiving = false;

   public ModelledPartickle(
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

   public ModelledPartickle(
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

   public ModelledPartickle(
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

   public void setModel(ModelBase model, @Nullable Entity modelEntityLink) {
      this.model = model;
      this.modelEntityLink = modelEntityLink;
      this.isEntityLiving = modelEntityLink instanceof EntityLiving;
   }

   public void setModel(ModelBase model, @Nullable EntityLiving modelEntityLink) {
      this.model = model;
      this.modelEntityLink = modelEntityLink;
      this.isEntityLiving = true;
   }

   public void setTextureAnimation(ResourceLocation[] textures, int frameDelay) {
      this.texturesAnimation = textures;
      this.frameDelay = frameDelay;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.texturesAnimation != null) {
         this.texture = this.texturesAnimation[this.ticksExisted / this.frameDelay % this.texturesAnimation.length];
      }
   }
}
