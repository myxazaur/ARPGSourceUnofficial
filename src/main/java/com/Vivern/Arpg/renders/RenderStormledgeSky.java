//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.dimensions.generationutils.AbstractWorldProvider;
import com.Vivern.Arpg.weather.TimeOfDayProvider;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RenderStormledgeSky extends IRenderHandler {
   public static ResourceLocation SUN_TEXTURES = new ResourceLocation("arpg:textures/sun_stormledge.png");
   public static ResourceLocation SPACE_TEXTURES = new ResourceLocation("arpg:textures/space.png");
   public static ResourceLocation RINGS_TEXTURES = new ResourceLocation("arpg:textures/stormledge_rings.png");
   public int starGLCallList = -1;
   public int spaceGLCallList = -1;
   public int glSkyList = -1;
   public int glSkyList2 = -1;
   public float[] colorsSunriseSunset = new float[4];
   public static int skyLightColor = 11458303;
   public static int skyDarkColor = 992103;
   public static int skyMidColor = 7241089;
   public Vec3d[] skycolors = new Vec3d[]{new Vec3d(0.058, 0.133, 0.4), new Vec3d(0.235, 0.5, 1.0), new Vec3d(0.235, 0.5, 1.0), new Vec3d(0.682, 0.839, 1.0)};
   public TimeOfDayProvider timeOfDayProvider;

   public RenderStormledgeSky(TimeOfDayProvider timeOfDayProvider) {
      this.timeOfDayProvider = timeOfDayProvider;
   }

   public void render(float partialTicks, WorldClient world, Minecraft mc) {
      AbstractWorldProvider.disableFarplane();
      if (this.starGLCallList < 0) {
         this.generateStars();
      }

      if (this.spaceGLCallList < 0) {
         this.generateSpace();
      }

      if (this.glSkyList < 0) {
         this.generateSky();
      }

      if (this.glSkyList2 < 0) {
         this.generateSky2();
      }

      this.skyRender(partialTicks, world, mc);
   }

   public void renderSkySphere(float partialTicks, WorldClient world, Minecraft mc) {
      float notNight = 1.0F - this.timeOfDayProvider.getPowerOf(3, world.getWorldTime());
      GlStateManager.pushMatrix();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      float sc = 100.0F;
      GlStateManager.scale(-sc * 3.0F, -sc, -sc * 3.0F);
      GlStateManager.disableFog();
      GlStateManager.disableDepth();
      Vec3d[] fskycolors = this.timeOfDayProvider.getColorsVec3d(0, 2, world.getWorldTime());
      RenderFrozenSky.sphere.renderWithGradient2(false, fskycolors);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      float sc2 = 50.0F;
      GlStateManager.scale(-sc2 * (1.0F + 4.0F * notNight), -sc2 * 1.0F, -sc2 * (1.0F + 4.0F * notNight));
      GlStateManager.rotate(world.getCelestialAngle(partialTicks) * 360.0F, 0.0F, 0.0F, 1.0F);
      Vec3d[] zenithcolors = this.timeOfDayProvider.getColorsVec3d(2, 3, world.getWorldTime());
      RenderFrozenSky.sphere.renderWithGradient2(false, zenithcolors);
      GlStateManager.disableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.enableDepth();
      GlStateManager.popMatrix();
   }

   public void skyRender(float partialTicks, WorldClient world, Minecraft mc) {
      GlStateManager.disableTexture2D();
      Vec3d vec3d = this.getSkyColor(world, mc.getRenderViewEntity(), partialTicks, skyMidColor);
      float f = (float)vec3d.x;
      float f1 = (float)vec3d.y;
      float f2 = (float)vec3d.z;
      this.renderSkySphere(partialTicks, world, mc);
      GlStateManager.disableFog();
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      GlStateManager.disableTexture2D();
      GlStateManager.depthMask(false);
      GlStateManager.enableFog();
      GlStateManager.color(f, f1, f2);
      GlStateManager.disableFog();
      GlStateManager.disableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.enableTexture2D();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.pushMatrix();
      float f16 = 1.0F - world.getRainStrength(partialTicks);
      GlStateManager.color(1.0F, 1.0F, 1.0F, f16);
      GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
      float f17 = 22.0F;
      Minecraft.getMinecraft().getTextureManager().bindTexture(SUN_TEXTURES);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-f17, 100.0, -f17).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(f17, 100.0, -f17).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(f17, 100.0, f17).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(-f17, 100.0, f17).tex(0.0, 1.0).endVertex();
      tessellator.draw();
      f17 = 6.0F;
      float f15 = world.getStarBrightness(partialTicks) * f16;
      if (f15 > 0.0F) {
         GlStateManager.color(f15, f15, f15, f15);
         GlStateManager.callList(this.starGLCallList);
         float sbr = Math.min(f15 * 1.4F, 1.0F);
         GlStateManager.color(sbr, sbr, sbr, sbr);
         Minecraft.getMinecraft().getTextureManager().bindTexture(SPACE_TEXTURES);
         GlStateManager.callList(this.spaceGLCallList);
      }

      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(RINGS_TEXTURES);
      float sizeRings = 130.0F;
      float farRings = 20.0F;
      GlStateManager.rotate(100.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(30.0F, 0.0F, 0.0F, 1.0F);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-sizeRings, farRings, -sizeRings).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(sizeRings, farRings, -sizeRings).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(sizeRings, farRings, sizeRings).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(-sizeRings, farRings, sizeRings).tex(0.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableFog();
      GlStateManager.enableTexture2D();
      GlStateManager.depthMask(true);
   }

   public void renderStars(BufferBuilder bufferBuilderIn) {
      GlStateManager.disableTexture2D();
      Random random = new Random(10742L);
      bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION);

      for (int i = 0; i < 1000; i++) {
         double d0 = random.nextFloat() * 2.0F - 1.0F;
         double d1 = random.nextFloat() * 2.0F - 1.0F;
         double d2 = random.nextFloat() * 2.0F - 1.0F;
         double d3 = 0.1F + random.nextFloat() * 0.1F;
         double d4 = d0 * d0 + d1 * d1 + d2 * d2;
         if (d4 < 1.0 && d4 > 0.01) {
            d4 = 1.0 / Math.sqrt(d4);
            d0 *= d4;
            d1 *= d4;
            d2 *= d4;
            double d5 = d0 * 100.0;
            double d6 = d1 * 100.0;
            double d7 = d2 * 100.0;
            double d8 = Math.atan2(d0, d2);
            double d9 = Math.sin(d8);
            double d10 = Math.cos(d8);
            double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
            double d12 = Math.sin(d11);
            double d13 = Math.cos(d11);
            double d14 = random.nextDouble() * Math.PI * 2.0;
            double d15 = Math.sin(d14);
            double d16 = Math.cos(d14);

            for (int j = 0; j < 4; j++) {
               double d17 = 0.0;
               double d18 = ((j & 2) - 1) * d3;
               double d19 = ((j + 1 & 2) - 1) * d3;
               double d20 = 0.0;
               double d21 = d18 * d16 - d19 * d15;
               double d22 = d19 * d16 + d18 * d15;
               double d23 = d21 * d12 + 0.0 * d13;
               double d24 = 0.0 * d12 - d21 * d13;
               double d25 = d24 * d9 - d22 * d10;
               double d26 = d22 * d9 + d24 * d10;
               bufferBuilderIn.pos(d5 + d25, d6 + d23, d7 + d26).endVertex();
            }
         }
      }
   }

   public void renderSpace(BufferBuilder bufferBuilderIn) {
      GlStateManager.enableTexture2D();
      Random random = new Random(2L);
      bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION_TEX);
      float[] texcrdsSp1 = new float[]{0.25F, 0.5F, 0.75F, 0.25F, 0.75F};
      float[] texcrdsSp2 = new float[]{0.25F, 0.75F, 0.75F, 0.75F, 0.75F};

      for (int i = 0; i < 5; i++) {
         float x1 = texcrdsSp1[i];
         float y1 = texcrdsSp2[i];
         float[] texcrds5 = new float[]{x1, x1, x1 + 0.25F, x1 + 0.25F};
         float[] texcrds6 = new float[]{y1, y1 + 0.25F, y1 + 0.25F, y1};
         double d0 = random.nextFloat() * 2.0F - 1.0F;
         double d1 = random.nextFloat() * 2.0F - 1.0F;
         double d2 = random.nextFloat() * 2.0F - 1.0F;
         double d3 = 1.15F + random.nextFloat() * 1.7F;
         double d4 = d0 * d0 + d1 * d1 + d2 * d2;
         if (d4 < 1.0 && d4 > 0.01) {
            d4 = 1.0 / Math.sqrt(d4);
            d0 *= d4;
            d1 *= d4;
            d2 *= d4;
            double d5 = d0 * 100.0;
            double d6 = d1 * 100.0;
            double d7 = d2 * 100.0;
            double d8 = Math.atan2(d0, d2);
            double d9 = Math.sin(d8);
            double d10 = Math.cos(d8);
            double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
            double d12 = Math.sin(d11);
            double d13 = Math.cos(d11);
            double d14 = random.nextDouble() * Math.PI * 2.0;
            double d15 = Math.sin(d14);
            double d16 = Math.cos(d14);

            for (int j = 0; j < 4; j++) {
               double d17 = 0.0;
               double d18 = ((j & 2) - 1) * d3;
               double d19 = ((j + 1 & 2) - 1) * d3;
               double d20 = 0.0;
               double d21 = d18 * d16 - d19 * d15;
               double d22 = d19 * d16 + d18 * d15;
               double d23 = d21 * d12 + 0.0 * d13;
               double d24 = 0.0 * d12 - d21 * d13;
               double d25 = d24 * d9 - d22 * d10;
               double d26 = d22 * d9 + d24 * d10;
               bufferBuilderIn.pos(d5 + d25, d6 + d23, d7 + d26).tex(texcrds5[j], texcrds6[j]).endVertex();
            }
         }
      }
   }

   public void generateStars() {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      if (this.starGLCallList >= 0) {
         GLAllocation.deleteDisplayLists(this.starGLCallList);
         this.starGLCallList = -1;
      }

      this.starGLCallList = GLAllocation.generateDisplayLists(1);
      GlStateManager.pushMatrix();
      GlStateManager.glNewList(this.starGLCallList, 4864);
      this.renderStars(bufferbuilder);
      tessellator.draw();
      GlStateManager.glEndList();
      GlStateManager.popMatrix();
   }

   public void generateSpace() {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      if (this.spaceGLCallList >= 0) {
         GLAllocation.deleteDisplayLists(this.spaceGLCallList);
         this.spaceGLCallList = -1;
      }

      this.spaceGLCallList = GLAllocation.generateDisplayLists(1);
      GlStateManager.pushMatrix();
      GlStateManager.glNewList(this.spaceGLCallList, 4864);
      this.renderSpace(bufferbuilder);
      tessellator.draw();
      GlStateManager.glEndList();
      GlStateManager.popMatrix();
   }

   @Nullable
   @SideOnly(Side.CLIENT)
   public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
      float f = 0.4F;
      float f1 = MathHelper.cos(celestialAngle * (float) (Math.PI * 2)) - 0.0F;
      float f2 = -0.0F;
      if (f1 >= -0.4F && f1 <= 0.4F) {
         float f3 = (f1 - -0.0F) / 0.4F * 0.5F + 0.5F;
         float f4 = 1.0F - (1.0F - MathHelper.sin(f3 * (float) Math.PI)) * 0.99F;
         f4 *= f4;
         this.colorsSunriseSunset[0] = f3 * 0.3F + 0.7F;
         this.colorsSunriseSunset[1] = f3 * f3 * 0.7F + 0.2F;
         this.colorsSunriseSunset[2] = f3 * f3 * 0.0F + 0.2F;
         this.colorsSunriseSunset[3] = f4;
         return this.colorsSunriseSunset;
      } else {
         return null;
      }
   }

   public void generateSky() {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      if (this.glSkyList >= 0) {
         GLAllocation.deleteDisplayLists(this.glSkyList);
         this.glSkyList = -1;
      }

      this.glSkyList = GLAllocation.generateDisplayLists(1);
      GlStateManager.glNewList(this.glSkyList, 4864);
      this.renderSkyList(bufferbuilder, 56.0F, false);
      tessellator.draw();
      GlStateManager.glEndList();
   }

   public void generateSky2() {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      if (this.glSkyList2 >= 0) {
         GLAllocation.deleteDisplayLists(this.glSkyList2);
         this.glSkyList2 = -1;
      }

      this.glSkyList2 = GLAllocation.generateDisplayLists(1);
      GlStateManager.glNewList(this.glSkyList2, 4864);
      this.renderSkyList(bufferbuilder, 16.0F, false);
      tessellator.draw();
      GlStateManager.glEndList();
   }

   public void renderSkyList(BufferBuilder bufferBuilderIn, float posY, boolean reverseX) {
      int i = 64;
      int j = 6;
      bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION);

      for (int k = -384; k <= 384; k += 64) {
         for (int l = -384; l <= 384; l += 64) {
            float f = k;
            float f1 = k + 64;
            if (reverseX) {
               f1 = k;
               f = k + 64;
            }

            bufferBuilderIn.pos(f, posY, l).endVertex();
            bufferBuilderIn.pos(f1, posY, l).endVertex();
            bufferBuilderIn.pos(f1, posY, l + 64).endVertex();
            bufferBuilderIn.pos(f, posY, l + 64).endVertex();
         }
      }
   }

   public Vec3d getSkyColor(World world, Entity cameraEntity, float partialTicks, int color) {
      float f = world.getCelestialAngle(partialTicks);
      float f1 = MathHelper.cos(f * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
      float f3 = (color >> 16 & 0xFF) / 255.0F;
      float f4 = (color >> 8 & 0xFF) / 255.0F;
      float f5 = (color & 0xFF) / 255.0F;
      f3 *= f1;
      f4 *= f1;
      f5 *= f1;
      float f6 = world.getRainStrength(partialTicks);
      if (f6 > 0.0F) {
         float f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
         float f8 = 1.0F - f6 * 0.75F;
         f3 = f3 * f8 + f7 * (1.0F - f8);
         f4 = f4 * f8 + f7 * (1.0F - f8);
         f5 = f5 * f8 + f7 * (1.0F - f8);
      }

      float f10 = world.getThunderStrength(partialTicks);
      if (f10 > 0.0F) {
         float f11 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
         float f9 = 1.0F - f10 * 0.75F;
         f3 = f3 * f9 + f11 * (1.0F - f9);
         f4 = f4 * f9 + f11 * (1.0F - f9);
         f5 = f5 * f9 + f11 * (1.0F - f9);
      }

      if (world.getLastLightningBolt() > 0) {
         float f12 = world.getLastLightningBolt() - partialTicks;
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
}
