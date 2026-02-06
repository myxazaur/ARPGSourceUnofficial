package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.tileentity.TileCalibrationBundle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class CalibrationBundleTESR extends TileEntitySpecialRenderer<TileCalibrationBundle> {
   public void render(TileCalibrationBundle te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(x, y + 0.5, z);
      Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      float translateScale = 0.0625F;

      for (TileCalibrationBundle.CalibrationThing thing : te.things) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(thing.posX * translateScale, 0.0F, thing.posZ * translateScale);
         thing.item.renderInTESR(thing);
         GlStateManager.popMatrix();
      }

      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
   }
}
