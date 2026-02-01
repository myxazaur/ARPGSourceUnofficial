package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.Freezing;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.GUNParticle;
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
import net.minecraft.world.World;

public class EntityMinigunIcicle extends EntityThrowable implements IEntitySynchronize {
   public final ItemStack weaponstack;
   public int livetime = 10;
   ResourceLocation snow = new ResourceLocation("arpg:textures/shards.png");
   ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");

   public EntityMinigunIcicle(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.ICICLEMINIGUN);
   }

   public EntityMinigunIcicle(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.ICICLEMINIGUN);
   }

   public EntityMinigunIcicle(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.ICICLEMINIGUN);
   }

   public EntityMinigunIcicle(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.3;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.3;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.3;
      }
   }

   protected float getGravityVelocity() {
      return 0.001F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > this.livetime) {
         this.setDead();
      }
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      for (int ss = 0; ss < 2; ss++) {
         GUNParticle bigsmoke = new GUNParticle(
            this.largecloud,
            0.3F + (float)this.rand.nextGaussian() / 14.0F,
            -0.003F,
            20,
            240,
            this.world,
            x,
            y,
            z,
            (float)this.rand.nextGaussian() / 16.0F,
            (float)this.rand.nextGaussian() / 16.0F,
            (float)this.rand.nextGaussian() / 16.0F,
            0.45F + this.rand.nextFloat() / 10.0F,
            0.8F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke.alphaGlowing = true;
         bigsmoke.alphaTickAdding = -0.05F;
         this.world.spawnEntity(bigsmoke);
      }

      for (int ss = 0; ss < this.rand.nextInt(2) + 1; ss++) {
         GUNParticle bigsmoke = new GUNParticle(
            this.snow,
            0.2F + (float)this.rand.nextGaussian() / 15.0F,
            0.025F,
            30,
            150,
            this.world,
            x,
            y,
            z,
            (float)this.rand.nextGaussian() / 15.0F,
            (float)this.rand.nextGaussian() / 15.0F,
            (float)this.rand.nextGaussian() / 15.0F,
            0.6F + this.rand.nextFloat() / 10.0F,
            0.9F,
            1.0F,
            true,
            this.rand.nextInt(360),
            true,
            1.6F
         );
         bigsmoke.alphaTickAdding = -0.03F;
         bigsmoke.dropMode = true;
         this.world.spawnEntity(bigsmoke);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
            float bdamage = parameters.getEnchanted("damage", might);
            if (result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               boolean active = Freezing.canImmobilizeEntity(entitylivingbase, entitylivingbase.getActivePotionEffect(PotionEffects.FREEZING));
               if (active) {
                  bdamage += parameters.getEnchanted("icebreak_damage", might);
               }

               if (active && entitylivingbase.getHealth() <= 0.0F) {
                  DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING);
               }
            }

            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.bullet),
               bdamage,
               this.getThrower(),
               result.entityHit,
               true,
               parameters.getEnchanted("knockback", impulse)
            );
            result.entityHit.hurtResistantTime = 0;
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.bullet,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            this.setDead();
         }
      } else if (this.world
            .getBlockState(result.getBlockPos())
            .getBlock()
            .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
         != null) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.bullet,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (!this.world.isRemote) {
            if (result.hitVec != null) {
               IEntitySynchronize.sendSynchronize(
                  this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z, 0.0, 0.0, 0.0
               );
            }

            this.setDead();
         }
      }
   }
}
