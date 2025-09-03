//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.recipes;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Vec2i;
import java.util.Stack;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

public class SpellsRedactors {
   public static Stack<WriteGraph> writeGraphStack = new Stack<>();
   public static WriteGraph writeGraph;
   public static int prevClickedIdWriteGraph = -1;
   public static boolean ctrlZ = false;

   public static void update() {
      if (ctrlZ) {
         if (Keyboard.isKeyDown(44) && Keyboard.isKeyDown(29)) {
            if (!writeGraphStack.isEmpty()) {
               writeGraph = writeGraphStack.pop();
            }

            ctrlZ = false;
         }
      } else if (!Keyboard.isKeyDown(44) || !Keyboard.isKeyDown(29)) {
         ctrlZ = true;
      }
   }

   public static void handleMouseClick(int x, int y, int mouseButton, int specialization, boolean isShiftKeyDown) {
      System.out.println("posX " + x + " | posY " + y + " | button " + mouseButton);
      if (specialization == 3) {
         x = MathHelper.clamp(x - 87, 0, 50);
         y = MathHelper.clamp(y - 65, 0, 75);
         if (mouseButton == 0) {
            makePoint(x, y);
         }

         if (mouseButton == 1) {
            int clickedId = getIdAt(x, y, 2);
            if (prevClickedIdWriteGraph != -1 && clickedId != -1) {
               if (clickedId != prevClickedIdWriteGraph) {
                  makeLink(prevClickedIdWriteGraph, clickedId);
                  prevClickedIdWriteGraph = -1;
               }
            } else if (clickedId != -1) {
               prevClickedIdWriteGraph = clickedId;
            }
         }

         if (mouseButton == 2) {
            prevClickedIdWriteGraph = -1;
            System.out.print("\n");
            System.out.print("\n");
            System.out.print("public static WriteGraph graph = new ");
            System.out.print("\n");
            System.out.print("WriteGraph(");

            for (int i = 0; i < writeGraph.vertexes.length; i++) {
               if (i != 0) {
                  System.out.print(", ");
               }

               Vec2i vert = writeGraph.vertexes[i];
               System.out.print("new Vec2i(" + vert.x + ", " + vert.y + ")");
            }

            System.out.print(")");
            System.out.print("\n");
            System.out.print(".addLinks(");

            for (int i = 0; i < writeGraph.links.length; i++) {
               if (i != 0) {
                  System.out.print(", ");
               }

               Vec2i link = writeGraph.links[i];
               System.out.print("new Vec2i(" + link.x + ", " + link.y + ")");
            }

            System.out.print(").calculateAvoidMatrix(50, 75, avoidRadiusAdd);");
            System.out.print("\n");
            System.out.print("\n");
            if (isShiftKeyDown) {
               writeGraph = null;
               writeGraphStack.clear();
            }
         }
      }
   }

   public static void makePoint(int x, int y) {
      if (writeGraph == null) {
         writeGraph = new WriteGraph(new Vec2i(x, y));
         writeGraph.addLinks();
      } else {
         WriteGraph old = writeGraph;
         writeGraph = new WriteGraph((Vec2i[])ArrayUtils.add(old.vertexes == null ? new Vec2i[0] : old.vertexes, new Vec2i(x, y)));
         writeGraph.addLinks(old.links == null ? new Vec2i[0] : old.links);
         writeGraphStack.add(old);
      }
   }

   public static void makeLink(int id1, int id2) {
      if (writeGraph == null) {
         writeGraph = new WriteGraph();
         writeGraph.addLinks();
      } else {
         WriteGraph old = writeGraph;
         writeGraph = new WriteGraph(old.vertexes == null ? new Vec2i[0] : old.vertexes);
         writeGraph.addLinks((Vec2i[])ArrayUtils.add(old.links == null ? new Vec2i[0] : old.links, new Vec2i(id1, id2)));
         writeGraphStack.add(old);
      }
   }

   public static int getIdAt(int x, int y, int approximation) {
      for (int i = 0; i < writeGraph.vertexes.length; i++) {
         Vec2i vert = writeGraph.vertexes[i];
         if (GetMOP.approximatelyEqual(vert.x, x, approximation) && GetMOP.approximatelyEqual(vert.y, y, approximation)) {
            return i;
         }
      }

      return -1;
   }

   public static boolean isRedactorEnabled() {
      return Debugger.floats[10] == 11.0F;
   }

   public static boolean isInstantResearchWin() {
      return Debugger.floats[11] == 10.0F;
   }
}
