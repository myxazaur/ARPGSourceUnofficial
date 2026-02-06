package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class PhoenixGhostCapeModel extends ModelBase {
   public ModelRenderer shape15;
   public ModelRenderer shape15_1;
   public ModelRenderer shape15_2;
   public ModelRenderer shape15_3;
   public ModelRenderer shape15_4;
   public ModelRenderer shape15_5;
   public ModelRenderer shape15_6;
   public ModelRenderer shape15_7;
   public ModelRenderer shape15_8;
   public ModelRenderer shape15_9;

   public PhoenixGhostCapeModel() {
      this.textureWidth = 32;
      this.textureHeight = 14;
      this.shape15_3 = new ModelRenderer(this, 20, 10);
      this.shape15_3.setRotationPoint(0.0F, 3.0F, 1.0F);
      this.shape15_3.addBox(-2.5F, 0.0F, 0.0F, 5, 3, 1, 0.0F);
      this.shape15 = new ModelRenderer(this, 18, 0);
      this.shape15.setRotationPoint(0.0F, 0.0F, 2.0F);
      this.shape15.addBox(-3.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
      this.shape15_9 = new ModelRenderer(this, 12, 0);
      this.shape15_9.setRotationPoint(-3.0F, 0.0F, 0.0F);
      this.shape15_9.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
      this.shape15_8 = new ModelRenderer(this, 8, 0);
      this.shape15_8.setRotationPoint(3.0F, 0.0F, 0.0F);
      this.shape15_8.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
      this.shape15_2 = new ModelRenderer(this, 4, 0);
      this.shape15_2.setRotationPoint(0.0F, 1.0F, 0.5F);
      this.shape15_2.addBox(1.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
      this.shape15_6 = new ModelRenderer(this, 0, 4);
      this.shape15_6.setRotationPoint(0.0F, 6.0F, 0.5F);
      this.shape15_6.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
      this.shape15_1 = new ModelRenderer(this, 0, 0);
      this.shape15_1.setRotationPoint(0.0F, 1.0F, 0.5F);
      this.shape15_1.addBox(-2.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
      this.shape15_5 = new ModelRenderer(this, 0, 7);
      this.shape15_5.setRotationPoint(0.0F, 4.0F, 0.5F);
      this.shape15_5.addBox(-4.5F, 0.0F, 0.0F, 9, 6, 1, 0.0F);
      this.shape15_7 = new ModelRenderer(this, 20, 7);
      this.shape15_7.setRotationPoint(0.0F, 2.0F, 0.5F);
      this.shape15_7.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
      this.shape15_4 = new ModelRenderer(this, 16, 2);
      this.shape15_4.setRotationPoint(0.0F, 3.0F, 0.5F);
      this.shape15_4.addBox(-3.5F, 0.0F, 0.0F, 7, 4, 1, 0.0F);
      this.shape15.addChild(this.shape15_3);
      this.shape15_7.addChild(this.shape15_9);
      this.shape15_7.addChild(this.shape15_8);
      this.shape15.addChild(this.shape15_2);
      this.shape15_5.addChild(this.shape15_6);
      this.shape15.addChild(this.shape15_1);
      this.shape15_4.addChild(this.shape15_5);
      this.shape15_6.addChild(this.shape15_7);
      this.shape15_3.addChild(this.shape15_4);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape15.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
