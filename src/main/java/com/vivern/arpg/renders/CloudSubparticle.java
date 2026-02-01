package com.vivern.arpg.renders;

import com.vivern.arpg.elements.models.ModelsCloud;
import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.weather.Weather;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class CloudSubparticle {
   public static ArrayList<CloudSubparticle> particles = new ArrayList<>();
   public static float windX = 0.0F;
   public static float windY = 0.0F;
   public static float windZ = 0.0F;
   public static ResourceLocation standartTex = new ResourceLocation("arpg:textures/clouds4.png");
   public int model;
   public float sizeX;
   public float sizeY;
   public float sizeZ;
   public double posX;
   public double posY;
   public double posZ;
   public double lastTickPosX;
   public double lastTickPosY;
   public double lastTickPosZ;
   public ResourceLocation texture;
   public int timebound;
   public float alpha = 0.0F;
   public float alphaIncreaseTime;
   public float alphaDecreaseTime;
   public float alphaAdding;
   public float rotateAngle = 90.0F;
   public float rotateX = 0.0F;
   public float rotateY = 0.0F;
   public float rotateZ = 0.0F;

   public CloudSubparticle(
      ResourceLocation texture, int model, double posX, double posY, double posZ, float sizeX, float sizeY, float sizeZ, int livetime, int alphaChangeTime
   ) {
      this.texture = texture;
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.sizeX = sizeX;
      this.sizeY = sizeY;
      this.sizeZ = sizeZ;
      this.timebound = AnimationTimer.normaltick + livetime;
      this.model = Math.min(model, ModelsCloud.modelsCount);
      this.alphaIncreaseTime = AnimationTimer.normaltick + alphaChangeTime;
      this.alphaDecreaseTime = this.timebound - alphaChangeTime;
      this.alphaAdding = 1.0F / alphaChangeTime;
   }

   public static void renderInWorld(RenderWorldLastEvent event) {
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      float pt = event.getPartialTicks();
      double cameraX = 0.0;
      double cameraY = 0.0;
      double cameraZ = 0.0;
      Entity rvEntity = Minecraft.getMinecraft().getRenderViewEntity();
      long time = 0L;
      if (rvEntity != null) {
         cameraX = rvEntity.prevPosX + (rvEntity.posX - rvEntity.prevPosX) * pt;
         cameraY = rvEntity.prevPosY + (rvEntity.posY - rvEntity.prevPosY) * pt;
         cameraZ = rvEntity.prevPosZ + (rvEntity.posZ - rvEntity.prevPosZ) * pt;
         time = rvEntity.world.getWorldTime();
      }

      float br = Weather.getBrightnessFromTime(time);
      Iterator<CloudSubparticle> iterator = particles.iterator();

      while (iterator.hasNext()) {
         CloudSubparticle part = iterator.next();
         if (AnimationTimer.normaltick < part.alphaIncreaseTime) {
            part.alpha = part.alpha + part.alphaAdding;
         } else if (AnimationTimer.normaltick > part.alphaDecreaseTime) {
            part.alpha = part.alpha - part.alphaAdding;
         }

         double d0 = part.posX - cameraX;
         double d1 = part.posY - cameraY;
         double d2 = part.posZ - cameraZ;
         GlStateManager.matrixMode(5888);
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)d0, (float)d1, (float)d2);
         Minecraft.getMinecraft().renderEngine.bindTexture(part.texture);
         GlStateManager.rotate(part.rotateAngle, part.rotateX, part.rotateY, part.rotateZ);
         GlStateManager.scale(part.sizeX, part.sizeY, part.sizeZ);
         GlStateManager.disableCull();
         GlStateManager.enableBlend();
         GlStateManager.color(br + 0.13F, br + 0.14F, br + 0.2F, part.alpha);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         ModelsCloud.cloudsBySize[part.model].render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.enableCull();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
         if (AnimationTimer.normaltick > part.timebound) {
            iterator.remove();
         } else {
            part.posX = part.posX + windX;
            part.posY = part.posY + windY;
            part.posZ = part.posZ + windZ;
         }
      }

      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
   }
}
