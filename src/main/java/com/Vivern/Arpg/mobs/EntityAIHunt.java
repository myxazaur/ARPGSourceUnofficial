//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIHunt extends EntityAIBase {
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
   public int timeToRush = 0;
   public int shootCooldown = 0;
   public int shootTimer = 0;
   public Predicate<Block> blocks;
   public int findDelay = 0;
   public int findCount = 0;
   public BlockPos lastPos = null;
   public Random rand;
   public int findDistance = 8;

   public EntityAIHunt(AbstractMob creature, boolean useLongMemory, boolean attack, boolean triggerAttackAnimation, int shootCooldown, Predicate<Block> blocks) {
      this.attacker = creature;
      this.world = creature.world;
      this.speedTowardsTarget = 1.0;
      this.longMemory = useLongMemory;
      this.triggerAttackAnimation = triggerAttackAnimation;
      this.attack = attack;
      this.shootCooldown = shootCooldown;
      this.blocks = blocks;
      this.rand = creature.getRNG();
      this.setMutexBits(3);
   }

   public EntityAIHunt configureDistance(float lowerDist, float higherDist) {
      this.lowerDist = lowerDist;
      this.higherDist = higherDist;
      return this;
   }

   public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
      if (entitylivingbase == null) {
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

         double distsq = this.attacker
            .getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
         return this.attacker.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue() >= distsq
            || this.getAttackReachSqr(entitylivingbase) >= distsq;
      }
   }

   public boolean shouldContinueExecuting() {
      EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
      if (entitylivingbase == null) {
         return false;
      } else if (!entitylivingbase.isEntityAlive()) {
         return false;
      } else {
         return !this.attacker.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase))
            ? false
            : !(entitylivingbase instanceof EntityPlayer)
               || !((EntityPlayer)entitylivingbase).isSpectator() && !((EntityPlayer)entitylivingbase).isCreative();
      }
   }

   public void startExecuting() {
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
      EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
      this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
      double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
      this.delayCounter--;
      this.timeToRush--;
      double followRange = this.attacker.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
      boolean cansee = this.attacker.getEntitySenses().canSee(entitylivingbase);
      if (cansee) {
         this.attacker.faceEntity(entitylivingbase, 30.0F, 30.0F);
      }

      boolean tooFar = d0 + 5.0 > followRange * followRange;
      if (d0 < 13.0) {
         this.timeToRush = 200;
      }

      if ((!(d0 * 2.0 > followRange * followRange) || cansee) && !tooFar && this.timeToRush <= 0) {
         BlockPos pos = this.attacker.getPosition();
         Block block = this.world.getBlockState(pos).getBlock();
         if (!this.blocks.apply(block) && this.findDelay <= 0) {
            if (this.lastPos == null
               || !this.attacker
                  .getNavigator()
                  .tryMoveToXYZ(this.lastPos.getX() + 0.5, this.lastPos.getY(), this.lastPos.getZ() + 0.5, 1.0)) {
               int doubFD = this.findDistance * 2 + 1;
               BlockPos pos2 = pos.add(
                  this.rand.nextInt(doubFD) - this.findDistance,
                  this.rand.nextInt(doubFD / 2) - this.findDistance / 2,
                  this.rand.nextInt(doubFD) - this.findDistance
               );
               BlockPos pos3 = pos.add(
                  this.rand.nextInt(doubFD) - this.findDistance,
                  this.rand.nextInt(doubFD / 2) - this.findDistance / 2,
                  this.rand.nextInt(doubFD) - this.findDistance
               );
               BlockPos pos4 = pos.add(
                  this.rand.nextInt(doubFD) - this.findDistance,
                  this.rand.nextInt(doubFD / 2) - this.findDistance / 2,
                  this.rand.nextInt(doubFD) - this.findDistance
               );
               BlockPos pos5 = pos.add(
                  this.rand.nextInt(doubFD) - this.findDistance,
                  this.rand.nextInt(doubFD / 2) - this.findDistance / 2,
                  this.rand.nextInt(doubFD) - this.findDistance
               );
               BlockPos pos6 = pos.add(
                  this.rand.nextInt(doubFD) - this.findDistance,
                  this.rand.nextInt(doubFD / 2) - this.findDistance / 2,
                  this.rand.nextInt(doubFD) - this.findDistance
               );
               if (this.blocks.apply(this.world.getBlockState(pos2).getBlock())
                  && this.attacker.getNavigator().tryMoveToXYZ(pos2.getX() + 0.5, pos2.getY(), pos2.getZ() + 0.5, 1.0)) {
                  this.lastPos = pos2;
               } else if (this.blocks.apply(this.world.getBlockState(pos3).getBlock())
                  && this.attacker.getNavigator().tryMoveToXYZ(pos3.getX() + 0.5, pos3.getY(), pos3.getZ() + 0.5, 1.0)) {
                  this.lastPos = pos3;
               } else if (this.blocks.apply(this.world.getBlockState(pos4).getBlock())
                  && this.attacker.getNavigator().tryMoveToXYZ(pos4.getX() + 0.5, pos4.getY(), pos4.getZ() + 0.5, 1.0)) {
                  this.lastPos = pos4;
               } else if (this.blocks.apply(this.world.getBlockState(pos5).getBlock())
                  && this.attacker.getNavigator().tryMoveToXYZ(pos5.getX() + 0.5, pos5.getY(), pos5.getZ() + 0.5, 1.0)) {
                  this.lastPos = pos5;
               } else if (this.blocks.apply(this.world.getBlockState(pos6).getBlock())
                  && this.attacker.getNavigator().tryMoveToXYZ(pos6.getX() + 0.5, pos6.getY(), pos6.getZ() + 0.5, 1.0)) {
                  this.lastPos = pos6;
               } else {
                  this.findCount++;
                  if (this.findCount > 180) {
                     this.findDelay = 100;
                     this.findCount = 0;
                  }
               }
            } else if (!this.blocks.apply(this.world.getBlockState(this.lastPos).getBlock())) {
               this.lastPos = null;
            }
         } else if (cansee && this.shootTimer <= 0) {
            this.attacker.shoot();
            this.shootTimer = this.shootCooldown;
            if (this.triggerAttackAnimation) {
               this.attacker.triggerAnimation(-2);
            }
         }
      } else {
         if (tooFar) {
            this.timeToRush = 400;
         }

         if ((this.longMemory || cansee)
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
      }

      this.findDelay--;
      this.shootTimer--;
      this.attackTick = Math.max(this.attackTick - 1, 0);
      if (this.attack) {
         this.checkAndPerformAttack(entitylivingbase, d0);
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
