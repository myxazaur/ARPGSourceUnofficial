package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class WinterBreathModel extends ModelBase {
   public ModelRenderer shapessh;
   public ModelRenderer shapessh_1;
   public ModelRenderer shapessh_2;
   public ModelRenderer shape;
   public ModelRenderer shape_1;
   public ModelRenderer shape_2;
   public ModelRenderer shape_3;
   public ModelRenderer shapeglow;

   public WinterBreathModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.shape = new ModelRenderer(this, 0, 14);
      this.shape.setRotationPoint(-5.0F, -3.0F, 1.0F);
      this.shape.addBox(0.0F, 0.0F, 0.0F, 2, 10, 8, 0.0F);
      this.shapeglow = new ModelRenderer(this, 12, 6);
      this.shapeglow.setRotationPoint(-4.5F, -2.5F, 1.5F);
      this.shapeglow.addBox(0.0F, 0.0F, 0.0F, 1, 9, 7, 0.0F);
      this.shape_1 = new ModelRenderer(this, 0, 14);
      this.shape_1.setRotationPoint(-5.0F, -2.0F, 1.0F);
      this.shape_1.addBox(0.0F, 0.0F, -2.0F, 2, 6, 2, 0.0F);
      this.setRotateAngle(this.shape_1, 0.0F, -0.18203785F, 0.0F);
      this.shapessh_1 = new ModelRenderer(this, 0, 0);
      this.shapessh_1.setRotationPoint(0.5F, 2.0F, 4.5F);
      this.shapessh_1.addBox(-0.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh_1, 0.13665928F, 0.0F, 0.0F);
      this.shapessh_2 = new ModelRenderer(this, 0, 0);
      this.shapessh_2.setRotationPoint(0.5F, 2.0F, 4.5F);
      this.shapessh_2.addBox(3.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh_2, 0.0F, -0.13665928F, (float) (Math.PI / 2));
      this.shapessh = new ModelRenderer(this, 0, 0);
      this.shapessh.setRotationPoint(0.5F, 2.0F, 4.5F);
      this.shapessh.addBox(-1.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh, 0.0F, -0.13665928F, (float) (Math.PI / 2));
      this.shape_2 = new ModelRenderer(this, 20, 24);
      this.shape_2.setRotationPoint(-5.0F, -2.0F, 9.0F);
      this.shape_2.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
      this.setRotateAngle(this.shape_2, 0.0F, 0.18203785F, 0.0F);
      this.shape_3 = new ModelRenderer(this, 0, 0);
      this.shape_3.setRotationPoint(-5.0F, 7.0F, 2.0F);
      this.shape_3.addBox(0.0F, 0.0F, 0.0F, 2, 4, 6, 0.0F);
      this.setRotateAngle(this.shape_3, 0.0F, 0.0F, -0.18203785F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      AbstractMobModel.light(170, false);
      this.shapeglow.render(f5);
      AbstractMobModel.returnlight();
      this.shape.render(f5);
      this.shape_1.render(f5);
      this.shapessh_1.render(f5);
      this.shapessh_2.render(f5);
      this.shapessh.render(f5);
      this.shape_2.render(f5);
      this.shape_3.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
