//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.Vivern.Arpg.main.IAttributedBauble;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SpikeRing extends Item implements IBauble, IAttributedBauble {
   public SpikeRing() {
      this.setRegistryName("spike_ring");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("spike_ring");
      this.setMaxDamage(1000);
      this.setMaxStackSize(1);
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.RING;
   }

   @Override
   public IAttribute getAttribute() {
      return SharedMonsterAttributes.ATTACK_DAMAGE;
   }

   @Override
   public double value() {
      return 3.0;
   }

   @Override
   public int operation() {
      return 0;
   }

   @Override
   public String itemName() {
      return "spike_ring";
   }
}
