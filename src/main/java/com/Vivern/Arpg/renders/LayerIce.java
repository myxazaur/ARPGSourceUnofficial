package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.potions.PotionEffects;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerIce implements LayerRenderer<EntityLivingBase> {
   private final RenderLivingBase<?> renderer;

   public LayerIce(RenderLivingBase<?> rendererIn) {
      this.renderer = rendererIn;
   }

   public void doRenderLayer(
      EntityLivingBase entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch,
      float scale
   ) {
      PotionEffect effect = entitylivingbaseIn.getActivePotionEffect(PotionEffects.FREEZING);
      if (effect != null) {
         float baseSize = Math.min(effect.getDuration(), 60) / 60.0F * 0.235F * (entitylivingbaseIn.width * entitylivingbaseIn.height);
         int i = Math.min(5 + (effect.getAmplifier() >= 4 ? 8 + effect.getAmplifier() - 4 : effect.getAmplifier() * 2), 20);
         Random random = new Random(entitylivingbaseIn.getEntityId());
         RenderHelper.disableStandardItemLighting();

         for (int j = 0; j < i; j++) {
            ModelRenderer modelrenderer = this.renderer.getMainModel().getRandomModelBox(random);
            if (modelrenderer.cubeList.size() > 0) {
               GlStateManager.pushMatrix();
               ModelBox modelbox = (ModelBox)modelrenderer.cubeList.get(random.nextInt(modelrenderer.cubeList.size()));
               modelrenderer.postRender(0.0625F);
               float f = random.nextFloat();
               float f1 = random.nextFloat();
               float f2 = random.nextFloat();
               float f3 = (modelbox.posX1 + (modelbox.posX2 - modelbox.posX1) * f) / 16.0F;
               float f4 = (modelbox.posY1 + (modelbox.posY2 - modelbox.posY1) * f1) / 16.0F;
               float f5 = (modelbox.posZ1 + (modelbox.posZ2 - modelbox.posZ1) * f2) / 16.0F;
               float scl = baseSize + random.nextFloat() * 0.03F;
               GlStateManager.scale(scl, scl, scl);
               f = f * 2.0F - 1.0F;
               f1 = f1 * 2.0F - 1.0F;
               f2 = f2 * 2.0F - 1.0F;
               f *= -1.0F;
               f1 *= -1.0F;
               f2 *= -1.0F;
               float f6 = MathHelper.sqrt(f * f + f2 * f2);
               float rotationYaw = (float)(Math.atan2(f, f2) * (180.0 / Math.PI));
               float rotationPitch = (float)(Math.atan2(f1, f6) * (180.0 / Math.PI));
               GlStateManager.rotate(rotationYaw, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(rotationPitch, 1.0F, 0.0F, 0.0F);
               GlStateManager.translate(f3, f4, f5);
               double d0 = 0.0;
               double d1 = 0.0;
               double d2 = 0.0;
               Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
               BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
               GlStateManager.enableBlend();
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               blockrendererdispatcher.renderBlockBrightness(Blocks.ICE.getDefaultState(), entitylivingbaseIn.getBrightness());
               GlStateManager.disableBlend();
               GlStateManager.popMatrix();
            }
         }

         RenderHelper.enableStandardItemLighting();
      }
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}
