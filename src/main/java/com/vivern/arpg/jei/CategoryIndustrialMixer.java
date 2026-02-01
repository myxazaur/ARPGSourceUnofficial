package com.vivern.arpg.jei;

import com.vivern.arpg.container.GUIIndustrialMixer;
import com.vivern.arpg.tileentity.TileIndustrialMixer;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;

public class CategoryIndustrialMixer implements IRecipeCategory<WrapperIndustrialMixer> {
   public static final String UID = "arpg:industrial_mixer";
   public IDrawableStatic background;
   public IDrawableAnimated arrow;
   public IGuiHelper helper;

   public CategoryIndustrialMixer(IGuiHelper h) {
      this.background = h.createDrawable(GUIIndustrialMixer.GUI_TEXTURES, 6, 13, 166, 85);
      IDrawableStatic staticarrow = h.createDrawable(GUIIndustrialMixer.GUI_TEXTURES, 176, 35, 48, 44);
      this.arrow = h.createAnimatedDrawable(staticarrow, 60, StartDirection.TOP, false);
   }

   public String getUid() {
      return "arpg:industrial_mixer";
   }

   public String getTitle() {
      return "Industrial Mixer";
   }

   public String getModName() {
      return "arpg";
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public void drawExtras(Minecraft minecraft) {
      this.arrow.draw(minecraft, 58, 20);
   }

   public void setRecipe(IRecipeLayout recipeLayout, WrapperIndustrialMixer recipeWrapper, IIngredients ingredients) {
      IGuiItemStackGroup isg = recipeLayout.getItemStacks();
      IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
      if (recipeWrapper.fluid1 != null && recipeWrapper.fluid1.getFluid() != null) {
         guiFluidStacks.init(0, true, 2, 22, 16, 60, TileIndustrialMixer.fluidMax, true, null);
         guiFluidStacks.set(0, recipeWrapper.fluid1);
      }

      if (recipeWrapper.fluid2 != null && recipeWrapper.fluid2.getFluid() != null) {
         guiFluidStacks.init(1, true, 24, 22, 16, 60, TileIndustrialMixer.fluidMax, true, null);
         guiFluidStacks.set(1, recipeWrapper.fluid2);
      }

      if (recipeWrapper.fluid3 != null && recipeWrapper.fluid3.getFluid() != null) {
         guiFluidStacks.init(2, false, 124, 22, 16, 60, TileIndustrialMixer.fluidMax, true, null);
         guiFluidStacks.set(2, recipeWrapper.fluid3);
      }

      if (recipeWrapper.fluid4 != null && recipeWrapper.fluid4.getFluid() != null) {
         guiFluidStacks.init(3, false, 146, 22, 16, 60, TileIndustrialMixer.fluidMax, true, null);
         guiFluidStacks.set(3, recipeWrapper.fluid4);
      }

      for (int i = 0; i < 3; i++) {
         isg.init(i, true, 55 + 18 * i, 1);
         isg.set(i, recipeWrapper.getInOrEmpty(true, i));
      }

      isg.init(7, true, 95, 33);
      isg.set(7, recipeWrapper.catalystStack1);
      isg.init(6, true, 51, 33);
      isg.set(6, recipeWrapper.catalystStack2);

      for (int i = 0; i < 3; i++) {
         isg.init(i + 3, false, 55 + 18 * i, 65);
         isg.set(i + 3, recipeWrapper.getInOrEmpty(false, i));
      }
   }
}
