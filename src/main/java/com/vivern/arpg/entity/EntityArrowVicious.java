package com.vivern.arpg.entity;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityArrowVicious extends AbstractArrow {
   static ResourceLocation texture = new ResourceLocation("arpg:textures/shard.png");
   public boolean hasMoreArrows = false;

   public EntityArrowVicious(World worldIn) {
      super(worldIn);
   }

   public EntityArrowVicious(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   public EntityArrowVicious(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
   }

   @Override
   public boolean doWaterMoveHook() {
      return true;
   }

   @Override
   public int waterParticlesHookAdding() {
      return 1;
   }

   @Override
   public void modifySpeedInWater() {
      double multiplier = 0.9;
      this.motionX *= multiplier;
      this.motionY *= multiplier;
      this.motionZ *= multiplier;
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound compound) {
      super.writeEntityToNBT(compound);
      compound.setBoolean("hasMoreArrows", this.hasMoreArrows);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound compound) {
      super.readEntityFromNBT(compound);
      if (compound.hasKey("hasMoreArrows")) {
         this.hasMoreArrows = compound.getBoolean("hasMoreArrows");
      }
   }

   @Override
   public SoundEvent getHitSound() {
      return Sounds.arrow_vicious;
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(ItemsRegister.ARROWVICIOUS);
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.hasMoreArrows) {
         if (this.ticksExisted >= 3 && !this.inGround && this.pickupStatus == PickupStatus.ALLOWED) {
            this.hasMoreArrows = false;
            if (!this.world.isRemote) {
               EntityArrowVicious newarrow = new EntityArrowVicious(this.world, this.posX, this.posY, this.posZ);
               newarrow.shoot(
                  null, -this.rotationPitch, -this.rotationYaw + 3.0F + this.rand.nextInt(3), 0.0F, (float)GetMOP.getSpeed(this), 2.0F
               );
               this.world.spawnEntity(newarrow);
               newarrow.setDamage(this.getDamage());
               newarrow.itemArrow = this.itemArrow;
               newarrow.shootingEntity = this.shootingEntity;
               newarrow.isTrueUnpickableArrow = true;
               newarrow.setDamage(this.bowDamage);
               newarrow.itemArrow = this.itemArrow;
               EntityArrowVicious newarrow2 = new EntityArrowVicious(this.world, this.posX, this.posY, this.posZ);
               newarrow2.shoot(
                  null, -this.rotationPitch, -this.rotationYaw - 3.0F - this.rand.nextInt(3), 0.0F, (float)GetMOP.getSpeed(this), 2.0F
               );
               this.world.spawnEntity(newarrow2);
               newarrow2.setDamage(this.getDamage());
               newarrow2.itemArrow = this.itemArrow;
               newarrow2.shootingEntity = this.shootingEntity;
               newarrow2.isTrueUnpickableArrow = true;
               newarrow2.setDamage(this.bowDamage);
               newarrow2.itemArrow = this.itemArrow;
               newarrow.pickupStatus = PickupStatus.DISALLOWED;
               newarrow2.pickupStatus = PickupStatus.DISALLOWED;
            }

            this.playSound(Sounds.arrow_vicious_multiple, 1.0F, 0.85F + this.rand.nextFloat() * 0.3F);
            this.pickupStatus = PickupStatus.DISALLOWED;
            this.isTrueUnpickableArrow = true;
            this.world.setEntityState(this, (byte)9);
         }
      } else if (!this.world.isRemote && this.timeInGround > 300) {
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 9) {
         for (int i = 0; i < 5; i++) {
            GUNParticle part = new GUNParticle(
               texture,
               0.06F + this.rand.nextFloat() * 0.04F,
               0.05F,
               10 + this.rand.nextInt(10),
               170,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 30.0F + (float)this.motionX,
               (float)this.rand.nextGaussian() / 30.0F + (float)this.motionY,
               (float)this.rand.nextGaussian() / 30.0F + (float)this.motionZ,
               0.5F,
               0.7F - this.rand.nextFloat() * 0.2F,
               0.55F - this.rand.nextFloat() * 0.1F,
               false,
               this.rand.nextInt(360)
            );
            part.scaleTickAdding = -0.006F;
            this.world.spawnEntity(part);
         }
      } else {
         super.handleStatusUpdate(id);
      }
   }
}
