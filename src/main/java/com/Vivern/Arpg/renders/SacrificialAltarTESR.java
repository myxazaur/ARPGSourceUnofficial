//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.tileentity.TileSacrificialAltar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class SacrificialAltarTESR extends TileEntitySpecialRenderer<TileSacrificialAltar> {
   public void render(TileSacrificialAltar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      ItemStack stack0 = te.getStackInSlot(0);
      if (!stack0.isEmpty()) {
         float fend = 0.5F;
         float fy = 1.1125F;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x + fend, (float)y + fy, (float)z + fend);
         GlStateManager.rotate((te.ticksExisted + partialTicks) * 3.0F, 0.0F, 1.0F, 0.0F);
         this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         Minecraft.getMinecraft().getRenderItem().renderItem(stack0, TransformType.GROUND);
         GlStateManager.popMatrix();
      }
   }
}
