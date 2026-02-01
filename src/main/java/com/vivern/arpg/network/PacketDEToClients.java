package com.vivern.arpg.network;

import com.vivern.arpg.main.DeathEffect;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketDEToClients extends Packet {
   int entityId = 0;
   int effect = 0;

   public void writeargs(int entityId, int effect) {
      this.buf().writeInt(entityId);
      this.buf().writeInt(effect);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.entityId = buffer.readInt();
      this.effect = buffer.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player.world));
   }

   void processMessage(World world) {
      try {
         if (this.effect > 0) {
            DeathEffect de = DeathEffect.registry.get(this.effect);
            if (de != null) {
               Entity entity = world.getEntityByID(this.entityId);
               if (entity != null && entity instanceof EntityLivingBase) {
                  de.add((EntityLivingBase)entity);
               }
            }
         }
      } catch (ConcurrentModificationException var4) {
         var4.printStackTrace();
      }
   }
}
