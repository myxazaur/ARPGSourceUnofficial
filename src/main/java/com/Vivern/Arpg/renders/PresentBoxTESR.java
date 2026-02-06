package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.PresentBoxModel;
import com.Vivern.Arpg.tileentity.TilePresentBox;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class PresentBoxTESR extends TileEntitySpecialRenderer<TilePresentBox> {
   public static PresentBoxModel model = new PresentBoxModel();
   public static ResourceLocation[] tex = new ResourceLocation[]{
      new ResourceLocation("arpg:textures/present_box_1.png"),
      new ResourceLocation("arpg:textures/present_box_2.png"),
      new ResourceLocation("arpg:textures/present_box_3.png"),
      new ResourceLocation("arpg:textures/present_box_4.png"),
      new ResourceLocation("arpg:textures/present_box_5.png"),
      new ResourceLocation("arpg:textures/present_box_6.png")
   };

   public void render(TilePresentBox te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      if (te != null) {
         if (te.opened) {
            if (te.openTime < 20) {
               te.openTime++;
            }
         } else if (te.openTime > 0) {
            te.openTime--;
         }
      }

      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.disableCull();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      if (destroyStage >= 0) {
         this.bindTexture(DESTROY_STAGES[destroyStage]);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scale(4.0F, 4.0F, 1.0F);
         GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.matrixMode(5888);
      } else if (te != null) {
         this.bindTexture(tex[te.texture]);
      } else {
         this.bindTexture(tex[0]);
      }

      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.501F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      model.render(null, te == null ? 0.0F : te.openTime * 0.14F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
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
}
