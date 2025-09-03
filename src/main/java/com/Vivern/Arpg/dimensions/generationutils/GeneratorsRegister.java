package com.Vivern.Arpg.dimensions.generationutils;

import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Post;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class GeneratorsRegister {
   public static List<IWorldGenerator> generators = new ArrayList<>();

   @SubscribeEvent
   public static void populate(Post event) {
      for (IWorldGenerator generator : generators) {
         generator.generate(event.getRand(), event.getChunkX(), event.getChunkZ(), event.getWorld(), event.getGenerator(), null);
      }
   }
}
