package com.vivern.arpg.network;

import com.vivern.arpg.elements.IWeapon;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketIWeaponGuiClickToServer extends Packet {
   public int mx = 0;
   public int my = 0;
   public int key = 0;
   public boolean isMessage;

   public void writeints(int mouseX, int mouseY, int mouseButton) {
      this.buf().writeInt(mouseX);
      this.buf().writeInt(mouseY);
      this.buf().writeInt(mouseButton);
      this.isMessage = true;
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.mx = buffer.readInt();
      this.my = buffer.readInt();
      this.key = buffer.readInt();
   }

   @Override
   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         ItemStack stack = player.getHeldItemMainhand();
         if (!stack.isEmpty() && stack.getItem() instanceof IWeapon) {
            ((IWeapon)stack.getItem()).guiClick(stack, player, this.mx, this.my, this.key);
         }
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
