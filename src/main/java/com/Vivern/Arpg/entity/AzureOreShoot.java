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
import com.Vivern.Arpg.renders.AnimatedGParticle;
import java.util.List;
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

public class AzureOreShoot extends EntityThrowable {
   public final ItemStack weaponstack;
   public EntityLivingBase targ = null;
   public float magicPower = 1.0F;
   public float homingPower = -0.37F;
   public boolean powered;
   static ResourceLocation explode = new ResourceLocation("arpg:textures/magic_explode3.png");
   static ResourceLocation texture = new ResourceLocation("arpg:textures/generic_beam5.png");

   public AzureOreShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.STAFFOFTHEAZUREORE);
      this.setHomingPower();
   }

   public AzureOreShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.STAFFOFTHEAZUREORE);
      this.setHomingPower();
   }

   public AzureOreShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.STAFFOFTHEAZUREORE);
      this.setHomingPower();
   }

   public AzureOreShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
      this.setHomingPower();
   }

   public void setHomingPower() {
      if (this.powered) {
         this.homingPower = 0.0F;
      } else {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         float homingpower = parameters.get("homing_power") + this.rand.nextFloat() * parameters.get("homing_power_random");
         this.homingPower = -homingpower;
      }
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
      if (this.powered) {
         if (!this.world.isRemote && (this.ticksExisted < 2 || this.ticksExisted % 10 == 0)) {
            this.world.setEntityState(this, (byte)11);
         }

         if (this.ticksExisted > 60) {
            this.setDead();
         }
      } else if (this.ticksExisted > 140) {
         this.setDead();
      }

      if (this.targ == null) {
         if (this.ticksExisted % 5 == 0) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            if (!this.powered) {
               double max = Double.MAX_VALUE;
               double damageRadius = parameters.getEnchanted("find_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
               AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
                  .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
                  .offset(-damageRadius, -damageRadius, -damageRadius);
               List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
               if (!list.isEmpty()) {
                  for (EntityLivingBase entitylivingbase : list) {
                     if (entitylivingbase.isCreatureType(EnumCreatureType.MONSTER, false)
                        && Team.checkIsOpponent(this.thrower, entitylivingbase)
                        && entitylivingbase != this.getThrower()
                        && EntitySelectors.NOT_SPECTATING.apply(entitylivingbase)
                        && entitylivingbase.getHealth() > 0.0F
                        && GetMOP.thereIsNoBlockCollidesBetween(
                           this.world, GetMOP.entityCenterPos(this), GetMOP.entityCenterPos(entitylivingbase), 1.0F, null, false
                        )) {
                        double dist = entitylivingbase.getDistance(this);
                        if (dist < max) {
                           max = dist;
                           this.targ = entitylivingbase;
                        }
                     }
                  }

                  if (this.targ == null) {
                     for (EntityLivingBase entitylivingbasex : list) {
                        if (Team.checkIsOpponent(this.thrower, entitylivingbasex)
                           && entitylivingbasex != this.getThrower()
                           && EntitySelectors.NOT_SPECTATING.apply(entitylivingbasex)
                           && entitylivingbasex.getHealth() > 0.0F
                           && GetMOP.thereIsNoBlockCollidesBetween(
                              this.world, GetMOP.entityCenterPos(this), GetMOP.entityCenterPos(entitylivingbasex), 1.0F, null, false
                           )) {
                           double dist = entitylivingbasex.getDistance(this);
                           if (dist < max) {
                              max = dist;
                              this.targ = entitylivingbasex;
                           }
                        }
                     }
                  }
               }
            }

            if (this.targ == null) {
               float randommotion = this.powered
                  ? parameters.getEnchanted("random_motion_powered", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, this.weaponstack))
                  : parameters.getEnchanted("random_motion", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, this.weaponstack));
               this.motionX = this.motionX + this.rand.nextGaussian() * randommotion;
               this.motionY = this.motionY + this.rand.nextGaussian() * randommotion;
               this.motionZ = this.motionZ + this.rand.nextGaussian() * randommotion;
            } else {
               this.motionX *= 0.5;
               this.motionY *= 0.5;
               this.motionZ *= 0.5;
            }
         }
      } else {
         this.motionX *= 0.92;
         this.motionY *= 0.92;
         this.motionZ *= 0.92;
         SuperKnockback.applyMove(
            this, this.homingPower, this.targ.posX, this.targ.posY + this.targ.height / 2.0F, this.targ.posZ
         );
         this.homingPower -= 0.023F;
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         AnimatedGParticle anim = new AnimatedGParticle(
            explode,
            0.5F,
            -0.01F,
            32,
            200,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (this.rand.nextFloat() - 0.5F) / 20.0F,
            0.0F,
            (this.rand.nextFloat() - 0.5F) / 20.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            0
         );
         anim.framecount = 16;
         anim.alphaGlowing = true;
         anim.randomDeath = false;
         anim.animDelay = 2;
         this.world.spawnEntity(anim);
      }

      if (id == 11) {
         this.powered = true;
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if ((!this.powered || result.entityHit.isEntityAlive())
            && Team.checkIsOpponent(this.thrower, result.entityHit)
            && !this.world.isRemote) {
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
               this.prevPosX,
               this.prevPosY,
               this.prevPosZ
            );
            result.entityHit.hurtResistantTime = 0;
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.azure_ore_staff_impact,
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
               Sounds.azure_ore_staff_impact,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      if (!this.powered) {
         Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         Vec3d pos2 = this.getPositionVector();
         if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, texture, 0.12F, 190, 0.0F, 0.04F, 0.8F, 0.1F, pos1.distanceTo(pos2), 7, -0.15F, 9999.0F, pos1, pos2
            );
            laser.alphaGlowing = true;
            laser.texstart = 0.1F;
            laser.offset = 0.1F;
            laser.setPosition(pos1.x, pos1.y, pos1.z);
            this.world.spawnEntity(laser);
         }
      }
   }
}
