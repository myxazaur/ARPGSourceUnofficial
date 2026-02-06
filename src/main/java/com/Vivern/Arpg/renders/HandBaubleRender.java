package com.Vivern.Arpg.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;

public class HandBaubleRender {
   public static void doRenderLayer(EntityLivingBase entitylivingbaseIn, ItemStack itemstack, EnumHandSide handside) {
      doRenderLayer(entitylivingbaseIn, itemstack, handside, TransformType.HEAD);
   }

   public static void doRenderLayer(EntityLivingBase entitylivingbaseIn, ItemStack itemstack, EnumHandSide handside, TransformType ttype) {
      Render rend = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(entitylivingbaseIn);
      if (rend != null && rend instanceof RenderLivingBase && !itemstack.isEmpty()) {
         GlStateManager.pushMatrix();
         if (((RenderLivingBase)rend).getMainModel().isChild) {
            float f = 0.5F;
            GlStateManager.translate(0.0F, 0.75F, 0.0F);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
         }

         renderHeldItem(entitylivingbaseIn, itemstack, ttype, handside, (RenderLivingBase)rend);
         GlStateManager.popMatrix();
      }
   }

   public static void renderHeldItem(
      EntityLivingBase p_188358_1_, ItemStack p_188358_2_, TransformType p_188358_3_, EnumHandSide handSide, RenderLivingBase renderlb
   ) {
      if (!p_188358_2_.isEmpty()) {
         GlStateManager.pushMatrix();
         if (p_188358_1_.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
         }

         translateToHand(handSide, renderlb);
         GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
         boolean flag = handSide == EnumHandSide.LEFT;
         GlStateManager.translate((flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
         PlayerAnimations.instance.renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
         GlStateManager.popMatrix();
      }
   }

   public static void translateToHand(EnumHandSide p_191361_1_, RenderLivingBase renderlb) {
      ((ModelBiped)renderlb.getMainModel()).postRenderArm(0.0625F, p_191361_1_);
   }
}
