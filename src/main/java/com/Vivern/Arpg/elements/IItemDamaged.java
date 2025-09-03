package com.Vivern.Arpg.elements;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface IItemDamaged {
   float onDamagedWithItem(float var1, ItemStack var2, EntityPlayer var3, DamageSource var4);

   default boolean cancelOnNull() {
      return false;
   }
}
