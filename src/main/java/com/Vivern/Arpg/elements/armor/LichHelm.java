package com.Vivern.Arpg.elements.armor;

import com.Vivern.Arpg.elements.models.BoneArmorModel;
import com.Vivern.Arpg.elements.models.LichArmorModel;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LichHelm extends AbstractArmor {
   public static LichArmorModel armormodel = new LichArmorModel();
   public static String armortexture = "arpg:textures/lich_armor_model_tex.png";

   public LichHelm() {
      super(EntityEquipmentSlot.HEAD, "lich_armor_helmet", 3000, 6);
   }

   @Override
   public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      if (equipmentSlot == this.armorType) {
         multimap.put(
            SharedMonsterAttributes.ARMOR.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 2.0, 0)
         );
         multimap.put(
            PropertiesRegistry.MAGIC_POWER_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor magic", 0.2, 0)
         );
         multimap.put(
            PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 20.0, 0)
         );
         multimap.put(
            PropertiesRegistry.MANASPEED_MAX.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.15, 0)
         );
         multimap.put(
            PropertiesRegistry.ARMOR_PROTECTION.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 3.0, 0)
         );
      }

      return multimap;
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
      if (itemStack != ItemStack.EMPTY) {
         armormodel.setModelAttributes(model);
         armormodel.helm.showModel = true;
         armormodel.chest.showModel = false;
         armormodel.rightarm.showModel = false;
         armormodel.leftarm.showModel = false;
         armormodel.rightleg.showModel = false;
         armormodel.leftleg.showModel = false;
         armormodel.rightboot.showModel = false;
         armormodel.leftboot.showModel = false;
         return armormodel;
      } else {
         return null;
      }
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      return armortexture;
   }

   public static class LichBoots extends AbstractArmor {
      public LichBoots() {
         super(EntityEquipmentSlot.FEET, "lich_armor_boots", 3000, 6);
      }

      @Override
      public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            multimap.put(
               SharedMonsterAttributes.ARMOR.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 2.0, 0)
            );
            multimap.put(
               PropertiesRegistry.MAGIC_POWER_MAX.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor magic", 0.1, 0)
            );
            multimap.put(
               PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 15.0, 0)
            );
            multimap.put(
               PropertiesRegistry.MANASPEED_MAX.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.1, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.04, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            LichHelm.armormodel.setModelAttributes(model);
            LichHelm.armormodel.helm.showModel = false;
            LichHelm.armormodel.chest.showModel = false;
            LichHelm.armormodel.rightarm.showModel = false;
            LichHelm.armormodel.leftarm.showModel = false;
            LichHelm.armormodel.rightleg.showModel = false;
            LichHelm.armormodel.leftleg.showModel = false;
            LichHelm.armormodel.rightboot.showModel = true;
            LichHelm.armormodel.leftboot.showModel = true;
            return LichHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return LichHelm.armortexture;
      }
   }

   public static class LichChestplate extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public LichChestplate() {
         super(EntityEquipmentSlot.CHEST, "lich_armor_chestplate", 3500, 6);
      }

      @Override
      public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            multimap.put(
               SharedMonsterAttributes.ARMOR.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 4.0, 0)
            );
            multimap.put(
               PropertiesRegistry.MAGIC_POWER_MAX.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor magic", 0.2, 0)
            );
            multimap.put(
               PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 30.0, 0)
            );
            multimap.put(
               PropertiesRegistry.MANASPEED_MAX.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.2, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 3.5, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            LichHelm.armormodel.setModelAttributes(model);
            LichHelm.armormodel.helm.showModel = false;
            LichHelm.armormodel.chest.showModel = true;
            LichHelm.armormodel.rightarm.showModel = true;
            LichHelm.armormodel.leftarm.showModel = true;
            LichHelm.armormodel.rightleg.showModel = false;
            LichHelm.armormodel.leftleg.showModel = false;
            LichHelm.armormodel.rightboot.showModel = false;
            LichHelm.armormodel.leftboot.showModel = false;
            return LichHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return LichHelm.armortexture;
      }
   }

   public static class LichLeggins extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public LichLeggins() {
         super(EntityEquipmentSlot.LEGS, "lich_armor_leggins", 3200, 6);
      }

      @Override
      public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            multimap.put(
               SharedMonsterAttributes.ARMOR.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 2.0, 0)
            );
            multimap.put(
               PropertiesRegistry.MAGIC_POWER_MAX.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor magic", 0.15, 0)
            );
            multimap.put(
               PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 20.0, 0)
            );
            multimap.put(
               PropertiesRegistry.MANASPEED_MAX.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.1, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.0, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            LichHelm.armormodel.setModelAttributes(model);
            LichHelm.armormodel.helm.showModel = false;
            LichHelm.armormodel.chest.showModel = false;
            LichHelm.armormodel.rightarm.showModel = false;
            LichHelm.armormodel.leftarm.showModel = false;
            LichHelm.armormodel.rightleg.showModel = true;
            LichHelm.armormodel.leftleg.showModel = true;
            LichHelm.armormodel.rightboot.showModel = false;
            LichHelm.armormodel.leftboot.showModel = false;
            return LichHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return LichHelm.armortexture;
      }
   }
}
