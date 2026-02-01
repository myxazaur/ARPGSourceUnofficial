package com.vivern.arpg.mobs;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.SuperKnockback;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityAIBird extends AbstractMob.AbstractAI {
   public final AbstractMob entity;
   public int MODE = 0;
   public int HITS = 0;
   public float TURNMODECHANCE = 0.0F;
   public float FLYSPEEDMULT = 2.0F;
   public int maxattackCooldown;
   public int currentattackCooldown;
   public final float maxAttackDistancesq;
   public boolean useshoot = false;
   public boolean burst = false;
   public int burstMaxCount = 0;
   public int burstHave = 0;
   public int maxnormalShootsToBurst = 0;
   public int normalShootsToBurst = 0;
   public boolean canStop = true;
   public int maxCooldownOnBurst;
   public final Random rand;
   public boolean triggerAnim3 = false;
   public int attackDelay = 0;
   public int currentattackDelay = 0;
   public boolean firstAgred = true;
   public int attackTick;
   double speedTowardsTarget = 1.0;
   boolean longMemory = false;
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
   public int noPathTime = 0;
   public int attackTryingTimer = 0;
   public Vec3d flyingAxisVector;
   public Vec3d nextFlyPointRel;
   public double maxdistSq = 256.0;
   public double mindistSq = 100.0;
   public float flyingDirection = 0.3F;
   public Vec3d stayPOS;
   public int stayTIME = 0;
   public int rushTIME = 0;
   public double followFlyDistSq = 2304.0;
   public int sendEntStateAdd = 10;

   public EntityAIBird(
      AbstractMob mob,
      int attackCooldown,
      float maxShootDistance,
      int attackDelay,
      boolean shoot,
      float strafingDistance,
      float strafingAmplitude,
      boolean triggerAnim3
   ) {
      this.entity = mob;
      this.rand = mob.getRNG();
      this.maxattackCooldown = attackCooldown;
      this.maxAttackDistancesq = maxShootDistance * maxShootDistance;
      this.useshoot = shoot;
      this.attackDelay = attackDelay;
      this.triggerAnim3 = triggerAnim3;
      this.setMutexBits(3);
   }

   public EntityAIBird setBurst(boolean burst, int burstMaxCount, int maxnormalShootsToBurst, boolean canStop, int maxCooldownOnBurst) {
      this.burst = burst;
      this.burstMaxCount = burstMaxCount;
      this.maxnormalShootsToBurst = maxnormalShootsToBurst;
      this.canStop = canStop;
      this.maxCooldownOnBurst = maxCooldownOnBurst;
      return this;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public boolean shouldContinueExecuting() {
      return this.shouldExecute() || !this.entity.getNavigator().noPath();
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
      return this.isNotColliding(this.entity.world, this.entity.getEntityBoundingBox().offset(x, y, z), this.entity);
   }

   @Override
   public float onEntityAttacked(DamageSource source, float amount) {
      if (amount < 3.0F && (source.getTrueSource() != null || source.getImmediateSource() != null)) {
         this.HITS++;
      }

      if (amount >= 3.0F) {
         this.HITS++;
      }

      if (amount >= 5.0F) {
         this.HITS++;
      }

      return amount;
   }

   public boolean onGround(World world, double x, double y, double z) {
      Vec3d vewc = new Vec3d(x, y, z);
      RayTraceResult res = world.rayTraceBlocks(vewc, vewc.add(0.0, -4.0, 0.0), false);
      return res == null ? false : res.typeOfHit == Type.BLOCK;
   }

   public Vec3d rotateVecAroundAxis(Vec3d vector, Vec3d axisVector, float angle) {
      axisVector = axisVector.normalize();
      double cosangle = Math.cos(angle);
      return axisVector.scale(axisVector.dotProduct(vector) * (1.0 - cosangle))
         .add(axisVector.crossProduct(vector).scale(Math.sin(angle)))
         .add(vector.scale(cosangle));
   }

   public Vec3d perpendicularVec(Vec3d vec, Vec3d randomVec) {
      return randomVec.subtract(vec.scale(vec.dotProduct(randomVec) / vec.lengthSquared()));
   }

   public void updateTask() {
      EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
      if (this.currentattackCooldown > 0) {
         this.currentattackCooldown--;
      }

      if (this.attackTryingTimer > 0) {
         this.attackTryingTimer--;
      }

      if (this.MODE == 0) {
         if (this.onGround(this.entity.world, this.entity.posX, this.entity.posY, this.entity.posZ)) {
            this.entity.setNoGravity(false);
            this.startRush();
            this.MODE = 1;
            this.entity.world.setEntityState(this.entity, (byte)(1 + this.sendEntStateAdd));
         } else {
            this.entity.setNoGravity(true);
            this.MODE = 2;
            this.entity.world.setEntityState(this.entity, (byte)(2 + this.sendEntStateAdd));
         }
      }

      if (entitylivingbase != null) {
         double d0 = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
         boolean cansee = this.entity.getEntitySenses().canSee(entitylivingbase);
         float speed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * this.FLYSPEEDMULT;
         if (this.MODE == 1) {
            this.rushTIME++;
            this.updateRush();
            if (this.entity.getNavigator().noPath()) {
               this.noPathTime++;
            }

            if (this.rushTIME > 160 && this.rand.nextFloat() < 0.01
               || this.HITS >= 8
               || this.noPathTime > 30
               || d0 > this.followFlyDistSq
               || this.entity.fallDistance >= 2.0F
                  && !this.onGround(this.entity.world, this.entity.posX, this.entity.posY, this.entity.posZ)) {
               this.resetRush();
               this.entity.setNoGravity(true);
               this.HITS = 0;
               this.MODE = 2;
               this.firstAgred = true;
               this.rushTIME = 0;
               this.noPathTime = 0;
               this.entity.world.setEntityState(this.entity, (byte)(2 + this.sendEntStateAdd));
            }

            Vec3d pwvec = this.entity.getLookVec();
            pwvec = pwvec.scale(Math.sqrt(d0));
            double vX = entitylivingbase.posX - this.entity.posX;
            double vY = entitylivingbase.posY - this.entity.posY;
            double vZ = entitylivingbase.posZ - this.entity.posZ;
            if (pwvec.squareDistanceTo(vX, vY, vZ) < 0.56) {
               this.tryShoot(d0, cansee, entitylivingbase);
            }
         } else if (this.attack) {
            this.attackTick = Math.max(this.attackTick - 1, 0);
            this.checkAndPerformAttack(entitylivingbase, d0);
         }

         if (this.MODE == 3) {
            if (this.entity.ticksExisted % 4 == 0) {
               if (this.stayPOS != null) {
                  SuperKnockback.applyMove(this.entity, -speed, this.stayPOS.x, this.stayPOS.y, this.stayPOS.z);
               }

               if (cansee) {
                  this.entity.faceEntity(entitylivingbase, 30.0F, 30.0F);
               }

               this.stayTIME += 4;
               if (this.stayTIME > 130 || this.HITS > 8) {
                  if (this.onGround(this.entity.world, this.entity.posX, this.entity.posY, this.entity.posZ)) {
                     this.entity.setNoGravity(false);
                     this.startRush();
                     this.MODE = 1;
                     this.stayTIME = 0;
                     this.HITS = 0;
                     this.entity.world.setEntityState(this.entity, (byte)(1 + this.sendEntStateAdd));
                  } else if (cansee
                     && this.onGround(this.entity.world, entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ)
                     )
                   {
                     this.stayTIME /= 2;
                     this.HITS /= 2;
                     this.stayPOS = entitylivingbase.getPositionVector();
                     this.entity.world.setEntityState(this.entity, (byte)(4 + this.sendEntStateAdd));
                  } else if (this.stayTIME > 180) {
                     this.entity.setNoGravity(true);
                     this.HITS = 0;
                     this.MODE = 2;
                     this.firstAgred = true;
                     this.entity.world.setEntityState(this.entity, (byte)(2 + this.sendEntStateAdd));
                  }
               }
            }

            if (cansee) {
               this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
            }

            Vec3d pwvec = this.entity.getLookVec();
            pwvec = pwvec.scale(Math.sqrt(d0));
            double vX = entitylivingbase.posX - this.entity.posX;
            double vY = entitylivingbase.posY - this.entity.posY;
            double vZ = entitylivingbase.posZ - this.entity.posZ;
            if (pwvec.squareDistanceTo(vX, vY, vZ) < 0.56) {
               this.tryShoot(d0, cansee, entitylivingbase);
            }
         }

         if (this.MODE == 2) {
            if (this.firstAgred) {
               this.firstAgred = false;
               double relX = this.entity.posX - entitylivingbase.posX;
               double relY = this.entity.posY - entitylivingbase.posY;
               double relZ = this.entity.posZ - entitylivingbase.posZ;
               this.nextFlyPointRel = new Vec3d(relX, relY, relZ);
               Vec3d randVec = this.nextFlyPointRel
                  .add(
                     this.rand.nextFloat() < 0.5 ? this.rand.nextFloat() - 1.5F : this.rand.nextFloat() + 0.5F,
                     this.rand.nextFloat() < 0.5 ? this.rand.nextFloat() - 1.5F : this.rand.nextFloat() + 0.5F,
                     this.rand.nextFloat() < 0.5 ? this.rand.nextFloat() - 1.5F : this.rand.nextFloat() + 0.5F
                  );
               this.flyingAxisVector = this.perpendicularVec(this.nextFlyPointRel, randVec);
               this.TURNMODECHANCE = 0.0F;
            }

            if (this.entity.ticksExisted % 5 == 0 && this.flyingAxisVector != null) {
               this.TURNMODECHANCE = (float)(this.TURNMODECHANCE + 0.001);

               for (int axi = 0; axi < 6; axi++) {
                  if (axi == 1 || axi == 3) {
                     double rellX = this.entity.posX - entitylivingbase.posX;
                     double rellY = this.entity.posY - entitylivingbase.posY;
                     double rellZ = this.entity.posZ - entitylivingbase.posZ;
                     Vec3d axis = new Vec3d(rellX, rellY, rellZ);
                     this.flyingAxisVector = this.rotateVecAroundAxis(this.flyingAxisVector, axis, 0.5F);
                  }

                  if (axi == 2) {
                     double rellX = this.entity.posX - entitylivingbase.posX;
                     double rellY = this.entity.posY - entitylivingbase.posY;
                     double rellZ = this.entity.posZ - entitylivingbase.posZ;
                     Vec3d axis = new Vec3d(rellX, rellY, rellZ);
                     this.flyingAxisVector = this.rotateVecAroundAxis(this.flyingAxisVector, axis, -1.0F);
                  }

                  if (axi == 5) {
                     if (this.rand.nextFloat() < this.TURNMODECHANCE + 0.04) {
                        if (this.onGround(this.entity.world, this.entity.posX, this.entity.posY, this.entity.posZ)) {
                           this.entity.setNoGravity(false);
                           this.startRush();
                           this.MODE = 1;
                           this.HITS = 0;
                           this.entity.world.setEntityState(this.entity, (byte)(1 + this.sendEntStateAdd));
                        } else {
                           this.entity.setNoGravity(true);
                           this.MODE = 3;
                           this.stayPOS = this.entity.getPositionVector();
                           this.HITS = 0;
                           this.stayTIME = 0;
                           this.entity.world.setEntityState(this.entity, (byte)(3 + this.sendEntStateAdd));
                           this.currentattackCooldown = 15;
                        }

                        this.TURNMODECHANCE = 0.0F;
                     }

                     this.flyingDirection = -this.flyingDirection;
                     this.TURNMODECHANCE = (float)(this.TURNMODECHANCE + 0.003);
                     this.entity.world.setEntityState(this.entity, (byte)(5 + this.sendEntStateAdd));
                  }

                  Vec3d tempFlyPointRel = this.rotateVecAroundAxis(this.nextFlyPointRel, this.flyingAxisVector, this.flyingDirection);
                  double lenSq = tempFlyPointRel.lengthSquared();
                  if (lenSq > this.maxdistSq) {
                     tempFlyPointRel = tempFlyPointRel.scale(0.8);
                  } else if (lenSq < this.mindistSq) {
                     tempFlyPointRel = tempFlyPointRel.scale(1.2);
                  }

                  if (axi == 3) {
                     tempFlyPointRel = tempFlyPointRel.scale(1.3);
                  }

                  if (axi == 4) {
                     tempFlyPointRel = tempFlyPointRel.scale(0.7);
                  }

                  double newPosX = entitylivingbase.posX + tempFlyPointRel.x;
                  double newPosY = entitylivingbase.posY + tempFlyPointRel.y;
                  double newPosZ = entitylivingbase.posZ + tempFlyPointRel.z;
                  double moveX = newPosX - this.entity.posX;
                  double moveY = newPosY - this.entity.posY;
                  double moveZ = newPosZ - this.entity.posZ;
                  if (this.tryMove(moveX, moveY, moveZ)) {
                     float dd0 = MathHelper.sqrt(moveX * moveX + moveY * moveY + moveZ * moveZ);
                     if (dd0 < 1.0E-4) {
                        moveX = 0.0;
                        moveY = 0.0;
                        moveZ = 0.0;
                     } else {
                        moveX /= dd0;
                        moveY /= dd0;
                        moveZ /= dd0;
                     }

                     this.entity.motionX *= 0.7;
                     this.entity.motionY *= 0.7;
                     this.entity.motionZ *= 0.7;
                     this.entity.motionX = moveX * speed;
                     this.entity.motionY = moveY * speed;
                     this.entity.motionZ = moveZ * speed;
                     this.entity.velocityChanged = true;
                     this.nextFlyPointRel = tempFlyPointRel;
                     break;
                  }
               }

               if (cansee && this.rand.nextFloat() < this.TURNMODECHANCE) {
                  this.entity.setNoGravity(true);
                  this.MODE = 3;
                  this.stayPOS = this.entity.getPositionVector();
                  this.HITS = 0;
                  this.stayTIME = 20;
                  this.TURNMODECHANCE = 0.0F;
                  this.entity.world.setEntityState(this.entity, (byte)(3 + this.sendEntStateAdd));
                  this.currentattackCooldown = 15;
               }
            }

            if (this.currentattackCooldown <= 0 && this.attackTryingTimer <= 0) {
               this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
               Vec3d pwvec = this.entity.getLookVec();
               pwvec = pwvec.scale(Math.sqrt(d0));
               double vX = entitylivingbase.posX - this.entity.posX;
               double vY = entitylivingbase.posY - this.entity.posY;
               double vZ = entitylivingbase.posZ - this.entity.posZ;
               if (pwvec.squareDistanceTo(vX, vY, vZ) < 4.0) {
                  this.tryShoot(d0, cansee, entitylivingbase);
               } else {
                  this.attackTryingTimer = 15;
               }
            }
         }
      } else {
         this.firstAgred = true;
      }
   }

   public void tryShoot(double distSq, boolean cansee, EntityLivingBase entitylivingbase) {
      if (this.useshoot && entitylivingbase.isEntityAlive()) {
         if (distSq < this.maxAttackDistancesq && cansee || !this.canStop && this.burstHave > 0) {
            this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
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

   public static Vec3d RotatedPosRayTrace(
      EntityLivingBase attacktarget,
      double blockReachDistance,
      float partialTicks,
      EntityLivingBase entity,
      double size,
      double step,
      float rotationPitch,
      float rotationYaw
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

      return GetMOP.findEndCoordOnPath(vec3d, vec3d2, entity.world, entity, size, step);
   }

   public void updateRush() {
      EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
      this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
      double d0 = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
      this.delayCounter--;
      if ((this.longMemory || this.entity.getEntitySenses().canSee(entitylivingbase))
         && this.delayCounter <= 0
         && (
            this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0
               || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0
               || this.entity.getRNG().nextFloat() < 0.05F
         )) {
         this.targetX = entitylivingbase.posX;
         this.targetY = entitylivingbase.getEntityBoundingBox().minY;
         this.targetZ = entitylivingbase.posZ;
         this.delayCounter = 4 + this.entity.getRNG().nextInt(7);
         if (this.canPenalize) {
            this.delayCounter = this.delayCounter + this.failedPathFindingPenalty;
            if (this.entity.getNavigator().getPath() != null) {
               PathPoint finalPathPoint = this.entity.getNavigator().getPath().getFinalPathPoint();
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

         if (!this.entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget)) {
            this.delayCounter += 15;
         }
      }

      this.attackTick = Math.max(this.attackTick - 1, 0);
      if (this.attack) {
         this.checkAndPerformAttack(entitylivingbase, d0);
      }
   }

   protected void checkAndPerformAttack(EntityLivingBase enemy, double distToEnemySqr) {
      double d0 = this.getAttackReachSqr(enemy);
      if (distToEnemySqr <= d0 && this.attackTick <= 0) {
         this.attackTick = 20;
         this.entity.swingArm(EnumHand.MAIN_HAND);
         this.entity.attackEntityAsMob(enemy);
         if (this.triggerAttackAnimation) {
            this.entity.triggerAnimation(-1);
         }
      }
   }

   protected double getAttackReachSqr(EntityLivingBase attackTarget) {
      return this.entity.width * 2.0F * this.entity.width * 2.0F + attackTarget.width;
   }

   public boolean shouldExecuteRush() {
      EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
      if (entitylivingbase == null) {
         return false;
      } else if (!entitylivingbase.isEntityAlive()) {
         return false;
      } else {
         if (this.lowerDist > -1.0F) {
            double dist = this.entity.getDistance(entitylivingbase);
            if (!(dist > this.lowerDist) || !(dist < this.higherDist)) {
               return false;
            }
         }

         if (!this.canPenalize) {
            this.path = this.entity.getNavigator().getPathToEntityLiving(entitylivingbase);
            return this.path != null
               ? true
               : this.getAttackReachSqr(entitylivingbase)
                  >= this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
         } else if (--this.delayCounter <= 0) {
            this.path = this.entity.getNavigator().getPathToEntityLiving(entitylivingbase);
            this.delayCounter = 4 + this.entity.getRNG().nextInt(7);
            return this.path != null;
         } else {
            return true;
         }
      }
   }

   public boolean shouldContinueRush() {
      EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
      if (entitylivingbase == null) {
         return false;
      } else if (!entitylivingbase.isEntityAlive()) {
         return false;
      } else if (!this.longMemory) {
         return !this.entity.getNavigator().noPath();
      } else {
         return !this.entity.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase))
            ? false
            : !(entitylivingbase instanceof EntityPlayer)
               || !((EntityPlayer)entitylivingbase).isSpectator() && !((EntityPlayer)entitylivingbase).isCreative();
      }
   }

   public void resetRush() {
      EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
      if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer)entitylivingbase).isSpectator() || ((EntityPlayer)entitylivingbase).isCreative())) {
         this.entity.setAttackTarget((EntityLivingBase)null);
      }

      this.entity.getNavigator().clearPath();
   }

   public void startRush() {
      this.entity.getNavigator().setPath(this.path, this.speedTowardsTarget);
      this.delayCounter = 0;
   }
}
