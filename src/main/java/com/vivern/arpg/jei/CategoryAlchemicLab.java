package com.vivern.arpg.jei;

import com.vivern.arpg.container.GUIAlchemicLab;
import com.vivern.arpg.tileentity.TileAlchemicLab;
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

public class CategoryAlchemicLab implements IRecipeCategory<WrapperAlchemicLab> {
   public static final String UID = "arpg:alchemic_lab";
   public IDrawableStatic background;
   public IDrawableAnimated arrow;
   public IDrawableAnimated flame;
   public IDrawableAnimated mana;
   public IGuiHelper helper;

   public CategoryAlchemicLab(IGuiHelper h) {
      this.background = h.createDrawable(GUIAlchemicLab.GUI_TEXTURES, 6, 7, 188, 79);
      IDrawableStatic staticarrow = h.createDrawable(GUIAlchemicLab.GUI_TEXTURES, 0, 212, 46, 32);
      this.arrow = h.createAnimatedDrawable(staticarrow, 60, StartDirection.LEFT, false);
      IDrawableStatic staticflame = h.createDrawable(GUIAlchemicLab.GUI_TEXTURES, 0, 190, 14, 14);
      this.flame = h.createAnimatedDrawable(staticflame, 60, StartDirection.BOTTOM, false);
      IDrawableStatic staticmana = h.createDrawable(GUIAlchemicLab.GUI_TEXTURES, 0, 204, 74, 8);
      this.mana = h.createAnimatedDrawable(staticmana, 120, StartDirection.RIGHT, true);
   }

   public String getUid() {
      return "arpg:alchemic_lab";
   }

   public String getTitle() {
      return "Alchemic Lab";
   }

   public String getModName() {
      return "arpg";
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public void drawExtras(Minecraft minecraft) {
      this.arrow.draw(minecraft, 71, 14);
      this.flame.draw(minecraft, 87, 51);
      this.mana.draw(minecraft, 57, 66);
   }

   public void setRecipe(IRecipeLayout recipeLayout, WrapperAlchemicLab recipeWrapper, IIngredients ingredients) {
      IGuiItemStackGroup isg = recipeLayout.getItemStacks();
      IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
      guiFluidStacks.init(0, true, 2, 9, 16, 60, TileAlchemicLab.fluidMax, true, null);
      guiFluidStacks.set(0, recipeWrapper.fluid1);
      guiFluidStacks.init(1, false, 170, 9, 16, 60, TileAlchemicLab.fluidMax, true, null);
      guiFluidStacks.set(1, recipeWrapper.fluid3);

      for (int i = 0; i < 3; i++) {
         isg.init(i, true, 31, 8 + i * 18);
         isg.set(i, recipeWrapper.getInOrEmpty(true, i));
      }

      for (int i = 0; i < 3; i++) {
         isg.init(i + 3, true, 49, 8 + i * 18);
         isg.set(i + 3, recipeWrapper.getInOrEmpty(true, i + 3));
      }

      for (int i = 0; i < 3; i++) {
         isg.init(i + 6, false, 121, 8 + i * 18);
         isg.set(i + 6, recipeWrapper.getInOrEmpty(false, i));
      }

      for (int i = 0; i < 3; i++) {
         isg.init(i + 9, false, 139, 8 + i * 18);
         isg.set(i + 9, recipeWrapper.getInOrEmpty(false, i + 3));
      }
   }
}
