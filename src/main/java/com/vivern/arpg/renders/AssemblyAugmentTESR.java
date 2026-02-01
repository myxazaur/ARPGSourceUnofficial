package com.vivern.arpg.renders;

import com.vivern.arpg.blocks.AssemblyTable;
import com.vivern.arpg.elements.models.PlasmaAugmentModel;
import com.vivern.arpg.elements.models.PressAugmentModel;
import com.vivern.arpg.elements.models.TurningAugmentModel;
import com.vivern.arpg.elements.models.WeldAugmentModel;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.tileentity.TileAssemblyAugment;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class AssemblyAugmentTESR extends TileEntitySpecialRenderer<TileAssemblyAugment> {
   public static PressAugmentModel model_press = new PressAugmentModel();
   public static TurningAugmentModel model_turning = new TurningAugmentModel();
   public static WeldAugmentModel model_weld = new WeldAugmentModel();
   public static PlasmaAugmentModel model_plasma = new PlasmaAugmentModel();
   public static ResourceLocation tex_press = new ResourceLocation("arpg:textures/press_augment_model_tex.png");
   public static ResourceLocation tex_turning = new ResourceLocation("arpg:textures/turning_augment_model_tex.png");
   public static ResourceLocation tex_weld = new ResourceLocation("arpg:textures/weld_augment_model_tex.png");
   public static ResourceLocation tex_plasma = new ResourceLocation("arpg:textures/plasma_augment_model_tex.png");

   public void render(@Nullable TileAssemblyAugment te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      float blockRotation = 0.0F;
      ResourceLocation tex = tex_press;
      ModelBase model = model_press;
      float plasmaRenders = 1.0F;
      if (te != null) {
         IBlockState blockState = te.getWorld().getBlockState(te.getPos());
         Block block = blockState.getBlock();
         if (block == BlocksRegister.AUGMENTCUT) {
            tex = tex_turning;
            model = model_turning;
            te.setAnimationTurning(model_turning, partialTicks);
         }

         if (block == BlocksRegister.AUGMENTPRESS) {
            tex = tex_press;
            model = model_press;
            te.setAnimationPress(model_press, partialTicks);
         }

         if (block == BlocksRegister.AUGMENTWELD) {
            tex = tex_weld;
            model = model_weld;
            te.setAnimationWeld(model_weld, partialTicks);
         }

         if (block == BlocksRegister.AUGMENTPLASMA) {
            tex = tex_plasma;
            model = model_plasma;
            te.setAnimationPlasmaSpray(model_plasma, partialTicks);
            plasmaRenders = te.plasmaToolOpening;
         }

         EnumFacing facing = (EnumFacing)blockState.getValue(AssemblyTable.FACING);
         if (facing != null) {
            blockRotation = facing.getHorizontalAngle();
         }
      } else {
         if (destroyStage == 1) {
            tex = tex_turning;
            model = model_turning;
         }

         if (destroyStage == 2) {
            tex = tex_press;
            model = model_press;
         }

         if (destroyStage == 3) {
            tex = tex_weld;
            model = model_weld;
         }

         if (destroyStage == 4) {
            tex = tex_plasma;
            model = model_plasma;
            plasmaRenders = 0.0F;
         }
      }

      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.disableCull();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      if (te != null && destroyStage >= 0) {
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
      model.render(null, plasmaRenders, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.enableCull();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (te != null && destroyStage >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }
   }
}
