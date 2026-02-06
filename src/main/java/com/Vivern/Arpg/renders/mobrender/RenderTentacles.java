package com.Vivern.Arpg.renders.mobrender;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class RenderTentacles {
   public static ResourceLocation testTexture = new ResourceLocation("arpg:textures/crystal_star_model_tex.png");

   public static void testRender(Entity entity, float partialTicks) {
      Vec3d[] segmentsPoses = getTentacleSegmenstPositions(
         entity.getPositionVector().add(0.0, -0.5, 0.0),
         new Vec3d(Debugger.floats[0] - 154.5, Debugger.floats[1] + 71.0F, Debugger.floats[2] + 246.5),
         new Vec3d(0.0, -1.0, 0.0),
         new Vec3d(0.0, 1.0, 0.0),
         10.0F + Debugger.floats[3],
         20 + (int)Debugger.floats[4]
      );
      Minecraft.getMinecraft().getTextureManager().bindTexture(testTexture);
      renderTentacleAnimFulltexture(segmentsPoses, 0.0F, entity.ticksExisted % 40 / 40.0F, partialTicks, 0.5F, 0.25F, 200, 200);
   }

   public static void renderTentacleAnimFulltexture(
      Vec3d[] segmentsPoses, float overlapShare, float progress, float partialTicks, float scaleStart, float scaleEnd, int lightStart, int lightEnd
   ) {
      double cameraX = 0.0;
      double cameraY = 0.0;
      double cameraZ = 0.0;
      Entity rvEntity = Minecraft.getMinecraft().getRenderViewEntity();
      if (rvEntity != null) {
         cameraX = rvEntity.prevPosX + (rvEntity.posX - rvEntity.prevPosX) * partialTicks;
         cameraY = rvEntity.prevPosY + (rvEntity.posY - rvEntity.prevPosY) * partialTicks;
         cameraZ = rvEntity.prevPosZ + (rvEntity.posZ - rvEntity.prevPosZ) * partialTicks;
      }

      GlStateManager.disableCull();
      GlStateManager.depthMask(true);
      GL11.glTexParameteri(3553, 10243, 33069);
      float scalePrev = scaleStart;
      float scaleAdding = (scaleEnd - scaleStart) / segmentsPoses.length;
      float texOneSegmentLength = 1.0F / (segmentsPoses.length - 1);

      for (int i = 0; i < segmentsPoses.length - 1; i++) {
         Vec3d point1 = segmentsPoses[i];
         Vec3d point2 = segmentsPoses[i + 1];
         float mx = (float)(point1.x - point2.x);
         float mz = (float)(point1.z - point2.z);
         float my = (float)(point2.y - point1.y);
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

         float rotatYaw = angle_Yaw + 180.0F;
         float rotatPitch = -angle;
         double distTo = point1.distanceTo(point2);
         float lightRatio = (float)i / segmentsPoses.length;
         AbstractMobModel.light((int)(lightStart * (1.0F - lightRatio) + lightEnd * lightRatio), false);
         float textureYmin = 1.0F + texOneSegmentLength * i - progress * 2.0F;
         float textureYmax = textureYmin + texOneSegmentLength;
         renderSegment(
            distTo,
            distTo * overlapShare,
            scalePrev,
            scalePrev + scaleAdding,
            point1.x - cameraX,
            point1.y - cameraY,
            point1.z - cameraZ,
            rotatPitch,
            rotatYaw,
            textureYmin,
            textureYmax
         );
         AbstractMobModel.returnlight();
         scalePrev += scaleAdding;
      }

      GL11.glTexParameteri(3553, 10243, 10497);
   }

   public static void renderTentacleAnimGlow(
      Vec3d[] segmentsPoses,
      float partialTicks,
      float scaleStart,
      float scaleEnd,
      int lightStart,
      int lightEnd,
      int segmentGlowed,
      float R,
      float G,
      float B,
      int segmentGlowedLight
   ) {
      double cameraX = 0.0;
      double cameraY = 0.0;
      double cameraZ = 0.0;
      Entity rvEntity = Minecraft.getMinecraft().getRenderViewEntity();
      if (rvEntity != null) {
         cameraX = rvEntity.prevPosX + (rvEntity.posX - rvEntity.prevPosX) * partialTicks;
         cameraY = rvEntity.prevPosY + (rvEntity.posY - rvEntity.prevPosY) * partialTicks;
         cameraZ = rvEntity.prevPosZ + (rvEntity.posZ - rvEntity.prevPosZ) * partialTicks;
      }

      GlStateManager.disableCull();
      GlStateManager.depthMask(true);
      float scalePrev = scaleStart;
      float scaleAdding = (scaleEnd - scaleStart) / segmentsPoses.length;

      for (int i = 0; i < segmentsPoses.length - 1; i++) {
         Vec3d point1 = segmentsPoses[i];
         Vec3d point2 = segmentsPoses[i + 1];
         float mx = (float)(point1.x - point2.x);
         float mz = (float)(point1.z - point2.z);
         float my = (float)(point2.y - point1.y);
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

         float rotatYaw = angle_Yaw + 180.0F;
         float rotatPitch = -angle;
         double distTo = point1.distanceTo(point2);
         boolean doUndo = false;
         int addLight = 0;
         if (segmentGlowed == i) {
            GlStateManager.color(R, G, B);
            addLight = segmentGlowedLight;
            doUndo = true;
         } else {
            int abs = Math.abs(segmentGlowed - i);
            if (abs == 1) {
               GlStateManager.color(R * 0.7F + 0.3F, G * 0.7F + 0.3F, B * 0.7F + 0.3F);
               addLight = segmentGlowedLight / 3;
               doUndo = true;
            } else if (abs == 2) {
               GlStateManager.color(R * 0.3F + 0.7F, G * 0.3F + 0.7F, B * 0.3F + 0.7F);
               addLight = segmentGlowedLight / 6;
               doUndo = true;
            }
         }

         float lightRatio = (float)i / segmentsPoses.length;
         AbstractMobModel.light((int)(lightStart * (1.0F - lightRatio) + lightEnd * lightRatio) + addLight, false);
         renderSegment(
            distTo,
            distTo / 14.0,
            scalePrev,
            scalePrev + scaleAdding,
            point1.x - cameraX,
            point1.y - cameraY,
            point1.z - cameraZ,
            rotatPitch,
            rotatYaw
         );
         AbstractMobModel.returnlight();
         if (doUndo) {
            GlStateManager.color(1.0F, 1.0F, 1.0F);
         }

         scalePrev += scaleAdding;
      }
   }

   public static void renderTentacle(Vec3d[] segmentsPoses, float partialTicks, float scaleStart, float scaleEnd, int lightStart, int lightEnd) {
      double cameraX = 0.0;
      double cameraY = 0.0;
      double cameraZ = 0.0;
      Entity rvEntity = Minecraft.getMinecraft().getRenderViewEntity();
      if (rvEntity != null) {
         cameraX = rvEntity.prevPosX + (rvEntity.posX - rvEntity.prevPosX) * partialTicks;
         cameraY = rvEntity.prevPosY + (rvEntity.posY - rvEntity.prevPosY) * partialTicks;
         cameraZ = rvEntity.prevPosZ + (rvEntity.posZ - rvEntity.prevPosZ) * partialTicks;
      }

      GlStateManager.disableCull();
      GlStateManager.depthMask(true);
      float scalePrev = scaleStart;
      float scaleAdding = (scaleEnd - scaleStart) / segmentsPoses.length;

      for (int i = 0; i < segmentsPoses.length - 1; i++) {
         Vec3d point1 = segmentsPoses[i];
         Vec3d point2 = segmentsPoses[i + 1];
         float mx = (float)(point1.x - point2.x);
         float mz = (float)(point1.z - point2.z);
         float my = (float)(point2.y - point1.y);
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

         float rotatYaw = angle_Yaw + 180.0F;
         float rotatPitch = -angle;
         double distTo = point1.distanceTo(point2);
         float lightRatio = (float)i / segmentsPoses.length;
         AbstractMobModel.light((int)(lightStart * (1.0F - lightRatio) + lightEnd * lightRatio), false);
         renderSegment(
            distTo,
            distTo / 14.0,
            scalePrev,
            scalePrev + scaleAdding,
            point1.x - cameraX,
            point1.y - cameraY,
            point1.z - cameraZ,
            rotatPitch,
            rotatYaw
         );
         AbstractMobModel.returnlight();
         scalePrev += scaleAdding;
      }
   }

   public static void renderSegment(double dist, double overlap, double scaleMin, double scaleMax, double x, double y, double z, float pitch, float yaw) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.rotate(-yaw, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(pitch, 1.0F, 0.0F, 0.0F);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-scaleMin, 0.0, -overlap).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(-scaleMax, 0.0, dist + overlap).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(scaleMax, 0.0, dist + overlap).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(scaleMin, 0.0, -overlap).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(0.0, -scaleMin, -overlap).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(0.0, -scaleMax, dist + overlap).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(0.0, scaleMax, dist + overlap).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(0.0, scaleMin, -overlap).tex(1.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.popMatrix();
   }

   public static void renderSegment(
      double dist, double overlap, double scaleMin, double scaleMax, double x, double y, double z, float pitch, float yaw, float textureYmin, float textureYmax
   ) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.rotate(-yaw, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(pitch, 1.0F, 0.0F, 0.0F);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-scaleMin, 0.0, -overlap).tex(0.0, textureYmax).endVertex();
      bufferbuilder.pos(-scaleMax, 0.0, dist + overlap).tex(0.0, textureYmin).endVertex();
      bufferbuilder.pos(scaleMax, 0.0, dist + overlap).tex(1.0, textureYmin).endVertex();
      bufferbuilder.pos(scaleMin, 0.0, -overlap).tex(1.0, textureYmax).endVertex();
      bufferbuilder.pos(0.0, -scaleMin, -overlap).tex(0.0, textureYmax).endVertex();
      bufferbuilder.pos(0.0, -scaleMax, dist + overlap).tex(0.0, textureYmin).endVertex();
      bufferbuilder.pos(0.0, scaleMax, dist + overlap).tex(1.0, textureYmin).endVertex();
      bufferbuilder.pos(0.0, scaleMin, -overlap).tex(1.0, textureYmax).endVertex();
      tessellator.draw();
      GlStateManager.popMatrix();
   }

   public static Vec3d[] getTentacleSegmenstPositions(
      Vec3d pos1, Vec3d pos2, Vec3d normalizedRotation1, Vec3d normalizedRotation2, double rotationVecLength, int posesAmount
   ) {
      Vec3d[] poses = new Vec3d[posesAmount];
      double oneFragm = rotationVecLength / (posesAmount - 1);

      for (int i = 0; i < posesAmount; i++) {
         float ratio = GetMOP.softfromto(i, 0.0F, posesAmount - 1);
         float unratio = 1.0F - ratio;
         double lengthSplitted = oneFragm * i;
         double unlengthSplitted = rotationVecLength - lengthSplitted;
         Vec3d line1 = pos1.add(
            normalizedRotation1.x * lengthSplitted,
            normalizedRotation1.y * lengthSplitted,
            normalizedRotation1.z * lengthSplitted
         );
         Vec3d line2 = pos2.add(
            normalizedRotation2.x * unlengthSplitted,
            normalizedRotation2.y * unlengthSplitted,
            normalizedRotation2.z * unlengthSplitted
         );
         poses[i] = new Vec3d(
            line1.x * unratio + line2.x * ratio,
            line1.y * unratio + line2.y * ratio,
            line1.z * unratio + line2.z * ratio
         );
      }

      return poses;
   }
}
