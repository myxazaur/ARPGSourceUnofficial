package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.entity.EntitySunrise;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSpear<T extends EntitySunrise> extends Render<T> {
   private final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
   protected final Item item;
   protected final float scale;
   protected boolean light;

   public RenderSpear(RenderManager renderManagerIn, Item itemIn, float scale, boolean light) {
      super(renderManagerIn);
      this.item = itemIn;
      this.scale = scale;
      this.light = light;
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.disableCull();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      if (this.light) {
         GL11.glDisable(2896);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 220.0F, 240.0F);
      }

      if (entity.link) {
         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
      }

      if (!entity.link) {
         GlStateManager.rotate(entity.rotationYaw - 90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
      }

      GlStateManager.rotate(225.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.scale(this.scale, this.scale, this.scale);
      this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      if (this.renderOutlines) {
         GlStateManager.enableColorMaterial();
         GlStateManager.enableOutlineMode(this.getTeamColor(entity));
      }

      this.itemRenderer.renderItem(this.getStackToRender(entity), TransformType.GROUND);
      if (this.renderOutlines) {
         GlStateManager.disableOutlineMode();
         GlStateManager.disableColorMaterial();
      }

      if (this.light) {
         GL11.glEnable(2896);
      }

      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   public ItemStack getStackToRender(T entityIn) {
      return new ItemStack(this.item);
   }

   protected ResourceLocation getEntityTexture(EntitySunrise entity) {
      return TextureMap.LOCATION_BLOCKS_TEXTURE;
   }
}
