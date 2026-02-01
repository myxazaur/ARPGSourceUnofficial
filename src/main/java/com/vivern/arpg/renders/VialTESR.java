package com.vivern.arpg.renders;

import com.vivern.arpg.tileentity.TileVial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class VialTESR extends TileEntitySpecialRenderer<TileVial> {
   public void render(TileVial te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(x, y + 0.5, z);
      Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.enableCull();
      float translateScale = 0.0625F;

      for (int i = 0; i < 4; i++) {
         ItemStack itemstack = te.vials[i];
         if (itemstack != null && !itemstack.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(te.positions[i][0] * translateScale, 0.0F, te.positions[i][1] * translateScale);
            if (te.rotations[i]) {
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            }

            Minecraft.getMinecraft().getRenderItem().renderItem(itemstack, TransformType.NONE);
            GlStateManager.popMatrix();
         }
      }

      GlStateManager.disableBlend();
      GlStateManager.disableCull();
      GlStateManager.popMatrix();
   }
}
