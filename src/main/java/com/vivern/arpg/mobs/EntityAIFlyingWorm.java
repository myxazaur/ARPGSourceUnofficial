package com.vivern.arpg.mobs;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.SuperKnockback;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class EntityAIFlyingWorm extends EntityAIBase {
   public EntityCreature entity;
   public Vec3d vec;
   public int delay = 30;
   public int frequency = 30;
   public float radius = 15.0F;
   public float speed = 0.05F;
   public float radiusVar = 1.0F;
   public float yOffset = 0.0F;
   public float resetDistance = 1.0F;
   public float attackChance = 0.2F;
   public float friction = 1.0F;
   public boolean checkInBlock = false;

   public EntityAIFlyingWorm(
      EntityCreature entity,
      int frequency,
      float radius,
      float speed,
      float radiusVar,
      float yOffset,
      float resetDistance,
      float attackChance,
      float friction,
      boolean checkInBlock
   ) {
      this.entity = entity;
      this.vec = entity.getPositionVector();
      this.frequency = frequency;
      this.radius = radius;
      this.speed = speed;
      this.radiusVar = radiusVar;
      this.yOffset = yOffset;
      this.resetDistance = resetDistance;
      this.delay = frequency;
      this.attackChance = attackChance;
      this.friction = friction;
      this.checkInBlock = checkInBlock;
   }

   public EntityAIFlyingWorm(EntityCreature entity) {
      this.entity = entity;
      this.vec = entity.getPositionVector();
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null
         ? this.entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue() > this.entity.getDistance(this.entity.getAttackTarget())
         : false;
   }

   public void updateTask() {
      if (this.entity.getAttackTarget() != null) {
         if (this.delay <= 0) {
            if (this.entity.getRNG().nextFloat() > this.attackChance) {
               if (this.yOffset == 0.0F) {
                  this.vec = GetMOP.findRandPosNearEntity(this.entity.getAttackTarget(), this.radius + this.entity.getRNG().nextFloat() * this.radiusVar);
                  if (this.checkInBlock) {
                     double radr = 0.1;
                     AxisAlignedBB aabb = new AxisAlignedBB(
                        this.vec.x - radr,
                        this.vec.y - radr,
                        this.vec.z - radr,
                        this.vec.x + radr,
                        this.vec.y + radr,
                        this.vec.z + radr
                     );
                     if (this.entity.world.collidesWithAnyBlock(aabb)) {
                        this.delay = 0;
                     }
                  }
               } else {
                  this.vec = GetMOP.findRandPosNearPoint(
                     this.entity.getAttackTarget().posX,
                     this.entity.getAttackTarget().posY + this.yOffset,
                     this.entity.getAttackTarget().posZ,
                     this.radius + this.entity.getRNG().nextFloat() * this.radiusVar
                  );
                  if (this.checkInBlock) {
                     double radr = 0.1;
                     AxisAlignedBB aabb = new AxisAlignedBB(
                        this.vec.x - radr,
                        this.vec.y - radr,
                        this.vec.z - radr,
                        this.vec.x + radr,
                        this.vec.y + radr,
                        this.vec.z + radr
                     );
                     if (this.entity.world.collidesWithAnyBlock(aabb)) {
                        this.delay = 0;
                     }
                  }
               }
            } else {
               this.vec = this.entity.getAttackTarget().getPositionVector();
            }

            this.delay = this.frequency;
         }

         this.delay--;
         if (this.entity.getDistance(this.vec.x, this.vec.y, this.vec.z) < this.resetDistance) {
            this.delay = 0;
         }

         this.entity.motionX = this.entity.motionX / this.friction;
         this.entity.motionY = this.entity.motionY / this.friction;
         this.entity.motionZ = this.entity.motionZ / this.friction;
         SuperKnockback.applyMove(
            this.entity, -this.speed, this.vec.x, this.vec.y + this.entity.getAttackTarget().height / 2.0F, this.vec.z
         );
      }
   }
}
