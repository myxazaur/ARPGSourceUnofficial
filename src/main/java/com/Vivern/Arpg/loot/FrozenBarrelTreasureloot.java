package com.Vivern.Arpg.loot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FrozenBarrelTreasureloot {
   public static List<Treasure> list1 = new ArrayList<>();
   static int a = 0;

   public static ItemStack getStack(Random rand) {
      Collections.shuffle(list1);

      for (Treasure treasure : list1) {
         if (rand.nextFloat() <= treasure.chance) {
            return new ItemStack(treasure.item, treasure.mincount + rand.nextInt(treasure.maxcount - treasure.mincount + 1), treasure.damage);
         }
      }

      return ItemStack.EMPTY;
   }

   public static void init() {
      if (a != 0) {
         try {
            File file2 = new File("/Users/Vivern/Desktop/Modding/Arpg/src/main/resources/assets/arpg/loot/breaktile/frozen_treasure_barrel.txt");
            FileReader fr2 = new FileReader(file2);
            BufferedReader reader2 = new BufferedReader(fr2);

            for (String line2 = reader2.readLine(); line2 != null; line2 = reader2.readLine()) {
               String[] words = line2.split(" ");
               Item item = Item.getByNameOrId(words[0]);
               int min = Integer.parseInt(words[1]);
               int max = Integer.parseInt(words[2]);
               float chance = Float.parseFloat(words[3]);
               Treasure treasure = new Treasure(item, chance, 0, min, max);
               list1.add(treasure);
            }
         } catch (FileNotFoundException var10) {
            var10.printStackTrace();
         } catch (IOException var11) {
            var11.printStackTrace();
         }
      }
   }
}
