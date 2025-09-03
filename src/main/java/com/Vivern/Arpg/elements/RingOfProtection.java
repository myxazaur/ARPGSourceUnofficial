//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.Vivern.Arpg.main.IAttributedBauble;
import com.Vivern.Arpg.main.PropertiesRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RingOfProtection extends Item implements IBauble, IAttributedBauble {
   public RingOfProtection() {
      this.setRegistryName("ring_of_protection");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("ring_of_protection");
      this.setMaxDamage(500);
      this.setMaxStackSize(1);
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.RING;
   }

   @Override
   public IAttribute getAttribute() {
      return PropertiesRegistry.ARMOR_PROTECTION;
   }

   @Override
   public double value() {
      return 1.0;
   }

   @Override
   public int operation() {
      return 0;
   }

   @Override
   public String itemName() {
      return "ring_of_protection";
   }
}
