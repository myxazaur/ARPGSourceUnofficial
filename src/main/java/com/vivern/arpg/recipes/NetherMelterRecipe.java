package com.vivern.arpg.recipes;

import com.vivern.arpg.tileentity.TileNetherMelter;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class NetherMelterRecipe {
   public NonNullList<Ingridient> recipe;
   public NonNullList<Ingridient> output;
   public float manacost;

   public NetherMelterRecipe(Ingridient[] output, float manacost, Ingridient[] recipe) {
      this.manacost = manacost;
      this.recipe = NonNullList.from(Ingridient.EMPTY, recipe);
      this.output = NonNullList.from(Ingridient.EMPTY, output);
   }

   public boolean isAllowForCraft(TileNetherMelter melter) {
      int countInTile = 0;

      for (int i = 0; i < 5; i++) {
         if (!melter.getStackInSlot(i).isEmpty()) {
            countInTile++;
         }
      }

      if (countInTile != this.recipe.size()) {
         return false;
      } else {
         for (Ingridient need : this.recipe) {
            boolean hasThisStack = false;

            for (int ix = 0; ix < 5; ix++) {
               ItemStack has = melter.getStackInSlot(ix);
               if (this.allowed(has, need)) {
                  if (hasThisStack) {
                     return false;
                  }

                  hasThisStack = true;
               }
            }

            if (!hasThisStack) {
               return false;
            }
         }

         for (Ingridient out : this.output) {
            int slottoset = this.trySetTo(out.createStack(), melter);
            if (slottoset == -1) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean tryCraft(TileNetherMelter melter) {
      int countInTile = 0;

      for (int i = 0; i < 5; i++) {
         if (!melter.getStackInSlot(i).isEmpty()) {
            countInTile++;
         }
      }

      if (countInTile != this.recipe.size()) {
         return false;
      } else {
         for (Ingridient need : this.recipe) {
            boolean hasThisStack = false;

            for (int ix = 0; ix < 5; ix++) {
               ItemStack has = melter.getStackInSlot(ix);
               if (this.allowed(has, need)) {
                  if (hasThisStack) {
                     return false;
                  }

                  hasThisStack = true;
               }
            }

            if (!hasThisStack) {
               return false;
            }
         }

         for (Ingridient out : this.output) {
            int slottoset = this.trySetTo(out.createStack(), melter);
            if (slottoset == -1) {
               return false;
            }
         }

         for (Ingridient outx : this.output) {
            ItemStack outstack = outx.createStack();
            int slottoset = this.trySetTo(outstack, melter);
            if (slottoset != -1) {
               ItemStack have = melter.getStackInSlot(slottoset);
               ItemStack result = have.isEmpty()
                  ? outstack
                  : new ItemStack(have.getItem(), have.getCount() + outx.getCount(), have.getMetadata(), have.getTagCompound());
               melter.setInventorySlotContents(slottoset, result);
            }
         }

         for (Ingridient need : this.recipe) {
            int indx = this.findStackIndex(need, melter);
            if (indx != -1) {
               ItemStack have = melter.getStackInSlot(indx);
               int size = have.getCount() - need.getCount();
               ItemStack result = size > 0 ? new ItemStack(have.getItem(), size, have.getMetadata(), have.getTagCompound()) : ItemStack.EMPTY;
               melter.setInventorySlotContents(indx, result);
            }
         }

         return true;
      }
   }

   public boolean allowed(ItemStack have, Ingridient need) {
      return need.isStackAllowed(have);
   }

   public int trySetTo(ItemStack stack, TileNetherMelter melter) {
      int stlimit = melter.getInventoryStackLimit();

      for (int i = 5; i < 8; i++) {
         ItemStack have = melter.getStackInSlot(i);
         if (have.isEmpty()) {
            return i;
         }

         if (this.isItemEquals(stack, have) && have.getCount() + stack.getCount() <= stlimit) {
            return i;
         }
      }

      return -1;
   }

   public int findStackIndex(Ingridient stack, TileNetherMelter melter) {
      for (int i = 0; i < 5; i++) {
         ItemStack have = melter.getStackInSlot(i);
         if (!have.isEmpty() && this.allowed(have, stack)) {
            return i;
         }
      }

      return -1;
   }

   public boolean isItemEquals(ItemStack stack, ItemStack other) {
      boolean hastag = stack.hasTagCompound() == other.hasTagCompound();
      boolean eqtag = true;
      if (stack.hasTagCompound() && hastag) {
         eqtag = stack.getTagCompound().equals(other.getTagCompound());
      }

      return !other.isEmpty() && stack.getItem() == other.getItem() && stack.getItemDamage() == other.getItemDamage() && hastag && eqtag;
   }

   public List<ItemStack> exportInputsAsList() {
      List<ItemStack> list = new ArrayList<>();

      for (int i = 0; i < this.recipe.size(); i++) {
         if (this.recipe.get(i) != Ingridient.EMPTY) {
            list.add(((Ingridient)this.recipe.get(i)).createStack());
         }
      }

      return list;
   }

   public List<ItemStack> exportOutputAsList() {
      List<ItemStack> list = new ArrayList<>();

      for (int i = 0; i < this.output.size(); i++) {
         if (this.output.get(i) != Ingridient.EMPTY) {
            list.add(((Ingridient)this.output.get(i)).createStack());
         }
      }

      return list;
   }
}
