package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.StingingCellEntityModel;
import com.Vivern.Arpg.entity.StingingCellEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

public class RenderStingingCell<T extends StingingCellEntity> extends Render<T> {
   static ResourceLocation texture = new ResourceLocation("arpg:textures/stinging_cell_entity_model_tex.png");
   static ResourceLocation overlay = new ResourceLocation("arpg:textures/stinging_cell_entity_model_overlay_tex.png");
   private final StingingCellEntityModel model = new StingingCellEntityModel();

   public RenderStingingCell(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y + 0.28F, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.translate(0.0F, -0.12F, 0.0F);
      GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks - 90.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(0.0F, 0.12F, 0.0F);
      GlStateManager.disableLighting();
      GL11.glEnable(3042);
      if (this.renderOutlines) {
         GlStateManager.enableColorMaterial();
         GlStateManager.enableOutlineMode(this.getTeamColor(entity));
      }

      this.bindTexture(texture);
      GlStateManager.scale(entity.scale, entity.scale, entity.scale);
      this.model.render(entity, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0625F);
      if (this.renderOutlines) {
         GlStateManager.disableOutlineMode();
         GlStateManager.disableColorMaterial();
      }

      GL11.glDisable(3042);
      GlStateManager.enableLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y + 0.28F, (float)z);
      GlStateManager.translate(0.0F, -0.12F, 0.0F);
      GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks - 90.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(0.0F, 0.12F, 0.0F);
      this.bindTexture(overlay);
      GlStateManager.scale(entity.scale, entity.scale, entity.scale);
      this.model.render(entity, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0625F);
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(StingingCellEntity entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }

   public static class StingingCellRenderFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new RenderStingingCell(manager);
      }
   }
}
