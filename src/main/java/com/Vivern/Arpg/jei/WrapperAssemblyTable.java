//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.jei;

import com.Vivern.Arpg.container.GUIAssemblyTable;
import com.Vivern.Arpg.recipes.AssemblyTableRecipe;
import com.Vivern.Arpg.recipes.Ingridient;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class WrapperAssemblyTable implements IRecipeWrapper {
   public AssemblyTableRecipe recipe;

   public WrapperAssemblyTable(AssemblyTableRecipe recipe) {
      this.recipe = recipe;
   }

   public void getIngredients(IIngredients ingredients) {
      ingredients.setInputs(VanillaTypes.ITEM, this.recipe.exportInputsAsList());
      ingredients.setOutputs(
         VanillaTypes.ITEM,
         NonNullList.from(ItemStack.EMPTY, new ItemStack[]{this.recipe.craftresult.createStack(), this.recipe.craftresult2.createStack()})
      );
   }

   public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
      if (ARPGPlugin.guihelper != null && this.recipe.augmentsRecipe != null && !this.recipe.augmentsRecipe.isEmpty()) {
         int i = 0;

         for (AssemblyTableRecipe.AugmentCost augcost : this.recipe.augmentsRecipe) {
            IDrawableStatic augment = ARPGPlugin.guihelper.createDrawable(GUIAssemblyTable.GUI_TEXTURES, 180 + 12 * augcost.augmentID, 31, 12, 12);
            augment.draw(minecraft, 9 + i * 27, 23);
            i++;
         }
      }

      if (mouseX > 2 && mouseY > 76 && mouseX < 20 && mouseY < 112) {
         minecraft.fontRenderer.drawString("RF: " + this.recipe.RFToTick * (this.recipe.timeToCraft + 1), mouseX, mouseY, -1, true);
      }

      // FIX: added `IRecipeWrapper.` before `super`
      IRecipeWrapper.super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
   }

   public ItemStack getInOrEmpty(boolean input, int index) {
      if (input) {
         return index < this.recipe.recipe.size() ? ((Ingridient)this.recipe.recipe.get(index)).createStackForJeiInput() : ItemStack.EMPTY;
      } else if (index == 0) {
         return this.recipe.craftresult.createStack();
      } else {
         return index == 1 ? this.recipe.craftresult2.createStack() : ItemStack.EMPTY;
      }
   }
}
