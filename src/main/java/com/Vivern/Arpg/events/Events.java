package com.Vivern.Arpg.events;

import com.Vivern.Arpg.main.ItemsRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class Events {
   public static Random rand = new Random();

   @SubscribeEvent
   public static void blockDrop(HarvestDropsEvent event) {
      Block block = event.getState().getBlock();
      if (block == Blocks.MOB_SPAWNER && rand.nextFloat() < 0.75F) {
         event.getDrops().add(new ItemStack(ItemsRegister.SPAWNERPIECE));
         if (rand.nextFloat() < 0.2F) {
            event.getDrops().add(new ItemStack(ItemsRegister.SPAWNERPIECE));
         }
      }
   }
}
