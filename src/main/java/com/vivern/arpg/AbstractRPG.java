package com.vivern.arpg;

import com.vivern.arpg.events.CommandArpgInfo;
import com.vivern.arpg.events.CommandDebug;
import com.vivern.arpg.events.CommandGameStyles;
import com.vivern.arpg.events.CommandSwarmPoints;
import com.vivern.arpg.events.CommandWorldEvents;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.proxy.CommonProxy;
import com.vivern.arpg.renders.PlayerAnimations;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(
   modid = Tags.MOD_ID,
   name = Tags.MOD_NAME,
   dependencies = Tags.DEPENDENCIES,
   version = Tags.VERSION
)
public class AbstractRPG {

   @Instance
   public static AbstractRPG instance;

   @SidedProxy(
      clientSide = Tags.CLIENT_PROXY_PATH,
      serverSide = Tags.SERVER_PROXY_PATH
   )
   public static CommonProxy proxy;

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) throws IllegalAccessException {
      Sounds.registerSounds();
      proxy.preInit(event);
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      proxy.init(event);
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      proxy.postInit(event);
      PlayerAnimations.instance = new PlayerAnimations(Minecraft.getMinecraft());
   }

   @EventHandler
   public void serverStarting(FMLServerStartingEvent event) {
      event.registerServerCommand(new CommandDebug());
      event.registerServerCommand(new CommandSwarmPoints());
      event.registerServerCommand(new CommandWorldEvents());
      event.registerServerCommand(new CommandGameStyles());
      event.registerServerCommand(new CommandArpgInfo());
   }

   static {
      FluidRegistry.enableUniversalBucket();
   }
}
