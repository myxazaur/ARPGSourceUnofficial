//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.Team;
import java.util.UUID;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextComponentString;

public class TileTeamBanner extends TileEntity {
   public String teamname;
   public String playername;
   public UUID playerUUID;
   public short rotation;
   public NetworkPlayerInfo savedPlayerInfo;

   public void joinNewPlayer(EntityPlayer player) {
      try {
         Scoreboard scoreboard = player.getWorldScoreboard();
         if (scoreboard.addPlayerToTeam(player.getName(), this.teamname)) {
            player.sendMessage(new TextComponentString("You joined to team of " + this.playername + "!"));
         } else {
            player.sendMessage(new TextComponentString("Cannot join to team of " + this.playername + " | Team does not exist"));
         }
      } catch (Exception var3) {
         player.sendMessage(new TextComponentString("Cannot join to team of " + this.playername + " | " + var3.getMessage()));
      }
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("playername")) {
         this.playername = compound.getString("playername");
         this.teamname = Team.nameOfPersonalTeam(this.playername);
         this.savedPlayerInfo = null;
      }

      if (compound.hasKey("rotation")) {
         this.rotation = compound.getShort("rotation");
      }

      if (compound.hasKey("playeruuidMost") && compound.hasKey("playeruuidLeast")) {
         this.playerUUID = compound.getUniqueId("playeruuid");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      if (this.playername != null) {
         compound.setString("playername", this.playername);
      }

      compound.setShort("rotation", this.rotation);
      if (this.playerUUID != null) {
         compound.setUniqueId("playeruuid", this.playerUUID);
      }

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
