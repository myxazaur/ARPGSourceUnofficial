package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.container.Container1;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.renders.IMagicVision;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TileDisenchantmentTable extends TileEntityLockableLoot implements IManaBuffer, ITickable, IMagicVision {
   private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
   public float manaExtracted = 0.0F;
   public ManaBuffer manaBuffer = new ManaBuffer(this, this, 20.0F, 3, -0.5F, 2.0F);
   public int progress = 0;
   public int progressMax = 200;
   public Enchantment enchantmentRemoving;
   public int tickCount;
   public float bookSpread;
   public float bookSpreadPrev;
   public float bookRotation;
   public float bookRotationPrev;
   public float tRot;

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

   public void setInventorySlotContents(int index, ItemStack stack) {
      super.setInventorySlotContents(index, stack);
      this.progress = 0;
      this.enchantmentRemoving = null;
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

   public int getInventoryStackLimit() {
      return 1;
   }

   public String getName() {
      return "disenchantment_table";
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      this.fillWithLoot(playerIn);
      return new Container1(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.disenchantment_table";
   }

   protected NonNullList<ItemStack> getItems() {
      return this.stacks;
   }

   public void update() {
      this.manaBuffer.updateManaBuffer(this.world, this.pos);
      if (this.world.isRemote) {
         this.updateRotation();
      } else {
         if (this.enchantmentRemoving == null) {
            ItemStack stack = this.getStackInSlot(0);
            if (stack.isItemEnchanted()) {
               Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
               int randI = GetMOP.rand.nextInt(map.size());
               int i = 0;

               for (Enchantment enchantment : map.keySet()) {
                  if (i == randI) {
                     this.enchantmentRemoving = enchantment;
                     break;
                  }

                  i++;
               }
            } else if (this.progress > 0) {
               this.progress = 0;
               PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 8.0);
            }
         }

         if (this.getManaBuffer().getManaStored() < this.getManaBuffer().getManaStorageSize()) {
            if (this.enchantmentRemoving != null) {
               this.progress++;
               if (this.progress >= this.progressMax) {
                  ItemStack stack = this.getStackInSlot(0);
                  int lvl = EnchantmentHelper.getEnchantmentLevel(this.enchantmentRemoving, stack);
                  if (lvl > 0) {
                     Rarity rarity = this.enchantmentRemoving.getRarity();
                     float manaReleased = 0.0F;
                     if (this.enchantmentRemoving == EnchantmentInit.SPECIAL) {
                        manaReleased = 500.0F;
                     } else if (rarity == Rarity.COMMON) {
                        manaReleased = 25.0F;
                     } else if (rarity == Rarity.UNCOMMON) {
                        manaReleased = 40.0F;
                     } else if (rarity == Rarity.RARE) {
                        manaReleased = 125.0F;
                     } else if (rarity == Rarity.VERY_RARE) {
                        manaReleased = 350.0F;
                     }

                     this.manaExtracted = this.manaExtracted + manaReleased * Math.max(lvl - 1, 1);
                     Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
                     if (lvl - 1 <= 0) {
                        map.remove(this.enchantmentRemoving);
                        this.enchantmentRemoving = null;
                     } else {
                        map.put(this.enchantmentRemoving, lvl - 1);
                     }

                     EnchantmentHelper.setEnchantments(map, stack);
                  } else {
                     this.enchantmentRemoving = null;
                  }

                  this.progress = 0;
               }

               PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 8.0);
            }

            float manaConverted = this.manaExtracted <= 0.5F ? this.manaExtracted : this.manaExtracted / Math.max(this.progressMax - this.progress, 1);
            float manaAdd = Math.min(manaConverted, this.getManaBuffer().getManaStorageSize() - this.getManaBuffer().getManaStored());
            this.getManaBuffer().addMana(manaAdd);
            this.manaExtracted -= manaAdd;
         }
      }
   }

   public void updateRotation() {
      this.bookSpreadPrev = this.bookSpread;
      this.bookRotationPrev = this.bookRotation;
      EntityPlayer entityplayer = this.world
         .getClosestPlayer(
            this.pos.getX() + 0.5F, this.pos.getY() + 0.5F, this.pos.getZ() + 0.5F, 3.0, false
         );
      if (entityplayer != null) {
         double d0 = entityplayer.posX - (this.pos.getX() + 0.5F);
         double d1 = entityplayer.posZ - (this.pos.getZ() + 0.5F);
         this.tRot = (float)MathHelper.atan2(d1, d0);
         this.bookSpread += 0.1F;
      } else {
         this.tRot += 0.02F;
         this.bookSpread -= 0.1F;
      }

      while (this.bookRotation >= (float) Math.PI) {
         this.bookRotation -= (float) (Math.PI * 2);
      }

      while (this.bookRotation < (float) -Math.PI) {
         this.bookRotation += (float) (Math.PI * 2);
      }

      while (this.tRot >= (float) Math.PI) {
         this.tRot -= (float) (Math.PI * 2);
      }

      while (this.tRot < (float) -Math.PI) {
         this.tRot += (float) (Math.PI * 2);
      }

      float f2 = this.tRot - this.bookRotation;

      while (f2 >= (float) Math.PI) {
         f2 -= (float) (Math.PI * 2);
      }

      while (f2 < (float) -Math.PI) {
         f2 += (float) (Math.PI * 2);
      }

      this.bookRotation += f2 * 0.4F;
      this.bookSpread = MathHelper.clamp(this.bookSpread, 0.0F, 1.0F);
      this.tickCount++;
   }

   public void read(NBTTagCompound compound) {
      this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      if (!this.checkLootAndRead(compound)) {
         ItemStackHelper.loadAllItems(compound, this.stacks);
      }

      if (compound.hasKey("CustomName", 8)) {
         this.customName = compound.getString("CustomName");
      }

      this.manaBuffer.readFromNBT(compound);
      if (compound.hasKey("progress")) {
         this.progress = compound.getInteger("progress");
      }

      if (compound.hasKey("manaExtracted")) {
         this.manaExtracted = compound.getFloat("manaExtracted");
      }

      if (compound.hasKey("enchantId")) {
         this.enchantmentRemoving = Enchantment.getEnchantmentByID(compound.getInteger("enchantId"));
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      if (!this.checkLootAndWrite(compound)) {
         ItemStackHelper.saveAllItems(compound, this.stacks);
      }

      if (this.hasCustomName()) {
         compound.setString("CustomName", this.customName);
      }

      this.manaBuffer.writeToNBT(compound);
      compound.setInteger("progress", this.progress);
      compound.setFloat("manaExtracted", this.manaExtracted);
      compound.setInteger("enchantId", Enchantment.getEnchantmentID(this.enchantmentRemoving));
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

   public int getField(int id) {
      if (id == 0) {
         return this.progress;
      } else if (id == 1) {
         return this.progressMax;
      } else if (id == 2) {
         return this.enchantmentRemoving != null ? Enchantment.getEnchantmentID(this.enchantmentRemoving) : -1;
      } else {
         return super.getField(id);
      }
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
