package com.vivern.arpg.entity;

import com.vivern.arpg.renders.ParticleTracker;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityCubicParticle extends EntityThrowable {
   public float scale;
   public float gravity;
   public int livetime;
   public int light;
   public ResourceLocation texture;
   public float Red;
   public float Green;
   public float Blue;
   public boolean opa;
   public float rotateX;
   public float rotateY;
   public float rotateZ;
   public float rotateSpeed;
   public boolean rotateRand;
   public float scaleTickAdding;
   public float maxsize = 999999.0F;
   public ParticleTracker tracker;
   public boolean alphaGlowing = false;
   public boolean collide;
   public float frictionMultipl = 1.0F;

   public EntityCubicParticle(
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
      float rotateX,
      float rotateY,
      float rotateZ,
      float rotateSpeed,
      boolean rotateRand,
      float scaleTickAdding
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
      this.rotateSpeed = rotateSpeed;
      this.rotateRand = rotateRand;
      this.scaleTickAdding = scaleTickAdding;
      this.rotateX = rotateX;
      this.rotateY = rotateY;
      this.rotateZ = rotateZ;
      this.setVelocity(speedx, speedy, speedz);
   }

   protected float getGravityVelocity() {
      return this.gravity;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.scale < this.maxsize && this.ticksExisted > 1) {
         this.scale = this.scale + this.scaleTickAdding;
      }

      if (this.ticksExisted > this.livetime) {
         this.setDead();
      }

      if (this.tracker != null) {
         this.tracker.update(this);
      }
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
         if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
            this.motionY = 0.0;
            this.motionX = this.motionX / (1.005F * this.frictionMultipl);
            this.motionZ = this.motionZ / (1.005F * this.frictionMultipl);
            if (this.rotateSpeed != 0.0F) {
               this.rotateSpeed = 0.0F;
               this.rotateX = 0.0F;
               this.rotateZ = 0.0F;
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
