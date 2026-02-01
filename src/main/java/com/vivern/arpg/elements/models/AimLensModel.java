package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class AimLensModel extends ModelBase {
   public ModelRenderer lens;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;

   public AimLensModel() {
      this.textureWidth = 22;
      this.textureHeight = 11;
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-3.7F, -5.5F, -5.0F, 3, 3, 1, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 0);
      this.shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape4.addBox(-3.2F, -8.8F, -4.5F, 2, 1, 9, 0.0F);
      this.shape3 = new ModelRenderer(this, 13, 0);
      this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape3.addBox(-3.2F, -7.8F, -4.5F, 2, 3, 1, 0.0F);
      this.lens = new ModelRenderer(this, 0, 4);
      this.lens.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.lens.addBox(-3.0F, -4.8F, -6.0F, 2, 2, 2, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape2.addBox(-4.7F, -4.8F, -4.5F, 1, 2, 9, 0.0F);
      this.shape5 = new ModelRenderer(this, 13, 0);
      this.shape5.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape5.addBox(-3.7F, -7.8F, 3.5F, 2, 5, 1, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape1.render(f5);
      this.shape4.render(f5);
      this.shape3.render(f5);
      this.shape2.render(f5);
      this.shape5.render(f5);
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 180.0F, 180.0F);
      GL11.glColor3f(f, f1, f2);
      this.lens.render(f5);
      GL11.glEnable(2896);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
