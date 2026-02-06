package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.renders.ModelRendererLimited;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class BeakerModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;

   public BeakerModel() {
      this.textureWidth = 16;
      this.textureHeight = 32;
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(0.0F, -2.0F, 0.0F);
      this.shape2.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
      this.shape4 = new ModelRenderer(this, 4, 6);
      this.shape4.setRotationPoint(0.0F, -3.0F, 0.0F);
      this.shape4.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
      this.shape5 = new ModelRendererLimited(this, 0, 0, false, true, false, false, false, false);
      this.shape5.setRotationPoint(0.0F, 0.0F, 4.0F);
      this.shape5.addBox(0.0F, 0.0F, -2.0F, 0, 10, 4, 0.0F);
      this.shape3 = new ModelRenderer(this, 8, 0);
      this.shape3.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.shape3.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.shape3, (float) (Math.PI / 4), 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 10);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-2.0F, 0.0F, -2.0F, 4, 18, 4, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape2.render(f5);
      this.shape4.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
      GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.2, 1.2);
      GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
      GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
      this.shape3.render(f5);
      GlStateManager.popMatrix();
      this.shape1.render(f5);
      GlStateManager.disableCull();
      this.shape5.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
