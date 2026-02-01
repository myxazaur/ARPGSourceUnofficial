package com.vivern.arpg.elements.armor;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface IItemDamaging {
   float onCauseDamageWithItem(float var1, ItemStack var2, EntityPlayer var3, EntityLivingBase var4, DamageSource var5);
}
