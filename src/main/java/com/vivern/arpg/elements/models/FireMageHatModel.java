package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class FireMageHatModel extends ModelBiped {
   public ModelRenderer cubea3;
   public ModelRenderer cubea2;
   public ModelRenderer cubea1;
   public ModelRenderer cube3;
   public ModelRenderer cubea;
   public ModelRenderer cube2;
   public ModelRenderer cube1;

   public FireMageHatModel() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.cubea1 = new ModelRenderer(this, 24, 0);
      this.cubea1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cubea1.addBox(7.8F, -9.3F, -0.3F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.cubea1, 0.0F, 0.0F, -1.3658947F);
      this.cubea3 = new ModelRenderer(this, 0, 19);
      this.cubea3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cubea3.addBox(8.4F, -19.3F, -0.3F, 1, 10, 1, 0.0F);
      this.setRotateAngle(this.cubea3, -0.045553092F, 0.0F, -0.22759093F);
      this.cube3 = new ModelRenderer(this, 52, 16);
      this.cube3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cube3.addBox(-1.5F, -18.5F, -2.4F, 3, 7, 3, 0.0F);
      this.setRotateAngle(this.cube3, -0.13665928F, 0.0F, 0.0F);
      this.cube2 = new ModelRenderer(this, 33, 20);
      this.cube2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cube2.addBox(-3.0F, -11.8F, -3.2F, 6, 4, 6, 0.0F);
      this.setRotateAngle(this.cube2, -0.045553092F, 0.0F, 0.0F);
      this.cube1 = new ModelRenderer(this, 0, 19);
      this.cube1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cube1.addBox(-5.5F, -8.02F, -5.5F, 11, 2, 11, 0.0F);
      this.cubea = new ModelRenderer(this, 32, 0);
      this.cubea.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cubea.addBox(-9.8F, -9.3F, -0.3F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.cubea, 0.0F, 0.0F, 1.3658947F);
      this.cubea2 = new ModelRenderer(this, 5, 19);
      this.cubea2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cubea2.addBox(-9.4F, -19.3F, -0.3F, 1, 10, 1, 0.0F);
      this.setRotateAngle(this.cubea2, -0.045553092F, 0.0F, 0.22759093F);
      this.bipedHead.addChild(this.cube1);
      this.bipedHead.addChild(this.cube2);
      this.bipedHead.addChild(this.cube3);
      this.bipedHead.addChild(this.cubea);
      this.bipedHead.addChild(this.cubea1);
      this.bipedHead.addChild(this.cubea2);
      this.bipedHead.addChild(this.cubea3);
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
