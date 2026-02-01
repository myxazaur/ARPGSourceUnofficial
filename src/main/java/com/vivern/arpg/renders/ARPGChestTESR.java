package com.vivern.arpg.renders;

import com.vivern.arpg.elements.models.BigChestModel;
import com.vivern.arpg.elements.models.StandartChestModel;
import com.vivern.arpg.elements.models.StormBigChestModel;
import com.vivern.arpg.elements.models.StormChestModel;
import com.vivern.arpg.tileentity.ChestLock;
import com.vivern.arpg.tileentity.EnumChest;
import com.vivern.arpg.tileentity.TileARPGChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class ARPGChestTESR extends TileEntitySpecialRenderer<TileARPGChest> {
   public static EnumChest reservedChestType;
   public static StandartChestModel model = new StandartChestModel();
   public static BigChestModel modelBig = new BigChestModel();
   public static StormChestModel modelStorm = new StormChestModel();
   public static StormBigChestModel modelBigStorm = new StormBigChestModel();
   public static ResourceLocation TEXTURE = new ResourceLocation("arpg:textures/arpg_chest_tex.png");

   public void render(TileARPGChest te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      EnumChest chestType = te == null ? reservedChestType : te.type;
      this.render(te, x, y, z, partialTicks, destroyStage, alpha, chestType);
   }

   public void render(TileARPGChest te, double x, double y, double z, float partialTicks, int destroyStage, float alpha, EnumChest chestType) {
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
         this.bindTexture(chestType.texture);
      }

      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      if (te != null) {
         GlStateManager.rotate(90 * te.getChestFacing().getHorizontalIndex(), 0.0F, 1.0F, 0.0F);
      }

      if (te != null) {
         float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
         f = 1.0F - f;
         f = 1.0F - f * f * f;
         float rotateX = -(f * (float) (Math.PI / 2));
         TileARPGChest.EnumChestStanding standing = te.getChestStanding();
         if (standing == TileARPGChest.EnumChestStanding.MIDDLE) {
            chestType.model
               .render(
                  null,
                  te.getPos().getX(),
                  te.getPos().getY(),
                  te.getPos().getZ(),
                  chestType.light,
                  rotateX,
                  0.0625F
               );
         }

         if (standing == TileARPGChest.EnumChestStanding.LEFT) {
            chestType.modelLarge
               .render(
                  null,
                  te.getPos().getX(),
                  te.getPos().getY(),
                  te.getPos().getZ(),
                  chestType.light,
                  rotateX,
                  0.0625F
               );
         }

         if (te.isLocked()) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5F, 0.5F, -0.5F);

            for (ChestLock lock : te.getLocks()) {
               lock.render(te, partialTicks, chestType, standing, rotateX);
            }

            GlStateManager.popMatrix();
         }
      } else {
         model.render(null, 0.0F, 0.0F, 0.0F, chestType.light, 0.0F, 0.0625F);
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
