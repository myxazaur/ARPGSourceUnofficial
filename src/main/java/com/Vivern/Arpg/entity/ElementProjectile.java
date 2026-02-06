package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElementProjectile extends EntityThrowable {
   public final ItemStack weaponstack;
   public int type;
   ResourceLocation sparkle = new ResourceLocation("arpg:textures/sparkle.png");
   ResourceLocation puff = new ResourceLocation("arpg:textures/air.png");
   ResourceLocation boulder = new ResourceLocation("arpg:textures/boulder.png");
   ResourceLocation bubble = new ResourceLocation("arpg:textures/bubble.png");

   public ElementProjectile(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.ELEMENTFOCUS);
   }

   public ElementProjectile(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.ELEMENTFOCUS);
   }

   public ElementProjectile(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.ELEMENTFOCUS);
   }

   public ElementProjectile(World world, EntityLivingBase thrower, int type, ItemStack stack) {
      super(world, thrower);
      this.type = type;
      this.weaponstack = stack;
   }

   protected float getGravityVelocity() {
      if (this.type == 1) {
         return 0.0F;
      } else if (this.type == 2) {
         return 0.01F;
      } else if (this.type == 3) {
         return 0.002F;
      } else {
         return this.type == 4 ? 0.005F : 0.0F;
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 20 && this.type == 1) {
         this.setDead();
      }

      if (this.ticksExisted > 10 && this.type == 2) {
         this.setDead();
      }

      if (this.ticksExisted > 13 && this.type == 3) {
         this.setDead();
      }

      if (this.ticksExisted > 14 && this.type == 4) {
         this.setDead();
      }

      if (this.type == 1) {
         this.world.setEntityState(this, (byte)11);
      }

      if (this.type == 2) {
         this.world.setEntityState(this, (byte)12);
      }

      if (this.type == 3) {
         this.world.setEntityState(this, (byte)13);
      }

      if (this.type == 4) {
         this.world.setEntityState(this, (byte)14);
      }
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

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 1) {
         for (int ss = 0; ss < 9; ss++) {
            Entity air = new GUNParticle(
               this.puff,
               0.2F,
               0.0F,
               9,
               Math.round(180.0F + (float)this.rand.nextGaussian() * 50.0F),
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 16.0F,
               (float)this.rand.nextGaussian() / 16.0F,
               (float)this.rand.nextGaussian() / 16.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               0
            );
            this.world.spawnEntity(air);
         }
      }

      if (id == 2) {
         for (int ss = 0; ss < 6; ss++) {
            Entity earth = new EntityCubicParticle(
               this.boulder,
               0.013F,
               0.015F,
               12 + this.rand.nextInt(4),
               89,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F,
               1.0F,
               1.0F,
               1.0F,
               false,
               this.rand.nextFloat(),
               this.rand.nextFloat(),
               this.rand.nextFloat(),
               0.14F,
               true,
               0.0F
            );
            this.world.spawnEntity(earth);
         }
      }

      if (id == 3) {
         for (int ss = 0; ss < 10; ss++) {
            Entity fire = new GUNParticle(
               this.sparkle,
               0.07F + (float)this.rand.nextGaussian() / 30.0F,
               -0.01F,
               7,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 12.0F,
               (float)this.rand.nextGaussian() / 12.0F,
               (float)this.rand.nextGaussian() / 12.0F,
               1.0F,
               0.8F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               true,
               0
            );
            this.world.spawnEntity(fire);
         }
      }

      if (id == 4) {
         for (int ss = 0; ss < 8; ss++) {
            Entity water = new GUNParticle(
               this.bubble,
               0.08F + (float)this.rand.nextGaussian() / 30.0F,
               0.017F,
               10,
               160,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 10.0F,
               (float)this.rand.nextGaussian() / 10.0F,
               (float)this.rand.nextGaussian() / 10.0F,
               0.8F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               1.0F,
               true,
               0
            );
            this.world.spawnEntity(water);
         }
      }

      if (id == 11) {
         Entity air = new GUNParticle(
            this.puff,
            0.13F,
            0.0F,
            9,
            Math.round(190.0F + (float)this.rand.nextGaussian() * 40.0F),
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 30.0F,
            (float)this.rand.nextGaussian() / 30.0F,
            (float)this.rand.nextGaussian() / 30.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            0
         );
         this.world.spawnEntity(air);
      }

      if (id == 12) {
         Entity earth = new EntityCubicParticle(
            this.boulder,
            0.005F,
            0.01F,
            12 + this.rand.nextInt(4),
            89,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 44.0F,
            (float)this.rand.nextGaussian() / 44.0F,
            (float)this.rand.nextGaussian() / 44.0F,
            1.0F,
            1.0F,
            1.0F,
            false,
            1.0F,
            1.0F,
            1.0F,
            0.06F,
            true,
            0.0F
         );
         this.world.spawnEntity(earth);
      }

      if (id == 13) {
         Entity fire = new GUNParticle(
            this.sparkle,
            0.07F,
            -0.004F,
            7,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            0.8F + (float)this.rand.nextGaussian() / 5.0F,
            1.0F,
            true,
            0
         );
         this.world.spawnEntity(fire);
      }

      if (id == 14) {
         Entity water = new GUNParticle(
            this.bubble,
            0.08F,
            0.01F,
            10,
            160,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 20.0F,
            (float)this.rand.nextGaussian() / 20.0F,
            (float)this.rand.nextGaussian() / 20.0F,
            0.8F + (float)this.rand.nextGaussian() / 5.0F,
            1.0F,
            1.0F,
            true,
            0
         );
         this.world.spawnEntity(water);
      }

      if (id == 5) {
         this.world.playSound(this.posX, this.posY, this.posZ, Sounds.apple, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
      }
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean repulse(Entity entityHit, byte handleId) {
      int rep = Weapons.getEntityRepulseType(entityHit);
      if (rep == 0) {
         return false;
      } else if (entityHit instanceof EntityThrowable && ((EntityThrowable)entityHit).getThrower() == this.getThrower()) {
         return false;
      } else if (rep != 1 && rep != 5) {
         if (rep == 2) {
            entityHit.motionX = entityHit.motionX + (entityHit.motionX > this.motionX ? 0.0 : this.motionX / 2.0);
            entityHit.motionY = entityHit.motionY + (entityHit.motionY > this.motionY ? 0.0 : this.motionY / 2.0);
            entityHit.motionZ = entityHit.motionZ + (entityHit.motionZ > this.motionZ ? 0.0 : this.motionZ / 2.0);
            Weapons.setAcceleration(entityHit);
            if (!this.world.isRemote) {
               this.world.setEntityState(this, handleId);
            }

            this.setDead();
            return true;
         } else if (rep == 3 || rep == 4) {
            entityHit.setDead();
            if (!this.world.isRemote) {
               this.world.setEntityState(this, handleId);
            }

            this.setDead();
            return true;
         } else if (rep == 6) {
            int axisnoreflect = this.rand.nextInt(3);
            entityHit.motionX *= axisnoreflect == 0 ? 1.0 : -1.0;
            entityHit.motionY *= axisnoreflect == 1 ? 1.0 : -1.0;
            entityHit.motionZ *= axisnoreflect == 2 ? 1.0 : -1.0;
            this.motionX *= axisnoreflect == 0 ? 1.0 : -1.0;
            this.motionY *= axisnoreflect == 1 ? 1.0 : -1.0;
            this.motionZ *= axisnoreflect == 2 ? 1.0 : -1.0;
            Weapons.setAcceleration(entityHit);
            if (!this.world.isRemote) {
               this.world.setEntityState(this, handleId);
            }

            return true;
         } else if (rep == 7) {
            if (!this.world.isRemote) {
               this.world.setEntityState(this, handleId);
            }

            this.setDead();
            return true;
         } else if (rep == 8) {
            int axisnoreflect = this.rand.nextInt(3);
            this.motionX *= axisnoreflect == 0 ? 1.0 : -1.0;
            this.motionY *= axisnoreflect == 1 ? 1.0 : -1.0;
            this.motionZ *= axisnoreflect == 2 ? 1.0 : -1.0;
            if (!this.world.isRemote) {
               this.world.setEntityState(this, handleId);
            }

            return true;
         } else {
            return false;
         }
      } else {
         entityHit.motionX = entityHit.motionX + (entityHit.motionX > this.motionX ? 0.0 : this.motionX);
         entityHit.motionY = entityHit.motionY + (entityHit.motionY > this.motionY ? 0.0 : this.motionY);
         entityHit.motionZ = entityHit.motionZ + (entityHit.motionZ > this.motionZ ? 0.0 : this.motionZ);
         Weapons.setAcceleration(entityHit);
         if (!this.world.isRemote) {
            this.world.setEntityState(this, handleId);
         }

         this.setDead();
         return true;
      }
   }

   protected void onImpact(RayTraceResult result) {
      float might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
      float impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
      if (this.type == 1) {
         if (result.entityHit != null && result.entityHit != this.thrower && result.entityHit instanceof EntityLivingBase) {
            EntityLivingBase base = (EntityLivingBase)result.entityHit;
            IAttributeInstance entityAttribute = base.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
            double baseValue = entityAttribute.getBaseValue();
            entityAttribute.setBaseValue(1.0);
            base.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 2.0F + might / 2.3F);
            base.hurtResistantTime = 0;
            SuperKnockback.applyKnockback(
               base, 1.3F + impulse / 2.0F, this.thrower.posX, this.thrower.posY, this.thrower.posZ
            );
            entityAttribute.setBaseValue(baseValue);
         }

         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)1);
         }
      }

      if (this.type == 2) {
         if (result.entityHit != null && result.entityHit != this.thrower && result.entityHit instanceof EntityLivingBase) {
            EntityLivingBase base = (EntityLivingBase)result.entityHit;
            IAttributeInstance entityAttribute = base.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
            double baseValue = entityAttribute.getBaseValue();
            entityAttribute.setBaseValue(1.0);
            base.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 6.0F + might / 2.0F);
            base.hurtResistantTime = 0;
            SuperKnockback.applyKnockback(
               base, 3.0F + impulse / 1.7F, this.thrower.posX, this.thrower.posY, this.thrower.posZ
            );
            entityAttribute.setBaseValue(baseValue);
         }

         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)2);
         }
      }

      if (this.type == 3) {
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().expand(2.6, 2.6, 2.6).offset(-1.3, -1.3, -1.3);
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityLivingBase base : list) {
               if (base != this.getThrower()) {
                  IAttributeInstance entityAttribute = base.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                  double baseValue = entityAttribute.getBaseValue();
                  entityAttribute.setBaseValue(1.0);
                  base.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 4.2F + might / 2.2F);
                  base.hurtResistantTime = 0;
                  SuperKnockback.applyKnockback(
                     base, 0.5F + impulse / 6.0F, this.thrower.posX, this.thrower.posY, this.thrower.posZ
                  );
                  entityAttribute.setBaseValue(baseValue);
                  base.setFire(2);
               }
            }
         }

         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
         }
      }

      if (this.type == 4) {
         if (result.entityHit != null && result.entityHit != this.thrower && result.entityHit instanceof EntityLivingBase) {
            EntityLivingBase basex = (EntityLivingBase)result.entityHit;
            IAttributeInstance entityAttribute = basex.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
            double baseValue = entityAttribute.getBaseValue();
            entityAttribute.setBaseValue(1.0);
            basex.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 3.7F + might / 2.5F);
            basex.hurtResistantTime = 0;
            SuperKnockback.applyKnockback(
               basex, 1.7F + impulse / 3.5F, this.thrower.posX, this.thrower.posY, this.thrower.posZ
            );
            entityAttribute.setBaseValue(baseValue);
            basex.extinguish();
            PotionEffect baff = new PotionEffect(Potion.getPotionById(2), 40, 2);
            PotionEffect baff2 = new PotionEffect(Potion.getPotionById(18), 70, 1);
            basex.addPotionEffect(baff);
            basex.addPotionEffect(baff2);
         }

         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)4);
         }
      }

      if (!this.world.isRemote && result.entityHit != this.thrower) {
         this.setDead();
      }
   }
}
