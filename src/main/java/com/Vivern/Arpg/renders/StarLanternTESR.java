package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.tileentity.TileStarLantern;
import java.util.Random;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class StarLanternTESR extends TileEntitySpecialRenderer<TileStarLantern> {
   public void render(TileStarLantern te, double xx, double yy, double zz, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(xx, yy, zz);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      RenderHelper.disableStandardItemLighting();
      float f = AnimationTimer.tick / 700.0F;
      float fcount = 0.5F;
      float f1 = 0.0F;
      f1 = (fcount - 0.8F) / 0.2F;
      Random random = new Random(te.lightSeed);
      GlStateManager.disableTexture2D();
      GlStateManager.shadeModel(7425);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      GlStateManager.disableAlpha();
      GlStateManager.enableCull();
      GlStateManager.depthMask(false);
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.5F, 0.5F, 0.5F);

      for (int i = 0; i < (fcount + fcount * fcount) / 2.0F * 60.0F; i++) {
         GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
         float f2 = (random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F) * 0.5F;
         float f3 = (random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F) * 0.5F;
         bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
         bufferbuilder.pos(0.0, 0.0, 0.0).color(255, 255, 255, (int)(255.0F * (1.0F - f1))).endVertex();
         bufferbuilder.pos(-0.866 * f3, f2, -0.5F * f3).color(te.red, te.green, te.blue, 0.0F).endVertex();
         bufferbuilder.pos(0.866 * f3, f2, -0.5F * f3).color(te.red, te.green, te.blue, 0.0F).endVertex();
         bufferbuilder.pos(0.0, f2, 1.0F * f3).color(te.red, te.green, te.blue, 0.0F).endVertex();
         bufferbuilder.pos(-0.866 * f3, f2, -0.5F * f3).color(te.red, te.green, te.blue, 0.0F).endVertex();
         tessellator.draw();
      }

      GlStateManager.popMatrix();
      GlStateManager.depthMask(true);
      GlStateManager.disableCull();
      GlStateManager.disableBlend();
      GlStateManager.shadeModel(7424);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
   }
}
