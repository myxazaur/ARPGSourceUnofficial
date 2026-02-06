package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.ItemsElements;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.network.PacketHandler;
import java.util.ArrayList;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class TileBookcase extends TileEntity {
   public int[] booksGems = new int[]{50, 50, 50};
   public int booksVariant = 0;
   public int rotation;
   public static Random rand = new Random();
   public NonNullList<ItemStack> stacks = NonNullList.withSize(3, ItemStack.EMPTY);

   public TileBookcase() {
      this.booksVariant = rand.nextInt(7) + 1;
   }

   public boolean hasBooks() {
      for (int i = 0; i < 3; i++) {
         if (!((ItemStack)this.stacks.get(i)).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   public ItemsElements.ElementsPack getInformation(ItemStack stack) {
      ArrayList<ItemStack> stacksInBooks = new ArrayList<>();

      for (int i = 0; i < 3; i++) {
         ItemStack book = (ItemStack)this.stacks.get(i);
         if (!book.isEmpty()) {
            NBTTagList tagList = NBTHelper.GetNbtTagList(book, "pages", 10);
            if (!tagList.isEmpty()) {
               for (NBTBase base : tagList) {
                  if (base instanceof NBTTagCompound) {
                     NBTTagCompound tagCompound = (NBTTagCompound)base;
                     if (tagCompound.hasKey("item") && tagCompound.hasKey("metadata")) {
                        Item item = Item.getByNameOrId(tagCompound.getString("item"));
                        if (item != null) {
                           stacksInBooks.add(new ItemStack(item, 1, tagCompound.getInteger("metadata")));
                        }
                     }
                  }
               }
            }
         }
      }

      for (ItemStack writtenStack : stacksInBooks) {
         if (stack.getItem() == writtenStack.getItem()) {
            if (stack.getMetadata() == writtenStack.getMetadata()) {
               return ItemsElements.getAllElements(stack);
            }

            ItemsElements.ElementsPack elementsInCase = ItemsElements.getAllElements(writtenStack);
            ItemsElements.ElementsPack elementsAnalyzed = ItemsElements.getAllElements(stack);
            if (elementsAnalyzed == elementsInCase && elementsAnalyzed != ItemsElements.EMPTY_ELEMENTS) {
               return elementsAnalyzed;
            }
         }
      }

      return null;
   }

   public boolean addBook(ItemStack book) {
      for (int i = 0; i < 3; i++) {
         if (this.booksGems[i] == 50) {
            int gem = NBTHelper.GetNBTint(book, "gem");
            this.booksGems[i] = gem;
            this.stacks.set(i, book.copy());
            PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
            return true;
         }
      }

      return false;
   }

   public void write(NBTTagCompound compound) {
      byte b0 = (byte)this.booksGems[0];
      byte b1 = (byte)this.booksGems[1];
      byte b2 = (byte)this.booksGems[2];
      byte b3 = (byte)this.booksVariant;
      int gemsAndVariant = b0 | b1 << 8 | b2 << 16 | b3 << 24;
      compound.setInteger("gemsAndVariant", gemsAndVariant);

      for (int i = 0; i < 3; i++) {
         ItemStack stack = (ItemStack)this.stacks.get(i);
         if (!stack.isEmpty() && stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound().copy();
            tag.removeTag("gem");
            compound.setTag("book" + i, tag);
         }
      }
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("gemsAndVariant")) {
         int gemsAndVariant = compound.getInteger("gemsAndVariant");
         this.booksVariant = gemsAndVariant >>> 24 & 0xFF;
         this.booksGems[2] = gemsAndVariant >>> 16 & 0xFF;
         this.booksGems[1] = gemsAndVariant >>> 8 & 0xFF;
         this.booksGems[0] = gemsAndVariant & 0xFF;
      }

      this.stacks = NonNullList.withSize(3, ItemStack.EMPTY);

      for (int i = 0; i < 3; i++) {
         String tagname = "book" + i;
         if (compound.hasKey(tagname, 10)) {
            NBTTagCompound tag = compound.getCompoundTag(tagname);
            ItemStack itemStack = new ItemStack(ItemsRegister.BOOKOFELEMENTS);
            tag.setInteger("gem", this.booksGems[i]);
            itemStack.setTagCompound(tag);
            this.stacks.set(i, itemStack);
         }
      }
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
}
