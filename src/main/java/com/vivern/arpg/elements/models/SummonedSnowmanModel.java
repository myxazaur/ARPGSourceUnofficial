package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class SummonedSnowmanModel extends ModelBase {
   public ModelRenderer head;
   public ModelRenderer leftHand;
   public ModelRenderer bottomBody;
   public ModelRenderer rightHand;
   public ModelRenderer body;
   public ModelRenderer hat;
   public ModelRenderer hat2;
   public ModelRenderer stick;
   public ModelRenderer shape6;

   public SummonedSnowmanModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.hat = new ModelRenderer(this, 28, 7);
      this.hat.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.hat.addBox(-4.5F, -8.0F, -4.5F, 9, 1, 9, 0.0F);
      this.stick = new ModelRenderer(this, 35, 0);
      this.stick.setRotationPoint(0.0F, 0.0F, -0.0F);
      this.stick.addBox(1.0F, -4.9F, -0.5F, 4, 1, 1, 0.0F);
      this.setRotateAngle(this.stick, (float) (-Math.PI / 2), 1.910612F, (float) (-Math.PI / 2));
      this.head = new ModelRenderer(this, 0, 0);
      this.head.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, -0.5F);
      this.leftHand = new ModelRenderer(this, 32, 0);
      this.leftHand.setRotationPoint(-5.0F, 6.0F, 0.0F);
      this.leftHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
      this.setRotateAngle(this.leftHand, 0.0F, 2.321986F, -1.0000737F);
      this.body = new ModelRenderer(this, 0, 17);
      this.body.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.body.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, -0.5F);
      this.bottomBody = new ModelRenderer(this, 0, 37);
      this.bottomBody.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.bottomBody.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12, -0.5F);
      this.rightHand = new ModelRenderer(this, 32, 0);
      this.rightHand.setRotationPoint(5.0F, 6.0F, -0.0F);
      this.rightHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
      this.setRotateAngle(this.rightHand, 0.0F, 0.0F, 1.0F);
      this.hat2 = new ModelRenderer(this, 40, 17);
      this.hat2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.hat2.addBox(-3.0F, -10.0F, -3.0F, 6, 2, 6, 0.0F);
      this.shape6 = new ModelRenderer(this, 40, 42);
      this.shape6.setRotationPoint(9.47F, 0.8F, -0.6F);
      this.shape6.addBox(0.0F, 0.0F, 0.0F, 1, 1, 5, 0.0F);
      this.setRotateAngle(this.shape6, 0.13665928F, -0.091106184F, 0.091106184F);
      this.head.addChild(this.hat);
      this.head.addChild(this.stick);
      this.head.addChild(this.hat2);
      this.leftHand.addChild(this.shape6);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.head.render(f5);
      this.leftHand.render(f5);
      this.body.render(f5);
      this.bottomBody.render(f5);
      this.rightHand.render(f5);
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
      this.head.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      this.body.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0) * 0.25F;
      float f = MathHelper.sin(this.body.rotateAngleY);
      float f1 = MathHelper.cos(this.body.rotateAngleY);
      this.rightHand.rotateAngleZ = 1.0F;
      this.leftHand.rotateAngleZ = -1.0F;
      this.rightHand.rotateAngleY = 0.0F + this.body.rotateAngleY;
      this.rightHand.rotationPointX = f1 * 5.0F;
      this.rightHand.rotationPointZ = -f * 5.0F;
      this.leftHand.rotationPointX = -f1 * 5.0F;
      this.leftHand.rotationPointZ = f * 5.0F;
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
