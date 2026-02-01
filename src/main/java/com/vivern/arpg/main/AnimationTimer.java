package com.vivern.arpg.main;

import com.vivern.arpg.renders.CloudSubparticle;
import com.vivern.arpg.renders.MagicSubparticle;
import com.vivern.arpg.renders.SparkleSubparticle;
import com.vivern.arpg.shader.ShaderMain;
import java.util.Random;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(
   modid = "arpg"
)
@SideOnly(Side.CLIENT)
public class AnimationTimer {
   public static int tick = 0;
   public static int normaltick = 0;
   public static float rainbow1 = 0.0F;
   public static float rainbow2 = 0.0F;
   public static float rainbow3 = 0.0F;
   public static Random rand = new Random();
   public static float motion = 0.1F;
   public static float motion2 = 0.1F;
   public static float motion3 = 0.1F;
   public static boolean reversecloudX = false;
   public static boolean reversecloudZ = false;

   public AnimationTimer() {
      tick = 0;
   }

   public static void TickClient(ClientTickEvent event) {
   }

   @SubscribeEvent
   public static void Tick(RenderWorldLastEvent event) {
      tick++;
      CloudSubparticle.renderInWorld(event);
      SparkleSubparticle.renderInWorld(event);
      MagicSubparticle.renderInWorld(event);
      ShaderMain.shaderCounter = 0;
   }

   @SubscribeEvent
   public static void TickWorld(WorldTickEvent event) {
      normaltick++;
      rainbow1 = rainbow1 + motion;
      if (rainbow1 != 0.0F) {
         if (motion == 0.1F && rand.nextGaussian() / 50.0 > 1.0 / Math.pow(1.2, rainbow1)) {
            motion = -0.1F;
         }

         if (motion == -0.1F && rand.nextGaussian() / 50.0 > 1.0 / Math.pow(1.2, -rainbow1)) {
            motion = 0.1F;
         }
      }

      rainbow2 = rainbow2 + motion2;
      if (rainbow2 != 0.0F) {
         if (motion2 == 0.1F && rand.nextGaussian() / 50.0 > 1.0 / Math.pow(1.2, rainbow2)) {
            motion2 = -0.1F;
         }

         if (motion2 == -0.1F && rand.nextGaussian() / 50.0 > 1.0 / Math.pow(1.2, -rainbow2)) {
            motion2 = 0.1F;
         }
      }

      rainbow3 = rainbow3 + motion3;
      if (rainbow3 != 0.0F) {
         if (motion3 == 0.1F && rand.nextGaussian() / 50.0 > 1.0 / Math.pow(1.2, rainbow3)) {
            motion3 = -0.1F;
         }

         if (motion3 == -0.1F && rand.nextGaussian() / 50.0 > 1.0 / Math.pow(1.2, -rainbow3)) {
            motion3 = 0.1F;
         }
      }

      if (rand.nextFloat() < 0.03) {
         if (CloudSubparticle.windX >= 0.0F) {
            if (rand.nextFloat() < CloudSubparticle.windX * 2.0F) {
               reversecloudX = false;
            }
         } else if (rand.nextFloat() < -CloudSubparticle.windX * 2.0F) {
            reversecloudX = true;
         }

         if (CloudSubparticle.windZ >= 0.0F) {
            if (rand.nextFloat() < CloudSubparticle.windZ * 2.0F) {
               reversecloudZ = false;
            }
         } else if (rand.nextFloat() < -CloudSubparticle.windZ * 2.0F) {
            reversecloudZ = true;
         }

         if (reversecloudX) {
            CloudSubparticle.windX = CloudSubparticle.windX + rand.nextFloat() * 0.01F;
         } else {
            CloudSubparticle.windX = CloudSubparticle.windX - rand.nextFloat() * 0.01F;
         }

         if (reversecloudZ) {
            CloudSubparticle.windZ = CloudSubparticle.windZ + rand.nextFloat() * 0.01F;
         } else {
            CloudSubparticle.windZ = CloudSubparticle.windZ - rand.nextFloat() * 0.01F;
         }
      }
   }
}
