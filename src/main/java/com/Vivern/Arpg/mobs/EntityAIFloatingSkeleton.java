package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityAIFloatingSkeleton extends EntityAIBase {
   public boolean enable = true;
   public final AbstractMob entity;
   public int maxattackCooldown;
   public int currentattackCooldown;
   public final float maxAttackDistancesq;
   public boolean useshoot = false;
   public int deltaRotation = 30;
   public boolean burst = false;
   public int burstMaxCount = 0;
   public int burstHave = 0;
   public int maxnormalShootsToBurst = 0;
   public int normalShootsToBurst = 0;
   public boolean canStop = true;
   public int maxCooldownOnBurst;
   public float strafingDistanceSq = 0.0F;
   public float strafingDistance = 0.0F;
   public float strafingAltDistanceSq = 0.0F;
   public float strafingAmplitude = 0.0F;
   public float currentRadius = 0.0F;
   public boolean strafingDirection = false;
   public float radiusDirection = 0.0F;
   public int cantMoveTime = 0;
   public float levitateHeight;
   public float levitateHeightSq;
   public float levitateStair = 1.0F;
   public float levitatePower;
   public boolean canFlyup;
   public boolean levitateOverMobs = true;
   public float levitateFlyBonus = 0.0F;
   public final Random rand;
   public boolean triggerAnim3 = false;
   public int attackDelay = 0;
   public int currentattackDelay = 0;

   public EntityAIFloatingSkeleton(
      AbstractMob mob,
      int attackCooldown,
      float maxShootDistance,
      int attackDelay,
      boolean shoot,
      float strafingDistance,
      float strafingAmplitude,
      float levitateHeight,
      float levitatePower,
      boolean canFlyup,
      boolean triggerAnim3
   ) {
      this.entity = mob;
      this.rand = mob.getRNG();
      this.maxattackCooldown = attackCooldown;
      this.maxAttackDistancesq = maxShootDistance * maxShootDistance;
      this.useshoot = shoot;
      this.strafingDistanceSq = strafingDistance * strafingDistance;
      this.strafingDistance = strafingDistance;
      this.strafingAltDistanceSq = (strafingDistance + strafingAmplitude) * (strafingDistance + strafingAmplitude / 2.0F);
      this.strafingAmplitude = strafingAmplitude;
      levitateHeight += mob.height;
      this.levitateHeight = levitateHeight;
      this.levitateHeightSq = levitateHeight * levitateHeight;
      this.currentRadius = strafingDistance;
      this.levitatePower = levitatePower;
      this.canFlyup = canFlyup;
      this.attackDelay = attackDelay;
      this.triggerAnim3 = triggerAnim3;
      this.setMutexBits(3);
   }

   public EntityAIFloatingSkeleton setBurst(boolean burst, int burstMaxCount, int maxnormalShootsToBurst, boolean canStop, int maxCooldownOnBurst) {
      this.burst = burst;
      this.burstMaxCount = burstMaxCount;
      this.maxnormalShootsToBurst = maxnormalShootsToBurst;
      this.canStop = canStop;
      this.maxCooldownOnBurst = maxCooldownOnBurst;
      return this;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null && this.enable;
   }

   public boolean shouldContinueExecuting() {
      return (this.shouldExecute() || !this.entity.getNavigator().noPath()) && this.enable;
   }

   public void startExecuting() {
      super.startExecuting();
   }

   public void resetTask() {
      super.resetTask();
      this.entity.resetActiveHand();
   }

   public boolean isNotColliding(World world, AxisAlignedBB aabb, @Nullable Entity entity) {
      return !world.containsAnyLiquid(aabb) && world.getCollisionBoxes(entity, aabb).isEmpty() && world.checkNoEntityCollision(aabb, entity);
   }

   public boolean tryMove(double x, double y, double z) {
      if (this.isNotColliding(this.entity.world, this.entity.getEntityBoundingBox().offset(x, y, z), this.entity)) {
         Vec3d vec = new Vec3d(this.entity.posX + x, this.entity.posY + y, this.entity.posZ + z);
         RayTraceResult res = this.entity
            .world
            .rayTraceBlocks(
               vec,
               vec.add(0.0, -this.levitateHeight - this.levitateStair, 0.0),
               this.entity.getAttackTarget() == null ? true : !this.entity.getAttackTarget().isInWater()
            );
         if (res != null && (res.typeOfHit == Type.BLOCK || res.typeOfHit == Type.ENTITY)) {
            return true;
         }
      }

      return false;
   }

   public void updateTask() {
      EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
      boolean vlChange = false;
      float levit = this.levitateHeight + this.levitateFlyBonus;
      if (this.currentattackCooldown > 0) {
         this.currentattackCooldown--;
      }

      Vec3d lvec = RotatedPosRayTrace(entitylivingbase, levit, 1.0F, this.entity, 0.6, 0.6, 90.0F, 0.0F, this.levitateOverMobs);
      if (lvec.distanceTo(this.entity.getPositionEyes(1.0F)) < levit) {
         this.entity.motionY = this.entity.motionY + this.levitatePower;
         vlChange = true;
      }

      if (entitylivingbase != null) {
         double d0 = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
         boolean cansee = this.entity.getEntitySenses().canSee(entitylivingbase);
         float speed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
         if (this.canFlyup) {
            if (this.entity.posY + this.levitateHeight < entitylivingbase.posY) {
               this.levitateFlyBonus += speed;
            } else {
               this.levitateFlyBonus *= 0.93F;
            }
         }

         if (this.entity.ticksExisted % 4 == 0) {
            if (d0 > this.strafingAltDistanceSq) {
               float dist = (float)Math.sqrt(d0);
               float prunex = (float)((entitylivingbase.posX - this.entity.posX) / dist / 2.0);
               float pruney = (float)((entitylivingbase.posY - this.entity.posY) / dist / 2.0);
               float prunez = (float)((entitylivingbase.posZ - this.entity.posZ) / dist / 2.0);
               float dd0 = MathHelper.sqrt(prunex * prunex + pruney * pruney + prunez * prunez);
               if (dd0 < 1.0E-4) {
                  prunex = 0.0F;
                  pruney = 0.0F;
                  prunez = 0.0F;
               } else {
                  prunex /= dd0;
                  pruney /= dd0;
                  prunez /= dd0;
               }

               prunex *= speed;
               pruney *= speed;
               prunez *= speed;
               if (!this.tryMove(prunex * 4.0F, pruney * 4.0F, prunez * 4.0F) && this.cantMoveTime <= 5) {
                  this.entity.motionX *= 0.4;
                  this.entity.motionY *= 0.5;
                  this.entity.motionZ *= 0.4;
                  this.cantMoveTime++;
               } else {
                  this.entity.motionX *= 0.7;
                  this.entity.motionY *= 0.8;
                  this.entity.motionZ *= 0.7;
                  this.entity.motionX += prunex;
                  this.entity.motionY = this.entity.motionY + MathHelper.clamp(pruney / 2.0F, -0.3, 0.3);
                  this.entity.motionZ += prunez;
                  vlChange = true;
                  this.cantMoveTime = 0;
                  this.entity.faceEntity(entitylivingbase, this.deltaRotation, this.deltaRotation);
               }
            } else {
               this.currentRadius = this.currentRadius + this.radiusDirection;
               double radius = this.currentRadius;
               boolean caninvert = true;
               double relX = this.entity.posX - entitylivingbase.posX;
               double relZ = this.entity.posZ - entitylivingbase.posZ;
               double ang = Math.atan2(relZ, relX);
               ang += this.strafingDirection ? 0.169 : -0.169;
               double finalX = radius * Math.cos(ang) + entitylivingbase.posX - this.entity.posX;
               double finalZ = radius * Math.sin(ang) + entitylivingbase.posZ - this.entity.posZ;
               float dd0x = MathHelper.sqrt(finalX * finalX + finalZ * finalZ);
               if (dd0x < 1.0E-4) {
                  finalX = 0.0;
                  finalZ = 0.0;
               } else {
                  finalX /= dd0x;
                  finalZ /= dd0x;
               }

               finalX *= speed;
               finalZ *= speed;
               if (!this.tryMove(finalX * 4.0, 0.0, finalZ * 4.0) && this.cantMoveTime <= 5) {
                  this.entity.motionX *= 0.4;
                  this.entity.motionY *= 0.5;
                  this.entity.motionZ *= 0.4;
                  vlChange = true;
                  this.strafingDirection = !this.strafingDirection;
                  caninvert = false;
                  this.cantMoveTime++;
               } else {
                  this.entity.motionX *= 0.7;
                  this.entity.motionY *= 0.8;
                  this.entity.motionZ *= 0.7;
                  this.entity.motionX += finalX;
                  this.entity.motionZ += finalZ;
                  vlChange = true;
                  this.cantMoveTime = 0;
                  this.entity.faceEntity(entitylivingbase, this.deltaRotation, this.deltaRotation);
               }

               if (this.rand.nextFloat() < 0.27 * speed && caninvert) {
                  this.strafingDirection = !this.strafingDirection;
               }

               if (this.rand.nextFloat() < 0.34 * speed) {
                  this.radiusDirection = (this.rand.nextFloat() - 0.5F) * (this.rand.nextFloat() < 0.5 ? 0.6F : 0.35F);
               }

               if (this.radiusDirection > 0.0F && this.currentRadius >= this.strafingDistance + this.strafingAmplitude) {
                  this.radiusDirection = this.rand.nextFloat() < 0.5 ? 0.0F : -this.radiusDirection;
               }

               if (this.radiusDirection < 0.0F && this.currentRadius <= this.strafingDistance - this.strafingAmplitude) {
                  this.radiusDirection = this.rand.nextFloat() < 0.5 ? 0.0F : -this.radiusDirection;
               }
            }
         }

         if (this.useshoot) {
            if (d0 < this.maxAttackDistancesq && cansee || !this.canStop && this.burstHave > 0) {
               this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, this.deltaRotation, this.deltaRotation);
               if (this.burst) {
                  if (this.burstHave > 0) {
                     if (this.currentattackCooldown <= 0) {
                        if (this.triggerAnim3 && this.burstHave == this.burstMaxCount) {
                           this.entity.triggerAnimation(-3);
                        }

                        this.currentattackCooldown = this.maxCooldownOnBurst;
                        this.entity.shoot();
                        this.burstHave--;
                        if (this.burstHave <= 0) {
                           this.currentattackCooldown = this.maxattackCooldown;
                        }
                     }
                  } else if (this.normalShootsToBurst >= this.maxnormalShootsToBurst) {
                     this.normalShootsToBurst = 0;
                     this.burstHave = this.burstMaxCount;
                  } else if (this.currentattackCooldown <= 0) {
                     if (this.currentattackDelay >= this.attackDelay) {
                        this.currentattackCooldown = this.maxattackCooldown;
                        this.entity.shoot();
                        this.currentattackDelay = 0;
                        this.normalShootsToBurst++;
                     } else {
                        if (this.triggerAnim3 && this.currentattackDelay == 0) {
                           this.entity.triggerAnimation(-3);
                        }

                        this.currentattackDelay++;
                     }
                  }
               } else if (this.currentattackCooldown <= 0) {
                  if (this.currentattackDelay >= this.attackDelay) {
                     this.currentattackCooldown = this.maxattackCooldown;
                     this.currentattackDelay = 0;
                     this.entity.shoot();
                  } else {
                     if (this.triggerAnim3 && this.currentattackDelay == 0) {
                        this.entity.triggerAnimation(-3);
                     }

                     this.currentattackDelay++;
                  }
               }
            } else if (this.currentattackDelay > 0) {
               this.currentattackDelay--;
            }
         }
      }

      if (vlChange && this.entity.ticksExisted % 2 == 0) {
         this.entity.velocityChanged = true;
      }
   }

   public static Vec3d RotatedPosRayTrace(
      EntityLivingBase attacktarget,
      double blockReachDistance,
      float partialTicks,
      EntityLivingBase entity,
      double size,
      double step,
      float rotationPitch,
      float rotationYaw,
      boolean levitateOverMobs
   ) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = GetMOP.PitchYawToVec3d(rotationPitch, rotationYaw);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = entity.world
         .rayTraceBlocks(vec3d, vec3d2, attacktarget == null ? true : !attacktarget.isInWater(), true, false);
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return levitateOverMobs ? GetMOP.findEndCoordOnPath(vec3d, vec3d2, entity.world, entity, size, step) : vec3d2;
   }
}
