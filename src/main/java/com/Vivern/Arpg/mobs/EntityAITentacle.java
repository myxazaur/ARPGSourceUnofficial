//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Weapons;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityAITentacle extends EntityAIBase {
   public boolean enable = true;
   public final AbstractMob entity;
   public float strafingDistance;
   public float strafingAmplitude;
   public float tremor;
   public int maxStrikeCooldown;
   public int strikeCooldownAmplitude;
   public float strikeTimeIncrease = 2.3F;
   public boolean useStrikes;
   public boolean useShoot;
   public int maxShootCooldown;
   public int maxOnBurstCooldown;
   public int maxBurstCount;
   public boolean canStopBurst;
   public float strikeAcceleration;
   public int strikeCooldown;
   public int attackTick;
   public float strikeTime = 60.0F;
   public Vec3d strikeDirection;
   public Vec3d randomTremorDirection = Vec3d.ZERO;
   public int tremorResetTick;
   public Vec3d stayPos;
   public Vec3d axisVec;
   public int shootCooldown;
   public int onBurstCooldown;
   public int burstCountHas;
   public float hardnessBreaks = 0.0F;
   public float blockDropChance = 0.12F;
   public int radiusBreaks = 3;
   public int byteSendDelay = 0;

   public EntityAITentacle(
      AbstractMob mob,
      boolean useStrikes,
      float strafingDistance,
      float strafingAmplitude,
      float tremor,
      int maxStrikeCooldown,
      int strikeCooldownAmplitude,
      float strikeAcceleration
   ) {
      this.entity = mob;
      this.strafingDistance = strafingDistance;
      this.strafingAmplitude = strafingAmplitude;
      this.tremor = tremor;
      this.maxStrikeCooldown = maxStrikeCooldown;
      this.strikeCooldownAmplitude = strikeCooldownAmplitude;
      this.useStrikes = useStrikes;
      this.strikeAcceleration = strikeAcceleration;
      if (useStrikes) {
         this.strikeCooldown = maxStrikeCooldown + mob.getRNG().nextInt(strikeCooldownAmplitude) - mob.getRNG().nextInt(strikeCooldownAmplitude);
      }
   }

   public EntityAITentacle setShoot(int maxShootCooldown, int maxOnBurstCooldown, int maxBurstCount, boolean canStopBurst) {
      this.useShoot = true;
      this.maxShootCooldown = maxShootCooldown;
      this.maxOnBurstCooldown = maxOnBurstCooldown;
      this.maxBurstCount = maxBurstCount;
      this.canStopBurst = canStopBurst;
      return this;
   }

   public EntityAITentacle setBreakBlocks(float hardnessBreaks, float blockDropChance, int radiusBreaks) {
      this.hardnessBreaks = hardnessBreaks;
      this.blockDropChance = blockDropChance;
      this.radiusBreaks = radiusBreaks;
      return this;
   }

   public boolean shouldExecute() {
      return this.enable && this.entity.getAttackTarget() != null;
   }

   public void updateTask() {
      this.attackTick = Math.max(this.attackTick - 1, 0);
      this.byteSendDelay = Math.max(this.byteSendDelay - 1, 0);
      EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
      Random rand = this.entity.getRNG();
      if (entitylivingbase != null) {
         float lookTremor = Debugger.floats[4];
         if (this.burstCountHas > 0) {
            lookTremor = 0.0F;
         }

         double p0 = entitylivingbase.posX + this.randomTremorDirection.x * lookTremor - this.entity.posX;
         double p1 = entitylivingbase.posY
            + entitylivingbase.height / 2.0F
            + this.randomTremorDirection.y * lookTremor
            - (this.entity.posY + this.entity.getEyeHeight());
         double p2 = entitylivingbase.posZ + this.randomTremorDirection.z * lookTremor - this.entity.posZ;
         double d3 = MathHelper.sqrt(p0 * p0 + p2 * p2);
         float f = (float)(MathHelper.atan2(p2, p0) * (180.0 / Math.PI)) - 90.0F;
         float f1 = (float)(-(MathHelper.atan2(p1, d3) * (180.0 / Math.PI)));
         this.entity.rotationPitch = f1;
         this.entity.rotationYaw = f;
         if (this.useShoot) {
            if (this.canStopBurst) {
               if (this.burstCountHas > 0) {
                  if (this.onBurstCooldown <= 0) {
                     if (this.entity.getEntitySenses().canSee(entitylivingbase) && this.strikeTime >= 60.0F) {
                        this.entity.shoot();
                        this.burstCountHas--;
                     }

                     this.onBurstCooldown = this.maxOnBurstCooldown;
                  } else {
                     this.onBurstCooldown--;
                  }
               } else if (this.shootCooldown <= 0) {
                  this.burstCountHas = this.maxBurstCount;
                  this.shootCooldown = this.maxShootCooldown;
               } else {
                  this.shootCooldown--;
               }
            } else if (this.burstCountHas > 0) {
               if (this.onBurstCooldown <= 0) {
                  this.entity.shoot();
                  this.onBurstCooldown = this.maxOnBurstCooldown;
                  this.burstCountHas--;
               } else {
                  this.onBurstCooldown--;
               }
            } else if (this.shootCooldown <= 0) {
               if (this.entity.getEntitySenses().canSee(entitylivingbase) && this.strikeTime >= 60.0F) {
                  this.burstCountHas = this.maxBurstCount;
                  this.shootCooldown = this.maxShootCooldown;
               }
            } else {
               this.shootCooldown--;
            }
         }

         if (this.strikeCooldown > 15 && this.strikeTime >= 60.0F) {
            if (this.stayPos == null) {
               this.stayPos = new Vec3d(rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian())
                  .normalize()
                  .scale(this.strafingDistance - this.strafingAmplitude / 2.0F + rand.nextFloat() * this.strafingAmplitude);
               this.axisVec = new Vec3d(rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian());
            } else {
               float speed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
               SuperKnockback.applyMove(
                  this.entity,
                  -speed,
                  this.stayPos.x + entitylivingbase.posX,
                  this.stayPos.y + entitylivingbase.posY + entitylivingbase.height / 2.0F,
                  this.stayPos.z + entitylivingbase.posZ
               );
               if (this.entity.ticksExisted % 3 == 0 && this.axisVec != null) {
                  this.stayPos = GetMOP.rotateVecAroundAxis(this.stayPos, this.axisVec, 0.052F);
               }
            }
         }

         if (this.useStrikes) {
            if (this.strikeTime < 60.0F) {
               if (this.strikeDirection != null) {
                  float speed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
                  if (this.strikeTime < 22.0F) {
                     this.entity.motionX = this.entity.motionX - this.strikeDirection.x * speed;
                     this.entity.motionY = this.entity.motionY - this.strikeDirection.y * speed;
                     this.entity.motionZ = this.entity.motionZ - this.strikeDirection.z * speed;
                  } else {
                     speed *= this.strikeAcceleration;
                     this.entity.motionX = this.entity.motionX + this.strikeDirection.x * speed;
                     this.entity.motionY = this.entity.motionY + this.strikeDirection.y * speed;
                     this.entity.motionZ = this.entity.motionZ + this.strikeDirection.z * speed;
                     double d0 = this.entity
                        .getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
                     this.checkAndPerformAttack(entitylivingbase, d0);
                     if (this.hardnessBreaks > 0.0F) {
                        this.breakBlocks(rand, this.radiusBreaks);
                     }
                  }
               }

               this.strikeTime = this.strikeTime + this.strikeTimeIncrease;
               if (this.strikeTime >= 60.0F) {
                  this.stayPos = null;
               }
            } else if (this.strikeCooldown <= 0) {
               this.strikeDirection = GetMOP.entityCenterPos(entitylivingbase).subtract(GetMOP.entityCenterPos(this.entity)).normalize();
               this.strikeTime = 0.0F;
               this.strikeCooldown = this.maxStrikeCooldown + rand.nextInt(this.strikeCooldownAmplitude) - rand.nextInt(this.strikeCooldownAmplitude);
            } else {
               this.strikeCooldown--;
            }
         }
      }

      if (this.tremor > 0.0F && this.strikeTime >= 60.0F) {
         if (this.randomTremorDirection != null && rand.nextInt(100) >= this.tremorResetTick) {
            float speed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
            this.entity.motionX = this.entity.motionX + this.randomTremorDirection.x * speed;
            this.entity.motionY = this.entity.motionY + this.randomTremorDirection.y * speed;
            this.entity.motionZ = this.entity.motionZ + this.randomTremorDirection.z * speed;
            this.tremorResetTick++;
         } else {
            this.tremorResetTick = 0;
            this.randomTremorDirection = new Vec3d(rand.nextGaussian() * this.tremor, rand.nextGaussian() * this.tremor, rand.nextGaussian() * this.tremor);
         }
      }
   }

   protected void checkAndPerformAttack(EntityLivingBase enemy, double distToEnemySqr) {
      double d0 = this.getAttackReachSqr(enemy);
      if (distToEnemySqr <= d0 && this.attackTick <= 0) {
         this.attackTick = 20;
         if (this.entity.attackEntityAsMob(enemy) && this.byteSendDelay <= 0) {
            this.entity.world.setEntityState(this.entity, (byte)13);
            this.byteSendDelay = 60;
         }
      }
   }

   protected double getAttackReachSqr(EntityLivingBase attackTarget) {
      return this.entity.width * 2.0F * this.entity.width * 2.0F + attackTarget.width;
   }

   public void breakBlocks(Random rand, int radius) {
      BlockPos poss = this.entity.getPosition();

      for (BlockPos pos : GetMOP.getPosesInsideSphere(poss.getX(), poss.getY(), poss.getZ(), radius)) {
         IBlockState blockState = this.entity.world.getBlockState(pos);
         if (Weapons.easyBreakBlockFor(this.entity.world, this.hardnessBreaks, pos, blockState)
            && !blockState.getMaterial().isLiquid()
            && blockState.getMaterial() != Material.AIR) {
            if (rand.nextFloat() < this.blockDropChance) {
               this.entity.world.destroyBlock(pos, true);
            } else {
               this.entity.world.setBlockToAir(pos);
            }

            if (this.byteSendDelay <= 0) {
               this.entity.world.setEntityState(this.entity, (byte)13);
               this.byteSendDelay = 60;
            }
         }
      }
   }
}
