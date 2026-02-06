package com.Vivern.Arpg.elements.armor;

import com.Vivern.Arpg.elements.models.AdamantiumArmorModel;
import com.Vivern.Arpg.elements.models.BoneArmorModel;
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

public class AdamantiumHelm extends AbstractArmor {
   public static AdamantiumArmorModel armormodel = new AdamantiumArmorModel();
   public static String armortexture = "arpg:textures/adamantium_armor_model_tex.png";

   public AdamantiumHelm() {
      super(EntityEquipmentSlot.HEAD, "adamantium_armor_helmet", 3000, 6);
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
            SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", -0.005, 0)
         );
         multimap.put(
            SharedMonsterAttributes.ATTACK_SPEED.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0)
         );
         multimap.put(
            PropertiesRegistry.ARMOR_PROTECTION.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.0, 0)
         );
         multimap.put(
            SharedMonsterAttributes.MAX_HEALTH.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 1.0, 0)
         );
         multimap.put(
            SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.1, 0)
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

   public static class AdamantiumBoots extends AbstractArmor {
      public AdamantiumBoots() {
         super(EntityEquipmentSlot.FEET, "adamantium_armor_boots", 3000, 6);
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
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", -0.005, 0)
            );
            multimap.put(
               SharedMonsterAttributes.ATTACK_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 1.5, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MAX_HEALTH.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 1.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.1, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            AdamantiumHelm.armormodel.setModelAttributes(model);
            AdamantiumHelm.armormodel.helm.showModel = false;
            AdamantiumHelm.armormodel.chest.showModel = false;
            AdamantiumHelm.armormodel.rightarm.showModel = false;
            AdamantiumHelm.armormodel.leftarm.showModel = false;
            AdamantiumHelm.armormodel.rightleg.showModel = false;
            AdamantiumHelm.armormodel.leftleg.showModel = false;
            AdamantiumHelm.armormodel.rightboot.showModel = true;
            AdamantiumHelm.armormodel.leftboot.showModel = true;
            return AdamantiumHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return AdamantiumHelm.armortexture;
      }
   }

   public static class AdamantiumChestplate extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public AdamantiumChestplate() {
         super(EntityEquipmentSlot.CHEST, "adamantium_armor_chestplate", 4000, 6);
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
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", -0.01, 0)
            );
            multimap.put(
               SharedMonsterAttributes.ATTACK_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.5, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MAX_HEALTH.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 1.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.3, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            AdamantiumHelm.armormodel.setModelAttributes(model);
            AdamantiumHelm.armormodel.helm.showModel = false;
            AdamantiumHelm.armormodel.chest.showModel = true;
            AdamantiumHelm.armormodel.rightarm.showModel = true;
            AdamantiumHelm.armormodel.leftarm.showModel = true;
            AdamantiumHelm.armormodel.rightleg.showModel = false;
            AdamantiumHelm.armormodel.leftleg.showModel = false;
            AdamantiumHelm.armormodel.rightboot.showModel = false;
            AdamantiumHelm.armormodel.leftboot.showModel = false;
            return AdamantiumHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return AdamantiumHelm.armortexture;
      }
   }

   public static class AdamantiumLeggins extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public AdamantiumLeggins() {
         super(EntityEquipmentSlot.LEGS, "adamantium_armor_leggins", 3500, 6);
      }

      @Override
      public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            multimap.put(
               SharedMonsterAttributes.ARMOR.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 3.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", -0.01, 0)
            );
            multimap.put(
               SharedMonsterAttributes.ATTACK_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MAX_HEALTH.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 1.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.2, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            AdamantiumHelm.armormodel.setModelAttributes(model);
            AdamantiumHelm.armormodel.helm.showModel = false;
            AdamantiumHelm.armormodel.chest.showModel = false;
            AdamantiumHelm.armormodel.rightarm.showModel = false;
            AdamantiumHelm.armormodel.leftarm.showModel = false;
            AdamantiumHelm.armormodel.rightleg.showModel = true;
            AdamantiumHelm.armormodel.leftleg.showModel = true;
            AdamantiumHelm.armormodel.rightboot.showModel = false;
            AdamantiumHelm.armormodel.leftboot.showModel = false;
            return AdamantiumHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return AdamantiumHelm.armortexture;
      }
   }
}
