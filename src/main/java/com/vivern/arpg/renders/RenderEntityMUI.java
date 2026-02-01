package com.vivern.arpg.renders;

import com.vivern.arpg.entity.EntityMagicUI;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityMUI<T extends EntityMagicUI> extends Render<T> {
   public RenderEntityMUI(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      if (entity != null) {
         entity.renderAsEntity(x, y, z, entityYaw, partialTicks, this.renderManager);
         super.doRender(entity, x, y, z, entityYaw, partialTicks);
      }
   }

   protected ResourceLocation getEntityTexture(T entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }

   public static class RenderEntityMUIFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new RenderEntityMUI(manager);
      }
   }
}
