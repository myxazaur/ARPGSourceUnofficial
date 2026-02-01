package com.vivern.arpg.renders;

import com.vivern.arpg.entity.ParticleGore;
import com.vivern.arpg.events.Debugger;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<ParticleGore>`
public class RenderParticleGore<T extends ParticleGore> extends Render<ParticleGore> {
   public RenderParticleGore(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(ParticleGore entity, double x, double y, double z, float entityYaw, float partialTicks) {
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      boolean boxnonull = entity.box != null;
      boolean listnonull = entity.renderCutList != null;
      if (boxnonull || listnonull) {
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(entity.rotateX, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(entity.rotateY, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.rotateZ, 0.0F, 0.0F, 1.0F);
         if (entity.texture != null) {
            this.bindTexture(entity.texture);
         }

         if (entity.light >= 0) {
            GL11.glDisable(2896);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
         } else {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         if (entity.ticksExisted < 120) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         } else {
            GlStateManager.color(1.0F, 1.0F, 1.0F, (150.0F - entity.ticksExisted) / 30.0F);
         }

         GL11.glEnable(3042);
         GL11.glDisable(2884);
         if (boxnonull) {
            float raX = entity.box.rotateAngleX;
            float raY = entity.box.rotateAngleY;
            float raZ = entity.box.rotateAngleZ;
            entity.box.rotateAngleX = 0.0F;
            entity.box.rotateAngleY = 0.0F;
            entity.box.rotateAngleZ = 0.0F;
            entity.box.render(entity.scale);
            entity.box.rotateAngleX = raX;
            entity.box.rotateAngleY = raY;
            entity.box.rotateAngleZ = raZ;
         } else if (listnonull) {
            for (ModelRenderer boxx : entity.renderCutList) {
               float raX = boxx.rotateAngleX;
               float raY = boxx.rotateAngleY;
               float raZ = boxx.rotateAngleZ;
               boxx.rotateAngleX = 0.0F;
               boxx.rotateAngleY = 0.0F;
               boxx.rotateAngleZ = 0.0F;
               GlStateManager.translate(
                  -boxx.rotationPointX * Debugger.floats[0], -boxx.rotationPointY * Debugger.floats[0], -boxx.rotationPointZ * Debugger.floats[0]
               );
               GlStateManager.translate(
                  boxx.offsetX * Debugger.floats[1], boxx.offsetY * Debugger.floats[1], boxx.offsetZ * Debugger.floats[1]
               );
               boxx.render(entity.scale);
               boxx.rotateAngleX = raX;
               boxx.rotateAngleY = raY;
               boxx.rotateAngleZ = raZ;
            }
         }

         GlStateManager.disableRescaleNormal();
         GL11.glDisable(3042);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         if (entity.light >= 0) {
            GL11.glEnable(2896);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
         }

         GL11.glEnable(2884);
         GlStateManager.popMatrix();
      }

      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(ParticleGore entity) {
      return entity.texture;
   }

   public static class ParticleGoreFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new RenderParticleGore(manager);
      }
   }
}
