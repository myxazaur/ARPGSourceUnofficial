//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySunrise extends EntityThrowable {
   protected Entity to = null;
   protected float yaw;
   protected float pitch;
   public boolean link = true;

   public EntitySunrise(World world) {
      super(world);
   }

   public EntitySunrise(World world, EntityLivingBase thrower) {
      super(world, thrower);
   }

   public EntitySunrise(World world, double x, double y, double z) {
      super(world, x, y, z);
   }

   @SideOnly(Side.CLIENT)
   public void setVelocity(double x, double y, double z) {
      this.motionX = x;
      this.motionY = y;
      this.motionZ = z;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.3;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.3;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.3;
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      ResourceLocation resoue = new ResourceLocation("arpg:textures/light.png");
      if (id == 5) {
         double d1 = this.lastTickPosX - this.posX;
         double d2 = this.lastTickPosY - this.posY;
         double d3 = this.lastTickPosZ - this.posZ;
         float dist = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
         float prunex = (float)(d1 / dist / 3.0);
         float pruney = (float)(d2 / dist / 3.0);
         float prunez = (float)(d3 / dist / 3.0);
         this.world.playSound(this.posX, this.posY, this.posZ, Sounds.fire_a, SoundCategory.AMBIENT, 0.7F, 1.5F, false);

         for (int ss = 0; ss < 10; ss++) {
            Entity spelll = new GUNParticle(
               resoue,
               0.1F,
               0.01F,
               10,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               prunex + (float)this.rand.nextGaussian() / 20.0F,
               pruney + (float)this.rand.nextGaussian() / 20.0F,
               prunez + (float)this.rand.nextGaussian() / 20.0F,
               0.9F + (float)this.rand.nextGaussian() / 10.0F,
               0.9F + (float)this.rand.nextGaussian() / 10.0F,
               0.8F + (float)this.rand.nextGaussian() / 10.0F,
               false,
               0
            );
            this.world.spawnEntity(spelll);
         }
      }

      if (id == 18) {
         Entity spelll = new GUNParticle(
            resoue,
            0.1F,
            -0.01F,
            10,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 14.0F,
            (float)this.rand.nextGaussian() / 14.0F,
            (float)this.rand.nextGaussian() / 14.0F,
            0.9F + (float)this.rand.nextGaussian() / 10.0F,
            0.9F + (float)this.rand.nextGaussian() / 10.0F,
            0.8F + (float)this.rand.nextGaussian() / 10.0F,
            false,
            0
         );
         this.world.spawnEntity(spelll);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (this.to == null
         && result.entityHit != this.thrower
         && result.entityHit != null
         && result.entityHit != this.getThrower()
         && this.ticksExisted > 1
         && this.link) {
         this.link = false;
         this.to = result.entityHit;
         this.yaw = this.rotationYaw;
         this.pitch = this.rotationPitch;
         System.out.println("Link!");
         this.to.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 5.0F);
         this.to.hurtResistantTime = 1;
         this.setRotation(this.yaw, this.pitch);
      }

      if (result.typeOfHit == Type.BLOCK) {
         this.setDead();
      }

      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)5);
      }
   }

   public void onUpdate() {
      if (this.rand.nextGaussian() > 0.0) {
         this.world.setEntityState(this, (byte)18);
      }

      super.onUpdate();
      if (this.ticksExisted > 100) {
         this.setDead();
      }

      if (this.ticksExisted > 1 && this.to != null && this.to != this.thrower && this.to != this.getThrower()) {
         this.setNoGravity(true);
         this.setRotation(this.yaw, this.pitch);
         this.setPositionAndUpdate(this.to.posX, this.to.posY + this.to.getEyeHeight() / 2.0F, this.to.posZ);
         System.out.println(this.rotationYaw + "  " + this.rotationPitch);
         this.setRotationYawHead(this.yaw);
         this.motionX = 0.0;
         this.motionY = 0.0;
         this.motionZ = 0.0;
         if (this.ticksExisted % 20 == 0) {
            this.to.attackEntityFrom(DamageSource.MAGIC, 1.0F);
            this.to.hurtResistantTime = 0;
         }

         if (this.to.isDead) {
            this.setDead();
         }
      }
   }
}
