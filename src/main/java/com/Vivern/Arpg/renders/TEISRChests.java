package com.Vivern.Arpg.renders;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Deprecated
@SideOnly(Side.CLIENT)
public class TEISRChests extends TileEntityItemStackRenderer {
   public final ModelChest simpleChest = new ModelChest();

   public void renderByItem(ItemStack itemStackIn) {
      this.renderByItem(itemStackIn, 1.0F);
   }

   public void drawModel() {
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      GlStateManager.translate(0.5F, 0.5F, 0.5F);
      GlStateManager.translate(-0.5F, -0.5F, -0.5F);
      this.simpleChest.renderAll();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   static {
      instance = new TEISRChests();
   }
}
