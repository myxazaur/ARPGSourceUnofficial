//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class MyRenderHelper {
   public static void renderLaserSpiral(
      float spirallength,
      int spiralsegments,
      float spiralRadius,
      float lineThickness,
      float edgeWidth,
      float animationSpeed,
      float[] color1,
      float[] color2,
      float[] color3
   ) {
      float R3 = color3[0];
      float G3 = color3[1];
      float B3 = color3[2];
      float A3 = color3[3];
      float edgeWidth2 = edgeWidth / 2.0F;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      float depthAdd = spirallength / spiralsegments;
      float anim = AnimationTimer.tick * animationSpeed;
      double a = Math.atan(Math.abs(edgeWidth2) / spiralRadius);
      float angle = (float)(a * 2.0) * (float) (-180.0 / Math.PI);
      if (edgeWidth < 0.0F) {
         angle = -angle;
      }

      for (int i = 0; i < spiralsegments; i++) {
         float firstCol = i == 0 ? 0.0F : 1.0F;
         GlStateManager.pushMatrix();
         GlStateManager.rotate(angle * i - anim, 0.0F, 0.0F, 1.0F);
         float progr = (float)i / spiralsegments;
         float progr2 = (float)(i + 1) / spiralsegments;
         float R1 = GetMOP.partial(color2[0], color1[0], progr) * firstCol;
         float G1 = GetMOP.partial(color2[1], color1[1], progr) * firstCol;
         float B1 = GetMOP.partial(color2[2], color1[2], progr) * firstCol;
         float A1 = GetMOP.partial(color2[3], color1[3], progr) * firstCol;
         float R2 = GetMOP.partial(color2[0], color1[0], progr2);
         float G2 = GetMOP.partial(color2[1], color1[1], progr2);
         float B2 = GetMOP.partial(color2[2], color1[2], progr2);
         float A2 = GetMOP.partial(color2[3], color1[3], progr2);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         bufferbuilder.pos(edgeWidth2, -spiralRadius, 0.0F - depthAdd * i).color(R1, G1, B1, A1).endVertex();
         bufferbuilder.pos(edgeWidth2, -spiralRadius, -lineThickness - depthAdd * i)
            .color(R3 * firstCol, G3 * firstCol, B3 * firstCol, A3 * firstCol)
            .endVertex();
         bufferbuilder.pos(-edgeWidth2, -spiralRadius, -lineThickness - depthAdd - depthAdd * i).color(R3, G3, B3, A3).endVertex();
         bufferbuilder.pos(-edgeWidth2, -spiralRadius, 0.0F - depthAdd - depthAdd * i).color(R2, G2, B2, A2).endVertex();
         tessellator.draw();
         GlStateManager.popMatrix();
      }
   }

   public static void renderLaserLinear(
      float length,
      int beamsCount,
      float angle,
      float scale,
      float zoffset,
      float textureYOffset,
      float textureRepeats,
      int frame,
      int frameCount,
      float[] color1,
      float[] color2
   ) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      if (beamsCount == 1) {
         GlStateManager.rotate(angle, 0.0F, 0.0F, 1.0F);
         drawVerticalColoredLaser(bufferbuilder, scale, length, tessellator, 0.0F, zoffset, textureYOffset, textureRepeats, frame, frameCount, color1, color2);
      } else if (beamsCount > 1) {
         GlStateManager.rotate(angle, 0.0F, 0.0F, 1.0F);
         float a = 180.0F / beamsCount;

         for (int i = 0; i < beamsCount; i++) {
            GlStateManager.rotate(a, 0.0F, 0.0F, 1.0F);
            drawVerticalColoredLaser(
               bufferbuilder, scale, length, tessellator, 0.0F, zoffset, textureYOffset, textureRepeats, frame, frameCount, color1, color2
            );
         }
      }
   }

   public static void renderLaserFacedToCamera(
      Vec3d laserWorldPosition,
      Vec3d laserLook,
      float length,
      float scale,
      float xoffset,
      float zoffset,
      float textureYOffset,
      float textureRepeats,
      int frame,
      int frameCount,
      float[] color1,
      float[] color2,
      float partialTicks
   ) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      GlStateManager.rotate(
         calculateBeamFaceToCameraAngle(
            laserWorldPosition.x,
            laserWorldPosition.y,
            laserWorldPosition.z,
            laserLook.x,
            laserLook.y,
            laserLook.z,
            partialTicks
         ),
         0.0F,
         0.0F,
         1.0F
      );
      drawVerticalColoredLaser(bufferbuilder, scale, length, tessellator, xoffset, zoffset, textureYOffset, textureRepeats, frame, frameCount, color1, color2);
   }

   public static void drawVerticalLaser(
      BufferBuilder bufferbuilder,
      float finalscale,
      float finallength,
      Tessellator tessellator,
      float xoffset,
      float zoffset,
      float textureYOffset,
      float textureRepeats,
      int frame,
      int frameCount
   ) {
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      float oneFrameLen = 1.0F / frameCount;
      float nextframe = frame + 1;
      bufferbuilder.pos(xoffset, finalscale, zoffset).tex(oneFrameLen * nextframe, 0.0F + textureYOffset).endVertex();
      bufferbuilder.pos(-xoffset, finalscale, zoffset - finallength)
         .tex(oneFrameLen * nextframe, textureRepeats + textureYOffset)
         .endVertex();
      bufferbuilder.pos(-xoffset, -finalscale, zoffset - finallength)
         .tex(oneFrameLen * frame, textureRepeats + textureYOffset)
         .endVertex();
      bufferbuilder.pos(xoffset, -finalscale, zoffset).tex(oneFrameLen * frame, 0.0F + textureYOffset).endVertex();
      tessellator.draw();
   }

   public static void drawVerticalColoredLaser(
      BufferBuilder bufferbuilder,
      float finalscale,
      float finallength,
      Tessellator tessellator,
      float xoffset,
      float zoffset,
      float textureYOffset,
      float textureRepeats,
      int frame,
      int frameCount,
      float[] color1,
      float[] color2
   ) {
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      float oneFrameLen = 1.0F / frameCount;
      float nextframe = frame + 1;
      bufferbuilder.pos(xoffset, finalscale, zoffset)
         .tex(oneFrameLen * nextframe, 0.0F + textureYOffset)
         .color(color1[0], color1[1], color1[2], color1[3])
         .endVertex();
      bufferbuilder.pos(-xoffset, finalscale, zoffset - finallength)
         .tex(oneFrameLen * nextframe, textureRepeats + textureYOffset)
         .color(color2[0], color2[1], color2[2], color2[3])
         .endVertex();
      bufferbuilder.pos(-xoffset, -finalscale, zoffset - finallength)
         .tex(oneFrameLen * frame, textureRepeats + textureYOffset)
         .color(color2[0], color2[1], color2[2], color2[3])
         .endVertex();
      bufferbuilder.pos(xoffset, -finalscale, zoffset)
         .tex(oneFrameLen * frame, 0.0F + textureYOffset)
         .color(color1[0], color1[1], color1[2], color1[3])
         .endVertex();
      tessellator.draw();
   }

   public static void drawVerticalLaser(BufferBuilder bufferbuilder, float finalscale, float finallength, Tessellator tessellator, float xoffset, float zoffset) {
      drawVerticalLaser(bufferbuilder, finalscale, finallength, tessellator, xoffset, zoffset, 0.0F, 1.0F, 0, 1);
   }

   public static float calculateBeamFaceToCameraAngle(
      double posX, double posY, double posZ, double beamLookX, double beamLookY, double beamLookZ, float partialTicks
   ) {
      Vec3d cameraPos = getGameCameraPosition(partialTicks);
      Vec3d posOnLine = GetMOP.getNearestPointInLineToPoint(new Vec3d(posX, posY, posZ), new Vec3d(beamLookX, beamLookY, beamLookZ), cameraPos);
      Vec3d directionVec = new Vec3d(beamLookX, beamLookY, beamLookZ);
      Vec3d differenceVec = new Vec3d(
         posOnLine.x - cameraPos.x,
         posOnLine.y - cameraPos.y,
         posOnLine.z - cameraPos.z
      );
      Vec3d perp = directionVec.crossProduct(differenceVec);
      Vec3d pw = GetMOP.Vec3dToPitchYaw(directionVec);
      Vec3d normal = GetMOP.PitchYawToVec3d((float)pw.x + 90.0F, (float)pw.y);
      double angle = GetMOP.getAngleBetweenVectors(perp, normal, perp.length(), 1.0);
      float mult = cameraPos.y > posOnLine.y ? -1.0F : 1.0F;
      return (float)angle * mult;
   }

   public static Vec3d getEntityPartialPosition(Entity entity, float partialTicks) {
      double cX = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
      double cY = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks;
      double cZ = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
      return new Vec3d(cX, cY, cZ);
   }

   public static Vec3d getGameCameraPosition(float partialTicks) {
      Entity rventity = Minecraft.getMinecraft().getRenderViewEntity();
      float eh = rventity.getEyeHeight();
      double cX = rventity.prevPosX + (rventity.posX - rventity.prevPosX) * partialTicks;
      double cY = rventity.prevPosY + (rventity.posY - rventity.prevPosY) * partialTicks + eh;
      double cZ = rventity.prevPosZ + (rventity.posZ - rventity.prevPosZ) * partialTicks;
      return new Vec3d(cX, cY, cZ);
   }

   public static Vec3d getRenderViewEntityPosition(float partialTicks) {
      Entity rventity = Minecraft.getMinecraft().getRenderViewEntity();
      double cX = rventity.prevPosX + (rventity.posX - rventity.prevPosX) * partialTicks;
      double cY = rventity.prevPosY + (rventity.posY - rventity.prevPosY) * partialTicks;
      double cZ = rventity.prevPosZ + (rventity.posZ - rventity.prevPosZ) * partialTicks;
      return new Vec3d(cX, cY, cZ);
   }

   public static void renderLaserCircles(
      double dist, int animspeed, int circlesCount, float circleRadiusStart, float circleRadiusEnd, float[] color1, float[] color2
   ) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      int max = circlesCount;
      float animatinOffset = (float)(AnimationTimer.tick % animspeed) / animspeed;

      for (int i = circlesCount; i > 0; i--) {
         float col2 = (i + animatinOffset) / max;
         float col1 = 1.0F - col2;
         double unit = dist / max;
         double depth = unit * i + unit * animatinOffset;
         double circleRadius = circleRadiusStart * col1 + circleRadiusEnd * col2;
         double xoffset = 0.0;
         double yoffset = 0.0;
         if (depth < dist) {
            float r = color1[0] * col1 + color2[0] * col2;
            float g = color1[1] * col1 + color2[1] * col2;
            float b = color1[2] * col1 + color2[2] * col2;
            float a = color1[3] * col1 + color2[3] * col2;
            GlStateManager.color(r, g, b, a);
            bufferbuilder.pos(-circleRadius - xoffset, -circleRadius + yoffset, -depth).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(-circleRadius - xoffset, circleRadius + yoffset, -depth).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(circleRadius - xoffset, circleRadius + yoffset, -depth).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(circleRadius - xoffset, -circleRadius + yoffset, -depth).tex(1.0, 1.0).endVertex();
         }
      }

      tessellator.draw();
   }
}
