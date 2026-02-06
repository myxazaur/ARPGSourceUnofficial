package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.renders.ModelRendererGlow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class HazardSuitModel extends ModelBiped {
   public int lightt = 180;
   public ModelRenderer chest;
   public ModelRenderer rightarm;
   public ModelRenderer leftarm;
   public ModelRenderer helm;
   public ModelRenderer leftleg;
   public ModelRenderer rightleg;
   public ModelRenderer rightboot;
   public ModelRenderer leftboot;
   public ModelRenderer pipe;
   public ModelRenderer pipe_1;
   public ModelRenderer pipe_2;
   public ModelRenderer pipe_3;
   public ModelRenderer pipes;
   public ModelRenderer pipesbelt;
   public ModelRenderer back;
   public ModelRenderer pipeGLOW;
   public ModelRenderer helmGLOW;
   public ModelRenderer pipe_4;

   public HazardSuitModel() {
      this.textureWidth = 64;
      this.textureHeight = 48;
      this.pipesbelt = new ModelRenderer(this, 32, 38);
      this.pipesbelt.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.pipesbelt.addBox(-5.0F, 7.5F, -1.0F, 10, 4, 6, 0.0F);
      this.leftboot = new ModelRenderer(this, 40, 22);
      this.leftboot.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.leftboot.addBox(-1.8F, 6.0F, -2.0F, 4, 6, 4, 0.4F);
      this.pipe_4 = new ModelRenderer(this, 36, 12);
      this.pipe_4.setRotationPoint(3.0F, -5.0F, 4.0F);
      this.pipe_4.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
      this.back = new ModelRenderer(this, 30, 0);
      this.back.setRotationPoint(0.0F, 0.0F, 2.0F);
      this.back.addBox(-2.5F, -0.5F, 0.5F, 5, 8, 2, 0.0F);
      this.pipes = new ModelRenderer(this, 48, 33);
      this.pipes.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.pipes.addBox(-1.0F, 1.0F, -1.5F, 5, 5, 0, 0.0F);
      this.pipe = new ModelRenderer(this, 48, 0);
      this.pipe.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.pipe.addBox(-3.0F, 1.0F, -2.0F, 2, 5, 1, 0.0F);
      this.leftleg = new ModelRenderer(this, 0, 14);
      this.leftleg.mirror = true;
      this.leftleg.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.leftleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.2F);
      this.helm = new ModelRenderer(this, 0, 0);
      this.helm.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.helm.addBox(-4.5F, -7.3F, -2.5F, 9, 7, 6, 0.4F);
      this.pipe_2 = new ModelRenderer(this, 54, 3);
      this.pipe_2.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.pipe_2.addBox(-1.0F, 6.0F, -2.0F, 2, 1, 1, 0.0F);
      this.rightboot = new ModelRenderer(this, 40, 22);
      this.rightboot.mirror = true;
      this.rightboot.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.rightboot.addBox(-2.2F, 6.0F, -2.0F, 4, 6, 4, 0.4F);
      this.pipe_1 = new ModelRenderer(this, 54, 0);
      this.pipe_1.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.pipe_1.addBox(-1.0F, 4.0F, -2.0F, 4, 2, 1, 0.0F);
      this.helmGLOW = new ModelRendererGlow(this, 0, 30, this.lightt, false);
      this.helmGLOW.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.helmGLOW.addBox(-4.0F, -9.0F, -2.9F, 8, 10, 8, 0.0F);
      this.rightarm = new ModelRenderer(this, 40, 6);
      this.rightarm.mirror = true;
      this.rightarm.setRotationPoint(-5.0F, 2.0F, 0.0F);
      this.rightarm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.4F);
      this.leftarm = new ModelRenderer(this, 40, 6);
      this.leftarm.setRotationPoint(5.0F, 2.0F, 0.0F);
      this.leftarm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.4F);
      this.rightleg = new ModelRenderer(this, 0, 14);
      this.rightleg.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.rightleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.2F);
      this.pipe_3 = new ModelRenderer(this, 30, 31);
      this.pipe_3.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.pipe_3.addBox(-1.5F, 7.0F, -2.5F, 3, 3, 2, 0.0F);
      this.chest = new ModelRenderer(this, 16, 14);
      this.chest.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chest.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.5F);
      this.pipeGLOW = new ModelRenderer(this, 25, 31);
      this.pipeGLOW.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.pipeGLOW.addBox(-0.5F, 8.0F, -4.7F, 1, 1, 1, 0.0F);
      this.chest.addChild(this.pipesbelt);
      this.helm.addChild(this.pipe_4);
      this.chest.addChild(this.back);
      this.chest.addChild(this.pipes);
      this.chest.addChild(this.pipe);
      this.chest.addChild(this.pipe_2);
      this.chest.addChild(this.pipe_1);
      this.helm.addChild(this.helmGLOW);
      this.chest.addChild(this.pipe_3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      if (entity.isSneaking()) {
         GlStateManager.translate(0.0F, 0.2F, 0.0F);
      }

      AbstractMobModel.copyModel(this.chest, this.pipeGLOW);
      this.rightarm.render(f5);
      this.leftboot.render(f5);
      this.chest.render(f5);
      this.rightboot.render(f5);
      this.rightleg.render(f5);
      this.leftleg.render(f5);
      this.leftarm.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.helm.offsetX, this.helm.offsetY, this.helm.offsetZ);
      GlStateManager.translate(this.helm.rotationPointX * f5, this.helm.rotationPointY * f5, this.helm.rotationPointZ * f5);
      GlStateManager.scale(1.08, 1.08, 1.08);
      GlStateManager.translate(-this.helm.offsetX, -this.helm.offsetY, -this.helm.offsetZ);
      GlStateManager.translate(-this.helm.rotationPointX * f5, -this.helm.rotationPointY * f5, -this.helm.rotationPointZ * f5);
      this.helm.render(f5);
      GlStateManager.popMatrix();
      AbstractMobModel.light((int)(135.0F + 45.0F * MathHelper.sin(AnimationTimer.tick)), false);
      this.pipeGLOW.render(f5);
      AbstractMobModel.returnlight();
      GlStateManager.popMatrix();
   }

   public void setModelAttributes(ModelBase model) {
      super.setModelAttributes(model);
      if (model instanceof ModelBiped) {
         ModelBiped modelbiped = (ModelBiped)model;
         this.rightarm.rotateAngleX = modelbiped.bipedRightArm.rotateAngleX;
         this.rightarm.rotateAngleY = modelbiped.bipedRightArm.rotateAngleY;
         this.rightarm.rotateAngleZ = modelbiped.bipedRightArm.rotateAngleZ;
         this.leftarm.rotateAngleX = modelbiped.bipedLeftArm.rotateAngleX;
         this.leftarm.rotateAngleY = modelbiped.bipedLeftArm.rotateAngleY;
         this.leftarm.rotateAngleZ = modelbiped.bipedLeftArm.rotateAngleZ;
         this.helm.rotateAngleX = modelbiped.bipedHead.rotateAngleX;
         this.helm.rotateAngleY = modelbiped.bipedHead.rotateAngleY;
         this.helm.rotateAngleZ = modelbiped.bipedHead.rotateAngleZ;
         this.chest.rotateAngleX = modelbiped.bipedBody.rotateAngleX;
         this.chest.rotateAngleY = modelbiped.bipedBody.rotateAngleY;
         this.chest.rotateAngleZ = modelbiped.bipedBody.rotateAngleZ;
         this.rightleg.rotateAngleX = modelbiped.bipedRightLeg.rotateAngleX;
         this.rightleg.rotateAngleY = modelbiped.bipedRightLeg.rotateAngleY;
         this.rightleg.rotateAngleZ = modelbiped.bipedRightLeg.rotateAngleZ;
         this.leftleg.rotateAngleX = modelbiped.bipedLeftLeg.rotateAngleX;
         this.leftleg.rotateAngleY = modelbiped.bipedLeftLeg.rotateAngleY;
         this.leftleg.rotateAngleZ = modelbiped.bipedLeftLeg.rotateAngleZ;
         this.rightboot.rotateAngleX = modelbiped.bipedRightLeg.rotateAngleX;
         this.rightboot.rotateAngleY = modelbiped.bipedRightLeg.rotateAngleY;
         this.rightboot.rotateAngleZ = modelbiped.bipedRightLeg.rotateAngleZ;
         this.leftboot.rotateAngleX = modelbiped.bipedLeftLeg.rotateAngleX;
         this.leftboot.rotateAngleY = modelbiped.bipedLeftLeg.rotateAngleY;
         this.leftboot.rotateAngleZ = modelbiped.bipedLeftLeg.rotateAngleZ;
         this.rightarm.rotationPointX = modelbiped.bipedRightArm.rotationPointX;
         this.rightarm.rotationPointY = modelbiped.bipedRightArm.rotationPointY;
         this.rightarm.rotationPointZ = modelbiped.bipedRightArm.rotationPointZ;
         this.leftarm.rotationPointX = modelbiped.bipedLeftArm.rotationPointX;
         this.leftarm.rotationPointY = modelbiped.bipedLeftArm.rotationPointY;
         this.leftarm.rotationPointZ = modelbiped.bipedLeftArm.rotationPointZ;
         this.helm.rotationPointX = modelbiped.bipedHead.rotationPointX;
         this.helm.rotationPointY = modelbiped.bipedHead.rotationPointY;
         this.helm.rotationPointZ = modelbiped.bipedHead.rotationPointZ;
         this.chest.rotationPointX = modelbiped.bipedBody.rotationPointX;
         this.chest.rotationPointY = modelbiped.bipedBody.rotationPointY;
         this.chest.rotationPointZ = modelbiped.bipedBody.rotationPointZ;
         this.rightleg.rotationPointX = modelbiped.bipedRightLeg.rotationPointX;
         this.rightleg.rotationPointY = modelbiped.bipedRightLeg.rotationPointY;
         this.rightleg.rotationPointZ = modelbiped.bipedRightLeg.rotationPointZ;
         this.leftleg.rotationPointX = modelbiped.bipedLeftLeg.rotationPointX;
         this.leftleg.rotationPointY = modelbiped.bipedLeftLeg.rotationPointY;
         this.leftleg.rotationPointZ = modelbiped.bipedLeftLeg.rotationPointZ;
         this.rightboot.rotationPointX = modelbiped.bipedRightLeg.rotationPointX;
         this.rightboot.rotationPointY = modelbiped.bipedRightLeg.rotationPointY;
         this.rightboot.rotationPointZ = modelbiped.bipedRightLeg.rotationPointZ;
         this.leftboot.rotationPointX = modelbiped.bipedLeftLeg.rotationPointX;
         this.leftboot.rotationPointY = modelbiped.bipedLeftLeg.rotationPointY;
         this.leftboot.rotationPointZ = modelbiped.bipedLeftLeg.rotationPointZ;
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
