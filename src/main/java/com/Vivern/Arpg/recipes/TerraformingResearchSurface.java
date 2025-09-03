package com.Vivern.Arpg.recipes;

import com.Vivern.Arpg.renders.TRRenderer;
import javax.annotation.Nullable;

public class TerraformingResearchSurface {
   public static int[][] neightbours4 = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
   public TRSurfaceType type;
   private int[] elements;
   public TRRenderer[] renderer;
   public int expansionQuality = 0;
   public boolean canExpandOnNull = false;
   public int id;
   @Nullable
   public TerraformingResearchParticle.TFRSParticleSystem particleSystem;

   public TerraformingResearchSurface(int id, TRSurfaceType type) {
      this.type = type;
      this.id = id;
   }

   public TerraformingResearchSurface(int id, TRSurfaceType type, int... elements) {
      this.type = type;
      this.id = id;
      this.elements = elements;
      if (elements.length != 12) {
         throw new RuntimeException("Count of elements must be 12!");
      }
   }

   public TerraformingResearchSurface setExpansion(int expansionQuality, boolean canExpandOnNull) {
      this.expansionQuality = expansionQuality;
      this.canExpandOnNull = canExpandOnNull;
      return this;
   }

   public TerraformingResearchSurface setParticles(TerraformingResearchParticle.TFRSParticleSystem particleSystem) {
      this.particleSystem = particleSystem;
      return this;
   }

   public int getElement(int elementId) {
      return this.elements[elementId - 1];
   }

   public void setElement(int elementId, int amount) {
      this.elements[elementId - 1] = amount;
   }

   public void onStep(TerraformingResearchPuzzle puzzle, TFRSurfaceChange[][] changes, int x, int y) {
      if (this.expansionQuality > 0) {
         for (int i = 0; i < 4; i++) {
            int xx = x + neightbours4[i][0];
            int yy = y + neightbours4[i][1];
            if (puzzle.checkBounds(xx, yy)) {
               TerraformingResearchSurface surf1 = puzzle.getSurface(xx, yy, this.type);
               if (this.canExpandOnNull) {
                  if ((surf1 == null || surf1.expansionQuality < this.expansionQuality)
                     && (changes[xx][yy] == null || changes[xx][yy].priority < this.expansionQuality)) {
                     changes[xx][yy] = new TFRSurfaceChange(this, this.expansionQuality);
                  }
               } else if (surf1 != null
                  && surf1.expansionQuality < this.expansionQuality
                  && (changes[xx][yy] == null || changes[xx][yy].priority < this.expansionQuality)) {
                  changes[xx][yy] = new TFRSurfaceChange(this, this.expansionQuality);
               }
            }
         }
      }
   }

   public static boolean hasNear(TerraformingResearchPuzzle puzzle, int x, int y, TerraformingResearchSurface surfacehas) {
      return puzzle.getSurface(x + 1, y, surfacehas.type) == surfacehas
         || puzzle.getSurface(x - 1, y, surfacehas.type) == surfacehas
         || puzzle.getSurface(x, y + 1, surfacehas.type) == surfacehas
         || puzzle.getSurface(x, y - 1, surfacehas.type) == surfacehas;
   }

   public TerraformingResearchSurface setRenders(TRRenderer... renderers) {
      this.renderer = renderers;
      return this;
   }

   public boolean canReplace(TerraformingResearchSurface another) {
      return another == null;
   }

   public static class TFRSurfaceChange {
      public TerraformingResearchSurface surfaceTo;
      public int priority;

      public TFRSurfaceChange(TerraformingResearchSurface surfaceTo, int priority) {
         this.surfaceTo = surfaceTo;
         this.priority = priority;
      }

      public void applyChange(TerraformingResearchPuzzle puzzle, int x, int y) {
         puzzle.setSurface(x, y, this.surfaceTo);
      }
   }

   public static enum TRSurfaceType {
      TERRAIN,
      ATMOSPHERE,
      CREATURE;
   }
}
