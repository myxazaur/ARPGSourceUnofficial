package com.vivern.arpg.jei;

import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.Spell;
import com.vivern.arpg.recipes.Ingridient;
import com.vivern.arpg.recipes.SpellForgeRecipe;
import com.vivern.arpg.renders.ManaBar;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WrapperSpellForge implements IRecipeWrapper {
   public SpellForgeRecipe recipe;
   public static IDrawableStatic[] idrawRunesMain = new IDrawableStatic[12];
   private static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_forging.png");

   public WrapperSpellForge(SpellForgeRecipe recipe) {
      this.recipe = recipe;
   }

   public void getIngredients(IIngredients ingredients) {
      ingredients.setInputs(VanillaTypes.ITEM, this.recipe.exportInputsAsList());
      ingredients.setOutput(VanillaTypes.ITEM, this.recipe.craftresult.createStack());
   }

   public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
      minecraft.fontRenderer.drawString(ManaBar.asString(this.recipe.hitsNeed), 129.0F, 28.0F, 7763574, false);
      if (this.recipe.useEnergy) {
         int sum = 0;

         for (int i = 0; i < 12; i++) {
            float amount = this.recipe.arrayelementsCost[i];
            if (amount > 0.0F) {
               sum++;
            }
         }

         int oneLength = 12;
         int space = 2;
         int fullLength = oneLength * sum + space * (sum - 1);
         int halfLength = fullLength / 2;
         int currentTranslation = 85 - halfLength;
         boolean first = true;

         for (int ix = 0; ix < 12; ix++) {
            float amount = this.recipe.arrayelementsCost[ix];
            if (amount > 0.0F) {
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               idrawRunesMain[ix].draw(minecraft, currentTranslation, 87);
               String amountstring = ManaBar.asString(amount);
               minecraft.fontRenderer
                  .drawString(amountstring, currentTranslation + 6 - minecraft.fontRenderer.getStringWidth(amountstring) / 2, 98.0F, 16777215, true);
               currentTranslation += oneLength + space;
            }
         }
      }

      if (this.recipe.spells != null && this.recipe.spells.length > 0) {
         int oneLength = 9;
         int space = this.recipe.spells.length > 14 ? (this.recipe.spells.length > 16 ? (this.recipe.spells.length > 18 ? -1 : 0) : 1) : 2;
         int fullLength = oneLength * this.recipe.spells.length + space * (this.recipe.spells.length - 1);
         int halfLength = fullLength / 2;
         int currentTranslation = 85 - halfLength;

         for (int k = 0; k < this.recipe.spells.length; k++) {
            Spell spell = this.recipe.spells[k];
            spell.renderRune(currentTranslation + -5, 1, true);
            currentTranslation += oneLength + space;
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (this.recipe.catalyst != null) {
         IDrawableStatic drawCatalystSlot = ARPGPlugin.guihelper.createDrawable(GUI_TEXTURES, 180, 206, 46, 35, 256, 256);
         IDrawableStatic drawOverlay = ARPGPlugin.guihelper.createDrawable(GUI_TEXTURES, 180, 138, 46, 68, 256, 256);
         drawCatalystSlot.draw(minecraft, 5, 45);
         GlStateManager.pushMatrix();
         GlStateManager.translate(28.0F, 69.0F, 20.0F);
         GlStateManager.rotate(AnimationTimer.tick / 2.0F, 0.0F, 1.0F, 0.57735F);
         float scl = 32.0F;
         GlStateManager.scale(scl, scl, scl);
         minecraft.getRenderItem().renderItem(new ItemStack(BlocksRegister.GEMSPARKBLOCK), TransformType.GUI);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 0.0F, 40.0F);
         drawOverlay.draw(minecraft, 5, 19);
         GlStateManager.popMatrix();
      }

      // FIX: added `IRecipeWrapper.` before `super`
      IRecipeWrapper.super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
   }

   public ItemStack getInOrEmpty(boolean input, int index) {
      if (input) {
         if (index == 10 && this.recipe.catalyst != null) {
            return this.recipe.catalyst.getGoodStackForRender();
         } else {
            return index < this.recipe.recipe.size() ? ((Ingridient)this.recipe.recipe.get(index)).createStackForJeiInput() : ItemStack.EMPTY;
         }
      } else {
         return this.recipe.craftresult.createStack();
      }
   }
}
