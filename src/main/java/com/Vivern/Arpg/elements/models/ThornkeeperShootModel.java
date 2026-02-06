package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ThornkeeperShootModel extends ModelBase {
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;

   public ThornkeeperShootModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shape3 = new ModelRenderer(this, 0, 0);
      this.shape3.setRotationPoint(1.0F, -7.0F, -7.0F);
      this.shape3.addBox(0.0F, 0.0F, 0.0F, 14, 14, 2, 0.0F);
      this.setRotateAngle(this.shape3, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(-7.0F, -7.0F, -1.0F);
      this.shape2.addBox(0.0F, 0.0F, 0.0F, 14, 14, 2, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 0);
      this.shape4.setRotationPoint(-7.0F, -1.0F, 7.0F);
      this.shape4.addBox(0.0F, 0.0F, 0.0F, 14, 14, 2, 0.0F);
      this.setRotateAngle(this.shape4, (float) (-Math.PI / 2), 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
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
