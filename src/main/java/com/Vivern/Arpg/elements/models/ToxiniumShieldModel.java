package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ToxiniumShieldModel extends ModelBase {
   public ModelRenderer shapessh;
   public ModelRenderer shapessh_1;
   public ModelRenderer shapessh_2;
   public ModelRenderer shapemain;
   public ModelRenderer shape;
   public ModelRenderer shape_1;
   public ModelRenderer shapeplate;
   public ModelRenderer shape_2;
   public ModelRenderer shapeinvert;
   public ModelRenderer spike1;
   public ModelRenderer spike2;
   public ModelRenderer spike3;

   public ToxiniumShieldModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.shapessh_2 = new ModelRenderer(this, 8, 0);
      this.shapessh_2.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh_2.addBox(3.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh_2, 0.0F, -0.045553092F, (float) (Math.PI / 2));
      this.spike2 = new ModelRenderer(this, 16, 24);
      this.spike2.setRotationPoint(-6.5F, 0.0F, 4.5F);
      this.spike2.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 0, 0.0F);
      this.shapessh_1 = new ModelRenderer(this, 8, 0);
      this.shapessh_1.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh_1.addBox(-0.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh_1, 0.045553092F, 0.0F, 0.0F);
      this.shapessh = new ModelRenderer(this, 8, 0);
      this.shapessh.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh.addBox(-1.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh, 0.0F, -0.045553092F, (float) (Math.PI / 2));
      this.shape_2 = new ModelRenderer(this, 22, 0);
      this.shape_2.setRotationPoint(-6.0F, 16.0F, 0.5F);
      this.shape_2.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
      this.setRotateAngle(this.shape_2, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.spike3 = new ModelRenderer(this, 16, 24);
      this.spike3.setRotationPoint(-6.5F, 0.0F, 1.5F);
      this.spike3.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 0, 0.0F);
      this.setRotateAngle(this.spike3, 0.0F, -0.22759093F, 0.0F);
      this.shapemain = new ModelRenderer(this, 0, 6);
      this.shapemain.setRotationPoint(-6.5F, -3.0F, 0.5F);
      this.shapemain.addBox(0.0F, 0.0F, 0.0F, 3, 10, 8, 0.0F);
      this.shapeplate = new ModelRenderer(this, 0, 16);
      this.shapeplate.setRotationPoint(-5.5F, 7.0F, 0.5F);
      this.shapeplate.addBox(0.0F, 0.0F, 0.0F, 0, 8, 8, 0.0F);
      this.spike1 = new ModelRenderer(this, 16, 24);
      this.spike1.setRotationPoint(-6.5F, 0.0F, 7.5F);
      this.spike1.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 0, 0.0F);
      this.setRotateAngle(this.spike1, 0.0F, 0.22759093F, 0.0F);
      this.shapeinvert = new ModelRenderer(this, 22, 9);
      this.shapeinvert.setRotationPoint(-6.5F, 0.0F, 1.5F);
      this.shapeinvert.addBox(0.0F, 0.0F, 0.0F, 3, 6, 2, 0.0F);
      this.setRotateAngle(this.shapeinvert, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape_1 = new ModelRenderer(this, 14, 0);
      this.shape_1.setRotationPoint(-6.5F, 0.0F, 8.5F);
      this.shape_1.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
      this.shape = new ModelRenderer(this, 0, 0);
      this.shape.setRotationPoint(-6.5F, 0.0F, -1.5F);
      this.shape.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      AbstractMobModel.light(180, false);
      this.shapeinvert.render(f5);
      AbstractMobModel.returnlight();
      this.shapessh_2.render(f5);
      this.spike2.render(f5);
      this.shapessh_1.render(f5);
      this.shapessh.render(f5);
      this.shape_2.render(f5);
      this.spike3.render(f5);
      this.spike1.render(f5);
      this.shape_1.render(f5);
      this.shape.render(f5);
      this.shapemain.render(f5);
      this.shapeplate.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
