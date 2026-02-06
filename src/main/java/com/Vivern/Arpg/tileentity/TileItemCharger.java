package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.BlockItemCharger;
import com.Vivern.Arpg.container.ContainerCharger;
import com.Vivern.Arpg.elements.IEnergyItem;
import com.Vivern.Arpg.elements.ItemAccumulator;
import com.Vivern.Arpg.main.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class TileItemCharger extends TileEntityLockableLoot implements ITickable {
   public NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
   public EnergyStorage electricStorage;
   public int prevEnergy;
   public byte rotation;
   public boolean isRFinfinite = false;

   public TileItemCharger() {
      this.electricStorage = new EnergyStorage(
         ItemAccumulator.LEAD_ACID_CAPACITY, ItemAccumulator.LEAD_ACID_THROUGHPUT, ItemAccumulator.LEAD_ACID_THROUGHPUT, 0
      );
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

   public boolean consumePower(int amount) {
      return this.electricStorage.extractEnergy(amount, true) >= amount ? this.electricStorage.extractEnergy(amount, false) >= amount : false;
   }

   public void update() {
      if (!this.world.isRemote) {
         ItemStack stack = (ItemStack)this.stacks.get(0);
         boolean send = true;
         if (stack.getItem() instanceof IEnergyItem && this.electricStorage.getEnergyStored() > 0) {
            IEnergyItem energyItem = (IEnergyItem)stack.getItem();
            int maxChargeSpeed = 1000;
            int add = this.isRFinfinite ? 15000 : Math.min(this.electricStorage.getEnergyStored(), maxChargeSpeed);
            int accepted = energyItem.addEnergyToItem(stack, add, false);
            if (!this.isRFinfinite) {
               this.consumePower(accepted);
            }

            if (energyItem.getEnergyStored(stack) >= energyItem.getMaxEnergyStored(stack)) {
               send = false;
               BlockItemCharger.trySendPacketUpdate(this.world, this.getPos(), this, 64);
            }
         }

         if (send
            && (
               Math.abs(this.electricStorage.getEnergyStored() - this.prevEnergy) > this.electricStorage.getMaxEnergyStored() / 68.0F
                  || this.electricStorage.getEnergyStored() != this.prevEnergy
                     && (this.electricStorage.getEnergyStored() == 0 || this.electricStorage.getEnergyStored() == this.electricStorage.getMaxEnergyStored())
            )) {
            BlockItemCharger.trySendPacketUpdate(this.world, this.getPos(), this, 8);
            this.prevEnergy = this.electricStorage.getEnergyStored();
         }
      }
   }

   public boolean isItemFullcharged() {
      ItemStack stack = (ItemStack)this.stacks.get(0);
      if (stack.getItem() instanceof IEnergyItem) {
         IEnergyItem energyItem = (IEnergyItem)stack.getItem();
         return energyItem.getEnergyStored(stack) >= energyItem.getMaxEnergyStored(stack);
      } else {
         return false;
      }
   }

   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
      return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
   }

   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
      return (T)(capability == CapabilityEnergy.ENERGY ? this.electricStorage : super.getCapability(capability, facing));
   }

   public void read(NBTTagCompound compound) {
      this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      if (!this.checkLootAndRead(compound)) {
         ItemStackHelper.loadAllItems(compound, this.stacks);
      }

      if (compound.hasKey("rf")) {
         this.electricStorage = new EnergyStorage(
            ItemAccumulator.LEAD_ACID_CAPACITY, ItemAccumulator.LEAD_ACID_THROUGHPUT, ItemAccumulator.LEAD_ACID_THROUGHPUT, compound.getInteger("rf")
         );
      }

      if (compound.hasKey("rotation")) {
         this.rotation = compound.getByte("rotation");
      }

      if (compound.hasKey("isRFinfinite")) {
         this.isRFinfinite = compound.getBoolean("isRFinfinite");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      if (!this.checkLootAndWrite(compound)) {
         ItemStackHelper.saveAllItems(compound, this.stacks);
      }

      compound.setInteger("rf", this.electricStorage.getEnergyStored());
      compound.setByte("rotation", this.rotation);
      compound.setBoolean("isRFinfinite", this.isRFinfinite);
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
      if (!this.world.isRemote) {
         BlockItemCharger.trySendPacketUpdate(this.world, this.getPos(), this, 8);
      }
   }

   public int getField(int id) {
      switch (id) {
         case 0:
            return this.electricStorage.getEnergyStored();
         case 1:
            if (((ItemStack)this.stacks.get(0)).getItem() instanceof IEnergyItem) {
               return Math.round(
                  100.0F
                     * NBTHelper.GetNBTint((ItemStack)this.stacks.get(0), "rf")
                     / ((IEnergyItem)((ItemStack)this.stacks.get(0)).getItem()).getMaxEnergyStored((ItemStack)this.stacks.get(0))
               );
            }
         default:
            return 0;
      }
   }

   public void setField(int id, int value) {
      switch (id) {
         case 0:
            this.electricStorage = new EnergyStorage(
               ItemAccumulator.LEAD_ACID_CAPACITY, ItemAccumulator.LEAD_ACID_THROUGHPUT, ItemAccumulator.LEAD_ACID_THROUGHPUT, value
            );
      }
   }

   public int getInventoryStackLimit() {
      return 1;
   }

   public String getName() {
      return "tile_item_charger";
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerCharger(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.item_charger";
   }

   protected NonNullList<ItemStack> getItems() {
      return this.stacks;
   }
}
