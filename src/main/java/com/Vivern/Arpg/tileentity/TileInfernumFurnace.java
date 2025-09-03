//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.InfernumFurnace;
import com.Vivern.Arpg.container.ContainerInfernumFurnace;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.renders.IMagicVision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TileInfernumFurnace extends TileEntityLockable implements IManaBuffer, ITickable, ISidedInventory, IMagicVision {
   public static final int[] SLOTS_TOP_SIDES = new int[]{0};
   public static final int[] SLOTS_BOTTOM = new int[]{1};
   public NonNullList<ItemStack> furnaceItemStacks = NonNullList.withSize(2, ItemStack.EMPTY);
   public ManaBuffer manaBuffer = new ManaBuffer(this, this, 30.0F, 8, 1.0F, 4.0F);
   public float meltProgress = 0.0F;
   public float progressPerTick = 0.15F;
   public float costToRecipe = 10.0F;

   public int getSizeInventory() {
      return 2;
   }

   public boolean isEmpty() {
      for (ItemStack itemstack : this.furnaceItemStacks) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack getStackInSlot(int index) {
      return (ItemStack)this.furnaceItemStacks.get(MathHelper.clamp(index, 0, 1));
   }

   public ItemStack decrStackSize(int index, int count) {
      return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
   }

   public ItemStack removeStackFromSlot(int index) {
      return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
   }

   public void setInventorySlotContents(int index, ItemStack stack) {
      ItemStack itemstack = (ItemStack)this.furnaceItemStacks.get(index);
      boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
      this.furnaceItemStacks.set(index, stack);
      if (stack.getCount() > this.getInventoryStackLimit()) {
         stack.setCount(this.getInventoryStackLimit());
      }

      if (index == 0 && !flag) {
         this.meltProgress = 0.0F;
         InfernumFurnace.trySendPacketUpdate(this.world, this.getPos(), this);
      }
   }

   public String getName() {
      return "tile_infernum_furnace";
   }

   public boolean hasCustomName() {
      return false;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isUsableByPlayer(EntityPlayer player) {
      return this.world.getTileEntity(this.pos) != this
         ? false
         : player.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5)
            <= 64.0;
   }

   public void openInventory(EntityPlayer player) {
   }

   public void closeInventory(EntityPlayer player) {
   }

   public boolean isItemValidForSlot(int index, ItemStack stack) {
      return index == 0;
   }

   public int[] getSlotsForFace(EnumFacing side) {
      return side == EnumFacing.DOWN ? SLOTS_BOTTOM : SLOTS_TOP_SIDES;
   }

   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
      return this.isItemValidForSlot(index, itemStackIn);
   }

   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
      return true;
   }

   public int getField(int id) {
      switch (id) {
         case 0:
            return (int)this.getManaBuffer().getManaStored();
         case 1:
            return (int)this.getManaBuffer().getManaStorageSize();
         case 2:
            return 0;
         case 3:
            return (int)this.meltProgress;
         case 4:
            return (int)this.progressPerTick;
         case 5:
            return (int)this.costToRecipe;
         default:
            return 0;
      }
   }

   public void setField(int id, int value) {
      switch (id) {
         case 0:
         case 1:
         case 2:
         default:
            break;
         case 3:
            this.meltProgress = value;
            break;
         case 4:
            this.progressPerTick = value;
            break;
         case 5:
            this.costToRecipe = value;
      }
   }

   public int getFieldCount() {
      return 5;
   }

   public void clear() {
      this.furnaceItemStacks.clear();
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerInfernumFurnace(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.infernum_furnace";
   }

   public void smeltItem() {
      if (this.canSmelt()) {
         ItemStack itemstack = (ItemStack)this.furnaceItemStacks.get(0);
         ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
         ItemStack itemstack2 = (ItemStack)this.furnaceItemStacks.get(1);
         if (itemstack2.isEmpty()) {
            this.furnaceItemStacks.set(1, itemstack1.copy());
         } else if (itemstack2.getItem() == itemstack1.getItem()) {
            itemstack2.grow(itemstack1.getCount());
         }

         itemstack.shrink(1);
      }
   }

   public void update() {
      this.manaBuffer.updateManaBuffer(this.world, this.pos);
      if (!this.world.isRemote) {
         float cost = Math.min(this.progressPerTick, this.getManaBuffer().getManaStored());
         if (cost > 0.0F && this.canSmelt()) {
            if (this.meltProgress < this.costToRecipe) {
               this.meltProgress += cost;
               this.getManaBuffer().addMana(-cost);
               InfernumFurnace.trySendPacketUpdate(this.world, this.getPos(), this);
            } else {
               this.smeltItem();
               this.meltProgress = 0.0F;
               InfernumFurnace.trySendPacketUpdate(this.world, this.getPos(), this);
            }
         }
      }
   }

   public boolean canSmelt() {
      if (((ItemStack)this.furnaceItemStacks.get(0)).isEmpty()) {
         return false;
      } else {
         ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult((ItemStack)this.furnaceItemStacks.get(0));
         if (itemstack.isEmpty()) {
            return false;
         } else {
            ItemStack itemstack1 = (ItemStack)this.furnaceItemStacks.get(1);
            if (itemstack1.isEmpty()) {
               return true;
            } else if (!itemstack1.isItemEqual(itemstack)) {
               return false;
            } else {
               return itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit()
                     && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()
                  ? true
                  : itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
            }
         }
      }
   }

   public void read(NBTTagCompound compound) {
      this.furnaceItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);
      if (compound.hasKey("progress")) {
         this.meltProgress = compound.getFloat("progress");
      }

      if (compound.hasKey("pertick")) {
         this.progressPerTick = compound.getFloat("pertick");
      }

      if (compound.hasKey("costnow")) {
         this.costToRecipe = compound.getFloat("costnow");
      }

      this.manaBuffer.readFromNBT(compound);
      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);
      compound.setFloat("progress", this.meltProgress);
      compound.setFloat("pertick", this.progressPerTick);
      compound.setFloat("costnow", this.costToRecipe);
      this.manaBuffer.writeToNBT(compound);
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

   @Override
   public ManaBuffer getManaBuffer() {
      return this.manaBuffer;
   }

   @Override
   public float getElementEnergy(ShardType shardType) {
      return 0.0F;
   }

   @Override
   public float getMana() {
      return this.getManaBuffer().getMana();
   }

   @Override
   public float getManaStorageSize(World world, BlockPos pos) {
      return this.getManaBuffer().getManaStorageSize();
   }
}
