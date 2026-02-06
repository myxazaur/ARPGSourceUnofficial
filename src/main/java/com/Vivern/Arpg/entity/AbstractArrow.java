package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponParameters;
import java.lang.reflect.Field;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class AbstractArrow extends EntityArrow {
   public int ticksAir = 0;
   public int livetimeAir = 120;
   public int knockbackStrength;
   public double bowDamage;
   public boolean waterMoveHook = false;
   public static int updateFrequency = 1;
   public Item itemArrow = Items.ARROW;
   public boolean isTrueUnpickableArrow = false;

   public AbstractArrow(World worldIn) {
      super(worldIn);
   }

   public AbstractArrow(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   public AbstractArrow(World worldIn, EntityLivingBase shooter) {
      super(worldIn);
      if (shooter != null) {
         this.setPosition(shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1F, shooter.posZ);
         this.shootingEntity = shooter;
         if (shooter instanceof EntityPlayer) {
            this.pickupStatus = PickupStatus.ALLOWED;
         }
      }
   }

   public boolean doWaterMoveHook() {
      return false;
   }

   public int waterParticlesHookAdding() {
      return 0;
   }

   public void modifySpeedInWater() {
   }

   public double getItemArrowDamage(World worldIn) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.itemArrow);
      return parameters.get("damage");
   }

   public abstract SoundEvent getHitSound();

   public float getHitSoundVolume(float basevolume) {
      return basevolume;
   }

   public float getHitSoundPitch(float basepitch) {
      return basepitch;
   }

   public void playSound(SoundEvent soundIn, float volume, float pitch) {
      if (soundIn == SoundEvents.ENTITY_ARROW_HIT) {
         soundIn = this.getHitSound();
      }

      super.playSound(soundIn, this.getHitSoundVolume(volume), this.getHitSoundPitch(pitch));
   }

   protected void entityInit() {
      super.entityInit();
   }

   public void shoot(@Nullable Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(yaw * (float) (Math.PI / 180.0)) * MathHelper.cos(pitch * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin(pitch * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(yaw * (float) (Math.PI / 180.0)) * MathHelper.cos(pitch * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      if (shooter != null) {
         this.motionX = this.motionX + shooter.motionX;
         this.motionZ = this.motionZ + shooter.motionZ;
         if (!shooter.onGround) {
            this.motionY = this.motionY + shooter.motionY;
         }
      }

      this.velocityChanged = true;
   }

   public float calculateDamage(RayTraceResult raytraceResultIn, DamageSource damagesource) {
      if (this.pickupStatus != PickupStatus.ALLOWED && !this.isTrueUnpickableArrow) {
         return 0.0F;
      } else {
         double finalDamagePerSpeed = this.getItemArrowDamage(this.world) + this.bowDamage;
         if (Team.checkIsOpponent(this.shootingEntity, raytraceResultIn.entityHit)) {
            float f = MathHelper.sqrt(
               this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ
            );
            int i = MathHelper.ceil(f * finalDamagePerSpeed);
            if (this.getIsCritical()) {
               i += this.rand.nextInt(i / 2 + 2);
            }

            return i;
         } else {
            float f = MathHelper.sqrt(
               this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ
            );
            int i = MathHelper.ceil(f * finalDamagePerSpeed);
            return (float)Math.min(finalDamagePerSpeed, (double)i);
         }
      }
   }

   public void onHit(RayTraceResult raytraceResultIn) {
      Entity entity = raytraceResultIn.entityHit;
      this.world.setEntityState(this, (byte)8);
      if (entity != null) {
         DamageSource damagesource;
         if (this.shootingEntity == null) {
            damagesource = DamageSource.causeArrowDamage(this, this);
         } else {
            damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
         }

         if (entity.attackEntityFrom(damagesource, this.calculateDamage(raytraceResultIn, damagesource))) {
            if (entity instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
               entity.hurtResistantTime = 0;
               if (!this.world.isRemote) {
                  entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
               }

               if (this.knockbackStrength > 0) {
                  float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                  if (f1 > 0.0F) {
                     entitylivingbase.addVelocity(
                        this.motionX * this.knockbackStrength * 0.6F / f1, 0.1, this.motionZ * this.knockbackStrength * 0.6F / f1
                     );
                  }
               }

               if (this.shootingEntity instanceof EntityLivingBase) {
                  EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
                  EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entitylivingbase);
               }

               this.arrowHit(entitylivingbase);
               if (this.shootingEntity != null
                  && entitylivingbase != this.shootingEntity
                  && entitylivingbase instanceof EntityPlayer
                  && this.shootingEntity instanceof EntityPlayerMP) {
                  ((EntityPlayerMP)this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
               }
            }

            if (this.isBurning() && !(entity instanceof EntityEnderman)) {
               entity.setFire(5);
            }

            this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            if (!(entity instanceof EntityEnderman)) {
               this.removeArrowEntity(entity);
            }
         } else {
            this.ricochet(entity);
            if (!this.world.isRemote
               && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ < 0.001F) {
               if (this.pickupStatus == PickupStatus.ALLOWED) {
                  this.entityDropItem(this.getArrowStack(), 0.1F);
               }

               this.removeArrowEntity(null);
            }
         }
      } else {
         BlockPos blockpos = raytraceResultIn.getBlockPos();
         IBlockState iblockstate = this.world.getBlockState(blockpos);

         try {
            Class thisclass = this.getClass();
            Field fxtile = thisclass.getField("xTile");
            Field fytile = thisclass.getField("yTile");
            Field fztile = thisclass.getField("zTile");
            fxtile.setAccessible(true);
            fytile.setAccessible(true);
            fztile.setAccessible(true);
            fxtile.setInt(this, blockpos.getX());
            fytile.setInt(this, blockpos.getY());
            fztile.setInt(this, blockpos.getZ());
            Field finTile = thisclass.getField("inTile");
            Field finData = thisclass.getField("inData");
            finTile.setAccessible(true);
            finData.setAccessible(true);
            finTile.set(this, iblockstate.getBlock());
            finData.set(this, iblockstate.getBlock().getMetaFromState(iblockstate));
         } catch (Exception var11) {
         }

         this.motionX = (float)(raytraceResultIn.hitVec.x - this.posX);
         this.motionY = (float)(raytraceResultIn.hitVec.y - this.posY);
         this.motionZ = (float)(raytraceResultIn.hitVec.z - this.posZ);
         float f2 = MathHelper.sqrt(
            this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ
         );
         this.posX = this.posX - this.motionX / f2 * 0.05F;
         this.posY = this.posY - this.motionY / f2 * 0.05F;
         this.posZ = this.posZ - this.motionZ / f2 * 0.05F;
         this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
         this.inGround = true;
         this.arrowShake = 7;
         this.setIsCritical(false);
         if (iblockstate.getMaterial() != Material.AIR) {
            iblockstate.getBlock().onEntityCollision(this.world, blockpos, iblockstate, this);
         }
      }
   }

   public void ricochet(Entity entityFrom) {
      this.motionX *= -0.1F;
      this.motionY *= -0.1F;
      this.motionZ *= -0.1F;
      this.rotationYaw += 180.0F;
      this.prevRotationYaw += 180.0F;
      this.ticksAir = 0;
   }

   public void onUpdate() {
      if (this.doWaterMoveHook() && !this.inGround && this.inWater) {
         this.waterMoveHook = true;
         super.onUpdate();
         this.waterMoveHook = false;
         int imax = this.waterParticlesHookAdding();
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

         this.modifySpeedInWater();
      } else {
         super.onUpdate();
      }

      if (this.world.isRemote) {
         if (this.inGround) {
            if (this.timeInGround % 5 == 0) {
               this.spawnArrowParticles(1);
            }
         } else {
            this.spawnArrowParticles(2);
         }
      }

      if (this.ticksAir > this.livetimeAir) {
         this.setDead();
      }

      if (!this.inGround) {
         this.ticksAir++;
      }
   }

   public boolean isInWater() {
      return this.waterMoveHook ? false : super.isInWater();
   }

   public void spawnArrowParticles(int particleCount) {
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      super.writeEntityToNBT(compound);
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      super.readEntityFromNBT(compound);
   }

   public void arrowHit(EntityLivingBase living) {
      super.arrowHit(living);
   }

   public void setKnockbackStrength(int knockbackStrengthIn) {
      this.knockbackStrength = knockbackStrengthIn;
   }

   public void setDamage(double damageIn) {
      this.bowDamage = damageIn;
      super.setDamage(damageIn);
   }

   public double getDamage() {
      return this.bowDamage;
   }

   public void removeArrowEntity(Entity entityHitted) {
      this.setDead();
   }
}
