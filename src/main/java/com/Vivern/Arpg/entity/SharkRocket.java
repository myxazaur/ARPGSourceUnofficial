//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SharkRocket extends EntityThrowable {
   public final ItemStack weaponstack;

   public SharkRocket(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.SHARKCANN);
   }

   public SharkRocket(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.SHARKCANN);
   }

   public SharkRocket(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.SHARKCANN);
   }

   public SharkRocket(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   protected float getGravityVelocity() {
      return 0.001F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 200) {
         this.setDead();
      }

      if (this.ticksExisted > 22) {
         EntityLivingBase target = GetMOP.findNearestEnemy(
            this.world,
            this.thrower,
            this.posX,
            this.posY,
            this.posZ,
            8.0,
            EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0
         );
         if (target != null) {
            SuperKnockback.applyMove(
               this,
               -0.07F - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, this.weaponstack) * 0.01F,
               target.posX,
               target.posY + target.height / 3.0F,
               target.posZ
            );
         }
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
      if (!this.world.isRemote) {
         double damageRadius = 4.0;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  Weapons.dealDamage(
                     null,
                     Math.max(15.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack) - 1.2F * entity.getDistance(this), 2.0F),
                     this.thrower,
                     entity,
                     true,
                     2.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 1.5F,
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
               }
            }
         }

         this.world.setEntityState(this, (byte)4);
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      if (this.ticksExisted == 2) {
         this.world.playSound(this.posX, this.posY, this.posZ, Sounds.drrdrr, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
      }

      if (this.ticksExisted == 20) {
         this.world
            .playSound(this.posX, this.posY, this.posZ, Sounds.sharkrocket, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
      }

      if (this.ticksExisted == 40) {
         this.world
            .playSound(this.posX, this.posY, this.posZ, Sounds.sharkrocket, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
      }

      if (this.ticksExisted == 60) {
         this.world
            .playSound(this.posX, this.posY, this.posZ, Sounds.sharkrocket, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
      }

      if (this.ticksExisted == 80) {
         this.world
            .playSound(this.posX, this.posY, this.posZ, Sounds.sharkrocket, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
      }

      if (this.ticksExisted == 100) {
         this.world
            .playSound(this.posX, this.posY, this.posZ, Sounds.sharkrocket, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
      }

      if (this.ticksExisted == 120) {
         this.world
            .playSound(this.posX, this.posY, this.posZ, Sounds.sharkrocket, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
      }

      if (this.ticksExisted == 22) {
         for (int kf = 0; kf < 5; kf++) {
            this.world
               .spawnParticle(
                  EnumParticleTypes.SMOKE_LARGE,
                  this.posX,
                  this.posY,
                  this.posZ,
                  this.rand.nextGaussian() / 10.0,
                  this.rand.nextGaussian() / 10.0,
                  this.rand.nextGaussian() / 10.0,
                  new int[0]
               );
         }
      }

      double gaussianX = this.rand.nextGaussian() / 15.0;
      double gaussianY = this.rand.nextGaussian() / 15.0;
      double gaussianZ = this.rand.nextGaussian() / 15.0;
      this.world
         .spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, gaussianX, gaussianY, gaussianZ, new int[0]);
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 4) {
         if (Minecraft.getMinecraft().getRenderViewEntity().getDistance(this) < 10.0F) {
            Booom.lastTick = Math.round(16.0F / Minecraft.getMinecraft().getRenderViewEntity().getDistance(this));
            Booom.x = (float)this.rand.nextGaussian();
            Booom.y = (float)this.rand.nextGaussian();
            Booom.z = (float)this.rand.nextGaussian();
         }

         this.world.playSound(this.posX, this.posY, this.posZ, Sounds.boom_a, SoundCategory.AMBIENT, 2.0F, 1.0F, false);
         this.world
            .spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, new int[0]);

         for (int kf = 0; kf < 10; kf++) {
            this.world
               .spawnParticle(
                  EnumParticleTypes.SMOKE_LARGE,
                  this.posX,
                  this.posY,
                  this.posZ,
                  this.rand.nextGaussian() / 3.0,
                  this.rand.nextGaussian() / 3.0,
                  this.rand.nextGaussian() / 3.0,
                  new int[0]
               );
         }
      }
   }
}
