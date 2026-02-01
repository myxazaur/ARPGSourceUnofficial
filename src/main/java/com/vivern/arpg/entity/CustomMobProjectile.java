package com.vivern.arpg.entity;

import com.vivern.arpg.mobs.IMobCustomProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomMobProjectile extends EntityThrowable {
   public float grav = 0.0F;
   public double unstabilization = 1.0;
   public IMobCustomProjectile mobInterf = null;
   public int light = -1;
   public ResourceLocation texture;
   public float Red = 1.0F;
   public float Green = 1.0F;
   public float Blue = 1.0F;
   public boolean enableAlpha = false;
   public int rotation = 0;
   public float scale = 1.0F;
   public float alpha = 1.0F;
   public boolean alphaGlowing = false;
   public boolean noWaterBubble = true;
   public boolean isPushedByLiquids = true;
   public int renderStyle = 0;

   public CustomMobProjectile(World world) {
      super(world);
   }

   public CustomMobProjectile(World world, EntityLivingBase thrower) {
      super(world, thrower);
   }

   public CustomMobProjectile(World world, double x, double y, double z) {
      super(world, x, y, z);
   }

   public CustomMobProjectile(World world, EntityLivingBase thrower, float grav, double unstabilization, IMobCustomProjectile mobInterf) {
      super(world, thrower);
      this.grav = grav;
      this.unstabilization = unstabilization;
      this.mobInterf = mobInterf;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * this.unstabilization;
      this.motionZ = this.motionZ + entityThrower.motionZ * this.unstabilization;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * this.unstabilization;
      }
   }

   public boolean isInWater() {
      return this.noWaterBubble ? false : this.inWater;
   }

   public boolean isPushedByWater() {
      return this.isPushedByLiquids;
   }

   protected float getGravityVelocity() {
      return this.grav;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.mobInterf != null) {
         this.mobInterf.onUpdate(this);
      }

      this.world.setEntityState(this, (byte)9);
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8 && this.mobInterf != null) {
         this.mobInterf.onImpactClient(this);
      }

      if (id == 9 && this.mobInterf != null) {
         this.mobInterf.onUpdateClient(this);
      }

      if (id == -1) {
         this.texture = new ResourceLocation("arpg:textures/lava_drop.png");
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (this.mobInterf != null) {
         this.mobInterf.onImpact(this, result);
      }

      this.world.setEntityState(this, (byte)8);
   }
}
