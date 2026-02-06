package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class RottenShieldModel extends ModelBase {
   public ModelRenderer shapessh;
   public ModelRenderer shapessh_1;
   public ModelRenderer shapessh_2;
   public ModelRenderer shape;
   public ModelRenderer shape_1;
   public ModelRenderer shape_2;
   public ModelRenderer shapecap1;
   public ModelRenderer shapecap2;
   public ModelRenderer shapecap3;

   public RottenShieldModel() {
      this.textureWidth = 36;
      this.textureHeight = 32;
      this.shape_1 = new ModelRenderer(this, 0, 0);
      this.shape_1.setRotationPoint(-6.0F, 2.0F, -1.5F);
      this.shape_1.addBox(0.0F, 0.0F, 0.0F, 2, 11, 4, 0.0F);
      this.shapessh_2 = new ModelRenderer(this, 28, 27);
      this.shapessh_2.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh_2.addBox(3.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh_2, 0.0F, -0.13665928F, (float) (Math.PI / 2));
      this.shape = new ModelRenderer(this, 4, 10);
      this.shape.setRotationPoint(-6.5F, -3.0F, 0.5F);
      this.shape.addBox(0.0F, 0.0F, 0.0F, 3, 10, 8, 0.0F);
      this.shape_2 = new ModelRenderer(this, 18, 0);
      this.shape_2.setRotationPoint(-5.5F, 5.0F, 1.5F);
      this.shape_2.addBox(0.0F, 0.0F, 0.0F, 1, 10, 8, 0.0F);
      this.shapecap1 = new ModelRenderer(this, 4, 1);
      this.shapecap1.setRotationPoint(-6.5F, 5.0F, 0.5F);
      this.shapecap1.addBox(0.0F, 0.0F, 0.0F, 3, 0, 8, 0.0F);
      this.shapecap3 = new ModelRenderer(this, -4, 0);
      this.shapecap3.setRotationPoint(-6.0F, 11.0F, -1.5F);
      this.shapecap3.addBox(0.0F, 0.0F, 0.0F, 2, 0, 4, 0.0F);
      this.shapessh_1 = new ModelRenderer(this, 28, 27);
      this.shapessh_1.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh_1.addBox(-0.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh_1, 0.13665928F, 0.0F, 0.0F);
      this.shapessh = new ModelRenderer(this, 28, 27);
      this.shapessh.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh.addBox(-1.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh, 0.0F, -0.13665928F, (float) (Math.PI / 2));
      this.shapecap2 = new ModelRenderer(this, 15, 0);
      this.shapecap2.setRotationPoint(-5.5F, 12.0F, 1.5F);
      this.shapecap2.addBox(0.0F, 0.0F, 0.0F, 1, 0, 8, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      this.shape_1.render(f5);
      this.shapessh_2.render(f5);
      this.shape.render(f5);
      this.shape_2.render(f5);
      this.shapecap1.render(f5);
      this.shapecap3.render(f5);
      this.shapessh_1.render(f5);
      this.shapessh.render(f5);
      this.shapecap2.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
