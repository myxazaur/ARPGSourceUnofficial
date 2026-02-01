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
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CryoGunEntity extends EntityThrowable {
   public final ItemStack weaponstack;
   public int livetime = 30;
   ResourceLocation snow = new ResourceLocation("arpg:textures/snowflake3.png");
   ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");

   public CryoGunEntity(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.CRYOGUN);
   }

   public CryoGunEntity(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.CRYOGUN);
   }

   public CryoGunEntity(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.CRYOGUN);
   }

   public CryoGunEntity(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.5;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.5;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.5;
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > this.livetime) {
         this.expl();
      }

      this.world.setEntityState(this, (byte)8);
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.cryo_gun_impact,
               SoundCategory.AMBIENT,
               0.7F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );

         for (int ss = 0; ss < 8; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               this.largecloud,
               0.8F + (float)this.rand.nextGaussian() / 8.0F,
               -0.003F,
               20 + this.rand.nextInt(10),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 16.0F,
               (float)this.rand.nextGaussian() / 16.0F,
               (float)this.rand.nextGaussian() / 16.0F,
               0.75F + this.rand.nextFloat() / 10.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaGlowing = true;
            bigsmoke.alphaTickAdding = -0.028F;
            this.world.spawnEntity(bigsmoke);
         }

         for (int ss = 0; ss < this.rand.nextInt(4) + 1; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               this.snow,
               0.3F + (float)this.rand.nextGaussian() / 15.0F,
               0.02F,
               50,
               150,
               this.world,
               this.prevPosX,
               this.prevPosY,
               this.prevPosZ,
               (float)this.rand.nextGaussian() / 10.0F,
               (float)this.rand.nextGaussian() / 10.0F,
               (float)this.rand.nextGaussian() / 10.0F,
               0.9F + this.rand.nextFloat() / 10.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360),
               true,
               1.6F
            );
            bigsmoke.alphaTickAdding = -0.02F;
            bigsmoke.dropMode = true;
            this.world.spawnEntity(bigsmoke);
         }
      }

      if (id == 8) {
         GUNParticle snow2 = new GUNParticle(
            this.snow,
            0.15F + (float)this.rand.nextGaussian() / 25.0F,
            -0.005F,
            10 + this.rand.nextInt(5),
            150,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            0.9F + this.rand.nextFloat() / 10.0F,
            1.0F,
            1.0F,
            true,
            this.rand.nextInt(100) - 50
         );
         snow2.alphaTickAdding = -0.07F;
         this.world.spawnEntity(snow2);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
            this.expl();
         }
      } else if (this.world
            .getBlockState(result.getBlockPos())
            .getBlock()
            .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
         != null) {
         this.expl();
      }
   }

   public void expl() {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (!this.world.isRemote) {
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
                  int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
                  int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
                  Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.frost),
                     parameters.getEnchanted("damage", might),
                     this.getThrower(),
                     entity,
                     true,
                     parameters.getEnchanted("knockback", impulse),
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
                  if (entity instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                     PotionEffect lastdebaff = Weapons.mixPotion(
                        entitylivingbase,
                        PotionEffects.FREEZING,
                        (float)parameters.getEnchantedi("potion_time_add", witchery),
                        (float)parameters.geti("potion_power_add"),
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumMathOperation.PLUS,
                        Weapons.EnumMathOperation.PLUS,
                        parameters.getEnchantedi("potion_time_max", witchery),
                        parameters.getEnchantedi("potion_power_max", witchery)
                     );
                     Freezing.tryPlaySound(entitylivingbase, lastdebaff);
                     if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.4 && entitylivingbase.deathTime < 1) {
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING);
                     }
                  }
               }
            }
         }

         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }
   }
}
