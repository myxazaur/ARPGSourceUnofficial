package com.vivern.arpg.elements.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface IItemAttacked {
   float onAttackedWithItem(float var1, ItemStack var2, EntityPlayer var3, DamageSource var4);

   default boolean cancelOnNull() {
      return false;
   }
}
