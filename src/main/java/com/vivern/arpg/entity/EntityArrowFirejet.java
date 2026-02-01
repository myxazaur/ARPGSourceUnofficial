package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityArrowFirejet extends AbstractArrow {
   static ResourceLocation texture = new ResourceLocation("arpg:textures/fire_beam.png");
   static ResourceLocation circle = new ResourceLocation("arpg:textures/fire_circle2.png");
   public Vec3d pos1 = this.getPositionVector();
   public Vec3d pos2 = this.getPositionVector();
   public Vec3d pos3 = this.getPositionVector();
   public Vec3d pos4 = this.getPositionVector();
   public Vec3d pos5 = this.getPositionVector();
   public float firee = 0.0F;
   public int hits = 3;

   public EntityArrowFirejet(World worldIn) {
      super(worldIn);
   }

   public EntityArrowFirejet(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   public EntityArrowFirejet(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
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
         this.pos1 = this.shootingEntity.getPositionVector();
      }
   }

   @Override
   public void arrowHit(EntityLivingBase living) {
      super.arrowHit(living);
      if (!living.isBurning()) {
         living.setFire(Math.round(this.firee));
         this.hits = 0;
      }

      if (living.getHealth() <= 0.0F && this.rand.nextFloat() < 0.6) {
         DeathEffects.applyDeathEffect(living, DeathEffects.DE_FIRE);
      }
   }

   @Override
   public void ricochet(Entity entityFrom) {
      if (!this.world.isRemote && this.hits <= 0) {
         super.ricochet(entityFrom);
         this.velocityChanged = true;
      }
   }

   @Override
   public void removeArrowEntity(Entity entityHitted) {
      this.pickupStatus = PickupStatus.DISALLOWED;
      if (entityHitted != null && entityHitted.isBurning()) {
         if (--this.hits <= 0) {
            this.setDead();
         }
      } else {
         this.setDead();
      }
   }

   public boolean canRenderOnFire() {
      return false;
   }

   @Override
   public SoundEvent getHitSound() {
      return Sounds.fire_c;
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(ItemsRegister.ARROWFIREJET);
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         GUNParticle bigsmoke1 = new GUNParticle(
            circle,
            0.04F,
            0.0F,
            10,
            220,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke1.alphaTickAdding = -0.1F;
         bigsmoke1.alphaGlowing = true;
         bigsmoke1.scaleTickAdding = 0.1F;
         this.world.spawnEntity(bigsmoke1);
      } else {
         super.handleStatusUpdate(id);
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      super.onEntityUpdate();
      Vec3d subtraction = this.pos1.subtract(this.pos2);
      if (this.world.isRemote
         && this.ticksExisted > 1
         && !this.inGround
         && this.pos1.lengthSquared() > 1.0E-6
         && this.pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world, texture, 0.11F, 220, 0.98F, 0.95F, 1.0F, 0.1F, this.pos1.distanceTo(this.pos2), 8, 0.3F, 2.0F, this.pos1, this.pos2
         );
         laser.setPosition(this.posX, this.posY, this.posZ);
         laser.alphaGlowing = true;
         this.world.spawnEntity(laser);
      }
   }
}
