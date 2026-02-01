package com.vivern.arpg.main;

import com.vivern.arpg.recipes.Ingridient;
import com.vivern.arpg.recipes.SieveRecipe;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

public class SieveRecipesRegister {
   public static List<SieveRecipe> allRecipes = new ArrayList<>();

   public static void register() {
      addRecipe(
         new Ingridient.IngridientItem("arpg:bones_block", 1, 0, false),
         100,
         Sounds.st_crunchy_step,
         new float[]{1.0F, 0.8F, 0.6F, 0.5F, 0.1F, 0.015F, 0.65F, 0.36F},
         new Ingridient.IngridientItem("minecraft:bone", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:bone", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:bone", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:bone", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:skull", 1, 0, true),
         new Ingridient.IngridientItem("minecraft:skull", 1, 1, true),
         new Ingridient.IngridientItem("minecraft:dye", 1, 15, true),
         new Ingridient.IngridientItem("arpg:ash", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:scrap", 1, 0, false),
         80,
         Sounds.st_crunchy_step,
         new float[]{0.18F, 0.05F, 0.2F, 0.2F, 0.25F, 0.18F, 0.1F, 0.2F, 0.07F, 0.3F, 0.12F, 0.11F, 0.1F, 0.1F, 0.12F, 0.12F, 0.07F, 0.3F, 0.1F, 0.06F, 0.01F},
         new Ingridient.IngridientItem("minecraft:glass_bottle", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxicola", 1, 0, false),
         new Ingridient.IngridientItem("arpg:nail", 1, 0, false),
         new Ingridient.IngridientItem("materialRubber", 1),
         new Ingridient.IngridientItem("arpg:dried_plant_fiber", 1, 0, false),
         new Ingridient.IngridientItem("arpg:junkweed_seed", 1, 0, false),
         new Ingridient.IngridientItem("string", 1),
         new Ingridient.IngridientItem("stickWood", 1),
         new Ingridient.IngridientItem("minecraft:book", 1, 0, false),
         new Ingridient.IngridientItem("paper", 1),
         new Ingridient.IngridientItem("arpg:empty_syringe", 1, 0, false),
         new Ingridient.IngridientItem("arpg:empty_cell", 1, 0, false),
         new Ingridient.IngridientItem("arpg:common_rocket", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mining_rocket", 1, 0, false),
         new Ingridient.IngridientItem("arpg:bullet_copper", 6, 0, false),
         new Ingridient.IngridientItem("arpg:bullet_lead", 6, 0, false),
         new Ingridient.IngridientItem("minecraft:painting", 1, 0, false),
         new Ingridient.IngridientItem("ingotBrick", 1),
         new Ingridient.IngridientItem("arpg:frag_grenade", 1, 0, false),
         new Ingridient.IngridientItem("arpg:gas_grenade", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxic_nuclear_warhead", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:scrap_metal", 1, 0, false),
         80,
         Sounds.st_crunchy_step,
         new float[]{0.6F, 0.5F, 0.5F, 0.6F, 0.4F, 0.6F, 0.4F, 0.5F, 0.16F, 0.16F, 0.35F, 0.3F, 0.23F, 0.23F, 0.18F},
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("nuggetBrass", 1),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("nuggetCopper", 1),
         new Ingridient.IngridientItem("nuggetTin", 1),
         new Ingridient.IngridientItem("nuggetLead", 1),
         new Ingridient.IngridientItem("nuggetSteel", 1),
         new Ingridient.IngridientItem("nuggetZinc", 1),
         new Ingridient.IngridientItem("gearCopper", 1),
         new Ingridient.IngridientItem("gearIron", 1),
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:nail", 3, 0, false),
         new Ingridient.IngridientItem("plateIron", 1),
         new Ingridient.IngridientItem("plateSteel", 1),
         new Ingridient.IngridientItem("arpg:anti_rad_plating", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:scrap_electronics", 1, 0, false),
         120,
         Sounds.st_crunchy_step,
         new float[]{0.15F, 0.35F, 0.7F, 0.6F, 0.1F, 0.1F, 0.06F, 0.22F, 0.05F, 0.08F, 0.34F, 0.1F, 0.07F, 0.8F},
         new Ingridient.IngridientItem("circuitBasic", 1),
         new Ingridient.IngridientItem("materialRubber", 1),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:lead_acid_battery", 1, 0, false),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("arpg:copper_transformer", 1, 0, false),
         new Ingridient.IngridientItem("arpg:processor", 1, 0, false),
         new Ingridient.IngridientItem("paneGlassColorless", 1),
         new Ingridient.IngridientItem("enderio:item_basic_capacitor", 1, 0, true),
         new Ingridient.IngridientItem("enderio:item_basic_capacitor", 1, 1, true),
         new Ingridient.IngridientItem("ic2:single_use_battery", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("minecraft:sand", 1, 0, false),
         70,
         SoundEvents.BLOCK_SAND_PLACE,
         new float[]{1.0F, 1.0F},
         new Ingridient.IngridientItem("arpg:quartz_dust", 3, 0, false),
         new Ingridient.IngridientItem("arpg:basalt_dust", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:scrap_bomb", 1, 0, false),
         90,
         Sounds.st_crunchy_step,
         new float[]{0.85F, 0.2F, 0.85F, 0.45F, 0.35F, 0.2F, 0.6F, 0.15F},
         new Ingridient.IngridientItem("nuggetLead", 1),
         new Ingridient.IngridientItem("nuggetTitanium", 1),
         new Ingridient.IngridientItem("arpg:uranium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("dustLead", 1),
         new Ingridient.IngridientItem("arpg:uranium_dust", 1, 0, false),
         new Ingridient.IngridientItem("arpg:radioactive_dust", 1, 0, false),
         new Ingridient.IngridientItem("dustRedstone", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("minecraft:mycelium", 1, 0, false),
         70,
         SoundEvents.BLOCK_GRAVEL_PLACE,
         new float[]{0.95F, 0.2F, 0.2F, 0.1F, 0.1F},
         new Ingridient.IngridientItem("dirt", 1),
         new Ingridient.IngridientItem("minecraft:red_mushroom", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:brown_mushroom", 1, 0, false),
         new Ingridient.IngridientItem("yeast", 1),
         new Ingridient.IngridientItem("arpg:whey_starter", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("minecraft:wheat", 1, 0, false),
         60,
         SoundEvents.BLOCK_GRASS_STEP,
         new float[]{0.6F, 0.8F, 0.5F, 0.2F},
         new Ingridient.IngridientItem("arpg:flour", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:wheat_seeds", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:wheat_seeds", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:wheat_seeds", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:rich_scrap", 1, 0, false),
         80,
         Sounds.st_crunchy_step,
         new float[]{0.8F, 0.6F, 0.2F, 0.4F, 0.1F, 0.05F, 0.9F, 0.2F},
         new Ingridient.IngridientItem("arpg:uranium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:uranium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:vile_substance", 1, 0, false),
         new Ingridient.IngridientItem("dustSulfur", 1),
         new Ingridient.IngridientItem("dustBrass", 1),
         new Ingridient.IngridientItem("arpg:toxinium_ore_dust", 1, 0, false),
         new Ingridient.IngridientItem("nuggetZinc", 1),
         new Ingridient.IngridientItem("nuggetPlatinum", 1)
      );
   }

   public static SieveRecipe addRecipe(Ingridient input, int timeToCraft, SoundEvent sound, float[] chances, Ingridient... output) {
      SieveRecipe rec = new SieveRecipe(input, timeToCraft, sound, chances, output);
      setregister(rec);
      return rec;
   }

   @Nullable
   public static SieveRecipe getRecipe(ItemStack input) {
      for (SieveRecipe sieveRecipe : allRecipes) {
         if (sieveRecipe.input.isStackAllowed(input)) {
            return sieveRecipe;
         }
      }

      return null;
   }

   public static void setregister(SieveRecipe rec) {
      allRecipes.add(rec);
   }
}
