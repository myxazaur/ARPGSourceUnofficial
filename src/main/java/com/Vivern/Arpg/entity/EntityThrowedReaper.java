package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.IWeapon;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityThrowedReaper extends EntityThrowable {
   public final ItemStack weaponstack;
   public float flyeddist = 0.0F;
   public boolean returning = false;
   public boolean entitycollided = false;
   public double prePosX = 0.0;
   public double prePosY = 0.0;
   public double prePosZ = 0.0;
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/purple_smoke.png");
   ResourceLocation texture = new ResourceLocation("arpg:textures/generic_beam.png");
   public Vec3d pos1 = this.getPositionVector();
   public Vec3d pos2 = this.getPositionVector();
   public Vec3d pos3 = this.getPositionVector();
   public Vec3d pos4 = this.getPositionVector();
   public Vec3d pos5 = this.getPositionVector();

   public EntityThrowedReaper(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.REAPER);
   }

   public EntityThrowedReaper(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.REAPER);
   }

   public EntityThrowedReaper(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.REAPER);
   }

   public EntityThrowedReaper(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.setSize(1.0F, 1.0F);
   }

   protected float getGravityVelocity() {
      return 0.001F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 340) {
         NBTHelper.SetNBTboolean(this.weaponstack, false, "throwed");
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

      if (!this.world.isRemote && this.prePosX != 0.0 && this.prePosY != 0.0 && this.prePosZ != 0.0) {
         this.flyeddist = (float)(this.flyeddist + this.getPositionVector().distanceTo(new Vec3d(this.prePosX, this.prePosY, this.prePosZ)));
      }

      this.prePosX = this.posX;
      this.prePosY = this.posY;
      this.prePosZ = this.posZ;
      Entity target = this.getThrower();
      if (this.ticksExisted < 100) {
         this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
      }

      if (target != null && (this.returning || this.ticksExisted > 20)) {
         this.motionX /= 1.07F;
         this.motionY /= 1.07F;
         this.motionZ /= 1.07F;
         SuperKnockback.applyMove(this, -0.3F, target.posX, target.posY + 1.0, target.posZ);
         double damageRadius = 1.0;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (entitylivingbase == this.getThrower() && this.getThrower() instanceof EntityPlayer) {
                  EntityPlayer player = (EntityPlayer)this.getThrower();
                  if (this.weaponstack.getItem() == ItemsRegister.REAPER) {
                     player.getCooldownTracker().setCooldown(this.weaponstack.getItem(), ((IWeapon)ItemsRegister.REAPER).getCooldownTime(this.weaponstack));
                     NBTHelper.SetNBTboolean(this.weaponstack, false, "throwed");
                  }

                  this.setDead();
               }
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.reaper_impact,
               SoundCategory.AMBIENT,
               0.7F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );

         for (int ss = 0; ss < 5; ss++) {
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
               0.7F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.06F;
            bigsmoke.alphaGlowing = true;
            this.world.spawnEntity(bigsmoke);
         }
      }
   }

   public boolean repulse(Entity entityHit) {
      int rep = Weapons.getEntityRepulseType(entityHit);
      if (this.entitycollided) {
         return true;
      } else if (rep == 0) {
         return false;
      } else if (entityHit instanceof EntityThrowable && ((EntityThrowable)entityHit).getThrower() == this.getThrower()) {
         return false;
      } else if (rep == 1) {
         entityHit.motionX = entityHit.motionX + (entityHit.motionX > this.motionX ? 0.0 : this.motionX);
         entityHit.motionY = entityHit.motionY + (entityHit.motionY > this.motionY ? 0.0 : this.motionY);
         entityHit.motionZ = entityHit.motionZ + (entityHit.motionZ > this.motionZ ? 0.0 : this.motionZ);
         Weapons.setAcceleration(entityHit);
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)5);
         }

         return true;
      } else if (rep == 2) {
         entityHit.motionX = entityHit.motionX + (entityHit.motionX > this.motionX ? 0.0 : this.motionX / 3.0);
         entityHit.motionY = entityHit.motionY + (entityHit.motionY > this.motionY ? 0.0 : this.motionY / 3.0);
         entityHit.motionZ = entityHit.motionZ + (entityHit.motionZ > this.motionZ ? 0.0 : this.motionZ / 3.0);
         Weapons.setAcceleration(entityHit);
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)5);
         }

         return true;
      } else if (rep == 3) {
         entityHit.setDead();
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)5);
         }

         return true;
      } else if (rep >= 4) {
         if (rep >= 7) {
            int axisnoreflect = this.rand.nextInt(3);
            entityHit.motionX *= axisnoreflect == 0 ? 1.0 : -1.0;
            entityHit.motionY *= axisnoreflect == 1 ? 1.0 : -1.0;
            entityHit.motionZ *= axisnoreflect == 2 ? 1.0 : -1.0;
            this.motionX *= axisnoreflect == 0 ? 1.0 : -1.0;
            this.motionY *= axisnoreflect == 1 ? 1.0 : -1.0;
            this.motionZ *= axisnoreflect == 2 ? 1.0 : -1.0;
            Weapons.setAcceleration(entityHit);
         }

         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)5);
         }

         this.entitycollided = true;
         this.returning = true;
         return true;
      } else {
         return false;
      }
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (!this.repulse(result.entityHit)
            && Team.checkIsOpponent(this.thrower, result.entityHit)
            && Weapons.canDealDamageTo(result.entityHit)
            && !this.entitycollided) {
            this.expl();
         }
      } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null
         && !this.entitycollided) {
         this.expl();
      }
   }

   public void expl() {
      double damageRadius = 1.0;
      boolean spec = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0;
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (!this.world.isRemote) {
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  float dd = 0.0F;
                  if (spec) {
                     dd = this.rand.nextFloat() < 0.2F ? 5.0F : 0.0F;
                  }

                  Weapons.dealDamage(
                     null,
                     10.0F + dd + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack) * 1.5F,
                     this.thrower,
                     entity,
                     true,
                     Math.min(this.flyeddist / 3.0F, 3.0F) + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 1.5F,
                     this.posX,
                     this.posY - 0.3,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
                  if (entity instanceof EntityLivingBase) {
                     NBTHelper.GiveNBTfloat(this.weaponstack, 0.0F, "charge");
                     if (spec) {
                        NBTHelper.SetNBTfloat(this.weaponstack, this.flyeddist + NBTHelper.GetNBTfloat(this.weaponstack, "charge"), "charge");
                     } else {
                        NBTHelper.SetNBTfloat(this.weaponstack, this.flyeddist, "charge");
                     }
                  }

                  this.entitycollided = true;
               }
            }
         }

         this.returning = true;
         this.world.setEntityState(this, (byte)5);
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      Vec3d subtraction = this.pos1.subtract(this.pos2);
      if (this.ticksExisted > 2 && this.world.isRemote && this.pos1.lengthSquared() > 1.0E-6 && this.pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world,
            this.texture,
            0.3F,
            240,
            0.8F,
            0.5F,
            1.0F,
            0.17F,
            this.pos1.distanceTo(this.pos2),
            Math.min(4, this.ticksExisted - 2),
            0.3F,
            2.0F,
            this.pos1,
            this.pos2
         );
         laser.setPosition(this.posX, this.posY, this.posZ);
         laser.alphaGlowing = true;
         this.world.spawnEntity(laser);
      }
   }
}
