package com.vivern.arpg.jei;

import com.vivern.arpg.recipes.Ingridient;
import com.vivern.arpg.recipes.SieveRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class WrapperSieve implements IRecipeWrapper {
   public SieveRecipe recipe;

   public WrapperSieve(SieveRecipe recipe) {
      this.recipe = recipe;
   }

   public void getIngredients(IIngredients ingredients) {
      ingredients.setInputs(VanillaTypes.ITEM, this.recipe.exportInputsAsList());
      ingredients.setOutputs(VanillaTypes.ITEM, this.recipe.exportOutputAsList());
   }

   public ItemStack getInOrEmpty(boolean input, int index) {
      if (input && index == 0) {
         return this.recipe.input.createStackForJeiInput();
      } else {
         return index < this.recipe.output.size() ? ((Ingridient)this.recipe.output.get(index)).createStack() : ItemStack.EMPTY;
      }
   }

   public int getChanceInPercent(int index) {
      return index < this.recipe.output.size() ? Math.round(this.recipe.chances[index] * 100.0F) : 0;
   }
}
