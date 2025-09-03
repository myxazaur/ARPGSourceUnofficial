//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.FluidEvent.FluidDrainingEvent;
import net.minecraftforge.fluids.FluidEvent.FluidFillingEvent;

public class FluidStorage extends FluidTank {
   public IFillDrain filldrain = null;

   public FluidStorage(int capacity) {
      super(capacity);
   }

   public FluidStorage(@Nullable FluidStack fluidStack, int capacity) {
      super(fluidStack, capacity);
   }

   public FluidStorage(Fluid fluid, int amount, int capacity) {
      super(fluid, amount, capacity);
   }

   public FluidStorage(int capacity, IFillDrain filldrain) {
      super(capacity);
      this.filldrain = filldrain;
   }

   public FluidStorage readFromNBT(NBTTagCompound nbt, String name) {
      if (nbt.hasKey(name)) {
         NBTTagCompound intag = nbt.getCompoundTag(name);
         if (!intag.hasKey("Empty")) {
            FluidStack fluid = FluidStack.loadFluidStackFromNBT(intag);
            this.setFluid(fluid);
         } else {
            this.setFluid(null);
         }
      } else {
         this.setFluid(null);
      }

      return this;
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbt, String name) {
      if (!nbt.hasKey(name)) {
         nbt.setTag(name, new NBTTagCompound());
      }

      NBTTagCompound intag = nbt.getCompoundTag(name);
      if (this.fluid != null) {
         this.fluid.writeToNBT(intag);
      } else {
         intag.setString("Empty", "");
      }

      return nbt;
   }

   @Nullable
   public FluidStack getFluid() {
      return this.fluid;
   }

   public void setFluid(@Nullable FluidStack fluid) {
      this.fluid = fluid;
   }

   public int getFluidAmount() {
      return this.fluid == null ? 0 : this.fluid.amount;
   }

   public int getCapacity() {
      return this.capacity;
   }

   public void setCapacity(int capacity) {
      this.capacity = capacity;
   }

   public void setTileEntity(TileEntity tile) {
      this.tile = tile;
   }

   public int fill(FluidStack resource, boolean doFill) {
      if (this.filldrain != null) {
         return this.filldrain.fill(this, resource, doFill);
      } else {
         return !this.canFillFluidType(resource) ? 0 : this.fillInternal(resource, doFill);
      }
   }

   public int fillInternal(FluidStack resource, boolean doFill) {
      if (resource == null || resource.amount <= 0) {
         return 0;
      } else if (!doFill) {
         if (this.fluid == null) {
            return Math.min(this.capacity, resource.amount);
         } else {
            return !this.fluid.isFluidEqual(resource) ? 0 : Math.min(this.capacity - this.fluid.amount, resource.amount);
         }
      } else if (this.fluid == null) {
         this.fluid = new FluidStack(resource, Math.min(this.capacity, resource.amount));
         this.onContentsChanged(true);
         if (this.tile != null) {
            FluidEvent.fireEvent(new FluidFillingEvent(this.fluid, this.tile.getWorld(), this.tile.getPos(), this, this.fluid.amount));
         }

         return this.fluid.amount;
      } else if (!this.fluid.isFluidEqual(resource)) {
         return 0;
      } else {
         int filled = this.capacity - this.fluid.amount;
         if (resource.amount < filled) {
            this.fluid.amount = this.fluid.amount + resource.amount;
            filled = resource.amount;
         } else {
            this.fluid.amount = this.capacity;
         }

         this.onContentsChanged(false);
         if (this.tile != null) {
            FluidEvent.fireEvent(new FluidFillingEvent(this.fluid, this.tile.getWorld(), this.tile.getPos(), this, filled));
         }

         return filled;
      }
   }

   public FluidStack drain(int maxDrain, boolean doDrain) {
      if (this.filldrain != null) {
         return this.filldrain.drain(this, maxDrain, doDrain);
      } else {
         return !this.canDrainFluidType(this.fluid) ? null : this.drainInternal(maxDrain, doDrain);
      }
   }

   @Nullable
   public FluidStack drainInternal(FluidStack resource, boolean doDrain) {
      return resource != null && resource.isFluidEqual(this.getFluid()) ? this.drainInternal(resource.amount, doDrain) : null;
   }

   @Nullable
   public FluidStack drainInternal(int maxDrain, boolean doDrain) {
      if (this.fluid != null && maxDrain > 0) {
         int drained = maxDrain;
         if (this.fluid.amount < maxDrain) {
            drained = this.fluid.amount;
         }

         FluidStack stack = new FluidStack(this.fluid, drained);
         if (doDrain) {
            this.fluid.amount -= drained;
            if (this.fluid.amount <= 0) {
               this.fluid = null;
               this.onContentsChanged(true);
            } else {
               this.onContentsChanged(false);
            }

            if (this.tile != null) {
               FluidEvent.fireEvent(new FluidDrainingEvent(this.fluid, this.tile.getWorld(), this.tile.getPos(), this, drained));
            }
         }

         return stack;
      } else {
         return null;
      }
   }

   public boolean canFill() {
      return this.canFill;
   }

   public boolean canDrain() {
      return this.canDrain;
   }

   public void setCanFill(boolean canFill) {
      this.canFill = canFill;
   }

   public void setCanDrain(boolean canDrain) {
      this.canDrain = canDrain;
   }

   public boolean canFillFluidType(FluidStack fluid) {
      return this.canFill();
   }

   public boolean canDrainFluidType(@Nullable FluidStack fluid) {
      return fluid != null && this.canDrain();
   }

   protected void onContentsChanged(boolean fluidTypeChanges) {
      if (this.filldrain != null) {
         this.filldrain.onContentsChanged(this, fluidTypeChanges);
      }
   }

   public FluidTankInfo getInfo() {
      return new FluidTankInfo(this);
   }
}
