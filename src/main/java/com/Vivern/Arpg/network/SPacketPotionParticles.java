//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class SPacketPotionParticles extends Packet {
   @Override
   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
      PacketHandler.sendToAllAround(
         new CPacketPotionParticles(player.posX, player.posY, player.posZ),
         player.world,
         player.posX,
         player.posY,
         player.posZ,
         30.0
      );
   }
}
