package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.tileentity.TileAdvancedBlockDetector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class AdvancedBlockDetectorTESR extends TileEntitySpecialRenderer<TileAdvancedBlockDetector> {
   public void render(TileAdvancedBlockDetector te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      if (te.stackForRender != null && !te.stackForRender.isEmpty()) {
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x + 0.5F, (float)y + 0.3125F, (float)z + 0.5F);
         this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         Minecraft.getMinecraft().getRenderItem().renderItem(te.stackForRender, TransformType.GROUND);
         GlStateManager.popMatrix();
      }
   }
}
