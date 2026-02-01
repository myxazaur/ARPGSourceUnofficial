package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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

public class BilebiterHomingShoot extends EntityThrowable {
   public final ItemStack weaponstack;
   public Vec3d pos1 = this.getPositionVector();
   public Vec3d pos2 = this.getPositionVector();
   public static ResourceLocation textur = new ResourceLocation("arpg:textures/bilebiter_shoot3.png");
   public static ResourceLocation texturbubble = new ResourceLocation("arpg:textures/bilebiter_shoot4.png");
   public static ResourceLocation texture5 = new ResourceLocation("arpg:textures/bilebiter_shoot5.png");

   public BilebiterHomingShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.BILEBITER);
   }

   public BilebiterHomingShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.BILEBITER);
   }

   public BilebiterHomingShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.BILEBITER);
   }

   public BilebiterHomingShoot(World world, EntityLivingBase thrower, ItemStack itemstack) {
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
      return 0.014F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.pos2.equals(this.pos1)) {
         this.pos2 = this.pos1;
      } else {
         this.pos2 = new Vec3d(this.prevPosX, this.prevPosY, this.prevPosZ);
      }

      this.pos1 = this.getPositionVector();
      double max = Double.MAX_VALUE;
      EntityLivingBase targ = null;
      if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) == 0) {
         double damageRadius = 30.0;
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
         double damageRadius = 30.0;
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
         this.motionX *= 0.93;
         this.motionY *= 0.93;
         this.motionZ *= 0.93;
         SuperKnockback.applyKnockback(
            this,
            -0.37F,
            targ.posX,
            targ.posY + (targ.getEntityBoundingBox().maxY - targ.getEntityBoundingBox().minY) / 2.0,
            targ.posZ
         );
      } else if (this.ticksExisted % 4 == 0) {
         this.setVelocity(this.rand.nextGaussian() / 15.0, this.rand.nextGaussian() / 15.0, this.rand.nextGaussian() / 15.0);
      }

      if (this.ticksExisted > 300) {
         this.expl();
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
               Sounds.bb_impact,
               SoundCategory.AMBIENT,
               1.0F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );

         for (int ss = 0; ss < 3; ss++) {
            TrailParticle trailpart = new TrailParticle(
               textur,
               0.1F,
               0.035F,
               25,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 8.0F,
               (float)this.rand.nextGaussian() / 8.0F + 0.15F,
               (float)this.rand.nextGaussian() / 8.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(180),
               true,
               1.2F,
               texture5,
               0.09F,
               240,
               1.0F,
               1.0F,
               1.0F,
               0.15F,
               5,
               0.3F,
               1.0F
            );
            this.world.spawnEntity(trailpart);
         }

         for (int ss = 0; ss < 8; ss++) {
            Entity bubble = new GUNParticle(
               texturbubble,
               0.09F + (float)this.rand.nextGaussian() / 25.0F,
               0.03F,
               50 + this.rand.nextInt(20),
               200,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 9.0F,
               (float)this.rand.nextGaussian() / 9.0F + 0.15F,
               (float)this.rand.nextGaussian() / 9.0F,
               1.0F,
               0.8F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               false,
               this.rand.nextInt(180),
               true,
               1.3F
            );
            this.world.spawnEntity(bubble);
         }
      }
   }

   public void expl() {
      double damageRadius = 3.0;
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (!this.world.isRemote) {
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.thrower, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  Weapons.dealDamage(
                     null,
                     5 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack),
                     this.thrower,
                     entity,
                     true,
                     0.5F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 2.5F,
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
                  if (entity instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                     if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.4 && entitylivingbase.deathTime < 1) {
                        IAttributeInstance entityColorR = entitylivingbase.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_RED_MAX);
                        IAttributeInstance entityColorG = entitylivingbase.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_GREEN_MAX);
                        IAttributeInstance entityColorB = entitylivingbase.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX);
                        entityColorR.setBaseValue(0.9);
                        entityColorG.setBaseValue(0.8);
                        entityColorB.setBaseValue(0.15);
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_COLOREDACID);
                     }
                  }
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
            texture5,
            0.15F,
            240,
            1.0F,
            1.0F,
            1.0F,
            0.15F,
            this.pos1.distanceTo(this.pos2),
            Math.min(5, this.ticksExisted - 2),
            0.3F,
            1.0F,
            this.pos1,
            this.pos2
         );
         laser.setPosition(this.posX, this.posY, this.posZ);
         laser.alphaGlowing = true;
         this.world.spawnEntity(laser);
      }
   }
}
