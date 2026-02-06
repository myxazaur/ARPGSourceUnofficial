package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.network.PacketNeedTileSendToServer;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileShimmeringBeastbloom extends TileEntity implements ITickable {
   public int firstLeavesCount = 0;
   public int secndLeavesCount = 0;
   public float firstLeavesOffset = 0.0F;
   public float secndLeavesOffset = 0.0F;
   public float coreSize = 0.0F;
   public int growStage = 0;
   public boolean hasCore = false;
   public int opening = 0;

   public static TileShimmeringBeastbloom generateRandomBloom(Random rand) {
      TileShimmeringBeastbloom tile = new TileShimmeringBeastbloom();
      tile.firstLeavesCount = rand.nextInt(7) + 4;
      tile.secndLeavesCount = rand.nextInt(7) + 4;
      tile.firstLeavesOffset = rand.nextFloat() * 10.0F + 3.0F;
      tile.secndLeavesOffset = rand.nextFloat() * 11.0F + 6.0F;
      tile.coreSize = 0.7F + rand.nextFloat();
      tile.opening = 1;
      return tile;
   }

   public void update() {
      if (this.world.isRemote) {
         if (this.firstLeavesCount == 0) {
            PacketNeedTileSendToServer.send(this.getPos());
         }

         if (Minecraft.getMinecraft().player != null
            && Minecraft.getMinecraft().player.getDistanceSq(this.getPos()) < 16.0
            && this.opening == 0) {
            this.opening = 1;
            PacketNeedTileSendToServer.send(this.getPos());
         }

         if (this.opening > 100) {
            this.opening = -this.opening;
         } else if (this.opening != 0) {
            this.opening++;
         }
      }
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("fleavescount")) {
         this.firstLeavesCount = compound.getInteger("fleavescount");
      }

      if (compound.hasKey("sleavescount")) {
         this.secndLeavesCount = compound.getInteger("sleavescount");
      }

      if (compound.hasKey("fleavesoffset")) {
         this.firstLeavesOffset = compound.getFloat("fleavesoffset");
      }

      if (compound.hasKey("sleavesoffset")) {
         this.secndLeavesOffset = compound.getFloat("sleavesoffset");
      }

      if (compound.hasKey("coresize")) {
         this.coreSize = compound.getFloat("coresize");
      }

      if (compound.hasKey("growstage")) {
         this.growStage = compound.getInteger("growstage");
      }

      if (compound.hasKey("hascore")) {
         this.hasCore = compound.getBoolean("hascore");
      }

      if (compound.hasKey("opening")) {
         this.opening = compound.getInteger("opening");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      compound.setInteger("fleavescount", this.firstLeavesCount);
      compound.setInteger("sleavescount", this.secndLeavesCount);
      compound.setFloat("fleavesoffset", this.firstLeavesOffset);
      compound.setFloat("sleavesoffset", this.secndLeavesOffset);
      compound.setFloat("coresize", this.coreSize);
      compound.setInteger("growstage", this.growStage);
      compound.setBoolean("hascore", this.hasCore);
      compound.setInteger("opening", this.opening);
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
}
