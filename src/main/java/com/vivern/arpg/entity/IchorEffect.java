package com.vivern.arpg.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class IchorEffect extends EntityThrowable {
   public IchorEffect(World world) {
      super(world);
   }

   public IchorEffect(World world, EntityLivingBase thrower) {
      super(world, thrower);
   }

   public IchorEffect(World world, double x, double y, double z) {
      super(world, x, y, z);
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)7);
         this.setDead();
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 7) {
         this.setDead();
      }
   }

   protected float getGravityVelocity() {
      return 0.002F;
   }
}
