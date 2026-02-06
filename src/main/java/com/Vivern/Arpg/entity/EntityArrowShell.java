package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityArrowShell extends AbstractArrow {
   public EntityArrowShell(World worldIn) {
      super(worldIn);
   }

   public EntityArrowShell(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   public EntityArrowShell(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
   }

   @Override
   public boolean doWaterMoveHook() {
      return true;
   }

   @Override
   public int waterParticlesHookAdding() {
      return this.rand.nextFloat() < 0.2F ? 1 : 0;
   }

   @Override
   public void modifySpeedInWater() {
      double multiplier = 0.97;
      this.motionX *= multiplier;
      this.motionY *= multiplier;
      this.motionZ *= multiplier;
   }

   @Override
   public void onHit(RayTraceResult raytraceResultIn) {
      double speed = GetMOP.getSpeed(this);
      super.onHit(raytraceResultIn);
      if (!this.world.isRemote && raytraceResultIn != null && speed > 1.0 && this.pickupStatus == PickupStatus.ALLOWED) {
         int max = 3 + this.rand.nextInt(4);
         boolean block = raytraceResultIn.typeOfHit == Type.BLOCK && raytraceResultIn.sideHit != null;
         float power = 0.5F;
         Vec3d poss = raytraceResultIn.hitVec != null && block ? raytraceResultIn.hitVec : this.getPositionVector();
         float blockoffset = 0.1F;
         if (block) {
            for (int i = 0; i < max; i++) {
               ShellShard newarrow = new ShellShard(
                  this.world,
                  raytraceResultIn.sideHit == EnumFacing.WEST ? poss.x - blockoffset : poss.x,
                  raytraceResultIn.sideHit == EnumFacing.DOWN ? poss.y - blockoffset : poss.y,
                  raytraceResultIn.sideHit == EnumFacing.NORTH ? poss.z - blockoffset : poss.z
               );
               newarrow.motionX = raytraceResultIn.sideHit.getAxis() == Axis.X
                  ? raytraceResultIn.sideHit.getXOffset() * 0.5F * power
                  : (this.rand.nextFloat() - 0.5F) * power;
               newarrow.motionY = raytraceResultIn.sideHit.getAxis() == Axis.Y
                  ? raytraceResultIn.sideHit.getYOffset() * 0.5F * power
                  : (this.rand.nextFloat() - 0.5F) * power;
               newarrow.motionZ = raytraceResultIn.sideHit.getAxis() == Axis.Z
                  ? raytraceResultIn.sideHit.getZOffset() * 0.5F * power
                  : (this.rand.nextFloat() - 0.5F) * power;
               this.world.spawnEntity(newarrow);
               newarrow.shootingEntity = this.shootingEntity;
               newarrow.setDamage(this.getDamage());
               newarrow.velocityChanged = true;
            }
         } else {
            for (int i = 0; i < max; i++) {
               ShellShard newarrow = new ShellShard(this.world, poss.x, poss.y, poss.z);
               newarrow.motionX = (this.rand.nextFloat() - 0.5F) * power + this.motionX * 0.2;
               newarrow.motionY = (this.rand.nextFloat() - 0.4F) * power + this.motionY * 0.2;
               newarrow.motionZ = (this.rand.nextFloat() - 0.5F) * power + this.motionZ * 0.2;
               this.world.spawnEntity(newarrow);
               newarrow.shootingEntity = this.shootingEntity;
               newarrow.setDamage(this.getDamage());
               newarrow.velocityChanged = true;
            }
         }

         this.removeArrowEntity(raytraceResultIn.entityHit);
      }
   }

   @Override
   public SoundEvent getHitSound() {
      return Sounds.arrow_shell;
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(ItemsRegister.ARROWSHELL);
   }
}
