package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.SuperKnockback;
import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAICorrupter extends EntityAIBase {
   public boolean enable = true;
   public final AbstractMob entity;
   public int maxattackCooldown;
   public int currentattackCooldown = -1;
   public final float maxAttackDistancesq;
   public int seeTime;
   public boolean strafingClockwise;
   public boolean strafingBackwards;
   public int strafingTime = -1;
   public int inUse = 0;
   public boolean isHandActive = false;
   public boolean useshoot = false;
   public int deltaRotation = 30;
   public int attackDelay = 0;
   public int currentattackDelay = 0;
   public boolean burst = false;
   public int burstMaxCount = 0;
   public int burstHave = 0;
   public int maxnormalShootsToBurst = 0;
   public int normalShootsToBurst = 0;
   public boolean canStop = true;
   public int maxCooldownOnBurst;
   public float strafingDistanceSq = 0.0F;
   public float movingDirection = 0.0F;
   public float strafingAmplitude = 0.0F;
   public float dislocDirectionX = 0.0F;
   public float dislocDirectionY = 0.0F;
   public float dislocDirectionZ = 0.0F;
   public boolean triggerAnim3 = false;
   public final Random rand;

   public EntityAICorrupter(
      AbstractMob mob, int attackCooldown, float maxShootDistance, int attackDelay, boolean shoot, float strafingDistance, float strafingAmplitude
   ) {
      this.entity = mob;
      this.rand = mob.getRNG();
      this.maxattackCooldown = attackCooldown;
      this.maxAttackDistancesq = maxShootDistance * maxShootDistance;
      this.useshoot = shoot;
      this.strafingDistanceSq = strafingDistance * strafingDistance;
      this.strafingAmplitude = strafingAmplitude;
      this.dislocDirectionX = (float)(this.rand.nextGaussian() / 4.0);
      this.dislocDirectionY = (float)(this.rand.nextGaussian() / 4.0);
      this.dislocDirectionZ = (float)(this.rand.nextGaussian() / 4.0);
      this.attackDelay = attackDelay;
      this.setMutexBits(3);
   }

   public void setAttackCooldown(int attackCooldown) {
      this.maxattackCooldown = attackCooldown;
   }

   public EntityAICorrupter setBurst(boolean burst, int burstMaxCount, int maxnormalShootsToBurst, boolean canStop, int maxCooldownOnBurst, int attackDelay) {
      this.burst = burst;
      this.burstMaxCount = burstMaxCount;
      this.maxnormalShootsToBurst = maxnormalShootsToBurst;
      this.canStop = canStop;
      this.maxCooldownOnBurst = maxCooldownOnBurst;
      this.attackDelay = attackDelay;
      return this;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null && this.enable;
   }

   public boolean shouldContinueExecuting() {
      return (this.shouldExecute() || !this.entity.getNavigator().noPath()) && this.enable;
   }

   public void startExecuting() {
      super.startExecuting();
   }

   public void resetTask() {
      super.resetTask();
      this.seeTime = 0;
      this.currentattackCooldown = -1;
      this.entity.resetActiveHand();
   }

   public void updateTask() {
      EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
      if (this.currentattackCooldown > 0) {
         this.currentattackCooldown--;
      }

      if (entitylivingbase != null) {
         double d0 = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
         boolean flag = this.entity.getEntitySenses().canSee(entitylivingbase);
         boolean flag1 = this.seeTime > 0;
         if (flag != flag1) {
            this.seeTime = 0;
         }

         if (flag) {
            this.seeTime++;
         } else {
            this.seeTime--;
         }

         if (d0 <= this.maxAttackDistancesq && this.seeTime >= 20) {
            this.entity.getNavigator().clearPath();
            this.strafingTime++;
         } else {
            this.strafingTime = -1;
         }

         if (this.strafingTime >= 20) {
            if (this.entity.getRNG().nextFloat() < 0.3) {
               this.strafingClockwise = !this.strafingClockwise;
            }

            if (this.entity.getRNG().nextFloat() < 0.3) {
               this.strafingBackwards = !this.strafingBackwards;
            }

            this.strafingTime = 0;
         }

         if (this.strafingTime > -1) {
            if (d0 > this.maxAttackDistancesq * 0.75F) {
               this.strafingBackwards = false;
            } else if (d0 < this.maxAttackDistancesq * 0.25F) {
               this.strafingBackwards = true;
            }
         }

         this.entity.faceEntity(entitylivingbase, this.deltaRotation, this.deltaRotation);
         this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, this.deltaRotation, this.deltaRotation);
         double directDist = this.strafingDistanceSq - d0;
         if (this.rand.nextFloat() * this.strafingAmplitude < Math.abs(directDist)) {
            float flyspeed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
            this.movingDirection = this.rand.nextFloat() * flyspeed * (this.rand.nextGaussian() * this.strafingAmplitude > directDist ? -1 : 1);
         }

         SuperKnockback.applyMove(
            this.entity, this.movingDirection, entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ
         );
         if (this.rand.nextFloat() < 0.07) {
            this.dislocDirectionX = (float)(this.rand.nextGaussian() / 4.0);
            this.dislocDirectionY = (float)(this.rand.nextGaussian() / 4.0);
            this.dislocDirectionZ = (float)(this.rand.nextGaussian() / 4.0);
         }

         SuperKnockback.applyMove(
            this.entity,
            this.movingDirection / 2.5F,
            this.entity.posX + this.dislocDirectionX,
            this.entity.posY + this.dislocDirectionY,
            this.entity.posZ + this.dislocDirectionZ
         );
         if (this.useshoot) {
            if (d0 < this.maxAttackDistancesq && flag || !this.canStop && this.burstHave > 0) {
               this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, this.deltaRotation, this.deltaRotation);
               if (this.burst) {
                  if (this.burstHave > 0) {
                     if (this.currentattackCooldown <= 0) {
                        if (this.triggerAnim3 && this.burstHave == this.burstMaxCount) {
                           this.entity.triggerAnimation(-3);
                        }

                        this.currentattackCooldown = this.maxCooldownOnBurst;
                        this.entity.shoot();
                        this.burstHave--;
                        if (this.burstHave <= 0) {
                           this.currentattackCooldown = this.maxattackCooldown;
                        }
                     }
                  } else if (this.normalShootsToBurst >= this.maxnormalShootsToBurst) {
                     this.normalShootsToBurst = 0;
                     this.burstHave = this.burstMaxCount;
                  } else if (this.currentattackCooldown <= 0) {
                     if (this.currentattackDelay >= this.attackDelay) {
                        this.currentattackCooldown = this.maxattackCooldown;
                        this.entity.shoot();
                        this.currentattackDelay = 0;
                        this.normalShootsToBurst++;
                     } else {
                        if (this.triggerAnim3 && this.currentattackDelay == 0) {
                           this.entity.triggerAnimation(-3);
                        }

                        this.currentattackDelay++;
                     }
                  }
               } else if (this.currentattackCooldown <= 0) {
                  if (this.currentattackDelay >= this.attackDelay) {
                     this.currentattackCooldown = this.maxattackCooldown;
                     this.currentattackDelay = 0;
                     this.entity.shoot();
                  } else {
                     if (this.triggerAnim3 && this.currentattackDelay == 0) {
                        this.entity.triggerAnimation(-3);
                     }

                     this.currentattackDelay++;
                  }
               }
            } else if (this.currentattackDelay > 0) {
               this.currentattackDelay--;
            }
         }
      }
   }
}
