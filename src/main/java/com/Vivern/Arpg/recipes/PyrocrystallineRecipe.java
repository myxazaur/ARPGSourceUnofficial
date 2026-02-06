package com.Vivern.Arpg.recipes;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.tileentity.TilePyrocrystallineConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class PyrocrystallineRecipe {
   public static List<PyrocrystallineRecipe> registry = new ArrayList<>();
   public static OreCost[] EMPTY = new OreCost[0];
   public List<OreCost> input1;
   public List<OreCost> input2;
   public List<OreCost> input3;
   int recipeSize = 0;
   public String output;
   public int fluidcost = 1000;
   public float geoEnergyCost;
   public float manaToMelt;
   public int damageToFocus;
   public int progressPerOne = 10;
   public Block source = null;

   public PyrocrystallineRecipe(
      String output,
      Block source,
      int fluidcost,
      float geoEnergyCost,
      float manaToMelt,
      int progressPerOne,
      int damageToFocus,
      OreCost[] input1,
      OreCost[] input2,
      OreCost[] input3
   ) {
      this.geoEnergyCost = geoEnergyCost;
      this.manaToMelt = manaToMelt;
      this.damageToFocus = damageToFocus;
      this.input1 = Arrays.asList(input1);
      this.input2 = Arrays.asList(input2);
      this.input3 = Arrays.asList(input3);
      this.fluidcost = fluidcost;
      this.output = output;
      this.progressPerOne = progressPerOne;
      this.source = source;
      if (!this.input1.isEmpty()) {
         this.recipeSize++;
      }

      if (!this.input2.isEmpty()) {
         this.recipeSize++;
      }

      if (!this.input3.isEmpty()) {
         this.recipeSize++;
      }
   }

   public static void init() {
      registerRecipe(
         new PyrocrystallineRecipe(
            "oreCopper",
            Blocks.STONE,
            1000,
            2.0F,
            10.0F,
            20,
            20,
            new OreCost[]{
               new OreCost("dirt", 2),
               new OreCost("sand", 2),
               new OreCost("sandstone", 1),
               new OreCost(Blocks.SOUL_SAND, 1),
               new OreCost(BlocksRegister.TOXICDIRT, 1),
               new OreCost(BlocksRegister.SCRAP, 1)
            },
            new OreCost[]{
               new OreCost("slimeball", 8),
               new OreCost(Items.PRISMARINE_CRYSTALS, 3),
               new OreCost(Items.PRISMARINE_SHARD, 3)
            },
            new OreCost[]{
               new OreCost("blockPrismarine", 2),
               new OreCost("blockPrismarineBrick", 2),
               new OreCost("blockPrismarineDark", 2),
               new OreCost(Blocks.SEA_LANTERN, 1),
               new OreCost(BlocksRegister.SLUDGE, 4),
               new OreCost(BlocksRegister.JUNK, 4)
            }
         )
      );
   }

   public static void registerRecipe(PyrocrystallineRecipe recipe) {
      if (!registry.contains(recipe)) {
         registry.add(recipe);
      }
   }

   public boolean tryMeltItems(TilePyrocrystallineConverter tile) {
      if (tile.manaStored < this.manaToMelt) {
         return false;
      } else if (tile.geomanticEnergy < this.geoEnergyCost) {
         return false;
      } else if (!tile.melt.isEmpty() && !tile.melt.equals(this.output)) {
         return false;
      } else {
         if (tile.tank1.getFluid() != null) {
            String name = tile.tank1.getFluid().getFluid().getName();
            int fluidcostmod = this.fluidcost;
            if (!"lava".equals(name)) {
               if ("pyroteum".equals(name)) {
                  fluidcostmod = (int)(fluidcostmod * 0.35);
               } else if ("pethrotheum".equals(name)) {
                  fluidcostmod = (int)(fluidcostmod * 0.3);
               } else if ("redstone".equals(name)) {
                  fluidcostmod = (int)(fluidcostmod * 0.25);
               } else {
                  if (!"basalt".equals(name)) {
                     return false;
                  }

                  fluidcostmod = (int)(fluidcostmod * 0.9);
               }
            }

            if (tile.meltCount > 9000) {
               return false;
            }

            if (tile.tank1.getFluidAmount() < fluidcostmod) {
               return false;
            }

            if (this.hasItems(tile)) {
               tile.manaStored = tile.manaStored - this.manaToMelt;
               tile.geomanticEnergy = tile.geomanticEnergy - this.geoEnergyCost;
               tile.tank1.drain(fluidcostmod, true);
               tile.melt = this.output;
               tile.attemptFocusDamage = this.damageToFocus;
               tile.progressPerOne = this.progressPerOne;
               tile.source = this.source;
               tile.meltCount += 1000;
               this.spendItems(tile);
               return true;
            }
         }

         return false;
      }
   }

   public boolean hasItems(TilePyrocrystallineConverter tile) {
      int countInTile = 0;

      for (int i = 0; i < 3; i++) {
         if (!tile.getStackInSlot(i).isEmpty()) {
            countInTile++;
         }
      }

      if (countInTile < this.recipeSize) {
         return false;
      } else {
         boolean hasstack1 = false;
         if (!this.input1.isEmpty()) {
            for (OreCost need : this.input1) {
               for (int ix = 0; ix < 3; ix++) {
                  ItemStack has = tile.getStackInSlot(ix);
                  if (this.allowed(has, need)) {
                     hasstack1 = true;
                     break;
                  }
               }
            }
         }

         if (!hasstack1) {
            return false;
         } else {
            boolean hasstack2 = false;
            if (!this.input2.isEmpty()) {
               for (OreCost need : this.input2) {
                  for (int ixx = 0; ixx < 3; ixx++) {
                     ItemStack has = tile.getStackInSlot(ixx);
                     if (this.allowed(has, need)) {
                        hasstack2 = true;
                        break;
                     }
                  }
               }
            }

            if (!hasstack2) {
               return false;
            } else {
               boolean hasstack3 = false;
               if (!this.input3.isEmpty()) {
                  for (OreCost need : this.input3) {
                     for (int ixxx = 0; ixxx < 3; ixxx++) {
                        ItemStack has = tile.getStackInSlot(ixxx);
                        if (this.allowed(has, need)) {
                           hasstack3 = true;
                           break;
                        }
                     }
                  }
               }

               return hasstack3;
            }
         }
      }
   }

   public void spendItems(TilePyrocrystallineConverter tile) {
      if (!this.input1.isEmpty()) {
         for (OreCost need : this.input1) {
            boolean breakk = false;

            for (int i = 0; i < 3; i++) {
               ItemStack has = tile.getStackInSlot(i);
               if (this.allowed(has, need)) {
                  tile.decrStackSize(i, need.count);
                  breakk = true;
                  break;
               }
            }

            if (breakk) {
               break;
            }
         }
      }

      if (!this.input2.isEmpty()) {
         for (OreCost need : this.input2) {
            boolean breakk = false;

            for (int ix = 0; ix < 3; ix++) {
               ItemStack has = tile.getStackInSlot(ix);
               if (this.allowed(has, need)) {
                  tile.decrStackSize(ix, need.count);
                  breakk = true;
                  break;
               }
            }

            if (breakk) {
               break;
            }
         }
      }

      if (!this.input3.isEmpty()) {
         for (OreCost need : this.input3) {
            boolean breakk = false;

            for (int ixx = 0; ixx < 3; ixx++) {
               ItemStack has = tile.getStackInSlot(ixx);
               if (this.allowed(has, need)) {
                  tile.decrStackSize(ixx, need.count);
                  breakk = true;
                  break;
               }
            }

            if (breakk) {
               break;
            }
         }
      }
   }

   public boolean allowed(ItemStack have, OreCost need) {
      if (have.getCount() < need.count) {
         return false;
      } else {
         NonNullList<ItemStack> ores = OreDictionary.getOres(need.name);
         if (ores != OreDictionary.EMPTY_LIST) {
            for (ItemStack stack : ores) {
               if (stack.getItem() == have.getItem()) {
                  return true;
               }
            }
         }

         return have.getItem() == Item.getByNameOrId(need.name);
      }
   }

   public static class OreCost {
      public String name;
      public int count;

      public OreCost(String name, int count) {
         this.name = name;
         this.count = count;
      }

      public OreCost(Item toname, int count) {
         this.name = toname.getRegistryName().toString();
         this.count = count;
      }

      public OreCost(Block toname, int count) {
         this.name = toname.getRegistryName().toString();
         this.count = count;
      }
   }
}
