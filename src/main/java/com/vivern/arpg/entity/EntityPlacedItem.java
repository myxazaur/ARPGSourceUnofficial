package com.vivern.arpg.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityPlacedItem extends EntityThrowable {
   public ItemStack currentItem = ItemStack.EMPTY;

   public EntityPlacedItem(World worldIn) {
      super(worldIn);
   }

   public EntityPlacedItem(World worldIn, double x, double y, double z) {
      super(worldIn);
      this.setPosition(x, y, z);
   }

   public EntityPlacedItem(World worldIn, EntityLivingBase throwerIn) {
      this(worldIn, throwerIn.posX, throwerIn.posY + throwerIn.getEyeHeight() - 0.1F, throwerIn.posZ);
      this.thrower = throwerIn;
   }

   public EntityPlacedItem(World worldIn, double x, double y, double z, ItemStack currentItem, EntityLivingBase throwerIn) {
      super(worldIn);
      this.setPosition(x, y, z);
      this.currentItem = currentItem;
      this.thrower = throwerIn;
   }

   public ItemStack getPlacedItemStack() {
      return this.currentItem;
   }

   protected float getGravityVelocity() {
      return this.onGround ? 0.0F : 0.03F;
   }

   protected void entityInit() {
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 1000) {
         this.setDead();
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.typeOfHit == Type.BLOCK
         && this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
         if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
            this.motionY = 0.0;
            this.motionX /= 1.01F;
            this.motionZ /= 1.01F;
         }

         if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
            this.motionZ = 0.0;
            this.motionX /= 1.01F;
            this.motionY /= 1.005F;
         }

         if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
            this.motionX = 0.0;
            this.motionZ /= 1.01F;
            this.motionY /= 1.005F;
         }
      }
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      NBTTagCompound nbttagcompound = compound.getCompoundTag("PlacedItem");
      if (!nbttagcompound.isEmpty()) {
         this.currentItem = new ItemStack(nbttagcompound);
      } else {
         this.currentItem = ItemStack.EMPTY;
      }

      super.readEntityFromNBT(nbttagcompound);
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      if (this.getPlacedItemStack() != null) {
         compound.setTag("PlacedItem", this.getPlacedItemStack().writeToNBT(new NBTTagCompound()));
      } else {
         compound.setTag("PlacedItem", ItemStack.EMPTY.writeToNBT(new NBTTagCompound()));
      }

      super.writeEntityToNBT(compound);
   }
}
