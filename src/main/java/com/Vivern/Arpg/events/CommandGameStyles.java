package com.Vivern.Arpg.events;

import com.Vivern.Arpg.arpgamemodes.SurvivorGamestyleWatcher;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandGameStyles extends CommandBase {
   public static final String NAME = "gamestyles";
   public static final String USAGE = "/gamestyles style <start|stage|...>";

   public String getName() {
      return "gamestyles";
   }

   public String getUsage(ICommandSender sender) {
      return "/gamestyles style <start|stage|...>";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
      if (args.length == 1) {
         return getListOfStringsMatchingLastWord(args, new String[]{"survivor"});
      } else {
         return args.length == 2 ? getListOfStringsMatchingLastWord(args, new String[]{"start", "level", "stage"}) : Collections.emptyList();
      }
   }

   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException, NumberFormatException {
      if (args.length <= 0) {
         throw new WrongUsageException("/gamestyles style <start|stage|...>", new Object[0]);
      } else {
         if (args.length == 2 && args[0].equals("survivor") && args[1].equals("start")) {
            SurvivorGamestyleWatcher.startSurvivor(server);
         } else if (args.length == 3 && args[0].equals("survivor") && args[1].equals("level")) {
            if (SurvivorGamestyleWatcher.currentWatcher == null) {
               SurvivorGamestyleWatcher.currentWatcher.LEVEL = Integer.parseInt(args[2]);
            }
         } else {
            if (args.length != 3 || !args[0].equals("survivor") || !args[1].equals("stage")) {
               throw new WrongUsageException("/gamestyles style <start|stage|...>", new Object[0]);
            }

            if (SurvivorGamestyleWatcher.currentWatcher == null) {
               SurvivorGamestyleWatcher.currentWatcher.STAGE = Integer.parseInt(args[2]);
            }
         }
      }
   }
}
