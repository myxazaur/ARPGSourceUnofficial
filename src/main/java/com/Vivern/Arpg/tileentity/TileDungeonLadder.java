package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.DungeonTopLadder;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Sounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;

public class TileDungeonLadder extends TileEntity implements ITickable {
   public Block blockmaterial = Blocks.STONE;
   public EnumFacing face = EnumFacing.EAST;
   public Block laddermaterial = Blocks.LADDER;
   public int ladderStyle = 0;
   public ResourceLocation BLOCK_TEXTURES = new ResourceLocation("textures/blocks/stone.png");
   public ResourceLocation LADDER_TEXTURES = new ResourceLocation("textures/blocks/ladder.png");
   public Entity playerToTeleport;
   public int teleportTick = 0;
   public Vec3d positionToTeleport;
   public int playsoundTick = 0;

   public void update() {
      if (!this.world.isRemote) {
         if (this.playerToTeleport != null && this.positionToTeleport != null) {
            if (this.teleportTick > 0) {
               this.teleportTick--;
               DungeonTopLadder.doTeleportAgressively(
                  this.playerToTeleport,
                  this.positionToTeleport.x,
                  this.positionToTeleport.y,
                  this.positionToTeleport.z,
                  this.playerToTeleport.rotationYaw,
                  this.playerToTeleport.rotationPitch
               );
            } else {
               this.playerToTeleport = null;
               this.positionToTeleport = null;
            }
         }

         if (this.playsoundTick > 0) {
            this.playsoundTick--;
            if (this.playsoundTick == 0) {
               this.world
                  .playSound(
                     null,
                     this.pos.getX(),
                     this.pos.getY(),
                     this.pos.getZ(),
                     Sounds.dungeon_ladder,
                     SoundCategory.BLOCKS,
                     0.7F,
                     0.85F + GetMOP.rand.nextFloat() * 0.3F
                  );
            }
         }
      }
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("blockmaterial")) {
         this.blockmaterial = Block.getBlockFromName(compound.getString("blockmaterial"));
         if ("minecraft".equals(this.blockmaterial.getRegistryName().getNamespace())) {
            this.BLOCK_TEXTURES = new ResourceLocation("textures/blocks/" + this.blockmaterial.getRegistryName().getPath() + ".png");
         } else {
            this.BLOCK_TEXTURES = new ResourceLocation("arpg:textures/" + this.blockmaterial.getRegistryName().getPath() + ".png");
         }
      }

      if (compound.hasKey("laddermaterial")) {
         this.laddermaterial = Block.getBlockFromName(compound.getString("laddermaterial"));
         if ("minecraft".equals(this.laddermaterial.getRegistryName().getNamespace())) {
            this.LADDER_TEXTURES = new ResourceLocation("textures/blocks/" + this.laddermaterial.getRegistryName().getPath() + ".png");
         } else {
            this.LADDER_TEXTURES = new ResourceLocation("arpg:textures/" + this.laddermaterial.getRegistryName().getPath() + ".png");
         }
      }

      if (compound.hasKey("face")) {
         this.face = EnumFacing.byIndex(compound.getInteger("face"));
      }

      if (compound.hasKey("ladderStyle")) {
         this.ladderStyle = compound.getInteger("ladderStyle");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      compound.setString("blockmaterial", this.blockmaterial.getRegistryName().toString());
      compound.setString("laddermaterial", this.laddermaterial.getRegistryName().toString());
      compound.setInteger("face", this.face.getIndex());
      compound.setInteger("ladderStyle", this.ladderStyle);
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
