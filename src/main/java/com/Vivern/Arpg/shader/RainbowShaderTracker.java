//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.shader;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.potions.PotionEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.ARBShaderObjects;

@EventBusSubscriber(
   modid = "arpg"
)
@SideOnly(Side.CLIENT)
public class RainbowShaderTracker {
   static Minecraft mc = Minecraft.getMinecraft();
   public static Framebuffer fbuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);

   @SubscribeEvent
   public static void onRenderTick(RenderTickEvent event) {
      if (mc.player != null && mc.player.isPotionActive(PotionEffects.RAINBOW)) {
         Framebuffer minefb = Minecraft.getMinecraft().getFramebuffer();
         GlStateManager.pushMatrix();
         TextureManager r = Minecraft.getMinecraft().renderEngine;
         new ScaledResolution(mc);
         int h = mc.displayHeight;
         int w = mc.displayWidth;
         fbuffer.createBindFramebuffer(w, h);
         fbuffer.framebufferRender(w, h);
         minefb.bindFramebuffer(true);
         minefb.bindFramebufferTexture();
         fbuffer.bindFramebuffer(true);
         ShaderMain.RainbowShader.start();
         ARBShaderObjects.glUniform1fARB(ShaderMain.RainbowShader.getUniform("time"), AnimationTimer.normaltick / 100.0F);
         if (mc.player != null) {
            ARBShaderObjects.glUniform1fARB(
               ShaderMain.RainbowShader.getUniform("power"), (mc.player.getActivePotionEffect(PotionEffects.RAINBOW).getAmplifier() + 1) / 3.0F
            );
         } else {
            ARBShaderObjects.glUniform1fARB(ShaderMain.RainbowShader.getUniform("power"), 0.3F);
         }

         Tessellator tesf = Tessellator.getInstance();
         BufferBuilder bufferbuilderf = tesf.getBuffer();
         bufferbuilderf.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilderf.pos(0.0, h, 0.0).tex(0.0, 1.0).endVertex();
         bufferbuilderf.pos(w, h, 0.0).tex(1.0, 1.0).endVertex();
         bufferbuilderf.pos(w, 0.0, 0.0).tex(1.0, 0.0).endVertex();
         bufferbuilderf.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).endVertex();
         tesf.draw();
         ShaderMain.RainbowShader.stop();
         fbuffer.bindFramebufferTexture();
         fbuffer.unbindFramebuffer();
         minefb.bindFramebuffer(true);
         Tessellator tes = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tes.getBuffer();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(0.0, h, 0.0).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(w, h, 0.0).tex(1.0, 1.0).endVertex();
         bufferbuilder.pos(w, 0.0, 0.0).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).endVertex();
         tes.draw();
         GlStateManager.popMatrix();
         minefb.unbindFramebufferTexture();
      }
   }
}
