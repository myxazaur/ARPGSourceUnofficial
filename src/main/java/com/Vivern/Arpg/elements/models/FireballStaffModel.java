//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class FireballStaffModel extends ModelBase {
   public ModelRenderer staf;
   public ModelRenderer shape1;
   public ModelRenderer shape1_1;
   public ModelRenderer shape1_2;
   public ModelRenderer shape1_3;
   public ModelRenderer stone;
   public ModelRenderer shape1_4;
   public ModelRenderer shape1_5;
   public ModelRenderer shape1_6;
   public ModelRenderer shape1_7;

   public FireballStaffModel() {
      this.textureWidth = 16;
      this.textureHeight = 22;
      this.shape1_1 = new ModelRenderer(this, 0, 0);
      this.shape1_1.setRotationPoint(0.0F, -0.8F, 6.9F);
      this.shape1_1.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
      this.setRotateAngle(this.shape1_1, -0.5462881F, 0.0F, 0.0F);
      this.shape1_3 = new ModelRenderer(this, 0, 0);
      this.shape1_3.setRotationPoint(0.0F, -5.1F, 8.2F);
      this.shape1_3.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
      this.setRotateAngle(this.shape1_3, 0.95609134F, 0.0F, 0.0F);
      this.staf = new ModelRenderer(this, 8, 0);
      this.staf.setRotationPoint(0.0F, 8.0F, 5.0F);
      this.staf.addBox(-1.0F, -8.0F, -1.0F, 2, 19, 2, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.7F, 4.9F);
      this.shape1.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
      this.setRotateAngle(this.shape1, -0.8651597F, 0.0F, 0.0F);
      this.shape1_4 = new ModelRenderer(this, 0, 14);
      this.shape1_4.setRotationPoint(0.0F, -6.7F, 7.4F);
      this.shape1_4.addBox(-1.0F, -3.1F, 0.0F, 2, 3, 1, 0.0F);
      this.setRotateAngle(this.shape1_4, 0.59184116F, 0.0F, 0.0F);
      this.shape1_5 = new ModelRenderer(this, 0, 10);
      this.shape1_5.setRotationPoint(0.0F, -6.7F, 7.4F);
      this.shape1_5.addBox(-1.0F, -3.1F, -1.0F, 2, 3, 1, 0.0F);
      this.setRotateAngle(this.shape1_5, 1.5934856F, 0.0F, 0.0F);
      this.shape1_7 = new ModelRenderer(this, 0, 6);
      this.shape1_7.setRotationPoint(0.0F, -6.7F, 7.4F);
      this.shape1_7.addBox(-1.0F, 0.0F, 0.3F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.shape1_7, 0.8651597F, -0.3642502F, 0.0F);
      this.shape1_6 = new ModelRenderer(this, 0, 6);
      this.shape1_6.setRotationPoint(0.0F, -6.7F, 7.4F);
      this.shape1_6.addBox(0.0F, 0.0F, 0.3F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.shape1_6, 0.8651597F, 0.3642502F, 0.0F);
      this.stone = new ModelRenderer(this, 0, 18);
      this.stone.setRotationPoint(0.0F, -8.0F, 5.0F);
      this.stone.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.stone, 0.7740535F, 0.8196066F, 0.0F);
      this.shape1_2 = new ModelRenderer(this, 0, 0);
      this.shape1_2.setRotationPoint(0.0F, -2.6F, 8.2F);
      this.shape1_2.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
      this.setRotateAngle(this.shape1_2, 0.22759093F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_1.offsetX, this.shape1_1.offsetY, this.shape1_1.offsetZ);
      GlStateManager.translate(this.shape1_1.rotationPointX * f5, this.shape1_1.rotationPointY * f5, this.shape1_1.rotationPointZ * f5);
      GlStateManager.scale(0.7, 1.0, 0.7);
      GlStateManager.translate(-this.shape1_1.offsetX, -this.shape1_1.offsetY, -this.shape1_1.offsetZ);
      GlStateManager.translate(-this.shape1_1.rotationPointX * f5, -this.shape1_1.rotationPointY * f5, -this.shape1_1.rotationPointZ * f5);
      this.shape1_1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_3.offsetX, this.shape1_3.offsetY, this.shape1_3.offsetZ);
      GlStateManager.translate(this.shape1_3.rotationPointX * f5, this.shape1_3.rotationPointY * f5, this.shape1_3.rotationPointZ * f5);
      GlStateManager.scale(0.5, 0.8, 0.5);
      GlStateManager.translate(-this.shape1_3.offsetX, -this.shape1_3.offsetY, -this.shape1_3.offsetZ);
      GlStateManager.translate(-this.shape1_3.rotationPointX * f5, -this.shape1_3.rotationPointY * f5, -this.shape1_3.rotationPointZ * f5);
      this.shape1_3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf.offsetX, this.staf.offsetY, this.staf.offsetZ);
      GlStateManager.translate(this.staf.rotationPointX * f5, this.staf.rotationPointY * f5, this.staf.rotationPointZ * f5);
      GlStateManager.scale(0.6, 1.0, 0.6);
      GlStateManager.translate(-this.staf.offsetX, -this.staf.offsetY, -this.staf.offsetZ);
      GlStateManager.translate(-this.staf.rotationPointX * f5, -this.staf.rotationPointY * f5, -this.staf.rotationPointZ * f5);
      this.staf.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      this.shape1_4.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_5.offsetX, this.shape1_5.offsetY, this.shape1_5.offsetZ);
      GlStateManager.translate(this.shape1_5.rotationPointX * f5, this.shape1_5.rotationPointY * f5, this.shape1_5.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.95);
      GlStateManager.translate(-this.shape1_5.offsetX, -this.shape1_5.offsetY, -this.shape1_5.offsetZ);
      GlStateManager.translate(-this.shape1_5.rotationPointX * f5, -this.shape1_5.rotationPointY * f5, -this.shape1_5.rotationPointZ * f5);
      this.shape1_5.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_7.offsetX, this.shape1_7.offsetY, this.shape1_7.offsetZ);
      GlStateManager.translate(this.shape1_7.rotationPointX * f5, this.shape1_7.rotationPointY * f5, this.shape1_7.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.shape1_7.offsetX, -this.shape1_7.offsetY, -this.shape1_7.offsetZ);
      GlStateManager.translate(-this.shape1_7.rotationPointX * f5, -this.shape1_7.rotationPointY * f5, -this.shape1_7.rotationPointZ * f5);
      this.shape1_7.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_6.offsetX, this.shape1_6.offsetY, this.shape1_6.offsetZ);
      GlStateManager.translate(this.shape1_6.rotationPointX * f5, this.shape1_6.rotationPointY * f5, this.shape1_6.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.shape1_6.offsetX, -this.shape1_6.offsetY, -this.shape1_6.offsetZ);
      GlStateManager.translate(-this.shape1_6.rotationPointX * f5, -this.shape1_6.rotationPointY * f5, -this.shape1_6.rotationPointZ * f5);
      this.shape1_6.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_2.offsetX, this.shape1_2.offsetY, this.shape1_2.offsetZ);
      GlStateManager.translate(this.shape1_2.rotationPointX * f5, this.shape1_2.rotationPointY * f5, this.shape1_2.rotationPointZ * f5);
      GlStateManager.scale(0.6, 1.0, 0.6);
      GlStateManager.translate(-this.shape1_2.offsetX, -this.shape1_2.offsetY, -this.shape1_2.offsetZ);
      GlStateManager.translate(-this.shape1_2.rotationPointX * f5, -this.shape1_2.rotationPointY * f5, -this.shape1_2.rotationPointZ * f5);
      this.shape1_2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 150.0F, 150.0F);
      this.stone.render(f5);
      GL11.glEnable(2896);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
