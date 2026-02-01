package com.vivern.arpg.recipes;

import java.lang.reflect.Field;
import java.util.HashMap;
import javax.annotation.Nullable;

public class Soul {
   public static HashMap<Integer, Soul> soulRegistry = new HashMap<>();
   public static Soul EMPTY = new Soul(0, 0.0F);
   public static Soul COMMON = new Soul(1, 50.0F);
   public static Soul EXHAUSTED = new Soul(2, 250.0F);
   public static Soul STRANGER = new Soul(3, 80.0F);
   public static Soul GREEDY = new Soul(4, 240.0F);
   public static Soul WINTRY = new Soul(5, 320.0F);
   public static Soul MUTATED = new Soul(6, 400.0F);
   public static Soul MYSTIC = new Soul(7, 270.0F);
   public static Soul PALE = new Soul(8, 25.0F);
   public static Soul NOBLE = new Soul(9, 2250.0F);
   public static Soul COLLECTIVE = new Soul(10, 500.0F);
   public static Soul SHADOW = new Soul(11, 470.0F);
   public static Soul WORSHIPPING = new Soul(12, 650.0F);
   public static Soul SINFUL = new Soul(13, 666.0F);
   public static Soul FESTIVE = new Soul(14, 340.0F);
   public static Soul INNOCENT = new Soul(15, 150.0F);
   public static Soul DEEPWATER = new Soul(16, 575.0F);
   public static Soul IONIZED = new Soul(17, 780.0F);
   public static Soul LIGHT = new Soul(18, 750.0F);
   public int id;
   public float manaContains;
   public String name;

   public Soul(int id, float manaContains) {
      this.id = id;
      this.manaContains = manaContains;
   }

   @Override
   public String toString() {
      return this.name;
   }

   @Nullable
   public static Soul byId(int id) {
      return soulRegistry.get(id);
   }

   public boolean canCatchWithPower(int catchPower) {
      return this == WORSHIPPING ? catchPower >= 1 : true;
   }

   public static void registerSoul() throws IllegalArgumentException, IllegalAccessException {
      Field[] fields = Soul.class.getFields();

      for (Field field : fields) {
         if (field.getType() == Soul.class) {
            Soul soul = (Soul)field.get(null);
            soul.name = field.getName().toLowerCase();
            soulRegistry.put(soul.id, soul);
         }
      }
   }
}
