package com.vivern.arpg.renders;

import com.vivern.arpg.elements.models.ResearchTableExpModel;
import com.vivern.arpg.elements.models.ResearchTableResModel;
import com.vivern.arpg.elements.models.ResearchTableWriModel;
import com.vivern.arpg.tileentity.TileResearchTable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class ResearchTableTESR extends TileEntitySpecialRenderer<TileResearchTable> {
   public static ResearchTableExpModel modelExploring = new ResearchTableExpModel();
   public static ResearchTableResModel modelResearch = new ResearchTableResModel();
   public static ResearchTableWriModel modelWriting = new ResearchTableWriModel();
   public static ResourceLocation texExploring = new ResourceLocation("arpg:textures/research_table_exp_model_tex.png");
   public static ResourceLocation texResearch = new ResourceLocation("arpg:textures/research_table_res_model_tex.png");
   public static ResourceLocation texWriting = new ResourceLocation("arpg:textures/research_table_wri_model_tex.png");

   public void render(TileResearchTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      int specialization = te.specialization;
      if (specialization > 0) {
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
         } else if (specialization == 1) {
            this.bindTexture(texExploring);
         } else if (specialization == 2) {
            this.bindTexture(texResearch);
         } else if (specialization == 3) {
            this.bindTexture(texWriting);
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
         GlStateManager.rotate(90.0F * te.rotation + 180.0F, 0.0F, 1.0F, 0.0F);
         if (specialization == 1) {
            modelExploring.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         } else if (specialization == 2) {
            modelResearch.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         } else if (specialization == 3) {
            modelWriting.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
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
}
