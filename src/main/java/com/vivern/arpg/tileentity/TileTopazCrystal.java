package com.vivern.arpg.tileentity;

import com.vivern.arpg.entity.EntityMagicUI;
import com.vivern.arpg.main.IMagicUI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileTopazCrystal extends TileEntity implements ITickable, IMagicUI {
   public float energyStored = 0.0F;
   public float energyProvideAtOnce = 1.0F;
   public BlockPos drainPos = null;
   public BlockPos givePos = null;
   public int ticksExisted = 0;

   @Override
   public void open(World world, EntityPlayer player, BlockPos pos, Entity entity) {
      if (!world.isRemote && IMagicUI.checkNoNearOpened(world, pos, null, 2)) {
         EntityMagicUI.EntityMUILink mui1 = new EntityMagicUI.EntityMUILink(world, pos);
         EntityMagicUI.EntityMUICloser muiC = new EntityMagicUI.EntityMUICloser(world).addALL(mui1);
         muiC.origin = pos;
         IMagicUI.spawnEntityMUIinRound(world, player, pos, null, 0.1, 0.6F, muiC, mui1);
      }
   }

   @Override
   public void onProvideLink(World world, EntityPlayer player, BlockPos thispos, Entity thisentity, BlockPos linkpos, Entity linkentity) {
      this.setPosToGive(linkpos);
   }

   public void setPosToDrain(BlockPos pos) {
      this.drainPos = pos;
   }

   public void setPosToGive(BlockPos pos) {
      this.givePos = pos;
   }

   public void update() {
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setFloat("stored", this.energyStored);
      if (this.givePos != null) {
         compound.setInteger("giveposx", this.givePos.getX());
         compound.setInteger("giveposy", this.givePos.getY());
         compound.setInteger("giveposz", this.givePos.getZ());
      }

      if (this.drainPos != null) {
         compound.setInteger("drainposx", this.drainPos.getX());
         compound.setInteger("drainposy", this.drainPos.getY());
         compound.setInteger("drainposz", this.drainPos.getZ());
      }

      return new SPacketUpdateTileEntity(this.pos, 1, compound);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      if (compound.hasKey("stored")) {
         this.energyStored = compound.getFloat("stored");
      }

      if (compound.hasKey("giveposx") && compound.hasKey("giveposy") && compound.hasKey("giveposz")) {
         this.givePos = new BlockPos(compound.getInteger("giveposx"), compound.getInteger("giveposy"), compound.getInteger("giveposz"));
      }

      if (compound.hasKey("drainposx") && compound.hasKey("drainposy") && compound.hasKey("drainposz")) {
         this.drainPos = new BlockPos(compound.getInteger("drainposx"), compound.getInteger("drainposy"), compound.getInteger("drainposz"));
      }
   }

   public NBTTagCompound getUpdateTag() {
      NBTTagCompound compound = super.getUpdateTag();
      compound.setFloat("stored", this.energyStored);
      if (this.givePos != null) {
         compound.setInteger("giveposx", this.givePos.getX());
         compound.setInteger("giveposy", this.givePos.getY());
         compound.setInteger("giveposz", this.givePos.getZ());
      }

      if (this.drainPos != null) {
         compound.setInteger("drainposx", this.drainPos.getX());
         compound.setInteger("drainposy", this.drainPos.getY());
         compound.setInteger("drainposz", this.drainPos.getZ());
      }

      return compound;
   }

   public void handleUpdateTag(NBTTagCompound compound) {
      if (compound.hasKey("stored")) {
         this.energyStored = compound.getFloat("stored");
      }

      if (compound.hasKey("giveposx") && compound.hasKey("giveposy") && compound.hasKey("giveposz")) {
         this.givePos = new BlockPos(compound.getInteger("giveposx"), compound.getInteger("giveposy"), compound.getInteger("giveposz"));
      }

      if (compound.hasKey("drainposx") && compound.hasKey("drainposy") && compound.hasKey("drainposz")) {
         this.drainPos = new BlockPos(compound.getInteger("drainposx"), compound.getInteger("drainposy"), compound.getInteger("drainposz"));
      }

      super.handleUpdateTag(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("stored")) {
         this.energyStored = compound.getFloat("stored");
      }

      if (compound.hasKey("giveposx") && compound.hasKey("giveposy") && compound.hasKey("giveposz")) {
         this.givePos = new BlockPos(compound.getInteger("giveposx"), compound.getInteger("giveposy"), compound.getInteger("giveposz"));
      }

      if (compound.hasKey("drainposx") && compound.hasKey("drainposy") && compound.hasKey("drainposz")) {
         this.drainPos = new BlockPos(compound.getInteger("drainposx"), compound.getInteger("drainposy"), compound.getInteger("drainposz"));
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setFloat("stored", this.energyStored);
      if (this.givePos != null) {
         compound.setInteger("giveposx", this.givePos.getX());
         compound.setInteger("giveposy", this.givePos.getY());
         compound.setInteger("giveposz", this.givePos.getZ());
      }

      if (this.drainPos != null) {
         compound.setInteger("drainposx", this.drainPos.getX());
         compound.setInteger("drainposy", this.drainPos.getY());
         compound.setInteger("drainposz", this.drainPos.getZ());
      }

      return super.writeToNBT(compound);
   }
}
