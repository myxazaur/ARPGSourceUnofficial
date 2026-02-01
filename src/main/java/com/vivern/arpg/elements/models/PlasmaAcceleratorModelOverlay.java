package com.vivern.arpg.elements.models;

import com.vivern.arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class PlasmaAcceleratorModelOverlay extends ModelBase {
   public ModelRenderer shape7;
   public ModelRenderer shape;

   public PlasmaAcceleratorModelOverlay() {
      this.textureWidth = 14;
      this.textureHeight = 14;
      this.shape7 = new ModelRenderer(this, 0, 8);
      this.shape7.setRotationPoint(0.0F, 0.0F, 0.5F);
      this.shape7.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
      this.shape = new ModelRenderer(this, 0, 0);
      this.shape.mirror = true;
      this.shape.setRotationPoint(0.0F, 0.0F, -9.6F);
      this.shape.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
      this.setRotateAngle(this.shape, (float) (Math.PI / 2), 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      this.shape7.render(f5);
      this.shape.rotateAngleZ = AnimationTimer.tick * 0.09F;
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape.offsetX, this.shape.offsetY, this.shape.offsetZ);
      GlStateManager.translate(this.shape.rotationPointX * f5, this.shape.rotationPointY * f5, this.shape.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.shape.offsetX, -this.shape.offsetY, -this.shape.offsetZ);
      GlStateManager.translate(-this.shape.rotationPointX * f5, -this.shape.rotationPointY * f5, -this.shape.rotationPointZ * f5);
      this.shape.render(f5);
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
