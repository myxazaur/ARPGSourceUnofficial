package com.vivern.arpg.mobs;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.SuperKnockback;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityAIFlyingAttack extends EntityAIBase {
   public EntityCreature entity;
   public Vec3d vec;
   public int attackCTimer;
   public float vampireAmount;
   public boolean vampire;

   public EntityAIFlyingAttack(EntityCreature entity, float vampireAmount) {
      this.entity = entity;
      this.vec = entity.getPositionVector();
      this.vampireAmount = vampireAmount;
      if (vampireAmount > 0.0F) {
         this.vampire = true;
      } else {
         this.vampire = false;
      }
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      if (attackTarg != null) {
         this.entity.getLookHelper().setLookPositionWithEntity(attackTarg, 40.0F, 40.0F);
         if (Math.sin(this.entity.ticksExisted / 20.0) < 0.85) {
            if (this.entity.ticksExisted % 25 == 0) {
               this.vec = GetMOP.findRandPosNearEntity(attackTarg, 3.0F);
            }

            if (this.entity.getDistance(attackTarg) > 6.0F) {
               this.entity.motionX /= 2.0;
               this.entity.motionY /= 2.0;
               this.entity.motionZ /= 2.0;
               SuperKnockback.applyMove(
                  this.entity, -0.3F, attackTarg.posX, attackTarg.posY + attackTarg.height / 2.0F, attackTarg.posZ
               );
            } else {
               this.entity.motionX /= 2.0;
               this.entity.motionY /= 2.0;
               this.entity.motionZ /= 2.0;
               SuperKnockback.applyMove(
                  this.entity, 0.4F, attackTarg.posX, attackTarg.posY + attackTarg.height / 2.0F, attackTarg.posZ
               );
            }

            this.entity.getLookHelper().setLookPositionWithEntity(attackTarg, 40.0F, 40.0F);
            this.entity.motionX /= 2.0;
            this.entity.motionY /= 2.0;
            this.entity.motionZ /= 2.0;
            SuperKnockback.applyMove(this.entity, -0.3F, this.vec.x, this.vec.y, this.vec.z);
         } else {
            SuperKnockback.applyMove(
               this.entity, -0.3F, attackTarg.posX, attackTarg.posY + attackTarg.height / 2.0F, attackTarg.posZ
            );
         }

         if (this.attackCTimer > 0) {
            this.attackCTimer--;
         }

         if (this.entity.ticksExisted % 2 == 0 && !this.entity.world.isRemote) {
            AxisAlignedBB axisalignedbb = this.entity.getEntityBoundingBox();
            List<EntityLivingBase> list = this.entity.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (entitylivingbase == attackTarg && this.attackCTimer < 1) {
                     this.attackCTimer = 16;
                     IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                     double baseValue = entityAttribute.getBaseValue();
                     entityAttribute.setBaseValue(1.0);
                     this.entity.attackEntityAsMob(attackTarg);
                     entitylivingbase.hurtResistantTime = 0;
                     entityAttribute.setBaseValue(baseValue);
                     SuperKnockback.applyKnockback(entitylivingbase, 1.8F, this.entity.posX, this.entity.posY, this.entity.posZ);
                     if (this.vampire) {
                        this.entity.heal(this.vampireAmount);
                     }
                  }
               }
            }
         }
      }

      this.entity.rotationYaw = (float)(MathHelper.atan2(this.entity.motionX, this.entity.motionZ) * (180.0 / Math.PI));
   }
}
