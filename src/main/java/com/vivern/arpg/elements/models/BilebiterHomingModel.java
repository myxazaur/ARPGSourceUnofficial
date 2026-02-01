package com.vivern.arpg.elements.models;

import com.vivern.arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BilebiterHomingModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape1_1;
   public ModelRenderer shape3;
   public ModelRenderer shape3_1;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer shape8;

   public BilebiterHomingModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shape3_1 = new ModelRenderer(this, 4, 10);
      this.shape3_1.setRotationPoint(-0.5F, 0.8F, 0.8F);
      this.shape3_1.addBox(-3.0F, -1.5F, 0.0F, 3, 3, 3, 0.0F);
      this.setRotateAngle(this.shape3_1, 0.8196066F, 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 6);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-1.0F, 0.0F, -2.5F, 2, 1, 3, 0.0F);
      this.setRotateAngle(this.shape1, 0.7285004F, 0.0F, 0.0F);
      this.shape8 = new ModelRenderer(this, 8, 0);
      this.shape8.setRotationPoint(-1.0F, 0.0F, 6.5F);
      this.shape8.addBox(-1.5F, 0.0F, -0.5F, 3, 7, 1, 0.0F);
      this.setRotateAngle(this.shape8, 0.8196066F, -1.2747885F, 0.0F);
      this.shape5 = new ModelRenderer(this, 8, 0);
      this.shape5.setRotationPoint(1.0F, 0.0F, 6.5F);
      this.shape5.addBox(-1.5F, 0.0F, -0.5F, 3, 8, 1, 0.0F);
      this.setRotateAngle(this.shape5, 1.9577358F, 1.9577358F, 0.0F);
      this.shape6 = new ModelRenderer(this, 8, 0);
      this.shape6.setRotationPoint(1.0F, 0.0F, 6.5F);
      this.shape6.addBox(-1.5F, 0.0F, -0.5F, 3, 7, 1, 0.0F);
      this.setRotateAngle(this.shape6, 0.8196066F, 1.2747885F, 0.0F);
      this.shape1_1 = new ModelRenderer(this, 0, 6);
      this.shape1_1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1_1.addBox(-1.0F, -1.0F, -2.6F, 2, 1, 3, 0.0F);
      this.setRotateAngle(this.shape1_1, -0.13665928F, 0.0F, 0.0F);
      this.shape7 = new ModelRenderer(this, 8, 0);
      this.shape7.setRotationPoint(-1.0F, 0.0F, 6.5F);
      this.shape7.addBox(-1.5F, 0.0F, -0.5F, 3, 8, 1, 0.0F);
      this.setRotateAngle(this.shape7, 1.9577358F, -1.9577358F, 0.0F);
      this.shape3 = new ModelRenderer(this, 4, 10);
      this.shape3.setRotationPoint(0.5F, 0.8F, 0.8F);
      this.shape3.addBox(0.0F, -1.5F, 0.0F, 3, 3, 3, 0.0F);
      this.setRotateAngle(this.shape3, 0.8196066F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      int tick = AnimationTimer.tick;
      float sin = (float)Math.sin(tick) * 32.0F;
      this.shape3_1.render(f5);
      this.shape1.render(f5);
      this.setRotateAngle(this.shape5, (float)Math.toRadians(81.0) + sin, 1.9577358F, 0.0F);
      this.shape5.render(f5);
      this.setRotateAngle(this.shape8, (float)Math.toRadians(81.0) - sin, -1.2747885F, 0.0F);
      this.shape8.render(f5);
      this.setRotateAngle(this.shape6, (float)Math.toRadians(81.0) - sin, 1.2747885F, 0.0F);
      this.shape6.render(f5);
      this.shape1_1.render(f5);
      this.setRotateAngle(this.shape7, (float)Math.toRadians(81.0) + sin, -1.9577358F, 0.0F);
      this.shape7.render(f5);
      this.shape3.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
