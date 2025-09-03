//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.tileentity.TileSplitter;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class SplitterTESR extends TileEntitySpecialRenderer<TileSplitter> {
   public float[] getColor(float timestate, TileSplitter te) {
      if (timestate >= 0.0F && timestate < 0.25F) {
         float closeness = timestate * 4.0F;
         float uncloseness = 1.0F - closeness;
         return new float[]{
            te.color4[0] * uncloseness + te.color1[0] * closeness,
            te.color4[1] * uncloseness + te.color1[1] * closeness,
            te.color4[2] * uncloseness + te.color1[2] * closeness
         };
      } else if (timestate >= 0.25F && timestate < 0.5F) {
         float closeness = (timestate - 0.25F) * 4.0F;
         float uncloseness = 1.0F - closeness;
         return new float[]{
            te.color1[0] * uncloseness + te.color2[0] * closeness,
            te.color1[1] * uncloseness + te.color2[1] * closeness,
            te.color1[2] * uncloseness + te.color2[2] * closeness
         };
      } else if (timestate >= 0.5F && timestate < 0.75F) {
         float closeness = (timestate - 0.5F) * 4.0F;
         float uncloseness = 1.0F - closeness;
         return new float[]{
            te.color2[0] * uncloseness + te.color3[0] * closeness,
            te.color2[1] * uncloseness + te.color3[1] * closeness,
            te.color2[2] * uncloseness + te.color3[2] * closeness
         };
      } else if (timestate >= 0.75F && timestate < 1.0F) {
         float closeness = (timestate - 0.75F) * 4.0F;
         float uncloseness = 1.0F - closeness;
         return new float[]{
            te.color3[0] * uncloseness + te.color4[0] * closeness,
            te.color3[1] * uncloseness + te.color4[1] * closeness,
            te.color3[2] * uncloseness + te.color4[2] * closeness
         };
      } else {
         return new float[]{0.0F, 0.0F, 0.0F};
      }
   }

   public void render(TileSplitter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      AbstractMobModel.light(240, false);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableTexture2D();
      GlStateManager.shadeModel(7425);
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.enableCull();
      GlStateManager.depthMask(false);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x + 0.5F, (float)y + 0.349F + 0.6F * Math.min(1.0F, te.dissolvedElementsSumm / 192.0F), (float)z + 0.5F);
      float alphaa = 0.3F + Debugger.floats[3];
      float size = 0.31F;
      int timescale = 200;
      int timeoffset2 = timescale / 4;
      int timeoffset3 = timeoffset2 * 2;
      int timeoffset4 = timeoffset2 * 3;
      float timestate1 = (te.ticksExisted % timescale + partialTicks) / timescale;
      float timestate2 = ((te.ticksExisted + timeoffset2) % timescale + partialTicks) / timescale;
      float timestate3 = ((te.ticksExisted + timeoffset3) % timescale + partialTicks) / timescale;
      float timestate4 = ((te.ticksExisted + timeoffset4) % timescale + partialTicks) / timescale;
      float[] color1 = this.getColor(timestate1, te);
      float[] color2 = this.getColor(timestate2, te);
      float[] color3 = this.getColor(timestate3, te);
      float[] color4 = this.getColor(timestate4, te);

      for (int i = 0; i < 3; i++) {
         if (i == 2) {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         }

         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         float yy = 0.025F * i;
         bufferbuilder.pos(-size, yy, -size).color(color1[0], color1[1], color1[2], alphaa).endVertex();
         bufferbuilder.pos(-size, yy, size).color(color2[0], color2[1], color2[2], alphaa).endVertex();
         bufferbuilder.pos(size, yy, size).color(color3[0], color3[1], color3[2], alphaa).endVertex();
         bufferbuilder.pos(size, yy, -size).color(color4[0], color4[1], color4[2], alphaa).endVertex();
         tessellator.draw();
      }

      AbstractMobModel.returnlight();
      GlStateManager.depthMask(true);
      GlStateManager.disableCull();
      GlStateManager.disableBlend();
      GlStateManager.shadeModel(7424);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
   }
}
