package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ResearchTableExpModel extends ModelBase {
   public ModelRenderer shapeCarpet;
   public ModelRenderer shape11;
   public ModelRenderer shape11_1;
   public ModelRenderer shape11_2;
   public ModelRenderer shape11_3;
   public ModelRenderer shape11_4;
   public ModelRenderer shape11_5;
   public ModelRenderer shape11_6;
   public ModelRenderer shapeDust;

   public ResearchTableExpModel() {
      this.textureWidth = 32;
      this.textureHeight = 16;
      this.shapeDust = new ModelRenderer(this, 0, 8);
      this.shapeDust.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.shapeDust.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
      this.shape11_5 = new ModelRenderer(this, 0, 0);
      this.shape11_5.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.shape11_5.addBox(-0.5F, -2.0F, 5.5F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.shape11_5, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.shape11_2 = new ModelRenderer(this, 0, 0);
      this.shape11_2.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.shape11_2.addBox(-0.5F, -2.0F, 5.5F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.shape11_2, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.shapeCarpet = new ModelRenderer(this, -1, 0);
      this.shapeCarpet.setRotationPoint(0.0F, 7.95F, 0.0F);
      this.shapeCarpet.addBox(-5.5F, 0.0F, -5.5F, 11, 0, 11, 0.0F);
      this.setRotateAngle(this.shapeCarpet, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.shape11_1 = new ModelRenderer(this, 0, 0);
      this.shape11_1.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.shape11_1.addBox(-0.5F, -3.0F, 5.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape11_1, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.shape11_6 = new ModelRenderer(this, 0, 0);
      this.shape11_6.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.shape11_6.addBox(-0.5F, -1.0F, 5.5F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.shape11_6, 0.0F, (float) (Math.PI * 3.0 / 4.0), 0.0F);
      this.shape11_3 = new ModelRenderer(this, 0, 0);
      this.shape11_3.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.shape11_3.addBox(-0.5F, -1.0F, 5.5F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.shape11_3, 0.0F, (float) (-Math.PI * 3.0 / 4.0), 0.0F);
      this.shape11 = new ModelRenderer(this, 0, 0);
      this.shape11.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.shape11.addBox(-0.5F, -4.0F, 5.5F, 1, 5, 1, 0.0F);
      this.shape11_4 = new ModelRenderer(this, 0, 0);
      this.shape11_4.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.shape11_4.addBox(-0.5F, -3.0F, 5.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape11_4, 0.0F, (float) (Math.PI / 4), 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shapeDust.render(f5);
      this.shape11_5.render(f5);
      this.shape11_2.render(f5);
      this.shapeCarpet.render(f5);
      this.shape11_1.render(f5);
      this.shape11_6.render(f5);
      this.shape11_3.render(f5);
      this.shape11.render(f5);
      this.shape11_4.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
