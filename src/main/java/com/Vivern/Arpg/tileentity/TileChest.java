//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.Chest;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.Freezing;
import com.Vivern.Arpg.potions.PotionEffects;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileChest extends TileEntityLockableLoot implements ITickable {
   private NonNullList<ItemStack> chestContents = NonNullList.withSize(27, ItemStack.EMPTY);
   public boolean adjacentChestChecked;
   public TileChest adjacentChestZNeg;
   public TileChest adjacentChestXPos;
   public TileChest adjacentChestXNeg;
   public TileChest adjacentChestZPos;
   public float lidAngle;
   public float prevLidAngle;
   public int numPlayersUsing;
   private int ticksSinceSync;
   public EnumChest type;
   public int chestState = 0;
   public ChestItemHandler doubleChestHandler;

   public TileChest() {
      this.type = EnumChest.FROZEN;
   }

   public TileChest(EnumChest type) {
      this.type = type;
   }

   public boolean onTryOpenChest(EntityPlayer player, Random rand) {
      if (this.isLockedWithWinterCurse()) {
         if (player.getHeldItemMainhand().getItem() == ItemsRegister.ICECOMPASS || player.getHeldItemOffhand().getItem() == ItemsRegister.ICECOMPASS) {
            return false;
         }

         if (!player.world.isRemote) {
            if (rand.nextFloat() < 0.6) {
               PotionEffect lastdebaff = Weapons.mixPotion(
                  player,
                  PotionEffects.FREEZING,
                  80.0F,
                  3.0F,
                  Weapons.EnumPotionMix.GREATEST,
                  Weapons.EnumPotionMix.GREATEST,
                  Weapons.EnumMathOperation.NONE,
                  Weapons.EnumMathOperation.NONE,
                  160,
                  3
               );
               Freezing.tryPlaySound(player, lastdebaff);
            } else {
               player.addPotionEffect(new PotionEffect(PotionEffects.WINTER_CURSE, 260));
            }
         }
      }

      return true;
   }

   public boolean isLockedWithPuzzle() {
      return (this.chestState & 1) != 0;
   }

   public boolean isLockedWithWinterCurse() {
      return (this.chestState & 2) != 0;
   }

   public boolean isLockedWithRustedKey() {
      return (this.chestState & 4) != 0;
   }

   public void lockWithPuzzle(boolean lock) {
      if (lock) {
         this.chestState |= 1;
      } else {
         this.chestState &= -2;
      }
   }

   public void lockWithWinterCurse(boolean lock) {
      if (lock) {
         this.chestState |= 2;
      } else {
         this.chestState &= -3;
      }
   }

   public void lockWithRustedKey(boolean lock) {
      if (lock) {
         this.chestState |= 4;
      } else {
         this.chestState &= -5;
      }
   }

   public boolean isLocked() {
      return (this.chestState & 7) != 0;
   }

   public boolean isUsableByPlayer(EntityPlayer player) {
      return !this.isLocked();
   }

   public ItemStack getStackInSlot(int index) {
      return this.isLocked() ? ItemStack.EMPTY : super.getStackInSlot(index);
   }

   public int getSizeInventory() {
      return 27;
   }

   public boolean isEmpty() {
      for (ItemStack itemstack : this.chestContents) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public String getName() {
      return this.hasCustomName() ? this.customName : "chest";
   }

   public static void registerFixesChest(DataFixer fixer) {
      fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileChest.class, new String[]{"Items"}));
   }

   public void read(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.chestContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      if (!this.checkLootAndRead(compound)) {
         ItemStackHelper.loadAllItems(compound, this.chestContents);
      }

      if (compound.hasKey("CustomName", 8)) {
         this.customName = compound.getString("CustomName");
      }

      if (compound.hasKey("chestState")) {
         this.chestState = compound.getInteger("chestState");
      }

      if (compound.hasKey("chesttype")) {
         this.type = EnumChest.byId(compound.getInteger("chesttype"));
      }
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      super.writeToNBT(compound);
      if (!this.checkLootAndWrite(compound)) {
         ItemStackHelper.saveAllItems(compound, this.chestContents);
      }

      if (this.hasCustomName()) {
         compound.setString("CustomName", this.customName);
      }

      compound.setInteger("chestState", this.chestState);
      compound.setInteger("chesttype", this.type.id);
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

   public int getInventoryStackLimit() {
      return 64;
   }

   public void updateContainingBlockInfo() {
      super.updateContainingBlockInfo();
      this.adjacentChestChecked = false;
      this.doubleChestHandler = null;
   }

   private void setNeighbor(TileChest chestTe, EnumFacing side) {
      if (chestTe.isInvalid()) {
         this.adjacentChestChecked = false;
      } else if (this.adjacentChestChecked) {
         switch (side) {
            case NORTH:
               if (this.adjacentChestZNeg != chestTe) {
                  this.adjacentChestChecked = false;
               }
               break;
            case SOUTH:
               if (this.adjacentChestZPos != chestTe) {
                  this.adjacentChestChecked = false;
               }
               break;
            case EAST:
               if (this.adjacentChestXPos != chestTe) {
                  this.adjacentChestChecked = false;
               }
               break;
            case WEST:
               if (this.adjacentChestXNeg != chestTe) {
                  this.adjacentChestChecked = false;
               }
         }
      }
   }

   public void checkForAdjacentChests() {
      if (!this.adjacentChestChecked) {
         if (this.world == null || !this.world.isAreaLoaded(this.pos, 1)) {
            return;
         }

         this.adjacentChestChecked = true;
         this.adjacentChestXNeg = this.getAdjacentChest(EnumFacing.WEST);
         this.adjacentChestXPos = this.getAdjacentChest(EnumFacing.EAST);
         this.adjacentChestZNeg = this.getAdjacentChest(EnumFacing.NORTH);
         this.adjacentChestZPos = this.getAdjacentChest(EnumFacing.SOUTH);
      }
   }

   @Nullable
   protected TileChest getAdjacentChest(EnumFacing side) {
      BlockPos blockpos = this.pos.offset(side);
      if (this.isChestAt(blockpos)) {
         TileEntity tileentity = this.world.getTileEntity(blockpos);
         if (tileentity instanceof TileChest) {
            TileChest tileentitychest = (TileChest)tileentity;
            tileentitychest.setNeighbor(this, side.getOpposite());
            return tileentitychest;
         }
      }

      return null;
   }

   private boolean isChestAt(BlockPos posIn) {
      if (this.world == null) {
         return false;
      } else {
         Block block = this.world.getBlockState(posIn).getBlock();
         return block instanceof Chest && ((Chest)block).chestType == this.type;
      }
   }

   public void update() {
      this.checkForAdjacentChests();
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
      if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
         double d1 = i + 0.5;
         double d2 = k + 0.5;
         if (this.adjacentChestZPos != null) {
            d2 += 0.5;
         }

         if (this.adjacentChestXPos != null) {
            d1 += 0.5;
         }

         this.world
            .playSound(
               (EntityPlayer)null,
               d1,
               j + 0.5,
               d2,
               SoundEvents.BLOCK_CHEST_OPEN,
               SoundCategory.BLOCKS,
               0.5F,
               this.world.rand.nextFloat() * 0.1F + 0.9F
            );
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
         if (this.lidAngle < 0.5F && f2 >= 0.5F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
            double d3 = i + 0.5;
            double d0 = k + 0.5;
            if (this.adjacentChestZPos != null) {
               d0 += 0.5;
            }

            if (this.adjacentChestXPos != null) {
               d3 += 0.5;
            }

            this.world
               .playSound(
                  (EntityPlayer)null,
                  d3,
                  j + 0.5,
                  d0,
                  SoundEvents.BLOCK_CHEST_CLOSE,
                  SoundCategory.BLOCKS,
                  0.5F,
                  this.world.rand.nextFloat() * 0.1F + 0.9F
               );
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
      if (!player.isSpectator() && this.getBlockType() instanceof Chest) {
         this.numPlayersUsing--;
         this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
         this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
      }
   }

   public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
      return this.isLocked() ? false : super.hasCapability(capability, facing);
   }

   @Nullable
   public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
      if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
         if (this.doubleChestHandler == null || this.doubleChestHandler.needsRefresh()) {
            this.doubleChestHandler = ChestItemHandler.get(this);
         }

         if (this.doubleChestHandler != null && this.doubleChestHandler != ChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE) {
            return (T)this.doubleChestHandler;
         }
      }

      return (T)super.getCapability(capability, facing);
   }

   public IItemHandler getSingleChestHandler() {
      return (IItemHandler)super.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
   }

   public void invalidate() {
      super.invalidate();
      this.updateContainingBlockInfo();
      this.checkForAdjacentChests();
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
}
