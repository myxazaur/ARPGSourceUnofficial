package com.Vivern.Arpg.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerButtonTracker {
   public static boolean doublejump = false;
   public static boolean pressjump = false;
   public static boolean ongr = false;
   public static boolean flyingjump = false;
   public static boolean unpressjump = false;
   public static boolean scopeactived = false;
   public static int itemslot = 0;
   public static int previtemslot = 0;
   public static boolean pressscope = false;
   public static float mousesens = 1.0F;
   public static float redmousesens = 1.0F;
   public static boolean activedms = false;

   public static boolean getDoubleJump(EntityPlayer player) {
      pressjump = GameSettings.isKeyDown(Keys.JUMP);
      if (player.onGround) {
         ongr = true;
         flyingjump = false;
         unpressjump = false;
         doublejump = false;
      }

      if (ongr && !player.onGround) {
         flyingjump = true;
      }

      if (flyingjump && !pressjump) {
         unpressjump = true;
      }

      if (unpressjump && pressjump) {
         doublejump = true;
         return doublejump;
      } else {
         return false;
      }
   }

   public static boolean getScopeActive(EntityPlayer player, boolean hasItemScoped) {
      if (!activedms) {
         mousesens = Minecraft.getMinecraft().gameSettings.mouseSensitivity;
      }

      if (GameSettings.isKeyDown(Keys.SCOPE) && !pressscope) {
         pressscope = true;
         scopeactived = !scopeactived;
      }

      if (pressscope && !GameSettings.isKeyDown(Keys.SCOPE)) {
         pressscope = false;
      }

      itemslot = player.inventory.currentItem;
      if (previtemslot != itemslot) {
         scopeactived = false;
      }

      if (Minecraft.getMinecraft().getRenderManager().options != null && Minecraft.getMinecraft().getRenderManager().options.thirdPersonView != 0) {
         scopeactived = false;
      }

      previtemslot = itemslot;
      if (!scopeactived) {
         Minecraft.getMinecraft().gameSettings.mouseSensitivity = mousesens;
         activedms = false;
      }

      if (scopeactived && !activedms && hasItemScoped) {
         Minecraft.getMinecraft().gameSettings.mouseSensitivity = 0.17F;
         activedms = true;
      }

      return scopeactived;
   }
}
