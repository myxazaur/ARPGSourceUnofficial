package com.vivern.arpg.tileentity;

import net.minecraftforge.fluids.FluidStack;

public interface IFillDrain {
   int fill(FluidStorage var1, FluidStack var2, boolean var3);

   FluidStack drain(FluidStorage var1, int var2, boolean var3);

   void onContentsChanged(FluidStorage var1, boolean var2);
}
