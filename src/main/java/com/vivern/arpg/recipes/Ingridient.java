package com.vivern.arpg.recipes;

import com.vivern.arpg.elements.GemStaff;
import com.vivern.arpg.elements.SoulStone;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.OreDicHelper;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;

public abstract class Ingridient {
   public static Ingridient EMPTY = new Ingridient() {
      @Override
      public boolean isStackAllowed(ItemStack itemStack) {
         return itemStack.isEmpty();
      }

      @Override
      public ItemStack createStack() {
         return ItemStack.EMPTY;
      }

      @Override
      public int getCount() {
         return 0;
      }

      @Override
      public String toString() {
         return "EM";
      }
   };

   public abstract boolean isStackAllowed(ItemStack var1);

   public abstract ItemStack createStack();

   public abstract int getCount();

   public ItemStack createStackForJeiInput() {
      return this.createStack();
   }

   public static Ingridient getIngridient(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         Item item = itemStack.getItem();
         int count = itemStack.getCount();
         if (item == ItemsRegister.SLIMEPLASTIC) {
            return new IngridientItem(OreDicHelper.PLASTIC, count);
         } else if (item == ItemsRegister.PLASTIC) {
            return new IngridientItem(OreDicHelper.PLASTICADVANCED, count);
         } else if (item == ItemsRegister.RUBBER) {
            return new IngridientItem(OreDicHelper.RUBBER2, count);
         } else if (item == ItemsRegister.SOULSTONE) {
            return new IngridientSoulStone(SoulStone.getSoul(itemStack));
         } else if (item == ItemsRegister.GEMSTAFF) {
            return new IngridientGemStaff(NBTHelper.GetNBTint(itemStack, "type"));
         } else {
            List<String> orenames = OreDicHelper.getOreNames(itemStack);
            if (!orenames.isEmpty()) {
               boolean isDye = false;

               for (String oren : orenames) {
                  if ("dye".equals(oren)) {
                     isDye = true;
                     break;
                  }
               }

               if (isDye) {
                  for (String orenx : orenames) {
                     if (orenx.contains("dye") && orenx.length() > 3) {
                        return new IngridientItem(orenx, count);
                     }
                  }
               }

               return new IngridientItem(orenames.get(0), count);
            } else {
               return itemStack.getHasSubtypes()
                  ? new IngridientItem(item, count, itemStack.getMetadata(), true)
                  : new IngridientItem(item, count, 0, false);
            }
         }
      } else {
         return EMPTY;
      }
   }

   public static class IngridientFluidBucket extends Ingridient {
      public String fluidName;
      public int mbAmount;

      public IngridientFluidBucket(String fluidName, int mbAmount) {
         this.fluidName = fluidName;
         this.mbAmount = mbAmount;
      }

      public IngridientFluidBucket(String fluidName) {
         this.fluidName = fluidName;
         this.mbAmount = 1000;
      }

      @Override
      public boolean isStackAllowed(ItemStack itemStack) {
         if (!(itemStack.getItem() instanceof UniversalBucket)) {
            return false;
         } else {
            FluidStack fluidStack = FluidUtil.getFluidContained(itemStack);
            return fluidStack.amount >= this.mbAmount && this.fluidName.equals(fluidStack.getFluid().getName());
         }
      }

      @Override
      public ItemStack createStack() {
         Fluid fluid = FluidRegistry.getFluid(this.fluidName);
         return fluid == null ? new ItemStack(Items.BUCKET) : FluidUtil.getFilledBucket(new FluidStack(fluid, this.mbAmount));
      }

      @Override
      public int getCount() {
         return 1;
      }

      @Override
      public String toString() {
         return this.mbAmount == 1000
            ? "new IngridientFluidBucket(\"" + this.fluidName + "\")"
            : "new IngridientFluidBucket(\"" + this.fluidName + "\", " + this.mbAmount + ")";
      }
   }

   public static class IngridientGemStaff extends Ingridient {
      public int gem;

      public IngridientGemStaff(int gem) {
         this.gem = gem;
      }

      @Override
      public boolean isStackAllowed(ItemStack itemStack) {
         return itemStack.getItem() == ItemsRegister.GEMSTAFF && NBTHelper.GetNBTint(itemStack, "type") == this.gem;
      }

      @Override
      public ItemStack createStack() {
         return GemStaff.getStackWithGem(this.gem);
      }

      @Override
      public int getCount() {
         return 1;
      }

      @Override
      public String toString() {
         return "new IngridientGemStaff(" + this.gem + ")";
      }
   }

   public static class IngridientItem extends Ingridient {
      public Item item;
      public int amount;
      public int meta;
      public boolean useMeta;
      @Nullable
      public String oreDictionaryName;
      @Nullable
      public String missingItemName;
      @Nullable
      public NBTTagCompound stackTagCompound;

      public IngridientItem(Item item, int amount, int meta, boolean useMeta) {
         this.item = item;
         this.amount = amount;
         this.meta = meta;
         this.useMeta = useMeta;
      }

      public IngridientItem(String itemName, int amount, int meta, boolean useMeta) {
         this.item = Item.getByNameOrId(itemName);
         if (this.item == null) {
            this.missingItemName = itemName;
         }

         this.amount = amount;
         this.meta = meta;
         this.useMeta = useMeta;
      }

      public IngridientItem(String oreDictionaryName, int amount) {
         this.amount = amount;
         this.oreDictionaryName = oreDictionaryName;
      }

      public IngridientItem setNbt(NBTTagCompound stackTagCompound) {
         this.stackTagCompound = stackTagCompound;
         return this;
      }

      @Override
      public boolean isStackAllowed(ItemStack itemStack) {
         if (this.missingItemName != null) {
            return false;
         } else if (this.stackTagCompound != null && !this.stackTagCompound.equals(itemStack.getTagCompound())) {
            return false;
         } else if (this.oreDictionaryName != null) {
            return itemStack.getCount() >= this.amount && OreDicHelper.itemStringOredigMatches(itemStack, this.oreDictionaryName);
         } else {
            return this.useMeta
               ? itemStack.getCount() >= this.amount && itemStack.getItem() == this.item && itemStack.getMetadata() == this.meta
               : itemStack.getCount() >= this.amount && itemStack.getItem() == this.item;
         }
      }

      @Override
      public ItemStack createStack() {
         if (this.oreDictionaryName != null) {
            if (OreDicHelper.doesOreNameExist(this.oreDictionaryName)) {
               ItemStack stack = OreDicHelper.getButThisModInPriority(this.oreDictionaryName, this.amount);
               if (this.stackTagCompound != null) {
                  stack.setTagCompound(this.stackTagCompound.copy());
               }

               return stack;
            } else {
               return OreDicHelper.getMissingItemStack(this.oreDictionaryName, this.amount);
            }
         } else if (this.missingItemName != null) {
            return OreDicHelper.getMissingItemStack(this.missingItemName, this.amount);
         } else {
            ItemStack stack = new ItemStack(
               this.item, this.amount, this.useMeta ? this.meta : 0, this.stackTagCompound != null ? this.stackTagCompound.copy() : null
            );
            if (this.stackTagCompound != null && !stack.hasTagCompound()) {
               stack.setTagCompound(this.stackTagCompound.copy());
            }

            return stack;
         }
      }

      @Override
      public ItemStack createStackForJeiInput() {
         if (this.oreDictionaryName != null) {
            if (OreDicHelper.doesOreNameExist(this.oreDictionaryName)) {
               ItemStack stack = OreDicHelper.getForJei(this.oreDictionaryName, this.amount);
               if (this.stackTagCompound != null) {
                  stack.setTagCompound(this.stackTagCompound.copy());
               }

               return stack;
            } else {
               return OreDicHelper.getMissingItemStack(this.oreDictionaryName, this.amount);
            }
         } else if (this.missingItemName != null) {
            return OreDicHelper.getMissingItemStack(this.missingItemName, this.amount);
         } else {
            ItemStack stack = new ItemStack(
               this.item, this.amount, this.useMeta ? this.meta : 0, this.stackTagCompound != null ? this.stackTagCompound.copy() : null
            );
            if (this.stackTagCompound != null && !stack.hasTagCompound()) {
               stack.setTagCompound(this.stackTagCompound.copy());
            }

            return stack;
         }
      }

      @Override
      public int getCount() {
         return this.amount;
      }

      @Override
      public String toString() {
         String text = "new IngridientItem(";
         if (this.oreDictionaryName != null) {
            text = text + "\"" + this.oreDictionaryName + "\", " + this.amount;
         } else {
            text = text + "\"" + this.item.getRegistryName().toString() + "\", " + this.amount + ", " + this.meta + ", " + this.useMeta;
         }

         return text + ")";
      }
   }

   public static class IngridientSoulStone extends Ingridient {
      public int soul;

      public IngridientSoulStone(int soul) {
         this.soul = soul;
      }

      @Override
      public boolean isStackAllowed(ItemStack itemStack) {
         return itemStack.getItem() == ItemsRegister.SOULSTONE && SoulStone.getSoul(itemStack) == this.soul;
      }

      @Override
      public ItemStack createStack() {
         return SoulStone.getSouledStack(this.soul);
      }

      @Override
      public int getCount() {
         return 1;
      }

      @Override
      public String toString() {
         return "new IngridientSoulStone(" + this.soul + ")";
      }
   }
}
