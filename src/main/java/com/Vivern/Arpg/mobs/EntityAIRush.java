//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIRush extends EntityAIBase {
   public boolean enable = true;
   World world;
   public AbstractMob attacker;
   public int attackTick;
   double speedTowardsTarget;
   boolean longMemory;
   Path path;
   public int delayCounter;
   public double targetX;
   public double targetY;
   public double targetZ;
   public final int attackInterval = 20;
   public int failedPathFindingPenalty = 0;
   public boolean canPenalize = false;
   public boolean triggerAttackAnimation = false;
   public boolean attack = true;
   public float lowerDist = -2.0F;
   public float higherDist = -2.0F;

   public EntityAIRush(AbstractMob creature, boolean useLongMemory, boolean attack, boolean triggerAttackAnimation) {
      this.attacker = creature;
      this.world = creature.world;
      this.speedTowardsTarget = 1.0;
      this.longMemory = useLongMemory;
      this.triggerAttackAnimation = triggerAttackAnimation;
      this.attack = attack;
      this.setMutexBits(3);
   }

   public EntityAIRush configureDistance(float lowerDist, float higherDist) {
      this.lowerDist = lowerDist;
      this.higherDist = higherDist;
      return this;
   }

   public static boolean setEnable(EntityCreature entity, boolean to) {
      for (EntityAITaskEntry entry : entity.tasks.taskEntries) {
         if (entry.action instanceof EntityAIRush) {
            EntityAIRush rush = (EntityAIRush)entry.action;
            boolean en = rush.enable;
            rush.enable = to;
            return en;
         }
      }

      return false;
   }

   public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
      if (entitylivingbase == null || !this.enable) {
         return false;
      } else if (!entitylivingbase.isEntityAlive()) {
         return false;
      } else {
         if (this.lowerDist > -1.0F) {
            double dist = this.attacker.getDistance(entitylivingbase);
            if (!(dist > this.lowerDist) || !(dist < this.higherDist)) {
               return false;
            }
         }

         if (!this.canPenalize) {
            this.path = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
            return this.path != null
               ? true
               : this.getAttackReachSqr(entitylivingbase)
                  >= this.attacker
                     .getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
         } else if (--this.delayCounter <= 0) {
            this.path = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
            this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
            return this.path != null;
         } else {
            return true;
         }
      }
   }

   public boolean shouldContinueExecuting() {
      EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
      if (entitylivingbase == null || !this.enable) {
         return false;
      } else if (!entitylivingbase.isEntityAlive()) {
         return false;
      } else if (!this.longMemory) {
         return !this.attacker.getNavigator().noPath();
      } else {
         return !this.attacker.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase))
            ? false
            : !(entitylivingbase instanceof EntityPlayer)
               || !((EntityPlayer)entitylivingbase).isSpectator() && !((EntityPlayer)entitylivingbase).isCreative();
      }
   }

   public void startExecuting() {
      this.attacker.getNavigator().setPath(this.path, this.speedTowardsTarget);
      this.delayCounter = 0;
   }

   public void resetTask() {
      EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
      if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer)entitylivingbase).isSpectator() || ((EntityPlayer)entitylivingbase).isCreative())) {
         this.attacker.setAttackTarget((EntityLivingBase)null);
      }

      this.attacker.getNavigator().clearPath();
   }

   public void updateTask() {
      if (this.enable) {
         EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
         this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
         double d0 = this.attacker
            .getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
         this.delayCounter--;
         if ((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase))
            && this.delayCounter <= 0
            && (
               this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0
                  || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0
                  || this.attacker.getRNG().nextFloat() < 0.05F
            )) {
            this.targetX = entitylivingbase.posX;
            this.targetY = entitylivingbase.getEntityBoundingBox().minY;
            this.targetZ = entitylivingbase.posZ;
            this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
            if (this.canPenalize) {
               this.delayCounter = this.delayCounter + this.failedPathFindingPenalty;
               if (this.attacker.getNavigator().getPath() != null) {
                  PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
                  if (finalPathPoint != null
                     && entitylivingbase.getDistanceSq(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1.0) {
                     this.failedPathFindingPenalty = 0;
                  } else {
                     this.failedPathFindingPenalty += 10;
                  }
               } else {
                  this.failedPathFindingPenalty += 10;
               }
            }

            if (d0 > 1024.0) {
               this.delayCounter += 10;
            } else if (d0 > 256.0) {
               this.delayCounter += 5;
            }

            if (!this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget)) {
               this.delayCounter += 15;
            }
         }

         this.attackTick = Math.max(this.attackTick - 1, 0);
         if (this.attack) {
            this.checkAndPerformAttack(entitylivingbase, d0);
         }
      }
   }

   protected void checkAndPerformAttack(EntityLivingBase enemy, double distToEnemySqr) {
      double d0 = this.getAttackReachSqr(enemy);
      if (distToEnemySqr <= d0 && this.attackTick <= 0) {
         this.attackTick = 20;
         this.attacker.swingArm(EnumHand.MAIN_HAND);
         this.attacker.attackEntityAsMob(enemy);
         if (this.triggerAttackAnimation) {
            this.attacker.triggerAnimation(-1);
         }
      }
   }

   protected double getAttackReachSqr(EntityLivingBase attackTarget) {
      return this.attacker.width * 2.0F * this.attacker.width * 2.0F + attackTarget.width;
   }
}
