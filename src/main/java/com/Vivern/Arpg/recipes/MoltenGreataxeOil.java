package com.Vivern.Arpg.recipes;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.potions.PotionEffects;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;

public class MoltenGreataxeOil extends Impl<IRecipe> implements IRecipe {
   public static ResourceLocation name = new ResourceLocation("arpg:molten_greataxe_oil_recipe");

   public boolean matches(InventoryCrafting inv, World worldIn) {
      ItemStack greataxe = null;
      ItemStack poison = null;

      for (int i = 0; i < inv.getSizeInventory(); i++) {
         if (inv.getStackInSlot(i).getItem() == ItemsRegister.MOLTENGREATAXE) {
            if (greataxe != null) {
               return false;
            }

            greataxe = inv.getStackInSlot(i);
         }

         if (inv.getStackInSlot(i).getItem() == Items.POTIONITEM) {
            if (poison != null) {
               return false;
            }

            poison = inv.getStackInSlot(i);
         }
      }

      return greataxe != null && poison != null;
   }

   public ItemStack getCraftingResult(InventoryCrafting inv) {
      ItemStack greataxe = null;
      ItemStack poison = null;

      for (int i = 0; i < inv.getSizeInventory(); i++) {
         if (inv.getStackInSlot(i).getItem() == ItemsRegister.MOLTENGREATAXE) {
            greataxe = inv.getStackInSlot(i);
         }

         if (inv.getStackInSlot(i).getItem() == Items.POTIONITEM) {
            poison = inv.getStackInSlot(i);
         }
      }

      boolean special = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, greataxe) > 0;
      List<PotionEffect> potions = PotionUtils.getEffectsFromStack(poison);
      PotionEffect resulteffect = null;

      for (PotionEffect effect : potions) {
         if (effect.getPotion() == PotionEffects.FIERYOIL) {
            resulteffect = effect;
            break;
         }

         if (effect.getPotion() == MobEffects.WITHER) {
            resulteffect = effect;
            break;
         }

         if (effect.getPotion() == MobEffects.SLOWNESS) {
            resulteffect = effect;
            break;
         }

         if (special) {
            if (effect.getPotion() == MobEffects.POISON) {
               resulteffect = effect;
               break;
            }

            if (effect.getPotion() == MobEffects.WEAKNESS) {
               resulteffect = effect;
               break;
            }

            if (effect.getPotion() == MobEffects.MINING_FATIGUE) {
               resulteffect = effect;
               break;
            }

            if (effect.getPotion() == PotionEffects.BLOOD_THIRST) {
               resulteffect = effect;
               break;
            }

            if (effect.getPotion() == PotionEffects.ENDER_POISON) {
               resulteffect = effect;
               break;
            }

            if (effect.getPotion() == PotionEffects.ICHOR_CURSE) {
               resulteffect = effect;
               break;
            }

            if (effect.getPotion() == PotionEffects.MANA_OIL) {
               resulteffect = effect;
               break;
            }

            if (effect.getPotion() == PotionEffects.SLIME) {
               resulteffect = effect;
               break;
            }

            if (effect.getPotion() == PotionEffects.RAINBOW) {
               resulteffect = effect;
               break;
            }

            if (effect.getPotion() == PotionEffects.WAVING) {
               resulteffect = effect;
               break;
            }

            if (effect.getPotion() == PotionEffects.LENSBLUR) {
               resulteffect = effect;
               break;
            }

            if (effect.getPotion() == PotionEffects.TOXIN) {
               resulteffect = effect;
               break;
            }
         }
      }

      ItemStack newgreataxe = greataxe.copy();
      if (resulteffect != null) {
         NBTHelper.SetNBTint(newgreataxe, resulteffect.getDuration(), "duration");
         NBTHelper.SetNBTint(newgreataxe, resulteffect.getAmplifier(), "amplifier");
         resulteffect.getPotion();
         NBTHelper.SetNBTint(newgreataxe, Potion.getIdFromPotion(resulteffect.getPotion()), "potion");
      }

      return newgreataxe;
   }

   public boolean canFit(int width, int height) {
      return width + height == 3;
   }

   public ItemStack getRecipeOutput() {
      return ItemStack.EMPTY;
   }
}
