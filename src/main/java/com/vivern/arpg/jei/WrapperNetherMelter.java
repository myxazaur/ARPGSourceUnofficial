package com.vivern.arpg.jei;

import com.vivern.arpg.recipes.Ingridient;
import com.vivern.arpg.recipes.NetherMelterRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class WrapperNetherMelter implements IRecipeWrapper {
   public NetherMelterRecipe recipe;

   public WrapperNetherMelter(NetherMelterRecipe recipe) {
      this.recipe = recipe;
   }

   public void getIngredients(IIngredients ingredients) {
      ingredients.setInputs(VanillaTypes.ITEM, this.recipe.exportInputsAsList());
      ingredients.setOutputs(VanillaTypes.ITEM, this.recipe.exportOutputAsList());
   }

   public ItemStack getInOrEmpty(boolean input, int index) {
      if (input) {
         return index < this.recipe.recipe.size() ? ((Ingridient)this.recipe.recipe.get(index)).createStackForJeiInput() : ItemStack.EMPTY;
      } else {
         return index < this.recipe.output.size() ? ((Ingridient)this.recipe.output.get(index)).createStack() : ItemStack.EMPTY;
      }
   }
}
