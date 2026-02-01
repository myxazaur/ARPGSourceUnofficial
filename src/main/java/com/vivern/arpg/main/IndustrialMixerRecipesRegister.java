package com.vivern.arpg.main;

import com.vivern.arpg.recipes.IndustrialMixerRecipe;
import com.vivern.arpg.recipes.Ingridient;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class IndustrialMixerRecipesRegister {
   public static final Ingridient EM = Ingridient.EMPTY;
   public static List<IndustrialMixerRecipe> allRecipes = new ArrayList<>();

   public static void register() {
      addRecipe(
         ItemsRegister.MODULEPOLYMERIZATION,
         ItemsRegister.MODULEBIOFILTERING,
         2000,
         new Ingridient.IngridientItem("arpg:slime_plastic", 2, 0, false),
         EM,
         EM,
         60,
         new Ingridient.IngridientItem("arpg:white_slimeball", 1, 0, false),
         new Ingridient.IngridientItem("slimeball", 1),
         EM
      );
      addRecipe(
            ItemsRegister.MODULEPOLYMERIZATION,
            ItemsRegister.MODULEBIOFILTERING,
            8000,
            new Ingridient.IngridientItem("materialAdvancedPolymer", 2),
            EM,
            EM,
            90,
            new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
            new Ingridient.IngridientItem("arpg:paraffin", 1, 0, false),
            new Ingridient.IngridientItem("arpg:nylon", 1, 0, false)
         )
         .setFluidCost1("natural_gas", 500)
         .setFluidCost2("toxin", 250);
      addRecipe(ItemsRegister.MODULEBIOFILTERING, null, 4000, EM, EM, EM, 100, new Ingridient.IngridientItem("arpg:vile_substance", 2, 0, false), EM, EM)
         .setFluidOutput1("biogenic_acid", 250);
      addRecipe(
         ItemsRegister.MODULEBIOFILTERING,
         null,
         4000,
         new Ingridient.IngridientItem("arpg:sludge", 2, 0, false),
         new Ingridient.IngridientItem("arpg:rich_scrap", 1, 0, false),
         EM,
         50,
         new Ingridient.IngridientItem("arpg:nuclear_waste", 2, 0, false),
         EM,
         EM
      );
      addRecipe(
         ItemsRegister.MODULEELECTROLYZER,
         null,
         20000,
         new Ingridient.IngridientItem("dustAluminum", 1),
         EM,
         EM,
         60,
         new Ingridient.IngridientItem("arpg:bauxite", 3, 0, false),
         EM,
         EM
      );
      addRecipe(
         ItemsRegister.MODULEELECTROLYZER,
         ItemsRegister.MODULEPYROLYSIS,
         10000,
         new Ingridient.IngridientItem("dustAluminum", 1),
         EM,
         EM,
         70,
         new Ingridient.IngridientItem("arpg:bauxite", 2, 0, false),
         EM,
         EM
      );
      addRecipe(
            ItemsRegister.MODULEELECTROLYZER,
            ItemsRegister.MODULEPYROLYSIS,
            50000,
            new Ingridient.IngridientItem("dustAluminum", 5),
            new Ingridient.IngridientItem("nuggetAluminum", 6),
            new Ingridient.IngridientItem("arpg:titanium_slag", 1, 0, false),
            250,
            new Ingridient.IngridientItem("arpg:bauxite", 10, 0, false),
            EM,
            EM
         )
         .setFluidCost1("pyrotheum", 500);
      addRecipe(
            ItemsRegister.MODULECENTRIFUGE,
            null,
            1000,
            new Ingridient.IngridientItem("arpg:limestone_dust", 1, 0, false),
            new Ingridient.IngridientItem("arpg:basalt_dust", 1, 0, false),
            EM,
            60,
            new Ingridient.IngridientItem("arpg:stone_dust", 2, 0, false),
            EM,
            EM
         )
         .setFluidCost1("tile.water", 200);
      addRecipe(
            ItemsRegister.MODULECENTRIFUGE,
            null,
            1000,
            new Ingridient.IngridientItem("arpg:limestone_dust", 2, 0, false),
            new Ingridient.IngridientItem("arpg:radioactive_dust", 2, 0, false),
            EM,
            60,
            new Ingridient.IngridientItem("arpg:sludge", 1, 0, false),
            EM,
            EM
         )
         .setFluidOutput1("poison", 250);
      addRecipe(
            null,
            null,
            1000,
            new Ingridient.IngridientItem("arpg:sludge", 1, 0, false),
            EM,
            EM,
            60,
            new Ingridient.IngridientItem("arpg:limestone_dust", 2, 0, false),
            new Ingridient.IngridientItem("arpg:radioactive_dust", 2, 0, false),
            EM
         )
         .setFluidCost1("poison", 250);
      addRecipe(
            ItemsRegister.MODULECENTRIFUGE,
            null,
            4000,
            new Ingridient.IngridientItem("dustQuartz", 3),
            new Ingridient.IngridientItem("arpg:basalt_dust", 1, 0, false),
            EM,
            80,
            new Ingridient.IngridientItem("sand", 1),
            EM,
            EM
         )
         .setFluidCost1("tile.water", 200);
      addRecipe(ItemsRegister.MODULEPYROLYSIS, null, 2500, EM, EM, EM, 40, new Ingridient.IngridientItem("dustSulfur", 1), EM, EM)
         .setFluidOutput1("sulfuric_gas", 500);
      addRecipe(null, null, 500, EM, EM, EM, 70, EM, EM, EM)
         .setFluidCost1("sulfuric_gas", 2000)
         .setFluidCost2("tile.water", 1000)
         .setFluidOutput1("sulfuric_acid", 1000);
      addRecipe(ItemsRegister.MODULEDISTILLATION, null, 6000, new Ingridient.IngridientItem("arpg:stone_dust", 1, 0, false), EM, EM, 180, EM, EM, EM)
         .setFluidCost1("petroleum", 1000)
         .setFluidOutput1("gasoline", 500)
         .setFluidOutput2("fuel_oil", 500);
      addRecipe(
            ItemsRegister.MODULEPOLYMERIZATION,
            ItemsRegister.MODULEPYROLYSIS,
            5000,
            new Ingridient.IngridientItem("materialAdvancedPlastic", 2),
            new Ingridient.IngridientItem("arpg:paraffin", 1, 0, false),
            EM,
            100,
            EM,
            EM,
            EM
         )
         .setFluidCost1("gasoline", 450)
         .setFluidCost2("tile.water", 250)
         .setFluidOutput1("natural_gas", 600);
      addRecipe(
            ItemsRegister.MODULEDISTILLATION,
            null,
            6000,
            new Ingridient.IngridientItem("arpg:nylon", 1, 0, false),
            new Ingridient.IngridientItem("arpg:tar", 2, 0, false),
            new Ingridient.IngridientItem("dustSulfur", 1),
            220,
            EM,
            EM,
            EM
         )
         .setFluidCost1("fuel_oil", 1000)
         .setFluidCost2("sulfuric_acid", 500)
         .setFluidOutput1("sulfuric_gas", 1000);
      addRecipe(
            ItemsRegister.MODULEPOLYMERIZATION,
            null,
            8000,
            new Ingridient.IngridientItem("materialAdvancedPolymer", 1),
            EM,
            EM,
            90,
            new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
            new Ingridient.IngridientItem("arpg:paraffin", 1, 0, false),
            new Ingridient.IngridientItem("arpg:nylon", 1, 0, false)
         )
         .setFluidCost1("natural_gas", 500)
         .setFluidCost2("toxin", 250);
      addRecipe(
         ItemsRegister.MODULEDISTILLATION,
         null,
         2000,
         new Ingridient.IngridientItem("arpg:paraffin", 2, 0, false),
         new Ingridient.IngridientItem("dustSulfur", 1),
         EM,
         60,
         new Ingridient.IngridientItem("arpg:alchemical_wax", 3, 0, false),
         EM,
         EM
      );
      addRecipe(
            ItemsRegister.MODULEPYROLYSIS,
            null,
            3400,
            new Ingridient.IngridientItem("arpg:toxinium_nugget", 1, 0, false),
            new Ingridient.IngridientItem("arpg:radioactive_dust", 2, 0, false),
            new Ingridient.IngridientItem("arpg:arsenic_nugget", 1, 0, false),
            80,
            new Ingridient.IngridientItem("arpg:toxinium_ore", 1, 0, false),
            EM,
            EM
         )
         .setFluidCost1("biogenic_acid", 600)
         .setFluidOutput1("dissolved_toxinium", 800);
      addRecipe(
            ItemsRegister.MODULEDISTILLATION,
            null,
            6400,
            new Ingridient.IngridientItem("arpg:toxinium_dust", 1, 0, false),
            new Ingridient.IngridientItem("arpg:uranium_nugget", 1, 0, false),
            EM,
            70,
            EM,
            EM,
            EM
         )
         .setFluidCost1("dissolved_toxinium", 800)
         .setFluidOutput1("biogenic_acid", 300)
         .setFluidOutput2("toxin", 500);
      addRecipe(
            ItemsRegister.MODULEPYROLYSIS,
            null,
            1400,
            new Ingridient.IngridientItem("arpg:radioactive_dust", 1, 0, false),
            new Ingridient.IngridientItem("arpg:arsenic_nugget", 1, 0, false),
            EM,
            40,
            new Ingridient.IngridientItem("arpg:toxinium_ore_dust", 1, 0, false),
            EM,
            EM
         )
         .setFluidCost1("biogenic_acid", 500)
         .setFluidOutput1("dissolved_toxinium", 700);
      addRecipe(
            null,
            null,
            2000,
            new Ingridient.IngridientItem("arpg:adamantium_dust", 1, 0, false),
            new Ingridient.IngridientItem("arpg:stone_dust", 1, 0, false),
            new Ingridient.IngridientItem("arpg:copper_sulfate", 1, 0, false),
            60,
            new Ingridient.IngridientItem("arpg:adamantium_ore_dust", 2, 0, false),
            EM,
            EM
         )
         .setFluidCost1("sulfuric_acid", 250);
      addRecipe(
            null,
            null,
            2000,
            new Ingridient.IngridientItem("arpg:mithril_dust", 1, 0, false),
            new Ingridient.IngridientItem("arpg:stone_dust", 1, 0, false),
            new Ingridient.IngridientItem("arpg:copper_sulfate", 1, 0, false),
            60,
            new Ingridient.IngridientItem("arpg:mithril_ore_dust", 2, 0, false),
            EM,
            EM
         )
         .setFluidCost1("sulfuric_acid", 250);
      addRecipe(
            ItemsRegister.MODULEPYROLYSIS,
            null,
            10000,
            new Ingridient.IngridientItem("nuggetCopper", 1),
            EM,
            EM,
            50,
            new Ingridient.IngridientItem("arpg:copper_sulfate", 1, 0, false),
            EM,
            EM
         )
         .setFluidOutput1("sulfuric_gas", 500);
      addRecipe(
            null,
            null,
            2000,
            new Ingridient.IngridientItem("dustTitanium", 1),
            new Ingridient.IngridientItem("arpg:stone_dust", 1, 0, false),
            EM,
            60,
            new Ingridient.IngridientItem("arpg:titanium_slag", 2, 0, false),
            EM,
            EM
         )
         .setFluidCost1("sulfuric_acid", 250)
         .setFluidOutput1("sulfuric_gas", 500);
      addRecipe(
         ItemsRegister.MODULEELECTROLYZER,
         ItemsRegister.MODULEPYROLYSIS,
         12000,
         new Ingridient.IngridientItem("dustAluminum", 1),
         new Ingridient.IngridientItem("dustBeryllium", 1),
         new Ingridient.IngridientItem("dustQuartz", 1),
         80,
         new Ingridient.IngridientItem("gemEmerald", 3),
         EM,
         EM
      );
      addRecipe(
         ItemsRegister.MODULEELECTROLYZER,
         ItemsRegister.MODULEPYROLYSIS,
         11000,
         new Ingridient.IngridientItem("arpg:beryllium_grains", 2, 0, false),
         EM,
         EM,
         90,
         new Ingridient.IngridientItem("enderpearl", 1),
         EM,
         EM
      );
      addRecipe(ItemsRegister.MODULEELECTROLYZER, null, 3400, new Ingridient.IngridientItem("arpg:beryllium_grains", 3, 0, false), EM, EM, 70, EM, EM, EM)
         .setFluidCost1("ender", 250);
      addRecipe(
            null,
            null,
            1500,
            new Ingridient.IngridientItem("arpg:antidote", 1, 0, false),
            EM,
            EM,
            80,
            new Ingridient.IngridientItem("arpg:empty_syringe", 1, 0, false),
            new Ingridient.IngridientItem("arpg:toxiberry_vibrant_seed", 2, 0, false),
            new Ingridient.IngridientItem("arpg:whey_starter", 1, 0, false)
         )
         .setFluidCost1("luminescent_liquid", 250);
      addRecipe(
         null,
         null,
         16000,
         new Ingridient.IngridientItem("arpg:antipotion", 4, 0, false),
         EM,
         EM,
         160,
         new Ingridient.IngridientItem("arpg:antidote", 4, 0, false),
         new Ingridient.IngridientItem("itemGhastTear", 1),
         new Ingridient.IngridientItem("arpg:health_berry", 4, 0, false)
      );
      addRecipe(
            null,
            null,
            3600,
            new Ingridient.IngridientItem("arpg:anti_rad_injector", 1, 0, false),
            EM,
            EM,
            60,
            new Ingridient.IngridientItem("arpg:empty_syringe", 1, 0, false),
            new Ingridient.IngridientItem("arpg:toxiberry_vibrant_seed", 1, 0, false),
            new Ingridient.IngridientItem("minecraft:double_plant", 1, 0, true)
         )
         .setFluidCost1("luminescent_liquid", 100);
      addRecipe(
            ItemsRegister.MODULEDISTILLATION,
            null,
            15000,
            new Ingridient.IngridientItem("arpg:white_slimeball", 1, 0, false),
            new Ingridient.IngridientItem("itemBiomass", 1),
            EM,
            200,
            new Ingridient.IngridientItem("slimeball", 6),
            EM,
            EM
         )
         .setFluidOutput1("slime", 200);
      addRecipe(
         ItemsRegister.MODULEFERMENTER,
         ItemsRegister.MODULECENTRIFUGE,
         1500,
         new Ingridient.IngridientItem("arpg:cocoa_butter", 1, 0, false),
         new Ingridient.IngridientItem("arpg:cocoa_powder", 2, 0, false),
         EM,
         50,
         new Ingridient.IngridientItem("minecraft:dye", 3, 3, true),
         EM,
         EM
      );
      addRecipe(
            ItemsRegister.MODULECENTRIFUGE,
            null,
            1500,
            new Ingridient.IngridientItem("arpg:butter", 1, 0, false),
            EM,
            EM,
            50,
            new Ingridient.IngridientItem("minecraft:milk_bucket", 1, 0, false),
            EM,
            EM
         )
         .setFluidOutput1("tile.water", 750);
      addRecipe(
         ItemsRegister.MODULEFERMENTER,
         null,
         2500,
         new Ingridient.IngridientItem("arpg:whey_starter", 8, 0, false),
         EM,
         EM,
         400,
         new Ingridient.IngridientItem("arpg:whey_starter", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:milk_bucket", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false)
      );
      addRecipe(
            ItemsRegister.MODULEFERMENTER,
            null,
            2000,
            new Ingridient.IngridientItem("yeast", 8),
            EM,
            EM,
            440,
            new Ingridient.IngridientItem("yeast", 1),
            new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false),
            EM
         )
         .setFluidCost1("tile.water", 1000);
      addRecipe(
            ItemsRegister.MODULEPOLYMERIZATION,
            null,
            4000,
            new Ingridient.IngridientItem("arpg:snapball_ammo", 16, 0, false),
            EM,
            EM,
            40,
            new Ingridient.IngridientItem("arpg:quantum_slimeball", 1, 0, false),
            new Ingridient.IngridientItem("arpg:tar", 1, 0, false),
            new Ingridient.IngridientItem("dyeBlue", 1)
         )
         .setFluidCost1("gasoline", 160);
      addRecipe(
            null,
            null,
            14000,
            new Ingridient.IngridientItem("arpg:corrosive_flask", 1, 0, false),
            EM,
            EM,
            80,
            new Ingridient.IngridientItem("arpg:ammonia_flask", 1, 0, false),
            new Ingridient.IngridientItem("arpg:gaseous_energy_drink", 1, 0, false),
            EM
         )
         .setFluidCost1("luminescent_liquid", 250);
      addRecipe(
            ItemsRegister.MODULECENTRIFUGE,
            null,
            2000,
            new Ingridient.IngridientItem("arpg:copper_sulfate", 1, 0, false),
            new Ingridient.IngridientItem("dustSalt", 1),
            new Ingridient.IngridientItem("dustSaltpeter", 1),
            60,
            new Ingridient.IngridientItem("arpg:crystallized_poison", 4, 0, false),
            EM,
            EM
         )
         .setFluidCost1("tile.water", 250)
         .setFluidOutput1("toxin", 500);
      addRecipe(
            ItemsRegister.MODULECENTRIFUGE,
            null,
            4000,
            new Ingridient.IngridientItem("arpg:crystallized_poison", 1, 0, false),
            new Ingridient.IngridientItem("slimeball", 1),
            EM,
            80,
            EM,
            EM,
            EM
         )
         .setFluidCost1("poison", 2000)
         .setFluidOutput1("tile.water", 1500);
      addRecipe(
            null,
            null,
            200,
            new Ingridient.IngridientItem("arpg:molotov_cocktail", 1, 0, false),
            EM,
            EM,
            10,
            new Ingridient.IngridientItem("minecraft:glass_bottle", 1, 0, false),
            EM,
            EM
         )
         .setFluidCost1("pyrotheum", 50);
      addRecipe(
            ItemsRegister.MODULEPOLYMERIZATION,
            null,
            1000,
            new Ingridient.IngridientItem("arpg:slime_plastic", 1, 0, false),
            EM,
            EM,
            60,
            new Ingridient.IngridientItem("arpg:white_slimeball", 2, 0, false),
            EM,
            EM
         )
         .setFluidCost1("slime", 100);
      addRecipe(
            ItemsRegister.MODULEPOLYMERIZATION,
            null,
            2000,
            new Ingridient.IngridientItem("arpg:slime_plastic", 1, 0, false),
            EM,
            EM,
            80,
            new Ingridient.IngridientItem("arpg:white_slimeball", 2, 0, false),
            new Ingridient.IngridientItem("slimeball", 1),
            EM
         )
         .setFluidCost1("tile.water", 250);
      addRecipe(
            null,
            null,
            1000,
            new Ingridient.IngridientItem("dustSaltpeter", 3),
            EM,
            EM,
            60,
            new Ingridient.IngridientItem("arpg:limestone_dust", 1, 0, false),
            new Ingridient.IngridientItem("dustAsh", 1),
            EM
         )
         .setFluidCost1("nitric_acid", 250)
         .setFluidCost2("tile.water", 250);
      addRecipe(
            ItemsRegister.MODULEFERMENTER,
            null,
            2000,
            EM,
            EM,
            EM,
            200,
            new Ingridient.IngridientItem("ic2:crafting", 1, 21, true),
            new Ingridient.IngridientItem("cropWheat", 1),
            EM
         )
         .setFluidCost1("tile.water", 500)
         .setFluidOutput1("nitric_acid", 500);
      addRecipe(
            ItemsRegister.MODULEFERMENTER,
            null,
            2000,
            EM,
            EM,
            EM,
            200,
            new Ingridient.IngridientItem("itemBiomass", 1),
            new Ingridient.IngridientItem("cropWheat", 1),
            EM
         )
         .setFluidCost1("tile.water", 500)
         .setFluidOutput1("nitric_acid", 500);
      addRecipe(
            ItemsRegister.MODULEFERMENTER,
            null,
            2000,
            EM,
            EM,
            EM,
            200,
            new Ingridient.IngridientItem("itemBioblend", 1),
            new Ingridient.IngridientItem("cropWheat", 1),
            EM
         )
         .setFluidCost1("tile.water", 500)
         .setFluidOutput1("nitric_acid", 500);
      addRecipe(
            ItemsRegister.MODULEFERMENTER,
            null,
            2000,
            EM,
            EM,
            EM,
            200,
            new Ingridient.IngridientItem("treeLeaves", 8),
            new Ingridient.IngridientItem("cropWheat", 1),
            EM
         )
         .setFluidCost1("tile.water", 500)
         .setFluidOutput1("nitric_acid", 500);
      addRecipe(
            ItemsRegister.MODULEFERMENTER,
            null,
            2000,
            EM,
            EM,
            EM,
            200,
            new Ingridient.IngridientItem("sugarcane", 8),
            new Ingridient.IngridientItem("cropWheat", 1),
            EM
         )
         .setFluidCost1("tile.water", 500)
         .setFluidOutput1("nitric_acid", 500);
      addRecipe(
            ItemsRegister.MODULEFERMENTER,
            null,
            2000,
            EM,
            EM,
            EM,
            200,
            new Ingridient.IngridientItem("seedWheat", 8),
            new Ingridient.IngridientItem("cropWheat", 1),
            EM
         )
         .setFluidCost1("tile.water", 500)
         .setFluidOutput1("nitric_acid", 500);
      addRecipe(
            ItemsRegister.MODULEFERMENTER,
            null,
            2000,
            EM,
            EM,
            EM,
            200,
            new Ingridient.IngridientItem("arpg:plant_fiber", 8, 0, false),
            new Ingridient.IngridientItem("cropWheat", 1),
            EM
         )
         .setFluidCost1("tile.water", 500)
         .setFluidOutput1("nitric_acid", 500);
      addRecipe(
            ItemsRegister.MODULEFERMENTER,
            null,
            2000,
            EM,
            EM,
            EM,
            200,
            new Ingridient.IngridientItem("minecraft:tallgrass", 8, 1, true),
            new Ingridient.IngridientItem("cropWheat", 1),
            EM
         )
         .setFluidCost1("tile.water", 500)
         .setFluidOutput1("nitric_acid", 500);
      addRecipe(
         null,
         null,
         2000,
         new Ingridient.IngridientItem("itemFlint", 1),
         new Ingridient.IngridientItem("arpg:stone_rubble", 3, 0, false),
         EM,
         80,
         new Ingridient.IngridientItem("gravel", 1),
         EM,
         EM
      );
      addRecipe(Items.BOWL, null, 24000, new Ingridient.IngridientItem("arpg:salt_grains", 1, 0, false), EM, EM, 200, EM, EM, EM)
         .setFluidCost1("tile.water", 2000);
      addRecipe(
         ItemsRegister.MODULEFERMENTER,
         null,
         1000,
         new Ingridient.IngridientItem("arpg:mozzarella", 1, 0, false),
         EM,
         EM,
         100,
         new Ingridient.IngridientItem("minecraft:milk_bucket", 1, 0, false),
         new Ingridient.IngridientItem("arpg:salt_grains", 1, 0, false),
         new Ingridient.IngridientItem("arpg:whey_starter", 1, 0, false)
      );
      addRecipe(
         ItemsRegister.MODULEELECTROLYZER,
         ItemsRegister.MODULEPYROLYSIS,
         3500,
         new Ingridient.IngridientItem("nuggetLithium", 1),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("dustQuartz", 1),
         100,
         new Ingridient.IngridientItem("arpg:lepidolite", 1, 0, false),
         EM,
         EM
      );
      addRecipe(
            ItemsRegister.MODULEELECTROLYZER,
            null,
            8000,
            new Ingridient.IngridientItem("arpg:galvanized_plate", 1, 0, false),
            EM,
            EM,
            140,
            new Ingridient.IngridientItem("arpg:litographed_plate", 1, 0, false),
            new Ingridient.IngridientItem("arpg:copper_sulfate", 1, 0, false),
            EM
         )
         .setFluidCost1("sulfuric_acid", 500)
         .setFluidCost2("tile.water", 500);
      addRecipe(
            null,
            null,
            500,
            new Ingridient.IngridientItem("arpg:copper_sulfate", 1, 0, false),
            EM,
            EM,
            40,
            new Ingridient.IngridientItem("nuggetCopper", 1),
            EM,
            EM
         )
         .setFluidCost1("sulfuric_acid", 250)
         .setFluidOutput1("tile.water", 100);
      addRecipe(
            null,
            null,
            4000,
            new Ingridient.IngridientItem("arpg:wolfram_dust", 1, 0, false),
            new Ingridient.IngridientItem("arpg:radioactive_dust", 3, 0, false),
            EM,
            160,
            new Ingridient.IngridientItem("arpg:wolfram_ore", 1, 0, false),
            EM,
            EM
         )
         .setFluidCost1("biogenic_acid", 250);
      addRecipe(
         ItemsRegister.MODULEFERMENTER,
         null,
         2000,
         new Ingridient.IngridientItem("arpg:healthful_capsule", 1, 0, false),
         EM,
         EM,
         400,
         new Ingridient.IngridientItem("arpg:glowing_toxiberry", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mutagen", 2, 0, false),
         new Ingridient.IngridientItem("arpg:mossplant_seed", 10, 0, false)
      );
      addRecipe(
            null,
            null,
            5000,
            new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 1, true),
            EM,
            EM,
            60,
            new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 0, true),
            new Ingridient.IngridientItem("arpg:ice_dust", 1, 0, false),
            EM
         )
         .setFluidCost1("tile.water", 1000);
      addRecipe(
            null,
            null,
            5000,
            new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 1, true),
            EM,
            EM,
            60,
            new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 0, true),
            new Ingridient.IngridientItem("dustCryotheum", 1),
            EM
         )
         .setFluidCost1("tile.water", 1000);
      addRecipe(
            null,
            null,
            5000,
            new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 1, true),
            new Ingridient.IngridientItem("arpg:empty_cell", 1, 0, false),
            EM,
            60,
            new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 0, true),
            new Ingridient.IngridientItem("arpg:cryogen_cell", 1, 0, false),
            EM
         )
         .setFluidCost1("tile.water", 1000);
      addRecipe(
         null,
         null,
         2000,
         new Ingridient.IngridientItem("arpg:fish_feed", 5, 0, false),
         EM,
         EM,
         50,
         new Ingridient.IngridientItem("arpg:pale_meat_raw", 1, 0, false),
         new Ingridient.IngridientItem("arpg:dough", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false)
      );
      addRecipe(
         null,
         null,
         2000,
         new Ingridient.IngridientItem("arpg:fish_feed", 5, 0, false),
         EM,
         EM,
         50,
         new Ingridient.IngridientItem("minecraft:rotten_flesh", 2, 0, false),
         new Ingridient.IngridientItem("arpg:dough", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false)
      );
      addRecipe(
         null,
         null,
         2000,
         new Ingridient.IngridientItem("arpg:fish_feed", 5, 0, false),
         new Ingridient.IngridientItem("minecraft:bowl", 2, 0, false),
         EM,
         50,
         new Ingridient.IngridientItem("arpg:meat_broth", 2, 0, false),
         new Ingridient.IngridientItem("arpg:dough", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false)
      );
   }

   public static IndustrialMixerRecipe addRecipe(
      @Nullable Item catalyst1,
      @Nullable Item catalyst2,
      int rfToAll,
      Ingridient out3,
      Ingridient out4,
      Ingridient out5,
      int ticksToBrew,
      Ingridient slot0,
      Ingridient slot1,
      Ingridient slot2
   ) {
      Ingridient[] arrayIn = null;
      if (slot0 != EM) {
         if (slot1 != EM) {
            if (slot2 != EM) {
               arrayIn = new Ingridient[]{slot0, slot1, slot2};
            } else {
               arrayIn = new Ingridient[]{slot0, slot1};
            }
         } else {
            arrayIn = new Ingridient[]{slot0};
         }
      } else {
         arrayIn = new Ingridient[0];
      }

      Ingridient[] arrayOut = null;
      if (out3 != EM) {
         if (out4 != EM) {
            if (out5 != EM) {
               arrayOut = new Ingridient[]{out3, out4, out5};
            } else {
               arrayOut = new Ingridient[]{out3, out4};
            }
         } else {
            arrayOut = new Ingridient[]{out3};
         }
      } else {
         arrayOut = new Ingridient[0];
      }

      IndustrialMixerRecipe recipe = new IndustrialMixerRecipe(catalyst1, catalyst2, rfToAll, ticksToBrew, arrayIn, arrayOut);
      setregister(recipe);
      return recipe;
   }

   public static void setregister(IndustrialMixerRecipe rec) {
      allRecipes.add(rec);
   }
}
