//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIRandomSwim extends EntityAIBase {
   public AbstractMob entity;
   public float motionMaxSpeed;
   public float speedIncrease;
   public boolean sendToClients = true;
   public float directionChangeChance;
   boolean isIdle;
   public float randomMotionVecX;
   public float randomMotionVecY;
   public float randomMotionVecZ;
   public float randomMotionSpeed;
   public boolean move = false;
   public float motionSlowdown = 0.9F;
   public boolean advancedCollisionCheck = false;

   public EntityAIRandomSwim(AbstractMob entity, float motionMaxSpeed, float speedIncrease, float directionChangeChance, boolean isIdle) {
      this.entity = entity;
      this.motionMaxSpeed = motionMaxSpeed;
      this.speedIncrease = speedIncrease;
      this.directionChangeChance = directionChangeChance;
      this.isIdle = isIdle;
      this.createNewVector();
   }

   public boolean shouldExecute() {
      return this.entity.isInWater() && (!this.isIdle || this.entity.getAttackTarget() == null);
   }

   public boolean shouldContinueExecuting() {
      return this.entity.isInWater() && (!this.isIdle || this.entity.getAttackTarget() == null);
   }

   public void updateTask() {
      if (this.move) {
         float speed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
         this.entity.motionX = this.entity.motionX * 0.9 + this.randomMotionVecX * this.randomMotionSpeed * speed;
         this.entity.motionY = this.entity.motionY * 0.9 + this.randomMotionVecY * this.randomMotionSpeed * speed;
         this.entity.motionZ = this.entity.motionZ * 0.9 + this.randomMotionVecZ * this.randomMotionSpeed * speed;
         this.randomMotionSpeed = this.randomMotionSpeed * this.motionSlowdown;
         if (this.randomMotionSpeed < 0.05) {
            this.move = false;
            if (this.sendToClients) {
               this.entity.world.setEntityState(this.entity, (byte)28);
            }
         }

         if (this.advancedCollisionCheck) {
            AxisAlignedBB aabb = this.entity.getEntityBoundingBox().offset(this.entity.motionX, this.entity.motionY, this.entity.motionZ);

            for (BlockPos poss : GetMOP.getBlockPosesCollidesAABB(this.entity.world, aabb, false)) {
               IBlockState st = this.entity.world.getBlockState(poss);
               if (st.getCollisionBoundingBox(this.entity.world, poss) != null || st.getMaterial() != Material.WATER) {
                  this.randomMotionSpeed = (float)(this.randomMotionSpeed * 0.2);
                  this.createNewVector();
                  break;
               }
            }
         } else {
            BlockPos possx = new BlockPos(
               this.entity.posX + this.entity.motionX,
               this.entity.posY + this.entity.motionY,
               this.entity.posZ + this.entity.motionZ
            );
            IBlockState st = this.entity.world.getBlockState(possx);
            if (st.getCollisionBoundingBox(this.entity.world, possx) != null || st.getMaterial() != Material.WATER) {
               this.randomMotionSpeed = (float)(this.randomMotionSpeed * 0.2);
               this.createNewVector();
            }
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
      Vec3d targ = new Vec3d(this.entity.getRNG().nextGaussian(), this.entity.getRNG().nextGaussian(), this.entity.getRNG().nextGaussian());
      targ = targ.normalize();
      this.randomMotionVecX = (float)targ.x;
      this.randomMotionVecY = (float)targ.y
         + (this.entity.world.getBlockState(this.entity.getPosition().up(3)).getMaterial() == Material.AIR ? -0.8F : 0.0F);
      this.randomMotionVecZ = (float)targ.z;
   }
}
