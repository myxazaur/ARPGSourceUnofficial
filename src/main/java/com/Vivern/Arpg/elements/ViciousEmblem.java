package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.Vivern.Arpg.main.IAttributedBauble;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ViciousEmblem extends Item implements IBauble, IAttributedBauble {
   public ViciousEmblem() {
      this.setRegistryName("vicious_emblem");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("vicious_emblem");
      this.setMaxDamage(1000);
      this.setMaxStackSize(1);
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.TRINKET;
   }

   @Override
   public IAttribute getAttribute() {
      return PropertiesRegistry.VAMPIRISM;
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
      return "vicious_emblem";
   }

   @Override
   public boolean useMultimap() {
      return true;
   }

   @Override
   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
      Multimap<String, AttributeModifier> multimap = HashMultimap.create();
      UUID uuid = UUID.fromString("CB2F4" + equipmentSlot + "D3-64" + equipmentSlot + "A-4F78-A497-9C56A33DB" + equipmentSlot + "BB");
      multimap.put(PropertiesRegistry.VAMPIRISM.getName(), new AttributeModifier(uuid, "vampirism modifier", 0.05, 0));
      multimap.put(PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(uuid, "mana max modifier", 5.0, 0));
      return multimap;
   }
}
