package com.Vivern.Arpg.network;

import com.Vivern.Arpg.elements.IWeapon;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketIWeaponStringToServer extends Packet {
   public String str;

   public void write(String str) {
      ByteBufUtils.writeUTF8String(this.buf(), str);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.str = ByteBufUtils.readUTF8String(buffer);
   }

   @Override
   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         ItemStack stack = player.getHeldItemMainhand();
         if (!stack.isEmpty() && stack.getItem() instanceof IWeapon) {
            ((IWeapon)stack.getItem()).receiveClientString(stack, player, this.str);
         }
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
