//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.container.GUIElementDistributor;
import com.Vivern.Arpg.elements.Buckshot;
import com.Vivern.Arpg.elements.IWeapon;
import com.Vivern.Arpg.elements.ItemBullet;
import com.Vivern.Arpg.recipes.AlchemicLabRecipe;
import com.Vivern.Arpg.recipes.AssemblyTableRecipe;
import com.Vivern.Arpg.recipes.IndustrialMixerRecipe;
import com.Vivern.Arpg.recipes.Ingridient;
import com.Vivern.Arpg.recipes.NetherMelterRecipe;
import com.Vivern.Arpg.recipes.SpellForgeRecipe;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ItemsElements {
   static HashMap<Item, ItemElementsMetadataSelector> itemElementsRegistry = new HashMap<>();
   public static ElementsPack EMPTY_ELEMENTS = new ElementsPack();

   public static void init() {
      List<File> listAllFiles = getAllElementsFiles();
      if (!listAllFiles.isEmpty()) {
         for (File file : listAllFiles) {
            try {
               FileReader fr = new FileReader(file);
               BufferedReader reader = new BufferedReader(fr);
               int lineNumber = 0;

               for (String line = reader.readLine(); line != null; lineNumber++) {
                  ItemElementsFileEntry elementsFileEntry = lineToElementsFileEntry(line, lineNumber);
                  if (elementsFileEntry != null) {
                     registerElements(elementsFileEntry.item, elementsFileEntry.useMeta, elementsFileEntry.meta, elementsFileEntry.elements);
                  }

                  line = reader.readLine();
               }
            } catch (FileNotFoundException var8) {
               var8.printStackTrace();
            } catch (IOException var9) {
               var9.printStackTrace();
            }
         }
      }
   }

   public static List<File> getAllElementsFiles() {
      List<File> list = new ArrayList<>();
      File config = FileHelper.getConfigFile();
      if (config != null) {
         File arpg_item_elements = new File(config, "arpg_item_elements");
         if (arpg_item_elements != null) {
            File filemain = FileHelper.getChildFile(arpg_item_elements, "arpg.txt");
            if (filemain == null) {
               filemain = FileHelper.createChildFile(arpg_item_elements, "arpg.txt");
               String resource = FileHelper.readFromResources("arpg_item_elements/arpg.txt");
               if (filemain != null && resource != null) {
                  FileHelper.writeToFile(filemain, resource);
               }
            }

            if (filemain != null && filemain.exists()) {
               list.add(filemain);
            }

            filemain = FileHelper.getChildFile(arpg_item_elements, "generated.txt");
            if (filemain == null) {
               filemain = FileHelper.createChildFile(arpg_item_elements, "generated.txt");
               String resource = FileHelper.readFromResources("arpg_item_elements/generated.txt");
               if (filemain != null && resource != null) {
                  FileHelper.writeToFile(filemain, resource);
               }
            }

            if (filemain != null && filemain.exists()) {
               list.add(filemain);
            }
         }
      }

      return list;
   }

   public static void registerElements(Item item, boolean useMeta, int meta, ShardType[] elements, float[] elementsAmount, float purity) {
      if (useMeta) {
         ItemElementsMetadataSelector oldSelector = itemElementsRegistry.get(item);
         if (oldSelector != null) {
            ElementsPack pack = new ElementsPack(elements, elementsAmount, purity);
            oldSelector.addNewEntry(meta, pack);
         } else {
            ElementsPack pack = new ElementsPack(elements, elementsAmount, purity);
            ItemElementsMetadataSelector selector = new ItemElementsMetadataSelector(true, meta, pack);
            itemElementsRegistry.put(item, selector);
         }
      } else {
         ElementsPack pack = new ElementsPack(elements, elementsAmount, purity);
         ItemElementsMetadataSelector selector = new ItemElementsMetadataSelector(false, meta, pack);
         itemElementsRegistry.put(item, selector);
      }
   }

   public static void registerElements(Item item, boolean useMeta, int meta, ElementsPack pack) {
      if (useMeta) {
         ItemElementsMetadataSelector oldSelector = itemElementsRegistry.get(item);
         if (oldSelector != null) {
            oldSelector.addNewEntry(meta, pack);
         } else {
            ItemElementsMetadataSelector selector = new ItemElementsMetadataSelector(true, meta, pack);
            itemElementsRegistry.put(item, selector);
         }
      } else {
         ItemElementsMetadataSelector selector = new ItemElementsMetadataSelector(false, meta, pack);
         itemElementsRegistry.put(item, selector);
      }
   }

   @Nullable
   public static ItemElementsFileEntry lineToElementsFileEntry(String line, int lineNumber) {
      String[] words = line.split(" ");
      if (words.length == 16) {
         Item item = Item.getByNameOrId(words[0]);
         if (item != null) {
            try {
               int meta = Integer.parseInt(words[1]);
               float purity = Float.parseFloat(words[2]);
               boolean useMeta = words[3].equals("true");
               float[] elementsAmount = new float[12];

               for (int i = 0; i < 12; i++) {
                  elementsAmount[i] = Float.parseFloat(words[4 + i]);
               }

               ItemElementsFileEntry fileEntry = new ItemElementsFileEntry();
               fileEntry.elements = new ElementsPack(elementsAmount, purity);
               fileEntry.fileLine = lineNumber;
               fileEntry.item = item;
               fileEntry.meta = meta;
               fileEntry.useMeta = useMeta;
               return fileEntry;
            } catch (NumberFormatException var9) {
               return null;
            }
         }
      }

      return null;
   }

   public static ElementsPack getAllElements(ItemStack stack) {
      Item item = stack.getItem();
      int meta = stack.getMetadata();
      ItemElementsMetadataSelector selector = itemElementsRegistry.get(item);
      if (selector != null) {
         ElementsPack pack = selector.get(meta);
         if (pack != null) {
            return pack;
         }
      }

      return EMPTY_ELEMENTS;
   }

   public static ElementsPack getAllElements(Block block) {
      Item item = Item.getItemFromBlock(block);
      int meta = 0;
      ItemElementsMetadataSelector selector = itemElementsRegistry.get(item);
      if (selector != null) {
         ElementsPack pack = selector.get(meta);
         if (pack != null) {
            return pack;
         }
      }

      return EMPTY_ELEMENTS;
   }

   public static ElementsPack getAllElements(Fluid fluid) {
      String name = fluid.getName();
      if ("water".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.2F);
      } else if ("lava".equals(name)) {
         return new ElementsPack(new float[]{5.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.2F);
      } else if ("milk".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 2.0F}, 0.2F);
      } else if ("gasoline".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 0.0F}, 0.9F);
      } else if ("petroleum".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 0.0F}, 0.0F);
      } else if ("fuel_oil".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 0.0F}, 0.0F);
      } else if ("mana_oil".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 4.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.2F);
      } else if ("natural_gas".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.5F);
      } else if ("creosote".equals(name) || "ic2creosote".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 2.0F, 2.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 0.0F}, 0.2F);
      } else if ("coal".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 2.0F, 2.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 0.0F}, 0.2F);
      } else if ("refined_oil".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 0.0F}, 0.6F);
      } else if ("refined_fuel".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 0.0F}, 0.9F);
      } else if ("tree_oil".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 1.0F, 2.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 0.0F}, 0.2F);
      } else if ("seed_oil".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 1.0F, 2.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 0.0F}, 0.2F);
      } else if ("refined_biofuel".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 2.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 0.0F}, 0.6F);
      } else if ("ic2biogas".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F}, 0.1F);
      } else if ("ic2hydrogen".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.8F);
      } else if ("cryon".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.6F);
      } else if ("poison".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 5.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.2F);
      } else if ("slime".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 3.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 3.0F}, 0.1F);
      } else if ("darkness".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.8F);
      } else if ("larva_water".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 5.0F}, 0.0F);
      } else if ("hydrothermal_solution".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.6F);
      } else if ("biogenic_acid".equals(name)) {
         return new ElementsPack(new float[]{3.0F, 0.0F, 5.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F}, 0.0F);
      } else if ("toxin".equals(name)) {
         return new ElementsPack(new float[]{0.0F, 0.0F, 2.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.5F);
      } else if ("sulfuric_acid".equals(name)) {
         return new ElementsPack(new float[]{4.0F, 0.0F, 1.0F, 0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.6F);
      } else if ("luminescent_liquid".equals(name)) {
         return new ElementsPack(new float[]{1.0F, 0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F}, 0.2F);
      } else if ("sulfuric_gas".equals(name)) {
         return new ElementsPack(new float[]{1.0F, 0.0F, 0.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.2F);
      } else if ("nitric_acid".equals(name)) {
         return new ElementsPack(new float[]{3.0F, 0.0F, 3.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.6F);
      } else {
         return "dissolved_toxinium".equals(name)
            ? new ElementsPack(new float[]{5.0F, 2.0F, 4.0F, 0.0F, 10.0F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.0F)
            : EMPTY_ELEMENTS;
      }
   }

   public static void writeGeneratedItemElements() {
      for (IRecipe irecipe : CraftingManager.REGISTRY) {
         writeGeneratedForIRecipe(irecipe);
      }

      for (SpellForgeRecipe recipe : SpellForgeRecipesRegister.allRecipes) {
         writeGeneratedForSpellForge(recipe);
      }

      for (AssemblyTableRecipe recipe : AssemblyTableRecipesRegister.allRecipes) {
         writeGeneratedForList(
            recipe.exportInputsAsList(),
            recipe.craftresult.createStack(),
            recipe.craftresult2 != null && recipe.craftresult2 != Ingridient.EMPTY ? recipe.craftresult2.createStack() : null
         );
      }

      for (NetherMelterRecipe recipe : NetherMelterRecipesRegister.allRecipes) {
         if (recipe.output.size() == 1 || recipe.output.size() == 2) {
            writeGeneratedForList(
               recipe.exportInputsAsList(),
               ((Ingridient)recipe.output.get(0)).createStack(),
               recipe.output.size() == 2 ? ((Ingridient)recipe.output.get(1)).createStack() : null
            );
         }
      }

      for (AlchemicLabRecipe recipex : AlchemicLabRecipesRegister.allRecipes) {
         if (recipex.output.size() == 1 || recipex.output.size() == 2) {
            if (recipex.costfluidname == null || recipex.costfluidname.isEmpty()) {
               writeGeneratedForList(
                  recipex.exportInputsAsList(),
                  ((Ingridient)recipex.output.get(0)).createStack(),
                  recipex.output.size() == 2 ? ((Ingridient)recipex.output.get(1)).createStack() : null
               );
            } else if (FluidRegistry.getFluid(recipex.costfluidname) != null && recipex.amountexist == 0) {
               writeGeneratedForListAndLiquid(
                  recipex.exportInputsAsList(),
                  new FluidStack(FluidRegistry.getFluid(recipex.costfluidname), recipex.amountcost),
                  ((Ingridient)recipex.output.get(0)).createStack(),
                  recipex.output.size() == 2 ? ((Ingridient)recipex.output.get(1)).createStack() : null
               );
            }
         }
      }

      for (IndustrialMixerRecipe recipexx : IndustrialMixerRecipesRegister.allRecipes) {
         if (recipexx.output.size() == 1 || recipexx.output.size() == 2) {
            if (recipexx.costfluidname1 != null && !recipexx.costfluidname1.isEmpty()) {
               if (FluidRegistry.getFluid(recipexx.costfluidname1) != null && recipexx.amountexist1 == 0 && recipexx.amountexist2 == 0) {
                  writeGeneratedForListAndLiquid(
                     recipexx.exportInputsAsList(),
                     new FluidStack(FluidRegistry.getFluid(recipexx.costfluidname1), recipexx.amountcost1),
                     ((Ingridient)recipexx.output.get(0)).createStack(),
                     recipexx.output.size() == 2 ? ((Ingridient)recipexx.output.get(1)).createStack() : null
                  );
               }
            } else if (recipexx.amountexist1 == 0 && recipexx.amountexist2 == 0 && recipexx.amountcost1 == 0 && recipexx.amountcost2 == 0) {
               writeGeneratedForList(
                  recipexx.exportInputsAsList(),
                  ((Ingridient)recipexx.output.get(0)).createStack(),
                  recipexx.output.size() == 2 ? ((Ingridient)recipexx.output.get(1)).createStack() : null
               );
            }
         }
      }
   }

   private static void writeGeneratedForListAndLiquid(List<ItemStack> input, FluidStack inputFluid, ItemStack output, @Nullable ItemStack output2) {
      ElementsPack pack = new ElementsPack();
      pack.purity = 1.0F;

      for (ItemStack stack : input) {
         if (stack != null && !stack.isEmpty()) {
            ElementsPack ingridientElements = getAllElements(stack);
            addElementsToGeneratedPack(pack, ingridientElements, stack.getCount());
         }
      }

      ElementsPack fluidElements = getAllElements(inputFluid.getFluid());
      if (fluidElements != EMPTY_ELEMENTS) {
         addElementsToGeneratedPack(pack, fluidElements, inputFluid.amount / 1000.0F);
      }

      if (subtractElementsToGeneratedPack(pack, output2)) {
         pack.scale(1.0F / output.getCount());
         pack.normalize();
         if (pack.howMuchNotNull() > 0) {
            applyOtherGeneratedContributions(pack, output);
            GUIElementDistributor.writeElementsOfItemToFile(
               output.getItem(), output.getMetadata(), pack.purity, output.getItem().getHasSubtypes(), "generated", false, pack.elementsAmount
            );
         }
      }
   }

   private static void writeGeneratedForList(List<ItemStack> input, ItemStack output, @Nullable ItemStack output2) {
      ElementsPack pack = new ElementsPack();
      pack.purity = 1.0F;

      for (ItemStack stack : input) {
         if (stack != null && !stack.isEmpty()) {
            ElementsPack ingridientElements = getAllElements(stack);
            addElementsToGeneratedPack(pack, ingridientElements, stack.getCount());
         }
      }

      if (subtractElementsToGeneratedPack(pack, output2)) {
         pack.scale(1.0F / output.getCount());
         pack.normalize();
         if (pack.howMuchNotNull() > 0) {
            applyOtherGeneratedContributions(pack, output);
            GUIElementDistributor.writeElementsOfItemToFile(
               output.getItem(), output.getMetadata(), pack.purity, output.getItem().getHasSubtypes(), "generated", false, pack.elementsAmount
            );
         }
      }
   }

   private static void writeGeneratedForSpellForge(SpellForgeRecipe recipe) {
      ElementsPack pack = new ElementsPack();
      pack.purity = 1.0F;
      if (recipe.catalyst != null) {
         ItemStack stack = recipe.catalyst.getGoodStackForRender();
         if (stack != null && !stack.isEmpty()) {
            ElementsPack ingridientElements = getAllElements(stack);
            addElementsToGeneratedPack(pack, ingridientElements, stack.getCount());
         }
      }

      if (recipe.useEnergy) {
         for (int i = 0; i < 12; i++) {
            pack.elementsAmount[i] = pack.elementsAmount[i] + recipe.arrayelementsCost[i];
         }
      }

      boolean hasAtLeast1 = false;

      for (Ingridient ingridient : recipe.recipe) {
         ItemStack stack = ingridient.createStack();
         if (stack != null && !stack.isEmpty()) {
            ElementsPack ingridientElements = getAllElements(stack);
            if (ingridientElements != EMPTY_ELEMENTS) {
               pack.add(ingridientElements, stack.getCount());
               if (!hasAtLeast1 && ingridientElements.howMuchNotNull() > 0) {
                  hasAtLeast1 = true;
               }
            }
         }
      }

      if (hasAtLeast1) {
         ItemStack output = recipe.craftresult.createStack();
         pack.scale(1.0F / output.getCount());
         pack.normalize();
         if (pack.howMuchNotNull() > 0) {
            applyOtherGeneratedContributions(pack, output);
            GUIElementDistributor.writeElementsOfItemToFile(
               output.getItem(), output.getMetadata(), pack.purity, output.getItem().getHasSubtypes(), "generated", false, pack.elementsAmount
            );
         }
      }
   }

   private static void writeGeneratedForIRecipe(IRecipe irecipe) {
      ElementsPack pack = new ElementsPack();
      pack.purity = 1.0F;

      for (Ingredient ingridient : irecipe.getIngredients()) {
         ItemStack[] stacks = ingridient.getMatchingStacks();
         if (stacks == null || stacks.length <= 0) {
            return;
         }

         for (int i = 0; i < stacks.length; i++) {
            ElementsPack ingridientElements = getAllElements(stacks[i]);
            if (ingridientElements.howMuchNotNull() > 0) {
               addElementsToGeneratedPack(pack, ingridientElements, stacks[i].getCount());
               break;
            }
         }
      }

      ItemStack output = irecipe.getRecipeOutput();
      pack.scale(1.0F / output.getCount());
      pack.normalize();
      if (pack.howMuchNotNull() > 0) {
         applyOtherGeneratedContributions(pack, output);
         GUIElementDistributor.writeElementsOfItemToFile(
            output.getItem(), output.getMetadata(), pack.purity, output.getItem().getHasSubtypes(), "generated", false, pack.elementsAmount
         );
      }
   }

   public static void addElementsToGeneratedPack(ElementsPack pack, ElementsPack toAdd, float count) {
      boolean addLive = pack.getAmount(ShardType.LIVE) >= 1.0F;
      if (toAdd != EMPTY_ELEMENTS) {
         for (int i = 0; i < 12; i++) {
            if (i != 11 || addLive) {
               pack.elementsAmount[i] = pack.elementsAmount[i] + toAdd.elementsAmount[i] * count;
            }
         }

         pack.purity = Math.min(pack.purity, toAdd.purity);
      }
   }

   public static boolean subtractElementsToGeneratedPack(ElementsPack pack, @Nullable ItemStack itemToSubtract) {
      if (itemToSubtract == null) {
         return true;
      } else {
         float count = itemToSubtract.getCount();
         ElementsPack toSubtract = getAllElements(itemToSubtract);
         if (toSubtract == EMPTY_ELEMENTS) {
            return false;
         } else {
            for (int i = 0; i < 12; i++) {
               pack.elementsAmount[i] = Math.max(pack.elementsAmount[i] - toSubtract.elementsAmount[i] * count, 0.0F);
            }

            return true;
         }
      }
   }

   private static void applyOtherGeneratedContributions(ElementsPack pack, ItemStack output) {
      if (output.getItem() instanceof IWeapon) {
         pack.elementsAmount[9] = Math.max(pack.elementsAmount[9], output.getCount() == 1 ? 4.0F : 1.0F);
      }

      if ((output.getItem() instanceof ItemBullet || output.getItem() instanceof Buckshot) && pack.howMuchNotNull() > 0) {
         pack.elementsAmount[9] = Math.max(pack.elementsAmount[9], 0.1F);
      }

      if (output.getItem() instanceof ItemArrow) {
         pack.elementsAmount[9] = Math.max(pack.elementsAmount[9], 0.5F);
      }
   }

   public static class ElementsPack {
      public float[] elementsAmount;
      public float purity;

      public ElementsPack() {
         this.purity = 0.1F;
         this.elementsAmount = new float[12];
      }

      public ElementsPack(float[] elementsAmountById, float purity) {
         this.purity = purity;
         this.elementsAmount = elementsAmountById;
      }

      public ElementsPack(ShardType[] elements, float[] elementsAmount, float purity) {
         this.purity = purity;
         elementsAmount = new float[12];

         for (int i = 0; i < elements.length; i++) {
            elementsAmount[elements[i].id - 1] = elementsAmount[i];
         }
      }

      public float getAmount(ShardType shardType) {
         return this.elementsAmount[shardType.id - 1];
      }

      public int howMuchNotNull() {
         int amount = 0;

         for (int i = 0; i < 12; i++) {
            if (this.elementsAmount[i] > 0.0F) {
               amount++;
            }
         }

         return amount;
      }

      public void add(ElementsPack other, float multiplier) {
         for (int i = 0; i < this.elementsAmount.length; i++) {
            this.elementsAmount[i] = this.elementsAmount[i] + other.elementsAmount[i] * multiplier;
         }

         this.purity = Math.min(this.purity, other.purity);
      }

      public ElementsPack copy() {
         ElementsPack newpack = new ElementsPack();
         newpack.purity = 1.0F;
         newpack.add(this, 1.0F);
         return newpack;
      }

      public void scale(float multiplier) {
         for (int i = 0; i < this.elementsAmount.length; i++) {
            this.elementsAmount[i] = this.elementsAmount[i] * multiplier;
         }
      }

      public void normalize() {
         for (int i = 0; i < this.elementsAmount.length; i++) {
            this.elementsAmount[i] = (int)(this.elementsAmount[i] * 10.0F) / 10.0F;
         }
      }

      public EnumPurity getPurity() {
         if (this.purity < 0.2F) {
            return EnumPurity.IMPURITY;
         } else if (this.purity < 0.4F) {
            return EnumPurity.CONGENERIC;
         } else if (this.purity < 0.65F) {
            return EnumPurity.REFINED;
         } else {
            return this.purity < 1.0F ? EnumPurity.PRISTINE : EnumPurity.PERFECT;
         }
      }
   }

   public static enum EnumPurity {
      IMPURITY("impurity", 6908269),
      CONGENERIC("congeneric", 3568594),
      REFINED("refined", 4054056),
      PRISTINE("pristine", 15974163),
      PERFECT("perfect", 9846015);

      public String name;
      public int color;

      private EnumPurity(String name, int color) {
         this.name = name;
         this.color = color;
      }

      public String getDisplayName() {
         return this.name;
      }
   }

   public static class ItemElementsFileEntry {
      public Item item;
      public int meta;
      public boolean useMeta;
      public int fileLine;
      public ElementsPack elements;
      public File file;
   }

   public static class ItemElementsMetadataSelector {
      HashMap<Integer, ElementsPack> metadataMap;
      ElementsPack simplePack;

      public ItemElementsMetadataSelector(boolean useMeta, int meta, ElementsPack elementsPack) {
         if (useMeta) {
            this.metadataMap = new HashMap<>();
            this.metadataMap.put(meta, elementsPack);
         } else {
            this.simplePack = elementsPack;
         }
      }

      public void addNewEntry(int meta, ElementsPack elementsPack) {
         if (this.metadataMap != null) {
            this.metadataMap.put(meta, elementsPack);
         } else {
            throw new RuntimeException("TRYING ADD NEW METADATA ENTRY TO ITEM THAT NOT USES METADATA DURING ITEM ELEMENTS REGISTRATION");
         }
      }

      public ElementsPack get(int metadata) {
         return this.simplePack != null ? this.simplePack : this.metadataMap.get(metadata);
      }
   }
}
