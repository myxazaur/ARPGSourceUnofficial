package com.vivern.arpg.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class EmptyContainer extends Container {
   public EmptyContainer(InventoryPlayer playerInventory) {
      int y = 1000;

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + y));
         }
      }

      for (int k = 0; k < 9; k++) {
         this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142 + y));
      }
   }

   public boolean canInteractWith(EntityPlayer playerIn) {
      return false;
   }

   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
      return ItemStack.EMPTY;
   }
}
