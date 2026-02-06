package com.Vivern.Arpg.elements.animation;

import com.Vivern.Arpg.elements.AnimationCapabilityProvider;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class AnimationAndFluidCapProvider extends AnimationCapabilityProvider implements IFluidHandlerItem {
   public static final String FLUID_NBT_KEY = "Fluid";
   @Nonnull
   protected ItemStack container;
   protected int capacity;

   public AnimationAndFluidCapProvider(@Nonnull ItemStack container, int capacity) {
      this.container = container;
      this.capacity = capacity;
   }

   @Nonnull
   public ItemStack getContainer() {
      return this.container;
   }

   @Nullable
   public FluidStack getFluid() {
      NBTTagCompound tagCompound = this.container.getTagCompound();
      return tagCompound != null && tagCompound.hasKey("Fluid") ? FluidStack.loadFluidStackFromNBT(tagCompound.getCompoundTag("Fluid")) : null;
   }

   protected void setFluid(FluidStack fluid) {
      if (!this.container.hasTagCompound()) {
         this.container.setTagCompound(new NBTTagCompound());
      }

      NBTTagCompound fluidTag = new NBTTagCompound();
      fluid.writeToNBT(fluidTag);
      this.container.getTagCompound().setTag("Fluid", fluidTag);
   }

   public IFluidTankProperties[] getTankProperties() {
      return new FluidTankProperties[]{new FluidTankProperties(this.getFluid(), this.capacity)};
   }

   public int fill(FluidStack resource, boolean doFill) {
      if (this.container.getCount() == 1 && resource != null && resource.amount > 0 && this.canFillFluidType(resource)) {
         FluidStack contained = this.getFluid();
         if (contained == null) {
            int fillAmount = Math.min(this.capacity, resource.amount);
            if (doFill) {
               FluidStack filled = resource.copy();
               filled.amount = fillAmount;
               this.setFluid(filled);
            }

            return fillAmount;
         } else if (contained.isFluidEqual(resource)) {
            int fillAmount = Math.min(this.capacity - contained.amount, resource.amount);
            if (doFill && fillAmount > 0) {
               contained.amount += fillAmount;
               this.setFluid(contained);
            }

            return fillAmount;
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   public FluidStack drain(FluidStack resource, boolean doDrain) {
      return this.container.getCount() == 1 && resource != null && resource.amount > 0 && resource.isFluidEqual(this.getFluid())
         ? this.drain(resource.amount, doDrain)
         : null;
   }

   public FluidStack drain(int maxDrain, boolean doDrain) {
      if (this.container.getCount() == 1 && maxDrain > 0) {
         FluidStack contained = this.getFluid();
         if (contained != null && contained.amount > 0 && this.canDrainFluidType(contained)) {
            int drainAmount = Math.min(contained.amount, maxDrain);
            FluidStack drained = contained.copy();
            drained.amount = drainAmount;
            if (doDrain) {
               contained.amount -= drainAmount;
               if (contained.amount == 0) {
                  this.setContainerToEmpty();
               } else {
                  this.setFluid(contained);
               }
            }

            return drained;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public boolean canFillFluidType(FluidStack fluid) {
      return true;
   }

   public boolean canDrainFluidType(FluidStack fluid) {
      return true;
   }

   protected void setContainerToEmpty() {
      this.container.getTagCompound().removeTag("Fluid");
   }

   @Override
   public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
      return capability == CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
   }

   @Nullable
   @Override
   public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
      if (capability == CAPABILITY) {
         return (T)this.animations;
      } else {
         return (T)(capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY ? this : null);
      }
   }
}
