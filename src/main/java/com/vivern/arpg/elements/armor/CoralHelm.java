package com.vivern.arpg.elements.armor;

import com.vivern.arpg.elements.models.BoneArmorModel;
import com.vivern.arpg.elements.models.CoralArmorModel;
import com.vivern.arpg.main.PropertiesRegistry;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CoralHelm extends AbstractArmor {
   public static CoralArmorModel armormodel = new CoralArmorModel();
   public static String armortexture = "arpg:textures/coral_armor_model_tex.png";

   public CoralHelm() {
      super(EntityEquipmentSlot.HEAD, "coral_armor_helmet", 2300, 6);
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
            PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 15.0, 0)
         );
         multimap.put(
            PropertiesRegistry.MANASPEED_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.2, 0)
         );
         multimap.put(
            PropertiesRegistry.ARMOR_PROTECTION.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 1.0, 0)
         );
         multimap.put(EntityPlayer.SWIM_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor swimming", 0.2, 0));
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

   public static class CoralBoots extends AbstractArmor {
      public CoralBoots() {
         super(EntityEquipmentSlot.FEET, "coral_armor_boots", 2250, 6);
      }

      @Override
      public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            multimap.put(
               SharedMonsterAttributes.ARMOR.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 1.0, 0)
            );
            multimap.put(
               PropertiesRegistry.MAGIC_POWER_MAX.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor magic", 0.1, 0)
            );
            multimap.put(
               PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 10.0, 0)
            );
            multimap.put(
               PropertiesRegistry.MANASPEED_MAX.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.1, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 1.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.02, 0)
            );
            multimap.put(
               EntityPlayer.SWIM_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor swimming", 0.25, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            CoralHelm.armormodel.setModelAttributes(model);
            CoralHelm.armormodel.helm.showModel = false;
            CoralHelm.armormodel.chest.showModel = false;
            CoralHelm.armormodel.rightarm.showModel = false;
            CoralHelm.armormodel.leftarm.showModel = false;
            CoralHelm.armormodel.rightleg.showModel = false;
            CoralHelm.armormodel.leftleg.showModel = false;
            CoralHelm.armormodel.rightboot.showModel = true;
            CoralHelm.armormodel.leftboot.showModel = true;
            return CoralHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return CoralHelm.armortexture;
      }
   }

   public static class CoralChestplate extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public CoralChestplate() {
         super(EntityEquipmentSlot.CHEST, "coral_armor_chestplate", 2600, 6);
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
               PropertiesRegistry.MAGIC_POWER_MAX.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor magic", 0.1, 0)
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
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.5, 0)
            );
            multimap.put(
               EntityPlayer.SWIM_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor swimming", 0.2, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            CoralHelm.armormodel.setModelAttributes(model);
            CoralHelm.armormodel.helm.showModel = false;
            CoralHelm.armormodel.chest.showModel = true;
            CoralHelm.armormodel.rightarm.showModel = true;
            CoralHelm.armormodel.leftarm.showModel = true;
            CoralHelm.armormodel.rightleg.showModel = false;
            CoralHelm.armormodel.leftleg.showModel = false;
            CoralHelm.armormodel.rightboot.showModel = false;
            CoralHelm.armormodel.leftboot.showModel = false;
            return CoralHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return CoralHelm.armortexture;
      }
   }

   public static class CoralLeggins extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public CoralLeggins() {
         super(EntityEquipmentSlot.LEGS, "coral_armor_leggins", 2300, 6);
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
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 1.0, 0)
            );
            multimap.put(
               EntityPlayer.SWIM_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor swimming", 0.25, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            CoralHelm.armormodel.setModelAttributes(model);
            CoralHelm.armormodel.helm.showModel = false;
            CoralHelm.armormodel.chest.showModel = false;
            CoralHelm.armormodel.rightarm.showModel = false;
            CoralHelm.armormodel.leftarm.showModel = false;
            CoralHelm.armormodel.rightleg.showModel = true;
            CoralHelm.armormodel.leftleg.showModel = true;
            CoralHelm.armormodel.rightboot.showModel = false;
            CoralHelm.armormodel.leftboot.showModel = false;
            return CoralHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return CoralHelm.armortexture;
      }
   }
}
