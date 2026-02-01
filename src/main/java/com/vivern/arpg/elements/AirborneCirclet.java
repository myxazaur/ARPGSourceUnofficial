package com.vivern.arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.vivern.arpg.main.IAttributedBauble;
import com.vivern.arpg.main.PropertiesRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AirborneCirclet extends Item implements IBauble, IAttributedBauble {
   public AirborneCirclet() {
      this.setRegistryName("airborne_circlet");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("airborne_circlet");
      this.setMaxStackSize(1);
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.RING;
   }

   @Override
   public IAttribute getAttribute() {
      return PropertiesRegistry.AIRBORNE_MOBILITY;
   }

   @Override
   public double value() {
      return 0.05;
   }

   @Override
   public int operation() {
      return 0;
   }

   @Override
   public String itemName() {
      return "airborne_circlet";
   }
}
