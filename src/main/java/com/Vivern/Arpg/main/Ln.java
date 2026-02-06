package com.Vivern.Arpg.main;

import com.Vivern.Arpg.elements.IWeapon;
import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;

public class Ln {
   public static String translate(String input) {
      return I18n.translateToLocal(input);
   }

   public static String localizedName(Item input) {
      return I18n.translateToLocal(input.getTranslationKey());
   }

   public static void printDescriptionsSample() {
      System.out.print("\n");

      for (Item item : Item.REGISTRY) {
         if (item instanceof IWeapon) {
            String name = item.getRegistryName().getPath();
            System.out.print("description." + name + "=");
            System.out.print("\n");
            System.out.print("descspecial." + name + "=");
            System.out.print("\n");
         }
      }

      System.out.print("\n");
   }

   public static boolean canTranslate(String input) {
      return I18n.canTranslate(input);
   }
}
