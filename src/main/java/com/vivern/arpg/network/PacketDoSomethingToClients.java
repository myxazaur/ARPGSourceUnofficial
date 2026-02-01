package com.vivern.arpg.network;

import com.vivern.arpg.arpgamemodes.SurvivorGamestyleWatcher;
import com.vivern.arpg.elements.BaublesPack;
import com.vivern.arpg.entity.EntityGeomanticCrystal;
import com.vivern.arpg.mobs.EntityAIArcAttack;
import com.vivern.arpg.potions.RespawnPenalty;
import com.vivern.arpg.renders.KillScore;
import com.vivern.arpg.renders.ManaBar;
import com.vivern.arpg.tileentity.TilePyrocrystallineConverter;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketDoSomethingToClients extends Packet {
   double x = 0.0;
   double y = 0.0;
   double z = 0.0;
   double a = 0.0;
   double b = 0.0;
   double c = 0.0;
   int id = 0;

   public static void send(EntityPlayer playerTo, double x, double y, double z, double a, double b, double c, int id) {
      if (playerTo instanceof EntityPlayerMP) {
         PacketDoSomethingToClients packet = new PacketDoSomethingToClients();
         packet.writeargs(x, y, z, a, b, c, id);
         PacketHandler.sendTo(packet, (EntityPlayerMP)playerTo);
      }
   }

   public void writeargs(double x, double y, double z, double a, double b, double c, int id) {
      this.buf().writeDouble(x);
      this.buf().writeDouble(y);
      this.buf().writeDouble(z);
      this.buf().writeDouble(a);
      this.buf().writeDouble(b);
      this.buf().writeDouble(c);
      this.buf().writeInt(id);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.x = buffer.readDouble();
      this.y = buffer.readDouble();
      this.z = buffer.readDouble();
      this.a = buffer.readDouble();
      this.b = buffer.readDouble();
      this.c = buffer.readDouble();
      this.id = buffer.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player.world));
   }

   void processMessage(World world) {
      try {
         if (this.id == 1) {
         }

         if (this.id == 2 && world.isRemote) {
            Entity entity = world.getEntityByID((int)this.c);
            if (entity != null && entity instanceof EntityGeomanticCrystal) {
               EntityGeomanticCrystal cr = (EntityGeomanticCrystal)entity;
               cr.setClientStackColors((int)this.x, (int)this.y, (int)this.z, (int)this.a);
            }
         }

         if (this.id == 3) {
            TileEntity tile = world.getTileEntity(new BlockPos(this.a, this.b, this.c));
            if (tile != null && tile instanceof TilePyrocrystallineConverter) {
               ((TilePyrocrystallineConverter)tile).spawnParticles(this.x, this.y, this.z);
            }
         }

         if (this.id == 4) {
            BaublesPack.SpringerWaistband.particle(world, this.x, this.y, this.z);
         }

         if (this.id == 5) {
            EntityAIArcAttack.spawnParticle(world, (int)this.x, (float)this.y, (int)this.z, (float)this.a, (int)this.b);
         }

         if (this.id == 6) {
            RespawnPenalty.clientDelayCounter = (int)this.x;
         }

         if (this.id == 7) {
            BaublesPack.EnderCrown.effect(world, this.x, this.y, this.z, this.a, this.b, this.c);
         }

         if (this.id == 8 && world.isRemote) {
            ManaBar.dungeonLadderAnim = ManaBar.dungeonLadderAnimMax;
         }

         if (this.id == 9) {
            SurvivorGamestyleWatcher.onClient(this.x, this.y, this.z, this.a, this.b, this.c);
         }

         if (this.id == 10) {
            if (this.x > 0.0) {
               KillScore.currentDPS.damage = KillScore.currentDPS.damage + (float)this.x;
               KillScore.DPStimeCounter = 0;
            }

            KillScore.kills = KillScore.kills + (int)this.y;
            KillScore.eliteKills = KillScore.eliteKills + (int)this.z;
         }
      } catch (ConcurrentModificationException var4) {
         var4.printStackTrace();
      }
   }
}
