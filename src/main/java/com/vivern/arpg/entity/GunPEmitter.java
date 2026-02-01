package com.vivern.arpg.entity;

import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GunPEmitter extends GUNParticle {
   protected float EEscale;
   protected float EEgravity;
   protected int EElivetime;
   protected int EElight;
   protected ResourceLocation EEtexture;
   protected float EERed;
   protected float EEGreen;
   protected float EEBlue;
   protected boolean EEopa;
   protected boolean collide;
   protected float EEspeedx;
   protected float EEspeedy;
   protected float EEspeedz;
   public float EEsclTickAdd;
   public boolean EEalphaGlowing;
   protected int delay = 1;
   public EnumParticleTypes spawnVanillaParticles;
   ResourceLocation smoke = new ResourceLocation("arpg:textures/smoke.png");

   public GunPEmitter(
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
      int delay,
      boolean collide,
      ResourceLocation EEtexture,
      float EEscale,
      float EEgravity,
      int EElivetime,
      int EElight,
      float EEspeedx,
      float EEspeedy,
      float EEspeedz,
      float EERed,
      float EEGreen,
      float EEBlue,
      boolean EEopa
   ) {
      super(texture, scale, gravity, livetime, light, world, x, y, z, speedx, speedy, speedz, Red, Green, Blue, opa, 0);
      this.EEscale = EEscale;
      this.EEgravity = EEgravity;
      this.EElivetime = EElivetime;
      this.EElight = EElight;
      this.EEtexture = EEtexture;
      this.EERed = EERed;
      this.EEGreen = EEGreen;
      this.EEBlue = EEBlue;
      this.EEopa = EEopa;
      this.collide = collide;
      this.delay = delay;
      this.EEspeedx = EEspeedx;
      this.EEspeedy = EEspeedy;
      this.EEspeedz = EEspeedz;
      this.setVelocity(speedx, speedy, speedz);
   }

   public GunPEmitter(
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
      int delay,
      boolean collide,
      EnumParticleTypes spawnVanillaParticles,
      float EEspeedx,
      float EEspeedy,
      float EEspeedz
   ) {
      super(texture, scale, gravity, livetime, light, world, x, y, z, speedx, speedy, speedz, Red, Green, Blue, opa, 0);
      this.spawnVanillaParticles = spawnVanillaParticles;
      this.collide = collide;
      this.delay = delay;
      this.EEspeedx = EEspeedx;
      this.EEspeedy = EEspeedy;
      this.EEspeedz = EEspeedz;
      this.setVelocity(speedx, speedy, speedz);
   }

   @Override
   protected float getGravityVelocity() {
      return this.gravity;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted % this.delay == 0) {
         if (this.spawnVanillaParticles != null) {
            this.world
               .spawnParticle(
                  this.spawnVanillaParticles,
                  this.posX,
                  this.posY,
                  this.posZ,
                  this.EEspeedx,
                  this.EEspeedy,
                  this.EEspeedz,
                  new int[0]
               );
         } else {
            GUNParticle part = new GUNParticle(
               this.EEtexture,
               this.EEscale,
               this.EEgravity,
               this.EElivetime,
               this.EElight,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / this.EEspeedx,
               (float)this.rand.nextGaussian() / this.EEspeedy,
               (float)this.rand.nextGaussian() / this.EEspeedz,
               this.EERed,
               this.EEGreen,
               this.EEBlue,
               this.EEopa,
               0
            );
            part.scaleTickAdding = this.EEsclTickAdd;
            part.alphaGlowing = this.EEalphaGlowing;
            this.world.spawnEntity(part);
         }
      }

      if (this.ticksExisted > this.livetime + this.rand.nextGaussian() * 4.0) {
         this.setDead();
      }
   }

   @Override
   protected void onImpact(RayTraceResult result) {
      if (this.collide && result.typeOfHit == Type.BLOCK) {
         this.setDead();
      }
   }
}
