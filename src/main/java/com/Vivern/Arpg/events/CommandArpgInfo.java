//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.events;

import com.Vivern.Arpg.container.GUIArpgInfo;
import com.Vivern.Arpg.container.GuiHandler;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandArpgInfo extends CommandBase {
   public static final String NAME = "arpg";
   public static final String USAGE = "/arpg info";

   public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
      return true;
   }

   public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
      return getListOfStringsMatchingLastWord(args, new String[]{"info"});
   }

   public String getName() {
      return "arpg";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public String getUsage(ICommandSender sender) {
      return "/arpg info";
   }

   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException, NumberFormatException {
      if (args.length == 1 && "info".equals(args[0])) {
         if (Minecraft.getMinecraft().player != null) {
            GuiHandler.displayGui(Minecraft.getMinecraft().player, new GUIArpgInfo(Minecraft.getMinecraft().player));
         }
      }
   }
}
