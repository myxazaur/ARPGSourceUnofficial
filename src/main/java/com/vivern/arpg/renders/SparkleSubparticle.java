package com.vivern.arpg.renders;

import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.weather.Weather;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class SparkleSubparticle {
   public static ArrayList<SparkleSubparticle> particles = new ArrayList<>();
   public static boolean iterating = false;
   public float size;
   public double posX;
   public double posY;
   public double posZ;
   public float moveX;
   public float moveY;
   public float moveZ;
   public float color;
   public int timebound;
   public static Random rand = new Random();

   public SparkleSubparticle(double posX, double posY, double posZ, float size, int livetime, float moveX, float moveY, float moveZ, float color) {
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.size = size;
      this.timebound = AnimationTimer.normaltick + livetime;
      this.moveX = moveX;
      this.moveY = moveY;
      this.moveZ = moveZ;
      this.color = color;
   }

   public static void renderInWorld(RenderWorldLastEvent event) {
      if (!particles.isEmpty()) {
         float lbX = OpenGlHelper.lastBrightnessX;
         float lbY = OpenGlHelper.lastBrightnessY;
         float pt = event.getPartialTicks();
         double cameraX = 0.0;
         double cameraY = 0.0;
         double cameraZ = 0.0;
         Entity rvEntity = Minecraft.getMinecraft().getRenderViewEntity();
         long time = 0L;
         float rotateYaw = 0.0F;
         if (rvEntity != null) {
            cameraX = rvEntity.prevPosX + (rvEntity.posX - rvEntity.prevPosX) * pt;
            cameraY = rvEntity.prevPosY + (rvEntity.posY - rvEntity.prevPosY) * pt;
            cameraZ = rvEntity.prevPosZ + (rvEntity.posZ - rvEntity.prevPosZ) * pt;
            time = rvEntity.world.getWorldTime();
            rotateYaw = rvEntity.rotationYaw;
         }

         GlStateManager.matrixMode(5888);
         GlStateManager.pushMatrix();
         GlStateManager.disableTexture2D();
         GlStateManager.disableBlend();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         int size = particles.size();

         for (int s = 0; s < size; s++) {
            SparkleSubparticle part = particles.get(s);
            if (part != null) {
               double d0 = part.posX - cameraX + part.moveX * pt;
               double d1 = part.posY - cameraY + part.moveY * pt;
               double d2 = part.posZ - cameraZ + part.moveZ * pt;
               double dist = Math.sqrt(d0 * d0 + d2 * d2);
               float colorR = 1.0F - GetMOP.getfromto(part.color, 0.7F, 1.0F);
               float colorG = 1.0F - GetMOP.getfromto(part.color, 0.45F, 0.8F);
               float colorB = 0.95F - GetMOP.getfromto(part.color, 0.0F, 0.55F) * 0.95F;
               float unit = part.size;
               float unit1X = (float)(-(d2 / dist)) * part.size;
               float unit1Z = (float)(d0 / dist) * part.size;
               float unit2X = -unit1X;
               float unit2Z = -unit1Z;
               bufferbuilder.pos(d0 + unit1X, d1 - unit, d2 + unit1Z).color(colorR, colorG, colorB, 1.0F).endVertex();
               bufferbuilder.pos(d0 + unit1X, d1 + unit, d2 + unit1Z).color(colorR, colorG, colorB, 1.0F).endVertex();
               bufferbuilder.pos(d0 + unit2X, d1 + unit, d2 + unit2Z).color(colorR, colorG, colorB, 1.0F).endVertex();
               bufferbuilder.pos(d0 + unit2X, d1 - unit, d2 + unit2Z).color(colorR, colorG, colorB, 1.0F).endVertex();
               if (AnimationTimer.normaltick <= part.timebound) {
                  part.posX = part.posX + part.moveX;
                  part.posY = part.posY + part.moveY;
                  part.posZ = part.posZ + part.moveZ;
                  part.moveX = part.moveX + ((rand.nextFloat() - 0.5F) * 0.003F + Weather.getWindX(part.posX, part.posZ) * 0.012F);
                  part.moveY = part.moveY + ((rand.nextFloat() - 0.4F) * 0.003F + Weather.getWindY(part.posX, part.posZ) * 0.012F);
                  part.moveZ = part.moveZ + ((rand.nextFloat() - 0.5F) * 0.003F + Weather.getWindZ(part.posX, part.posZ) * 0.012F);
                  part.color += 0.008F;
                  part.size = (float)(part.size * 0.999);
               }
            }
         }

         tessellator.draw();
         GlStateManager.popMatrix();
         GlStateManager.enableTexture2D();
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
         particles.removeIf(particle -> particle == null || AnimationTimer.normaltick > particle.timebound);
      }
   }
}
