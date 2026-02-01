package com.vivern.arpg.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAssemblyTable extends Container {
   private final IInventory tileInventory;

   public ContainerAssemblyTable(InventoryPlayer playerInventory, IInventory iInventory) {
      this.tileInventory = iInventory;
      int indx = 0;

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            this.addSlotToContainer(new Slot(iInventory, indx++, 52 + j * 18, 75 + i * 18));
         }
      }

      for (int k = 0; k < 5; k++) {
         this.addSlotToContainer(new Slot(iInventory, indx++, 26 + k * 27, 19));
      }

      this.addSlotToContainer(new Slot(iInventory, 14, 148, 92));
      this.addSlotToContainer(new Slot(iInventory, 15, 148, 122));

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 16 + j * 18, 151 + i * 18));
         }
      }

      for (int k = 0; k < 9; k++) {
         this.addSlotToContainer(new Slot(playerInventory, k, 16 + k * 18, 209));
      }
   }

   public void addListener(IContainerListener listener) {
      super.addListener(listener);
      listener.sendAllWindowProperties(this, this.tileInventory);
   }

   public boolean canInteractWith(EntityPlayer playerIn) {
      return this.tileInventory.isUsableByPlayer(playerIn);
   }

   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = (Slot)this.inventorySlots.get(index);
      if (slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if (index < this.tileInventory.getSizeInventory()) {
            if (!this.mergeItemStack(itemstack1, this.tileInventory.getSizeInventory(), this.inventorySlots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.mergeItemStack(itemstack1, 0, this.tileInventory.getSizeInventory(), false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
         } else {
            slot.onSlotChanged();
         }
      }

      return itemstack;
   }
}
