//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.dimensions.generationutils.AbstractWorldProvider;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.weather.TimeOfDayProvider;
import com.Vivern.Arpg.weather.Weather;
import java.lang.reflect.Field;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RenderAquaticaSky extends IRenderHandler {
   public static ResourceLocation mountain = new ResourceLocation("arpg:textures/sky_frozen_mountains.png");
   public static ResourceLocation MOON_BLUE_TEXTURES = new ResourceLocation("arpg:textures/moon_aquatica_1.png");
   public static ResourceLocation MOON_ROCK_TEXTURES = new ResourceLocation("arpg:textures/moon_aquatica_2.png");
   public static ResourceLocation MOON_CIRCLE_TEXTURES = new ResourceLocation("arpg:textures/moon_aquatica_3.png");
   public static ResourceLocation SUN_TEXTURES = new ResourceLocation("arpg:textures/sun_aqua.png");
   public static ResourceLocation SPACE_TEXTURES = new ResourceLocation("arpg:textures/space.png");
   public static ResourceLocation CLOUDS_TEXTURES = new ResourceLocation("arpg:textures/clouds7.png");
   public static ResourceLocation SHADOW = new ResourceLocation("arpg:textures/shadow_round64x_allwh.png");
   public int starGLCallList = -1;
   public int spaceGLCallList = -1;
   public int glSkyList = -1;
   private int glSkyList2 = -1;
   public float nounderwater = 1.0F;
   public TimeOfDayProvider timeOfDayProvider;

   public RenderAquaticaSky(TimeOfDayProvider timeOfDayProvider) {
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

      if (this.nounderwater > 0.0F) {
         this.skyRender(partialTicks, world, mc);
      }

      if (mc.getRenderViewEntity() != null) {
         if (mc.getRenderViewEntity().posY <= 193.0 && mc.getRenderViewEntity().isInWater()) {
            if (this.nounderwater > 0.0F) {
               this.nounderwater -= 0.02F;
            }
         } else if (this.nounderwater < 1.0F) {
            this.nounderwater += 0.02F;
         }
      }

      float br = Weather.getBrightnessFromTime(world.getWorldTime());
      float c0 = 70.0F;
      float c = 25.0F;
      float c2 = c0 / 2.4141834F;
   }

   public void renderSkySphere(float partialTicks, WorldClient world, Minecraft mc) {
      float notNight = 1.0F - this.timeOfDayProvider.getPowerOf(3, world.getWorldTime());
      GlStateManager.pushMatrix();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      float sc = 100.0F;
      GlStateManager.scale(-sc * 3.0F, -sc, -sc * 3.0F);
      GlStateManager.disableFog();
      GlStateManager.disableDepth();
      Vec3d[] fskycolors = this.timeOfDayProvider.getColorsVec3d(0, 3, world.getWorldTime());
      RenderFrozenSky.sphere.renderWithGradient2(false, fskycolors);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      float sc2 = 50.0F;
      GlStateManager.scale(-sc2 * (1.0F + 4.0F * notNight), -sc2 * 1.0F, -sc2 * (1.0F + 4.0F * notNight));
      GlStateManager.rotate(world.getCelestialAngle(partialTicks) * 360.0F, 0.0F, 0.0F, 1.0F);
      Vec3d[] zenithcolors = this.timeOfDayProvider.getColorsVec3d(3, 3, world.getWorldTime());
      RenderFrozenSky.sphere.renderWithGradient2(false, zenithcolors);
      GlStateManager.disableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.enableDepth();
      GlStateManager.popMatrix();
   }

   public void skyRender(float partialTicks, WorldClient world, Minecraft mc) {
      float foggR = 0.0F;
      float foggG = 0.0F;
      float foggB = 0.0F;

      try {
         Field[] fields = EntityRenderer.class.getDeclaredFields();

         for (Field field : fields) {
            if ("fogColorRed".equals(field.getName())) {
               field.setAccessible(true);
               foggR = field.getFloat(mc.entityRenderer);
            }

            if ("fogColorGreen".equals(field.getName())) {
               field.setAccessible(true);
               foggG = field.getFloat(mc.entityRenderer);
            }

            if ("fogColorBlue".equals(field.getName())) {
               field.setAccessible(true);
               foggB = field.getFloat(mc.entityRenderer);
            }
         }
      } catch (IllegalArgumentException var52) {
         var52.printStackTrace();
      } catch (IllegalAccessException var53) {
         var53.printStackTrace();
      }

      Vec3d vec3d = world.getSkyColor(mc.getRenderViewEntity(), partialTicks);
      float f = (float)vec3d.x;
      float f1 = (float)vec3d.y;
      float f2 = (float)vec3d.z;
      GlStateManager.disableTexture2D();
      this.renderSkySphere(partialTicks, world, mc);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      GlStateManager.depthMask(false);
      GlStateManager.disableAlpha();
      GlStateManager.enableBlend();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.enableTexture2D();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.pushMatrix();
      float f16 = 1.0F - world.getRainStrength(partialTicks);
      float f15 = world.getStarBrightness(partialTicks) * f16;
      GlStateManager.color(1.0F, 1.0F, 1.0F, f16);
      GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
      float celAngle = world.getCelestialAngle(partialTicks) * 360.0F;
      float celAngle2 = this.calculateCelestialAngle(world.getWorldTime(), partialTicks, 19000L) * 360.0F;
      float celAngle3 = this.calculateCelestialAngle(world.getWorldTime(), partialTicks, 15500L) * -360.0F;
      GlStateManager.rotate(celAngle, 1.0F, 0.0F, 0.0F);
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.disableTexture2D();
      if (f15 > 0.0F) {
         GlStateManager.color(f15, f15, f15, f15);
         GlStateManager.enableTexture2D();
         GlStateManager.callList(this.starGLCallList);
         float sbr = Math.min(f15 * 1.4F * this.nounderwater, 1.0F);
         GlStateManager.color(sbr, sbr, sbr, sbr);
         Minecraft.getMinecraft().getTextureManager().bindTexture(SPACE_TEXTURES);
         GlStateManager.callList(this.spaceGLCallList);
      }

      GlStateManager.rotate(-celAngle, 1.0F, 0.0F, 0.0F);
      GlStateManager.enableTexture2D();
      GlStateManager.rotate(celAngle, 1.0F, 0.0F, 0.0F);
      float f17 = 15.0F;
      float sunset = 0.0F;
      if (celAngle > 75.0F && celAngle < 115.0F) {
         sunset = 1.0F - Math.abs(95.0F - celAngle) / 20.0F;
      }

      float sunBRIGH = 1.0F;
      if (celAngle > 75.0F && celAngle < 115.0F) {
         sunset = Math.abs(95.0F - celAngle) / 20.0F;
      }

      GlStateManager.color(1.0F, 1.0F - 0.5F * sunset, 1.0F - 0.25F * sunset, this.nounderwater);
      Minecraft.getMinecraft().getTextureManager().bindTexture(SUN_TEXTURES);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-f17, 100.0, -f17).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(f17, 100.0, -f17).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(f17, 100.0, f17).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(-f17, 100.0, f17).tex(0.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.rotate(-celAngle, 1.0F, 0.0F, 0.0F);
      Vec3d vec3df = world.getFogColor(partialTicks);
      Vec3d vec3dfRefl = new Vec3d(foggR, foggG, foggB);
      float fg = (float)vec3df.x;
      float fg1 = (float)vec3df.y;
      float fg2 = (float)vec3df.z;
      float fgR = (float)vec3dfRefl.x;
      float fgR1 = (float)vec3dfRefl.y;
      float fgR2 = (float)vec3dfRefl.z;
      float shadowAlpha = 1.0F;
      float celangMR = 1.0F - this.getPlanetShadowCol(world, partialTicks, 15500L, -1.0F, 1.4F) * 0.75F;
      float invMR = 1.0F - celangMR;
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.rotate(-40.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(celAngle3, 1.0F, 0.0F, 0.0F);
      f17 = 7.4F;
      Minecraft.getMinecraft().getTextureManager().bindTexture(SHADOW);
      GlStateManager.color(fgR * celangMR + f * invMR, fgR1 * celangMR + f1 * invMR, fgR2 * celangMR + f2 * invMR, shadowAlpha * this.nounderwater);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-f17, -100.0, f17).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(f17, -100.0, f17).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(f17, -100.0, -f17).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(-f17, -100.0, -f17).tex(0.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.disableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
      f17 = 8.0F;
      Minecraft.getMinecraft().getTextureManager().bindTexture(MOON_ROCK_TEXTURES);
      GlStateManager.color(1.0F, 1.0F, 1.0F, this.nounderwater);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-f17, -100.0, f17).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(f17, -100.0, f17).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(f17, -100.0, -f17).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(-f17, -100.0, -f17).tex(0.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.rotate(-celAngle3, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(40.0F, 0.0F, 0.0F, 1.0F);
      float celangMB = 1.0F - this.getPlanetShadowCol(world, partialTicks, 19000L, 1.0F, 1.9F) * 0.75F;
      float invMB = 1.0F - celangMB;
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.rotate(50.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(celAngle2, 1.0F, 0.0F, 0.0F);
      f17 = 11.0F;
      Minecraft.getMinecraft().getTextureManager().bindTexture(SHADOW);
      GlStateManager.color(fgR * celangMB + f * invMB, fgR1 * celangMB + f1 * invMB, fgR2 * celangMB + f2 * invMB, shadowAlpha * this.nounderwater);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-f17, -100.0, f17).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(f17, -100.0, f17).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(f17, -100.0, -f17).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(-f17, -100.0, -f17).tex(0.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.disableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
      f17 = 12.0F;
      Minecraft.getMinecraft().getTextureManager().bindTexture(MOON_BLUE_TEXTURES);
      GlStateManager.color(1.0F, 1.0F, 1.0F, this.nounderwater);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-f17, -100.0, f17).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(f17, -100.0, f17).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(f17, -100.0, -f17).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(-f17, -100.0, -f17).tex(0.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.rotate(-celAngle2, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
      float celangMC = 1.0F - this.getPlanetShadowCol(world, partialTicks, 24000L, 1.0F, 0.6F) * 0.75F;
      float invMC = 1.0F - celangMC;
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.rotate(celAngle, 1.0F, 0.0F, 0.0F);
      f17 = 13.7F;
      Minecraft.getMinecraft().getTextureManager().bindTexture(SHADOW);
      GlStateManager.color(fgR * celangMC + f * invMC, fgR1 * celangMC + f1 * invMC, fgR2 * celangMC + f2 * invMC, shadowAlpha * this.nounderwater);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-f17, -100.0, f17).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(f17, -100.0, f17).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(f17, -100.0, -f17).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(-f17, -100.0, -f17).tex(0.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.disableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
      f17 = 30.0F;
      Minecraft.getMinecraft().getTextureManager().bindTexture(MOON_CIRCLE_TEXTURES);
      GlStateManager.color(1.0F, 1.0F, 1.0F, this.nounderwater);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-f17, -100.0, f17).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(f17, -100.0, f17).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(f17, -100.0, -f17).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(-f17, -100.0, -f17).tex(0.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.rotate(-celAngle, 1.0F, 0.0F, 0.0F);
      Random random = new Random(world.getSeed() + (world.getWorldTime() + 5500L) / 24000L);
      float c0 = 70.0F;
      float c = 25.0F;
      float c2 = c0 / 2.4141834F;
      int maxi = 8 + random.nextInt(9);
      float cloudset = 1.0F;
      if (celAngle > 180.0F && celAngle < 200.0F) {
         cloudset = Math.abs(190.0F - celAngle) / 10.0F;
      }

      cloudset *= this.nounderwater;

      for (int i = 0; i < maxi; i++) {
         GlStateManager.pushMatrix();
         GlStateManager.rotate(random.nextInt(360) + AnimationTimer.tick / 250.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0, random.nextGaussian() * 7.0 + 20.0, c0);
         float scl = 0.1F + random.nextFloat() * 0.3F;
         GlStateManager.scale(scl, scl, scl);
         if (mc.player == null) {
            float var10000 = 1.0F;
         } else {
            MathHelper.clamp(1.0F - (float)(mc.player.posY - 140.0) / 265.0F, 0.0F, 1.0F);
         }

         float cloudBright = MathHelper.clamp(1.3F - this.getPlanetShadowCol(world, partialTicks, 24000L, 1.0F, 0.0F), 0.0F, 1.0F) * 0.7F * cloudset;
         Minecraft.getMinecraft().getTextureManager().bindTexture(CLOUDS_TEXTURES);
         GlStateManager.color(cloudBright, cloudBright, cloudBright, cloudBright);
         int texposX = random.nextInt(5);
         int texposY = random.nextInt(5);
         float px = 0.2F * texposX;
         float px2 = px + 0.2F;
         float py = 0.2F * texposY;
         float py2 = py + 0.2F;
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-c2, -c, 0.0).tex(px, py2).endVertex();
         bufferbuilder.pos(-c2, c, 0.0).tex(px, py).endVertex();
         bufferbuilder.pos(c2, c, 0.0).tex(px2, py).endVertex();
         bufferbuilder.pos(c2, -c, 0.0).tex(px2, py2).endVertex();
         tessellator.draw();
         GlStateManager.popMatrix();
      }

      GlStateManager.rotate(celAngle, 1.0F, 0.0F, 0.0F);
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
         c = 1.0F;
         c2 = -((float)(d3 + 65.0));
         float f20 = -1.0F;
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         bufferbuilder.pos(-1.0, c2, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, c2, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, c2, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, c2, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, c2, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, c2, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, c2, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, c2, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(-1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
         bufferbuilder.pos(1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
         tessellator.draw();
      }

      GlStateManager.color(f, f1, f2);
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
      Random random = new Random(11747L);
      bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION_TEX);

      for (int i = 0; i < 30; i++) {
         double d0 = random.nextFloat() * 2.0F - 1.0F;
         double d1 = random.nextFloat() * 2.0F - 1.0F;
         double d2 = random.nextFloat() * 2.0F - 1.0F;
         double d3 = 0.95F + random.nextFloat() * 1.0F;
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

      for (int ixx = 0; ixx < 5; ixx++) {
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
      this.renderSkyList(bufferbuilder, -16.0F, true);
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

   public float calculateCelestialAngle(long worldTime, float partialTicks, long dayLength) {
      int i = (int)(worldTime % dayLength);
      float f = (i + partialTicks) / (float)dayLength - 0.25F;
      if (f < 0.0F) {
         f++;
      }

      if (f > 1.0F) {
         f--;
      }

      float f1 = 1.0F - (float)((Math.cos(f * Math.PI) + 1.0) / 2.0);
      return f + (f1 - f) / 3.0F;
   }

   @SideOnly(Side.CLIENT)
   public float getPlanetShadowCol(World world, float partialTicks, long dayLength, float Anglemult, float off) {
      float f = this.calculateCelestialAngle(world.getWorldTime(), partialTicks, dayLength);
      float f1 = 1.0F - (MathHelper.cos(f * (float) (Math.PI * 2)) * 2.0F + 0.25F + off);
      f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
      return f1 * f1 * 0.96F;
   }
}
