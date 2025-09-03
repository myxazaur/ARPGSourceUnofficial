//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class HolographicShieldModel extends ModelBase {
   public ModelRenderer shapessh;
   public ModelRenderer shapessh_1;
   public ModelRenderer shapessh_2;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer shape8;
   public ModelRenderer shape9;

   public HolographicShieldModel() {
      this.textureWidth = 38;
      this.textureHeight = 32;
      this.shapessh_2 = new ModelRenderer(this, 28, 27);
      this.shapessh_2.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh_2.addBox(3.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh_2, 0.0F, -0.13665928F, (float) (Math.PI / 2));
      this.shape9 = new ModelRenderer(this, 15, 0);
      this.shape9.setRotationPoint(-4.0F, 5.3F, -0.5F);
      this.shape9.addBox(-0.5F, -2.9F, -0.4F, 1, 2, 2, 0.0F);
      this.setRotateAngle(this.shape9, 0.63739425F, -0.4098033F, 0.31869712F);
      this.shape1 = new ModelRenderer(this, 8, 0);
      this.shape1.setRotationPoint(-3.0F, -6.0F, 6.3F);
      this.shape1.addBox(-1.5F, -0.5F, 0.0F, 1, 9, 4, 0.0F);
      this.setRotateAngle(this.shape1, -0.95609134F, 0.0F, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 16);
      this.shape3.setRotationPoint(-4.0F, -4.2F, 6.9F);
      this.shape3.addBox(-0.5F, -0.5F, 0.0F, 1, 12, 4, 0.0F);
      this.setRotateAngle(this.shape3, -0.4098033F, 0.0F, 0.0F);
      this.shapessh_1 = new ModelRenderer(this, 28, 27);
      this.shapessh_1.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh_1.addBox(-1.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh_1, 0.0F, -0.13665928F, (float) (Math.PI / 2));
      this.shapessh = new ModelRenderer(this, 28, 27);
      this.shapessh.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh.addBox(-0.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh, 0.13665928F, 0.0F, 0.0F);
      this.shape6 = new ModelRenderer(this, 23, 6);
      this.shape6.setRotationPoint(-4.0F, -3.9F, 7.5F);
      this.shape6.addBox(-0.5F, -8.5F, -8.0F, 1, 8, 6, 0.0F);
      this.setRotateAngle(this.shape6, -1.0016445F, 0.045553092F, 0.4098033F);
      this.shape8 = new ModelRenderer(this, 18, 0);
      this.shape8.setRotationPoint(-4.0F, 0.1F, 1.5F);
      this.shape8.addBox(-0.5F, -3.9F, -2.5F, 1, 4, 4, 0.0F);
      this.setRotateAngle(this.shape8, 0.63739425F, -0.31869712F, 0.31869712F);
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(-3.0F, 0.2F, 9.0F);
      this.shape2.addBox(-1.5F, -0.5F, 0.0F, 1, 8, 3, 0.0F);
      this.setRotateAngle(this.shape2, -1.0927507F, 0.0F, 0.0F);
      this.shape4 = new ModelRenderer(this, 16, 15);
      this.shape4.setRotationPoint(-4.0F, -4.9F, 7.5F);
      this.shape4.addBox(-0.5F, -12.5F, 0.0F, 1, 12, 5, 0.0F);
      this.setRotateAngle(this.shape4, -0.4553564F, 0.0F, 0.31869712F);
      this.shape7 = new ModelRenderer(this, 10, 16);
      this.shape7.setRotationPoint(-4.0F, -2.9F, 13.5F);
      this.shape7.addBox(-0.5F, -8.1F, 1.2F, 1, 14, 2, 0.0F);
      this.setRotateAngle(this.shape7, -1.0927507F, 0.31869712F, -0.22759093F);
      this.shape5 = new ModelRenderer(this, 23, 6);
      this.shape5.setRotationPoint(-4.0F, -3.9F, 4.5F);
      this.shape5.addBox(-0.5F, -8.5F, -3.0F, 1, 8, 6, 0.0F);
      this.setRotateAngle(this.shape5, 0.63739425F, -0.22759093F, 0.31869712F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shapessh_2.render(f5);
      this.shapessh_1.render(f5);
      this.shapessh.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.5, 0.7);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
      GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.5, 0.7);
      GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
      GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
      this.shape2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(
         OpenGlHelper.lightmapTexUnit, Math.min(240.0F, OpenGlHelper.lastBrightnessX + 150.0F), Math.min(240.0F, OpenGlHelper.lastBrightnessY + 150.0F)
      );
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape9.offsetX, this.shape9.offsetY, this.shape9.offsetZ);
      GlStateManager.translate(this.shape9.rotationPointX * f5, this.shape9.rotationPointY * f5, this.shape9.rotationPointZ * f5);
      GlStateManager.scale(0.5, 1.2, 0.6);
      GlStateManager.translate(-this.shape9.offsetX, -this.shape9.offsetY, -this.shape9.offsetZ);
      GlStateManager.translate(-this.shape9.rotationPointX * f5, -this.shape9.rotationPointY * f5, -this.shape9.rotationPointZ * f5);
      this.shape9.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
      GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
      GlStateManager.scale(0.5, 1.1, 1.7);
      GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
      GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
      this.shape3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape6.offsetX, this.shape6.offsetY, this.shape6.offsetZ);
      GlStateManager.translate(this.shape6.rotationPointX * f5, this.shape6.rotationPointY * f5, this.shape6.rotationPointZ * f5);
      GlStateManager.scale(0.5, 1.2, 0.6);
      GlStateManager.translate(-this.shape6.offsetX, -this.shape6.offsetY, -this.shape6.offsetZ);
      GlStateManager.translate(-this.shape6.rotationPointX * f5, -this.shape6.rotationPointY * f5, -this.shape6.rotationPointZ * f5);
      this.shape6.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape8.offsetX, this.shape8.offsetY, this.shape8.offsetZ);
      GlStateManager.translate(this.shape8.rotationPointX * f5, this.shape8.rotationPointY * f5, this.shape8.rotationPointZ * f5);
      GlStateManager.scale(0.5, 1.2, 0.6);
      GlStateManager.translate(-this.shape8.offsetX, -this.shape8.offsetY, -this.shape8.offsetZ);
      GlStateManager.translate(-this.shape8.rotationPointX * f5, -this.shape8.rotationPointY * f5, -this.shape8.rotationPointZ * f5);
      this.shape8.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape4.offsetX, this.shape4.offsetY, this.shape4.offsetZ);
      GlStateManager.translate(this.shape4.rotationPointX * f5, this.shape4.rotationPointY * f5, this.shape4.rotationPointZ * f5);
      GlStateManager.scale(0.5, 0.7, 1.2);
      GlStateManager.translate(-this.shape4.offsetX, -this.shape4.offsetY, -this.shape4.offsetZ);
      GlStateManager.translate(-this.shape4.rotationPointX * f5, -this.shape4.rotationPointY * f5, -this.shape4.rotationPointZ * f5);
      this.shape4.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape7.offsetX, this.shape7.offsetY, this.shape7.offsetZ);
      GlStateManager.translate(this.shape7.rotationPointX * f5, this.shape7.rotationPointY * f5, this.shape7.rotationPointZ * f5);
      GlStateManager.scale(0.5, 1.2, 0.6);
      GlStateManager.translate(-this.shape7.offsetX, -this.shape7.offsetY, -this.shape7.offsetZ);
      GlStateManager.translate(-this.shape7.rotationPointX * f5, -this.shape7.rotationPointY * f5, -this.shape7.rotationPointZ * f5);
      this.shape7.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape5.offsetX, this.shape5.offsetY, this.shape5.offsetZ);
      GlStateManager.translate(this.shape5.rotationPointX * f5, this.shape5.rotationPointY * f5, this.shape5.rotationPointZ * f5);
      GlStateManager.scale(0.5, 1.2, 0.6);
      GlStateManager.translate(-this.shape5.offsetX, -this.shape5.offsetY, -this.shape5.offsetZ);
      GlStateManager.translate(-this.shape5.rotationPointX * f5, -this.shape5.rotationPointY * f5, -this.shape5.rotationPointZ * f5);
      this.shape5.render(f5);
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
