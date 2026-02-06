package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.elements.models.CubikModel;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public abstract class RenderModule {
   public static Random rand = new Random();
   public boolean multicolored;
   public SourceFactor sourceFactor = SourceFactor.SRC_ALPHA;
   public DestFactor destFactor = DestFactor.ONE_MINUS_SRC_ALPHA;
   public ResourceLocation tex;

   public RenderModule setMulticolored() {
      this.multicolored = true;
      return this;
   }

   public RenderModule setBlendFunc(SourceFactor sourceFactor, DestFactor destFactor) {
      this.sourceFactor = sourceFactor;
      this.destFactor = destFactor;
      return this;
   }

   public static void color(@Nullable Vec3d color) {
      if (color != null) {
         GlStateManager.color((float)color.x, (float)color.y, (float)color.z);
      }
   }

   public abstract void render(Entity var1, double var2, double var4, double var6, float var8, float var9);

   public interface IRenderModuleMulticolored {
      @Nullable
      Vec3d getColor(int var1);
   }

   public interface IRenderModuleOverride {
      void rewriteModuleParameters(RenderModule var1);
   }

   public static class RMod2dSprite extends RenderModule {
      public float scale;
      public boolean dynamicScale;
      public int light;
      public float Red;
      public float Green;
      public float Blue;
      public float Alpha;
      public boolean blend;
      public float displacement = 0.0F;
      public int framecount = 1;
      public int animDelay = 1;
      public int animOffset;
      public boolean disableOnEnd;
      public boolean randomizeLoop;
      public Vec3d rotationXYZ = Vec3d.ZERO;
      public Vec3d rotationSpeedXYZ;
      public boolean turnToCamera = true;
      public boolean cullface = true;
      public static int[] randomOffsets = new int[]{
         rand.nextInt(256),
         rand.nextInt(256),
         rand.nextInt(256),
         rand.nextInt(256),
         rand.nextInt(256),
         rand.nextInt(256),
         rand.nextInt(256),
         rand.nextInt(256),
         rand.nextInt(256),
         rand.nextInt(256)
      };

      public RMod2dSprite(ResourceLocation tex, float scale, int light, float Red, float Green, float Blue, float Alpha, boolean blend, float displacement) {
         this.scale = scale;
         this.light = light;
         this.tex = tex;
         this.Red = Red;
         this.Green = Green;
         this.Blue = Blue;
         this.Alpha = Alpha;
         this.blend = blend;
         this.displacement = displacement;
      }

      public RMod2dSprite setAnimation(int framecount, int animDelay, int animOffset, boolean disableOnEnd, boolean randomizeLoop) {
         this.framecount = framecount;
         this.animDelay = animDelay;
         this.animOffset = animOffset;
         this.disableOnEnd = disableOnEnd;
         this.randomizeLoop = randomizeLoop;
         return this;
      }

      public RMod2dSprite setRotation(Vec3d rotationXYZ, @Nullable Vec3d rotationSpeedXYZ, boolean turnToCamera, boolean cullface) {
         this.rotationXYZ = rotationXYZ;
         this.rotationSpeedXYZ = rotationSpeedXYZ;
         this.turnToCamera = turnToCamera;
         this.cullface = cullface;
         return this;
      }

      public RMod2dSprite setDynamicScale() {
         this.dynamicScale = true;
         return this;
      }

      @Override
      public void render(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
         float time = entity.ticksExisted / this.animDelay + this.animOffset;
         if (this.randomizeLoop) {
            time += randomOffsets[entity.getEntityId() % 10];
         }

         if (!this.disableOnEnd || time < this.framecount) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x, (float)y + entity.height / 2.0F, (float)z);
            GlStateManager.enableRescaleNormal();
            GlStateManager.disableLighting();
            float xx = (float)this.rotationXYZ.x;
            float yy = (float)this.rotationXYZ.y;
            float zz = (float)this.rotationXYZ.z;
            if (this.turnToCamera) {
               xx += (Minecraft.getMinecraft().getRenderManager().options.thirdPersonView == 2 ? -1 : 1)
                  * Minecraft.getMinecraft().getRenderManager().playerViewX;
               yy += -Minecraft.getMinecraft().getRenderManager().playerViewY;
            }

            if (this.rotationSpeedXYZ != null) {
               float te = entity.ticksExisted + partialTicks;
               xx += (float)this.rotationSpeedXYZ.x * te;
               yy += (float)this.rotationSpeedXYZ.y * te;
               zz += (float)this.rotationSpeedXYZ.z * te;
            }

            GlStateManager.rotate(yy, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(xx, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(zz, 0.0F, 0.0F, 1.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.tex);
            if (this.dynamicScale) {
               GlStateManager.scale(this.scale * entity.width, this.scale * entity.height, this.scale * entity.width);
            } else {
               GlStateManager.scale(this.scale, this.scale, this.scale);
            }

            GL11.glDepthMask(false);
            if (!this.cullface) {
               GL11.glDisable(2884);
            }

            GL11.glDisable(2896);
            GlStateManager.blendFunc(this.sourceFactor, this.destFactor);
            if (this.light < 0) {
               AbstractMobModel.setLightmapWithBrightnessForRender(entity);
            } else {
               AbstractMobModel.light(this.light, false);
            }

            if (this.blend) {
               GL11.glEnable(3042);
            }

            if (this.multicolored) {
               color(((IRenderModuleMulticolored)entity).getColor(0));
            } else {
               GlStateManager.color(this.Red, this.Green, this.Blue, 1.0F);
            }

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-1.0, -1.0, this.displacement).tex(0.0, 1.0F / this.framecount + time / this.framecount).endVertex();
            bufferbuilder.pos(-1.0, 1.0, this.displacement).tex(0.0, 0.0F + time / this.framecount).endVertex();
            bufferbuilder.pos(1.0, 1.0, this.displacement).tex(1.0, 0.0F + time / this.framecount).endVertex();
            bufferbuilder.pos(1.0, -1.0, this.displacement).tex(1.0, 1.0F / this.framecount + time / this.framecount).endVertex();
            tessellator.draw();
            if (this.blend) {
               GL11.glDisable(3042);
            }

            AbstractMobModel.returnlight();
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GL11.glDepthMask(true);
            if (!this.cullface) {
               GL11.glEnable(2884);
            }

            GlStateManager.enableLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
         }
      }
   }

   public static class RModCeratargetTail extends RenderModule {
      public float red1;
      public float green1;
      public float blue1;
      public float red2;
      public float green2;
      public float blue2;
      public float motionLengthMultiplier = 1.5F;
      public float lengthMin = 0.3F;
      public float lengthMax = 64.0F;
      public float displacement = 0.025F;
      public float constriction = 0.02F;
      public int light;

      public RModCeratargetTail(
         ResourceLocation tex,
         float red1,
         float green1,
         float blue1,
         float red2,
         float green2,
         float blue2,
         float motionLengthMultiplier,
         float lengthMin,
         float lengthMax,
         float displacement,
         float constriction,
         int light
      ) {
         this.tex = tex;
         this.red1 = red1;
         this.green1 = green1;
         this.blue1 = blue1;
         this.red2 = red2;
         this.green2 = green2;
         this.blue2 = blue2;
         this.motionLengthMultiplier = motionLengthMultiplier;
         this.lengthMin = lengthMin;
         this.lengthMax = lengthMax;
         this.displacement = displacement;
         this.constriction = constriction;
         this.light = light;
      }

      public RModCeratargetTail(
         ResourceLocation tex, float motionLengthMultiplier, float lengthMin, float lengthMax, float displacement, float constriction, int light
      ) {
         this.tex = tex;
         this.motionLengthMultiplier = motionLengthMultiplier;
         this.lengthMin = lengthMin;
         this.lengthMax = lengthMax;
         this.displacement = displacement;
         this.constriction = constriction;
         this.light = light;
      }

      @Override
      public void render(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entity.height / 2.0F, (float)z);
         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         float lbX = OpenGlHelper.lastBrightnessX;
         float lbY = OpenGlHelper.lastBrightnessY;
         Minecraft.getMinecraft().renderEngine.bindTexture(this.tex);
         if (this.light < 0) {
            AbstractMobModel.setLightmapWithBrightnessForRender(entity);
         } else {
            AbstractMobModel.light(this.light, false);
         }

         RenderHelper.disableStandardItemLighting();
         GlStateManager.shadeModel(7425);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(this.sourceFactor, this.destFactor);
         GlStateManager.disableAlpha();
         GlStateManager.enableTexture2D();
         GlStateManager.disableCull();
         GlStateManager.depthMask(false);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         double motion = Math.sqrt(
            entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ
         );
         double zLength = -MathHelper.clamp(motion * this.motionLengthMultiplier, this.lengthMin, this.lengthMax);
         float R2 = this.red2;
         float G2 = this.green2;
         float B2 = this.blue2;
         float colorR1 = this.red1;
         float colorG1 = this.green1;
         float colorB1 = this.blue1;
         if (this.multicolored) {
            Vec3d col1 = ((IRenderModuleMulticolored)entity).getColor(1);
            Vec3d col2 = ((IRenderModuleMulticolored)entity).getColor(2);
            colorR1 = (float)col1.x;
            colorG1 = (float)col1.y;
            colorB1 = (float)col1.z;
            R2 = (float)col2.x;
            G2 = (float)col2.y;
            B2 = (float)col2.z;
         }

         float xDisp = this.displacement;
         float yUp = -this.displacement;
         float yDown = this.displacement;
         float xDispAdd = -this.constriction;
         float yDispAdd = -this.constriction;
         float texYAdd = -AnimationTimer.tick / 40.0F;
         bufferbuilder.pos(-xDisp, yUp, 0.0).tex(0.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(-xDisp - xDispAdd, yUp - yDispAdd, zLength)
            .tex(0.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(xDisp + xDispAdd, yUp - yDispAdd, zLength)
            .tex(1.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(xDisp, yUp, 0.0).tex(1.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(-xDisp, yDown, 0.0).tex(0.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(-xDisp - xDispAdd, yDown + yDispAdd, zLength)
            .tex(0.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(xDisp + xDispAdd, yDown + yDispAdd, zLength)
            .tex(1.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(xDisp, yDown, 0.0).tex(1.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(-xDisp, yUp, 0.0).tex(0.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(-xDisp - xDispAdd, yUp - yDispAdd, zLength)
            .tex(0.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(-xDisp - xDispAdd, yDown + yDispAdd, zLength)
            .tex(1.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(-xDisp, yDown, 0.0).tex(1.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(xDisp, yUp, 0.0).tex(0.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(xDisp + xDispAdd, yUp - yDispAdd, zLength)
            .tex(0.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(xDisp + xDispAdd, yDown + yDispAdd, zLength)
            .tex(1.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(xDisp, yDown, 0.0).tex(1.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         tessellator.draw();
         GlStateManager.shadeModel(7424);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         AbstractMobModel.returnlight();
         GlStateManager.popMatrix();
      }
   }

   public static class RModCubic extends RenderModule {
      public float scale;
      public int light;
      public float Red;
      public float Green;
      public float Blue;
      public float Alpha;
      public boolean blend;
      public float rotateSpeed;
      public int rotateMode;
      public static CubikModel model = new CubikModel();

      public RModCubic(
         ResourceLocation tex, float scale, int light, float Red, float Green, float Blue, float Alpha, boolean blend, int rotateMode, float rotateSpeed
      ) {
         this.scale = scale;
         this.light = light;
         this.tex = tex;
         this.Red = Red;
         this.Green = Green;
         this.Blue = Blue;
         this.Alpha = Alpha;
         this.blend = blend;
         this.rotateSpeed = rotateSpeed;
         this.rotateMode = rotateMode;
      }

      @Override
      public void render(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
         float rrotateX = 0.0F;
         float rrotateY = 0.0F;
         float rrotateZ = 0.0F;
         if (this.rotateMode == 2) {
            float time = entity.ticksExisted + partialTicks;
            float f = entity.getEntityId() % 2 == 0 ? 1.1343557F : 194.3975F;
            rrotateX = entity.getEntityId() + time * this.rotateSpeed;
            rrotateY = f * entity.getEntityId() + time * this.rotateSpeed;
            rrotateZ = entity.getEntityId() / 1836.0F * f + time * this.rotateSpeed;
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entity.height / 2.0F, (float)z);
         if (this.rotateMode == 1) {
            GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
         }

         GlStateManager.enableRescaleNormal();
         GlStateManager.disableLighting();
         Minecraft.getMinecraft().renderEngine.bindTexture(this.tex);
         GlStateManager.scale(this.scale, this.scale, this.scale);
         GL11.glDisable(2896);
         GlStateManager.blendFunc(this.sourceFactor, this.destFactor);
         if (this.light < 0) {
            AbstractMobModel.setLightmapWithBrightnessForRender(entity);
         } else {
            AbstractMobModel.light(this.light, false);
         }

         if (this.blend) {
            GL11.glEnable(3042);
         }

         if (this.multicolored) {
            color(((IRenderModuleMulticolored)entity).getColor(0));
         } else {
            GlStateManager.color(this.Red, this.Green, this.Blue, 1.0F);
         }

         model.setRotateAngle(model.shape1, rrotateX, rrotateY, rrotateZ);
         model.render(entity, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
         if (this.blend) {
            GL11.glDisable(3042);
         }

         AbstractMobModel.returnlight();
         GL11.glEnable(2896);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.enableLighting();
         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
      }
   }

   public static class RModCutter extends RenderModule {
      public int light;
      public float Red;
      public float Green;
      public float Blue;
      public float Alpha;
      public boolean blend;
      public boolean cullface = false;
      public float rotationRoll;
      public int ticksForMaxWidth;
      public float cutterSize;
      public float zScale;
      public float yScale;
      public int framecount = 1;
      public int animDelay = 1;
      public int pixelsTopView;
      public int pixelsFrontView;

      public RModCutter(
         ResourceLocation tex,
         float cutterSize,
         float zScale,
         float yScale,
         int light,
         float red,
         float green,
         float blue,
         float alpha,
         boolean blend,
         float rotationRoll,
         int ticksForMaxWidth,
         int pixelsTopView,
         int pixelsFrontView
      ) {
         this.light = light;
         this.tex = tex;
         this.Red = red;
         this.Green = green;
         this.Blue = blue;
         this.Alpha = alpha;
         this.blend = blend;
         this.rotationRoll = rotationRoll;
         this.ticksForMaxWidth = ticksForMaxWidth;
         this.cutterSize = cutterSize;
         this.zScale = zScale;
         this.yScale = yScale;
         this.pixelsTopView = pixelsTopView;
         this.pixelsFrontView = pixelsFrontView;
      }

      public RModCutter setAnimation(int framecount, int animDelay) {
         this.framecount = framecount;
         this.animDelay = animDelay;
         return this;
      }

      @Override
      public void render(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entity.height / 2.0F, (float)z);
         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(this.rotationRoll, 1.0F, 0.0F, 0.0F);
         float tick = entity.ticksExisted + partialTicks;
         float fromto = GetMOP.getfromto(tick, 0.0F, (float)this.ticksForMaxWidth);
         float beamwidth = this.cutterSize / 2.0F * fromto;
         GlStateManager.pushMatrix();
         GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glDepthMask(false);
         Minecraft.getMinecraft().renderEngine.bindTexture(this.tex);
         if (!this.cullface) {
            GL11.glDisable(2884);
         }

         GL11.glDisable(2896);
         GlStateManager.blendFunc(this.sourceFactor, this.destFactor);
         if (this.light < 0) {
            AbstractMobModel.setLightmapWithBrightnessForRender(entity);
         } else {
            AbstractMobModel.light(this.light, false);
         }

         if (this.blend) {
            GL11.glEnable(3042);
         }

         if (this.multicolored) {
            color(((IRenderModuleMulticolored)entity).getColor(0));
         } else {
            GlStateManager.color(this.Red, this.Green, this.Blue, 1.0F);
         }

         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         float frame = entity.ticksExisted / this.animDelay % this.framecount;
         float framesAmount = this.framecount;
         float one = 1.0F / framesAmount;
         float pixelsAll = this.pixelsTopView + this.pixelsFrontView;
         float oneBeamBig = one * (this.pixelsTopView / pixelsAll);
         float oneBeamSmall = one * (this.pixelsFrontView / pixelsAll);
         float posBeamBigLower = frame * one;
         float posBeamBigHigher = posBeamBigLower + oneBeamBig;
         float posBeamSmallHigher = posBeamBigHigher + oneBeamSmall;
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-beamwidth, 0.0, -0.2F * this.zScale).tex(0.0, posBeamBigHigher).endVertex();
         bufferbuilder.pos(-beamwidth, 0.0, 0.3F * this.zScale).tex(0.0, posBeamBigLower).endVertex();
         bufferbuilder.pos(beamwidth, 0.0, 0.3F * this.zScale).tex(1.0, posBeamBigLower).endVertex();
         bufferbuilder.pos(beamwidth, 0.0, -0.2F * this.zScale).tex(1.0, posBeamBigHigher).endVertex();
         float onePartWidth = beamwidth * 2.0F / 3.0F;
         float offsetZ = 0.17F * this.zScale;
         float scaleY = this.yScale;
         bufferbuilder.pos(-beamwidth, -scaleY, -0.2F * this.zScale + offsetZ).tex(0.0, posBeamSmallHigher).endVertex();
         bufferbuilder.pos(-beamwidth, scaleY, -0.2F * this.zScale + offsetZ).tex(0.0, posBeamBigHigher).endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth, scaleY, -0.04F * this.zScale + offsetZ)
            .tex(0.33333334F, posBeamBigHigher)
            .endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth, -scaleY, -0.04F * this.zScale + offsetZ)
            .tex(0.33333334F, posBeamSmallHigher)
            .endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth, -scaleY, -0.04F * this.zScale + offsetZ)
            .tex(0.33333334F, posBeamSmallHigher)
            .endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth, scaleY, -0.04F * this.zScale + offsetZ)
            .tex(0.33333334F, posBeamBigHigher)
            .endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth * 2.0F, scaleY, -0.04F * this.zScale + offsetZ)
            .tex(0.6666667F, posBeamBigHigher)
            .endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth * 2.0F, -scaleY, -0.04F * this.zScale + offsetZ)
            .tex(0.6666667F, posBeamSmallHigher)
            .endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth * 2.0F, -scaleY, -0.04F * this.zScale + offsetZ)
            .tex(0.6666667F, posBeamSmallHigher)
            .endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth * 2.0F, scaleY, -0.04F * this.zScale + offsetZ)
            .tex(0.6666667F, posBeamBigHigher)
            .endVertex();
         bufferbuilder.pos(beamwidth, scaleY, -0.2F * this.zScale + offsetZ).tex(1.0, posBeamBigHigher).endVertex();
         bufferbuilder.pos(beamwidth, -scaleY, -0.2F * this.zScale + offsetZ).tex(1.0, posBeamSmallHigher).endVertex();
         tessellator.draw();
         GL11.glDepthMask(true);
         if (this.blend) {
            GL11.glDisable(3042);
         }

         AbstractMobModel.returnlight();
         GL11.glEnable(2896);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         if (!this.cullface) {
            GL11.glEnable(2884);
         }

         GlStateManager.popMatrix();
         GlStateManager.popMatrix();
      }
   }

   public static class RModModel extends RenderModule {
      public float scale;
      public int light;
      public float Red;
      public float Green;
      public float Blue;
      public float Alpha;
      public boolean blend;
      public float rotateSpeed;
      public int rotateMode;
      public ModelBase model;
      public int rotateseed;

      public RModModel(
         ResourceLocation tex,
         ModelBase model,
         float scale,
         int light,
         float Red,
         float Green,
         float Blue,
         float Alpha,
         boolean blend,
         int rotateMode,
         float rotateSpeed
      ) {
         this.scale = scale;
         this.light = light;
         this.tex = tex;
         this.Red = Red;
         this.Green = Green;
         this.Blue = Blue;
         this.Alpha = Alpha;
         this.blend = blend;
         this.rotateSpeed = rotateSpeed;
         this.rotateMode = rotateMode;
         this.model = model;
      }

      public RModModel(
         ResourceLocation tex,
         ModelBase model,
         float scale,
         int light,
         float Red,
         float Green,
         float Blue,
         float Alpha,
         boolean blend,
         int rotateMode,
         float rotateSpeed,
         int rotateSeed
      ) {
         this.scale = scale;
         this.light = light;
         this.tex = tex;
         this.Red = Red;
         this.Green = Green;
         this.Blue = Blue;
         this.Alpha = Alpha;
         this.blend = blend;
         this.rotateSpeed = rotateSpeed;
         this.rotateMode = rotateMode;
         this.model = model;
         this.rotateseed = rotateSeed;
      }

      @Override
      public void render(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
         float rrotateX = 0.0F;
         float rrotateY = 0.0F;
         float rrotateZ = 0.0F;
         if (this.rotateMode == 2) {
            float time = entity.ticksExisted + partialTicks;
            int entiId = entity.getEntityId() + this.rotateseed;
            float f = entiId % 2 == 0 ? 1.1343557F : 194.3975F;
            rrotateX = entiId + time * this.rotateSpeed;
            rrotateY = f * entiId + time * this.rotateSpeed;
            rrotateZ = entiId / 1836.0F * f + time * this.rotateSpeed;
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entity.height / 2.0F, (float)z);
         if (this.rotateMode == 1) {
            GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
         }

         if (this.rotateMode == 2) {
            GlStateManager.rotate(rrotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(rrotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(rrotateZ, 0.0F, 0.0F, 1.0F);
         }

         if (this.rotateMode == 3) {
            GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(90.0F, 0.0F, -1.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
         }

         GlStateManager.enableRescaleNormal();
         GlStateManager.disableLighting();
         Minecraft.getMinecraft().renderEngine.bindTexture(this.tex);
         GlStateManager.scale(this.scale, this.scale, this.scale);
         GL11.glDisable(2896);
         GlStateManager.blendFunc(this.sourceFactor, this.destFactor);
         if (this.light < 0) {
            AbstractMobModel.setLightmapWithBrightnessForRender(entity);
         } else {
            AbstractMobModel.light(this.light, false);
         }

         if (this.blend) {
            GL11.glEnable(3042);
         }

         if (this.multicolored) {
            color(((IRenderModuleMulticolored)entity).getColor(0));
         } else {
            GlStateManager.color(this.Red, this.Green, this.Blue, 1.0F);
         }

         this.model.render(entity, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
         if (this.blend) {
            GL11.glDisable(3042);
         }

         AbstractMobModel.returnlight();
         GL11.glEnable(2896);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.enableLighting();
         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
      }
   }

   public static class RModRenderByBoundingBox extends RenderModule {
      public int light;
      public float Red;
      public float Green;
      public float Blue;
      public float Alpha;
      public boolean blend;
      public boolean cullface = false;
      public boolean stretchTexture = false;

      public RModRenderByBoundingBox(ResourceLocation tex, int light, float Red, float Green, float Blue, float Alpha, boolean blend, boolean stretchTexture) {
         this.light = light;
         this.tex = tex;
         this.Red = Red;
         this.Green = Green;
         this.Blue = Blue;
         this.Alpha = Alpha;
         this.blend = blend;
         this.stretchTexture = stretchTexture;
      }

      @Override
      public void render(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.disableLighting();
         Minecraft.getMinecraft().renderEngine.bindTexture(this.tex);
         GL11.glDepthMask(false);
         if (!this.cullface) {
            GL11.glDisable(2884);
         }

         GL11.glDisable(2896);
         GlStateManager.blendFunc(this.sourceFactor, this.destFactor);
         if (this.light < 0) {
            AbstractMobModel.setLightmapWithBrightnessForRender(entity);
         } else {
            AbstractMobModel.light(this.light, false);
         }

         if (this.blend) {
            GL11.glEnable(3042);
         }

         if (this.multicolored) {
            color(((IRenderModuleMulticolored)entity).getColor(0));
         } else {
            GlStateManager.color(this.Red, this.Green, this.Blue, 1.0F);
         }

         double texScl = 16.0;
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         AxisAlignedBB raabb = entity.getRenderBoundingBox();
         AxisAlignedBB aabb = raabb.offset(-entity.posX, -entity.posY, -entity.posZ);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         if (this.stretchTexture) {
            bufferbuilder.pos(aabb.minX, aabb.minY, aabb.minZ).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.minY, aabb.minZ).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ).tex(1.0, 1.0).endVertex();
            bufferbuilder.pos(aabb.minX, aabb.minY, aabb.maxZ).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(aabb.minX, aabb.maxY, aabb.minZ).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).tex(1.0, 1.0).endVertex();
            bufferbuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(aabb.minX, aabb.minY, aabb.minZ).tex(1.0, 1.0).endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.minY, aabb.minZ).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(aabb.minX, aabb.maxY, aabb.minZ).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(aabb.minX, aabb.minY, aabb.maxZ).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ).tex(1.0, 1.0).endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(aabb.minX, aabb.minY, aabb.minZ).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(aabb.minX, aabb.minY, aabb.maxZ).tex(1.0, 1.0).endVertex();
            bufferbuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(aabb.minX, aabb.maxY, aabb.minZ).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.minY, aabb.minZ).tex(1.0, 1.0).endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ).tex(1.0, 0.0).endVertex();
         } else {
            bufferbuilder.pos(aabb.minX, aabb.minY, aabb.minZ)
               .tex(aabb.minX, aabb.minZ)
               .endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.minY, aabb.minZ)
               .tex(aabb.maxX, aabb.minZ)
               .endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ)
               .tex(aabb.maxX, aabb.maxZ)
               .endVertex();
            bufferbuilder.pos(aabb.minX, aabb.minY, aabb.maxZ)
               .tex(aabb.minX, aabb.maxZ)
               .endVertex();
            bufferbuilder.pos(aabb.minX, aabb.maxY, aabb.minZ)
               .tex(aabb.minX, aabb.minZ)
               .endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ)
               .tex(aabb.maxX, aabb.minZ)
               .endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ)
               .tex(aabb.maxX, aabb.maxZ)
               .endVertex();
            bufferbuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ)
               .tex(aabb.minX, aabb.maxZ)
               .endVertex();
            bufferbuilder.pos(aabb.minX, aabb.minY, aabb.minZ)
               .tex(aabb.maxX, -aabb.minY)
               .endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.minY, aabb.minZ)
               .tex(aabb.minX, -aabb.minY)
               .endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ)
               .tex(aabb.minX, -aabb.maxY)
               .endVertex();
            bufferbuilder.pos(aabb.minX, aabb.maxY, aabb.minZ)
               .tex(aabb.maxX, -aabb.maxY)
               .endVertex();
            bufferbuilder.pos(aabb.minX, aabb.minY, aabb.maxZ)
               .tex(aabb.minX, -aabb.minY)
               .endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ)
               .tex(aabb.maxX, -aabb.minY)
               .endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ)
               .tex(aabb.maxX, -aabb.maxY)
               .endVertex();
            bufferbuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ)
               .tex(aabb.minX, -aabb.maxY)
               .endVertex();
            bufferbuilder.pos(aabb.minX, aabb.minY, aabb.minZ)
               .tex(aabb.minZ, -aabb.minY)
               .endVertex();
            bufferbuilder.pos(aabb.minX, aabb.minY, aabb.maxZ)
               .tex(aabb.maxZ, -aabb.minY)
               .endVertex();
            bufferbuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ)
               .tex(aabb.maxZ, -aabb.maxY)
               .endVertex();
            bufferbuilder.pos(aabb.minX, aabb.maxY, aabb.minZ)
               .tex(aabb.minZ, -aabb.maxY)
               .endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.minY, aabb.minZ)
               .tex(aabb.maxZ, -aabb.minY)
               .endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ)
               .tex(aabb.minZ, -aabb.minY)
               .endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ)
               .tex(aabb.minZ, -aabb.maxY)
               .endVertex();
            bufferbuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ)
               .tex(aabb.maxZ, -aabb.maxY)
               .endVertex();
         }

         tessellator.draw();
         if (this.blend) {
            GL11.glDisable(3042);
         }

         AbstractMobModel.returnlight();
         GL11.glEnable(2896);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GL11.glDepthMask(true);
         if (!this.cullface) {
            GL11.glEnable(2884);
         }

         GlStateManager.enableLighting();
         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
      }

      public static double positive(double d) {
         if (d < 0.0) {
            double add = Math.ceil(-d);
            return d + add;
         } else {
            return d;
         }
      }
   }

   public static class RModTail extends RenderModule {
      public float scale;
      public float length;
      public boolean lengthMultipliedBySpeed;
      public int light;
      public float Red;
      public float Green;
      public float Blue;
      public float Alpha;
      public boolean blend;
      public int style = -1;
      public boolean cullface = false;
      public float centerRatio;

      public RModTail(
         ResourceLocation tex,
         float scale,
         float length,
         boolean lengthMultipliedBySpeed,
         float centerRatio,
         int light,
         float Red,
         float Green,
         float Blue,
         float Alpha,
         boolean blend,
         int style
      ) {
         this.scale = scale;
         this.length = length;
         this.lengthMultipliedBySpeed = lengthMultipliedBySpeed;
         this.light = light;
         this.tex = tex;
         this.Red = Red;
         this.Green = Green;
         this.Blue = Blue;
         this.Alpha = Alpha;
         this.blend = blend;
         this.style = style;
         this.centerRatio = centerRatio;
      }

      @Override
      public void render(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entity.height / 2.0F, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.disableLighting();
         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
         Minecraft.getMinecraft().renderEngine.bindTexture(this.tex);
         GL11.glDepthMask(false);
         if (!this.cullface) {
            GL11.glDisable(2884);
         }

         GL11.glDisable(2896);
         GlStateManager.blendFunc(this.sourceFactor, this.destFactor);
         if (this.light < 0) {
            AbstractMobModel.setLightmapWithBrightnessForRender(entity);
         } else {
            AbstractMobModel.light(this.light, false);
         }

         if (this.blend) {
            GL11.glEnable(3042);
         }

         if (this.multicolored) {
            color(((IRenderModuleMulticolored)entity).getColor(0));
         } else {
            GlStateManager.color(this.Red, this.Green, this.Blue, 1.0F);
         }

         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         float finallength = this.lengthMultipliedBySpeed ? this.length * (float)GetMOP.getSpeed(entity) : this.length;
         if (this.style == -1) {
            MyRenderHelper.drawVerticalLaser(bufferbuilder, this.scale, finallength, tessellator, 0.0F, finallength * (1.0F - this.centerRatio));
         }

         if (this.style == 1) {
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            MyRenderHelper.drawVerticalLaser(bufferbuilder, this.scale, finallength, tessellator, 0.0F, finallength * (1.0F - this.centerRatio));
         }

         if (this.style > 1) {
            float angle = 180.0F / this.style;

            for (int i = 0; i < this.style; i++) {
               GlStateManager.rotate(angle, 0.0F, 0.0F, 1.0F);
               MyRenderHelper.drawVerticalLaser(bufferbuilder, this.scale, finallength, tessellator, 0.0F, finallength * (1.0F - this.centerRatio));
            }
         }

         if (this.style == 0) {
            double entityY = entity.posY + entity.height / 2.0F;
            GlStateManager.rotate(
               MyRenderHelper.calculateBeamFaceToCameraAngle(
                  entity.posX, entityY, entity.posZ, entity.motionX, entity.motionY, entity.motionZ, partialTicks
               ),
               0.0F,
               0.0F,
               1.0F
            );
            MyRenderHelper.drawVerticalLaser(bufferbuilder, this.scale, finallength, tessellator, 0.0625F, finallength * (1.0F - this.centerRatio));
         }

         if (this.blend) {
            GL11.glDisable(3042);
         }

         AbstractMobModel.returnlight();
         GL11.glEnable(2896);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GL11.glDepthMask(true);
         if (!this.cullface) {
            GL11.glEnable(2884);
         }

         GlStateManager.enableLighting();
         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
      }
   }
}
