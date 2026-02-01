package com.vivern.arpg.renders;

import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.tileentity.TileEntityFrostPortal;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.GlStateManager.TexGen;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class FrostPortalTESR extends TileEntitySpecialRenderer<TileEntityFrostPortal> {
   private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("arpg:textures/frost_portal4.png");
   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("arpg:textures/frost_portal3.png");
   private static final Random RANDOM = new Random(31100L);
   private static final FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
   private static final FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
   private final FloatBuffer buffer = GLAllocation.createDirectFloatBuffer(16);

   public void render(TileEntityFrostPortal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.disableLighting();
      RANDOM.setSeed(31100L);
      GlStateManager.getFloat(2982, MODELVIEW);
      GlStateManager.getFloat(2983, PROJECTION);
      double d0 = x * x + y * y + z * z;
      double d2 = Math.sqrt(d0) + 0.01;
      int i = this.getPasses(d0);
      float f = this.getOffset();
      boolean flag = false;

      for (int j = 9; j < i; j++) {
         GlStateManager.pushMatrix();
         float f1 = 2.0F / (18 - j);
         if (j == 9) {
            this.bindTexture(END_SKY_TEXTURE);
            f1 = 1.0F;
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         if (j >= 10) {
            this.bindTexture(END_PORTAL_TEXTURE);
            flag = true;
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
         }

         if (j == 10) {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
         }

         GlStateManager.texGen(TexGen.S, 9216);
         GlStateManager.texGen(TexGen.T, 9216);
         GlStateManager.texGen(TexGen.R, 9216);
         GlStateManager.texGen(TexGen.S, 9474, this.getBuffer(1.0F, 0.0F, 0.0F, 0.0F));
         GlStateManager.texGen(TexGen.T, 9474, this.getBuffer(0.0F, 1.0F, 0.0F, 0.0F));
         GlStateManager.texGen(TexGen.R, 9474, this.getBuffer(0.0F, 0.0F, 1.0F, 0.0F));
         GlStateManager.enableTexGenCoord(TexGen.S);
         GlStateManager.enableTexGenCoord(TexGen.T);
         GlStateManager.enableTexGenCoord(TexGen.R);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.loadIdentity();
         GlStateManager.translate(0.5F, 0.5F, 0.0F);
         GlStateManager.scale(0.5F, 0.5F, 1.0F);
         float f2 = j + 1;
         double d3 = Math.sin(AnimationTimer.tick / 80.5 + f2 * 9.0) + 2.0;
         GlStateManager.translate(17.0F / f2, 0.0F, 0.0F);
         GlStateManager.rotate((f2 * f2 * 4321.0F + f2 * 9.0F) * 2.0F, 0.0F, 0.0F, 1.0F);
         if (j != 9) {
            GlStateManager.scale((4.5F - f2 / 4.0F) * d3, (4.5F - f2 / 4.0F) * d3, 1.0);
         } else {
            GlStateManager.translate(17.0F / f2, AnimationTimer.tick / (f2 * 20.0F), 0.0F);
            GlStateManager.scale((4.5F - f2 / 4.0F) / 5.0F, (4.5F - f2 / 4.0F) / 5.0F, 1.0F);
         }

         GlStateManager.multMatrix(MODELVIEW);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         bufferbuilder.pos(x, y, z + 1.0).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x + 1.0, y, z + 1.0).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x + 1.0, y + 1.0, z + 1.0).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x, y + 1.0, z + 1.0).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x, y + 1.0, z).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x + 1.0, y + 1.0, z).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x + 1.0, y, z).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x, y, z).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x + 1.0, y + 1.0, z).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x + 1.0, y + 1.0, z + 1.0).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x + 1.0, y, z + 1.0).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x + 1.0, y, z).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x, y, z).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x, y, z + 1.0).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x, y + 1.0, z + 1.0).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x, y + 1.0, z).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x, y, z).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x + 1.0, y, z).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x + 1.0, y, z + 1.0).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x, y, z + 1.0).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x, y + f, z + 1.0).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x + 1.0, y + f, z + 1.0).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x + 1.0, y + f, z).color(f1, f1, f1, 1.0F).endVertex();
         bufferbuilder.pos(x, y + f, z).color(f1, f1, f1, 1.0F).endVertex();
         tessellator.draw();
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
         this.bindTexture(END_SKY_TEXTURE);
      }

      GlStateManager.disableBlend();
      GlStateManager.disableTexGenCoord(TexGen.S);
      GlStateManager.disableTexGenCoord(TexGen.T);
      GlStateManager.disableTexGenCoord(TexGen.R);
      GlStateManager.enableLighting();
      if (flag) {
         Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
      }
   }

   protected int getPasses(double p_191286_1_) {
      int i;
      if (p_191286_1_ > 36864.0) {
         i = 1;
      } else if (p_191286_1_ > 25600.0) {
         i = 3;
      } else if (p_191286_1_ > 16384.0) {
         i = 5;
      } else if (p_191286_1_ > 9216.0) {
         i = 7;
      } else if (p_191286_1_ > 4096.0) {
         i = 9;
      } else if (p_191286_1_ > 1024.0) {
         i = 11;
      } else if (p_191286_1_ > 576.0) {
         i = 13;
      } else if (p_191286_1_ > 256.0) {
         i = 14;
      } else {
         i = 15;
      }

      return i;
   }

   protected float getOffset() {
      return 1.0F;
   }

   private FloatBuffer getBuffer(float p_147525_1_, float p_147525_2_, float p_147525_3_, float p_147525_4_) {
      ((Buffer)this.buffer).clear();
      this.buffer.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
      ((Buffer)this.buffer).flip();
      return this.buffer;
   }
}
