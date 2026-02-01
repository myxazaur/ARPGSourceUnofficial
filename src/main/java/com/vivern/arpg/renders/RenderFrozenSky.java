package com.vivern.arpg.renders;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.elements.models.ModelSphere;
import com.vivern.arpg.weather.TimeOfDayProvider;
import java.util.Random;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;

public class RenderFrozenSky extends IRenderHandler {
   public static ResourceLocation mountain = new ResourceLocation("arpg:textures/sky_frozen_mountains.png");
   public static ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("arpg:textures/moon_phases_frozen.png");
   public static ResourceLocation SUN_TEXTURES = new ResourceLocation("arpg:textures/sun_frozen.png");
   public static ResourceLocation SPACE_TEXTURES = new ResourceLocation("arpg:textures/space.png");
   public int starGLCallList = -1;
   public int spaceGLCallList = -1;
   public TimeOfDayProvider timeOfDayProvider;
   public static ModelSphere sphere = new ModelSphere(1.0F, 6);

   public RenderFrozenSky(TimeOfDayProvider timeOfDayProvider) {
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
      sphere.renderWithGradient2(false, fskycolors);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      float sc2 = 50.0F;
      GlStateManager.scale(-sc2 * (1.0F + 4.0F * notNight), -sc2 * 1.0F, -sc2 * (1.0F + 4.0F * notNight));
      GlStateManager.rotate(world.getCelestialAngle(partialTicks) * 360.0F, 0.0F, 0.0F, 1.0F);
      Vec3d[] zenithcolors = this.timeOfDayProvider.getColorsVec3d(2, 3, world.getWorldTime());
      sphere.renderWithGradient2(false, zenithcolors);
      GlStateManager.disableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.enableDepth();
      GlStateManager.popMatrix();
   }

   public void skyRender(float partialTicks, WorldClient world, Minecraft mc) {
      GlStateManager.disableTexture2D();
      Vec3d vec3d = world.getSkyColor(mc.getRenderViewEntity(), partialTicks);
      float f = (float)vec3d.x;
      float f1 = (float)vec3d.y;
      float f2 = (float)vec3d.z;
      float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
      float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
      float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
      this.renderSkySphere(partialTicks, world, mc);
      GlStateManager.color(f3, f4, f5);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      GlStateManager.depthMask(false);
      GlStateManager.enableFog();
      GlStateManager.color(f3, f4, f5);
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
      float f17 = 15.0F;
      Minecraft.getMinecraft().getTextureManager().bindTexture(SUN_TEXTURES);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-f17, 100.0, -f17).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(f17, 100.0, -f17).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(f17, 100.0, f17).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(-f17, 100.0, f17).tex(0.0, 1.0).endVertex();
      tessellator.draw();
      f17 = 20.0F;
      Minecraft.getMinecraft().getTextureManager().bindTexture(MOON_PHASES_TEXTURES);
      int k1 = world.getMoonPhase();
      int i2 = k1 % 4;
      int k2 = k1 / 4 % 2;
      float f22 = (i2 + 0) / 4.0F;
      float f23 = (k2 + 0) / 2.0F;
      float f24 = (i2 + 1) / 4.0F;
      float f14 = (k2 + 1) / 2.0F;
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-f17, -100.0, f17).tex(f24, f14).endVertex();
      bufferbuilder.pos(f17, -100.0, f17).tex(f22, f14).endVertex();
      bufferbuilder.pos(f17, -100.0, -f17).tex(f22, f23).endVertex();
      bufferbuilder.pos(-f17, -100.0, -f17).tex(f24, f23).endVertex();
      tessellator.draw();
      GlStateManager.disableTexture2D();
      float f15 = world.getStarBrightness(partialTicks) * f16;
      if (f15 > 0.0F) {
         GlStateManager.color(f15, f15, f15, f15);
         GlStateManager.enableTexture2D();
         GlStateManager.callList(this.starGLCallList);
         float sbr = Math.min(f15 * 1.4F, 1.0F);
         GlStateManager.color(sbr, sbr, sbr, sbr);
         Minecraft.getMinecraft().getTextureManager().bindTexture(SPACE_TEXTURES);
         GlStateManager.callList(this.spaceGLCallList);
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableFog();
      GlStateManager.popMatrix();
      GlStateManager.disableTexture2D();
      GlStateManager.color(0.0F, 0.0F, 0.0F);
      double d3 = mc.player != null ? mc.player.getPositionEyes(partialTicks).y - world.getHorizon() : 0.0;
      if (d3 < 0.0) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 12.0F, 0.0F);
         GlStateManager.popMatrix();
         float f18 = 1.0F;
         float f19 = -((float)(d3 + 65.0));
         float f20 = -1.0F;
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         bufferbuilder.pos(-1.0, f19, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, f19, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, f19, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, f19, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, f19, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, f19, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, f19, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, f19, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
         tessellator.draw();
      }

      if (world.provider.isSkyColored()) {
         GlStateManager.color(f3 * 0.2F + 0.04F, f4 * 0.2F + 0.04F, f5 * 0.6F + 0.1F);
      } else {
         GlStateManager.color(f3, f4, f5);
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, -((float)(d3 - 16.0)), 0.0F);
      GlStateManager.popMatrix();
      GlStateManager.enableTexture2D();
      GlStateManager.depthMask(true);
   }

   public void renderStars(BufferBuilder bufferBuilderIn) {
      GlStateManager.disableTexture2D();
      Random random = new Random(10863L);
      bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION);

      for (int i = 0; i < 2000; i++) {
         double d0 = random.nextFloat() * 2.0F - 1.0F;
         double d1 = random.nextFloat() * 2.0F - 1.0F;
         double d2 = random.nextFloat() * 2.0F - 1.0F;
         double d3 = 0.05F + random.nextFloat() * 0.1F;
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
      float[] texcrds = new float[]{0.0F, 0.0F, 0.25F, 0.25F};
      float[] texcrds2 = new float[]{0.25F, 0.5F, 0.5F, 0.25F};
      Random random = new Random(10845L);
      bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION_TEX);

      for (int i = 0; i < 50; i++) {
         double d0 = random.nextFloat() * 2.0F - 1.0F;
         double d1 = random.nextFloat() * 2.0F - 1.0F;
         double d2 = random.nextFloat() * 2.0F - 1.0F;
         double d3 = 1.15F + random.nextFloat() * 1.0F;
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
               bufferBuilderIn.pos(d5 + d25, d6 + d23, d7 + d26).tex(texcrds[j], texcrds2[j]).endVertex();
            }
         }
      }

      float[] texcrds3 = new float[]{0.5F, 0.5F, 0.75F, 0.75F};
      float[] texcrds4 = new float[]{0.0F, 0.25F, 0.25F, 0.0F};

      for (int ix = 0; ix < 5; ix++) {
         double d0 = random.nextFloat() * 2.0F - 1.0F;
         double d1 = random.nextFloat() * 2.0F - 1.0F;
         double d2 = random.nextFloat() * 2.0F - 1.0F;
         double d3 = 1.15F + random.nextFloat() * 1.5F;
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
               bufferBuilderIn.pos(d5 + d25, d6 + d23, d7 + d26).tex(texcrds3[j], texcrds4[j]).endVertex();
            }
         }
      }

      for (int ixx = 0; ixx < 10; ixx++) {
         float x1 = random.nextInt(4) * 0.25F;
         float y1 = random.nextInt(3) * 0.25F;
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
}
