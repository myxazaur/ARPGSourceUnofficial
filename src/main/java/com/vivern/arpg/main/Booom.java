package com.vivern.arpg.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(
   modid = "arpg"
)
@SideOnly(Side.CLIENT)
public class Booom {
   public static int lastTick = 0;
   public static int FOVlastTick = 0;
   public static float x;
   public static float y;
   public static float z;
   public static float frequency = 4.0F;
   public static float power = 0.14F;
   public static float FOVboom = 0.0F;
   public static float FOVfrequency = 4.0F;
   public static float FOVpower = 0.14F;
   public static int drunkTick = 0;
   public static float drunkPower = 0.0F;

   public Booom(EntityPlayer player, int lasttick) {
      lastTick = lasttick;
   }

   public static float getFrequencyForTicks(int ticks) {
      return -0.0067F + 3.2026F / ticks;
   }

   @SubscribeEvent
   @SideOnly(Side.CLIENT)
   public static void onRenderWorld(CameraSetup event) {
      if (lastTick > 0) {
         lastTick--;
         if (Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer) {
            GlStateManager.rotate((float)Math.sin(lastTick * frequency) * lastTick * power, x, y, z);
         }
      }

      if (FOVlastTick > 0) {
         FOVlastTick--;
         FOVboom = (float)Math.sin(FOVlastTick * FOVfrequency) * FOVlastTick * FOVpower;
      }

      if (drunkTick > 0) {
         drunkTick--;
         GlStateManager.rotate(drunkPower, AnimationTimer.rainbow1, AnimationTimer.rainbow2, 0.0F);
      }
   }
}
