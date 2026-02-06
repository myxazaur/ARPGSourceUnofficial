package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WhispersShoot extends EntityThrowable implements IEntitySynchronize {
   public final ItemStack weaponstack;
   public float cutterSize = 2.6F;
   public float cutterSizeRender = 2.0F;
   public float magicPower = 1.0F;
   public List<Entity> impacted = new ArrayList<>();
   public Vec3d bladeNormal = new Vec3d(0.0, 1.0, 0.0);
   public float rotationRoll = 0.0F;
   public boolean powered = false;
   public int lastBounces = 5;
   static ResourceLocation cloud = new ResourceLocation("arpg:textures/whispers_part.png");

   public WhispersShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.WHISPERSBLADE);
      this.setSize(0.1F, 0.1F);
   }

   public WhispersShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.WHISPERSBLADE);
      this.setSize(0.1F, 0.1F);
   }

   public WhispersShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.WHISPERSBLADE);
      this.setSize(0.1F, 0.1F);
   }

   public WhispersShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
      this.setSize(0.1F, 0.1F);
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      double mot = 0.3;
      this.motionX = this.motionX + entityThrower.motionX * mot;
      this.motionZ = this.motionZ + entityThrower.motionZ * mot;
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderDist(double distance) {
      double d0 = 64.0;
      return distance < d0 * d0;
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public double min(double d1, double d2, double d3, double d4) {
      return Math.min(Math.min(d1, d2), Math.min(d3, d4));
   }

   public double max(double d1, double d2, double d3, double d4) {
      return Math.max(Math.max(d1, d2), Math.max(d3, d4));
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted < 2 || this.ticksExisted % 10 == 0) {
         Vec3d look = this.getVectorForRotation(this.rotationPitch, -this.rotationYaw);
         this.bladeNormal = GetMOP.rotateVecAroundAxis(
            GetMOP.PitchYawToVec3d(this.rotationPitch - 90.0F, -this.rotationYaw), look, (float)Math.toRadians(this.rotationRoll)
         );
         IEntitySynchronize.sendSynchronize(this, 64.0, this.rotationRoll, this.cutterSize);
         if (this.powered) {
            this.world.setEntityState(this, (byte)13);
         }
      }

      if (!this.world.isRemote) {
         if (this.ticksExisted > 80) {
            this.setDead();
         }

         if (!this.isDead) {
            Vec3d center = this.getPositionVector();
            Vec3d centerPrev = new Vec3d(this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ);
            Vec3d look = this.getVectorForRotation(this.rotationPitch, -this.rotationYaw);
            Vec3d lookScaled = look.scale(this.cutterSize / 2.0F);
            Vec3d rightTip = GetMOP.rotateVecAroundAxis(lookScaled, this.bladeNormal, 1.570796F);
            Vec3d leftTip = GetMOP.rotateVecAroundAxis(lookScaled, this.bladeNormal, -1.570796F);
            Vec3d rightPos = center.add(rightTip);
            Vec3d leftPos = center.add(leftTip);
            Vec3d prevRightPos = centerPrev.add(rightTip);
            Vec3d prevLeftPos = centerPrev.add(leftTip);
            AxisAlignedBB aabb = new AxisAlignedBB(
               this.min(rightPos.x, leftPos.x, prevRightPos.x, prevLeftPos.x),
               this.min(rightPos.y, leftPos.y, prevRightPos.y, prevLeftPos.y),
               this.min(rightPos.z, leftPos.z, prevRightPos.z, prevLeftPos.z),
               this.max(rightPos.x, leftPos.x, prevRightPos.x, prevLeftPos.x),
               this.max(rightPos.y, leftPos.y, prevRightPos.y, prevLeftPos.y),
               this.max(rightPos.z, leftPos.z, prevRightPos.z, prevLeftPos.z)
            );
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, aabb);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);

            for (Entity entity : list) {
               if (GetMOP.isBoxInPlane(center, this.bladeNormal, entity.getEntityBoundingBox())
                  && Team.checkIsOpponent(this.thrower, entity)
                  && !this.impacted.contains(entity)) {
                  Weapons.dealDamage(
                     new WeaponDamage(
                        this.weaponstack, this.getThrower(), this, false, false, this, this.powered ? WeaponDamage.dismember : WeaponDamage.blade
                     ),
                     (this.powered ? parameters.getEnchanted("damage_powered", might) : parameters.getEnchanted("damage_edge", might)) * this.magicPower,
                     this.getThrower(),
                     entity,
                     true,
                     this.powered ? parameters.getEnchanted("knockback_powered", impulse) : parameters.getEnchanted("knockback_edge", impulse)
                  );
                  entity.hurtResistantTime = 0;
                  DeathEffects.applyDeathEffect(entity, DeathEffects.DE_CUT, this.powered ? 1.0F : 0.3F);
                  this.world.setEntityState(this, (byte)10);
                  this.impacted.add(entity);
               }
            }
         }
      }
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 2) {
         this.rotationRoll = (float)args[0];
         this.cutterSizeRender = (float)args[1];
      } else if (args.length == 3) {
         Vec3d center = new Vec3d(args[0], args[1], args[2]);
         Vec3d look = this.getVectorForRotation(this.rotationPitch, -this.rotationYaw);
         Vec3d lookScaled = look.scale(this.powered ? 2.3 : 1.3);
         Vec3d rightTip = GetMOP.rotateVecAroundAxis(lookScaled, this.bladeNormal, 1.570796F);

         for (int ss = 0; ss < 21; ss++) {
            Vec3d partpos = center.add(rightTip.scale((this.rand.nextFloat() - 0.5) * this.cutterSizeRender));
            int lt = this.rand.nextInt(7) + 12;
            GUNParticle bigsmoke = new GUNParticle(
               cloud,
               0.17F + this.rand.nextFloat() * 0.07F,
               0.0F,
               lt,
               240,
               this.world,
               partpos.x,
               partpos.y,
               partpos.z,
               (float)this.rand.nextGaussian() / 25.0F,
               (float)this.rand.nextGaussian() / 25.0F,
               (float)this.rand.nextGaussian() / 25.0F,
               1.0F,
               0.9F + this.rand.nextFloat() / 10.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -1.0F / lt;
            bigsmoke.alphaGlowing = true;
            bigsmoke.scaleTickAdding = -0.006F;
            this.world.spawnEntity(bigsmoke);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.whispers_blade_impact,
               SoundCategory.AMBIENT,
               1.1F,
               0.9F + this.rand.nextFloat() * 0.2F,
               false
            );
      }

      if (id == 9) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.whispers_blade_ricochet,
               SoundCategory.AMBIENT,
               1.0F,
               0.95F + this.rand.nextFloat() * 0.1F,
               false
            );
      }

      if (id == 10) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.whispers_blade_hit,
               SoundCategory.AMBIENT,
               1.1F,
               0.9F + this.rand.nextFloat() * 0.2F,
               false
            );
      }

      if (id == 11) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.whispers_blade_bighit,
               SoundCategory.AMBIENT,
               1.0F,
               0.9F + this.rand.nextFloat() * 0.2F,
               false
            );
      }

      if (id == 13) {
         this.powered = true;
      }
   }

   public boolean handleWaterMovement() {
      return false;
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.world.isRemote) {
         if (this.powered) {
            boolean bounced = this.bounce(result, 1.0);
            if (bounced) {
               this.lastBounces--;
               this.world.setEntityState(this, (byte)9);
            }

            if (this.lastBounces <= 0) {
               if (result != null && result.hitVec != null) {
                  IEntitySynchronize.sendSynchronize(
                     this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z
                  );
               }

               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.blade),
                  parameters.getEnchanted("damage", might) * this.magicPower,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  parameters.getEnchanted("knockback", impulse)
               );
               result.entityHit.hurtResistantTime = 0;
               DeathEffects.applyDeathEffect(result.entityHit, DeathEffects.DE_CUT, 0.5F);
               IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY, this.posZ);
               this.world.setEntityState(this, (byte)11);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            if (result.hitVec != null) {
               IEntitySynchronize.sendSynchronize(
                  this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z
               );
            }

            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }

   public boolean bounce(@Nullable RayTraceResult result, double speedMultipl) {
      boolean ret = false;
      if (result != null
         && result.getBlockPos() != null
         && this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
         if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
            this.motionY = -this.motionY * speedMultipl;
            ret = true;
         }

         if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
            this.motionZ = -this.motionZ * speedMultipl;
            ret = true;
         }

         if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
            this.motionX = -this.motionX * speedMultipl;
            ret = true;
         }

         this.velocityChanged = true;
      }

      return ret;
   }
}
