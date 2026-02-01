package com.vivern.arpg.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerIndustrialMixer extends Container {
   private final IInventory tileInventory;

   public ContainerIndustrialMixer(InventoryPlayer playerInventory, IInventory iInventory) {
      this.tileInventory = iInventory;

      for (int i = 0; i < 3; i++) {
         this.addSlotToContainer(new Slot(iInventory, i, 62 + i * 18, 15));
      }

      for (int i = 0; i < 3; i++) {
         this.addSlotToContainer(new Slot(iInventory, i + 3, 62 + i * 18, 79));
      }

      this.addSlotToContainer(new Slot(iInventory, 6, 58, 47));
      this.addSlotToContainer(new Slot(iInventory, 7, 102, 47));

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 110 + i * 18));
         }
      }

      for (int k = 0; k < 9; k++) {
         this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 168));
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
