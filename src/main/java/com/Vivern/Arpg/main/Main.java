package com.Vivern.Arpg.main;

import com.Vivern.Arpg.events.CommandArpgInfo;
import com.Vivern.Arpg.events.CommandDebug;
import com.Vivern.Arpg.events.CommandGameStyles;
import com.Vivern.Arpg.events.CommandSwarmPoints;
import com.Vivern.Arpg.events.CommandWorldEvents;
import com.Vivern.Arpg.proxy.CommonProxy;
import com.Vivern.Arpg.renders.PlayerAnimations;
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
   modid = "arpg",
   dependencies = "after:thermalfoundation;after:thermalexpansion;after:endercore;after:enderio;after:redstoneflux;after:cofhcore;after:cofhworld;after:codechickenlib;after:ic2",
   acceptedMinecraftVersions = "[1.12.2]"
)
public class Main {
   @Instance
   public static Main instance;
   @SidedProxy(
      clientSide = "com.Vivern.Arpg.proxy.ClientProxy",
      serverSide = "com.Vivern.Arpg.proxy.CommonProxy"
   )
   public static CommonProxy proxy;
   public static final String modid = "arpg";
   public static boolean ENDERIO_installed = false;
   public static final boolean IS_RELEASE = true;

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) throws IllegalArgumentException, IllegalAccessException {
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
