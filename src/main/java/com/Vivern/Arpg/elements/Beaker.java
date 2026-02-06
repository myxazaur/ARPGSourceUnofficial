package com.Vivern.Arpg.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class Beaker extends Item {
   public Beaker() {
      this.setRegistryName("beaker");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("beaker");
      this.setMaxStackSize(1);
   }

   public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
      return true;
   }

   public static void writeToNbt(ItemStack itemStack, BeakerFluid fluid) {
      if (!itemStack.hasTagCompound()) {
         NBTTagCompound itemCompound = new NBTTagCompound();
         itemStack.setTagCompound(itemCompound);
      }

      writeToNbt(itemStack.getTagCompound(), fluid);
   }

   public static BeakerFluid readFromNbt(ItemStack itemStack, int defaultCapacity) {
      return itemStack.hasTagCompound() ? readFromNbt(itemStack.getTagCompound(), defaultCapacity) : new BeakerFluid(defaultCapacity);
   }

   public static void writeToNbt(NBTTagCompound tagCompound, BeakerFluid fluid) {
      for (int i = 0; i < fluid.elements.length; i++) {
         tagCompound.setByte("element" + i, fluid.elements[i]);
         tagCompound.setByte("modifier" + i, fluid.modifiers[i]);
      }

      tagCompound.setInteger("capacity", fluid.elements.length);
   }

   public static BeakerFluid readFromNbt(NBTTagCompound tagCompound, int defaultCapacity) {
      int capacity = defaultCapacity;
      if (tagCompound.hasKey("capacity")) {
         capacity = tagCompound.getInteger("capacity");
      }

      BeakerFluid beakerFluid = new BeakerFluid(capacity);

      for (int i = 0; i < capacity; i++) {
         String keyNameEl = "element" + i;
         String keyNameMd = "modifier" + i;
         if (tagCompound.hasKey(keyNameEl)) {
            beakerFluid.elements[i] = tagCompound.getByte(keyNameEl);
         }

         if (tagCompound.hasKey(keyNameMd)) {
            beakerFluid.modifiers[i] = tagCompound.getByte(keyNameMd);
         }

         if (beakerFluid.elements[i] != 0) {
            beakerFluid.isEmpty = false;
            beakerFluid.maxLay = i;
         }
      }

      return beakerFluid;
   }

   public static class BeakerFluid {
      public byte[] elements;
      public byte[] modifiers;
      public boolean isEmpty = true;
      public int maxLay = -1;

      public BeakerFluid(int capacity) {
         this.elements = new byte[capacity];
         this.modifiers = new byte[capacity];
      }

      public boolean isReadyToRefine() {
         if (!this.isEmpty && this.maxLay + 1 >= this.elements.length) {
            for (int i = 0; i < this.elements.length; i++) {
               if (this.modifiers[i] != 0 || this.elements[i] != this.elements[0]) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }

      public boolean add(int elementId, byte modifier) {
         if (this.maxLay < this.elements.length - 1) {
            this.maxLay++;
            this.elements[this.maxLay] = (byte)elementId;
            this.modifiers[this.maxLay] = modifier;
            this.isEmpty = false;
            return true;
         } else {
            return false;
         }
      }

      public void combineLays() {
         if (this.maxLay >= 0 && this.modifiers[0] != 0) {
            for (int i = 0; i < this.elements.length; i++) {
               this.elements[i] = 0;
               this.modifiers[i] = 0;
               this.isEmpty = true;
               this.maxLay = -1;
            }
         } else {
            for (int i = 1; i < this.elements.length; i++) {
               byte prevelement = this.elements[i - 1];
               byte prevmodifier = this.modifiers[i - 1];
               byte element = this.elements[i];
               byte modifier = this.modifiers[i];
               if (element == prevelement && modifier == 2 && prevmodifier == 1) {
                  this.modifiers[i] = 0;
                  this.modifiers[i - 1] = 0;
               }
            }
         }
      }

      public boolean remove() {
         if (this.maxLay >= 0) {
            this.elements[this.maxLay] = 0;
            this.modifiers[this.maxLay] = 0;
            this.maxLay--;
            if (this.maxLay < 0) {
               this.isEmpty = true;
            }

            return true;
         } else {
            return false;
         }
      }

      public byte transfuseTo(BeakerFluid other) {
         byte ret = 0;

         while (this.maxLay >= 0 && other.maxLay + 1 < other.elements.length) {
            byte transfusingElement = this.elements[this.maxLay];
            byte transfusingModifier = this.modifiers[this.maxLay];
            other.add(transfusingElement, transfusingModifier);
            ret = transfusingElement;
            this.elements[this.maxLay] = 0;
            this.modifiers[this.maxLay] = 0;
            this.maxLay--;
            if (this.maxLay < 0) {
               this.isEmpty = true;
            } else if (this.elements[this.maxLay] != transfusingElement || this.modifiers[this.maxLay] != transfusingModifier) {
               break;
            }
         }

         return ret;
      }
   }
}
