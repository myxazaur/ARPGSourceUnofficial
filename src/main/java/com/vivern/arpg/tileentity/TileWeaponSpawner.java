package com.vivern.arpg.tileentity;

import com.vivern.arpg.main.FindAmmo;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;

public class TileWeaponSpawner extends TileEntityLockableLoot implements ITickable, TileEntityClicked {
   public static Random rand = new Random();
   public NonNullList<ItemStack> stacks = NonNullList.withSize(5, ItemStack.EMPTY);
   public ItemStack[] spawn = new ItemStack[]{
      ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY
   };
   public HashMap<String, Integer> cooldowns = new HashMap<>();
   public boolean onlyOnePrimaryItem = true;
   public boolean randomOneSecondItem = false;
   public boolean hasSecondItemLimit = false;
   public boolean playerRelativeCooldown = false;
   public int maxCooldown = 100;
   public int secondItemLimit = 64;
   public int redstone = 0;
   public int cooldown = 0;
   public int cooldown2 = 0;

   @Override
   public void mouseClick(int mouseX, int mouseY, int mouseButton) {
   }

   public int getSizeInventory() {
      return 5;
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
      this.spawnclear();
      this.cooldown = this.maxCooldown;
   }

   public void spawnclear() {
      this.spawn[0] = ItemStack.EMPTY;
      this.spawn[1] = ItemStack.EMPTY;
      this.spawn[2] = ItemStack.EMPTY;
      this.spawn[3] = ItemStack.EMPTY;
      this.spawn[4] = ItemStack.EMPTY;
   }

   public boolean spawnempty() {
      return this.spawn[0].isEmpty()
         && this.spawn[1].isEmpty()
         && this.spawn[2].isEmpty()
         && this.spawn[3].isEmpty()
         && this.spawn[4].isEmpty();
   }

   public void update() {
      if (!this.world.isRemote && !this.playerRelativeCooldown) {
         if (this.cooldown <= 0) {
            this.cooldown2++;
            if (this.cooldown2 > this.maxCooldown) {
               this.spawnclear();
               this.cooldown2 = 0;
            }

            if (this.spawnempty()) {
               if (this.randomOneSecondItem) {
                  this.spawn[0] = ((ItemStack)this.stacks.get(0)).copy();
                  this.spawn[1] = ((ItemStack)this.stacks.get(rand.nextInt(4) + 1)).copy();
               } else {
                  for (int i = 0; i < 5; i++) {
                     ItemStack stack = (ItemStack)this.stacks.get(i);
                     if (!stack.isEmpty()) {
                        this.spawn[i] = stack.copy();
                     }
                  }
               }
            }

            if (!this.spawnempty()) {
               AxisAlignedBB aabb = new AxisAlignedBB(
                  this.getPos().getX(),
                  this.getPos().getY(),
                  this.getPos().getZ(),
                  this.getPos().getX() + 1,
                  this.getPos().getY() + 3,
                  this.getPos().getZ() + 1
               );
               List<EntityPlayer> listpl = this.world.getEntitiesWithinAABB(EntityPlayer.class, aabb);
               if (!listpl.isEmpty()) {
                  for (EntityPlayer player : listpl) {
                     boolean spawnChanged = false;
                     if (!this.onlyOnePrimaryItem && !this.hasSecondItemLimit) {
                        for (ItemStack stack : this.spawn) {
                           if (stack != null && !stack.isEmpty()) {
                              player.addItemStackToInventory(stack);
                           }
                        }

                        spawnChanged = true;
                     } else {
                        if (!this.spawn[0].isEmpty() && (!this.onlyOnePrimaryItem || !this.playerhasItem(player, this.spawn[0].getItem()))) {
                           player.addItemStackToInventory(this.spawn[0]);
                           spawnChanged = true;
                        }

                        for (int ix = 1; ix < 5; ix++) {
                           ItemStack stackx = this.spawn[ix];
                           if (!stackx.isEmpty()) {
                              int count = this.hasSecondItemLimit ? FindAmmo.Find(player.inventory, stackx.getItem()) : -1;
                              if (count < this.secondItemLimit) {
                                 ItemStack stackresult = stackx.copy();
                                 stackresult.setCount(Math.min(stackx.getCount(), this.secondItemLimit - count));
                                 player.addItemStackToInventory(stackresult);
                                 spawnChanged = true;
                              }
                           }
                        }
                     }

                     if (spawnChanged) {
                        this.spawnclear();
                        this.cooldown = this.maxCooldown;
                        break;
                     }
                  }
               }
            }
         } else {
            this.cooldown--;
         }
      }
   }

   public boolean playerhasItem(EntityPlayer player, Item item) {
      for (ItemStack st : player.inventory.mainInventory) {
         if (item == st.getItem()) {
            return true;
         }
      }

      for (ItemStack stx : player.inventory.armorInventory) {
         if (item == stx.getItem()) {
            return true;
         }
      }

      for (ItemStack stxx : player.inventory.offHandInventory) {
         if (item == stxx.getItem()) {
            return true;
         }
      }

      return false;
   }

   public void read(NBTTagCompound compound) {
      this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      if (!this.checkLootAndRead(compound)) {
         ItemStackHelper.loadAllItems(compound, this.stacks);
      }

      if (compound.hasKey("onePrimItem")) {
         this.onlyOnePrimaryItem = compound.getBoolean("onePrimItem");
      }

      if (compound.hasKey("randomSecItem")) {
         this.randomOneSecondItem = compound.getBoolean("randomSecItem");
      }

      if (compound.hasKey("hasSecItemLimit")) {
         this.hasSecondItemLimit = compound.getBoolean("hasSecItemLimit");
      }

      if (compound.hasKey("playerRelativeCooldown")) {
         this.playerRelativeCooldown = compound.getBoolean("playerRelativeCooldown");
      }

      if (compound.hasKey("secItemLimit")) {
         this.secondItemLimit = compound.getInteger("secItemLimit");
      }

      if (compound.hasKey("maxCooldown")) {
         this.maxCooldown = compound.getInteger("maxCooldown");
      }

      if (compound.hasKey("cooldown")) {
         this.cooldown = compound.getInteger("cooldown");
      }

      if (compound.hasKey("redstone")) {
         this.redstone = compound.getBoolean("redstone") ? 1 : 0;
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      if (!this.checkLootAndWrite(compound)) {
         ItemStackHelper.saveAllItems(compound, this.stacks);
      }

      compound.setBoolean("onePrimItem", this.onlyOnePrimaryItem);
      compound.setBoolean("randomSecItem", this.randomOneSecondItem);
      compound.setBoolean("hasSecItemLimit", this.hasSecondItemLimit);
      compound.setBoolean("playerRelativeCooldown", this.playerRelativeCooldown);
      compound.setInteger("secItemLimit", this.secondItemLimit);
      compound.setInteger("maxCooldown", this.maxCooldown);
      compound.setInteger("cooldown", this.cooldown);
      compound.setBoolean("redstone", this.redstone > 0);
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

   public int getInventoryStackLimit() {
      return 64;
   }

   public String getName() {
      return "tile_weapon_spawner";
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerHopper(playerInventory, this, playerIn);
   }

   public String getGuiID() {
      return "minecraft:hopper";
   }

   protected NonNullList<ItemStack> getItems() {
      return this.stacks;
   }
}
