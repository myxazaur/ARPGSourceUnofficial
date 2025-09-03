//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileAdvancedBlockDetector extends TileEntity {
   public IBlockState state = null;
   public boolean detectOnlyBlock = false;
   public ItemStack stackForRender = ItemStack.EMPTY;

   public String getChatMessage() {
      String st = "";
      if (this.state != null) {
         st = st + "Detecting block: " + this.state.getBlock().getLocalizedName();
      } else {
         st = st + "No detecting block selected";
      }

      if (this.detectOnlyBlock) {
         st = st + " | metadata is ignored";
      } else if (this.state != null) {
         st = st + " | with metadata " + this.state.getBlock().getMetaFromState(this.state);
      } else {
         st = st + " | metadata is used";
      }

      return st;
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound tags = new NBTTagCompound();
      if (this.state != null) {
         tags.setInteger("meta", this.state.getBlock().getMetaFromState(this.state));
         tags.setString("blockname", this.state.getBlock().getRegistryName().toString());
      }

      tags.setBoolean("dontdetectmeta", this.detectOnlyBlock);
      return new SPacketUpdateTileEntity(this.pos, 1, tags);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      if (compound.hasKey("meta") && compound.hasKey("blockname")) {
         this.setState(Block.getBlockFromName(compound.getString("blockname")).getStateFromMeta(compound.getInteger("meta")));
      }

      if (compound.hasKey("dontdetectmeta")) {
         this.detectOnlyBlock = compound.getBoolean("dontdetectmeta");
      }
   }

   public NBTTagCompound getUpdateTag() {
      NBTTagCompound tags = super.getUpdateTag();
      if (this.state != null) {
         tags.setInteger("meta", this.state.getBlock().getMetaFromState(this.state));
         tags.setString("blockname", this.state.getBlock().getRegistryName().toString());
      }

      tags.setBoolean("dontdetectmeta", this.detectOnlyBlock);
      return tags;
   }

   public void handleUpdateTag(NBTTagCompound compound) {
      if (compound.hasKey("meta") && compound.hasKey("blockname")) {
         this.setState(Block.getBlockFromName(compound.getString("blockname")).getStateFromMeta(compound.getInteger("meta")));
      }

      if (compound.hasKey("dontdetectmeta")) {
         this.detectOnlyBlock = compound.getBoolean("dontdetectmeta");
      }

      super.handleUpdateTag(compound);
   }

   @SideOnly(Side.CLIENT)
   public double getMaxRenderDistanceSquared() {
      return 1024.0;
   }

   public void setState(IBlockState iblockstate) {
      this.state = iblockstate;
      ItemStack stack = new ItemStack(Item.getItemFromBlock(iblockstate.getBlock()));
      stack.setItemDamage(iblockstate.getBlock().damageDropped(iblockstate));
      this.stackForRender = stack;
   }

   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("meta") && compound.hasKey("blockname")) {
         this.setState(Block.getBlockFromName(compound.getString("blockname")).getStateFromMeta(compound.getInteger("meta")));
      }

      if (compound.hasKey("dontdetectmeta")) {
         this.detectOnlyBlock = compound.getBoolean("dontdetectmeta");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      if (this.state != null) {
         compound.setInteger("meta", this.state.getBlock().getMetaFromState(this.state));
         compound.setString("blockname", this.state.getBlock().getRegistryName().toString());
      }

      compound.setBoolean("dontdetectmeta", this.detectOnlyBlock);
      return super.writeToNBT(compound);
   }
}
