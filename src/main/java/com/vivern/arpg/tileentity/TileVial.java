package com.vivern.arpg.tileentity;

import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.network.PacketHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class TileVial extends TileEntity {
   public ItemStack[] vials = new ItemStack[4];
   public int[][] positions = new int[4][2];
   public boolean[] rotations = new boolean[4];
   public AxisAlignedBB[] hitboxes = new AxisAlignedBB[4];
   public AxisAlignedBB fullHitbox;

   public boolean addNewVialItem(ItemStack stack, int posx, int posz, boolean rotated) {
      posx = MathHelper.clamp(posx, 0, 15);
      posz = MathHelper.clamp(posz, 0, 15);

      for (int i = 0; i < 4; i++) {
         if (this.vials[i] == null) {
            boolean emptyvial = stack.getItem() == ItemsRegister.VIALEMPTY;
            ShardType shardType = ShardType.byId(stack.getMetadata());
            if (shardType == null && !emptyvial) {
               return false;
            }

            AxisAlignedBB vialaabb = emptyvial
               ? ShardType.emptyVialBoundingBox.offset(posx / 16.0F, 0.0, posz / 16.0F)
               : shardType.vialBoundingBox.offset(posx / 16.0F, 0.0, posz / 16.0F);
            if (this.checkNoCollide(vialaabb)) {
               this.vials[i] = stack.splitStack(1);
               this.positions[i][0] = posx;
               this.positions[i][1] = posz;
               this.rotations[i] = rotated;
               this.hitboxes[i] = vialaabb;
               this.recalculateFullHitbox();
               PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
               return true;
            }

            return false;
         }
      }

      return false;
   }

   public boolean hasNoItems() {
      for (int i = 0; i < 4; i++) {
         if (this.vials[i] != null) {
            return false;
         }
      }

      return true;
   }

   public ItemStack removeVialItem(int number) {
      ItemStack itemStack = this.vials[number];
      this.vials[number] = null;
      this.hitboxes[number] = null;
      this.recalculateFullHitbox();
      return itemStack;
   }

   public void recalculateFullHitbox() {
      boolean isfirst = true;

      for (int i = 0; i < 4; i++) {
         AxisAlignedBB boxHas = this.hitboxes[i];
         if (boxHas != null) {
            if (isfirst) {
               this.fullHitbox = boxHas;
               isfirst = false;
            } else {
               this.fullHitbox = this.fullHitbox.union(boxHas);
            }
         }
      }

      if (isfirst) {
         this.fullHitbox = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.3125, 1.0);
      }
   }

   public boolean checkNoCollide(AxisAlignedBB vialaabb) {
      if (!(vialaabb.minX < 0.0) && !(vialaabb.minZ < 0.0) && !(vialaabb.maxX > 1.0) && !(vialaabb.maxZ > 1.0)) {
         for (AxisAlignedBB boxHas : this.hitboxes) {
            if (boxHas != null && boxHas.intersects(vialaabb)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void write(NBTTagCompound compound) {
      for (int i = 0; i < 4; i++) {
         ItemStack itemstack = this.vials[i];
         if (itemstack != null && !itemstack.isEmpty()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            itemstack.writeToNBT(nbttagcompound);
            compound.setTag("slot" + i, nbttagcompound);
         }
      }

      int allPoses = 0;

      for (int ix = 0; ix < 4; ix++) {
         int posx = MathHelper.clamp(this.positions[ix][0], 0, 15);
         int posz = MathHelper.clamp(this.positions[ix][1], 0, 15);
         int poses8bit = posx | posz << 4;
         allPoses |= poses8bit << ix * 8;
      }

      compound.setInteger("allPoses", allPoses);
      int allRotations = 0;

      for (int ix = 0; ix < 4; ix++) {
         if (this.rotations[ix]) {
            allRotations |= 1 << ix;
         }
      }

      compound.setByte("rotations", (byte)allRotations);
   }

   public void read(NBTTagCompound compound) {
      this.vials = new ItemStack[4];

      for (int i = 0; i < 4; i++) {
         String key = "slot" + i;
         if (compound.hasKey(key)) {
            NBTTagCompound nbttagcompound = compound.getCompoundTag(key);
            ItemStack itemstack = new ItemStack(nbttagcompound);
            if (!itemstack.isEmpty()) {
               this.vials[i] = itemstack;
            }
         }
      }

      this.positions = new int[4][2];
      if (compound.hasKey("allPoses")) {
         int allPoses = compound.getInteger("allPoses");

         for (int ix = 0; ix < 4; ix++) {
            int poses8bit = allPoses >>> ix * 8 & 0xFF;
            int posx = poses8bit & 15;
            int posz = poses8bit >>> 4 & 15;
            this.positions[ix][0] = posx;
            this.positions[ix][1] = posz;
         }
      }

      this.rotations = new boolean[4];
      if (compound.hasKey("rotations")) {
         byte allRotations = compound.getByte("rotations");

         for (int ix = 0; ix < 4; ix++) {
            this.rotations[ix] = (allRotations >>> ix & 1) != 0;
         }
      }

      this.hitboxes = new AxisAlignedBB[4];

      for (int ix = 0; ix < 4; ix++) {
         ItemStack itemstack = this.vials[ix];
         if (itemstack != null && !itemstack.isEmpty()) {
            ShardType shardType = ShardType.byId(itemstack.getMetadata());
            if (shardType != null) {
               AxisAlignedBB vialaabb = shardType.vialBoundingBox.offset(this.positions[ix][0] / 16.0F, 0.0, this.positions[ix][1] / 16.0F);
               this.hitboxes[ix] = vialaabb;
            } else {
               AxisAlignedBB vialaabb = ShardType.emptyVialBoundingBox.offset(this.positions[ix][0] / 16.0F, 0.0, this.positions[ix][1] / 16.0F);
               this.hitboxes[ix] = vialaabb;
            }
         }
      }

      this.recalculateFullHitbox();
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
