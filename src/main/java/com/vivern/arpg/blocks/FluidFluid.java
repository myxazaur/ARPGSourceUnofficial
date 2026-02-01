package com.vivern.arpg.blocks;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidFluid extends Fluid {
   public int color = -4263425;

   public FluidFluid(String fluidName, String still, String flowing, int color) {
      super(fluidName, new ResourceLocation("arpg", still), new ResourceLocation("arpg", flowing));
      this.setViscosity(700);
      this.setDensity(900);
      this.color = color;
   }

   public static class FluidLum extends Fluid {
      public FluidLum(String fluidName, String still, String flowing, int color) {
         super(fluidName, new ResourceLocation("arpg", still), new ResourceLocation("arpg", flowing));
      }

      public int getColor() {
         return 12189679;
      }
   }

   public static class FluidSulfAcid extends Fluid {
      public FluidSulfAcid(String fluidName, String still, String flowing, int color) {
         super(fluidName, new ResourceLocation("arpg", still), new ResourceLocation("arpg", flowing));
      }

      public int getColor() {
         return 10655026;
      }
   }

   public static class FluidSulfGas extends Fluid {
      public FluidSulfGas(String fluidName, String still, String flowing, int color) {
         super(fluidName, new ResourceLocation("arpg", still), new ResourceLocation("arpg", flowing));
      }

      public int getColor() {
         return 12828261;
      }
   }

   public static class FluidToxin extends Fluid {
      public FluidToxin(String fluidName, String still, String flowing, int color) {
         super(fluidName, new ResourceLocation("arpg", still), new ResourceLocation("arpg", flowing));
      }

      public int getColor() {
         return 1024783;
      }
   }
}
