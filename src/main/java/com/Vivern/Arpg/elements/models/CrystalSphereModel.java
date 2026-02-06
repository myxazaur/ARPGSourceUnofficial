package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class CrystalSphereModel extends ModelBase {
   public ModelRenderer shapeIn;
   public ModelRenderer plate;
   public ModelRenderer pod;
   public ModelRenderer shapeOver;

   public CrystalSphereModel() {
      this.textureWidth = 24;
      this.textureHeight = 18;
      this.pod = new ModelRenderer(this, 16, 13);
      this.pod.setRotationPoint(0.0F, 21.0F, 0.0F);
      this.pod.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
      this.shapeIn = new ModelRenderer(this, 0, 0);
      this.shapeIn.setRotationPoint(0.0F, 18.0F, 0.0F);
      this.shapeIn.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
      this.plate = new ModelRenderer(this, 0, 12);
      this.plate.setRotationPoint(0.0F, 23.0F, 0.0F);
      this.plate.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 4, 0.0F);
      this.shapeOver = new ModelRenderer(this, 0, 0);
      this.shapeOver.setRotationPoint(0.0F, 18.0F, 0.0F);
      this.shapeOver.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.1F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f == -1.0F) {
         this.pod.render(f5);
         this.plate.render(f5);
      } else if (f == 0.0F) {
         GlStateManager.enableBlend();
         GlStateManager.color(f1, f2, f3);
         this.shapeIn.render(f5);
         GlStateManager.color(1.0F, 1.0F, 1.0F);
         GlStateManager.disableBlend();
      } else if (f == 1.0F) {
         this.shapeOver.render(f5);
      } else {
         this.shapeIn.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
