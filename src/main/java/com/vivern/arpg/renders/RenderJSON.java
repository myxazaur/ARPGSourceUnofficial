package com.vivern.arpg.renders;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.pipeline.LightUtil;

public class RenderJSON<T extends Entity> extends Render<T> {
   public ModelResourceLocation location = new ModelResourceLocation("ion_battery", "inventory");
   public IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(this.location);
   private final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

   public RenderJSON(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      List<BakedQuad> bakedquads = this.model.getQuads(null, null, 4L);
      if (!bakedquads.isEmpty()) {
         for (BakedQuad var12 : bakedquads) {
            ;
         }
      }

      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   private void renderModel(IBakedModel model) {
      int color = -1;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

      for (EnumFacing enumfacing : EnumFacing.values()) {
         this.renderQuads(bufferbuilder, model.getQuads((IBlockState)null, enumfacing, 0L), color);
      }

      this.renderQuads(bufferbuilder, model.getQuads((IBlockState)null, (EnumFacing)null, 0L), color);
      tessellator.draw();
   }

   public void renderQuads(BufferBuilder renderer, List<BakedQuad> quads, int color) {
      int i = 0;

      for (int j = quads.size(); i < j; i++) {
         BakedQuad bakedquad = quads.get(i);
         int k = color;
         if (EntityRenderer.anaglyphEnable) {
            k = TextureUtil.anaglyphColor(color);
         }

         k |= -16777216;
         LightUtil.renderQuadColor(renderer, bakedquad, k);
      }
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return TextureMap.LOCATION_BLOCKS_TEXTURE;
   }
}
