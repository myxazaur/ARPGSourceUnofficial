package com.vivern.arpg.elements.armor;

import com.vivern.arpg.main.PropertiesRegistry;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class SlimeHelm extends ItemArmor {
   private static final UUID[] ARMOR_MODIFIERSG = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };
   public static ArmorMaterial slime_mt = EnumHelper.addArmorMaterial(
      "arpg:slime_mt", "arpg:slime_tx", 9, new int[]{3, 4, 5, 3}, 5, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1.0F
   );

   public SlimeHelm() {
      super(slime_mt, 0, EntityEquipmentSlot.HEAD);
      this.setRegistryName("slime_helmet");
      this.setTranslationKey("slime_helmet");
      this.setMaxDamage(650);
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.COMBAT);
      BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
   }

   public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      if (equipmentSlot == this.armorType) {
         multimap.put(
            SharedMonsterAttributes.ARMOR.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0)
         );
         multimap.put(
            PropertiesRegistry.JUMP_HEIGHT.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor jumping", 0.02, 0)
         );
         multimap.put(
            SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.02, 0)
         );
      }

      return multimap;
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      return "arpg:textures/slime_armor_1.png";
   }
}
