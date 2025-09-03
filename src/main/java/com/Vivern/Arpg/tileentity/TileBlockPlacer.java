//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.ISeed;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.IPlantable;

public class TileBlockPlacer extends TileEntityLockableLoot {
   private static final Random RNG = new Random();
   private NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);

   public int getSizeInventory() {
      return 9;
   }

   public int getRandStackId() {
      int ii = 0;

      for (int k = RNG.nextInt(9); ii < 9; k++) {
         if (k > 8) {
            k = 0;
         }

         if (!((ItemStack)this.stacks.get(k)).isEmpty() && this.canItemBePlaced(((ItemStack)this.stacks.get(k)).getItem())) {
            return k;
         }

         ii++;
      }

      return -1;
   }

   public boolean canItemBePlaced(Item item) {
      return item instanceof ItemBlock || item instanceof IPlantable || item instanceof ISeed;
   }

   public boolean isEmpty() {
      for (ItemStack itemstack : this.stacks) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public int addItemStack(ItemStack stack) {
      for (int i = 0; i < this.stacks.size(); i++) {
         if (((ItemStack)this.stacks.get(i)).isEmpty()) {
            this.setInventorySlotContents(i, stack);
            return i;
         }
      }

      return -1;
   }

   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      if (!this.checkLootAndRead(compound)) {
         ItemStackHelper.loadAllItems(compound, this.stacks);
      }

      if (compound.hasKey("CustomName", 8)) {
         this.customName = compound.getString("CustomName");
      }
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      if (!this.checkLootAndWrite(compound)) {
         ItemStackHelper.saveAllItems(compound, this.stacks);
      }

      if (this.hasCustomName()) {
         compound.setString("CustomName", this.customName);
      }

      return compound;
   }

   public int getInventoryStackLimit() {
      return 1;
   }

   public String getName() {
      return "block_placer";
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      this.fillWithLoot(playerIn);
      return new Container9(playerInventory, this);
   }

   public String getGuiID() {
      return "minecraft:dispenser";
   }

   protected NonNullList<ItemStack> getItems() {
      return this.stacks;
   }
}
