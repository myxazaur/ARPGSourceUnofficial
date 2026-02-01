package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class CanisterModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shapess2;
   public ModelRenderer shape1;
   public ModelRenderer roll2;
   public ModelRenderer stick2;
   public ModelRenderer roll1;
   public ModelRenderer stick1;
   public ModelRenderer shell1;
   public ModelRenderer pipe1;
   public ModelRenderer pipe2;
   public ModelRenderer shell2;

   public CanisterModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.shell2 = new ModelRenderer(this, 24, 6);
      this.shell2.setRotationPoint(0.0F, 8.0F, -4.0F);
      this.shell2.addBox(-1.5F, 1.0F, -1.0F, 3, 4, 1, 0.0F);
      this.shapess1 = new ModelRenderer(this, 0, 0);
      this.shapess1.setRotationPoint(0.0F, 3.5F, 5.0F);
      this.shapess1.addBox(-0.5F, 0.0F, 0.0F, 1, 5, 2, 0.0F);
      this.shell1 = new ModelRenderer(this, 16, 0);
      this.shell1.setRotationPoint(0.0F, 8.0F, -4.0F);
      this.shell1.addBox(-1.5F, 0.0F, -1.0F, 3, 1, 5, 0.0F);
      this.pipe2 = new ModelRenderer(this, 2, 3);
      this.pipe2.setRotationPoint(0.0F, 7.4F, -4.7F);
      this.pipe2.addBox(-0.5F, -0.5F, -5.0F, 1, 2, 5, 0.0F);
      this.stick2 = new ModelRenderer(this, 0, 17);
      this.stick2.setRotationPoint(0.0F, 10.0F, 5.5F);
      this.stick2.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.stick2, (float) (-Math.PI / 4), 0.0F, 0.0F);
      this.stick1 = new ModelRenderer(this, 0, 17);
      this.stick1.setRotationPoint(0.0F, 4.5F, 1.5F);
      this.stick1.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
      this.setRotateAngle(this.stick1, (float) (-Math.PI / 4), 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 16);
      this.shape1.setRotationPoint(0.0F, 9.0F, 0.0F);
      this.shape1.addBox(-4.0F, 0.0F, -4.0F, 8, 8, 8, 0.0F);
      this.roll1 = new ModelRenderer(this, 0, 11);
      this.roll1.setRotationPoint(0.0F, 4.0F, 2.0F);
      this.roll1.addBox(-1.5F, -1.0F, -1.0F, 3, 2, 2, 0.0F);
      this.shapess2 = new ModelRenderer(this, 26, 11);
      this.shapess2.setRotationPoint(0.0F, 3.5F, 3.0F);
      this.shapess2.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
      this.roll2 = new ModelRenderer(this, 0, 11);
      this.roll2.setRotationPoint(0.0F, 9.5F, 6.0F);
      this.roll2.addBox(-1.5F, -1.0F, -1.0F, 3, 2, 2, 0.0F);
      this.pipe1 = new ModelRenderer(this, 11, 9);
      this.pipe1.setRotationPoint(0.0F, 9.0F, -4.0F);
      this.pipe1.addBox(-1.0F, -2.0F, -2.0F, 2, 3, 3, 0.0F);
      this.setRotateAngle(this.pipe1, (float) (Math.PI / 4), 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shell2.render(f5);
      this.shapess1.render(f5);
      this.shell1.render(f5);
      this.stick2.render(f5);
      this.stick1.render(f5);
      this.roll1.render(f5);
      this.shapess2.render(f5);
      this.roll2.render(f5);
      this.pipe1.render(f5);
      GlStateManager.disableCull();
      this.shape1.render(f5);
      this.pipe2.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
