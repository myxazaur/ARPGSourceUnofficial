package com.vivern.arpg.mobs;

import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.main.GetMOP;
import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LogicRay {
   public static Vec3d[] offsets = new Vec3d[]{
      new Vec3d(1.0, 0.0, 1.0),
      new Vec3d(-1.0, 0.0, 1.0),
      new Vec3d(1.0, 0.0, -1.0),
      new Vec3d(-1.0, 0.0, -1.0),
      new Vec3d(1.0, 1.0, 0.0),
      new Vec3d(-1.0, 1.0, 0.0),
      new Vec3d(0.0, 1.0, 1.0),
      new Vec3d(0.0, 1.0, -1.0),
      new Vec3d(1.0, -1.0, 0.0),
      new Vec3d(-1.0, -1.0, 0.0),
      new Vec3d(0.0, -1.0, 1.0),
      new Vec3d(0.0, -1.0, -1.0),
      new Vec3d(1.0, 0.0, 0.0),
      new Vec3d(-1.0, 0.0, 0.0),
      new Vec3d(0.0, 0.0, 1.0),
      new Vec3d(0.0, 0.0, -1.0),
      new Vec3d(0.0, 1.0, 0.0),
      new Vec3d(0.0, -1.0, 0.0)
   };
   ResourceLocation debugtexture = new ResourceLocation("arpg:textures/shard_trace.png");
   public double posX;
   public double posY;
   public double posZ;
   public Entity targetEntity = null;
   public LogicRay nextRay = null;
   public float raySize = 0.25F;

   public LogicRay(double posX, double posY, double posZ, Entity targetEntity, LogicRay nextRay) {
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.targetEntity = targetEntity;
      this.nextRay = nextRay;
   }

   public void checkShortRay(
      World world, Entity target, Predicate<? super Entity> successEntity, Predicate<? super Entity> filterEntityToIgnore, boolean stopOnLiquid
   ) {
      Vec3d to = new Vec3d(target.posX, target.posY + target.height / 2.0F, target.posZ);
      Vec3d impact = GetMOP.logicRayTrace(world, this.posVector(), to, 1.0F, filterEntityToIgnore, this.raySize, this.raySize, stopOnLiquid);
      AxisAlignedBB cube = new AxisAlignedBB(
         impact.x - this.raySize,
         impact.y - this.raySize,
         impact.z - this.raySize,
         impact.x + this.raySize,
         impact.y + this.raySize,
         impact.z + this.raySize
      );

      for (EntityLivingBase base : world.getEntitiesWithinAABB(EntityLivingBase.class, cube)) {
         if (successEntity.apply(base)) {
            this.targetEntity = target;
            this.nextRay = null;
         }
      }
   }

   public void checkOwnerWalkedOn(Entity owner) {
      if (this.nextRay != null && owner.getEntityBoundingBox().contains(this.nextRay.posVector())) {
         if (this.nextRay.nextRay == null) {
            this.targetEntity = this.nextRay.targetEntity;
         }

         this.nextRay = this.nextRay.nextRay;
         if (this.nextRay == null && this.targetEntity == null) {
            System.out.println("WARNING RAY LOGIC LOOSE TARGET");
         }
      }
   }

   public boolean exist(World world, Predicate<? super Entity> successEntity, Predicate<? super Entity> filterEntityToIgnore, boolean stopOnLiquid) {
      Vec3d thispos = this.posVector();
      Vec3d impact = this.rayTrace(world, stopOnLiquid, thispos, filterEntityToIgnore);
      AxisAlignedBB cube = new AxisAlignedBB(
         impact.x - this.raySize,
         impact.y - this.raySize,
         impact.z - this.raySize,
         impact.x + this.raySize,
         impact.y + this.raySize,
         impact.z + this.raySize
      );

      for (EntityLivingBase base : world.getEntitiesWithinAABB(EntityLivingBase.class, cube)) {
         if (successEntity.apply(base)) {
            return true;
         }
      }

      boolean noblockImpact = this.noBlockImpact(world, impact);
      if (this.nextRay != null && noblockImpact) {
         return this.nextRay.exist(world, successEntity, filterEntityToIgnore, stopOnLiquid);
      } else {
         if (!noblockImpact) {
            if (this.nextRay == null) {
               for (Vec3d offset : offsets) {
                  Vec3d offpos = offset.add(impact.x, impact.y, impact.z);
                  if (this.checkOffPos(world, offpos, stopOnLiquid, filterEntityToIgnore)) {
                     this.nextRay = new LogicRay(offpos.x, offpos.y, offpos.z, this.targetEntity, null);
                     this.targetEntity = null;
                     break;
                  }
               }
            } else {
               for (Vec3d offsetx : offsets) {
                  Vec3d offpos = offsetx.add(this.nextRay.posX, this.nextRay.posY, this.nextRay.posZ);
                  if (this.checkOffPos(world, offpos, stopOnLiquid, filterEntityToIgnore)) {
                     this.nextRay.posX = offpos.x;
                     this.nextRay.posY = offpos.y;
                     this.nextRay.posZ = offpos.z;
                     break;
                  }
               }
            }
         }

         return false;
      }
   }

   public Vec3d posVector() {
      return new Vec3d(this.posX, this.posY, this.posZ);
   }

   public Vec3d getForwardPosition() {
      if (this.targetEntity != null) {
         return new Vec3d(
            this.targetEntity.posX, this.targetEntity.posY + this.targetEntity.height / 2.0F, this.targetEntity.posZ
         );
      } else {
         return this.nextRay != null ? new Vec3d(this.nextRay.posX, this.nextRay.posY, this.nextRay.posZ) : null;
      }
   }

   public boolean checkOffPos(World world, Vec3d offpos, boolean stopOnLiquid, Predicate<? super Entity> filterEntityToIgnore) {
      Vec3d impactBACK = GetMOP.logicRayTrace(world, offpos, this.posVector(), 1.0F, filterEntityToIgnore, this.raySize, this.raySize, stopOnLiquid);
      Vec3d target;
      if (this.targetEntity != null) {
         target = new Vec3d(
            this.targetEntity.posX, this.targetEntity.posY + this.targetEntity.height / 2.0F, this.targetEntity.posZ
         );
      } else {
         if (this.nextRay == null) {
            return false;
         }

         target = new Vec3d(this.nextRay.posX, this.nextRay.posY, this.nextRay.posZ);
      }

      Vec3d impactFORWARD = GetMOP.logicRayTrace(world, offpos, target, 1.0F, filterEntityToIgnore, this.raySize, this.raySize, stopOnLiquid);
      return this.noBlockImpact(world, impactBACK) && this.noBlockImpact(world, impactFORWARD) && this.noBlockImpact(world, offpos);
   }

   public boolean noBlockImpact(World world, Vec3d impact) {
      return !world.checkBlockCollision(
         new AxisAlignedBB(
            impact.x - this.raySize,
            impact.y - this.raySize,
            impact.z - this.raySize,
            impact.x + this.raySize,
            impact.y + this.raySize,
            impact.z + this.raySize
         )
      );
   }

   public Vec3d rayTrace(World world, boolean stopOnLiquid, Vec3d thispos, Predicate<? super Entity> filterEntityToIgnore) {
      Vec3d target;
      if (this.targetEntity != null) {
         target = new Vec3d(
            this.targetEntity.posX, this.targetEntity.posY + this.targetEntity.height / 2.0F, this.targetEntity.posZ
         );
      } else {
         if (this.nextRay == null) {
            return thispos;
         }

         target = new Vec3d(this.nextRay.posX, this.nextRay.posY, this.nextRay.posZ);
      }

      return GetMOP.logicRayTrace(world, thispos, target, 1.0F, filterEntityToIgnore, this.raySize, this.raySize, stopOnLiquid);
   }

   public void debug(World world) {
      Vec3d target;
      if (this.targetEntity != null) {
         target = new Vec3d(
            this.targetEntity.posX, this.targetEntity.posY + this.targetEntity.height / 2.0F, this.targetEntity.posZ
         );
      } else {
         if (this.nextRay == null) {
            return;
         }

         target = new Vec3d(this.nextRay.posX, this.nextRay.posY, this.nextRay.posZ);
      }

      Vec3d thisp = new Vec3d(this.posX, this.posY, this.posZ);
      BetweenParticle laser = new BetweenParticle(
         world, this.debugtexture, 0.05F, 240, 0.0F, 0.0F, 1.0F, 0.17F, thisp.distanceTo(target), 2, 0.3F, 2.0F, thisp, target
      );
      laser.setPosition(this.posX, this.posY, this.posZ);
      laser.alphaGlowing = true;
      world.spawnEntity(laser);
   }
}
