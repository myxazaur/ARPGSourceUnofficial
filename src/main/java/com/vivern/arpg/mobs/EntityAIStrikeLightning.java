package com.vivern.arpg.mobs;

import com.vivern.arpg.entity.LightningStrike;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIStrikeLightning extends EntityAIBase {
   public AbstractMob entity;
   public int attackTimer = 0;
   public int maxCooldown = 30;
   public float radiusHigher = 0.0F;
   public float radiusLower = 0.0F;
   public float damage = 0.0F;
   public boolean shouldSee = false;
   public boolean vanillaLightning = true;
   public SoundEvent soundOnCharge = null;
   public int chargeMaxTime = 30;
   public int chargeCurrentTime = 0;
   public int triggeredAnim = -2;
   public int lastSeeTime = 0;
   public int seeMemory = 60;
   public PotionEffect shock;
   public int maxHeight = 20;
   public int minHeight = 10;

   public EntityAIStrikeLightning(
      AbstractMob entity,
      int maxCooldown,
      float radiusHigher,
      float radiusLower,
      float damage,
      int chargeMaxTime,
      boolean shouldSee,
      boolean vanillaLightning,
      int seeMemory
   ) {
      this.entity = entity;
      this.maxCooldown = maxCooldown;
      this.radiusHigher = radiusHigher;
      this.radiusLower = radiusLower;
      this.damage = damage;
      this.shouldSee = shouldSee;
      this.vanillaLightning = vanillaLightning;
      this.chargeMaxTime = chargeMaxTime;
      this.seeMemory = seeMemory;
   }

   public EntityAIStrikeLightning setPotionEffect(PotionEffect shock) {
      this.shock = shock;
      return this;
   }

   public EntityAIStrikeLightning setSoundOnCharge(SoundEvent soundOnCharge) {
      this.soundOnCharge = soundOnCharge;
      return this;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      this.attackTimer--;
      if (attackTarg != null) {
         if (this.attackTimer <= 0) {
            double dist = this.entity.getDistance(attackTarg);
            if (dist < this.radiusHigher && dist > this.radiusLower) {
               if (this.shouldSee) {
                  if (this.entity.getEntitySenses().canSee(attackTarg) && attackTarg.isEntityAlive()) {
                     if (this.lastSeeTime < this.seeMemory) {
                        this.lastSeeTime++;
                     }
                  } else if (this.lastSeeTime > 0) {
                     this.lastSeeTime--;
                  }

                  if (this.lastSeeTime > 0) {
                     this.chargeCurrentTime++;
                     if (this.chargeCurrentTime == 1) {
                        if (this.soundOnCharge != null) {
                           this.entity.playSound(this.soundOnCharge, 1.7F, 0.9F + this.entity.getRNG().nextFloat() / 5.0F);
                        }

                        this.entity.triggerAnimation(this.triggeredAnim);
                     }

                     if (this.chargeCurrentTime >= this.chargeMaxTime) {
                        this.chargeCurrentTime = 0;
                        this.attackTimer = this.maxCooldown;
                        if (this.vanillaLightning) {
                           EntityLightningBolt bolt = new EntityLightningBolt(
                              this.entity.world, attackTarg.posX, attackTarg.posY, attackTarg.posZ, false
                           );
                           this.entity.world.addWeatherEffect(bolt);
                        } else {
                           double stposX = attackTarg.posX + this.entity.getRNG().nextGaussian();
                           double stposZ = attackTarg.posZ + this.entity.getRNG().nextGaussian();
                           int actY = this.actualHeight(this.entity.world, (int)stposX, (int)attackTarg.posY, (int)stposZ);
                           LightningStrike bolt = new LightningStrike(this.entity.world, stposX, actY, stposZ, this.entity, attackTarg.getPositionVector());
                           bolt.damage = this.damage;
                           if (this.shock != null) {
                              bolt.shock = this.shock;
                           }

                           this.entity.world.spawnEntity(bolt);
                        }
                     }
                  } else if (this.chargeCurrentTime > 0) {
                     this.chargeCurrentTime--;
                  }
               } else {
                  this.chargeCurrentTime++;
                  if (this.chargeCurrentTime == 1) {
                     if (this.soundOnCharge != null) {
                        this.entity.playSound(this.soundOnCharge, 1.5F, 0.9F + this.entity.getRNG().nextFloat() / 5.0F);
                     }

                     this.entity.triggerAnimation(this.triggeredAnim);
                  }

                  if (this.chargeCurrentTime >= this.chargeMaxTime) {
                     this.chargeCurrentTime = 0;
                     this.attackTimer = this.maxCooldown;
                     if (this.vanillaLightning) {
                        EntityLightningBolt bolt = new EntityLightningBolt(
                           this.entity.world, attackTarg.posX, attackTarg.posY, attackTarg.posZ, false
                        );
                        this.entity.world.addWeatherEffect(bolt);
                     } else {
                        double stposX = attackTarg.posX + this.entity.getRNG().nextGaussian();
                        double stposZ = attackTarg.posZ + this.entity.getRNG().nextGaussian();
                        int actY = this.actualHeight(this.entity.world, (int)stposX, (int)attackTarg.posY, (int)stposZ);
                        LightningStrike bolt = new LightningStrike(this.entity.world, stposX, actY, stposZ, this.entity, attackTarg.getPositionVector());
                        bolt.damage = this.damage;
                        if (this.shock != null) {
                           bolt.shock = this.shock;
                        }

                        this.entity.world.spawnEntity(bolt);
                     }
                  }
               }
            } else if (this.chargeCurrentTime > 0) {
               this.chargeCurrentTime--;
            }
         } else {
            this.chargeCurrentTime = 0;
         }
      }
   }

   public int actualHeight(World world, int x, int y, int z) {
      boolean hasCollisions = false;

      for (int yy = y + this.minHeight; yy <= y + this.maxHeight; yy++) {
         BlockPos pos = new BlockPos(x, yy, z);
         if (world.getBlockState(pos).getCollisionBoundingBox(world, pos) == null) {
            if (world.getBlockState(pos.up()).getCollisionBoundingBox(world, pos.up()) != null) {
               return yy;
            }
         } else {
            hasCollisions = true;
         }
      }

      return hasCollisions ? y + this.minHeight : y + this.maxHeight;
   }
}
