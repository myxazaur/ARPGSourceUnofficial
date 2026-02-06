package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class AdamantiumArmorModel extends ModelBiped {
   public ModelRenderer chest;
   public ModelRenderer helm;
   public ModelRenderer leftarm;
   public ModelRenderer rightarm;
   public ModelRenderer rightleg;
   public ModelRenderer leftleg;
   public ModelRenderer rightboot;
   public ModelRenderer leftboot;
   public ModelRenderer helm1;
   public ModelRenderer helm2;
   public ModelRenderer helm3;
   public ModelRenderer lefta;
   public ModelRenderer righta;

   public AdamantiumArmorModel() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.rightarm = new ModelRenderer(this, 40, 12);
      this.rightarm.mirror = true;
      this.rightarm.setRotationPoint(-5.0F, 2.0F, 0.0F);
      this.rightarm.addBox(-3.0F, -2.0F, -2.0F, 4, 8, 4, 0.4F);
      this.leftarm = new ModelRenderer(this, 40, 12);
      this.leftarm.setRotationPoint(5.0F, 2.0F, 0.0F);
      this.leftarm.addBox(-1.0F, -2.0F, -2.0F, 4, 8, 4, 0.4F);
      this.righta = new ModelRenderer(this, 0, 0);
      this.righta.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.righta.addBox(-4.0F, -5.0F, -2.0F, 1, 6, 2, 0.0F);
      this.setRotateAngle(this.righta, (float) (-Math.PI / 4), 0.0F, 0.0F);
      this.lefta = new ModelRenderer(this, 0, 0);
      this.lefta.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.lefta.addBox(3.0F, -5.0F, -2.0F, 1, 6, 2, 0.0F);
      this.setRotateAngle(this.lefta, (float) (-Math.PI / 4), 0.0F, 0.0F);
      this.helm = new ModelRenderer(this, 0, 0);
      this.helm.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.helm.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.2F);
      this.rightboot = new ModelRenderer(this, 40, 24);
      this.rightboot.mirror = true;
      this.rightboot.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.rightboot.addBox(-2.2F, 8.0F, -2.0F, 4, 4, 4, 0.4F);
      this.chest = new ModelRenderer(this, 16, 16);
      this.chest.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chest.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.5F);
      this.helm1 = new ModelRenderer(this, 32, 0);
      this.helm1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.helm1.addBox(3.0F, -10.0F, -7.0F, 2, 8, 3, 0.0F);
      this.setRotateAngle(this.helm1, (float) (-Math.PI / 4), 0.0F, 0.0F);
      this.leftboot = new ModelRenderer(this, 40, 24);
      this.leftboot.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.leftboot.addBox(-1.8F, 8.0F, -2.0F, 4, 4, 4, 0.4F);
      this.rightleg = new ModelRenderer(this, 0, 16);
      this.rightleg.mirror = true;
      this.rightleg.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.rightleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.2F);
      this.helm3 = new ModelRenderer(this, 42, 0);
      this.helm3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.helm3.addBox(-1.0F, -11.0F, -8.0F, 2, 8, 3, 0.0F);
      this.setRotateAngle(this.helm3, (float) (-Math.PI / 4), 0.0F, 0.0F);
      this.leftleg = new ModelRenderer(this, 0, 16);
      this.leftleg.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.leftleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.2F);
      this.helm2 = new ModelRenderer(this, 32, 0);
      this.helm2.mirror = true;
      this.helm2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.helm2.addBox(-5.0F, -10.0F, -7.0F, 2, 8, 3, 0.0F);
      this.setRotateAngle(this.helm2, (float) (-Math.PI / 4), 0.0F, 0.0F);
      this.rightarm.addChild(this.righta);
      this.leftarm.addChild(this.lefta);
      this.helm.addChild(this.helm1);
      this.helm.addChild(this.helm3);
      this.helm.addChild(this.helm2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      if (entity.isSneaking()) {
         GlStateManager.translate(0.0F, 0.2F, 0.0F);
      }

      this.rightarm.render(f5);
      this.leftarm.render(f5);
      this.helm.render(f5);
      this.rightboot.render(f5);
      this.chest.render(f5);
      this.leftboot.render(f5);
      this.rightleg.render(f5);
      this.leftleg.render(f5);
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
