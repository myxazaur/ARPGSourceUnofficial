package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.recipes.EnergyCost;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;

public interface IVialElementsAccepter {
   float acceptVialElements(ItemStack var1, ShardType var2, float var3);

   float getElementCount(ShardType var1);

   @Nullable
   default EnergyCost provideVialElements(ItemStack vial_or_spellRod, float amount, boolean dontDecreaseIfLow) {
      return null;
   }
}
