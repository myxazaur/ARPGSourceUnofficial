package com.Vivern.Arpg.mobs;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIJellyfishRush extends EntityAIBase {
   public AbstractMob entity;
   public float motionMaxSpeed;
   public float speedIncrease;
   public boolean sendToClients = true;
   public float directionChangeChance;
   public double randomMotionVecX;
   public double randomMotionVecY;
   public double randomMotionVecZ;
   public float randomMotionSpeed;
   public boolean move = false;

   public EntityAIJellyfishRush(AbstractMob entity, float motionMaxSpeed, float speedIncrease, float directionChangeChance) {
      this.entity = entity;
      this.motionMaxSpeed = motionMaxSpeed;
      this.speedIncrease = speedIncrease;
      this.directionChangeChance = directionChangeChance;
      this.createNewVector();
   }

   public boolean shouldExecute() {
      return this.entity.isInWater() && this.entity.getAttackTarget() != null;
   }

   public boolean shouldContinueExecuting() {
      return this.entity.isInWater() && this.entity.getAttackTarget() != null;
   }

   public void updateTask() {
      if (this.move) {
         float speed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
         this.entity.motionX = this.entity.motionX * 0.9 + this.randomMotionVecX * this.randomMotionSpeed * speed;
         this.entity.motionY = this.entity.motionY * 0.9 + this.randomMotionVecY * this.randomMotionSpeed * speed;
         this.entity.motionZ = this.entity.motionZ * 0.9 + this.randomMotionVecZ * this.randomMotionSpeed * speed;
         this.randomMotionSpeed = (float)(this.randomMotionSpeed * 0.9);
         if (this.randomMotionSpeed < 0.05) {
            this.move = false;
            if (this.sendToClients) {
               this.entity.world.setEntityState(this.entity, (byte)28);
            }
         }

         BlockPos poss = new BlockPos(
            this.entity.posX + this.entity.motionX,
            this.entity.posY + this.entity.motionY,
            this.entity.posZ + this.entity.motionZ
         );
         IBlockState st = this.entity.world.getBlockState(poss);
         if (st.getCollisionBoundingBox(this.entity.world, poss) != null || st.getMaterial() != Material.WATER) {
            this.randomMotionSpeed = (float)(this.randomMotionSpeed * 0.2);
            this.createNewVector();
         }
      } else {
         float speedx = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
         this.entity.motionX = this.entity.motionX * 0.9 + this.randomMotionVecX * 0.03 * speedx;
         this.entity.motionY = this.entity.motionY * 0.9 + this.randomMotionVecY * 0.03 * speedx;
         this.entity.motionZ = this.entity.motionZ * 0.9 + this.randomMotionVecZ * 0.03 * speedx;
         if (this.randomMotionSpeed < this.motionMaxSpeed) {
            this.randomMotionSpeed = this.randomMotionSpeed + this.speedIncrease;
            if (this.entity.getRNG().nextFloat() < this.directionChangeChance) {
               this.createNewVector();
            }
         } else {
            this.move = true;
            this.entity.world.setEntityState(this.entity, (byte)29);
         }
      }
   }

   public void createNewVector() {
      if (this.entity.getAttackTarget() != null) {
         double vx = this.entity.getAttackTarget().posX - this.entity.posX;
         double vy = this.entity.getAttackTarget().posY - this.entity.posY;
         double vz = this.entity.getAttackTarget().posZ - this.entity.posZ;
         Vec3d v = new Vec3d(vx, vy, vz);
         v = v.normalize();
         this.randomMotionVecX = (float)v.x;
         this.randomMotionVecY = (float)v.y
            + (this.entity.world.getBlockState(this.entity.getPosition().up(3)).getMaterial() == Material.AIR ? -0.8F : 0.0F);
         this.randomMotionVecZ = (float)v.z;
      }
   }
}
