package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.renders.IMagicVision;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class TileMagicGenerator extends TileEntity implements IManaBuffer, ITickable, IMagicVision {
   public EnergyStorage electricStorage;
   public ManaBuffer manaBuffer = new ManaBuffer(this, this, 40.0F, 6, 1.0F, 2.0F);

   public TileMagicGenerator() {
      this.electricStorage = new EnergyStorage(8000, 4000, 4000, 0);
   }

   public void update() {
      this.getManaBuffer().updateManaBuffer(this.world, this.pos);
      if (!this.world.isRemote) {
         if (this.getManaBuffer().getManaStored() >= 0.2F && this.electricStorage.receiveEnergy(200, true) == 200) {
            this.getManaBuffer().addMana(-0.2F);
            this.electricStorage.receiveEnergy(200, false);
         }

         outputEnergyAsGenerator(this, this.electricStorage, EnumFacing.EAST, 4000);
         outputEnergyAsGenerator(this, this.electricStorage, EnumFacing.WEST, 4000);
         outputEnergyAsGenerator(this, this.electricStorage, EnumFacing.SOUTH, 4000);
         outputEnergyAsGenerator(this, this.electricStorage, EnumFacing.NORTH, 4000);
         outputEnergyAsGenerator(this, this.electricStorage, EnumFacing.DOWN, 4000);
      }
   }

   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
      return capability == CapabilityEnergy.ENERGY && facing != EnumFacing.UP || super.hasCapability(capability, facing);
   }

   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
      return (T)(capability == CapabilityEnergy.ENERGY && facing != EnumFacing.UP ? this.electricStorage : super.getCapability(capability, facing));
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("rf")) {
         this.electricStorage = new EnergyStorage(8000, 4000, 4000, compound.getInteger("rf"));
      }

      this.manaBuffer.readFromNBT(compound);
      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      compound.setInteger("rf", this.electricStorage.getEnergyStored());
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

   public static int outputEnergyAsGenerator(TileEntity tile, EnergyStorage energyStorage, EnumFacing facing, int maxOutput) {
      if (energyStorage.canExtract()) {
         TileEntity tileoffset = tile.getWorld().getTileEntity(tile.getPos().offset(facing));
         if (tileoffset != null && tileoffset.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
            IEnergyStorage energystorageOffset = (IEnergyStorage)tileoffset.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
            if (energystorageOffset != null && energystorageOffset.canReceive()) {
               int accepted = energystorageOffset.receiveEnergy(maxOutput, true);
               int provided = energyStorage.extractEnergy(maxOutput, true);
               int finalValue = Math.min(accepted, provided);
               if (finalValue > 0) {
                  energyStorage.extractEnergy(finalValue, false);
                  energystorageOffset.receiveEnergy(finalValue, false);
                  return finalValue;
               }
            }
         }
      }

      return 0;
   }
}
