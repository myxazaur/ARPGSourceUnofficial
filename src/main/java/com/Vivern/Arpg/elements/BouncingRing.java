package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.Vivern.Arpg.main.IAttributedBauble;
import com.Vivern.Arpg.main.PropertiesRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BouncingRing extends Item implements IBauble, IAttributedBauble {
   public BouncingRing() {
      this.setRegistryName("bouncing_ring");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("bouncing_ring");
      this.setMaxDamage(1000);
      this.setMaxStackSize(1);
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.RING;
   }

   @Override
   public IAttribute getAttribute() {
      return PropertiesRegistry.JUMP_HEIGHT;
   }

   @Override
   public double value() {
      return 0.1;
   }

   @Override
   public int operation() {
      return 0;
   }

   @Override
   public String itemName() {
      return "bouncing_ring";
   }
}
