package com.vivern.arpg.entity;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.mobs.HostileProjectiles;
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
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlasmaPistolShoot extends EntityThrowable {
   public final ItemStack weaponstack;
   public int livetime = 40;
   static ResourceLocation explode = new ResourceLocation("arpg:textures/plasma_pistol_explode.png");
   static ResourceLocation cloud = new ResourceLocation("arpg:textures/plasma_cloud.png");

   public PlasmaPistolShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.PLASMAPISTOL);
   }

   public PlasmaPistolShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.PLASMAPISTOL);
   }

   public PlasmaPistolShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.PLASMAPISTOL);
   }

   public PlasmaPistolShoot(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.4;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.4;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.4;
      }
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote && this.ticksExisted > this.livetime) {
         this.setDead();
      }

      this.world.setEntityState(this, (byte)5);
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.plasma_pistol_impact,
               SoundCategory.AMBIENT,
               0.9F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
         GUNParticle bigsmoke1 = new GUNParticle(
            explode,
            0.1F,
            0.0F,
            13,
            240,
            this.world,
            this.lastTickPosX,
            this.lastTickPosY,
            this.lastTickPosZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            0.9F + this.rand.nextFloat() / 10.0F,
            0.9F + this.rand.nextFloat() / 10.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke1.alphaTickAdding = -0.07F;
         bigsmoke1.alphaGlowing = true;
         bigsmoke1.scaleTickAdding = 0.1F;
         this.world.spawnEntity(bigsmoke1);
      }

      if (id == 5) {
         GUNParticle fire2 = new GUNParticle(
            cloud,
            0.2F,
            0.0F,
            11,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            0.5F,
            1.0F,
            0.8F + (float)this.rand.nextGaussian() / 5.0F,
            true,
            this.rand.nextInt(360)
         );
         fire2.alphaTickAdding = -0.1F;
         fire2.scaleTickAdding = -0.018F;
         fire2.alphaGlowing = true;
         this.world.spawnEntity(fire2);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && Weapons.canDealDamageTo(result.entityHit)) {
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
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
      int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (!this.world.isRemote) {
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.plasma),
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
               }
            }
         }
      }

      if (!this.world.isRemote) {
         if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0) {
            float plasmadamage = parameters.getEnchanted("damage_plasma", might);
            float plasmaknockback = parameters.getEnchanted("knockback_plasma", impulse);
            float g;
            float b;
            float r;
            if (this.rand.nextFloat() < 0.4F) {
               r = 0.625F + this.rand.nextFloat() * 0.22F;
               g = 0.4F + this.rand.nextFloat() * 0.2F;
               b = 0.93F;
            } else {
               r = 0.64F - this.rand.nextFloat() * 0.48F;
               g = 0.96F - this.rand.nextFloat() * 0.36F;
               b = 0.83F + this.rand.nextFloat() * 0.07F;
            }

            if (this.rand.nextFloat() < 0.33F) {
               float bright = 1.0F + this.rand.nextFloat() * 0.5F;
               r = Math.min(r * bright, 1.0F);
               g = Math.min(g * bright, 1.0F);
               b = Math.min(b * bright, 1.0F);
            }

            HostileProjectiles.Plasma plasma = new HostileProjectiles.Plasma(
               this.world,
               this.getThrower(),
               (float)damageRadius * 0.6F,
               0.0F,
               80,
               this.posX,
               this.posY,
               this.posZ,
               plasmadamage,
               plasmaknockback,
               2,
               false,
               r,
               g,
               b
            );
            this.world.spawnEntity(plasma);
         }

         this.world.setEntityState(this, (byte)8);
         this.setDead();
      }
   }
}
