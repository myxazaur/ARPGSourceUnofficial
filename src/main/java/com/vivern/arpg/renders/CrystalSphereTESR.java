package com.vivern.arpg.renders;

import com.vivern.arpg.elements.models.CrystalSphereModel;
import com.vivern.arpg.elements.models.EarthInSphereModel;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.tileentity.TileCrystalSphere;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class CrystalSphereTESR extends TileEntitySpecialRenderer<TileCrystalSphere> {
   public static CrystalSphereModel model = new CrystalSphereModel();
   public static EarthInSphereModel modelEarth = new EarthInSphereModel();
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/energy_vessel_model_tex.png");
   public static ResourceLocation tex1 = new ResourceLocation("arpg:textures/blaze_summon.png");
   public static ResourceLocation tex1anim = new ResourceLocation("arpg:textures/fire_sphere_animation.png");
   public static ResourceLocation tex2 = new ResourceLocation("arpg:textures/frost_portal.png");
   public static ResourceLocation snowfall = new ResourceLocation("arpg:textures/snowfall2.png");
   public static ResourceLocation poison_over = new ResourceLocation("arpg:textures/poison_sphere_over.png");
   public static ResourceLocation poison_fire = new ResourceLocation("arpg:textures/poison_fire.png");
   public static ResourceLocation rain = new ResourceLocation("arpg:textures/rain.png");
   public static ResourceLocation gloss2 = new ResourceLocation("arpg:textures/gloss_map2.png");
   public static ResourceLocation water_round = new ResourceLocation("arpg:textures/water_round.png");
   public static ResourceLocation pain_shards = new ResourceLocation("arpg:textures/pain_shards.png");
   public static ResourceLocation simple_magic_shoot = new ResourceLocation("arpg:textures/simple_magic_shoot.png");
   public static ResourceLocation pleasure_petals = new ResourceLocation("arpg:textures/pleasure_petals.png");
   public static ResourceLocation neon_effect = new ResourceLocation("arpg:textures/neon_effect.png");
   public static ResourceLocation death_skulls = new ResourceLocation("arpg:textures/death_skulls.png");
   public static ResourceLocation void_bubbles = new ResourceLocation("arpg:textures/void_bubbles.png");
   public static ResourceLocation void_sphere_over = new ResourceLocation("arpg:textures/void_sphere_over.png");
   public static ResourceLocation electric_in_sphere = new ResourceLocation("arpg:textures/electric_in_sphere.png");
   public static ResourceLocation electric_sphere_over = new ResourceLocation("arpg:textures/electric_sphere_over.png");
   public static ResourceLocation earth_in_sphere_model_tex = new ResourceLocation("arpg:textures/earth_in_sphere_model_tex.png");
   public static ResourceLocation earth_sphere_over = new ResourceLocation("arpg:textures/earth_sphere_over.png");
   public static ResourceLocation air_sphere_over = new ResourceLocation("arpg:textures/air_sphere_over.png");
   public static ResourceLocation live_vines = new ResourceLocation("arpg:textures/live_vines.png");
   public static ResourceLocation live_vines2 = new ResourceLocation("arpg:textures/live_vines2.png");
   public static ResourceLocation live_sphere_over = new ResourceLocation("arpg:textures/live_sphere_over.png");

   public void render(TileCrystalSphere te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      double eyeY;
      ShardType shard;
      float energystored;
      if (te == null) {
         shard = ShardType.byId((int)y);
         eyeY = -1.0;
         energystored = (float)x;
         x = 0.0;
         y = 0.0;
      } else {
         shard = te.energyType;
         eyeY = y - this.rendererDispatcher.entity.getEyeHeight();
         energystored = te.energyStored;
      }

      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.disableCull();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      float r = 1.0F;
      float g = 1.0F;
      float b = 1.0F;
      if (eyeY < 0.0) {
         this.bindTexture(tex);
         model.render(null, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      if (shard != null && energystored > 0.0F) {
         shard.renderInSphere.renderIn(shard, te, x, y, z, partialTicks, destroyStage, alpha);
         float coof = 0.85F;
         float uncoof = 1.0F - coof;
         r = uncoof + shard.colorR * coof;
         g = uncoof + shard.colorG * coof;
         b = uncoof + shard.colorB * coof;
      }

      if (destroyStage >= 0) {
         this.bindTexture(DESTROY_STAGES[destroyStage]);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scale(4.0F, 4.0F, 1.0F);
         GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.matrixMode(5888);
      } else {
         this.bindTexture(tex);
      }

      if (eyeY >= 0.0) {
         model.render(null, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      model.render(null, 0.0F, r, g, b, 0.0F, 0.0625F);
      if (shard != null && energystored > 0.0F) {
         shard.renderInSphere.renderOut(shard, te, x, y, z, partialTicks, destroyStage, alpha);
      }

      GlStateManager.enableCull();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (destroyStage >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }
   }

   public static class RenderShardsEffect {
      public void renderIn(ShardType shard, @Nullable TileCrystalSphere te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
         int tick = AnimationTimer.tick % 16000;
         float time = tick / 2;
         if (shard == ShardType.FIRE) {
            GlStateManager.depthMask(false);
            GL11.glDisable(2896);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.tex1anim);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableCull();
            float height = 0.37F;
            float downAll = 0.9F;
            float upfromCenter = 0.1F;
            float downfromCenter = 0.15F;
            float width = 0.18F;
            float onepixelY = 0.0014619883F;

            for (int i = 0; i < 4; i++) {
               GlStateManager.pushMatrix();
               Tessellator tessellator = Tessellator.getInstance();
               BufferBuilder bufferbuilder = tessellator.getBuffer();
               bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
               float ad2 = 0.14F;
               GlStateManager.rotate(90 * i, 0.0F, 1.0F, 0.0F);
               int anplus = i * 20;
               bufferbuilder.pos(-width, height + downAll, downfromCenter).tex(0.0, 0.01754386F + time / 57.0F + anplus).endVertex();
               bufferbuilder.pos(-width, downAll, upfromCenter).tex(0.0, onepixelY + time / 57.0F + anplus).endVertex();
               bufferbuilder.pos(width, downAll, upfromCenter).tex(1.0, onepixelY + time / 57.0F + anplus).endVertex();
               bufferbuilder.pos(width, height + downAll, downfromCenter).tex(1.0, 0.01754386F + time / 57.0F + anplus).endVertex();
               tessellator.draw();
               GlStateManager.popMatrix();
            }

            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
         } else if (shard == ShardType.COLD) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.snowfall);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, -time * 0.007F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         } else if (shard == ShardType.POISON) {
            GlStateManager.depthMask(false);
            GL11.glDisable(2896);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.poison_fire);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableCull();
            float height = -0.43F;
            float downAll = 1.37F;
            float upfromCenter = 0.15F;
            float downfromCenter = 0.2F;
            float width = 0.2F;
            float framecount = 55.0F;

            for (int i = 0; i < 4; i++) {
               GlStateManager.pushMatrix();
               Tessellator tessellator = Tessellator.getInstance();
               BufferBuilder bufferbuilder = tessellator.getBuffer();
               bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
               GlStateManager.rotate(90 * i, 0.0F, 1.0F, 0.0F);
               int anplus = i * 20;
               bufferbuilder.pos(-width, height + downAll, downfromCenter)
                  .tex(0.0, 1.0F / framecount + time / framecount + anplus)
                  .endVertex();
               bufferbuilder.pos(-width, downAll, upfromCenter).tex(0.0, 0.0F + time / framecount + anplus).endVertex();
               bufferbuilder.pos(width, downAll, upfromCenter).tex(1.0, 0.0F + time / framecount + anplus).endVertex();
               bufferbuilder.pos(width, height + downAll, downfromCenter)
                  .tex(1.0, 1.0F / framecount + time / framecount + anplus)
                  .endVertex();
               tessellator.draw();
               GlStateManager.popMatrix();
            }

            GlStateManager.pushMatrix();
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.rain);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, -time * 0.015F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.color(shard.colorR, shard.colorG, shard.colorB, 1.0F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
         } else if (shard == ShardType.WATER) {
            GlStateManager.depthMask(false);
            GL11.glDisable(2896);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.water_round);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableCull();
            float height = 0.2F + Debugger.floats[0];
            float downAll = 1.02F + Debugger.floats[1];
            float upfromCenter = 0.2F + Debugger.floats[2];
            float downfromCenter = -0.2F + Debugger.floats[3];
            float width = 0.2F + Debugger.floats[4];
            float framecount = 50.0F;

            for (int i = 0; i < 3; i++) {
               GlStateManager.pushMatrix();
               Tessellator tessellator = Tessellator.getInstance();
               BufferBuilder bufferbuilder = tessellator.getBuffer();
               bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
               if (i == 0) {
                  GlStateManager.rotate(tick, 0.0F, 1.0F, 0.0F);
               }

               if (i == 1) {
                  GlStateManager.rotate(-tick, 0.0F, 1.0F, 0.0F);
               }

               if (i == 2) {
                  GlStateManager.rotate(tick * 0.9F, 0.0F, 1.0F, 0.0F);
               }

               int anplus = i * 20;
               bufferbuilder.pos(-width, height + downAll, downfromCenter)
                  .tex(0.0, 1.0F / framecount + time / framecount + anplus)
                  .endVertex();
               bufferbuilder.pos(-width, downAll, upfromCenter).tex(0.0, 0.0F + time / framecount + anplus).endVertex();
               bufferbuilder.pos(width, downAll, upfromCenter).tex(1.0, 0.0F + time / framecount + anplus).endVertex();
               bufferbuilder.pos(width, height + downAll, downfromCenter)
                  .tex(1.0, 1.0F / framecount + time / framecount + anplus)
                  .endVertex();
               tessellator.draw();
               GlStateManager.popMatrix();
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
         } else if (shard == ShardType.PAIN) {
            GlStateManager.depthMask(false);
            GL11.glDisable(2896);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.pain_shards);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableCull();
            float height = 0.37F;
            float downAll = 0.95F;
            float upfromCenter = 0.0F;
            float downfromCenter = 0.0F;
            float width = 0.23F;

            for (int i = 0; i < 3; i++) {
               GlStateManager.pushMatrix();
               Tessellator tessellatorx = Tessellator.getInstance();
               BufferBuilder bufferbuilderx = tessellatorx.getBuffer();
               bufferbuilderx.begin(7, DefaultVertexFormats.POSITION_TEX);
               float ad2 = 0.14F;
               GlStateManager.rotate(60 * i, 0.0F, 1.0F, 0.0F);
               int anplus = i * 16;
               bufferbuilderx.pos(-width, height + downAll, downfromCenter).tex(0.0, 0.024390243F + time / 41.0F + anplus).endVertex();
               bufferbuilderx.pos(-width, downAll, upfromCenter).tex(0.0, 0.0F + time / 41.0F + anplus).endVertex();
               bufferbuilderx.pos(width, downAll, upfromCenter).tex(1.0, 0.0F + time / 41.0F + anplus).endVertex();
               bufferbuilderx.pos(width, height + downAll, downfromCenter).tex(1.0, 0.024390243F + time / 41.0F + anplus).endVertex();
               tessellatorx.draw();
               GlStateManager.popMatrix();
            }

            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
         } else if (shard == ShardType.PLEASURE) {
            GlStateManager.depthMask(false);
            GL11.glDisable(2896);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.pleasure_petals);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableCull();
            float height = 0.37F + Debugger.floats[0];
            float downAll = 0.95F + Debugger.floats[1];
            float upfromCenter = 0.0F;
            float downfromCenter = 0.0F;
            float width = 0.23F + Debugger.floats[4];

            for (int i = 0; i < 3; i++) {
               GlStateManager.pushMatrix();
               Tessellator tessellatorx = Tessellator.getInstance();
               BufferBuilder bufferbuilderx = tessellatorx.getBuffer();
               bufferbuilderx.begin(7, DefaultVertexFormats.POSITION_TEX);
               float ad2 = 0.14F;
               GlStateManager.rotate(60 * i + 30, 0.0F, 1.0F, 0.0F);
               int anplus = i * 16;
               bufferbuilderx.pos(-width, height + downAll, downfromCenter).tex(0.0, 0.02F + time / 50.0F + anplus).endVertex();
               bufferbuilderx.pos(-width, downAll, upfromCenter).tex(0.0, 0.0F + time / 50.0F + anplus).endVertex();
               bufferbuilderx.pos(width, downAll, upfromCenter).tex(1.0, 0.0F + time / 50.0F + anplus).endVertex();
               bufferbuilderx.pos(width, height + downAll, downfromCenter).tex(1.0, 0.02F + time / 50.0F + anplus).endVertex();
               tessellatorx.draw();
               GlStateManager.popMatrix();
            }

            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
         } else if (shard == ShardType.DEATH) {
            GlStateManager.depthMask(false);
            GL11.glDisable(2896);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.death_skulls);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            float height = 0.37F + Debugger.floats[0];
            float downAll = 0.9F + Debugger.floats[1];
            float upfromCenter = 0.0F;
            float downfromCenter = 0.0F;
            float width = 0.18F + Debugger.floats[4];
            float time2 = tick / 4;
            Vec3d vec = GetMOP.Vec3dToPitchYaw(new Vec3d(x, y, z));

            for (int i = 0; i < 1; i++) {
               GlStateManager.pushMatrix();
               Tessellator tessellatorx = Tessellator.getInstance();
               BufferBuilder bufferbuilderx = tessellatorx.getBuffer();
               bufferbuilderx.begin(7, DefaultVertexFormats.POSITION_TEX);
               float ad2 = 0.14F;
               RenderManager renderm = Minecraft.getMinecraft().getRenderManager();
               if (renderm != null) {
                  float vewX = renderm.playerViewX / 90.0F;
                  upfromCenter = -0.15F * vewX;
                  downfromCenter = 0.15F * vewX;
                  GlStateManager.rotate(renderm.playerViewY + Debugger.floats[5], 0.0F, 1.0F, 0.0F);
               }

               int anplus = i * 22;
               bufferbuilderx.pos(-width, height + downAll, downfromCenter).tex(0.0, 0.032258064F + time2 / 31.0F + anplus).endVertex();
               bufferbuilderx.pos(-width, downAll, upfromCenter).tex(0.0, 0.0F + time2 / 31.0F + anplus).endVertex();
               bufferbuilderx.pos(width, downAll, upfromCenter).tex(1.0, 0.0F + time2 / 31.0F + anplus).endVertex();
               bufferbuilderx.pos(width, height + downAll, downfromCenter).tex(1.0, 0.032258064F + time2 / 31.0F + anplus).endVertex();
               tessellatorx.draw();
               GlStateManager.popMatrix();
            }

            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
         } else if (shard == ShardType.VOID) {
            GlStateManager.depthMask(false);
            GL11.glDisable(2896);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.void_bubbles);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            float height = 0.37F + Debugger.floats[0];
            float downAll = 0.95F + Debugger.floats[1];
            float upfromCenter = 0.0F;
            float downfromCenter = 0.0F;
            float width = 0.18F + Debugger.floats[4];
            float time2 = tick / 6;
            Vec3d vec = GetMOP.Vec3dToPitchYaw(new Vec3d(x, y, z));

            for (int i = 0; i < 1; i++) {
               GlStateManager.pushMatrix();
               Tessellator tessellatorx = Tessellator.getInstance();
               BufferBuilder bufferbuilderx = tessellatorx.getBuffer();
               bufferbuilderx.begin(7, DefaultVertexFormats.POSITION_TEX);
               float ad2 = 0.14F;
               RenderManager renderm = Minecraft.getMinecraft().getRenderManager();
               if (renderm != null) {
                  float vewX = renderm.playerViewX / 90.0F;
                  upfromCenter = -0.16F * vewX;
                  downfromCenter = 0.16F * vewX;
                  GlStateManager.rotate(renderm.playerViewY, 0.0F, 1.0F, 0.0F);
               }

               int anplus = i * 22;
               bufferbuilderx.pos(-width, height + downAll, downfromCenter)
                  .tex(0.0, 0.0076923077F + time2 / 130.0F + anplus)
                  .endVertex();
               bufferbuilderx.pos(-width, downAll, upfromCenter).tex(0.0, 0.0F + time2 / 130.0F + anplus).endVertex();
               bufferbuilderx.pos(width, downAll, upfromCenter).tex(1.0, 0.0F + time2 / 130.0F + anplus).endVertex();
               bufferbuilderx.pos(width, height + downAll, downfromCenter)
                  .tex(1.0, 0.0076923077F + time2 / 130.0F + anplus)
                  .endVertex();
               tessellatorx.draw();
               GlStateManager.popMatrix();
            }

            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
         } else if (shard == ShardType.ELECTRIC) {
            GlStateManager.depthMask(false);
            GL11.glDisable(2896);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.electric_in_sphere);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableCull();
            float height = 0.33F + Debugger.floats[0];
            float downAll = 0.97F + Debugger.floats[1];
            float upfromCenter = 0.0F;
            float downfromCenter = 0.0F;
            float width = 0.19F + Debugger.floats[4];

            for (int i = 0; i < 1; i++) {
               GlStateManager.pushMatrix();
               Tessellator tessellatorx = Tessellator.getInstance();
               BufferBuilder bufferbuilderx = tessellatorx.getBuffer();
               bufferbuilderx.begin(7, DefaultVertexFormats.POSITION_TEX);
               float ad2 = 0.14F;
               RenderManager renderm = Minecraft.getMinecraft().getRenderManager();
               if (renderm != null) {
                  float vewX = renderm.playerViewX / 90.0F;
                  upfromCenter = -0.18F * vewX;
                  downfromCenter = 0.18F * vewX;
                  GlStateManager.rotate(renderm.playerViewY, 0.0F, 1.0F, 0.0F);
               }

               int anplus = i * 9;
               bufferbuilderx.pos(-width, height + downAll, downfromCenter).tex(0.0, 0.03125F + time / 32.0F + anplus).endVertex();
               bufferbuilderx.pos(-width, downAll, upfromCenter).tex(0.0, 0.0F + time / 32.0F + anplus).endVertex();
               bufferbuilderx.pos(width, downAll, upfromCenter).tex(1.0, 0.0F + time / 32.0F + anplus).endVertex();
               bufferbuilderx.pos(width, height + downAll, downfromCenter).tex(1.0, 0.03125F + time / 32.0F + anplus).endVertex();
               tessellatorx.draw();
               GlStateManager.popMatrix();
            }

            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
         } else if (shard == ShardType.EARTH) {
            GL11.glDisable(2896);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.earth_in_sphere_model_tex);
            GlStateManager.disableCull();
            GlStateManager.pushMatrix();
            CrystalSphereTESR.modelEarth.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GlStateManager.popMatrix();
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
         } else if (shard == ShardType.LIVE) {
            for (int st = 0; st < 2; st++) {
               GlStateManager.depthMask(false);
               GL11.glDisable(2896);
               GlStateManager.enableBlend();
               if (st == 0) {
                  GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
                  Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.live_vines2);
               } else {
                  GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
                  Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.live_vines);
               }

               OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
               GlStateManager.disableCull();
               float height = 0.41F + Debugger.floats[0];
               float downAll = 0.93F + Debugger.floats[1];
               float upfromCenter = 0.0F;
               float downfromCenter = 0.0F;
               float width = 0.23F + Debugger.floats[4];

               for (int i = 0; i < 3; i++) {
                  if (i != 2 || st != 0) {
                     GlStateManager.pushMatrix();
                     Tessellator tessellatorx = Tessellator.getInstance();
                     BufferBuilder bufferbuilderx = tessellatorx.getBuffer();
                     bufferbuilderx.begin(7, DefaultVertexFormats.POSITION_TEX);
                     if (i == 2) {
                        RenderManager renderm = Minecraft.getMinecraft().getRenderManager();
                        if (renderm != null) {
                           float vewX = renderm.playerViewX / 90.0F;
                           upfromCenter = -0.16F * vewX;
                           downfromCenter = 0.16F * vewX;
                           GlStateManager.rotate(renderm.playerViewY, 0.0F, 1.0F, 0.0F);
                           float absX = Math.abs(vewX);
                           GlStateManager.color(absX, absX, absX, 1.0F);
                        }
                     } else {
                        GlStateManager.rotate(90 * i + time, 0.0F, 1.0F, 0.0F);
                     }

                     int anplus = i * 25;
                     bufferbuilderx.pos(-width, height + downAll, downfromCenter)
                        .tex(0.0, 0.016666668F + (time + anplus) / 60.0F)
                        .endVertex();
                     bufferbuilderx.pos(-width, downAll, upfromCenter).tex(0.0, 0.0F + (time + anplus) / 60.0F).endVertex();
                     bufferbuilderx.pos(width, downAll, upfromCenter).tex(1.0, 0.0F + (time + anplus) / 60.0F).endVertex();
                     bufferbuilderx.pos(width, height + downAll, downfromCenter)
                        .tex(1.0, 0.016666668F + (time + anplus) / 60.0F)
                        .endVertex();
                     tessellatorx.draw();
                     GlStateManager.popMatrix();
                  }
               }

               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.enableCull();
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
            }
         }
      }

      public void renderOut(ShardType shard, @Nullable TileCrystalSphere te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
         float time = AnimationTimer.tick % 16000;
         if (shard == ShardType.FIRE) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.tex1);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float ef2 = time * 0.001F;
            GlStateManager.rotate(ef2, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, ef2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.color(shard.colorR, shard.colorG, shard.colorB, 1.0F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         } else if (shard == ShardType.COLD) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.tex2);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float ef2 = time * 0.005F;
            GlStateManager.rotate(ef2, 0.0F, 0.0F, 1.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         } else if (shard == ShardType.POISON) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.poison_over);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float ef2 = time * 5.0E-4F;
            GlStateManager.rotate(ef2, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, ef2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.color(shard.colorR, shard.colorG, shard.colorB, (float)(Math.sin(time / 100.0F) / 2.0 + 1.0));
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         } else if (shard == ShardType.WATER) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.gloss2);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float ef2 = time * 8.0E-4F;
            GlStateManager.rotate(ef2, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, ef2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float alpha1 = (float)(Math.sin(time / 130.0F) / 4.0 + 0.75);
            GlStateManager.color(shard.colorR * alpha1, shard.colorG * alpha1, shard.colorB * alpha1, 1.0F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         } else if (shard == ShardType.PAIN) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.simple_magic_shoot);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float ef2 = time * 0.0012F;
            GlStateManager.rotate(ef2, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, ef2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.color(shard.colorR, shard.colorG, shard.colorB, 1.0F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         } else if (shard == ShardType.PLEASURE) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.neon_effect);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float ef2 = time * 0.0012F;
            GlStateManager.rotate(ef2, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, ef2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float ratio = (float)(Math.sin(time / 357.0F) / 4.0 + 0.75);
            float unratio = 1.0F - ratio;
            Vec3d rainbow = ColorConverters.getRainbow(time % 200.0F / 200.0F);
            GlStateManager.color(
               (float)rainbow.x * ratio + shard.colorR * unratio,
               (float)rainbow.y * ratio + shard.colorG * unratio,
               (float)rainbow.z * ratio + shard.colorB * unratio,
               1.0F
            );
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         } else if (shard == ShardType.DEATH) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.neon_effect);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float ef2 = time * 0.001F;
            GlStateManager.rotate(ef2, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, -ef2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.color(shard.colorR, shard.colorG, shard.colorB, 1.0F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         } else if (shard == ShardType.VOID) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.void_sphere_over);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float ef2 = time * 0.001F;
            GlStateManager.translate(-ef2, 0.0F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.popMatrix();
         } else if (shard == ShardType.ELECTRIC) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.electric_sphere_over);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float ef2 = time * 0.01F;
            GlStateManager.rotate(ef2, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(ef2, ef2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float ratio = (float)(Math.sin(time / 7.0F) * 0.15F + 0.15);
            float unratio = 1.0F - ratio;
            GlStateManager.color(ratio + shard.colorR * unratio, ratio + shard.colorG * unratio, ratio + shard.colorB * unratio, 1.0F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         } else if (shard == ShardType.EARTH) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.earth_sphere_over);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float ef2 = time * 1.0E-4F;
            GlStateManager.translate(ef2, 0.0F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.color(shard.colorR, shard.colorG, shard.colorB, 1.0F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         } else if (shard == ShardType.AIR) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.air_sphere_over);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float ef2 = time * 0.008F;
            GlStateManager.rotate(-ef2, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(-ef2, -ef2 * 0.5F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.color(shard.colorR * 0.75F, shard.colorG * 0.75F, shard.colorB * 0.75F, 0.75F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         } else if (shard == ShardType.LIVE) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(CrystalSphereTESR.live_sphere_over);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float ef2 = time * 0.004F;
            GlStateManager.rotate(ef2 * 5.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(ef2 * 0.1F, ef2 * 0.1F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float unratio = (float)Math.min(Math.sqrt(x * x + y * y + z * z) / 11.0, 1.0);
            float ratio = 1.0F - unratio;
            GlStateManager.color(
               ratio * 0.7F + shard.colorR * unratio, ratio * 0.7F + shard.colorG * unratio, ratio * 0.7F + shard.colorB * unratio, 1.0F
            );
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            CrystalSphereTESR.model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         }
      }
   }
}
