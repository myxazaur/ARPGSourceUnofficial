package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.Beaker;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.tileentity.TileRetort;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RetortTESR extends TileEntitySpecialRenderer<TileRetort> {
   public static float[] void_additional_color = new float[]{0.5F, 0.5F, 0.5F};

   public void render(TileRetort te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      Beaker.BeakerFluid beakerFluid = te.fluid;
      if (beakerFluid != null && !beakerFluid.isEmpty) {
         GlStateManager.pushMatrix();
         GlStateManager.enableCull();
         GlStateManager.translate((float)x + 0.5F, (float)y, (float)z + 0.5F);
         float height = 0.0625F;
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         float boundOffset = 0.025F;

         for (int i = 0; i <= beakerFluid.maxLay; i++) {
            if (beakerFluid.elements[i] > 0) {
               byte modifier = beakerFluid.modifiers[i];
               ShardType shardType = ShardType.byId(beakerFluid.elements[i]);
               float[] color = shardType == ShardType.VOID && modifier != 0
                  ? void_additional_color
                  : new float[]{shardType.colorR, shardType.colorG, shardType.colorB};
               boolean firstLay = i == 0;
               boolean lastLay = i == beakerFluid.maxLay;
               if (firstLay && lastLay) {
                  TEISROther.renderSides(
                     0.0, 0.1875F + height * i - boundOffset, 0.0, color, color, 0.25F, height + boundOffset * 2.0F, partialTicks, 0.45F, modifier
                  );
                  TEISROther.renderCap(0.0, 0.1875F + height * i - boundOffset, 0.0, color, false, 0.25F, height, partialTicks, 0.45F, modifier);
                  TEISROther.renderCap(0.0, 0.1875F + height * i, 0.0, color, true, 0.25F, height + boundOffset, partialTicks, 0.45F, modifier);
               } else if (firstLay) {
                  TEISROther.renderSides(0.0, 0.1875F + height * i - boundOffset, 0.0, color, color, 0.25F, height + boundOffset, partialTicks, 0.45F, modifier);
                  TEISROther.renderCap(0.0, 0.1875F + height * i - boundOffset, 0.0, color, false, 0.25F, height, partialTicks, 0.45F, modifier);
               } else if (lastLay) {
                  TEISROther.renderSides(0.0, 0.1875F + height * i, 0.0, color, color, 0.25F, height + boundOffset, partialTicks, 0.45F, modifier);
                  TEISROther.renderCap(0.0, 0.1875F + height * i, 0.0, color, true, 0.25F, height + boundOffset, partialTicks, 0.45F, modifier);
               } else {
                  TEISROther.renderSides(0.0, 0.1875F + height * i, 0.0, color, color, 0.25F, height, partialTicks, 0.45F, modifier);
               }
            }
         }

         float[] prevColor = null;

         for (int ix = 0; ix <= beakerFluid.maxLay; ix++) {
            if (beakerFluid.elements[ix] > 0) {
               ShardType shardType = ShardType.byId(beakerFluid.elements[ix]);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, shardType == ShardType.VOID ? DestFactor.ONE_MINUS_SRC_ALPHA : DestFactor.ONE);
               float[] color = new float[]{shardType.colorR, shardType.colorG, shardType.colorB};
               if (prevColor == null) {
                  prevColor = color;
               }

               byte modifier = beakerFluid.modifiers[ix];
               TEISROther.renderSides(0.0, 0.1875F + height * ix, 0.0, color, prevColor, 0.225F, height, partialTicks, 0.65F, modifier);
               if (ix == 0) {
                  TEISROther.renderCap(0.0, 0.1875F + height * ix, 0.0, color, false, 0.225F, height, partialTicks, 0.65F, modifier);
               }

               if (ix == beakerFluid.maxLay) {
                  TEISROther.renderCap(0.0, 0.1875F + height * ix, 0.0, color, true, 0.225F, height, partialTicks, 0.65F, modifier);
               }

               prevColor = color;
            }
         }

         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.popMatrix();
         GlStateManager.disableCull();
      }
   }
}
