//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class GrenadeModel extends ModelBase {
   public ModelRenderer shapemain;
   public ModelRenderer stick1;
   public ModelRenderer stick2;
   public ModelRenderer stick3;
   public ModelRenderer stick4;
   public ModelRenderer shapeup;
   public ModelRenderer shapedown;
   public ModelRenderer shapestick;
   public ModelRenderer pin;
   public ModelRenderer big;
   public ModelRenderer shapecubedown;
   public ModelRenderer stickdiag1;
   public ModelRenderer stickdiag2;
   public ModelRenderer stickdiag3;
   public ModelRenderer stickdiag4;
   public ModelRenderer shapecubeup;
   public ModelRenderer shapeup2;
   public ModelRenderer shapedown2;
   public ModelRenderer pinhandle1;
   public ModelRenderer pinhandle2;

   public GrenadeModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.shapemain = new ModelRenderer(this, 2, 0);
      this.shapemain.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shapemain.addBox(-1.5F, -2.5F, -1.5F, 3, 5, 3, 0.0F);
      this.shapedown2 = new ModelRenderer(this, 20, 26);
      this.shapedown2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shapedown2.addBox(-1.0F, 2.48F, -1.0F, 2, 1, 2, 0.0F);
      this.stickdiag3 = new ModelRenderer(this, 8, 26);
      this.stickdiag3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.stickdiag3.addBox(-0.5F, -2.49F, 1.5F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.stickdiag3, 0.0F, (float) (Math.PI * 3.0 / 4.0), 0.0F);
      this.pin = new ModelRenderer(this, 25, 4);
      this.pin.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.pin.addBox(-0.5F, -4.65F, 0.5F, 1, 2, 2, 0.0F);
      this.setRotateAngle(this.pin, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.shapecubeup = new ModelRenderer(this, 19, 6);
      this.shapecubeup.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shapecubeup.addBox(-1.0F, -4.5F, -1.0F, 2, 2, 2, 0.0F);
      this.stick1 = new ModelRenderer(this, 0, 8);
      this.stick1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.stick1.addBox(-0.5F, -2.49F, -2.5F, 1, 5, 1, 0.0F);
      this.shapestick = new ModelRenderer(this, 21, 2);
      this.shapestick.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shapestick.addBox(-0.5F, -5.5F, -0.5F, 1, 3, 1, 0.0F);
      this.stick4 = new ModelRenderer(this, 12, 8);
      this.stick4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.stick4.addBox(-2.5F, -2.49F, -0.5F, 1, 5, 1, 0.0F);
      this.shapedown = new ModelRenderer(this, 0, 20);
      this.shapedown.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shapedown.addBox(-2.5F, 2.5F, -2.5F, 5, 1, 5, 0.0F);
      this.pinhandle1 = new ModelRenderer(this, 26, 0);
      this.pinhandle1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.pinhandle1.addBox(-0.5F, -1.4F, -3.95F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.pinhandle1, -1.0016445F, (float) (Math.PI / 4), 0.0F);
      this.pinhandle2 = new ModelRenderer(this, 16, 0);
      this.pinhandle2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.pinhandle2.addBox(-0.5F, -2.45F, -3.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.pinhandle2, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.stickdiag4 = new ModelRenderer(this, 12, 26);
      this.stickdiag4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.stickdiag4.addBox(-0.5F, -2.49F, 1.5F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.stickdiag4, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.stick3 = new ModelRenderer(this, 8, 8);
      this.stick3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.stick3.addBox(1.5F, -2.49F, -0.5F, 1, 5, 1, 0.0F);
      this.stick2 = new ModelRenderer(this, 4, 8);
      this.stick2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.stick2.addBox(-0.5F, -2.49F, 1.5F, 1, 5, 1, 0.0F);
      this.shapeup2 = new ModelRenderer(this, 20, 23);
      this.shapeup2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shapeup2.addBox(-1.0F, -3.48F, -1.0F, 2, 1, 2, 0.0F);
      this.stickdiag2 = new ModelRenderer(this, 4, 26);
      this.stickdiag2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.stickdiag2.addBox(-0.5F, -2.49F, 1.5F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.stickdiag2, 0.0F, (float) (-Math.PI * 3.0 / 4.0), 0.0F);
      this.stickdiag1 = new ModelRenderer(this, 0, 26);
      this.stickdiag1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.stickdiag1.addBox(-0.5F, -2.49F, 1.5F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.stickdiag1, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.big = new ModelRenderer(this, 15, 10);
      this.big.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.big.addBox(-2.0F, -2.51F, -2.0F, 4, 5, 4, 0.0F);
      this.shapeup = new ModelRenderer(this, 0, 14);
      this.shapeup.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shapeup.addBox(-2.5F, -3.5F, -2.5F, 5, 1, 5, 0.0F);
      this.shapecubedown = new ModelRenderer(this, 19, 19);
      this.shapecubedown.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shapecubedown.addBox(-1.0F, 2.5F, -1.0F, 2, 2, 2, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shapemain.render(f5);
      this.shapestick.render(f5);
      this.big.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapecubedown.offsetX, this.shapecubedown.offsetY, this.shapecubedown.offsetZ);
      GlStateManager.translate(this.shapecubedown.rotationPointX * f5, this.shapecubedown.rotationPointY * f5, this.shapecubedown.rotationPointZ * f5);
      GlStateManager.scale(1.01, 1.0, 1.01);
      GlStateManager.translate(-this.shapecubedown.offsetX, -this.shapecubedown.offsetY, -this.shapecubedown.offsetZ);
      GlStateManager.translate(-this.shapecubedown.rotationPointX * f5, -this.shapecubedown.rotationPointY * f5, -this.shapecubedown.rotationPointZ * f5);
      this.shapecubedown.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapecubeup.offsetX, this.shapecubeup.offsetY, this.shapecubeup.offsetZ);
      GlStateManager.translate(this.shapecubeup.rotationPointX * f5, this.shapecubeup.rotationPointY * f5, this.shapecubeup.rotationPointZ * f5);
      GlStateManager.scale(1.01, 1.0, 1.01);
      GlStateManager.translate(-this.shapecubeup.offsetX, -this.shapecubeup.offsetY, -this.shapecubeup.offsetZ);
      GlStateManager.translate(-this.shapecubeup.rotationPointX * f5, -this.shapecubeup.rotationPointY * f5, -this.shapecubeup.rotationPointZ * f5);
      this.shapecubeup.render(f5);
      GlStateManager.popMatrix();
      this.shapeup2.render(f5);
      this.shapedown2.render(f5);
      this.shapeup.render(f5);
      this.shapedown.render(f5);
      this.stick1.render(f5);
      this.stick2.render(f5);
      this.stick3.render(f5);
      this.stick4.render(f5);
      this.stickdiag3.render(f5);
      this.stickdiag1.render(f5);
      this.stickdiag2.render(f5);
      this.stickdiag4.render(f5);
      this.pin.render(f5);
      this.pinhandle1.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.pinhandle2.offsetX, this.pinhandle2.offsetY, this.pinhandle2.offsetZ);
      GlStateManager.translate(this.pinhandle2.rotationPointX * f5, this.pinhandle2.rotationPointY * f5, this.pinhandle2.rotationPointZ * f5);
      GlStateManager.scale(0.99, 1.0, 0.99);
      GlStateManager.translate(-this.pinhandle2.offsetX, -this.pinhandle2.offsetY, -this.pinhandle2.offsetZ);
      GlStateManager.translate(-this.pinhandle2.rotationPointX * f5, -this.pinhandle2.rotationPointY * f5, -this.pinhandle2.rotationPointZ * f5);
      this.pinhandle2.render(f5);
      GlStateManager.popMatrix();
   }

   public void hideAll() {
      this.stick3.isHidden = true;
      this.shapecubeup.isHidden = true;
      this.stick2.isHidden = true;
      this.stickdiag2.isHidden = true;
      this.pin.isHidden = true;
      this.stick4.isHidden = true;
      this.stickdiag4.isHidden = true;
      this.shapeup.isHidden = true;
      this.shapecubedown.isHidden = true;
      this.shapestick.isHidden = true;
      this.stickdiag1.isHidden = true;
      this.stickdiag3.isHidden = true;
      this.stick1.isHidden = true;
      this.shapemain.isHidden = true;
      this.shapedown.isHidden = true;
      this.big.isHidden = true;
      this.shapeup2.isHidden = true;
      this.shapedown2.isHidden = true;
      this.pinhandle1.isHidden = true;
      this.pinhandle2.isHidden = true;
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
