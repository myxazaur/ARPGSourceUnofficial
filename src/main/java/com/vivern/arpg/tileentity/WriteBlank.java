package com.vivern.arpg.tileentity;

import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.Spell;
import com.vivern.arpg.recipes.WriteGraph;
import net.minecraft.client.gui.GuiScreen;

public class WriteBlank {
   public byte[][] pixels;
   public int sizeX;
   public int sizeY;
   public static int blackThreshold = 0;

   public WriteBlank(int sizeX, int sizeY) {
      this.pixels = new byte[sizeX][sizeY];
      this.sizeX = sizeX;
      this.sizeY = sizeY;

      for (int xx = 0; xx < sizeX; xx++) {
         for (int yy = 0; yy < sizeY; yy++) {
            this.pixels[xx][yy] = -128;
         }
      }
   }

   public double[] analyzeBlank(int fraction, float[][] analyzeTo) {
      if (this.sizeX % fraction == 0 && this.sizeY % fraction == 0) {
         double result = 0.0;
         int cells = 0;
         int filledCells = 0;
         int quadsX = this.sizeX / fraction;
         int quadsY = this.sizeY / fraction;

         for (int qx = 0; qx < quadsX; qx++) {
            for (int qy = 0; qy < quadsY; qy++) {
               int summ = Math.min(this.summOfQuad(qx * fraction, qy * fraction, fraction), 4000);
               result += analyzeTo[qx][qy] * summ;
               if (analyzeTo[qx][qy] >= 1.0F) {
                  cells++;
                  if (summ >= 255) {
                     filledCells++;
                  }
               }
            }
         }

         return new double[]{result, (double)filledCells / cells};
      } else {
         return new double[]{0.0, 0.0};
      }
   }

   public int summOfQuad(int x, int y, int size) {
      int summ = size * size * 128;
      int toX = x + size;
      int toY = y + size;

      for (int xx = x; xx < toX; xx++) {
         for (int yy = y; yy < toY; yy++) {
            summ += this.pixels[xx][yy];
         }
      }

      return summ;
   }

   public void renderInGui(GuiScreen gui, int xcoord, int ycoord) {
      this.renderInGui(gui, xcoord, ycoord, 0, this.sizeX, 0, this.sizeY);
   }

   public void renderInGui(GuiScreen gui, int xcoord, int ycoord, int renderFromX, int renderToX, int renderFromY, int renderToY) {
      int toX = Math.min(this.sizeX, renderToX);
      int toY = Math.min(this.sizeY, renderToY);

      for (int xx = renderFromX; xx < toX; xx++) {
         for (int yy = renderFromY; yy < toY; yy++) {
            int x = xcoord + xx;
            int y = ycoord + yy;
            GuiScreen.drawRect(x, y, x + 1, y + 1, ColorConverters.RGBAtoDecimal255(0, 0, 0, this.pixels[xx][yy] + 128));
         }
      }
   }

   public void spot(int x, int y) {
      if (x >= 0 && x < this.sizeX && y >= 0 && y < this.sizeY) {
         this.pixels[x][y] = 127;
         if (x + 1 < this.sizeX) {
            this.pixels[x + 1][y] = (byte)Math.min(this.pixels[x + 1][y] + 50, 127);
         }

         if (x > 0) {
            this.pixels[x - 1][y] = (byte)Math.min(this.pixels[x - 1][y] + 50, 127);
         }

         if (y + 1 < this.sizeY) {
            this.pixels[x][y + 1] = (byte)Math.min(this.pixels[x][y + 1] + 50, 127);
         }

         if (y > 0) {
            this.pixels[x][y - 1] = (byte)Math.min(this.pixels[x][y - 1] + 50, 127);
         }
      }
   }

   public void spot(int x, int y, int radius) {
      if (Debugger.floats[8] > 0.0F && Debugger.floats[9] > 0.0F) {
         WriteGraph.printCircle(x, y, (int)Debugger.floats[8], (int)Debugger.floats[9]);
      }

      int fromX = Math.max(0, x - radius);
      int fromY = Math.max(0, y - radius);
      int toX = Math.min(this.sizeX - 1, x + radius);
      int toY = Math.min(this.sizeY - 1, y + radius);

      for (int xx = fromX; xx <= toX; xx++) {
         for (int yy = fromY; yy <= toY; yy++) {
            float dx = xx - x;
            float dy = yy - y;
            double dist = Math.sqrt(dx * dx + dy * dy);
            float inkAdd = 70.0F + Debugger.floats[0];
            if (dist <= radius) {
               this.pixels[xx][yy] = (byte)Math.min(this.pixels[xx][yy] + inkAdd / Math.max(1.0, dist), 127.0);
            }
         }
      }
   }

   public boolean hasBlackInDash(int x, int y, int radius, boolean horizontal) {
      if (horizontal) {
         if (y >= 0 && y < this.sizeY) {
            int min = Math.max(x - radius, 0);
            int max = Math.min(x + radius, this.sizeX - 1);

            for (int i = min; i <= max; i++) {
               if (this.pixels[i][y] > blackThreshold) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      } else if (x >= 0 && x < this.sizeX) {
         int min = Math.max(y - radius, 0);
         int max = Math.min(y + radius, this.sizeY - 1);

         for (int ix = min; ix <= max; ix++) {
            if (this.pixels[x][ix] > blackThreshold) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean hasBlackLine(int fromX, int fromY, int toX, int toY, int radiusFrom, int radiusTo) {
      float diffX = toX - fromX;
      float diffY = toY - fromY;
      if (toX > fromX) {
         float yDispl = diffY / Math.abs(diffX);
         float radiusDispl = (radiusTo - radiusFrom) / Math.abs(diffX);
         int counter = 0;

         for (int x = fromX; x <= toX; x++) {
            int y = Math.round(fromY + yDispl * counter);
            int radius = Math.round(radiusFrom + radiusDispl * counter);
            if (!this.hasBlackInDash(x, y, radius, false)) {
               return false;
            }

            counter++;
         }
      } else if (toX < fromX) {
         float yDispl = diffY / Math.abs(diffX);
         float radiusDispl = (radiusTo - radiusFrom) / Math.abs(diffX);
         int counter = 0;

         for (int x = fromX; x >= toX; x--) {
            int y = Math.round(fromY + yDispl * counter);
            int radius = Math.round(radiusFrom + radiusDispl * counter);
            if (!this.hasBlackInDash(x, y, radius, false)) {
               return false;
            }

            counter++;
         }
      }

      if (toY > fromY) {
         float xDispl = diffX / Math.abs(diffY);
         float radiusDispl = (radiusTo - radiusFrom) / Math.abs(diffY);
         int counter = 0;

         for (int y = fromY; y <= toY; y++) {
            int x = Math.round(fromX + xDispl * counter);
            int radius = Math.round(radiusFrom + radiusDispl * counter);
            if (!this.hasBlackInDash(x, y, radius, true)) {
               return false;
            }

            counter++;
         }
      } else if (toY < fromY) {
         float xDispl = diffX / Math.abs(diffY);
         float radiusDispl = (radiusTo - radiusFrom) / Math.abs(diffY);
         int counter = 0;

         for (int y = fromY; y >= toY; y--) {
            int x = Math.round(fromX + xDispl * counter);
            int radius = Math.round(radiusFrom + radiusDispl * counter);
            if (!this.hasBlackInDash(x, y, radius, true)) {
               return false;
            }

            counter++;
         }
      }

      return true;
   }

   public boolean hasBlackInRound(int x, int y, int radius) {
      int radiusSq = radius * radius;
      int minX = Math.max(x - radius, 0);
      int maxX = Math.min(x + radius, this.sizeX - 1);
      int minY = Math.max(y - radius, 0);
      int maxY = Math.min(y + radius, this.sizeY - 1);

      for (int xx = minX; xx <= maxX; xx++) {
         for (int yy = minY; yy <= maxY; yy++) {
            int relX = xx - x;
            int relY = yy - y;
            int distSq = relX * relX + relY * relY;
            if (distSq <= radiusSq && this.pixels[xx][yy] > blackThreshold) {
               return true;
            }
         }
      }

      return false;
   }

   public static class WriteBlankSpell extends WriteBlank {
      public Spell averageSuitableSpell;
      public boolean wasted = false;

      public WriteBlankSpell(int sizeX, int sizeY) {
         super(sizeX, sizeY);
      }
   }
}
