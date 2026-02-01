package com.vivern.arpg.mobs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIShoot extends EntityAIBase {
   public boolean enable = true;
   public final AbstractMob entity;
   public int attackCooldown;
   public final float maxAttackDistancesq;
   public int attackTime = -1;
   public int seeTime;
   public int inUse = 0;
   public boolean isHandActive = false;
   public boolean burst = false;
   public int burstMaxCount = 0;
   public int burstHave = 0;
   public int maxnormalShootsToBurst = 0;
   public int normalShootsToBurst = 0;
   public boolean canStop = true;
   public int maxCooldownOnBurst;
   public int deltaRotation;

   public EntityAIShoot(AbstractMob mob, int attackCooldown, float maxAttackDistance, int deltaRotation) {
      this.entity = mob;
      this.attackCooldown = attackCooldown;
      this.maxAttackDistancesq = maxAttackDistance * maxAttackDistance;
      this.deltaRotation = deltaRotation;
   }

   public void setAttackCooldown(int attackCooldown) {
      this.attackCooldown = attackCooldown;
   }

   public EntityAIShoot setBurst(boolean burst, int burstMaxCount, int maxnormalShootsToBurst, boolean canStop, int maxCooldownOnBurst) {
      this.burst = burst;
      this.burstMaxCount = burstMaxCount;
      this.maxnormalShootsToBurst = maxnormalShootsToBurst;
      this.canStop = canStop;
      this.maxCooldownOnBurst = maxCooldownOnBurst;
      return this;
   }

   public boolean shouldExecute() {
      return this.enable && this.entity.getAttackTarget() != null;
   }

   public boolean shouldContinueExecuting() {
      return this.enable && (this.shouldExecute() || !this.entity.getNavigator().noPath());
   }

   public void startExecuting() {
      super.startExecuting();
   }

   public void resetTask() {
      super.resetTask();
      this.seeTime = 0;
      this.attackTime = -1;
      this.entity.resetActiveHand();
   }

   public void updateTask() {
      EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
      if (entitylivingbase != null) {
         double d0 = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
         boolean cansee = this.entity.getEntitySenses().canSee(entitylivingbase);
         if (cansee) {
            this.entity.faceEntity(entitylivingbase, this.deltaRotation, this.deltaRotation);
            this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, this.deltaRotation, this.deltaRotation);
         }

         if (this.isHandActive) {
            this.inUse++;
            if (cansee || this.seeTime >= -60 || this.burst && !this.canStop) {
               if (cansee || !this.canStop && this.burstHave > 0) {
                  if (this.maxnormalShootsToBurst <= 0 && this.burst) {
                     if (this.burst && (this.inUse >= 20 || this.burstHave > 0)) {
                        this.isHandActive = false;
                        this.entity.shoot();
                        this.inUse = 0;
                        if (this.burstHave == 0) {
                           this.burstHave = this.burstMaxCount;
                           this.attackTime = this.attackCooldown;
                        } else if (this.burstHave > 0) {
                           this.attackTime = this.maxCooldownOnBurst;
                           this.burstHave--;
                        } else {
                           this.burstHave = 0;
                           this.attackTime = this.attackCooldown;
                        }
                     }
                  } else if (this.inUse >= 20 || this.burstHave > 0) {
                     this.isHandActive = false;
                     this.entity.shoot();
                     this.inUse = 0;
                     if (this.burst) {
                        if (this.normalShootsToBurst < this.maxnormalShootsToBurst) {
                           this.normalShootsToBurst++;
                           this.attackTime = this.attackCooldown;
                           if (this.normalShootsToBurst == this.maxnormalShootsToBurst) {
                              this.burstHave = this.burstMaxCount;
                           }
                        } else if (this.burstHave > 0) {
                           this.attackTime = this.maxCooldownOnBurst;
                           this.burstHave--;
                        } else {
                           this.burstHave = 0;
                           this.normalShootsToBurst = 0;
                           this.attackTime = this.attackCooldown;
                        }
                     } else {
                        this.attackTime = this.attackCooldown;
                     }
                  }
               }
            } else {
               this.isHandActive = false;
               this.inUse = 0;
            }
         } else if (--this.attackTime <= 0 && this.seeTime >= -60 || !this.canStop && this.burstHave > 0) {
            this.isHandActive = true;
         }
      }
   }
}
