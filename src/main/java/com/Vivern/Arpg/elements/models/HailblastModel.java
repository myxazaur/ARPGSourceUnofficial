package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class HailblastModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;

   public HailblastModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shape1 = new ModelRenderer(this, 0, 8);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
      this.setRotateAngle(this.shape1, (float) (Math.PI / 2), 0.0F, 0.63739425F);
      this.shape3 = new ModelRenderer(this, 0, 0);
      this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape3.addBox(-1.0F, 4.0F, -1.0F, 2, 3, 2, 0.0F);
      this.setRotateAngle(this.shape3, (float) (Math.PI / 2), 0.0F, -0.13665928F);
      this.shape2 = new ModelRenderer(this, 4, 0);
      this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape2.addBox(-1.5F, -3.0F, -1.5F, 3, 1, 3, 0.0F);
      this.setRotateAngle(this.shape2, (float) (Math.PI / 2), 0.0F, 0.95609134F);
      this.shape4 = new ModelRenderer(this, 4, 3);
      this.shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape4.addBox(-1.5F, 2.0F, -1.5F, 3, 2, 3, 0.0F);
      this.setRotateAngle(this.shape4, (float) (Math.PI / 2), 0.0F, 0.31869712F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape1.render(f5);
      this.shape3.render(f5);
      this.shape2.render(f5);
      this.shape4.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
