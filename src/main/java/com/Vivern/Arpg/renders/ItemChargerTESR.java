package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.tileentity.TileItemCharger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class ItemChargerTESR extends TileEntitySpecialRenderer<TileItemCharger> {
   public ModelBase model;
   public ResourceLocation texture;

   public ItemChargerTESR(ModelBase model, ResourceLocation texture) {
      this.model = model;
      this.texture = texture;
   }

   public void render(TileItemCharger te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.disableCull();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      if (te != null) {
         float lbX = OpenGlHelper.lastBrightnessX;
         float lbY = OpenGlHelper.lastBrightnessY;
         GlStateManager.rotate(90 * te.rotation, 0.0F, 1.0F, 0.0F);
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 0.7F, 0.2F);
         GlStateManager.scale(0.5F, 0.5F, 0.5F);
         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
         Minecraft.getMinecraft().getRenderItem().renderItem(te.getStackInSlot(0), TransformType.FIXED);
         GlStateManager.popMatrix();
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
         this.bindTexture(this.texture);
         boolean fullcharged = te.isItemFullcharged();
         this.model
            .render(
               null,
               te.getPos().getX(),
               te.getPos().getY(),
               te.getPos().getZ(),
               !fullcharged && te.electricStorage.getEnergyStored() > 0 && !te.isEmpty() ? 1.0F : 0.0F,
               fullcharged ? 1.0F : 0.0F,
               0.0625F
            );
      } else {
         this.bindTexture(this.texture);
         this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
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
