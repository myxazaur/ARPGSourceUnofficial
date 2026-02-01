package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MagicHoodie extends ModelBiped {
   public ModelRenderer hoodie1;
   public ModelRenderer hoodie2;

   public MagicHoodie() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.hoodie2 = new ModelRenderer(this, 0, 8);
      this.hoodie2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.hoodie2.addBox(-23.0F, -3.1F, -3.3F, 16, 4, 4, 0.0F);
      this.setRotateAngle(this.hoodie2, 0.0F, 0.18203785F, -1.4486233F);
      this.hoodie1 = new ModelRenderer(this, 0, 0);
      this.hoodie1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.hoodie1.addBox(-23.0F, -1.0F, -3.3F, 16, 4, 4, 0.0F);
      this.setRotateAngle(this.hoodie1, 0.0F, 0.18203785F, -1.6929693F);
      this.bipedBody.addChild(this.hoodie1);
      this.bipedBody.addChild(this.hoodie2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.bipedLeftArm.offsetX = -0.04F;
      this.bipedRightArm.offsetX = 0.04F;
      this.bipedLeftArm.offsetY = -0.01F;
      this.bipedRightArm.offsetY = -0.01F;
      this.bipedBody.offsetY = -0.01F;
      this.bipedBody.offsetZ = -0.005F;
      super.render(entity, f, f1, f2, f3, f4, f5 * 1.1F);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
