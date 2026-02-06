package com.Vivern.Arpg.main;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class JumpBonusTracker {
   @SubscribeEvent
   public static void onLivingJump(LivingJumpEvent event) {
      EntityLivingBase var10000 = event.getEntityLiving();
      var10000.motionY = var10000.motionY + event.getEntityLiving().getEntityAttribute(PropertiesRegistry.JUMP_HEIGHT).getAttributeValue();
   }

   @SubscribeEvent
   public static void onLivingFall(LivingFallEvent event) {
      float height = (float)event.getEntityLiving().getEntityAttribute(PropertiesRegistry.JUMP_HEIGHT).getAttributeValue();
      if (height > 0.0F) {
         event.setDistance(event.getDistance() - height * 10.0F);
      }
   }

   public static void airbornMovement(EntityPlayer player) {
      if (!player.onGround && !player.isInWater() && !player.isElytraFlying()) {
         double abm = player.getEntityAttribute(PropertiesRegistry.AIRBORNE_MOBILITY).getAttributeValue();
         if (abm == 0.0) {
            return;
         }

         int keys = (Integer)player.getDataManager().get(PropertiesRegistry.KEYS_PRESSED);
         boolean forw = Keys.unpackCheckKey(keys, Keys.FORWARD);
         boolean righ = Keys.unpackCheckKey(keys, Keys.RIGHT);
         boolean left = Keys.unpackCheckKey(keys, Keys.LEFT);
         boolean back = Keys.unpackCheckKey(keys, Keys.BACK);
         double velocity = 0.0;
         int angle = 0;
         if (forw) {
            velocity = abm;
         }

         if (back) {
            velocity = -abm;
         }

         if (righ) {
            angle = 90;
            velocity = abm;
         }

         if (left) {
            angle = -90;
            velocity = abm;
         }

         if (left && righ) {
            angle = 0;
            velocity = 0.0;
         }

         if (forw && back) {
            angle = 0;
            velocity = 0.0;
         }

         if (righ && forw) {
            angle = 45;
            velocity = abm;
         }

         if (left && forw) {
            angle = -45;
            velocity = abm;
         }

         if (righ && back) {
            angle = 135;
            velocity = abm;
         }

         if (left && back) {
            angle = -135;
            velocity = abm;
         }

         if (velocity != 0.0) {
            float rotationYawIn = player.rotationYaw + angle;
            double x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0));
            double z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0));
            float f = MathHelper.sqrt(x * x + z * z);
            x /= f;
            z /= f;
            x *= velocity;
            z *= velocity;
            player.motionX += x;
            player.motionZ += z;
            double rm = 0.5 + 0.5 / (abm * 2.0 + 1.0);
            player.motionX *= rm;
            player.motionZ *= rm;
         }
      }
   }
}
