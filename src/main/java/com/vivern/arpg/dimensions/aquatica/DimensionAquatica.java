package com.vivern.arpg.dimensions.aquatica;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.main.DimensionsRegister;
import com.vivern.arpg.renders.RenderAquaticaSky;
import com.vivern.arpg.weather.Rainfall;
import com.vivern.arpg.weather.TimeOfDayProvider;
import com.vivern.arpg.weather.WorldEventsHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector4f;

public class DimensionAquatica extends AbstractWorldProvider {
   public static TimeOfDayProvider timeOfDayProvider = new TimeOfDayProvider()
      .addO(
         21700,
         23000,
         new Vector4f(0.09411765F, 0.7176471F, 0.69411767F, 0.9882353F),
         new Vector4f(0.3372549F, 0.36862746F, 0.6039216F, 0.9882353F),
         new Vector4f(0.023529412F, 0.24313726F, 0.8666667F, 0.99215686F),
         new Vector4f(1.0F, 0.3882353F, 0.1254902F, 0.98039216F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.9764706F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.2509804F, 0.2901961F, 0.49411765F, 0.49803922F),
         new Vector4f(1.0F, 0.68235296F, 0.50980395F, 0.8F),
         new Vector4f(1.0F, 0.45490196F, 0.34901962F, 0.8F)
      )
      .addO(
         23500,
         25000,
         new Vector4f(0.0F, 0.2784314F, 0.8039216F, 0.0F),
         new Vector4f(0.42745098F, 0.9647059F, 1.0F, 0.0F),
         new Vector4f(0.0F, 0.9882353F, 1.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.21568628F, 0.61960787F, 0.90588236F, 0.3647059F),
         new Vector4f(0.972549F, 0.99607843F, 1.0F, 0.8980392F),
         new Vector4f(0.972549F, 0.99607843F, 1.0F, 0.8980392F)
      )
      .addO(
         6000,
         7500,
         new Vector4f(0.12156863F, 0.72156864F, 0.74509805F, 0.9882353F),
         new Vector4f(0.6313726F, 0.61960787F, 0.38431373F, 0.99215686F),
         new Vector4f(0.9882353F, 0.89411765F, 0.3647059F, 0.99607843F),
         new Vector4f(0.039215688F, 0.105882354F, 0.019607844F, 0.98039216F),
         new Vector4f(0.4627451F, 0.3137255F, 0.1882353F, 0.99215686F),
         new Vector4f(0.0F, 0.40392157F, 0.4509804F, 0.80784315F),
         new Vector4f(0.3254902F, 0.70980394F, 0.6039216F, 0.28235295F),
         new Vector4f(1.0F, 0.6392157F, 0.7176471F, 0.5137255F),
         new Vector4f(0.28627452F, 0.8784314F, 0.73333335F, 0.5137255F)
      )
      .addO(
         11500,
         12500,
         new Vector4f(0.101960786F, 0.007843138F, 0.35686275F, 0.0F),
         new Vector4f(0.9490196F, 0.41960785F, 0.5372549F, 0.0F),
         new Vector4f(0.6745098F, 0.19215687F, 0.50980395F, 0.0F),
         new Vector4f(0.9098039F, 0.3254902F, 0.4745098F, 0.9764706F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.23137255F, 0.03529412F, 0.30980393F, 0.1254902F),
         new Vector4f(0.8666667F, 0.015686275F, 0.3372549F, 0.6627451F),
         new Vector4f(0.8666667F, 0.015686275F, 0.3372549F, 0.6627451F)
      )
      .addO(
         12500,
         13500,
         new Vector4f(0.0F, 0.007843138F, 0.05882353F, 0.0F),
         new Vector4f(0.22745098F, 0.16862746F, 0.26666668F, 0.0F),
         new Vector4f(0.92156863F, 0.6117647F, 0.34901962F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.03137255F, 0.0627451F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.14509805F, 0.16078432F, 0.2784314F, 0.32941177F),
         new Vector4f(0.08627451F, 0.050980393F, 0.16470589F, 0.2509804F),
         new Vector4f(0.08627451F, 0.050980393F, 0.16470589F, 0.2509804F)
      )
      .cycleAll();
   public static RenderAquaticaSky skyRender = new RenderAquaticaSky(timeOfDayProvider);
   public WorldEventsHandler worldEventsHandler;
   public Rainfall rainfall = new Rainfall(this, 0, 2000, 8000, 0.05F);

   public DimensionAquatica() {
      this.worldEventsHandler = new WorldEventsHandler(this, this.rainfall);
   }

   public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {
      timeOfDayProvider.setLightmapColors(8, this.getWorldTime(), partialTicks, sunBrightness, skyLight, blockLight, colors);
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

   public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
      return this.getSkyColorVec(cameraEntity, partialTicks);
   }

   public static Vec3d getBlockFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      if (state.getMaterial() == Material.WATER) {
         float r = MathHelper.clamp((-entity.rotationPitch + 90.0F) / 180.0F, 0.0F, 1.0F);
         return new Vec3d(0.1 + 0.1 * r, 0.4 + 0.35 * r, 0.85 + 0.23 * r);
      } else {
         return originalColor;
      }
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
      return DimensionsRegister.AQUATICA;
   }

   @Override
   public boolean canDoRainSnowIce(Chunk chunk) {
      return false;
   }

   public IChunkGenerator createChunkGenerator() {
      return new AquaticaChunkGenerator(this.world, this.world.getSeed());
   }

   @Override
   public void init() {
      super.init();
      this.hasSkyLight = true;
      this.biomeProvider = new BiomeProviderAquatica(this.world.getWorldInfo());
   }

   public boolean canRespawnHere() {
      return true;
   }
}
