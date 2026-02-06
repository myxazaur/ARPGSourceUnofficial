package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.renders.MyRenderHelper;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public abstract class LaserModel {
   public LaserModel next;
   public SourceFactor sourceFactor = SourceFactor.SRC_ALPHA;
   public DestFactor destFactor = DestFactor.ONE;
   @Nullable
   public ResourceLocation texture;
   public int lightAdd;

   public LaserModel(ResourceLocation texture, int lightAdd) {
      this.texture = texture;
      this.lightAdd = lightAdd;
   }

   public LaserModel setNext(LaserModel next) {
      this.next = next;
      return this;
   }

   public LaserModel setBlendFunc(SourceFactor sourceFactor, DestFactor destFactor) {
      this.sourceFactor = sourceFactor;
      this.destFactor = destFactor;
      return this;
   }

   public void bindTexture(ResourceLocation location) {
      Minecraft.getMinecraft().renderEngine.bindTexture(location);
   }

   public void renderLaserModel(
      Entity entity, Vec3d laserPos, float laserPitch, float laserYaw, double distance, float horizoffset, double x, double y, double z, float partialTicks
   ) {
      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.rotate(laserYaw, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(laserPitch, 1.0F, 0.0F, 0.0F);
      if (this.texture != null) {
         this.bindTexture(this.texture);
      } else {
         GlStateManager.disableTexture2D();
         GlStateManager.shadeModel(7425);
      }

      GL11.glEnable(3042);
      GlStateManager.disableCull();
      GlStateManager.disableLighting();
      AbstractMobModel.light(this.lightAdd, true);
      GlStateManager.blendFunc(this.sourceFactor, this.destFactor);
      this.renderLaser(entity, laserPos, laserPitch, laserYaw, distance, x, y, z, partialTicks);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GL11.glDisable(3042);
      GlStateManager.enableCull();
      GlStateManager.enableLighting();
      GlStateManager.disableRescaleNormal();
      if (this.texture == null) {
         GlStateManager.shadeModel(7424);
         GlStateManager.enableTexture2D();
      }

      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.next != null) {
         this.next.renderLaserModel(entity, laserPos, laserPitch, laserYaw, distance, horizoffset, x, y, z, partialTicks);
      }
   }

   public abstract void renderLaser(Entity var1, Vec3d var2, float var3, float var4, double var5, double var7, double var9, double var11, float var13);

   public void renderLaserModelInWorld(Entity entity, Vec3d start, Vec3d end, float partialTicks) {
      float rp = GetMOP.Vec3dToPitchYawFixed(end.subtract(start)).x;
      float rw = GetMOP.Vec3dToPitchYawFixed(end.subtract(start)).y;
      rp = -rp;
      rw = -rw + 180.0F;
      Vec3d rvePos = MyRenderHelper.getRenderViewEntityPosition(partialTicks);
      this.renderLaserModel(
         entity,
         start,
         rp,
         rw,
         start.distanceTo(end),
         0.0F,
         start.x - rvePos.x,
         start.y - rvePos.y,
         start.z - rvePos.z,
         partialTicks
      );
   }

   public static class LaserModelCap extends LaserModel {
      public float scale;
      public float positionOnLaser;
      public float[] color1;
      public float angle;
      public float angleAnimation;
      public int frameAnimationDelay = 1;
      public int framesCount = 1;

      public LaserModelCap(ResourceLocation texture, int lightAdd, float scale, float positionOnLaser, float[] color1, float angle, float angleAnimation) {
         super(texture, lightAdd);
         this.scale = scale;
         this.positionOnLaser = positionOnLaser;
         this.color1 = color1;
         this.angle = angle;
         this.angleAnimation = angleAnimation;
      }

      @Override
      public void renderLaser(
         Entity entity, Vec3d laserPos, float laserPitch, float laserYaw, double distance, double x, double y, double z, float partialTicks
      ) {
         int frame = AnimationTimer.tick / this.frameAnimationDelay % this.framesCount;
         float oneFrameLen = 1.0F / this.framesCount;
         float nextframe = frame + 1;
         GlStateManager.color(this.color1[0], this.color1[1], this.color1[2], this.color1[3]);
         GlStateManager.pushMatrix();
         GlStateManager.rotate(this.angle + this.angleAnimation * AnimationTimer.tick, 0.0F, 0.0F, 1.0F);
         float depth = (float)(distance * this.positionOnLaser);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-this.scale, -this.scale, -depth).tex(0.0, oneFrameLen * nextframe).endVertex();
         bufferbuilder.pos(-this.scale, this.scale, -depth).tex(0.0, oneFrameLen * frame).endVertex();
         bufferbuilder.pos(this.scale, this.scale, -depth).tex(1.0, oneFrameLen * frame).endVertex();
         bufferbuilder.pos(this.scale, -this.scale, -depth).tex(1.0, oneFrameLen * nextframe).endVertex();
         tessellator.draw();
         GlStateManager.popMatrix();
      }
   }

   public static class LaserModelCircles extends LaserModel {
      public int animspeed = 10;
      public int circlesCount = 10;
      public float circleRadiusStart = 1.0F;
      public float circleRadiusEnd = 0.1F;
      public float[] color1;
      public float[] color2;
      public double maximumDistance = 0.0;

      public LaserModelCircles(
         ResourceLocation texture,
         int lightAdd,
         double maximumDistance,
         int animspeed,
         int circlesCount,
         float circleRadiusStart,
         float circleRadiusEnd,
         float[] color1,
         @Nullable float[] color2
      ) {
         super(texture, lightAdd);
         this.animspeed = animspeed;
         this.circlesCount = circlesCount;
         this.circleRadiusStart = circleRadiusStart;
         this.circleRadiusEnd = circleRadiusEnd;
         this.color1 = color1;
         this.color2 = color2 == null ? color1 : color2;
         this.maximumDistance = maximumDistance;
      }

      @Override
      public void renderLaser(
         Entity entity, Vec3d laserPos, float laserPitch, float laserYaw, double distance, double x, double y, double z, float partialTicks
      ) {
         MyRenderHelper.renderLaserCircles(
            Math.min(distance, this.maximumDistance), this.animspeed, this.circlesCount, this.circleRadiusStart, this.circleRadiusEnd, this.color1, this.color2
         );
      }
   }

   public static class LaserModelLightnings extends LaserModel {
      public float width;
      public float lightningScale;
      public float[] color1;
      public float[] color2;
      public int segmentsCount = 10;
      public int changeRandomDelay = 1;
      public boolean randomizeAngle = true;
      public float bias;

      public LaserModelLightnings(
         ResourceLocation texture,
         int lightAdd,
         int segmentsCount,
         int changeRandomDelay,
         float width,
         float lightningScale,
         float bias,
         boolean randomizeAngle,
         float[] color1,
         float[] color2
      ) {
         super(texture, lightAdd);
         this.width = width;
         this.lightningScale = lightningScale;
         this.bias = bias;
         this.color1 = color1;
         this.color2 = color2 == null ? color1 : color2;
         this.segmentsCount = segmentsCount;
         this.changeRandomDelay = changeRandomDelay;
         this.randomizeAngle = randomizeAngle;
      }

      @Override
      public void renderLaser(
         Entity entity, Vec3d laserPos, float laserPitch, float laserYaw, double distance, double x, double y, double z, float partialTicks
      ) {
         Random rand = new Random(AnimationTimer.tick / this.changeRandomDelay);
         GlStateManager.pushMatrix();
         if (this.randomizeAngle) {
            GlStateManager.rotate(rand.nextInt(360), 0.0F, 0.0F, 1.0F);
         }

         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         float prevdisplacement = 0.0F;
         float prevdepth = (float)(-distance);
         float prevR = this.color2[0];
         float prevG = this.color2[1];
         float prevB = this.color2[2];
         float prevA = this.color2[3];

         for (int i = 1; i <= this.segmentsCount; i++) {
            float displacement = i == this.segmentsCount ? 0.0F : (rand.nextFloat() - 0.5F) * this.width;
            float number = (float)i / this.segmentsCount;
            float unbias = 1.0F - this.bias;
            double positionOnLaser = (-Math.sqrt(1.0F - (number - 1.0F) * (number - 1.0F)) + 1.0) * this.bias + Math.sqrt(1.0F - number * number) * unbias;
            float posOnLaserF = MathHelper.clamp((float)positionOnLaser, 0.0F, 1.0F);
            float depth = (float)(-distance * posOnLaserF);
            float R = GetMOP.partial(this.color2[0], this.color1[0], posOnLaserF);
            float G = GetMOP.partial(this.color2[1], this.color1[1], posOnLaserF);
            float B = GetMOP.partial(this.color2[2], this.color1[2], posOnLaserF);
            float A = GetMOP.partial(this.color2[3], this.color1[3], posOnLaserF);
            bufferbuilder.pos(0.0, this.lightningScale + prevdisplacement, prevdepth)
               .tex(0.0, 0.0)
               .color(prevR, prevG, prevB, prevA)
               .endVertex();
            bufferbuilder.pos(0.0, this.lightningScale + displacement, depth).tex(0.0, 1.0).color(R, G, B, A).endVertex();
            bufferbuilder.pos(0.0, -this.lightningScale + displacement, depth).tex(1.0, 1.0).color(R, G, B, A).endVertex();
            bufferbuilder.pos(0.0, -this.lightningScale + prevdisplacement, prevdepth)
               .tex(1.0, 0.0)
               .color(prevR, prevG, prevB, prevA)
               .endVertex();
            prevdisplacement = displacement;
            prevdepth = depth;
            prevR = R;
            prevG = G;
            prevB = B;
            prevA = A;
         }

         tessellator.draw();
         GlStateManager.popMatrix();
      }
   }

   public static class LaserModelLinear extends LaserModel {
      public int beamsCount;
      public float scale;
      public float zoffset;
      public float[] color1;
      public float[] color2;
      public float angle;
      public float angleAnimation;
      public float textureLength;
      public float textureAnimation;
      public int frameAnimationDelay = 1;
      public int framesCount = 1;

      public LaserModelLinear(
         ResourceLocation texture,
         int lightAdd,
         int beamsCount,
         float scale,
         float zoffset,
         float[] color1,
         @Nullable float[] color2,
         float angle,
         float angleAnimation,
         float textureLength,
         float textureAnimation
      ) {
         super(texture, lightAdd);
         this.beamsCount = beamsCount;
         this.scale = scale;
         this.zoffset = zoffset;
         this.color1 = color1;
         this.color2 = color2 == null ? color1 : color2;
         this.angle = angle;
         this.angleAnimation = angleAnimation;
         this.textureLength = textureLength;
         this.textureAnimation = textureAnimation;
      }

      @Override
      public void renderLaser(
         Entity entity, Vec3d laserPos, float laserPitch, float laserYaw, double distance, double x, double y, double z, float partialTicks
      ) {
         float repeats = (float)(distance / this.textureLength);
         int frame = AnimationTimer.tick / this.frameAnimationDelay % this.framesCount;
         if (this.beamsCount > 0) {
            MyRenderHelper.renderLaserLinear(
               (float)distance,
               this.beamsCount,
               this.angle + this.angleAnimation * AnimationTimer.tick,
               this.scale,
               this.zoffset,
               AnimationTimer.tick * this.textureAnimation,
               repeats,
               frame,
               this.framesCount,
               this.color1,
               this.color2
            );
         } else {
            MyRenderHelper.renderLaserFacedToCamera(
               laserPos,
               GetMOP.PitchYawToVec3d(laserPitch, laserYaw),
               (float)distance,
               this.scale,
               0.0F,
               this.zoffset,
               AnimationTimer.tick * this.textureAnimation,
               repeats,
               frame,
               this.framesCount,
               this.color1,
               this.color2,
               partialTicks
            );
         }
      }
   }

   public static class LaserModelSpiral extends LaserModel {
      public int animspeed = 10;
      public int spiralsegments = 10;
      public float spiralRadius = 1.0F;
      public float lineThickness = 0.1F;
      public float edgeWidth = 0.1F;
      public float[] color1;
      public float[] color2;
      public float[] color3;
      public double maximumDistance = 0.0;

      public LaserModelSpiral(
         ResourceLocation texture,
         int lightAdd,
         double maximumDistance,
         int animspeed,
         int spiralsegments,
         float spiralRadius,
         float lineThickness,
         float edgeWidth,
         float[] color1,
         @Nullable float[] color2,
         @Nullable float[] color3
      ) {
         super(texture, lightAdd);
         this.animspeed = animspeed;
         this.spiralsegments = spiralsegments;
         this.spiralRadius = spiralRadius;
         this.lineThickness = lineThickness;
         this.color1 = color1;
         this.color2 = color2 == null ? color1 : color2;
         this.color3 = color3 == null ? color1 : color3;
         this.maximumDistance = maximumDistance;
         this.edgeWidth = edgeWidth;
      }

      @Override
      public void renderLaser(
         Entity entity, Vec3d laserPos, float laserPitch, float laserYaw, double distance, double x, double y, double z, float partialTicks
      ) {
         MyRenderHelper.renderLaserSpiral(
            (float)Math.min(distance, this.maximumDistance),
            this.spiralsegments,
            this.spiralRadius,
            this.lineThickness,
            this.edgeWidth,
            this.animspeed,
            this.color1,
            this.color2,
            this.color3
         );
      }
   }
}
