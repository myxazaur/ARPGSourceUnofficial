package com.Vivern.Arpg.dimensions.stormledge;

import com.Vivern.Arpg.dimensions.generationutils.AbstractWorldProvider;
import com.Vivern.Arpg.main.DimensionsRegister;
import com.Vivern.Arpg.renders.RenderStormledgeSky;
import com.Vivern.Arpg.weather.Rainstorm;
import com.Vivern.Arpg.weather.Storm;
import com.Vivern.Arpg.weather.TimeOfDayProvider;
import com.Vivern.Arpg.weather.WorldEventsHandler;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector4f;

public class DimensionStormledge extends AbstractWorldProvider {
   public static TimeOfDayProvider timeOfDayProvider = new TimeOfDayProvider()
      .addO(
         22900,
         23600,
         new Vector4f(0.21568628F, 0.105882354F, 0.47058824F, 0.99607843F),
         new Vector4f(0.07450981F, 0.007843138F, 0.12156863F, 1.0F),
         new Vector4f(1.0F, 1.0F, 1.0F, 1.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.03137255F, 0.0F, 0.16470589F, 0.7882353F),
         new Vector4f(0.8235294F, 0.6039216F, 1.0F, 0.8F),
         new Vector4f(0.8235294F, 0.6039216F, 1.0F, 0.8F)
      )
      .addO(
         1500,
         2300,
         new Vector4f(0.0F, 0.023529412F, 1.0F, 1.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.99607843F),
         new Vector4f(0.2784314F, 0.2784314F, 0.2784314F, 1.0F),
         new Vector4f(0.76862746F, 0.9254902F, 0.95686275F, 0.9882353F),
         new Vector4f(0.0F, 0.0F, 0.0F, 1.0F),
         new Vector4f(0.40784314F, 0.25882354F, 0.0F, 0.28235295F),
         new Vector4f(0.99607843F, 1.0F, 0.96862745F, 0.7019608F),
         new Vector4f(0.43137255F, 0.47058824F, 0.9843137F, 0.7019608F)
      )
      .addO(
         5000,
         5300,
         new Vector4f(0.0F, 0.023529412F, 1.0F, 1.0F),
         new Vector4f(0.0F, 0.007843138F, 0.105882354F, 0.99607843F),
         new Vector4f(1.0F, 1.0F, 1.0F, 1.0F),
         new Vector4f(1.0F, 1.0F, 1.0F, 0.9882353F),
         new Vector4f(0.0F, 0.0F, 0.0F, 1.0F),
         new Vector4f(0.40784314F, 0.25882354F, 0.0F, 0.28235295F),
         new Vector4f(0.92156863F, 0.98039216F, 1.0F, 0.7019608F),
         new Vector4f(1.0F, 1.0F, 1.0F, 0.7019608F)
      )
      .addO(
         6400,
         7100,
         new Vector4f(0.03137255F, 0.03137255F, 0.24705882F, 1.0F),
         new Vector4f(0.03529412F, 0.007843138F, 0.15686275F, 1.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.9882353F),
         new Vector4f(0.50980395F, 0.9607843F, 1.0F, 1.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 1.0F),
         new Vector4f(0.3882353F, 0.90588236F, 1.0F, 0.5137255F),
         new Vector4f(0.0F, 0.043137256F, 0.38039216F, 0.36078432F),
         new Vector4f(0.14509805F, 0.3529412F, 0.7058824F, 0.36078432F)
      )
      .addO(
         11500,
         12100,
         new Vector4f(0.3137255F, 0.92941177F, 1.0F, 0.827451F),
         new Vector4f(1.0F, 1.0F, 1.0F, 0.91764706F),
         new Vector4f(0.5921569F, 0.0F, 0.18039216F, 1.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.8666667F),
         new Vector4f(0.0F, 0.0F, 0.0F, 1.0F),
         new Vector4f(0.36078432F, 0.078431375F, 0.2509804F, 0.42745098F),
         new Vector4f(1.0F, 0.92156863F, 0.80784315F, 0.7607843F),
         new Vector4f(1.0F, 0.81960785F, 0.49019608F, 0.7607843F)
      )
      .addO(
         13100,
         14000,
         new Vector4f(0.050980393F, 0.0F, 0.22352941F, 1.0F),
         new Vector4f(0.4392157F, 0.101960786F, 0.6117647F, 1.0F),
         new Vector4f(0.6392157F, 0.09019608F, 0.5803922F, 0.9882353F),
         new Vector4f(0.0F, 0.0F, 0.0F, 1.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 1.0F),
         new Vector4f(0.32941177F, 0.23137255F, 0.47843137F, 0.4117647F),
         new Vector4f(0.06666667F, 0.07450981F, 0.26666668F, 0.37254903F),
         new Vector4f(0.23921569F, 0.15686275F, 0.54509807F, 0.37254903F)
      )
      .addO(
         17500,
         18400,
         new Vector4f(0.09803922F, 0.07058824F, 0.2F, 1.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 1.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.9882353F),
         new Vector4f(0.09411765F, 0.08627451F, 0.20392157F, 1.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 1.0F),
         new Vector4f(0.02745098F, 0.02745098F, 0.14901961F, 0.39215687F),
         new Vector4f(0.14509805F, 0.101960786F, 0.2784314F, 0.24313726F),
         new Vector4f(0.14509805F, 0.101960786F, 0.2784314F, 0.24313726F)
      )
      .cycleAll();
   public static RenderStormledgeSky skyRender = new RenderStormledgeSky(timeOfDayProvider);
   public WorldEventsHandler worldEventsHandler;
   public Storm storm = new Storm(this, 0, 6000, 20000, 0.045F);
   public Rainstorm rainstorm = new Rainstorm(this, 1, 4000, 10000, 0.03F);

   public DimensionStormledge() {
      this.worldEventsHandler = new WorldEventsHandler(this, this.storm, this.rainstorm);
   }

   public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {
      timeOfDayProvider.setLightmapColors(7, this.getWorldTime(), partialTicks, sunBrightness, skyLight, blockLight, colors);
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

   public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
      float f = MathHelper.cos(p_76562_1_ * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      f = MathHelper.clamp(f, 0.0F, 1.0F);
      float f1 = 0.58F;
      float f2 = 0.85F;
      float f3 = 1.0F;
      f1 *= f * 0.94F + 0.06F;
      f2 *= f * 0.94F + 0.06F;
      f3 *= f * 0.91F + 0.09F;
      return new Vec3d(f1, f2, f3);
   }

   @SideOnly(Side.CLIENT)
   public Vec3d getSkyColorVec(Entity entityIn, float partialTicks) {
      float f = this.world.getCelestialAngle(partialTicks);
      float f1 = MathHelper.cos(f * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
      int l = 1085160;
      float f3 = (l >> 16 & 0xFF) / 255.0F;
      float f4 = (l >> 8 & 0xFF) / 255.0F;
      float f5 = (l & 0xFF) / 255.0F;
      f3 *= f1;
      f4 *= f1;
      f5 *= f1;
      float f6 = this.world.getRainStrength(partialTicks);
      if (f6 > 0.0F) {
         float f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
         float f8 = 1.0F - f6 * 0.75F;
         f3 = f3 * f8 + f7 * (1.0F - f8);
         f4 = f4 * f8 + f7 * (1.0F - f8);
         f5 = f5 * f8 + f7 * (1.0F - f8);
      }

      float f10 = this.world.getThunderStrength(partialTicks);
      if (f10 > 0.0F) {
         float f11 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
         float f9 = 1.0F - f10 * 0.75F;
         f3 = f3 * f9 + f11 * (1.0F - f9);
         f4 = f4 * f9 + f11 * (1.0F - f9);
         f5 = f5 * f9 + f11 * (1.0F - f9);
      }

      if (this.world.getLastLightningBolt() > 0) {
         float f12 = this.world.getLastLightningBolt() - partialTicks;
         if (f12 > 1.0F) {
            f12 = 1.0F;
         }

         f12 *= 0.45F;
         f3 = f3 * (1.0F - f12) + 0.8F * f12;
         f4 = f4 * (1.0F - f12) + 0.8F * f12;
         f5 = f5 * (1.0F - f12) + 1.0F * f12;
      }

      return new Vec3d(f3, f4, f5);
   }

   public DimensionType getDimensionType() {
      return DimensionsRegister.STORMLEDGE;
   }

   public IChunkGenerator createChunkGenerator() {
      return new StormledgeChunkGenerator(this.world, this.world.getSeed());
   }

   @Override
   public void init() {
      super.init();
      this.hasSkyLight = true;
      this.biomeProvider = new BiomeProviderStormledge(this.world);
   }

   public boolean canRespawnHere() {
      return true;
   }
}
