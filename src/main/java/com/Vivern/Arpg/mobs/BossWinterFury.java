//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.BloodType;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.BossInfo.Color;

public class BossWinterFury extends AbstractBoss {
   public float rotateDirectPitch = 0.0F;
   public float rotateDirectYaw = 0.0F;
   public int roarCooldown = 0;
   public int winterBreathTick = 0;
   public int cursedAttackTick = 0;
   public int cursedAttackCooldown = 0;
   public boolean phase = false;
   public boolean burnMode = false;
   public float burnDamage = 0.0F;
   public float burnDamageNeed = 80.0F;

   public BossWinterFury(World world) {
      super(world, 1.5F, 1.5F, Color.BLUE);
      this.hurtSound = Sounds.mob_ice_hurt;
      this.deathSound = Sounds.winter_fury_dead;
      this.defaultteam = EverfrostMobsPack.mobsteam;
      this.setNoGravity(true);
      this.setattributes(325.0, 84.0, 10.0, 0.25, 3 + 2 * this.getMobDifficulty(), 3.0, 0.4, 0.5, 0.0, 0.2);
      this.registerLOOT(
         new MobDrop[]{
            new MobDrop(ItemsRegister.WINTERSCALE, 1.0F, 0, 2, 4, 0),
            new MobDrop(ItemsRegister.WINTERSCALE, 1.0F, 0, 2, 4, 0),
            new MobDrop(ItemsRegister.WINTERSCALE, 1.0F, 0, 2, 4, 0),
            new MobDrop(ItemsRegister.WINTERSCALE, 1.0F, 0, 2, 4, 0),
            new MobDrop(ItemsRegister.HAILTEAR, 1.0F, 0, 2, 6, 2),
            new MobDrop(ItemsRegister.HAILTEAR, 1.0F, 0, 2, 6, 2),
            new MobDrop(ItemsRegister.HAILTEAR, 1.0F, 0, 2, 6, 2)
         }
      );
      this.var2 = 0.0F;
      this.var3 = 0.0F;
      this.var4 = 0.0F;
      this.var5 = 0.0F;
      this.setDropMoney(50, 100, 1.0F);
      this.experienceValue = 300;
   }

   @Override
   public void dropShards() {
      if (this.rand.nextFloat() < 0.8) {
         ShardType.spawnShards(ShardType.POISON, this, 5.0F * dropShardsQuantity);
      }

      if (this.rand.nextFloat() < 0.8) {
         ShardType.spawnShards(ShardType.AIR, this, 5.0F * dropShardsQuantity);
      }

      if (this.rand.nextFloat() < 0.8) {
         ShardType.spawnShards(ShardType.WATER, this, 12.0F * dropShardsQuantity);
      }

      if (this.rand.nextFloat() < 0.9) {
         ShardType.spawnShards(ShardType.COLD, this, 5.0F * dropShardsQuantity);
      }
   }

   @Override
   public BloodType getBloodType() {
      return DeathEffects.ICE_BLOOD;
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound compound) {
      super.writeEntityToNBT(compound);
      compound.setBoolean("phase", this.phase);
      compound.setBoolean("burnMode", this.burnMode);
      compound.setFloat("burndamage", this.burnDamage);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound compound) {
      super.readEntityFromNBT(compound);
      if (compound.hasKey("phase")) {
         this.phase = compound.getBoolean("phase");
      }

      if (compound.hasKey("burnMode")) {
         this.burnMode = compound.getBoolean("burnMode");
      }

      if (compound.hasKey("burndamage")) {
         this.burnDamage = compound.getFloat("burndamage");
      }
   }

   public float getEyeHeight() {
      return this.height * 0.55F;
   }

   @Override
   public void shoot() {
      if (!this.world.isRemote) {
         this.winterBreathTick = 50;
         this.triggerAnimation(-2);
         this.world.setEntityState(this, (byte)9);
      }
   }

   @Override
   public void specialAttack(double x, double y, double z, byte id) {
      if (id == 1) {
         HostileProjectiles.Hailblast entity = new HostileProjectiles.Hailblast(this.world, this);
         entity.setPosition(x, y, z);
         entity.motionX = this.rand.nextGaussian() / 17.0;
         entity.motionY = this.rand.nextFloat() * 0.6 - 0.5;
         entity.motionZ = this.rand.nextGaussian() / 17.0;
         entity.damage = 8.0F;
         entity.spawnFragmentChance = 0.0F;
         entity.changeHurtResistTime = false;
         this.world.spawnEntity(entity);
      }
   }

   public void fall(float distance, float damageMultiplier) {
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.world.isRemote) {
         this.roarCooldown--;
         if (this.var1 == 2 && this.rand.nextFloat() < 0.1 && this.animations[3] <= 60) {
            this.triggerAnimation(-4);
            if (this.rand.nextFloat() < 0.2) {
               this.world.setEntityState(this, (byte)3);
            }
         }

         if (this.var1 == 1) {
            if (this.var2 < 1.0F) {
               this.var2 = (float)(this.var2 + 0.1);
            }
         } else if (this.var2 > 0.0F) {
            this.var2 = (float)(this.var2 - 0.1);
         }

         if (this.var1 == 3) {
            if (this.animations[3] <= 83) {
               this.triggerAnimation(-4);
            }

            if (this.var3 < 1.0F) {
               this.var3 = (float)(this.var3 + 0.1);
            }
         } else if (this.var3 > 0.0F) {
            this.var3 = (float)(this.var3 - 0.1);
         }
      }

      if (this.var1 == 2) {
         float fun = (MathHelper.wrapDegrees(this.rotateDirectYaw) - MathHelper.wrapDegrees(this.rotationYaw) + 360.0F) % 360.0F;
         float derection;
         if (fun < 180.0F) {
            derection = MathHelper.clamp(Math.abs(fun), 0.0F, 10.0F);
         } else {
            derection = -MathHelper.clamp(Math.abs(fun), 0.0F, 10.0F);
         }

         this.rotationYaw += derection;
         fun = (MathHelper.wrapDegrees(this.rotateDirectPitch) - MathHelper.wrapDegrees(this.rotationPitch) + 360.0F) % 360.0F;
         if (fun < 180.0F) {
            derection = Math.min(4.0F, Math.abs(fun));
         } else {
            derection = -Math.min(4.0F, Math.abs(fun));
         }

         this.rotationPitch += derection;
         Vec3d vecpw = GetMOP.Vec3dToPitchYaw(new Vec3d(-this.motionX, -this.motionY, -this.motionZ));
         this.rotateDirectPitch = (float)vecpw.x;
         this.rotateDirectYaw = (float)vecpw.y;
      }

      if (!this.world.isRemote) {
         this.cursedAttackCooldown--;
         if (this.phase && this.var1 == 3 && this.cursedAttackCooldown <= 0) {
            this.cursedAttackTick = 60;
            this.cursedAttackCooldown = 200 + this.rand.nextInt(100);
            this.world.setEntityState(this, (byte)4);
            this.triggerAnimation(-3);
         }

         if (this.phase && this.var1 == 2) {
            if (!this.noClip) {
               this.noClip = true;
               this.world.setEntityState(this, (byte)6);
            }
         } else {
            if (this.noClip) {
               this.noClip = false;
               this.world.setEntityState(this, (byte)5);
            }

            double speedSq = this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ;
            if (speedSq > 0.25) {
               AxisAlignedBB aabb = this.getEntityBoundingBox()
                  .offset(this.motionX, this.motionY < -0.7 ? this.motionY : 0.0, this.motionZ);
               List<BlockPos> list = GetMOP.getBlockPosesCollidesAABB(this.world, aabb, false);
               boolean destroys = false;
               if (!list.isEmpty()) {
                  this.motionX *= 0.84;
                  this.motionY *= 0.84;
                  this.motionZ *= 0.84;

                  for (BlockPos pos : list) {
                     if (Weapons.easyBreakBlockFor(this.world, 10.0F, pos)) {
                        this.world.destroyBlock(pos, true);
                        destroys = true;
                     }
                  }

                  if (destroys) {
                     this.world.setEntityState(this, (byte)7);
                  }
               }
            }
         }

         if (this.winterBreathTick > 0) {
            this.winterBreathTick--;
            HostileProjectiles.WinterFuryBreath entity = new HostileProjectiles.WinterFuryBreath(this.world, this);
            entity.damage = 1.5F + this.getMobDifficulty() * 0.5F;
            entity.burn = this.burnMode;
            entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.0F, 3.0F);
            this.world.spawnEntity(entity);
            if (this.winterBreathTick % 5 == 0 && this.winterBreathTick > 20) {
               this.world.setEntityState(this, (byte)8);
            }
         }

         if (this.cursedAttackTick > 0) {
            this.cursedAttackTick--;
            if (this.cursedAttackTick < 50) {
               this.attackWithCurse();
            }
         }

         if (this.ticksExisted % 15 == 0 && this.isPotionActive(PotionEffects.FREEZING)) {
            PotionEffect eff = this.getActivePotionEffect(PotionEffects.FREEZING);
            this.removePotionEffect(PotionEffects.FREEZING);
            if (this.rand.nextFloat() < 0.85 && eff.getDuration() - 10 > 20) {
               this.addPotionEffect(new PotionEffect(eff.getPotion(), eff.getDuration() - 10, eff.getAmplifier(), eff.getIsAmbient(), eff.doesShowParticles()));
            }
         }

         if (this.ticksExisted % 10 == 0
            && (this.isBurning() || this.isPotionActive(PotionEffects.DEMONIC_BURN) || this.isPotionActive(MobEffects.POISON))) {
            this.burnDamage--;
            if (this.burnDamage < -this.burnDamageNeed / 2.0F) {
               this.burnDamage = 0.0F;
               this.burnMode = false;
            }
         }
      }
   }

   public void attackWithCurse() {
      double damageRadius = 32.0;
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
      if (!list.isEmpty()) {
         for (EntityLivingBase entitylivingbase : list) {
            if (Team.checkIsOpponent(this, entitylivingbase)
               && GetMOP.entityMopRayTrace(
                     BossWinterFury.class, 48.0, 1.0F, entitylivingbase, 1.0, 0.8F, entitylivingbase.rotationPitch, entitylivingbase.rotationYaw
                  )
                  .contains(this)) {
               entitylivingbase.addPotionEffect(new PotionEffect(PotionEffects.WINTER_CURSE, 99000));
            }
         }
      }
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      boolean b = super.attackEntityFrom(source, amount);
      if (this.getHealth() < this.getMaxHealth() / 2.0F) {
         this.phase = true;
      }

      if (b) {
         if (source.getImmediateSource() != null && source.getImmediateSource() instanceof HostileProjectiles.Hailblast) {
            this.burnMode = false;
            this.burnDamage = 0.0F;
         } else {
            this.burnDamage += amount;
            if (this.burnDamage > this.burnDamageNeed) {
               this.burnMode = true;
               this.burnDamage = 0.0F;
            }
         }
      }

      return b;
   }

   @Override
   public void handleStatusUpdate(byte id) {
      super.handleStatusUpdate(id);
      if (id == 5) {
         this.noClip = false;
      }

      if (id == 6) {
         this.noClip = true;
      }

      if (id == 8) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.winter_beam,
               SoundCategory.HOSTILE,
               1.4F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      }

      if (id == 7) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               SoundEvents.ENTITY_WITHER_BREAK_BLOCK,
               SoundCategory.HOSTILE,
               1.3F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      }

      if (id >= 10 && id <= 14) {
         this.var1 = id - 10;
         if (this.rand.nextFloat() < 0.25 && this.roarCooldown <= 0) {
            this.triggerAnimation(-2);
            this.roarCooldown = 190;
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.winter_fury_roar,
                  SoundCategory.HOSTILE,
                  1.8F,
                  0.8F + this.rand.nextFloat() * 0.4F,
                  false
               );
         }
      }

      if (id == 15 && this.animations[3] <= 70) {
         this.triggerAnimation(-4);
      }

      if (id == 3 && this.roarCooldown <= 0) {
         this.triggerAnimation(-2);
         this.roarCooldown = 190;
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.winter_fury_roar,
               SoundCategory.HOSTILE,
               1.8F,
               0.8F + this.rand.nextFloat() * 0.4F,
               false
            );
      }

      if (id == 9) {
         this.triggerAnimation(-2);
         this.roarCooldown = 80;
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.winter_fury_roar,
               SoundCategory.HOSTILE,
               1.8F,
               0.8F + this.rand.nextFloat() * 0.4F,
               false
            );
      }

      if (id == 4) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.winter_curse_a,
               SoundCategory.HOSTILE,
               2.5F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      }

      if (id == 17) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.winter_fury_cast,
               SoundCategory.HOSTILE,
               2.3F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      }

      if (id == 17) {
         this.triggerAnimation(-2);
         this.roarCooldown = 60;
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.winter_fury_roar,
               SoundCategory.HOSTILE,
               1.8F,
               1.6F + this.rand.nextFloat() * 0.4F,
               false
            );
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.rrrrar,
               SoundCategory.HOSTILE,
               1.8F,
               0.9F + this.rand.nextFloat() * 0.4F,
               false
            );
      }
   }

   public boolean isPotionApplicable(PotionEffect potioneffectIn) {
      return this.burnMode && potioneffectIn.getPotion() == PotionEffects.FROSTBURN ? false : super.isPotionApplicable(potioneffectIn);
   }

   protected void initEntityAI() {
      this.tasks.addTask(1, new EntityAIDragon(this, 140, 15.0F, 5, true, 8.0F, 2.0F, false));
      this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
      this.tasks
         .addTask(3, new EntityAIProjectileStorm(this, 20.0, this.getMobDifficulty() >= 3 ? 150 : 170, 20, 6.0F, 7, 15, 10, 5, false, (byte)1, (byte)17));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 14.0F));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
      this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
   }
}
