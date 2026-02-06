package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.container.ContainerAlchemicVaporizer;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.recipes.VaporizeAction;
import com.Vivern.Arpg.renders.IMagicVision;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileAlchemicVaporizer extends TileEntityLockable implements IManaBuffer, ITickable, ISidedInventory, IFillDrain, IMagicVision {
   public static List<VaporizeAction> allRecipes = new ArrayList<>();
   public static final ResourceLocation texbubble = new ResourceLocation("arpg:textures/blob.png");
   public static final int[] SLOT = new int[]{0};
   public NonNullList<ItemStack> containItemStacks = NonNullList.withSize(1, ItemStack.EMPTY);
   public ManaBuffer manaBuffer = new ManaBuffer(this, this, 20.0F, 4, -0.5F, 4.0F);
   public int fluidMax = 1000;
   public FluidStorage tank1 = null;
   public static Random rand = new Random();
   public VaporizeAction currentVapour;
   public int ticksRemaining;
   public static int startId = 0;

   public TileAlchemicVaporizer() {
      this.tank1 = new FluidStorage(this.fluidMax, this);
      this.tank1.setTileEntity(this);
   }

   public static void init() {
      register(new VaporizeAction.VaporizeActionManaOil());
   }

   public static void register(VaporizeAction action) {
      int idTo = startId++;
      action.id = idTo;
      allRecipes.add(action.id, action);
   }

   public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
      return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
   }

   @Nullable
   public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
      return (T)(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing != null && facing != EnumFacing.UP
         ? this.tank1
         : super.getCapability(capability, facing));
   }

   @Override
   public int fill(FluidStorage thisstorage, FluidStack resource, boolean doFill) {
      return !thisstorage.canFillFluidType(resource) ? 0 : thisstorage.fillInternal(resource, doFill);
   }

   @Override
   public FluidStack drain(FluidStorage thisstorage, int maxDrain, boolean doDrain) {
      return !thisstorage.canDrainFluidType(thisstorage.getFluid()) ? null : thisstorage.drainInternal(maxDrain, doDrain);
   }

   @Override
   public void onContentsChanged(FluidStorage thisstorage, boolean fluidTypeChanges) {
      PacketHandler.trySendPacketUpdate(this.world, this.getPos(), this, 8.0);
   }

   public boolean isItemEquals(ItemStack stack, ItemStack other) {
      return !other.isEmpty() && stack.getItem() == other.getItem() && stack.getItemDamage() == other.getItemDamage();
   }

   public void update() {
      this.manaBuffer.updateManaBuffer(this.world, this.pos);
      if (!this.world.isRemote) {
         boolean nonullfluid = this.tank1.getFluid() != null && this.tank1.getFluid().amount > 0;
         if (this.currentVapour == null && nonullfluid && !this.getStackInSlot(0).isEmpty()) {
            for (VaporizeAction recipe : allRecipes) {
               float power = recipe.getCatalystPower(this.getStackInSlot(0));
               if (power > 0.0F && this.tank1.getFluid().getFluid() == recipe.fluid.getFluid()) {
                  power = Math.min(power, (float)this.tank1.getFluid().amount / recipe.fluid.amount);
                  int fluidDrain = Math.round(recipe.fluid.amount * power);
                  if (this.tank1.getFluid().amount >= fluidDrain) {
                     this.currentVapour = recipe;
                     this.ticksRemaining = Math.round(recipe.ticksInAction * power);
                     this.decrStackSize(0, 1);
                     this.tank1.drain(fluidDrain, true);
                  }
               }
            }
         }
      }

      if (this.currentVapour != null) {
         if (this.ticksRemaining > 0) {
            this.currentVapour.vaporizeTick(this.world, this.pos, this, rand);
            this.ticksRemaining--;
         } else {
            this.ticksRemaining = 0;
            this.currentVapour = null;
            if (!this.world.isRemote) {
               PacketHandler.trySendPacketUpdate(this.world, this.getPos(), this, 8.0);
            }
         }
      }
   }

   public void read(NBTTagCompound compound) {
      this.containItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      ItemStackHelper.loadAllItems(compound, this.containItemStacks);
      this.tank1.readFromNBT(compound, "tank1");
      this.manaBuffer.readFromNBT(compound);
      if (compound.hasKey("vapour")) {
         int vId = compound.getInteger("vapour");
         if (vId >= 0 && vId < allRecipes.size()) {
            this.currentVapour = allRecipes.get(vId);
         }
      }

      if (compound.hasKey("ticksRemaining")) {
         this.ticksRemaining = compound.getInteger("ticksRemaining");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      ItemStackHelper.saveAllItems(compound, this.containItemStacks);
      this.tank1.writeToNBT(compound, "tank1");
      this.manaBuffer.writeToNBT(compound);
      if (this.currentVapour != null) {
         compound.setInteger("vapour", this.currentVapour.id);
      }

      compound.setInteger("ticksRemaining", this.ticksRemaining);
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

   public int getSizeInventory() {
      return 1;
   }

   public boolean isEmpty() {
      for (ItemStack itemstack : this.containItemStacks) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack getStackInSlot(int index) {
      return (ItemStack)this.containItemStacks.get(index);
   }

   public ItemStack decrStackSize(int index, int count) {
      return ItemStackHelper.getAndSplit(this.containItemStacks, index, count);
   }

   public ItemStack removeStackFromSlot(int index) {
      return ItemStackHelper.getAndRemove(this.containItemStacks, index);
   }

   public void setInventorySlotContents(int index, ItemStack stack) {
      ItemStack itemstack = (ItemStack)this.containItemStacks.get(index);
      boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
      this.containItemStacks.set(index, stack);
      if (stack.getCount() > this.getInventoryStackLimit()) {
         stack.setCount(this.getInventoryStackLimit());
      }

      if (!flag) {
         PacketHandler.trySendPacketUpdate(this.world, this.getPos(), this, 8.0);
      }
   }

   public String getName() {
      return "tile_alchemic_vaporizer";
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
      return true;
   }

   public int[] getSlotsForFace(EnumFacing side) {
      return SLOT;
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
            return (int)(this.getManaBuffer().getManaStored() * 10.0F);
         case 1:
            return (int)this.getManaBuffer().getManaStorageSize();
         case 2:
            return 0;
         case 3:
            return this.fluidMax;
         case 4:
            return this.tank1.getFluidAmount();
         default:
            return 0;
      }
   }

   public void setField(int id, int value) {
      switch (id) {
         case 3:
            this.fluidMax = value;
         case 0:
         case 1:
         case 2:
      }
   }

   public int getFieldCount() {
      return 4;
   }

   public void clear() {
      this.containItemStacks.clear();
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerAlchemicVaporizer(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.alchemic_vaporizer";
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
