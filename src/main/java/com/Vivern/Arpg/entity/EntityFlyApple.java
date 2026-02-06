package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.Sounds;
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

public class EntityFlyApple extends EntityThrowable {
   public EntityFlyApple(World world) {
      super(world);
   }

   public EntityFlyApple(World world, EntityLivingBase thrower) {
      super(world, thrower);
   }

   public EntityFlyApple(World world, double x, double y, double z) {
      super(world, x, y, z);
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 3) {
         this.world.playSound(this.posX, this.posY, this.posZ, Sounds.apple, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
      }
   }

   protected void onImpact(RayTraceResult result) {
      int i = 4;
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().expand(4.0, 4.0, 4.0).offset(-2.0, -2.0, -2.0);
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
      if (!list.isEmpty()) {
         for (EntityLivingBase entitylivingbase : list) {
            if (entitylivingbase != this.getThrower()) {
               entitylivingbase.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), i);
               entitylivingbase.hurtResistantTime = 0;
               if (entitylivingbase.getHealth() <= 0.0F) {
                  DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_SAND);
               }
            }
         }
      }

      if (!this.world.isRemote) {
         AppleEffect effect = new AppleEffect(this.world, this.posX, this.posY, this.posZ);
         this.world.spawnEntity(effect);
         this.world.setEntityState(this, (byte)3);
         this.setDead();
      }
   }
}
