//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.World;

public class WoodenSkiing extends ItemArmor {
   public WoodenSkiing() {
      super(ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.FEET);
      this.setRegistryName("wooden_skiing");
      this.setTranslationKey("wooden_skiing");
      this.setMaxDamage(250);
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.TRANSPORTATION);
      BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      return "arpg:textures/ice_armor_1.png";
   }

   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (itemSlot == 0 && entityIn.onGround) {
         entityIn.motionX *= 1.5;
         entityIn.motionZ *= 1.5;
      }
   }
}
