package com.vivern.arpg.main;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class OreDicHelper {
   public static String COPPER = "ingotCopper";
   public static String LEAD = "ingotLead";
   public static String STEEL = "ingotSteel";
   public static String BRONZE = "ingotBronze";
   public static String TIN = "ingotTin";
   public static String ALUMINIUM = "ingotAluminum";
   public static String ELECTRUM = "ingotElectrum";
   public static String TITANIUM = "ingotTitanium";
   public static String BRASS = "ingotBrass";
   public static String INVAR = "ingotInvar";
   public static String SILVER = "ingotSilver";
   public static String NICKEL = "ingotNickel";
   public static String PLATINUM = "ingotPlatinum";
   public static String CHROMIUM = "ingotChromium";
   public static String BERYLLIUM = "ingotBeryllium";
   public static String MANGANESE = "ingotManganese";
   public static String ZINC = "ingotZinc";
   public static String IRIDIUM = "ingotIridium";
   public static String LITHIUM = "ingotLithium";
   public static String SILICIUM = "ingotSilicium";
   public static String RUBY = "gemRuby";
   public static String SAPPHIRE = "gemSapphire";
   public static String CITRINE = "gemCitrine";
   public static String AMETHYST = "gemAmethyst";
   public static String TOPAZ = "gemTopaz";
   public static String RHINESTONE = "gemRhinestone";
   public static String CERTUSQUARTZ = "crystalCertusQuartz";
   public static String FLUIXCRYSTAL = "crystalFluix";
   public static String DUSTSULFUR = "dustSulfur";
   public static String DUSTCHARCOAL = "dustCharcoal";
   public static String DUSTCOAL = "dustCoal";
   public static String DUSTQUARTZ = "dustQuartz";
   public static String DUSTNETHERQUARTZ = "dustNetherQuartz";
   public static String DUSTCERTUSQUARTZ = "dustCertusQuartz";
   public static String DUSTLAZULI = "dustLapis";
   public static String DUSTOBSIDIAN = "dustObsidian";
   public static String DUSTSILICONDIOXIDE = "dustSiliconDioxide";
   public static String DUSTDIAMOND = "dustDiamond";
   public static String DUSTSALTPETER = "dustSaltpeter";
   public static String DUSTSALT = "dustSalt";
   public static String DUSTFLOUR = "dustFlour";
   public static String DUSTSTONE = "dustStone";
   public static String DUSTCOPPER = "dustCopper";
   public static String DUSTLEAD = "dustLead";
   public static String DUSTSTEEL = "dustSteel";
   public static String DUSTBRONZE = "dustBronze";
   public static String DUSTTIN = "dustTin";
   public static String DUSTALUMINIUM = "dustAluminum";
   public static String DUSTELECTRUM = "dustElectrum";
   public static String DUSTTITANIUM = "dustTitanium";
   public static String DUSTBRASS = "dustBrass";
   public static String DUSTSILVER = "dustSilver";
   public static String DUSTIRON = "dustIron";
   public static String DUSTGOLD = "dustGold";
   public static String DUSTNICKEL = "dustNickel";
   public static String DUSTPLATINUM = "dustPlatinum";
   public static String DUSTCHROMIUM = "dustChromium";
   public static String DUSTBERYLLIUM = "dustBeryllium";
   public static String DUSTMANGANESE = "dustManganese";
   public static String DUSTZINC = "dustZinc";
   public static String DUSTASH = "dustAsh";
   public static String DUSTLITHIUM = "dustLithium";
   public static String NUGGETBRASS = "nuggetBrass";
   public static String NUGGETTITANIUM = "nuggetTitanium";
   public static String NUGGETZINC = "nuggetZinc";
   public static String NUGGETSILVER = "nuggetSilver";
   public static String NUGGETALUMINIUM = "nuggetAluminum";
   public static String NUGGETLITHIUM = "nuggetLithium";
   public static String QUICKSILVER = "quicksilver";
   public static String RUBBER = "itemRubber";
   public static String RUBBER2 = "materialRubber";
   public static String SILICON = "itemSilicon";
   public static String COALCOKE = "fuelCoke";
   public static String RICHSLAG = "itemSlagRich";
   public static String YEAST = "yeast";
   public static String BEETROOT = "cropBeetroot";
   public static String FLINT = "itemFlint";
   public static String COAL = "itemCoal";
   public static String CHARCOAL = "itemCharcoal";
   public static String BLAZEROD = "rodBlaze";
   public static String GHASTTEAR = "itemGhastTear";
   public static String ENDEREYE = "pearlEnderEye";
   public static String BLAZEPOWDER = "itemBlazePowder";
   public static String CLAY = "itemClay";
   public static String LEATHER = "itemLeather";
   public static String PLANKS = "plankWood";
   public static String LOG = "logWood";
   public static String CIRCUIT = "circuitBasic";
   public static String CIRCUITADVANCED = "circuitAdvanced";
   public static String PLASTIC = "materialPlastic";
   public static String PLASTICADVANCED = "materialAdvancedPlastic";
   public static String POLYMERADVANCED = "materialAdvancedPolymer";
   public static String GLASSHARDENED = "blockGlassHardened";
   public static String BLOCKSILVER = "blockSilver";
   public static String ORESILVER = "oreSilver";

   public static List<String> getOreNames(ItemStack stack) {
      List<String> list = new ArrayList<>();
      if (!stack.isEmpty()) {
         int[] ids = OreDictionary.getOreIDs(stack);

         for (int id : ids) {
            list.add(OreDictionary.getOreName(id));
         }
      }

      return list;
   }

   public static boolean hasOreName(ItemStack stack) {
      return !stack.isEmpty() ? OreDictionary.getOreIDs(stack).length > 0 : false;
   }

   public static boolean itemStringOredigMatches(ItemStack st1, String name) {
      List<String> names = getOreNames(st1);
      if (!names.isEmpty()) {
         for (String n : names) {
            if (n.equals(name)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean matches(ItemStack st1, ItemStack st2) {
      List<String> names1 = getOreNames(st1);
      if (!names1.isEmpty()) {
         for (String n : names1) {
            if (itemStringOredigMatches(st2, n)) {
               return true;
            }
         }
      }

      return false;
   }

   public static ItemStack get(String oredigName, int stackCount) {
      for (ItemStack ore : OreDictionary.getOres(oredigName)) {
         if (!ore.isEmpty()) {
            return new ItemStack(ore.getItem(), stackCount, ore.getMetadata(), ore.getTagCompound());
         }
      }

      return ItemStack.EMPTY;
   }

   public static ItemStack getButThisModInPriority(String oredigName, int stackCount) {
      NonNullList<ItemStack> ores = OreDictionary.getOres(oredigName);
      ItemStack returned = ItemStack.EMPTY;

      for (ItemStack ore : ores) {
         if (!ore.isEmpty()) {
            if (returned.isEmpty()) {
               returned = new ItemStack(ore.getItem(), stackCount, ore.getMetadata(), ore.getTagCompound());
            } else {
               ResourceLocation registryName = ore.getItem().getRegistryName();
               String modId = registryName == null ? null : registryName.getNamespace();
               if ("arpg".equals(modId)) {
                  returned = new ItemStack(ore.getItem(), stackCount, ore.getMetadata(), ore.getTagCompound());
                  break;
               }
            }
         }
      }

      return returned;
   }

   public static ItemStack getForJei(String oredigName, int stackCount) {
      NonNullList<ItemStack> ores = OreDictionary.getOres(oredigName);
      int amount = ores.size();
      int current = (int)(Minecraft.getSystemTime() / 1000L % amount);
      ItemStack ore = (ItemStack)ores.get(current);
      return !ore.isEmpty() ? new ItemStack(ore.getItem(), stackCount, ore.getMetadata(), ore.getTagCompound()) : ItemStack.EMPTY;
   }

   @Nullable
   public static ItemStack getOrNull(String oredigName, int stackCount) {
      NonNullList<ItemStack> ores = OreDictionary.getOres(oredigName);
      if (!ores.isEmpty()) {
         for (ItemStack ore : ores) {
            if (!ore.isEmpty()) {
               return new ItemStack(ore.getItem(), stackCount, ore.getMetadata(), ore.getTagCompound());
            }
         }
      }

      return null;
   }

   public static IBlockState getBlock(String oredigName) {
      for (ItemStack ore : OreDictionary.getOres(oredigName)) {
         if (!ore.isEmpty() && ore.getItem() instanceof ItemBlock) {
            ItemBlock itembl = (ItemBlock)ore.getItem();
            int i = itembl.getMetadata(ore.getMetadata());
            return itembl.getBlock().getStateFromMeta(i);
         }
      }

      return Blocks.AIR.getDefaultState();
   }

   public static void init() {
      OreDictionary.registerOre(RUBY, ItemsRegister.RUBY);
      OreDictionary.registerOre(SAPPHIRE, ItemsRegister.SAPPHIRE);
      OreDictionary.registerOre(CITRINE, ItemsRegister.CITRINE);
      OreDictionary.registerOre(AMETHYST, ItemsRegister.AMETHYST);
      OreDictionary.registerOre(TOPAZ, ItemsRegister.TOPAZ);
      OreDictionary.registerOre(RHINESTONE, ItemsRegister.RHINESTONE);
      OreDictionary.registerOre(MANGANESE, ItemsRegister.INGOTMANGANESE);
      OreDictionary.registerOre(DUSTMANGANESE, ItemsRegister.DUSTMANGANESE);
      OreDictionary.registerOre(BERYLLIUM, ItemsRegister.INGOTBERYLLIUM);
      OreDictionary.registerOre(DUSTBERYLLIUM, ItemsRegister.DUSTBERYLLIUM);
      OreDictionary.registerOre(CHROMIUM, ItemsRegister.INGOTCHROMIUM);
      OreDictionary.registerOre(DUSTCHROMIUM, ItemsRegister.DUSTCHROMIUM);
      OreDictionary.registerOre(DUSTSULFUR, ItemsRegister.SULFUR);
      OreDictionary.registerOre(DUSTASH, ItemsRegister.ASH);
      OreDictionary.registerOre(DUSTSALTPETER, ItemsRegister.SALTPETER);
      OreDictionary.registerOre(DUSTSTONE, ItemsRegister.DUSTSTONE);
      OreDictionary.registerOre(BRASS, ItemsRegister.INGOTBRASS);
      OreDictionary.registerOre(DUSTBRASS, ItemsRegister.DUSTBRASS);
      OreDictionary.registerOre(NUGGETBRASS, ItemsRegister.NUGGETBRASS);
      OreDictionary.registerOre(TITANIUM, ItemsRegister.INGOTTITANIUM);
      OreDictionary.registerOre(DUSTTITANIUM, ItemsRegister.DUSTTITANIUM);
      OreDictionary.registerOre(NUGGETTITANIUM, ItemsRegister.NUGGETTITANIUM);
      OreDictionary.registerOre(SILVER, ItemsRegister.INGOTSILVER);
      OreDictionary.registerOre(DUSTSILVER, ItemsRegister.DUSTSILVER);
      OreDictionary.registerOre(NUGGETSILVER, ItemsRegister.NUGGETSILVER);
      OreDictionary.registerOre(ALUMINIUM, ItemsRegister.INGOTALUMINIUM);
      OreDictionary.registerOre(DUSTALUMINIUM, ItemsRegister.DUSTALUMINIUM);
      OreDictionary.registerOre(NUGGETALUMINIUM, ItemsRegister.NUGGETALUMINIUM);
      OreDictionary.registerOre(ZINC, ItemsRegister.INGOTZINC);
      OreDictionary.registerOre(DUSTZINC, ItemsRegister.DUSTZINC);
      OreDictionary.registerOre(NUGGETZINC, ItemsRegister.NUGGETZINC);
      OreDictionary.registerOre(PLANKS, BlocksRegister.FIERYBEANPLANKS);
      OreDictionary.registerOre(PLANKS, BlocksRegister.PALMPLANKS);
      OreDictionary.registerOre(PLANKS, BlocksRegister.ROTTENPLANKS);
      OreDictionary.registerOre(LOG, BlocksRegister.FIERYBEANLOG);
      OreDictionary.registerOre(LOG, BlocksRegister.PALMLOG);
      OreDictionary.registerOre(CIRCUIT, ItemsRegister.CIRCUIT);
      OreDictionary.registerOre(CIRCUITADVANCED, ItemsRegister.CIRCUITADVANCED);
      OreDictionary.registerOre(RUBBER, ItemsRegister.RUBBER);
      OreDictionary.registerOre(RUBBER2, ItemsRegister.RUBBER);
      OreDictionary.registerOre(PLASTIC, ItemsRegister.SLIMEPLASTIC);
      OreDictionary.registerOre(PLASTIC, ItemsRegister.PLASTIC);
      OreDictionary.registerOre(PLASTICADVANCED, ItemsRegister.PLASTIC);
      OreDictionary.registerOre(POLYMERADVANCED, ItemsRegister.ADVANCED_POLYMER);
      OreDictionary.registerOre(DUSTSALT, ItemsRegister.SALT);
      OreDictionary.registerOre(YEAST, ItemsRegister.YEAST);
      OreDictionary.registerOre(DUSTFLOUR, ItemsRegister.FLOUR);
      OreDictionary.registerOre(DUSTQUARTZ, ItemsRegister.DUSTQUARTZ);
      OreDictionary.registerOre(GLASSHARDENED, BlocksRegister.CHROMIUMGLASS);
      OreDictionary.registerOre(BLOCKSILVER, BlocksRegister.SILVERBLOCK);
      OreDictionary.registerOre(ORESILVER, BlocksRegister.ORESILVER);
      OreDictionary.registerOre(LITHIUM, ItemsRegister.INGOTLITHIUM);
      OreDictionary.registerOre(DUSTLITHIUM, ItemsRegister.DUSTLITHIUM);
      OreDictionary.registerOre(NUGGETLITHIUM, ItemsRegister.NUGGETLITHIUM);
      OreDictionary.registerOre(SILICIUM, ItemsRegister.SILICIUM);
      OreDictionary.registerOre("itemSilicon", ItemsRegister.SILICIUM);
      regiserOreIfNameNoAdded(Items.FLINT, FLINT);
      regiserOreIfNameNoAdded(new ItemStack(Items.COAL, 1, 0), COAL);
      regiserOreIfNameNoAdded(new ItemStack(Items.COAL, 1, 1), CHARCOAL);
      regiserOreIfNameNoAdded(Items.BLAZE_ROD, BLAZEROD);
      regiserOreIfNameNoAdded(Items.GHAST_TEAR, GHASTTEAR);
      regiserOreIfNameNoAdded(Items.ENDER_EYE, ENDEREYE);
      regiserOreIfNameNoAdded(Items.BLAZE_POWDER, BLAZEPOWDER);
      regiserOreIfNameNoAdded(Items.BEETROOT, BEETROOT);
      regiserOreIfNameNoAdded(Items.CLAY_BALL, CLAY);
      regiserOreIfNameNoAdded(Items.LEATHER, LEATHER);
      regiserOreIfNameNoAdded(new ItemStack(Blocks.IRON_BARS), "barsIron");
   }

   public static void regiserOreIfNameNoAdded(Item item, String name) {
      regiserOreIfNameNoAdded(new ItemStack(item), name);
   }

   public static void regiserOreIfNameNoAdded(ItemStack itemStack, String name) {
      if (!itemStringOredigMatches(itemStack, name)) {
         OreDictionary.registerOre(name, itemStack);
      }
   }

   public static ItemStack getMissingItemStack(String nameOrOredicName, int amount) {
      if (nameOrOredicName.contains("dust")) {
         return new ItemStack(ItemsRegister.MISSINGDUST, amount).setStackDisplayName("Missing mod item: " + nameOrOredicName);
      } else if (nameOrOredicName.contains("ingot")) {
         return new ItemStack(ItemsRegister.MISSINGINGOT, amount).setStackDisplayName("Missing mod item: " + nameOrOredicName);
      } else {
         return nameOrOredicName.contains("nugget")
            ? new ItemStack(ItemsRegister.MISSINGNUGGET, amount).setStackDisplayName("Missing mod item: " + nameOrOredicName)
            : new ItemStack(ItemsRegister.MISSINGMATERIAL, amount).setStackDisplayName("Missing mod item: " + nameOrOredicName);
      }
   }

   public static boolean isMissing(Item item) {
      return item == ItemsRegister.MISSINGMATERIAL
         || item == ItemsRegister.MISSINGINGOT
         || item == ItemsRegister.MISSINGDUST
         || item == ItemsRegister.MISSINGNUGGET;
   }

   public static boolean doesOreNameExist(String name) {
      for (ItemStack ore : OreDictionary.getOres(name)) {
         if (!ore.isEmpty()) {
            return true;
         }
      }

      return false;
   }
}
