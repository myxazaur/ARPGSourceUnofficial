package com.vivern.arpg.entity;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ChlorineCloud extends EntityThrowable {
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");
   public int ticks = 0;
   public double moveX = 0.0;
   public double moveY = 0.0;
   public double moveZ = 0.0;
   public static ParticleTracker.TrackerSmoothShowHide trssh = new ParticleTracker.TrackerSmoothShowHide(
      new Vec3d[]{new Vec3d(0.0, 15.0, 0.0666), new Vec3d(20.0, 45.0, -0.04)}, null
   );

   public ChlorineCloud(World world) {
      super(world);
      this.setSize(0.1F, 0.1F);
   }

   public ChlorineCloud(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.setSize(0.1F, 0.1F);
   }

   public ChlorineCloud(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.setSize(0.1F, 0.1F);
   }

   public boolean isInRangeToRenderDist(double distance) {
      return false;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted % 5 == 0) {
         if (!this.world.isRemote) {
            this.ticks += 5;
            if (this.ticks > 100) {
               this.setDead();
            }

            double damageRadius = 3.5;
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  PotionEffect eff = entitylivingbase.getActivePotionEffect(PotionEffects.CHLORITE);
                  int amp = 0;
                  if (eff != null) {
                     amp = eff.getAmplifier();
                  }

                  entitylivingbase.addPotionEffect(new PotionEffect(PotionEffects.CHLORITE, amp * 300 + 500, amp + 1));
               }
            }
         }

         this.motionX *= 0.75;
         this.motionY *= 0.75;
         this.motionZ *= 0.75;
         this.motionX = this.motionX + this.moveX;
         this.motionY = this.motionY + this.moveY;
         this.motionZ = this.motionZ + this.moveZ;
         this.moveY -= 0.005;
         this.velocityChanged = true;
      }
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.setInteger("ticks", this.ticks);
      super.writeEntityToNBT(compound);
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("ticks")) {
         this.ticks = compound.getInteger("ticks");
      }

      super.readEntityFromNBT(compound);
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onEntityUpdate() {
      super.onEntityUpdate();
      if (this.world.isRemote && this.rand.nextFloat() < 0.2F) {
         GUNParticle bigsmoke = new GUNParticle(
            this.largesmoke,
            1.5F + (float)this.rand.nextGaussian() / 3.0F,
            3.0E-4F,
            45,
            110,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 17.0F,
            (float)this.rand.nextGaussian() / 17.0F,
            (float)this.rand.nextGaussian() / 17.0F,
            0.8F + this.rand.nextFloat() * 0.2F,
            0.8F + this.rand.nextFloat() * 0.2F,
            0.4F,
            true,
            this.rand.nextInt(360),
            true,
            1.0F
         );
         bigsmoke.tracker = trssh;
         bigsmoke.alpha = 0.0F;
         this.world.spawnEntity(bigsmoke);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit == null) {
         Block block = this.world.getBlockState(result.getBlockPos()).getBlock();
         if (result != null
            && result.getBlockPos() != null
            && block.getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos()) != null
            && block != BlocksRegister.CHLORINEBELCHER) {
            if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
               this.motionY = -this.motionY;
               this.moveY = -this.moveY;
            }

            if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
               this.motionZ = -this.motionZ;
               this.moveZ = -this.moveZ;
            }

            if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
               this.motionX = -this.motionX;
               this.moveX = -this.moveX;
            }

            this.velocityChanged = true;
         }
      }
   }
}
