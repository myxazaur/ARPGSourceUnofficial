//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.container;

import com.Vivern.Arpg.tileentity.TileResearchTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerResearchTable extends Container {
   private final IInventory tileInventory;
   public int specialization;

   public ContainerResearchTable(InventoryPlayer playerInventory, IInventory iInventory) {
      this.tileInventory = iInventory;
      if (iInventory instanceof TileResearchTable) {
         TileResearchTable researchTable = (TileResearchTable)iInventory;
         this.specialization = researchTable.specialization;
      }

      if (this.specialization == 1) {
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
               this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 48 + j * 18, 161 + i * 18));
            }
         }

         for (int k = 0; k < 9; k++) {
            this.addSlotToContainer(new Slot(playerInventory, k, 48 + k * 18, 219));
         }
      } else if (this.specialization == 2) {
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
               this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 18 + j * 18, 196 + i * 18));
            }
         }

         for (int k = 0; k < 3; k++) {
            for (int i = 0; i < 3; i++) {
               this.addSlotToContainer(new Slot(playerInventory, k + i * 3, 184 + k * 18, 196 + i * 18));
            }
         }
      } else {
         this.addSlotToContainer(new Slot(iInventory, 0, 7, 174));
         this.addSlotToContainer(new Slot(iInventory, 1, 7, 192));
         this.addSlotToContainer(new Slot(iInventory, 2, 201, 174));
         this.addSlotToContainer(new Slot(iInventory, 3, 201, 192));

         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
               this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 32 + j * 18, 174 + i * 18));
            }
         }

         for (int k = 0; k < 9; k++) {
            this.addSlotToContainer(new Slot(playerInventory, k, 32 + k * 18, 232));
         }
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
