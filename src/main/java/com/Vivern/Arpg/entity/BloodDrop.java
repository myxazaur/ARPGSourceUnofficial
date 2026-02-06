package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.SuperKnockback;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BloodDrop extends EntityThrowable {
   public EntityLivingBase throwerthrower = null;

   public BloodDrop(World world) {
      super(world);
   }

   public BloodDrop(World world, EntityLivingBase thrower, EntityLivingBase throwerthrower) {
      super(world, thrower);
      this.throwerthrower = throwerthrower;
   }

   public BloodDrop(World world, double x, double y, double z, EntityLivingBase throwerthrower) {
      super(world, x, y, z);
      this.throwerthrower = throwerthrower;
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 200) {
         this.setDead();
      }

      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.ticksExisted % 4 == 0) {
         Entity target = this.throwerthrower;
         if (target != null && this.ticksExisted > 7) {
            SuperKnockback.applyMove(this, -0.1F, target.posX, target.posY + target.height / 2.0F, target.posZ);
            double damageRadius = 1.0;
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (entitylivingbase == target) {
                     entitylivingbase.heal(0.7F);
                     this.setDead();
                  }
               }
            }
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
   }
}
