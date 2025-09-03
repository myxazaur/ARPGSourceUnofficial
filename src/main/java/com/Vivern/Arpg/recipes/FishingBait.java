//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.recipes;

import com.Vivern.Arpg.elements.ItemFishingRod;
import com.Vivern.Arpg.loot.Fishing;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FishingBait implements IRecipe {
   public IRecipe setRegistryName(ResourceLocation name) {
      return this;
   }

   public ResourceLocation getRegistryName() {
      return new ResourceLocation("arpg:bait_use_recipe");
   }

   public Class<IRecipe> getRegistryType() {
      return this.getRegistryType();
   }

   public boolean matches(InventoryCrafting inv, World worldIn) {
      ItemStack rod = null;
      ItemStack bait = null;

      for (int i = 0; i < inv.getSizeInventory(); i++) {
         if (inv.getStackInSlot(i).getItem() instanceof ItemFishingRod) {
            if (rod != null) {
               return false;
            }

            rod = inv.getStackInSlot(i);
         }

         if (Fishing.getBaitPower(inv.getStackInSlot(i).getItem()) > 0.0F) {
            if (bait != null) {
               return false;
            }

            bait = inv.getStackInSlot(i);
         }
      }

      return rod != null && bait != null;
   }

   public ItemStack getCraftingResult(InventoryCrafting inv) {
      ItemStack rod = null;
      ItemStack bait = null;

      for (int i = 0; i < inv.getSizeInventory(); i++) {
         if (inv.getStackInSlot(i).getItem() instanceof ItemFishingRod) {
            rod = inv.getStackInSlot(i);
         }

         if (Fishing.getBaitPower(inv.getStackInSlot(i).getItem()) > 0.0F) {
            bait = inv.getStackInSlot(i);
         }
      }

      ItemStack newrod = rod.copy();
      NBTHelper.SetNBTstring(newrod, bait.getItem().getRegistryName().toString(), "bait");
      return newrod;
   }

   public boolean canFit(int width, int height) {
      return width + height == 3;
   }

   public ItemStack getRecipeOutput() {
      return new ItemStack(ItemsRegister.MAINFISHINGROD);
   }
}
