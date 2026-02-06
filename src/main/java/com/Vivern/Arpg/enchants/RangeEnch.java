package com.Vivern.Arpg.enchants;

import com.Vivern.Arpg.elements.IWeapon;
import com.Vivern.Arpg.main.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class RangeEnch extends Enchantment {
   public RangeEnch() {
      super(Rarity.UNCOMMON, EnchantmentInit.enchantmentTypeWeapon, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
      this.setName("Range");
      this.setRegistryName("arpg:range_ench");
      EnchantmentInit.ENCHANTMENTSLIST.add(this);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 8 + (enchantmentLevel - 1) * 12;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + 25;
   }

   public int getMaxLevel() {
      return 4;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return stack.getItem() instanceof IWeapon;
   }
}
