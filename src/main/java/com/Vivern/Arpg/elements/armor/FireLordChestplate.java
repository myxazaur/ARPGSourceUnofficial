package com.Vivern.Arpg.elements.armor;

import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.proxy.ClientProxy;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FireLordChestplate extends ItemArmor {
   private static final UUID[] ARMOR_MODIFIERSG = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };

   public FireLordChestplate() {
      super(FireLordHelm.firelord_mt, 0, EntityEquipmentSlot.CHEST);
      this.setRegistryName("firelord_chestplate");
      this.setTranslationKey("firelord_chestplate");
      this.setMaxDamage(3000);
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
            PropertiesRegistry.ARMOR_PROTECTION.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 1.5, 0)
         );
         multimap.put(
            SharedMonsterAttributes.MAX_HEALTH.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 3.0, 0)
         );
         multimap.put(
            SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.1, 0)
         );
         multimap.put(
            SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor move speed", -0.05, 2)
         );
      }

      return multimap;
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
      if (armorSlot != EntityEquipmentSlot.CHEST) {
         return null;
      } else if (itemStack != ItemStack.EMPTY) {
         ModelBiped whm = ClientProxy.firelordchestmodel;
         whm.isSneak = entityLiving.isSneaking();
         whm.isRiding = entityLiving.isRiding();
         whm.isChild = entityLiving.isChild();
         return whm;
      } else {
         return null;
      }
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      return "arpg:textures/firelord_armor_model_tex2.png";
   }
}
