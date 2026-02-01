package com.vivern.arpg.jei;

import com.vivern.arpg.recipes.IndustrialMixerRecipe;
import com.vivern.arpg.recipes.Ingridient;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class WrapperIndustrialMixer implements IRecipeWrapper {
   public IndustrialMixerRecipe recipe;
   public ItemStack catalystStack1;
   public ItemStack catalystStack2;
   @Nullable
   public FluidStack fluid1;
   @Nullable
   public FluidStack fluid2;
   @Nullable
   public FluidStack fluid3;
   @Nullable
   public FluidStack fluid4;
   NonNullList<FluidStack> fluidInput = NonNullList.create();
   NonNullList<FluidStack> fluidOutput = NonNullList.create();

   public WrapperIndustrialMixer(IndustrialMixerRecipe recipe) {
      this.recipe = recipe;
      if (recipe.catalyst1 != null) {
         this.catalystStack1 = new ItemStack(recipe.catalyst1);
      }

      if (recipe.catalyst2 != null) {
         this.catalystStack2 = new ItemStack(recipe.catalyst2);
      }
   }

   public boolean initFluids() {
      if (this.recipe.costfluidname1 != null) {
         Fluid fluid = FluidRegistry.getFluid(this.recipe.costfluidname1);
         if (fluid == null) {
            return false;
         }

         this.fluid1 = new FluidStack(fluid, this.recipe.amountcost1);
         this.fluidInput.add(this.fluid1);
      }

      if (this.recipe.costfluidname2 != null) {
         Fluid fluid = FluidRegistry.getFluid(this.recipe.costfluidname2);
         if (fluid == null) {
            return false;
         }

         this.fluid2 = new FluidStack(fluid, this.recipe.amountcost2);
         this.fluidInput.add(this.fluid2);
      }

      if (this.recipe.existfluidname1 != null) {
         Fluid fluid = FluidRegistry.getFluid(this.recipe.existfluidname1);
         if (fluid == null) {
            return false;
         }

         this.fluid3 = new FluidStack(fluid, this.recipe.amountexist1);
         this.fluidOutput.add(this.fluid3);
      }

      if (this.recipe.existfluidname2 != null) {
         Fluid fluid = FluidRegistry.getFluid(this.recipe.existfluidname2);
         if (fluid == null) {
            return false;
         }

         this.fluid4 = new FluidStack(fluid, this.recipe.amountexist2);
         this.fluidOutput.add(this.fluid4);
      }

      return true;
   }

   public void getIngredients(IIngredients ingredients) {
      List inputItems;
      if (this.recipe.catalyst1 == null && this.recipe.catalyst2 == null) {
         inputItems = this.recipe.exportInputsAsList();
      } else {
         inputItems = new ArrayList();
         inputItems.addAll(this.recipe.exportInputsAsList());
         if (this.catalystStack1 != null) {
            inputItems.add(this.catalystStack1);
         }

         if (this.catalystStack2 != null) {
            inputItems.add(this.catalystStack2);
         }
      }

      ingredients.setInputs(VanillaTypes.ITEM, inputItems);
      ingredients.setInputs(VanillaTypes.FLUID, this.fluidInput);
      ingredients.setOutputs(VanillaTypes.ITEM, this.recipe.exportOutputAsList());
      ingredients.setOutputs(VanillaTypes.FLUID, this.fluidOutput);
   }

   public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
      if (mouseX > 75 && mouseY > 25 && mouseX < 91 && mouseY < 59) {
         minecraft.fontRenderer.drawString("RF: " + this.recipe.rfToAll, mouseX, mouseY, -1, true);
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
