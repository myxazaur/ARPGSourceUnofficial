//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class StingerModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape1_1;
   public ModelRenderer shape1_2;
   public ModelRenderer shape1_3;
   public ModelRenderer shape1_4;
   public ModelRenderer shape1_5;
   public ModelRenderer bolt5main;
   public ModelRenderer bolt5mid;
   public ModelRenderer bolt5end;
   public ModelRenderer bolt9main;
   public ModelRenderer bolt9mid;
   public ModelRenderer bolt9end;
   public ModelRenderer bolt13main;
   public ModelRenderer bolt13mid;
   public ModelRenderer bolt13end;
   public ModelRenderer bolt1main;
   public ModelRenderer bolt1mid;
   public ModelRenderer bolt1end;
   public ModelRenderer bolt6main;
   public ModelRenderer bolt6mid;
   public ModelRenderer bolt6end;
   public ModelRenderer bolt10main;
   public ModelRenderer bolt10mid;
   public ModelRenderer bolt10end;
   public ModelRenderer bolt14main;
   public ModelRenderer bolt14mid;
   public ModelRenderer bolt14end;
   public ModelRenderer bolt2main;
   public ModelRenderer bolt2mid;
   public ModelRenderer bolt2end;
   public ModelRenderer bolt8main;
   public ModelRenderer bolt8mid;
   public ModelRenderer bolt8end;
   public ModelRenderer bolt12main;
   public ModelRenderer bolt12mid;
   public ModelRenderer bolt12end;
   public ModelRenderer bolt1main_1;
   public ModelRenderer bolt16mid;
   public ModelRenderer bolt16end;
   public ModelRenderer bolt4main;
   public ModelRenderer bolt4mid;
   public ModelRenderer bolt4end;
   public ModelRenderer bolt7main;
   public ModelRenderer bolt7mid;
   public ModelRenderer bolt7end;
   public ModelRenderer bolt11main;
   public ModelRenderer bolt11mid;
   public ModelRenderer bolt11end;
   public ModelRenderer bolt15main;
   public ModelRenderer bolt15mid;
   public ModelRenderer bolt15end;
   public ModelRenderer bolt3main;
   public ModelRenderer bolt3mid;
   public ModelRenderer bolt3end;

   public StingerModel() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.bolt1end = new ModelRenderer(this, 8, 12);
      this.bolt1end.setRotationPoint(-2.7F, -0.6F, -12.0F);
      this.bolt1end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt6main = new ModelRenderer(this, 50, 12);
      this.bolt6main.setRotationPoint(-0.9F, 1.1F, -12.0F);
      this.bolt6main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt7end = new ModelRenderer(this, 56, 18);
      this.bolt7end.setRotationPoint(0.9F, 1.1F, -12.0F);
      this.bolt7end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt5end = new ModelRenderer(this, 40, 3);
      this.bolt5end.setRotationPoint(-2.7F, 1.1F, -12.0F);
      this.bolt5end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt7mid = new ModelRenderer(this, 52, 18);
      this.bolt7mid.setRotationPoint(0.9F, 1.1F, -12.0F);
      this.bolt7mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt3mid = new ModelRenderer(this, 53, 20);
      this.bolt3mid.setRotationPoint(0.9F, -0.6F, -12.0F);
      this.bolt3mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt1main = new ModelRenderer(this, 52, 9);
      this.bolt1main.setRotationPoint(-2.7F, -0.6F, -12.0F);
      this.bolt1main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt2main = new ModelRenderer(this, 2, 17);
      this.bolt2main.setRotationPoint(-0.9F, -0.6F, -12.0F);
      this.bolt2main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt11end = new ModelRenderer(this, 0, 20);
      this.bolt11end.setRotationPoint(0.9F, 2.8F, -12.0F);
      this.bolt11end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt15main = new ModelRenderer(this, 2, 20);
      this.bolt15main.setRotationPoint(0.9F, 4.5F, -12.0F);
      this.bolt15main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.shape1_5 = new ModelRenderer(this, 0, 7);
      this.shape1_5.setRotationPoint(0.0F, 2.5F, 0.0F);
      this.shape1_5.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.shape1_5, 0.31869712F, 0.0F, 0.0F);
      this.bolt2end = new ModelRenderer(this, 10, 17);
      this.bolt2end.setRotationPoint(-0.9F, -0.6F, -12.0F);
      this.bolt2end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt11main = new ModelRenderer(this, 6, 19);
      this.bolt11main.setRotationPoint(0.9F, 2.8F, -12.0F);
      this.bolt11main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt12mid = new ModelRenderer(this, 22, 18);
      this.bolt12mid.setRotationPoint(2.7F, 2.8F, -12.0F);
      this.bolt12mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt9main = new ModelRenderer(this, 8, 7);
      this.bolt9main.setRotationPoint(-2.7F, 2.8F, -12.0F);
      this.bolt9main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.shape1_1 = new ModelRenderer(this, 12, 0);
      this.shape1_1.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.shape1_1.addBox(-2.0F, -2.0F, 0.0F, 4, 7, 2, 0.0F);
      this.bolt6mid = new ModelRenderer(this, 54, 12);
      this.bolt6mid.setRotationPoint(-0.9F, 1.1F, -12.0F);
      this.bolt6mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt10end = new ModelRenderer(this, 59, 14);
      this.bolt10end.setRotationPoint(-0.9F, 2.8F, -12.0F);
      this.bolt10end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt12main = new ModelRenderer(this, 18, 18);
      this.bolt12main.setRotationPoint(2.7F, 2.8F, -12.0F);
      this.bolt12main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt14end = new ModelRenderer(this, 0, 17);
      this.bolt14end.setRotationPoint(-0.9F, 4.5F, -12.0F);
      this.bolt14end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt4main = new ModelRenderer(this, 38, 18);
      this.bolt4main.setRotationPoint(2.7F, -0.6F, -12.0F);
      this.bolt4main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt10main = new ModelRenderer(this, 8, 14);
      this.bolt10main.setRotationPoint(-0.9F, 2.8F, -12.0F);
      this.bolt10main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt1main_1 = new ModelRenderer(this, 28, 18);
      this.bolt1main_1.setRotationPoint(2.7F, 4.5F, -12.0F);
      this.bolt1main_1.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt8main = new ModelRenderer(this, 12, 18);
      this.bolt8main.setRotationPoint(2.7F, 1.1F, -12.0F);
      this.bolt8main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt4mid = new ModelRenderer(this, 42, 18);
      this.bolt4mid.setRotationPoint(2.7F, -0.6F, -12.0F);
      this.bolt4mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt15end = new ModelRenderer(this, 33, 20);
      this.bolt15end.setRotationPoint(0.9F, 4.5F, -12.0F);
      this.bolt15end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt15mid = new ModelRenderer(this, 23, 20);
      this.bolt15mid.setRotationPoint(0.9F, 4.5F, -12.0F);
      this.bolt15mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-2.0F, -2.0F, 0.0F, 4, 5, 2, 0.0F);
      this.bolt8end = new ModelRenderer(this, 16, 18);
      this.bolt8end.setRotationPoint(2.7F, 1.1F, -12.0F);
      this.bolt8end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.shape1_3 = new ModelRenderer(this, 40, 0);
      this.shape1_3.setRotationPoint(0.0F, 0.0F, 2.0F);
      this.shape1_3.addBox(-1.5F, -2.0F, 0.0F, 3, 3, 6, 0.0F);
      this.bolt14main = new ModelRenderer(this, 50, 15);
      this.bolt14main.setRotationPoint(-0.9F, 4.5F, -12.0F);
      this.bolt14main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt16end = new ModelRenderer(this, 36, 18);
      this.bolt16end.setRotationPoint(2.7F, 4.5F, -12.0F);
      this.bolt16end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt13end = new ModelRenderer(this, 8, 10);
      this.bolt13end.setRotationPoint(-2.7F, 4.5F, -12.0F);
      this.bolt13end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt10mid = new ModelRenderer(this, 55, 14);
      this.bolt10mid.setRotationPoint(-0.9F, 2.8F, -12.0F);
      this.bolt10mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt14mid = new ModelRenderer(this, 56, 16);
      this.bolt14mid.setRotationPoint(-0.9F, 4.5F, -12.0F);
      this.bolt14mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt9mid = new ModelRenderer(this, 58, 5);
      this.bolt9mid.setRotationPoint(-2.7F, 2.8F, -12.0F);
      this.bolt9mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt13main = new ModelRenderer(this, 56, 7);
      this.bolt13main.setRotationPoint(-2.7F, 4.5F, -12.0F);
      this.bolt13main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt4end = new ModelRenderer(this, 46, 18);
      this.bolt4end.setRotationPoint(2.7F, -0.6F, -12.0F);
      this.bolt4end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt13mid = new ModelRenderer(this, 50, 9);
      this.bolt13mid.setRotationPoint(-2.7F, 4.5F, -12.0F);
      this.bolt13mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt5main = new ModelRenderer(this, 40, 0);
      this.bolt5main.setRotationPoint(-2.7F, 1.1F, -12.0F);
      this.bolt5main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt12end = new ModelRenderer(this, 26, 18);
      this.bolt12end.setRotationPoint(2.7F, 2.8F, -12.0F);
      this.bolt12end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt16mid = new ModelRenderer(this, 32, 18);
      this.bolt16mid.setRotationPoint(2.7F, 4.5F, -12.0F);
      this.bolt16mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.shape1_2 = new ModelRenderer(this, 14, 0);
      this.shape1_2.setRotationPoint(0.0F, 0.0F, -12.0F);
      this.shape1_2.addBox(-4.0F, -2.0F, 0.0F, 8, 8, 10, 0.0F);
      this.bolt6end = new ModelRenderer(this, 58, 12);
      this.bolt6end.setRotationPoint(-0.9F, 1.1F, -12.0F);
      this.bolt6end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.shape1_4 = new ModelRenderer(this, 52, 0);
      this.shape1_4.setRotationPoint(0.0F, 0.0F, 8.0F);
      this.shape1_4.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 1, 0.0F);
      this.bolt9end = new ModelRenderer(this, 6, 7);
      this.bolt9end.setRotationPoint(-2.7F, 2.8F, -12.0F);
      this.bolt9end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt11mid = new ModelRenderer(this, 59, 19);
      this.bolt11mid.setRotationPoint(0.9F, 2.8F, -12.0F);
      this.bolt11mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt8mid = new ModelRenderer(this, 59, 17);
      this.bolt8mid.setRotationPoint(2.7F, 1.1F, -12.0F);
      this.bolt8mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt5mid = new ModelRenderer(this, 10, 0);
      this.bolt5mid.setRotationPoint(-2.7F, 1.1F, -12.0F);
      this.bolt5mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt7main = new ModelRenderer(this, 48, 18);
      this.bolt7main.setRotationPoint(0.9F, 1.1F, -12.0F);
      this.bolt7main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt3end = new ModelRenderer(this, 11, 21);
      this.bolt3end.setRotationPoint(0.9F, -0.6F, -12.0F);
      this.bolt3end.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt3main = new ModelRenderer(this, 42, 20);
      this.bolt3main.setRotationPoint(0.9F, -0.6F, -12.0F);
      this.bolt3main.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
      this.bolt1mid = new ModelRenderer(this, 58, 10);
      this.bolt1mid.setRotationPoint(-2.7F, -0.6F, -12.0F);
      this.bolt1mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
      this.bolt2mid = new ModelRenderer(this, 6, 17);
      this.bolt2mid.setRotationPoint(-0.9F, -0.6F, -12.0F);
      this.bolt2mid.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 1, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f1 < 1.0F) {
         this.bolt1main.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt1mid.offsetX, this.bolt1mid.offsetY, this.bolt1mid.offsetZ);
         GlStateManager.translate(this.bolt1mid.rotationPointX * f5, this.bolt1mid.rotationPointY * f5, this.bolt1mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt1mid.offsetX, -this.bolt1mid.offsetY, -this.bolt1mid.offsetZ);
         GlStateManager.translate(-this.bolt1mid.rotationPointX * f5, -this.bolt1mid.rotationPointY * f5, -this.bolt1mid.rotationPointZ * f5);
         this.bolt1mid.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt1end.offsetX, this.bolt1end.offsetY, this.bolt1end.offsetZ);
         GlStateManager.translate(this.bolt1end.rotationPointX * f5, this.bolt1end.rotationPointY * f5, this.bolt1end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt1end.offsetX, -this.bolt1end.offsetY, -this.bolt1end.offsetZ);
         GlStateManager.translate(-this.bolt1end.rotationPointX * f5, -this.bolt1end.rotationPointY * f5, -this.bolt1end.rotationPointZ * f5);
         this.bolt1end.render(f5);
         GlStateManager.popMatrix();
      }

      if (f1 < 2.0F) {
         this.bolt2main.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt2end.offsetX, this.bolt2end.offsetY, this.bolt2end.offsetZ);
         GlStateManager.translate(this.bolt2end.rotationPointX * f5, this.bolt2end.rotationPointY * f5, this.bolt2end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt2end.offsetX, -this.bolt2end.offsetY, -this.bolt2end.offsetZ);
         GlStateManager.translate(-this.bolt2end.rotationPointX * f5, -this.bolt2end.rotationPointY * f5, -this.bolt2end.rotationPointZ * f5);
         this.bolt2end.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt2mid.offsetX, this.bolt2mid.offsetY, this.bolt2mid.offsetZ);
         GlStateManager.translate(this.bolt2mid.rotationPointX * f5, this.bolt2mid.rotationPointY * f5, this.bolt2mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt2mid.offsetX, -this.bolt2mid.offsetY, -this.bolt2mid.offsetZ);
         GlStateManager.translate(-this.bolt2mid.rotationPointX * f5, -this.bolt2mid.rotationPointY * f5, -this.bolt2mid.rotationPointZ * f5);
         this.bolt2mid.render(f5);
         GlStateManager.popMatrix();
      }

      if (f1 < 3.0F) {
         this.bolt3main.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt3end.offsetX, this.bolt3end.offsetY, this.bolt3end.offsetZ);
         GlStateManager.translate(this.bolt3end.rotationPointX * f5, this.bolt3end.rotationPointY * f5, this.bolt3end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt3end.offsetX, -this.bolt3end.offsetY, -this.bolt3end.offsetZ);
         GlStateManager.translate(-this.bolt3end.rotationPointX * f5, -this.bolt3end.rotationPointY * f5, -this.bolt3end.rotationPointZ * f5);
         this.bolt3end.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt3mid.offsetX, this.bolt3mid.offsetY, this.bolt3mid.offsetZ);
         GlStateManager.translate(this.bolt3mid.rotationPointX * f5, this.bolt3mid.rotationPointY * f5, this.bolt3mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt3mid.offsetX, -this.bolt3mid.offsetY, -this.bolt3mid.offsetZ);
         GlStateManager.translate(-this.bolt3mid.rotationPointX * f5, -this.bolt3mid.rotationPointY * f5, -this.bolt3mid.rotationPointZ * f5);
         this.bolt3mid.render(f5);
         GlStateManager.popMatrix();
      }

      if (f1 < 4.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt4end.offsetX, this.bolt4end.offsetY, this.bolt4end.offsetZ);
         GlStateManager.translate(this.bolt4end.rotationPointX * f5, this.bolt4end.rotationPointY * f5, this.bolt4end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt4end.offsetX, -this.bolt4end.offsetY, -this.bolt4end.offsetZ);
         GlStateManager.translate(-this.bolt4end.rotationPointX * f5, -this.bolt4end.rotationPointY * f5, -this.bolt4end.rotationPointZ * f5);
         this.bolt4end.render(f5);
         GlStateManager.popMatrix();
         this.bolt4main.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt4mid.offsetX, this.bolt4mid.offsetY, this.bolt4mid.offsetZ);
         GlStateManager.translate(this.bolt4mid.rotationPointX * f5, this.bolt4mid.rotationPointY * f5, this.bolt4mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt4mid.offsetX, -this.bolt4mid.offsetY, -this.bolt4mid.offsetZ);
         GlStateManager.translate(-this.bolt4mid.rotationPointX * f5, -this.bolt4mid.rotationPointY * f5, -this.bolt4mid.rotationPointZ * f5);
         this.bolt4mid.render(f5);
         GlStateManager.popMatrix();
      }

      if (f1 < 5.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt5mid.offsetX, this.bolt5mid.offsetY, this.bolt5mid.offsetZ);
         GlStateManager.translate(this.bolt5mid.rotationPointX * f5, this.bolt5mid.rotationPointY * f5, this.bolt5mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt5mid.offsetX, -this.bolt5mid.offsetY, -this.bolt5mid.offsetZ);
         GlStateManager.translate(-this.bolt5mid.rotationPointX * f5, -this.bolt5mid.rotationPointY * f5, -this.bolt5mid.rotationPointZ * f5);
         this.bolt5mid.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt5end.offsetX, this.bolt5end.offsetY, this.bolt5end.offsetZ);
         GlStateManager.translate(this.bolt5end.rotationPointX * f5, this.bolt5end.rotationPointY * f5, this.bolt5end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt5end.offsetX, -this.bolt5end.offsetY, -this.bolt5end.offsetZ);
         GlStateManager.translate(-this.bolt5end.rotationPointX * f5, -this.bolt5end.rotationPointY * f5, -this.bolt5end.rotationPointZ * f5);
         this.bolt5end.render(f5);
         GlStateManager.popMatrix();
         this.bolt5main.render(f5);
      }

      if (f1 < 6.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt6mid.offsetX, this.bolt6mid.offsetY, this.bolt6mid.offsetZ);
         GlStateManager.translate(this.bolt6mid.rotationPointX * f5, this.bolt6mid.rotationPointY * f5, this.bolt6mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt6mid.offsetX, -this.bolt6mid.offsetY, -this.bolt6mid.offsetZ);
         GlStateManager.translate(-this.bolt6mid.rotationPointX * f5, -this.bolt6mid.rotationPointY * f5, -this.bolt6mid.rotationPointZ * f5);
         this.bolt6mid.render(f5);
         GlStateManager.popMatrix();
         this.bolt6main.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt6end.offsetX, this.bolt6end.offsetY, this.bolt6end.offsetZ);
         GlStateManager.translate(this.bolt6end.rotationPointX * f5, this.bolt6end.rotationPointY * f5, this.bolt6end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt6end.offsetX, -this.bolt6end.offsetY, -this.bolt6end.offsetZ);
         GlStateManager.translate(-this.bolt6end.rotationPointX * f5, -this.bolt6end.rotationPointY * f5, -this.bolt6end.rotationPointZ * f5);
         this.bolt6end.render(f5);
         GlStateManager.popMatrix();
      }

      if (f1 < 7.0F) {
         this.bolt7main.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt7mid.offsetX, this.bolt7mid.offsetY, this.bolt7mid.offsetZ);
         GlStateManager.translate(this.bolt7mid.rotationPointX * f5, this.bolt7mid.rotationPointY * f5, this.bolt7mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt7mid.offsetX, -this.bolt7mid.offsetY, -this.bolt7mid.offsetZ);
         GlStateManager.translate(-this.bolt7mid.rotationPointX * f5, -this.bolt7mid.rotationPointY * f5, -this.bolt7mid.rotationPointZ * f5);
         this.bolt7mid.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt7end.offsetX, this.bolt7end.offsetY, this.bolt7end.offsetZ);
         GlStateManager.translate(this.bolt7end.rotationPointX * f5, this.bolt7end.rotationPointY * f5, this.bolt7end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt7end.offsetX, -this.bolt7end.offsetY, -this.bolt7end.offsetZ);
         GlStateManager.translate(-this.bolt7end.rotationPointX * f5, -this.bolt7end.rotationPointY * f5, -this.bolt7end.rotationPointZ * f5);
         this.bolt7end.render(f5);
         GlStateManager.popMatrix();
      }

      if (f1 < 8.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt8mid.offsetX, this.bolt8mid.offsetY, this.bolt8mid.offsetZ);
         GlStateManager.translate(this.bolt8mid.rotationPointX * f5, this.bolt8mid.rotationPointY * f5, this.bolt8mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt8mid.offsetX, -this.bolt8mid.offsetY, -this.bolt8mid.offsetZ);
         GlStateManager.translate(-this.bolt8mid.rotationPointX * f5, -this.bolt8mid.rotationPointY * f5, -this.bolt8mid.rotationPointZ * f5);
         this.bolt8mid.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt8end.offsetX, this.bolt8end.offsetY, this.bolt8end.offsetZ);
         GlStateManager.translate(this.bolt8end.rotationPointX * f5, this.bolt8end.rotationPointY * f5, this.bolt8end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt8end.offsetX, -this.bolt8end.offsetY, -this.bolt8end.offsetZ);
         GlStateManager.translate(-this.bolt8end.rotationPointX * f5, -this.bolt8end.rotationPointY * f5, -this.bolt8end.rotationPointZ * f5);
         this.bolt8end.render(f5);
         GlStateManager.popMatrix();
         this.bolt8main.render(f5);
      }

      if (f1 < 9.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt9mid.offsetX, this.bolt9mid.offsetY, this.bolt9mid.offsetZ);
         GlStateManager.translate(this.bolt9mid.rotationPointX * f5, this.bolt9mid.rotationPointY * f5, this.bolt9mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt9mid.offsetX, -this.bolt9mid.offsetY, -this.bolt9mid.offsetZ);
         GlStateManager.translate(-this.bolt9mid.rotationPointX * f5, -this.bolt9mid.rotationPointY * f5, -this.bolt9mid.rotationPointZ * f5);
         this.bolt9mid.render(f5);
         GlStateManager.popMatrix();
         this.bolt9main.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt9end.offsetX, this.bolt9end.offsetY, this.bolt9end.offsetZ);
         GlStateManager.translate(this.bolt9end.rotationPointX * f5, this.bolt9end.rotationPointY * f5, this.bolt9end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt9end.offsetX, -this.bolt9end.offsetY, -this.bolt9end.offsetZ);
         GlStateManager.translate(-this.bolt9end.rotationPointX * f5, -this.bolt9end.rotationPointY * f5, -this.bolt9end.rotationPointZ * f5);
         this.bolt9end.render(f5);
         GlStateManager.popMatrix();
      }

      if (f1 < 10.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt10end.offsetX, this.bolt10end.offsetY, this.bolt10end.offsetZ);
         GlStateManager.translate(this.bolt10end.rotationPointX * f5, this.bolt10end.rotationPointY * f5, this.bolt10end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt10end.offsetX, -this.bolt10end.offsetY, -this.bolt10end.offsetZ);
         GlStateManager.translate(-this.bolt10end.rotationPointX * f5, -this.bolt10end.rotationPointY * f5, -this.bolt10end.rotationPointZ * f5);
         this.bolt10end.render(f5);
         GlStateManager.popMatrix();
         this.bolt10main.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt10mid.offsetX, this.bolt10mid.offsetY, this.bolt10mid.offsetZ);
         GlStateManager.translate(this.bolt10mid.rotationPointX * f5, this.bolt10mid.rotationPointY * f5, this.bolt10mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt10mid.offsetX, -this.bolt10mid.offsetY, -this.bolt10mid.offsetZ);
         GlStateManager.translate(-this.bolt10mid.rotationPointX * f5, -this.bolt10mid.rotationPointY * f5, -this.bolt10mid.rotationPointZ * f5);
         this.bolt10mid.render(f5);
         GlStateManager.popMatrix();
      }

      if (f1 < 11.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt11end.offsetX, this.bolt11end.offsetY, this.bolt11end.offsetZ);
         GlStateManager.translate(this.bolt11end.rotationPointX * f5, this.bolt11end.rotationPointY * f5, this.bolt11end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt11end.offsetX, -this.bolt11end.offsetY, -this.bolt11end.offsetZ);
         GlStateManager.translate(-this.bolt11end.rotationPointX * f5, -this.bolt11end.rotationPointY * f5, -this.bolt11end.rotationPointZ * f5);
         this.bolt11end.render(f5);
         GlStateManager.popMatrix();
         this.bolt11main.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt11mid.offsetX, this.bolt11mid.offsetY, this.bolt11mid.offsetZ);
         GlStateManager.translate(this.bolt11mid.rotationPointX * f5, this.bolt11mid.rotationPointY * f5, this.bolt11mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt11mid.offsetX, -this.bolt11mid.offsetY, -this.bolt11mid.offsetZ);
         GlStateManager.translate(-this.bolt11mid.rotationPointX * f5, -this.bolt11mid.rotationPointY * f5, -this.bolt11mid.rotationPointZ * f5);
         this.bolt11mid.render(f5);
         GlStateManager.popMatrix();
      }

      if (f1 < 12.0F) {
         this.bolt12main.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt12end.offsetX, this.bolt12end.offsetY, this.bolt12end.offsetZ);
         GlStateManager.translate(this.bolt12end.rotationPointX * f5, this.bolt12end.rotationPointY * f5, this.bolt12end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt12end.offsetX, -this.bolt12end.offsetY, -this.bolt12end.offsetZ);
         GlStateManager.translate(-this.bolt12end.rotationPointX * f5, -this.bolt12end.rotationPointY * f5, -this.bolt12end.rotationPointZ * f5);
         this.bolt12end.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt12mid.offsetX, this.bolt12mid.offsetY, this.bolt12mid.offsetZ);
         GlStateManager.translate(this.bolt12mid.rotationPointX * f5, this.bolt12mid.rotationPointY * f5, this.bolt12mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt12mid.offsetX, -this.bolt12mid.offsetY, -this.bolt12mid.offsetZ);
         GlStateManager.translate(-this.bolt12mid.rotationPointX * f5, -this.bolt12mid.rotationPointY * f5, -this.bolt12mid.rotationPointZ * f5);
         this.bolt12mid.render(f5);
         GlStateManager.popMatrix();
      }

      if (f1 < 13.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt13end.offsetX, this.bolt13end.offsetY, this.bolt13end.offsetZ);
         GlStateManager.translate(this.bolt13end.rotationPointX * f5, this.bolt13end.rotationPointY * f5, this.bolt13end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt13end.offsetX, -this.bolt13end.offsetY, -this.bolt13end.offsetZ);
         GlStateManager.translate(-this.bolt13end.rotationPointX * f5, -this.bolt13end.rotationPointY * f5, -this.bolt13end.rotationPointZ * f5);
         this.bolt13end.render(f5);
         GlStateManager.popMatrix();
         this.bolt13main.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt13mid.offsetX, this.bolt13mid.offsetY, this.bolt13mid.offsetZ);
         GlStateManager.translate(this.bolt13mid.rotationPointX * f5, this.bolt13mid.rotationPointY * f5, this.bolt13mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt13mid.offsetX, -this.bolt13mid.offsetY, -this.bolt13mid.offsetZ);
         GlStateManager.translate(-this.bolt13mid.rotationPointX * f5, -this.bolt13mid.rotationPointY * f5, -this.bolt13mid.rotationPointZ * f5);
         this.bolt13mid.render(f5);
         GlStateManager.popMatrix();
      }

      if (f1 < 14.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt14end.offsetX, this.bolt14end.offsetY, this.bolt14end.offsetZ);
         GlStateManager.translate(this.bolt14end.rotationPointX * f5, this.bolt14end.rotationPointY * f5, this.bolt14end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt14end.offsetX, -this.bolt14end.offsetY, -this.bolt14end.offsetZ);
         GlStateManager.translate(-this.bolt14end.rotationPointX * f5, -this.bolt14end.rotationPointY * f5, -this.bolt14end.rotationPointZ * f5);
         this.bolt14end.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt14mid.offsetX, this.bolt14mid.offsetY, this.bolt14mid.offsetZ);
         GlStateManager.translate(this.bolt14mid.rotationPointX * f5, this.bolt14mid.rotationPointY * f5, this.bolt14mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt14mid.offsetX, -this.bolt14mid.offsetY, -this.bolt14mid.offsetZ);
         GlStateManager.translate(-this.bolt14mid.rotationPointX * f5, -this.bolt14mid.rotationPointY * f5, -this.bolt14mid.rotationPointZ * f5);
         this.bolt14mid.render(f5);
         GlStateManager.popMatrix();
         this.bolt14main.render(f5);
      }

      if (f1 < 15.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt15end.offsetX, this.bolt15end.offsetY, this.bolt15end.offsetZ);
         GlStateManager.translate(this.bolt15end.rotationPointX * f5, this.bolt15end.rotationPointY * f5, this.bolt15end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt15end.offsetX, -this.bolt15end.offsetY, -this.bolt15end.offsetZ);
         GlStateManager.translate(-this.bolt15end.rotationPointX * f5, -this.bolt15end.rotationPointY * f5, -this.bolt15end.rotationPointZ * f5);
         this.bolt15end.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt15mid.offsetX, this.bolt15mid.offsetY, this.bolt15mid.offsetZ);
         GlStateManager.translate(this.bolt15mid.rotationPointX * f5, this.bolt15mid.rotationPointY * f5, this.bolt15mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt15mid.offsetX, -this.bolt15mid.offsetY, -this.bolt15mid.offsetZ);
         GlStateManager.translate(-this.bolt15mid.rotationPointX * f5, -this.bolt15mid.rotationPointY * f5, -this.bolt15mid.rotationPointZ * f5);
         this.bolt15mid.render(f5);
         GlStateManager.popMatrix();
         this.bolt15main.render(f5);
      }

      if (f1 < 16.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt16end.offsetX, this.bolt16end.offsetY, this.bolt16end.offsetZ);
         GlStateManager.translate(this.bolt16end.rotationPointX * f5, this.bolt16end.rotationPointY * f5, this.bolt16end.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.6, 1.0);
         GlStateManager.translate(-this.bolt16end.offsetX, -this.bolt16end.offsetY, -this.bolt16end.offsetZ);
         GlStateManager.translate(-this.bolt16end.rotationPointX * f5, -this.bolt16end.rotationPointY * f5, -this.bolt16end.rotationPointZ * f5);
         this.bolt16end.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bolt16mid.offsetX, this.bolt16mid.offsetY, this.bolt16mid.offsetZ);
         GlStateManager.translate(this.bolt16mid.rotationPointX * f5, this.bolt16mid.rotationPointY * f5, this.bolt16mid.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.5, 0.8);
         GlStateManager.translate(-this.bolt16mid.offsetX, -this.bolt16mid.offsetY, -this.bolt16mid.offsetZ);
         GlStateManager.translate(-this.bolt16mid.rotationPointX * f5, -this.bolt16mid.rotationPointY * f5, -this.bolt16mid.rotationPointZ * f5);
         this.bolt16mid.render(f5);
         GlStateManager.popMatrix();
         this.bolt1main_1.render(f5);
      }

      this.shape1_1.render(f5);
      this.shape1_2.render(f5);
      this.shape1.render(f5);
      this.shape1_4.render(f5);
      this.shape1_5.render(f5);
      this.shape1_3.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
