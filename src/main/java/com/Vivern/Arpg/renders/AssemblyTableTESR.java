//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.blocks.AssemblyTable;
import com.Vivern.Arpg.elements.models.AssemblyTableModel;
import com.Vivern.Arpg.tileentity.TileAssemblyTable;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class AssemblyTableTESR extends TileEntitySpecialRenderer<TileAssemblyTable> {
   public static AssemblyTableModel model = new AssemblyTableModel();
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/assembly_table_model_tex.png");

   public void render(@Nullable TileAssemblyTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      float blockRotation = 0.0F;
      if (te != null) {
         EnumFacing facing = (EnumFacing)te.getWorld().getBlockState(te.getPos()).getValue(AssemblyTable.FACING);
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
      if (te != null) {
         for (int i = 0; i < te.animations.length; i++) {
            te.animations[i].setAnimation(model, partialTicks);
            model.render(null, te.animations[i].tool, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         }
      } else {
         for (int i = 1; i <= 3; i++) {
            model.render(null, i, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         }
      }

      model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
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
