package com.vivern.arpg.renders;

import com.vivern.arpg.tileentity.TileAlchemicLab;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class AlchemicLabTESR extends TileEntitySpecialRenderer<TileAlchemicLab> {
   public void render(TileAlchemicLab te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      boolean t1nonull = te.tank1.getFluid() != null;
      boolean t2nonull = te.tank2.getFluid() != null;
      if (t1nonull || t2nonull) {
         GlStateManager.enableDepth();
         GlStateManager.depthFunc(515);
         GlStateManager.depthMask(true);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.disableCull();
         GlStateManager.enableBlend();
         this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      }

      if (t1nonull) {
         float progr = (float)te.tank1.getFluid().amount / TileAlchemicLab.fluidMax * 0.74F;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x + 0.13F, (float)y, (float)z + 0.13F);
         RenderFluidHelper.drawFluidCube(0.74F, progr, progr / 0.74F, te.tank1.getFluid().getFluid(), te.tank1.getFluid());
         GlStateManager.popMatrix();
      }

      if (t2nonull) {
         float progr = (float)te.tank2.getFluid().amount / TileAlchemicLab.fluidMax * 0.52F;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x + 0.24F, (float)y + 0.9375F, (float)z + 0.24F);
         this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         RenderFluidHelper.drawFluidCube(0.52F, progr, progr / 0.52F, te.tank2.getFluid().getFluid(), te.tank2.getFluid());
         GlStateManager.popMatrix();
      }

      if (t1nonull || t2nonull) {
         GlStateManager.disableBlend();
         GlStateManager.enableCull();
      }
   }
}
