//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.events;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.Vec3d;

public class AMatrix {
   public List<Vec3d> list = new ArrayList<>();
   public int strings;
   public int columns;

   public AMatrix(int strings, int columns) {
      this.strings = strings;
      this.columns = columns;
   }

   public void put(Vec3d vec) {
      if (vec.x <= this.strings
         && vec.x >= 0.0
         && vec.y <= this.columns
         && vec.y >= 0.0
         && this.get(vec.x, vec.y) == 0.0) {
         this.list.add(vec);
      }
   }

   public double get(double string, double column) {
      for (Vec3d vect : this.list) {
         if (vect.x == string && vect.y == column) {
            return vect.z;
         }
      }

      return 0.0;
   }

   public void printMatrix() {
      System.out.println("");
      System.out.println("");

      for (int si = 0; si < this.strings; si++) {
         for (int ci = 0; ci < this.columns; ci++) {
            System.out.print(this.get(si, ci) + "   ");
         }

         System.out.println("");
      }

      System.out.println("");
      System.out.println("");
   }
}
