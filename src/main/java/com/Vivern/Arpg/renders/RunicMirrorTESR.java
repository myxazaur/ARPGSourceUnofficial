package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.RunicMirrorModel;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.tileentity.TileRunicMirror;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class RunicMirrorTESR extends TileEntitySpecialRenderer<TileRunicMirror> {
   public static RunicMirrorModel model = new RunicMirrorModel();
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/runic_mirror_model_tex.png");
   public static ResourceLocation beam_mirror_1 = new ResourceLocation("arpg:textures/beam_mirror_1.png");
   public static ResourceLocation beam_mirror_2 = new ResourceLocation("arpg:textures/beam_mirror_2.png");
   public static ResourceLocation beam_mirror_3 = new ResourceLocation("arpg:textures/beam_mirror_3.png");
   public static ResourceLocation beam_mirror_cap = new ResourceLocation("arpg:textures/beam_mirror_cap.png");

   public void render(TileRunicMirror te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.disableCull();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
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

      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      float colorR = 0.85F;
      float colorG = 0.6F;
      float colorB = 1.0F;
      if (te != null && !te.isEmpty()) {
         colorR = te.elementType.colorR;
         colorG = te.elementType.colorG;
         colorB = te.elementType.colorB;
      }

      if (te != null) {
         float pitch = GetMOP.partial(te.rendrotationPitch, te.prevrendrotationPitch, partialTicks);
         float yaw = GetMOP.partial(te.rendrotationYaw, te.prevrendrotationYaw, partialTicks);
         model.render(null, yaw / 57.2958F, pitch / 57.2958F, colorR, colorG, colorB, 0.0625F);
         if (!te.isEmpty()) {
            this.renderBeam(te.lastTraceLength, colorR, colorG, colorB, pitch, yaw, te, partialTicks);
         }
      } else {
         model.render(null, 0.0F, 0.0F, colorR, colorG, colorB, 0.0625F);
      }

      GlStateManager.enableCull();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (destroyStage >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }
   }

   public void renderBeam(
      float length, float colorR, float colorG, float colorB, float rotationPitch, float rotationYaw, TileRunicMirror mirror, float partialTicks
   ) {
      float[] color2 = ShardType.additionalColors[mirror.elementType.id];
      GlStateManager.pushMatrix();
      GlStateManager.rotate(rotationYaw, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(0.0, 0.5, 0.0);
      GlStateManager.rotate(rotationPitch, 1.0F, 0.0F, 0.0F);
      GlStateManager.translate(0.0, -0.5, 0.0);
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200.0F, 200.0F);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableTexture2D();
      GlStateManager.shadeModel(7425);
      GlStateManager.enableBlend();
      if (mirror.elementType == ShardType.VOID) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      } else {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      }

      GlStateManager.disableAlpha();
      GlStateManager.disableCull();
      GlStateManager.depthMask(false);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
      double zLength = -0.8;
      float R2 = color2[0];
      float G2 = color2[1];
      float B2 = color2[2];
      zLength = -0.4;
      bufferbuilder.pos(-0.25, 0.25, 0.0).color(colorR, colorG, colorB, 1.0F).endVertex();
      bufferbuilder.pos(-0.3, 0.2, zLength).color(R2, G2, B2, 0.0F).endVertex();
      bufferbuilder.pos(0.3, 0.2, zLength).color(R2, G2, B2, 0.0F).endVertex();
      bufferbuilder.pos(0.25, 0.25, 0.0).color(colorR, colorG, colorB, 1.0F).endVertex();
      bufferbuilder.pos(-0.25, 0.75, 0.0).color(colorR, colorG, colorB, 1.0F).endVertex();
      bufferbuilder.pos(-0.3, 0.8, zLength).color(R2, G2, B2, 0.0F).endVertex();
      bufferbuilder.pos(0.3, 0.8, zLength).color(R2, G2, B2, 0.0F).endVertex();
      bufferbuilder.pos(0.25, 0.75, 0.0).color(colorR, colorG, colorB, 1.0F).endVertex();
      bufferbuilder.pos(-0.25, 0.25, 0.0).color(colorR, colorG, colorB, 1.0F).endVertex();
      bufferbuilder.pos(-0.3, 0.2, zLength).color(R2, G2, B2, 0.0F).endVertex();
      bufferbuilder.pos(-0.3, 0.8, zLength).color(R2, G2, B2, 0.0F).endVertex();
      bufferbuilder.pos(-0.25, 0.75, 0.0).color(colorR, colorG, colorB, 1.0F).endVertex();
      bufferbuilder.pos(0.25, 0.25, 0.0).color(colorR, colorG, colorB, 1.0F).endVertex();
      bufferbuilder.pos(0.3, 0.2, zLength).color(R2, G2, B2, 0.0F).endVertex();
      bufferbuilder.pos(0.3, 0.8, zLength).color(R2, G2, B2, 0.0F).endVertex();
      bufferbuilder.pos(0.25, 0.75, 0.0).color(colorR, colorG, colorB, 1.0F).endVertex();
      tessellator.draw();
      int frames = 32;
      float offset = 0.5F;
      float beamWidth = 0.35F;
      float oneframeY = 1.0F / frames;
      int time = AnimationTimer.tick / 2 % frames;
      float frameYoffset = oneframeY * time;
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, offset, 0.0F);
      Vec3d beamLook = mirror.clientBeamLook;
      float angl = MyRenderHelper.calculateBeamFaceToCameraAngle(
         mirror.getPos().getX() + 0.5,
         mirror.getPos().getY() + 1,
         mirror.getPos().getZ() + 0.5,
         beamLook.x,
         beamLook.y,
         beamLook.z,
         partialTicks
      );
      GlStateManager.rotate(angl, 0.0F, 0.0F, 1.0F);
      GlStateManager.enableTexture2D();
      if (mirror.elementType == ShardType.VOID) {
         Minecraft.getMinecraft().renderEngine.bindTexture(beam_mirror_3);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         bufferbuilder.pos(0.0, beamWidth, 0.0).tex(0.0, frameYoffset).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
         bufferbuilder.pos(0.0, beamWidth, -length).tex(1.0, frameYoffset).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
         bufferbuilder.pos(0.0, -beamWidth, -length)
            .tex(1.0, frameYoffset + oneframeY)
            .color(1.0F, 1.0F, 1.0F, 1.0F)
            .endVertex();
         bufferbuilder.pos(0.0, -beamWidth, 0.0).tex(0.0, frameYoffset + oneframeY).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
         tessellator.draw();
      } else {
         Minecraft.getMinecraft().renderEngine.bindTexture(beam_mirror_1);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         bufferbuilder.pos(0.0, beamWidth, 0.0).tex(0.0, frameYoffset).color(colorR, colorG, colorB, 1.0F).endVertex();
         bufferbuilder.pos(0.0, beamWidth, -length).tex(1.0, frameYoffset).color(R2, G2, B2, 1.0F).endVertex();
         bufferbuilder.pos(0.0, -beamWidth, -length).tex(1.0, frameYoffset + oneframeY).color(R2, G2, B2, 1.0F).endVertex();
         bufferbuilder.pos(0.0, -beamWidth, 0.0)
            .tex(0.0, frameYoffset + oneframeY)
            .color(colorR, colorG, colorB, 1.0F)
            .endVertex();
         tessellator.draw();
         Minecraft.getMinecraft().renderEngine.bindTexture(beam_mirror_2);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         bufferbuilder.pos(0.0, beamWidth, 0.0).tex(0.0, frameYoffset).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
         bufferbuilder.pos(0.0, beamWidth, -length).tex(1.0, frameYoffset).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
         bufferbuilder.pos(0.0, -beamWidth, -length)
            .tex(1.0, frameYoffset + oneframeY)
            .color(1.0F, 1.0F, 1.0F, 1.0F)
            .endVertex();
         bufferbuilder.pos(0.0, -beamWidth, 0.0).tex(0.0, frameYoffset + oneframeY).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
         tessellator.draw();
      }

      float roundwidthHigh = beamWidth * 0.4375F;
      float roundwidthLow = beamWidth * 0.1875F;
      Minecraft.getMinecraft().renderEngine.bindTexture(beam_mirror_cap);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      bufferbuilder.pos(-roundwidthLow, roundwidthLow, -length + 0.05).tex(0.0, 0.0).color(R2, G2, B2, 1.0F).endVertex();
      bufferbuilder.pos(roundwidthLow, roundwidthLow, -length + 0.05).tex(1.0, 0.0).color(R2, G2, B2, 1.0F).endVertex();
      bufferbuilder.pos(roundwidthLow, -roundwidthLow, -length + 0.05).tex(1.0, 1.0).color(R2, G2, B2, 1.0F).endVertex();
      bufferbuilder.pos(-roundwidthLow, -roundwidthLow, -length + 0.05).tex(0.0, 1.0).color(R2, G2, B2, 1.0F).endVertex();
      bufferbuilder.pos(-roundwidthHigh, roundwidthHigh, -0.06).tex(0.0, 0.0).color(colorR, colorG, colorB, 1.0F).endVertex();
      bufferbuilder.pos(roundwidthHigh, roundwidthHigh, -0.06).tex(1.0, 0.0).color(colorR, colorG, colorB, 1.0F).endVertex();
      bufferbuilder.pos(roundwidthHigh, -roundwidthHigh, -0.06).tex(1.0, 1.0).color(colorR, colorG, colorB, 1.0F).endVertex();
      bufferbuilder.pos(-roundwidthHigh, -roundwidthHigh, -0.06).tex(0.0, 1.0).color(colorR, colorG, colorB, 1.0F).endVertex();
      if (mirror.elementType != ShardType.VOID) {
         bufferbuilder.pos(-roundwidthLow, roundwidthLow, -length + 0.05)
            .tex(0.0, 0.0)
            .color(0.5F, 0.5F, 0.5F, 0.5F)
            .endVertex();
         bufferbuilder.pos(roundwidthLow, roundwidthLow, -length + 0.05)
            .tex(1.0, 0.0)
            .color(0.5F, 0.5F, 0.5F, 0.5F)
            .endVertex();
         bufferbuilder.pos(roundwidthLow, -roundwidthLow, -length + 0.05)
            .tex(1.0, 1.0)
            .color(0.5F, 0.5F, 0.5F, 0.5F)
            .endVertex();
         bufferbuilder.pos(-roundwidthLow, -roundwidthLow, -length + 0.05)
            .tex(0.0, 1.0)
            .color(0.5F, 0.5F, 0.5F, 0.5F)
            .endVertex();
         bufferbuilder.pos(-roundwidthHigh, roundwidthHigh, -0.06).tex(0.0, 0.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
         bufferbuilder.pos(roundwidthHigh, roundwidthHigh, -0.06).tex(1.0, 0.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
         bufferbuilder.pos(roundwidthHigh, -roundwidthHigh, -0.06).tex(1.0, 1.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
         bufferbuilder.pos(-roundwidthHigh, -roundwidthHigh, -0.06).tex(0.0, 1.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      }

      tessellator.draw();
      GlStateManager.disableTexture2D();
      GlStateManager.popMatrix();
      float spirallength = length / 2.0F;
      int spiralsegments = 10;
      float multiplier = 2.5F / spiralsegments * spirallength;
      GlStateManager.translate(0.15F, 1.57F, 0.0F);
      float alpha = 1.0F;

      for (int i = 0; i < spiralsegments; i++) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(-0.15, -1.064, 0.0);
         GlStateManager.rotate(70 * i - AnimationTimer.tick * 2, 0.0F, 0.0F, 1.0F);
         GlStateManager.translate(0.15, 1.064, 0.0);
         float zz = -0.4F * i;
         float yy = -0.85F;
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         bufferbuilder.pos(0.0, yy, (0.0 + zz) * multiplier).color(colorR, colorG, colorB, i == 0 ? 0.0F : alpha).endVertex();
         bufferbuilder.pos(0.0, yy, (-0.3 + zz) * multiplier).color(R2, G2, B2, 0.0F).endVertex();
         alpha -= 1.0F / spiralsegments;
         bufferbuilder.pos(-0.3, yy, (-0.7 + zz) * multiplier).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(-0.3, yy, (-0.4 + zz) * multiplier).color(colorR, colorG, colorB, alpha).endVertex();
         tessellator.draw();
         GlStateManager.popMatrix();
      }

      GlStateManager.depthMask(true);
      GlStateManager.enableCull();
      GlStateManager.disableBlend();
      GlStateManager.shadeModel(7424);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
   }
}
