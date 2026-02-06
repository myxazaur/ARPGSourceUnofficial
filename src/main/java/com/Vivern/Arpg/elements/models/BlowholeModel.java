package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class BlowholeModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shapess2;
   public ModelRenderer shapess3;
   public ModelRenderer shape1;
   public ModelRenderer spike1;
   public ModelRenderer spike2;
   public ModelRenderer spike3;
   public ModelRenderer shape2;
   public ModelRenderer stick;

   public BlowholeModel() {
      this.textureWidth = 34;
      this.textureHeight = 18;
      this.shape1 = new ModelRenderer(this, 11, 2);
      this.shape1.setRotationPoint(-2.0F, -3.0F, -1.0F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 4, 4, 7, 0.0F);
      this.shape2 = new ModelRenderer(this, 1, 9);
      this.shape2.setRotationPoint(-1.5F, -2.5F, -3.0F);
      this.shape2.addBox(0.0F, 0.0F, 0.0F, 3, 3, 2, 0.0F);
      this.stick = new ModelRenderer(this, 0, 7);
      this.stick.setRotationPoint(0.0F, -1.0F, -1.0F);
      this.stick.addBox(0.0F, -5.5F, -5.0F, 0, 4, 7, 0.0F);
      this.shapess2 = new ModelRenderer(this, 22, 13);
      this.shapess2.setRotationPoint(0.0F, 6.0F, 5.4F);
      this.shapess2.addBox(-0.5F, -0.5F, 0.0F, 1, 3, 2, 0.0F);
      this.setRotateAngle(this.shapess2, -0.4098033F, 0.0F, 0.0F);
      this.shapess3 = new ModelRenderer(this, 29, 13);
      this.shapess3.setRotationPoint(0.0F, 8.0F, 5.4F);
      this.shapess3.addBox(-0.5F, -0.5F, 0.0F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.shapess3, -1.2292354F, 0.0F, 0.0F);
      this.spike2 = new ModelRenderer(this, 0, 0);
      this.spike2.setRotationPoint(0.0F, -1.8F, 2.5F);
      this.spike2.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 5, 0.0F);
      this.setRotateAngle(this.spike2, 0.1129228F, -0.1129228F, (float) (Math.PI / 4));
      this.spike3 = new ModelRenderer(this, 0, 0);
      this.spike3.setRotationPoint(0.0F, -1.8F, 4.5F);
      this.spike3.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 5, 0.0F);
      this.setRotateAngle(this.spike3, -0.022514747F, 0.022514747F, (float) (Math.PI / 4));
      this.spike1 = new ModelRenderer(this, 0, 0);
      this.spike1.setRotationPoint(0.0F, -1.5F, 0.5F);
      this.spike1.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 5, 0.0F);
      this.setRotateAngle(this.spike1, 0.20333086F, -0.20333086F, 0.7909832F);
      this.shapess1 = new ModelRenderer(this, 26, 0);
      this.shapess1.setRotationPoint(0.0F, 1.0F, 4.5F);
      this.shapess1.addBox(-0.5F, -0.5F, 0.0F, 1, 6, 1, 0.0F);
      this.setRotateAngle(this.shapess1, 0.18203785F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape1.render(f5);
      this.shape2.render(f5);
      if (f == 0.0F) {
         for (int i = 0; i < 8; i++) {
            this.stick.rotateAngleZ = i * 45 * (float) (Math.PI / 180.0);
            this.stick.render(f5);
            this.stick.rotateAngleX = f2 * -0.8F * (float) (Math.PI / 180.0);
            if (f3 > 0.0F) {
               this.stick.rotateAngleX = this.stick.rotateAngleX + 0.48F * GetMOP.getfromto(f3, 0.0F, 10.0F) * (1.0F - GetMOP.getfromto(f3, f4 - 10.0F, f4));
            }
         }
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess2.offsetX, this.shapess2.offsetY, this.shapess2.offsetZ);
      GlStateManager.translate(this.shapess2.rotationPointX * f5, this.shapess2.rotationPointY * f5, this.shapess2.rotationPointZ * f5);
      GlStateManager.scale(1.3, 1.0, 1.0);
      GlStateManager.translate(-this.shapess2.offsetX, -this.shapess2.offsetY, -this.shapess2.offsetZ);
      GlStateManager.translate(-this.shapess2.rotationPointX * f5, -this.shapess2.rotationPointY * f5, -this.shapess2.rotationPointZ * f5);
      this.shapess2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess3.offsetX, this.shapess3.offsetY, this.shapess3.offsetZ);
      GlStateManager.translate(this.shapess3.rotationPointX * f5, this.shapess3.rotationPointY * f5, this.shapess3.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.0, 1.0);
      GlStateManager.translate(-this.shapess3.offsetX, -this.shapess3.offsetY, -this.shapess3.offsetZ);
      GlStateManager.translate(-this.shapess3.rotationPointX * f5, -this.shapess3.rotationPointY * f5, -this.shapess3.rotationPointZ * f5);
      this.shapess3.render(f5);
      GlStateManager.popMatrix();
      if (f == 0.0F) {
         this.spike2.render(f5);
         this.spike3.render(f5);
         this.spike1.render(f5);
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
      GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.0, 1.2);
      GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
      GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
      this.shapess1.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
