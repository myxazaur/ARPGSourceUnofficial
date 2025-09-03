package com.Vivern.Arpg.recipes;

import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.OreDicHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public class GeomanticFocus {
   public static List<GeomanticFocus> registry = new ArrayList<>();
   public PyrocrystallineRecipe.OreCost[] costs;
   public String meltOutput;

   public GeomanticFocus(String meltOutput, PyrocrystallineRecipe.OreCost... costs) {
      this.costs = costs;
      this.meltOutput = meltOutput;
   }

   public static void init() {
      registerFocusType(
         new GeomanticFocus(
            "oreCopper",
            new PyrocrystallineRecipe.OreCost(OreDicHelper.DUSTSULFUR, 10),
            new PyrocrystallineRecipe.OreCost(OreDicHelper.DUSTCOPPER, 60),
            new PyrocrystallineRecipe.OreCost(OreDicHelper.DUSTGOLD, 10)
         )
      );
   }

   public static void registerFocusType(GeomanticFocus type) {
      if (!registry.contains(type)) {
         registry.add(type);
      }
   }

   public static String getMeltFromFocus(ItemStack stack) {
      String m = NBTHelper.GetNBTstring(stack, "melt");
      if (!m.isEmpty()) {
         return m;
      } else {
         for (GeomanticFocus focus : registry) {
            if (isStackFocus(focus, stack)) {
               NBTHelper.GiveNBTstring(stack, focus.meltOutput, "melt");
               return focus.meltOutput;
            }
         }

         return "";
      }
   }

   public static boolean isStackFocus(GeomanticFocus focustype, ItemStack stack) {
      for (PyrocrystallineRecipe.OreCost cost : focustype.costs) {
         if (NBTHelper.GetNBTfloat(stack, cost.name) < cost.count) {
            return false;
         }
      }

      return true;
   }
}
