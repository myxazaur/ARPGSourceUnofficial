package com.vivern.arpg.enchants;

import com.vivern.arpg.elements.IWeapon;
import com.vivern.arpg.main.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class SorceryEnch extends Enchantment {
   public SorceryEnch() {
      super(Rarity.RARE, EnchantmentInit.enchantmentTypeWeapon, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
      this.setName("Sorcery");
      this.setRegistryName("arpg:sorcery_ench");
      EnchantmentInit.ENCHANTMENTSLIST.add(this);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 6 + (enchantmentLevel - 1) * 14;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return 50;
   }

   public int getMaxLevel() {
      return 4;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return stack.getItem() instanceof IWeapon;
   }
}
