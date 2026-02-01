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
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class MagicSubparticle {
   public static ArrayList<MagicSubparticle> particles = new ArrayList<>();
   public static boolean iterating = false;
   public double posX;
   public double posY;
   public double posZ;
   public float moveX;
   public float moveY;
   public float moveZ;
   public byte[] color;
   public byte[] targetcolor;
   public int timebound;
   public int whencreate;
   public static Random rand = new Random();

   public MagicSubparticle(
      double posX,
      double posY,
      double posZ,
      int livetime,
      float moveX,
      float moveY,
      float moveZ,
      float colorR,
      float colorG,
      float colorB,
      float targetcolorR,
      float targetcolorG,
      float targetcolorB
   ) {
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.timebound = AnimationTimer.normaltick + livetime;
      this.whencreate = AnimationTimer.normaltick;
      this.moveX = moveX;
      this.moveY = moveY;
      this.moveZ = moveZ;
      this.color = new byte[]{(byte)(colorR * 255.0F), (byte)(colorG * 255.0F), (byte)(colorB * 255.0F)};
      this.targetcolor = new byte[]{(byte)(targetcolorR * 255.0F), (byte)(targetcolorG * 255.0F), (byte)(targetcolorB * 255.0F)};
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
         GlStateManager.enableBlend();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         int size = particles.size();
         boolean[] removes = new boolean[size];

         for (int s = 0; s < size; s++) {
            MagicSubparticle part = particles.get(s);
            double d0 = part.posX - cameraX + part.moveX * pt;
            double d1 = part.posY - cameraY + part.moveY * pt;
            double d2 = part.posZ - cameraZ + part.moveZ * pt;
            double dist = Math.sqrt(d0 * d0 + d2 * d2);
            float colorOriginal = GetMOP.getfromto((float)AnimationTimer.normaltick, (float)part.whencreate, (float)part.timebound);
            float colorTarget = 1.0F - colorOriginal;
            int colorR = (int)MathHelper.clamp(part.color[0] * colorOriginal + part.targetcolor[0] * colorTarget, 0.0F, 255.0F);
            int colorG = (int)MathHelper.clamp(part.color[1] * colorOriginal + part.targetcolor[1] * colorTarget, 0.0F, 255.0F);
            int colorB = (int)MathHelper.clamp(part.color[2] * colorOriginal + part.targetcolor[2] * colorTarget, 0.0F, 255.0F);
            float unit = 0.03125F;
            float unit1X = (float)(-(d2 / dist)) * unit;
            float unit1Z = (float)(d0 / dist) * unit;
            float unit2X = -unit1X;
            float unit2Z = -unit1Z;
            bufferbuilder.pos(d0 + unit1X, d1 - unit, d2 + unit1Z).color(colorR, colorG, colorB, 255).endVertex();
            bufferbuilder.pos(d0 + unit1X, d1 + unit, d2 + unit1Z).color(colorR, colorG, colorB, 255).endVertex();
            bufferbuilder.pos(d0 + unit2X, d1 + unit, d2 + unit2Z).color(colorR, colorG, colorB, 255).endVertex();
            bufferbuilder.pos(d0 + unit2X, d1 - unit, d2 + unit2Z).color(colorR, colorG, colorB, 255).endVertex();
            if (AnimationTimer.normaltick > part.timebound) {
               removes[s] = true;
            } else {
               part.posX = part.posX + part.moveX;
               part.posY = part.posY + part.moveY;
               part.posZ = part.posZ + part.moveZ;
               part.moveX = part.moveX + ((rand.nextFloat() - 0.5F) * 0.003F + Weather.getWindX(part.posX, part.posZ) * 0.006F);
               part.moveY = part.moveY + ((rand.nextFloat() - 0.4F) * 0.003F + Weather.getWindY(part.posX, part.posZ) * 0.006F);
               part.moveZ = part.moveZ + ((rand.nextFloat() - 0.5F) * 0.003F + Weather.getWindZ(part.posX, part.posZ) * 0.006F);
            }
         }

         try {
            int size1 = particles.size();

            for (int sx = 0; sx < size1; sx++) {
               if (sx <= size && removes[sx]) {
                  particles.remove(sx);
               }
            }
         } catch (IndexOutOfBoundsException var38) {
         }

         tessellator.draw();
         GlStateManager.popMatrix();
         GlStateManager.enableTexture2D();
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.disableBlend();
      }
   }
}
