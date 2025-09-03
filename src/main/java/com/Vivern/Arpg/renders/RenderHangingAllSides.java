//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.entity.EntityHangingAllSides;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHangingAllSides extends Render<EntityHangingAllSides> {
   private final Minecraft mc = Minecraft.getMinecraft();
   private static final ResourceLocation MAP_BACKGROUND_TEXTURES = new ResourceLocation("textures/map/map_background.png");
   private final ModelResourceLocation itemFrameModel = new ModelResourceLocation("item_frame", "normal");
   private final ModelResourceLocation mapModel = new ModelResourceLocation("item_frame", "map");

   public RenderHangingAllSides(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(EntityHangingAllSides entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      BlockPos blockpos = entity.getHangingPosition();
      double d0 = blockpos.getX() - entity.posX + x;
      double d1 = blockpos.getY() - entity.posY + y;
      double d2 = blockpos.getZ() - entity.posZ + z;
      GlStateManager.translate(d0 + 0.5, d1 + 0.5, d2 + 0.5);
      GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);
      this.renderManager.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
      ModelManager modelmanager = blockrendererdispatcher.getBlockModelShapes().getModelManager();
      IBakedModel ibakedmodel = null;
      if (entity.getDisplayedItem().getItem() instanceof ItemMap) {
         ibakedmodel = modelmanager.getModel(this.mapModel);
      }

      if (ibakedmodel != null) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(-0.5F, -0.5F, -0.5F);
         if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
         }

         blockrendererdispatcher.getBlockModelRenderer().renderModelBrightnessColor(ibakedmodel, 1.0F, 1.0F, 1.0F, 1.0F);
         if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
         }

         GlStateManager.popMatrix();
      }

      GlStateManager.translate(0.0F, 0.0F, 0.4375F);
      this.renderItem(entity);
      GlStateManager.popMatrix();
      if (entity.facingDirection != null) {
         this.renderName(entity, x + entity.facingDirection.getXOffset() * 0.3F, y - 0.25, z + entity.facingDirection.getZOffset() * 0.3F);
      }
   }

   @Nullable
   protected ResourceLocation getEntityTexture(EntityHangingAllSides entity) {
      return null;
   }

   private void renderItem(EntityHangingAllSides itemFrame) {
      ItemStack itemstack = itemFrame.getDisplayedItem();
      if (!itemstack.isEmpty()) {
         GlStateManager.pushMatrix();
         GlStateManager.disableLighting();
         boolean flag = itemstack.getItem() instanceof ItemMap;
         int i = flag ? itemFrame.getRotation() % 4 * 2 : itemFrame.getRotation();
         GlStateManager.rotate(i * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);
         if (flag) {
            this.renderManager.renderEngine.bindTexture(MAP_BACKGROUND_TEXTURES);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            float f = 0.0078125F;
            GlStateManager.scale(0.0078125F, 0.0078125F, 0.0078125F);
            GlStateManager.translate(-64.0F, -64.0F, 0.0F);
            MapData mapdata = ((ItemMap)itemstack.getItem()).getMapData(itemstack, itemFrame.world);
            GlStateManager.translate(0.0F, 0.0F, -1.0F);
            if (mapdata != null) {
               this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, true);
            }
         } else {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(itemstack, TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
         }

         GlStateManager.enableLighting();
         GlStateManager.popMatrix();
      }
   }

   protected void renderName(EntityHangingAllSides entity, double x, double y, double z) {
      if (Minecraft.isGuiEnabled()
         && !entity.getDisplayedItem().isEmpty()
         && entity.getDisplayedItem().hasDisplayName()
         && this.renderManager.pointedEntity == entity) {
         double d0 = entity.getDistanceSq(this.renderManager.renderViewEntity);
         float f = entity.isSneaking() ? 32.0F : 64.0F;
         if (d0 < f * f) {
            String s = entity.getDisplayedItem().getDisplayName();
            this.renderLivingLabel(entity, s, x, y, z, 64);
         }
      }
   }

   public static class RenderHangingAllSidesFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new RenderHangingAllSides(manager);
      }
   }
}
