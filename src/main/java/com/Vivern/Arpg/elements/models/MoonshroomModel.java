//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class MoonshroomModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer leng1;
   public ModelRenderer leng2;
   public ModelRenderer leng3;
   public ModelRenderer leng4;

   public MoonshroomModel() {
      this.textureWidth = 55;
      this.textureHeight = 27;
      this.leng1 = new ModelRenderer(this, 0, 0);
      this.leng1.mirror = true;
      this.leng1.setRotationPoint(1.5F, 8.8F, 0.0F);
      this.leng1.addBox(0.0F, -0.3F, -0.5F, 1, 15, 1, 0.0F);
      this.leng4 = new ModelRenderer(this, 0, 0);
      this.leng4.mirror = true;
      this.leng4.setRotationPoint(0.0F, 8.3F, -1.2F);
      this.leng4.addBox(-0.5F, -0.3F, -1.0F, 1, 15, 1, 0.0F);
      this.shape1 = new ModelRenderer(this, 4, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-6.0F, -5.0F, -7.0F, 12, 5, 11, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 16);
      this.shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape4.addBox(-4.0F, -5.0F, 2.0F, 8, 4, 6, 0.0F);
      this.setRotateAngle(this.shape4, -0.4098033F, 0.0F, 0.0F);
      this.leng2 = new ModelRenderer(this, 0, 0);
      this.leng2.setRotationPoint(-1.5F, 9.0F, 0.0F);
      this.leng2.addBox(-1.0F, -0.3F, -0.5F, 1, 15, 1, 0.0F);
      this.shape3 = new ModelRenderer(this, 39, 1);
      this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape3.addBox(-2.0F, 4.0F, -2.0F, 4, 6, 4, 0.0F);
      this.setRotateAngle(this.shape3, 0.045553092F, 0.0F, 0.0F);
      this.leng3 = new ModelRenderer(this, 0, 0);
      this.leng3.mirror = true;
      this.leng3.setRotationPoint(0.0F, 9.2F, 2.0F);
      this.leng3.addBox(-0.5F, -0.3F, 0.0F, 1, 15, 1, 0.0F);
      this.shape2 = new ModelRenderer(this, 28, 16);
      this.shape2.setRotationPoint(0.0F, -0.3F, 0.0F);
      this.shape2.addBox(-3.0F, 0.0F, -3.0F, 6, 4, 7, 0.0F);
      this.setRotateAngle(this.shape2, -0.091106184F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.leng1.offsetX, this.leng1.offsetY, this.leng1.offsetZ);
      GlStateManager.translate(this.leng1.rotationPointX * f5, this.leng1.rotationPointY * f5, this.leng1.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.0, 1.2);
      GlStateManager.translate(-this.leng1.offsetX, -this.leng1.offsetY, -this.leng1.offsetZ);
      GlStateManager.translate(-this.leng1.rotationPointX * f5, -this.leng1.rotationPointY * f5, -this.leng1.rotationPointZ * f5);
      this.leng1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.leng4.offsetX, this.leng4.offsetY, this.leng4.offsetZ);
      GlStateManager.translate(this.leng4.rotationPointX * f5, this.leng4.rotationPointY * f5, this.leng4.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.0, 1.2);
      GlStateManager.translate(-this.leng4.offsetX, -this.leng4.offsetY, -this.leng4.offsetZ);
      GlStateManager.translate(-this.leng4.rotationPointX * f5, -this.leng4.rotationPointY * f5, -this.leng4.rotationPointZ * f5);
      this.leng4.render(f5);
      GlStateManager.popMatrix();
      this.shape1.render(f5);
      this.shape4.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.leng2.offsetX, this.leng2.offsetY, this.leng2.offsetZ);
      GlStateManager.translate(this.leng2.rotationPointX * f5, this.leng2.rotationPointY * f5, this.leng2.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.0, 1.2);
      GlStateManager.translate(-this.leng2.offsetX, -this.leng2.offsetY, -this.leng2.offsetZ);
      GlStateManager.translate(-this.leng2.rotationPointX * f5, -this.leng2.rotationPointY * f5, -this.leng2.rotationPointZ * f5);
      this.leng2.render(f5);
      GlStateManager.popMatrix();
      this.shape3.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.leng3.offsetX, this.leng3.offsetY, this.leng3.offsetZ);
      GlStateManager.translate(this.leng3.rotationPointX * f5, this.leng3.rotationPointY * f5, this.leng3.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.0, 1.2);
      GlStateManager.translate(-this.leng3.offsetX, -this.leng3.offsetY, -this.leng3.offsetZ);
      GlStateManager.translate(-this.leng3.rotationPointX * f5, -this.leng3.rotationPointY * f5, -this.leng3.rotationPointZ * f5);
      this.leng3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
      GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.2, 1.0);
      GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
      GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
      this.shape2.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      float anglee = (float)Math.sin(AnimationTimer.tick / 40.0);
      this.leng1.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 20) / 40.0) / 3.0F;
      this.leng2.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 38) / 40.0) / 3.0F;
      this.leng3.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 61) / 40.0) / 3.0F;
      this.leng4.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 79) / 40.0) / 3.0F;
      this.leng1.rotateAngleZ = anglee / 5.0F;
      this.leng2.rotateAngleZ = -anglee / 5.0F;
      this.leng3.rotateAngleY = anglee / 9.0F;
      this.leng4.rotateAngleY = -anglee / 8.0F;
   }
}
