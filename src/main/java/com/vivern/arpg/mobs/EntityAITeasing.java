package com.vivern.arpg.mobs;

import com.vivern.arpg.main.Weapons;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAITeasing<T extends EntityLiving> extends EntityAIBase {
   public AbstractMob entity;
   public Class<? extends T> classTarget;
   public double raduis = 16.0;
   public double distToAttackTargetSq = 16.0;
   public int probablity = 30;
   public T targetEntity;
   public boolean triggerAttackAnimation = true;
   public int pathDelay = 0;
   public int noPathCount = 0;

   public EntityAITeasing(AbstractMob entity, Class<? extends T> classTarget, boolean triggerAttackAnimation, double distToAttackTarget) {
      this.entity = entity;
      this.classTarget = classTarget;
      this.triggerAttackAnimation = triggerAttackAnimation;
      this.distToAttackTargetSq = distToAttackTarget * distToAttackTarget;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public boolean shouldContinueExecuting() {
      return this.entity.getAttackTarget() != null && this.entity.getAttackTarget().isEntityAlive();
   }

   public void resetTask() {
      this.noPathCount = 0;
      this.targetEntity = null;
      super.resetTask();
   }

   public void updateTask() {
      if (this.entity.getAttackTarget() != null) {
         if (this.targetEntity == null) {
            if (this.entity.ticksExisted % this.probablity == 0) {
               AxisAlignedBB aabb = new AxisAlignedBB(
                  this.entity.posX - this.raduis,
                  this.entity.posY - this.raduis,
                  this.entity.posZ - this.raduis,
                  this.entity.posX + this.raduis,
                  this.entity.posY + this.raduis,
                  this.entity.posZ + this.raduis
               );

               for (T targ : this.entity.world.getEntitiesWithinAABB(this.classTarget, aabb)) {
                  if (targ.getAttackTarget() != this.entity.getAttackTarget()
                     && targ.getDistanceSq(this.entity.getAttackTarget()) <= this.distToAttackTargetSq
                     && this.entity.getNavigator().tryMoveToEntityLiving(targ, 1.0)) {
                     this.targetEntity = targ;
                     EntityAIRush.setEnable(this.entity, false);
                     this.noPathCount = 0;
                     break;
                  }
               }
            }
         } else {
            if (this.targetEntity != null) {
               this.checkAndPerformAttack(this.targetEntity, this.entity.getDistanceSq(this.targetEntity));
            }

            if (--this.pathDelay <= 0) {
               if (this.targetEntity != null) {
                  this.entity.getNavigator().tryMoveToEntityLiving(this.targetEntity, 1.0);
               }

               this.pathDelay = 15;
               if (this.entity.getNavigator().noPath()) {
                  this.noPathCount++;
                  if (this.noPathCount > 3) {
                     EntityAIRush.setEnable(this.entity, true);
                     this.targetEntity = null;
                     this.noPathCount = 0;
                  }
               }
            }
         }
      }
   }

   public void checkAndPerformAttack(EntityLivingBase enemy, double distToEnemySqr) {
      double d0 = this.entity.width * 2.0F * this.entity.width * 2.0F + enemy.width;
      if (distToEnemySqr <= d0) {
         this.entity.swingArm(EnumHand.MAIN_HAND);
         Weapons.dealDamage(
            null,
            1.0F,
            this.entity.getAttackTarget(),
            enemy,
            true,
            0.2F,
            this.entity.posX,
            this.entity.posY - 0.4,
            this.entity.posZ
         );
         EntityAIRush.setEnable(this.entity, true);
         if (this.triggerAttackAnimation) {
            this.entity.triggerAnimation(-1);
         }

         this.targetEntity = null;
      }
   }
}
