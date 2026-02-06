package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVampireKnife extends EntityThrowable {
   public final ItemStack weaponstack;

   public EntityVampireKnife(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.VAMPIREKNIFESLAUN);
   }

   public EntityVampireKnife(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.VAMPIREKNIFESLAUN);
   }

   public EntityVampireKnife(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.VAMPIREKNIFESLAUN);
   }

   public EntityVampireKnife(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.2;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.2;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.2;
      }
   }

   protected float getGravityVelocity() {
      return 0.01F;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.world.playSound(this.posX, this.posY, this.posZ, Sounds.knife_a, SoundCategory.AMBIENT, 0.6F, 1.0F, false);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.world.isRemote) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
               Weapons.dealDamage(
                  null,
                  4.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack) / 2.0F,
                  this.thrower,
                  result.entityHit,
                  true,
                  1.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 2.0F,
                  this.thrower.posX,
                  this.thrower.posY,
                  this.thrower.posZ
               );
               result.entityHit.hurtResistantTime = 0;
               if (result.entityHit instanceof EntityLivingBase) {
                  EntityLivingBase base = (EntityLivingBase)result.entityHit;
                  if (base.getHealth() >= 4.0F && !base.isEntityUndead()) {
                     for (int in = 0; in < this.rand.nextInt(4 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) * 4); in++) {
                        BloodDrop drop = new BloodDrop(this.world, this.posX, this.posY, this.posZ, this.thrower);
                        drop.setVelocity(
                           (float)this.rand.nextGaussian() / 14.0F,
                           (float)this.rand.nextGaussian() / 14.0F,
                           (float)this.rand.nextGaussian() / 14.0F
                        );
                        this.world.spawnEntity(drop);
                     }
                  }
               }

               if (this.rand.nextFloat() < 0.7 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, this.weaponstack) * 0.14F) {
                  this.world
                     .spawnEntity(
                        new EntityItem(
                           this.world, this.posX, this.posY, this.posZ, new ItemStack(ItemsRegister.VAMPIREKNIFE, 1)
                        )
                     );
               }

               this.world.setEntityState(this, (byte)5);
               this.setDead();
            }
         } else if (this.world
                  .getBlockState(result.getBlockPos())
                  .getBlock()
                  .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
               != null
            && !this.world.isRemote) {
            if (this.rand.nextFloat() < 0.7 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, this.weaponstack) * 0.14F) {
               this.world
                  .spawnEntity(
                     new EntityItem(
                        this.world, this.posX, this.posY, this.posZ, new ItemStack(ItemsRegister.VAMPIREKNIFE, 1)
                     )
                  );
            }

            this.world.setEntityState(this, (byte)5);
            this.setDead();
         }
      }
   }
}
