package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WandColdShoot extends StandardBullet {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public int ticksNoCollide = 0;
   public int impacts = 0;
   static ResourceLocation snowflake6 = new ResourceLocation("arpg:textures/snowflake6.png");

   public WandColdShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.WANDOFCOLD);
   }

   public WandColdShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.WANDOFCOLD);
   }

   public WandColdShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.WANDOFCOLD);
   }

   public WandColdShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   private void init() {
   }

   @Override
   public float getRotationTowardMovementSpeed() {
      return 1.0F;
   }

   @Override
   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.6;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.6;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.2;
      }
   }

   @Override
   public float getGravityVelocity() {
      return 0.0F;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      this.ticksNoCollide++;
      if (this.ticksExisted > 200 || this.ticksNoCollide > 60) {
         this.setDead();
      }

      if (this.world.isRemote) {
         float scl = 0.04F + 0.08F * this.rand.nextFloat();
         int lt = this.rand.nextInt(5) + 10;
         GUNParticle fire2 = new GUNParticle(
            snowflake6,
            scl,
            0.0F,
            lt,
            200,
            this.world,
            this.lastTickPosX + this.rand.nextGaussian() / 15.0,
            this.lastTickPosY + this.height / 2.0F + this.rand.nextGaussian() / 15.0,
            this.lastTickPosZ + this.rand.nextGaussian() / 15.0,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         fire2.alphaTickAdding = -1.0F / lt;
         fire2.scaleTickAdding = -scl / lt * 0.8F;
         fire2.alphaGlowing = true;
         this.world.spawnEntity(fire2);
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         for (int ss = 0; ss < this.rand.nextInt(6) + 7; ss++) {
            float scl = 0.08F + 0.08F * this.rand.nextFloat();
            int lt = this.rand.nextInt(5) + 10;
            GUNParticle fire2 = new GUNParticle(
               snowflake6,
               scl,
               0.0F,
               lt,
               200,
               this.world,
               this.lastTickPosX,
               this.lastTickPosY + this.height / 2.0F,
               this.lastTickPosZ,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            fire2.scaleTickAdding = -scl / lt * 0.95F;
            fire2.alphaGlowing = true;
            this.world.spawnEntity(fire2);
         }
      }
   }

   @Override
   public void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote && Weapons.canDealDamageTo(result.entityHit)) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
            if (Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.frost),
               parameters.getEnchanted("damage", might) * this.magicPower,
               this.getThrower(),
               result.entityHit,
               true,
               parameters.getEnchanted("knockback", impulse)
            )) {
               NBTHelper.AddNBTint(this.weaponstack, 1, "charges");
            }

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
               Sounds.normal_projectile,
               SoundCategory.AMBIENT,
               0.7F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         this.ticksNoCollide = 0;
         this.impacts++;
         if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
            this.motionY = -this.motionY;
         }

         if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
            this.motionZ = -this.motionZ;
         }

         if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
            this.motionX = -this.motionX;
         }

         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         if (!this.world.isRemote
            && this.impacts
               > WeaponParameters.getWeaponParameters(this.weaponstack.getItem())
                  .getEnchantedi("max_bounces", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack))) {
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }
}
