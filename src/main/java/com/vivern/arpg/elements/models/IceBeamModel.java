package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class IceBeamModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shape;
   public ModelRenderer shape_1;
   public ModelRenderer ice;
   public ModelRenderer ice1;
   public ModelRenderer ice2;
   public ModelRenderer ice3;

   public IceBeamModel() {
      this.textureWidth = 20;
      this.textureHeight = 16;
      this.ice = new ModelRenderer(this, 0, 8);
      this.ice.setRotationPoint(-2.5F, 2.0F, 3.3F);
      this.ice.addBox(-3.5F, -0.5F, 0.0F, 3, 7, 1, 0.0F);
      this.setRotateAngle(this.ice, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.shape_1 = new ModelRenderer(this, 0, 0);
      this.shape_1.setRotationPoint(-0.5F, 2.0F, 4.3F);
      this.shape_1.addBox(-4.5F, -0.5F, 0.0F, 5, 7, 1, 0.0F);
      this.setRotateAngle(this.shape_1, 0.0F, -0.68294734F, 0.0F);
      this.ice2 = new ModelRenderer(this, 8, 8);
      this.ice2.setRotationPoint(-0.5F, 2.0F, 4.0F);
      this.ice2.addBox(-4.5F, -0.5F, 0.0F, 5, 7, 1, 0.0F);
      this.setRotateAngle(this.ice2, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.shape = new ModelRenderer(this, 0, 0);
      this.shape.setRotationPoint(0.5F, 2.0F, 4.3F);
      this.shape.addBox(-0.5F, -0.5F, 0.0F, 5, 7, 1, 0.0F);
      this.setRotateAngle(this.shape, 0.0F, 0.68294734F, 0.0F);
      this.shapess1 = new ModelRenderer(this, 12, 0);
      this.shapess1.setRotationPoint(0.0F, 1.95F, 4.5F);
      this.shapess1.addBox(-1.0F, -0.5F, 0.0F, 2, 7, 1, 0.0F);
      this.ice1 = new ModelRenderer(this, 0, 8);
      this.ice1.setRotationPoint(3.5F, 2.0F, 2.3F);
      this.ice1.addBox(-2.5F, -0.5F, 0.0F, 3, 7, 1, 0.0F);
      this.setRotateAngle(this.ice1, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.ice3 = new ModelRenderer(this, 8, 8);
      this.ice3.mirror = true;
      this.ice3.setRotationPoint(0.5F, 2.0F, 0.0F);
      this.ice3.addBox(-4.5F, -0.5F, 0.0F, 5, 7, 1, 0.0F);
      this.setRotateAngle(this.ice3, 0.0F, (float) (Math.PI / 2), 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape_1.render(f5);
      this.shape.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
      GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.02, 1.0);
      GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
      GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
      this.shapess1.render(f5);
      GlStateManager.popMatrix();
      if (f1 == 0.0F) {
         this.ice2.render(f5);
         this.ice.render(f5);
         this.ice1.render(f5);
         this.ice3.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
