package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHeadShooter extends EntityThrowable implements IEntitySynchronize {
   static ResourceLocation drop = new ResourceLocation("arpg:textures/normaldrop.png");
   static ResourceLocation redsphere = new ResourceLocation("arpg:textures/red_sphere.png");
   public final ItemStack weaponstack;
   public Vec3d pos1 = this.getPositionVector();
   public Vec3d pos2 = this.getPositionVector();
   public Vec3d pos3 = this.getPositionVector();
   public Vec3d pos4 = this.getPositionVector();
   public Vec3d pos5 = this.getPositionVector();
   ResourceLocation texture = new ResourceLocation("arpg:textures/generic_beam.png");

   public EntityHeadShooter(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.HEADSHOOTER);
   }

   public EntityHeadShooter(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.HEADSHOOTER);
   }

   public EntityHeadShooter(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.HEADSHOOTER);
   }

   public EntityHeadShooter(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 16 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, this.weaponstack) * 3) {
         this.setDead();
      }

      if (this.pos4 != Vec3d.ZERO) {
         this.pos5 = this.pos4;
      } else {
         this.pos5 = this.getPositionVector();
      }

      if (this.pos3 != Vec3d.ZERO) {
         this.pos4 = this.pos3;
      } else {
         this.pos4 = this.getPositionVector();
      }

      if (this.pos2 != Vec3d.ZERO) {
         this.pos3 = this.pos2;
      } else {
         this.pos3 = this.getPositionVector();
      }

      if (this.pos1 != Vec3d.ZERO) {
         this.pos2 = this.pos1;
      } else {
         this.pos2 = this.getPositionVector();
      }

      if (this.getPositionVector() != Vec3d.ZERO) {
         this.pos1 = this.getPositionVector();
      } else {
         this.pos1 = this.thrower.getPositionVector();
      }
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      double d1 = this.lastTickPosX - x;
      double d2 = this.lastTickPosY - y;
      double d3 = this.lastTickPosZ - z;
      float dist = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
      float prunex = (float)(d1 / dist / 3.0);
      float pruney = (float)(d2 / dist / 3.0);
      float prunez = (float)(d3 / dist / 3.0);
      this.world.playSound(this.posX, this.posY, this.posZ, Sounds.fire_a, SoundCategory.AMBIENT, 0.7F, 1.5F, false);

      for (int ss = 0; ss < 8; ss++) {
         GUNParticle sp = new GUNParticle(
            drop,
            0.04F + this.rand.nextFloat() / 20.0F,
            0.03F,
            50,
            -1,
            this.world,
            x,
            y,
            z,
            prunex + (float)this.rand.nextGaussian() / 20.0F,
            pruney + (float)this.rand.nextGaussian() / 20.0F,
            prunez + (float)this.rand.nextGaussian() / 20.0F,
            0.8F,
            0.1F,
            0.1F,
            false,
            (int)Math.round(this.rand.nextGaussian() * 20.0),
            true,
            5.0F
         );
         sp.dropMode = true;
         this.world.spawnEntity(sp);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            int damagee = GetMOP.approximatelyEqual(
                  this.posY - result.entityHit.posY,
                  (double)(result.entityHit.getEyeHeight() + 0.1F),
                  result.entityHit.height / 5.0
               )
               ? 2
               : 1;
            Weapons.dealDamage(
               null,
               (10 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack)) * damagee,
               this.thrower,
               result.entityHit,
               true,
               1.8F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 1.5F,
               this.thrower.posX,
               this.thrower.posY,
               this.thrower.posZ
            );
            result.entityHit.hurtResistantTime = 0;
            if (result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.75) {
                  DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_CUT);
               }
            }

            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.plasma_railgun_impact,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
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
               Sounds.plasma_railgun_impact,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (!this.world.isRemote) {
            if (result.hitVec != null) {
               IEntitySynchronize.sendSynchronize(
                  this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z, 0.0, 0.0, 0.0
               );
            }

            this.setDead();
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      Vec3d subtraction = this.pos1.subtract(this.pos2);
      if (this.world.isRemote) {
         if (this.ticksExisted > 2 && this.pos1.lengthSquared() > 1.0E-6 && this.pos2.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, this.texture, 0.06F, 240, 1.0F, 0.45F, 0.55F, 0.2F, this.pos1.distanceTo(this.pos2), 4, 0.3F, 2.0F, this.pos1, this.pos2
            );
            laser.setPosition(this.posX, this.posY, this.posZ);
            laser.alphaGlowing = true;
            this.world.spawnEntity(laser);
         }

         if (this.ticksExisted == 2) {
            GUNParticle bigsmoke1 = new GUNParticle(
               redsphere,
               0.02F,
               0.0F,
               8,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke1.alphaTickAdding = -0.13F;
            bigsmoke1.alphaGlowing = true;
            bigsmoke1.scaleTickAdding = 0.09F;
            this.world.spawnEntity(bigsmoke1);
         }
      }
   }
}
