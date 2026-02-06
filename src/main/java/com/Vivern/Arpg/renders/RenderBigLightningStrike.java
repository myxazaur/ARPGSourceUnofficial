package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.entity.BigLightningStrike;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// FIX: change `Render<T>` to `Render<BigLightningStrike>`
@SideOnly(Side.CLIENT)
public class RenderBigLightningStrike<T extends BigLightningStrike> extends Render<BigLightningStrike> {
   public RenderBigLightningStrike(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(BigLightningStrike entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      if (entity.mainSegment != null) {
         entity.mainSegment.render(entity, x, y, z, entityYaw, partialTicks);
      }

      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(BigLightningStrike entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }

   public static class BigLightningStrikeFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new RenderBigLightningStrike(manager);
      }
   }
}
