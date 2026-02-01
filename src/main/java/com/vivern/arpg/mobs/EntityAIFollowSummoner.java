package com.vivern.arpg.mobs;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.world.World;

public class EntityAIFollowSummoner extends EntityAIBase {
   public final AbstractMob minion;
   World world;
   public final double followSpeed;
   public final PathNavigate petPathfinder;
   public int timeToRecalcPath;
   public float oldWaterCost;

   public EntityAIFollowSummoner(AbstractMob minion, double followSpeedIn) {
      this.minion = minion;
      this.world = minion.world;
      this.followSpeed = followSpeedIn;
      this.petPathfinder = minion.getNavigator();
      this.setMutexBits(8);
      if (!(minion.getNavigator() instanceof PathNavigateGround) && !(minion.getNavigator() instanceof PathNavigateFlying)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   public boolean shouldExecute() {
      if (this.minion.owner == null) {
         return false;
      } else if (this.minion.owner.isSpectator()) {
         return false;
      } else {
         return this.minion.isStaying ? false : !(this.minion.getDistanceSq(this.minion.owner) < this.minion.ownerFollowDistanceSq);
      }
   }

   public boolean shouldContinueExecuting() {
      return this.minion.owner != null && !(this.minion.getDistanceSq(this.minion.owner) < 16.0) ? !this.minion.isStaying : false;
   }

   public void startExecuting() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = this.minion.getPathPriority(PathNodeType.WATER);
      this.minion.setPathPriority(PathNodeType.WATER, 0.0F);
   }

   public void resetTask() {
      this.petPathfinder.clearPath();
      this.minion.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
   }

   public void updateTask() {
      if (--this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = 10;
         if (this.minion.owner != null) {
            this.petPathfinder.tryMoveToEntityLiving(this.minion.owner, this.followSpeed);
         }
      }
   }
}
