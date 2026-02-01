package com.vivern.arpg.entity;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySwordGhost extends EntityThrowable {
   public final ItemStack weaponstack;
   public Vec3d pos1 = this.getPositionVector();
   public Vec3d pos2 = this.getPositionVector();
   public Vec3d pos3 = this.getPositionVector();
   public Vec3d pos4 = this.getPositionVector();
   public Vec3d pos5 = this.getPositionVector();
   ResourceLocation texture = new ResourceLocation("arpg:textures/generic_beam.png");
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");

   public EntitySwordGhost(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.GHOSTSWORD);
   }

   public EntitySwordGhost(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.GHOSTSWORD);
   }

   public EntitySwordGhost(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.GHOSTSWORD);
   }

   public EntitySwordGhost(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.5;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.5;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.5;
      }
   }

   protected float getGravityVelocity() {
      return -0.004F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted == 1 && !this.world.isRemote) {
         this.world.setEntityState(this, (byte)8);
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

      double max = Double.MAX_VALUE;
      EntityLivingBase targ = null;
      if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) == 0) {
         double damageRadius = 5.0;
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
         double damageRadius = 5.0;
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
                  && entitylivingbasex.getHealth() > 0.0F) {
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
                     && entitylivingbasexx.getHealth() > 0.0F) {
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
         this.motionX *= 0.97;
         this.motionY *= 0.97;
         this.motionZ *= 0.97;
         SuperKnockback.applyKnockback(
            this,
            -0.07F,
            targ.posX,
            targ.posY + (targ.getEntityBoundingBox().maxY - targ.getEntityBoundingBox().minY) / 2.0,
            targ.posZ
         );
      } else if (this.ticksExisted % 6 == 0) {
         this.setVelocity(this.rand.nextGaussian() / 20.0, this.rand.nextGaussian() / 20.0, this.rand.nextGaussian() / 20.0);
      }

      if (this.ticksExisted > 300) {
         this.expl();
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
               Sounds.ghost_sword,
               SoundCategory.AMBIENT,
               0.3F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      }

      if (id == 5) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.magic_d,
               SoundCategory.AMBIENT,
               0.7F,
               1.25F + this.rand.nextFloat() / 4.0F,
               false
            );

         for (int ss = 0; ss < 6; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               this.largesmoke,
               0.3F + (float)this.rand.nextGaussian() / 20.0F,
               0.0F,
               15,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 20.0F,
               (float)this.rand.nextGaussian() / 25.0F,
               (float)this.rand.nextGaussian() / 20.0F,
               0.3F,
               1.0F,
               0.7F + (float)this.rand.nextGaussian() / 5.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.06F;
            bigsmoke.alphaGlowing = true;
            this.world.spawnEntity(bigsmoke);
         }
      }
   }

   public void expl() {
      double damageRadius = 1.0;
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (!this.world.isRemote) {
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  Weapons.dealDamage(
                     null,
                     2.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack),
                     this.thrower,
                     entity,
                     true,
                     0.3F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 1.5F,
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
                  Weapons.setPotionIfEntityLB(entity, PotionEffects.INCORPOREITY, 170, 0);
               }
            }
         }
      }

      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.typeOfHit == Type.BLOCK
         && this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
         if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
            this.motionY = -this.motionY / 1.004F;
            this.motionX /= 1.004F;
            this.motionZ /= 1.004F;
         }

         if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
            this.motionZ = -this.motionZ / 1.004F;
            this.motionX /= 1.004F;
            this.motionY /= 1.001F;
         }

         if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
            this.motionX = -this.motionX / 1.004F;
            this.motionZ /= 1.004F;
            this.motionY /= 1.001F;
         }
      }

      if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) == 0) {
         if (result.entityHit != null && Team.checkIsOpponent(this.thrower, result.entityHit)) {
            this.expl();
         }
      } else if (result.entityHit != null && Team.checkIsOpponent(this.thrower, result.entityHit)) {
         if (result.entityHit instanceof EntityLivingBase) {
            if (((EntityLivingBase)result.entityHit).getHealth() > 0.0F) {
               this.expl();
            }
         } else {
            this.expl();
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      Vec3d subtraction = this.pos1.subtract(this.pos2);
      if (this.ticksExisted > 2 && this.world.isRemote && this.pos1.lengthSquared() > 1.0E-6 && this.pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world,
            this.texture,
            0.17F,
            240,
            0.5F,
            1.0F,
            0.8F,
            0.15F,
            this.pos1.distanceTo(this.pos2),
            Math.min(5, this.ticksExisted - 2),
            0.3F,
            1.0F,
            this.pos1,
            this.pos2
         );
         laser.setPosition(this.posX, this.posY, this.posZ);
         this.world.spawnEntity(laser);
      }
   }
}
