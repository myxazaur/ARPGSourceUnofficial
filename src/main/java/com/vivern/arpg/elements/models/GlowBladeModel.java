package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class GlowBladeModel extends ModelBase {
   public ModelRenderer shapess;
   public ModelRenderer shapecore;
   public ModelRenderer shapess2;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape5_1;

   public GlowBladeModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shape5_1 = new ModelRenderer(this, 4, 4);
      this.shape5_1.setRotationPoint(-1.5F, -3.0F, 0.0F);
      this.shape5_1.addBox(-0.5F, -2.1F, -0.5F, 4, 2, 2, 0.0F);
      this.setRotateAngle(this.shape5_1, 0.25132743F, 0.0F, 0.0F);
      this.shapess2 = new ModelRenderer(this, 4, 0);
      this.shapess2.setRotationPoint(-0.02F, 0.0F, 0.0F);
      this.shapess2.addBox(-0.5F, -3.4F, 0.1F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.shapess2, 0.31869712F, 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 6, 8);
      this.shape1.setRotationPoint(-0.02F, -3.0F, 0.0F);
      this.shape1.addBox(-3.0F, -7.0F, 0.0F, 2, 6, 1, 0.0F);
      this.setRotateAngle(this.shape1, 0.25132743F, 0.0F, 0.0F);
      this.shapess = new ModelRenderer(this, 0, 0);
      this.shapess.setRotationPoint(0.0F, 4.0F, 4.5F);
      this.shapess.addBox(-0.5F, -0.7F, -0.1F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.shapess, 1.0016445F, 0.0F, 0.0F);
      this.shapecore = new ModelRenderer(this, 0, 6);
      this.shapecore.setRotationPoint(0.0F, 4.0F, 4.5F);
      this.shapecore.addBox(-0.5F, -13.0F, 0.25F, 1, 8, 2, 0.0F);
      this.setRotateAngle(this.shapecore, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape3 = new ModelRenderer(this, 12, 8);
      this.shape3.setRotationPoint(-0.02F, -3.0F, 0.0F);
      this.shape3.addBox(-2.0F, -13.0F, 0.0F, 1, 6, 1, 0.0F);
      this.setRotateAngle(this.shape3, 0.25132743F, 0.0F, 0.0F);
      this.shape5 = new ModelRenderer(this, 8, 0);
      this.shape5.setRotationPoint(-0.5F, -4.8F, 0.7F);
      this.shape5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.shape5, 0.25132743F, 0.0F, 0.0F);
      this.shape2 = new ModelRenderer(this, 6, 8);
      this.shape2.mirror = true;
      this.shape2.setRotationPoint(-0.02F, -3.0F, 0.0F);
      this.shape2.addBox(1.0F, -7.0F, 0.0F, 2, 6, 1, 0.0F);
      this.setRotateAngle(this.shape2, 0.25132743F, 0.0F, 0.0F);
      this.shape4 = new ModelRenderer(this, 12, 8);
      this.shape4.mirror = true;
      this.shape4.setRotationPoint(-0.02F, -3.0F, 0.0F);
      this.shape4.addBox(1.0F, -13.0F, 0.0F, 1, 6, 1, 0.0F);
      this.setRotateAngle(this.shape4, 0.25132743F, 0.0F, 0.0F);
      this.shapess2.addChild(this.shape5_1);
      this.shapess.addChild(this.shapess2);
      this.shapess2.addChild(this.shape1);
      this.shapess2.addChild(this.shape3);
      this.shapess2.addChild(this.shape5);
      this.shapess2.addChild(this.shape2);
      this.shapess2.addChild(this.shape4);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess.offsetX, this.shapess.offsetY, this.shapess.offsetZ);
      GlStateManager.translate(this.shapess.rotationPointX * f5, this.shapess.rotationPointY * f5, this.shapess.rotationPointZ * f5);
      GlStateManager.scale(1.0, 0.6, 1.0);
      GlStateManager.translate(-this.shapess.offsetX, -this.shapess.offsetY, -this.shapess.offsetZ);
      GlStateManager.translate(-this.shapess.rotationPointX * f5, -this.shapess.rotationPointY * f5, -this.shapess.rotationPointZ * f5);
      this.shapess.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 170.0F, 170.0F);
      GlStateManager.translate(this.shapecore.offsetX, this.shapecore.offsetY, this.shapecore.offsetZ);
      GlStateManager.translate(this.shapecore.rotationPointX * f5, this.shapecore.rotationPointY * f5, this.shapecore.rotationPointZ * f5);
      GlStateManager.scale(1.0, 0.6, 1.0);
      GlStateManager.translate(-this.shapecore.offsetX, -this.shapecore.offsetY, -this.shapecore.offsetZ);
      GlStateManager.translate(-this.shapecore.rotationPointX * f5, -this.shapecore.rotationPointY * f5, -this.shapecore.rotationPointZ * f5);
      this.shapecore.render(f5);
      GL11.glEnable(2896);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
