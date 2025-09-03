//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAISkeleton extends EntityAIBase {
   public boolean enable = true;
   public final AbstractMob entity;
   public final double moveSpeedAmp;
   public boolean useshoot = true;
   public int deltaRotation = 30;
   public int maxattackCooldown;
   public int currentattackCooldown;
   public final float maxAttackDistancesq;
   public boolean strafingClockwise;
   public boolean strafingBackwards;
   public int strafingTime = -1;
   public int inUse = 0;
   public boolean isHandActive = false;
   public boolean burst = false;
   public int burstMaxCount = 0;
   public int burstHave = 0;
   public int maxnormalShootsToBurst = 0;
   public int normalShootsToBurst = 0;
   public boolean canStop = true;
   public int maxCooldownOnBurst;
   public boolean approach = false;
   public byte forwardSendId = 27;
   public byte backwardSendId = 28;
   public boolean triggerAnim3 = false;
   public int attackDelay = 0;
   public int currentattackDelay = 0;
   public int seeTime;

   public EntityAISkeleton(AbstractMob mob, double moveSpeedAmp, int attackCooldown, float maxAttackDistance, int attackDelay) {
      this.entity = mob;
      this.moveSpeedAmp = moveSpeedAmp;
      this.maxattackCooldown = attackCooldown;
      this.maxAttackDistancesq = maxAttackDistance * maxAttackDistance;
      this.attackDelay = attackDelay;
      this.setMutexBits(3);
   }

   public void setAttackCooldown(int attackCooldown) {
      this.maxattackCooldown = attackCooldown;
   }

   public EntityAISkeleton setApproach() {
      this.approach = true;
      return this;
   }

   public EntityAISkeleton setBurst(boolean burst, int burstMaxCount, int maxnormalShootsToBurst, boolean canStop, int maxCooldownOnBurst) {
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
         boolean cansee = this.entity.getEntitySenses().canSee(entitylivingbase);
         boolean flag1 = this.seeTime > 0;
         if (cansee != flag1) {
            this.seeTime = 0;
         }

         if (cansee) {
            this.seeTime++;
         } else {
            this.seeTime--;
         }

         if (d0 <= this.maxAttackDistancesq && this.seeTime >= 20) {
            this.entity.getNavigator().clearPath();
            this.strafingTime++;
         } else {
            this.entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
            this.strafingTime = -1;
            this.entity.world.setEntityState(this.entity, this.forwardSendId);
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
            if (this.approach) {
               if (d0 > this.maxAttackDistancesq * 0.25F) {
                  this.strafingBackwards = false;
               } else if (d0 < this.maxAttackDistancesq * 0.1F) {
                  this.strafingBackwards = true;
               }
            } else if (d0 > this.maxAttackDistancesq * 0.75F) {
               this.strafingBackwards = false;
            } else if (d0 < this.maxAttackDistancesq * 0.25F) {
               this.strafingBackwards = true;
            }

            this.entity.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
            this.entity.faceEntity(entitylivingbase, this.deltaRotation, this.deltaRotation);
            this.entity.world.setEntityState(this.entity, this.strafingBackwards ? this.backwardSendId : this.forwardSendId);
         } else {
            this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, this.deltaRotation, this.deltaRotation);
         }

         if (this.useshoot) {
            if (d0 < this.maxAttackDistancesq && cansee || !this.canStop && this.burstHave > 0) {
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
