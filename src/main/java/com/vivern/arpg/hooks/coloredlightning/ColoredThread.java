package com.vivern.arpg.hooks.coloredlightning;

import com.vivern.arpg.events.Debugger;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayDeque;

public class ColoredThread extends Thread {
   public ArrayDeque<ColoredThreadTask> tasks = new ArrayDeque<>();

   public void addTask(int x, int y, int z, boolean isChunk, World world) {
      if (!isChunk) {
         this.tasks.offer(new ColoredThreadTask(x, y, z, isChunk, world));
      }
   }

   @Override
   public void run() {
      while (true) {
         ColoredThreadTask next = this.tasks.poll();
         if (next != null) {
            if (next.isChunk) {
               ColoredLightning.calculateColorsInWholeChunk(next.world, next.x, next.z);
            } else {
               ColoredLightning.calculateColorsInRangeAt(next.world, new BlockPos(next.x, next.y, next.z));
            }
         }

         try {
            sleep((long)Debugger.floats[1] + 1000L);
            System.out.println("wait");
         } catch (InterruptedException var3) {
            var3.printStackTrace();
            return;
         }
      }
   }

   public static class ColoredThreadTask {
      public int x;
      public int y;
      public int z;
      public boolean isChunk;
      public World world;

      public ColoredThreadTask(int x, int y, int z, boolean isChunk, World world) {
         this.x = x;
         this.y = y;
         this.z = z;
         this.isChunk = isChunk;
         this.world = world;
      }
   }
}
