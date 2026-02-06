package com.Vivern.Arpg.events;

import com.Vivern.Arpg.main.Mana;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandSwarmPoints extends CommandBase {
   public static final String NAME = "swarmpoints";
   public static final String USAGE = "/swarmpoints add integer_value | /swarmpoints set integer_value | /swarmpoints player_name add integer_value | /swarmpoints player_name set integer_value";

   public String getName() {
      return "swarmpoints";
   }

   public String getUsage(ICommandSender sender) {
      return "/swarmpoints add integer_value | /swarmpoints set integer_value | /swarmpoints player_name add integer_value | /swarmpoints player_name set integer_value";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
      return args.length == 0 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : getListOfStringsMatchingLastWord(args, new String[]{"set", "add"});
   }

   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException, NumberFormatException {
      EntityPlayer senderPlayer = null;
      Entity entity = getEntity(server, sender, args[0]);
      if (entity != null && entity instanceof EntityPlayer) {
         senderPlayer = (EntityPlayer)entity;
      }

      if (args.length == 0) {
         Minecraft.getMinecraft().player.sendChatMessage("Swarm points: " + Mana.getSwarmPoints(senderPlayer));
      }

      if (args.length == 1) {
         if (senderPlayer == null) {
            throw new WrongUsageException(
               "/swarmpoints add integer_value | /swarmpoints set integer_value | /swarmpoints player_name add integer_value | /swarmpoints player_name set integer_value",
               new Object[0]
            );
         }

         Minecraft.getMinecraft().player.sendChatMessage("Swarm points of " + senderPlayer.getName() + ": " + Mana.getSwarmPoints(senderPlayer));
      }

      if (args.length == 2 && senderPlayer != null) {
         if (args[0].equals("add")) {
            int i = Integer.parseInt(args[1]);
            Mana.addSwarmPoints(senderPlayer, i);
         } else if (args[0].equals("set")) {
            int i = Integer.parseInt(args[1]);
            Mana.setSwarmPoints(senderPlayer, i);
         }
      }

      if (args.length == 3) {
         if (senderPlayer == null) {
            throw new WrongUsageException(
               "/swarmpoints add integer_value | /swarmpoints set integer_value | /swarmpoints player_name add integer_value | /swarmpoints player_name set integer_value",
               new Object[0]
            );
         }

         if (args[1].equals("add")) {
            int i = Integer.parseInt(args[2]);
            Mana.addSwarmPoints(senderPlayer, i);
         } else if (args[1].equals("set")) {
            int i = Integer.parseInt(args[2]);
            Mana.setSwarmPoints(senderPlayer, i);
         }
      }
   }
}
