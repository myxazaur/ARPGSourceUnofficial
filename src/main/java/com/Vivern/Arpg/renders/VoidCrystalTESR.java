//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.VoidCrystalModel;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.tileentity.TileVoidCrystal;
import java.util.Random;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class VoidCrystalTESR extends TileEntitySpecialRenderer<TileVoidCrystal> {
   public static VoidCrystalModel model = new VoidCrystalModel();
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/void_crystal_model_tex.png");

   public void render(TileVoidCrystal te, double xx, double yy, double zz, float partialTicks, int destroyStage, float alpha) {
      float distSq = (float)(xx * xx + yy * yy + zz * zz);
      float ftAllsize = GetMOP.getfromto(4030.0F - distSq, 0.0F, 1453.0F);
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.disableCull();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      this.bindTexture(tex);
      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      GlStateManager.translate((float)xx + 0.5F, (float)yy + 1.5F, (float)zz + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 1.0F, 0.0F);
      GlStateManager.scale(ftAllsize, ftAllsize, ftAllsize);
      GlStateManager.translate(0.0F, -1.0F, 0.0F);
      model.render(null, te == null ? 0.0F : te.renderseed, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.popMatrix();
      GlStateManager.enableCull();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (te != null) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(xx, yy, zz);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         RenderHelper.disableStandardItemLighting();
         float f = AnimationTimer.tick / 1000.0F;
         float fcount = 0.5F;
         float f1 = 0.0F;
         f1 = (fcount - 0.8F) / 0.2F;
         Random random = new Random(te.renderseed);
         GlStateManager.disableTexture2D();
         GlStateManager.shadeModel(7425);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         GlStateManager.disableAlpha();
         GlStateManager.enableCull();
         GlStateManager.depthMask(false);
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.5F, 0.5F, 0.5F);
         float size = (0.3F + random.nextFloat() * 0.2F) * ftAllsize;
         float size2 = (1.9F + random.nextFloat() * 0.2F) * ftAllsize;

         for (int i = 0; i < (fcount + fcount * fcount) / 2.0F * 60.0F; i++) {
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
            float f2 = (random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F) * 0.5F * size;
            float f3 = (random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F) * 0.5F * size2;
            bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(0.0, 0.0, 0.0).color(247, 114, 255, (int)(255.0F * (1.0F - f1))).endVertex();
            bufferbuilder.pos(-0.866 * f3, f2, -0.5F * f3).color(100, 0, 220, 0).endVertex();
            bufferbuilder.pos(0.866 * f3, f2, -0.5F * f3).color(100, 0, 220, 0).endVertex();
            bufferbuilder.pos(0.0, f2, 1.0F * f3).color(100, 0, 220, 0).endVertex();
            bufferbuilder.pos(-0.866 * f3, f2, -0.5F * f3).color(100, 0, 220, 0).endVertex();
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
}
