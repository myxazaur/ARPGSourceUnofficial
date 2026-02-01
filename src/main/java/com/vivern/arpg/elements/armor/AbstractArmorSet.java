package com.vivern.arpg.elements.armor;

import com.vivern.arpg.elements.IEnergyItem;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.NamedAttributeModifier;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractArmorSet {
   public ArrayList<NamedAttributeModifier> modifiers = new ArrayList<>();
   public int averageDurability;
   public int enchantability;
   public EnumArmorRepairRatio repairRatio;
   public String name;
   public String nameHelmet;
   public ARPGArmor HELMET;
   public ARPGArmor CHESTPLATE;
   public ARPGArmor LEGGINS;
   public ARPGArmor BOOTS;
   public int maxEnergyStored;
   public int throughput;
   public boolean isElectric = false;

   public AbstractArmorSet(String name, String nameHelmet) {
      this.name = name;
      this.nameHelmet = nameHelmet;
   }

   public AbstractArmorSet setParameters(int averageDurability, int enchantability, EnumArmorRepairRatio enumArmorRepairRatio) {
      this.averageDurability = averageDurability;
      this.enchantability = enchantability;
      this.repairRatio = enumArmorRepairRatio;
      return this;
   }

   public AbstractArmorSet modif(int operation, double value, IAttribute attribute) {
      NamedAttributeModifier modifier = new NamedAttributeModifier(attribute.getName(), (float)value, operation);
      this.modifiers.add(modifier);
      return this;
   }

   public AbstractArmorSet setElectric(int maxEnergyStored, int throughput) {
      this.maxEnergyStored = maxEnergyStored;
      this.throughput = throughput;
      this.isElectric = true;
      return this;
   }

   public void createArmorItems() {
      if (this.isElectric) {
         this.HELMET = new ARPGElectricArmor(this, EntityEquipmentSlot.HEAD, this.name + "_" + this.nameHelmet);
         this.CHESTPLATE = new ARPGElectricArmor(this, EntityEquipmentSlot.CHEST, this.name + "_chestplate");
         this.LEGGINS = new ARPGElectricArmor(this, EntityEquipmentSlot.LEGS, this.name + "_leggins");
         this.BOOTS = new ARPGElectricArmor(this, EntityEquipmentSlot.FEET, this.name + "_boots");
      } else {
         this.HELMET = new ARPGArmor(this, EntityEquipmentSlot.HEAD, this.name + "_" + this.nameHelmet);
         this.CHESTPLATE = new ARPGArmor(this, EntityEquipmentSlot.CHEST, this.name + "_chestplate");
         this.LEGGINS = new ARPGArmor(this, EntityEquipmentSlot.LEGS, this.name + "_leggins");
         this.BOOTS = new ARPGArmor(this, EntityEquipmentSlot.FEET, this.name + "_boots");
      }
   }

   public void onUpdateWearing(ARPGArmor item, ItemStack itemstack, World world, EntityLivingBase entity, int itemSlot, boolean isSelected) {
   }

   public void onUpdateFullset(ARPGArmor item, ItemStack itemstack, World world, EntityLivingBase entity, int itemSlot, boolean isSelected) {
   }

   @SideOnly(Side.CLIENT)
   public abstract ModelBiped getArmorModel(EntityLivingBase var1, ItemStack var2, EntityEquipmentSlot var3, ModelBiped var4);

   public abstract String getArmorTexture(ItemStack var1, Entity var2, EntityEquipmentSlot var3, String var4);

   public int getDurabilityForSlot(EntityEquipmentSlot armorSlot) {
      if (armorSlot == EntityEquipmentSlot.HEAD) {
         return (int)(this.averageDurability * 0.8);
      } else if (armorSlot == EntityEquipmentSlot.CHEST) {
         return this.averageDurability * 1;
      } else if (armorSlot == EntityEquipmentSlot.LEGS) {
         return (int)(this.averageDurability * 0.9);
      } else {
         return armorSlot == EntityEquipmentSlot.FEET ? (int)(this.averageDurability * 0.8) : this.averageDurability;
      }
   }

   public float getAttributeValueForSlot(float baseValue, EntityEquipmentSlot armorSlot, ItemStack itemStack) {
      float newValue = 0.0F;
      if (armorSlot == EntityEquipmentSlot.HEAD) {
         newValue = baseValue / 15.0F * 3.0F;
      }

      if (armorSlot == EntityEquipmentSlot.CHEST) {
         newValue = baseValue / 15.0F * 5.0F;
      }

      if (armorSlot == EntityEquipmentSlot.LEGS) {
         newValue = baseValue / 15.0F * 4.0F;
      }

      if (armorSlot == EntityEquipmentSlot.FEET) {
         newValue = baseValue / 15.0F * 3.0F;
      }

      if (this.isElectric && !isCharged(itemStack) && newValue > 0.0F) {
         newValue *= 0.25F;
      }

      float f1 = 100.0F;
      return Math.round(newValue * f1) / f1;
   }

   public float onHurtWithItem(
      ARPGArmor item, float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source, EntityEquipmentSlot armorSlot
   ) {
      return hurtdamage;
   }

   public static boolean isCharged(ItemStack stack) {
      return NBTHelper.GetNBTint(stack, "rf") > 0;
   }

   public boolean isFullset(EntityLivingBase entityLivingBase) {
      return entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == this.HELMET
         && entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == this.CHESTPLATE
         && entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == this.LEGGINS
         && entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == this.BOOTS;
   }

   public boolean isChargedFullset(EntityLivingBase entityLivingBase) {
      return entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == this.HELMET
         && isCharged(entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.HEAD))
         && entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == this.CHESTPLATE
         && isCharged(entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.CHEST))
         && entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == this.LEGGINS
         && isCharged(entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.LEGS))
         && entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == this.BOOTS
         && isCharged(entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.FEET));
   }

   public void constructItem(ARPGArmor item) {
   }

   public static class ARPGArmor extends AbstractArmor implements IItemHurted {
      public AbstractArmorSet armorSet;

      public ARPGArmor(AbstractArmorSet armorSet, EntityEquipmentSlot slot, String name) {
         super(slot, name, armorSet.getDurabilityForSlot(slot), armorSet.enchantability);
         this.armorSet = armorSet;
         this.armorSet.constructItem(this);
      }

      public float getXpRepairRatio(ItemStack stack) {
         float nonRelative = 4.0F;
         float armorRelative = 4.0F;
         if (this.armorSet.repairRatio == EnumArmorRepairRatio.CHEAP) {
            nonRelative = 5.0F;
            armorRelative = this.armorSet.averageDurability / 600.0F;
         }

         if (this.armorSet.repairRatio == EnumArmorRepairRatio.NORMAL) {
            nonRelative = 4.0F;
            armorRelative = this.armorSet.averageDurability / 750.0F;
         }

         if (this.armorSet.repairRatio == EnumArmorRepairRatio.EXPENSIVE) {
            nonRelative = 3.0F;
            armorRelative = this.armorSet.averageDurability / 1000.0F;
         }

         if (this.armorSet.repairRatio == EnumArmorRepairRatio.RICH) {
            nonRelative = 2.0F;
            armorRelative = this.armorSet.averageDurability / 1500.0F;
         }

         return nonRelative * 0.6F + armorRelative * 0.4F;
      }

      public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
         if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            if (this.armorType == EntityEquipmentSlot.HEAD
               && entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == stack
               && this.armorSet.isFullset(entityLivingBase)) {
               this.armorSet.onUpdateFullset(this, stack, world, entityLivingBase, itemSlot, isSelected);
            }

            if (entityLivingBase.getItemStackFromSlot(this.armorType) == stack) {
               this.armorSet.onUpdateWearing(this, stack, world, entityLivingBase, itemSlot, isSelected);
            }
         }
      }

      public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack itemStack) {
         Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
         if (equipmentSlot == this.armorType) {
            for (NamedAttributeModifier modifier : this.armorSet.modifiers) {
               multimap.put(
                  modifier.name,
                  new AttributeModifier(
                     ARMOR_MODIFIERSG[equipmentSlot.getIndex()],
                     modifier.name + " modifier",
                     this.armorSet.getAttributeValueForSlot(modifier.value, this.armorType, itemStack),
                     modifier.operation
                  )
               );
            }
         }

         return multimap;
      }

      @SideOnly(Side.CLIENT)
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         return itemStack != ItemStack.EMPTY ? this.armorSet.getArmorModel(entityLiving, itemStack, armorSlot, model) : null;
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
         return this.armorSet.getArmorTexture(stack, entity, slot, type);
      }

      public int getItemEnchantability(ItemStack stack) {
         return this.armorSet.enchantability;
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         return this.armorSet.onHurtWithItem(this, hurtdamage, stack, player, source, this.armorType);
      }
   }

   public static class ARPGElectricArmor extends ARPGArmor implements IEnergyItem {
      public ARPGElectricArmor(AbstractArmorSet armorSet, EntityEquipmentSlot slot, String name) {
         super(armorSet, slot, name);
      }

      @Override
      public int getMaxEnergyStored(ItemStack stack) {
         return this.armorSet.maxEnergyStored;
      }

      @Override
      public int getThroughput() {
         return this.armorSet.throughput;
      }

      @Override
      public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
         if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            if (this.armorType == EntityEquipmentSlot.HEAD
               && entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == stack
               && this.armorSet.isChargedFullset(entityLivingBase)) {
               this.armorSet.onUpdateFullset(this, stack, world, entityLivingBase, itemSlot, isSelected);
            }

            if (entityLivingBase.getItemStackFromSlot(this.armorType) == stack) {
               this.armorSet.onUpdateWearing(this, stack, world, entityLivingBase, itemSlot, isSelected);
               if (entity.ticksExisted % 5 == 0) {
                  this.extractEnergyFromItem(stack, 1, false);
               }
            }
         }
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         if (hurtdamage > 0.0F) {
            this.extractEnergyFromItem(stack, (int)(hurtdamage * 30.0F), false);
         }

         return this.armorSet.onHurtWithItem(this, hurtdamage, stack, player, source, this.armorType);
      }

      @SideOnly(Side.CLIENT)
      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         IEnergyItem.addRFInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static enum EnumArmorRepairRatio {
      CHEAP,
      NORMAL,
      EXPENSIVE,
      RICH;
   }
}
