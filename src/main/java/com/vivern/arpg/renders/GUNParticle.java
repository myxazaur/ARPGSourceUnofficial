package com.vivern.arpg.renders;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUNParticle extends EntityThrowable {
   public float scale;
   public float gravity;
   public int livetime;
   public int light;
   public ResourceLocation texture;
   public float Red;
   public float Green;
   public float Blue;
   public boolean opa;
   public int rotation;
   public boolean collide;
   public float frictionMultipl = 1.0F;
   public float airFrictionMultipl = 1.0F;
   public float scaleTickAdding = 0.0F;
   public float scaleMax = 1.0E9F;
   public float scaleTickDropAdding = 0.0F;
   public float alphaTickAdding = 0.0F;
   public float alpha = 1.0F;
   public boolean alphaGlowing = false;
   public boolean dropMode = false;
   public boolean dropped = false;
   public boolean acidRender = false;
   public boolean noWaterBubble = true;
   public boolean isPushedByLiquids = true;
   public boolean noAirFriction = false;
   public Vec2f rotationPitchYaw = null;
   public ParticleTracker tracker = null;
   public boolean randomDeath = true;
   public boolean disableLightning = true;
   public boolean sticky = false;

   public GUNParticle(
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
      super(world, x, y, z);
      this.scale = scale;
      this.gravity = gravity;
      this.livetime = livetime;
      this.light = light;
      this.texture = texture;
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
      this.opa = opa;
      this.rotation = rotation;
      this.collide = false;
      this.alphaGlowing = false;
      this.setVelocity(speedx, speedy, speedz);
   }

   public GUNParticle(
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
      super(world, x, y, z);
      this.scale = scale;
      this.gravity = gravity;
      this.livetime = livetime;
      this.light = light;
      this.texture = texture;
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
      this.opa = opa;
      this.rotation = rotation;
      this.collide = collide;
      this.frictionMultipl = frictionMultipl;
      this.alphaGlowing = false;
      this.setVelocity(speedx, speedy, speedz);
   }

   public GUNParticle(
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
      super(world, x, y, z);
      this.scale = scale;
      this.gravity = gravity;
      this.livetime = livetime;
      this.light = light;
      this.texture = texture;
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
      this.opa = opa;
      this.rotation = rotation;
      this.collide = collide;
      this.frictionMultipl = frictionMultipl;
      this.scaleMax = scaleMax;
      this.scaleTickAdding = scaleTickAdding;
      this.scaleTickDropAdding = scaleTickDropAdding;
      this.alphaGlowing = false;
      this.setVelocity(speedx, speedy, speedz);
   }

   protected float getGravityVelocity() {
      return this.gravity;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.tracker != null) {
         this.tracker.update(this);
      }

      if (this.noAirFriction) {
         float f1 = 0.99F;
         this.motionX /= f1;
         this.motionY /= f1;
         this.motionZ /= f1;
      } else {
         this.motionX = this.motionX * this.airFrictionMultipl;
         this.motionY = this.motionY * this.airFrictionMultipl;
         this.motionZ = this.motionZ * this.airFrictionMultipl;
      }

      if (this.scale < this.scaleMax) {
         this.scale = this.scale + this.scaleTickAdding;
      }

      if (this.ticksExisted > this.livetime + (this.randomDeath ? this.rand.nextGaussian() * 4.0 : 0.0)) {
         this.setDead();
      }

      this.alpha = this.alpha + this.alphaTickAdding;
   }

   public boolean isInWater() {
      return this.noWaterBubble ? false : this.inWater;
   }

   public boolean isPushedByWater() {
      return this.isPushedByLiquids;
   }

   protected void onImpact(RayTraceResult result) {
      if (this.collide
         && this.ticksExisted > 1
         && result.typeOfHit == Type.BLOCK
         && this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
         if (this.sticky) {
            this.motionY = 0.0;
            this.motionX = 0.0;
            this.motionZ = 0.0;
            this.gravity = 0.0F;
            this.snapToFace(result.sideHit);
         } else {
            if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
               this.motionY = 0.0;
               this.motionX = this.motionX / (1.005F * this.frictionMultipl);
               this.motionZ = this.motionZ / (1.005F * this.frictionMultipl);
               this.dropped = this.dropMode;
               if (this.scale < this.scaleMax) {
                  this.scale = this.scale + this.scaleTickDropAdding;
               }
            }

            if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
               this.motionZ = 0.0;
               this.motionX = this.motionX / (1.005F * this.frictionMultipl);
               this.motionY = this.motionY / (1.001F * this.frictionMultipl);
            }

            if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
               this.motionX = 0.0;
               this.motionZ = this.motionZ / (1.005F * this.frictionMultipl);
               this.motionY = this.motionY / (1.001F * this.frictionMultipl);
            }
         }
      }
   }

   public void snapToFace(EnumFacing face) {
      if (face.getHorizontalIndex() >= 0) {
         this.rotationPitchYaw = new Vec2f(0.0F, face.getHorizontalAngle() + 180.0F);
      } else if (face == EnumFacing.UP) {
         this.rotationPitchYaw = new Vec2f(90.0F, 0.0F);
      } else {
         this.rotationPitchYaw = new Vec2f(-90.0F, 0.0F);
      }
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy, float unstable) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * unstable;
      this.motionZ = this.motionZ + entityThrower.motionZ * unstable;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * unstable;
      }
   }

   public boolean shouldRenderInPass(int pass) {
      return pass == 1;
   }
}
