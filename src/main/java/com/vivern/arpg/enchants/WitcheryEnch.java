package com.vivern.arpg.enchants;

import com.vivern.arpg.elements.IWeapon;
import com.vivern.arpg.main.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class WitcheryEnch extends Enchantment {
   public WitcheryEnch() {
      super(Rarity.RARE, EnchantmentInit.enchantmentTypeWeapon, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
      this.setName("Witchery");
      this.setRegistryName("arpg:witchery_ench");
      EnchantmentInit.ENCHANTMENTSLIST.add(this);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 10 + (enchantmentLevel - 1) * 15;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + 30;
   }

   public int getMaxLevel() {
      return 5;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return stack.getItem() instanceof IWeapon;
   }
}
