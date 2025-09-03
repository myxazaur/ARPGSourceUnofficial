//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class PistolFishModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer sting1;
   public ModelRenderer sting2;
   public ModelRenderer sting3;
   public ModelRenderer sting4;
   public ModelRenderer plvshape1;
   public ModelRenderer plvshapeback;
   public ModelRenderer plvshape2;

   public PistolFishModel() {
      this.textureWidth = 22;
      this.textureHeight = 28;
      this.sting1 = new ModelRenderer(this, 0, 9);
      this.sting1.setRotationPoint(0.0F, 3.1F, -2.8F);
      this.sting1.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.sting1, -0.22759093F, 0.0F, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 1);
      this.shape2.setRotationPoint(0.0F, -1.0F, 0.0F);
      this.shape2.addBox(-1.5F, 0.0F, 0.0F, 3, 3, 5, 0.0F);
      this.setRotateAngle(this.shape2, -0.045553092F, 0.0F, 0.0F);
      this.plvshapeback = new ModelRenderer(this, 0, 23);
      this.plvshapeback.setRotationPoint(0.0F, -0.5F, 3.0F);
      this.plvshapeback.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 2, 0.0F);
      this.setRotateAngle(this.plvshapeback, -0.27314404F, 0.0F, 0.0F);
      this.sting4 = new ModelRenderer(this, 0, 9);
      this.sting4.setRotationPoint(0.0F, 1.4F, -0.1F);
      this.sting4.addBox(-0.5F, 0.0F, -0.5F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.sting4, (float) (-Math.PI / 2), 0.0F, 0.0F);
      this.shape6 = new ModelRenderer(this, 11, 9);
      this.shape6.setRotationPoint(0.0F, -0.5F, 4.8F);
      this.shape6.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 3, 0.0F);
      this.setRotateAngle(this.shape6, -0.13665928F, 0.0F, 0.0F);
      this.shape5 = new ModelRenderer(this, 11, 14);
      this.shape5.setRotationPoint(0.0F, 3.1F, -3.1F);
      this.shape5.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
      this.setRotateAngle(this.shape5, (float) (Math.PI / 3), 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 4, 9);
      this.shape1.setRotationPoint(0.0F, 1.9F, 5.0F);
      this.shape1.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 2, 0.0F);
      this.setRotateAngle(this.shape1, (float) (-Math.PI / 2), 0.0F, 0.0F);
      this.shape7 = new ModelRenderer(this, 11, 9);
      this.shape7.setRotationPoint(0.0F, 0.0F, 7.6F);
      this.shape7.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 3, 0.0F);
      this.setRotateAngle(this.shape7, -0.3642502F, 0.0F, 0.0F);
      this.shape3 = new ModelRenderer(this, 6, 23);
      this.shape3.setRotationPoint(0.0F, -1.3F, -2.1F);
      this.shape3.addBox(-1.5F, 0.0F, 0.0F, 3, 2, 3, 0.0F);
      this.setRotateAngle(this.shape3, -0.13665928F, 0.0F, 0.0F);
      this.sting2 = new ModelRenderer(this, 0, 9);
      this.sting2.setRotationPoint(0.0F, 2.7F, -2.6F);
      this.sting2.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.sting2, -0.95609134F, 0.0F, 0.0F);
      this.sting3 = new ModelRenderer(this, 0, 9);
      this.sting3.setRotationPoint(0.0F, 2.0F, -2.1F);
      this.sting3.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
      this.setRotateAngle(this.sting3, -1.3203416F, 0.0F, 0.0F);
      this.shape4 = new ModelRenderer(this, 9, 17);
      this.shape4.setRotationPoint(0.0F, 1.7F, -2.3F);
      this.shape4.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
      this.setRotateAngle(this.shape4, 0.22759093F, 0.0F, 0.0F);
      this.plvshape2 = new ModelRenderer(this, 0, 19);
      this.plvshape2.setRotationPoint(0.0F, 1.3F, 0.9F);
      this.plvshape2.addBox(-1.5F, 0.0F, 0.0F, 3, 2, 2, 0.0F);
      this.setRotateAngle(this.plvshape2, -0.045553092F, 0.0F, 0.0F);
      this.shapess1 = new ModelRenderer(this, 0, 0);
      this.shapess1.setRotationPoint(0.0F, 2.5F, 4.5F);
      this.shapess1.addBox(-0.5F, -0.7F, 0.0F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.shapess1, 0.18203785F, 0.0F, 0.0F);
      this.plvshape1 = new ModelRenderer(this, 11, 0);
      this.plvshape1.setRotationPoint(0.0F, -2.7F, 1.0F);
      this.plvshape1.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 4, 0.0F);
      this.setRotateAngle(this.plvshape1, -0.045553092F, 0.0F, 0.0F);
      this.shape7.addChild(this.plvshapeback);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.sting1.offsetX, this.sting1.offsetY, this.sting1.offsetZ);
      GlStateManager.translate(this.sting1.rotationPointX * f5, this.sting1.rotationPointY * f5, this.sting1.rotationPointZ * f5);
      GlStateManager.scale(0.5, 0.4, 0.4);
      GlStateManager.translate(-this.sting1.offsetX, -this.sting1.offsetY, -this.sting1.offsetZ);
      GlStateManager.translate(-this.sting1.rotationPointX * f5, -this.sting1.rotationPointY * f5, -this.sting1.rotationPointZ * f5);
      this.sting1.render(f5);
      GlStateManager.popMatrix();
      this.shape2.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.sting4.offsetX, this.sting4.offsetY, this.sting4.offsetZ);
      GlStateManager.translate(this.sting4.rotationPointX * f5, this.sting4.rotationPointY * f5, this.sting4.rotationPointZ * f5);
      GlStateManager.scale(0.57, 0.57, 0.6);
      GlStateManager.translate(-this.sting4.offsetX, -this.sting4.offsetY, -this.sting4.offsetZ);
      GlStateManager.translate(-this.sting4.rotationPointX * f5, -this.sting4.rotationPointY * f5, -this.sting4.rotationPointZ * f5);
      this.sting4.render(f5);
      GlStateManager.popMatrix();
      this.shape6.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape5.offsetX, this.shape5.offsetY, this.shape5.offsetZ);
      GlStateManager.translate(this.shape5.rotationPointX * f5, this.shape5.rotationPointY * f5, this.shape5.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.shape5.offsetX, -this.shape5.offsetY, -this.shape5.offsetZ);
      GlStateManager.translate(-this.shape5.rotationPointX * f5, -this.shape5.rotationPointY * f5, -this.shape5.rotationPointZ * f5);
      this.shape5.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(1.1, 0.6, 0.85);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape7.offsetX, this.shape7.offsetY, this.shape7.offsetZ);
      GlStateManager.translate(this.shape7.rotationPointX * f5, this.shape7.rotationPointY * f5, this.shape7.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.shape7.offsetX, -this.shape7.offsetY, -this.shape7.offsetZ);
      GlStateManager.translate(-this.shape7.rotationPointX * f5, -this.shape7.rotationPointY * f5, -this.shape7.rotationPointZ * f5);
      this.shape7.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
      GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
      GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
      this.shape3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.sting2.offsetX, this.sting2.offsetY, this.sting2.offsetZ);
      GlStateManager.translate(this.sting2.rotationPointX * f5, this.sting2.rotationPointY * f5, this.sting2.rotationPointZ * f5);
      GlStateManager.scale(0.55, 0.55, 0.55);
      GlStateManager.translate(-this.sting2.offsetX, -this.sting2.offsetY, -this.sting2.offsetZ);
      GlStateManager.translate(-this.sting2.rotationPointX * f5, -this.sting2.rotationPointY * f5, -this.sting2.rotationPointZ * f5);
      this.sting2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.sting3.offsetX, this.sting3.offsetY, this.sting3.offsetZ);
      GlStateManager.translate(this.sting3.rotationPointX * f5, this.sting3.rotationPointY * f5, this.sting3.rotationPointZ * f5);
      GlStateManager.scale(0.6, 0.6, 0.6);
      GlStateManager.translate(-this.sting3.offsetX, -this.sting3.offsetY, -this.sting3.offsetZ);
      GlStateManager.translate(-this.sting3.rotationPointX * f5, -this.sting3.rotationPointY * f5, -this.sting3.rotationPointZ * f5);
      this.sting3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape4.offsetX, this.shape4.offsetY, this.shape4.offsetZ);
      GlStateManager.translate(this.shape4.rotationPointX * f5, this.shape4.rotationPointY * f5, this.shape4.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.shape4.offsetX, -this.shape4.offsetY, -this.shape4.offsetZ);
      GlStateManager.translate(-this.shape4.rotationPointX * f5, -this.shape4.rotationPointY * f5, -this.shape4.rotationPointZ * f5);
      this.shape4.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.plvshape2.offsetX, this.plvshape2.offsetY, this.plvshape2.offsetZ);
      GlStateManager.translate(this.plvshape2.rotationPointX * f5, this.plvshape2.rotationPointY * f5, this.plvshape2.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 1.0);
      GlStateManager.translate(-this.plvshape2.offsetX, -this.plvshape2.offsetY, -this.plvshape2.offsetZ);
      GlStateManager.translate(-this.plvshape2.rotationPointX * f5, -this.plvshape2.rotationPointY * f5, -this.plvshape2.rotationPointZ * f5);
      this.plvshape2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
      GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
      GlStateManager.scale(0.9, 1.0, 1.2);
      GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
      GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
      this.shapess1.render(f5);
      GlStateManager.popMatrix();
      this.plvshape1.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void setRotationAngles() {
      float angle = 0.0F;
      if (AnimationTimer.tick / 35 % 10 == 0) {
         angle = (float)Math.sin(AnimationTimer.tick / 1.5F) * 10.0F;
      }

      this.shape7.rotateAngleY = angle * (float) (Math.PI / 180.0);
      this.plvshapeback.rotateAngleY = angle * (float) (Math.PI / 180.0);
   }
}
