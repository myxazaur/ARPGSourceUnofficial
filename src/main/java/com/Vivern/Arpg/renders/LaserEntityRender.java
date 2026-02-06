package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.entity.EntityLaserParticle;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<EntityLaserParticle>`
@SideOnly(Side.CLIENT)
public class LaserEntityRender<T extends EntityLaserParticle> extends Render<EntityLaserParticle> {
   public LaserEntityRender(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(EntityLaserParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
      double dist = entity.distance;
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.rotate(-entity.rotW, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate((this.renderManager.options.thirdPersonView == 2 ? 1 : 1) * entity.rotP, 1.0F, 0.0F, 0.0F);
      this.bindTexture(entity.texture);
      GL11.glDisable(2896);
      GL11.glEnable(3042);
      GL11.glDisable(2884);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alphatime);
      float animAdd = entity.animation * (entity.ticksExisted + partialTicks) + entity.animationDisplace;
      float ty1 = 0.0F + animAdd;
      float ty2 = entity.visibleZone + animAdd;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-entity.scale - entity.horizOffset, -0.1, 0.0).tex(0.0, ty2).endVertex();
      bufferbuilder.pos(-entity.scale, 0.0, dist + 0.08).tex(0.0, ty1).endVertex();
      bufferbuilder.pos(entity.scale, 0.0, dist + 0.08).tex(1.0, ty1).endVertex();
      bufferbuilder.pos(entity.scale - entity.horizOffset, -0.1, 0.0).tex(1.0, ty2).endVertex();
      tessellator.draw();
      GlStateManager.disableRescaleNormal();
      GL11.glDisable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(2884);
      GlStateManager.popMatrix();
      if (entity.horizontal) {
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(-entity.rotW, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate((this.renderManager.options.thirdPersonView == 2 ? 1 : 1) * entity.rotP, 1.0F, 0.0F, 0.0F);
         this.bindTexture(entity.texture);
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         GL11.glDisable(2884);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
         GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
         Tessellator tessellator2 = Tessellator.getInstance();
         BufferBuilder bufferbuilder2 = tessellator2.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder2.pos(-entity.horizOffset, -entity.scale - 0.1, 0.0).tex(0.0, ty2).endVertex();
         bufferbuilder2.pos(0.0, -entity.scale, dist + 0.08).tex(0.0, ty1).endVertex();
         bufferbuilder2.pos(0.0, entity.scale, dist + 0.08).tex(1.0, ty1).endVertex();
         bufferbuilder2.pos(-entity.horizOffset, entity.scale - 0.1, 0.0).tex(1.0, ty2).endVertex();
         tessellator2.draw();
         GlStateManager.disableRescaleNormal();
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         GL11.glEnable(2884);
         GlStateManager.popMatrix();
      }

      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
      if (entity.timeToAddAlpha == -1) {
         if (entity.ticksExisted <= entity.livetime / 2) {
            entity.alpha = entity.alpha + entity.alphatime;
         }
      } else if (entity.ticksExisted <= entity.timeToAddAlpha) {
         entity.alpha = entity.alpha + entity.alphatime;
      }

      if (entity.timeToDecrAlpha == -1) {
         if (entity.ticksExisted > entity.livetime / 2) {
            entity.alpha = entity.alpha - entity.alphatime;
         }
      } else if (entity.ticksExisted > entity.timeToDecrAlpha) {
         entity.alpha = entity.alpha - entity.alphatime;
      }
   }

   protected ResourceLocation getEntityTexture(EntityLaserParticle entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }
}
