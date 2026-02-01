package com.vivern.arpg.tileentity;

import com.vivern.arpg.network.PacketHandler;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileARPGChest extends TileEntityLockableLoot implements ITickable {
   private NonNullList<ItemStack> chestContents = NonNullList.withSize(27, ItemStack.EMPTY);
   public EnumChest type;
   public EnumFacing otherChestSide;
   public EnumFacing facing;
   public float lidAngle;
   public float prevLidAngle;
   public int numPlayersUsing;
   private int ticksSinceSync;
   private ArrayList<ChestLock> locks;
   public int chestState;
   private boolean locksChanged;
   private IItemHandler itemHandler;

   public boolean isLockedWith(ChestLock chestLock) {
      int i = 1 << chestLock.id;
      return (this.chestState & i) != 0;
   }

   public void lockOrUnlockWith(ChestLock chestLock, boolean shouldLock) {
      int i = 1 << chestLock.id;
      if (shouldLock) {
         this.chestState |= i;
      } else {
         this.chestState &= ~i;
      }

      this.locksChanged = true;
   }

   public ArrayList<ChestLock> getLocks() {
      if (this.locks == null || this.locksChanged) {
         this.locks = new ArrayList<>();

         for (ChestLock locktype : ChestLock.locksRegister) {
            if (this.isLockedWith(locktype)) {
               this.locks.add(locktype);
            }
         }

         this.locksChanged = false;
      }

      return this.locks;
   }

   public boolean isLocked() {
      return this.chestState != 0;
   }

   public void update() {
      int i = this.pos.getX();
      int j = this.pos.getY();
      int k = this.pos.getZ();
      this.ticksSinceSync++;
      if (!this.world.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0) {
         this.numPlayersUsing = 0;
         float f = 5.0F;

         for (EntityPlayer entityplayer : this.world
            .getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(i - 5.0F, j - 5.0F, k - 5.0F, i + 1 + 5.0F, j + 1 + 5.0F, k + 1 + 5.0F))) {
            if (entityplayer.openContainer instanceof ContainerChest) {
               IInventory iinventory = ((ContainerChest)entityplayer.openContainer).getLowerChestInventory();
               if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest(this)) {
                  this.numPlayersUsing++;
               }
            }
         }
      }

      this.prevLidAngle = this.lidAngle;
      float f1 = 0.1F;
      if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
         double d1 = i + 0.5;
         double d2 = k + 0.5;
         if (this.getChestStanding() != EnumChestStanding.RIGHT) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  d1,
                  j + 0.5,
                  d2,
                  this.type.soundOpen,
                  SoundCategory.BLOCKS,
                  0.5F,
                  this.world.rand.nextFloat() * 0.1F + 0.9F
               );
         }
      }

      if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
         float f2 = this.lidAngle;
         if (this.numPlayersUsing > 0) {
            this.lidAngle += 0.1F;
         } else {
            this.lidAngle -= 0.1F;
         }

         if (this.lidAngle > 1.0F) {
            this.lidAngle = 1.0F;
         }

         float f3 = 0.5F;
         if (this.lidAngle < 0.5F && f2 >= 0.5F) {
            double d3 = i + 0.5;
            double d0 = k + 0.5;
            if (this.getChestStanding() != EnumChestStanding.RIGHT) {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     d3,
                     j + 0.5,
                     d0,
                     this.type.soundClose,
                     SoundCategory.BLOCKS,
                     0.5F,
                     this.world.rand.nextFloat() * 0.1F + 0.9F
                  );
            }
         }

         if (this.lidAngle < 0.0F) {
            this.lidAngle = 0.0F;
         }
      }
   }

   public boolean receiveClientEvent(int id, int type) {
      if (id == 1) {
         this.numPlayersUsing = type;
         return true;
      } else if (id == 2) {
         this.facing = EnumFacing.byIndex(type);
         return true;
      } else {
         return super.receiveClientEvent(id, type);
      }
   }

   public void openInventory(EntityPlayer player) {
      if (!player.isSpectator() && !this.isLocked()) {
         if (this.numPlayersUsing < 0) {
            this.numPlayersUsing = 0;
         }

         this.numPlayersUsing++;
         this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
         this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
      }
   }

   public void closeInventory(EntityPlayer player) {
      if (!player.isSpectator()) {
         this.numPlayersUsing--;
         this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
         this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
      }
   }

   public EnumChestStanding getChestStanding() {
      if (this.otherChestSide == null) {
         return EnumChestStanding.MIDDLE;
      } else {
         return this.facing.rotateYCCW() == this.otherChestSide ? EnumChestStanding.LEFT : EnumChestStanding.RIGHT;
      }
   }

   @Nullable
   public TileARPGChest getOtherChest() {
      if (this.otherChestSide != null) {
         TileEntity tileEntity = this.world.getTileEntity(this.pos.offset(this.otherChestSide));
         if (tileEntity != null && tileEntity instanceof TileARPGChest) {
            return (TileARPGChest)tileEntity;
         }
      }

      this.otherChestSide = null;
      return null;
   }

   public IInventory getOpenedInventory() {
      TileARPGChest otherChest = this.getOtherChest();
      if (otherChest != null) {
         EnumChestStanding standing = this.getChestStanding();
         return standing == EnumChestStanding.LEFT
            ? new InventoryLargeChest("container.chestDouble", this, otherChest)
            : new InventoryLargeChest("container.chestDouble", otherChest, this);
      } else {
         return this;
      }
   }

   public boolean canOpen() {
      if (this.world
         .getBlockState(this.pos.up())
         .doesSideBlockChestOpening(this.world, this.pos.up(), EnumFacing.DOWN)) {
         return false;
      } else if (this.isLocked()) {
         return false;
      } else {
         TileARPGChest other = this.getOtherChest();
         if (other != null) {
            if (this.world
               .getBlockState(other.getPos().up())
               .doesSideBlockChestOpening(this.world, other.getPos().up(), EnumFacing.DOWN)) {
               return false;
            }

            if (other.isLocked()) {
               return false;
            }
         }

         return true;
      }
   }

   public void onPlace(EntityLivingBase placer, EnumFacing face) {
      if (!this.world.isRemote) {
         this.setChestFacing(placer.getHorizontalFacing().getOpposite());
         if (face.getAxis() != Axis.Y) {
            BlockPos offpos = this.getPos().offset(face.getOpposite());
            TileEntity tileEntity = this.world.getTileEntity(offpos);
            if (tileEntity != null && tileEntity instanceof TileARPGChest) {
               TileARPGChest chest = (TileARPGChest)tileEntity;
               tryMakeDoubleChest(chest, this, face);
            }
         }
      }
   }

   public static boolean tryMakeDoubleChest(TileARPGChest chestHas, TileARPGChest chestPlaced, EnumFacing chestHasOtherChestSide) {
      if (chestHas.otherChestSide == null && chestPlaced.otherChestSide == null && chestHas.type == chestPlaced.type) {
         chestHas.otherChestSide = chestHasOtherChestSide;
         chestPlaced.otherChestSide = chestHasOtherChestSide.getOpposite();
         EnumFacing facingVariant1 = chestHas.getChestFacing();
         if (chestHasOtherChestSide.getAxis() != facingVariant1.getAxis()) {
            chestPlaced.setChestFacing(facingVariant1);
         } else {
            EnumFacing facingVariant2 = chestPlaced.getChestFacing();
            if (chestHasOtherChestSide.getAxis() != facingVariant2.getAxis()) {
               chestHas.setChestFacing(facingVariant2);
            } else {
               EnumFacing facingVariant3 = chestHasOtherChestSide.rotateY();
               chestPlaced.setChestFacing(facingVariant3);
               chestHas.setChestFacing(facingVariant3);
            }
         }

         PacketHandler.trySendPacketUpdate(chestHas.getWorld(), chestHas.getPos(), chestHas, 64.0);
         PacketHandler.trySendPacketUpdate(chestPlaced.getWorld(), chestPlaced.getPos(), chestPlaced, 64.0);
         return true;
      } else {
         return false;
      }
   }

   public static void tryRemoveDoubleChest(TileARPGChest chestHas) {
      TileARPGChest other = chestHas.getOtherChest();
      if (other == null) {
         PacketHandler.trySendPacketUpdate(chestHas.getWorld(), chestHas.getPos(), chestHas, 64.0);
      }
   }

   public EnumFacing getChestFacing() {
      return this.facing == null ? EnumFacing.NORTH : this.facing;
   }

   public void setChestFacing(EnumFacing facing) {
      this.facing = facing;
      if (!this.world.isRemote) {
         this.world.addBlockEvent(this.pos, this.getBlockType(), 2, this.getChestFacing().getIndex());
      }
   }

   public int getSizeInventory() {
      return 27;
   }

   public ItemStack getStackInSlot(int index) {
      return this.isLocked() ? ItemStack.EMPTY : super.getStackInSlot(index);
   }

   public boolean isItemValidForSlot(int index, ItemStack stack) {
      if (!this.isLocked()) {
         TileARPGChest other = this.getOtherChest();
         if (other == null || !other.isLocked()) {
            return true;
         }
      }

      return false;
   }

   public boolean isEmpty() {
      for (ItemStack itemstack : this.chestContents) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public String getName() {
      return this.hasCustomName() ? this.customName : "arpg_chest";
   }

   public String getGuiID() {
      return "arpg.chest";
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      this.fillWithLoot(playerIn);
      return new ContainerChest(playerInventory, this, playerIn);
   }

   protected NonNullList<ItemStack> getItems() {
      return this.chestContents;
   }

   protected IItemHandler createUnSidedHandler() {
      return new InvWrapper(this.getOpenedInventory());
   }

   @Nullable
   public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
      return (T)(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? this.createUnSidedHandler() : super.getCapability(capability, facing));
   }

   public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
      if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && !this.isLocked()) {
         TileARPGChest other = this.getOtherChest();
         if (other == null || !other.isLocked()) {
            return true;
         }
      }

      return super.hasCapability(capability, facing);
   }

   public AxisAlignedBB getRenderBoundingBox() {
      return this.getChestStanding() == EnumChestStanding.LEFT
         ? super.getRenderBoundingBox().expand(this.otherChestSide.getXOffset(), 0.0, this.otherChestSide.getZOffset())
         : super.getRenderBoundingBox();
   }

   public void read(NBTTagCompound compound) {
      this.chestContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      if (!this.checkLootAndRead(compound)) {
         ItemStackHelper.loadAllItems(compound, this.chestContents);
      }

      if (compound.hasKey("CustomName", 8)) {
         this.customName = compound.getString("CustomName");
      }

      if (compound.hasKey("chestState")) {
         this.chestState = compound.getInteger("chestState");
         this.locksChanged = true;
      }

      if (compound.hasKey("chesttype")) {
         this.type = EnumChest.byId(compound.getInteger("chesttype"));
      }

      if (compound.hasKey("otherChestSide")) {
         byte ocs = compound.getByte("otherChestSide");
         this.otherChestSide = ocs == -1 ? null : EnumFacing.byIndex(ocs);
      }

      if (compound.hasKey("facing")) {
         this.facing = EnumFacing.byIndex(compound.getByte("facing"));
      }
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      if (!this.checkLootAndWrite(compound)) {
         ItemStackHelper.saveAllItems(compound, this.chestContents);
      }

      if (this.hasCustomName()) {
         compound.setString("CustomName", this.customName);
      }

      compound.setInteger("chestState", this.chestState);
      if (this.type != null) {
         compound.setInteger("chesttype", this.type.id);
      }

      compound.setByte("otherChestSide", (byte)(this.otherChestSide == null ? -1 : this.otherChestSide.getIndex()));
      compound.setByte("facing", (byte)this.getChestFacing().getIndex());
      return compound;
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

   public static enum EnumChestStanding {
      LEFT,
      RIGHT,
      MIDDLE;
   }
}
