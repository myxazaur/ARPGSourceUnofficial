//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class CompoundBowModel extends ModelBase {
   public ModelRenderer shapeMAIN;
   public ModelRenderer shapeUP1;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shapeDOWN1;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shapeUP2;
   public ModelRenderer ropeUP;
   public ModelRenderer shapeUP3;
   public ModelRenderer shapeUP4;
   public ModelRenderer ropeUP2;
   public ModelRenderer shapeDOWN2;
   public ModelRenderer ropeDOWN;
   public ModelRenderer shapeDOWN3;
   public ModelRenderer shapeDOWN4;
   public ModelRenderer ropeDOWN2;

   public CompoundBowModel() {
      this.textureWidth = 25;
      this.textureHeight = 25;
      this.shapeMAIN = new ModelRenderer(this, 2, 0);
      this.shapeMAIN.setRotationPoint(0.0F, 3.5F, 5.0F);
      this.shapeMAIN.addBox(-1.0F, -4.0F, -1.1F, 2, 7, 2, 0.0F);
      this.shapeDOWN2 = new ModelRenderer(this, 10, 0);
      this.shapeDOWN2.setRotationPoint(0.0F, -6.0F, -0.5F);
      this.shapeDOWN2.addBox(-0.5F, -9.0F, -0.5F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.shapeDOWN2, -1.5025539F, 0.0F, 0.0F);
      this.shapeDOWN3 = new ModelRenderer(this, 14, -4);
      this.shapeDOWN3.setRotationPoint(0.0F, -9.0F, 0.0F);
      this.shapeDOWN3.addBox(0.0F, -2.0F, -2.0F, 0, 4, 4, 0.0F);
      this.ropeUP2 = new ModelRenderer(this, 0, -1);
      this.ropeUP2.setRotationPoint(0.4F, -9.5F, 1.0F);
      this.ropeUP2.addBox(0.0F, 0.0F, -0.5F, 0, 25, 1, 0.0F);
      this.setRotateAngle(this.ropeUP2, 0.91053826F, 0.0F, 0.0F);
      this.shapeDOWN1 = new ModelRenderer(this, 2, 9);
      this.shapeDOWN1.setRotationPoint(0.0F, 6.0F, 5.0F);
      this.shapeDOWN1.addBox(-0.5F, -6.0F, -0.8F, 1, 6, 3, 0.1F);
      this.setRotateAngle(this.shapeDOWN1, 0.5009095F, 0.0F, (float) Math.PI);
      this.shapeUP3 = new ModelRenderer(this, 14, -4);
      this.shapeUP3.setRotationPoint(0.0F, -9.0F, 0.0F);
      this.shapeUP3.addBox(0.0F, -2.0F, -2.0F, 0, 4, 4, 0.0F);
      this.shapeUP2 = new ModelRenderer(this, 10, 0);
      this.shapeUP2.setRotationPoint(0.0F, -6.0F, -0.5F);
      this.shapeUP2.addBox(-0.5F, -9.0F, -0.5F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.shapeUP2, -1.5025539F, 0.0F, 0.0F);
      this.shapeUP1 = new ModelRenderer(this, 2, 9);
      this.shapeUP1.setRotationPoint(0.0F, 0.0F, 5.0F);
      this.shapeUP1.addBox(-0.5F, -6.0F, -0.8F, 1, 6, 3, 0.1F);
      this.setRotateAngle(this.shapeUP1, 0.5009095F, 0.0F, 0.0F);
      this.ropeUP = new ModelRenderer(this, 0, -1);
      this.ropeUP.setRotationPoint(0.1F, -10.5F, 1.0F);
      this.ropeUP.addBox(0.0F, 0.0F, -0.5F, 0, 14, 1, 0.0F);
      this.setRotateAngle(this.ropeUP, 1.0016445F, 0.0F, 0.0F);
      this.ropeDOWN = new ModelRenderer(this, 0, -1);
      this.ropeDOWN.setRotationPoint(0.1F, -10.5F, 1.0F);
      this.ropeDOWN.addBox(0.0F, 0.0F, -0.5F, 0, 14, 1, 0.0F);
      this.setRotateAngle(this.ropeDOWN, 1.0016445F, 0.0F, 0.0F);
      this.shapeUP4 = new ModelRenderer(this, 14, 1);
      this.shapeUP4.setRotationPoint(0.0F, -9.0F, 0.0F);
      this.shapeUP4.addBox(0.55F, -1.5F, -1.5F, 0, 3, 3, 0.0F);
      this.shape3 = new ModelRenderer(this, 3, 19);
      this.shape3.setRotationPoint(0.0F, 0.0F, -1.0F);
      this.shape3.addBox(0.5F, -1.5F, -1.5F, 1, 1, 5, 0.0F);
      this.shape1 = new ModelRenderer(this, 7, 15);
      this.shape1.setRotationPoint(0.0F, 1.0F, 5.9F);
      this.shape1.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 3, 0.0F);
      this.shape2 = new ModelRenderer(this, 10, 8);
      this.shape2.setRotationPoint(-1.0F, 5.0F, 5.0F);
      this.shape2.addBox(-0.5F, -0.5F, 0.0F, 1, 2, 5, 0.0F);
      this.shapeDOWN4 = new ModelRenderer(this, 14, 1);
      this.shapeDOWN4.setRotationPoint(0.0F, -9.0F, 0.0F);
      this.shapeDOWN4.addBox(0.55F, -1.5F, -1.5F, 0, 3, 3, 0.0F);
      this.ropeDOWN2 = new ModelRenderer(this, 0, -1);
      this.ropeDOWN2.setRotationPoint(0.4F, -9.5F, 1.0F);
      this.ropeDOWN2.addBox(0.0F, 0.0F, -0.5F, 0, 25, 1, 0.0F);
      this.setRotateAngle(this.ropeDOWN2, 0.91053826F, 0.0F, 0.0F);
      this.shape4 = new ModelRenderer(this, 2, 18);
      this.shape4.setRotationPoint(0.0F, -0.5F, -2.0F);
      this.shape4.addBox(1.5F, -1.5F, -1.0F, 2, 2, 1, 0.0F);
      this.shapeDOWN1.addChild(this.shapeDOWN2);
      this.shapeDOWN2.addChild(this.shapeDOWN3);
      this.shapeUP2.addChild(this.ropeUP2);
      this.shapeUP2.addChild(this.shapeUP3);
      this.shapeUP1.addChild(this.shapeUP2);
      this.shapeUP2.addChild(this.ropeUP);
      this.shapeDOWN2.addChild(this.ropeDOWN);
      this.shapeUP2.addChild(this.shapeUP4);
      this.shapeMAIN.addChild(this.shape3);
      this.shapeDOWN2.addChild(this.shapeDOWN4);
      this.shapeDOWN2.addChild(this.ropeDOWN2);
      this.shapeMAIN.addChild(this.shape4);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      this.shapeUP1.rotateAngleX = GetMOP.partial(0.4553564F, 0.5009095F, f);
      this.shapeUP2.rotateAngleX = GetMOP.partial(-1.5934856F, -1.5025539F, f);
      this.ropeUP.rotateAngleX = GetMOP.partial(1.6390387F, 1.0016445F, f);
      this.shapeUP3.rotateAngleX = GetMOP.partial((float) -Math.PI, 0.0F, f);
      this.shapeUP4.rotateAngleX = GetMOP.partial(-2.3675392F, 0.0F, f);
      this.ropeUP2.rotateAngleX = GetMOP.partial((float) (Math.PI / 3), 0.91053826F, f);
      this.ropeDOWN.rotateAngleX = this.ropeUP.rotateAngleX;
      this.shapeDOWN1.rotateAngleX = this.shapeUP1.rotateAngleX;
      this.shapeDOWN2.rotateAngleX = this.shapeUP2.rotateAngleX;
      this.ropeDOWN2.rotateAngleX = this.ropeUP2.rotateAngleX;
      this.shapeDOWN3.rotateAngleX = this.shapeUP3.rotateAngleX;
      this.shapeDOWN4.rotateAngleX = this.shapeUP4.rotateAngleX;
      this.shapeMAIN.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapeDOWN1.offsetX, this.shapeDOWN1.offsetY, this.shapeDOWN1.offsetZ);
      GlStateManager.translate(this.shapeDOWN1.rotationPointX * f5, this.shapeDOWN1.rotationPointY * f5, this.shapeDOWN1.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.shapeDOWN1.offsetX, -this.shapeDOWN1.offsetY, -this.shapeDOWN1.offsetZ);
      GlStateManager.translate(-this.shapeDOWN1.rotationPointX * f5, -this.shapeDOWN1.rotationPointY * f5, -this.shapeDOWN1.rotationPointZ * f5);
      this.shapeDOWN1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapeUP1.offsetX, this.shapeUP1.offsetY, this.shapeUP1.offsetZ);
      GlStateManager.translate(this.shapeUP1.rotationPointX * f5, this.shapeUP1.rotationPointY * f5, this.shapeUP1.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.shapeUP1.offsetX, -this.shapeUP1.offsetY, -this.shapeUP1.offsetZ);
      GlStateManager.translate(-this.shapeUP1.rotationPointX * f5, -this.shapeUP1.rotationPointY * f5, -this.shapeUP1.rotationPointZ * f5);
      this.shapeUP1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(0.5, 0.5, 1.0);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      this.shape2.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
