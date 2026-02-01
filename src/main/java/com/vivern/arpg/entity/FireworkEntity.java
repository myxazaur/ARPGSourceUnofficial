package com.vivern.arpg.entity;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FireworkEntity extends EntityThrowable {
   public final ItemStack weaponstack;
   ResourceLocation sparkle = new ResourceLocation("arpg:textures/firework_sparkle.png");
   float grav = (float)this.rand.nextGaussian() * 0.0015F;
   float redh = 1.0F - (float)this.rand.nextGaussian() / 10.0F;
   float greenh = 1.0F - (float)this.rand.nextGaussian() / 2.0F;
   float blueh = 1.0F - (float)this.rand.nextGaussian() / 2.0F;
   int lt = this.rand.nextInt(3);
   float motiX = (float)this.rand.nextGaussian() / 70.0F;
   float motiY = (float)this.rand.nextGaussian() / 70.0F;
   float motiZ = (float)this.rand.nextGaussian() / 70.0F;
   public float scaleYX = 0.04F + this.rand.nextFloat() / 20.0F;
   public float scaleZZ = 0.04F + this.rand.nextFloat() / 20.0F;
   public float redR = 1.0F - (float)this.rand.nextGaussian();
   public float greenR = 1.0F - (float)this.rand.nextGaussian();
   public float blueR = 1.0F - (float)this.rand.nextGaussian();
   public static final DataParameter<Integer> FIREWORK_SIZE = EntityDataManager.createKey(FireworkEntity.class, DataSerializers.VARINT);

   public FireworkEntity(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.FIREWORKSLAUN);
   }

   public FireworkEntity(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.FIREWORKSLAUN);
   }

   public FireworkEntity(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.FIREWORKSLAUN);
   }

   public FireworkEntity(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   protected void entityInit() {
      this.dataManager.register(FIREWORK_SIZE, 1);
   }

   public int getFireworkSize() {
      return (Integer)this.dataManager.get(FIREWORK_SIZE);
   }

   public void setFireworkSize(int value) {
      this.dataManager.set(FIREWORK_SIZE, value);
   }

   protected float getGravityVelocity() {
      return 1.0E-4F + this.grav;
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
         float red = 1.0F - (float)this.rand.nextGaussian();
         float green = 1.0F - (float)this.rand.nextGaussian();
         float blue = 1.0F - (float)this.rand.nextGaussian();
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.firework_explode,
               SoundCategory.AMBIENT,
               1.5F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
         int countOfParticles = 30 + this.getFireworkSize();
         float R = 0.08F + 0.008F * this.getFireworkSize() + (float)this.rand.nextGaussian() / 30.0F;

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

            GUNParticle particle = new GUNParticle(
               this.sparkle,
               0.07F + (float)this.rand.nextGaussian() / 30.0F,
               0.005F,
               22 + this.rand.nextInt(6),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               X,
               Y,
               Z,
               red - (float)this.rand.nextGaussian() / 10.0F,
               green - (float)this.rand.nextGaussian() / 10.0F,
               blue - (float)this.rand.nextGaussian() / 10.0F,
               true,
               0
            );
            particle.alphaGlowing = true;
            this.world.spawnEntity(particle);
         }
      }

      if (id == 14) {
         GUNParticle fire = new GUNParticle(
            this.sparkle,
            0.06F + (float)this.rand.nextGaussian() / 30.0F,
            0.005F,
            7,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            this.redh - (float)this.rand.nextGaussian() / 10.0F,
            this.greenh - (float)this.rand.nextGaussian() / 10.0F,
            this.blueh - (float)this.rand.nextGaussian() / 10.0F,
            true,
            0
         );
         fire.alphaGlowing = true;
         this.world.spawnEntity(fire);
      }
   }

   public void onUpdate() {
      super.onUpdate();
      this.motionX = this.motionX + this.motiX;
      this.motionY = this.motionY + this.motiY;
      this.motionZ = this.motionZ + this.motiZ;
      if (this.ticksExisted > 14 + this.lt && !this.world.isRemote) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         double minRadius = parameters.getEnchanted("min_damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
         double damageRadius = minRadius + this.getFireworkSize() / 7.5;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
                  int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
                  float flyBonus = 0.0F;
                  if (!entity.onGround) {
                     flyBonus = parameters.getEnchanted("flying_damage_bonus", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack));
                  }

                  Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                     parameters.getEnchanted("damage", might) + flyBonus,
                     this.getThrower(),
                     entity,
                     true,
                     parameters.getEnchanted("knockback", impulse),
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
               }
            }
         }

         this.world.setEntityState(this, (byte)15);
         this.setDead();
      }

      this.world.setEntityState(this, (byte)14);
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != this.thrower && !this.world.isRemote) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         double minRadius = parameters.getEnchanted("min_damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
         double damageRadius = minRadius + this.getFireworkSize() / 7.5;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
                  int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
                  float flyBonus = 0.0F;
                  if (!entity.onGround) {
                     flyBonus = parameters.getEnchanted("flying_damage_bonus", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack));
                  }

                  Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                     parameters.getEnchanted("damage", might) + flyBonus,
                     this.getThrower(),
                     entity,
                     true,
                     parameters.getEnchanted("knockback", impulse),
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
               }
            }
         }

         this.world.setEntityState(this, (byte)15);
         this.setDead();
      }
   }
}
