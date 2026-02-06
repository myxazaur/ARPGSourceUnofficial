package com.Vivern.Arpg.network;

import com.Vivern.Arpg.elements.GraplingHook;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGrapplingHookToClients extends Packet {
   double x = 0.0;
   double y = 0.0;
   double z = 0.0;
   int entityid = 0;
   int itemid = 0;

   public static void send(EntityPlayer player, double x, double y, double z, Item graplingHook) {
      PacketGrapplingHookToClients packet = new PacketGrapplingHookToClients();
      packet.writeargs(x, y, z, player.getEntityId(), Item.getIdFromItem(graplingHook));
      PacketHandler.sendToAllAround(packet, player.world, player.posX, player.posY, player.posZ, 64.0);
   }

   public void writeargs(double x, double y, double z, int identity, int iditem) {
      this.buf().writeDouble(x);
      this.buf().writeDouble(y);
      this.buf().writeDouble(z);
      this.buf().writeInt(identity);
      this.buf().writeInt(iditem);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.x = buffer.readDouble();
      this.y = buffer.readDouble();
      this.z = buffer.readDouble();
      this.entityid = buffer.readInt();
      this.itemid = buffer.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player.world));
   }

   void processMessage(World world) {
      try {
         Entity entity = world.getEntityByID(this.entityid);
         if (entity != null && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            Item item = Item.getItemById(this.itemid);
            if (item != null && item instanceof GraplingHook) {
               ((GraplingHook)item).spawnParticle(player.getPositionEyes(0.0F), new Vec3d(this.x, this.y, this.z), world, player);
            }
         }
      } catch (ConcurrentModificationException var5) {
         var5.printStackTrace();
      }
   }
}
