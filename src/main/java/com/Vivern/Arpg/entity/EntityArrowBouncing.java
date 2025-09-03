//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityArrowBouncing extends AbstractArrow {
   public boolean allowBounce = true;

   public EntityArrowBouncing(World worldIn) {
      super(worldIn);
   }

   public EntityArrowBouncing(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   public EntityArrowBouncing(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
   }

   @Override
   public boolean doWaterMoveHook() {
      return true;
   }

   @Override
   public int waterParticlesHookAdding() {
      return 2;
   }

   @Override
   public void modifySpeedInWater() {
      double multiplier = 0.86;
      this.motionX *= multiplier;
      this.motionY *= multiplier;
      this.motionZ *= multiplier;
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound compound) {
      super.writeEntityToNBT(compound);
      compound.setBoolean("allowBounce", this.allowBounce);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound compound) {
      super.readEntityFromNBT(compound);
      if (compound.hasKey("allowBounce")) {
         this.allowBounce = compound.getBoolean("allowBounce");
      }
   }

   @Override
   public void onHit(RayTraceResult raytraceResultIn) {
      if (!this.allowBounce || raytraceResultIn.entityHit != null) {
         super.onHit(raytraceResultIn);
      } else if (this.bounce(raytraceResultIn, 0.8)) {
         this.playSound(this.getHitSound(), 1.0F, 1.2F + this.rand.nextFloat() * 0.2F);
         this.allowBounce = false;
         double blockoffset = 0.01;
         if (raytraceResultIn.sideHit != null && raytraceResultIn.hitVec != null) {
            Vec3d hit = new Vec3d(
               raytraceResultIn.sideHit == EnumFacing.WEST
                  ? raytraceResultIn.hitVec.x - blockoffset
                  : raytraceResultIn.hitVec.x,
               raytraceResultIn.sideHit == EnumFacing.DOWN
                  ? raytraceResultIn.hitVec.y - blockoffset
                  : raytraceResultIn.hitVec.y,
               raytraceResultIn.sideHit == EnumFacing.NORTH
                  ? raytraceResultIn.hitVec.z - blockoffset
                  : raytraceResultIn.hitVec.z
            );
            this.setPosition(hit.x, hit.y, hit.z);
         }

         this.setDamage(this.getDamage() + 1.2);
      }
   }

   public boolean bounce(@Nullable RayTraceResult result, double speedMultipl) {
      boolean ret = false;
      if (result != null
         && result.getBlockPos() != null
         && this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
         if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
            this.motionY = -this.motionY * speedMultipl;
            ret = true;
         }

         if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
            this.motionZ = -this.motionZ * speedMultipl;
            ret = true;
         }

         if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
            this.motionX = -this.motionX * speedMultipl;
            ret = true;
         }

         this.velocityChanged = true;
      }

      return ret;
   }

   @Override
   public SoundEvent getHitSound() {
      return Sounds.arrow_bouncing;
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(ItemsRegister.ARROWBOUNCING);
   }
}
