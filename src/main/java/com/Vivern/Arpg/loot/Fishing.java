package com.Vivern.Arpg.loot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class Fishing {
   public static final ResourceLocation fishing1 = new ResourceLocation("arpg:loot/fishing/fishing.json");
   public static List<FishingTreasure> list1 = new ArrayList<>();
   public static List<Bait> list2 = new ArrayList<>();
   public static Random rand = new Random();

   public static void add(FishingTreasure treas) {
      list1.add(treas);
   }

   public static void addb(Bait bait) {
      list2.add(bait);
   }

   public static float getBaitPower(Item item) {
      if (item == null) {
         return 0.0F;
      } else {
         for (Bait bait : list2) {
            if (bait.item == item) {
               return bait.power;
            }
         }

         return 0.0F;
      }
   }

   public static Bait getBait(Item item) {
      if (item == null) {
         return null;
      } else {
         for (Bait bait : list2) {
            if (bait.item == item) {
               return bait;
            }
         }

         return null;
      }
   }

   public static FishingTreasure getActualCapturedFish(Block liquid, Biome biome, float depth) {
      Collections.shuffle(list1);

      for (FishingTreasure treasure : list1) {
         if (treasure.liquid == liquid && (treasure.biome == null || treasure.biome == biome) && treasure.depth <= depth && rand.nextFloat() <= treasure.chance
            )
          {
            return treasure;
         }
      }

      return null;
   }

   public static void initFish() {
   }

   public static boolean checkString(String string) {
      if (string != null && string.length() != 0) {
         int i = 0;
         if (string.charAt(0) == '-') {
            if (string.length() == 1) {
               return false;
            }

            i = 1;
         }

         while (i < string.length()) {
            char c = string.charAt(i);
            if (c < '0' || c > '9') {
               return false;
            }

            i++;
         }

         return true;
      } else {
         return false;
      }
   }
}
