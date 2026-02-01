package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class FireLordHelmModel extends ModelBiped {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape5;
   public ModelRenderer shape4;

   public FireLordHelmModel() {
      this.bipedBody.showModel = false;
      this.bipedHead.showModel = false;
      this.bipedHeadwear.showModel = false;
      this.bipedLeftArm.showModel = false;
      this.bipedRightArm.showModel = false;
      this.bipedLeftLeg.showModel = false;
      this.bipedRightLeg.showModel = false;
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.shape3 = new ModelRenderer(this, 48, 18);
      this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape3.addBox(-3.0F, -14.5F, -1.0F, 4, 10, 4, 0.0F);
      this.setRotateAngle(this.shape3, 0.0F, 0.0F, -0.4553564F);
      this.shape5 = new ModelRenderer(this, 40, 18);
      this.shape5.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape5.addBox(9.8F, -20.5F, 0.0F, 2, 12, 2, 0.0F);
      this.setRotateAngle(this.shape5, 0.0F, 0.0F, -0.3642502F);
      this.shape4 = new ModelRenderer(this, 40, 18);
      this.shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape4.addBox(-11.8F, -20.5F, 0.0F, 2, 12, 2, 0.0F);
      this.setRotateAngle(this.shape4, 0.0F, 0.0F, 0.3642502F);
      this.shape1 = new ModelRenderer(this, 32, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-4.0F, -7.5F, -4.0F, 8, 8, 8, 0.0F);
      this.shape2 = new ModelRenderer(this, 48, 18);
      this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape2.addBox(-1.0F, -14.5F, -1.0F, 4, 10, 4, 0.0F);
      this.setRotateAngle(this.shape2, 0.0F, 0.0F, 0.4553564F);
      this.bipedHead.addChild(this.shape1);
      this.bipedHead.addChild(this.shape2);
      this.bipedHead.addChild(this.shape3);
      this.bipedHead.addChild(this.shape4);
      this.bipedHead.addChild(this.shape5);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
