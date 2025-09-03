//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.entity.EntityCoin;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.ColorConverters;
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
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCoin<T extends EntityCoin> extends Render<T> {
   public final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
   public static ItemStack torender;
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/sobig-3.png");

   public RenderCoin(RenderManager renderManagerIn) {
      super(renderManagerIn);
      torender = new ItemStack(ItemsRegister.COIN);
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y + 0.1, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.rotate(AnimationTimer.tick * 5 + entity.randomRotat, 0.0F, 1.0F, 0.0F);
      GlStateManager.scale(0.8F, 0.8F, 0.8F);
      this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      if (this.renderOutlines) {
         GlStateManager.enableColorMaterial();
         GlStateManager.enableOutlineMode(this.getTeamColor(entity));
      }

      GlStateManager.pushMatrix();
      int coins = entity.store;
      String stcoins = "" + coins;
      int stlength = stcoins.length();
      float size = -0.035F + 0.003F * stlength + 0.009F;
      GlStateManager.translate(0.0, 0.22 - stlength * 0.01, -0.02);
      GlStateManager.scale(size, size, size);
      GlStateManager.translate(stlength * -3.0F, 0.0F, 0.0F);
      Minecraft.getMinecraft().fontRenderer.drawString(stcoins, 0, 0, 7028224);
      GlStateManager.popMatrix();
      this.renderItem(torender, TransformType.GROUND, entity.getRed(), entity.getGreen(), entity.getBlue(), entity.randTranslate);
      if (this.renderOutlines) {
         GlStateManager.disableOutlineMode();
         GlStateManager.disableColorMaterial();
      }

      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(EntityCoin entity) {
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

   public static class RenderCoinFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new RenderCoin(manager);
      }
   }
}
