//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.SacrificialAltar;
import com.Vivern.Arpg.elements.SoulStone;
import com.Vivern.Arpg.main.IMagicUI;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.recipes.Soul;
import com.Vivern.Arpg.renders.IMagicVision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileSacrificialAltar extends TileEntity implements IManaBuffer, ITickable, ISidedInventory, IMagicUI, IMagicVision {
   public ManaBuffer manaBuffer = new ManaBuffer(this, this, 15.0F, 4, -0.5F, 1.0F);
   public NonNullList<ItemStack> stack = NonNullList.withSize(1, ItemStack.EMPTY);
   public static final int[] SLOT = new int[]{0};
   public float generationspeed = 0.2F;
   public float charge = 0.0F;
   public int ticksExisted = 0;

   public void update() {
      this.getManaBuffer().updateManaBuffer(this.world, this.pos);
      this.ticksExisted++;
      if (this.ticksExisted % 10 == 0) {
         if (this.charge < 1.0F) {
            ItemStack soulstone = (ItemStack)this.stack.get(0);
            if (!soulstone.isEmpty() && soulstone.getItem() == ItemsRegister.SOULSTONE) {
               int soulid = SoulStone.getSoul(soulstone);
               if (soulid > 0) {
                  Soul soul = Soul.byId(soulid);
                  this.charge = soul.manaContains;
               }
            }
         } else if (this.charge > 1.0F && this.getManaBuffer().getManaStored() < this.getManaBuffer().getManaStorageSize()) {
            float add = Math.min(this.generationspeed, this.getManaBuffer().getManaStorageSize() - this.getManaBuffer().getManaStored());
            this.charge -= add;
            this.getManaBuffer().addMana(add);
            if (this.ticksExisted % 100 == 0) {
               this.world.playSound((EntityPlayer)null, this.getPos(), Sounds.sacrificial_altar, SoundCategory.BLOCKS, 0.6F, 1.0F);
            }

            if (this.charge <= 1.0F) {
               this.charge = 0.0F;
               this.setInventorySlotContents(0, new ItemStack(ItemsRegister.SOULSTONE));
            }
         }
      }
   }

   public void read(NBTTagCompound compound) {
      this.stack = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      ItemStackHelper.loadAllItems(compound, this.stack);
      if (compound.hasKey("charge")) {
         this.charge = compound.getFloat("charge");
      }

      this.manaBuffer.readFromNBT(compound);
      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      ItemStackHelper.saveAllItems(compound, this.stack);
      compound.setFloat("charge", this.charge);
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
      return 1;
   }

   public boolean isEmpty() {
      return ((ItemStack)this.stack.get(0)).isEmpty();
   }

   public ItemStack getStackInSlot(int index) {
      return (ItemStack)this.stack.get(index);
   }

   public ItemStack decrStackSize(int index, int count) {
      ItemStack stack = ItemStackHelper.getAndSplit(this.stack, index, count);
      SacrificialAltar.trySendPacketUpdate(this.world, this.getPos(), this);
      return stack;
   }

   public ItemStack removeStackFromSlot(int index) {
      ItemStack stack = ItemStackHelper.getAndRemove(this.stack, index);
      SacrificialAltar.trySendPacketUpdate(this.world, this.getPos(), this);
      return stack;
   }

   public void setInventorySlotContents(int index, ItemStack stack) {
      this.stack.set(index, stack);
      SacrificialAltar.trySendPacketUpdate(this.world, this.getPos(), this);
   }

   public int getInventoryStackLimit() {
      return 1;
   }

   public boolean isUsableByPlayer(EntityPlayer player) {
      return false;
   }

   public void openInventory(EntityPlayer player) {
   }

   public void closeInventory(EntityPlayer player) {
   }

   public boolean isItemValidForSlot(int index, ItemStack stack) {
      return stack.getItem() == ItemsRegister.SOULSTONE;
   }

   public int getField(int id) {
      return 0;
   }

   public void setField(int id, int value) {
   }

   public int getFieldCount() {
      return 0;
   }

   public void clear() {
      this.stack.set(0, ItemStack.EMPTY);
   }

   public String getName() {
      return "tile_sacrificial_altar";
   }

   public boolean hasCustomName() {
      return false;
   }

   public int[] getSlotsForFace(EnumFacing side) {
      return SLOT;
   }

   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
      return ((ItemStack)this.stack.get(0)).isEmpty() && this.isItemValidForSlot(index, itemStackIn) && direction != EnumFacing.DOWN;
   }

   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
      return SoulStone.getSoul((ItemStack)this.stack.get(0)) == 0 && direction == EnumFacing.DOWN;
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
