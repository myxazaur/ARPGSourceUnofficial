package com.vivern.arpg.renders;

import com.vivern.arpg.elements.models.FireworkModel;
import com.vivern.arpg.entity.FireworkEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class RenderFirework<T extends FireworkEntity> extends Render<T> {
   protected final ResourceLocation texture = new ResourceLocation("arpg:textures/firework_tex.png");
   protected final FireworkModel model = new FireworkModel();

   public RenderFirework(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.disableCull();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
      Vec3d vec = entity.getForward();
      GlStateManager.rotate(1.0F, 0.0F, (float)vec.y, (float)vec.z);
      GlStateManager.rotate(90.0F, 0.0F, -1.0F, 0.0F);
      GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
      this.bindTexture(this.texture);
      float ang = entity.ticksExisted;
      if (this.renderOutlines) {
         GlStateManager.enableColorMaterial();
         GlStateManager.enableOutlineMode(this.getTeamColor(entity));
      }

      GlStateManager.color(entity.redR, entity.greenR, entity.blueR);
      GlStateManager.scale(entity.scaleYX, entity.scaleYX, entity.scaleZZ);
      this.model.render(entity, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      if (this.renderOutlines) {
         GlStateManager.disableOutlineMode();
         GlStateManager.disableColorMaterial();
      }

      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(FireworkEntity entity) {
      return TextureMap.LOCATION_BLOCKS_TEXTURE;
   }
}
