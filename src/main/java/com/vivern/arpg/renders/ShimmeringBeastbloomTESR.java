package com.vivern.arpg.renders;

import com.vivern.arpg.elements.models.ShimmeringBeastbloomModel;
import com.vivern.arpg.tileentity.TileShimmeringBeastbloom;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class ShimmeringBeastbloomTESR extends TileEntitySpecialRenderer<TileShimmeringBeastbloom> {
   public static ShimmeringBeastbloomModel model = new ShimmeringBeastbloomModel();
   public static ResourceLocation TEXTURE = new ResourceLocation("arpg:textures/shimmering_beastbloom_model_tex.png");

   public void render(TileShimmeringBeastbloom te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
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
         this.bindTexture(TEXTURE);
      }

      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      if (te != null) {
         model.growstage = te.growStage;
         model.opening = te.opening;
         model.render(null, te.firstLeavesCount, te.secndLeavesCount, te.firstLeavesOffset, te.secndLeavesOffset, te.coreSize, 0.0625F);
      } else {
         model.growstage = 0;
         model.opening = 1;
         model.render(null, 5.0F, 5.0F, 5.0F, 10.0F, 1.0F, 0.0625F);
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
}
