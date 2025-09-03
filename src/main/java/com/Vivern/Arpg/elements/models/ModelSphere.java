//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class ModelSphere {
   public Vec3d[] firstPoints;
   public Vec3d[] secndPoints;
   public float displAngle;
   public int polyCount;
   public float radius;
   public int renderlist;
   public boolean useRenderList = false;

   public ModelSphere(float radius, int polyCount) {
      this.displAngle = (float)(Math.PI / polyCount);
      this.firstPoints = new Vec3d[polyCount + 1];
      this.secndPoints = new Vec3d[polyCount + 1];
      this.firstPoints[0] = new Vec3d(0.0, radius, 0.0);
      Vec3d axis1 = new Vec3d(0.0, 0.0, 1.0);
      Vec3d axis2 = new Vec3d(0.0, 1.0, 0.0);

      for (int i = 0; i < polyCount; i++) {
         this.firstPoints[i + 1] = GetMOP.rotateVecAroundAxis(this.firstPoints[i], axis1, this.displAngle);
      }

      for (int i = 0; i < polyCount + 1; i++) {
         this.secndPoints[i] = GetMOP.rotateVecAroundAxis(this.firstPoints[i], axis2, this.displAngle);
      }

      this.radius = radius;
      this.polyCount = polyCount;
   }

   public void render() {
      GlStateManager.pushMatrix();
      if (this.useRenderList) {
         GlStateManager.callList(this.renderlist);
      } else {
         this.renderlist = GLAllocation.generateDisplayLists(1);
         GlStateManager.glNewList(this.renderlist, 4864);

         for (int i = 0; i < this.polyCount * 2; i++) {
            GlStateManager.rotate(this.displAngle * (float) (180.0 / Math.PI), 0.0F, 1.0F, 0.0F);

            for (int h = 0; h < this.polyCount; h++) {
               Tessellator tes = Tessellator.getInstance();
               BufferBuilder bufferbuilder = tes.getBuffer();
               bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
               Vec3d vecA = this.firstPoints[h + 1];
               Vec3d vecB = this.firstPoints[h];
               Vec3d vecC = this.secndPoints[h];
               Vec3d vecD = this.secndPoints[h + 1];
               bufferbuilder.pos(-vecA.x, -vecA.y, -vecA.z).tex(0.0, 1.0).endVertex();
               bufferbuilder.pos(-vecB.x, -vecB.y, -vecB.z).tex(0.0, 0.0).endVertex();
               bufferbuilder.pos(-vecC.x, -vecC.y, -vecC.z).tex(1.0, 0.0).endVertex();
               bufferbuilder.pos(-vecD.x, -vecD.y, -vecD.z).tex(1.0, 1.0).endVertex();
               tes.draw();
            }
         }

         GlStateManager.glEndList();
         this.useRenderList = true;
      }

      GlStateManager.popMatrix();
   }

   public void renderWithLight(float shadowR, float shadowG, float shadowB) {
      GlStateManager.pushMatrix();
      GlStateManager.disableLighting();
      GL11.glShadeModel(7425);
      GL11.glEnable(3042);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      if (this.useRenderList) {
         GlStateManager.callList(this.renderlist);
      } else {
         this.renderlist = GLAllocation.generateDisplayLists(1);
         GlStateManager.glNewList(this.renderlist, 4864);

         for (int i = 0; i < this.polyCount * 2; i++) {
            GlStateManager.rotate(this.displAngle * (float) (180.0 / Math.PI), 0.0F, 1.0F, 0.0F);
            float lastColor = 1.0F;

            for (int h = 0; h < this.polyCount; h++) {
               Tessellator tes = Tessellator.getInstance();
               BufferBuilder bufferbuilder = tes.getBuffer();
               bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
               Vec3d vecA = this.firstPoints[h + 1];
               Vec3d vecB = this.firstPoints[h];
               Vec3d vecC = this.secndPoints[h];
               Vec3d vecD = this.secndPoints[h + 1];
               float colB = (float)Math.pow(
                  1.0 - GetMOP.getAngleBetweenVectors(0.0, -3.0, 0.0, vecA.x, vecA.y, vecA.z) / 250.0, 2.0
               );
               float colA = (float)Math.pow(
                  1.0 - GetMOP.getAngleBetweenVectors(0.0, -3.0, 0.0, vecB.x, vecB.y, vecB.z) / 250.0, 2.0
               );
               bufferbuilder.pos(-vecA.x, -vecA.y, -vecA.z)
                  .tex(0.0, 1.0)
                  .color(
                     MathHelper.clamp(colB * shadowR, 0.0F, 1.0F),
                     MathHelper.clamp(colB * shadowG, 0.0F, 1.0F),
                     MathHelper.clamp(colB * shadowB, 0.0F, 1.0F),
                     1.0F
                  )
                  .endVertex();
               bufferbuilder.pos(-vecB.x, -vecB.y, -vecB.z)
                  .tex(0.0, 0.0)
                  .color(
                     MathHelper.clamp(colA * shadowR, 0.0F, 1.0F),
                     MathHelper.clamp(colA * shadowG, 0.0F, 1.0F),
                     MathHelper.clamp(colA * shadowB, 0.0F, 1.0F),
                     1.0F
                  )
                  .endVertex();
               bufferbuilder.pos(-vecC.x, -vecC.y, -vecC.z)
                  .tex(1.0, 0.0)
                  .color(
                     MathHelper.clamp(colA * shadowR, 0.0F, 1.0F),
                     MathHelper.clamp(colA * shadowG, 0.0F, 1.0F),
                     MathHelper.clamp(colA * shadowB, 0.0F, 1.0F),
                     1.0F
                  )
                  .endVertex();
               bufferbuilder.pos(-vecD.x, -vecD.y, -vecD.z)
                  .tex(1.0, 1.0)
                  .color(
                     MathHelper.clamp(colB * shadowR, 0.0F, 1.0F),
                     MathHelper.clamp(colB * shadowG, 0.0F, 1.0F),
                     MathHelper.clamp(colB * shadowB, 0.0F, 1.0F),
                     1.0F
                  )
                  .endVertex();
               tes.draw();
            }
         }

         GlStateManager.glEndList();
         this.useRenderList = true;
      }

      GlStateManager.enableLighting();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   public void renderAnimated(float framecount, float time) {
      GlStateManager.pushMatrix();

      for (int i = 0; i < this.polyCount * 2; i++) {
         GlStateManager.rotate(this.displAngle * (float) (180.0 / Math.PI), 0.0F, 1.0F, 0.0F);

         for (int h = 0; h < this.polyCount; h++) {
            Tessellator tes = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tes.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            Vec3d vecA = this.firstPoints[h + 1];
            Vec3d vecB = this.firstPoints[h];
            Vec3d vecC = this.secndPoints[h];
            Vec3d vecD = this.secndPoints[h + 1];
            bufferbuilder.pos(vecA.x, vecA.y, vecA.z)
               .tex(0.0, 1.0F / framecount + time / framecount)
               .endVertex();
            bufferbuilder.pos(vecB.x, vecB.y, vecB.z)
               .tex(0.0, 0.0F + time / framecount)
               .endVertex();
            bufferbuilder.pos(vecC.x, vecC.y, vecC.z)
               .tex(1.0, 0.0F + time / framecount)
               .endVertex();
            bufferbuilder.pos(vecD.x, vecD.y, vecD.z)
               .tex(1.0, 1.0F / framecount + time / framecount)
               .endVertex();
            tes.draw();
         }
      }

      GlStateManager.popMatrix();
   }

   public void renderScaledtexture() {
      GlStateManager.pushMatrix();
      if (this.useRenderList) {
         GlStateManager.callList(this.renderlist);
      } else {
         this.renderlist = GLAllocation.generateDisplayLists(1);
         GlStateManager.glNewList(this.renderlist, 4864);
         float polyCount2 = this.polyCount * 2;
         float texXunit = 1.0F / polyCount2;
         float texYunit = 1.0F / this.polyCount;

         for (int i = 0; i < polyCount2; i++) {
            float texXcoord = i / polyCount2;
            GlStateManager.rotate(this.displAngle * (float) (180.0 / Math.PI), 0.0F, 1.0F, 0.0F);

            for (int h = 0; h < this.polyCount; h++) {
               float texYcoord = (float)h / this.polyCount;
               Tessellator tes = Tessellator.getInstance();
               BufferBuilder bufferbuilder = tes.getBuffer();
               bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
               Vec3d vecA = this.firstPoints[h + 1];
               Vec3d vecB = this.firstPoints[h];
               Vec3d vecC = this.secndPoints[h];
               Vec3d vecD = this.secndPoints[h + 1];
               bufferbuilder.pos(-vecA.x, -vecA.y, -vecA.z)
                  .tex(texYcoord + texYunit, texXcoord)
                  .endVertex();
               bufferbuilder.pos(-vecB.x, -vecB.y, -vecB.z).tex(texYcoord, texXcoord).endVertex();
               bufferbuilder.pos(-vecC.x, -vecC.y, -vecC.z)
                  .tex(texYcoord, texXcoord + texXunit)
                  .endVertex();
               bufferbuilder.pos(-vecD.x, -vecD.y, -vecD.z)
                  .tex(texYcoord + texYunit, texXcoord + texXunit)
                  .endVertex();
               tes.draw();
            }
         }

         GlStateManager.glEndList();
         this.useRenderList = true;
      }

      GlStateManager.popMatrix();
   }

   public void renderScaledtextureAnimated(float framecount, float time) {
      GlStateManager.pushMatrix();
      float polyCount2 = this.polyCount * 2;
      float texXunit = 1.0F / polyCount2;
      float texYunit = 1.0F / framecount / this.polyCount;

      for (int i = 0; i < polyCount2; i++) {
         float texXcoord = texXunit * i;
         GlStateManager.rotate(this.displAngle * (float) (180.0 / Math.PI), 0.0F, 1.0F, 0.0F);

         for (int h = 0; h < this.polyCount; h++) {
            float texYcoord = texYunit * h + (int)time % (int)framecount / framecount;
            Tessellator tes = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tes.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            Vec3d vecA = this.firstPoints[h + 1];
            Vec3d vecB = this.firstPoints[h];
            Vec3d vecC = this.secndPoints[h];
            Vec3d vecD = this.secndPoints[h + 1];
            bufferbuilder.pos(-vecA.x, -vecA.y, -vecA.z).tex(texXcoord, texYcoord).endVertex();
            bufferbuilder.pos(-vecB.x, -vecB.y, -vecB.z)
               .tex(texXcoord, texYcoord + texYunit)
               .endVertex();
            bufferbuilder.pos(-vecC.x, -vecC.y, -vecC.z)
               .tex(texXcoord + texXunit, texYcoord + texYunit)
               .endVertex();
            bufferbuilder.pos(-vecD.x, -vecD.y, -vecD.z)
               .tex(texXcoord + texXunit, texYcoord)
               .endVertex();
            tes.draw();
         }
      }

      GlStateManager.popMatrix();
   }

   public void renderWithGradient(boolean canUseRendList, int arrayStopPolygon, Vec3d... colors) {
      GlStateManager.pushMatrix();
      GlStateManager.disableLighting();
      GL11.glShadeModel(7425);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      if (this.useRenderList && canUseRendList) {
         GlStateManager.callList(this.renderlist);
      } else {
         if (canUseRendList) {
            this.renderlist = GLAllocation.generateDisplayLists(1);
            GlStateManager.glNewList(this.renderlist, 4864);
         }

         for (int i = 0; i < this.polyCount * 2; i++) {
            GlStateManager.rotate(this.displAngle * (float) (180.0 / Math.PI), 0.0F, 1.0F, 0.0F);
            float lastColor = 1.0F;

            for (int h = 0; h < this.polyCount; h++) {
               Tessellator tes = Tessellator.getInstance();
               BufferBuilder bufferbuilder = tes.getBuffer();
               bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
               Vec3d vecA = this.firstPoints[h + 1];
               Vec3d vecB = this.firstPoints[h];
               Vec3d vecC = this.secndPoints[h];
               Vec3d vecD = this.secndPoints[h + 1];
               int plh = Math.min(h, arrayStopPolygon);
               int plh1 = Math.min(h + 1, arrayStopPolygon);
               bufferbuilder.pos(-vecA.x, -vecA.y, -vecA.z)
                  .tex(0.0, 1.0)
                  .color(
                     MathHelper.clamp((float)colors[plh1].x, 0.0F, 1.0F),
                     MathHelper.clamp((float)colors[plh1].y, 0.0F, 1.0F),
                     MathHelper.clamp((float)colors[plh1].z, 0.0F, 1.0F),
                     1.0F
                  )
                  .endVertex();
               bufferbuilder.pos(-vecB.x, -vecB.y, -vecB.z)
                  .tex(0.0, 0.0)
                  .color(
                     MathHelper.clamp((float)colors[plh].x, 0.0F, 1.0F),
                     MathHelper.clamp((float)colors[plh].y, 0.0F, 1.0F),
                     MathHelper.clamp((float)colors[plh].z, 0.0F, 1.0F),
                     1.0F
                  )
                  .endVertex();
               bufferbuilder.pos(-vecC.x, -vecC.y, -vecC.z)
                  .tex(1.0, 0.0)
                  .color(
                     MathHelper.clamp((float)colors[plh].x, 0.0F, 1.0F),
                     MathHelper.clamp((float)colors[plh].y, 0.0F, 1.0F),
                     MathHelper.clamp((float)colors[plh].z, 0.0F, 1.0F),
                     1.0F
                  )
                  .endVertex();
               bufferbuilder.pos(-vecD.x, -vecD.y, -vecD.z)
                  .tex(1.0, 1.0)
                  .color(
                     MathHelper.clamp((float)colors[plh1].x, 0.0F, 1.0F),
                     MathHelper.clamp((float)colors[plh1].y, 0.0F, 1.0F),
                     MathHelper.clamp((float)colors[plh1].z, 0.0F, 1.0F),
                     1.0F
                  )
                  .endVertex();
               tes.draw();
            }
         }

         if (canUseRendList) {
            GlStateManager.glEndList();
            this.useRenderList = true;
         }
      }

      GlStateManager.enableLighting();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   public void renderWithGradient2(boolean canUseRendList, Vec3d... colors) {
      GlStateManager.pushMatrix();
      GlStateManager.disableLighting();
      GL11.glShadeModel(7425);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      float one = 1.0F / this.polyCount;
      if (this.useRenderList && canUseRendList) {
         GlStateManager.callList(this.renderlist);
      } else {
         if (canUseRendList) {
            this.renderlist = GLAllocation.generateDisplayLists(1);
            GlStateManager.glNewList(this.renderlist, 4864);
         }

         for (int i = 0; i < this.polyCount * 2; i++) {
            GlStateManager.rotate(this.displAngle * (float) (180.0 / Math.PI), 0.0F, 1.0F, 0.0F);
            float lastColor = 1.0F;

            for (int h = 0; h < this.polyCount; h++) {
               Tessellator tes = Tessellator.getInstance();
               BufferBuilder bufferbuilder = tes.getBuffer();
               bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
               Vec3d vecA = this.firstPoints[h + 1];
               Vec3d vecB = this.firstPoints[h];
               Vec3d vecC = this.secndPoints[h];
               Vec3d vecD = this.secndPoints[h + 1];
               float polyPosition1 = h * one;
               float polyPosition2 = (h + 1) * one;
               float colorPosition1 = polyPosition1 * (colors.length - 1);
               float colorPosition2 = polyPosition2 * (colors.length - 1);
               int colorFirstIndex1 = MathHelper.floor(colorPosition1);
               int colorSecndIndex1 = MathHelper.ceil(colorPosition1);
               float colorTranslation1 = colorPosition1 - colorFirstIndex1;
               float colorUnTranslation1 = 1.0F - colorTranslation1;
               Vec3d color1 = new Vec3d(
                  colors[colorFirstIndex1].x * colorUnTranslation1 + colors[colorSecndIndex1].x * colorTranslation1,
                  colors[colorFirstIndex1].y * colorUnTranslation1 + colors[colorSecndIndex1].y * colorTranslation1,
                  colors[colorFirstIndex1].z * colorUnTranslation1 + colors[colorSecndIndex1].z * colorTranslation1
               );
               int colorFirstIndex2 = MathHelper.floor(colorPosition2);
               int colorSecndIndex2 = MathHelper.ceil(colorPosition2);
               float colorTranslation2 = colorPosition2 - colorFirstIndex2;
               float colorUnTranslation2 = 1.0F - colorTranslation2;
               Vec3d color2 = new Vec3d(
                  colors[colorFirstIndex2].x * colorUnTranslation2 + colors[colorSecndIndex2].x * colorTranslation2,
                  colors[colorFirstIndex2].y * colorUnTranslation2 + colors[colorSecndIndex2].y * colorTranslation2,
                  colors[colorFirstIndex2].z * colorUnTranslation2 + colors[colorSecndIndex2].z * colorTranslation2
               );
               bufferbuilder.pos(-vecA.x, -vecA.y, -vecA.z)
                  .tex(0.0, 1.0)
                  .color((float)color2.x, (float)color2.y, (float)color2.z, 1.0F)
                  .endVertex();
               bufferbuilder.pos(-vecB.x, -vecB.y, -vecB.z)
                  .tex(0.0, 0.0)
                  .color((float)color1.x, (float)color1.y, (float)color1.z, 1.0F)
                  .endVertex();
               bufferbuilder.pos(-vecC.x, -vecC.y, -vecC.z)
                  .tex(1.0, 0.0)
                  .color((float)color1.x, (float)color1.y, (float)color1.z, 1.0F)
                  .endVertex();
               bufferbuilder.pos(-vecD.x, -vecD.y, -vecD.z)
                  .tex(1.0, 1.0)
                  .color((float)color2.x, (float)color2.y, (float)color2.z, 1.0F)
                  .endVertex();
               tes.draw();
            }
         }

         if (canUseRendList) {
            GlStateManager.glEndList();
            this.useRenderList = true;
         }
      }

      GlStateManager.enableLighting();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }
}
