//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.entity.EntitySeaEffloresce;
import com.Vivern.Arpg.main.AnimationTimer;
import java.util.Random;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

public class RenderSeaEffloresce<T extends EntitySeaEffloresce> extends Render<T> {
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/sea_effloresce.png");

   public RenderSeaEffloresce(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      Random rand = new Random(entity.getEntityId());

      for (int i = 0; i < 8; i++) {
         double dist = (rand.nextFloat() + Math.sin((AnimationTimer.tick + rand.nextInt(500)) / 40.0F) + 1.0) / 2.0;
         float rotYaw = rand.nextInt(360) + (float)Math.sin((AnimationTimer.tick + rand.nextInt(360)) / 55.0F) * 30.0F;
         float rotPitch = rand.nextInt(360) + (float)Math.sin((AnimationTimer.tick + rand.nextInt(360)) / 55.0F) * 30.0F;
         float scale = 0.11F;
         float lengh = 8.0F;
         float animation = 0.3F;
         float Tickpl = 0.0F;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + 0.25, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(rotYaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(rotPitch, 1.0F, 0.0F, 0.0F);
         this.bindTexture(tex);
         GL11.glDisable(2884);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-scale, 0.0, 0.0).tex(0.0, 1.0 + dist / lengh + Tickpl * animation).endVertex();
         bufferbuilder.pos(-scale, 0.0, dist).tex(0.0, 0.0F + Tickpl * animation).endVertex();
         bufferbuilder.pos(scale, 0.0, dist).tex(1.0, 0.0F + Tickpl * animation).endVertex();
         bufferbuilder.pos(scale, 0.0, 0.0).tex(1.0, 1.0 + dist / lengh + Tickpl * animation).endVertex();
         bufferbuilder.pos(0.0, -scale + 0.0F, 0.0).tex(0.0, 1.0 + dist / lengh + Tickpl * animation).endVertex();
         bufferbuilder.pos(0.0, -scale, dist).tex(0.0, 0.0F + Tickpl * animation).endVertex();
         bufferbuilder.pos(0.0, scale, dist).tex(1.0, 0.0F + Tickpl * animation).endVertex();
         bufferbuilder.pos(0.0, scale + 0.0F, 0.0).tex(1.0, 1.0 + dist / lengh + Tickpl * animation).endVertex();
         tessellator.draw();
         GlStateManager.disableRescaleNormal();
         GL11.glEnable(2884);
         GlStateManager.popMatrix();
      }

      if (entity.thrower != null) {
         float f = (float)(entity.posX - entity.thrower.posX);
         float f1 = (float)(entity.posY - entity.thrower.posY);
         float f2 = (float)(entity.posZ - entity.thrower.posZ);
         double dist1sq = f * f + f1 * f1 + f2 * f2;
         float g = (float)(entity.prevPosX - entity.thrower.prevPosX);
         float g1 = (float)(entity.prevPosY - entity.thrower.prevPosY);
         float g2 = (float)(entity.prevPosZ - entity.thrower.prevPosZ);
         double prevdistsq = f * f + g1 * g1 + g2 * g2;
         double dist = MathHelper.sqrt(prevdistsq + (dist1sq - prevdistsq) * partialTicks);
         float mx = (float)(entity.posX - entity.thrower.posX);
         float mz = (float)(entity.posZ - entity.thrower.posZ);
         float my = (float)(entity.thrower.posY + entity.thrower.height / 2.0F - entity.posY);
         float moti_zx = (float)Math.sqrt(mx * mx + mz * mz);
         float moti_zy = (float)Math.sqrt(my * my + mz * mz);
         float cosangle_Yaw = mz / moti_zx;
         float cosangle_Pitch = mz / moti_zy;
         float angle_Yaw = (float)Math.toDegrees(Math.acos(cosangle_Yaw));
         float angle_Pitch = (float)Math.toDegrees(Math.acos(cosangle_Pitch));
         float tanangle = my / moti_zx;
         float angle = (float)Math.toDegrees(Math.atan(tanangle));
         if (mx > 0.0F) {
            angle_Yaw = -angle_Yaw;
         }

         if (my < 0.0F) {
            angle_Pitch = -angle_Pitch;
         }

         float rotYaw = angle_Yaw + 180.0F;
         float rotPitch = -angle;
         float mx2 = (float)(entity.prevPosX - entity.thrower.prevPosX);
         float mz2 = (float)(entity.prevPosZ - entity.thrower.prevPosZ);
         float my2 = (float)(entity.thrower.prevPosY + entity.thrower.height / 2.0F - entity.prevPosY);
         float moti_zx2 = (float)Math.sqrt(mx2 * mx2 + mz2 * mz2);
         float moti_zy2 = (float)Math.sqrt(my2 * my2 + mz2 * mz2);
         float cosangle_Yaw2 = mz2 / moti_zx2;
         float cosangle_Pitch2 = mz2 / moti_zy2;
         float angle_Yaw2 = (float)Math.toDegrees(Math.acos(cosangle_Yaw2));
         float angle_Pitch2 = (float)Math.toDegrees(Math.acos(cosangle_Pitch2));
         float tanangle2 = my2 / moti_zx2;
         float angle2 = (float)Math.toDegrees(Math.atan(tanangle2));
         if (mx2 > 0.0F) {
            angle_Yaw2 = -angle_Yaw2;
         }

         if (my2 < 0.0F) {
            angle_Pitch2 = -angle_Pitch2;
         }

         float prevYaw = angle_Yaw2 + 180.0F;
         float prevPitch = -angle2;
         float scale = 0.05F;
         float lengh = 8.0F;
         float animation = 0.3F;
         float Tickpl = 0.0F;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + 0.25, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(-(prevYaw + (rotYaw - prevYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(prevPitch + (rotPitch - prevPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
         this.bindTexture(tex);
         GL11.glDisable(2884);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-scale, 0.0, 0.0).tex(0.0, 1.0 + dist / lengh + Tickpl * animation).endVertex();
         bufferbuilder.pos(-scale, 0.0, dist).tex(0.0, 0.0F + Tickpl * animation).endVertex();
         bufferbuilder.pos(scale, 0.0, dist).tex(1.0, 0.0F + Tickpl * animation).endVertex();
         bufferbuilder.pos(scale, 0.0, 0.0).tex(1.0, 1.0 + dist / lengh + Tickpl * animation).endVertex();
         bufferbuilder.pos(0.0, -scale + 0.0F, 0.0).tex(0.0, 1.0 + dist / lengh + Tickpl * animation).endVertex();
         bufferbuilder.pos(0.0, -scale, dist).tex(0.0, 0.0F + Tickpl * animation).endVertex();
         bufferbuilder.pos(0.0, scale, dist).tex(1.0, 0.0F + Tickpl * animation).endVertex();
         bufferbuilder.pos(0.0, scale + 0.0F, 0.0).tex(1.0, 1.0 + dist / lengh + Tickpl * animation).endVertex();
         tessellator.draw();
         GlStateManager.disableRescaleNormal();
         GL11.glEnable(2884);
         GlStateManager.popMatrix();
      }

      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(EntitySeaEffloresce entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }

   public static class SeaEffloresceFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new RenderSeaEffloresce(manager);
      }
   }
}
