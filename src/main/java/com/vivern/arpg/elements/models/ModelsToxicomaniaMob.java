package com.vivern.arpg.elements.models;

import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.mobs.AbstractMob;
import com.vivern.arpg.mobs.ToxicomaniaMobsPack;
import com.vivern.arpg.renders.ModelRendererGlow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class ModelsToxicomaniaMob {
   public static final Vec3d getVectorForRotation(float pitch, float yaw) {
      float f = MathHelper.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f1 = MathHelper.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f2 = -MathHelper.cos(-pitch * (float) (Math.PI / 180.0));
      float f3 = MathHelper.sin(-pitch * (float) (Math.PI / 180.0));
      return new Vec3d(f1 * f2, f3, f * f2);
   }

   public static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
      float f = yawOffset - prevYawOffset;

      while (f < -180.0F) {
         f += 360.0F;
      }

      while (f >= 180.0F) {
         f -= 360.0F;
      }

      return prevYawOffset + partialTicks * f;
   }

   public static class AbominationCannonModel extends ModelBase {
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer shape5;
      public ModelRenderer shape6;
      public ModelRenderer shape7;
      public ModelRenderer cannon2;
      public ModelRenderer cannon0;
      public ModelRenderer cannon1;
      public ModelRenderer flame5;
      public ModelRenderer flame6;
      public ModelRenderer flame1;
      public ModelRenderer flame2;
      public ModelRenderer flame3;
      public ModelRenderer flame4;
      public ModelRenderer cannon2a;
      public ModelRenderer cannon2b;
      public ModelRenderer cannon2c;
      public ModelRenderer cannon2d;
      public ModelRenderer cannon0a;
      public ModelRenderer cannon0b;
      public ModelRenderer cannon0c;
      public ModelRenderer cannon1a;
      public ModelRenderer cannon1b;
      public ModelRenderer cannon1c;
      public ModelRenderer cannon1d;

      public AbominationCannonModel() {
         this.textureWidth = 64;
         this.textureHeight = 64;
         this.flame6 = new ModelRendererGlow(this, 0, 0, 180, true);
         this.flame6.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.flame6.addBox(-2.5F, 0.0F, 0.0F, 5, 6, 0, 0.0F);
         this.setRotateAngle(this.flame6, 0.0F, 0.68294734F, 0.0F);
         this.cannon0b = new ModelRenderer(this, 50, 43);
         this.cannon0b.setRotationPoint(0.0F, 0.0F, -5.0F);
         this.cannon0b.addBox(-3.0F, -3.0F, 0.0F, 6, 6, 1, 0.0F);
         this.cannon1 = new ModelRenderer(this, 0, 16);
         this.cannon1.setRotationPoint(0.0F, -11.5F, 0.0F);
         this.cannon1.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 8, 0.0F);
         this.shape6 = new ModelRenderer(this, 34, 12);
         this.shape6.setRotationPoint(-4.0F, 3.0F, 2.0F);
         this.shape6.addBox(-2.5F, 0.0F, -2.5F, 5, 6, 5, 0.0F);
         this.setRotateAngle(this.shape6, 0.31869712F, 0.0F, 0.5462881F);
         this.cannon0 = new ModelRenderer(this, 0, 16);
         this.cannon0.setRotationPoint(0.0F, -11.5F, 0.0F);
         this.cannon0.addBox(-4.0F, -4.0F, -3.0F, 8, 8, 8, 0.0F);
         this.flame3 = new ModelRendererGlow(this, 0, 0, 180, true);
         this.flame3.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.flame3.addBox(-2.5F, 0.0F, 0.0F, 5, 6, 0, 0.0F);
         this.setRotateAngle(this.flame3, 0.0F, -0.68294734F, 0.0F);
         this.flame5 = new ModelRendererGlow(this, 0, 0, 180, true);
         this.flame5.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.flame5.addBox(-2.5F, 0.0F, 0.0F, 5, 6, 0, 0.0F);
         this.setRotateAngle(this.flame5, 0.0F, -0.68294734F, 0.0F);
         this.flame1 = new ModelRendererGlow(this, 0, 0, 180, true);
         this.flame1.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.flame1.addBox(-2.5F, 0.0F, 0.0F, 5, 6, 0, 0.0F);
         this.setRotateAngle(this.flame1, 0.0F, -0.68294734F, 0.0F);
         this.shape1 = new ModelRenderer(this, 12, 0);
         this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape1.addBox(-5.0F, 0.0F, -4.0F, 10, 4, 8, 0.0F);
         this.shape5 = new ModelRenderer(this, 34, 12);
         this.shape5.setRotationPoint(4.0F, 3.0F, 2.0F);
         this.shape5.addBox(-2.5F, 0.0F, -2.5F, 5, 6, 5, 0.0F);
         this.setRotateAngle(this.shape5, 0.31869712F, 0.0F, -0.5462881F);
         this.flame4 = new ModelRendererGlow(this, 0, 0, 180, true);
         this.flame4.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.flame4.addBox(-2.5F, 0.0F, 0.0F, 5, 6, 0, 0.0F);
         this.setRotateAngle(this.flame4, 0.0F, 0.68294734F, 0.0F);
         this.shape2 = new ModelRenderer(this, 40, 0);
         this.shape2.setRotationPoint(0.0F, -4.0F, 0.0F);
         this.shape2.addBox(-4.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F);
         this.shape3 = new ModelRenderer(this, 54, 8);
         this.shape3.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.shape3.addBox(4.0F, -7.0F, -1.5F, 2, 12, 3, 0.0F);
         this.cannon1c = new ModelRenderer(this, 0, 32);
         this.cannon1c.setRotationPoint(0.0F, -0.5F, -4.0F);
         this.cannon1c.addBox(-2.5F, 0.0F, -3.0F, 5, 5, 7, 0.0F);
         this.cannon2 = new ModelRenderer(this, 0, 16);
         this.cannon2.setRotationPoint(0.0F, -11.5F, 0.0F);
         this.cannon2.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 8, 0.0F);
         this.cannon0a = new ModelRenderer(this, 0, 45);
         this.cannon0a.setRotationPoint(0.0F, -2.0F, -9.0F);
         this.cannon0a.addBox(-2.0F, 0.0F, -3.0F, 4, 4, 9, 0.0F);
         this.cannon2a = new ModelRenderer(this, 21, 43);
         this.cannon2a.setRotationPoint(0.0F, -4.0F, -9.0F);
         this.cannon2a.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 15, 0.0F);
         this.cannon1b = new ModelRenderer(this, 0, 8);
         this.cannon1b.setRotationPoint(0.0F, -3.0F, -12.0F);
         this.cannon1b.addBox(-1.5F, 0.0F, -3.0F, 3, 3, 5, 0.0F);
         this.cannon2c = new ModelRenderer(this, 48, 50);
         this.cannon2c.setRotationPoint(6.0F, 1.0F, 5.5F);
         this.cannon2c.addBox(-2.0F, 0.0F, -2.5F, 3, 3, 5, 0.0F);
         this.cannon1d = new ModelRenderer(this, 26, 27);
         this.cannon1d.setRotationPoint(0.0F, -4.0F, 0.0F);
         this.cannon1d.addBox(-1.5F, 0.0F, -3.0F, 3, 1, 7, 0.0F);
         this.cannon1a = new ModelRenderer(this, 34, 23);
         this.cannon1a.setRotationPoint(0.0F, -2.5F, -13.0F);
         this.cannon1a.addBox(-1.0F, 0.0F, -3.0F, 2, 2, 13, 0.0F);
         this.shape7 = new ModelRenderer(this, 34, 12);
         this.shape7.setRotationPoint(0.0F, 3.0F, -2.5F);
         this.shape7.addBox(-2.5F, 0.0F, -2.5F, 5, 6, 5, 0.0F);
         this.setRotateAngle(this.shape7, -0.68294734F, 0.0F, 0.0F);
         this.cannon0c = new ModelRenderer(this, 15, 0);
         this.cannon0c.setRotationPoint(0.0F, 0.0F, 2.0F);
         this.cannon0c.addBox(1.0F, -9.0F, 0.0F, 1, 5, 1, 0.0F);
         this.setRotateAngle(this.cannon0c, -0.13665928F, 0.0F, 0.0F);
         this.cannon2d = new ModelRenderer(this, 48, 50);
         this.cannon2d.setRotationPoint(7.0F, 4.5F, 6.0F);
         this.cannon2d.addBox(-2.0F, 0.0F, -2.5F, 3, 3, 5, 0.0F);
         this.setRotateAngle(this.cannon2d, 0.0F, 0.0F, 0.3642502F);
         this.shape4 = new ModelRenderer(this, 54, 8);
         this.shape4.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.shape4.addBox(-6.0F, -7.0F, -1.5F, 2, 12, 3, 0.0F);
         this.cannon2b = new ModelRenderer(this, 48, 50);
         this.cannon2b.setRotationPoint(4.0F, -2.0F, 5.0F);
         this.cannon2b.addBox(-2.0F, 0.0F, -2.5F, 3, 3, 5, 0.0F);
         this.setRotateAngle(this.cannon2b, 0.0F, 0.0F, -0.22759093F);
         this.flame2 = new ModelRendererGlow(this, 0, 0, 180, true);
         this.flame2.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.flame2.addBox(-2.5F, 0.0F, 0.0F, 5, 6, 0, 0.0F);
         this.setRotateAngle(this.flame2, 0.0F, 0.68294734F, 0.0F);
         this.shape5.addChild(this.flame6);
         this.cannon0.addChild(this.cannon0b);
         this.shape7.addChild(this.flame3);
         this.shape5.addChild(this.flame5);
         this.shape6.addChild(this.flame1);
         this.shape7.addChild(this.flame4);
         this.cannon1.addChild(this.cannon1c);
         this.cannon0.addChild(this.cannon0a);
         this.cannon2.addChild(this.cannon2a);
         this.cannon1.addChild(this.cannon1b);
         this.cannon2.addChild(this.cannon2c);
         this.cannon1.addChild(this.cannon1d);
         this.cannon1.addChild(this.cannon1a);
         this.cannon0.addChild(this.cannon0c);
         this.cannon2.addChild(this.cannon2d);
         this.cannon2.addChild(this.cannon2b);
         this.shape6.addChild(this.flame2);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 1.2F, 0.0F);
         int type = ((ToxicomaniaMobsPack.AbominationCannon)entity).typeCannon;
         this.shape2.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.shape3.rotateAngleY = this.shape2.rotateAngleY;
         this.shape4.rotateAngleY = this.shape2.rotateAngleY;
         float a = MathHelper.sin(AnimationTimer.tick / 4.0F) * 0.05F;
         this.flame1.offsetY = a;
         this.flame2.offsetY = a;
         this.flame3.offsetY = a;
         this.flame4.offsetY = a;
         this.flame5.offsetY = a;
         this.flame6.offsetY = a;
         if (type == 0) {
            this.cannon0.rotateAngleY = this.shape2.rotateAngleY;
            this.cannon0.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            this.cannon0.render(f5);
         } else if (type == 1) {
            this.cannon1.rotateAngleY = this.shape2.rotateAngleY;
            this.cannon1.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            this.cannon1.render(f5);
         } else if (type == 2) {
            this.cannon2.rotateAngleY = this.shape2.rotateAngleY;
            this.cannon2.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            this.cannon2.render(f5);
         }

         this.shape1.render(f5);
         this.shape2.render(f5);
         this.shape3.render(f5);
         this.shape4.render(f5);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         this.shape6.render(f5);
         this.shape5.render(f5);
         this.shape7.render(f5);
         GL11.glDisable(3042);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class BossAbominationModel extends AbstractMobModel {
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer shape5;
      public ModelRenderer shape6;
      public ModelRenderer shape7;
      public ModelRenderer pipea1;
      public ModelRenderer shape8;
      public ModelRenderer shape9;
      public ModelRenderer shape10;
      public ModelRenderer pipea2;
      public ModelRenderer pipea3;
      public ModelRenderer pipea4;
      public ModelRenderer pipeb1;
      public ModelRenderer pipeb2;
      public ModelRenderer pipeb3;
      public ModelRenderer pipeb4;

      public BossAbominationModel() {
         this.textureWidth = 128;
         this.textureHeight = 128;
         this.shape4 = new ModelRenderer(this, 16, 0);
         this.shape4.setRotationPoint(-10.0F, 8.0F, -2.0F);
         this.shape4.addBox(-2.0F, -8.0F, -2.0F, 4, 10, 4, 0.0F);
         this.setRotateAngle(this.shape4, 0.27314404F, 0.0F, -0.4098033F);
         this.pipea1 = new ModelRenderer(this, 0, 80);
         this.pipea1.setRotationPoint(0.0F, 16.5F, -1.0F);
         this.pipea1.addBox(3.0F, -9.0F, -0.5F, 1, 9, 1, 0.0F);
         this.shape5 = new ModelRenderer(this, 0, 16);
         this.shape5.mirror = true;
         this.shape5.setRotationPoint(6.0F, 11.0F, 10.0F);
         this.shape5.addBox(-2.0F, -8.0F, -2.0F, 3, 10, 3, 0.0F);
         this.setRotateAngle(this.shape5, -0.27314404F, -0.045553092F, 0.95609134F);
         this.pipea2 = new ModelRenderer(this, 0, 80);
         this.pipea2.setRotationPoint(0.0F, 16.0F, 1.0F);
         this.pipea2.addBox(3.0F, -9.0F, -0.5F, 1, 9, 1, 0.0F);
         this.shape1 = new ModelRenderer(this, 0, 0);
         this.shape1.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.shape1.addBox(-16.0F, -16.0F, -16.0F, 32, 32, 32, 0.0F);
         this.shape9 = new ModelRenderer(this, 0, 32);
         this.shape9.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.shape9.addBox(10.0F, -24.0F, -13.0F, 0, 8, 32, 0.0F);
         this.pipea4 = new ModelRenderer(this, 0, 80);
         this.pipea4.setRotationPoint(0.0F, 16.0F, 1.0F);
         this.pipea4.addBox(-4.0F, -9.0F, -0.5F, 1, 9, 1, 0.0F);
         this.shape7 = new ModelRenderer(this, 98, 8);
         this.shape7.setRotationPoint(0.0F, 16.0F, 0.0F);
         this.shape7.addBox(-2.0F, -2.0F, -2.5F, 4, 4, 5, 0.0F);
         this.pipeb3 = new ModelRenderer(this, 7, 80);
         this.pipeb3.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.pipeb3.addBox(-3.0F, -9.0F, -0.5F, 1, 1, 1, 0.0F);
         this.shape3 = new ModelRenderer(this, 0, 0);
         this.shape3.setRotationPoint(9.0F, 8.0F, -2.0F);
         this.shape3.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
         this.setRotateAngle(this.shape3, 0.4553564F, 0.0F, 0.5462881F);
         this.pipeb2 = new ModelRenderer(this, 7, 80);
         this.pipeb2.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.pipeb2.addBox(2.0F, -9.0F, -0.5F, 1, 1, 1, 0.0F);
         this.shape8 = new ModelRenderer(this, 0, 32);
         this.shape8.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.shape8.addBox(-10.0F, -24.0F, -13.0F, 0, 8, 32, 0.0F);
         this.pipea3 = new ModelRenderer(this, 0, 80);
         this.pipea3.setRotationPoint(0.0F, 16.5F, -1.0F);
         this.pipea3.addBox(-4.0F, -9.0F, -0.5F, 1, 9, 1, 0.0F);
         this.shape2 = new ModelRenderer(this, 0, 72);
         this.shape2.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.shape2.addBox(-14.0F, -14.0F, -14.0F, 28, 28, 28, 0.0F);
         this.shape6 = new ModelRenderer(this, 15, 18);
         this.shape6.mirror = true;
         this.shape6.setRotationPoint(-8.0F, 11.0F, 9.0F);
         this.shape6.addBox(-2.0F, -8.0F, -2.0F, 5, 10, 2, 0.0F);
         this.setRotateAngle(this.shape6, -0.63739425F, -0.95609134F, -0.22759093F);
         this.shape10 = new ModelRenderer(this, 86, 64);
         this.shape10.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.shape10.addBox(-5.0F, -8.0F, -5.0F, 10, 8, 10, 0.0F);
         this.pipeb4 = new ModelRenderer(this, 7, 80);
         this.pipeb4.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.pipeb4.addBox(-3.0F, -9.0F, -0.5F, 1, 1, 1, 0.0F);
         this.pipeb1 = new ModelRenderer(this, 7, 80);
         this.pipeb1.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.pipeb1.addBox(2.0F, -9.0F, -0.5F, 1, 1, 1, 0.0F);
         this.pipea3.addChild(this.pipeb3);
         this.pipea2.addChild(this.pipeb2);
         this.pipea4.addChild(this.pipeb4);
         this.pipea1.addChild(this.pipeb1);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         GlStateManager.pushMatrix();
         GlStateManager.scale(1.8F, 1.8F, 1.8F);
         GlStateManager.translate(0.0F, -0.65F, 0.0F);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         if (entity.height < 2.0F) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.7F, 0.7F, 0.7F);
            this.shape10.offsetY = 1.5F;
            light(170, true);
            this.shape10.render(f5);
            this.shape7.offsetY = 0.45F;
            this.shape7.render(f5);
            returnlight();
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            if (an3 >= 80) {
               float an = 100 - an3;
               float a = (float)(Math.sin(an / 6.5F) * 0.2F);
               GlStateManager.scale(0.28F, 0.28F * (1.0F + a), 0.28F);
            } else {
               GlStateManager.scale(0.28F, 0.28F, 0.28F);
            }

            GlStateManager.translate(0.0F, 3.7F, 0.0F);
            this.shape1.render(f5);
            GlStateManager.popMatrix();
         } else {
            this.shape10.offsetY = 0.0F;
            this.shape7.offsetY = 0.0F;
            this.pipea1.render(f5);
            this.pipea2.render(f5);
            light(170, true);
            this.shape10.render(f5);
            this.shape7.render(f5);
            returnlight();
            this.shape4.render(f5);
            this.shape5.render(f5);
            this.shape9.render(f5);
            this.pipea4.render(f5);
            this.shape3.render(f5);
            this.shape8.render(f5);
            this.pipea3.render(f5);
            this.shape6.render(f5);
            GlStateManager.pushMatrix();
            if (an3 >= 60) {
               float an = 100 - an3;
               float a = (float)(Math.sin(an * 0.078F) * 0.25);
               GlStateManager.scale(1.0F, 1.0F + a, 1.0F);
            }

            if (isAbstractMob) {
               light(MathHelper.clamp(((AbstractMob)entity).var1 * 2, 0, 240), true);
            }

            this.shape2.render(f5);
            this.shape1.render(f5);
            if (isAbstractMob) {
               returnlight();
            }

            GlStateManager.popMatrix();
         }

         GL11.glDisable(3042);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class DronModel extends ModelBase {
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer shape5;
      public ModelRenderer shape6;
      public ModelRenderer shape8;
      public ModelRenderer shape9;
      public ModelRenderer shape10;
      public ModelRenderer shape11;
      public ModelRenderer shape12;
      public ModelRenderer shape13;
      public ModelRenderer shape7;

      public DronModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.shape1 = new ModelRenderer(this, 13, 8);
         this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape1.addBox(-3.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F);
         this.shape3 = new ModelRenderer(this, 0, 13);
         this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape3.addBox(-1.0F, -4.5F, -4.5F, 2, 9, 9, 0.0F);
         this.shape9 = new ModelRenderer(this, 20, 4);
         this.shape9.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape9.addBox(-1.5F, -1.5F, -6.5F, 3, 3, 1, 0.0F);
         this.shape11 = new ModelRendererGlow(this, 0, 12, 180, true);
         this.shape11.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape11.addBox(0.0F, 5.0F, -1.5F, 0, 3, 3, 0.0F);
         this.setRotateAngle(this.shape11, 0.0F, (float) (Math.PI / 2), 0.0F);
         this.shape10 = new ModelRenderer(this, 8, 6);
         this.shape10.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape10.addBox(-1.5F, 4.0F, -1.5F, 3, 1, 3, 0.0F);
         this.shape2 = new ModelRendererGlow(this, 8, 0, 130, true);
         this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape2.addBox(-1.0F, -1.0F, -8.5F, 2, 2, 4, 0.0F);
         this.shape13 = new ModelRendererGlow(this, 0, 16, 180, true);
         this.shape13.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape13.addBox(0.0F, 5.0F, -1.5F, 0, 3, 3, 0.0F);
         this.setRotateAngle(this.shape13, 0.0F, (float) (Math.PI / 6), 0.0F);
         this.shape12 = new ModelRendererGlow(this, 0, 8, 180, true);
         this.shape12.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape12.addBox(0.0F, 5.0F, -1.5F, 0, 3, 3, 0.0F);
         this.setRotateAngle(this.shape12, 0.0F, (float) (-Math.PI / 6), 0.0F);
         this.shape5 = new ModelRenderer(this, 13, 22);
         this.shape5.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape5.addBox(-4.5F, -0.5F, -4.5F, 9, 1, 9, 0.0F);
         this.shape7 = new ModelRenderer(this, 0, 5);
         this.shape7.setRotationPoint(0.02F, -6.2F, 0.0F);
         this.shape7.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.shape7, 0.7740535F, 0.0F, 0.0F);
         this.shape8 = new ModelRenderer(this, 4, 0);
         this.shape8.setRotationPoint(-2.25F, -1.0F, 2.2F);
         this.shape8.addBox(-0.5F, -7.5F, -0.5F, 1, 6, 1, 0.0F);
         this.shape4 = new ModelRenderer(this, 40, 20);
         this.shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape4.addBox(-4.5F, -4.5F, -1.0F, 9, 9, 2, 0.0F);
         this.shape6 = new ModelRenderer(this, 0, 0);
         this.shape6.setRotationPoint(2.25F, -1.0F, 0.2F);
         this.shape6.addBox(-0.5F, -6.5F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.shape6, -0.63739425F, 0.0F, 0.0F);
         this.shape6.addChild(this.shape7);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 1.2F, 0.0F);
         this.shape1.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.shape1.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.shape3.rotateAngleY = this.shape1.rotateAngleY;
         this.shape3.rotateAngleX = this.shape1.rotateAngleX;
         this.shape5.rotateAngleY = this.shape1.rotateAngleY;
         this.shape5.rotateAngleX = this.shape1.rotateAngleX;
         this.shape2.rotateAngleY = this.shape1.rotateAngleY;
         this.shape2.rotateAngleX = this.shape1.rotateAngleX;
         this.shape9.rotateAngleY = this.shape1.rotateAngleY;
         this.shape9.rotateAngleX = this.shape1.rotateAngleX;
         this.shape4.rotateAngleY = this.shape1.rotateAngleY;
         this.shape11.offsetY = MathHelper.sin(AnimationTimer.tick / 4.0F) * 0.05F;
         this.shape12.offsetY = MathHelper.sin((AnimationTimer.tick + 17) / 5.0F) * 0.05F;
         this.shape13.offsetY = MathHelper.sin((AnimationTimer.tick + 41) / 7.4F) * 0.05F;
         this.shape1.render(f5);
         this.shape3.render(f5);
         this.shape9.render(f5);
         this.shape10.render(f5);
         this.shape2.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape5.offsetX, this.shape5.offsetY, this.shape5.offsetZ);
         GlStateManager.translate(this.shape5.rotationPointX * f5, this.shape5.rotationPointY * f5, this.shape5.rotationPointZ * f5);
         GlStateManager.scale(0.95, 0.95, 0.95);
         GlStateManager.translate(-this.shape5.offsetX, -this.shape5.offsetY, -this.shape5.offsetZ);
         GlStateManager.translate(-this.shape5.rotationPointX * f5, -this.shape5.rotationPointY * f5, -this.shape5.rotationPointZ * f5);
         this.shape5.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape8.offsetX, this.shape8.offsetY, this.shape8.offsetZ);
         GlStateManager.translate(this.shape8.rotationPointX * f5, this.shape8.rotationPointY * f5, this.shape8.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.shape8.offsetX, -this.shape8.offsetY, -this.shape8.offsetZ);
         GlStateManager.translate(-this.shape8.rotationPointX * f5, -this.shape8.rotationPointY * f5, -this.shape8.rotationPointZ * f5);
         this.shape8.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape4.offsetX, this.shape4.offsetY, this.shape4.offsetZ);
         GlStateManager.translate(this.shape4.rotationPointX * f5, this.shape4.rotationPointY * f5, this.shape4.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.shape4.offsetX, -this.shape4.offsetY, -this.shape4.offsetZ);
         GlStateManager.translate(-this.shape4.rotationPointX * f5, -this.shape4.rotationPointY * f5, -this.shape4.rotationPointZ * f5);
         this.shape4.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape6.offsetX, this.shape6.offsetY, this.shape6.offsetZ);
         GlStateManager.translate(this.shape6.rotationPointX * f5, this.shape6.rotationPointY * f5, this.shape6.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.shape6.offsetX, -this.shape6.offsetY, -this.shape6.offsetZ);
         GlStateManager.translate(-this.shape6.rotationPointX * f5, -this.shape6.rotationPointY * f5, -this.shape6.rotationPointZ * f5);
         this.shape6.render(f5);
         GlStateManager.popMatrix();
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         this.shape11.render(f5);
         this.shape12.render(f5);
         this.shape13.render(f5);
         GL11.glDisable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class Experiment9Model extends AbstractMobModel {
      public ModelRenderer bipedRightLeg;
      public ModelRenderer bipedLeftArm;
      public ModelRenderer bipedBody;
      public ModelRenderer bipedHead;
      public ModelRenderer bipedLeftLeg;
      public ModelRenderer bipedBody_1;
      public ModelRenderer bipedRightArm;
      public ModelRenderer bipedLeftbackleg;
      public ModelRenderer bipedRightbackleg;

      public Experiment9Model() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.bipedRightbackleg = new ModelRenderer(this, 46, 0);
         this.bipedRightbackleg.mirror = true;
         this.bipedRightbackleg.setRotationPoint(-2.0F, 21.2F, 4.0F);
         this.bipedRightbackleg.addBox(-2.0F, 0.0F, -2.0F, 3, 9, 2, 0.0F);
         this.setRotateAngle(this.bipedRightbackleg, 1.2747885F, 0.0F, 0.0F);
         this.bipedLeftArm = new ModelRenderer(this, 32, 0);
         this.bipedLeftArm.mirror = true;
         this.bipedLeftArm.setRotationPoint(3.0F, 15.0F, -2.0F);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 11, 3, 0.0F);
         this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
         this.bipedLeftLeg.mirror = true;
         this.bipedLeftLeg.setRotationPoint(2.9F, 21.0F, 2.1F);
         this.bipedLeftLeg.addBox(0.0F, 0.0F, -2.0F, 1, 3, 2, 0.0F);
         this.bipedRightLeg = new ModelRenderer(this, 0, 23);
         this.bipedRightLeg.setRotationPoint(-2.7F, 18.0F, 2.3F);
         this.bipedRightLeg.addBox(-1.3F, 0.0F, -1.0F, 1, 6, 1, 0.0F);
         this.bipedBody = new ModelRenderer(this, 5, 16);
         this.bipedBody.setRotationPoint(0.0F, 13.0F, -1.0F);
         this.bipedBody.addBox(-3.0F, 0.0F, -2.0F, 6, 10, 6, 0.0F);
         this.setRotateAngle(this.bipedBody, 0.22759093F, 0.0F, 0.0F);
         this.bipedBody_1 = new ModelRenderer(this, 29, 16);
         this.bipedBody_1.setRotationPoint(0.0F, 8.0F, -4.0F);
         this.bipedBody_1.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 5, 0.0F);
         this.setRotateAngle(this.bipedBody_1, 0.5462881F, 0.0F, 0.0F);
         this.bipedRightArm = new ModelRenderer(this, 32, 0);
         this.bipedRightArm.mirror = true;
         this.bipedRightArm.setRotationPoint(-3.0F, 15.0F, -2.0F);
         this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 11, 3, 0.0F);
         this.bipedLeftbackleg = new ModelRenderer(this, 46, 0);
         this.bipedLeftbackleg.mirror = true;
         this.bipedLeftbackleg.setRotationPoint(2.0F, 21.2F, 4.0F);
         this.bipedLeftbackleg.addBox(-1.0F, 0.0F, -2.0F, 3, 7, 2, 0.0F);
         this.setRotateAngle(this.bipedLeftbackleg, 1.2747885F, 0.0F, 0.0F);
         this.bipedHead = new ModelRenderer(this, 0, 0);
         this.bipedHead.setRotationPoint(0.0F, 9.6F, -4.0F);
         this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         if (an3 >= 80) {
            float an = 100 - an3;
            float angle = (float)(Math.sin(an / 6.5F) * -1.3F);
            this.bipedRightArm.rotateAngleX = angle;
            this.bipedLeftArm.rotateAngleX = angle;
         }

         this.bipedRightbackleg.render(f5);
         this.bipedLeftArm.render(f5);
         this.bipedLeftLeg.render(f5);
         this.bipedRightLeg.render(f5);
         this.bipedBody.render(f5);
         this.bipedBody_1.render(f5);
         this.bipedRightArm.render(f5);
         this.bipedLeftbackleg.render(f5);
         this.bipedHead.render(f5);
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.bipedHead.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.bipedHead.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
         this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
         this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
         this.bipedLeftbackleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount + 1.2747885F;
         this.bipedRightbackleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount + 1.2747885F;
         this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
         this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class GlowingSkeletonModel extends ModelBiped {
      public ModelRenderer shape1;
      public ModelRenderer glow;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4glow;

      public GlowingSkeletonModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.bipedLeftArm = new ModelRenderer(this, 40, 16);
         this.bipedLeftArm.mirror = true;
         this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, 0.0F);
         this.setRotateAngle(this.bipedLeftArm, 0.0F, 0.0F, -0.1F);
         this.shape1 = new ModelRenderer(this, 41, 0);
         this.shape1.setRotationPoint(-1.0F, 4.0F, -2.0F);
         this.shape1.addBox(-2.5F, 0.0F, -2.5F, 5, 7, 5, 0.0F);
         this.setRotateAngle(this.shape1, 0.4098033F, 0.0F, -0.27314404F);
         this.bipedHead = new ModelRenderer(this, 0, 0);
         this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
         this.bipedRightArm = new ModelRenderer(this, 40, 16);
         this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
         this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, 0.0F);
         this.setRotateAngle(this.bipedRightArm, 0.0F, 0.0F, 0.1F);
         this.bipedRightLeg = new ModelRenderer(this, 0, 16);
         this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.1F);
         this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
         this.shape3 = new ModelRenderer(this, 25, 0);
         this.shape3.setRotationPoint(0.0F, -4.0F, 0.0F);
         this.shape3.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F);
         this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
         this.bipedLeftLeg.mirror = true;
         this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.1F);
         this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
         this.bipedBody = new ModelRenderer(this, 16, 16);
         this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
         this.shape4glow = new ModelRendererGlow(this, 46, 13, 120, true);
         this.shape4glow.setRotationPoint(-1.5F, 2.0F, -1.52F);
         this.shape4glow.addBox(-1.0F, 0.0F, -1.0F, 5, 5, 0, 0.0F);
         this.glow = new ModelRendererGlow(this, 40, 30, 240, false);
         this.glow.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.glow.addBox(-3.0F, -5.0F, -4.1F, 6, 2, 0, 0.0F);
         this.shape2 = new ModelRenderer(this, 38, 0);
         this.shape2.setRotationPoint(0.0F, -2.0F, 0.0F);
         this.shape2.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
         this.shape1.addChild(this.shape3);
         this.shape1.addChild(this.shape4glow);
         this.bipedHead.addChild(this.glow);
         this.shape1.addChild(this.shape2);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         float shape1scale = 1.0F;
         int deathTime = 0;
         if (entity instanceof AbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            this.rightArmPose = mob.animations[2] > 80 ? ArmPose.BOW_AND_ARROW : ArmPose.EMPTY;
            if (mob.deathTime > 0) {
               deathTime = mob.deathTime;
               float k = mob.deathTime / 20.0F;
               k = MathHelper.clamp(f, 0.0F, 1.0F);
               k *= k;
               k *= k;
               float k1 = 1.0F + k * 0.4F;
               shape1scale = k1;
            }
         }

         this.bipedHead.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.bipedHead.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
         this.bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
         if (deathTime > 0 && deathTime / 5 % 2 == 0) {
            GlStateManager.disableTexture2D();
         }

         this.shape1.render(f5 * shape1scale);
         if (deathTime > 0 && deathTime / 5 % 2 == 0) {
            GlStateManager.enableTexture2D();
         }

         this.bipedLeftArm.render(f5);
         this.bipedHead.render(f5);
         this.bipedRightArm.render(f5);
         this.bipedRightLeg.render(f5);
         this.bipedLeftLeg.render(f5);
         this.bipedBody.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class KillBotModel extends AbstractMobModel {
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer shapehead;
      public ModelRenderer shape1;
      public ModelRenderer whell;
      public ModelRenderer armgun1;
      public ModelRenderer armblade1;
      public ModelRenderer head;
      public ModelRenderer armgun2;
      public ModelRenderer armgun3;
      public ModelRenderer armblade2;
      public ModelRenderer armblade3;
      public ModelRenderer armblade4;
      public ModelRenderer head2;

      public KillBotModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.armblade2 = new ModelRenderer(this, 12, 0);
         this.armblade2.setRotationPoint(-1.0F, 11.0F, -2.0F);
         this.armblade2.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.armblade2, (float) (-Math.PI / 2), 0.0F, 0.0F);
         this.whell = new ModelRenderer(this, 35, 26);
         this.whell.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.whell.addBox(-2.0F, -4.0F, -4.0F, 4, 8, 8, 0.0F);
         this.shape4 = new ModelRenderer(this, 32, 1);
         this.shape4.setRotationPoint(0.0F, 5.0F, 0.0F);
         this.shape4.addBox(-4.0F, -6.0F, -4.0F, 8, 6, 8, 0.0F);
         this.armblade4 = new ModelRenderer(this, 12, 11);
         this.armblade4.setRotationPoint(0.5F, 5.0F, 1.5F);
         this.armblade4.addBox(0.0F, 0.0F, -1.0F, 1, 5, 2, 0.0F);
         this.setRotateAngle(this.armblade4, -0.22759093F, (float) Math.PI, 0.0F);
         this.shapehead = new ModelRenderer(this, 0, 31);
         this.shapehead.setRotationPoint(0.0F, -1.0F, 0.0F);
         this.shapehead.addBox(-6.0F, -6.0F, -6.0F, 12, 6, 11, 0.0F);
         this.armgun2 = new ModelRenderer(this, 20, 0);
         this.armgun2.setRotationPoint(0.0F, 11.0F, -2.0F);
         this.armgun2.addBox(0.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F);
         this.setRotateAngle(this.armgun2, (float) (-Math.PI / 2), 0.0F, 0.0F);
         this.armgun1 = new ModelRenderer(this, 0, 0);
         this.armgun1.mirror = true;
         this.armgun1.setRotationPoint(6.0F, -4.0F, 0.0F);
         this.armgun1.addBox(0.0F, -2.0F, -2.0F, 2, 14, 4, 0.0F);
         this.shape3 = new ModelRenderer(this, 24, 18);
         this.shape3.setRotationPoint(0.0F, 11.0F, 0.0F);
         this.shape3.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 4, 0.0F);
         this.shape2 = new ModelRenderer(this, 8, 21);
         this.shape2.setRotationPoint(0.0F, 14.0F, 0.0F);
         this.shape2.addBox(-2.0F, -3.0F, -3.0F, 4, 3, 7, 0.0F);
         this.armblade1 = new ModelRenderer(this, 0, 0);
         this.armblade1.mirror = true;
         this.armblade1.setRotationPoint(-6.0F, -4.0F, 0.0F);
         this.armblade1.addBox(-2.0F, -2.0F, -2.0F, 2, 14, 4, 0.0F);
         this.setRotateAngle(this.armblade1, 0.3642502F, 0.0F, 0.0F);
         this.armblade3 = new ModelRenderer(this, 12, 11);
         this.armblade3.setRotationPoint(0.5F, 5.0F, -1.5F);
         this.armblade3.addBox(-1.0F, 0.0F, -1.0F, 1, 5, 2, 0.0F);
         this.setRotateAngle(this.armblade3, -0.22759093F, 0.0F, 0.0F);
         this.head = new ModelRenderer(this, 41, 15);
         this.head.setRotationPoint(0.0F, 1.0F, -6.0F);
         this.head.addBox(-2.0F, -3.0F, -1.0F, 4, 4, 2, 0.0F);
         this.head2 = new ModelRendererGlow(this, 43, 17, 180, true);
         this.head2.setRotationPoint(0.0F, 1.0F, -1.0F);
         this.head2.addBox(-1.0F, -3.0F, -1.0F, 2, 2, 1, 0.0F);
         this.shape1 = new ModelRenderer(this, 0, 20);
         this.shape1.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.shape1.addBox(2.0F, -9.0F, -1.0F, 2, 10, 2, 0.0F);
         this.armgun3 = new ModelRenderer(this, 28, 0);
         this.armgun3.setRotationPoint(0.0F, 9.5F, -2.0F);
         this.armgun3.addBox(-0.5F, -2.0F, 0.0F, 3, 6, 3, 0.0F);
         this.setRotateAngle(this.armgun3, (float) (-Math.PI / 2), 0.0F, 0.0F);
         this.armblade1.addChild(this.armblade2);
         this.shape1.addChild(this.whell);
         this.armblade2.addChild(this.armblade4);
         this.armgun1.addChild(this.armgun2);
         this.shapehead.addChild(this.armgun1);
         this.shapehead.addChild(this.armblade1);
         this.armblade2.addChild(this.armblade3);
         this.shapehead.addChild(this.head);
         this.shape2.addChild(this.shape1);
         this.armgun1.addChild(this.armgun3);
         this.head.addChild(this.head2);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         GlStateManager.pushMatrix();
         Vec3d vec = ModelsToxicomaniaMob.getVectorForRotation(0.0F, entity.rotationYaw + 90.0F);
         double angbias = GetMOP.getAngleBetweenVectors(entity.motionX, entity.motionZ, vec.x, vec.z);
         double angbias2 = Math.pow(1.006, -angbias * angbias / 2.0);
         double angbias3 = -Math.pow(1.006, -(angbias - 180.0) * (angbias - 180.0) / 2.0);
         GlStateManager.translate(0.0F, 1.4F, 0.0F);
         GlStateManager.rotate((float)(-10.0 * angbias2 + -10.0 * angbias3), 0.0F, 0.0F, 1.0F);
         GlStateManager.translate(0.0F, -1.4F, 0.0F);
         this.shapehead.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.shapehead.rotateAngleX = f4 * 0.007453292F;
         this.armgun1.rotateAngleX = f4 * 0.01F;
         this.head.rotateAngleX = f4 * 0.01F;
         if (an1 >= 85) {
            float an = 100 - an1;
            float angle = (float)(Math.sin(an * 0.21) * 0.75);
            this.armblade1.rotateAngleX = 0.3642502F - angle;
            this.armblade2.rotateAngleX = (float) (-Math.PI / 2) + angle;
            this.armblade3.rotateAngleX = -0.22759093F + angle / 2.0F;
            this.armblade4.rotateAngleX = this.armblade3.rotateAngleX;
         }

         if (isAbstractMob && !entity.isRiding()) {
            float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
            AbstractMob entitylb = (AbstractMob)entity;
            float qf = ModelsToxicomaniaMob.interpolateRotation(entitylb.prevRenderYawOffset, entitylb.renderYawOffset, partialTicks);
            float qf1 = ModelsToxicomaniaMob.interpolateRotation(entitylb.prevRotationYawHead, entitylb.rotationYawHead, partialTicks);
            float qf2 = qf1 - qf;
            float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            float f8 = entity.ticksExisted + partialTicks;
            double angvec = GetMOP.getAngleBetweenVectors(entity.motionX, entity.motionZ, 1.0, 0.0) + 90.0;
            this.shape2.rotateAngleY = (180.0F - qf) * (float) (Math.PI / 180.0) + (float)(angvec * (float) (Math.PI / 180.0));
            float whellangle = entitylb.var3 + (entitylb.var2 - entitylb.var3) * partialTicks;
            this.whell.rotateAngleX = whellangle;
         }

         this.shape4.render(f5);
         this.shapehead.render(f5);
         this.shape3.render(f5);
         this.shape2.render(f5);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class LumpModel extends AbstractMobModel {
      public ModelRenderer shape1;
      public ModelRenderer shape2;

      public LumpModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.shape2 = new ModelRenderer(this, 0, 32);
         this.shape2.setRotationPoint(0.0F, 16.0F, 0.0F);
         this.shape2.addBox(-4.0F, -5.0F, -4.0F, 8, 5, 8, 0.0F);
         this.shape1 = new ModelRenderer(this, 0, 0);
         this.shape1.setRotationPoint(0.0F, 16.0F, 0.0F);
         this.shape1.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         GlStateManager.pushMatrix();
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         if (an3 >= 90) {
            float an = 100 - an3;
            float a = MathHelper.sin(an * 0.33F) * 0.2F;
            GlStateManager.scale(1.0F - a, 1.0F + a, 1.0F - a);
         }

         light(70, true);
         this.shape2.render(f5);
         returnlight();
         this.shape1.render(f5);
         GL11.glDisable(3042);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class MosquitoModel extends ModelBase {
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer shape5;

      public MosquitoModel() {
         this.textureWidth = 16;
         this.textureHeight = 16;
         this.shape1 = new ModelRenderer(this, 0, 0);
         this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.shape1, -0.27314404F, 0.0F, 0.0F);
         this.shape2 = new ModelRenderer(this, 6, 0);
         this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape2.addBox(-0.5F, -1.0F, -1.0F, 1, 1, 1, 0.0F);
         this.shape5 = new ModelRenderer(this, 0, 3);
         this.shape5.setRotationPoint(0.0F, 0.1F, 0.0F);
         this.shape5.addBox(0.0F, 0.0F, -1.0F, 0, 5, 1, 0.0F);
         this.setRotateAngle(this.shape5, -0.91053826F, 0.0F, 0.0F);
         this.shape3 = new ModelRenderer(this, 0, 4);
         this.shape3.setRotationPoint(0.5F, -1.0F, 0.0F);
         this.shape3.addBox(0.0F, 0.0F, -1.0F, 4, 0, 2, 0.0F);
         this.shape4 = new ModelRenderer(this, 0, 6);
         this.shape4.setRotationPoint(-0.5F, -1.0F, 0.0F);
         this.shape4.addBox(-4.0F, 0.0F, -1.0F, 4, 0, 2, 0.0F);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0, 1.4, 0.0);
         float fl = (float)Math.sin(AnimationTimer.tick);
         this.shape3.rotateAngleZ = fl;
         this.shape4.rotateAngleZ = -fl;
         this.shape1.render(f5);
         this.shape2.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape5.offsetX, this.shape5.offsetY, this.shape5.offsetZ);
         GlStateManager.translate(this.shape5.rotationPointX * f5, this.shape5.rotationPointY * f5, this.shape5.rotationPointZ * f5);
         GlStateManager.scale(1.0, 0.2, 1.0);
         GlStateManager.translate(-this.shape5.offsetX, -this.shape5.offsetY, -this.shape5.offsetZ);
         GlStateManager.translate(-this.shape5.rotationPointX * f5, -this.shape5.rotationPointY * f5, -this.shape5.rotationPointZ * f5);
         this.shape5.render(f5);
         GlStateManager.popMatrix();
         this.shape3.render(f5);
         this.shape4.render(f5);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class MutantBeastModel extends AbstractMobModel {
      public ModelRenderer bipedBody;
      public ModelRenderer bipedLeftLeg;
      public ModelRenderer bipedRightLeg;
      public ModelRenderer bipedHead;
      public ModelRenderer bipedLeftArm;
      public ModelRenderer bipedRightArm;
      public ModelRenderer bipedBody2;
      public ModelRenderer bipedHead2;
      public ModelRenderer bipedLeftArm2;
      public ModelRenderer bipedRightArm2;

      public MutantBeastModel() {
         this.textureWidth = 64;
         this.textureHeight = 64;
         this.bipedLeftArm2 = new ModelRenderer(this, 26, 38);
         this.bipedLeftArm2.setRotationPoint(0.0F, 11.0F, 0.0F);
         this.bipedLeftArm2.addBox(0.5F, -2.0F, -2.0F, 4, 11, 4, 0.0F);
         this.setRotateAngle(this.bipedLeftArm2, -1.3658947F, 0.091106184F, 0.0F);
         this.bipedHead = new ModelRenderer(this, 32, 18);
         this.bipedHead.setRotationPoint(0.0F, -6.0F, -5.0F);
         this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 7, 8, 0.0F);
         this.bipedHead2 = new ModelRenderer(this, 38, 34);
         this.bipedHead2.setRotationPoint(0.0F, -1.0F, 0.0F);
         this.bipedHead2.addBox(-3.0F, 0.0F, -5.0F, 6, 2, 6, 0.0F);
         this.setRotateAngle(this.bipedHead2, 0.22759093F, 0.0F, 0.0F);
         this.bipedRightArm = new ModelRenderer(this, 42, 45);
         this.bipedRightArm.setRotationPoint(-8.0F, -5.0F, -1.0F);
         this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 5, 14, 5, 0.0F);
         this.setRotateAngle(this.bipedRightArm, 0.091106184F, -0.10000736F, 0.10000736F);
         this.bipedBody2 = new ModelRenderer(this, 18, 0);
         this.bipedBody2.setRotationPoint(0.0F, -7.0F, -2.0F);
         this.bipedBody2.addBox(-7.0F, 0.0F, -2.0F, 14, 9, 9, 0.0F);
         this.setRotateAngle(this.bipedBody2, 0.13665928F, 0.0F, 0.0F);
         this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
         this.bipedLeftLeg.mirror = true;
         this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
         this.bipedLeftLeg.addBox(-1.5F, 0.0F, -2.0F, 5, 12, 5, 0.0F);
         this.bipedBody = new ModelRenderer(this, 0, 47);
         this.bipedBody.setRotationPoint(0.0F, 1.0F, 0.5F);
         this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 11, 6, 0.0F);
         this.setRotateAngle(this.bipedBody, -0.091106184F, 0.0F, 0.0F);
         this.bipedRightArm2 = new ModelRenderer(this, 26, 38);
         this.bipedRightArm2.setRotationPoint(0.0F, 11.0F, 0.0F);
         this.bipedRightArm2.addBox(-2.5F, -2.0F, -2.0F, 4, 11, 4, 0.0F);
         this.setRotateAngle(this.bipedRightArm2, -1.3658947F, -0.091106184F, 0.0F);
         this.bipedRightLeg = new ModelRenderer(this, 0, 16);
         this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
         this.bipedRightLeg.addBox(-3.5F, 0.0F, -2.0F, 5, 12, 5, 0.0F);
         this.bipedLeftArm = new ModelRenderer(this, 42, 45);
         this.bipedLeftArm.mirror = true;
         this.bipedLeftArm.setRotationPoint(6.0F, -6.0F, 0.0F);
         this.bipedLeftArm.addBox(0.0F, 0.0F, -2.0F, 5, 12, 5, 0.0F);
         this.setRotateAngle(this.bipedLeftArm, 0.18203785F, 0.10000736F, -0.10000736F);
         this.bipedLeftArm.addChild(this.bipedLeftArm2);
         this.bipedHead.addChild(this.bipedHead2);
         this.bipedRightArm.addChild(this.bipedRightArm2);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float anim = (float)MathHelper.clamp(Math.sin(Math.max(0, an1 - 82) * 0.18) * 1.6, 0.0, 1.0);
         float dec = 1.0F - anim;
         this.bipedHead.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.bipedHead.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.bipedRightArm.rotateAngleX = -anim * 2.0F + MathHelper.cos(f * 0.6662F) * dec * f1;
         this.bipedRightArm.rotateAngleY = anim * -0.31053826F;
         this.bipedLeftArm.rotateAngleX = -anim * 2.0F + MathHelper.cos(f * 0.6662F + (float) Math.PI) * dec * f1;
         this.bipedLeftArm.rotateAngleY = anim * 0.31053826F;
         this.bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
         this.bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
         this.bipedHead2.rotateAngleX = 0.22759093F + MathHelper.sin(AnimationTimer.tick / 60.0F) * 0.1F;
         this.bipedHead.render(f5);
         this.bipedRightArm.render(f5);
         this.bipedBody2.render(f5);
         this.bipedLeftLeg.render(f5);
         this.bipedBody.render(f5);
         this.bipedRightLeg.render(f5);
         this.bipedLeftArm.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class MutantZombieModel extends ModelBiped {
      public ModelRenderer bipedHead2;

      public MutantZombieModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.bipedRightArm = new ModelRenderer(this, 40, 15);
         this.bipedRightArm.setRotationPoint(-4.0F, 3.0F, 0.0F);
         this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 3, 12, 5, 0.0F);
         this.setRotateAngle(this.bipedRightArm, (float) (-Math.PI * 4.0 / 9.0), -0.10000736F, 0.10000736F);
         this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
         this.bipedLeftLeg.mirror = true;
         this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
         this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
         this.bipedBody = new ModelRenderer(this, 16, 16);
         this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
         this.bipedHead = new ModelRenderer(this, 0, 0);
         this.bipedHead.setRotationPoint(0.0F, 0.5F, -1.0F);
         this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 7, 8, 0.0F);
         this.bipedHead2 = new ModelRenderer(this, 33, 0);
         this.bipedHead2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedHead2.addBox(-3.0F, -1.0F, -3.0F, 6, 5, 4, 0.0F);
         this.setRotateAngle(this.bipedHead2, -0.091106184F, 0.0F, 0.0F);
         this.bipedLeftArm = new ModelRenderer(this, 40, 15);
         this.bipedLeftArm.mirror = true;
         this.bipedLeftArm.setRotationPoint(5.0F, 3.0F, 0.0F);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 5, 0.0F);
         this.setRotateAngle(this.bipedLeftArm, (float) (-Math.PI * 4.0 / 9.0), 0.10000736F, -0.10000736F);
         this.bipedRightLeg = new ModelRenderer(this, 0, 16);
         this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
         this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
         this.bipedHead.addChild(this.bipedHead2);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         int an1 = 0;
         if (entity instanceof AbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            an1 = mob.animations[0];
         }

         this.bipedHead.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.bipedHead.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
         this.bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
         float anim = (float)Math.sin(Math.max(0, an1 - 90) * 0.315);
         this.bipedRightArm.offsetX = 0.06F;
         this.bipedRightArm.rotateAngleX = (float) (-Math.PI * 4.0 / 9.0) - anim;
         this.bipedLeftArm.rotateAngleX = (float) (-Math.PI * 4.0 / 9.0) - anim;
         this.bipedRightArm.render(f5);
         this.bipedLeftLeg.render(f5);
         this.bipedBody.render(f5);
         this.bipedHead.render(f5);
         this.bipedLeftArm.render(f5);
         this.bipedRightLeg.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class PoisonSpitterModel extends ModelBase {
      public ModelRenderer body1;
      public ModelRenderer body2;
      public ModelRenderer body3;
      public ModelRenderer head1;
      public ModelRenderer head2;
      public ModelRenderer head3;
      public ModelRenderer head4;

      public PoisonSpitterModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.body2 = new ModelRenderer(this, 32, 6);
         this.body2.setRotationPoint(0.0F, -2.5F, 0.0F);
         this.body2.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
         this.setRotateAngle(this.body2, 0.27314404F, 0.0F, 0.0F);
         this.head4 = new ModelRendererGlow(this, 32, 8, 130, true);
         this.head4.setRotationPoint(0.0F, -8.0F, 0.0F);
         this.head4.addBox(0.0F, 0.0F, -7.0F, 0, 10, 14, 0.0F);
         this.setRotateAngle(this.head4, 0.0F, (float) (Math.PI / 2), 0.0F);
         this.body1 = new ModelRenderer(this, 0, 21);
         this.body1.setRotationPoint(0.0F, 24.0F, 0.0F);
         this.body1.addBox(-4.0F, -3.0F, -4.0F, 8, 3, 8, 0.0F);
         this.head2 = new ModelRenderer(this, 32, 0);
         this.head2.setRotationPoint(0.0F, -8.05F, 0.0F);
         this.head2.addBox(-2.0F, -2.0F, -2.0F, 4, 2, 4, 0.0F);
         this.head1 = new ModelRenderer(this, 0, 0);
         this.head1.setRotationPoint(0.0F, -5.5F, 0.0F);
         this.head1.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
         this.setRotateAngle(this.head1, 0.68294734F, 0.0F, 0.0F);
         this.body3 = new ModelRenderer(this, 48, 9);
         this.body3.setRotationPoint(0.0F, -7.5F, 0.0F);
         this.body3.addBox(-1.5F, -6.0F, -1.5F, 3, 6, 3, 0.0F);
         this.setRotateAngle(this.body3, 0.5009095F, 0.0F, 0.0F);
         this.head3 = new ModelRendererGlow(this, 32, 8, 130, true);
         this.head3.setRotationPoint(0.0F, -8.0F, 0.0F);
         this.head3.addBox(0.0F, 0.0F, -7.0F, 0, 10, 14, 0.0F);
         this.body1.addChild(this.body2);
         this.head1.addChild(this.head4);
         this.head1.addChild(this.head2);
         this.body3.addChild(this.head1);
         this.body2.addChild(this.body3);
         this.head1.addChild(this.head3);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         f5 *= 1.7F;
         this.setRotationAngles(f, f1, f2, f3, f4 + 80.0F, f5, entity);
         this.head2.rotateAngleY = MathHelper.sin((AnimationTimer.tick + entity.getEntityId()) / 40.0F) / 2.0F;
         this.head1.rotateAngleY = MathHelper.sin((AnimationTimer.tick - entity.getEntityId()) / 57.35123F) / 9.0F;
         this.head1.rotateAngleZ = -this.head1.rotateAngleY;
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -1.05F, 0.0F);
         this.body1.render(f5);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.body2.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0) * 0.9F;
         this.body3.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0) * 0.1F;
         this.body2.rotateAngleX = headPitch * (float) (Math.PI / 180.0) / 3.0F;
         this.body3.rotateAngleX = headPitch * (float) (Math.PI / 180.0) / 6.0F;
         this.head1.rotateAngleX = headPitch * (float) (Math.PI / 180.0) / 2.25F;
      }
   }

   public static class RocketBotModel extends AbstractMobModel {
      public ModelRenderer whshape1;
      public ModelRenderer whshape2;
      public ModelRenderer whshape3;
      public ModelRenderer whshape4;
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer head;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer leftarm;
      public ModelRenderer rightarm;
      public ModelRenderer whell1;
      public ModelRenderer whell2;
      public ModelRenderer whell3;
      public ModelRenderer whell4;
      public ModelRenderer whell5;
      public ModelRenderer whell6;
      public ModelRenderer whshape5;
      public ModelRenderer whshape6;
      public ModelRenderer whshape7;
      public ModelRenderer head2;
      public ModelRenderer arm1;
      public ModelRenderer arm2;
      public ModelRenderer cannon1;
      public ModelRenderer cannon2;
      public ModelRenderer cannon3;
      public ModelRenderer cannon4;
      public ModelRenderer cannon5;

      public RocketBotModel() {
         this.textureWidth = 64;
         this.textureHeight = 64;
         this.whell6 = new ModelRenderer(this, 50, 22);
         this.whell6.setRotationPoint(-5.0F, 22.0F, 5.0F);
         this.whell6.addBox(-2.9F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.whshape2 = new ModelRenderer(this, 34, 30);
         this.whshape2.setRotationPoint(0.0F, 21.0F, 0.0F);
         this.whshape2.addBox(3.0F, -4.0F, -5.0F, 5, 1, 10, 0.0F);
         this.whshape1 = new ModelRenderer(this, 0, 23);
         this.whshape1.setRotationPoint(0.0F, 21.0F, 0.0F);
         this.whshape1.addBox(-5.0F, -3.0F, -6.0F, 10, 5, 12, 0.0F);
         this.leftarm = new ModelRenderer(this, 34, 18);
         this.leftarm.setRotationPoint(5.0F, 6.0F, -1.0F);
         this.leftarm.addBox(0.0F, -2.0F, -2.0F, 3, 7, 4, 0.0F);
         this.cannon3 = new ModelRenderer(this, 0, 17);
         this.cannon3.setRotationPoint(0.0F, 0.0F, -5.0F);
         this.cannon3.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 5, 0.0F);
         this.whell1 = new ModelRenderer(this, 50, 22);
         this.whell1.setRotationPoint(5.0F, 22.0F, -5.0F);
         this.whell1.addBox(-0.1F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.whell3 = new ModelRenderer(this, 50, 22);
         this.whell3.setRotationPoint(5.0F, 22.0F, 5.0F);
         this.whell3.addBox(-0.1F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.whshape6 = new ModelRenderer(this, 34, 30);
         this.whshape6.setRotationPoint(0.0F, 21.0F, 0.0F);
         this.whshape6.addBox(-8.0F, -4.0F, -5.0F, 5, 1, 10, 0.0F);
         this.cannon4 = new ModelRenderer(this, 0, 24);
         this.cannon4.setRotationPoint(0.0F, 0.0F, -5.0F);
         this.cannon4.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 5, 0.0F);
         this.whshape5 = new ModelRenderer(this, 44, 16);
         this.whshape5.setRotationPoint(0.0F, 21.0F, -2.1F);
         this.whshape5.addBox(-7.98F, -5.0F, -5.0F, 5, 1, 5, 0.0F);
         this.setRotateAngle(this.whshape5, 0.63739425F, 0.0F, 0.0F);
         this.cannon1 = new ModelRenderer(this, 0, 0);
         this.cannon1.setRotationPoint(-1.5F, 3.5F, -5.0F);
         this.cannon1.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 5, 0.0F);
         this.arm1 = new ModelRenderer(this, 30, 0);
         this.arm1.setRotationPoint(1.5F, 4.0F, -1.5F);
         this.arm1.addBox(-1.0F, -1.0F, -5.0F, 2, 2, 5, 0.0F);
         this.setRotateAngle(this.arm1, 0.4553564F, 0.0F, 0.0F);
         this.shape4 = new ModelRenderer(this, 36, 0);
         this.shape4.mirror = true;
         this.shape4.setRotationPoint(-7.0F, 2.0F, 2.0F);
         this.shape4.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 8, 0.0F);
         this.whell2 = new ModelRenderer(this, 50, 22);
         this.whell2.setRotationPoint(5.0F, 22.0F, 0.0F);
         this.whell2.addBox(0.0F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.arm2 = new ModelRenderer(this, 14, 5);
         this.arm2.setRotationPoint(0.0F, 0.0F, -4.5F);
         this.arm2.addBox(-2.0F, -2.0F, -4.5F, 4, 4, 4, 0.0F);
         this.setRotateAngle(this.arm2, 0.0F, 0.0F, 0.68294734F);
         this.rightarm = new ModelRenderer(this, 34, 18);
         this.rightarm.setRotationPoint(-5.0F, 6.0F, -1.0F);
         this.rightarm.addBox(-3.0F, -2.0F, -2.0F, 3, 7, 4, 0.0F);
         this.shape3 = new ModelRenderer(this, 36, 0);
         this.shape3.setRotationPoint(7.0F, 2.0F, 2.0F);
         this.shape3.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 8, 0.0F);
         this.whshape4 = new ModelRenderer(this, 44, 16);
         this.whshape4.setRotationPoint(0.0F, 21.0F, 2.1F);
         this.whshape4.addBox(-7.98F, -5.0F, -5.0F, 5, 1, 5, 0.0F);
         this.setRotateAngle(this.whshape4, 0.63739425F, (float) Math.PI, 0.0F);
         this.whell4 = new ModelRenderer(this, 50, 22);
         this.whell4.setRotationPoint(-5.0F, 22.0F, -5.0F);
         this.whell4.addBox(-2.9F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.head = new ModelRenderer(this, 40, 54);
         this.head.setRotationPoint(0.0F, 2.0F, 1.0F);
         this.head.addBox(-3.0F, -3.0F, -3.0F, 6, 4, 6, 0.0F);
         this.whshape3 = new ModelRenderer(this, 44, 16);
         this.whshape3.setRotationPoint(0.0F, 21.0F, -2.1F);
         this.whshape3.addBox(2.98F, -5.0F, -5.0F, 5, 1, 5, 0.0F);
         this.setRotateAngle(this.whshape3, 0.63739425F, 0.0F, 0.0F);
         this.shape2 = new ModelRenderer(this, 0, 44);
         this.shape2.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.shape2.addBox(-5.0F, -3.0F, -4.0F, 10, 10, 10, 0.0F);
         this.whshape7 = new ModelRenderer(this, 44, 16);
         this.whshape7.setRotationPoint(0.0F, 21.0F, 2.1F);
         this.whshape7.addBox(2.98F, -5.0F, -5.0F, 5, 1, 5, 0.0F);
         this.setRotateAngle(this.whshape7, 0.63739425F, (float) Math.PI, 0.0F);
         this.cannon2 = new ModelRenderer(this, 0, 9);
         this.cannon2.setRotationPoint(0.0F, 0.0F, -5.0F);
         this.cannon2.addBox(-1.5F, -1.5F, -2.0F, 3, 3, 5, 0.0F);
         this.shape1 = new ModelRenderer(this, 32, 41);
         this.shape1.setRotationPoint(0.0F, 16.0F, 0.0F);
         this.shape1.addBox(-4.0F, -3.0F, -3.0F, 8, 5, 8, 0.0F);
         this.whell5 = new ModelRenderer(this, 50, 22);
         this.whell5.setRotationPoint(-5.0F, 22.0F, 0.0F);
         this.whell5.addBox(-3.0F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.head2 = new ModelRendererGlow(this, 46, 61, 180, true);
         this.head2.setRotationPoint(0.0F, 0.0F, -2.1F);
         this.head2.addBox(-2.0F, -1.0F, -1.0F, 4, 1, 1, 0.0F);
         this.cannon5 = new ModelRenderer(this, 16, 13);
         this.cannon5.setRotationPoint(0.0F, 0.0F, -2.0F);
         this.cannon5.addBox(-2.5F, -3.0F, -3.0F, 5, 6, 3, 0.0F);
         this.cannon2.addChild(this.cannon3);
         this.cannon3.addChild(this.cannon4);
         this.rightarm.addChild(this.cannon1);
         this.leftarm.addChild(this.arm1);
         this.arm1.addChild(this.arm2);
         this.cannon1.addChild(this.cannon2);
         this.head.addChild(this.head2);
         this.cannon4.addChild(this.cannon5);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         f5 *= 1.3F;
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -0.45F, 0.0F);
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         float an = Math.min(100 - an1, 30);
         if (an > 0.0F && an < 30.0F) {
            an += pt;
         }

         float anim1 = GetMOP.getfromto(an, 0.0F, 8.0F) * 1.6F;
         float anim2 = (1.0F - GetMOP.getfromto(an, 0.0F, 10.0F)) * 0.41F;
         float anim3 = GetMOP.getfromto(an, 8.0F, 13.0F) * 1.6F;
         float anim4 = (1.0F - GetMOP.getfromto(an, 16.0F, 30.0F)) * 0.41F;
         this.rightarm.rotateAngleX = -anim1 + anim3;
         this.cannon2.offsetZ = 0.41F + anim2 - anim4;
         this.cannon3.offsetZ = 0.41F + anim2 - anim4;
         this.cannon4.offsetZ = 0.41F + anim2 - anim4;
         this.cannon5.offsetZ = 0.15F + anim2 / 2.5F - anim4 / 2.5F;
         if (isAbstractMob) {
            AbstractMob m = (AbstractMob)entity;
            float whellangle = m.var3 + (m.var2 - m.var3) * pt;
            this.whell1.rotateAngleX = whellangle;
            this.whell2.rotateAngleX = whellangle;
            this.whell3.rotateAngleX = whellangle;
            this.whell4.rotateAngleX = whellangle;
            this.whell5.rotateAngleX = whellangle;
            this.whell6.rotateAngleX = whellangle;
         }

         this.arm2.rotateAngleZ = 0.68294734F + MathHelper.sin(AnimationTimer.tick / 50.0F) * 0.5F;
         this.head.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.head.rotateAngleX = f4 * 0.015453292F;
         this.leftarm.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.shape3.rotateAngleY = f3 * 0.014453292F;
         this.shape3.rotateAngleX = f4 * 0.014453292F;
         this.shape4.rotateAngleY = this.shape3.rotateAngleY;
         this.shape4.rotateAngleX = this.shape3.rotateAngleX;
         this.whell6.render(f5);
         this.whshape2.render(f5);
         this.whshape1.render(f5);
         this.leftarm.render(f5);
         this.whell1.render(f5);
         this.whell3.render(f5);
         this.whshape6.render(f5);
         this.whshape5.render(f5);
         this.shape4.render(f5);
         this.whell2.render(f5);
         this.rightarm.render(f5);
         this.shape3.render(f5);
         this.whshape4.render(f5);
         this.whell4.render(f5);
         this.head.render(f5);
         this.whshape3.render(f5);
         this.shape2.render(f5);
         this.whshape7.render(f5);
         this.shape1.render(f5);
         this.whell5.render(f5);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class SpringerModel extends AbstractMobModel {
      public ModelRenderer head;
      public ModelRenderer leg1;
      public ModelRenderer leg2;
      public ModelRenderer lega1;
      public ModelRenderer legb1;
      public ModelRenderer lega2;
      public ModelRenderer legb2;

      public SpringerModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.legb2 = new ModelRenderer(this, 11, 21);
         this.legb2.setRotationPoint(-1.0F, 9.0F, 0.0F);
         this.legb2.addBox(-2.5F, 0.0F, -6.0F, 5, 1, 7, 0.0F);
         this.setRotateAngle(this.legb2, -0.31869712F, 0.0F, 0.0F);
         this.leg1 = new ModelRenderer(this, 0, 14);
         this.leg1.setRotationPoint(4.0F, 20.0F, -3.0F);
         this.leg1.addBox(0.0F, -6.0F, -1.0F, 2, 6, 3, 0.0F);
         this.setRotateAngle(this.leg1, -0.4098033F, 0.0F, 0.0F);
         this.lega1 = new ModelRenderer(this, 10, 14);
         this.lega1.setRotationPoint(1.0F, -6.0F, 0.0F);
         this.lega1.addBox(-0.5F, 0.0F, -1.0F, 1, 10, 2, 0.0F);
         this.setRotateAngle(this.lega1, 0.7740535F, 0.0F, 0.0F);
         this.leg2 = new ModelRenderer(this, 0, 14);
         this.leg2.setRotationPoint(-4.0F, 20.0F, -3.0F);
         this.leg2.addBox(-2.0F, -6.0F, -1.0F, 2, 6, 3, 0.0F);
         this.setRotateAngle(this.leg2, -0.4098033F, 0.0F, 0.0F);
         this.lega2 = new ModelRenderer(this, 10, 14);
         this.lega2.setRotationPoint(-1.0F, -6.0F, 0.0F);
         this.lega2.addBox(-0.5F, 0.0F, -1.0F, 1, 10, 2, 0.0F);
         this.setRotateAngle(this.lega2, 0.7740535F, 0.0F, 0.0F);
         this.head = new ModelRenderer(this, 0, 0);
         this.head.setRotationPoint(0.0F, 20.0F, -2.0F);
         this.head.addBox(-4.0F, -6.0F, -4.0F, 8, 6, 8, 0.0F);
         this.legb1 = new ModelRenderer(this, 11, 21);
         this.legb1.setRotationPoint(1.0F, 9.0F, 0.0F);
         this.legb1.addBox(-2.5F, 0.0F, -6.0F, 5, 1, 7, 0.0F);
         this.setRotateAngle(this.legb1, -0.31869712F, 0.0F, 0.0F);
         this.lega2.addChild(this.legb2);
         this.leg1.addChild(this.lega1);
         this.leg2.addChild(this.lega2);
         this.lega1.addChild(this.legb1);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         if (an4 >= 85) {
            float an = 100 - an4;
            float angle = (float)(Math.sin(an / 4.8F) * 2.0);
            angle = MathHelper.clamp(angle, 0.0F, 1.5F);
            this.leg1.rotateAngleX = -0.4098033F - angle;
            this.leg2.rotateAngleX = -0.4098033F - angle;
            this.lega1.rotateAngleX = 0.7740535F + angle;
            this.lega2.rotateAngleX = 0.7740535F + angle;
            this.legb1.rotateAngleX = -0.31869712F + angle;
            this.legb2.rotateAngleX = -0.31869712F + angle;
         } else {
            this.leg1.rotateAngleX = -0.4098033F;
            this.leg2.rotateAngleX = -0.4098033F;
            this.lega1.rotateAngleX = 0.7740535F;
            this.lega2.rotateAngleX = 0.7740535F;
            this.legb1.rotateAngleX = -0.31869712F;
            this.legb2.rotateAngleX = -0.31869712F;
         }

         this.leg1.render(f5);
         this.leg2.render(f5);
         this.head.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class TestTubeCreatureModel extends AbstractMobModel {
      public ModelRenderer body;
      public ModelRenderer head;
      public ModelRenderer shape;
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape6;
      public ModelRenderer shape9;
      public ModelRenderer shape4;
      public ModelRenderer shape5;
      public ModelRenderer shape7;
      public ModelRenderer shape8;

      public TestTubeCreatureModel() {
         this.textureWidth = 68;
         this.textureHeight = 48;
         this.shape6 = new ModelRenderer(this, 0, 0);
         this.shape6.setRotationPoint(0.2F, 2.0F, 0.0F);
         this.shape6.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 2, 0.0F);
         this.setRotateAngle(this.shape6, -1.548107F, 1.0927507F, 0.0F);
         this.shape1 = new ModelRenderer(this, 0, 31);
         this.shape1.setRotationPoint(0.2F, -3.0F, 0.0F);
         this.shape1.addBox(-1.0F, 1.0F, 1.5F, 2, 8, 2, 0.0F);
         this.setRotateAngle(this.shape1, -0.8651597F, -1.6845918F, -1.8668041F);
         this.shape8 = new ModelRenderer(this, 0, 0);
         this.shape8.setRotationPoint(0.2F, 8.0F, 0.0F);
         this.shape8.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 2, 0.0F);
         this.setRotateAngle(this.shape8, -0.95609134F, 0.045553092F, 0.0F);
         this.shape5 = new ModelRenderer(this, 0, 0);
         this.shape5.setRotationPoint(0.2F, 8.0F, 0.0F);
         this.shape5.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 2, 0.0F);
         this.setRotateAngle(this.shape5, -1.548107F, -0.31869712F, 0.0F);
         this.body = new ModelRenderer(this, 0, 0);
         this.body.setRotationPoint(0.0F, 13.5F, 0.0F);
         this.body.addBox(-8.5F, -7.0F, -8.5F, 17, 14, 17, 0.0F);
         this.shape2 = new ModelRenderer(this, 0, 31);
         this.shape2.setRotationPoint(0.2F, 0.0F, 0.0F);
         this.shape2.addBox(-1.0F, 4.0F, 0.5F, 2, 12, 2, 0.0F);
         this.setRotateAngle(this.shape2, 0.4098033F, -1.6845918F, -1.8668041F);
         this.shape = new ModelRenderer(this, 19, 37);
         this.shape.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.shape.addBox(-2.0F, 0.0F, -1.5F, 4, 6, 4, 0.0F);
         this.setRotateAngle(this.shape, 0.4098033F, 0.0F, 0.0F);
         this.shape4 = new ModelRenderer(this, 0, 0);
         this.shape4.setRotationPoint(0.2F, 8.0F, 0.0F);
         this.shape4.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 2, 0.0F);
         this.setRotateAngle(this.shape4, -1.3203416F, -0.68294734F, 0.0F);
         this.shape7 = new ModelRenderer(this, 0, 0);
         this.shape7.setRotationPoint(0.2F, 8.0F, 0.0F);
         this.shape7.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 2, 0.0F);
         this.setRotateAngle(this.shape7, -1.548107F, 1.0927507F, 0.0F);
         this.head = new ModelRenderer(this, 35, 32);
         this.head.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.head.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
         this.shape3 = new ModelRenderer(this, 8, 31);
         this.shape3.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.shape3.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 3, 0.0F);
         this.setRotateAngle(this.shape3, -1.3203416F, 0.0F, 0.0F);
         this.shape9 = new ModelRenderer(this, 0, 31);
         this.shape9.setRotationPoint(0.2F, 0.0F, 0.0F);
         this.shape9.addBox(-1.0F, 3.0F, -2.5F, 2, 12, 2, 0.0F);
         this.setRotateAngle(this.shape9, 1.2747885F, -0.3642502F, 0.22759093F);
         this.shape.addChild(this.shape6);
         this.head.addChild(this.shape1);
         this.shape7.addChild(this.shape8);
         this.shape4.addChild(this.shape5);
         this.head.addChild(this.shape2);
         this.shape3.addChild(this.shape4);
         this.shape6.addChild(this.shape7);
         this.shape.addChild(this.shape3);
         this.shape.addChild(this.shape9);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         this.head.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.head.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         float a = 0.0F;
         if (an3 >= 80) {
            float an = 100 - an3;
            a = (float)(Math.sin(an / 6.5F) * 0.28F);
         }

         float b1 = MathHelper.sin(AnimationTimer.tick / 35.0F) * 0.2F;
         float b2 = MathHelper.sin(AnimationTimer.tick / 43.0F) * 0.2F;
         this.shape7.rotateAngleX = -1.548107F + b1;
         this.shape8.rotateAngleX = -0.95609134F + b1;
         this.shape4.rotateAngleX = -1.3203416F + b2;
         this.shape5.rotateAngleX = -1.548107F + b2;
         this.shape.render(f5);
         this.head.render(f5);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.body.offsetX, this.body.offsetY, this.body.offsetZ);
         GlStateManager.translate(this.body.rotationPointX * f5, this.body.rotationPointY * f5, this.body.rotationPointZ * f5);
         GlStateManager.scale(1.5 - a, 1.5 + a, 1.5 - a);
         GlStateManager.translate(-this.body.offsetX, -this.body.offsetY, -this.body.offsetZ);
         GlStateManager.translate(-this.body.rotationPointX * f5, -this.body.rotationPointY * f5, -this.body.rotationPointZ * f5);
         this.body.render(f5);
         GlStateManager.popMatrix();
         GL11.glDisable(3042);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class TestTubeSubstanceModel extends AbstractMobModel {
      public ModelRenderer body;
      public ModelRenderer head;
      public ModelRenderer shape;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;

      public TestTubeSubstanceModel() {
         this.textureWidth = 64;
         this.textureHeight = 38;
         this.shape3 = new ModelRenderer(this, 0, 0);
         this.shape3.setRotationPoint(0.0F, 17.6F, 1.5F);
         this.shape3.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.shape3, -1.0927507F, -1.2747885F, 0.0F);
         this.body = new ModelRenderer(this, 0, 11);
         this.body.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.body.addBox(-7.5F, -6.0F, -7.5F, 15, 12, 15, 0.0F);
         this.head = new ModelRenderer(this, 19, 0);
         this.head.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.head.addBox(-2.5F, -4.0F, -2.5F, 5, 5, 5, 0.0F);
         this.shape2 = new ModelRenderer(this, 11, 0);
         this.shape2.setRotationPoint(0.0F, 19.5F, 3.0F);
         this.shape2.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.shape2, -1.0927507F, 0.0F, 0.0F);
         this.shape4 = new ModelRenderer(this, 6, 0);
         this.shape4.setRotationPoint(0.0F, 17.6F, 1.5F);
         this.shape4.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.shape4, 1.0927507F, -1.9123572F, 0.0F);
         this.shape = new ModelRenderer(this, 0, 10);
         this.shape.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.shape.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
         this.setRotateAngle(this.shape, 0.4098033F, 0.0F, 0.0F);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float a = 0.0F;
         if (an3 >= 80) {
            float an = 100 - an3;
            a = (float)(Math.sin(an / 6.5F) * 0.24F);
         }

         this.shape3.render(f5);
         this.head.render(f5);
         this.shape2.render(f5);
         this.shape4.render(f5);
         this.shape.render(f5);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.body.offsetX, this.body.offsetY, this.body.offsetZ);
         GlStateManager.translate(this.body.rotationPointX * f5, this.body.rotationPointY * f5, this.body.rotationPointZ * f5);
         GlStateManager.scale(1.5 - a, 1.5 + a, 1.5 - a);
         GlStateManager.translate(-this.body.offsetX, -this.body.offsetY, -this.body.offsetZ);
         GlStateManager.translate(-this.body.rotationPointX * f5, -this.body.rotationPointY * f5, -this.body.rotationPointZ * f5);
         this.body.render(f5);
         GlStateManager.popMatrix();
         GL11.glDisable(3042);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class TurretModel extends AbstractMobModel {
      public ModelRenderer shape5;
      public ModelRenderer shape6;
      public ModelRenderer shape8;
      public ModelRenderer shape7;
      public ModelRenderer base;
      public ModelRenderer body;
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape4;
      public ModelRenderer shape3;
      public ModelRenderer shapes1;
      public ModelRenderer shapes2;
      public ModelRenderer gun1;
      public ModelRenderer gun2;
      public ModelRenderer gun3;
      public ModelRenderer gun4;
      public ModelRenderer gun5;
      public ModelRenderer gun6;

      public TurretModel() {
         this.textureWidth = 48;
         this.textureHeight = 32;
         this.shape8 = new ModelRenderer(this, 12, 0);
         this.shape8.setRotationPoint(0.0F, 20.0F, 0.0F);
         this.shape8.addBox(2.0F, 0.0F, 1.0F, 2, 2, 4, 0.0F);
         this.shape1 = new ModelRenderer(this, 0, 0);
         this.shape1.mirror = true;
         this.shape1.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shape1.addBox(3.0F, 0.0F, -6.0F, 3, 3, 3, 0.0F);
         this.shapes1 = new ModelRenderer(this, 0, 18);
         this.shapes1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shapes1.addBox(2.0F, -6.0F, -0.5F, 1, 9, 3, 0.0F);
         this.body = new ModelRenderer(this, 28, 12);
         this.body.setRotationPoint(0.0F, 16.0F, 0.0F);
         this.body.addBox(-2.0F, 0.0F, -3.0F, 4, 3, 6, 0.0F);
         this.gun2 = new ModelRendererGlow(this, 1, 23, 40, true);
         this.gun2.setRotationPoint(0.0F, -3.0F, 0.0F);
         this.gun2.addBox(-3.0F, -2.0F, -4.0F, 6, 2, 7, 0.0F);
         this.gun1 = new ModelRendererGlow(this, 27, 21, 40, true);
         this.gun1.setRotationPoint(0.0F, -4.5F, 1.0F);
         this.gun1.addBox(-2.0F, -3.0F, -4.0F, 4, 5, 6, 0.0F);
         this.shape5 = new ModelRenderer(this, 12, 0);
         this.shape5.setRotationPoint(0.0F, 20.0F, 0.0F);
         this.shape5.addBox(2.0F, 0.0F, -5.0F, 2, 2, 4, 0.0F);
         this.gun3 = new ModelRenderer(this, 27, 0);
         this.gun3.setRotationPoint(0.0F, -2.0F, 2.0F);
         this.gun3.addBox(-1.5F, -2.0F, 0.0F, 3, 5, 3, 0.0F);
         this.shape6 = new ModelRenderer(this, 12, 0);
         this.shape6.mirror = true;
         this.shape6.setRotationPoint(0.0F, 20.0F, 0.0F);
         this.shape6.addBox(-4.0F, 0.0F, -5.0F, 2, 2, 4, 0.0F);
         this.base = new ModelRenderer(this, 8, 15);
         this.base.setRotationPoint(0.0F, 19.0F, 0.0F);
         this.base.addBox(-3.0F, 0.0F, -2.0F, 6, 4, 4, 0.0F);
         this.shape2 = new ModelRenderer(this, 0, 0);
         this.shape2.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shape2.addBox(-6.0F, 0.0F, -6.0F, 3, 3, 3, 0.0F);
         this.gun5 = new ModelRendererGlow(this, 0, 12, 200, true);
         this.gun5.setRotationPoint(0.0F, -3.0F, -4.0F);
         this.gun5.addBox(-0.5F, -1.0F, -5.0F, 1, 1, 5, 0.0F);
         this.shape4 = new ModelRenderer(this, 0, 0);
         this.shape4.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shape4.addBox(3.0F, 0.0F, 3.0F, 3, 3, 3, 0.0F);
         this.gun4 = new ModelRenderer(this, 16, 6);
         this.gun4.setRotationPoint(0.0F, -1.0F, -4.0F);
         this.gun4.addBox(-1.0F, -1.0F, -7.0F, 2, 2, 7, 0.0F);
         this.gun6 = new ModelRenderer(this, 36, 5);
         this.gun6.setRotationPoint(0.0F, -1.0F, -5.0F);
         this.gun6.addBox(-1.5F, -2.5F, -3.0F, 3, 4, 3, 0.0F);
         this.shape7 = new ModelRenderer(this, 12, 0);
         this.shape7.mirror = true;
         this.shape7.setRotationPoint(0.0F, 20.0F, 0.0F);
         this.shape7.addBox(-4.0F, 0.0F, 1.0F, 2, 2, 4, 0.0F);
         this.shape3 = new ModelRenderer(this, 0, 0);
         this.shape3.mirror = true;
         this.shape3.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shape3.addBox(-6.0F, 0.0F, 3.0F, 3, 3, 3, 0.0F);
         this.shapes2 = new ModelRenderer(this, 0, 18);
         this.shapes2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shapes2.addBox(-3.0F, -6.0F, -0.5F, 1, 9, 3, 0.0F);
         this.base.addChild(this.shape1);
         this.body.addChild(this.shapes1);
         this.gun1.addChild(this.gun2);
         this.shapes2.addChild(this.gun1);
         this.gun1.addChild(this.gun3);
         this.base.addChild(this.shape2);
         this.gun1.addChild(this.gun5);
         this.base.addChild(this.shape4);
         this.gun1.addChild(this.gun4);
         this.gun1.addChild(this.gun6);
         this.base.addChild(this.shape3);
         this.body.addChild(this.shapes2);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
         float offMultipl = 0.065F;
         float building = 0.0F;
         if (isAbstractMob && !entity.isRiding()) {
            AbstractMob entitylb = (AbstractMob)entity;
            float qf = ModelsToxicomaniaMob.interpolateRotation(entitylb.prevRenderYawOffset, entitylb.renderYawOffset, partialTicks);
            this.base.rotateAngleY = (180.0F - qf) * (float) (Math.PI / 180.0) + entitylb.var4;
            this.shape5.rotateAngleY = this.base.rotateAngleY;
            this.shape6.rotateAngleY = this.base.rotateAngleY;
            this.shape7.rotateAngleY = this.base.rotateAngleY;
            this.shape8.rotateAngleY = this.base.rotateAngleY;
            building = MathHelper.clamp(entitylb.var3 + (entitylb.var2 - entitylb.var3) * partialTicks, 0.0F, 1.0F);
         }

         float unbuilding = 1.0F - building;
         this.gun4.offsetZ = GetMOP.getfromto(unbuilding, 0.0F, 0.2F) * 6.0F * offMultipl;
         this.gun5.offsetZ = GetMOP.getfromto(unbuilding, 0.1F, 0.3F) * 4.0F * offMultipl;
         this.gun4.offsetZ = GetMOP.getfromto(unbuilding, 0.0F, 0.1F) * 5.0F * offMultipl;
         this.gun3.rotateAngleX = -1.570796F * GetMOP.getfromto(unbuilding, 0.3F, 0.5F);
         this.gun1.rotateAngleX = 1.570796F * GetMOP.getfromto(unbuilding, 0.5F, 0.8F);
         this.shapes2.rotateAngleX = -1.570796F * GetMOP.getfromto(unbuilding, 0.8F, 1.0F);
         this.shapes1.rotateAngleX = this.shapes2.rotateAngleX;
         this.shape1.offsetX = -(GetMOP.getfromto(unbuilding, 0.7F, 0.9F) * 3.0F) * offMultipl;
         this.shape2.offsetX = -this.shape1.offsetX;
         this.shape3.offsetX = -this.shape1.offsetX;
         this.shape4.offsetX = this.shape1.offsetX;
         if (an1 >= 94) {
            float an = 100 - an1 + Minecraft.getMinecraft().getRenderPartialTicks();
            float aoff = (float)(Math.sin(an * 0.45) * 2.0);
            this.gun4.offsetZ += aoff * offMultipl;
         }

         this.body.rotateAngleY = f3 * (float) (Math.PI / 180.0) * building + this.base.rotateAngleY * unbuilding;
         this.gun1.rotateAngleX += f4 * (float) (Math.PI / 180.0);
         this.shape5.render(f5);
         this.body.render(f5);
         this.shape7.render(f5);
         this.shape8.render(f5);
         this.shape6.render(f5);
         this.base.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class VineChopsModel extends AbstractMobModel {
      public ModelRenderer body1;
      public ModelRenderer body2;
      public ModelRenderer leg2;
      public ModelRenderer leg1;
      public ModelRenderer leg3;
      public ModelRenderer body3;
      public ModelRenderer body4;
      public ModelRenderer decor;
      public ModelRenderer head;
      public ModelRenderer head2;
      public ModelRenderer decor2;

      public VineChopsModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.leg1 = new ModelRenderer(this, 0, 18);
         this.leg1.setRotationPoint(3.0F, 14.0F, -2.0F);
         this.leg1.addBox(-2.0F, 0.0F, -3.0F, 4, 10, 4, 0.0F);
         this.setRotateAngle(this.leg1, -0.091106184F, -0.4098033F, -0.22759093F);
         this.body2 = new ModelRenderer(this, 0, 0);
         this.body2.setRotationPoint(0.0F, 7.0F, 3.0F);
         this.body2.addBox(-2.5F, -10.0F, -3.0F, 5, 12, 5, 0.0F);
         this.setRotateAngle(this.body2, -0.59184116F, 0.0F, 0.0F);
         this.leg2 = new ModelRenderer(this, 0, 18);
         this.leg2.setRotationPoint(-3.0F, 14.0F, -2.0F);
         this.leg2.addBox(-2.0F, 0.0F, -3.0F, 4, 10, 4, 0.0F);
         this.setRotateAngle(this.leg2, -0.091106184F, 0.3642502F, 0.3642502F);
         this.body3 = new ModelRenderer(this, 20, 0);
         this.body3.setRotationPoint(0.0F, -9.4F, 2.0F);
         this.body3.addBox(-3.0F, -9.0F, -3.0F, 6, 9, 6, 0.0F);
         this.setRotateAngle(this.body3, 0.95609134F, 0.0F, 0.0F);
         this.head2 = new ModelRenderer(this, 16, 20);
         this.head2.setRotationPoint(0.0F, -2.0F, 1.0F);
         this.head2.addBox(-4.0F, 0.0F, -7.0F, 8, 4, 8, 0.0F);
         this.setRotateAngle(this.head2, 0.4098033F, 0.0F, 0.0F);
         this.decor = new ModelRenderer(this, 50, -6);
         this.decor.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.decor.addBox(-0.5F, -7.0F, -1.0F, 1, 10, 6, 0.0F);
         this.setRotateAngle(this.decor, -0.31869712F, 0.0F, 0.0F);
         this.body4 = new ModelRenderer(this, 0, 0);
         this.body4.setRotationPoint(0.0F, -9.0F, 1.0F);
         this.body4.addBox(-2.5F, -10.0F, -3.0F, 5, 11, 5, 0.0F);
         this.setRotateAngle(this.body4, 1.6390387F, 0.0F, 0.0F);
         this.leg3 = new ModelRenderer(this, 0, 18);
         this.leg3.setRotationPoint(0.0F, 14.0F, 3.0F);
         this.leg3.addBox(-2.0F, 0.0F, -1.0F, 4, 10, 4, 0.0F);
         this.setRotateAngle(this.leg3, 0.27314404F, -0.045553092F, 0.0F);
         this.head = new ModelRenderer(this, 16, 16);
         this.head.setRotationPoint(0.0F, -12.0F, -2.0F);
         this.head.addBox(-4.0F, -7.0F, -6.0F, 8, 5, 8, 0.0F);
         this.setRotateAngle(this.head, -2.003289F, 0.0F, 0.0F);
         this.decor2 = new ModelRenderer(this, 40, 11);
         this.decor2.setRotationPoint(1.0F, 8.0F, 1.0F);
         this.decor2.addBox(-4.0F, -3.7F, -8.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.decor2, -0.27314404F, 0.0F, 0.0F);
         this.body1 = new ModelRenderer(this, 21, 1);
         this.body1.setRotationPoint(0.0F, 16.0F, 1.0F);
         this.body1.addBox(-3.0F, -8.0F, -3.0F, 6, 8, 5, 0.0F);
         this.setRotateAngle(this.body1, -0.091106184F, 0.0F, 0.0F);
         this.body2.addChild(this.body3);
         this.head.addChild(this.head2);
         this.body4.addChild(this.decor);
         this.body3.addChild(this.body4);
         this.body4.addChild(this.head);
         this.head2.addChild(this.decor2);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         if (an1 >= 80) {
            float an = 100 - an1;
            float angle = (float)(Math.sin(an / 6.5F) * 0.5);
            this.body2.rotateAngleX = -0.59184116F + angle;
            this.body3.rotateAngleX = 0.95609134F + angle;
            this.body4.rotateAngleX = 1.6390387F - angle;
            this.head.rotateAngleX = -2.003289F - angle;
            this.head2.rotateAngleX = 0.4098033F - angle;
         } else if (an3 >= 80) {
            float an = 100 - an3;
            float angle = (float)(Math.sin(an / 6.5F) * 0.5);
            this.body2.rotateAngleX = -0.59184116F + angle;
            this.body3.rotateAngleX = 0.95609134F + angle;
            this.body4.rotateAngleX = 1.6390387F - angle;
            this.head.rotateAngleX = -2.003289F - angle;
            this.head2.rotateAngleX = 0.4098033F - angle;
         } else {
            this.body2.rotateAngleX = -0.59184116F;
            this.body3.rotateAngleX = 0.95609134F;
            this.body4.rotateAngleX = 1.6390387F;
            this.head.rotateAngleX = -2.003289F;
            this.head2.rotateAngleX = 0.4098033F;
         }

         this.leg1.render(f5);
         this.body2.render(f5);
         this.leg2.render(f5);
         this.leg3.render(f5);
         this.body1.render(f5);
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.head.rotateAngleZ = netHeadYaw * (float) (Math.PI / 180.0);
         this.head.rotateAngleX = headPitch * (float) (Math.PI / 180.0) - 2.003289F;
         this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount - 0.091106184F;
         this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount - 0.091106184F;
         this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount + 0.27314404F;
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }
}
