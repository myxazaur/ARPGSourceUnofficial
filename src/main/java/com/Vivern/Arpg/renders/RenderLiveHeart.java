package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.entity.EntityLiveHeart;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.CullFace;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLiveHeart<T extends EntityLiveHeart> extends Render<T> {
   public final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
   public static ItemStack torender;
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/sobig-3.png");

   public RenderLiveHeart(RenderManager renderManagerIn) {
      super(renderManagerIn);
      torender = new ItemStack(ItemsRegister.LIVEHEART);
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y + 0.18, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.rotate(AnimationTimer.tick * 5 + entity.randomRotat, 0.0F, 1.0F, 0.0F);
      GlStateManager.scale(1.2F, 1.2F, 1.2F);
      this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      if (this.renderOutlines) {
         GlStateManager.enableColorMaterial();
         GlStateManager.enableOutlineMode(this.getTeamColor(entity));
      }

      float toft = (entity.ticksExisted + entity.randomRotat) % 60 + partialTicks;
      float light = Math.max(MathHelper.sin(GetMOP.getfromto(toft, 20.0F, 40.0F) * 3.2F), 0.2F) * 200.0F;
      AbstractMobModel.light((int)light, true);
      this.renderItem(torender, TransformType.GROUND, entity.getRed(), entity.getGreen(), entity.getBlue(), entity.randTranslate);
      AbstractMobModel.returnlight();
      if (this.renderOutlines) {
         GlStateManager.disableOutlineMode();
         GlStateManager.disableColorMaterial();
      }

      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected int getTeamColor(T entityIn) {
      Vec3d col = ColorConverters.getTeamColor(entityIn.clientTeamColor);
      return ColorConverters.RGBtoDecimal((float)col.x, (float)col.y, (float)col.z);
   }

   protected ResourceLocation getEntityTexture(EntityLiveHeart entity) {
      return TextureMap.LOCATION_BLOCKS_TEXTURE;
   }

   public void renderItem(ItemStack stack, TransformType cameraTransformType, float r, float g, float b, float translate) {
      if (!stack.isEmpty()) {
         IBakedModel ibakedmodel = this.itemRenderer.getItemModelWithOverrides(stack, (World)null, (EntityLivingBase)null);
         this.renderItemModel(stack, ibakedmodel, cameraTransformType, false, r, g, b, translate);
      }
   }

   protected void renderItemModel(
      ItemStack stack, IBakedModel bakedmodel, TransformType transform, boolean leftHanded, float r, float g, float b, float translate
   ) {
      if (!stack.isEmpty()) {
         Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         Minecraft.getMinecraft().renderEngine.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
         GlStateManager.enableRescaleNormal();
         GlStateManager.alphaFunc(516, 0.1F);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
         GlStateManager.pushMatrix();
         bakedmodel = ForgeHooksClient.handleCameraTransforms(bakedmodel, transform, leftHanded);
         this.renderItem(stack, bakedmodel, r, g, b, translate);
         GlStateManager.cullFace(CullFace.BACK);
         GlStateManager.popMatrix();
         GlStateManager.disableRescaleNormal();
         GlStateManager.disableBlend();
         Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         Minecraft.getMinecraft().renderEngine.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
      }
   }

   private void renderEffect(IBakedModel model, float r, float g, float b, float translate) {
      float mix = r + g + b;
      if (mix < 3.0F) {
         float ex = MathHelper.clamp((3.0F - mix) * 2.0F, 0.0F, 1.0F);
         int col = -ColorConverters.RGBtoDecimal(1.0F - r * ex, 1.0F - g * ex, 1.0F - b * ex);
         GlStateManager.depthMask(false);
         GlStateManager.depthFunc(514);
         GlStateManager.disableLighting();
         GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE);
         Minecraft.getMinecraft().renderEngine.bindTexture(tex);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         float scale = 13.0F;
         GlStateManager.scale(scale, scale, scale);
         float f = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 15.0F;
         GlStateManager.translate(f + translate, 0.0F, 0.0F);
         GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
         this.renderModel(model, col, ItemStack.EMPTY);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.scale(scale, scale, scale);
         float f1 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F / 15.0F;
         GlStateManager.translate(-f1, 0.0F, 0.0F);
         GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
         this.renderModel(model, col, ItemStack.EMPTY);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.enableLighting();
         GlStateManager.depthFunc(515);
         GlStateManager.depthMask(true);
         Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      }
   }

   public void renderItem(ItemStack stack, IBakedModel model, float r, float g, float b, float translate) {
      if (!stack.isEmpty()) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(-0.5F, -0.5F, -0.5F);
         this.renderModel(model, stack);
         this.renderEffect(model, r, g, b, translate);
         GlStateManager.popMatrix();
      }
   }

   private void renderModel(IBakedModel model, ItemStack stack) {
      this.renderModel(model, -1, stack);
   }

   private void renderModel(IBakedModel model, int color, ItemStack stack) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

      for (EnumFacing enumfacing : EnumFacing.values()) {
         this.itemRenderer.renderQuads(bufferbuilder, model.getQuads((IBlockState)null, enumfacing, 0L), color, stack);
      }

      this.itemRenderer.renderQuads(bufferbuilder, model.getQuads((IBlockState)null, (EnumFacing)null, 0L), color, stack);
      tessellator.draw();
   }

   public static class RenderLiveHeartFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new RenderLiveHeart(manager);
      }
   }
}
