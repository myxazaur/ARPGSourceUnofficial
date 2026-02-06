package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SpikedBurst extends EntityThrowable {
   public String team;
   public float damage = 3.0F;
   static ResourceLocation tex = new ResourceLocation("arpg:textures/shard.png");

   public SpikedBurst(World world) {
      super(world);
   }

   public SpikedBurst(World world, EntityLivingBase thrower) {
      super(world, thrower);
   }

   public SpikedBurst(World world, double x, double y, double z) {
      super(world, x, y, z);
   }

   public SpikedBurst(World world, EntityLivingBase thrower, String team) {
      super(world, thrower);
      this.team = team;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      double mot = 0.5;
      this.motionX = this.motionX + entityThrower.motionX * mot;
      this.motionZ = this.motionZ + entityThrower.motionZ * mot;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * mot;
      }
   }

   protected float getGravityVelocity() {
      return 0.025F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 30) {
         this.setDead();
      }

      if (this.isInWater()) {
         float f1 = 1.2F;
         this.motionX *= f1;
         this.motionY *= f1;
         this.motionZ *= f1;
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         for (int ss = 0; ss < this.rand.nextInt(4) + 2; ss++) {
            GUNParticle part = new GUNParticle(
               tex,
               0.03F + this.rand.nextFloat() / 30.0F,
               0.028F,
               40,
               -1,
               this.world,
               this.prevPosX,
               this.prevPosY,
               this.prevPosZ,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F,
               1.0F,
               0.9F,
               0.85F - this.rand.nextFloat() / 10.0F,
               false,
               this.rand.nextInt(360),
               true,
               1.5F
            );
            this.world.spawnEntity(part);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if ((this.team == null || this.team.isEmpty() || !this.team.equals(Team.getTeamFor(result.entityHit)))
            && Team.checkIsOpponent(this.thrower, result.entityHit)
            && !this.world.isRemote) {
            Weapons.dealDamage(
               null,
               this.damage,
               this.thrower,
               result.entityHit,
               true,
               1.0F,
               this.thrower.posX,
               this.thrower.posY,
               this.thrower.posZ
            );
            result.entityHit.hurtResistantTime = 0;
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.bullet,
                  SoundCategory.AMBIENT,
                  0.4F,
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
               Sounds.bullet,
               SoundCategory.AMBIENT,
               0.4F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }
}
