package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ToxicNukeModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape1_1;
   public ModelRenderer shape1_2;
   public ModelRenderer shape1_3;
   public ModelRenderer shape1_4;

   public ToxicNukeModel() {
      this.textureWidth = 16;
      this.textureHeight = 21;
      this.shape1_1 = new ModelRenderer(this, 0, 10);
      this.shape1_1.setRotationPoint(0.0F, 0.0F, 6.0F);
      this.shape1_1.addBox(-1.5F, -2.0F, -1.5F, 3, 3, 3, 0.0F);
      this.setRotateAngle(this.shape1_1, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape1_4 = new ModelRenderer(this, 12, 15);
      this.shape1_4.setRotationPoint(0.0F, 0.0F, 10.0F);
      this.shape1_4.addBox(-0.5F, -2.5F, -0.05F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.shape1_4, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-2.0F, -2.0F, -2.0F, 4, 6, 4, 0.0F);
      this.setRotateAngle(this.shape1, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape1_2 = new ModelRenderer(this, 0, 16);
      this.shape1_2.setRotationPoint(0.0F, 0.0F, 8.0F);
      this.shape1_2.addBox(-1.0F, -1.0F, -1.0F, 2, 3, 2, 0.0F);
      this.setRotateAngle(this.shape1_2, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape1_3 = new ModelRenderer(this, 12, 15);
      this.shape1_3.setRotationPoint(0.0F, 0.0F, 10.0F);
      this.shape1_3.addBox(-0.5F, -2.5F, 0.0F, 1, 5, 1, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape1_1.render(f5);
      this.shape1_4.render(f5);
      this.shape1.render(f5);
      this.shape1_2.render(f5);
      this.shape1_3.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
