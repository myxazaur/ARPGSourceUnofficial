package com.vivern.arpg.entity;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LavaDropperShoot extends EntityThrowable {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   static ResourceLocation tex = new ResourceLocation("arpg:textures/lava_drop.png");

   public LavaDropperShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.LAVADROPPER);
      this.setSize(0.5F, 0.5F);
   }

   public LavaDropperShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.LAVADROPPER);
      this.setSize(0.5F, 0.5F);
   }

   public LavaDropperShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.LAVADROPPER);
      this.setSize(0.5F, 0.5F);
   }

   public LavaDropperShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
      this.setSize(0.5F, 0.5F);
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      double mot = 1.0;
      this.motionX = this.motionX + entityThrower.motionX * mot;
      this.motionZ = this.motionZ + entityThrower.motionZ * mot;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * mot;
      }
   }

   protected float getGravityVelocity() {
      return 0.05F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 60) {
         this.setDead();
      }

      if (this.ticksExisted % 5 == 0 && !this.world.isRemote) {
         this.world.setEntityState(this, (byte)5);
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         GUNParticle part = new GUNParticle(
            tex,
            0.05F + this.rand.nextFloat() / 30.0F,
            0.025F,
            40 + this.rand.nextInt(20),
            240,
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
            false,
            0,
            true,
            1.5F
         );
         part.alphaGlowing = true;
         part.dropMode = true;
         this.world.spawnEntity(part);
         this.world
            .spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, new int[0]);
      }

      if (id == 8) {
         for (int ss = 0; ss < this.rand.nextInt(10) + 4; ss++) {
            GUNParticle part = new GUNParticle(
               tex,
               0.05F + this.rand.nextFloat() / 30.0F,
               0.035F,
               40 + this.rand.nextInt(20),
               240,
               this.world,
               this.lastTickPosX,
               this.lastTickPosY,
               this.lastTickPosZ,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F,
               1.0F,
               1.0F,
               1.0F,
               false,
               0,
               true,
               1.5F
            );
            part.alphaGlowing = true;
            part.dropMode = true;
            this.world.spawnEntity(part);
            this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX, this.posY, this.posZ, 0.0, 0.5, 0.0, new int[0]);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit)
            && Weapons.canDealDamageTo(result.entityHit)
            && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
            int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.fire),
               parameters.getEnchanted("damage", might),
               this.getThrower(),
               result.entityHit,
               true,
               parameters.getEnchanted("knockback", impulse)
            );
            result.entityHit.hurtResistantTime = 0;
            result.entityHit.setFire(parameters.getEnchantedi("fire", witchery));
            this.tryPlaceLava(result);
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.lava_dropper_impact,
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
               Sounds.lava_dropper_impact,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (!this.world.isRemote) {
            this.tryPlaceLava(result);
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }

   public void tryPlaceLava(RayTraceResult result) {
      if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0) {
         BlockPos pos = result.sideHit == EnumFacing.UP ? this.getPosition() : this.getPosition().down();
         IBlockState state = this.world.getBlockState(pos);
         if (state.getBlock() == Blocks.FLOWING_LAVA) {
            pos = pos.up();
         }

         if (Weapons.easyBreakBlockFor(this.world, 0.4F, pos)) {
            this.world.destroyBlock(pos, true);
            this.world.setBlockState(pos, Blocks.FLOWING_LAVA.getStateFromMeta(2));
         }
      }
   }
}
