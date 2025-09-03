//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class LayerLaser implements LayerRenderer<EntityLivingBase> {
   public void doRenderLayer(
      EntityLivingBase player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale
   ) {
      float x = (float)player.posX;
      float y = (float)player.posY;
      float z = (float)player.posZ;
      GlStateManager.pushMatrix();
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 20.0F);
      GlStateManager.scale(0.85F, 0.85F, 0.85F);
      GlStateManager.rotate(-headPitch, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-netHeadYaw, 0.0F, 1.0F, 0.0F);
      if (player.isSneaking()) {
         GlStateManager.rotate(-30.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0F, -0.155F, 0.04F);
      }

      Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(Items.APPLE), player, TransformType.HEAD, false);
      GlStateManager.popMatrix();
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}
