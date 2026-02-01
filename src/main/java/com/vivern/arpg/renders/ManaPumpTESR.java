package com.vivern.arpg.renders;

import com.vivern.arpg.elements.models.ManaPumpModel;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.tileentity.TileManaPump;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumFacing.Axis;

public class ManaPumpTESR extends TileEntitySpecialRenderer<TileManaPump> {
   public static ManaPumpModel model = new ManaPumpModel();
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/mana_pump_model_tex.png");

   public void renderColoredGlowingCuboid(
      float fillValue,
      float y,
      float[] color,
      float[] gradientColor,
      float sizeX,
      float sizeZ,
      float height,
      float glowOffset,
      float partialTicks,
      boolean bottom,
      boolean top,
      byte modifier
   ) {
      if (fillValue > 0.0F) {
         TEISROther.renderColoredGlowingCuboid(
            0.0, y, 0.0, color, gradientColor, sizeX, sizeZ, height * fillValue, glowOffset, partialTicks, bottom, top, modifier
         );
      }
   }

   public void render(TileManaPump te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      if (te != null) {
         GlStateManager.rotate(te.facing.getHorizontalAngle() - 90.0F, 0.0F, 1.0F, 0.0F);
      }

      this.bindTexture(tex);
      GlStateManager.disableCull();
      model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.enableCull();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x + 0.5F, (float)y, (float)z + 0.5F);
      float[] color = new float[]{0.47F, 0.65F, 1.0F};
      float[] color2 = new float[]{1.0F, 1.0F, 1.0F};
      if (te != null && te.facing != null) {
         float flow = Math.min(te.flow, 4.0F) / 4.0F;
         if (Debugger.floats[1] > 0.0F) {
            flow = Debugger.floats[1];
         }

         if (flow > 0.0F) {
            float sizeBig = 0.6875F;
            float sizeSmall = 0.03125F * flow;
            if (te.facing.getAxis() == Axis.X) {
               this.renderColoredGlowingCuboid(
                  1.0F, 0.3125F - sizeSmall, color, color2, sizeBig, sizeSmall, sizeSmall * 2.0F, 0.03125F, partialTicks, true, true, (byte)4
               );
            } else {
               this.renderColoredGlowingCuboid(
                  1.0F, 0.3125F - sizeSmall, color, color2, sizeSmall, sizeBig, sizeSmall * 2.0F, 0.03125F, partialTicks, true, true, (byte)4
               );
            }
         }
      }

      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      if (te != null) {
         GlStateManager.rotate(te.facing.getHorizontalAngle() - 90.0F, 0.0F, 1.0F, 0.0F);
      }

      this.bindTexture(tex);
      model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }
}
