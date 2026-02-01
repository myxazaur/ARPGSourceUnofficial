package com.vivern.arpg.entity;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DragonFireworkEntity extends EntityThrowable {
   public final ItemStack weaponstack;
   ResourceLocation sparkle = new ResourceLocation("arpg:textures/firework_sparkle.png");
   ResourceLocation sparkle2 = new ResourceLocation("arpg:textures/sparkle.png");
   ResourceLocation body = new ResourceLocation("arpg:textures/dragon_body.png");
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   public final float fRotationYaw;
   public final float fRotationPitch;
   float fdamage = 2.0F;
   float fknockback = 0.5F;

   public DragonFireworkEntity(World world) {
      super(world);
      this.fRotationPitch = 0.0F;
      this.fRotationYaw = 0.0F;
      this.weaponstack = new ItemStack(ItemsRegister.FIREWORKSLAUN);
   }

   public DragonFireworkEntity(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.fRotationPitch = thrower.rotationPitch;
      this.fRotationYaw = thrower.rotationYaw;
      this.weaponstack = new ItemStack(ItemsRegister.FIREWORKSLAUN);
   }

   public DragonFireworkEntity(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.fRotationPitch = 0.0F;
      this.fRotationYaw = 0.0F;
      this.weaponstack = new ItemStack(ItemsRegister.FIREWORKSLAUN);
   }

   public DragonFireworkEntity(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.fRotationPitch = thrower.rotationPitch;
      this.fRotationYaw = thrower.rotationYaw;
      this.weaponstack = itemstack;
      this.fdamage = 10.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack) * 1.5F;
      this.fknockback = 5.5F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.02;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.02;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.02;
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 15) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.firework_dragon_explode,
               SoundCategory.AMBIENT,
               2.0F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );

         for (int ss = 0; ss < 15; ss++) {
            Entity bigsmoke = new GUNParticle(
               this.largesmoke,
               0.9F,
               0.0F,
               14,
               80,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 20.0F,
               (float)this.rand.nextGaussian() / 20.0F,
               (float)this.rand.nextGaussian() / 20.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               0
            );
            this.world.spawnEntity(bigsmoke);
            GunPEmitter emi = new GunPEmitter(
               this.sparkle2,
               0.3F,
               0.06F,
               30,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 2.0F,
               (float)this.rand.nextGaussian() / 2.0F + 1.5F,
               (float)this.rand.nextGaussian() / 2.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               1,
               false,
               this.largesmoke,
               0.45F,
               0.0F,
               8,
               100,
               100.0F,
               100.0F,
               100.0F,
               1.0F,
               1.0F,
               1.0F,
               true
            );
            this.world.spawnEntity(emi);
         }

         int countOfParticles = 50 + this.rand.nextInt(10);
         float R = 0.4F + (float)this.rand.nextGaussian() / 30.0F;

         for (int i = 0; i < countOfParticles; i++) {
            float rand1 = this.rand.nextFloat() * 2.0F - 1.0F;
            float rand2 = this.rand.nextFloat() * 2.0F - 1.0F;
            float X = rand1 * R;
            float new_R = (float)Math.sqrt(R * R - X * X);
            float Y = rand2 * new_R;
            float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
            if (this.rand.nextBoolean()) {
               Z *= -1.0F;
            }

            Entity particle = new GUNParticle(
               this.sparkle,
               0.15F + (float)this.rand.nextGaussian() / 30.0F,
               0.005F,
               22 + this.rand.nextInt(16),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               X + (float)this.rand.nextGaussian() / 20.0F,
               Y + (float)this.rand.nextGaussian() / 20.0F,
               Z + (float)this.rand.nextGaussian() / 20.0F,
               1.0F - (float)this.rand.nextGaussian() / 10.0F,
               0.5F - (float)this.rand.nextGaussian() / 10.0F,
               (float)this.rand.nextGaussian() / 10.0F,
               true,
               0
            );
            this.world.spawnEntity(particle);
         }
      }

      if (id == 14) {
         double d1 = this.lastTickPosX - this.posX;
         double d2 = this.lastTickPosY - this.posY;
         double d3 = this.lastTickPosZ - this.posZ;
         float dist = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
         float prunex = (float)(d1 / dist / 5.0);
         float pruney = (float)(d2 / dist / 5.0);
         float prunez = (float)(d3 / dist / 5.0);

         for (int ss = 0; ss < 3; ss++) {
            Entity spelll = new GUNParticle(
               this.sparkle2,
               0.07F + (float)this.rand.nextGaussian() / 30.0F,
               0.02F,
               14,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               prunex + (float)this.rand.nextGaussian() / 15.0F,
               pruney + (float)this.rand.nextGaussian() / 15.0F,
               prunez + (float)this.rand.nextGaussian() / 15.0F,
               1.0F,
               0.8F + (float)this.rand.nextGaussian() / 3.0F,
               1.0F,
               true,
               0
            );
            this.world.spawnEntity(spelll);
         }

         Entity spel = new EntityCubicParticle(
            this.body,
            0.03F,
            0.0F,
            15,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            this.rand.nextInt(180),
            this.rand.nextInt(180),
            this.rand.nextInt(180),
            0.0F,
            false,
            -0.002F
         );
         this.world.spawnEntity(spel);
      }
   }

   public void onUpdate() {
      super.onUpdate();
      float rotationYawIn = this.fRotationYaw;
      float rotationPitchIn = this.fRotationPitch + 90.0F;
      float velocity = (float)(0.2F * Math.sin(this.ticksExisted));
      float x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float y = -MathHelper.sin(rotationPitchIn * (float) (Math.PI / 180.0));
      float z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f = MathHelper.sqrt(x * x + y * y + z * z);
      x /= f;
      y /= f;
      z /= f;
      x *= velocity;
      y *= velocity;
      z *= velocity;
      this.motionX += x;
      this.motionY += y;
      this.motionZ += z;
      if (this.ticksExisted > 100 && !this.world.isRemote) {
         this.expl();
      }

      this.world.setEntityState(this, (byte)14);
   }

   public void expl() {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double damageRadius = parameters.getEnchanted("dragon_damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.getThrower(), axisalignedbb);
      Explosion explosion = new Explosion(
         this.world,
         null,
         this.posX,
         this.posY,
         this.posZ,
         parameters.getEnchanted("explode_size", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack)),
         true,
         true
      );
      explosion.doExplosionA();
      explosion.doExplosionB(true);
      if (!list.isEmpty()) {
         for (Entity entity : list) {
            if (Team.checkIsOpponent(this.getThrower(), entity)) {
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                  parameters.getEnchanted("damage_dragon", might),
                  this.getThrower(),
                  entity,
                  true,
                  parameters.getEnchanted("knockback_dragon", impulse),
                  this.posX,
                  this.posY - 1.0,
                  this.posZ
               );
               entity.hurtResistantTime = 0;
            } else {
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
               SuperKnockback.applyKnockback(
                  entity, parameters.getEnchanted("knockback_dragon", impulse), this.posX, this.posY - 1.0, this.posZ
               );
            }
         }
      }

      this.world.setEntityState(this, (byte)15);
      this.setDead();
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != this.getThrower() && !this.world.isRemote) {
         this.expl();
      }
   }
}
