//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.Coins;
import com.Vivern.Arpg.network.PacketHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileBank extends TileEntity implements TileEntityClicked {
   public int coins;

   @Override
   public void mouseClick(EntityLivingBase sender, int mouseX, int mouseY, int mouseButton) {
      if (sender != null
         && this.pos.distanceSq(sender.posX, sender.posY, sender.posZ) <= 64.0
         && sender instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)sender;
         if (mouseX > 0) {
            int coinsmoved = Math.min(Coins.getCoins(player), mouseX);
            if (coinsmoved > 0) {
               Coins.addCoins(player, -coinsmoved);
               this.coins += coinsmoved;
               PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 8.0);
            }
         } else if (mouseX < 0) {
            int var7 = -mouseX;
            var7 = Math.min(var7, this.coins);
            if (var7 > 0) {
               Coins.addCoins(player, var7);
               this.coins -= var7;
               PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 8.0);
            }
         }
      }
   }

   public void write(NBTTagCompound compound) {
      compound.setInteger("coins", this.coins);
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("coins")) {
         this.coins = compound.getInteger("coins");
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
