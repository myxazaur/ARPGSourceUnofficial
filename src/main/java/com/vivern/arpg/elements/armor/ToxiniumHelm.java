package com.vivern.arpg.elements.armor;

import com.vivern.arpg.elements.models.ToxiniumArmorModel;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.PotionEffects;
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

public class ToxiniumHelm extends AbstractArmor {
   public static ToxiniumArmorModel armormodel = new ToxiniumArmorModel();
   public static String armortexture = "arpg:textures/toxinium_armor_model_tex.png";

   public ToxiniumHelm() {
      super(EntityEquipmentSlot.HEAD, "toxinium_helmet", 8200, 4);
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
                  200.0F,
                  2.0F,
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
                  0.0F,
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
                  0.0F,
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

   public static class ToxiniumBoots extends AbstractArmor implements IItemHurted {
      public ToxiniumBoots() {
         super(EntityEquipmentSlot.FEET, "toxinium_boots", 8000, 4);
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
            if (ToxiniumHelm.isCharged(stack)) {
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
            ToxiniumHelm.armormodel.setModelAttributes(model);
            ToxiniumHelm.armormodel.helm.showModel = false;
            ToxiniumHelm.armormodel.chest.showModel = false;
            ToxiniumHelm.armormodel.rightarm.showModel = false;
            ToxiniumHelm.armormodel.leftarm.showModel = false;
            ToxiniumHelm.armormodel.rightleg.showModel = false;
            ToxiniumHelm.armormodel.leftleg.showModel = false;
            ToxiniumHelm.armormodel.rightboot.showModel = true;
            ToxiniumHelm.armormodel.leftboot.showModel = true;
            return ToxiniumHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return ToxiniumHelm.armortexture;
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         return source == DamageSource.HOT_FLOOR && ToxiniumHelm.isCharged(stack) ? Math.max(hurtdamage - 8.0F, 0.0F) : hurtdamage;
      }
   }

   public static class ToxiniumChestplate extends AbstractArmor {
      public ToxiniumChestplate() {
         super(EntityEquipmentSlot.CHEST, "toxinium_chestplate", 9000, 4);
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
            if (ToxiniumHelm.isCharged(stack)) {
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
            ToxiniumHelm.armormodel.setModelAttributes(model);
            ToxiniumHelm.armormodel.helm.showModel = false;
            ToxiniumHelm.armormodel.chest.showModel = true;
            ToxiniumHelm.armormodel.rightarm.showModel = true;
            ToxiniumHelm.armormodel.leftarm.showModel = true;
            ToxiniumHelm.armormodel.rightleg.showModel = false;
            ToxiniumHelm.armormodel.leftleg.showModel = false;
            ToxiniumHelm.armormodel.rightboot.showModel = false;
            ToxiniumHelm.armormodel.leftboot.showModel = false;
            return ToxiniumHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return ToxiniumHelm.armortexture;
      }
   }

   public static class ToxiniumLeggins extends AbstractArmor implements IItemHurted {
      public ToxiniumLeggins() {
         super(EntityEquipmentSlot.LEGS, "toxinium_leggins", 8500, 4);
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
            if (ToxiniumHelm.isCharged(stack)) {
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
            ToxiniumHelm.armormodel.setModelAttributes(model);
            ToxiniumHelm.armormodel.helm.showModel = false;
            ToxiniumHelm.armormodel.chest.showModel = false;
            ToxiniumHelm.armormodel.rightarm.showModel = false;
            ToxiniumHelm.armormodel.leftarm.showModel = false;
            ToxiniumHelm.armormodel.rightleg.showModel = true;
            ToxiniumHelm.armormodel.leftleg.showModel = true;
            ToxiniumHelm.armormodel.rightboot.showModel = false;
            ToxiniumHelm.armormodel.leftboot.showModel = false;
            return ToxiniumHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return ToxiniumHelm.armortexture;
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         return source == DamageSource.FALL && ToxiniumHelm.isCharged(stack) ? Math.max(hurtdamage - 8.0F, 0.0F) : hurtdamage;
      }
   }
}
