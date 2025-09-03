package com.Vivern.Arpg.weather;

import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WeatherRenderList {
   public List<Double> listX = new ArrayList<>();
   public List<Double> listY = new ArrayList<>();
   public List<Double> listZ = new ArrayList<>();
   public final WeatherPhenomenon weather;

   public WeatherRenderList(WeatherPhenomenon weather) {
      this.weather = weather;
   }

   public void render(double x, double y, double z, float rotationY, float rotationX, float partialTicks) {
   }

   public void doRender(double x, double y, double z, float partialTicks, double noisevalue, float rotationY, float rotationX) {
   }

   public void clearAll() {
      this.listX.clear();
      this.listY.clear();
      this.listZ.clear();
   }

   public boolean isListsEmpty() {
      return this.listX.isEmpty() || this.listY.isEmpty() || this.listZ.isEmpty();
   }
}
