package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SmokeDemonModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer shape8;
   public ModelRenderer shape9;
   public ModelRenderer shape10;
   public ModelRenderer shape12;
   public ModelRenderer shape11;
   public ModelRenderer shape13;

   public SmokeDemonModel() {
      this.textureWidth = 26;
      this.textureHeight = 18;
      this.shape7 = new ModelRenderer(this, 0, 0);
      this.shape7.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape7.addBox(2.0F, -4.0F, 5.0F, 3, 9, 1, 0.0F);
      this.setRotateAngle(this.shape7, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.shape8 = new ModelRenderer(this, 0, 0);
      this.shape8.setRotationPoint(-4.0F, -7.0F, -3.0F);
      this.shape8.addBox(-1.0F, -7.0F, 0.0F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.shape8, -0.8196066F, -0.4553564F, -0.31869712F);
      this.shape10 = new ModelRenderer(this, 0, 0);
      this.shape10.setRotationPoint(-5.0F, -4.3F, -3.9F);
      this.shape10.addBox(-1.0F, -9.0F, 0.0F, 2, 9, 2, 0.0F);
      this.setRotateAngle(this.shape10, -0.91053826F, -0.13665928F, -1.5025539F);
      this.shape3 = new ModelRenderer(this, 18, 0);
      this.shape3.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape3.addBox(-5.0F, -1.0F, -6.0F, 3, 8, 1, 0.0F);
      this.shape1 = new ModelRenderer(this, 8, 6);
      this.shape1.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape1.addBox(-2.0F, -3.0F, -6.0F, 4, 4, 1, 0.0F);
      this.shape5 = new ModelRenderer(this, 0, 11);
      this.shape5.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape5.addBox(-5.0F, -7.0F, -5.0F, 10, 4, 3, 0.0F);
      this.shape6 = new ModelRenderer(this, 0, 0);
      this.shape6.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape6.addBox(2.0F, -4.0F, -6.0F, 3, 9, 1, 0.0F);
      this.setRotateAngle(this.shape6, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.shape2 = new ModelRenderer(this, 8, 0);
      this.shape2.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape2.addBox(-3.0F, 7.0F, -6.0F, 6, 1, 3, 0.0F);
      this.shape9 = new ModelRenderer(this, 0, 0);
      this.shape9.setRotationPoint(4.0F, -7.0F, -3.0F);
      this.shape9.addBox(-1.0F, -7.0F, 0.0F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.shape9, -0.8196066F, 0.4553564F, 0.31869712F);
      this.shape12 = new ModelRenderer(this, 0, 0);
      this.shape12.setRotationPoint(5.0F, -4.3F, -3.9F);
      this.shape12.addBox(-1.0F, -9.0F, 0.0F, 2, 9, 2, 0.0F);
      this.setRotateAngle(this.shape12, -0.91053826F, 0.13665928F, 1.5025539F);
      this.shape11 = new ModelRenderer(this, 0, 0);
      this.shape11.setRotationPoint(0.0F, -8.5F, 1.0F);
      this.shape11.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
      this.setRotateAngle(this.shape11, 0.18203785F, -0.13665928F, -1.9123572F);
      this.shape4 = new ModelRenderer(this, 18, 0);
      this.shape4.mirror = true;
      this.shape4.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape4.addBox(2.0F, -1.0F, -6.0F, 3, 8, 1, 0.0F);
      this.shape13 = new ModelRenderer(this, 0, 0);
      this.shape13.setRotationPoint(0.0F, -8.5F, 1.0F);
      this.shape13.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
      this.setRotateAngle(this.shape13, 0.18203785F, 0.13665928F, 1.9123572F);
      this.shape5.addChild(this.shape7);
      this.shape5.addChild(this.shape8);
      this.shape5.addChild(this.shape10);
      this.shape5.addChild(this.shape6);
      this.shape5.addChild(this.shape9);
      this.shape5.addChild(this.shape12);
      this.shape10.addChild(this.shape11);
      this.shape12.addChild(this.shape13);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.shape3.render(f5);
      this.shape1.render(f5);
      this.shape5.render(f5);
      this.shape2.render(f5);
      this.shape4.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      this.shape1.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.shape1.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      this.shape2.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.shape2.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      this.shape3.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.shape3.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      this.shape4.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.shape4.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      this.shape5.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.shape5.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
   }
}
