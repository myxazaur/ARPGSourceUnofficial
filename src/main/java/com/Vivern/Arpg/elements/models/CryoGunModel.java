//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class CryoGunModel extends ModelBase {
   public ModelRenderer shapessh;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer shape8;
   public ModelRenderer shape9;

   public CryoGunModel() {
      this.textureWidth = 22;
      this.textureHeight = 32;
      this.shape9 = new ModelRenderer(this, 17, 19);
      this.shape9.setRotationPoint(0.9F, 3.7F, -1.0F);
      this.shape9.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.shape9, 0.13665928F, 0.0F, 0.0F);
      this.shape5 = new ModelRenderer(this, 0, 28);
      this.shape5.setRotationPoint(0.0F, 2.7F, -3.9F);
      this.shape5.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 1, 0.0F);
      this.shapessh = new ModelRenderer(this, 18, 27);
      this.shapessh.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh.addBox(-0.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh, 0.13665928F, 0.0F, 0.0F);
      this.shape7 = new ModelRenderer(this, 10, 6);
      this.shape7.setRotationPoint(0.0F, 2.0F, 0.15F);
      this.shape7.addBox(-1.5F, -1.5F, -1.5F, 3, 4, 2, 0.0F);
      this.setRotateAngle(this.shape7, -0.95609134F, 0.0F, 0.0F);
      this.shape6 = new ModelRenderer(this, 8, 18);
      this.shape6.setRotationPoint(0.0F, 2.7F, -8.5F);
      this.shape6.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 4, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 15);
      this.shape2.setRotationPoint(0.0F, 4.5F, 0.0F);
      this.shape2.addBox(-1.5F, -1.5F, -1.5F, 3, 11, 1, 0.0F);
      this.setRotateAngle(this.shape2, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape3 = new ModelRenderer(this, 8, 12);
      this.shape3.setRotationPoint(0.0F, 3.7F, -1.0F);
      this.shape3.addBox(-0.7F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
      this.setRotateAngle(this.shape3, 0.13665928F, 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 2.0F);
      this.shape1.addBox(-1.5F, -1.5F, -1.5F, 3, 13, 2, 0.0F);
      this.setRotateAngle(this.shape1, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape8 = new ModelRenderer(this, 9, 28);
      this.shape8.setRotationPoint(0.0F, 2.7F, -4.5F);
      this.shape8.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 3, 0.0F);
      this.shape4 = new ModelRenderer(this, 10, 0);
      this.shape4.setRotationPoint(0.0F, 2.7F, -3.0F);
      this.shape4.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape6.render(f5);
      this.shape2.render(f5);
      this.shape3.render(f5);
      this.shape1.render(f5);
      this.shapessh.render(f5);
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 170.0F, 170.0F);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape9.offsetX, this.shape9.offsetY, this.shape9.offsetZ);
      GlStateManager.translate(this.shape9.rotationPointX * f5, this.shape9.rotationPointY * f5, this.shape9.rotationPointZ * f5);
      GlStateManager.scale(3.6, 0.9, 0.9);
      GlStateManager.translate(-this.shape9.offsetX, -this.shape9.offsetY, -this.shape9.offsetZ);
      GlStateManager.translate(-this.shape9.rotationPointX * f5, -this.shape9.rotationPointY * f5, -this.shape9.rotationPointZ * f5);
      this.shape9.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape5.offsetX, this.shape5.offsetY, this.shape5.offsetZ);
      GlStateManager.translate(this.shape5.rotationPointX * f5, this.shape5.rotationPointY * f5, this.shape5.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.2, 1.2);
      GlStateManager.translate(-this.shape5.offsetX, -this.shape5.offsetY, -this.shape5.offsetZ);
      GlStateManager.translate(-this.shape5.rotationPointX * f5, -this.shape5.rotationPointY * f5, -this.shape5.rotationPointZ * f5);
      this.shape5.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape7.offsetX, this.shape7.offsetY, this.shape7.offsetZ);
      GlStateManager.translate(this.shape7.rotationPointX * f5, this.shape7.rotationPointY * f5, this.shape7.rotationPointZ * f5);
      GlStateManager.scale(0.9, 1.15, 1.0);
      GlStateManager.translate(-this.shape7.offsetX, -this.shape7.offsetY, -this.shape7.offsetZ);
      GlStateManager.translate(-this.shape7.rotationPointX * f5, -this.shape7.rotationPointY * f5, -this.shape7.rotationPointZ * f5);
      this.shape7.render(f5);
      GlStateManager.popMatrix();
      this.shape8.render(f5);
      GL11.glEnable(2896);
      GlStateManager.popMatrix();
      this.shape4.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
