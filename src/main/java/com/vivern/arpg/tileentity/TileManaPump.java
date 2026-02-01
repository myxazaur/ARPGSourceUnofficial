package com.vivern.arpg.tileentity;

import com.vivern.arpg.main.GetMOP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileManaPump extends TileEntity implements ITickable, ITileEntitySynchronize {
   public EnumFacing facing;
   public float lastFlow;
   public float flow;

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         this.lastFlow = (float)args[0];
      }
   }

   public void update() {
      if (this.world.isRemote) {
         float followAmount = 0.75F / ManaBuffer.TICKRATE;
         this.flow = GetMOP.followNumber(this.flow, this.lastFlow, followAmount);
      }

      if (this.world.getWorldTime() % ManaBuffer.TICKRATE == 0L) {
         BlockPos buf1Pos = this.pos.offset(this.facing);
         BlockPos buf2Pos = this.pos.offset(this.facing.getOpposite());
         TileEntity tile1 = this.world.getTileEntity(buf1Pos);
         TileEntity tile2 = this.world.getTileEntity(buf2Pos);
         float lastUpdateChanges = 0.0F;
         if (tile1 instanceof IManaBuffer && tile2 instanceof IManaBuffer) {
            IManaBuffer ibuffer1 = (IManaBuffer)tile1;
            IManaBuffer ibuffer2 = (IManaBuffer)tile2;
            ManaBuffer buffer1 = ibuffer1.getManaBuffer();
            ManaBuffer buffer2 = ibuffer2.getManaBuffer();
            ManaBuffer.Calibration modifiers1 = ManaBuffer.getCalibrationAt(this.world, buf1Pos);
            ManaBuffer.Calibration modifiers2 = ManaBuffer.getCalibrationAt(this.world, buf2Pos);
            float flowSpeed = Math.min(buffer1.initialFlowSpeed + modifiers1.speed, buffer2.initialFlowSpeed + modifiers2.speed);
            flowSpeed = Math.min(flowSpeed, buffer2.getManaStorageSize() - buffer2.getManaStored());
            float mana = buffer1.provideMana(flowSpeed, buf2Pos);
            buffer2.addMana(mana);
            lastUpdateChanges = mana;
         }

         if (lastUpdateChanges != this.lastFlow) {
            this.lastFlow = lastUpdateChanges;
            ITileEntitySynchronize.sendSynchronize(this, 48.0, this.lastFlow);
         }
      }
   }

   public void write(NBTTagCompound compound) {
      compound.setByte("facing", (byte)this.facing.getIndex());
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("facing")) {
         this.facing = EnumFacing.byIndex(compound.getByte("facing"));
      } else {
         this.facing = EnumFacing.NORTH;
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
