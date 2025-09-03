//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.ModelSphere;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.tileentity.TileAquaticaPortal;
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

public class AquaticaPortalTESR extends TileEntitySpecialRenderer<TileAquaticaPortal> {
   private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("arpg:textures/aquatica_portal_1.png");
   private static final ResourceLocation PARTICLES_TEXTURE = new ResourceLocation("arpg:textures/aquatica_portal_1.png");
   private static final Random RANDOM = new Random(31100L);
   private static FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
   private static FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
   private final FloatBuffer buffer = GLAllocation.createDirectFloatBuffer(16);
   public static ModelSphere sphere = new ModelSphere(1.0F, 8);

   public void render(TileAquaticaPortal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.disableLighting();
      RANDOM.setSeed(31100L);
      GlStateManager.getFloat(2982, MODELVIEW);
      GlStateManager.getFloat(2983, PROJECTION);
      double distToCameraSq = x * x + y * y + z * z;
      double d2 = Math.sqrt(distToCameraSq) + 0.01;
      int i = this.getPasses(distToCameraSq);
      float f = this.getOffset();
      boolean flag = false;

      for (int j = 9; j < i; j++) {
         GlStateManager.pushMatrix();
         float f1 = 2.0F / (18 - j);
         if (j == 9) {
            this.bindTexture(BACKGROUND_TEXTURE);
            f1 = 1.0F;
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         if (j >= 10) {
            this.bindTexture(PARTICLES_TEXTURE);
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
         GlStateManager.rotate((f2 * f2 * 4321.0F + f2 * 9.0F) * 2.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(AnimationTimer.tick * (-1.0F + (j - 9) * -0.3F), 0.0F, 0.0F, 1.0F);
         float scale = 3.1F;
         GlStateManager.scale(scale - f2 / 4.0F, scale - f2 / 4.0F, 1.0F);
         GlStateManager.multMatrix(PROJECTION);
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
         this.bindTexture(BACKGROUND_TEXTURE);
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

   protected int getPasses(double distToCameraSq) {
      int i;
      if (distToCameraSq > 36864.0) {
         i = 1;
      } else if (distToCameraSq > 25600.0) {
         i = 3;
      } else if (distToCameraSq > 16384.0) {
         i = 5;
      } else if (distToCameraSq > 9216.0) {
         i = 7;
      } else if (distToCameraSq > 4096.0) {
         i = 9;
      } else if (distToCameraSq > 1024.0) {
         i = 11;
      } else if (distToCameraSq > 576.0) {
         i = 13;
      } else if (distToCameraSq > 256.0) {
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
