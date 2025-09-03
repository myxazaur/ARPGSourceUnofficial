//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.network.PacketDoSomethingToClients;
import java.util.ArrayList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KillScore {
   public static int timeToIdle = 100;
   public static int maxRecordSeconds = 30;
   public static int kills;
   public static int eliteKills;
   public static ArrayList<DPS> DPSrecorder = new ArrayList<>();
   public static DPS currentDPS = new DPS(true);
   public static int DPStimeCounter = timeToIdle;
   public static float averageDPS;
   public static float averageSmallDPS;
   public static boolean ENABLED = false;

   public static void onHealthReduction(EntityLivingBase entity, float reduction) {
      if (ENABLED) {
         if (!entity.world.isRemote
            && entity instanceof EntityLiving
            && ((EntityLiving)entity).getAttackTarget() instanceof EntityPlayer
            && Debugger.floats[17] == 1.0F) {
            PacketDoSomethingToClients.send((EntityPlayer)((EntityLiving)entity).getAttackTarget(), -reduction, 0.0, 0.0, 0.0, 0.0, 0.0, 10);
         }
      }
   }

   public static void onLivingHurt(LivingHurtEvent event) {
      if (ENABLED) {
         if (!event.getEntity().world.isRemote) {
            if (event.getSource().getTrueSource() instanceof EntityPlayer && event.getEntity() instanceof EntityLiving) {
               PacketDoSomethingToClients.send((EntityPlayer)event.getSource().getTrueSource(), event.getAmount(), 0.0, 0.0, 0.0, 0.0, 0.0, 10);
            } else if (event.getSource().getTrueSource() == null
               && event.getEntity() instanceof EntityLiving
               && ((EntityLiving)event.getEntity()).getAttackTarget() instanceof EntityPlayer) {
               if (Debugger.floats[15] == 1.0F
                  && event.getEntityLiving().isBurning()
                  && (event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.ON_FIRE)) {
                  PacketDoSomethingToClients.send(
                     (EntityPlayer)((EntityLiving)event.getEntity()).getAttackTarget(), event.getAmount(), 0.0, 0.0, 0.0, 0.0, 0.0, 10
                  );
               }

               if (Debugger.floats[16] == 1.0F && event.getSource() == DamageSource.LAVA) {
                  PacketDoSomethingToClients.send(
                     (EntityPlayer)((EntityLiving)event.getEntity()).getAttackTarget(), event.getAmount(), 0.0, 0.0, 0.0, 0.0, 0.0, 10
                  );
               }

               if (Debugger.floats[17] == 1.0F && event.getSource() instanceof WeaponDamage) {
                  PacketDoSomethingToClients.send(
                     (EntityPlayer)((EntityLiving)event.getEntity()).getAttackTarget(), event.getAmount(), 0.0, 0.0, 0.0, 0.0, 0.0, 10
                  );
               }

               if (Debugger.floats[18] == 1.0F && event.getSource() == DamageSource.IN_WALL) {
                  PacketDoSomethingToClients.send(
                     (EntityPlayer)((EntityLiving)event.getEntity()).getAttackTarget(), event.getAmount(), 0.0, 0.0, 0.0, 0.0, 0.0, 10
                  );
               }

               if (Debugger.floats[19] == 1.0F && event.getSource().isMagicDamage()) {
                  PacketDoSomethingToClients.send(
                     (EntityPlayer)((EntityLiving)event.getEntity()).getAttackTarget(), event.getAmount(), 0.0, 0.0, 0.0, 0.0, 0.0, 10
                  );
               }
            }
         }
      }
   }

   public static void onLivingDeath(LivingDeathEvent event) {
      if (ENABLED) {
         if (!event.getEntity().world.isRemote
            && event.getSource().getTrueSource() instanceof EntityPlayer
            && event.getEntity() instanceof EntityLiving) {
            if (event.getEntityLiving() != null && event.getEntityLiving().getMaxHealth() > 30.0F) {
               PacketDoSomethingToClients.send((EntityPlayer)event.getSource().getTrueSource(), 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 10);
            } else {
               PacketDoSomethingToClients.send((EntityPlayer)event.getSource().getTrueSource(), 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 10);
            }
         }
      }
   }

   public static void tick() {
      if (ENABLED) {
         DPStimeCounter++;
         currentDPS.ticks++;
         if (currentDPS.ticks >= 20) {
            if (!currentDPS.isVoid) {
               DPSrecorder.add(currentDPS);
            }

            currentDPS = new DPS(DPStimeCounter > timeToIdle);
            if (DPSrecorder.size() > maxRecordSeconds) {
               DPSrecorder.remove(0);
            }

            averageDPS = 0.0F;
            int count = 0;

            for (DPS dps : DPSrecorder) {
               if (!dps.isVoid) {
                  averageDPS = averageDPS + dps.damage;
                  count++;
               }
            }

            averageDPS /= count;
            averageSmallDPS = 0.0F;
            count = 0;
            int k = 0;

            for (int i = DPSrecorder.size() - 1; k < 4 && i >= 0; i--) {
               DPS dpsx = DPSrecorder.get(i);
               if (!dpsx.isVoid) {
                  averageSmallDPS = averageSmallDPS + dpsx.damage;
                  count++;
               }

               k++;
            }

            averageSmallDPS /= count;
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public static void renderAll(FontRenderer fontRenderer, int screenWidth, int screenHeight) {
      if (ENABLED) {
         int margin = 30;
         int offsetX = -10;
         int offsetY = 10;
         String text1 = "kills: " + kills;
         fontRenderer.drawStringWithShadow(text1, offsetX + screenWidth / 2 - fontRenderer.getStringWidth(text1) - margin, offsetY, 11781046);
         String text2 = "elites: " + eliteKills;
         fontRenderer.drawStringWithShadow(text2, offsetX + screenWidth / 2, offsetY, 11781046);
         String text3 = "DPS: " + ManaBar.asString(averageSmallDPS);
         fontRenderer.drawStringWithShadow(text3, offsetX + screenWidth / 2 + fontRenderer.getStringWidth(text2) + margin, offsetY, 11781046);
         String text4 = "average DPS: " + ManaBar.asString(averageDPS);
         fontRenderer.drawStringWithShadow(
            text4, offsetX + screenWidth / 2 + fontRenderer.getStringWidth(text2) + margin + fontRenderer.getStringWidth(text3) + margin, offsetY, 11781046
         );
      }
   }

   public static class DPS {
      public boolean isVoid;
      public int ticks;
      public float damage;

      public DPS(boolean isVoid) {
         this.isVoid = isVoid;
      }
   }
}
