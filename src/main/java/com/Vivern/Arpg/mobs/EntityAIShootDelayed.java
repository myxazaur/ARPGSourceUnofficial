//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIShootDelayed extends EntityAIBase {
   public boolean enable = true;
   public final AbstractMob entity;
   public int maxattackCooldown;
   public int currentattackCooldown;
   public final float maxAttackDistancesq;
   public boolean burst = false;
   public int burstMaxCount = 0;
   public int burstHave = 0;
   public int maxnormalShootsToBurst = 0;
   public int normalShootsToBurst = 0;
   public boolean canStop = true;
   public int maxCooldownOnBurst;
   public int deltaRotation;
   public boolean triggerAnim3 = false;
   public int attackDelay = 0;
   public int currentattackDelay = 0;

   public EntityAIShootDelayed(AbstractMob mob, int attackCooldown, float maxShootDistance, int attackDelay, boolean triggerAnim3) {
      this.entity = mob;
      this.maxattackCooldown = attackCooldown;
      this.maxAttackDistancesq = maxShootDistance * maxShootDistance;
      this.attackDelay = attackDelay;
      this.triggerAnim3 = triggerAnim3;
   }

   public EntityAIShootDelayed(AbstractMob mob, int attackCooldown, float maxShootDistance, int attackDelay, boolean triggerAnim3, int deltaRotation) {
      this.entity = mob;
      this.maxattackCooldown = attackCooldown;
      this.maxAttackDistancesq = maxShootDistance * maxShootDistance;
      this.attackDelay = attackDelay;
      this.triggerAnim3 = triggerAnim3;
      this.deltaRotation = deltaRotation;
   }

   public EntityAIShootDelayed setBurst(boolean burst, int burstMaxCount, int maxnormalShootsToBurst, boolean canStop, int maxCooldownOnBurst) {
      this.burst = burst;
      this.burstMaxCount = burstMaxCount;
      this.maxnormalShootsToBurst = maxnormalShootsToBurst;
      this.canStop = canStop;
      this.maxCooldownOnBurst = maxCooldownOnBurst;
      return this;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null && this.enable;
   }

   public boolean shouldContinueExecuting() {
      return this.shouldExecute();
   }

   public void startExecuting() {
      super.startExecuting();
   }

   public void updateTask() {
      EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
      if (this.currentattackCooldown > 0) {
         this.currentattackCooldown--;
      }

      if (entitylivingbase != null) {
         double d0 = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
         boolean cansee = this.entity.getEntitySenses().canSee(entitylivingbase);
         if (cansee) {
            this.entity.faceEntity(entitylivingbase, this.deltaRotation, this.deltaRotation);
         }

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
