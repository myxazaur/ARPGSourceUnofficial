package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AntiRadPackModel extends ModelBase {
   public ModelRenderer pack;
   public ModelRenderer pipe;
   public ModelRenderer pipe_1;
   public ModelRenderer pipe_2;
   public ModelRenderer pipe_3;
   public ModelRenderer pipe_4;
   public ModelRenderer pipe_5;
   public ModelRenderer core;
   public ModelRenderer stick;

   public AntiRadPackModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.pack = new ModelRenderer(this, 2, 5);
      this.pack.setRotationPoint(0.0F, 1.0F, 2.0F);
      this.pack.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 3, 0.0F);
      this.pipe_2 = new ModelRenderer(this, 0, 0);
      this.pipe_2.setRotationPoint(0.0F, 6.0F, 2.0F);
      this.pipe_2.addBox(2.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
      this.pipe_4 = new ModelRenderer(this, 0, 0);
      this.pipe_4.setRotationPoint(0.0F, 4.0F, 2.0F);
      this.pipe_4.addBox(-3.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
      this.core = new ModelRenderer(this, 10, 0);
      this.core.setRotationPoint(0.0F, 6.0F, 2.0F);
      this.core.addBox(-1.0F, 0.0F, 3.0F, 2, 2, 1, 0.0F);
      this.pipe_1 = new ModelRenderer(this, 0, 0);
      this.pipe_1.setRotationPoint(0.0F, 4.0F, 2.0F);
      this.pipe_1.addBox(2.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
      this.pipe_3 = new ModelRenderer(this, 0, 0);
      this.pipe_3.setRotationPoint(0.0F, 2.0F, 2.0F);
      this.pipe_3.addBox(-3.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
      this.pipe_5 = new ModelRenderer(this, 0, 0);
      this.pipe_5.setRotationPoint(0.0F, 6.0F, 2.0F);
      this.pipe_5.addBox(-3.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
      this.stick = new ModelRenderer(this, 6, 0);
      this.stick.setRotationPoint(0.0F, 2.0F, 2.0F);
      this.stick.addBox(-1.0F, -3.0F, 1.0F, 1, 2, 1, 0.0F);
      this.pipe = new ModelRenderer(this, 0, 0);
      this.pipe.setRotationPoint(0.0F, 2.0F, 2.0F);
      this.pipe.addBox(2.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.pack.render(f5);
      this.pipe_2.render(f5);
      this.pipe_4.render(f5);
      this.pipe_1.render(f5);
      this.pipe_3.render(f5);
      this.pipe_5.render(f5);
      this.stick.render(f5);
      this.pipe.render(f5);
      AbstractMobModel.light(150, true);
      this.core.render(f5);
      AbstractMobModel.returnlight();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
