package com.vivern.arpg.enchants;

import com.vivern.arpg.elements.IWeapon;
import com.vivern.arpg.main.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ReloadingEnch extends Enchantment {
   public ReloadingEnch() {
      super(Rarity.RARE, EnchantmentInit.enchantmentTypeWeapon, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
      this.setName("Reloading");
      this.setRegistryName("arpg:reloading_ench");
      EnchantmentInit.ENCHANTMENTSLIST.add(this);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 8 + (enchantmentLevel - 1) * 10;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + 20;
   }

   public int getMaxLevel() {
      return 2;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return stack.getItem() instanceof IWeapon;
   }
}
