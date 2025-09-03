//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.entity.BetweenParticle;
import com.Vivern.Arpg.main.AnimationTimer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<BetweenParticle>`
@SideOnly(Side.CLIENT)
public class RenderBetween<T extends BetweenParticle> extends Render<BetweenParticle> {
   public RenderBetween(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(BetweenParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
      double dist = entity.distance;
      GlStateManager.pushMatrix();
      GlStateManager.depthMask(false);
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.rotate(-entity.rotatYaw, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(entity.rotatPitch, 1.0F, 0.0F, 0.0F);
      this.bindTexture(entity.texture);
      if (entity.light >= 0) {
         GL11.glDisable(2896);
      }

      GL11.glEnable(3042);
      GL11.glDisable(2884);
      if (entity.alphaGlowing) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      } else {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      if (entity.light >= 0) {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
      }

      GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
      if (entity.tracker != null) {
         entity.tracker.render(entity, x, y, z, entityYaw, partialTicks);
      }

      float timee = entity.softAnimation ? AnimationTimer.tick : entity.ticksExisted + partialTicks;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-entity.scale, 0.0, 0.0)
         .tex(0.0, entity.texstart + dist / entity.texlengh + timee * entity.animation + entity.offset)
         .endVertex();
      bufferbuilder.pos(-entity.scale, 0.0, dist).tex(0.0, 0.0F + timee * entity.animation + entity.offset).endVertex();
      bufferbuilder.pos(entity.scale, 0.0, dist).tex(1.0, 0.0F + timee * entity.animation + entity.offset).endVertex();
      bufferbuilder.pos(entity.scale, 0.0, 0.0)
         .tex(1.0, entity.texstart + dist / entity.texlengh + timee * entity.animation + entity.offset)
         .endVertex();
      tessellator.draw();
      GlStateManager.disableRescaleNormal();
      GL11.glDisable(3042);
      if (entity.light >= 0) {
         GL11.glEnable(2896);
      }

      GL11.glEnable(2884);
      GlStateManager.depthMask(true);
      GlStateManager.popMatrix();
      if (entity.horizontal) {
         GlStateManager.pushMatrix();
         GlStateManager.depthMask(false);
         GlStateManager.translate((float)x, (float)y, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(-entity.rotatYaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.rotatPitch, 1.0F, 0.0F, 0.0F);
         this.bindTexture(entity.texture);
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         GL11.glDisable(2884);
         if (entity.alphaGlowing) {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         }

         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
         GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
         Tessellator tessellator2 = Tessellator.getInstance();
         BufferBuilder bufferbuilder2 = tessellator2.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder2.pos(0.0, -entity.scale, 0.0)
            .tex(0.0, entity.texstart + dist / entity.texlengh + timee * entity.animation + entity.offset)
            .endVertex();
         bufferbuilder2.pos(0.0, -entity.scale, dist).tex(0.0, 0.0F + timee * entity.animation + entity.offset).endVertex();
         bufferbuilder2.pos(0.0, entity.scale, dist).tex(1.0, 0.0F + timee * entity.animation + entity.offset).endVertex();
         bufferbuilder2.pos(0.0, entity.scale, 0.0)
            .tex(1.0, entity.texstart + dist / entity.texlengh + timee * entity.animation + entity.offset)
            .endVertex();
         tessellator2.draw();
         GlStateManager.disableRescaleNormal();
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         GL11.glEnable(2884);
         GlStateManager.depthMask(true);
         GlStateManager.popMatrix();
      }

      if (entity.alphaGlowing) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(BetweenParticle entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }
}
