package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.IRenderOptions;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMagicRocket extends EntityThrowable implements IEntitySynchronize, IRenderOptions {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public int disableMagic = 0;
   public int ticks = 0;
   public int livetime = 50;
   public Entity specialTarget;
   public boolean useTarget = false;
   static ResourceLocation texture = new ResourceLocation("arpg:textures/ghostly_shoot.png");
   static ResourceLocation star = new ResourceLocation("arpg:textures/magic_rocket.png");

   public EntityMagicRocket(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.MAGICROCKET);
   }

   public EntityMagicRocket(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.MAGICROCKET);
   }

   public EntityMagicRocket(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.MAGICROCKET);
   }

   public EntityMagicRocket(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.2;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.2;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.1;
      }
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         if (this.ticks > this.livetime) {
            this.setDead();
         }

         if (this.disableMagic > 0) {
            this.disableMagic--;
            this.ticks = 0;
         } else {
            this.ticks++;
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            double mt = parameters.getEnchanted("friction", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, this.weaponstack));
            float acceleration = parameters.getEnchanted("acceleration", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, this.weaponstack));
            if (!this.useTarget) {
               double max = Double.MAX_VALUE;
               EntityLivingBase targ = null;
               double damageRadius = parameters.getEnchanted("find_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
               if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) == 0) {
                  AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
                     .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
                     .offset(-damageRadius, -damageRadius, -damageRadius);
                  List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
                  if (!list.isEmpty()) {
                     for (EntityLivingBase entitylivingbase : list) {
                        if (Team.checkIsOpponent(this.thrower, entitylivingbase)
                           && entitylivingbase != this.getThrower()
                           && EntitySelectors.NOT_SPECTATING.apply(entitylivingbase)) {
                           double dist = entitylivingbase.getDistance(this);
                           if (dist < max) {
                              max = dist;
                              targ = entitylivingbase;
                           }
                        }
                     }
                  }
               } else {
                  AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
                     .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
                     .offset(-damageRadius, -damageRadius, -damageRadius);
                  List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
                  if (!list.isEmpty()) {
                     for (EntityLivingBase entitylivingbasex : list) {
                        if (entitylivingbasex.isCreatureType(EnumCreatureType.MONSTER, false)
                           && Team.checkIsOpponent(this.thrower, entitylivingbasex)
                           && entitylivingbasex != this.getThrower()
                           && EntitySelectors.NOT_SPECTATING.apply(entitylivingbasex)
                           && entitylivingbasex.getHealth() > 0.0F
                           && GetMOP.thereIsNoBlockCollidesBetween(
                              this.world, GetMOP.entityCenterPos(this), GetMOP.entityCenterPos(entitylivingbasex), 1.0F, null, false
                           )) {
                           double dist = entitylivingbasex.getDistance(this);
                           if (dist < max) {
                              max = dist;
                              targ = entitylivingbasex;
                           }
                        }
                     }

                     if (targ == null) {
                        for (EntityLivingBase entitylivingbasexx : list) {
                           if (Team.checkIsOpponent(this.thrower, entitylivingbasexx)
                              && entitylivingbasexx != this.getThrower()
                              && EntitySelectors.NOT_SPECTATING.apply(entitylivingbasexx)
                              && entitylivingbasexx.getHealth() > 0.0F
                              && GetMOP.thereIsNoBlockCollidesBetween(
                                 this.world, GetMOP.entityCenterPos(this), GetMOP.entityCenterPos(entitylivingbasexx), 1.0F, null, false
                              )) {
                              double dist = entitylivingbasexx.getDistance(this);
                              if (dist < max) {
                                 max = dist;
                                 targ = entitylivingbasexx;
                              }
                           }
                        }
                     }
                  }
               }

               if (targ != null) {
                  this.motionX *= mt;
                  this.motionY *= mt;
                  this.motionZ *= mt;
                  SuperKnockback.applyKnockback(this, -acceleration, targ.posX, targ.posY + targ.height / 2.0F, targ.posZ);
                  this.velocityChanged = true;
               }
            } else if (this.specialTarget != null) {
               this.motionX *= mt;
               this.motionY *= mt;
               this.motionZ *= mt;
               SuperKnockback.applyKnockback(
                  this,
                  -acceleration,
                  this.specialTarget.posX,
                  this.specialTarget.posY + this.specialTarget.height / 2.0F,
                  this.specialTarget.posZ
               );
               this.velocityChanged = true;
            }
         }
      }
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      for (int ss = 0; ss < 10; ss++) {
         float delScl = 17 + this.rand.nextInt(7);
         float delSpd = 40.0F - delScl;
         float scl = 0.15F + (float)this.rand.nextGaussian() / delScl;
         GUNParticle bigsmoke = new GUNParticle(
            star,
            scl,
            0.0F,
            35,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / delSpd,
            (float)this.rand.nextGaussian() / delSpd,
            (float)this.rand.nextGaussian() / delSpd,
            0.2F,
            0.65F + (float)this.rand.nextGaussian() / 5.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke.scaleTickAdding = -scl / 40.0F;
         bigsmoke.alphaGlowing = true;
         this.world.spawnEntity(bigsmoke);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.magic),
               parameters.getEnchanted("damage", might) * this.magicPower,
               this.getThrower(),
               result.entityHit,
               true,
               parameters.getEnchanted("knockback", impulse),
               this.posX,
               this.posY - 1.0,
               this.posZ
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
               Sounds.hitmagic_a,
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
      Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      Vec3d pos2 = this.getPositionVector();
      if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world, texture, 0.17F, 240, 0.6F, 0.8F, 1.0F, 0.1F, pos1.distanceTo(pos2), 5, -0.15F, 9999.0F, pos1, pos2
         );
         laser.alphaGlowing = true;
         laser.texstart = 0.1F;
         laser.offset = -0.1F;
         laser.setPosition(pos1.x, pos1.y, pos1.z);
         this.world.spawnEntity(laser);
      }
   }

   @Override
   public void doOptions() {
      GlStateManager.color(0.2F, 0.7F, 1.0F);
   }

   @Override
   public void undoOptions() {
      GlStateManager.color(1.0F, 1.0F, 1.0F);
   }
}
