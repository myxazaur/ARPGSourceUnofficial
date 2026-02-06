package com.Vivern.Arpg.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerTrader extends Container {
   public ContainerTrader(InventoryPlayer playerInventory) {
      int ypos = 76;
      int xpos = 6;

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 20 + xpos + j * 18, ypos + 98 + i * 18));
         }
      }

      for (int k = 0; k < 9; k++) {
         this.addSlotToContainer(new Slot(playerInventory, k, 20 + xpos + k * 18, 156 + ypos));
      }
   }

   public boolean canInteractWith(EntityPlayer playerIn) {
      return playerIn.isEntityAlive();
   }
}
