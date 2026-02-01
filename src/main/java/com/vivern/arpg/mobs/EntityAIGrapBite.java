package com.vivern.arpg.mobs;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.network.PacketEntityPositionToClients;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;

public class EntityAIGrapBite extends AbstractMob.AbstractAI {
   public AbstractMob entity;
   public int maxCooldown = 30;
   public float radiusHigherSq = 0.0F;
   public float radiusHigher = 0.0F;
   public float radiusLowerSq = 0.0F;
   public float power = 0.0F;
   public int minGrapTime = 0;
   public float damageToFree = 0.2F;
   public boolean onlyOthersCanFree;
   public float grapVectorLength = 1.0F;
   public boolean useKnockbackResistance;
   public boolean invertedPitchYaw;
   public Entity grapped = null;
   public int attackTimer = 0;
   public float currentDamage = 0.0F;
   public int grapTime = 0;
   public int frequency = 5;
   public boolean doAttacks;
   public boolean grabAllOpponents;
   public boolean canDismountRiders = true;

   public EntityAIGrapBite(
      AbstractMob entity,
      boolean doAttacks,
      int maxCooldown,
      float radiusHigher,
      float radiusLower,
      float power,
      int minGrapTime,
      float damageToFree,
      boolean onlyOthersCanFree,
      float grapVectorLength,
      boolean useKnockbackResistance,
      boolean invertedPitchYaw,
      boolean grabAllOpponents
   ) {
      this.entity = entity;
      this.maxCooldown = maxCooldown;
      this.radiusHigherSq = radiusHigher * radiusHigher;
      this.radiusHigher = radiusHigher;
      this.radiusLowerSq = radiusLower * radiusLower;
      this.power = power;
      this.minGrapTime = minGrapTime;
      this.damageToFree = damageToFree;
      this.grapVectorLength = grapVectorLength;
      this.onlyOthersCanFree = onlyOthersCanFree;
      this.useKnockbackResistance = useKnockbackResistance;
      this.invertedPitchYaw = invertedPitchYaw;
      this.doAttacks = doAttacks;
      this.grabAllOpponents = grabAllOpponents;
   }

   @Override
   public float onEntityAttacked(DamageSource source, float amount) {
      if (this.onlyOthersCanFree) {
         if (source.getTrueSource() != null && source.getTrueSource() != this.grapped) {
            this.currentDamage += amount;
         }
      } else {
         this.currentDamage += amount;
      }

      return amount;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public Vec3d getGrapPosition() {
      Vec3d lookVec = this.invertedPitchYaw ? GetMOP.PitchYawToVec3d(-this.entity.rotationPitch, -this.entity.rotationYaw) : this.entity.getLook(1.0F);
      return this.grapVectorLength == 1.0F
         ? lookVec.add(this.entity.posX, this.entity.posY, this.entity.posZ)
         : lookVec.scale(this.grapVectorLength).add(this.entity.posX, this.entity.posY, this.entity.posZ);
   }

   public void resetGrab() {
      this.grapped = null;
      this.grapTime = 0;
      this.attackTimer = this.maxCooldown;
      this.currentDamage = 0.0F;
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      this.attackTimer--;
      if (attackTarg != null) {
         if (this.grapped != null) {
            if (!this.grapped.isEntityAlive() || !this.grapped.isAddedToWorld() || this.grapped.dimension != this.entity.dimension) {
               this.resetGrab();
               return;
            }

            this.grapTime++;
            Vec3d posv = this.getGrapPosition();
            this.grapped.motionX *= 0.6;
            this.grapped.motionY *= 0.6;
            this.grapped.motionZ *= 0.6;
            if (this.doAttacks && this.entity.ticksExisted % 20 == 0) {
               this.entity.attackEntityAsMob(this.grapped);
               this.entity.world.setEntityState(this.entity, (byte)15);
            }

            if (this.currentDamage >= this.damageToFree) {
               SuperKnockback.applyMove(this.grapped, this.power, this.entity.posX, this.entity.posY, this.entity.posZ);
               this.grapped.velocityChanged = true;
               this.resetGrab();
               this.entity.world.setEntityState(this.entity, (byte)14);
            } else if (this.grapTime > this.minGrapTime && this.entity.getRNG().nextFloat() < 0.05F) {
               SuperKnockback.applyMove(this.grapped, this.power, this.entity.posX, this.entity.posY, this.entity.posZ);
               this.grapped.velocityChanged = true;
               this.resetGrab();
               this.entity.world.setEntityState(this.entity, (byte)14);
            } else {
               if (this.grapped instanceof EntityPlayer) {
                  this.grapped.setPosition(posv.x, posv.y, posv.z);
                  PacketEntityPositionToClients.send(this.grapped, posv.x, posv.y, posv.z, 64.0);
               } else {
                  this.grapped.setPosition(posv.x, posv.y, posv.z);
               }

               this.grapped.velocityChanged = true;
            }
         } else if (this.attackTimer <= 0 && this.entity.ticksExisted % this.frequency == 0) {
            Vec3d grabPosition = this.getGrapPosition();
            double distSq = grabPosition.squareDistanceTo(attackTarg.posX, attackTarg.posY, attackTarg.posZ);
            if (distSq < this.radiusHigherSq && distSq > this.radiusLowerSq) {
               this.tryGrab(attackTarg);
            } else if (this.grabAllOpponents) {
               for (EntityLivingBase livingBase : GetMOP.getHostilesInAABBto(
                  this.entity.world, grabPosition, this.radiusHigher, this.radiusHigher, this.entity, this.entity
               )) {
                  double distSq2 = grabPosition.squareDistanceTo(livingBase.posX, livingBase.posY, livingBase.posZ);
                  if (distSq2 < this.radiusHigherSq && distSq2 > this.radiusLowerSq && this.tryGrab(livingBase)) {
                     break;
                  }
               }
            }
         }
      }
   }

   public boolean tryGrab(EntityLivingBase attackTarg) {
      boolean ret = false;
      if (attackTarg.isRiding()) {
         if (!this.canDismountRiders) {
            return false;
         }

         attackTarg.dismountRidingEntity();
      }

      for (Entity e : GetMOP.getEntitiesInAABBof(attackTarg.world, attackTarg, 32.0, this.entity)) {
         if (e instanceof EntityLiving) {
            EntityLiving living = (EntityLiving)e;

            for (EntityAITaskEntry taskentry : living.tasks.taskEntries) {
               if (taskentry.action instanceof EntityAIGrapBite) {
                  EntityAIGrapBite aibite = (EntityAIGrapBite)taskentry.action;
                  if (aibite.grapped == attackTarg) {
                     this.attackTimer = this.maxCooldown;
                     return false;
                  }
               }
            }
         }
      }

      if (this.useKnockbackResistance) {
         IAttributeInstance attribute = attackTarg.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
         double knockResist = attribute == null ? 0.0 : attribute.getAttributeValue();
         if (this.entity.width > attackTarg.width && this.entity.height > attackTarg.height) {
            knockResist *= 0.45;
         } else if (this.entity.width + this.entity.height > attackTarg.height + attackTarg.width) {
            knockResist *= 0.65;
         } else if (this.entity.width > attackTarg.width || this.entity.height > attackTarg.height) {
            knockResist *= 0.82;
         }

         if (this.entity.getRNG().nextFloat() < knockResist) {
            SuperKnockback.applyMove(attackTarg, this.power, this.entity.posX, this.entity.posY, this.entity.posZ);
            attackTarg.velocityChanged = true;
         } else {
            this.grapped = attackTarg;
            this.entity.world.setEntityState(this.entity, (byte)15);
            ret = true;
         }

         this.attackTimer = this.maxCooldown;
         this.currentDamage = 0.0F;
         this.grapTime = 0;
      } else {
         this.grapped = attackTarg;
         this.entity.world.setEntityState(this.entity, (byte)15);
         this.attackTimer = this.maxCooldown;
         this.currentDamage = 0.0F;
         this.grapTime = 0;
         ret = true;
      }

      return ret;
   }
}
