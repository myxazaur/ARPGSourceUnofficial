package com.Vivern.Arpg.mobs;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityAIJumpingMovement extends EntityAIBase {
   public AbstractMob entity;
   public int frequency = 30;
   public float jumpMoveAdding = 0.0F;
   public int anim = 0;
   public boolean sendAnimation = false;

   public EntityAIJumpingMovement(AbstractMob entity, int frequency, float jumpMoveAdding) {
      this.entity = entity;
      this.frequency = frequency;
      this.jumpMoveAdding = jumpMoveAdding;
   }

   public EntityAIJumpingMovement(AbstractMob entity) {
      this.entity = entity;
   }

   public boolean shouldExecute() {
      return true;
   }

   public EntityAIJumpingMovement setAnimationOnJump(int id) {
      this.sendAnimation = true;
      this.anim = id;
      return this;
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      if (attackTarg != null) {
         this.entity.getNavigator().tryMoveToEntityLiving(attackTarg, this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
      } else if (this.entity.ticksExisted % 80 == 0) {
         Vec3d dd = this.getPosition();
         if (dd != null) {
            this.entity
               .getNavigator()
               .tryMoveToXYZ(
                  dd.x, dd.y, dd.z, this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()
               );
         }
      }

      if (this.entity.ticksExisted % this.frequency == 0 && this.entity.hasPath()) {
         this.entity.getJumpHelper().setJumping();
         if (this.jumpMoveAdding != 0.0F) {
            float rotationYawIn = this.entity.rotationYaw;
            float rotationPitchIn = this.entity.rotationPitch;
            float velocity = this.jumpMoveAdding;
            float x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0))
               * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
            float y = -MathHelper.sin(rotationPitchIn * (float) (Math.PI / 180.0));
            float z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
            float f = MathHelper.sqrt(x * x + y * y + z * z);
            x /= f;
            y /= f;
            z /= f;
            x *= velocity;
            y *= velocity;
            z *= velocity;
            this.entity.motionX = x;
            this.entity.motionY = y;
            this.entity.motionZ = z;
         }

         if (this.sendAnimation) {
            this.entity.triggerAnimation(this.anim);
         }
      }
   }

   @Nullable
   protected Vec3d getPosition() {
      return this.entity.isInWater() ? RandomPositionGenerator.getLandPos(this.entity, 15, 7) : RandomPositionGenerator.getLandPos(this.entity, 10, 7);
   }
}
