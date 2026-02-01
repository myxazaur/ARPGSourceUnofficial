package com.vivern.arpg.recipes;

import com.vivern.arpg.tileentity.TileIndustrialMixer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class IndustrialMixerRecipe {
   public NonNullList<Ingridient> recipe;
   public NonNullList<Ingridient> output;
   public int rfToAll;
   public String costfluidname1 = null;
   public String costfluidname2 = null;
   public String existfluidname1 = null;
   public String existfluidname2 = null;
   public int amountcost1 = 0;
   public int amountcost2 = 0;
   public int amountexist1 = 0;
   public int amountexist2 = 0;
   public int ticksToBrew;
   public Item catalyst1;
   public Item catalyst2;

   public IndustrialMixerRecipe(@Nullable Item catalyst1, @Nullable Item catalyst2, int rfToAll, int ticksToBrew, Ingridient[] recipe, Ingridient[] output) {
      this.rfToAll = rfToAll;
      this.recipe = NonNullList.from(Ingridient.EMPTY, recipe);
      this.output = NonNullList.from(Ingridient.EMPTY, output);
      this.ticksToBrew = ticksToBrew;
      this.catalyst1 = catalyst1;
      this.catalyst2 = catalyst2;
   }

   public IndustrialMixerRecipe setFluidCost1(String fluid, int amount) {
      if ("tile.water".equals(fluid)) {
         fluid = "water";
      }

      this.costfluidname1 = fluid;
      this.amountcost1 = amount;
      return this;
   }

   public IndustrialMixerRecipe setFluidCost2(String fluid, int amount) {
      if ("tile.water".equals(fluid)) {
         fluid = "water";
      }

      this.costfluidname2 = fluid;
      this.amountcost2 = amount;
      return this;
   }

   public IndustrialMixerRecipe setFluidOutput1(String fluid, int amount) {
      if ("tile.water".equals(fluid)) {
         fluid = "water";
      }

      this.existfluidname1 = fluid;
      this.amountexist1 = amount;
      return this;
   }

   public IndustrialMixerRecipe setFluidOutput2(String fluid, int amount) {
      if ("tile.water".equals(fluid)) {
         fluid = "water";
      }

      this.existfluidname2 = fluid;
      this.amountexist2 = amount;
      return this;
   }

   public boolean isAllowForCraft(TileIndustrialMixer lab) {
      if (this.catalyst1 != null) {
         ItemStack catalIn = lab.getStackInSlot(7);
         if (catalIn.isEmpty() || catalIn.getItem() != this.catalyst1) {
            return false;
         }
      }

      if (this.catalyst2 != null) {
         ItemStack catalIn = lab.getStackInSlot(6);
         if (catalIn.isEmpty() || catalIn.getItem() != this.catalyst2) {
            return false;
         }
      }

      FluidStack fluidcost1 = null;
      if (this.costfluidname1 != null) {
         Fluid fluid = FluidRegistry.getFluid(this.costfluidname1);
         if (fluid == null) {
            return false;
         }

         fluidcost1 = new FluidStack(fluid, this.amountcost1);
      }

      if (fluidcost1 == null
         || lab.tank1.getFluid() != null && lab.tank1.getFluid().containsFluid(fluidcost1)
         || lab.tank2.getFluid() != null && lab.tank2.getFluid().containsFluid(fluidcost1)) {
         FluidStack fluidcost2 = null;
         if (this.costfluidname2 != null) {
            Fluid fluid = FluidRegistry.getFluid(this.costfluidname2);
            if (fluid == null) {
               return false;
            }

            fluidcost2 = new FluidStack(fluid, this.amountcost2);
         }

         if (fluidcost2 == null
            || lab.tank1.getFluid() != null && lab.tank1.getFluid().containsFluid(fluidcost2)
            || lab.tank2.getFluid() != null && lab.tank2.getFluid().containsFluid(fluidcost2)) {
            FluidStack fluidexist1 = null;
            if (this.existfluidname1 != null) {
               Fluid fluid = FluidRegistry.getFluid(this.existfluidname1);
               if (fluid == null) {
                  return false;
               }

               fluidexist1 = new FluidStack(fluid, this.amountexist1);
            }

            if (fluidexist1 != null) {
               boolean succ = false;
               boolean isSameFluidInTank3 = lab.tank3.getFluid() == null || lab.tank3.getFluidAmount() == 0 || lab.tank3.getFluid().isFluidEqual(fluidexist1);
               boolean isSameFluidInTank4 = lab.tank4.getFluid() == null || lab.tank4.getFluidAmount() == 0 || lab.tank4.getFluid().isFluidEqual(fluidexist1);
               if (!isSameFluidInTank3 && !isSameFluidInTank4) {
                  return false;
               }

               if (isSameFluidInTank3) {
                  if (TileIndustrialMixer.fluidMax < lab.tank3.getFluidAmount() + fluidexist1.amount) {
                     return false;
                  }

                  succ = true;
               }

               if (isSameFluidInTank4 && !succ && TileIndustrialMixer.fluidMax < lab.tank4.getFluidAmount() + fluidexist1.amount) {
                  return false;
               }
            }

            FluidStack fluidexist2 = null;
            if (this.existfluidname2 != null) {
               Fluid fluid = FluidRegistry.getFluid(this.existfluidname2);
               if (fluid == null) {
                  return false;
               }

               fluidexist2 = new FluidStack(fluid, this.amountexist2);
            }

            if (fluidexist2 != null) {
               boolean succx = false;
               boolean isSameFluidInTank3x = lab.tank3.getFluid() == null || lab.tank3.getFluidAmount() == 0 || lab.tank3.getFluid().isFluidEqual(fluidexist2);
               boolean isSameFluidInTank4x = lab.tank4.getFluid() == null || lab.tank4.getFluidAmount() == 0 || lab.tank4.getFluid().isFluidEqual(fluidexist2);
               if (!isSameFluidInTank3x && !isSameFluidInTank4x) {
                  return false;
               }

               if (isSameFluidInTank3x) {
                  if (TileIndustrialMixer.fluidMax < lab.tank3.getFluidAmount() + fluidexist2.amount) {
                     return false;
                  }

                  succx = true;
               }

               if (isSameFluidInTank4x && !succx && TileIndustrialMixer.fluidMax < lab.tank4.getFluidAmount() + fluidexist2.amount) {
                  return false;
               }
            }

            int countInTile = 0;

            for (int i = 0; i < 3; i++) {
               if (!lab.getStackInSlot(i).isEmpty()) {
                  countInTile++;
               }
            }

            if (countInTile != this.recipe.size()) {
               return false;
            } else {
               for (Ingridient need : this.recipe) {
                  boolean hasThisStack = false;

                  for (int ix = 0; ix < 3; ix++) {
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
      } else {
         return false;
      }
   }

   public boolean tryCraft(TileIndustrialMixer lab) {
      if (this.catalyst1 != null) {
         ItemStack catalIn = lab.getStackInSlot(7);
         if (catalIn.isEmpty() || catalIn.getItem() != this.catalyst1) {
            return false;
         }
      }

      if (this.catalyst2 != null) {
         ItemStack catalIn = lab.getStackInSlot(6);
         if (catalIn.isEmpty() || catalIn.getItem() != this.catalyst2) {
            return false;
         }
      }

      FluidStack fluidcost1 = null;
      boolean cost1Intank2 = false;
      if (this.costfluidname1 != null) {
         Fluid fluid = FluidRegistry.getFluid(this.costfluidname1);
         if (fluid == null) {
            return false;
         }

         fluidcost1 = new FluidStack(fluid, this.amountcost1);
      }

      if (fluidcost1 != null) {
         boolean notank1 = lab.tank1.getFluid() == null || !lab.tank1.getFluid().containsFluid(fluidcost1);
         boolean notank2 = lab.tank2.getFluid() == null || !lab.tank2.getFluid().containsFluid(fluidcost1);
         if (notank1 && notank2) {
            return false;
         }

         cost1Intank2 = notank1;
      }

      FluidStack fluidcost2 = null;
      boolean cost2Intank2 = false;
      if (this.costfluidname2 != null) {
         Fluid fluid = FluidRegistry.getFluid(this.costfluidname2);
         if (fluid == null) {
            return false;
         }

         fluidcost2 = new FluidStack(fluid, this.amountcost2);
      }

      if (fluidcost2 != null) {
         boolean notank1 = lab.tank1.getFluid() == null || !lab.tank1.getFluid().containsFluid(fluidcost2);
         boolean notank2 = lab.tank2.getFluid() == null || !lab.tank2.getFluid().containsFluid(fluidcost2);
         if (notank1 && notank2) {
            return false;
         }

         cost2Intank2 = notank1;
      }

      FluidStack fluidexist1 = null;
      boolean exist1Intank3 = false;
      if (this.existfluidname1 != null) {
         Fluid fluid = FluidRegistry.getFluid(this.existfluidname1);
         if (fluid == null) {
            return false;
         }

         fluidexist1 = new FluidStack(fluid, this.amountexist1);
      }

      if (fluidexist1 != null) {
         boolean succ = false;
         boolean isSameFluidInTank3 = lab.tank3.getFluid() == null || lab.tank3.getFluidAmount() == 0 || lab.tank3.getFluid().isFluidEqual(fluidexist1);
         boolean isSameFluidInTank4 = lab.tank4.getFluid() == null || lab.tank4.getFluidAmount() == 0 || lab.tank4.getFluid().isFluidEqual(fluidexist1);
         if (!isSameFluidInTank3 && !isSameFluidInTank4) {
            return false;
         }

         if (isSameFluidInTank3) {
            if (TileIndustrialMixer.fluidMax < lab.tank3.getFluidAmount() + fluidexist1.amount) {
               return false;
            }

            succ = true;
            exist1Intank3 = true;
         }

         if (isSameFluidInTank4 && !succ) {
            if (TileIndustrialMixer.fluidMax < lab.tank4.getFluidAmount() + fluidexist1.amount) {
               return false;
            }

            exist1Intank3 = false;
         }
      }

      FluidStack fluidexist2 = null;
      boolean exist2Intank3 = false;
      if (this.existfluidname2 != null) {
         Fluid fluid = FluidRegistry.getFluid(this.existfluidname2);
         if (fluid == null) {
            return false;
         }

         fluidexist2 = new FluidStack(fluid, this.amountexist2);
      }

      if (fluidexist2 != null) {
         boolean succx = false;
         boolean isSameFluidInTank3x = lab.tank3.getFluid() == null || lab.tank3.getFluidAmount() == 0 || lab.tank3.getFluid().isFluidEqual(fluidexist2);
         boolean isSameFluidInTank4x = lab.tank4.getFluid() == null || lab.tank4.getFluidAmount() == 0 || lab.tank4.getFluid().isFluidEqual(fluidexist2);
         if (!isSameFluidInTank3x && !isSameFluidInTank4x) {
            return false;
         }

         if (isSameFluidInTank4x) {
            if (TileIndustrialMixer.fluidMax < lab.tank4.getFluidAmount() + fluidexist2.amount) {
               return false;
            }

            succx = true;
            exist2Intank3 = false;
         }

         if (isSameFluidInTank3x && !succx) {
            if (TileIndustrialMixer.fluidMax < lab.tank3.getFluidAmount() + fluidexist2.amount) {
               return false;
            }

            exist2Intank3 = true;
         }
      }

      int countInTile = 0;

      for (int i = 0; i < 3; i++) {
         if (!lab.getStackInSlot(i).isEmpty()) {
            countInTile++;
         }
      }

      if (countInTile != this.recipe.size()) {
         return false;
      } else {
         for (Ingridient need : this.recipe) {
            boolean hasThisStack = false;

            for (int ix = 0; ix < 3; ix++) {
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

         if (fluidcost1 != null) {
            if (cost1Intank2) {
               lab.tank2.drain(fluidcost1.copy(), true);
            } else {
               lab.tank1.drain(fluidcost1.copy(), true);
            }
         }

         if (fluidcost2 != null) {
            if (cost2Intank2) {
               lab.tank2.drain(fluidcost2.copy(), true);
            } else {
               lab.tank1.drain(fluidcost2.copy(), true);
            }
         }

         if (fluidexist1 != null) {
            if (exist1Intank3) {
               lab.tank3.fill(fluidexist1.copy(), true);
            } else {
               lab.tank4.fill(fluidexist1.copy(), true);
            }
         }

         if (fluidexist2 != null) {
            if (exist2Intank3) {
               lab.tank3.fill(fluidexist2.copy(), true);
            } else {
               lab.tank4.fill(fluidexist2.copy(), true);
            }
         }

         lab.getStackInSlot(7).attemptDamageItem(1, TileIndustrialMixer.rand, null);
         if (lab.getStackInSlot(7).getItemDamage() > lab.getStackInSlot(7).getMaxDamage()) {
            lab.setInventorySlotContents(7, ItemStack.EMPTY);
         }

         lab.getStackInSlot(6).attemptDamageItem(1, TileIndustrialMixer.rand, null);
         if (lab.getStackInSlot(6).getItemDamage() > lab.getStackInSlot(6).getMaxDamage()) {
            lab.setInventorySlotContents(6, ItemStack.EMPTY);
         }

         return true;
      }
   }

   public boolean allowed(ItemStack have, Ingridient need) {
      return need.isStackAllowed(have);
   }

   public int trySetTo(ItemStack stack, TileIndustrialMixer lab) {
      int stlimit = lab.getInventoryStackLimit();

      for (int i = 3; i < 6; i++) {
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

   public int findStackIndex(Ingridient ingridient, TileIndustrialMixer lab) {
      for (int i = 0; i < 3; i++) {
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
