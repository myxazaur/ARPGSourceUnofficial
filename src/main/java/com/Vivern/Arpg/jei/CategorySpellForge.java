package com.Vivern.Arpg.jei;

import com.Vivern.Arpg.container.GUIResearchTable;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;

public class CategorySpellForge implements IRecipeCategory<WrapperSpellForge> {
   private static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_forging.png");
   public static final String UID = "arpg:spell_forge";
   public IDrawableStatic background;
   public IGuiHelper helper;

   public CategorySpellForge(IGuiHelper h) {
      this.background = h.createDrawable(GUI_TEXTURES, 5, 143, 170, 108);

      for (int i = 0; i < 12; i++) {
         WrapperSpellForge.idrawRunesMain[i] = ARPGPlugin.guihelper.createDrawable(GUIResearchTable.MAIN_RUNES, 0, 12 + 12 * i, 12, 12, 12, 156);
      }
   }

   public String getUid() {
      return "arpg:spell_forge";
   }

   public String getTitle() {
      return "Spell Forge";
   }

   public String getModName() {
      return "arpg";
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public void setRecipe(IRecipeLayout recipeLayout, WrapperSpellForge recipeWrapper, IIngredients ingredients) {
      IGuiItemStackGroup isg = recipeLayout.getItemStacks();

      for (int i = 0; i < 9; i++) {
         int yy = i / 3;
         int xx = i % 3;
         isg.init(i, true, 58 + 18 * xx, 26 + 18 * yy);
         isg.set(i, recipeWrapper.getInOrEmpty(true, i));
      }

      isg.init(9, false, 141, 44);
      isg.set(9, recipeWrapper.getInOrEmpty(false, 9));
      isg.init(10, true, 19, 33);
      isg.set(10, recipeWrapper.getInOrEmpty(true, 10));
   }
}
