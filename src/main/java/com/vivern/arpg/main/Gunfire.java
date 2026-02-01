package com.vivern.arpg.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(
   modid = "arpg"
)
@SideOnly(Side.CLIENT)
public class Gunfire {
   public static ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");

   public static void onRenderHand(RenderHandEvent event) {
      ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
      EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();
      int i = resolution.getScaledWidth();
      int f = resolution.getScaledHeight();
      GlStateManager.pushMatrix();
      GlStateManager.disableRescaleNormal();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableLighting();
      Minecraft.getMinecraft().getTextureManager().bindTexture(flame);
      drawCustomSizedTexturedRect(i / 2 - 120 + player.posX, f - 25, 36, 36, 0.0);
      GlStateManager.popMatrix();
   }

   private static void drawCustomSizedTexturedRect(double x, double y, int width, int height, double progr) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(x, y + height, 0.0).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(x + width, y + height, 0.0).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(x + width, y + height * progr, 0.0).tex(1.0, progr).endVertex();
      bufferbuilder.pos(x, y + height * progr, 0.0).tex(0.0, progr).endVertex();
      tessellator.draw();
   }
}
