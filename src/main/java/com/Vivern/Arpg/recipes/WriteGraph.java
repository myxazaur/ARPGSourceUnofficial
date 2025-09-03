//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.recipes;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Vec2i;
import com.Vivern.Arpg.tileentity.WriteBlank;
import net.minecraft.util.math.Vec3d;

public class WriteGraph {
   public Vec2i[] vertexes;
   public Vec2i[] links;
   public int[] sizes;
   public boolean[][] avoid;
   public int countOfAll;

   public WriteGraph(Vec2i... vertexes) {
      this.vertexes = vertexes;
      this.sizes = new int[vertexes.length];

      for (int i = 0; i < this.sizes.length; i++) {
         this.sizes[i] = defaultRadius();
      }
   }

   public WriteGraph addLinks(Vec2i... links) {
      this.links = links;
      this.countOfAll = this.vertexes.length + this.links.length;
      return this;
   }

   public WriteGraph size(int vert, int size) {
      this.sizes[vert] = size;
      return this;
   }

   public WriteGraph addsize(int vert, int addsize) {
      this.sizes[vert] = this.sizes[vert] + addsize;
      return this;
   }

   public WriteGraph calculateAvoidMatrix(int width, int height, int radiusAdd) {
      this.avoid = new boolean[width][height];

      for (Vec2i link : this.links) {
         Vec2i vert1 = this.vertexes[link.x];
         Vec2i vert2 = this.vertexes[link.y];
         int siz1 = this.sizes[link.x];
         int siz2 = this.sizes[link.y];
         setTrueInLine(this.avoid, width, height, vert1.x, vert1.y, vert2.x, vert2.y, siz1 + radiusAdd, siz2 + radiusAdd);
      }

      for (int i = 0; i < this.vertexes.length; i++) {
         Vec2i vertex = this.vertexes[i];
         int size = this.sizes[i];
         setTrueInRound(this.avoid, width, height, vertex.x, vertex.y, size + radiusAdd);
      }

      return this;
   }

   public int checkLines(WriteBlank blank) {
      int l = 0;

      for (Vec2i link : this.links) {
         Vec2i vert1 = this.vertexes[link.x];
         Vec2i vert2 = this.vertexes[link.y];
         int siz1 = this.sizes[link.x];
         int siz2 = this.sizes[link.y];
         boolean b1 = blank.hasBlackLine(vert1.x, vert1.y, vert2.x, vert2.y, siz1, siz2);
         if (b1) {
            l++;
         }
      }

      for (int i = 0; i < this.vertexes.length; i++) {
         Vec2i vertex = this.vertexes[i];
         int size = this.sizes[i];
         if (blank.hasBlackInRound(vertex.x, vertex.y, size)) {
            l++;
         }
      }

      return l;
   }

   public int checkDirt(WriteBlank blank) {
      int dirt = 0;

      for (int x = 0; x < blank.sizeX; x++) {
         for (int y = 0; y < blank.sizeY; y++) {
            if (!this.avoid[x][y]) {
               int black = blank.pixels[x][y] + 128;
               if (black > 80) {
                  dirt += black - 80;
               }
            }
         }
      }

      return dirt;
   }

   public int checkSimilarity(WriteBlank blank) {
      int sim = 0;

      for (int x = 0; x < blank.sizeX; x++) {
         for (int y = 0; y < blank.sizeY; y++) {
            if (this.avoid[x][y]) {
               sim += blank.pixels[x][y] + 128;
            } else {
               sim -= blank.pixels[x][y] + 128;
            }
         }
      }

      return sim;
   }

   public static void setTrueInDash(boolean[][] array, int sizeX, int sizeY, int x, int y, int radius, boolean horizontal) {
      int blackThreshold = 0;
      if (horizontal) {
         if (y < 0 || y >= sizeY) {
            return;
         }

         int min = Math.max(x - radius, 0);
         int max = Math.min(x + radius, sizeX - 1);

         for (int i = min; i <= max; i++) {
            array[i][y] = true;
         }
      } else {
         if (x < 0 || x >= sizeX) {
            return;
         }

         int min = Math.max(y - radius, 0);
         int max = Math.min(y + radius, sizeY - 1);

         for (int i = min; i <= max; i++) {
            array[x][i] = true;
         }
      }
   }

   public static void setTrueInLine(boolean[][] array, int sizeX, int sizeY, int fromX, int fromY, int toX, int toY, int radiusFrom, int radiusTo) {
      float diffX = toX - fromX;
      float diffY = toY - fromY;
      if (toX > fromX) {
         float yDispl = diffY / Math.abs(diffX);
         float radiusDispl = (radiusTo - radiusFrom) / Math.abs(diffX);
         int counter = 0;

         for (int x = fromX; x <= toX; x++) {
            int y = Math.round(fromY + yDispl * counter);
            int radius = Math.round(radiusFrom + radiusDispl * counter);
            setTrueInDash(array, sizeX, sizeY, x, y, radius, false);
            counter++;
         }
      } else if (toX < fromX) {
         float yDispl = diffY / Math.abs(diffX);
         float radiusDispl = (radiusTo - radiusFrom) / Math.abs(diffX);
         int counter = 0;

         for (int x = fromX; x >= toX; x--) {
            int y = Math.round(fromY + yDispl * counter);
            int radius = Math.round(radiusFrom + radiusDispl * counter);
            setTrueInDash(array, sizeX, sizeY, x, y, radius, false);
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
            setTrueInDash(array, sizeX, sizeY, x, y, radius, true);
            counter++;
         }
      } else if (toY < fromY) {
         float xDispl = diffX / Math.abs(diffY);
         float radiusDispl = (radiusTo - radiusFrom) / Math.abs(diffY);
         int counter = 0;

         for (int y = fromY; y >= toY; y--) {
            int x = Math.round(fromX + xDispl * counter);
            int radius = Math.round(radiusFrom + radiusDispl * counter);
            setTrueInDash(array, sizeX, sizeY, x, y, radius, true);
            counter++;
         }
      }
   }

   public static void setTrueInRound(boolean[][] array, int sizeX, int sizeY, int x, int y, int radius) {
      int radiusSq = radius * radius;
      int minX = Math.max(x - radius, 0);
      int maxX = Math.min(x + radius, sizeX - 1);
      int minY = Math.max(y - radius, 0);
      int maxY = Math.min(y + radius, sizeY - 1);

      for (int xx = minX; xx <= maxX; xx++) {
         for (int yy = minY; yy <= maxY; yy++) {
            int relX = xx - x;
            int relY = yy - y;
            int distSq = relX * relX + relY * relY;
            if (distSq <= radiusSq) {
               array[xx][yy] = true;
            }
         }
      }
   }

   public static void printCircle(int x, int y, int radius, int points) {
      System.out.println();
      float angle = 360.0F / points;

      for (int i = 0; i < points; i++) {
         Vec3d vec = GetMOP.YawToVec3d(angle * i);
         vec = vec.scale(radius).add(x, 0.0, y);
         System.out.print("new Vec2i(" + (int)Math.round(vec.x) + ", " + (int)Math.round(vec.z));
         if (i == points - 1) {
            System.out.print(")");
         } else {
            System.out.print("), ");
         }
      }

      System.out.println();
   }

   public static int defaultRadius() {
      return 5 + (int)Debugger.floats[2];
   }

   public void makeDebugBlank(WriteBlank blank, boolean win) {
      boolean[][] array = new boolean[blank.sizeX][blank.sizeY];

      for (Vec2i link : this.links) {
         Vec2i vert1 = this.vertexes[link.x];
         Vec2i vert2 = this.vertexes[link.y];
         int siz1 = this.sizes[link.x];
         int siz2 = this.sizes[link.y];
         setTrueInLine(array, blank.sizeX, blank.sizeY, vert1.x, vert1.y, vert2.x, vert2.y, siz1, siz2);
      }

      for (int i = 0; i < this.vertexes.length; i++) {
         Vec2i vertex = this.vertexes[i];
         int size = this.sizes[i];
         setTrueInRound(array, blank.sizeX, blank.sizeY, vertex.x, vertex.y, size);
      }

      if (win) {
         for (int x = 0; x < blank.sizeX; x++) {
            for (int y = 0; y < blank.sizeY; y++) {
               blank.pixels[x][y] = -128;
            }
         }
      }

      for (int x = 0; x < blank.sizeX; x++) {
         for (int y = 0; y < blank.sizeY; y++) {
            if (array[x][y] == win) {
               blank.pixels[x][y] = (byte)(win ? 60 : -60);
            }
         }
      }
   }
}
