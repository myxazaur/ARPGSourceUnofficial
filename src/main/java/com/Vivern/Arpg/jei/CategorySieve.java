//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.jei;

import com.Vivern.Arpg.main.NBTHelper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CategorySieve implements IRecipeCategory<WrapperSieve> {
   public static final String UID = "arpg:sieve";
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_sieve.png");
   public IDrawableStatic background;

   public CategorySieve(IGuiHelper h) {
      this.background = h.createDrawable(GUI_TEXTURES, 0, 0, 166, 78);
   }

   public String getUid() {
      return "arpg:sieve";
   }

   public String getTitle() {
      return "Sieve";
   }

   public String getModName() {
      return "arpg";
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public void setRecipe(IRecipeLayout recipeLayout, WrapperSieve recipeWrapper, IIngredients ingredients) {
      IGuiItemStackGroup isg = recipeLayout.getItemStacks();
      int xx1 = 74;
      int yy1 = 2;
      int xx2 = 2;
      int yy2 = 22;
      isg.init(0, true, xx1, yy1);
      isg.set(0, recipeWrapper.getInOrEmpty(true, 0));

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            int indexx = j + i * 9 + 1;
            isg.init(indexx, false, xx2 + j * 18, yy2 + i * 18);
            ItemStack stack = recipeWrapper.getInOrEmpty(false, indexx - 1);
            if (!stack.isEmpty()) {
               NBTHelper.SetLore(stack, "Chance: " + recipeWrapper.getChanceInPercent(indexx - 1) + "%");
            }

            isg.set(indexx, stack);
         }
      }
   }
}
