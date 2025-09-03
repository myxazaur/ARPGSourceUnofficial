//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.recipes.TerraformingResearchSurface;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TRRenderer {
   public static Vec3d DEFAULT_DIFFUSE = new Vec3d(1.0, 1.0, 1.0);
   public float layer;
   public RenderStyle renderStyle = RenderStyle.NORMAL;
   public Vec3d diffuseColor = DEFAULT_DIFFUSE;
   public boolean diffuseDirection;

   public void render(RenderTerraformingResearch renderTFR, float posx, float posy, int arrayX, int arrayY, long seed, float fullness) {
   }

   public int time() {
      return AnimationTimer.tick;
   }

   public TRRenderer setLayer(float layer) {
      this.layer = layer;
      return this;
   }

   public TRRenderer setStyle(RenderStyle renderStyle) {
      this.renderStyle = renderStyle;
      return this;
   }

   public TRRenderer setDiffuseColor(float red, float green, float blue, boolean invertedDiffuseDirection) {
      this.diffuseColor = new Vec3d(red, green, blue);
      this.diffuseDirection = invertedDiffuseDirection;
      return this;
   }

   public TRRenderer setDiffuseColor(int color, boolean invertedDiffuseDirection) {
      this.diffuseColor = ColorConverters.DecimaltoRGB(color);
      this.diffuseDirection = invertedDiffuseDirection;
      return this;
   }

   public static enum RenderStyle {
      NORMAL(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, false),
      TRANSLUCENT(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, true),
      ADDITIVE(SourceFactor.SRC_ALPHA, DestFactor.ONE, true);

      SourceFactor sourceFactor;
      DestFactor destinationFactor;
      boolean useBlend;

      private RenderStyle(SourceFactor sourceFactor, DestFactor destinationFactor, boolean useBlend) {
         this.sourceFactor = sourceFactor;
         this.destinationFactor = destinationFactor;
         this.useBlend = useBlend;
      }

      public void preRender() {
         if (this.useBlend) {
            GlStateManager.enableBlend();
         }

         GlStateManager.blendFunc(this.sourceFactor, this.destinationFactor);
      }

      public void postRender() {
         if (this.useBlend) {
            GlStateManager.disableBlend();
         }
      }
   }

   public static class TRRendererScatter extends TRRenderer {
      public ResourceLocation texture;
      public int x;
      public int y;
      public int texx;
      public int texy;
      public int texWidth;
      public int texHeight;
      public int texturesAmount;
      public int density;
      public float yBias = 0.2F;
      public int allTextureSizeX;
      public int allTextureSizeY;
      public int animationDelay;
      public int animationCycle;
      public float randomBrightness = 0.0F;
      public int boundCheckSize;

      public TRRendererScatter(
         ResourceLocation texture,
         int texturesAmount,
         int density,
         int x,
         int y,
         int texx,
         int texy,
         int texWidth,
         int texHeight,
         int allTextureSizeX,
         int allTextureSizeY,
         int boundCheckSize
      ) {
         this.texture = texture;
         this.x = x;
         this.y = y;
         this.texx = texx;
         this.texy = texy;
         this.texWidth = texWidth;
         this.texHeight = texHeight;
         this.allTextureSizeX = allTextureSizeX;
         this.allTextureSizeY = allTextureSizeY;
         this.texturesAmount = texturesAmount;
         this.density = density;
         this.boundCheckSize = boundCheckSize;
      }

      public TRRendererScatter(
         ResourceLocation texture,
         int texturesAmount,
         int density,
         int texx,
         int texy,
         int texWidth,
         int texHeight,
         int allTextureSizeX,
         int allTextureSizeY,
         int boundCheckSize
      ) {
         this.texture = texture;
         this.x = 0;
         this.y = 0;
         this.texx = texx;
         this.texy = texy;
         this.texWidth = texWidth;
         this.texHeight = texHeight;
         this.allTextureSizeX = allTextureSizeX;
         this.allTextureSizeY = allTextureSizeY;
         this.texturesAmount = texturesAmount;
         this.density = density;
         this.boundCheckSize = boundCheckSize;
      }

      public TRRendererScatter setAnimation(int animationDelay, int animationCycle) {
         this.animationDelay = animationDelay;
         this.animationCycle = animationCycle;
         return this;
      }

      public TRRendererScatter setYBias(float bias) {
         this.yBias = bias;
         return this;
      }

      public TRRendererScatter setRandomBrightness(float randomBrightness) {
         this.randomBrightness = randomBrightness;
         return this;
      }

      @Override
      public void render(RenderTerraformingResearch renderTFR, float posx, float posy, int arrayX, int arrayY, long seed, float fullness) {
         Random rand = new Random(seed);
         posx += this.x;
         posy += this.y;
         Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
         double onepixelX = 1.0 / this.allTextureSizeX;
         double onepixelY = 1.0 / this.allTextureSizeY;
         float halfW = this.texWidth / 2.0F * fullness;
         float unYBias = 1.0F - this.yBias;
         float halfHpluss = this.texHeight * this.yBias * fullness;
         float halfHminus = -this.texHeight * unYBias * fullness;
         double u = onepixelX * this.texx;
         double v = onepixelY * this.texy;
         this.renderStyle.preRender();
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         float ccolorDiffuseRange = 87.0F / renderTFR.scale;
         float oneDensity = 1.0F / this.density;

         for (int i = 0; i < this.density; i++) {
            float yadd = -25.0F + ((float)i / this.density + rand.nextFloat() * oneDensity) * 50.0F;
            float xadd = -25.0F + rand.nextFloat() * 50.0F;
            double uadd = this.texturesAmount > 1 ? rand.nextInt(this.texturesAmount) * (onepixelX * this.texWidth) : 0.0;
            double animAdd = this.animationDelay == 0
               ? 0.0
               : onepixelY * this.texHeight * ((this.time() + rand.nextInt(999999)) / this.animationDelay % this.animationCycle);
            float yColorDiffuse = GetMOP.getfromto(posy + yadd, -ccolorDiffuseRange, ccolorDiffuseRange);
            float unyColorDiffuse = 1.0F - yColorDiffuse;
            float r = (unyColorDiffuse + (float)this.diffuseColor.x * yColorDiffuse) * (1.0F - this.randomBrightness * rand.nextFloat());
            float g = (unyColorDiffuse + (float)this.diffuseColor.y * yColorDiffuse) * (1.0F - this.randomBrightness * rand.nextFloat());
            float b = (unyColorDiffuse + (float)this.diffuseColor.z * yColorDiffuse) * (1.0F - this.randomBrightness * rand.nextFloat());
            float alpha = 1.0F;
            if (renderTFR.checkForBounds(xadd, yadd, arrayX, arrayY, TerraformingResearchSurface.TRSurfaceType.TERRAIN, this.boundCheckSize)) {
               bufferbuilder.pos(posx - halfW + xadd, posy + halfHminus + yadd, this.layer)
                  .tex(u + uadd, v + animAdd)
                  .color(r, g, b, alpha)
                  .endVertex();
               bufferbuilder.pos(posx - halfW + xadd, posy + halfHpluss + yadd, this.layer)
                  .tex(u + uadd, v + onepixelY * this.texHeight + animAdd)
                  .color(r, g, b, alpha)
                  .endVertex();
               bufferbuilder.pos(posx + halfW + xadd, posy + halfHpluss + yadd, this.layer)
                  .tex(u + uadd + onepixelX * this.texWidth, v + onepixelY * this.texHeight + animAdd)
                  .color(r, g, b, alpha)
                  .endVertex();
               bufferbuilder.pos(posx + halfW + xadd, posy + halfHminus + yadd, this.layer)
                  .tex(u + uadd + onepixelX * this.texWidth, v + animAdd)
                  .color(r, g, b, alpha)
                  .endVertex();
            }
         }

         tessellator.draw();
         this.renderStyle.postRender();
      }
   }

   public static class TRRendererSprite extends TRRenderer {
      public ResourceLocation texture;
      public int x;
      public int y;
      public int texx;
      public int texy;
      public int texWidth;
      public int texHeight;
      public int allTextureSizeX;
      public int allTextureSizeY;
      public int animationDelay;
      public int animationCycle;
      public boolean hasWoodenFrame = false;
      public boolean isAtmosphereEffect = false;

      public TRRendererSprite(ResourceLocation texture, int x, int y, int texx, int texy, int texWidth, int texHeight, int allTextureSizeX, int allTextureSizeY) {
         this.texture = texture;
         this.x = x;
         this.y = y;
         this.texx = texx;
         this.texy = texy;
         this.texWidth = texWidth;
         this.texHeight = texHeight;
         this.allTextureSizeX = allTextureSizeX;
         this.allTextureSizeY = allTextureSizeY;
      }

      public TRRendererSprite(ResourceLocation texture, int texx, int texy, int texWidth, int texHeight, int allTextureSizeX, int allTextureSizeY) {
         this.texture = texture;
         this.x = 0;
         this.y = 0;
         this.texx = texx;
         this.texy = texy;
         this.texWidth = texWidth;
         this.texHeight = texHeight;
         this.allTextureSizeX = allTextureSizeX;
         this.allTextureSizeY = allTextureSizeY;
      }

      public TRRendererSprite setAnimation(int animationDelay, int animationCycle) {
         this.animationDelay = animationDelay;
         this.animationCycle = animationCycle;
         return this;
      }

      public TRRendererSprite setWoodenFrame() {
         this.hasWoodenFrame = true;
         return this;
      }

      public TRRendererSprite setAtmosphereEffect() {
         this.isAtmosphereEffect = true;
         return this;
      }

      @Override
      public void render(RenderTerraformingResearch renderTFR, float posx, float posy, int arrayX, int arrayY, long seed, float fullness) {
         posx += this.x;
         posy += this.y;
         Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
         double onepixelX = 1.0 / this.allTextureSizeX;
         double onepixelY = 1.0 / this.allTextureSizeY;
         float halfW = this.texWidth / 2.0F * fullness;
         float halfH = this.texHeight / 2.0F * fullness;
         double u = onepixelX * this.texx;
         double v = onepixelY * this.texy;
         double animAdd = this.animationDelay == 0 ? 0.0 : onepixelY * this.texHeight * (this.time() / this.animationDelay % this.animationCycle);
         this.renderStyle.preRender();
         float ccolorDiffuseRange = 87.0F / renderTFR.scale;
         float yColorDiffuse1;
         float yColorDiffuse2;
         if (this.diffuseDirection) {
            yColorDiffuse1 = 1.0F - GetMOP.getfromto(posy + 25.0F, -ccolorDiffuseRange, ccolorDiffuseRange);
            yColorDiffuse2 = 1.0F - GetMOP.getfromto(posy - 25.0F, -ccolorDiffuseRange, ccolorDiffuseRange);
         } else {
            yColorDiffuse1 = GetMOP.getfromto(posy + 25.0F, -ccolorDiffuseRange, ccolorDiffuseRange);
            yColorDiffuse2 = GetMOP.getfromto(posy - 25.0F, -ccolorDiffuseRange, ccolorDiffuseRange);
         }

         float unyColorDiffuse1 = 1.0F - yColorDiffuse1;
         float unyColorDiffuse2 = 1.0F - yColorDiffuse2;
         float r1 = unyColorDiffuse1 + (float)this.diffuseColor.x * yColorDiffuse1;
         float g1 = unyColorDiffuse1 + (float)this.diffuseColor.y * yColorDiffuse1;
         float b1 = unyColorDiffuse1 + (float)this.diffuseColor.z * yColorDiffuse1;
         float r2 = unyColorDiffuse2 + (float)this.diffuseColor.x * yColorDiffuse2;
         float g2 = unyColorDiffuse2 + (float)this.diffuseColor.y * yColorDiffuse2;
         float b2 = unyColorDiffuse2 + (float)this.diffuseColor.z * yColorDiffuse2;
         float alpha1 = 1.0F;
         float alpha2 = 1.0F;
         float alpha3 = 1.0F;
         float alpha4 = 1.0F;
         if (this.isAtmosphereEffect) {
            float airAlpha = 0.4F;
            boolean noright = !renderTFR.hasSomethingAt(arrayX + 1, arrayY, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE);
            boolean noleft = !renderTFR.hasSomethingAt(arrayX - 1, arrayY, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE);
            boolean nodown = !renderTFR.hasSomethingAt(arrayX, arrayY + 1, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE);
            boolean noup = !renderTFR.hasSomethingAt(arrayX, arrayY - 1, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE);
            boolean norightUp = !renderTFR.hasSomethingAt(arrayX + 1, arrayY - 1, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE);
            boolean norightDown = !renderTFR.hasSomethingAt(arrayX + 1, arrayY + 1, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE);
            boolean noleftUp = !renderTFR.hasSomethingAt(arrayX - 1, arrayY - 1, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE);
            boolean noleftDown = !renderTFR.hasSomethingAt(arrayX - 1, arrayY + 1, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE);
            if (noleft || noup || noleftUp) {
               alpha1 = airAlpha;
            }

            if (noleft || nodown || noleftDown) {
               alpha2 = airAlpha;
            }

            if (noright || nodown || norightDown) {
               alpha3 = airAlpha;
            }

            if (noright || noup || norightUp) {
               alpha4 = airAlpha;
            }
         }

         GlStateManager.shadeModel(7425);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         bufferbuilder.pos(posx - halfW, posy - halfH, this.layer)
            .tex(u, v + animAdd)
            .color(r2 * alpha1, g2 * alpha1, b2 * alpha1, alpha1)
            .endVertex();
         bufferbuilder.pos(posx - halfW, posy + halfH, this.layer)
            .tex(u, v + onepixelY * this.texHeight + animAdd)
            .color(r1 * alpha2, g1 * alpha2, b1 * alpha2, alpha2)
            .endVertex();
         bufferbuilder.pos(posx + halfW, posy + halfH, this.layer)
            .tex(u + onepixelX * this.texWidth, v + onepixelY * this.texHeight + animAdd)
            .color(r1 * alpha3, g1 * alpha3, b1 * alpha3, alpha3)
            .endVertex();
         bufferbuilder.pos(posx + halfW, posy - halfH, this.layer)
            .tex(u + onepixelX * this.texWidth, v + animAdd)
            .color(r2 * alpha4, g2 * alpha4, b2 * alpha4, alpha4)
            .endVertex();
         tessellator.draw();
         GlStateManager.shadeModel(7424);
         this.renderStyle.postRender();
         if (this.hasWoodenFrame) {
            boolean right = renderTFR.hasSomethingAt(arrayX + 1, arrayY, TerraformingResearchSurface.TRSurfaceType.TERRAIN);
            boolean left = renderTFR.hasSomethingAt(arrayX - 1, arrayY, TerraformingResearchSurface.TRSurfaceType.TERRAIN);
            boolean down = renderTFR.hasSomethingAt(arrayX, arrayY + 1, TerraformingResearchSurface.TRSurfaceType.TERRAIN);
            boolean up = renderTFR.hasSomethingAt(arrayX, arrayY - 1, TerraformingResearchSurface.TRSurfaceType.TERRAIN);
            boolean rightUp = renderTFR.hasSomethingAt(arrayX + 1, arrayY - 1, TerraformingResearchSurface.TRSurfaceType.TERRAIN);
            boolean rightDown = renderTFR.hasSomethingAt(arrayX + 1, arrayY + 1, TerraformingResearchSurface.TRSurfaceType.TERRAIN);
            boolean leftUp = renderTFR.hasSomethingAt(arrayX - 1, arrayY - 1, TerraformingResearchSurface.TRSurfaceType.TERRAIN);
            boolean leftDown = renderTFR.hasSomethingAt(arrayX - 1, arrayY + 1, TerraformingResearchSurface.TRSurfaceType.TERRAIN);
            if (!right) {
               renderTFR.woodFrameRendererRight.render(renderTFR, posx, posy, arrayX, arrayY, seed, fullness);
            }

            if (!left) {
               renderTFR.woodFrameRendererLeft.render(renderTFR, posx, posy, arrayX, arrayY, seed, fullness);
            }

            if (!down) {
               renderTFR.woodFrameRendererDown.render(renderTFR, posx, posy, arrayX, arrayY, seed, fullness);
            }

            if (!up) {
               renderTFR.woodFrameRendererUp.render(renderTFR, posx, posy, arrayX, arrayY, seed, fullness);
            }

            if (!right && !up && !rightUp) {
               renderTFR.woodFrameRendererRightUpOut.render(renderTFR, posx, posy, arrayX, arrayY, seed, fullness);
            }

            if (!right && !down && !rightDown) {
               renderTFR.woodFrameRendererRightDownOut.render(renderTFR, posx, posy, arrayX, arrayY, seed, fullness);
            }

            if (!left && !up && !leftUp) {
               renderTFR.woodFrameRendererLeftUpOut.render(renderTFR, posx, posy, arrayX, arrayY, seed, fullness);
            }

            if (!left && !down && !leftDown) {
               renderTFR.woodFrameRendererLeftDownOut.render(renderTFR, posx, posy, arrayX, arrayY, seed, fullness);
            }

            if (!up && rightUp) {
               renderTFR.woodFrameRendererRightUpIn.render(renderTFR, posx, posy, arrayX, arrayY, seed, fullness);
            }

            if (!right && rightDown) {
               renderTFR.woodFrameRendererRightDownIn.render(renderTFR, posx, posy, arrayX, arrayY, seed, fullness);
            }

            if (!left && leftUp) {
               renderTFR.woodFrameRendererLeftUpIn.render(renderTFR, posx, posy, arrayX, arrayY, seed, fullness);
            }

            if (!down && leftDown) {
               renderTFR.woodFrameRendererLeftDownIn.render(renderTFR, posx, posy, arrayX, arrayY, seed, fullness);
            }
         }
      }
   }

   public static class TRRendererSpriteGlimmer extends TRRenderer {
      public ResourceLocation texture;
      public int x;
      public int y;
      public int texx;
      public int texy;
      public int texWidth;
      public int texHeight;
      public int allTextureSizeX;
      public int allTextureSizeY;
      public int animationDelay;
      public int animationCycle;
      public float glimmerPeriod;

      public TRRendererSpriteGlimmer(
         ResourceLocation texture, int x, int y, int texx, int texy, int texWidth, int texHeight, int allTextureSizeX, int allTextureSizeY, float glimmerPeriod
      ) {
         this.texture = texture;
         this.x = x;
         this.y = y;
         this.texx = texx;
         this.texy = texy;
         this.texWidth = texWidth;
         this.texHeight = texHeight;
         this.allTextureSizeX = allTextureSizeX;
         this.allTextureSizeY = allTextureSizeY;
         this.glimmerPeriod = glimmerPeriod;
      }

      public TRRendererSpriteGlimmer(
         ResourceLocation texture, int texx, int texy, int texWidth, int texHeight, int allTextureSizeX, int allTextureSizeY, float glimmerPeriod
      ) {
         this.texture = texture;
         this.x = 0;
         this.y = 0;
         this.texx = texx;
         this.texy = texy;
         this.texWidth = texWidth;
         this.texHeight = texHeight;
         this.allTextureSizeX = allTextureSizeX;
         this.allTextureSizeY = allTextureSizeY;
         this.glimmerPeriod = glimmerPeriod;
      }

      public TRRendererSpriteGlimmer setAnimation(int animationDelay, int animationCycle) {
         this.animationDelay = animationDelay;
         this.animationCycle = animationCycle;
         return this;
      }

      @Override
      public void render(RenderTerraformingResearch renderTFR, float posx, float posy, int arrayX, int arrayY, long seed, float fullness) {
         posx += this.x;
         posy += this.y;
         Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
         double onepixelX = 1.0 / this.allTextureSizeX;
         double onepixelY = 1.0 / this.allTextureSizeY;
         float halfW = this.texWidth / 2.0F * fullness;
         float halfH = this.texHeight / 2.0F * fullness;
         double u = onepixelX * this.texx;
         double v = onepixelY * this.texy;
         double animAdd = this.animationDelay == 0 ? 0.0 : onepixelY * this.texHeight * (this.time() / this.animationDelay % this.animationCycle);
         this.renderStyle.preRender();
         GlStateManager.color(1.0F, 1.0F, 1.0F, MathHelper.sin(this.time() / this.glimmerPeriod) / 2.0F + 0.5F);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(posx - halfW, posy - halfH, this.layer).tex(u, v + animAdd).endVertex();
         bufferbuilder.pos(posx - halfW, posy + halfH, this.layer).tex(u, v + onepixelY * this.texHeight + animAdd).endVertex();
         bufferbuilder.pos(posx + halfW, posy + halfH, this.layer)
            .tex(u + onepixelX * this.texWidth, v + onepixelY * this.texHeight + animAdd)
            .endVertex();
         bufferbuilder.pos(posx + halfW, posy - halfH, this.layer).tex(u + onepixelX * this.texWidth, v + animAdd).endVertex();
         tessellator.draw();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.renderStyle.postRender();
      }
   }
}
