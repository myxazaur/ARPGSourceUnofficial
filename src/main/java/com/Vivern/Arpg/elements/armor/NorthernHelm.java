package com.Vivern.Arpg.elements.armor;

import com.Vivern.Arpg.elements.models.BoneArmorModel;
import com.Vivern.Arpg.elements.models.NorthernArmorModel;
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

public class NorthernHelm extends AbstractArmor {
   public static NorthernArmorModel armormodel = new NorthernArmorModel();
   public static String armortexture = "arpg:textures/northern_armor_model_tex.png";

   public NorthernHelm() {
      super(EntityEquipmentSlot.HEAD, "northern_armor_helmet", 2330, 6);
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
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", -0.05, 1)
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

   public static class NorthernBoots extends AbstractArmor {
      public NorthernBoots() {
         super(EntityEquipmentSlot.FEET, "northern_armor_boots", 2230, 6);
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
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", -0.03, 1)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 1.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MAX_HEALTH.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 2.0, 0)
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
            NorthernHelm.armormodel.setModelAttributes(model);
            NorthernHelm.armormodel.helm.showModel = false;
            NorthernHelm.armormodel.chest.showModel = false;
            NorthernHelm.armormodel.rightarm.showModel = false;
            NorthernHelm.armormodel.leftarm.showModel = false;
            NorthernHelm.armormodel.rightleg.showModel = false;
            NorthernHelm.armormodel.leftleg.showModel = false;
            NorthernHelm.armormodel.rightboot.showModel = true;
            NorthernHelm.armormodel.leftboot.showModel = true;
            return NorthernHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return NorthernHelm.armortexture;
      }
   }

   public static class NorthernChestplate extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public NorthernChestplate() {
         super(EntityEquipmentSlot.CHEST, "northern_armor_chestplate", 2760, 6);
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
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", -0.08, 1)
            );
            multimap.put(
               SharedMonsterAttributes.ATTACK_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.2, 0)
            );
            multimap.put(
               SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor damage", 0.1, 2)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MAX_HEALTH.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 3.0, 0)
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
            NorthernHelm.armormodel.setModelAttributes(model);
            NorthernHelm.armormodel.helm.showModel = false;
            NorthernHelm.armormodel.chest.showModel = true;
            NorthernHelm.armormodel.rightarm.showModel = true;
            NorthernHelm.armormodel.leftarm.showModel = true;
            NorthernHelm.armormodel.rightleg.showModel = false;
            NorthernHelm.armormodel.leftleg.showModel = false;
            NorthernHelm.armormodel.rightboot.showModel = false;
            NorthernHelm.armormodel.leftboot.showModel = false;
            return NorthernHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return NorthernHelm.armortexture;
      }
   }

   public static class NorthernLeggins extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public NorthernLeggins() {
         super(EntityEquipmentSlot.LEGS, "northern_armor_leggins", 2130, 6);
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
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 1.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MAX_HEALTH.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 2.0, 0)
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
            NorthernHelm.armormodel.setModelAttributes(model);
            NorthernHelm.armormodel.helm.showModel = false;
            NorthernHelm.armormodel.chest.showModel = false;
            NorthernHelm.armormodel.rightarm.showModel = false;
            NorthernHelm.armormodel.leftarm.showModel = false;
            NorthernHelm.armormodel.rightleg.showModel = true;
            NorthernHelm.armormodel.leftleg.showModel = true;
            NorthernHelm.armormodel.rightboot.showModel = false;
            NorthernHelm.armormodel.leftboot.showModel = false;
            return NorthernHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return NorthernHelm.armortexture;
      }
   }
}
