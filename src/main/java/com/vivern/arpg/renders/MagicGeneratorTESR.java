package com.vivern.arpg.renders;

import com.vivern.arpg.elements.models.MagicGeneratorModel;
import com.vivern.arpg.tileentity.TileMagicGenerator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class MagicGeneratorTESR extends TileEntitySpecialRenderer<TileMagicGenerator> {
   public static MagicGeneratorModel model = new MagicGeneratorModel();
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/magic_generator_model_tex.png");

   public void renderColoredGlowingCuboid(
      float fillValue,
      float y,
      float[] color,
      float[] gradientColor,
      float size,
      float height,
      float glowOffset,
      float partialTicks,
      boolean bottom,
      boolean top,
      byte modifier
   ) {
      if (fillValue > 0.0F) {
         TEISROther.renderColoredGlowingCuboid(0.0, y, 0.0, color, gradientColor, size, height * fillValue, glowOffset, partialTicks, bottom, top, modifier);
      }
   }

   public void render(TileMagicGenerator te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      GlStateManager.disableCull();
      this.bindTexture(tex);
      model.render(null, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.enableCull();
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      float[] color = new float[]{0.47F, 0.65F, 1.0F};
      float[] color2 = new float[]{1.0F, 1.0F, 1.0F};
      this.renderColoredGlowingCuboid(1.0F, -0.9375F, color, color2, 0.0625F, 0.375F, 0.03125F, partialTicks, true, true, (byte)4);
      GlStateManager.popMatrix();
      GlStateManager.enableBlend();
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      this.bindTexture(tex);
      model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.disableCull();
      this.bindTexture(tex);
      model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
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
}
