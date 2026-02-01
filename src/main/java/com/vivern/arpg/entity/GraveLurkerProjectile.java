package com.vivern.arpg.entity;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.Weapons;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GraveLurkerProjectile extends EntityThrowable {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public boolean isCrit;
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/purple_smoke.png");

   public GraveLurkerProjectile(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.GRAVELURKER);
      this.isCrit = false;
   }

   public GraveLurkerProjectile(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.GRAVELURKER);
      this.isCrit = false;
   }

   public GraveLurkerProjectile(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.GRAVELURKER);
      this.isCrit = false;
   }

   public GraveLurkerProjectile(World world, EntityLivingBase thrower, ItemStack itemstack, float power, boolean isCrit) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
      this.isCrit = isCrit;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.1;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.1;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.1;
      }
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 40) {
         this.setDead();
      }

      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)5);
         if (this.ticksExisted < 2 && this.isCrit) {
            this.world.setEntityState(this, (byte)11);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      float mult = this.isCrit ? 0.3F : 1.0F;
      if (id == 8) {
         for (int ss = 0; ss < 8; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               this.largesmoke,
               0.3F + (float)this.rand.nextGaussian() / 20.0F,
               0.0F,
               12,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 20.0F,
               (float)this.rand.nextGaussian() / 25.0F,
               (float)this.rand.nextGaussian() / 20.0F,
               1.0F,
               (0.7F + (float)this.rand.nextGaussian() / 5.0F) * mult,
               mult,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.06F;
            bigsmoke.alphaGlowing = true;
            this.world.spawnEntity(bigsmoke);
         }
      }

      if (id == 5) {
         GUNParticle fire2 = new GUNParticle(
            this.largesmoke,
            0.16F + (float)this.rand.nextGaussian() / 20.0F,
            -0.009F,
            10 + this.rand.nextInt(5),
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            (0.7F + (float)this.rand.nextGaussian() / 5.0F) * mult,
            mult,
            true,
            this.rand.nextInt(100) - 50
         );
         fire2.alphaTickAdding = -0.06F;
         fire2.alphaGlowing = true;
         this.world.spawnEntity(fire2);
      }

      if (id == 11) {
         this.isCrit = true;
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            float damagee = this.isCrit ? 16.0F : 8.0F;
            Weapons.dealDamage(
               null,
               (damagee + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack)) * this.magicPower,
               this.thrower,
               result.entityHit,
               true,
               0.5F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 2.0F,
               this.thrower.posX,
               this.thrower.posY,
               this.thrower.posZ
            );
            result.entityHit.hurtResistantTime = 0;
            if (result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0 && entitylivingbase.getHealth() <= 0.0F && this.isCrit) {
                  NBTHelper.GiveNBTboolean(this.weaponstack, false, "crit");
                  NBTHelper.SetNBTboolean(this.weaponstack, true, "crit");
               }
            }

            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.magic_d,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            this.world.setEntityState(this, (byte)8);
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
               Sounds.magic_d,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }
}
