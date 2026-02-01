package com.vivern.arpg.renders;

import com.vivern.arpg.elements.models.DisenchantmentTableCubeModel;
import com.vivern.arpg.tileentity.TileDisenchantmentTable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class DisenchantmentTableTESR extends TileEntitySpecialRenderer<TileDisenchantmentTable> {
   public static ResourceLocation TEXTURE = new ResourceLocation("arpg:textures/disenchantment_table_cube_model_tex.png");
   public static DisenchantmentTableCubeModel modelBook = new DisenchantmentTableCubeModel();

   public void render(TileDisenchantmentTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x + 0.5F, (float)y + 0.875F, (float)z + 0.5F);
      float f = te.tickCount + partialTicks;
      GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F, 0.0F);
      float f1 = te.bookRotation - te.bookRotationPrev;

      while (f1 >= (float) Math.PI) {
         f1 -= (float) (Math.PI * 2);
      }

      while (f1 < (float) -Math.PI) {
         f1 += (float) (Math.PI * 2);
      }

      float f2 = te.bookRotationPrev + f1 * partialTicks;
      GlStateManager.rotate(-f2 * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
      this.bindTexture(TEXTURE);
      float f5 = te.bookSpreadPrev + (te.bookSpread - te.bookSpreadPrev) * partialTicks;
      GlStateManager.enableCull();
      modelBook.render((Entity)null, 0.0F, 0.0F, 0.0F, f5, 0.0F, 0.0625F);
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableTexture2D();
      GlStateManager.shadeModel(7425);
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.enableCull();
      GlStateManager.depthMask(false);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x + 0.5F, (float)y + 0.72F, (float)z + 0.5F);
      float alphaa = 0.6F;
      float size = 0.4F;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
      bufferbuilder.pos(-size, 0.0, -size).color(0.0F, 0.0F, 0.0F, alphaa).endVertex();
      bufferbuilder.pos(-size, 0.0, size).color(0.0F, 0.0F, 0.0F, alphaa).endVertex();
      bufferbuilder.pos(size, 0.0, size).color(0.0F, 0.0F, 0.0F, alphaa).endVertex();
      bufferbuilder.pos(size, 0.0, -size).color(0.0F, 0.0F, 0.0F, alphaa).endVertex();
      tessellator.draw();
      GlStateManager.depthMask(true);
      GlStateManager.disableCull();
      GlStateManager.disableBlend();
      GlStateManager.shadeModel(7424);
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
   }
}
