package com.Vivern.Arpg.jei;

import com.Vivern.Arpg.container.GUIAssemblyTable;
import com.Vivern.Arpg.recipes.AssemblyTableRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;

public class CategoryAssemblyTable implements IRecipeCategory<WrapperAssemblyTable> {
   public static final String UID = "arpg:assembly_table";
   public IDrawableStatic background;
   public IDrawableAnimated arrow;
   public IGuiHelper helper;

   public CategoryAssemblyTable(IGuiHelper h) {
      this.background = h.createDrawable(GUIAssemblyTable.GUI_TEXTURES, 19, 16, 152, 125);
      IDrawableStatic staticarrow = h.createDrawable(GUIAssemblyTable.GUI_TEXTURES, 192, 14, 24, 17);
      this.arrow = h.createAnimatedDrawable(staticarrow, 60, StartDirection.LEFT, false);
   }

   public String getUid() {
      return "arpg:assembly_table";
   }

   public String getTitle() {
      return "Assembly Table";
   }

   public String getModName() {
      return "arpg";
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public void drawExtras(Minecraft minecraft) {
      this.arrow.draw(minecraft, 95, 77);
   }

   public void setRecipe(IRecipeLayout recipeLayout, WrapperAssemblyTable recipeWrapper, IIngredients ingredients) {
      IGuiItemStackGroup isg = recipeLayout.getItemStacks();

      for (int i = 0; i < 9; i++) {
         int yy = i / 3;
         int xx = i % 3;
         isg.init(i, true, 32 + 18 * xx, 58 + 18 * yy);
         isg.set(i, recipeWrapper.getInOrEmpty(true, i));
      }

      if (recipeWrapper.recipe.augmentsRecipe != null && !recipeWrapper.recipe.augmentsRecipe.isEmpty()) {
         int i = 11;

         for (AssemblyTableRecipe.AugmentCost augcost : recipeWrapper.recipe.augmentsRecipe) {
            isg.init(i, true, 6 + 27 * (i - 11), 2);
            isg.set(i, augcost.ingridient.createStackForJeiInput());
            i++;
         }
      }

      for (int i = 0; i < 2; i++) {
         isg.init(i + 9, false, 128, 75 + 30 * i);
         isg.set(i + 9, recipeWrapper.getInOrEmpty(false, i));
      }
   }
}
