package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.network.PacketDoSomethingToClients;
import com.Vivern.Arpg.network.PacketHandler;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class RespawnPenalty extends AdvancedPotion {
   public static int clientDelayCounter = 0;

   public RespawnPenalty(int index) {
      super(true, 0, index, true);
      this.setRegistryName("arpg:respawn_penalty");
      this.setPotionName("Respawn_Penalty");
      this.setIconIndex(0, 0);
   }

   public static int getNormalBossPenalty() {
      return 400;
   }

   public static void renderMessage(EntityPlayer clientPlayer) {
      if (clientPlayer.isDead && clientPlayer.isPotionActive(PotionEffects.RESPAWN_PENALTY)) {
         ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
         int i = resolution.getScaledWidth();
         int f = resolution.getScaledHeight();
         GlStateManager.pushMatrix();
         float scale = 2.0F;
         GlStateManager.scale(scale, scale, 1.0F);
         Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("YOU DIED IN BOSS BATTLE!", (i / 2 - 126) / scale, 30.0F / scale, 14884382);
         GlStateManager.popMatrix();
         int dur = clientDelayCounter;
         Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("waiting for respawn: " + dur / 20 + "." + dur / 2 % 10, i / 2 - 61, 115.0F, 14884382);
      }
   }

   public List<ItemStack> getCurativeItems() {
      return new ArrayList<>();
   }

   public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
      super.performEffect(entityLivingBaseIn, amplifier);
      if (!entityLivingBaseIn.world.isRemote && entityLivingBaseIn.isDead && entityLivingBaseIn instanceof EntityPlayerMP) {
         PacketDoSomethingToClients packet = new PacketDoSomethingToClients();
         packet.writeargs(this.getThisDuration(entityLivingBaseIn), 0.0, 0.0, 0.0, 0.0, 0.0, 6);
         PacketHandler.sendTo(packet, (EntityPlayerMP)entityLivingBaseIn);
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }
}
