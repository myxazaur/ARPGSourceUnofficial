package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.GemStaff;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.IRenderOptions;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
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

public class GemStaffShoot extends EntityThrowable implements IRenderOptions {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public int type = 0;
   public float damage = 0.0F;
   public float knockback = 0.0F;
   public int livetime = 40;
   public int countDamaged = 0;
   public List<Entity> damagedList;
   public float red = 0.0F;
   public float green = 0.0F;
   public float blue = 0.0F;
   static ResourceLocation cloud = new ResourceLocation("arpg:textures/simple_magic_shoot.png");
   static ResourceLocation tex = new ResourceLocation("arpg:textures/spellblue3.png");

   public GemStaffShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.GEMSTAFF);
   }

   public GemStaffShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.GEMSTAFF);
   }

   public GemStaffShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.GEMSTAFF);
   }

   public GemStaffShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
      this.type = NBTHelper.GetNBTint(itemstack, "type");
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      double mot = 0.5;
      this.motionX = this.motionX + entityThrower.motionX * mot;
      this.motionZ = this.motionZ + entityThrower.motionZ * mot;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * mot;
      }
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > this.livetime) {
         this.setDead();
      }

      if (!this.world.isRemote) {
         if (this.ticksExisted < 2 || this.ticksExisted % 21 == 0) {
            this.world.setEntityState(this, (byte)this.type);
         }
      } else {
         GUNParticle fire2 = new GUNParticle(
            cloud,
            0.21F,
            0.0F,
            14,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            this.red,
            this.green,
            this.blue,
            true,
            this.rand.nextInt(360)
         );
         fire2.alphaTickAdding = -0.06F;
         fire2.scaleTickAdding = -0.008F;
         fire2.alphaGlowing = true;
         this.world.spawnEntity(fire2);
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      this.type = id > 7 ? id - 8 : id;
      WeaponParameters parameters = GemStaff.getWeaponParameter(this.type);
      this.red = parameters.get("red");
      this.green = parameters.get("green");
      this.blue = parameters.get("blue");
      if (id > 7) {
         this.spawnpart(this.red, this.green, this.blue);
      }
   }

   public void spawnpart(float red, float green, float blue) {
      for (int ss = 0; ss < this.rand.nextInt(8) + 6; ss++) {
         GUNParticle part = new GUNParticle(
            tex,
            0.06F + this.rand.nextFloat() / 30.0F,
            0.002F,
            30 + this.rand.nextInt(20),
            240,
            this.world,
            this.lastTickPosX,
            this.lastTickPosY,
            this.lastTickPosZ,
            (float)this.rand.nextGaussian() / 17.0F,
            (float)this.rand.nextGaussian() / 17.0F,
            (float)this.rand.nextGaussian() / 17.0F,
            red,
            green,
            blue,
            false,
            0,
            true,
            1.2F
         );
         this.world.spawnEntity(part);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.world.isRemote) {
         if (result.entityHit != null) {
            if ((this.damagedList == null || !this.damagedList.contains(result.entityHit))
               && Team.checkIsOpponent(this.thrower, result.entityHit)
               && Weapons.canDealDamageTo(result.entityHit)) {
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.magic),
                  this.damage * this.magicPower,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  this.knockback
               );
               result.entityHit.hurtResistantTime = 0;
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.hitmagic_a,
                     SoundCategory.AMBIENT,
                     0.8F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)(this.type + 8));
               if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0) {
                  if (this.damagedList == null) {
                     this.damagedList = new ArrayList<>();
                  }

                  this.countDamaged++;
                  if (this.countDamaged > 2) {
                     this.setDead();
                  } else {
                     this.damagedList.add(result.entityHit);
                  }
               } else {
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
                  Sounds.hitmagic_a,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            this.world.setEntityState(this, (byte)(this.type + 8));
            this.setDead();
         }
      }
   }

   @Override
   public void doOptions() {
      GlStateManager.color(this.red, this.green, this.blue);
   }

   @Override
   public void undoOptions() {
   }
}
