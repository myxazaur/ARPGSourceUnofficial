package com.vivern.arpg.mobs;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.SuperKnockback;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityAIFlyingKeepDistToTarget extends EntityAIBase {
   public boolean enable = true;
   public EntityCreature entity;
   public Vec3d followPoint;
   public int frequency = 30;
   public float radius = 5.0F;
   public float radiusVar = 1.0F;
   public float nearPointRadius = 3.0F;
   public float speed = 0.15F;
   public float randradius = 0.0F;
   public float yOffset = 0.0F;
   public float friction = 2.0F;

   public EntityAIFlyingKeepDistToTarget(
      EntityCreature entity, int frequency, float radius, float speed, float radiusVar, float nearPointRadius, float yOffset, float friction
   ) {
      this.entity = entity;
      this.followPoint = entity.getPositionVector();
      this.frequency = frequency;
      this.radius = radius;
      this.speed = speed;
      this.nearPointRadius = nearPointRadius;
      this.radiusVar = radiusVar;
      this.yOffset = yOffset;
      this.friction = friction;
   }

   public EntityAIFlyingKeepDistToTarget(EntityCreature entity) {
      this.entity = entity;
      this.followPoint = entity.getPositionVector();
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null && this.enable;
   }

   public boolean shouldContinueExecuting() {
      return super.shouldContinueExecuting() && this.enable;
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      if (attackTarg != null) {
         if (this.entity.ticksExisted % this.frequency == 0) {
            this.randradius = this.radius + this.entity.getRNG().nextFloat() * this.radiusVar;
            this.followPoint = GetMOP.findRandPosNearPoint(
               attackTarg.posX, attackTarg.posY + this.yOffset, attackTarg.posZ, this.nearPointRadius
            );
         }

         if (this.entity.getDistance(attackTarg) > this.randradius) {
            this.entity.motionX = this.entity.motionX / this.friction;
            this.entity.motionY = this.entity.motionY / this.friction;
            this.entity.motionZ = this.entity.motionZ / this.friction;
            SuperKnockback.applyMove(this.entity, -this.speed, attackTarg.posX, attackTarg.posY, attackTarg.posZ);
         } else {
            this.entity.motionX = this.entity.motionX / this.friction;
            this.entity.motionY = this.entity.motionY / this.friction;
            this.entity.motionZ = this.entity.motionZ / this.friction;
            SuperKnockback.applyMove(this.entity, this.speed, attackTarg.posX, attackTarg.posY, attackTarg.posZ);
         }

         this.entity.getLookHelper().setLookPositionWithEntity(attackTarg, 40.0F, 40.0F);
         if (this.followPoint != null) {
            this.entity.motionX = this.entity.motionX / this.friction;
            this.entity.motionY = this.entity.motionY / this.friction;
            this.entity.motionZ = this.entity.motionZ / this.friction;
            SuperKnockback.applyMove(this.entity, -this.speed, this.followPoint.x, this.followPoint.y, this.followPoint.z);
         }

         Vec3d vecr = attackTarg.getPositionVector().subtract(this.entity.getPositionVector());
         this.entity.rotationYaw = (float)(MathHelper.atan2(vecr.x, vecr.z) * (180.0 / Math.PI));
      }
   }
}
