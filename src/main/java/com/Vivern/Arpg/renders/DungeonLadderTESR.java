package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.DungeonLadderModel;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.tileentity.TileDungeonLadder;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class DungeonLadderTESR extends TileEntitySpecialRenderer<TileDungeonLadder> {
   public static DungeonLadderModel model = new DungeonLadderModel();

   public void render(TileDungeonLadder te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      boolean up = te.ladderStyle > 2;
      int style = up ? te.ladderStyle - 3 : te.ladderStyle;
      float lightmapDiffuse = up ? 120.0F : 240.0F;
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.enableCull();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.495F + (up ? -1.99F : 0.0F), (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      if (up) {
         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      }

      int lightmapBlock = te.blockmaterial.getPackedLightmapCoords(te.blockmaterial.getDefaultState(), te.getWorld(), te.getPos());
      Vec3d vec1 = ColorConverters.DecimaltoRGB(lightmapBlock);
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(
         OpenGlHelper.lightmapTexUnit, Math.min(240, (int)(vec1.z * lightmapDiffuse)), Math.min(240, (int)(vec1.x * lightmapDiffuse))
      );
      this.bindTexture(te.BLOCK_TEXTURES);
      model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      int lightmapLadder = te.laddermaterial.getPackedLightmapCoords(te.laddermaterial.getDefaultState(), te.getWorld(), te.getPos());
      Vec3d vec2 = ColorConverters.DecimaltoRGB(lightmapLadder);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240, (int)(vec2.z * 240.0)), Math.min(240, (int)(vec2.x * 240.0)));
      this.bindTexture(te.LADDER_TEXTURES);
      if (up) {
         GlStateManager.rotate(-te.face.getHorizontalAngle() - 90.0F, 0.0F, 1.0F, 0.0F);
      } else {
         GlStateManager.rotate(te.face.getHorizontalAngle() + 90.0F, 0.0F, 1.0F, 0.0F);
      }

      model.render(null, 1.0F, style, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.disableCull();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableTexture2D();
      GlStateManager.shadeModel(7425);
      GlStateManager.disableAlpha();
      GlStateManager.depthMask(false);
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 8.4F, 0.0F);
      float alphaa = 0.16F;
      float size = 0.5F;

      for (int i = 0; i < 15; i++) {
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         bufferbuilder.pos(-size, 0.0, -size).color(0.0F, 0.0F, 0.0F, alphaa).endVertex();
         bufferbuilder.pos(-size, 0.0, size).color(0.0F, 0.0F, 0.0F, alphaa).endVertex();
         bufferbuilder.pos(size, 0.0, size).color(0.0F, 0.0F, 0.0F, alphaa).endVertex();
         bufferbuilder.pos(size, 0.0, -size).color(0.0F, 0.0F, 0.0F, alphaa).endVertex();
         tessellator.draw();
         GlStateManager.translate(0.0F, -0.5F, 0.0F);
      }

      GlStateManager.popMatrix();
      GlStateManager.enableCull();
      GlStateManager.depthMask(true);
      GlStateManager.shadeModel(7424);
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.disableCull();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (destroyStage >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }

      GL11.glEnable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
   }
}
