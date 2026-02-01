package com.vivern.arpg.recipes;

import com.vivern.arpg.tileentity.TileAssemblyTable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class AssemblyTableRecipe {
   public final Ingridient craftresult;
   public final Ingridient craftresult2;
   public final boolean useEnergy;
   public NonNullList<Ingridient> recipe;
   public List<AugmentCost> augmentsRecipe;
   public int timeToCraft;
   public int RFToTick;

   public AssemblyTableRecipe(boolean useEnergy, Ingridient[] slots, Ingridient craftresult, Ingridient craftresult2, int timeToCraft, int RFtoAll) {
      this.useEnergy = useEnergy;
      this.recipe = NonNullList.from(Ingridient.EMPTY, slots);
      this.craftresult = craftresult;
      this.craftresult2 = craftresult2;
      this.timeToCraft = timeToCraft;
      this.RFToTick = RFtoAll / timeToCraft;
   }

   public AssemblyTableRecipe setAugmentsRecipe(AugmentCost... costs) {
      this.augmentsRecipe = Arrays.asList(costs);
      return this;
   }

   public AssemblyTableRecipe setAugmentsRecipe(List<AugmentCost> costs) {
      this.augmentsRecipe = costs;
      return this;
   }

   public boolean tryCraft(TileAssemblyTable tile) {
      if (this.useEnergy && tile.electricStorage.extractEnergy(this.RFToTick, true) < this.RFToTick) {
         return false;
      } else {
         boolean[] slotsUsed = new boolean[]{false, false, false, false, false};
         int augRecCount = this.augmentsRecipe == null ? 0 : this.augmentsRecipe.size();
         if (augRecCount > 0) {
            for (int ac = 0; ac < augRecCount; ac++) {
               AugmentCost need = this.augmentsRecipe.get(ac);

               for (int sl = 0; sl < 5; sl++) {
                  ItemStack has = tile.getStackInSlot(9 + sl);
                  if (!slotsUsed[sl] && this.allowed(need, has, tile, sl)) {
                     slotsUsed[sl] = true;
                     break;
                  }

                  if (sl == 4) {
                     return false;
                  }
               }
            }
         }

         NonNullList<ItemStack> stacks = tile.stacks;

         for (int i = 0; i < 9; i++) {
            ItemStack have = (ItemStack)stacks.get(i);
            if (!((Ingridient)this.recipe.get(i)).isStackAllowed(have)) {
               return false;
            }
         }

         if (!tile.getStackInSlot(14).isEmpty()) {
            ItemStack craftresultStack = this.craftresult.createStack();
            if (!this.canOutput(tile.getStackInSlot(14), craftresultStack, tile.getInventoryStackLimit())) {
               return false;
            }
         }

         if (!tile.getStackInSlot(15).isEmpty() && this.craftresult2 != Ingridient.EMPTY) {
            ItemStack craftresultStack2 = this.craftresult2.createStack();
            if (!this.canOutput(tile.getStackInSlot(15), craftresultStack2, tile.getInventoryStackLimit())) {
               return false;
            }
         }

         if (tile.progress < this.timeToCraft) {
            tile.slotsUsed = slotsUsed;
            tile.currentMaxCraftTime = this.timeToCraft;
            if (this.useEnergy) {
               tile.currentRFtotick = this.RFToTick;
            }

            if (this.augmentsRecipe != null && !this.augmentsRecipe.isEmpty()) {
               TileAssemblyTable.AugmentInfo recipeNeeds = new TileAssemblyTable.AugmentInfo();

               for (AugmentCost cost : this.augmentsRecipe) {
                  recipeNeeds.addById(cost.augmentID, 1);
               }

               tile.setAugmentsTilesToWork(recipeNeeds, this.timeToCraft);
            }

            return true;
         } else {
            for (int ix = 0; ix < 9; ix++) {
               ItemStack have = (ItemStack)stacks.get(ix);
               Ingridient need = (Ingridient)this.recipe.get(ix);
               int size = have.getCount() - need.getCount();
               ItemStack result = size > 0 ? new ItemStack(have.getItem(), size, have.getMetadata(), have.getTagCompound()) : ItemStack.EMPTY;
               tile.stacks.set(ix, result);
            }

            int size = tile.getStackInSlot(14).getCount() + this.craftresult.getCount();
            ItemStack result = size > 0 ? this.craftresult.createStack() : ItemStack.EMPTY;
            result.setCount(size);
            tile.stacks.set(14, result);
            size = tile.getStackInSlot(15).getCount() + this.craftresult2.getCount();
            result = size > 0 ? this.craftresult2.createStack() : ItemStack.EMPTY;
            result.setCount(size);
            tile.stacks.set(15, result);
            boolean[] slotsUsed2 = new boolean[]{false, false, false, false, false};
            if (augRecCount > 0) {
               for (int ac = 0; ac < augRecCount; ac++) {
                  AugmentCost need = this.augmentsRecipe.get(ac);

                  for (int sl = 0; sl < 5; sl++) {
                     ItemStack hasx = tile.getStackInSlot(sl + 9);
                     if (!slotsUsed2[sl] && this.allowed(need, hasx, tile, sl)) {
                        tile.decrStackSize(sl + 9, need.ingridient.getCount());
                        slotsUsed2[sl] = true;
                        break;
                     }
                  }
               }
            }

            return true;
         }
      }
   }

   public boolean canContinueCrafting(TileAssemblyTable tile) {
      if (this.useEnergy && tile.electricStorage.extractEnergy(this.RFToTick, true) < this.RFToTick) {
         return false;
      } else if (!tile.getStackInSlot(14).isEmpty() && tile.getStackInSlot(14).getCount() + this.craftresult.getCount() > tile.getInventoryStackLimit()) {
         return false;
      } else if (!tile.getStackInSlot(15).isEmpty()
         && this.craftresult2 != Ingridient.EMPTY
         && tile.getStackInSlot(15).getCount() + this.craftresult2.getCount() > tile.getInventoryStackLimit()) {
         return false;
      } else {
         boolean[] slotsUsed = new boolean[]{false, false, false, false, false};
         int augRecCount = this.augmentsRecipe == null ? 0 : this.augmentsRecipe.size();
         if (augRecCount > 0) {
            for (int ac = 0; ac < augRecCount; ac++) {
               AugmentCost need = this.augmentsRecipe.get(ac);

               for (int sl = 0; sl < 5; sl++) {
                  ItemStack has = tile.getStackInSlot(9 + sl);
                  if (!slotsUsed[sl] && this.allowed(need, has, tile, sl)) {
                     slotsUsed[sl] = true;
                     break;
                  }

                  if (sl == 4) {
                     return false;
                  }
               }
            }
         }

         NonNullList<ItemStack> stacks = tile.stacks;

         for (int i = 0; i < 9; i++) {
            ItemStack have = (ItemStack)stacks.get(i);
            if (!((Ingridient)this.recipe.get(i)).isStackAllowed(have)) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean allowed(AugmentCost need, ItemStack have, TileAssemblyTable tile, int augmentSlot) {
      if (augmentSlot == 0) {
         if (need.augmentID != tile.augmIDslot9) {
            return false;
         }
      } else if (augmentSlot == 1) {
         if (need.augmentID != tile.augmIDslot10) {
            return false;
         }
      } else if (augmentSlot == 2) {
         if (need.augmentID != tile.augmIDslot11) {
            return false;
         }
      } else if (augmentSlot == 3) {
         if (need.augmentID != tile.augmIDslot12) {
            return false;
         }
      } else if (augmentSlot == 4 && need.augmentID != tile.augmIDslot13) {
         return false;
      }

      return have == null || need == null || need.ingridient == null ? false : need.ingridient.isStackAllowed(have);
   }

   public boolean canOutput(ItemStack st1, ItemStack st2, int invStackLimit) {
      int absLimit = Math.min(invStackLimit, Math.min(st1.getMaxStackSize(), st2.getMaxStackSize()));
      if (st1.getCount() + st2.getCount() > absLimit) {
         return false;
      } else if (st1.getItem() != st2.getItem()) {
         return false;
      } else if (st1.getMetadata() != st2.getMetadata()) {
         return false;
      } else if (st1.getItemDamage() != st2.getItemDamage()) {
         return false;
      } else {
         boolean b = st1.hasTagCompound();
         return b != st2.hasTagCompound() ? false : !b || st1.getTagCompound().equals(st2.getTagCompound());
      }
   }

   public AugmentCost getAugmentCost(int idInArray) {
      return idInArray < this.augmentsRecipe.size() ? this.augmentsRecipe.get(idInArray) : AugmentCost.EMPTY;
   }

   public List<ItemStack> exportInputsAsList() {
      List<ItemStack> list = new ArrayList<>();

      for (int i = 0; i < this.recipe.size(); i++) {
         if (this.recipe.get(i) != Ingridient.EMPTY) {
            list.add(((Ingridient)this.recipe.get(i)).createStack());
         }
      }

      for (int ix = 0; ix < this.augmentsRecipe.size(); ix++) {
         if (this.augmentsRecipe.get(ix) != null && this.augmentsRecipe.get(ix).ingridient != Ingridient.EMPTY) {
            list.add(this.augmentsRecipe.get(ix).ingridient.createStack());
         }
      }

      return list;
   }

   public static class AugmentCost {
      public int augmentID;
      public Ingridient ingridient;
      public static AugmentCost EMPTY = new AugmentCost(0, Ingridient.EMPTY);

      public AugmentCost(int augmentID, Ingridient ingridient) {
         this.augmentID = augmentID;
         this.ingridient = ingridient;
      }
   }
}
