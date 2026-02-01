package com.vivern.arpg.renders;

import com.vivern.arpg.elements.models.AbstractMobModel;
import com.vivern.arpg.entity.GraplingHookParticle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<GraplingHookParticle>`
public class GHParticleRender<T extends GraplingHookParticle> extends Render<GraplingHookParticle> {
   public GHParticleRender(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(GraplingHookParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
      if (entity.entityOn != null) {
         Vec3d rvePos = MyRenderHelper.getRenderViewEntityPosition(partialTicks);
         Vec3d transl = MyRenderHelper.getEntityPartialPosition(entity.entityOn, partialTicks)
            .subtract(rvePos)
            .add(0.0, entity.getEyeHeight() - 0.3, 0.0);
         double dist = entity.distance - entity.pikeLength / 2.0F;
         float verticalTranslate = 0.0F;
         GlStateManager.pushMatrix();
         AbstractMobModel.alphaGlowDisable();
         GlStateManager.translate((float)transl.x, (float)transl.y, (float)transl.z);
         GlStateManager.translate(0.0F, verticalTranslate, 0.0F);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(-entity.rotatYaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.rotatPitch, 1.0F, 0.0F, 0.0F);
         this.bindTexture(entity.texture);
         if (entity.light >= 0) {
            GL11.glDisable(2896);
         }

         GL11.glEnable(3042);
         GL11.glDisable(2884);
         if (entity.light >= 0) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
         }

         GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-entity.scale, 0.0, 0.0).tex(0.0, 1.0 + dist / entity.lengh + entity.Tickpl * entity.animation).endVertex();
         bufferbuilder.pos(-entity.scale, 0.0, dist).tex(0.0, 0.0F + entity.Tickpl * entity.animation).endVertex();
         bufferbuilder.pos(entity.scale, 0.0, dist).tex(1.0, 0.0F + entity.Tickpl * entity.animation).endVertex();
         bufferbuilder.pos(entity.scale, 0.0, 0.0).tex(1.0, 1.0 + dist / entity.lengh + entity.Tickpl * entity.animation).endVertex();
         tessellator.draw();
         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         AbstractMobModel.alphaGlowDisable();
         GlStateManager.translate((float)transl.x, (float)transl.y, (float)transl.z);
         GlStateManager.translate(0.0F, verticalTranslate, 0.0F);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(-entity.rotatYaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.rotatPitch, 1.0F, 0.0F, 0.0F);
         this.bindTexture(entity.texture);
         GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
         Tessellator tessellator2 = Tessellator.getInstance();
         BufferBuilder bufferbuilder2 = tessellator2.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder2.pos(0.0, -entity.scale, 0.0).tex(0.0, 1.0 + dist / entity.lengh + entity.Tickpl * entity.animation).endVertex();
         bufferbuilder2.pos(0.0, -entity.scale, dist).tex(0.0, 0.0F + entity.Tickpl * entity.animation).endVertex();
         bufferbuilder2.pos(0.0, entity.scale, dist).tex(1.0, 0.0F + entity.Tickpl * entity.animation).endVertex();
         bufferbuilder2.pos(0.0, entity.scale, 0.0).tex(1.0, 1.0 + dist / entity.lengh + entity.Tickpl * entity.animation).endVertex();
         tessellator2.draw();
         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
         double dist2 = entity.pikeLength;
         GlStateManager.pushMatrix();
         AbstractMobModel.alphaGlowDisable();
         GlStateManager.translate((float)transl.x, (float)transl.y, (float)transl.z);
         GlStateManager.translate(0.0F, verticalTranslate, 0.0F);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(-entity.rotatYaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.rotatPitch, 1.0F, 0.0F, 0.0F);
         this.bindTexture(entity.texture2);
         GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
         Tessellator tessellator3 = Tessellator.getInstance();
         BufferBuilder bufferbuilder3 = tessellator3.getBuffer();
         bufferbuilder3.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder3.pos(-entity.pikeScale, 0.0, dist).tex(0.0, 1.0).endVertex();
         bufferbuilder3.pos(-entity.pikeScale, 0.0, dist + dist2).tex(0.0, 0.0).endVertex();
         bufferbuilder3.pos(entity.pikeScale, 0.0, dist + dist2).tex(1.0, 0.0).endVertex();
         bufferbuilder3.pos(entity.pikeScale, 0.0, dist).tex(1.0, 1.0).endVertex();
         tessellator3.draw();
         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         AbstractMobModel.alphaGlowDisable();
         GlStateManager.translate((float)transl.x, (float)transl.y, (float)transl.z);
         GlStateManager.translate(0.0F, verticalTranslate, 0.0F);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(-entity.rotatYaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.rotatPitch, 1.0F, 0.0F, 0.0F);
         GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
         Tessellator tessellator4 = Tessellator.getInstance();
         BufferBuilder bufferbuilder4 = tessellator4.getBuffer();
         bufferbuilder4.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder4.pos(0.0, -entity.pikeScale, dist).tex(0.0, 1.0).endVertex();
         bufferbuilder4.pos(0.0, -entity.pikeScale, dist + dist2).tex(0.0, 0.0).endVertex();
         bufferbuilder4.pos(0.0, entity.pikeScale, dist + dist2).tex(1.0, 0.0).endVertex();
         bufferbuilder4.pos(0.0, entity.pikeScale, dist).tex(1.0, 1.0).endVertex();
         tessellator4.draw();
         GlStateManager.disableRescaleNormal();
         GL11.glDisable(3042);
         if (entity.light >= 0) {
            GL11.glEnable(2896);
         }

         GL11.glEnable(2884);
         GlStateManager.popMatrix();
         super.doRender(entity, x, y, z, entityYaw, partialTicks);
      }
   }

   protected ResourceLocation getEntityTexture(GraplingHookParticle entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }
}
