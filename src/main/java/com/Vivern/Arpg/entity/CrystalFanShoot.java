package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystalFanShoot extends EntityThrowable {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   boolean powerOn;
   int randomSound = 0;
   public float damage;
   public boolean canSpawnBonus = true;
   public float soundAndParticleChance = 1.0F;
   static ResourceLocation tex = new ResourceLocation("arpg:textures/spellblue3.png");
   static ResourceLocation shard1 = new ResourceLocation("arpg:textures/glass_shard1.png");
   static ResourceLocation shard2 = new ResourceLocation("arpg:textures/glass_shard2.png");
   static ResourceLocation shard3 = new ResourceLocation("arpg:textures/glass_shard3.png");
   public static ResourceLocation[] shards = new ResourceLocation[]{shard1, shard2, shard3};

   public CrystalFanShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.CRYSTALFAN);
      this.powerOn = false;
   }

   public CrystalFanShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.CRYSTALFAN);
      this.powerOn = false;
   }

   public CrystalFanShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.CRYSTALFAN);
      this.powerOn = false;
   }

   public CrystalFanShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float power, boolean powerOn) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
      this.powerOn = powerOn;
      this.randomSound = this.rand.nextInt(4);
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.8;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.8;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.4;
      }
   }

   protected float getGravityVelocity() {
      return 0.01F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 35) {
         this.setDead();
      }

      if (this.powerOn && this.ticksExisted == this.randomSound) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.crystal_fan,
               SoundCategory.AMBIENT,
               0.5F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.glass_break,
               SoundCategory.AMBIENT,
               0.55F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );

         for (int ss = 0; ss < this.rand.nextInt(5) + 4; ss++) {
            GUNParticle part = new GUNParticle(
               shards[this.rand.nextInt(3)],
               0.06F + this.rand.nextFloat() / 30.0F,
               0.08F,
               30 + this.rand.nextInt(20),
               -1,
               this.world,
               this.lastTickPosX,
               this.lastTickPosY,
               this.lastTickPosZ,
               (float)this.rand.nextGaussian() / 13.0F,
               (float)this.rand.nextGaussian() / 13.0F + 0.1F,
               (float)this.rand.nextGaussian() / 13.0F,
               0.9F,
               0.3F,
               1.0F,
               false,
               this.rand.nextInt(360),
               true,
               1.2F
            );
            part.dropMode = true;
            part.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
            part.tracker = new ParticleTracker.TrackerGlassShard(
               (float)this.rand.nextGaussian() * 2.0F,
               (float)this.rand.nextGaussian() * 2.0F,
               (int)this.rand.nextGaussian() * 2,
               false
            );
            this.world.spawnEntity(part);
         }
      }

      if (id == 9) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.glass_break,
               SoundCategory.AMBIENT,
               0.55F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );

         for (int ss = 0; ss < this.rand.nextInt(3) + 3; ss++) {
            GUNParticle part = new GUNParticle(
               shards[this.rand.nextInt(3)],
               0.06F + this.rand.nextFloat() / 30.0F,
               0.08F,
               30 + this.rand.nextInt(20),
               -1,
               this.world,
               this.lastTickPosX,
               this.lastTickPosY,
               this.lastTickPosZ,
               (float)this.rand.nextGaussian() / 13.0F,
               (float)this.rand.nextGaussian() / 13.0F + 0.1F,
               (float)this.rand.nextGaussian() / 13.0F,
               0.9F,
               0.3F,
               1.0F,
               false,
               this.rand.nextInt(360),
               true,
               1.2F
            );
            part.dropMode = true;
            part.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
            part.tracker = new ParticleTracker.TrackerGlassShard(
               (float)this.rand.nextGaussian() * 2.0F,
               (float)this.rand.nextGaussian() * 2.0F,
               (int)this.rand.nextGaussian() * 2,
               false
            );
            this.world.spawnEntity(part);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.shards),
               this.damage * this.magicPower,
               this.getThrower(),
               result.entityHit,
               true,
               (this.powerOn ? 2.0F : 0.7F) + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 2.0F,
               this.posX,
               this.posY,
               this.posZ
            );
            result.entityHit.hurtResistantTime = 0;
            if (result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               if (entitylivingbase.getHealth() <= 0.0F) {
                  if (!this.powerOn) {
                     if (this.canSpawnBonus
                        && entitylivingbase.deathTime < 1
                        && this.rand.nextFloat() < 0.2F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, this.weaponstack) / 10.0F) {
                        CrystalFanBonus projectile = new CrystalFanBonus(this.world, entitylivingbase);
                        this.world.spawnEntity(projectile);
                     }
                  } else if (this.rand.nextFloat() < 0.95) {
                     DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_CUT);
                  }
               }

               if (this.rand.nextFloat() < this.soundAndParticleChance) {
                  this.world.setEntityState(this, (byte)(this.powerOn ? 9 : 8));
               }

               this.setDead();
            }
         }
      } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null
         && !this.world.isRemote) {
         if (this.rand.nextFloat() < this.soundAndParticleChance) {
            this.world.setEntityState(this, (byte)(this.powerOn ? 9 : 8));
         }

         this.setDead();
      }
   }
}
