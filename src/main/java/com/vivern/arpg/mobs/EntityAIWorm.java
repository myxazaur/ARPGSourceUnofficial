package com.vivern.arpg.mobs;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.SuperKnockback;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityAIWorm extends EntityAIBase {
   public AbstractMob entity;
   public Vec3d vec;
   public int delay = 5;
   public int frequency = 30;
   public float radius = 15.0F;
   public float speed = 0.05F;
   public float radiusVar = 1.0F;
   public float yOffset = 0.0F;
   public float resetDistance = 1.0F;
   public float attackChance = 0.2F;
   public float friction = 1.0F;
   public boolean checkInBlock = false;
   public int solidblockChecks = 10;
   public Vec3d lastSolidBlock = null;
   public boolean start = false;
   public Vec3d homePosition;
   public List<AbstractMob> segments;
   public float gravityOnFall = 0.08F;

   public EntityAIWorm(
      AbstractMob entity,
      int frequency,
      float radius,
      float radiusVar,
      float yOffset,
      float resetDistance,
      float attackChance,
      float friction,
      boolean checkInBlock,
      int solidblockChecks
   ) {
      this.entity = entity;
      this.vec = entity.getPositionVector();
      this.frequency = frequency;
      this.radius = radius;
      this.speed = (float)entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
      this.radiusVar = radiusVar;
      this.yOffset = yOffset;
      this.resetDistance = resetDistance;
      this.attackChance = attackChance;
      this.friction = friction;
      this.checkInBlock = checkInBlock;
      this.solidblockChecks = solidblockChecks;
   }

   public List<AbstractMob> getSegments() {
      double radiu = 48.0;
      Vec3d cenVec3d = GetMOP.entityCenterPos(this.entity);
      AxisAlignedBB bb = new AxisAlignedBB(
         cenVec3d.x - radiu,
         cenVec3d.y - radiu,
         cenVec3d.z - radiu,
         cenVec3d.x + radiu,
         cenVec3d.y + radiu,
         cenVec3d.z + radiu
      );
      List<AbstractMob> list = this.entity.world.getEntitiesWithinAABB(AbstractMob.class, bb);
      List<AbstractMob> list2 = new ArrayList<>();

      for (AbstractMob mobb : list) {
         if (mobb.getOwnerIfSegment() == this.entity) {
            list2.add(mobb);
         }
      }

      return list2;
   }

   public EntityAIWorm(AbstractMob entity) {
      this.entity = entity;
      this.vec = entity.getPositionVector();
   }

   public void startExecuting() {
      this.homePosition = this.entity.getPositionVector();
      super.startExecuting();
   }

   public void resetTask() {
      this.homePosition = null;
      super.resetTask();
   }

   public boolean shouldExecute() {
      return !this.entity.isSubMob;
   }

   public void updateTask() {
      Vec3d followPoint = this.entity.getAttackTarget() != null ? GetMOP.entityCenterPos(this.entity.getAttackTarget()) : this.homePosition;
      if (followPoint != null) {
         if (this.delay <= 0) {
            if (this.entity.getRNG().nextFloat() > this.attackChance) {
               if (this.checkInBlock) {
                  boolean suc = false;

                  for (int i = 0; i < this.solidblockChecks; i++) {
                     this.vec = GetMOP.findRandPosNearPoint(
                        followPoint.x,
                        followPoint.y + this.yOffset,
                        followPoint.z,
                        this.radius + this.entity.getRNG().nextFloat() * this.radiusVar
                     );
                     if (!this.entity.world.isAirBlock(new BlockPos(this.vec))) {
                        suc = true;
                        this.lastSolidBlock = this.vec;
                        this.start = true;
                        this.speed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
                        break;
                     }
                  }

                  if (!suc) {
                     this.vec = this.lastSolidBlock == null
                        ? new Vec3d(
                           this.entity.posX, MathHelper.clamp(this.entity.posY - 40.0, 1.0, 255.0), this.entity.posZ
                        )
                        : this.lastSolidBlock;
                     this.speed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
                  }
               } else {
                  this.vec = GetMOP.findRandPosNearPoint(
                     followPoint.x,
                     followPoint.y + this.yOffset,
                     followPoint.z,
                     this.radius + this.entity.getRNG().nextFloat() * this.radiusVar
                  );
                  this.start = true;
                  this.speed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
               }
            } else {
               this.vec = followPoint;
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
         if (this.start) {
            SuperKnockback.applyMove(this.entity, -this.speed, this.vec.x, this.vec.y, this.vec.z);
         }
      }

      if (this.checkInBlock) {
         if (this.segments == null || this.entity.ticksExisted % 150 == 0) {
            this.segments = this.getSegments();
         }

         boolean inground = false;

         for (AbstractMob mob : this.segments) {
            Vec3d center = GetMOP.entityCenterPos(mob);
            BlockPos poss = new BlockPos(center);
            IBlockState state = this.entity.world.getBlockState(poss);
            AxisAlignedBB aabb = state.getCollisionBoundingBox(this.entity.world, poss);
            if (aabb != null && aabb.offset(poss).contains(center)) {
               inground = true;
               break;
            }
         }

         if (!inground) {
            this.entity.motionY = this.entity.motionY - this.gravityOnFall;
         }
      }
   }
}
