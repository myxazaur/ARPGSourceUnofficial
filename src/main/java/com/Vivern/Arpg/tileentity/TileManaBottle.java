package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.renders.IMagicVision;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileManaBottle extends TileEntity implements IManaBuffer, ITickable, IMagicVision {
   public static float CAPACITY_MANABOTTLE_TIER_1 = 1000.0F;
   public static float CAPACITY_MANABOTTLE_TIER_2 = 8000.0F;
   public static float CAPACITY_MANABOTTLE_TIER_3 = 50000.0F;
   public ManaBuffer manaBuffer = new ManaBuffer(this, this, CAPACITY_MANABOTTLE_TIER_1, 8, 0.0F, 1.0F);
   public float openness = 0.0F;
   public float prevopenness = 0.0F;
   public boolean opened;

   public void update() {
      if (this.opened) {
         this.manaBuffer.updateManaBuffer(this.world, this.pos);
      }

      if (this.world.isRemote) {
         this.prevopenness = this.openness;
         if (this.opened && this.openness < 1.0F) {
            this.openness += 0.2F;
         }

         if (!this.opened && this.openness > 0.0F) {
            this.openness -= 0.2F;
         }
      }
   }

   @Override
   public ManaBuffer getManaBuffer() {
      return this.manaBuffer;
   }

   @Override
   public boolean canProvideMana() {
      return this.opened;
   }

   public void read(NBTTagCompound compound) {
      this.getManaBuffer().readFromNBT(compound);
      if (compound.hasKey("max")) {
         this.getManaBuffer().setManaStorageSize(compound.getFloat("max"));
      }

      if (compound.hasKey("opened")) {
         this.opened = compound.getBoolean("opened");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      this.getManaBuffer().writeToNBT(compound);
      compound.setFloat("max", this.getManaBuffer().getManaStorageSize());
      compound.setBoolean("opened", this.opened);
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
   public float getElementEnergy(ShardType shardType) {
      return 0.0F;
   }

   @Override
   public float getMana() {
      return this.getManaBuffer().getManaStored();
   }

   @Override
   public float getManaStorageSize(World world, BlockPos pos) {
      return this.getManaBuffer().getManaStorageSize();
   }
}
