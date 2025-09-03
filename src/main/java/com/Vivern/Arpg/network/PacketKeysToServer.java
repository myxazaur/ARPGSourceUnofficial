//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.potions.Freezing;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class PacketKeysToServer extends Packet {
   public int k = 0;
   public boolean isMessage;

   public void writeint(int keys) {
      this.buf().writeInt(keys);
      this.isMessage = true;
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.k = buffer.readInt();
   }

   @Override
   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         if (Freezing.onKeysChange(player, this.k)) {
            player.getDataManager().set(PropertiesRegistry.KEYS_PRESSED, this.k);
         } else {
            player.getDataManager().set(PropertiesRegistry.KEYS_PRESSED, 0);
         }
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
