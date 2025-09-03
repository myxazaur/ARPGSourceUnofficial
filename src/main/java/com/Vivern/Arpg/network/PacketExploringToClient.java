//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketExploringToClient extends Packet {
   NBTTagCompound spellsTagCompound;

   public void writeargs(NBTTagCompound spellsTagCompound) {
      writeCompoundTag(spellsTagCompound, this.buf());
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      try {
         this.spellsTagCompound = readCompoundTag(buffer);
      } catch (IOException var3) {
         var3.printStackTrace();
      }
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      if (this.spellsTagCompound != null) {
         FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
      }
   }

   void processMessage(EntityPlayer player) {
      try {
         NBTTagCompound entityData = player.getEntityData();
         entityData.setTag("arpg_spells", this.spellsTagCompound);
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
