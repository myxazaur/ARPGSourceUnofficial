//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.CreativeElementDistributor;
import com.Vivern.Arpg.container.ContainerElementDistributor;
import com.Vivern.Arpg.main.ItemsElements;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.NonNullList;

public class TileElementDistributor extends TileEntityLockableLoot implements TileEntityClicked {
   public NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
   public boolean optionUseMetadata = false;
   public static NonNullList<ItemStack> nextStacks;
   public static ItemStack nextDisplayed;

   public static void fillNextStacks() {
      CreativeTabs.SEARCH.displayAllRelevantItems(nextStacks);

      while (ItemsElements.getAllElements((ItemStack)nextStacks.get(0)) != ItemsElements.EMPTY_ELEMENTS) {
         nextStacks.remove(0);
         if (nextStacks.isEmpty()) {
            return;
         }
      }
   }

   public static void removeFromNextStacks(ItemStack itemStack, boolean useMeta) {
      if (nextStacks != null) {
         if (useMeta) {
            for (int i = 0; i < nextStacks.size(); i++) {
               ItemStack stackIn = (ItemStack)nextStacks.get(i);
               if (stackIn.getItem() == itemStack.getItem() && stackIn.getMetadata() == itemStack.getMetadata()) {
                  nextStacks.remove(i);
                  return;
               }
            }
         } else {
            NonNullList<ItemStack> newnextStacks = NonNullList.create();

            for (ItemStack stackHas : nextStacks) {
               if (stackHas.getItem() != itemStack.getItem()) {
                  newnextStacks.add(stackHas);
               }
            }

            nextStacks = newnextStacks;
         }
      }
   }

   @Override
   public void mouseClick(int mouseX, int mouseY, int mouseButton) {
      if (!this.world.isRemote) {
         if (mouseButton == 3 && !this.isEmpty()) {
            if (nextStacks != null && nextStacks.size() > 0) {
               removeFromNextStacks(this.getStackInSlot(0), this.optionUseMetadata);
            }

            this.clear();
            if (nextStacks != null && nextStacks.size() > 0) {
               nextDisplayed = (ItemStack)nextStacks.get(0);
            }
         } else if (mouseButton == 4) {
            this.optionUseMetadata = !this.optionUseMetadata;
         } else if (mouseButton == 5) {
            if (nextStacks == null) {
               nextStacks = NonNullList.create();
               fillNextStacks();
            }

            if (nextStacks != null && nextStacks.size() > 0) {
               this.setInventorySlotContents(0, ((ItemStack)nextStacks.get(0)).copy());
            }
         } else if (mouseButton == 6) {
            this.optionUseMetadata = true;
         } else if (mouseButton == 7) {
            this.optionUseMetadata = false;
         }

         CreativeElementDistributor.trySendPacketUpdate(this.world, this.getPos(), this);
      }
   }

   public int getSizeInventory() {
      return 1;
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

   public void read(NBTTagCompound compound) {
      this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      if (!this.checkLootAndRead(compound)) {
         ItemStackHelper.loadAllItems(compound, this.stacks);
      }

      if (compound.hasKey("usemeta")) {
         this.optionUseMetadata = compound.getBoolean("usemeta");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      if (!this.checkLootAndWrite(compound)) {
         ItemStackHelper.saveAllItems(compound, this.stacks);
      }

      compound.setBoolean("usemeta", this.optionUseMetadata);
      return super.writeToNBT(compound);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      this.write(compound);
      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.read(compound);
      super.readFromNBT(compound);
   }

   public NBTTagCompound getUpdateTag() {
      NBTTagCompound compound = super.getUpdateTag();
      this.write(compound);
      return compound;
   }

   public void handleUpdateTag(NBTTagCompound compound) {
      this.read(compound);
      super.handleUpdateTag(compound);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      this.read(compound);
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound compound = new NBTTagCompound();
      this.write(compound);
      return new SPacketUpdateTileEntity(this.pos, 1, compound);
   }

   public void setInventorySlotContents(int index, ItemStack stack) {
      super.setInventorySlotContents(index, stack);
   }

   public int getField(int id) {
      switch (id) {
         case 0:
            return this.optionUseMetadata ? 1 : 0;
         default:
            return 0;
      }
   }

   public void setField(int id, int value) {
      switch (id) {
         case 0:
            this.optionUseMetadata = value > 0;
      }
   }

   public int getInventoryStackLimit() {
      return 1;
   }

   public String getName() {
      return "tile_creative_element_distributor";
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerElementDistributor(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.creative_element_distributor";
   }

   protected NonNullList<ItemStack> getItems() {
      return this.stacks;
   }
}
