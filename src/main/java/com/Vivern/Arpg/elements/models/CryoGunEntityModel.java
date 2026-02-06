package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class CryoGunEntityModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;

   public CryoGunEntityModel() {
      this.textureWidth = 16;
      this.textureHeight = 6;
      this.shape3 = new ModelRenderer(this, 0, 0);
      this.shape3.setRotationPoint(0.0F, 0.0F, 0.9F);
      this.shape3.addBox(-2.0F, -2.0F, -2.0F, 4, 2, 4, 0.0F);
      this.setRotateAngle(this.shape3, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(0.0F, 0.0F, 1.0F);
      this.shape2.addBox(-2.0F, -2.0F, -2.0F, 4, 2, 4, 0.0F);
      this.setRotateAngle(this.shape2, (float) (Math.PI / 2), 0.0F, 0.7740535F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, -0.4F);
      this.shape1.addBox(-2.0F, -2.0F, -2.0F, 4, 2, 4, 0.0F);
      this.setRotateAngle(this.shape1, (float) (Math.PI / 2), 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 170.0F, 170.0F);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
      GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, 0.6);
      GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
      GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
      this.shape3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
      GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, 0.8);
      GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
      GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
      this.shape2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.8);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      GL11.glEnable(2896);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
