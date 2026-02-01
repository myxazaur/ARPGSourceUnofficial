package com.vivern.arpg.elements.armor;

import com.vivern.arpg.elements.models.BoneArmorModel;
import com.vivern.arpg.main.PropertiesRegistry;
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

public class BoneHelm extends AbstractArmor {
   public static BoneArmorModel armormodel = new BoneArmorModel();
   public static String armortexture = "arpg:textures/bone_armor_model_tex.png";

   public BoneHelm() {
      super(EntityEquipmentSlot.HEAD, "bone_armor_helmet", 5000, 6);
   }

   @Override
   public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      if (equipmentSlot == this.armorType) {
         multimap.put(
            SharedMonsterAttributes.ARMOR.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0)
         );
         multimap.put(
            PropertiesRegistry.JUMP_HEIGHT.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor jumping", 0.04, 0)
         );
         multimap.put(
            SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.03, 0)
         );
         multimap.put(
            SharedMonsterAttributes.ATTACK_SPEED.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0)
         );
         multimap.put(
            PropertiesRegistry.ARMOR_PROTECTION.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 0.5, 0)
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

   public static class BoneBoots extends AbstractArmor {
      public BoneBoots() {
         super(EntityEquipmentSlot.FEET, "bone_armor_boots", 5000, 6);
      }

      @Override
      public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            multimap.put(
               SharedMonsterAttributes.ARMOR.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0)
            );
            multimap.put(
               PropertiesRegistry.JUMP_HEIGHT.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor jumping", 0.04, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.03, 0)
            );
            multimap.put(
               SharedMonsterAttributes.ATTACK_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 0.5, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            BoneHelm.armormodel.setModelAttributes(model);
            BoneHelm.armormodel.helm.showModel = false;
            BoneHelm.armormodel.chest.showModel = false;
            BoneHelm.armormodel.rightarm.showModel = false;
            BoneHelm.armormodel.leftarm.showModel = false;
            BoneHelm.armormodel.rightleg.showModel = false;
            BoneHelm.armormodel.leftleg.showModel = false;
            BoneHelm.armormodel.rightboot.showModel = true;
            BoneHelm.armormodel.leftboot.showModel = true;
            return BoneHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return BoneHelm.armortexture;
      }
   }

   public static class BoneChestplate extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public BoneChestplate() {
         super(EntityEquipmentSlot.CHEST, "bone_armor_chestplate", 6000, 6);
      }

      @Override
      public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            multimap.put(
               SharedMonsterAttributes.ARMOR.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0)
            );
            multimap.put(
               PropertiesRegistry.JUMP_HEIGHT.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor jumping", 0.04, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.03, 0)
            );
            multimap.put(
               SharedMonsterAttributes.ATTACK_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 0.5, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            BoneHelm.armormodel.setModelAttributes(model);
            BoneHelm.armormodel.helm.showModel = false;
            BoneHelm.armormodel.chest.showModel = true;
            BoneHelm.armormodel.rightarm.showModel = true;
            BoneHelm.armormodel.leftarm.showModel = true;
            BoneHelm.armormodel.rightleg.showModel = false;
            BoneHelm.armormodel.leftleg.showModel = false;
            BoneHelm.armormodel.rightboot.showModel = false;
            BoneHelm.armormodel.leftboot.showModel = false;
            return BoneHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return BoneHelm.armortexture;
      }
   }

   public static class BoneLeggins extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public BoneLeggins() {
         super(EntityEquipmentSlot.LEGS, "bone_armor_leggins", 5500, 6);
      }

      @Override
      public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            multimap.put(
               SharedMonsterAttributes.ARMOR.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0)
            );
            multimap.put(
               PropertiesRegistry.JUMP_HEIGHT.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor jumping", 0.04, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.03, 0)
            );
            multimap.put(
               SharedMonsterAttributes.ATTACK_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 0.5, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            BoneHelm.armormodel.setModelAttributes(model);
            BoneHelm.armormodel.helm.showModel = false;
            BoneHelm.armormodel.chest.showModel = false;
            BoneHelm.armormodel.rightarm.showModel = false;
            BoneHelm.armormodel.leftarm.showModel = false;
            BoneHelm.armormodel.rightleg.showModel = true;
            BoneHelm.armormodel.leftleg.showModel = true;
            BoneHelm.armormodel.rightboot.showModel = false;
            BoneHelm.armormodel.leftboot.showModel = false;
            return BoneHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return BoneHelm.armortexture;
      }
   }
}
