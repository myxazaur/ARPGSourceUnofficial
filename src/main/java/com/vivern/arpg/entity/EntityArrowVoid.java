package com.vivern.arpg.entity;

import com.vivern.arpg.main.ItemsRegister;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityArrowVoid extends AbstractArrow {
   public List<Entity> damaged = new ArrayList<>();

   public EntityArrowVoid(World worldIn) {
      super(worldIn);
      this.setNoGravity(true);
   }

   public EntityArrowVoid(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
      this.setNoGravity(true);
   }

   public EntityArrowVoid(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
      this.setNoGravity(true);
   }

   @Override
   public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
      super.shoot(shooter, pitch, yaw, p_184547_4_, velocity * 0.6F, inaccuracy * 0.8F);
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote && this.ticksExisted % 11 == 0) {
         if (this.shootingEntity != null && this.getDistanceSq(this.shootingEntity) > 4096.0) {
            this.setDead();
         }

         if (this.ticksExisted > 400) {
            this.setDead();
         }
      }
   }

   @Override
   public void onHit(RayTraceResult raytraceResultIn) {
      if (raytraceResultIn.entityHit != null && !this.damaged.contains(raytraceResultIn.entityHit)) {
         this.damaged.add(raytraceResultIn.entityHit);
         super.onHit(raytraceResultIn);
      }
   }

   @Override
   public void ricochet(Entity entityFrom) {
   }

   @Override
   public void removeArrowEntity(Entity entityHitted) {
   }

   @Override
   public SoundEvent getHitSound() {
      return null;
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(ItemsRegister.ARROWVOID);
   }

   public boolean handleWaterMovement() {
      return false;
   }

   @Override
   public void spawnArrowParticles(int particleCount) {
      for (int i = 0; i < particleCount; i++) {
         this.world
            .spawnParticle(
               EnumParticleTypes.PORTAL,
               this.posX + (this.rand.nextDouble() - 0.5) * this.width,
               this.posY + this.rand.nextDouble() * this.height - 0.25,
               this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
               (this.rand.nextDouble() - 0.5) * 2.0,
               -this.rand.nextDouble(),
               (this.rand.nextDouble() - 0.5) * 2.0,
               new int[0]
            );
      }
   }
}
