package com.vivern.arpg.renders;

import com.vivern.arpg.recipes.Phenomenons;
import com.vivern.arpg.recipes.TerraformingResearchParticle;
import com.vivern.arpg.recipes.TerraformingResearchPuzzle;
import com.vivern.arpg.recipes.TerraformingResearchSurface;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTerraformingResearch {
   public static Random rand = new Random();
   public float posx = 0.0F;
   public float posy = 0.0F;
   public float scaleSpeed;
   public float scale = 1.0F;
   public int cellWidth = 50;
   public int cellHeight = 50;
   public int width;
   public int height;
   public TFRSurfaceRenderInstance[][] terrainAnimations;
   public TFRSurfaceRenderInstance[][] atmosphereAnimations;
   public ArrayList<TerraformingResearchParticle> particles = new ArrayList<>();
   public static Predicate<TerraformingResearchParticle> particlesDeleter = new Predicate<TerraformingResearchParticle>() {
      public boolean apply(TerraformingResearchParticle input) {
         return input.isDead;
      }
   };
   public TRRenderer pointRenderer = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 0, 0, 100, 50, 18, 18, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer selectionRenderer1 = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 0, 0, 164, 3, 44, 44, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer selectionRenderer2 = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 0, 0, 211, 3, 44, 44, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer moveRendererUp = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 0, -41, 137, 0, 12, 37, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer moveRendererRight = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 41, 0, 100, 0, 37, 12, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer moveRendererDown = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 0, 41, 149, 0, 12, 37, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer moveRendererLeft = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, -41, 0, 100, 12, 37, 12, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer[] waysRenderers = new TRRenderer[]{
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 0, 0, 0, 50, 50, 50, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 0, 10, 50, 80, 50, 30, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, -10, 0, 0, 100, 30, 50, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 0, -10, 50, 50, 50, 30, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 10, 0, 30, 100, 30, 50, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 0, 0, 103, 68, 8, 50, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 0, 0, 118, 53, 50, 8, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 10, -10, 120, 100, 30, 30, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 10, 10, 120, 70, 30, 30, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, -10, 10, 150, 70, 30, 30, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, -10, -10, 150, 100, 30, 30, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 0, -10, 111, 120, 8, 30, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 10, 0, 120, 134, 30, 8, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 0, 10, 103, 120, 8, 30, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, -10, 0, 120, 142, 30, 8, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 10, -10, 20, 50, 30, 30, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, 10, 10, 20, 70, 30, 30, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, -10, 10, 0, 70, 30, 30, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY),
      new TRRenderer.TRRendererSprite(Phenomenons.tfr_sprites, -10, -10, 0, 50, 30, 30, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY)
   };
   public TRRenderer woodFrameRendererLeft = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, -29, 0, 242, 100, 8, 50, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer woodFrameRendererRight = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 29, 0, 242, 100, 8, 50, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer woodFrameRendererUp = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 0, -29, 192, 142, 50, 8, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer woodFrameRendererDown = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 0, 29, 192, 142, 50, 8, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer woodFrameRendererLeftUpOut = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, -27, -27, 210, 100, 16, 16, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer woodFrameRendererRightUpOut = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 27, -27, 226, 100, 16, 16, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer woodFrameRendererLeftDownOut = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, -27, 27, 210, 116, 16, 16, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer woodFrameRendererRightDownOut = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 27, 27, 226, 116, 16, 16, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer woodFrameRendererLeftUpIn = new TRRenderer.TRRendererSprite(
         Phenomenons.tfr_sprites, -29, -21, 210, 132, 8, 8, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
      )
      .setLayer(0.1F);
   public TRRenderer woodFrameRendererRightUpIn = new TRRenderer.TRRendererSprite(
         Phenomenons.tfr_sprites, 21, -29, 218, 132, 8, 8, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
      )
      .setLayer(0.1F);
   public TRRenderer woodFrameRendererLeftDownIn = new TRRenderer.TRRendererSprite(
         Phenomenons.tfr_sprites, -21, 29, 226, 132, 8, 8, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
      )
      .setLayer(0.1F);
   public TRRenderer woodFrameRendererRightDownIn = new TRRenderer.TRRendererSprite(
         Phenomenons.tfr_sprites, 29, 21, 234, 132, 8, 8, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
      )
      .setLayer(0.1F);
   public TRRenderer orbRendererMoving = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 0, 0, 340, 0, 20, 20, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer orbRendererInterflow = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 0, 0, 360, 0, 20, 20, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer orbRendererSplit = new TRRenderer.TRRendererSprite(
      Phenomenons.tfr_sprites, 0, 0, 380, 0, 20, 20, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
   );
   public TRRenderer electricFieldRenderer = new TRRenderer.TRRendererSprite(
         Phenomenons.tfr_sprites, 1050, 0, 50, 50, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
      )
      .setAnimation(3, 20)
      .setStyle(TRRenderer.RenderStyle.ADDITIVE);

   public RenderTerraformingResearch(int width, int height) {
      this.width = width;
      this.height = height;
      this.terrainAnimations = new TFRSurfaceRenderInstance[width][height];
      this.atmosphereAnimations = new TFRSurfaceRenderInstance[width][height];

      for (int x = 0; x < width; x++) {
         for (int y = 0; y < height; y++) {
            this.terrainAnimations[x][y] = new TFRSurfaceRenderInstance();
            this.atmosphereAnimations[x][y] = new TFRSurfaceRenderInstance();
         }
      }
   }

   public void setTerrainAnimation(TerraformingResearchSurface surface, TerraformingResearchSurface.TRSurfaceType type, int x, int y, boolean instant) {
      if (type == TerraformingResearchSurface.TRSurfaceType.TERRAIN) {
         this.terrainAnimations[x][y].reset(surface, instant);
      } else if (type == TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE) {
         this.atmosphereAnimations[x][y].reset(surface, instant);
      }
   }

   public boolean hasSomethingAt(int x, int y, TerraformingResearchSurface.TRSurfaceType type) {
      if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
         return false;
      } else if (type == TerraformingResearchSurface.TRSurfaceType.TERRAIN) {
         return this.terrainAnimations[x][y].hasSomething();
      } else {
         return type == TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE ? this.atmosphereAnimations[x][y].hasSomething() : false;
      }
   }

   public boolean checkForBounds(float displaceX, float displaceY, int arrayX, int arrayY, TerraformingResearchSurface.TRSurfaceType type, int boundSize) {
      float bound = 33 - boundSize;
      if (displaceX > bound && !this.hasSomethingAt(arrayX + 1, arrayY, type)) {
         return false;
      } else if (displaceX < -bound && !this.hasSomethingAt(arrayX - 1, arrayY, type)) {
         return false;
      } else {
         return displaceY > bound && !this.hasSomethingAt(arrayX, arrayY + 1, type)
            ? false
            : !(displaceX < -bound) || this.hasSomethingAt(arrayX, arrayY - 1, type);
      }
   }

   public void updateTerrainAnimation() {
      for (int x = 0; x < this.width; x++) {
         float xx = x * this.cellWidth;

         for (int y = 0; y < this.height; y++) {
            float yy = y * this.cellHeight;
            this.terrainAnimations[x][y].update(this, xx, yy, x, y);
            this.atmosphereAnimations[x][y].update(this, xx, yy, x, y);
         }
      }

      for (TerraformingResearchParticle particle : this.particles) {
         particle.update();
      }

      this.particles.removeIf(particlesDeleter);
   }

   public void spawnParticle(TerraformingResearchParticle particle) {
      this.particles.add(particle);
   }

   public void mouseWhellInput(int input) {
      if (input > 0) {
         this.scaleSpeed += 0.04F;
      } else if (input < 0) {
         this.scaleSpeed -= 0.04F;
      }
   }

   public float[] screenPosToBoardPos(int centeredX, int centeredY) {
      float resultx = this.posx + centeredX / this.scale;
      float resulty = this.posy + centeredY / this.scale;
      return new float[]{resultx, resulty};
   }

   public int[] boardPosToCellPos(float posX, float posY) {
      int resultx = (int)posX / this.cellWidth;
      int resulty = (int)posY / this.cellHeight;
      return new int[]{resultx, resulty};
   }

   public void renderAndBind(TerraformingResearchPuzzle researchPuzzle, int renderx, int rendery, float partialTicks) {
      Minecraft mc = Minecraft.getMinecraft();
      this.scale = MathHelper.clamp(this.scale + this.scaleSpeed, 0.2F, 1.0F);
      this.scaleSpeed *= 0.7F;
      int halfcellWidth = this.cellWidth / 2;
      int halfcellHeight = this.cellHeight / 2;
      float resultX = -this.posx + halfcellWidth;
      float resultY = -this.posy + halfcellHeight;
      GlStateManager.pushMatrix();
      GlStateManager.translate(renderx, rendery, 0.0F);
      GlStateManager.scale(this.scale, this.scale, 1.0F);
      int maxLayers = 3;

      for (int layer = 0; layer < maxLayers; layer++) {
         for (int y = 0; y < researchPuzzle.height; y++) {
            for (int x = 0; x < researchPuzzle.width; x++) {
               this.terrainAnimations[x][y]
                  .render(this, resultX + x * this.cellWidth, resultY + y * this.cellHeight, x, y, x * 231896917442L + y * 542813726771L, partialTicks, layer);
            }
         }
      }

      for (int y = 0; y < researchPuzzle.height; y++) {
         for (int x = 0; x < researchPuzzle.width; x++) {
            if (researchPuzzle.fields[x][y][0]) {
               this.electricFieldRenderer
                  .render(this, resultX + x * this.cellWidth, resultY + y * this.cellHeight, x, y, x * 231896917442L + y * 542813726771L, 1.0F);
            }
         }
      }

      for (int layer = 0; layer < maxLayers; layer++) {
         for (int y = 0; y < researchPuzzle.height; y++) {
            for (int xx = 0; xx < researchPuzzle.width; xx++) {
               this.atmosphereAnimations[xx][y]
                  .render(
                     this, resultX + xx * this.cellWidth, resultY + y * this.cellHeight, xx, y, xx * 231896917442L + y * 542813726771L, partialTicks, layer
                  );
            }
         }
      }

      GlStateManager.disableDepth();

      for (int y = 0; y < researchPuzzle.height; y++) {
         for (int xx = 0; xx < researchPuzzle.width; xx++) {
            TerraformingResearchPuzzle.TerraformingResearchCell cell = researchPuzzle.board[xx][y];
            float xxx = resultX + xx * this.cellWidth;
            float yy = resultY + y * this.cellHeight;
            if (cell.wayRenderIndex >= 0) {
               this.waysRenderers[cell.wayRenderIndex].render(this, xxx, yy, xx, y, 0L, 1.0F);
            }

            if (cell.isPoint) {
               this.pointRenderer.render(this, xxx, yy, xx, y, 0L, 1.0F);
            }
         }
      }

      for (int y = 0; y < researchPuzzle.height; y++) {
         for (int xx = 0; xx < researchPuzzle.width; xx++) {
            TerraformingResearchPuzzle.TerraformingResearchCell cellx = researchPuzzle.board[xx][y];
            if (cellx.phenomenon != null) {
               cellx.phenomenon.render(this, resultX + xx * this.cellWidth, resultY + y * this.cellHeight, xx, y, 1.0F);
            }

            if (cellx.orb == 1) {
               this.orbRendererMoving.render(this, resultX + xx * this.cellWidth, resultY + y * this.cellHeight, xx, y, 0L, 1.0F);
            } else if (cellx.orb == 2) {
               this.orbRendererInterflow.render(this, resultX + xx * this.cellWidth, resultY + y * this.cellHeight, xx, y, 0L, 1.0F);
            } else if (cellx.orb == 3) {
               this.orbRendererSplit.render(this, resultX + xx * this.cellWidth, resultY + y * this.cellHeight, xx, y, 0L, 1.0F);
            }
         }
      }

      if (researchPuzzle.selected != null && researchPuzzle.selected.phenomenon != null) {
         float xx = resultX + researchPuzzle.selected.phenomenon.posX * this.cellWidth;
         float yyx = resultY + researchPuzzle.selected.phenomenon.posY * this.cellHeight;
         if (researchPuzzle.selected.phenomenon.getId() <= 12) {
            this.selectionRenderer2.render(this, xx, yyx, researchPuzzle.selected.phenomenon.posX, researchPuzzle.selected.phenomenon.posY, 0L, 1.0F);
         } else {
            this.selectionRenderer1.render(this, xx, yyx, researchPuzzle.selected.phenomenon.posX, researchPuzzle.selected.phenomenon.posY, 0L, 1.0F);
         }

         if (researchPuzzle.selected.wayUp) {
            this.moveRendererUp.render(this, xx, yyx, researchPuzzle.selected.phenomenon.posX, researchPuzzle.selected.phenomenon.posY, 0L, 1.0F);
         }

         if (researchPuzzle.selected.wayRight) {
            this.moveRendererRight.render(this, xx, yyx, researchPuzzle.selected.phenomenon.posX, researchPuzzle.selected.phenomenon.posY, 0L, 1.0F);
         }

         if (researchPuzzle.selected.wayDown) {
            this.moveRendererDown.render(this, xx, yyx, researchPuzzle.selected.phenomenon.posX, researchPuzzle.selected.phenomenon.posY, 0L, 1.0F);
         }

         if (researchPuzzle.selected.wayLeft) {
            this.moveRendererLeft.render(this, xx, yyx, researchPuzzle.selected.phenomenon.posX, researchPuzzle.selected.phenomenon.posY, 0L, 1.0F);
         }
      }

      for (TerraformingResearchParticle particle : this.particles) {
         particle.render(this, resultX, resultY, partialTicks);
      }

      if (researchPuzzle.selected != null && researchPuzzle.selected.phenomenon != null) {
         if (!researchPuzzle.selected.phenomenon.subselected) {
         }

         if (researchPuzzle.selected.phenomenon.subselected) {
            float xxxx = resultX + researchPuzzle.selected.phenomenon.subselectedX * this.cellWidth;
            float yyxx = resultY + researchPuzzle.selected.phenomenon.subselectedY * this.cellHeight;
            this.renderLineFigure(xxxx, yyxx);
         }
      }

      GlStateManager.enableDepth();
      GlStateManager.popMatrix();
   }

   public void renderLine(float startX, float startY, float endX, float endY) {
   }

   public void renderLineFigure(float x, float y) {
   }

   public void renderLinesQuad(float x, float y, float radius) {
   }

   public static class TFRSurfaceRenderInstance {
      public TerraformingResearchSurface current;
      public TerraformingResearchSurface last;
      public int progress;
      public static int progressMax = 10;

      public void reset(TerraformingResearchSurface newSurface, boolean instant) {
         if (this.current != null) {
            this.last = this.current;
         }

         this.current = newSurface;
         if (newSurface == this.last) {
            instant = true;
         }

         this.progress = instant ? progressMax : 0;
      }

      public void update(RenderTerraformingResearch renderTFR, float x, float y, int arrayX, int arrayY) {
         if (this.progress < progressMax) {
            this.progress++;
         } else {
            this.last = null;
         }

         if (this.current != null && this.current.particleSystem != null) {
            this.current.particleSystem.update(renderTFR, x, y, this.current, RenderTerraformingResearch.rand, arrayX, arrayY);
         }
      }

      public void render(RenderTerraformingResearch renderTFR, float x, float y, int arrayX, int arrayY, long seed, float partialTicks, int layer) {
         float progF = Math.min((this.progress + partialTicks) / progressMax, 1.0F);
         float unProgF = 1.0F - progF;
         if (this.last != null && this.last.renderer != null) {
            for (int i = 0; i < this.last.renderer.length; i++) {
               if ((int)this.last.renderer[i].layer == layer) {
                  this.last.renderer[i].render(renderTFR, x, y, arrayX, arrayY, seed, unProgF);
               }
            }
         }

         if (this.current != null && this.current.renderer != null) {
            for (int ix = 0; ix < this.current.renderer.length; ix++) {
               if ((int)this.current.renderer[ix].layer == layer) {
                  this.current.renderer[ix].render(renderTFR, x, y, arrayX, arrayY, seed, progF);
               }
            }
         }
      }

      public boolean hasSomething() {
         return this.current != null || this.last != null;
      }
   }
}
