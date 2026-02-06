package com.Vivern.Arpg.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.lwjgl.opengl.GL11;

@EventBusSubscriber(
   modid = "arpg"
)
public class FBO {
   private static Framebuffer framebuffer;
   private static Minecraft mc = Minecraft.getMinecraft();

   public static void fboTest(RenderWorldLastEvent event) {
      GL11.glEnable(3042);
      int current = GL11.glGetInteger(32873);
      if (framebuffer == null) {
         framebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, false);
      }

      if (mc.displayWidth != framebuffer.framebufferWidth || mc.displayHeight != framebuffer.framebufferHeight) {
         framebuffer.createBindFramebuffer(mc.displayWidth, mc.displayHeight);
      }

      framebuffer.bindFramebuffer(false);
      GL11.glBindTexture(3553, 0);
      GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.5F);
      drawQuad();
      mc.getFramebuffer().bindFramebuffer(false);
      GL11.glPushMatrix();
      GL11.glBindTexture(3553, framebuffer.framebufferTexture);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      drawQuad();
      GL11.glPopMatrix();
      framebuffer.framebufferClear();
      mc.getFramebuffer().bindFramebuffer(false);
      GL11.glBindTexture(3553, current);
      GL11.glDisable(3042);
   }

   private static void drawQuad() {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder buffer = tessellator.getBuffer();
      buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
      buffer.pos(-1.0, -1.0, 0.0).tex(0.0, 0.0).endVertex();
      buffer.pos(1.0, -1.0, 0.0).tex(1.0, 0.0).endVertex();
      buffer.pos(1.0, 1.0, 0.0).tex(1.0, 1.0).endVertex();
      buffer.pos(-1.0, 1.0, 0.0).tex(0.0, 1.0).endVertex();
      tessellator.draw();
   }
}
