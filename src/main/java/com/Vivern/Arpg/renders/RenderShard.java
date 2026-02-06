package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.entity.EntityShard;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShard<T extends EntityShard> extends Render<T> {
   public RenderShard(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      if (entity != null && entity.shardType != null) {
         entity.shardType.renderShardEntity(entity, x, y, z, entityYaw, partialTicks, this.renderManager);
         super.doRender(entity, x, y, z, entityYaw, partialTicks);
      }
   }

   protected ResourceLocation getEntityTexture(T entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }
}
