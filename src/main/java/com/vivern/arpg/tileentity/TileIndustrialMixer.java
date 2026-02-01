package com.vivern.arpg.tileentity;

import com.vivern.arpg.blocks.IndustrialMixer;
import com.vivern.arpg.container.ContainerIndustrialMixer;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.IndustrialMixerRecipesRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.recipes.IndustrialMixerRecipe;
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
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileIndustrialMixer extends TileEntityLockable implements ITickable, ISidedInventory, IFillDrain, ITileEntitySynchronize {
   public static final int[] SLOTS_TOP = new int[]{0, 1, 2};
   public static final int[] SLOTS_BOTTOM = new int[]{3, 4, 5};
   public static final int[] SLOTS_OTHER = new int[]{6, 7};
   public static int rfMax = 10000;
   public static int fluidMax = 5000;
   public static Random rand = new Random();
   public NonNullList<ItemStack> containItemStacks = NonNullList.withSize(8, ItemStack.EMPTY);
   public FluidStorage tank1 = null;
   public FluidStorage tank2 = null;
   public FluidStorage tank3 = null;
   public FluidStorage tank4 = null;
   public float brewProgress = 0.0F;
   public float progressPerTick = 1.0F;
   public boolean started = false;
   public float ticksToRecipe = 0.0F;
   public int currentRFtotick = 0;
   public int ticksExisted = 0;
   public EnergyStorage electricStorage;
   public int rfprevious = 0;
   public float clientRotorRotation = 0.0F;
   public float prevclientRotorRotation = 0.0F;
   public float clientRotorSpeed = 0.0F;
   public IItemHandler[] itemHandlers = new IItemHandler[6];

   public TileIndustrialMixer() {
      this.tank1 = new FluidStorage(fluidMax, this);
      this.tank2 = new FluidStorage(fluidMax, this);
      this.tank3 = new FluidStorage(fluidMax, this);
      this.tank4 = new FluidStorage(fluidMax, this);
      this.tank1.setTileEntity(this);
      this.tank2.setTileEntity(this);
      this.tank3.setTileEntity(this);
      this.tank4.setTileEntity(this);
      this.electricStorage = new EnergyStorage(rfMax, 10000, 10000, 0);
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         this.started = args[0] > 0.0;
      }
   }

   public void update() {
      if (!this.world.isRemote) {
         if (this.started) {
            if (this.brewProgress < this.ticksToRecipe) {
               if (this.electricStorage.extractEnergy(this.currentRFtotick, true) == this.currentRFtotick) {
                  this.brewProgress = this.brewProgress + this.progressPerTick;
                  this.electricStorage.extractEnergy(this.currentRFtotick, false);
                  if (this.ticksExisted % 5 == 0) {
                     IndustrialMixer.trySendPacketUpdate(this.world, this.getPos(), this, 8, true);
                  }

                  if (this.ticksExisted % 10 == 0) {
                     this.world.playSound((EntityPlayer)null, this.getPos(), Sounds.industrial_mixer, SoundCategory.BLOCKS, 0.9F, 1.0F);
                  }
               }
            } else {
               for (IndustrialMixerRecipe recipe : IndustrialMixerRecipesRegister.allRecipes) {
                  if (this.brewProgress >= recipe.ticksToBrew && recipe.tryCraft(this)) {
                     this.brewProgress = 0.0F;
                     this.electricStorage.extractEnergy(recipe.rfToAll % this.currentRFtotick, false);
                     this.checkStart();
                     IndustrialMixer.trySendPacketUpdate(this.world, this.getPos(), this, 64, false);
                     break;
                  }
               }
            }
         } else if (this.ticksExisted % 5 == 0) {
            if (this.ticksExisted % 20 == 0) {
               this.checkStart();
            }

            if (this.electricStorage.getEnergyStored() != this.rfprevious) {
               this.rfprevious = this.electricStorage.getEnergyStored();
               IndustrialMixer.trySendPacketUpdate(this.world, this.getPos(), this, 8, true);
            }
         }
      } else {
         if (this.started) {
            if (this.clientRotorSpeed < 0.32F) {
               this.clientRotorSpeed += 0.01F;
            }
         } else if (this.clientRotorSpeed > 0.0F) {
            this.clientRotorSpeed -= 0.01F;
         }

         this.prevclientRotorRotation = this.clientRotorRotation;
         this.clientRotorRotation = this.clientRotorRotation + this.clientRotorSpeed;
      }

      this.ticksExisted++;
   }

   public void checkStart() {
      if (this.electricStorage.getEnergyStored() > 0) {
         for (IndustrialMixerRecipe recipe : IndustrialMixerRecipesRegister.allRecipes) {
            if (recipe.isAllowForCraft(this)) {
               if (!this.started) {
                  this.world
                     .playSound(
                        (EntityPlayer)null, this.getPos(), Sounds.industrial_mixer_start, SoundCategory.BLOCKS, 1.0F, 0.9F + rand.nextFloat() / 5.0F
                     );
               }

               this.started = true;
               ITileEntitySynchronize.sendSynchronize(this, 64.0, 1.0);
               this.ticksToRecipe = recipe.ticksToBrew;
               this.currentRFtotick = recipe.rfToAll / recipe.ticksToBrew;
               return;
            }
         }
      }

      this.started = false;
      ITileEntitySynchronize.sendSynchronize(this, 64.0, 0.0);
   }

   public FluidStorage getTankForSide(EnumFacing facing) {
      EnumFacing blockFacing = (EnumFacing)this.world.getBlockState(this.getPos()).getValue(IndustrialMixer.FACING);
      if (facing.getHorizontalIndex() == GetMOP.next(EnumFacing.WEST.getHorizontalIndex(), blockFacing.getHorizontalIndex(), 4)) {
         return this.tank1;
      } else if (facing.getHorizontalIndex() == GetMOP.next(EnumFacing.NORTH.getHorizontalIndex(), blockFacing.getHorizontalIndex(), 4)) {
         return this.tank2;
      } else if (facing.getHorizontalIndex() == GetMOP.next(EnumFacing.EAST.getHorizontalIndex(), blockFacing.getHorizontalIndex(), 4)) {
         return this.tank3;
      } else {
         return facing.getHorizontalIndex() == GetMOP.next(EnumFacing.SOUTH.getHorizontalIndex(), blockFacing.getHorizontalIndex(), 4) ? this.tank4 : null;
      }
   }

   public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
      return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
   }

   @Nullable
   public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
      if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing != null && facing.getHorizontalIndex() >= 0) {
         return (T)this.getTankForSide(facing);
      } else if (capability == CapabilityEnergy.ENERGY) {
         return (T)this.electricStorage;
      } else if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
         if (this.itemHandlers[facing.getIndex()] == null) {
            this.itemHandlers[facing.getIndex()] = new SidedInvWrapper(this, facing);
         }

         return (T)this.itemHandlers[facing.getIndex()];
      } else {
         return (T)super.getCapability(capability, facing);
      }
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
      IndustrialMixer.trySendPacketUpdate(this.world, this.getPos(), this, 8, false);
   }

   public int getSizeInventory() {
      return 8;
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

      if (index < 3 && !flag) {
         this.brewProgress = 0.0F;
         IndustrialMixer.trySendPacketUpdate(this.world, this.getPos(), this, 8, false);
      }

      this.checkStart();
   }

   public String getName() {
      return "tile_industrial_mixer";
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
      if (side == EnumFacing.DOWN) {
         return SLOTS_BOTTOM;
      } else {
         return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_OTHER;
      }
   }

   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
      return this.getStackInSlot(index).isEmpty() || ItemStack.areItemsEqual(this.getStackInSlot(index), itemStackIn);
   }

   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
      return true;
   }

   public int getField(int id) {
      switch (id) {
         case 0:
            return this.electricStorage.getEnergyStored();
         case 1:
            return rfMax;
         case 2:
            return 0;
         case 3:
            return fluidMax;
         case 4:
            return this.tank1.getFluidAmount();
         case 5:
            return this.tank2.getFluidAmount();
         case 6:
            return (int)this.brewProgress;
         case 7:
            return (int)this.progressPerTick;
         case 8:
            return this.started ? 1 : 0;
         case 9:
            return (int)this.ticksToRecipe;
         default:
            return 0;
      }
   }

   public void setField(int id, int value) {
      switch (id) {
         case 0:
         case 1:
         case 2:
         case 4:
         case 5:
         default:
            break;
         case 3:
            fluidMax = value;
            break;
         case 6:
            this.brewProgress = value;
            break;
         case 7:
            this.progressPerTick = value;
            break;
         case 8:
            this.started = value != 0;
            break;
         case 9:
            this.ticksToRecipe = value;
      }
   }

   public int getFieldCount() {
      return 10;
   }

   public void clear() {
      this.containItemStacks.clear();
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerIndustrialMixer(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.industrial_mixer";
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("easy") && compound.getBoolean("easy")) {
         if (compound.hasKey("rf")) {
            this.electricStorage = new EnergyStorage(rfMax, 10000, 10000, compound.getInteger("rf"));
         }

         if (compound.hasKey("progress")) {
            this.brewProgress = compound.getFloat("progress");
         }
      } else {
         this.containItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
         ItemStackHelper.loadAllItems(compound, this.containItemStacks);
         this.tank1.readFromNBT(compound, "tank1");
         this.tank2.readFromNBT(compound, "tank2");
         this.tank3.readFromNBT(compound, "tank3");
         this.tank4.readFromNBT(compound, "tank4");
         if (compound.hasKey("rf")) {
            this.electricStorage = new EnergyStorage(rfMax, 10000, 10000, compound.getInteger("rf"));
         }

         if (compound.hasKey("rftick")) {
            this.currentRFtotick = compound.getInteger("rftick");
         }

         if (compound.hasKey("progress")) {
            this.brewProgress = compound.getFloat("progress");
         }

         if (compound.hasKey("pertick")) {
            this.progressPerTick = compound.getFloat("pertick");
         }

         if (compound.hasKey("costnow")) {
            this.ticksToRecipe = compound.getFloat("costnow");
         }

         if (compound.hasKey("started")) {
            this.started = compound.getBoolean("started");
         }

         super.readFromNBT(compound);
      }
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      ItemStackHelper.saveAllItems(compound, this.containItemStacks);
      this.tank1.writeToNBT(compound, "tank1");
      this.tank2.writeToNBT(compound, "tank2");
      this.tank3.writeToNBT(compound, "tank3");
      this.tank4.writeToNBT(compound, "tank4");
      compound.setInteger("rf", this.electricStorage.getEnergyStored());
      compound.setInteger("rftick", this.currentRFtotick);
      compound.setFloat("progress", this.brewProgress);
      compound.setFloat("pertick", this.progressPerTick);
      compound.setFloat("costnow", this.ticksToRecipe);
      compound.setBoolean("started", this.started);
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

   public SPacketUpdateTileEntity getEasyUpdatePacket() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setInteger("rf", this.electricStorage.getEnergyStored());
      compound.setFloat("progress", this.brewProgress);
      compound.setBoolean("easy", true);
      return new SPacketUpdateTileEntity(this.pos, 1, compound);
   }
}
