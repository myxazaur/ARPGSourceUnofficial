package com.Vivern.Arpg.events;

import com.Vivern.Arpg.dimensions.generationutils.AbstractWorldProvider;
import com.Vivern.Arpg.weather.WorldEvent;
import com.Vivern.Arpg.weather.WorldEventsHandler;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandWorldEvents extends CommandBase {
   public static final String NAME = "worldevents";
   public static final String USAGE = "/worldevents <start|stop|info> event_class_name";

   public String getName() {
      return "worldevents";
   }

   public String getUsage(ICommandSender sender) {
      return "/worldevents <start|stop|info> event_class_name";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
      return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[]{"start", "stop", "info"}) : Collections.emptyList();
   }

   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException, NumberFormatException {
      if (args.length <= 0) {
         throw new WrongUsageException("/worldevents <start|stop|info> event_class_name", new Object[0]);
      } else {
         if (args.length == 1 && args[0].equals("info")) {
            World world = sender.getEntityWorld();
            boolean printNone = true;
            if (world.provider instanceof AbstractWorldProvider) {
               AbstractWorldProvider abstractWorldProvider = (AbstractWorldProvider)world.provider;
               WorldEventsHandler worldEventsHandler = abstractWorldProvider.getWorldEventsHandler();

               for (int i = 0; i < worldEventsHandler.events.length; i++) {
                  WorldEvent event = worldEventsHandler.events[i];
                  if (event.isStarted) {
                     String name = event.getClass().getSimpleName();
                     Minecraft.getMinecraft().player.sendChatMessage(name + " | " + (event.livetime - event.ticksExisted) + " ticks left");
                     printNone = false;
                  }
               }
            }

            if (printNone) {
               Minecraft.getMinecraft().player.sendChatMessage("There is no active world events in this world");
            }
         } else {
            if (args.length != 2 || !args[0].equals("start") && !args[0].equals("stop")) {
               throw new WrongUsageException("/worldevents <start|stop|info> event_class_name", new Object[0]);
            }

            boolean start = args[0].equals("start");
            World worldx = sender.getEntityWorld();
            if (worldx.provider instanceof AbstractWorldProvider) {
               AbstractWorldProvider abstractWorldProvider = (AbstractWorldProvider)worldx.provider;
               WorldEventsHandler worldEventsHandler = abstractWorldProvider.getWorldEventsHandler();

               for (int ix = 0; ix < worldEventsHandler.events.length; ix++) {
                  WorldEvent event = worldEventsHandler.events[ix];
                  String name = event.getClass().getSimpleName();
                  if (name.equals(args[1])) {
                     if (start) {
                        event.start();
                     } else {
                        event.stop();
                     }

                     return;
                  }
               }
            }
         }
      }
   }
}
