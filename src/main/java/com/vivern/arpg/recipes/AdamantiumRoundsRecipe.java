package com.vivern.arpg.recipes;

import com.vivern.arpg.elements.ItemBullet;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;

public class AdamantiumRoundsRecipe extends Impl<IRecipe> implements IRecipe {
   public boolean matches(InventoryCrafting inv, World worldIn) {
      if (inv.getStackInSlot(4).getItem() == ItemsRegister.NUGGETADAMANTIUM) {
         Item item1 = inv.getStackInSlot(1).getItem();
         if (item1 instanceof ItemBullet
            && inv.getStackInSlot(3).getItem() == item1
            && inv.getStackInSlot(5).getItem() == item1
            && inv.getStackInSlot(7).getItem() == item1) {
            return true;
         }
      }

      return false;
   }

   public ItemStack getCraftingResult(InventoryCrafting inv) {
      Item item1 = inv.getStackInSlot(1).getItem();
      if (item1 instanceof ItemBullet) {
         ItemStack st = new ItemStack(ItemsRegister.ADAMANTIUMROUNDS);
         String name = ((ItemBullet)item1).getNbtName();
         NBTHelper.GiveNBTstring(st, name, "bullet");
         NBTHelper.SetNBTstring(st, name, "bullet");
         return st;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public boolean canFit(int width, int height) {
      return width + height == 6;
   }

   public ItemStack getRecipeOutput() {
      return new ItemStack(ItemsRegister.ADAMANTIUMROUNDS);
   }
}
