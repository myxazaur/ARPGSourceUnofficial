package com.vivern.arpg.tileentity;

import com.vivern.arpg.blocks.PresentBox;
import com.vivern.arpg.main.Sounds;
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
import net.minecraft.util.SoundCategory;

public class TilePresentBox extends TileEntityLockableLoot {
   private NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
   public boolean opened = false;
   public int openTime = 0;
   public int texture = 0;

   public TilePresentBox() {
   }

   public TilePresentBox(int texture) {
      this.texture = texture;
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setBoolean("opened", this.opened);
      return new SPacketUpdateTileEntity(this.pos, 1, compound);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      if (compound.hasKey("opened")) {
         this.opened = compound.getBoolean("opened");
      }
   }

   public void openInventory(EntityPlayer player) {
      if (!player.isSpectator() && !this.opened) {
         this.opened = true;
         PresentBox.trySendPacketUpdate(this.getWorld(), this.getPos(), this, 64);
         this.world
            .playSound(
               (EntityPlayer)null,
               this.pos,
               Sounds.box_open,
               SoundCategory.BLOCKS,
               0.5F,
               this.world.rand.nextFloat() * 0.1F + 0.9F
            );
      }
   }

   public void closeInventory(EntityPlayer player) {
      if (!player.isSpectator()) {
         this.opened = false;
         PresentBox.trySendPacketUpdate(this.getWorld(), this.getPos(), this, 64);
         this.world
            .playSound(
               (EntityPlayer)null,
               this.pos,
               Sounds.box_close,
               SoundCategory.BLOCKS,
               0.5F,
               this.world.rand.nextFloat() * 0.1F + 0.9F
            );
      }
   }

   public int getSizeInventory() {
      return 9;
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
      return 64;
   }

   public String getName() {
      return "tile_present_box";
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
