package com.vivern.arpg.tileentity;

import com.vivern.arpg.blocks.NetherMelter;
import com.vivern.arpg.container.ContainerNetherMelter;
import com.vivern.arpg.main.NetherMelterRecipesRegister;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.recipes.NetherMelterRecipe;
import com.vivern.arpg.renders.IMagicVision;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileNetherMelter extends TileEntityLockable implements IManaBuffer, ITickable, ISidedInventory, IMagicVision {
   public static final int[] SLOTS_TOP_SIDES = new int[]{0, 1, 2, 3, 4};
   public static final int[] SLOTS_BOTTOM = new int[]{5, 6, 7};
   public NonNullList<ItemStack> furnaceItemStacks = NonNullList.withSize(8, ItemStack.EMPTY);
   public ManaBuffer manaBuffer = new ManaBuffer(this, this, 80.0F, 8, 1.0F, 4.0F);
   public float meltProgress = 0.0F;
   public float progressPerTick = 0.1F;
   public boolean started = false;
   public float costToRecipe = 0.0F;
   public int ticksExisted = 0;

   public int getSizeInventory() {
      return this.furnaceItemStacks.size();
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
      return (ItemStack)this.furnaceItemStacks.get(index);
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
         NetherMelter.trySendPacketUpdate(this.world, this.getPos(), this);
      }

      this.checkStart();
   }

   public String getName() {
      return "tile_nether_melter";
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
      return index > 4 ? false : index < 8;
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
            return (int)(this.getManaBuffer().getManaStored() * 10.0F);
         case 1:
            return (int)this.getManaBuffer().getManaStorageSize();
         case 2:
            return 0;
         case 3:
            return (int)this.meltProgress;
         case 4:
            return (int)this.progressPerTick;
         case 5:
            return this.started ? 1 : 0;
         case 6:
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
            this.started = value != 0;
            break;
         case 6:
            this.costToRecipe = value;
      }
   }

   public int getFieldCount() {
      return 6;
   }

   public void clear() {
      this.furnaceItemStacks.clear();
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerNetherMelter(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.nether_melter";
   }

   public void update() {
      this.manaBuffer.updateManaBuffer(this.world, this.pos);
      if (this.started) {
         float cost = Math.min(this.progressPerTick, this.getManaBuffer().getManaStored());
         if (cost > 0.0F) {
            if (this.meltProgress < this.costToRecipe) {
               this.meltProgress += cost;
               this.getManaBuffer().addMana(-cost);
               NetherMelter.trySendPacketUpdate(this.world, this.getPos(), this);
               if (this.ticksExisted % 76 == 0) {
                  this.world.playSound((EntityPlayer)null, this.getPos(), Sounds.nether_melter, SoundCategory.BLOCKS, 0.9F, 1.0F);
               }
            } else {
               for (NetherMelterRecipe recipe : NetherMelterRecipesRegister.allRecipes) {
                  if (this.meltProgress >= recipe.manacost && recipe.tryCraft(this)) {
                     this.meltProgress = this.meltProgress - recipe.manacost;
                     this.checkStart();
                     NetherMelter.trySendPacketUpdate(this.world, this.getPos(), this);
                     break;
                  }
               }
            }
         }
      }

      this.ticksExisted++;
   }

   public void checkStart() {
      boolean noempty = false;
      int[] var2 = SLOTS_TOP_SIDES;
      int recipe = var2.length;
      byte var4 = 0;
      if (var4 < recipe) {
         int i = var2[var4];
         if (!((ItemStack)this.furnaceItemStacks.get(i)).isEmpty()) {
         }

         noempty = true;
      }

      if (noempty) {
         for (NetherMelterRecipe recipex : NetherMelterRecipesRegister.allRecipes) {
            if (recipex.isAllowForCraft(this)) {
               this.started = true;
               this.costToRecipe = recipex.manacost;
               return;
            }
         }
      }

      this.started = false;
   }

   public void read(NBTTagCompound compound) {
      if (this.furnaceItemStacks == null) {
         this.furnaceItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      }

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

      if (compound.hasKey("started")) {
         this.started = compound.getBoolean("started");
      }

      this.manaBuffer.readFromNBT(compound);
      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);
      compound.setFloat("progress", this.meltProgress);
      compound.setFloat("pertick", this.progressPerTick);
      compound.setFloat("costnow", this.costToRecipe);
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
