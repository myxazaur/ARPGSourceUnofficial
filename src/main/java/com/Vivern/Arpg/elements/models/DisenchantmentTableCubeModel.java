package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class DisenchantmentTableCubeModel extends ModelBase {
   public ModelRenderer salt;
   public ModelRenderer shape1;
   public ModelRenderer shape2;

   public DisenchantmentTableCubeModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.salt = new ModelRenderer(this, 0, 18);
      this.salt.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.salt.addBox(-0.5F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-1.5F, -3.0F, -3.0F, 6, 6, 3, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 9);
      this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape2.addBox(-1.5F, -3.0F, 0.0F, 6, 6, 3, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float amount = 0.125F;
      this.shape1.offsetZ = -amount * f3;
      this.shape2.offsetZ = amount * f3;
      this.salt.render(f5);
      this.shape1.render(f5);
      this.shape2.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
