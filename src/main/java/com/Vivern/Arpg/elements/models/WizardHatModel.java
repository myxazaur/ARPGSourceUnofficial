package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class WizardHatModel extends ModelBiped {
   public ModelRenderer hat1;
   public ModelRenderer hat2;
   public ModelRenderer hat3;

   public WizardHatModel() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.hat1 = new ModelRenderer(this, 0, 20);
      this.hat1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.hat1.addBox(-5.0F, -9.0F, -5.0F, 10, 2, 10, 0.0F);
      this.hat2 = new ModelRenderer(this, 40, 22);
      this.hat2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.hat2.addBox(-3.0F, -12.0F, -4.5F, 6, 4, 6, 0.0F);
      this.setRotateAngle(this.hat2, -0.18203785F, 0.0F, 0.0F);
      this.hat3 = new ModelRenderer(this, 30, 19);
      this.hat3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.hat3.addBox(-1.5F, -13.5F, -9.5F, 3, 6, 3, 0.0F);
      this.setRotateAngle(this.hat3, -0.8196066F, 0.0F, 0.0F);
      this.bipedHead.addChild(this.hat1);
      this.bipedHead.addChild(this.hat2);
      this.bipedHead.addChild(this.hat3);
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
