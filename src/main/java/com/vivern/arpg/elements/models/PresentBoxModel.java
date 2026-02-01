package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class PresentBoxModel extends ModelBase {
   public ModelRenderer base;
   public ModelRenderer open1;
   public ModelRenderer open2;
   public ModelRenderer open3;
   public ModelRenderer open4;
   public ModelRenderer band1;
   public ModelRenderer band2;

   public PresentBoxModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.open1 = new ModelRenderer(this, 0, 0);
      this.open1.setRotationPoint(0.0F, 10.0F, 7.0F);
      this.open1.addBox(-7.0F, 0.0F, -7.0F, 14, 1, 7, 0.0F);
      this.open3 = new ModelRenderer(this, 0, 16);
      this.open3.setRotationPoint(7.0F, 10.0F, 0.0F);
      this.open3.addBox(-7.0F, 0.0F, 0.0F, 14, 1, 7, 0.0F);
      this.setRotateAngle(this.open3, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.base = new ModelRenderer(this, 0, 36);
      this.base.setRotationPoint(0.0F, 17.0F, 0.0F);
      this.base.addBox(-7.0F, -7.0F, -7.0F, 14, 14, 14, 0.0F);
      this.band1 = new ModelRenderer(this, 44, 0);
      this.band1.setRotationPoint(0.0F, -3.0F, -7.0F);
      this.band1.addBox(0.0F, 0.0F, 0.0F, 5, 3, 5, 0.0F);
      this.setRotateAngle(this.band1, 0.0F, -0.5462881F, 0.0F);
      this.open2 = new ModelRenderer(this, 0, 8);
      this.open2.setRotationPoint(0.0F, 10.0F, -7.0F);
      this.open2.addBox(-7.0F, 0.0F, 0.0F, 14, 1, 7, 0.0F);
      this.band2 = new ModelRenderer(this, 44, 8);
      this.band2.setRotationPoint(0.0F, -3.0F, 7.0F);
      this.band2.addBox(0.0F, 0.0F, 0.0F, 5, 3, 5, 0.0F);
      this.setRotateAngle(this.band2, 0.0F, 2.5953045F, 0.0F);
      this.open4 = new ModelRenderer(this, 0, 24);
      this.open4.setRotationPoint(-7.0F, 10.0F, 0.0F);
      this.open4.addBox(-7.0F, 0.0F, 0.0F, 14, 1, 7, 0.0F);
      this.setRotateAngle(this.open4, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.open1.addChild(this.band1);
      this.open2.addChild(this.band2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.open1.rotateAngleX = -f;
      this.open2.rotateAngleX = f;
      this.open3.rotateAngleX = f;
      this.open4.rotateAngleX = f;
      this.open1.render(f5);
      this.open3.render(f5);
      this.base.render(f5);
      this.open2.render(f5);
      this.open4.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
