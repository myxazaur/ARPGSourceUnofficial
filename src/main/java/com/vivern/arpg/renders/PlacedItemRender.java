package com.vivern.arpg.renders;

import com.vivern.arpg.entity.EntityPlacedItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// FIX: change `Render<T>` to `Render<EntityPlacedItem>`
@SideOnly(Side.CLIENT)
public class PlacedItemRender<T extends EntityPlacedItem> extends Render<EntityPlacedItem> {
   RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

   public PlacedItemRender(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(EntityPlacedItem entity, double x, double y, double z, float entityYaw, float partialTicks) {
      ItemStack torender = entity.getPlacedItemStack();
      if (torender != null && torender != ItemStack.EMPTY) {
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
         }

         this.itemRenderer.renderItem(torender, TransformType.GROUND);
         if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
         }

         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
      }

      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(EntityPlacedItem entity) {
      return TextureMap.LOCATION_BLOCKS_TEXTURE;
   }
}
