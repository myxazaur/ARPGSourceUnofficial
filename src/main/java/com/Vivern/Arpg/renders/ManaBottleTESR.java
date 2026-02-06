package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.ManaBottleModel;
import com.Vivern.Arpg.elements.models.ManaBottleModelOverlay;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.tileentity.TileManaBottle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class ManaBottleTESR extends TileEntitySpecialRenderer<TileManaBottle> {
   public static ManaBottleModel model = new ManaBottleModel();
   public static ManaBottleModelOverlay model_overlay = new ManaBottleModelOverlay();
   public static ResourceLocation tex_glass = new ResourceLocation("arpg:textures/mana_bottle_glass.png");
   public static ResourceLocation tex_silver = new ResourceLocation("arpg:textures/mana_bottle_silver.png");

   public void renderColoredGlowingCuboid(
      float fillValue,
      float y,
      float[] color,
      float[] gradientColor,
      float size,
      float height,
      float glowOffset,
      float partialTicks,
      boolean bottom,
      boolean top,
      byte modifier
   ) {
      if (fillValue > 0.0F) {
         TEISROther.renderColoredGlowingCuboid(0.0, y, 0.0, color, gradientColor, size, height * fillValue, glowOffset, partialTicks, bottom, top, modifier);
      }
   }

   public void render(TileManaBottle te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      float energyMax = 50.0F;
      float fullness = 0.0F;
      if (te != null) {
         energyMax = te.getManaBuffer().getManaStorageSize();
         fullness = te.getManaBuffer().getManaStored() / energyMax;
      } else {
         energyMax = (float)x;
         fullness = (float)(y / energyMax);
         x = 0.0;
         y = 0.0;
      }

      int type = 0;
      if (energyMax >= TileManaBottle.CAPACITY_MANABOTTLE_TIER_2) {
         type = 1;
      }

      if (energyMax >= TileManaBottle.CAPACITY_MANABOTTLE_TIER_3) {
         type = 2;
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x + 0.5F, (float)y, (float)z + 0.5F);
      float[] color = new float[]{0.47F, 0.65F, 1.0F};
      float[] color2 = new float[]{1.0F, 1.0F, 1.0F};
      if (type == 0) {
         this.renderColoredGlowingCuboid(
            GetMOP.getfromto(fullness, 0.0F, 0.6F), 0.0625F, color, color2, 0.25F, 0.375F, 0.03125F, partialTicks, true, true, (byte)4
         );
         this.renderColoredGlowingCuboid(
            GetMOP.getfromto(fullness, 0.6F, 0.9F), 0.4375F, color, color, 0.125F, 0.1875F, 0.03125F, partialTicks, false, true, (byte)4
         );
         this.renderColoredGlowingCuboid(
            GetMOP.getfromto(fullness, 0.9F, 1.0F), 0.625F, color, color, 0.03125F, 0.3125F, 0.03125F, partialTicks, false, true, (byte)4
         );
      }

      if (type == 1) {
         float min = 0.0625F;
         float max = 1.375F;
         float fillValue = fullness * (max - min);
         this.renderColoredGlowingCuboid(
            GetMOP.getfromto(fullness, 0.0F, 0.62F), 0.0625F, color, color2, 0.28125F, 0.5625F, 0.03125F, partialTicks, true, true, (byte)4
         );
         this.renderColoredGlowingCuboid(
            GetMOP.getfromto(fullness, 0.62F, 0.92F), 0.625F, color, color, 0.125F, 0.5F, 0.03125F, partialTicks, false, true, (byte)4
         );
         this.renderColoredGlowingCuboid(
            GetMOP.getfromto(fullness, 0.92F, 1.0F), 1.125F, color, color, 0.03125F, 0.25F, 0.03125F, partialTicks, false, true, (byte)4
         );
      }

      if (type == 2) {
         float min = 0.25F;
         float max = 1.625F;
         float fillValue = fullness * (max - min);
         this.renderColoredGlowingCuboid(
            GetMOP.getfromto(fullness, 0.0F, 0.75F), 0.25F, color, color2, 0.3125F, 0.625F, 0.03125F, partialTicks, true, true, (byte)4
         );
         this.renderColoredGlowingCuboid(
            GetMOP.getfromto(fullness, 0.75F, 0.95F), 0.875F, color, color, 0.1875F, 0.3125F, 0.03125F, partialTicks, false, true, (byte)4
         );
         this.renderColoredGlowingCuboid(
            GetMOP.getfromto(fullness, 0.95F, 1.0F), 1.1875F, color, color, 0.03125F, 0.4375F, 0.03125F, partialTicks, false, true, (byte)4
         );
      }

      GlStateManager.popMatrix();
      GlStateManager.enableBlend();
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F + type * -1, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      float openness = te == null ? 0.0F : -0.1875F * GetMOP.partial(te.openness, te.prevopenness, partialTicks);
      this.bindTexture(tex_glass);
      model.render(null, type, openness, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.disableCull();
      this.bindTexture(tex_silver);
      model_overlay.render(null, type, openness, 0.0F, 0.0F, 0.0F, 0.0625F);
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
}
