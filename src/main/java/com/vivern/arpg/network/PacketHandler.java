package com.vivern.arpg.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
   public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("arpg_network");
   private static int ID = 0;

   public static void packetHandlerRegister() {
      register(SPacketPotionParticles.class, Side.SERVER);
      register(CPacketPotionParticles.class, Side.CLIENT);
      register(PacketKeysToServer.class, Side.SERVER);
      register(PacketWeaponEffectToClients.class, Side.CLIENT);
      register(PacketTileClickToServer.class, Side.SERVER);
      register(PacketTraderClickToServer.class, Side.SERVER);
      register(PacketTradesToClient.class, Side.CLIENT);
      register(PacketCoinToServer.class, Side.SERVER);
      register(PacketCoinToClient.class, Side.CLIENT);
      register(PacketIESToClients.class, Side.CLIENT);
      register(PacketBulletEffectToClients.class, Side.CLIENT);
      register(PacketDoSomethingToClients.class, Side.CLIENT);
      register(PacketWhipToClients.class, Side.CLIENT);
      register(PacketNeedTileSendToServer.class, Side.SERVER);
      register(PacketAdvPotionToClients.class, Side.CLIENT);
      register(PacketMUIClickToServer.class, Side.SERVER);
      register(PacketMUIOpenToServer.class, Side.SERVER);
      register(PacketNexusInfoToClients.class, Side.CLIENT);
      register(PacketAnimationsToClients.class, Side.CLIENT);
      register(PacketFallingBlockToClients.class, Side.CLIENT);
      register(PacketItemBoomToClient.class, Side.CLIENT);
      register(PacketIWeaponGuiClickToServer.class, Side.SERVER);
      register(PacketIWeaponStringToServer.class, Side.SERVER);
      register(PacketSmallSomethingToClients.class, Side.CLIENT);
      register(PacketDEToClients.class, Side.CLIENT);
      register(PacketInfluenceToClients.class, Side.CLIENT);
      register(PacketTileEntityToClients.class, Side.CLIENT);
      register(PacketBaublesNbtToClients.class, Side.CLIENT);
      register(PacketTileEntityToServer.class, Side.SERVER);
      register(PacketWeaponStateToClients.class, Side.CLIENT);
      register(PacketManaBufferToClients.class, Side.CLIENT);
      register(PacketTFRPuzzleToClients.class, Side.CLIENT);
      register(PacketTFRPuzzleToServer.class, Side.SERVER);
      register(PacketExploringToServer.class, Side.SERVER);
      register(PacketExploringToClient.class, Side.CLIENT);
      register(PacketWorldEventToClients.class, Side.CLIENT);
      register(PacketEntityPositionToClients.class, Side.CLIENT);
      register(PacketDoSomethingToServer.class, Side.SERVER);
      register(PacketGrapplingHookToClients.class, Side.CLIENT);
   }

   private static int nextID() {
      return ID++;
   }

   public static void sendToAllAround(Packet packet, World world, double x, double y, double z, double distance) {
      NETWORK.sendToAllAround(packet, new TargetPoint(world.provider.getDimension(), x, y, z, distance));
   }

   public static void sendToAllAroundExcluding(Packet packet, World world, double x, double y, double z, double distance, EntityPlayer excluding) {
      for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
         if (player.dimension == world.provider.getDimension()) {
            double d4 = x - player.posX;
            double d5 = y - player.posY;
            double d6 = z - player.posZ;
            if (d4 * d4 + d5 * d5 + d6 * d6 < distance * distance && player != excluding) {
               NETWORK.sendTo(packet, player);
            }
         }
      }
   }

   public static void sendTo(Packet packet, EntityPlayerMP player) {
      NETWORK.sendTo(packet, player);
   }

   public static void sendToDimension(Packet packet, int dimension) {
      NETWORK.sendToDimension(packet, dimension);
   }

   private static void register(Class<? extends Packet> packet, Side side) {
      try {
         NETWORK.registerMessage(packet.newInstance(), packet, nextID(), side);
      } catch (IllegalAccessException | InstantiationException var3) {
         var3.printStackTrace();
      }
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileEntity tile, double range) {
      for (EntityPlayerMP playerIn : world.getEntitiesWithinAABB(
         EntityPlayerMP.class,
         new AxisAlignedBB(
            pos.getX() + range,
            pos.getY() + range,
            pos.getZ() + range,
            pos.getX() - range,
            pos.getY() - range,
            pos.getZ() - range
         )
      )) {
         SPacketUpdateTileEntity spacketupdatetileentity = tile.getUpdatePacket();
         if (spacketupdatetileentity != null) {
            playerIn.connection.sendPacket(spacketupdatetileentity);
         }
      }
   }
}
