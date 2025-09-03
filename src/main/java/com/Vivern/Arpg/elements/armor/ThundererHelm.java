//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.armor;

import com.Vivern.Arpg.elements.models.BoneArmorModel;
import com.Vivern.Arpg.elements.models.ThundererArmorModel;
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

public class ThundererHelm extends AbstractArmor {
   public static ThundererArmorModel armormodel = new ThundererArmorModel();
   public static String armortexture = "arpg:textures/thunderer_armor_model_tex.png";

   public ThundererHelm() {
      super(EntityEquipmentSlot.HEAD, "thunderer_armor_helmet", 5000, 6);
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
            SharedMonsterAttributes.ATTACK_SPEED.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0)
         );
         multimap.put(
            PropertiesRegistry.ARMOR_PROTECTION.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 3.0, 0)
         );
         multimap.put(
            SharedMonsterAttributes.MAX_HEALTH.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 2.0, 0)
         );
         multimap.put(
            SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.1, 0)
         );
         multimap.put(
            PropertiesRegistry.LEADERSHIP.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor leadership", 10.0, 0)
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
         armormodel.belt.showModel = false;
         return armormodel;
      } else {
         return null;
      }
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      return armortexture;
   }

   public static class ThundererBoots extends AbstractArmor {
      public ThundererBoots() {
         super(EntityEquipmentSlot.FEET, "thunderer_armor_boots", 5000, 6);
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
               SharedMonsterAttributes.ATTACK_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.5, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MAX_HEALTH.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 3.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.2, 0)
            );
            multimap.put(
               PropertiesRegistry.LEADERSHIP.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor leadership", 8.0, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            ThundererHelm.armormodel.setModelAttributes(model);
            ThundererHelm.armormodel.helm.showModel = false;
            ThundererHelm.armormodel.chest.showModel = false;
            ThundererHelm.armormodel.rightarm.showModel = false;
            ThundererHelm.armormodel.leftarm.showModel = false;
            ThundererHelm.armormodel.rightleg.showModel = false;
            ThundererHelm.armormodel.leftleg.showModel = false;
            ThundererHelm.armormodel.rightboot.showModel = true;
            ThundererHelm.armormodel.leftboot.showModel = true;
            ThundererHelm.armormodel.belt.showModel = false;
            return ThundererHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return ThundererHelm.armortexture;
      }
   }

   public static class ThundererChestplate extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public ThundererChestplate() {
         super(EntityEquipmentSlot.CHEST, "thunderer_armor_chestplate", 6700, 6);
      }

      @Override
      public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            multimap.put(
               SharedMonsterAttributes.ARMOR.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 5.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", -0.01, 0)
            );
            multimap.put(
               SharedMonsterAttributes.ATTACK_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.2, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 4.5, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MAX_HEALTH.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 5.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.2, 0)
            );
            multimap.put(
               PropertiesRegistry.LEADERSHIP.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor leadership", 15.0, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            ThundererHelm.armormodel.setModelAttributes(model);
            ThundererHelm.armormodel.helm.showModel = false;
            ThundererHelm.armormodel.chest.showModel = true;
            ThundererHelm.armormodel.rightarm.showModel = true;
            ThundererHelm.armormodel.leftarm.showModel = true;
            ThundererHelm.armormodel.rightleg.showModel = false;
            ThundererHelm.armormodel.leftleg.showModel = false;
            ThundererHelm.armormodel.rightboot.showModel = false;
            ThundererHelm.armormodel.leftboot.showModel = false;
            ThundererHelm.armormodel.belt.showModel = false;
            return ThundererHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return ThundererHelm.armortexture;
      }
   }

   public static class ThundererLeggins extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public ThundererLeggins() {
         super(EntityEquipmentSlot.LEGS, "thunderer_armor_leggins", 5300, 6);
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
               SharedMonsterAttributes.ATTACK_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.15, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 3.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MAX_HEALTH.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 4.0, 0)
            );
            multimap.put(
               SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.15, 0)
            );
            multimap.put(
               PropertiesRegistry.LEADERSHIP.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor leadership", 8.0, 0)
            );
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            ThundererHelm.armormodel.setModelAttributes(model);
            ThundererHelm.armormodel.helm.showModel = false;
            ThundererHelm.armormodel.chest.showModel = false;
            ThundererHelm.armormodel.rightarm.showModel = false;
            ThundererHelm.armormodel.leftarm.showModel = false;
            ThundererHelm.armormodel.rightleg.showModel = true;
            ThundererHelm.armormodel.leftleg.showModel = true;
            ThundererHelm.armormodel.rightboot.showModel = false;
            ThundererHelm.armormodel.leftboot.showModel = false;
            ThundererHelm.armormodel.belt.showModel = true;
            return ThundererHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return ThundererHelm.armortexture;
      }
   }
}
