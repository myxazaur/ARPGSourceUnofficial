package com.vivern.arpg.elements.armor;

import com.vivern.arpg.elements.models.BoneArmorModel;
import com.vivern.arpg.elements.models.SnowcoatArmorModel;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.OreDicHelper;
import com.vivern.arpg.main.PropertiesRegistry;
import com.google.common.collect.Multimap;
import javax.annotation.Nullable;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;

public class SnowcoatHelm extends AbstractArmor {
   public static SnowcoatArmorModel armormodel = new SnowcoatArmorModel();
   public static String[] armortextures = new String[]{
      "arpg:textures/snowcoat_armor_model_tex.png", "arpg:textures/snowcoat_armor_model_tex_blue.png", "arpg:textures/snowcoat_armor_model_tex_white.png"
   };

   public SnowcoatHelm() {
      super(EntityEquipmentSlot.HEAD, "snowcoat_armor_helmet", 800, 6);
      this.addPropertyOverride(new ResourceLocation("type"), new IItemPropertyGetter() {
         @SideOnly(Side.CLIENT)
         public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
            return NBTHelper.GetNBTint(stack, "colorid");
         }
      });
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
            PropertiesRegistry.LEADERSHIP.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor leadership", 10.0, 0)
         );
         multimap.put(
            PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 10.0, 0)
         );
         multimap.put(
            PropertiesRegistry.MANASPEED_MAX.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.05, 0)
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
      return armortextures[MathHelper.clamp(NBTHelper.GetNBTint(stack, "colorid"), 0, 2)];
   }

   public static class SnowcoatBoots extends AbstractArmor {
      public SnowcoatBoots() {
         super(EntityEquipmentSlot.FEET, "snowcoat_armor_boots", 900, 6);
         this.addPropertyOverride(new ResourceLocation("type"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
               return NBTHelper.GetNBTint(stack, "colorid");
            }
         });
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
               PropertiesRegistry.LEADERSHIP.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor leadership", 4.0, 0)
            );
            multimap.put(
               PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 3.0, 0)
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
            SnowcoatHelm.armormodel.setModelAttributes(model);
            SnowcoatHelm.armormodel.helm.showModel = false;
            SnowcoatHelm.armormodel.chest.showModel = false;
            SnowcoatHelm.armormodel.rightarm.showModel = false;
            SnowcoatHelm.armormodel.leftarm.showModel = false;
            SnowcoatHelm.armormodel.rightleg.showModel = false;
            SnowcoatHelm.armormodel.leftleg.showModel = false;
            SnowcoatHelm.armormodel.rightboot.showModel = true;
            SnowcoatHelm.armormodel.leftboot.showModel = true;
            return SnowcoatHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return SnowcoatHelm.armortextures[MathHelper.clamp(NBTHelper.GetNBTint(stack, "colorid"), 0, 2)];
      }
   }

   public static class SnowcoatChestplate extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public SnowcoatChestplate() {
         super(EntityEquipmentSlot.CHEST, "snowcoat_armor_chestplate", 1100, 6);
         this.addPropertyOverride(new ResourceLocation("type"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
               return NBTHelper.GetNBTint(stack, "colorid");
            }
         });
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
               PropertiesRegistry.LEADERSHIP.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor leadership", 15.0, 0)
            );
            multimap.put(
               PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 10.0, 0)
            );
            multimap.put(
               PropertiesRegistry.MANASPEED_MAX.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.05, 0)
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
            SnowcoatHelm.armormodel.setModelAttributes(model);
            SnowcoatHelm.armormodel.helm.showModel = false;
            SnowcoatHelm.armormodel.chest.showModel = true;
            SnowcoatHelm.armormodel.rightarm.showModel = true;
            SnowcoatHelm.armormodel.leftarm.showModel = true;
            SnowcoatHelm.armormodel.rightleg.showModel = false;
            SnowcoatHelm.armormodel.leftleg.showModel = false;
            SnowcoatHelm.armormodel.rightboot.showModel = false;
            SnowcoatHelm.armormodel.leftboot.showModel = false;
            return SnowcoatHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return SnowcoatHelm.armortextures[MathHelper.clamp(NBTHelper.GetNBTint(stack, "colorid"), 0, 2)];
      }
   }

   public static class SnowcoatDye extends Impl<IRecipe> implements IRecipe {
      public static ResourceLocation name = new ResourceLocation("arpg:snowcoat_dye_recipe");

      public boolean matches(InventoryCrafting inv, World worldIn) {
         ItemStack coat = null;
         ItemStack dye = null;

         for (int i = 0; i < inv.getSizeInventory(); i++) {
            Item iitem = inv.getStackInSlot(i).getItem();
            if (iitem == ItemsRegister.SNOWCOATARMORBOOTS
               || iitem == ItemsRegister.SNOWCOATARMORLEGS
               || iitem == ItemsRegister.SNOWCOATARMORCHEST
               || iitem == ItemsRegister.SNOWCOATARMORHELM) {
               if (coat != null) {
                  return false;
               }

               coat = inv.getStackInSlot(i);
            }

            if (OreDicHelper.itemStringOredigMatches(inv.getStackInSlot(i), "dyeBlue")
               || OreDicHelper.itemStringOredigMatches(inv.getStackInSlot(i), "dyeLightBlue")
               || OreDicHelper.itemStringOredigMatches(inv.getStackInSlot(i), "dyeRed")) {
               if (dye != null) {
                  return false;
               }

               dye = inv.getStackInSlot(i);
            }
         }

         return coat != null && dye != null;
      }

      public ItemStack getCraftingResult(InventoryCrafting inv) {
         ItemStack coat = null;
         ItemStack dye = null;
         int color = 0;

         for (int i = 0; i < inv.getSizeInventory(); i++) {
            Item iitem = inv.getStackInSlot(i).getItem();
            if (iitem == ItemsRegister.SNOWCOATARMORBOOTS
               || iitem == ItemsRegister.SNOWCOATARMORLEGS
               || iitem == ItemsRegister.SNOWCOATARMORCHEST
               || iitem == ItemsRegister.SNOWCOATARMORHELM) {
               coat = inv.getStackInSlot(i);
            }

            if (OreDicHelper.itemStringOredigMatches(inv.getStackInSlot(i), "dyeRed")) {
               dye = inv.getStackInSlot(i);
               color = 0;
            }

            if (OreDicHelper.itemStringOredigMatches(inv.getStackInSlot(i), "dyeBlue")) {
               dye = inv.getStackInSlot(i);
               color = 1;
            }

            if (OreDicHelper.itemStringOredigMatches(inv.getStackInSlot(i), "dyeLightBlue")) {
               dye = inv.getStackInSlot(i);
               color = 2;
            }
         }

         ItemStack newcoat = coat.copy();
         NBTHelper.GiveNBTint(newcoat, color, "colorid");
         NBTHelper.SetNBTint(newcoat, color, "colorid");
         return newcoat;
      }

      public boolean canFit(int width, int height) {
         return width + height == 3;
      }

      public ItemStack getRecipeOutput() {
         return ItemStack.EMPTY;
      }
   }

   public static class SnowcoatLeggins extends AbstractArmor {
      public static BoneArmorModel model = new BoneArmorModel();

      public SnowcoatLeggins() {
         super(EntityEquipmentSlot.LEGS, "snowcoat_armor_leggins", 1000, 6);
         this.addPropertyOverride(new ResourceLocation("type"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
               return NBTHelper.GetNBTint(stack, "colorid");
            }
         });
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
               PropertiesRegistry.LEADERSHIP.getName(),
               new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor leadership", 5.0, 0)
            );
            multimap.put(
               PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 5.0, 0)
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
            SnowcoatHelm.armormodel.setModelAttributes(model);
            SnowcoatHelm.armormodel.helm.showModel = false;
            SnowcoatHelm.armormodel.chest.showModel = false;
            SnowcoatHelm.armormodel.rightarm.showModel = false;
            SnowcoatHelm.armormodel.leftarm.showModel = false;
            SnowcoatHelm.armormodel.rightleg.showModel = true;
            SnowcoatHelm.armormodel.leftleg.showModel = true;
            SnowcoatHelm.armormodel.rightboot.showModel = false;
            SnowcoatHelm.armormodel.leftboot.showModel = false;
            return SnowcoatHelm.armormodel;
         } else {
            return null;
         }
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return SnowcoatHelm.armortextures[MathHelper.clamp(NBTHelper.GetNBTint(stack, "colorid"), 0, 2)];
      }
   }
}
