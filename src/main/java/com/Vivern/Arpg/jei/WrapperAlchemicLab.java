package com.Vivern.Arpg.jei;

import com.Vivern.Arpg.recipes.AlchemicLabRecipe;
import com.Vivern.Arpg.recipes.Ingridient;
import com.Vivern.Arpg.renders.ManaBar;
import javax.annotation.Nullable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class WrapperAlchemicLab implements IRecipeWrapper {
   public AlchemicLabRecipe recipe;
   public ItemStack catalystStack;
   @Nullable
   public FluidStack fluid1;
   @Nullable
   public FluidStack fluid3;
   FluidStack fluidInput;
   FluidStack fluidOutput;

   public WrapperAlchemicLab(AlchemicLabRecipe recipe) {
      this.recipe = recipe;
   }

   public boolean initFluids() {
      if (this.recipe.costfluidname != null) {
         Fluid fluid = FluidRegistry.getFluid(this.recipe.costfluidname);
         if (fluid == null) {
            return false;
         }

         this.fluid1 = new FluidStack(fluid, this.recipe.amountcost);
         this.fluidInput = this.fluid1;
      }

      if (this.recipe.existfluidname != null) {
         Fluid fluid = FluidRegistry.getFluid(this.recipe.existfluidname);
         if (fluid == null) {
            return false;
         }

         this.fluid3 = new FluidStack(fluid, this.recipe.amountexist);
         this.fluidOutput = this.fluid3;
      }

      return true;
   }

   public void getIngredients(IIngredients ingredients) {
      ingredients.setInputs(VanillaTypes.ITEM, this.recipe.exportInputsAsList());
      ingredients.setInput(VanillaTypes.FLUID, this.fluidInput);
      ingredients.setOutputs(VanillaTypes.ITEM, this.recipe.exportOutputAsList());
      ingredients.setOutput(VanillaTypes.FLUID, this.fluidOutput);
   }

   public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
      if (mouseX > 56 && mouseY > 64 && mouseX < 133 && mouseY < 76) {
         minecraft.fontRenderer.drawString(ManaBar.asString(this.recipe.manacost) + " Mana", mouseX + 4, mouseY, 38655, true);
      }

      // FIX: added `IRecipeWrapper.` before `super`
      IRecipeWrapper.super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
   }

   public ItemStack getInOrEmpty(boolean input, int index) {
      if (input) {
         return index < this.recipe.recipe.size() ? ((Ingridient)this.recipe.recipe.get(index)).createStackForJeiInput() : ItemStack.EMPTY;
      } else {
         return index < this.recipe.output.size() ? ((Ingridient)this.recipe.output.get(index)).createStack() : ItemStack.EMPTY;
      }
   }
}
