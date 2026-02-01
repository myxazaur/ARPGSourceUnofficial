package com.vivern.arpg.tileentity;

import com.vivern.arpg.blocks.AlchemicLab;
import com.vivern.arpg.container.ContainerAlchemicLab;
import com.vivern.arpg.main.AlchemicLabRecipesRegister;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.recipes.AlchemicLabRecipe;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.IMagicVision;
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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileAlchemicLab extends TileEntityLockable implements IManaBuffer, ITickable, ISidedInventory, IFillDrain, IMagicVision {
   public static final ResourceLocation texbubble = new ResourceLocation("arpg:textures/blob.png");
   public static final int[] SLOTS_SIDES = new int[]{0, 1, 2, 3, 4, 5};
   public static final int[] SLOTS_BOTTOM_TOP = new int[]{6, 7, 8, 9, 10, 11};
   public NonNullList<ItemStack> containItemStacks = NonNullList.withSize(12, ItemStack.EMPTY);
   public ManaBuffer manaBuffer = new ManaBuffer(this, this, 80.0F, 8, 1.0F, 4.0F);
   public static int fluidMax = 5000;
   public FluidStorage tank1 = null;
   public FluidStorage tank2 = null;
   public float brewProgress = 0.0F;
   public float progressPerTick = 1.0F;
   public boolean started = false;
   public float ticksToRecipe = 0.0F;
   public int ticksExisted = 0;
   public static Random rand = new Random();

   public TileAlchemicLab() {
      this.tank1 = new FluidStorage(fluidMax, this);
      this.tank2 = new FluidStorage(fluidMax, this);
      this.tank1.setTileEntity(this);
      this.tank2.setTileEntity(this);
   }

   public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
      return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
   }

   @Nullable
   public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
      if (capability != CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || facing == null || facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
         return (T)(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? this.tank2 : super.getCapability(capability, facing));
      } else {
         return (T)this.tank1;
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
      AlchemicLab.trySendPacketUpdate(this.world, this.getPos(), this, fluidTypeChanges ? 64 : 8);
   }

   public void update() {
      this.manaBuffer.updateManaBuffer(this.world, this.pos);
      if (!this.world.isRemote && this.started) {
         if (this.brewProgress < this.ticksToRecipe) {
            this.brewProgress = this.brewProgress + this.progressPerTick;
            AlchemicLab.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         } else {
            for (AlchemicLabRecipe recipe : AlchemicLabRecipesRegister.allRecipes) {
               if (this.brewProgress >= recipe.ticksToBrew && this.getManaBuffer().getManaStored() >= recipe.manacost && recipe.tryCraft(this)) {
                  this.world
                     .playSound((EntityPlayer)null, this.getPos(), Sounds.alchemic_craft, SoundCategory.BLOCKS, 1.0F, 0.9F + rand.nextFloat() / 5.0F);
                  this.brewProgress = 0.0F;
                  this.getManaBuffer().addMana(-recipe.manacost);
                  this.checkStart();
                  AlchemicLab.trySendPacketUpdate(this.world, this.getPos(), this, 64);
                  break;
               }
            }
         }
      }

      this.ticksExisted++;
      if (this.ticksExisted % 15 == 0) {
         if (this.started) {
            this.world.playSound((EntityPlayer)null, this.getPos(), Sounds.alchemic_brewing, SoundCategory.BLOCKS, 0.9F, 1.0F);
         } else if (!this.world.isRemote) {
            this.checkStart();
         }

         if (this.world.isRemote) {
            if (this.tank1.getFluid() != null) {
               int color = this.tank1.getFluid().getFluid().getColor(this.tank1.getFluid());
               float R = (color >> 16 & 0xFF) / 255.0F;
               float G = (color >> 8 & 0xFF) / 255.0F;
               float B = (color >> 0 & 0xFF) / 255.0F;
               GUNParticle spelll = new GUNParticle(
                  texbubble,
                  0.05F,
                  -8.0E-4F,
                  40,
                  80,
                  this.world,
                  this.getPos().getX() + 0.0625F + rand.nextFloat() * 0.875F,
                  this.getPos().getY() + 0.1875F,
                  this.getPos().getZ() + 0.0625F + rand.nextFloat() * 0.875F,
                  0.0F,
                  0.0F,
                  0.0F,
                  R,
                  G,
                  B,
                  true,
                  0
               );
               spelll.scaleTickAdding = 0.001F;
               this.world.spawnEntity(spelll);
            }

            if (this.started && rand.nextFloat() < 0.7) {
               this.world
                  .spawnParticle(
                     EnumParticleTypes.CLOUD,
                     this.getPos().getX() + 0.5,
                     this.getPos().getY() + 1.3,
                     this.getPos().getZ() + 0.5,
                     0.0,
                     0.1,
                     0.0,
                     new int[0]
                  );
            }
         }
      }
   }

   public void checkStart() {
      boolean noempty = false;
      int[] var2 = SLOTS_SIDES;
      int recipe = var2.length;
      byte var4 = 0;
      if (var4 < recipe) {
         int i = var2[var4];
         if (!((ItemStack)this.containItemStacks.get(i)).isEmpty()) {
         }

         noempty = true;
      }

      if (noempty) {
         for (AlchemicLabRecipe recipex : AlchemicLabRecipesRegister.allRecipes) {
            if (recipex.isAllowForCraft(this) && recipex.manacost <= this.getManaBuffer().getManaStored()) {
               this.started = true;
               this.ticksToRecipe = recipex.ticksToBrew;
               return;
            }
         }
      }

      this.started = false;
   }

   public void read(NBTTagCompound compound) {
      this.containItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      ItemStackHelper.loadAllItems(compound, this.containItemStacks);
      this.tank1.readFromNBT(compound, "tank1");
      this.tank2.readFromNBT(compound, "tank2");
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

      this.manaBuffer.readFromNBT(compound);
      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      ItemStackHelper.saveAllItems(compound, this.containItemStacks);
      this.tank1.writeToNBT(compound, "tank1");
      this.tank2.writeToNBT(compound, "tank2");
      compound.setFloat("progress", this.brewProgress);
      compound.setFloat("pertick", this.progressPerTick);
      compound.setFloat("costnow", this.ticksToRecipe);
      compound.setBoolean("started", this.started);
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

   public int getSizeInventory() {
      return 12;
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

      if (index < 6 && !flag) {
         this.brewProgress = 0.0F;
         AlchemicLab.trySendPacketUpdate(this.world, this.getPos(), this, 8);
      }

      this.checkStart();
   }

   public String getName() {
      return "tile_alchemic_lab";
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
      return side != EnumFacing.DOWN && side != EnumFacing.UP ? SLOTS_SIDES : SLOTS_BOTTOM_TOP;
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
      return new ContainerAlchemicLab(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.alchemic_lab";
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
