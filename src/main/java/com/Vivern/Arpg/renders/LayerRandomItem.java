package com.Vivern.Arpg.renders;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerRandomItem implements LayerRenderer<EntityLivingBase> {
   public RenderLivingBase<?> renderer;
   public float yOffset;
   public boolean inverseLayers;

   public LayerRandomItem(float yOffset, boolean inverseLayers) {
      this.yOffset = yOffset;
      this.inverseLayers = inverseLayers;
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
      Random random = new Random(entitylivingbaseIn.getEntityId());
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, this.yOffset, 0.0F);
      GlStateManager.rotate(
         (float)random.nextGaussian() * 6.0F, (float)random.nextGaussian(), (float)random.nextGaussian(), (float)random.nextGaussian()
      );
      GlStateManager.enableRescaleNormal();
      Minecraft.getMinecraft().getRenderItem().renderItem(entitylivingbaseIn.getHeldItemMainhand(), TransformType.GROUND);
      GlStateManager.popMatrix();
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}
