//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class MoltenGreataxeModel extends ModelBase {
   public ModelRenderer staf;
   public ModelRenderer base;
   public ModelRenderer blade1;
   public ModelRenderer blade2;
   public ModelRenderer blade3;
   public ModelRenderer blade4;
   public ModelRenderer blade5;
   public ModelRenderer blade6;
   public ModelRenderer gem;

   public MoltenGreataxeModel() {
      this.textureWidth = 24;
      this.textureHeight = 32;
      this.gem = new ModelRenderer(this, 7, 23);
      this.gem.setRotationPoint(0.0F, 6.0F, 5.0F);
      this.gem.addBox(-1.0F, -12.2F, -1.0F, 2, 2, 2, 0.0F);
      this.blade5 = new ModelRenderer(this, 0, 24);
      this.blade5.setRotationPoint(0.0F, 3.0F, 5.0F);
      this.blade5.addBox(-0.5F, -7.0F, -6.7F, 1, 3, 4, 0.0F);
      this.setRotateAngle(this.blade5, -0.22759093F, 0.0F, 0.0F);
      this.blade2 = new ModelRenderer(this, 0, 11);
      this.blade2.setRotationPoint(0.0F, 3.0F, 5.0F);
      this.blade2.addBox(-0.5F, 0.0F, -4.0F, 1, 9, 2, 0.0F);
      this.setRotateAngle(this.blade2, 2.5953045F, 0.0F, 0.0F);
      this.blade6 = new ModelRenderer(this, 0, 24);
      this.blade6.mirror = true;
      this.blade6.setRotationPoint(0.0F, 3.0F, 5.0F);
      this.blade6.addBox(-0.5F, -7.0F, 2.7F, 1, 3, 4, 0.0F);
      this.setRotateAngle(this.blade6, 0.22759093F, 0.0F, 0.0F);
      this.staf = new ModelRenderer(this, 16, 0);
      this.staf.setRotationPoint(0.0F, 6.0F, 5.0F);
      this.staf.addBox(-1.0F, -12.0F, -1.0F, 2, 30, 2, 0.0F);
      this.base = new ModelRenderer(this, 0, 0);
      this.base.setRotationPoint(0.0F, 6.0F, 5.0F);
      this.base.addBox(-2.0F, -11.0F, -2.0F, 4, 4, 4, 0.0F);
      this.blade1 = new ModelRenderer(this, 0, 11);
      this.blade1.setRotationPoint(0.0F, 3.0F, 5.0F);
      this.blade1.addBox(-0.5F, -9.0F, -4.0F, 1, 9, 2, 0.0F);
      this.setRotateAngle(this.blade1, 0.5462881F, 0.0F, 0.0F);
      this.blade4 = new ModelRenderer(this, 7, 12);
      this.blade4.setRotationPoint(0.0F, 3.0F, 5.0F);
      this.blade4.addBox(-0.5F, 2.8F, -9.5F, 1, 8, 2, 0.0F);
      this.setRotateAngle(this.blade4, -2.8228955F, 0.0F, 0.0F);
      this.blade3 = new ModelRenderer(this, 7, 12);
      this.blade3.setRotationPoint(0.0F, 3.0F, 5.0F);
      this.blade3.addBox(-0.5F, -10.8F, -9.5F, 1, 8, 2, 0.0F);
      this.setRotateAngle(this.blade3, -0.31869712F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf.offsetX, this.staf.offsetY, this.staf.offsetZ);
      GlStateManager.translate(this.staf.rotationPointX * f5, this.staf.rotationPointY * f5, this.staf.rotationPointZ * f5);
      GlStateManager.scale(0.7, 1.0, 0.7);
      GlStateManager.translate(-this.staf.offsetX, -this.staf.offsetY, -this.staf.offsetZ);
      GlStateManager.translate(-this.staf.rotationPointX * f5, -this.staf.rotationPointY * f5, -this.staf.rotationPointZ * f5);
      this.staf.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.gem.offsetX, this.gem.offsetY, this.gem.offsetZ);
      GlStateManager.translate(this.gem.rotationPointX * f5, this.gem.rotationPointY * f5, this.gem.rotationPointZ * f5);
      GlStateManager.scale(1.8, 0.8, 0.8);
      GlStateManager.translate(-this.gem.offsetX, -this.gem.offsetY, -this.gem.offsetZ);
      GlStateManager.translate(-this.gem.rotationPointX * f5, -this.gem.rotationPointY * f5, -this.gem.rotationPointZ * f5);
      this.gem.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 150.0F, 150.0F);
      GlStateManager.translate(this.blade5.offsetX, this.blade5.offsetY, this.blade5.offsetZ);
      GlStateManager.translate(this.blade5.rotationPointX * f5, this.blade5.rotationPointY * f5, this.blade5.rotationPointZ * f5);
      GlStateManager.scale(0.51, 1.0, 1.0);
      GlStateManager.translate(-this.blade5.offsetX, -this.blade5.offsetY, -this.blade5.offsetZ);
      GlStateManager.translate(-this.blade5.rotationPointX * f5, -this.blade5.rotationPointY * f5, -this.blade5.rotationPointZ * f5);
      this.blade5.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.blade2.offsetX, this.blade2.offsetY, this.blade2.offsetZ);
      GlStateManager.translate(this.blade2.rotationPointX * f5, this.blade2.rotationPointY * f5, this.blade2.rotationPointZ * f5);
      GlStateManager.scale(0.7, 1.0, 0.9);
      GlStateManager.translate(-this.blade2.offsetX, -this.blade2.offsetY, -this.blade2.offsetZ);
      GlStateManager.translate(-this.blade2.rotationPointX * f5, -this.blade2.rotationPointY * f5, -this.blade2.rotationPointZ * f5);
      this.blade2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.blade6.offsetX, this.blade6.offsetY, this.blade6.offsetZ);
      GlStateManager.translate(this.blade6.rotationPointX * f5, this.blade6.rotationPointY * f5, this.blade6.rotationPointZ * f5);
      GlStateManager.scale(0.51, 1.0, 1.0);
      GlStateManager.translate(-this.blade6.offsetX, -this.blade6.offsetY, -this.blade6.offsetZ);
      GlStateManager.translate(-this.blade6.rotationPointX * f5, -this.blade6.rotationPointY * f5, -this.blade6.rotationPointZ * f5);
      this.blade6.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.base.offsetX, this.base.offsetY, this.base.offsetZ);
      GlStateManager.translate(this.base.rotationPointX * f5, this.base.rotationPointY * f5, this.base.rotationPointZ * f5);
      GlStateManager.scale(0.7, 1.0, 0.9);
      GlStateManager.translate(-this.base.offsetX, -this.base.offsetY, -this.base.offsetZ);
      GlStateManager.translate(-this.base.rotationPointX * f5, -this.base.rotationPointY * f5, -this.base.rotationPointZ * f5);
      this.base.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.blade1.offsetX, this.blade1.offsetY, this.blade1.offsetZ);
      GlStateManager.translate(this.blade1.rotationPointX * f5, this.blade1.rotationPointY * f5, this.blade1.rotationPointZ * f5);
      GlStateManager.scale(0.7, 1.0, 0.9);
      GlStateManager.translate(-this.blade1.offsetX, -this.blade1.offsetY, -this.blade1.offsetZ);
      GlStateManager.translate(-this.blade1.rotationPointX * f5, -this.blade1.rotationPointY * f5, -this.blade1.rotationPointZ * f5);
      this.blade1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.blade4.offsetX, this.blade4.offsetY, this.blade4.offsetZ);
      GlStateManager.translate(this.blade4.rotationPointX * f5, this.blade4.rotationPointY * f5, this.blade4.rotationPointZ * f5);
      GlStateManager.scale(0.71, 1.0, 0.9);
      GlStateManager.translate(-this.blade4.offsetX, -this.blade4.offsetY, -this.blade4.offsetZ);
      GlStateManager.translate(-this.blade4.rotationPointX * f5, -this.blade4.rotationPointY * f5, -this.blade4.rotationPointZ * f5);
      this.blade4.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.blade3.offsetX, this.blade3.offsetY, this.blade3.offsetZ);
      GlStateManager.translate(this.blade3.rotationPointX * f5, this.blade3.rotationPointY * f5, this.blade3.rotationPointZ * f5);
      GlStateManager.scale(0.71, 1.0, 0.9);
      GlStateManager.translate(-this.blade3.offsetX, -this.blade3.offsetY, -this.blade3.offsetZ);
      GlStateManager.translate(-this.blade3.rotationPointX * f5, -this.blade3.rotationPointY * f5, -this.blade3.rotationPointZ * f5);
      this.blade3.render(f5);
      GL11.glEnable(2896);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
