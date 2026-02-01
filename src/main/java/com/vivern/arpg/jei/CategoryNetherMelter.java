package com.vivern.arpg.jei;

import com.vivern.arpg.container.GUINetherMelter;
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

public class CategoryNetherMelter implements IRecipeCategory<WrapperNetherMelter> {
   public static final String UID = "arpg:nether_melter";
   public IDrawableStatic background;
   public IDrawableAnimated arrow;

   public CategoryNetherMelter(IGuiHelper h) {
      this.background = h.createDrawable(GUINetherMelter.FURNACE_GUI_TEXTURES, 5, 14, 165, 48);
      IDrawableStatic staticarrow = h.createDrawable(GUINetherMelter.FURNACE_GUI_TEXTURES, 176, 14, 41, 16);
      this.arrow = h.createAnimatedDrawable(staticarrow, 60, StartDirection.LEFT, false);
   }

   public String getUid() {
      return "arpg:nether_melter";
   }

   public String getTitle() {
      return "Nether Melter";
   }

   public String getModName() {
      return "arpg";
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public void drawExtras(Minecraft minecraft) {
      this.arrow.draw(minecraft, 99, 6);
   }

   public void setRecipe(IRecipeLayout recipeLayout, WrapperNetherMelter recipeWrapper, IIngredients ingredients) {
      IGuiItemStackGroup isg = recipeLayout.getItemStacks();

      for (int i = 0; i < 5; i++) {
         isg.init(i, true, 2 + 18 * i, 2);
         isg.set(i, recipeWrapper.getInOrEmpty(true, i));
      }

      for (int i = 0; i < 3; i++) {
         isg.init(i + 5, false, 109 + 18 * i, 22);
         isg.set(i + 5, recipeWrapper.getInOrEmpty(false, i));
      }
   }
}
