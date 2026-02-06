package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.entity.CustomMobProjectile;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<CustomMobProjectile>`
public class CustomProjectileRender<T extends CustomMobProjectile> extends Render<CustomMobProjectile> {
   public CustomProjectileRender(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(CustomMobProjectile entity, double x, double y, double z, float entityYaw, float partialTicks) {
      if (entity.texture != null) {
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y, (float)z);
         GlStateManager.enableRescaleNormal();
         if (entity.renderStyle == 0) {
            GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(entity.rotation, 0.0F, 0.0F, 1.0F);
         } else {
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         }

         this.bindTexture(entity.texture);
         GlStateManager.scale(entity.scale, entity.scale, entity.scale);
         GL11.glDepthMask(false);
         if (entity.light >= 0) {
            GL11.glDisable(2896);
         }

         if (entity.enableAlpha) {
            GL11.glEnable(3042);
         }

         if (entity.alphaGlowing) {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         } else {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         float lbX = OpenGlHelper.lastBrightnessX;
         float lbY = OpenGlHelper.lastBrightnessY;
         if (entity.light >= 0) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
         }

         GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
         tessellator.draw();
         GlStateManager.disableRescaleNormal();
         if (entity.light >= 0) {
            GL11.glEnable(2896);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
         }

         GL11.glDepthMask(true);
         if (entity.enableAlpha) {
            GL11.glDisable(3042);
         }

         if (entity.alphaGlowing) {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         GlStateManager.popMatrix();
         super.doRender(entity, x, y, z, entityYaw, partialTicks);
      }
   }

   protected ResourceLocation getEntityTexture(CustomMobProjectile entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }

   public static class CustomProjectileFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new CustomProjectileRender(manager);
      }
   }
}
