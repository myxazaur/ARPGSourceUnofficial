package com.vivern.arpg.dimensions.toxicomania;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.main.DimensionsRegister;
import com.vivern.arpg.renders.RenderToxicSky;
import com.vivern.arpg.weather.PoisonRain;
import com.vivern.arpg.weather.TimeOfDayProvider;
import com.vivern.arpg.weather.WorldEventsHandler;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.util.vector.Vector4f;

public class DimensionToxicomania extends AbstractWorldProvider {
   public static TimeOfDayProvider timeOfDayProvider = new TimeOfDayProvider()
      .addO(
         21900,
         22600,
         new Vector4f(0.07450981F, 0.105882354F, 0.14901961F, 0.0F),
         new Vector4f(0.88235295F, 0.78431374F, 0.21960784F, 0.0F),
         new Vector4f(1.0F, 0.8666667F, 0.21960784F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.047058824F, 0.11372549F, 0.8980392F),
         new Vector4f(1.0F, 0.8352941F, 0.16862746F, 0.8F)
      )
      .addO(
         23800,
         25300,
         new Vector4f(0.003921569F, 0.16470589F, 0.043137256F, 0.99607843F),
         new Vector4f(0.0F, 0.2F, 0.050980393F, 1.0F),
         new Vector4f(0.007843138F, 0.015686275F, 0.2F, 0.99607843F),
         new Vector4f(0.79607844F, 0.9098039F, 0.41568628F, 0.99607843F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.99215686F),
         new Vector4f(0.10980392F, 0.5647059F, 0.34117648F, 0.21176471F),
         new Vector4f(0.99215686F, 1.0F, 0.3882353F, 0.98039216F)
      )
      .addO(
         11500,
         12100,
         new Vector4f(0.15686275F, 0.003921569F, 0.15686275F, 0.9764706F),
         new Vector4f(0.12156863F, 0.5176471F, 0.1764706F, 0.9843137F),
         new Vector4f(0.2F, 1.0F, 0.019607844F, 0.99607843F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.96862745F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.99215686F),
         new Vector4f(0.49411765F, 0.0F, 0.65882355F, 0.5921569F),
         new Vector4f(0.5529412F, 1.0F, 0.25882354F, 0.8F)
      )
      .addO(
         12500,
         13500,
         new Vector4f(0.015686275F, 0.011764706F, 0.03529412F, 0.9764706F),
         new Vector4f(0.3882353F, 0.49019608F, 0.2901961F, 0.99607843F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.05490196F, 0.07058824F, 0.08235294F, 0.0F),
         new Vector4f(0.0F, 0.078431375F, 0.043137256F, 0.54901963F),
         new Vector4f(0.16862746F, 0.21176471F, 0.24705882F, 0.49803922F)
      )
      .cycleAll();
   public static RenderToxicSky skyRender = new RenderToxicSky(timeOfDayProvider);
   public WorldEventsHandler worldEventsHandler;
   public PoisonRain poisonRain = new PoisonRain(this, 0, 6000, 24000, 0.045F);

   public DimensionToxicomania() {
      this.worldEventsHandler = new WorldEventsHandler(this, this.poisonRain);
   }

   public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {
      timeOfDayProvider.setLightmapColors(6, this.getWorldTime(), partialTicks, sunBrightness, skyLight, blockLight, colors);
   }

   public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
      return null;
   }

   public IRenderHandler getSkyRenderer() {
      return skyRender;
   }

   public IRenderHandler getWeatherRenderer() {
      return this.worldEventsHandler;
   }

   public void updateWeather() {
      this.worldEventsHandler.onUpdate();
   }

   @Override
   public WorldEventsHandler getWorldEventsHandler() {
      return this.worldEventsHandler;
   }

   @Override
   public TimeOfDayProvider getTimeOfDayProvider() {
      return timeOfDayProvider;
   }

   public DimensionType getDimensionType() {
      return DimensionsRegister.TOXICOMANIA;
   }

   public IChunkGenerator createChunkGenerator() {
      return new ToxicomaniaChunkGenerator(this.world, this.world.getSeed());
   }

   @Override
   public void init() {
      super.init();
      this.hasSkyLight = true;
      this.biomeProvider = new BiomeProviderToxic(this.world.getSeed());
   }

   public boolean canRespawnHere() {
      return true;
   }
}
