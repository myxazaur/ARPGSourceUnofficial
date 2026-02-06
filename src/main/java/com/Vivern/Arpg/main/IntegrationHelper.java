package com.Vivern.Arpg.main;

import com.Vivern.Arpg.recipes.AssemblyTableRecipe;
import com.Vivern.Arpg.recipes.IndustrialMixerRecipe;
import com.Vivern.Arpg.recipes.Ingridient;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class IntegrationHelper {
   public static boolean printDebug = true;
   static Class clazzPulvelizer = null;
   static Method methodPulvelizer1 = null;
   static Method methodPulvelizer2 = null;
   static boolean thermalExpPulvelizer = false;
   static boolean ic2macerator = false;
   static Object ic2objInputGenerator = null;
   static Method ic2forStackMethod = null;
   static Object ic2managerMaceratorObject = null;
   static Method addrecipeIC2MethodMacerator = null;
   static boolean enderioSagmill = false;
   static Constructor constrEndIORecipeInput = null;
   static Constructor constrEndIORecipeOutput = null;
   static Constructor constructorEndIORecipe = null;
   static Class clazzEndIORecipeOutput = null;
   static Object enumBonusTypeCHANCE = null;
   static Object enumBonusTypeNONE = null;
   static Object endIOManagerSagmill = null;
   static Method methodAddRecipe = null;
   static boolean immersiveCrusher = false;
   static Method addRecipeMethodImmersCrusher = null;
   static boolean ic2extractor = false;
   static Object ic2managerExtractorObject = null;
   static Method addrecipeIC2MethodExtr = null;
   static Method methodMagmaCrucible = null;
   static boolean thermalExpMagmaCrucible = false;
   static Method methodTECentrifuge = null;
   static boolean thermalExpCentrifuge = false;
   static Class clazzTESmelter = null;
   static Method methodTESmelter1 = null;
   static Method methodTESmelter2 = null;
   static boolean thermalExpSmelter = false;
   static boolean enderioSmelter = false;
   static Method methodAddRecipeEndIOSmelter = null;
   static Object endIOManagerSmelter = null;
   static Constructor basicManyToOneConstr = null;
   static Constructor constructorEndIORecipe2 = null;
   static Class clazzEndIORecipeInput = null;
   static boolean immersiveAlloyKiln = false;
   static Method addRecipeMethodImmersAlloyKiln = null;
   public static FluidStack emptyfs = new FluidStack(FluidRegistry.WATER, 0);
   static boolean ic2compressor = false;
   static Object ic2managerCompressorObject = null;
   static Method addrecipeIC2MethodCompressor = null;
   static Method methodCompactor = null;
   static boolean thermalExpCompactor = false;
   static Class clazzCompactor = null;
   static Object compactorModeALL = null;

   public static void debugprint1(ItemStack output, String mod) {
      if (printDebug) {
         System.out.print("adding recipe crusher|" + mod + "|" + output.getDisplayName() + "|");
      }
   }

   public static void debugprint2(ItemStack output) {
      if (printDebug) {
         System.out.print("success");
         System.out.print("\n");
      }
   }

   public static void addRecipes() {
      if (printDebug) {
         System.out.println("addCrushersRecipes");
      }

      addCrushersRecipes();
      if (printDebug) {
         System.out.println("addAlloyRecipes");
      }

      addAlloyRecipes();
      if (printDebug) {
         System.out.println("addExtractorRecipes");
      }

      addExtractorRecipes();
      if (printDebug) {
         System.out.println("addMagmaCrucibleRecipes");
      }

      addMagmaCrucibleRecipes();
      if (printDebug) {
         System.out.println("addCentrifugeRecipes");
      }

      addCentrifugeRecipes();
      if (printDebug) {
         System.out.println("addPressRecipes");
      }

      addPressRecipes();
   }

   public static void addCrushersRecipes() {
      addCrushersRecipe(2000, new ItemStack(ItemsRegister.ICEGEM), new ItemStack(ItemsRegister.ICEDUST, 2), new ItemStack(ItemsRegister.ICEDUST), 20, false);
      addCrushersRecipe(
         1800,
         new ItemStack(BlocksRegister.CAVECRYSTALS),
         new ItemStack(ItemsRegister.DUSTMAGICCRYSTAL),
         new ItemStack(ItemsRegister.DUSTMAGICCRYSTAL),
         25,
         false
      );
      addCrushersRecipe(
         2200,
         new ItemStack(BlocksRegister.GLOWINGCAVECRYSTALS),
         new ItemStack(ItemsRegister.DUSTGLOWINGCRYSTAL),
         new ItemStack(ItemsRegister.DUSTGLOWINGCRYSTAL),
         25,
         false
      );
      addCrushersRecipe(
         4000,
         new ItemStack(BlocksRegister.MAGICSTONE),
         new ItemStack(ItemsRegister.DUSTMAGICCRYSTAL, 2),
         new ItemStack(ItemsRegister.DUSTMAGICCRYSTAL),
         10,
         false
      );
      addCrushersRecipe(1500, new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(ItemsRegister.MAGIC_POWDER), new ItemStack(Items.LEATHER), 10, false);
      addCrushersRecipe(6000, new ItemStack(ItemsRegister.INGOTADAMANTIUM), new ItemStack(ItemsRegister.DUSTADAMANTIUM), null, 0, false);
      addCrushersRecipe(5000, new ItemStack(ItemsRegister.INGOTTOXINIUM), new ItemStack(ItemsRegister.DUSTTOXINIUM), null, 0, false);
      addCrushersRecipe(2000, new ItemStack(ItemsRegister.INGOTARSENIC), new ItemStack(ItemsRegister.DUSTARSENIC), null, 0, false);
      addCrushersRecipe(3500, new ItemStack(ItemsRegister.INGOTWOLFRAM), new ItemStack(ItemsRegister.DUSTWOLFRAM), null, 0, false);
      addCrushersRecipe(5500, new ItemStack(ItemsRegister.INGOTSTORMSTEEL), new ItemStack(ItemsRegister.DUSTSTORMSTEEL), null, 0, false);
      addCrushersRecipe(4000, new ItemStack(ItemsRegister.INGOTSTORMBRASS), new ItemStack(ItemsRegister.DUSTSTORMBRASS), null, 0, false);
      addCrushersRecipe(2000, new ItemStack(ItemsRegister.INGOTBRASS), new ItemStack(ItemsRegister.DUSTBRASS), null, 0, false);
      addCrushersRecipe(2000, new ItemStack(ItemsRegister.INGOTZINC), new ItemStack(ItemsRegister.DUSTZINC), null, 0, false);
      addCrushersRecipe(2000, new ItemStack(ItemsRegister.INGOTTITANIUM), new ItemStack(ItemsRegister.DUSTTITANIUM), null, 0, false);
      addCrushersRecipe(2000, new ItemStack(ItemsRegister.INGOTMANGANESE), new ItemStack(ItemsRegister.DUSTMANGANESE), null, 0, false);
      addCrushersRecipe(2000, new ItemStack(ItemsRegister.INGOTCHROMIUM), new ItemStack(ItemsRegister.DUSTCHROMIUM), null, 0, false);
      addCrushersRecipe(2000, new ItemStack(ItemsRegister.INGOTBERYLLIUM), new ItemStack(ItemsRegister.DUSTBERYLLIUM), null, 0, false);
      addCrushersRecipe(2000, new ItemStack(ItemsRegister.INGOTSILVER), new ItemStack(ItemsRegister.DUSTSILVER), null, 0, true);
      addCrushersRecipe(1000, new ItemStack(ItemsRegister.INGOTLITHIUM), new ItemStack(ItemsRegister.DUSTLITHIUM), null, 0, false);
      addCrushersRecipe(
         3000, new ItemStack(BlocksRegister.OREALUMINIUM), new ItemStack(ItemsRegister.BAUXITE, 2), new ItemStack(ItemsRegister.BAUXITE), 30, false
      );
      addCrushersRecipe(4000, new ItemStack(BlocksRegister.OREZINC), new ItemStack(ItemsRegister.DUSTZINC, 2), new ItemStack(ItemsRegister.SULFUR), 25, false);
      addCrushersRecipe(
         4000, new ItemStack(BlocksRegister.OREARSENIC), new ItemStack(ItemsRegister.DUSTARSENIC, 2), new ItemStack(ItemsRegister.SULFUR), 30, false
      );
      addCrushersRecipe(2000, new ItemStack(BlocksRegister.SULFURCRYSTAL), new ItemStack(ItemsRegister.SULFUR), new ItemStack(ItemsRegister.SULFUR), 50, false);
      addCrushersRecipe(
         6000,
         new ItemStack(ItemsRegister.COPPERTRANSFORMER),
         new ItemStack(ItemsRegister.WIRECOPPER, 8),
         OreDicHelper.getOrNull(OreDicHelper.DUSTIRON, 1),
         100,
         false
      );
      addCrushersRecipe(
         2000, new ItemStack(ItemsRegister.BEARINGLEAD), new ItemStack(ItemsRegister.BEARINGALLOYDUST), new ItemStack(ItemsRegister.RUBBER), 50, false
      );
      addCrushersRecipe(1000, new ItemStack(Items.WHEAT), new ItemStack(ItemsRegister.FLOUR), null, 0, true);
      addCrushersRecipe(9000, new ItemStack(Blocks.HAY_BLOCK), new ItemStack(ItemsRegister.FLOUR, 9), null, 0, true);
      addCrushersRecipe(
         15000,
         new ItemStack(BlocksRegister.OREADAMANTIUM),
         new ItemStack(ItemsRegister.DUSTADAMANTIUMORE, 3),
         new ItemStack(ItemsRegister.DUSTSTONE),
         100,
         false
      );
      addCrushersRecipe(
         13000, new ItemStack(BlocksRegister.OREMITHRIL), new ItemStack(ItemsRegister.DUSTMITHRILORE, 3), new ItemStack(ItemsRegister.DUSTSTONE), 100, false
      );
      addCrushersRecipe(
         20000,
         new ItemStack(BlocksRegister.ORESTORMSTEEL),
         new ItemStack(ItemsRegister.DUSTSTORMSTEEL, 2),
         new ItemStack(ItemsRegister.DUSTSTONE, 2),
         100,
         false
      );
      addCrushersRecipe(
         10000,
         new ItemStack(BlocksRegister.OREWOLFRAM),
         new ItemStack(ItemsRegister.DUSTWOLFRAM),
         new ItemStack(ItemsRegister.DUSTRADIOACTIVESTONE, 3),
         100,
         false
      );
      addCrushersRecipe(
         12000,
         new ItemStack(BlocksRegister.ORETOXINIUM),
         new ItemStack(ItemsRegister.DUSTTOXINIUMORE, 2),
         new ItemStack(ItemsRegister.DUSTRADIOACTIVESTONE, 2),
         100,
         false
      );
      addCrushersRecipe(
         2000, new ItemStack(BlocksRegister.OREICEGL), new ItemStack(ItemsRegister.ICEDUST, 5), new ItemStack(Items.SNOWBALL, 2), 50, false
      );
      addCrushersRecipe(
         2000, new ItemStack(BlocksRegister.OREICESN), new ItemStack(ItemsRegister.ICEDUST, 5), new ItemStack(Items.SNOWBALL, 2), 50, false
      );
      addCrushersRecipe(2000, new ItemStack(BlocksRegister.RADIOSTONE), new ItemStack(ItemsRegister.DUSTRADIOACTIVESTONE, 4), null, 0, false);
      addCrushersRecipe(2000, new ItemStack(BlocksRegister.RADIOCOBBLE), new ItemStack(ItemsRegister.DUSTRADIOACTIVESTONE, 4), null, 0, false);
      addCrushersRecipe(
         3000, new ItemStack(BlocksRegister.SHELLROCK), new ItemStack(ItemsRegister.DUSTLIMESTONE, 4), new ItemStack(ItemsRegister.SEASHELL), 5, false
      );
      addCrushersRecipe(
         3000, new ItemStack(BlocksRegister.CHALKROCK), new ItemStack(ItemsRegister.DUSTLIMESTONE, 4), new ItemStack(Items.DYE, 1, 15), 50, false
      );
      addCrushersRecipe(
         3000, new ItemStack(BlocksRegister.STROMATOLITE), new ItemStack(ItemsRegister.DUSTLIMESTONE, 3), new ItemStack(ItemsRegister.SALT), 100, false
      );
      addCrushersRecipe(1000, new ItemStack(ItemsRegister.CORAL), new ItemStack(ItemsRegister.DUSTLIMESTONE), null, 0, false);
      addCrushersRecipe(1000, new ItemStack(BlocksRegister.CALCITE), new ItemStack(ItemsRegister.DUSTLIMESTONE, 4), null, 0, false);
      addCrushersRecipe(
         14000, new ItemStack(BlocksRegister.METALLICCORAL, 6), new ItemStack(ItemsRegister.DUSTAQUATIC), new ItemStack(ItemsRegister.DUSTLIMESTONE), 75, true
      );
      addCrushersRecipe(500, new ItemStack(ItemsRegister.RUBBLESTONE), new ItemStack(ItemsRegister.DUSTSTONE, 1), null, 0, false);
      addCrushersRecipe(
         1500, new ItemStack(BlocksRegister.ORESALT), new ItemStack(ItemsRegister.SALT, 3), new ItemStack(ItemsRegister.DUSTSTONE, 1), 100, false
      );
      addCrushersRecipe(
         3000, new ItemStack(BlocksRegister.BOMBRUSTED), new ItemStack(ItemsRegister.SCRAPBOMB, 2), new ItemStack(ItemsRegister.SCRAPBOMB), 40, false
      );
      addCrushersRecipe(
         3000, new ItemStack(BlocksRegister.BOMBSMALL), new ItemStack(ItemsRegister.SCRAPBOMB, 2), new ItemStack(ItemsRegister.SCRAPBOMB), 40, false
      );
      addCrushersRecipe(
         3000, new ItemStack(BlocksRegister.BOMBTOXIC), new ItemStack(ItemsRegister.SCRAPBOMB, 2), new ItemStack(ItemsRegister.SCRAPBOMB), 40, false
      );
      if (OreDicHelper.doesOreNameExist("dustObsidian")) {
         addCrushersRecipe(
            8000, new ItemStack(BlocksRegister.DEEPROCK), OreDicHelper.get("dustObsidian", 2), new ItemStack(ItemsRegister.DUSTBASALT, 2), 100, true
         );
      }
   }

   public static void addAlloyRecipes() {
      addAlloyRecipe(
         2000,
         new ItemStack(ItemsRegister.COPPERSULFATE, 9),
         OreDicHelper.get(OreDicHelper.COALCOKE, 1),
         null,
         OreDicHelper.get(OreDicHelper.COPPER, 1),
         OreDicHelper.get("itemSlag", 1),
         10
      );
      if (OreDicHelper.doesOreNameExist("fuelCoke")) {
         addAlloyRecipe(
            4200,
            new ItemStack(BlocksRegister.ORECHROMIUM),
            OreDicHelper.get("fuelCoke", 1),
            null,
            new ItemStack(ItemsRegister.INGOTCHROMIUM),
            OreDicHelper.get("itemCinnabar", 1),
            40
         );
      }

      if (OreDicHelper.doesOreNameExist("itemCinnabar")) {
         addAlloyRecipe(
            6000,
            new ItemStack(BlocksRegister.ORECHROMIUM),
            OreDicHelper.get("itemCinnabar", 1),
            null,
            new ItemStack(ItemsRegister.INGOTCHROMIUM, 2),
            OreDicHelper.get("itemSlagRich", 1),
            55
         );
      }

      addAlloyRecipe(500, new ItemStack(Items.SUGAR, 2), OreDicHelper.get("dyeRed", 1), null, new ItemStack(ItemsRegister.CANDYCANE), null, 0);
      addAlloyRecipe(
         500,
         new ItemStack(Items.APPLE),
         new ItemStack(Items.STICK),
         new ItemStack(Items.SUGAR),
         new ItemStack(ItemsRegister.CANDYAPPLE),
         null,
         0
      );
      addAlloyRecipe(
         2500,
         OreDicHelper.get("blockGlassHardened", 2),
         new ItemStack(ItemsRegister.NUGGETARSENIC),
         null,
         new ItemStack(ItemsRegister.CHEMICALGLASS, 6),
         null,
         0
      );
      if (OreDicHelper.doesOreNameExist("fusedQuartz") && OreDicHelper.doesOreNameExist("ingotLead")) {
         addAlloyRecipe(
            2500,
            OreDicHelper.get("fusedQuartz", 2),
            new ItemStack(ItemsRegister.NUGGETARSENIC),
            OreDicHelper.get("ingotLead", 1),
            new ItemStack(ItemsRegister.CHEMICALGLASS, 6),
            null,
            0
         );
      }

      addAlloyRecipe(2500, new ItemStack(ItemsRegister.FIERYOIL), new ItemStack(ItemsRegister.SULFUR), null, new ItemStack(ItemsRegister.RUBBER), null, 0);
      addAlloyRecipe(
         4000, OreDicHelper.get(OreDicHelper.COPPER, 3), new ItemStack(ItemsRegister.INGOTZINC), null, new ItemStack(ItemsRegister.INGOTBRASS, 4), null, 0
      );
      addAlloyRecipe(2000, new ItemStack(ItemsRegister.NUGGETADAMANTIUM, 9), null, null, new ItemStack(ItemsRegister.INGOTADAMANTIUM, 1), null, 0);
      addAlloyRecipe(
         6000, new ItemStack(BlocksRegister.ORECHROMIUM, 2), new ItemStack(Items.COAL), null, new ItemStack(ItemsRegister.INGOTCHROMIUM, 1), null, 0
      );
      addAlloyRecipe(
         8000,
         new ItemStack(Blocks.OBSIDIAN, 1),
         new ItemStack(Items.QUARTZ, 4),
         new ItemStack(ItemsRegister.INGOTCHROMIUM),
         new ItemStack(BlocksRegister.CHROMIUMGLASS, 1),
         null,
         0
      );
      if (OreDicHelper.doesOreNameExist("dustObsidian")) {
         addAlloyRecipe(
            8000,
            OreDicHelper.get("dustObsidian", 4),
            new ItemStack(ItemsRegister.DUSTQUARTZ, 4),
            new ItemStack(ItemsRegister.INGOTCHROMIUM),
            new ItemStack(BlocksRegister.CHROMIUMGLASS, 2),
            null,
            0
         );
      }

      addAlloyRecipe(
         3000, new ItemStack(ItemsRegister.BEARINGALLOYDUST), new ItemStack(ItemsRegister.RUBBER), null, new ItemStack(ItemsRegister.BEARINGLEAD, 1), null, 0
      );
      if (Item.getByNameOrId("ic2:crafting") != null) {
         addAlloyRecipe(
            3000,
            new ItemStack(ItemsRegister.BEARINGALLOYDUST),
            new ItemStack(Item.getByNameOrId("ic2:crafting"), 1, 0),
            null,
            new ItemStack(ItemsRegister.BEARINGLEAD, 1),
            null,
            0
         );
      }

      addAlloyRecipe(8000, new ItemStack(ItemsRegister.DUSTTOXINIUM), null, null, new ItemStack(ItemsRegister.INGOTTOXINIUM, 1), null, 0);
      if (OreDicHelper.doesOreNameExist("fuelCoke")) {
         addAlloyRecipe(
            5000, new ItemStack(ItemsRegister.DUSTQUARTZ, 3), OreDicHelper.get("fuelCoke", 1), null, new ItemStack(ItemsRegister.SILICIUM, 1), null, 0
         );
      }

      addAlloyRecipe(
         5000, new ItemStack(ItemsRegister.DUSTQUARTZ, 3), new ItemStack(Items.DIAMOND, 1), null, new ItemStack(ItemsRegister.SILICIUM, 1), null, 0
      );
      addAlloyRecipe(
         5000, new ItemStack(ItemsRegister.DUSTQUARTZ, 3), new ItemStack(ItemsRegister.DUSTLITHIUM, 1), null, new ItemStack(ItemsRegister.SILICIUM, 1), null, 0
      );
   }

   public static void addExtractorRecipes() {
      addExtractorRecipe(new ItemStack(BlocksRegister.FIERYBEANLOG, 2), new ItemStack(ItemsRegister.FIERYOIL));
      addExtractorRecipe(new ItemStack(BlocksRegister.CONIFERLOG, 2), new ItemStack(ItemsRegister.CONIFERROSIN));
      addExtractorRecipe(new ItemStack(BlocksRegister.TOXIBERRYLOG, 2), new ItemStack(ItemsRegister.TOXIBERRYJUICEDRIP));
      addExtractorRecipe(new ItemStack(BlocksRegister.TOXIBERRYLEAVES, 8), new ItemStack(ItemsRegister.TOXIBERRYJUICEDRIP));
      Item ic2res = Item.getByNameOrId("ic2:misc_resource");
      if (ic2res != null) {
         addExtractorRecipe(new ItemStack(BlocksRegister.SEAGRASS, 10), new ItemStack(ic2res, 1, 6));
         addExtractorRecipe(new ItemStack(BlocksRegister.SEAWEEDBLOCK, 10), new ItemStack(ic2res, 1, 6));
      }

      addExtractorRecipe(new ItemStack(ItemsRegister.MAGMABLOOMSEEDS, 16), new ItemStack(ItemsRegister.LIQUIDFIRE));
      addExtractorRecipe(new ItemStack(BlocksRegister.FIERYBEANSAPLING), new ItemStack(ItemsRegister.FIERYOIL, 3));
      addExtractorRecipe(new ItemStack(BlocksRegister.CONIFERSAPLING), new ItemStack(ItemsRegister.CONIFERROSIN, 3));
   }

   public static void addMagmaCrucibleRecipes() {
      addMagmaCricibleRecipe(4000, new ItemStack(Items.SLIME_BALL), new FluidStack(FluidsRegister.SLIME, 100));
      addMagmaCricibleRecipe(40000, new ItemStack(Blocks.SLIME_BLOCK), new FluidStack(FluidsRegister.SLIME, 1000));
      addMagmaCricibleRecipe(40000, new ItemStack(BlocksRegister.BROWNSLIME), new FluidStack(FluidsRegister.SLIME, 1000));
      addMagmaCricibleRecipe(300000, new ItemStack(BlocksRegister.DOLERITE), new FluidStack(FluidRegistry.LAVA, 1000));
      addMagmaCricibleRecipe(300000, new ItemStack(BlocksRegister.CAVEONYX), new FluidStack(FluidRegistry.LAVA, 1000));
      addMagmaCricibleRecipe(300000, new ItemStack(BlocksRegister.GREENONYX), new FluidStack(FluidRegistry.LAVA, 1000));
      addMagmaCricibleRecipe(300000, new ItemStack(BlocksRegister.DEEPROCK), new FluidStack(FluidRegistry.LAVA, 1000));
      addMagmaCricibleRecipe(300000, new ItemStack(BlocksRegister.RADIOSTONE), new FluidStack(FluidRegistry.LAVA, 1000));
      addMagmaCricibleRecipe(300000, new ItemStack(BlocksRegister.RADIOCOBBLE), new FluidStack(FluidRegistry.LAVA, 1000));
      if (FluidRegistry.getFluid("tree_oil") != null) {
         addMagmaCricibleRecipe(2000, new ItemStack(BlocksRegister.PALMSAPLING), new FluidStack(FluidRegistry.getFluid("tree_oil"), 50));
      }
   }

   public static void addCentrifugeRecipes() {
      addCentrifugeRecipe(4000, new ItemStack(BlocksRegister.FIERYBEANSAPLING), null, new ItemStack(ItemsRegister.FIERYOIL, 3), null, null, null, 100, 0, 0, 0);
      addCentrifugeRecipe(
         4000, new ItemStack(BlocksRegister.CONIFERSAPLING), null, new ItemStack(ItemsRegister.CONIFERROSIN, 3), null, null, null, 100, 0, 0, 0
      );
      addCentrifugeRecipe(
         3000,
         new ItemStack(ItemsRegister.CRYOGENCELL),
         new FluidStack(FluidsRegister.CRYON, 100),
         new ItemStack(ItemsRegister.EMPTYCELL),
         new ItemStack(Items.SNOWBALL),
         new ItemStack(ItemsRegister.ICEDUST),
         null,
         100,
         50,
         75,
         0
      );
      addCentrifugeRecipe(6000, new ItemStack(ItemsRegister.MAGMABLOOMSEEDS, 16), null, new ItemStack(ItemsRegister.LIQUIDFIRE), null, null, null, 100, 0, 0, 0);
      addCentrifugeRecipe(
         1000,
         new ItemStack(ItemsRegister.CONTEMPLANTSEEDS, 8),
         new FluidStack(FluidsRegister.POISON, 250),
         new ItemStack(ItemsRegister.TOXIBERRYJUICEDRIP),
         new ItemStack(ItemsRegister.PLANTFIBER, 2),
         null,
         null,
         100,
         100,
         0,
         0
      );
      addCentrifugeRecipe(
         1000,
         new ItemStack(ItemsRegister.MUCOPHILLUSSEEDS, 2),
         new FluidStack(FluidsRegister.SLIME, 100),
         new ItemStack(ItemsRegister.TOXIBERRYJUICEDRIP),
         new ItemStack(Items.SLIME_BALL, 2),
         null,
         null,
         100,
         100,
         0,
         0
      );
      addCentrifugeRecipe(
         1000,
         new ItemStack(ItemsRegister.VISCOSASEEDS, 2),
         new FluidStack(FluidsRegister.TOXIN, 100),
         new ItemStack(ItemsRegister.TOXIBERRYJUICEDRIP),
         new ItemStack(Items.SLIME_BALL),
         new ItemStack(ItemsRegister.PLANTFIBER),
         null,
         100,
         100,
         100,
         0
      );
      addCentrifugeRecipe(
         1000,
         new ItemStack(ItemsRegister.TOXIBERRYWEEPINGSEEDS, 3),
         new FluidStack(FluidsRegister.LUMINESCENT, 100),
         new ItemStack(ItemsRegister.TOXIBERRYJUICEDRIP),
         new ItemStack(ItemsRegister.CRYSTALPOISON),
         new ItemStack(ItemsRegister.PLANTFIBER),
         null,
         100,
         100,
         100,
         0
      );
      addCentrifugeRecipe(
         1000,
         new ItemStack(ItemsRegister.MUCOPHILLUSBROWNSEEDS, 5),
         new FluidStack(FluidsRegister.SLIME, 250),
         new ItemStack(ItemsRegister.TOXIBERRYJUICEDRIP, 2),
         new ItemStack(ItemsRegister.CRYSTALPOISON),
         new ItemStack(Items.SLIME_BALL, 3),
         null,
         100,
         100,
         100,
         0
      );
      addCentrifugeRecipe(
         1000,
         new ItemStack(ItemsRegister.TOXIBULBSEEDS, 1),
         new FluidStack(FluidsRegister.SULFURICACID, 200),
         new ItemStack(ItemsRegister.TOXIBERRYJUICEDRIP),
         new ItemStack(ItemsRegister.PLANTFIBER),
         null,
         null,
         100,
         100,
         0,
         0
      );
      addCentrifugeRecipe(
         1000,
         new ItemStack(ItemsRegister.GLOWINGTOXIBERRY, 3),
         new FluidStack(FluidsRegister.LUMINESCENT, 250),
         new ItemStack(ItemsRegister.TOXIBERRYJUICEDRIP, 3),
         new ItemStack(ItemsRegister.CRYSTALPOISON),
         new ItemStack(ItemsRegister.PLANTFIBER, 2),
         null,
         100,
         100,
         100,
         0
      );
      addCentrifugeRecipe(
         1000,
         new ItemStack(ItemsRegister.SMALLTOXIBERRY, 6),
         new FluidStack(FluidsRegister.POISON, 100),
         new ItemStack(ItemsRegister.TOXIBERRYJUICEDRIP),
         new ItemStack(ItemsRegister.PLANTFIBER),
         null,
         null,
         100,
         100,
         0,
         0
      );
      addCentrifugeRecipe(
         1000,
         new ItemStack(BlocksRegister.TOXIBERRYSAPLING),
         null,
         new ItemStack(ItemsRegister.TOXIBERRYSTICK),
         new ItemStack(ItemsRegister.TOXIBERRYJUICEDRIP),
         new ItemStack(ItemsRegister.PLANTFIBER),
         null,
         100,
         100,
         100,
         0
      );
   }

   public static void addPressRecipes() {
      addPressRecipe(5000, new ItemStack(ItemsRegister.DUSTWOLFRAM), new ItemStack(ItemsRegister.INGOTWOLFRAM), false);
   }

   public static void addCrushersRecipe(int rf, ItemStack input, ItemStack output, ItemStack output2, int chanceOut2, boolean bannedForIC2) {
      if (!thermalExpPulvelizer) {
         try {
            Class clazz = Class.forName("cofh.thermalexpansion.util.managers.machine.PulverizerManager");
            Method method = clazz.getMethod("addRecipe", int.class, ItemStack.class, ItemStack.class, ItemStack.class, int.class);
            Method method2 = clazz.getMethod("addRecipe", int.class, ItemStack.class, ItemStack.class);
            clazzPulvelizer = clazz;
            methodPulvelizer1 = method;
            methodPulvelizer2 = method2;
            if (clazzPulvelizer != null && methodPulvelizer1 != null && methodPulvelizer2 != null) {
               thermalExpPulvelizer = true;
            }
         } catch (Exception var22) {
         }
      }

      if (thermalExpPulvelizer) {
         try {
            debugprint1(output, "thermalexpansion");
            if (output2 != null) {
               methodPulvelizer1.invoke(null, rf, input, output, output2, chanceOut2);
            } else {
               methodPulvelizer2.invoke(null, rf, input, output);
            }

            debugprint2(output);
         } catch (Exception var21) {
         }
      }

      if (!ic2macerator) {
         try {
            Class clazzInputGenerator = Class.forName("ic2.core.recipe.RecipeInputFactory");
            Method forStackMethod = clazzInputGenerator.getMethod("forStack", ItemStack.class, int.class);
            Object objInputGenerator = clazzInputGenerator.newInstance();
            Class clazzIC2recipes = Class.forName("ic2.api.recipe.Recipes");
            Field managerMacerator = clazzIC2recipes.getField("macerator");
            Object managerMaceratorObject = managerMacerator.get(null);
            Class clazzIC2IRecipeInput = Class.forName("ic2.api.recipe.IRecipeInput");
            addrecipeIC2MethodMacerator = managerMacerator.getType()
               .getMethod("addRecipe", clazzIC2IRecipeInput, NBTTagCompound.class, boolean.class, ItemStack[].class);
            ic2forStackMethod = forStackMethod;
            ic2objInputGenerator = objInputGenerator;
            ic2macerator = true;
            ic2managerMaceratorObject = managerMaceratorObject;
         } catch (Exception var20) {
         }
      }

      if (ic2macerator) {
         try {
            debugprint1(output, "ic2");
            if (!bannedForIC2) {
               Object recipeIC2Input = ic2forStackMethod.invoke(ic2objInputGenerator, input.copy(), 1);
               addrecipeIC2MethodMacerator.invoke(ic2managerMaceratorObject, recipeIC2Input, new NBTTagCompound(), true, new ItemStack[]{output.copy()});
            }

            debugprint2(output);
         } catch (Exception var19) {
         }
      }

      if (!enderioSagmill) {
         try {
            Class clazzEndIORecipe = Class.forName("crazypants.enderio.base.recipe.Recipe");
            Class clazzEndIORecipeInput = Class.forName("crazypants.enderio.base.recipe.RecipeInput");
            clazzEndIORecipeOutput = Class.forName("crazypants.enderio.base.recipe.RecipeOutput");
            Class clazzEndIOIRecipeInput = Class.forName("crazypants.enderio.base.recipe.IRecipeInput");
            Class clazzEndIOBonusType = Class.forName("crazypants.enderio.base.recipe.RecipeBonusType");
            Class clazzEndIORecipeLvl = Class.forName("crazypants.enderio.base.recipe.RecipeLevel");
            Constructor constructorEndIORecipeInput = clazzEndIORecipeInput.getConstructor(ItemStack.class);
            Constructor constructorEndIORecipeOutput = clazzEndIORecipeOutput.getConstructor(ItemStack.class, float.class);
            constructorEndIORecipe = clazzEndIORecipe.getConstructor(
               clazzEndIOIRecipeInput, int.class, clazzEndIOBonusType, Array.newInstance(clazzEndIORecipeOutput, 1).getClass()
            );
            Class clazzEndIOManagerSagmill = Class.forName("crazypants.enderio.base.recipe.sagmill.SagMillRecipeManager");
            Method methodInstance = clazzEndIOManagerSagmill.getMethod("getInstance");
            methodAddRecipe = clazzEndIOManagerSagmill.getMethod("addRecipe", clazzEndIORecipe);
            endIOManagerSagmill = methodInstance.invoke(null);
            constrEndIORecipeInput = constructorEndIORecipeInput;
            constrEndIORecipeOutput = constructorEndIORecipeOutput;
            enumBonusTypeCHANCE = clazzEndIOBonusType.getField("CHANCE_ONLY").get(null);
            enderioSagmill = true;
         } catch (Exception var18) {
         }
      }

      if (enderioSagmill) {
         try {
            debugprint1(output, "enderio");
            Object recipeInput = constrEndIORecipeInput.newInstance(input.copy());
            Object recipeOutput = constrEndIORecipeOutput.newInstance(output.copy(), 1.0F);
            Object arrayOfOutputs = Array.newInstance(clazzEndIORecipeOutput, output2 == null ? 1 : 2);
            Array.set(arrayOfOutputs, 0, recipeOutput);
            if (output2 != null) {
               Array.set(arrayOfOutputs, 1, constrEndIORecipeOutput.newInstance(output2.copy(), chanceOut2 / 100.0F));
            }

            Object recipe = constructorEndIORecipe.newInstance(recipeInput, rf, enumBonusTypeCHANCE, arrayOfOutputs);
            methodAddRecipe.invoke(endIOManagerSagmill, recipe);
            debugprint2(output);
         } catch (Exception var17) {
         }
      }

      if (!immersiveCrusher) {
         try {
            Class clazzCrusherRecipe = Class.forName("blusunrize.immersiveengineering.api.crafting.CrusherRecipe");
            addRecipeMethodImmersCrusher = clazzCrusherRecipe.getMethod("addRecipe", ItemStack.class, Object.class, int.class);
            immersiveCrusher = true;
         } catch (Exception var16) {
         }
      }

      if (immersiveCrusher) {
         try {
            debugprint1(output, "immersiveengineering");
            addRecipeMethodImmersCrusher.invoke(null, output.copy(), input.copy(), rf);
            debugprint2(output);
         } catch (Exception var15) {
         }
      }
   }

   public static void addAlloyRecipe(int rf, ItemStack input, ItemStack input2, ItemStack input3, ItemStack output, ItemStack output2, int chanceOut2) {
      if (!thermalExpSmelter) {
         try {
            Class clazz = Class.forName("cofh.thermalexpansion.util.managers.machine.SmelterManager");
            Method method = clazz.getMethod("addRecipe", int.class, ItemStack.class, ItemStack.class, ItemStack.class, ItemStack.class, int.class);
            Method method2 = clazz.getMethod("addRecipe", int.class, ItemStack.class, ItemStack.class, ItemStack.class);
            clazzTESmelter = clazz;
            methodTESmelter1 = method;
            methodTESmelter2 = method2;
            if (clazzTESmelter != null && methodTESmelter1 != null && methodTESmelter2 != null) {
               thermalExpSmelter = true;
            }
         } catch (Exception var21) {
         }
      }

      if (thermalExpSmelter && input3 == null) {
         try {
            debugprint1(output, "thermalexpansion");
            if (output2 != null) {
               methodTESmelter1.invoke(null, rf, input, input2, output, output2, chanceOut2);
            } else {
               methodTESmelter2.invoke(null, rf, input, input2, output);
            }

            debugprint2(output);
         } catch (Exception var20) {
         }
      }

      if (!enderioSmelter) {
         try {
            Class clazzEndIORecipe = Class.forName("crazypants.enderio.base.recipe.Recipe");
            Class clazzEndIORecipeMany = Class.forName("crazypants.enderio.base.recipe.IManyToOneRecipe");
            clazzEndIORecipeInput = Class.forName("crazypants.enderio.base.recipe.RecipeInput");
            clazzEndIORecipeOutput = Class.forName("crazypants.enderio.base.recipe.RecipeOutput");
            Class clazzEndIOIRecipeInput = Class.forName("crazypants.enderio.base.recipe.IRecipeInput");
            Class clazzEndIOBonusType = Class.forName("crazypants.enderio.base.recipe.RecipeBonusType");
            Constructor constructorEndIORecipeInput = clazzEndIORecipeInput.getConstructor(ItemStack.class);
            Constructor constructorEndIORecipeOutput = clazzEndIORecipeOutput.getConstructor(ItemStack.class, float.class);
            constructorEndIORecipe2 = clazzEndIORecipe.getConstructor(
               clazzEndIORecipeOutput, int.class, clazzEndIOBonusType, Array.newInstance(clazzEndIOIRecipeInput, 1).getClass()
            );
            Class clazzEndIOManagerSmelter = Class.forName("crazypants.enderio.base.recipe.alloysmelter.AlloyRecipeManager");
            Method methodInstance = clazzEndIOManagerSmelter.getMethod("getInstance");
            methodAddRecipeEndIOSmelter = clazzEndIOManagerSmelter.getMethod("addRecipe", clazzEndIORecipeMany);
            endIOManagerSmelter = methodInstance.invoke(null);
            constrEndIORecipeInput = constructorEndIORecipeInput;
            constrEndIORecipeOutput = constructorEndIORecipeOutput;
            enumBonusTypeNONE = clazzEndIOBonusType.getField("NONE").get(null);
            Class clazzEndIORecipeManyBase = Class.forName("crazypants.enderio.base.recipe.BasicManyToOneRecipe");
            basicManyToOneConstr = clazzEndIORecipeManyBase.getConstructor(clazzEndIORecipe);
            enderioSmelter = true;
         } catch (Exception var19) {
         }
      }

      if (enderioSmelter) {
         try {
            debugprint1(output, "enderio");
            Object recipeInput = constrEndIORecipeInput.newInstance(input.copy());
            Object recipeOutput = constrEndIORecipeOutput.newInstance(output.copy(), 1.0F);
            Object arrayOfInputs = Array.newInstance(clazzEndIORecipeInput, input2 == null ? 1 : (input3 == null ? 2 : 3));
            Array.set(arrayOfInputs, 0, recipeInput);
            if (input2 != null) {
               Array.set(arrayOfInputs, 1, constrEndIORecipeInput.newInstance(input2.copy()));
            }

            if (input3 != null) {
               Array.set(arrayOfInputs, 2, constrEndIORecipeInput.newInstance(input3.copy()));
            }

            Object recipe = constructorEndIORecipe2.newInstance(recipeOutput, rf, enumBonusTypeNONE, arrayOfInputs);
            methodAddRecipeEndIOSmelter.invoke(endIOManagerSmelter, basicManyToOneConstr.newInstance(recipe));
            debugprint2(output);
         } catch (Exception var18) {
         }
      }

      if (!immersiveAlloyKiln) {
         try {
            Class clazzKilnRecipe = Class.forName("blusunrize.immersiveengineering.api.crafting.AlloyRecipe");
            addRecipeMethodImmersAlloyKiln = clazzKilnRecipe.getMethod("addRecipe", ItemStack.class, Object.class, Object.class, int.class);
            immersiveAlloyKiln = true;
         } catch (Exception var17) {
         }
      }

      if (immersiveAlloyKiln && input3 == null) {
         try {
            debugprint1(output, "immersiveengineering");
            addRecipeMethodImmersAlloyKiln.invoke(null, output.copy(), input.copy(), input2.copy(), Math.max(rf / 4, 1));
            debugprint2(output);
         } catch (Exception var16) {
         }
      }

      ItemStack[] inputsarray = input3 == null
         ? (input2 == null ? new ItemStack[]{input} : new ItemStack[]{input, input2})
         : new ItemStack[]{input, input2, input3};
      ItemStack[] outputsarray = output2 != null && chanceOut2 >= 100 ? new ItemStack[]{output, output2} : new ItemStack[]{output};
      NetherMelterRecipesRegister.addRecipe(outputsarray, rf / 1000.0F, inputsarray);
   }

   public static void addExtractorRecipe(ItemStack input, ItemStack output) {
      if (!ic2extractor) {
         try {
            Class clazzInputGenerator = Class.forName("ic2.core.recipe.RecipeInputFactory");
            Method forStackMethod = clazzInputGenerator.getMethod("forStack", ItemStack.class, int.class);
            Object objInputGenerator = clazzInputGenerator.newInstance();
            Class clazzIC2recipes = Class.forName("ic2.api.recipe.Recipes");
            Field managerExtractor = clazzIC2recipes.getField("extractor");
            Object managerMaceratorObject = managerExtractor.get(null);
            Class clazzIC2IRecipeInput = Class.forName("ic2.api.recipe.IRecipeInput");
            addrecipeIC2MethodExtr = managerExtractor.getType()
               .getMethod("addRecipe", clazzIC2IRecipeInput, NBTTagCompound.class, boolean.class, ItemStack[].class);
            ic2forStackMethod = forStackMethod;
            ic2objInputGenerator = objInputGenerator;
            ic2extractor = true;
            ic2managerExtractorObject = managerMaceratorObject;
         } catch (Exception var10) {
         }
      }

      if (ic2extractor) {
         try {
            debugprint1(output, "ic2");
            Object recipeIC2Input = ic2forStackMethod.invoke(ic2objInputGenerator, input.copy(), input.getCount());
            addrecipeIC2MethodExtr.invoke(ic2managerExtractorObject, recipeIC2Input, new NBTTagCompound(), true, new ItemStack[]{output.copy()});
            debugprint2(output);
         } catch (Exception var9) {
         }
      }
   }

   public static void addMagmaCricibleRecipe(int rf, ItemStack input, FluidStack output) {
      if (!thermalExpMagmaCrucible) {
         try {
            Class clazz = Class.forName("cofh.thermalexpansion.util.managers.machine.CrucibleManager");
            Method method = clazz.getMethod("addRecipe", int.class, ItemStack.class, FluidStack.class);
            methodMagmaCrucible = method;
            thermalExpMagmaCrucible = true;
         } catch (Exception var6) {
         }
      }

      if (thermalExpMagmaCrucible) {
         try {
            debugprint1(input, "thermalexpansion");
            methodMagmaCrucible.invoke(null, rf, input, output);
            debugprint2(input);
         } catch (Exception var5) {
         }
      }
   }

   public static void addCentrifugeRecipe(
      int rf,
      ItemStack input,
      FluidStack fluid,
      ItemStack output1,
      ItemStack output2,
      ItemStack output3,
      ItemStack output4,
      int chance1,
      int chance2,
      int chance3,
      int chance4
   ) {
      if (output1 != null && output4 == null && (output2 == null || chance2 >= 100) && (output3 == null || chance3 >= 100)) {
         IndustrialMixerRecipe rec = IndustrialMixerRecipesRegister.addRecipe(
            ItemsRegister.MODULECENTRIFUGE,
            null,
            rf,
            Ingridient.getIngridient(output1),
            Ingridient.getIngridient(output2),
            Ingridient.getIngridient(output3),
            rf / 20,
            Ingridient.getIngridient(input),
            Ingridient.EMPTY,
            Ingridient.EMPTY
         );
         if (fluid != null) {
            rec.setFluidOutput1(fluid.getUnlocalizedName().replaceFirst("fluid.", ""), fluid.amount);
         }
      }

      if (!thermalExpCentrifuge) {
         try {
            Class clazz = Class.forName("cofh.thermalexpansion.util.managers.machine.CentrifugeManager");
            Method method = clazz.getMethod("addRecipe", int.class, ItemStack.class, List.class, List.class, FluidStack.class);
            methodTECentrifuge = method;
            thermalExpCentrifuge = true;
         } catch (Exception var14) {
         }
      }

      if (thermalExpCentrifuge) {
         try {
            debugprint1(input, "thermalexpansion");
            List<ItemStack> listOutputs = new ArrayList<>();
            List<Integer> listChances = new ArrayList<>();
            listOutputs.add(output1);
            listChances.add(chance1);
            if (output2 != null) {
               listOutputs.add(output2);
               listChances.add(chance2);
            }

            if (output3 != null) {
               listOutputs.add(output3);
               listChances.add(chance3);
            }

            if (output4 != null) {
               listOutputs.add(output4);
               listChances.add(chance4);
            }

            methodTECentrifuge.invoke(null, rf, input, listOutputs, listChances, fluid == null ? emptyfs : fluid);
            debugprint2(input);
         } catch (Exception var13) {
         }
      }
   }

   public static void addPressRecipe(int rf, ItemStack input, ItemStack output, boolean bannedForIC2) {
      if (!thermalExpCompactor) {
         try {
            Class clazz = Class.forName("cofh.thermalexpansion.util.managers.machine.CompactorManager");
            Class compactorManagerMODEclazz = Class.forName("cofh.thermalexpansion.util.managers.machine.CompactorManager$Mode");
            Field firstField = compactorManagerMODEclazz.getFields()[0];
            Object enumModeALL = firstField.get(null);
            Method method = clazz.getMethod("addRecipe", int.class, ItemStack.class, ItemStack.class, compactorManagerMODEclazz);
            clazzCompactor = clazz;
            methodCompactor = method;
            if (clazzCompactor != null && methodCompactor != null && enumModeALL != null) {
               thermalExpCompactor = true;
               compactorModeALL = enumModeALL;
            }
         } catch (Exception var14) {
         }
      }

      if (!ic2compressor) {
         try {
            Class clazzInputGenerator = Class.forName("ic2.core.recipe.RecipeInputFactory");
            Method forStackMethod = clazzInputGenerator.getMethod("forStack", ItemStack.class, int.class);
            Object objInputGenerator = clazzInputGenerator.newInstance();
            Class clazzIC2recipes = Class.forName("ic2.api.recipe.Recipes");
            Field managerCompressor = clazzIC2recipes.getField("compressor");
            Object managerCompressorObject = managerCompressor.get(null);
            Class clazzIC2IRecipeInput = Class.forName("ic2.api.recipe.IRecipeInput");
            addrecipeIC2MethodCompressor = managerCompressor.getType()
               .getMethod("addRecipe", clazzIC2IRecipeInput, NBTTagCompound.class, boolean.class, ItemStack[].class);
            ic2forStackMethod = forStackMethod;
            ic2objInputGenerator = objInputGenerator;
            ic2compressor = true;
            ic2managerCompressorObject = managerCompressorObject;
         } catch (Exception var13) {
         }
      }

      if (thermalExpCompactor) {
         try {
            methodCompactor.invoke(null, rf, input, output, compactorModeALL);
         } catch (Exception var12) {
         }
      }

      if (ic2compressor) {
         try {
            debugprint1(output, "ic2");
            if (!bannedForIC2) {
               Object recipeIC2Input = ic2forStackMethod.invoke(ic2objInputGenerator, input.copy(), 1);
               addrecipeIC2MethodCompressor.invoke(
                  ic2managerCompressorObject, recipeIC2Input, new NBTTagCompound(), true, new ItemStack[]{output.copy()}
               );
            }

            debugprint2(output);
         } catch (Exception var11) {
         }
      }

      ItemStack out2x = output.copy();
      out2x.setCount(out2x.getCount() * 2);
      AssemblyTableRecipesRegister.addRecipe(
         out2x,
         rf * 2,
         ItemStack.EMPTY,
         200,
         ItemStack.EMPTY,
         ItemStack.EMPTY,
         ItemStack.EMPTY,
         ItemStack.EMPTY,
         input,
         ItemStack.EMPTY,
         ItemStack.EMPTY,
         ItemStack.EMPTY,
         ItemStack.EMPTY,
         new AssemblyTableRecipe.AugmentCost(
            AssemblyTableRecipesRegister.PRESS,
            new Ingridient.IngridientItem(input.getItem(), input.getCount(), input.getMetadata(), input.getHasSubtypes())
         ),
         null,
         null,
         null,
         null
      );
   }
}
