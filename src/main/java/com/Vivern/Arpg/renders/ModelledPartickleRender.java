package com.Vivern.Arpg.renders;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<ModelledPartickle>`
@SideOnly(Side.CLIENT)
public class ModelledPartickleRender<T extends ModelledPartickle> extends Render<ModelledPartickle> {
   public ModelledPartickleRender(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(ModelledPartickle entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      this.bindTexture(entity.texture);
      float finalscale = entity.scale + entity.scaleTickAdding * partialTicks;
      GlStateManager.scale(finalscale, finalscale, finalscale);
      GL11.glDepthMask(false);
      if (entity.noCullface) {
         GL11.glDisable(2884);
      }

      if (entity.light >= 0) {
         GL11.glDisable(2896);
      }

      if (entity.opa) {
         GL11.glEnable(3042);
      }

      if (entity.alphaGlowing) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      } else if (entity.acidRender) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.SRC_COLOR);
      } else {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      if (entity.light >= 0) {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
      }

      GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
      if (entity.tracker != null) {
         entity.tracker.render((T)entity, x, y, z, entityYaw, partialTicks);
      }

      if (entity.isEntityLiving) {
         EntityLiving living = (EntityLiving)entity.modelEntityLink;
         boolean shouldSit = living.isRiding() && living.getRidingEntity() != null && living.getRidingEntity().shouldRiderSit();
         entity.model.isRiding = shouldSit;
         entity.model.isChild = living.isChild();
         float f = this.interpolateRotation(living.prevRenderYawOffset, living.renderYawOffset, partialTicks);
         GlStateManager.rotate(180.0F - f, 0.0F, 1.0F, 0.0F);
         float scale = this.prepareScale();
         entity.model
            .render(living, living.limbSwing, living.limbSwingAmount, living.ticksExisted, living.rotationYawHead, living.rotationPitch, scale);
      } else {
         GlStateManager.enableRescaleNormal();
         entity.model.render(entity.modelEntityLink, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      GlStateManager.disableRescaleNormal();
      if (entity.light >= 0) {
         GL11.glEnable(2896);
      }

      GL11.glDepthMask(true);
      if (entity.opa) {
         GL11.glDisable(3042);
      }

      if (entity.noCullface) {
         GL11.glEnable(2884);
      }

      if (entity.alphaGlowing || entity.acidRender) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(ModelledPartickle entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }

   protected float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
      float f = yawOffset - prevYawOffset;

      while (f < -180.0F) {
         f += 360.0F;
      }

      while (f >= 180.0F) {
         f -= 360.0F;
      }

      return prevYawOffset + partialTicks * f;
   }

   public float prepareScale() {
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(-1.0F, -1.0F, 1.0F);
      float f = 0.0625F;
      GlStateManager.translate(0.0F, -1.501F, 0.0F);
      return 0.0625F;
   }

   public static class ModelledPartickleFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new ModelledPartickleRender(manager);
      }
   }
}
