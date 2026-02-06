package com.Vivern.Arpg.recipes;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.renders.RenderTerraformingResearch;
import com.Vivern.Arpg.renders.TRRenderer;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class TerraformingResearchParticle {
   public ResourceLocation texture;
   public int texx;
   public int texy;
   public int texWidth;
   public int texHeight;
   public float x;
   public float y;
   public float motionX;
   public float motionY;
   public float airFrictionMult = 1.0F;
   public int allTextureSizeX;
   public int allTextureSizeY;
   public int animationDelay;
   public int animationCycle;
   public int livetime;
   public float scale = 1.0F;
   public float red = 1.0F;
   public float green = 1.0F;
   public float blue = 1.0F;
   public float alpha = 1.0F;
   public Vec3d[] vectorsAlpha;
   public Vec3d[] vectorsScale;
   public TRRenderer.RenderStyle renderStyle = TRRenderer.RenderStyle.NORMAL;
   public TFRParticleTracker tracker;
   public IFrameController frameController;
   public int ticksExisted;
   public int utilsValue;
   public boolean isDead;
   public static IFrameController defaultFrameController = ticksExisted -> ticksExisted;
   public static TFRParticleTracker tfrp_tracker_snowfall = new TFRParticleTracker() {
      @Override
      public void update(TerraformingResearchParticle particle) {
         if (particle.utilsValue == 0) {
            particle.motionX = (float)(particle.motionX + 0.02);
            if (particle.motionX > 0.6F) {
               particle.utilsValue = 1;
            }
         } else {
            particle.motionX = (float)(particle.motionX - 0.02);
            if (particle.motionX < -0.6F) {
               particle.utilsValue = 0;
            }
         }
      }
   };
   public static TFRParticleTracker tfrp_tracker_sparkle = new TFRParticleTracker() {
      @Override
      public void update(TerraformingResearchParticle particle) {
         particle.motionX = particle.motionX + 0.06F * (RenderTerraformingResearch.rand.nextFloat() - 0.5F);
         particle.motionY = particle.motionY + 0.06F * (RenderTerraformingResearch.rand.nextFloat() - 0.5F);
      }
   };

   public TerraformingResearchParticle(int texx, int texy, int texWidth, int texHeight, ResourceLocation texture, int allTextureSizeX, int allTextureSizeY) {
      this.frameController = defaultFrameController;
      this.isDead = false;
      this.texx = texx;
      this.texy = texy;
      this.texWidth = texWidth;
      this.texHeight = texHeight;
      this.texture = texture;
      this.allTextureSizeX = allTextureSizeX;
      this.allTextureSizeY = allTextureSizeY;
   }

   public TerraformingResearchParticle livetimePosMotion(int livetime, float x, float y, float motionX, float motionY, float airFrictionMult) {
      this.livetime = livetime;
      this.x = x;
      this.y = y;
      this.motionX = motionX;
      this.motionY = motionY;
      this.airFrictionMult = airFrictionMult;
      return this;
   }

   public TerraformingResearchParticle animation(int animationDelay, int animationCycle) {
      this.animationDelay = animationDelay;
      this.animationCycle = animationCycle;
      return this;
   }

   public TerraformingResearchParticle scale(float baseScale, @Nullable Vec3d... vectorsScale) {
      this.scale = baseScale;
      this.vectorsScale = vectorsScale;
      return this;
   }

   public TerraformingResearchParticle alpha(float baseAlpha, @Nullable Vec3d... vectorsAlpha) {
      this.alpha = baseAlpha;
      this.vectorsAlpha = vectorsAlpha;
      return this;
   }

   public TerraformingResearchParticle renderStyle(TRRenderer.RenderStyle renderStyle) {
      this.renderStyle = renderStyle;
      return this;
   }

   public void update() {
      this.ticksExisted++;
      this.x = this.x + this.motionX;
      this.y = this.y + this.motionY;
      this.motionX = this.motionX * this.airFrictionMult;
      this.motionY = this.motionY * this.airFrictionMult;
      if (this.vectorsAlpha != null) {
         for (Vec3d vect : this.vectorsAlpha) {
            if (this.ticksExisted >= vect.x && this.ticksExisted <= vect.y) {
               this.alpha = (float)(this.alpha + vect.z);
               break;
            }
         }
      }

      if (this.vectorsScale != null) {
         for (Vec3d vectx : this.vectorsScale) {
            if (this.ticksExisted >= vectx.x && this.ticksExisted <= vectx.y) {
               this.scale = (float)(this.scale + vectx.z);
               break;
            }
         }
      }

      if (this.tracker != null) {
         this.tracker.update(this);
      }

      if (this.ticksExisted > this.livetime) {
         this.isDead = true;
      }
   }

   public void render(RenderTerraformingResearch renderTFR, float boardOffsetX, float boardOffsetY, float partialTicks) {
      float posx = this.x + boardOffsetX;
      float posy = this.y + boardOffsetY;
      Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
      double onepixelX = 1.0 / this.allTextureSizeX;
      double onepixelY = 1.0 / this.allTextureSizeY;
      float halfW = this.texWidth / 2.0F * this.scale;
      float halfH = this.texHeight / 2.0F * this.scale;
      double u = onepixelX * this.texx;
      double v = onepixelY * this.texy;
      double animAdd = this.animationDelay == 0
         ? 0.0
         : onepixelY * this.texHeight * (this.frameController.whatFrame(this.ticksExisted / this.animationDelay) % this.animationCycle);
      this.renderStyle.preRender();
      GlStateManager.color(this.red, this.green, this.blue, this.alpha);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(posx - halfW, posy - halfH, 0.0).tex(u, v + animAdd).endVertex();
      bufferbuilder.pos(posx - halfW, posy + halfH, 0.0).tex(u, v + onepixelY * this.texHeight + animAdd).endVertex();
      bufferbuilder.pos(posx + halfW, posy + halfH, 0.0)
         .tex(u + onepixelX * this.texWidth, v + onepixelY * this.texHeight + animAdd)
         .endVertex();
      bufferbuilder.pos(posx + halfW, posy - halfH, 0.0).tex(u + onepixelX * this.texWidth, v + animAdd).endVertex();
      tessellator.draw();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.renderStyle.postRender();
   }

   public static class FrameControllerStorm implements IFrameController {
      int thunder;
      int randomed;

      @Override
      public int whatFrame(int ticksExisted) {
         if (this.randomed != ticksExisted && GetMOP.rand.nextFloat() < 0.02F) {
            this.thunder = ticksExisted;
         }

         this.randomed = ticksExisted;
         if (ticksExisted == this.thunder + 1) {
            return 1;
         } else if (ticksExisted == this.thunder + 2) {
            return 2;
         } else if (ticksExisted == this.thunder + 3) {
            return 1;
         } else if (ticksExisted == this.thunder + 4) {
            return 2;
         } else {
            return ticksExisted == this.thunder + 5 ? 1 : 0;
         }
      }
   }

   public interface IFrameController {
      int whatFrame(int var1);
   }

   public abstract static class TFRParticleTracker {
      public abstract void update(TerraformingResearchParticle var1);
   }

   public abstract static class TFRSParticleSystem {
      public abstract void update(RenderTerraformingResearch var1, float var2, float var3, TerraformingResearchSurface var4, Random var5, int var6, int var7);
   }
}
