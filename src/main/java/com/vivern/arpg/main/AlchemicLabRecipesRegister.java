package com.vivern.arpg.main;

import com.vivern.arpg.recipes.AlchemicLabRecipe;
import com.vivern.arpg.recipes.Ingridient;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;

public class AlchemicLabRecipesRegister {
   public static final Ingridient EM = Ingridient.EMPTY;
   public static List<AlchemicLabRecipe> allRecipes = new ArrayList<>();

   @Nullable
   public static AlchemicLabRecipe addRecipe(
      float mana,
      Ingridient output6,
      Ingridient output7,
      Ingridient output8,
      Ingridient output9,
      Ingridient output10,
      Ingridient output11,
      int ticksToBrew,
      Ingridient input0,
      Ingridient input1,
      Ingridient input2,
      Ingridient input3,
      Ingridient input4,
      Ingridient input5
   ) {
      List<Ingridient> listoutput = new ArrayList<>();
      if (output6 != Ingridient.EMPTY) {
         listoutput.add(output6);
      }

      if (output7 != Ingridient.EMPTY) {
         listoutput.add(output7);
      }

      if (output8 != Ingridient.EMPTY) {
         listoutput.add(output8);
      }

      if (output9 != Ingridient.EMPTY) {
         listoutput.add(output9);
      }

      if (output10 != Ingridient.EMPTY) {
         listoutput.add(output10);
      }

      if (output11 != Ingridient.EMPTY) {
         listoutput.add(output11);
      }

      List<Ingridient> listinput = new ArrayList<>();
      if (input0 != Ingridient.EMPTY) {
         listinput.add(input0);
      }

      if (input1 != Ingridient.EMPTY) {
         listinput.add(input1);
      }

      if (input2 != Ingridient.EMPTY) {
         listinput.add(input2);
      }

      if (input3 != Ingridient.EMPTY) {
         listinput.add(input3);
      }

      if (input4 != Ingridient.EMPTY) {
         listinput.add(input4);
      }

      if (input5 != Ingridient.EMPTY) {
         listinput.add(input5);
      }

      return addRecipe(mana, listoutput.toArray(new Ingridient[listoutput.size()]), ticksToBrew, listinput.toArray(new Ingridient[listinput.size()]));
   }

   @Nullable
   public static AlchemicLabRecipe addRecipe(float mana, ItemStack[] outputSt, int ticksToBrew, ItemStack[] inputSt) {
      Ingridient[] output = new Ingridient[outputSt.length];
      Ingridient[] input = new Ingridient[inputSt.length];

      for (int i = 0; i < output.length; i++) {
         if (outputSt[i] == null) {
            return null;
         }

         output[i] = Ingridient.getIngridient(outputSt[i]);
      }

      for (int i = 0; i < input.length; i++) {
         if (inputSt[i] == null) {
            return null;
         }

         input[i] = Ingridient.getIngridient(inputSt[i]);
      }

      return addRecipe(mana, output, ticksToBrew, input);
   }

   @Nullable
   public static AlchemicLabRecipe addRecipe(float mana, Ingridient[] output, int ticksToBrew, Ingridient[] input) {
      for (Ingridient st : input) {
         if (st == null) {
            return null;
         }
      }

      for (Ingridient stx : output) {
         if (stx == null) {
            return null;
         }
      }

      AlchemicLabRecipe recipe = new AlchemicLabRecipe(mana, ticksToBrew, input, output);
      setregister(recipe);
      return recipe;
   }

   public static void register() {
      addRecipe(
            20.0F,
            EM,
            EM,
            EM,
            EM,
            EM,
            EM,
            100,
            new Ingridient.IngridientItem("arpg:limestone_dust", 4, 0, false),
            new Ingridient.IngridientItem("arpg:glowing_crystal_dust", 1, 0, false),
            EM,
            new Ingridient.IngridientItem("dustNetherQuartz", 4),
            EM,
            EM
         )
         .setFluidCost("tile.water", 750)
         .setFluidOutput("hydrothermal_solution", 1000);
      addRecipe(
            20.0F,
            EM,
            EM,
            EM,
            EM,
            EM,
            EM,
            100,
            new Ingridient.IngridientItem("arpg:limestone_dust", 4, 0, false),
            new Ingridient.IngridientItem("arpg:glowing_crystal_dust", 1, 0, false),
            EM,
            new Ingridient.IngridientItem("dustQuartz", 4),
            EM,
            EM
         )
         .setFluidCost("tile.water", 750)
         .setFluidOutput("hydrothermal_solution", 1000);
      addRecipe(
            20.0F,
            EM,
            EM,
            EM,
            EM,
            EM,
            EM,
            100,
            new Ingridient.IngridientItem("arpg:limestone_dust", 4, 0, false),
            new Ingridient.IngridientItem("arpg:glowing_crystal_dust", 1, 0, false),
            EM,
            new Ingridient.IngridientItem("dustObsidian", 4),
            EM,
            EM
         )
         .setFluidCost("tile.water", 750)
         .setFluidOutput("hydrothermal_solution", 1000);
      addRecipe(
            5.0F,
            new Ingridient.IngridientItem("arpg:toxicola", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            120,
            new Ingridient.IngridientItem("arpg:toxiberry_vibrant_seed", 1, 0, false),
            new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false),
            new Ingridient.IngridientItem("minecraft:glass_bottle", 1, 0, false),
            new Ingridient.IngridientItem("arpg:glowing_toxiberry", 1, 0, false),
            new Ingridient.IngridientItem("arpg:toxiberry_arcanophyllum_seed", 1, 0, false),
            new Ingridient.IngridientItem("arpg:toxibulb_seed", 1, 0, false)
         )
         .setFluidCost("tile.water", 250);
      addRecipe(
            5.0F,
            new Ingridient.IngridientItem("arpg:toxiberry_mojito", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            80,
            new Ingridient.IngridientItem("arpg:toxinia_seed", 1, 0, false),
            new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false),
            new Ingridient.IngridientItem("minecraft:glass_bottle", 1, 0, false),
            new Ingridient.IngridientItem("arpg:small_toxiberry", 1, 0, false),
            new Ingridient.IngridientItem("arpg:viscosa_seed", 1, 0, false),
            EM
         )
         .setFluidCost("luminescent_liquid", 80);
      addRecipe(
            4.0F,
            new Ingridient.IngridientItem("arpg:deceidus_juice", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            70,
            new Ingridient.IngridientItem("arpg:deceidus_seed", 3, 0, false),
            new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false),
            new Ingridient.IngridientItem("paper", 1),
            new Ingridient.IngridientItem("dyePurple", 1),
            EM,
            EM
         )
         .setFluidCost("tile.water", 250);
      addRecipe(
            8.0F,
            new Ingridient.IngridientItem("arpg:crimberry_wine", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            100,
            new Ingridient.IngridientItem("arpg:crimberry_seed", 4, 0, false),
            new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false),
            new Ingridient.IngridientItem("minecraft:glass_bottle", 1, 0, false),
            new Ingridient.IngridientItem("minecraft:apple", 1, 0, false),
            new Ingridient.IngridientItem("arpg:cocoa_powder", 1, 0, false),
            EM
         )
         .setFluidCost("tile.water", 250);
      addRecipe(
            10.0F,
            new Ingridient.IngridientItem("arpg:cryogen_cell", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            50,
            new Ingridient.IngridientItem("arpg:empty_cell", 1, 0, false),
            new Ingridient.IngridientItem("arpg:ice_flower", 1, 0, false),
            new Ingridient.IngridientItem("arpg:ice_dust", 1, 0, false),
            EM,
            EM,
            EM
         )
         .setFluidCost("cryon", 100);
      addRecipe(
            5.0F,
            new Ingridient.IngridientItem("arpg:slime_cell", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            60,
            new Ingridient.IngridientItem("arpg:empty_cell", 1, 0, false),
            new Ingridient.IngridientItem("slimeball", 1),
            new Ingridient.IngridientItem("dustSulfur", 1),
            EM,
            EM,
            EM
         )
         .setFluidCost("slime", 250);
      addRecipe(
            1.0F,
            new Ingridient.IngridientItem("arpg:molotov_cocktail", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            10,
            new Ingridient.IngridientItem("minecraft:glass_bottle", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM
         )
         .setFluidCost("pyrotheum", 50);
      addRecipe(
            10.0F,
            new Ingridient.IngridientItem("arpg:bullet_crystal", 64, 0, false),
            new Ingridient.IngridientItem("arpg:bullet_crystal", 32, 0, false),
            EM,
            EM,
            EM,
            EM,
            100,
            new Ingridient.IngridientItem("arpg:magic_powder", 1, 0, false),
            new Ingridient.IngridientItem("arpg:magic_crystal_dust", 2, 0, false),
            new Ingridient.IngridientItem("gunpowder", 2),
            EM,
            EM,
            EM
         )
         .setFluidCost("hydrothermal_solution", 250);
      addRecipe(
            10.0F,
            new Ingridient.IngridientItem("arpg:chemical_rocket", 15, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            40,
            new Ingridient.IngridientItem("arpg:common_rocket", 15, 0, false),
            new Ingridient.IngridientItem("arpg:vile_substance", 1, 0, false),
            new Ingridient.IngridientItem("arpg:toxinium_nugget", 1, 0, false),
            new Ingridient.IngridientItem("arpg:toxiberry_juice_drip", 1, 0, false),
            EM,
            EM
         )
         .setFluidCost("toxin", 500);
      addRecipe(
            10.0F,
            new Ingridient.IngridientItem("arpg:frostfire_rocket", 15, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            40,
            new Ingridient.IngridientItem("arpg:common_rocket", 15, 0, false),
            new Ingridient.IngridientItem("arpg:conifer_rosin", 3, 0, false),
            EM,
            EM,
            EM,
            EM
         )
         .setFluidCost("cryon", 500);
      addRecipe(
            35.0F,
            new Ingridient.IngridientItem("arpg:magic_powder", 5, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            50,
            new Ingridient.IngridientItem("arpg:magic_crystal_dust", 3, 0, false),
            new Ingridient.IngridientItem("arpg:glowing_crystal_dust", 1, 0, false),
            new Ingridient.IngridientItem("dustRedstone", 1),
            EM,
            EM,
            EM
         )
         .setFluidCost("experience", 2000);
      addRecipe(
            35.0F,
            new Ingridient.IngridientItem("arpg:magic_powder", 5, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            50,
            new Ingridient.IngridientItem("arpg:magic_crystal_dust", 3, 0, false),
            new Ingridient.IngridientItem("arpg:glowing_crystal_dust", 1, 0, false),
            new Ingridient.IngridientItem("dyeBlue", 1),
            EM,
            EM,
            EM
         )
         .setFluidCost("experience", 1300);
      addRecipe(
            35.0F,
            new Ingridient.IngridientItem("arpg:magic_powder", 5, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            40,
            new Ingridient.IngridientItem("arpg:magic_crystal_dust", 3, 0, false),
            new Ingridient.IngridientItem("arpg:glowing_crystal_dust", 1, 0, false),
            new Ingridient.IngridientItem("dustLapis", 1),
            EM,
            EM,
            EM
         )
         .setFluidCost("experience", 800);
      addRecipe(
            4.0F,
            new Ingridient.IngridientItem("arpg:slime_plastic", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            40,
            new Ingridient.IngridientItem("arpg:white_slimeball", 2, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM
         )
         .setFluidCost("slime", 100);
      addRecipe(
            6.0F,
            new Ingridient.IngridientItem("arpg:slime_plastic", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            80,
            new Ingridient.IngridientItem("arpg:white_slimeball", 2, 0, false),
            new Ingridient.IngridientItem("slimeball", 1),
            EM,
            EM,
            EM,
            EM
         )
         .setFluidCost("tile.water", 250);
      addRecipe(
         5.0F,
         new Ingridient.IngridientItem("arpg:fiery_oil", 2, 0, false),
         EM,
         EM,
         EM,
         EM,
         EM,
         90,
         new Ingridient.IngridientItem("arpg:fiery_bean", 1, 0, false),
         EM,
         EM,
         EM,
         EM,
         EM
      );
      addRecipe(20.0F, EM, EM, EM, EM, EM, EM, 200, new Ingridient.IngridientItem("slimeball", 1), EM, EM, EM, EM, EM).setFluidOutput("slime", 100);
      addRecipe(
            14.0F,
            EM,
            EM,
            EM,
            EM,
            EM,
            EM,
            110,
            new Ingridient.IngridientItem("arpg:mana_berry", 4, 0, false),
            new Ingridient.IngridientItem("arpg:health_berry", 1, 0, false),
            EM,
            EM,
            EM,
            EM
         )
         .setFluidOutput("mana_oil", 500);
      addRecipe(
            10.0F,
            EM,
            EM,
            EM,
            EM,
            EM,
            EM,
            100,
            new Ingridient.IngridientItem("arpg:mana_berry", 4, 0, false),
            new Ingridient.IngridientItem("arpg:magic_crystal_dust", 1, 0, false),
            EM,
            EM,
            EM,
            EM
         )
         .setFluidOutput("mana_oil", 500);
      addRecipe(
            10.0F,
            EM,
            EM,
            EM,
            EM,
            EM,
            EM,
            100,
            new Ingridient.IngridientItem("arpg:mana_berry", 4, 0, false),
            new Ingridient.IngridientItem("arpg:magic_powder", 1, 0, false),
            EM,
            EM,
            EM,
            EM
         )
         .setFluidOutput("mana_oil", 500);
      addRecipe(
            10.0F,
            EM,
            EM,
            EM,
            EM,
            EM,
            EM,
            90,
            new Ingridient.IngridientItem("arpg:mana_berry", 4, 0, false),
            new Ingridient.IngridientItem("arpg:quantum_slimeball", 1, 0, false),
            EM,
            EM,
            EM,
            EM
         )
         .setFluidOutput("mana_oil", 500);
      addRecipe(
            12.0F,
            EM,
            EM,
            EM,
            EM,
            EM,
            EM,
            120,
            new Ingridient.IngridientItem("arpg:mana_berry", 4, 0, false),
            new Ingridient.IngridientItem("arpg:ice_dust", 1, 0, false),
            EM,
            EM,
            EM,
            EM
         )
         .setFluidOutput("mana_oil", 500);
      addRecipe(
            8.0F,
            EM,
            EM,
            EM,
            EM,
            EM,
            EM,
            130,
            new Ingridient.IngridientItem("arpg:mana_berry", 4, 0, false),
            new Ingridient.IngridientItem("arpg:ice_flower", 1, 0, false),
            EM,
            EM,
            EM,
            EM
         )
         .setFluidOutput("mana_oil", 500);
      addRecipe(
            4.0F,
            new Ingridient.IngridientItem("arpg:magic_wood", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            40,
            new Ingridient.IngridientItem("logWood", 1),
            EM,
            EM,
            EM,
            EM,
            EM
         )
         .setFluidCost("mana_oil", 50);
      addRecipe(
         20.0F,
         new Ingridient.IngridientItem("arpg:bullet_erratic", 64, 0, false),
         new Ingridient.IngridientItem("arpg:bullet_erratic", 32, 0, false),
         EM,
         EM,
         EM,
         EM,
         70,
         new Ingridient.IngridientItem("endstone", 1),
         new Ingridient.IngridientItem("arpg:quantum_slimeball", 2, 0, false),
         new Ingridient.IngridientItem("gunpowder", 2),
         EM,
         EM,
         EM
      );
      addRecipe(
            10.0F,
            new Ingridient.IngridientItem("arpg:waterblast_rocket", 15, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            90,
            new Ingridient.IngridientItem("arpg:aquatic_ingot", 1, 0, false),
            new Ingridient.IngridientItem("arpg:coral", 1, 0, false),
            new Ingridient.IngridientItem("arpg:seagrass", 1, 0, false),
            new Ingridient.IngridientItem("gunpowder", 5),
            new Ingridient.IngridientItem("blockGlassColorless", 1),
            EM
         )
         .setFluidCost("tile.water", 5000);
      addRecipe(
            64.0F,
            new Ingridient.IngridientItem("arpg:blowhole_pellets", 8, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            60,
            new Ingridient.IngridientItem("arpg:aquatic_nugget", 1, 0, false),
            new Ingridient.IngridientItem("arpg:coral", 1, 0, false),
            EM,
            new Ingridient.IngridientItem("arpg:mesoglea", 1, 0, false),
            new Ingridient.IngridientItem("arpg:pearl", 1, 0, false),
            EM
         )
         .setFluidCost("tile.water", 500);
      addRecipe(
         25.0F,
         new Ingridient.IngridientItem("arpg:mana_expansion_potion", 1, 0, false),
         EM,
         EM,
         EM,
         EM,
         EM,
         80,
         new Ingridient.IngridientItem("minecraft:glass_bottle", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mana_berry", 10, 0, false),
         new Ingridient.IngridientItem("arpg:magic_powder", 4, 0, false),
         new Ingridient.IngridientItem("ingotSilver", 1),
         EM,
         EM
      );
      addRecipe(
            5.0F,
            new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 1, true),
            EM,
            EM,
            EM,
            EM,
            EM,
            60,
            new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 0, true),
            new Ingridient.IngridientItem("arpg:ice_dust", 1, 0, false),
            EM,
            EM,
            EM,
            EM
         )
         .setFluidCost("tile.water", 1000);
      addRecipe(
            5.0F,
            new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 1, true),
            EM,
            EM,
            EM,
            EM,
            EM,
            60,
            new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 0, true),
            new Ingridient.IngridientItem("dustCryotheum", 1),
            EM,
            EM,
            EM,
            EM
         )
         .setFluidCost("tile.water", 1000);
      addRecipe(
            5.0F,
            new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 1, true),
            new Ingridient.IngridientItem("arpg:empty_cell", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            60,
            new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 0, true),
            new Ingridient.IngridientItem("arpg:cryogen_cell", 1, 0, false),
            EM,
            EM,
            EM,
            EM
         )
         .setFluidCost("tile.water", 1000);
      addRecipe(
         2.0F,
         new Ingridient.IngridientItem("arpg:frozen_stone", 1, 0, false),
         EM,
         EM,
         EM,
         EM,
         EM,
         60,
         new Ingridient.IngridientItem("arpg:frozen_cobblestone", 1, 0, false),
         EM,
         EM,
         EM,
         EM,
         EM
      );
      addRecipe(
         2.0F,
         new Ingridient.IngridientItem("arpg:fish_feed", 5, 0, false),
         EM,
         EM,
         EM,
         EM,
         EM,
         50,
         new Ingridient.IngridientItem("arpg:pale_meat_raw", 1, 0, false),
         new Ingridient.IngridientItem("arpg:dough", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false),
         EM,
         EM,
         EM
      );
      addRecipe(
         2.0F,
         new Ingridient.IngridientItem("arpg:fish_feed", 5, 0, false),
         EM,
         EM,
         EM,
         EM,
         EM,
         50,
         new Ingridient.IngridientItem("minecraft:rotten_flesh", 2, 0, false),
         new Ingridient.IngridientItem("arpg:dough", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false),
         EM,
         EM,
         EM
      );
      addRecipe(
         2.0F,
         new Ingridient.IngridientItem("arpg:fish_feed", 5, 0, false),
         new Ingridient.IngridientItem("minecraft:bowl", 2, 0, false),
         EM,
         EM,
         EM,
         EM,
         50,
         new Ingridient.IngridientItem("arpg:meat_broth", 2, 0, false),
         new Ingridient.IngridientItem("arpg:dough", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false),
         EM,
         EM,
         EM
      );
      addRecipe(
            15.0F,
            new Ingridient.IngridientItem("arpg:snowflake_shuriken", 32, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            90,
            new Ingridient.IngridientItem("arpg:clean_ice", 1, 0, false),
            new Ingridient.IngridientItem("arpg:ice_dust", 1, 0, false),
            EM,
            EM,
            EM,
            EM
         )
         .setFluidCost("tile.water", 1000);
      addRecipe(
            50.0F,
            new Ingridient.IngridientItem("arpg:acid_bomb", 1, 0, false),
            EM,
            EM,
            EM,
            EM,
            EM,
            300,
            new Ingridient.IngridientItem("arpg:rust_armature", 1, 0, false),
            new Ingridient.IngridientItem("blockGlassColorless", 1),
            new Ingridient.IngridientItem("arpg:vile_substance", 5, 0, false),
            new Ingridient.IngridientItem("arpg:toxiberry_juice_drip", 2, 0, false),
            new Ingridient.IngridientItem("gunpowder", 1),
            EM
         )
         .setFluidCost("biogenic_acid", 3000);
   }

   public static void setregister(AlchemicLabRecipe rec) {
      allRecipes.add(rec);
   }
}
