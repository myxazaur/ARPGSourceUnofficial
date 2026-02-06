package com.Vivern.Arpg.elements.armor;

import com.Vivern.Arpg.elements.models.HazardSuitModel;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HazardSuitHelm extends AbstractArmor {
   public static HazardSuitModel armormodel = new HazardSuitModel();
   public static String armortexture = "arpg:textures/hazard_suit_model_tex.png";

   public HazardSuitHelm() {
      super(EntityEquipmentSlot.HEAD, "hazard_suit_helmet", 2000, 5);
   }

   public static boolean isCharged(ItemStack stack) {
      return true;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn.ticksExisted % 40 == 0 && entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         World world = player.getEntityWorld();
         if (player.inventory.armorInventory.get(3) == itemstack && isCharged((ItemStack)player.inventory.armorInventory.get(3))) {
            Weapons.mixPotion(
               player,
               PotionEffects.TOXIN,
               30.0F,
               0.0F,
               Weapons.EnumPotionMix.ABSOLUTE,
               Weapons.EnumPotionMix.ABSOLUTE,
               Weapons.EnumMathOperation.MINUS,
               Weapons.EnumMathOperation.MINUS,
               0,
               0
            );
            if (((ItemStack)player.inventory.armorInventory.get(2)).getItem() == ItemsRegister.TOXINIUMCHEST
               && isCharged((ItemStack)player.inventory.armorInventory.get(2))
               && ((ItemStack)player.inventory.armorInventory.get(1)).getItem() == ItemsRegister.TOXINIUMLEGS
               && isCharged((ItemStack)player.inventory.armorInventory.get(1))
               && ((ItemStack)player.inventory.armorInventory.get(0)).getItem() == ItemsRegister.TOXINIUMBOOTS
               && isCharged((ItemStack)player.inventory.armorInventory.get(0))) {
               Weapons.mixPotion(
                  player,
                  PotionEffects.CHLORITE,
                  40.0F,
                  1.0F,
                  Weapons.EnumPotionMix.ABSOLUTE,
                  Weapons.EnumPotionMix.ABSOLUTE,
                  Weapons.EnumMathOperation.MINUS,
                  Weapons.EnumMathOperation.MINUS,
                  0,
                  0
               );
               Weapons.mixPotion(
                  player,
                  MobEffects.SLOWNESS,
                  100.0F,
                  1.0F,
                  Weapons.EnumPotionMix.ABSOLUTE,
                  Weapons.EnumPotionMix.ABSOLUTE,
                  Weapons.EnumMathOperation.MINUS,
                  Weapons.EnumMathOperation.MINUS,
                  0,
                  0
               );
               Weapons.mixPotion(
                  player,
                  MobEffects.WEAKNESS,
                  100.0F,
                  1.0F,
                  Weapons.EnumPotionMix.ABSOLUTE,
                  Weapons.EnumPotionMix.ABSOLUTE,
                  Weapons.EnumMathOperation.MINUS,
                  Weapons.EnumMathOperation.MINUS,
                  0,
                  0
               );
            }
         }
      }
   }

   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      if (equipmentSlot == this.armorType) {
         multimap.put(
            SharedMonsterAttributes.ARMOR.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 3.0, 0)
         );
         multimap.put(
            PropertiesRegistry.ARMOR_PROTECTION.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.0, 0)
         );
         multimap.put(
            SharedMonsterAttributes.MAX_HEALTH.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 1.0, 0)
         );
         if (isCharged(stack)) {
            multimap.put(
               SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.1, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.005, 0)
            );
         }
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

   public static class HazardSuitBoots extends AbstractArmor implements IItemHurted {
      public HazardSuitBoots() {
         super(EntityEquipmentSlot.FEET, "hazard_suit_boots", 2000, 5);
      }

      public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            multimap.put(
               SharedMonsterAttributes.ARMOR.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 2.0, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 1.5, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MAX_HEALTH.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 2.0, 0)
            );
            if (HazardSuitHelm.isCharged(stack)) {
               multimap.put(
                  SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
                  new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.02, 0)
               );
               multimap.put(
                  SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
                  new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.3, 0)
               );
               multimap.put(
                  PropertiesRegistry.JUMP_HEIGHT.getName(),
                  new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor jumping", 0.05, 0)
               );
            }
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            HazardSuitHelm.armormodel.setModelAttributes(model);
            HazardSuitHelm.armormodel.helm.showModel = false;
            HazardSuitHelm.armormodel.chest.showModel = false;
            HazardSuitHelm.armormodel.rightarm.showModel = false;
            HazardSuitHelm.armormodel.leftarm.showModel = false;
            HazardSuitHelm.armormodel.rightleg.showModel = false;
            HazardSuitHelm.armormodel.leftleg.showModel = false;
            HazardSuitHelm.armormodel.rightboot.showModel = true;
            HazardSuitHelm.armormodel.leftboot.showModel = true;
            return HazardSuitHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return HazardSuitHelm.armortexture;
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         return source == DamageSource.HOT_FLOOR && HazardSuitHelm.isCharged(stack) ? Math.max(hurtdamage - 8.0F, 0.0F) : hurtdamage;
      }
   }

   public static class HazardSuitChestplate extends AbstractArmor {
      public HazardSuitChestplate() {
         super(EntityEquipmentSlot.CHEST, "hazard_suit_chestplate", 2300, 5);
      }

      public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            multimap.put(
               SharedMonsterAttributes.ARMOR.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 4.0, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.5, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MAX_HEALTH.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 4.0, 0)
            );
            if (HazardSuitHelm.isCharged(stack)) {
               multimap.put(
                  SharedMonsterAttributes.ATTACK_SPEED.getName(),
                  new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.3, 0)
               );
               multimap.put(
                  SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
                  new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.01, 0)
               );
               multimap.put(
                  SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
                  new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.4, 0)
               );
               multimap.put(
                  PropertiesRegistry.AIRBORNE_MOBILITY.getName(),
                  new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor airborne mobility", 0.03, 0)
               );
            }
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            HazardSuitHelm.armormodel.setModelAttributes(model);
            HazardSuitHelm.armormodel.helm.showModel = false;
            HazardSuitHelm.armormodel.chest.showModel = true;
            HazardSuitHelm.armormodel.rightarm.showModel = true;
            HazardSuitHelm.armormodel.leftarm.showModel = true;
            HazardSuitHelm.armormodel.rightleg.showModel = false;
            HazardSuitHelm.armormodel.leftleg.showModel = false;
            HazardSuitHelm.armormodel.rightboot.showModel = false;
            HazardSuitHelm.armormodel.leftboot.showModel = false;
            return HazardSuitHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return HazardSuitHelm.armortexture;
      }
   }

   public static class HazardSuitLeggins extends AbstractArmor implements IItemHurted {
      public HazardSuitLeggins() {
         super(EntityEquipmentSlot.LEGS, "hazard_suit_leggins", 2100, 5);
      }

      public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            multimap.put(
               SharedMonsterAttributes.ARMOR.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 4.0, 0)
            );
            multimap.put(
               PropertiesRegistry.ARMOR_PROTECTION.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.5, 0)
            );
            multimap.put(
               SharedMonsterAttributes.MAX_HEALTH.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 3.0, 0)
            );
            if (HazardSuitHelm.isCharged(stack)) {
               multimap.put(
                  PropertiesRegistry.JUMP_HEIGHT.getName(),
                  new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor jumping", 0.15, 0)
               );
               multimap.put(
                  SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
                  new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.06, 0)
               );
               multimap.put(
                  SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
                  new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.1, 0)
               );
            }
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (itemStack != ItemStack.EMPTY) {
            HazardSuitHelm.armormodel.setModelAttributes(model);
            HazardSuitHelm.armormodel.helm.showModel = false;
            HazardSuitHelm.armormodel.chest.showModel = false;
            HazardSuitHelm.armormodel.rightarm.showModel = false;
            HazardSuitHelm.armormodel.leftarm.showModel = false;
            HazardSuitHelm.armormodel.rightleg.showModel = true;
            HazardSuitHelm.armormodel.leftleg.showModel = true;
            HazardSuitHelm.armormodel.rightboot.showModel = false;
            HazardSuitHelm.armormodel.leftboot.showModel = false;
            return HazardSuitHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return HazardSuitHelm.armortexture;
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         return source == DamageSource.FALL && HazardSuitHelm.isCharged(stack) ? Math.max(hurtdamage - 8.0F, 0.0F) : hurtdamage;
      }
   }
}
