package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.container.GUIBookOfElements;
import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.elements.models.GlossaryModel;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsElements;
import com.Vivern.Arpg.tileentity.TileGlossary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class GlossaryTESR extends TileEntitySpecialRenderer<TileGlossary> {
   public static GlossaryModel model = new GlossaryModel();
   public static ResourceLocation texture = new ResourceLocation("arpg:textures/glossary_model_tex.png");

   public void render(TileGlossary te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.enableCull();
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
         this.bindTexture(texture);
      }

      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      float colorR = 0.85F;
      float colorG = 0.6F;
      float colorB = 1.0F;
      if (te != null) {
         GlStateManager.rotate(90 * te.rotation + 180, 0.0F, 1.0F, 0.0F);
         model.render(null, 0.0F, te.animationRunes / 10.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         double tick = AnimationTimer.tick / 70.0
            + te.getPos().getX()
            + te.getPos().getY()
            + te.getPos().getZ();
         float levitation1 = (float)Math.sin(tick) * 0.03F + 0.03F;
         float levitation2 = (float)Math.cos(tick) * 0.03F + 0.03F;
         GlStateManager.translate(0.0F, -levitation1, -levitation2 / 2.0F);
         model.render(
            null,
            GetMOP.partial((float)te.animationPapers, (float)te.prevanimationPapers, partialTicks) / TileGlossary.animationPapersMax,
            0.0F,
            1.0F,
            levitation2,
            0.0F,
            0.0625F
         );
      } else {
         model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         model.render(null, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0625F);
      }

      if (te != null && te.clientPackDisplayed != null && te.animationPapers == te.animationPapersTarget) {
         AbstractMobModel.light(200, true);
         GlStateManager.pushMatrix();
         GlStateManager.translate(Debugger.floats[0] - 0.27F, Debugger.floats[1] + 0.26F, Debugger.floats[2] - 0.08F);
         float size = 0.0065F + Debugger.floats[3];
         GlStateManager.scale(size, size, size);
         GlStateManager.rotate(-30.0F, 1.0F, 0.0F, 0.0F);
         int howmuch = te.clientPackDisplayed.howMuchNotNull();
         int howmuchHeight = (howmuch - 1) / 4;
         int i = Math.max(48 - 12 * howmuch, 0);
         int j = 24 - howmuchHeight * 12;
         this.renderElementsIcons(i, j, te.clientPackDisplayed);
         ItemsElements.EnumPurity purity = te.clientPackDisplayed.getPurity();
         String srtPurity = purity.getDisplayName();
         int swidth2 = Minecraft.getMinecraft().fontRenderer.getStringWidth(srtPurity);
         if (purity == ItemsElements.EnumPurity.IMPURITY) {
            AbstractMobModel.returnlight();
         }

         Minecraft.getMinecraft().fontRenderer.drawString(srtPurity, 42 - swidth2 / 2, -12, purity.color);
         if (purity != ItemsElements.EnumPurity.IMPURITY) {
            AbstractMobModel.returnlight();
         }

         this.renderElementsNumbers(i, j, te.clientPackDisplayed);
         GlStateManager.popMatrix();
      }

      GlStateManager.disableCull();
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

   public void renderElementsIcons(int i, int j, ItemsElements.ElementsPack elementsPack) {
      int oneLength = 12;
      int space = 12;
      int horizontalIcons = 4;
      int oneLengthAndSpace = oneLength + space;
      int currentRendered = 0;

      for (int s = 1; s < 13; s++) {
         float amount = elementsPack.elementsAmount[s - 1];
         if (amount > 0.0F) {
            int iadd = oneLengthAndSpace * (currentRendered % horizontalIcons);
            int jadd = oneLengthAndSpace * (currentRendered / horizontalIcons);
            this.bindTexture(GUIBookOfElements.EnergyRunes);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Gui.drawModalRectWithCustomSizedTexture(i + iadd, j + jadd, 0.0F, s * 12, 12, 12, 12.0F, 156.0F);
            currentRendered++;
         }
      }
   }

   public void renderElementsNumbers(int i, int j, ItemsElements.ElementsPack elementsPack) {
      int oneLength = 12;
      int space = 12;
      int horizontalIcons = 4;
      int oneLengthAndSpace = oneLength + space;
      int currentRendered = 0;

      for (int s = 1; s < 13; s++) {
         float amount = elementsPack.elementsAmount[s - 1];
         if (amount > 0.0F) {
            int iadd = oneLengthAndSpace * (currentRendered % horizontalIcons);
            int jadd = oneLengthAndSpace * (currentRendered / horizontalIcons);
            String str = ManaBar.asString(amount);
            int swidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(str);
            Minecraft.getMinecraft().fontRenderer.drawString(str, i + iadd + (oneLength - swidth) / 2, j + jadd + oneLength + 1, 3026737);
            currentRendered++;
         }
      }
   }
}
