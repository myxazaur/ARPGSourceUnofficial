package com.Vivern.Arpg.blocks;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidCryon extends Fluid {
   public FluidCryon(String fluidName, String still, String flowing) {
      super(fluidName, new ResourceLocation("arpg", still), new ResourceLocation("arpg", flowing));
      this.setViscosity(700);
      this.setDensity(900);
      this.setTemperature(150);
   }

   public int getColor() {
      return -4263425;
   }
}
