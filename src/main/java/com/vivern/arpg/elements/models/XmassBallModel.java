package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class XmassBallModel extends ModelBase {
   public ModelRenderer up;
   public ModelRenderer ball1;
   public ModelRenderer ball2;
   public ModelRenderer ball3;
   public ModelRenderer ball4;

   public XmassBallModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.ball3 = new ModelRenderer(this, 14, 8);
      this.ball3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.ball3.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.ball1 = new ModelRenderer(this, 14, 0);
      this.ball1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.ball1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.ball4 = new ModelRenderer(this, 14, 12);
      this.ball4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.ball4.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.up = new ModelRenderer(this, 10, 0);
      this.up.setRotationPoint(0.0F, -1.0F, 0.0F);
      this.up.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.ball2 = new ModelRenderer(this, 14, 4);
      this.ball2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.ball2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.up.render(f5);
      int i = (int)(entity.getEntityId() * 1.4827375F) % 4;
      if (i == 0) {
         this.ball1.render(f5);
      }

      if (i == 1) {
         this.ball2.render(f5);
      }

      if (i == 2) {
         this.ball3.render(f5);
      }

      if (i == 3) {
         this.ball4.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
