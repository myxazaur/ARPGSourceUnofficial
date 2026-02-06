package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.SuperKnockback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;

public class EntityAIForceAttack extends EntityAIBase {
   public AbstractMob entity;
   public int attackTimer = 0;
   public int maxCooldown = 30;
   public float radiusHigher = 0.0F;
   public float radiusLower = 0.0F;
   public float power = 0.0F;
   public float upMovement = 0.2F;
   public SoundEvent soundOnAttack = null;
   public float forcedistance = 5.0F;
   public float sizestep = 1.0F;
   public float damage = 1.0F;
   public boolean isWaterhammer = false;
   public boolean useRocketRotations = false;

   public EntityAIForceAttack(
      AbstractMob entity,
      float damage,
      int maxCooldown,
      float radiusHigher,
      float radiusLower,
      float power,
      float upMovement,
      float forcedistance,
      float sizestep
   ) {
      this.entity = entity;
      this.maxCooldown = maxCooldown;
      this.radiusHigher = radiusHigher;
      this.radiusLower = radiusLower;
      this.power = power;
      this.upMovement = upMovement;
      this.forcedistance = forcedistance;
      this.sizestep = sizestep;
      this.damage = damage;
   }

   public EntityAIForceAttack setSoundOnAttack(SoundEvent soundOnAttack) {
      this.soundOnAttack = soundOnAttack;
      return this;
   }

   public EntityAIForceAttack setWaterhammer() {
      this.isWaterhammer = true;
      return this;
   }

   public EntityAIForceAttack setuseRocketRotations() {
      this.useRocketRotations = true;
      return this;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      this.attackTimer--;
      if (attackTarg != null && this.attackTimer <= 0) {
         double dist = this.entity.getDistance(attackTarg);
         if (dist < this.radiusHigher && dist > this.radiusLower) {
            if (this.useRocketRotations) {
               for (Entity base : GetMOP.entityMopRayTrace(
                  Entity.class,
                  this.forcedistance,
                  1.0F,
                  this.entity,
                  this.sizestep,
                  this.sizestep,
                  this.entity.rotationPitch * -5.0F,
                  -this.entity.rotationYaw
               )) {
                  if (!this.isWaterhammer || base.isInWater()) {
                     SuperKnockback.applyKnockback(base, this.power, this.entity.posX, this.entity.posY, this.entity.posZ);
                     base.motionY = base.motionY + this.upMovement;
                     if (this.damage > 0.0F) {
                        base.attackEntityFrom(DamageSource.causeMobDamage(this.entity), this.damage);
                     } else {
                        base.attackEntityFrom(
                           DamageSource.causeMobDamage(this.entity), (float)this.entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()
                        );
                     }

                     base.velocityChanged = true;
                  }
               }
            } else {
               for (EntityLivingBase basex : GetMOP.MopRayTrace(this.forcedistance, 1.0F, this.entity, this.sizestep, this.sizestep)) {
                  if (!this.isWaterhammer || basex.isInWater()) {
                     SuperKnockback.applyKnockback(basex, this.power, this.entity.posX, this.entity.posY, this.entity.posZ);
                     basex.motionY = basex.motionY + this.upMovement;
                     if (this.damage > 0.0F) {
                        basex.attackEntityFrom(DamageSource.causeMobDamage(this.entity), this.damage);
                     } else {
                        basex.attackEntityFrom(
                           DamageSource.causeMobDamage(this.entity), (float)this.entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()
                        );
                     }

                     basex.velocityChanged = true;
                  }
               }
            }

            this.attackTimer = this.maxCooldown;
            this.entity.triggerAnimation(-2);
            this.entity.world.setEntityState(this.entity, (byte)11);
            if (this.soundOnAttack != null) {
               this.entity.playSound(this.soundOnAttack, 1.5F, 0.9F + this.entity.getRNG().nextFloat() / 5.0F);
            }
         }
      }
   }
}
