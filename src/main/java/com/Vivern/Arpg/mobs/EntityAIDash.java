package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.SuperKnockback;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.SoundEvent;

public class EntityAIDash extends EntityAIBase {
   public AbstractMob entity;
   public int attackTimer = 0;
   public int maxCooldown = 30;
   public float radiusHigher = 0.0F;
   public float radiusLower = 0.0F;
   public float power = 0.0F;
   public boolean shouldJump = false;
   public float upMovement = 0.2F;
   public SoundEvent soundOnDash = null;

   public EntityAIDash(AbstractMob entity, int maxCooldown, float radiusHigher, float radiusLower, float power, boolean shouldJump, float upMovement) {
      this.entity = entity;
      this.maxCooldown = maxCooldown;
      this.radiusHigher = radiusHigher;
      this.radiusLower = radiusLower;
      this.power = power;
      this.shouldJump = shouldJump;
      this.upMovement = upMovement;
   }

   public EntityAIDash setSoundOnDash(SoundEvent soundOnDash) {
      this.soundOnDash = soundOnDash;
      return this;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public boolean shouldContinueExecuting() {
      return this.entity.getAttackTarget() != null && this.entity.getAttackTarget().isEntityAlive();
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      this.attackTimer--;
      if (attackTarg != null && this.attackTimer <= 0) {
         double dist = this.entity.getDistance(attackTarg);
         if (dist < this.radiusHigher && dist > this.radiusLower) {
            if (this.shouldJump) {
               this.entity.getJumpHelper().setJumping();
            }

            SuperKnockback.applyMove(this.entity, -this.power, attackTarg.posX, attackTarg.posY + 0.5, attackTarg.posZ);
            this.entity.motionY = this.entity.motionY + this.upMovement;
            this.attackTimer = this.maxCooldown;
            this.entity.triggerAnimation(-3);
            if (this.soundOnDash != null) {
               this.entity.playSound(this.soundOnDash, 1.0F, 0.9F + this.entity.getRNG().nextFloat() / 5.0F);
            }
         }
      }
   }
}
