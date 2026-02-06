package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.renders.ModelRendererGlow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class EtheriteInvocatorModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;

   public EtheriteInvocatorModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.shape4 = new ModelRenderer(this, 0, 50);
      this.shape4.setRotationPoint(-4.0F, 5.0F, -4.0F);
      this.shape4.addBox(0.0F, 0.0F, 0.0F, 8, 3, 8, 0.0F);
      this.shape3 = new ModelRendererGlow(this, 0, 38, 240, false);
      this.shape3.setRotationPoint(-3.0F, 8.0F, -3.0F);
      this.shape3.addBox(0.0F, 0.0F, 0.0F, 6, 6, 6, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 26);
      this.shape2.setRotationPoint(-5.0F, 12.0F, -5.0F);
      this.shape2.addBox(0.0F, 0.0F, 0.0F, 10, 2, 10, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(-8.0F, 14.0F, -8.0F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 16, 10, 16, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape2.render(f5);
      this.shape1.render(f5);
      if (f == 1.0F) {
         this.shape4.render(f5);
         this.shape3.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
