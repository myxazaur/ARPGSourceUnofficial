package com.Vivern.Arpg.elements.armor;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface IItemKilling {
   void onKillWithItem(ItemStack var1, EntityPlayer var2, EntityLivingBase var3, DamageSource var4);
}
