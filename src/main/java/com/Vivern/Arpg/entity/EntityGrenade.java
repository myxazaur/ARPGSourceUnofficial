//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.ItemGrenade;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGrenade extends EntityThrowable {
   public ItemGrenade grenade;
   public int flyingTime = 0;
   public Vec3d randomRotate;
   public float rotationProgr = 0.0F;
   public float prevrotationProgr = 0.0F;
   public int impacts = 0;
   public boolean waterMoveHook = false;
   public int explodes = 0;

   public EntityGrenade(World world) {
      super(world);
      this.setSize(0.2F, 0.2F);
      this.randomRotate = new Vec3d(this.rand.nextGaussian(), this.rand.nextGaussian(), this.rand.nextGaussian());
   }

   public EntityGrenade(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.setSize(0.2F, 0.2F);
      this.randomRotate = new Vec3d(this.rand.nextGaussian(), this.rand.nextGaussian(), this.rand.nextGaussian());
   }

   public EntityGrenade(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.setSize(0.2F, 0.2F);
      this.randomRotate = new Vec3d(this.rand.nextGaussian(), this.rand.nextGaussian(), this.rand.nextGaussian());
   }

   protected float getGravityVelocity() {
      return this.inWater ? super.getGravityVelocity() / 2.0F : super.getGravityVelocity();
   }

   public float getspeedSq() {
      return (float)(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
   }

   public boolean isInWater() {
      return this.waterMoveHook ? false : super.isInWater();
   }

   public void onUpdate() {
      this.flyingTime++;
      if (this.grenade != null && this.grenade.doWaterMoveHook() && !this.inGround && this.inWater) {
         this.waterMoveHook = true;
         super.onUpdate();
         this.waterMoveHook = false;
         int imax = this.grenade.waterParticlesHookAdding();
         if (imax > 0) {
            for (int i = 0; i < imax; i++) {
               float f3 = 0.25F;
               this.world
                  .spawnParticle(
                     EnumParticleTypes.WATER_BUBBLE,
                     this.posX - this.motionX * 0.25,
                     this.posY - this.motionY * 0.25,
                     this.posZ - this.motionZ * 0.25,
                     this.motionX,
                     this.motionY,
                     this.motionZ,
                     new int[0]
                  );
            }
         }

         this.grenade.modifySpeedInWater(this);
      } else {
         super.onUpdate();
      }

      if (this.grenade != null) {
         this.grenade.onProjectileUpdate(this);
         if (!this.world.isRemote && (this.ticksExisted <= 2 || this.ticksExisted % 15 == 0)) {
            this.world.setEntityState(this, this.grenade.id);
         }
      }

      if (this.world.isRemote) {
         this.prevrotationProgr = this.rotationProgr;
         this.rotationProgr = this.rotationProgr + MathHelper.sqrt(this.getspeedSq());
      }
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.setInteger("time", this.flyingTime);
      if (this.grenade != null) {
         compound.setByte("grenadeid", this.grenade.id);
      }

      if (this.explodes != 0) {
         compound.setInteger("explodes", this.explodes);
      }

      super.writeEntityToNBT(compound);
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("time")) {
         this.flyingTime = compound.getInteger("time");
      }

      if (compound.hasKey("grenadeid")) {
         this.grenade = ItemGrenade.registry.get(compound.getByte("grenadeid"));
      }

      if (compound.hasKey("explodes")) {
         this.explodes = compound.getInteger("explodes");
      }

      super.readEntityFromNBT(compound);
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id != -1 || this.grenade == null) {
         this.grenade = ItemGrenade.registry.get(id);
      } else if (this.world.isRemote) {
         this.grenade.explode(this.world, this.thrower, this.posX, this.posY, this.posZ, null, this);
      }
   }

   public boolean bounce(@Nullable RayTraceResult result, double speedMultipl) {
      boolean ret = false;
      if (result != null
         && result.getBlockPos() != null
         && this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
         if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
            this.motionY = -this.motionY * speedMultipl;
            ret = true;
         }

         if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
            this.motionZ = -this.motionZ * speedMultipl;
            ret = true;
         }

         if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
            this.motionX = -this.motionX * speedMultipl;
            ret = true;
         }

         this.velocityChanged = true;
      }

      return ret;
   }

   public void slowdown(double mult) {
      this.motionX *= mult;
      this.motionY *= mult;
      this.motionZ *= mult;
   }

   protected void onImpact(RayTraceResult result) {
      if (this.grenade != null) {
         this.grenade.onProjectileImpact(this, result);
      }

      if (result.entityHit != this.thrower) {
         this.impacts++;
      }
   }
}
