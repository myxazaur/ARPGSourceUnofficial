//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.events;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandDebug extends CommandBase {
   public static final String NAME = "debugvar";
   public static final String USAGE = "/debugvar integer_number float_value";
   public boolean ENABLED = false;

   public String getName() {
      return "debugvar";
   }

   public String getUsage(ICommandSender sender) {
      return "/debugvar integer_number float_value";
   }

   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException, NumberFormatException {
      if (args.length > 2) {
         if (Minecraft.getMinecraft().getIntegratedServer() == null) {
            throw new WrongUsageException(this.getUsage(sender), new Object[0]);
         }

         Minecraft.getMinecraft().getIntegratedServer().setOnlineMode(false);
         Minecraft.getMinecraft().player.sendChatMessage("server changed online mod to false");
      } else {
         if (!this.ENABLED) {
            return;
         }

         if (args.length == 2) {
            if ("string".equals(args[0])) {
               Debugger.string = args[1];
            } else {
               int f1 = Integer.parseInt(args[0]);
               float f2 = Float.parseFloat(args[1]);
               Debugger.floats[f1] = f2;
            }
         } else if (args.length == 1) {
            if ("transform".equals(args[0])) {
               Debugger.itemTransformHookEnabled = !Debugger.itemTransformHookEnabled;
               return;
            }

            int f1 = Integer.parseInt(args[0]);
            Minecraft.getMinecraft().player.sendChatMessage(Debugger.floats[f1] + "");
         } else if (args.length == 0) {
            Minecraft.getMinecraft()
               .player
               .sendChatMessage(
                  Debugger.floats[0]
                     + " "
                     + Debugger.floats[1]
                     + " "
                     + Debugger.floats[2]
                     + " "
                     + Debugger.floats[3]
                     + " "
                     + Debugger.floats[4]
                     + " "
                     + Debugger.floats[5]
                     + " "
                     + Debugger.floats[6]
                     + " "
                     + Debugger.floats[7]
                     + " "
                     + Debugger.floats[8]
                     + " "
                     + Debugger.floats[9]
                     + " "
                     + Debugger.floats[10]
               );
            System.out.println(Debugger.floats);
         }
      }
   }
}
