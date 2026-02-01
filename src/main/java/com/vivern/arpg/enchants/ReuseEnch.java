package com.vivern.arpg.enchants;

import com.vivern.arpg.elements.IWeapon;
import com.vivern.arpg.main.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ReuseEnch extends Enchantment {
   public ReuseEnch() {
      super(Rarity.RARE, EnchantmentInit.enchantmentTypeWeapon, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
      this.setName("Reuse");
      this.setRegistryName("arpg:reuse_ench");
      EnchantmentInit.ENCHANTMENTSLIST.add(this);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 20 + (enchantmentLevel - 1) * 10;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return 60;
   }

   public int getMaxLevel() {
      return 2;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return stack.getItem() instanceof IWeapon;
   }
}
