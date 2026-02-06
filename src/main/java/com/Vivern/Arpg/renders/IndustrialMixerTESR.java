package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.blocks.IndustrialMixer;
import com.Vivern.Arpg.elements.models.IndustrialMixerModel;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.tileentity.TileIndustrialMixer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class IndustrialMixerTESR extends TileEntitySpecialRenderer<TileIndustrialMixer> {
   public static IndustrialMixerModel model = new IndustrialMixerModel();
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/industrial_mixer_model_tex.png");

   public void render(@Nullable TileIndustrialMixer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      float rotorRotation = 0.0F;
      float blockRotation = 0.0F;
      if (te != null) {
         rotorRotation = GetMOP.partial(te.clientRotorRotation, te.prevclientRotorRotation, partialTicks);
         EnumFacing facing = (EnumFacing)te.getWorld().getBlockState(te.getPos()).getValue(IndustrialMixer.FACING);
         if (facing != null) {
            blockRotation = facing.getHorizontalAngle();
         }
      }

      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.disableCull();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      if (destroyStage >= 0) {
         this.bindTexture(DESTROY_STAGES[destroyStage]);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scale(4.0F, 4.0F, 1.0F);
         GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.matrixMode(5888);
      } else {
         this.bindTexture(tex);
      }

      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.501F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      GlStateManager.rotate(blockRotation, 0.0F, 1.0F, 0.0F);
      model.render(null, rotorRotation, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.enableCull();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (destroyStage >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }
   }
}
