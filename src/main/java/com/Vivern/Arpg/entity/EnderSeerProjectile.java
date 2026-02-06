package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EnderSeerProjectile extends EntityThrowable {
   public EntityLivingBase target = null;
   public Vec3d pos1 = this.getPositionVector();
   public Vec3d pos2 = this.getPositionVector();
   public Vec3d pos3 = this.getPositionVector();
   public Vec3d pos4 = this.getPositionVector();
   public Vec3d pos5 = this.getPositionVector();
   ResourceLocation texture = new ResourceLocation("arpg:textures/generic_beam.png");
   ResourceLocation texturexpl = new ResourceLocation("arpg:textures/purple_smoke.png");

   public EnderSeerProjectile(World world) {
      super(world);
   }

   public EnderSeerProjectile(World world, EntityLivingBase thrower) {
      super(world, thrower);
   }

   public EnderSeerProjectile(World world, double x, double y, double z) {
      super(world, x, y, z);
   }

   public EnderSeerProjectile(World world, EntityLivingBase thrower, EntityLivingBase target) {
      super(world, thrower);
      this.target = target;
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

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 100) {
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

      this.motionX /= 1.08;
      this.motionY /= 1.08;
      this.motionZ /= 1.08;
      if (this.target != null) {
         SuperKnockback.applyMove(
            this, -0.3F, this.target.posX, this.target.posY + this.target.height / 2.0F, this.target.posZ
         );
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
               Sounds.ender_seer_projectile,
               SoundCategory.AMBIENT,
               0.6F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );

         for (int ss = 0; ss < 8; ss++) {
            GUNParticle fire = new GUNParticle(
               this.texturexpl,
               0.2F + (float)this.rand.nextGaussian() / 5.0F,
               -0.005F,
               23 + this.rand.nextInt(10),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 27.0F,
               (float)this.rand.nextGaussian() / 27.0F + 0.05F,
               (float)this.rand.nextGaussian() / 27.0F,
               0.8F + (float)this.rand.nextGaussian() / 5.0F,
               0.5F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               true,
               this.rand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -0.04F;
            fire.alphaGlowing = true;
            this.world.spawnEntity(fire);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if ((
            result.entityHit != null
               || this.world
                     .getBlockState(result.getBlockPos())
                     .getBlock()
                     .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
                  != null
         )
         && result.entityHit != this.getThrower()
         && !this.world.isRemote) {
         if (result.entityHit instanceof EntityLivingBase && (result.entityHit == this.target || this.target == null)) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
            SuperKnockback.applyKnockback(entitylivingbase, 1.3F, this.posX, this.posY, this.posZ);
            IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
            double baseValue = entityAttribute.getBaseValue();
            entityAttribute.setBaseValue(1.0);
            entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), 5.0F);
            entitylivingbase.hurtResistantTime = 0;
         }

         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      Vec3d subtraction = this.pos1.subtract(this.pos2);
      if (this.ticksExisted > 2 && this.world.isRemote && this.pos1.lengthSquared() > 1.0E-6 && this.pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world,
            this.texture,
            0.2F,
            240,
            0.8F,
            0.5F,
            1.0F,
            0.17F,
            this.pos1.distanceTo(this.pos2),
            Math.min(6, this.ticksExisted - 2),
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
