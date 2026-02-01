package com.vivern.arpg.renders;

import com.vivern.arpg.dimensions.generationutils.BlockAtPos;
import com.vivern.arpg.elements.models.DungeonPortalModel;
import com.vivern.arpg.elements.models.DungeonPortalModel2;
import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.DimensionsRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.tileentity.TileDungeonPortal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class DungeonPortalTESR extends TileEntitySpecialRenderer<TileDungeonPortal> {
   public static DungeonPortalModel model = new DungeonPortalModel();
   public static DungeonPortalModel2 model2 = new DungeonPortalModel2();
   public static Framebuffer framebuffer;
   private static Minecraft mc = Minecraft.getMinecraft();
   private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("arpg:textures/portal_dungeon.png");
   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("arpg:textures/portal_dungeon2.png");

   public void render(TileDungeonPortal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      if (te.notRendered) {
         double FX = x + 0.5;
         double FZ = z + 0.5;
         if (te.mainBlockPosition != null) {
            FX += te.mainBlockPosition.getX() - te.getPos().getX();
            FZ += te.mainBlockPosition.getZ() - te.getPos().getZ();
         }

         Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
         if (entity != null) {
            if (framebuffer == null) {
               framebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
            }

            if (mc.displayWidth != framebuffer.framebufferWidth || mc.displayHeight != framebuffer.framebufferHeight) {
               framebuffer.createBindFramebuffer(mc.displayWidth, mc.displayHeight);
            }

            float stretching = (float)mc.displayWidth / mc.displayHeight;
            framebuffer.bindFramebuffer(true);
            GlStateManager.enableDepth();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableFog();
            this.bindTexture(END_SKY_TEXTURE);
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.translate(0.0F, entity.getEyeHeight(), 0.0F);
            GlStateManager.rotate(-GetMOP.partial(entity.rotationYaw, entity.prevRotationYaw, partialTicks), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(GetMOP.partial(entity.rotationPitch, entity.prevRotationPitch, partialTicks), 1.0F, 0.0F, 0.0F);
            if (destroyStage < 0) {
               GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
            }

            float mult = -1.0F;
            float xx = (float)FZ * mult;
            float yy = (float)(-FX) * mult / stretching;
            float zz = 7.0F;
            GlStateManager.translate(xx, yy, zz);
            GlStateManager.scale(stretching * 1.5F, 1.5F, 1.5F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            this.bindTexture(END_PORTAL_TEXTURE);
            model2.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GlStateManager.enableBlend();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200.0F, 200.0F);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableAlpha();
            GlStateManager.depthMask(false);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 80.0F, 0.0F);
            float alphaa0 = MathHelper.clamp(
               0.08F + MathHelper.sin(AnimationTimer.tick / 100.0F) * 0.015F + AnimationTimer.rainbow1 * 6.25E-4F, 0.04F, 0.2F
            );
            float size = 2.5F;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);

            for (int i = 0; i < 30; i++) {
               float alphaa;
               if (i == 0) {
                  alphaa = 1.0F;
                  size = 1000.0F;
               } else {
                  alphaa = alphaa0;
                  size = 2.5F;
               }

               float offsetY = -2.6F * i;
               bufferbuilder.pos(-size, offsetY, -size).color(0.0F, 0.0F, 0.0F, alphaa).endVertex();
               bufferbuilder.pos(-size, offsetY, size).color(0.0F, 0.0F, 0.0F, alphaa).endVertex();
               bufferbuilder.pos(size, offsetY, size).color(0.0F, 0.0F, 0.0F, alphaa).endVertex();
               bufferbuilder.pos(size, offsetY, -size).color(0.0F, 0.0F, 0.0F, alphaa).endVertex();
            }

            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.shadeModel(7424);
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.popMatrix();
            GlStateManager.enableFog();
         }

         mc.getFramebuffer().bindFramebuffer(false);
         framebuffer.bindFramebufferTexture();
         GlStateManager.pushMatrix();
         GlStateManager.enableDepth();
         GlStateManager.depthFunc(515);
         GlStateManager.depthMask(true);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.translate(FX, (float)y, FZ);
         float xx = (float)FZ * 0.22F;
         float yy = (float)FX * 0.22F;
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         float f = 0.25F;
         bufferbuilder.pos(-2.5, f, -2.5).tex(0.0F + xx, 0.0F + yy).endVertex();
         bufferbuilder.pos(-2.5, f, 2.5).tex(1.0F + xx, 0.0F + yy).endVertex();
         bufferbuilder.pos(2.5, f, 2.5).tex(1.0F + xx, 1.0F + yy).endVertex();
         bufferbuilder.pos(2.5, f, -2.5).tex(0.0F + xx, 1.0F + yy).endVertex();
         tessellator.draw();
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
         framebuffer.framebufferClear();
         mc.getFramebuffer().bindFramebuffer(false);
         this.setFalseRenderToAll(te);
      } else {
         te.notRendered = true;
      }
   }

   public void setFalseRenderToAll(TileDungeonPortal te) {
      for (BlockPos bpmemb : DimensionsRegister.teleporterDUNGEON.membraneConfiguration) {
         BlockPos poss = te.getPos().add(bpmemb);
         boolean main = true;

         for (BlockAtPos bap : DimensionsRegister.teleporterDUNGEON.frameConfiguration) {
            if (te.getWorld().getBlockState(poss.add(bap)).getBlock() != bap.state.getBlock()) {
               main = false;
               break;
            }
         }

         if (main) {
            for (BlockPos posto : DimensionsRegister.teleporterDUNGEON.membraneConfiguration) {
               TileEntity tile = te.getWorld().getTileEntity(poss.add(posto));
               if (tile instanceof TileDungeonPortal) {
                  if (tile != te) {
                     ((TileDungeonPortal)tile).notRendered = false;
                  }

                  ((TileDungeonPortal)tile).mainBlockPosition = poss;
               }
            }

            return;
         }
      }
   }
}
