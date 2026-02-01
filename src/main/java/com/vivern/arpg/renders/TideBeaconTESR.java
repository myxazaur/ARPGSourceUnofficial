package com.vivern.arpg.renders;

import com.vivern.arpg.elements.models.TideBeaconModel;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.tileentity.TileNexusBeacon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TideBeaconTESR extends TileEntitySpecialRenderer<TileNexusBeacon> {
   public static TideBeaconModel model = new TideBeaconModel();
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/tide_beacon_model_tex.png");
   public static ResourceLocation texDried = new ResourceLocation("arpg:textures/tide_beacon_dried_tex.png");

   public void render(TileNexusBeacon te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.disableCull();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      if (te != null && te.ACTIVATOR > 0 && te.ACTIVATOR < 6) {
         Item activ = null;
         if (te.ACTIVATOR == 1) {
            activ = ItemsRegister.TIDEACTIVATOR1;
         }

         if (te.ACTIVATOR == 2) {
            activ = ItemsRegister.TIDEACTIVATOR2;
         }

         if (te.ACTIVATOR == 3) {
            activ = ItemsRegister.TIDEACTIVATOR3;
         }

         if (te.ACTIVATOR == 4) {
            activ = ItemsRegister.TIDEACTIVATOR4;
         }

         if (te.ACTIVATOR == 5) {
            activ = ItemsRegister.TIDEACTIVATOR5;
         }

         if (activ != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.7F, 0.0F);
            GlStateManager.rotate(-135.0F, 0.0F, 0.0F, 1.0F);
            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(activ), TransformType.GROUND);
            GlStateManager.popMatrix();
         }
      }

      if (destroyStage >= 0) {
         this.bindTexture(DESTROY_STAGES[destroyStage]);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scale(4.0F, 4.0F, 1.0F);
         GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.matrixMode(5888);
      } else {
         this.bindTexture(te != null && te.dried ? texDried : tex);
      }

      if (te != null) {
         model.isDried = te.dried;
         model.render(null, te.tentacklesCount, te.randAngle1, te.randAngle2, te.randAngle3, te.randAngle4, 0.0625F);
      } else {
         model.isDried = false;
         model.render(null, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      GlStateManager.enableCull();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
      if (destroyStage >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }
   }
}
