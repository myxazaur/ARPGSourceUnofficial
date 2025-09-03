package com.Vivern.Arpg.main;

import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(
   modid = "arpg"
)
@SideOnly(Side.CLIENT)
public class FishingTracker {
   public static boolean fishing;
   public static double posxx = 0.0;
   public static double posyy = 0.0;

   @SubscribeEvent
   @SideOnly(Side.CLIENT)
   public static void onRenderWorld(Post event) {
   }
}
