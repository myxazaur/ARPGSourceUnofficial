//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.jei;

import com.Vivern.Arpg.main.AlchemicLabRecipesRegister;
import com.Vivern.Arpg.main.AssemblyTableRecipesRegister;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.IndustrialMixerRecipesRegister;
import com.Vivern.Arpg.main.NetherMelterRecipesRegister;
import com.Vivern.Arpg.main.SieveRecipesRegister;
import com.Vivern.Arpg.main.SpellForgeRecipesRegister;
import com.Vivern.Arpg.recipes.AlchemicLabRecipe;
import com.Vivern.Arpg.recipes.AssemblyTableRecipe;
import com.Vivern.Arpg.recipes.IndustrialMixerRecipe;
import com.Vivern.Arpg.recipes.NetherMelterRecipe;
import com.Vivern.Arpg.recipes.SieveRecipe;
import com.Vivern.Arpg.recipes.SpellForgeRecipe;
import java.util.ArrayList;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class ARPGPlugin implements IModPlugin {
   public static CategoryNetherMelter categoryNetherMelter;
   public static CategoryAssemblyTable categoryAssemblyTable;
   public static CategorySpellForge categorySpellForge;
   public static CategoryIndustrialMixer categoryIndustrialMixer;
   public static CategoryAlchemicLab categoryAlchemicLab;
   public static CategorySieve categorySieve;
   public static IGuiHelper guihelper = null;
   static long prevTime = 0L;

   public void registerCategories(IRecipeCategoryRegistration registry) {
      guihelper = registry.getJeiHelpers().getGuiHelper();
      categoryNetherMelter = new CategoryNetherMelter(guihelper);
      categoryAssemblyTable = new CategoryAssemblyTable(guihelper);
      categorySpellForge = new CategorySpellForge(guihelper);
      categoryIndustrialMixer = new CategoryIndustrialMixer(guihelper);
      categoryAlchemicLab = new CategoryAlchemicLab(guihelper);
      categorySieve = new CategorySieve(guihelper);
      registry.addRecipeCategories(
         new IRecipeCategory[]{categoryNetherMelter, categoryAssemblyTable, categorySpellForge, categoryIndustrialMixer, categoryAlchemicLab, categorySieve}
      );
   }

   public void register(IModRegistry registry) {
      ArrayList<WrapperNetherMelter> listNM = new ArrayList<>();

      for (NetherMelterRecipe recipe : NetherMelterRecipesRegister.allRecipes) {
         WrapperNetherMelter wrapper = new WrapperNetherMelter(recipe);
         listNM.add(wrapper);
      }

      registry.addRecipes(listNM, "arpg:nether_melter");
      registry.addRecipeCatalyst(new ItemStack(BlocksRegister.NETHERMELTER), new String[]{"arpg:nether_melter"});
      ArrayList<WrapperAssemblyTable> listAT = new ArrayList<>();

      for (AssemblyTableRecipe recipe : AssemblyTableRecipesRegister.allRecipes) {
         WrapperAssemblyTable wrapper = new WrapperAssemblyTable(recipe);
         listAT.add(wrapper);
      }

      registry.addRecipes(listAT, "arpg:assembly_table");
      registry.addRecipeCatalyst(new ItemStack(BlocksRegister.ASSEMBLYTABLE), new String[]{"arpg:assembly_table"});
      ArrayList<WrapperSpellForge> listSF = new ArrayList<>();

      for (SpellForgeRecipe recipe : SpellForgeRecipesRegister.allRecipes) {
         WrapperSpellForge wrapper = new WrapperSpellForge(recipe);
         listSF.add(wrapper);
      }

      registry.addRecipes(listSF, "arpg:spell_forge");
      registry.addRecipeCatalyst(new ItemStack(BlocksRegister.SPELLFORGE), new String[]{"arpg:spell_forge"});
      ArrayList<WrapperIndustrialMixer> listIM = new ArrayList<>();

      for (IndustrialMixerRecipe recipe : IndustrialMixerRecipesRegister.allRecipes) {
         WrapperIndustrialMixer wrapper = new WrapperIndustrialMixer(recipe);
         if (wrapper.initFluids()) {
            listIM.add(wrapper);
         }
      }

      registry.addRecipes(listIM, "arpg:industrial_mixer");
      registry.addRecipeCatalyst(new ItemStack(BlocksRegister.INDUSTRIALMIXER), new String[]{"arpg:industrial_mixer"});
      ArrayList<WrapperAlchemicLab> listAL = new ArrayList<>();

      for (AlchemicLabRecipe recipex : AlchemicLabRecipesRegister.allRecipes) {
         WrapperAlchemicLab wrapper = new WrapperAlchemicLab(recipex);
         if (wrapper.initFluids()) {
            listAL.add(wrapper);
         }
      }

      registry.addRecipes(listAL, "arpg:alchemic_lab");
      registry.addRecipeCatalyst(new ItemStack(BlocksRegister.ALCHEMICLAB), new String[]{"arpg:alchemic_lab"});
      ArrayList<WrapperSieve> listSI = new ArrayList<>();

      for (SieveRecipe recipexx : SieveRecipesRegister.allRecipes) {
         WrapperSieve wrapper = new WrapperSieve(recipexx);
         listSI.add(wrapper);
      }

      registry.addRecipes(listSI, "arpg:sieve");
      registry.addRecipeCatalyst(new ItemStack(BlocksRegister.SIEVE), new String[]{"arpg:sieve"});
      registry.addRecipeCatalyst(new ItemStack(BlocksRegister.ELECTRICSIEVE), new String[]{"arpg:sieve"});
   }

   public static void tryFixOredicShow(Minecraft minecraft) {
   }

   public static void tick() {
      tryFixOredicShow(Minecraft.getMinecraft());
   }
}
