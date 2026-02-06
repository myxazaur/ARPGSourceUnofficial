package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.SuperKnockback;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

public class EntityAIFlyToSummoner extends EntityAIBase {
   private final EntitySummoned summoned;
   private EntityLivingBase owner;
   World world;
   private final double followSpeed;
   private final PathNavigate petPathfinder;
   private int timeToRecalcPath;
   float maxDist;
   float minDist;
   private float oldWaterCost;

   public EntityAIFlyToSummoner(EntitySummoned summonedIn, double followSpeedIn, float maxDistIn) {
      this.summoned = summonedIn;
      this.world = summonedIn.world;
      this.followSpeed = followSpeedIn;
      this.petPathfinder = summonedIn.getNavigator();
      this.minDist = 4.0F;
      this.maxDist = maxDistIn;
      this.setMutexBits(8);
      if (!(summonedIn.getNavigator() instanceof PathNavigateGround) && !(summonedIn.getNavigator() instanceof PathNavigateFlying)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.summoned.getOwner();
      if (entitylivingbase == null) {
         return false;
      } else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).isSpectator()) {
         return false;
      } else if (!this.summoned.allowedFollow) {
         return false;
      } else if (this.summoned.getDistance(entitylivingbase) < this.summoned.followPlayerMaxRange) {
         return false;
      } else {
         this.owner = entitylivingbase;
         return true;
      }
   }

   public boolean shouldContinueExecuting() {
      if (this.summoned.isEntityInsideOpaqueBlock()) {
         return true;
      } else {
         return this.summoned.getDistance(this.owner) < this.summoned.followPlayerMinRange && this.summoned.posY >= this.owner.posY
            ? false
            : this.summoned.allowedFollow;
      }
   }

   public void startExecuting() {
   }

   public void resetTask() {
      this.owner = null;
      this.summoned.noClip = false;
      this.summoned.setNoGravity(false);
   }

   public void updateTask() {
      float dist = this.summoned.getDistance(this.owner);
      if (this.summoned.allowedFollow
         && !this.summoned.getLeashed()
         && !this.summoned.isRiding()
         && (
            dist > this.summoned.followPlayerMaxRange
               || GetMOP.isCollideBlockOnPos(this.world, this.summoned.posX, this.summoned.posY, this.summoned.posZ)
               || GetMOP.isCollideBlockOnPos(this.world, this.summoned.posX, this.summoned.posY + 1.0, this.summoned.posZ)
         )) {
         this.summoned.motionX /= 2.0;
         this.summoned.motionY /= 2.0;
         this.summoned.motionZ /= 2.0;
         SuperKnockback.applyKnockback(this.summoned, -1.0F, this.owner.posX, this.owner.posY + 2.0, this.owner.posZ);
         this.summoned.noClip = true;
         this.summoned.setNoGravity(true);
      }

      if (dist <= this.summoned.followPlayerMaxRange
         && !GetMOP.isCollideBlockOnPos(this.world, this.summoned.posX, this.summoned.posY, this.summoned.posZ)
         && !GetMOP.isCollideBlockOnPos(this.world, this.summoned.posX, this.summoned.posY + 1.0, this.summoned.posZ)) {
         this.summoned.noClip = false;
         this.summoned.setNoGravity(false);
      }
   }
}
