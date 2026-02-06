package com.Vivern.Arpg.main;

import com.Vivern.Arpg.elements.armor.SnowcoatHelm;
import com.Vivern.Arpg.recipes.MoltenGreataxeOil;
import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class RecipesRegister {
   @SubscribeEvent
   public static void registerRecipes(Register<IRecipe> event) {
      IRecipe rRecipe = new MoltenGreataxeOil();
      rRecipe.setRegistryName(MoltenGreataxeOil.name);
      event.getRegistry().register(rRecipe);
      event.getRegistry().register(new SnowcoatHelm.SnowcoatDye().setRegistryName(SnowcoatHelm.SnowcoatDye.name));
   }

   public static IRecipe getShapelessRecipe(ResourceLocation name, ResourceLocation group, @Nonnull ItemStack output, Ingredient... params) {
      NonNullList<Ingredient> lst = NonNullList.create();

      for (Ingredient i : params) {
         lst.add(i);
      }

      return (IRecipe)new ShapelessRecipes(group == null ? "" : group.toString(), output, lst).setRegistryName(name);
   }
}
