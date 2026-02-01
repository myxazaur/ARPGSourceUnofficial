package com.vivern.arpg.mobs;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

public class EntityAIShootAndSwim extends EntityAIBase {
   public boolean enable = true;
   public AbstractMob entity;
   public int attackCooldown = 30;
   public float maxAttackDistanceSq = 0.0F;
   public int stayTime;
   public int attackDelay;
   public int attackTimer = 0;
   public int stayTimer = 0;
   public int attackDelayTimer = 0;
   public int moveToTimer = 0;
   public int randomMoveDelay = 0;
   public boolean sendStatusOnReadyShoot = true;

   public EntityAIShootAndSwim(AbstractMob entity, int attackCooldown, float maxAttackDistance, int stayTime, int attackDelay, int randomMoveDelay) {
      this.entity = entity;
      this.attackCooldown = attackCooldown;
      this.maxAttackDistanceSq = maxAttackDistance * maxAttackDistance;
      this.stayTime = stayTime;
      this.attackDelay = attackDelay;
      this.randomMoveDelay = randomMoveDelay;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null && this.enable;
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      if (attackTarg != null && this.enable) {
         if (this.attackTimer <= 0) {
            if (this.moveToTimer > 0) {
               this.moveToTimer--;
               this.entity.getNavigator().tryMoveToEntityLiving(attackTarg, 1.0);
            } else if (this.entity.getDistanceSq(attackTarg) < this.maxAttackDistanceSq && this.entity.getEntitySenses().canSee(attackTarg)) {
               this.entity.getNavigator().clearPath();
               if (this.attackDelayTimer == 0 && this.sendStatusOnReadyShoot) {
                  this.entity.world.setEntityState(this.entity, (byte)15);
               }

               this.attackDelayTimer++;
               this.entity.faceEntity(attackTarg, 30.0F, 90.0F);
               this.entity.getLookHelper().setLookPositionWithEntity(attackTarg, 30.0F, 90.0F);
               if (this.attackDelayTimer > this.attackDelay) {
                  this.entity.shoot();
                  this.stayTimer = this.stayTime;
                  this.attackDelayTimer = 0;
                  this.attackTimer = this.attackCooldown;
               }
            } else {
               this.moveToTimer = 30;
            }
         } else if (this.stayTimer > 0) {
            this.stayTimer--;
            this.attackTimer--;
         } else {
            this.attackTimer--;
            double fr = this.entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue() * 0.85;
            double frSq = fr * fr;
            if (this.entity.getDistanceSq(attackTarg) < frSq) {
               Vec3d pos = this.getPosition();
               if (pos != null) {
                  this.entity.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, 1.0);
                  this.stayTimer = this.randomMoveDelay;
               }
            } else {
               this.entity.getNavigator().tryMoveToEntityLiving(attackTarg, 1.0);
               this.stayTimer = 10;
            }
         }
      }
   }

   @Nullable
   protected Vec3d getPosition() {
      return RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
   }
}
