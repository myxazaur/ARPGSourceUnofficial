package com.vivern.arpg.renders;

import com.vivern.arpg.elements.models.TeamBannerModel;
import com.vivern.arpg.tileentity.TileTeamBanner;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TeamBannerTESR extends TileEntitySpecialRenderer<TileTeamBanner> {
   public static TeamBannerModel model = new TeamBannerModel();
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/team_banner_model_tex.png");

   public void render(TileTeamBanner te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
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
         this.bindTexture(tex);
      }

      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      if (destroyStage < 0) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      }

      GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      if (te != null) {
         GlStateManager.rotate(te.rotation, 0.0F, 1.0F, 0.0F);
      }

      model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      if (te != null) {
         if (te.savedPlayerInfo == null) {
            te.savedPlayerInfo = new NetworkPlayerInfo(new GameProfile(te.playerUUID == null ? UUID.randomUUID() : te.playerUUID, te.playername));
         }

         ResourceLocation textur = te.savedPlayerInfo.getLocationSkin();
         this.bindTexture(textur);
         model.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
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
}
