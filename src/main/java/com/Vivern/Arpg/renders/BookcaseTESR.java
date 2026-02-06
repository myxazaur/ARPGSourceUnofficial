package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.BooksModel;
import com.Vivern.Arpg.tileentity.TileBookcase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class BookcaseTESR extends TileEntitySpecialRenderer<TileBookcase> {
   public static BooksModel model = new BooksModel();
   public static ResourceLocation[] textures = new ResourceLocation[]{
      new ResourceLocation("arpg:textures/books_diamond_tex.png"),
      new ResourceLocation("arpg:textures/books_ruby_tex.png"),
      new ResourceLocation("arpg:textures/books_sapphire_tex.png"),
      new ResourceLocation("arpg:textures/books_emerald_tex.png"),
      new ResourceLocation("arpg:textures/books_citrine_tex.png"),
      new ResourceLocation("arpg:textures/books_amethyst_tex.png"),
      new ResourceLocation("arpg:textures/books_topaz_tex.png"),
      new ResourceLocation("arpg:textures/books_rhinestone_tex.png")
   };

   public void render(TileBookcase te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
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
      }

      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      GlStateManager.rotate(90 * te.rotation + 270, 0.0F, 1.0F, 0.0F);

      for (int i = 0; i < 3; i++) {
         if (te.booksGems[i] < 8) {
            this.bindTexture(textures[te.booksGems[i]]);
            model.render(null, te.booksVariant, i + 1, 0.0F, 0.0F, 0.0F, 0.0625F);
         }
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
