package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ThornkeeperShoot extends EntityThrowable {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public int impacts = 0;
   static ResourceLocation tex = new ResourceLocation("arpg:textures/shard.png");
   public boolean isMana = false;

   public ThornkeeperShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.THORNKEEPER);
   }

   public ThornkeeperShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.THORNKEEPER);
   }

   public ThornkeeperShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.THORNKEEPER);
   }

   public ThornkeeperShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX;
      this.motionZ = this.motionZ + entityThrower.motionZ;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY;
      }
   }

   protected float getGravityVelocity() {
      return this.isInWater() ? 0.0F : 0.1F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 280) {
         this.setDead();
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         for (int ss = 0; ss < this.rand.nextInt(8) + 14; ss++) {
            GUNParticle part = new GUNParticle(
               tex,
               0.04F + this.rand.nextFloat() / 30.0F,
               0.07F,
               20 + this.rand.nextInt(10),
               -1,
               this.world,
               this.lastTickPosX,
               this.lastTickPosY,
               this.lastTickPosZ,
               (float)this.rand.nextGaussian() / 16.0F,
               (float)this.rand.nextGaussian() / 16.0F + 0.1F,
               (float)this.rand.nextGaussian() / 16.0F,
               0.9F,
               0.7F,
               1.0F,
               false,
               this.rand.nextInt(360),
               true,
               3.0F
            );
            this.world.spawnEntity(part);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit)
            && Weapons.canDealDamageTo(result.entityHit)
            && !this.world.isRemote) {
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.pierce),
               (3.0F * WeaponParameters.EXlevelSEA_MIDDLE + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack) / 2.0F) * this.magicPower,
               this.thrower,
               result.entityHit,
               true,
               1.5F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 2.0F,
               this.posX,
               this.posY,
               this.posZ
            );
            result.entityHit.hurtResistantTime = 0;
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.normal_projectile,
                  SoundCategory.AMBIENT,
                  0.7F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            if (this.isMana && this.getThrower() != null && this.getThrower() instanceof EntityPlayer) {
               Mana.giveMana((EntityPlayer)this.getThrower(), 3.0F);
            }

            this.impacts++;
            if (this.impacts > 2) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
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
               Sounds.normal_projectile,
               SoundCategory.AMBIENT,
               0.7F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
            this.motionY *= -0.95;
         }

         if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
            this.motionZ = -this.motionZ;
         }

         if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
            this.motionX = -this.motionX;
         }
      }
   }
}
