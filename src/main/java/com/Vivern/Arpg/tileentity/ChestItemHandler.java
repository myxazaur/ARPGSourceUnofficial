package com.Vivern.Arpg.tileentity;

import com.google.common.base.Objects;
import java.lang.ref.WeakReference;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class ChestItemHandler extends WeakReference<TileChest> implements IItemHandlerModifiable {
   public static final ChestItemHandler NO_ADJACENT_CHESTS_INSTANCE = new ChestItemHandler(null, null, false);
   private final boolean mainChestIsUpper;
   private final TileChest mainChest;
   private final int hashCode;

   public ChestItemHandler(@Nullable TileChest mainChest, @Nullable TileChest other, boolean mainChestIsUpper) {
      super(other);
      this.mainChest = mainChest;
      this.mainChestIsUpper = mainChestIsUpper;
      this.hashCode = Objects.hashCode(new Object[]{mainChestIsUpper ? mainChest : other}) * 31
         + Objects.hashCode(new Object[]{!mainChestIsUpper ? mainChest : other});
   }

   @Nullable
   public static ChestItemHandler get(TileChest chest) {
      World world = chest.getWorld();
      BlockPos pos = chest.getPos();
      if (world != null && pos != null && world.isBlockLoaded(pos)) {
         Block blockType = chest.getBlockType();
         EnumFacing[] horizontals = EnumFacing.HORIZONTALS;

         for (int i = horizontals.length - 1; i >= 0; i--) {
            EnumFacing enumfacing = horizontals[i];
            BlockPos blockpos = pos.offset(enumfacing);
            Block block = world.getBlockState(blockpos).getBlock();
            if (block == blockType) {
               TileEntity otherTE = world.getTileEntity(blockpos);
               if (otherTE instanceof TileChest) {
                  TileChest otherChest = (TileChest)otherTE;
                  return new ChestItemHandler(chest, otherChest, enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH);
               }
            }
         }

         return NO_ADJACENT_CHESTS_INSTANCE;
      } else {
         return null;
      }
   }

   @Nullable
   public TileChest getChest(boolean accessingUpper) {
      return accessingUpper == this.mainChestIsUpper ? this.mainChest : this.getOtherChest();
   }

   @Nullable
   private TileChest getOtherChest() {
      TileChest tileEntityChest = this.get();
      return tileEntityChest != null && !tileEntityChest.isInvalid() ? tileEntityChest : null;
   }

   public int getSlots() {
      return 54;
   }

   @Nonnull
   public ItemStack getStackInSlot(int slot) {
      boolean accessingUpperChest = slot < 27;
      int targetSlot = accessingUpperChest ? slot : slot - 27;
      TileChest chest = this.getChest(accessingUpperChest);
      return chest != null ? chest.getStackInSlot(targetSlot) : ItemStack.EMPTY;
   }

   public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
      boolean accessingUpperChest = slot < 27;
      int targetSlot = accessingUpperChest ? slot : slot - 27;
      TileChest chest = this.getChest(accessingUpperChest);
      if (chest != null) {
         IItemHandler singleHandler = chest.getSingleChestHandler();
         if (singleHandler instanceof IItemHandlerModifiable) {
            ((IItemHandlerModifiable)singleHandler).setStackInSlot(targetSlot, stack);
         }
      }

      chest = this.getChest(!accessingUpperChest);
      if (chest != null) {
         chest.markDirty();
      }
   }

   @Nonnull
   public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
      boolean accessingUpperChest = slot < 27;
      int targetSlot = accessingUpperChest ? slot : slot - 27;
      TileChest chest = this.getChest(accessingUpperChest);
      if (chest == null) {
         return stack;
      } else {
         int starting = stack.getCount();
         ItemStack ret = chest.getSingleChestHandler().insertItem(targetSlot, stack, simulate);
         if (ret.getCount() != starting && !simulate) {
            chest = this.getChest(!accessingUpperChest);
            if (chest != null) {
               chest.markDirty();
            }
         }

         return ret;
      }
   }

   @Nonnull
   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      boolean accessingUpperChest = slot < 27;
      int targetSlot = accessingUpperChest ? slot : slot - 27;
      TileChest chest = this.getChest(accessingUpperChest);
      if (chest == null) {
         return ItemStack.EMPTY;
      } else {
         ItemStack ret = chest.getSingleChestHandler().extractItem(targetSlot, amount, simulate);
         if (!ret.isEmpty() && !simulate) {
            chest = this.getChest(!accessingUpperChest);
            if (chest != null) {
               chest.markDirty();
            }
         }

         return ret;
      }
   }

   public int getSlotLimit(int slot) {
      boolean accessingUpperChest = slot < 27;
      return this.getChest(accessingUpperChest).getInventoryStackLimit();
   }

   public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
      boolean accessingUpperChest = slot < 27;
      int targetSlot = accessingUpperChest ? slot : slot - 27;
      TileChest chest = this.getChest(accessingUpperChest);
      return chest != null ? chest.getSingleChestHandler().isItemValid(targetSlot, stack) : true;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ChestItemHandler that = (ChestItemHandler)o;
         if (this.hashCode != that.hashCode) {
            return false;
         } else {
            TileChest otherChest = this.getOtherChest();
            return this.mainChestIsUpper == that.mainChestIsUpper
               ? Objects.equal(this.mainChest, that.mainChest) && Objects.equal(otherChest, that.getOtherChest())
               : Objects.equal(this.mainChest, that.getOtherChest()) && Objects.equal(otherChest, that.mainChest);
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.hashCode;
   }

   public boolean needsRefresh() {
      if (this == NO_ADJACENT_CHESTS_INSTANCE) {
         return false;
      } else {
         TileChest tileEntityChest = this.get();
         return tileEntityChest == null || tileEntityChest.isInvalid();
      }
   }
}
