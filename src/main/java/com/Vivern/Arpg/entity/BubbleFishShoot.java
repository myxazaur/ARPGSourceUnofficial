package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
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

public class BubbleFishShoot extends EntityThrowable {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public int livetime = 50;
   static ResourceLocation tex = new ResourceLocation("arpg:textures/blob_explode.png");

   public BubbleFishShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.BUBBLEFISH);
   }

   public BubbleFishShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.BUBBLEFISH);
   }

   public BubbleFishShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.BUBBLEFISH);
   }

   public BubbleFishShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
   }

   protected float getGravityVelocity() {
      return -0.01F;
   }

   public void onUpdate() {
      boolean water = this.inWater;
      this.inWater = false;
      super.onUpdate();
      this.inWater = water;
      if (!this.world.isRemote && this.ticksExisted > this.livetime) {
         this.setDead();
      }

      if (this.isInWater()) {
         float f1 = 0.96F;
         this.motionX *= f1;
         this.motionY *= f1;
         this.motionZ *= f1;
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         GUNParticle bigsmoke1 = new GUNParticle(
            tex,
            0.05F,
            0.0F,
            5,
            -1,
            this.world,
            this.lastTickPosX,
            this.lastTickPosY,
            this.lastTickPosZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke1.alphaTickAdding = -0.095F;
         bigsmoke1.alphaGlowing = true;
         bigsmoke1.scaleTickAdding = 0.11F;
         this.world.spawnEntity(bigsmoke1);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
            int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.water),
               parameters.getEnchanted("damage", might) * this.magicPower,
               this.getThrower(),
               result.entityHit,
               true,
               parameters.getEnchanted("knockback", impulse),
               this.posX,
               this.posY,
               this.posZ
            );
            result.entityHit.hurtResistantTime = 0;
            Weapons.setPotionIfEntityLB(result.entityHit, parameters.getPotion("slime", PotionEffects.SLIME, witchery));
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.pop,
                  SoundCategory.AMBIENT,
                  0.7F,
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
               Sounds.pop,
               SoundCategory.AMBIENT,
               0.7F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }

   public boolean handleWaterMovement() {
      return false;
   }
}
