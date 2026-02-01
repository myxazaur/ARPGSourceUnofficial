package com.vivern.arpg.recipes;

import com.vivern.arpg.tileentity.TileAlchemicLab;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class AlchemicLabRecipe {
   public NonNullList<Ingridient> recipe;
   public NonNullList<Ingridient> output;
   public float manacost;
   public String costfluidname = null;
   public String existfluidname = null;
   public int amountcost = 0;
   public int amountexist = 0;
   public int ticksToBrew;
   public float shardOutput;

   public AlchemicLabRecipe(float manacost, int ticksToBrew, Ingridient[] recipe, Ingridient[] output) {
      this.manacost = manacost;
      this.recipe = NonNullList.from(Ingridient.EMPTY, recipe);
      this.output = NonNullList.from(Ingridient.EMPTY, output);
      this.ticksToBrew = ticksToBrew;
   }

   public AlchemicLabRecipe setFluidCost(String fluid, int amount) {
      if ("tile.water".equals(fluid)) {
         fluid = "water";
      }

      this.costfluidname = fluid;
      this.amountcost = amount;
      return this;
   }

   public AlchemicLabRecipe setFluidOutput(String fluid, int amount) {
      if ("tile.water".equals(fluid)) {
         fluid = "water";
      }

      this.existfluidname = fluid;
      this.amountexist = amount;
      return this;
   }

   public boolean isAllowForCraft(TileAlchemicLab lab) {
      FluidStack fluidcost = null;
      if (this.costfluidname != null) {
         Fluid fluid = FluidRegistry.getFluid(this.costfluidname);
         if (fluid == null) {
            return false;
         }

         fluidcost = new FluidStack(fluid, this.amountcost);
      }

      if (fluidcost == null || lab.tank1.getFluid() != null && lab.tank1.getFluid().containsFluid(fluidcost)) {
         FluidStack fluidexist = null;
         if (this.existfluidname != null) {
            Fluid fluid = FluidRegistry.getFluid(this.existfluidname);
            if (fluid == null) {
               return false;
            }

            fluidexist = new FluidStack(fluid, this.amountexist);
         }

         if (fluidexist != null && lab.tank2.getFluid() != null) {
            if (!lab.tank2.getFluid().isFluidEqual(fluidexist)) {
               return false;
            }

            if (TileAlchemicLab.fluidMax < lab.tank2.getFluid().amount + fluidexist.amount) {
               return false;
            }
         }

         int countInTile = 0;

         for (int i = 0; i < 6; i++) {
            if (!lab.getStackInSlot(i).isEmpty()) {
               countInTile++;
            }
         }

         if (countInTile != this.recipe.size()) {
            return false;
         } else {
            for (Ingridient need : this.recipe) {
               boolean hasThisStack = false;

               for (int ix = 0; ix < 6; ix++) {
                  ItemStack has = lab.getStackInSlot(ix);
                  if (this.allowed(has, need)) {
                     if (hasThisStack) {
                        return false;
                     }

                     hasThisStack = true;
                  }
               }

               if (!hasThisStack) {
                  return false;
               }
            }

            for (Ingridient out : this.output) {
               int slottoset = this.trySetTo(out.createStack(), lab);
               if (slottoset == -1) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean tryCraft(TileAlchemicLab lab) {
      FluidStack fluidcost = null;
      if (this.costfluidname != null) {
         Fluid fluid = FluidRegistry.getFluid(this.costfluidname);
         if (fluid == null) {
            return false;
         }

         fluidcost = new FluidStack(fluid, this.amountcost);
      }

      if (fluidcost == null || lab.tank1.getFluid() != null && lab.tank1.getFluid().containsFluid(fluidcost)) {
         FluidStack fluidexist = null;
         if (this.existfluidname != null) {
            Fluid fluid = FluidRegistry.getFluid(this.existfluidname);
            if (fluid == null) {
               return false;
            }

            fluidexist = new FluidStack(fluid, this.amountexist);
         }

         if (fluidexist != null && lab.tank2.getFluid() != null) {
            if (!lab.tank2.getFluid().isFluidEqual(fluidexist)) {
               return false;
            }

            if (TileAlchemicLab.fluidMax < lab.tank2.getFluid().amount + fluidexist.amount) {
               return false;
            }
         }

         int countInTile = 0;

         for (int i = 0; i < 6; i++) {
            if (!lab.getStackInSlot(i).isEmpty()) {
               countInTile++;
            }
         }

         if (countInTile != this.recipe.size()) {
            return false;
         } else {
            for (Ingridient need : this.recipe) {
               boolean hasThisStack = false;

               for (int ix = 0; ix < 6; ix++) {
                  ItemStack has = lab.getStackInSlot(ix);
                  if (this.allowed(has, need)) {
                     if (hasThisStack) {
                        return false;
                     }

                     hasThisStack = true;
                  }
               }

               if (!hasThisStack) {
                  return false;
               }
            }

            for (Ingridient out : this.output) {
               int slottoset = this.trySetTo(out.createStack(), lab);
               if (slottoset == -1) {
                  return false;
               }
            }

            for (Ingridient outx : this.output) {
               ItemStack newoutstack = outx.createStack();
               int slottoset = this.trySetTo(newoutstack, lab);
               if (slottoset != -1) {
                  ItemStack have = lab.getStackInSlot(slottoset);
                  ItemStack result = have.isEmpty()
                     ? newoutstack
                     : new ItemStack(have.getItem(), have.getCount() + outx.getCount(), have.getMetadata(), have.getTagCompound());
                  lab.setInventorySlotContents(slottoset, result);
               }
            }

            for (Ingridient need : this.recipe) {
               int indx = this.findStackIndex(need, lab);
               if (indx != -1) {
                  ItemStack have = lab.getStackInSlot(indx);
                  int size = have.getCount() - need.getCount();
                  ItemStack result = size > 0 ? new ItemStack(have.getItem(), size, have.getMetadata(), have.getTagCompound()) : ItemStack.EMPTY;
                  lab.setInventorySlotContents(indx, result);
               }
            }

            if (fluidcost != null) {
               lab.tank1.drain(fluidcost.copy(), true);
            }

            if (fluidexist != null) {
               lab.tank2.fill(fluidexist.copy(), true);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean allowed(ItemStack have, Ingridient need) {
      return need.isStackAllowed(have);
   }

   public int trySetTo(ItemStack stack, TileAlchemicLab lab) {
      int stlimit = lab.getInventoryStackLimit();

      for (int i = 6; i < 12; i++) {
         ItemStack have = lab.getStackInSlot(i);
         if (have.isEmpty()) {
            return i;
         }

         if (this.isItemEquals(stack, have) && have.getCount() + stack.getCount() <= stlimit) {
            return i;
         }
      }

      return -1;
   }

   public int findStackIndex(Ingridient ingridient, TileAlchemicLab lab) {
      for (int i = 0; i < 6; i++) {
         ItemStack have = lab.getStackInSlot(i);
         if (!have.isEmpty() && ingridient.isStackAllowed(have)) {
            return i;
         }
      }

      return -1;
   }

   public boolean isItemEquals(ItemStack stack, ItemStack other) {
      boolean hastag = stack.hasTagCompound() == other.hasTagCompound();
      boolean eqtag = true;
      if (stack.hasTagCompound() && hastag) {
         eqtag = stack.getTagCompound().equals(other.getTagCompound());
      }

      return !other.isEmpty() && stack.getItem() == other.getItem() && stack.getItemDamage() == other.getItemDamage() && hastag && eqtag;
   }

   public List<ItemStack> exportInputsAsList() {
      List<ItemStack> list = new ArrayList<>();

      for (int i = 0; i < this.recipe.size(); i++) {
         if (this.recipe.get(i) != Ingridient.EMPTY) {
            list.add(((Ingridient)this.recipe.get(i)).createStack());
         }
      }

      return list;
   }

   public List<ItemStack> exportOutputAsList() {
      List<ItemStack> list = new ArrayList<>();

      for (int i = 0; i < this.output.size(); i++) {
         if (this.output.get(i) != Ingridient.EMPTY) {
            list.add(((Ingridient)this.output.get(i)).createStack());
         }
      }

      return list;
   }
}
