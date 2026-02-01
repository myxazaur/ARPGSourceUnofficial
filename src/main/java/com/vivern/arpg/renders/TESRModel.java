package com.vivern.arpg.renders;

import javax.annotation.Nullable;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TESRModel extends TileEntitySpecialRenderer<TileEntity> {
   public ModelBase model;
   public ResourceLocation texture;
   @Nullable
   public ITileDataToModelFloats dataToFloats;

   public TESRModel(ModelBase model, ResourceLocation texture) {
      this.model = model;
      this.texture = texture;
   }

   public TESRModel(ModelBase model, ResourceLocation texture, ITileDataToModelFloats dataToFloats) {
      this.model = model;
      this.texture = texture;
      this.dataToFloats = dataToFloats;
   }

   public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.disableCull();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      if (destroyStage >= 0) {
         this.bindTexture(DESTROY_STAGES[destroyStage]);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scale(4.0F, 4.0F, 1.0F);
         GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.matrixMode(5888);
      } else {
         this.bindTexture(this.texture);
      }

      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      if (te != null) {
         if (this.dataToFloats != null) {
            float[] floats = this.dataToFloats.getFloats(te);
            if (floats != null) {
               this.model
                  .render(
                     null,
                     floats[0],
                     floats.length > 1 ? floats[1] : 0.0F,
                     floats.length > 2 ? floats[2] : 0.0F,
                     floats.length > 3 ? floats[3] : 0.0F,
                     floats.length > 4 ? floats[4] : 0.0F,
                     0.0625F
                  );
            } else {
               this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            }
         } else {
            this.model
               .render(
                  null, te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), 0.0F, 0.0F, 0.0625F
               );
         }
      } else {
         this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      GlStateManager.enableCull();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (destroyStage >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }
   }

   public interface ITileDataToModelFloats {
      float[] getFloats(TileEntity var1);
   }
}
