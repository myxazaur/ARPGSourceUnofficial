package com.vivern.arpg.entity;

import com.vivern.arpg.main.Sounds;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLaserDisk extends EntityThrowable {
   public EntityLaserDisk(World world) {
      super(world);
   }

   public EntityLaserDisk(World world, EntityLivingBase thrower) {
      super(world, thrower);
   }

   public EntityLaserDisk(World world, double x, double y, double z) {
      super(world, x, y, z);
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.world.playSound(this.posX, this.posY, this.posZ, Sounds.apple, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
      }
   }

   protected void onImpact(RayTraceResult result) {
      double damageRadius = 3.0;
      int i = 2;
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
      if (!list.isEmpty()) {
         for (EntityLivingBase entitylivingbase : list) {
            if (entitylivingbase != this.getThrower()) {
               entitylivingbase.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), i);
               entitylivingbase.hurtResistantTime = 0;
            }
         }
      }

      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }
   }
}
