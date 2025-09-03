//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.renders.ModelRendererGlow;
import com.Vivern.Arpg.renders.ModelRendererLimited;
import java.util.ArrayList;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

public class StormChestModel extends ModelBase {
   public ModelRenderer chestLock1;
   public ModelRenderer chestLock2;
   public ModelRenderer chestLock2a;
   public ModelRenderer chestLock3;
   public ModelRenderer chestLock3a;
   public ModelRenderer chestLock1big;
   public ModelRenderer chestBelowLightFront;
   public ModelRenderer chestBelowLightRight;
   public ModelRenderer chestBelowLightLeft;
   public ModelRenderer chestBelowLightBack;
   public ModelRenderer chestBelowLightBottom;
   public ModelRenderer chestBelowBigFront;
   public ModelRenderer chestBelowBigRight;
   public ModelRenderer chestBelowBigLeft;
   public ModelRenderer chestBelowBigBack;
   public ModelRenderer chestBelowBigBottom;
   public ModelRenderer chestLidBigFront;
   public ModelRenderer chestLidBigLeft;
   public ModelRenderer chestLidBigRight;
   public ModelRenderer chestLidBigTop;
   public ModelRenderer chestLidBigBack;
   public ModelRenderer chestLidFront;
   public ModelRenderer chestLidBottom;
   public ModelRenderer chestLidLeft;
   public ModelRenderer chestLidRight;
   public ModelRenderer chestLidBack;
   public ModelRenderer chestLidTop;
   public ModelRenderer chestLidLightFront;
   public ModelRenderer chestLidLightTop;
   public ModelRenderer chestLidLightRight;
   public ModelRenderer chestLidLightLeft;
   public ModelRenderer chestLidLightBack;
   public ModelRenderer chestBelowFront;
   public ModelRenderer chestBelowRight;
   public ModelRenderer chestBelowLeft;
   public ModelRenderer chestBelowBack;
   public ModelRenderer chestBelowTop;
   public ModelRenderer chestBelowBottom;
   public ModelRenderer chestLockStormA;
   public ModelRenderer chestLockStormA_1;
   public ModelRenderer chestLockStormA_2;
   public ModelRenderer chestLockStormB;
   public ModelRenderer chestLockStormB_1;
   public ModelRenderer chestLockStormB_2;
   public ModelRenderer shapeCircle;
   public ArrayList<ModelRenderer> lids = new ArrayList<>();

   public StormChestModel() {
      float mscale = 0.0F;
      this.textureWidth = 128;
      this.textureHeight = 128;
      this.chestLockStormB_1 = new ModelRenderer(this, 70, 32);
      this.chestLockStormB_1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chestLockStormB_1.addBox(-0.5F, -1.5F, 0.01F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.chestLockStormB_1, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.chestLockStormB_2 = new ModelRenderer(this, 70, 32);
      this.chestLockStormB_2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chestLockStormB_2.addBox(-0.5F, -1.5F, 0.01F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.chestLockStormB_2, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.chestLockStormA_2 = new ModelRendererGlow(this, 70, 32, 240, false);
      this.chestLockStormA_2.setRotationPoint(3.5F, 0.5F, -14.5F);
      this.chestLockStormA_2.addBox(-0.5F, -1.5F, 0.0F, 1, 3, 1, 0.0F);
      this.chestLockStormB = new ModelRenderer(this, 70, 32);
      this.chestLockStormB.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chestLockStormB.addBox(-0.5F, -1.5F, 0.01F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.chestLockStormB, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.chestLockStormA = new ModelRendererGlow(this, 70, 32, 240, false);
      this.chestLockStormA.setRotationPoint(0.0F, -4.5F, -14.5F);
      this.chestLockStormA.addBox(-0.5F, -1.5F, 0.0F, 1, 3, 1, 0.0F);
      this.chestLockStormA_1 = new ModelRendererGlow(this, 70, 32, 240, false);
      this.chestLockStormA_1.setRotationPoint(-3.5F, 0.5F, -14.5F);
      this.chestLockStormA_1.addBox(-0.5F, -1.5F, 0.0F, 1, 3, 1, 0.0F);
      this.shapeCircle = new ModelRenderer(this, 68, 28);
      this.shapeCircle.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.shapeCircle.addBox(-8.0F, 0.0F, -8.0F, 16, 0, 16, 0.01F);
      this.chestBelowLightBack = new ModelRendererLimited(this, 28, 18, false, false, false, false, true, false);
      this.chestBelowLightBack.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowLightBack.addBox(-7.0F, 0.0F, -7.01F, 14, 10, 0, mscale);
      this.setRotateAngle(this.chestBelowLightBack, 0.0F, (float) Math.PI, 0.0F);
      this.chestBelowRight = new ModelRendererLimited(this, 14, 4, false, false, false, false, true, false);
      this.chestBelowRight.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowRight.addBox(-7.0F, 0.0F, -7.0F, 14, 10, 0, mscale);
      this.setRotateAngle(this.chestBelowRight, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.chestBelowBigRight = new ModelRendererLimited(this, 14, 32, false, false, false, false, true, false);
      this.chestBelowBigRight.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowBigRight.addBox(-7.0F, 0.0F, -7.0F, 14, 10, 0, mscale);
      this.setRotateAngle(this.chestBelowBigRight, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.chestLidLightFront = new ModelRendererLimited(this, 0, 14, false, false, false, false, true, false);
      this.chestLidLightFront.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidLightFront.addBox(-7.0F, -6.0F, -13.01F, 14, 5, 0, mscale);
      this.chestLidTop = new ModelRendererLimited(this, 28, 0, false, false, true, false, false, false);
      this.chestLidTop.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidTop.addBox(-7.0F, -6.0F, -13.0F, 14, 0, 14, mscale);
      this.chestLidLightRight = new ModelRendererLimited(this, 0, 0, true, false, false, false, false, false);
      this.chestLidLightRight.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidLightRight.addBox(7.01F, -6.0F, -13.0F, 0, 5, 14, mscale);
      this.chestLidRight = new ModelRendererLimited(this, 0, -14, true, false, false, false, false, false);
      this.chestLidRight.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidRight.addBox(7.0F, -6.0F, -13.0F, 0, 5, 14, mscale);
      this.chestBelowBigFront = new ModelRendererLimited(this, 0, 32, false, false, false, false, true, false);
      this.chestBelowBigFront.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowBigFront.addBox(-7.0F, 0.0F, -7.0F, 14, 10, 0, mscale);
      this.chestBelowBigBottom = new ModelRendererLimited(this, 28, 28, false, false, false, true, false, false);
      this.chestBelowBigBottom.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowBigBottom.addBox(-7.0F, 10.0F, -7.0F, 14, 0, 14, mscale);
      this.chestLidLightLeft = new ModelRendererLimited(this, 0, 0, true, false, false, false, false, false);
      this.chestLidLightLeft.mirror = true;
      this.chestLidLightLeft.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidLightLeft.addBox(-7.01F, -6.0F, -13.0F, 0, 5, 14, mscale);
      this.chestBelowTop = new ModelRendererLimited(this, 70, 14, false, false, true, false, false, false);
      this.chestBelowTop.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowTop.addBox(-7.0F, 0.0F, -7.0F, 14, 0, 14, mscale);
      this.chestBelowLightLeft = new ModelRendererLimited(this, 14, 18, false, false, false, false, true, false);
      this.chestBelowLightLeft.mirror = true;
      this.chestBelowLightLeft.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowLightLeft.addBox(-7.0F, 0.0F, -7.01F, 14, 10, 0, mscale);
      this.setRotateAngle(this.chestBelowLightLeft, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.chestLidBigTop = new ModelRendererLimited(this, 28, 28, false, false, true, false, false, false);
      this.chestLidBigTop.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidBigTop.addBox(-7.0F, -6.0F, -12.66F, 14, 0, 14, mscale);
      this.chestBelowBack = new ModelRendererLimited(this, 28, 4, false, false, false, false, true, false);
      this.chestBelowBack.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowBack.addBox(-7.0F, 0.0F, -7.0F, 14, 10, 0, mscale);
      this.setRotateAngle(this.chestBelowBack, 0.0F, (float) Math.PI, 0.0F);
      this.chestBelowLightRight = new ModelRendererLimited(this, 14, 18, false, false, false, false, true, false);
      this.chestBelowLightRight.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowLightRight.addBox(-7.0F, 0.0F, -7.01F, 14, 10, 0, mscale);
      this.setRotateAngle(this.chestBelowLightRight, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.chestLidLeft = new ModelRendererLimited(this, 0, -14, true, false, false, false, false, false);
      this.chestLidLeft.mirror = true;
      this.chestLidLeft.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidLeft.addBox(-7.0F, -6.0F, -13.0F, 0, 5, 14, mscale);
      this.chestLidLightBack = new ModelRendererLimited(this, 14, 14, false, false, false, false, false, true);
      this.chestLidLightBack.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidLightBack.addBox(-7.0F, -6.0F, 1.01F, 14, 5, 0, mscale);
      this.chestLidBottom = new ModelRendererLimited(this, 56, 0, false, false, false, true, false, false);
      this.chestLidBottom.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidBottom.addBox(-7.0F, -1.0F, -13.0F, 14, 0, 14, mscale);
      this.chestLidBigFront = new ModelRendererLimited(this, 0, 28, false, false, false, false, true, false);
      this.chestLidBigFront.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidBigFront.addBox(-7.0F, -6.0F, -12.66F, 14, 5, 0, mscale);
      this.chestLidBigLeft = new ModelRendererLimited(this, 0, 14, true, false, false, false, false, false);
      this.chestLidBigLeft.mirror = true;
      this.chestLidBigLeft.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidBigLeft.addBox(-7.0F, -6.0F, -12.66F, 0, 5, 14, mscale);
      this.chestBelowLeft = new ModelRendererLimited(this, 14, 4, false, false, false, false, true, false);
      this.chestBelowLeft.mirror = true;
      this.chestBelowLeft.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowLeft.addBox(-7.0F, 0.0F, -7.0F, 14, 10, 0, mscale);
      this.setRotateAngle(this.chestBelowLeft, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.chestBelowFront = new ModelRendererLimited(this, 0, 4, false, false, false, false, true, false);
      this.chestBelowFront.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowFront.addBox(-7.0F, 0.0F, -7.0F, 14, 10, 0, mscale);
      this.chestLidBigRight = new ModelRendererLimited(this, 0, 14, true, false, false, false, false, false);
      this.chestLidBigRight.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidBigRight.addBox(7.0F, -6.0F, -12.66F, 0, 5, 14, mscale);
      this.chestBelowBigBack = new ModelRendererLimited(this, 28, 32, false, false, false, false, true, false);
      this.chestBelowBigBack.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowBigBack.addBox(-7.0F, 0.0F, -7.0F, 14, 10, 0, mscale);
      this.setRotateAngle(this.chestBelowBigBack, 0.0F, (float) Math.PI, 0.0F);
      this.chestBelowBottom = new ModelRendererLimited(this, 28, 0, false, false, false, true, false, false);
      this.chestBelowBottom.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowBottom.addBox(-7.0F, 10.0F, -7.0F, 14, 0, 14, mscale);
      this.chestLock2a = new ModelRenderer(this, 70, 5);
      this.chestLock2a.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLock2a.addBox(-4.0F, -3.0F, -14.0F, 2, 4, 1, 0.0F);
      this.chestLock3a = new ModelRenderer(this, 70, 10);
      this.chestLock3a.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLock3a.addBox(-6.0F, -3.0F, -14.0F, 2, 4, 1, 0.0F);
      this.chestLock2 = new ModelRenderer(this, 70, 5);
      this.chestLock2.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLock2.addBox(2.0F, -3.0F, -14.0F, 2, 4, 1, 0.0F);
      this.chestBelowLightBottom = new ModelRendererLimited(this, 28, 14, false, false, false, true, false, false);
      this.chestBelowLightBottom.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowLightBottom.addBox(-7.0F, 10.01F, -7.0F, 14, 0, 14, mscale);
      this.chestLidFront = new ModelRendererLimited(this, 0, 0, false, false, false, false, true, false);
      this.chestLidFront.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidFront.addBox(-7.0F, -6.0F, -13.0F, 14, 5, 0, mscale);
      this.chestLidBack = new ModelRendererLimited(this, 14, 0, false, false, false, false, false, true);
      this.chestLidBack.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidBack.addBox(-7.0F, -6.0F, 1.0F, 14, 5, 0, mscale);
      this.chestLidBigBack = new ModelRendererLimited(this, 14, 28, false, false, false, false, false, true);
      this.chestLidBigBack.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidBigBack.addBox(-7.0F, -6.0F, 1.35F, 14, 5, 0, 0.0F);
      this.chestLock1 = new ModelRenderer(this, 70, 0);
      this.chestLock1.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLock1.addBox(-1.0F, -3.0F, -14.0F, 2, 4, 1, 0.0F);
      this.chestLock1big = new ModelRenderer(this, 70, 15);
      this.chestLock1big.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLock1big.addBox(-2.0F, -3.0F, -14.0F, 4, 4, 1, 0.0F);
      this.chestLock3 = new ModelRenderer(this, 70, 10);
      this.chestLock3.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLock3.addBox(4.0F, -3.0F, -14.0F, 2, 4, 1, 0.0F);
      this.chestLidLightTop = new ModelRendererLimited(this, 28, 14, false, false, true, false, false, false);
      this.chestLidLightTop.setRotationPoint(0.0F, 16.0F, 6.0F);
      this.chestLidLightTop.addBox(-7.0F, -6.01F, -13.0F, 14, 0, 14, mscale);
      this.chestBelowLightFront = new ModelRendererLimited(this, 0, 18, false, false, false, false, true, false);
      this.chestBelowLightFront.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowLightFront.addBox(-7.0F, 0.0F, -7.01F, 14, 10, 0, mscale);
      this.chestBelowBigLeft = new ModelRendererLimited(this, 14, 32, false, false, false, false, true, false);
      this.chestBelowBigLeft.mirror = true;
      this.chestBelowBigLeft.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.chestBelowBigLeft.addBox(-7.0F, 0.0F, -7.0F, 14, 10, 0, mscale);
      this.setRotateAngle(this.chestBelowBigLeft, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.chestLockStormA_1.addChild(this.chestLockStormB_1);
      this.chestLockStormA_2.addChild(this.chestLockStormB_2);
      this.chestLidTop.addChild(this.chestLockStormA_2);
      this.chestLockStormA.addChild(this.chestLockStormB);
      this.chestLidTop.addChild(this.chestLockStormA_1);
      this.chestLidTop.addChild(this.chestLockStormA);
      this.lids.add(this.chestLidBack);
      this.lids.add(this.chestLidFront);
      this.lids.add(this.chestLidRight);
      this.lids.add(this.chestLidLeft);
      this.lids.add(this.chestLidBottom);
      this.lids.add(this.chestLidTop);
      this.lids.add(this.chestLidLightBack);
      this.lids.add(this.chestLidLightFront);
      this.lids.add(this.chestLidLightRight);
      this.lids.add(this.chestLidLightLeft);
      this.lids.add(this.chestLidLightTop);
      this.lids.add(this.chestLidBigBack);
      this.lids.add(this.chestLidBigFront);
      this.lids.add(this.chestLidBigRight);
      this.lids.add(this.chestLidBigLeft);
      this.lids.add(this.chestLidBigTop);
      this.lids.add(this.chestLock1);
      this.lids.add(this.chestLock2);
      this.lids.add(this.chestLock3);
      this.lids.add(this.chestLock2a);
      this.lids.add(this.chestLock3a);
      this.lids.add(this.chestLock1big);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      for (ModelRenderer modelRenderer : this.lids) {
         modelRenderer.rotateAngleX = f4;
      }

      this.chestBelowRight.render(f5);
      this.chestLidTop.render(f5);
      this.chestLidRight.render(f5);
      this.chestBelowTop.render(f5);
      this.chestBelowBack.render(f5);
      this.chestLidLeft.render(f5);
      this.chestLidBottom.render(f5);
      this.chestBelowLeft.render(f5);
      this.chestBelowFront.render(f5);
      this.chestBelowBottom.render(f5);
      this.chestLock2a.render(f5);
      this.chestLock3a.render(f5);
      this.chestLock2.render(f5);
      this.chestLidFront.render(f5);
      this.chestLidBack.render(f5);
      this.chestLock1.render(f5);
      this.chestLock1big.render(f5);
      this.chestLock3.render(f5);
      if (f3 > 0.0F) {
         int light = (int)f3;
         float lbX = OpenGlHelper.lastBrightnessX;
         float lbY = OpenGlHelper.lastBrightnessY;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240.0F, light + lbX), Math.min(240.0F, light + lbY));
         this.chestBelowLightBack.render(f5);
         this.chestLidLightFront.render(f5);
         this.chestLidLightRight.render(f5);
         this.chestLidLightLeft.render(f5);
         this.chestBelowLightLeft.render(f5);
         this.chestBelowLightRight.render(f5);
         this.chestLidLightBack.render(f5);
         this.chestBelowLightBottom.render(f5);
         this.chestLidLightTop.render(f5);
         this.chestBelowLightFront.render(f5);
         float circleSize = -0.7F + 1.7F * (f4 / (float) (-Math.PI / 2));
         AbstractMobModel.alphaGlow();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapeCircle.offsetX, this.shapeCircle.offsetY, this.shapeCircle.offsetZ);
         GlStateManager.translate(this.shapeCircle.rotationPointX * f5, this.shapeCircle.rotationPointY * f5, this.shapeCircle.rotationPointZ * f5);
         GlStateManager.scale(circleSize, 1.0, circleSize);
         GlStateManager.translate(-this.shapeCircle.offsetX, -this.shapeCircle.offsetY, -this.shapeCircle.offsetZ);
         GlStateManager.translate(-this.shapeCircle.rotationPointX * f5, -this.shapeCircle.rotationPointY * f5, -this.shapeCircle.rotationPointZ * f5);
         this.shapeCircle.render(f5);
         GlStateManager.popMatrix();
         AbstractMobModel.alphaGlowDisable();
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(this.chestBelowBigRight.offsetX, this.chestBelowBigRight.offsetY, this.chestBelowBigRight.offsetZ);
      GlStateManager.translate(
         this.chestBelowBigRight.rotationPointX * f5, this.chestBelowBigRight.rotationPointY * f5, this.chestBelowBigRight.rotationPointZ * f5
      );
      GlStateManager.scale(1.06, 1.06, 1.06);
      GlStateManager.translate(-this.chestBelowBigRight.offsetX, -this.chestBelowBigRight.offsetY, -this.chestBelowBigRight.offsetZ);
      GlStateManager.translate(
         -this.chestBelowBigRight.rotationPointX * f5, -this.chestBelowBigRight.rotationPointY * f5, -this.chestBelowBigRight.rotationPointZ * f5
      );
      this.chestBelowBigRight.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.chestBelowBigFront.offsetX, this.chestBelowBigFront.offsetY, this.chestBelowBigFront.offsetZ);
      GlStateManager.translate(
         this.chestBelowBigFront.rotationPointX * f5, this.chestBelowBigFront.rotationPointY * f5, this.chestBelowBigFront.rotationPointZ * f5
      );
      GlStateManager.scale(1.06, 1.06, 1.06);
      GlStateManager.translate(-this.chestBelowBigFront.offsetX, -this.chestBelowBigFront.offsetY, -this.chestBelowBigFront.offsetZ);
      GlStateManager.translate(
         -this.chestBelowBigFront.rotationPointX * f5, -this.chestBelowBigFront.rotationPointY * f5, -this.chestBelowBigFront.rotationPointZ * f5
      );
      this.chestBelowBigFront.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.chestBelowBigBottom.offsetX, this.chestBelowBigBottom.offsetY, this.chestBelowBigBottom.offsetZ);
      GlStateManager.translate(
         this.chestBelowBigBottom.rotationPointX * f5, this.chestBelowBigBottom.rotationPointY * f5, this.chestBelowBigBottom.rotationPointZ * f5
      );
      GlStateManager.scale(1.06, 1.06, 1.06);
      GlStateManager.translate(-this.chestBelowBigBottom.offsetX, -this.chestBelowBigBottom.offsetY, -this.chestBelowBigBottom.offsetZ);
      GlStateManager.translate(
         -this.chestBelowBigBottom.rotationPointX * f5, -this.chestBelowBigBottom.rotationPointY * f5, -this.chestBelowBigBottom.rotationPointZ * f5
      );
      this.chestBelowBigBottom.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.chestLidBigTop.offsetX, this.chestLidBigTop.offsetY, this.chestLidBigTop.offsetZ);
      GlStateManager.translate(this.chestLidBigTop.rotationPointX * f5, this.chestLidBigTop.rotationPointY * f5, this.chestLidBigTop.rotationPointZ * f5);
      GlStateManager.scale(1.06, 1.06, 1.06);
      GlStateManager.translate(-this.chestLidBigTop.offsetX, -this.chestLidBigTop.offsetY, -this.chestLidBigTop.offsetZ);
      GlStateManager.translate(-this.chestLidBigTop.rotationPointX * f5, -this.chestLidBigTop.rotationPointY * f5, -this.chestLidBigTop.rotationPointZ * f5);
      this.chestLidBigTop.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.chestLidBigFront.offsetX, this.chestLidBigFront.offsetY, this.chestLidBigFront.offsetZ);
      GlStateManager.translate(this.chestLidBigFront.rotationPointX * f5, this.chestLidBigFront.rotationPointY * f5, this.chestLidBigFront.rotationPointZ * f5);
      GlStateManager.scale(1.06, 1.06, 1.06);
      GlStateManager.translate(-this.chestLidBigFront.offsetX, -this.chestLidBigFront.offsetY, -this.chestLidBigFront.offsetZ);
      GlStateManager.translate(
         -this.chestLidBigFront.rotationPointX * f5, -this.chestLidBigFront.rotationPointY * f5, -this.chestLidBigFront.rotationPointZ * f5
      );
      this.chestLidBigFront.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.chestLidBigLeft.offsetX, this.chestLidBigLeft.offsetY, this.chestLidBigLeft.offsetZ);
      GlStateManager.translate(this.chestLidBigLeft.rotationPointX * f5, this.chestLidBigLeft.rotationPointY * f5, this.chestLidBigLeft.rotationPointZ * f5);
      GlStateManager.scale(1.06, 1.06, 1.06);
      GlStateManager.translate(-this.chestLidBigLeft.offsetX, -this.chestLidBigLeft.offsetY, -this.chestLidBigLeft.offsetZ);
      GlStateManager.translate(-this.chestLidBigLeft.rotationPointX * f5, -this.chestLidBigLeft.rotationPointY * f5, -this.chestLidBigLeft.rotationPointZ * f5);
      this.chestLidBigLeft.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.chestLidBigRight.offsetX, this.chestLidBigRight.offsetY, this.chestLidBigRight.offsetZ);
      GlStateManager.translate(this.chestLidBigRight.rotationPointX * f5, this.chestLidBigRight.rotationPointY * f5, this.chestLidBigRight.rotationPointZ * f5);
      GlStateManager.scale(1.06, 1.06, 1.06);
      GlStateManager.translate(-this.chestLidBigRight.offsetX, -this.chestLidBigRight.offsetY, -this.chestLidBigRight.offsetZ);
      GlStateManager.translate(
         -this.chestLidBigRight.rotationPointX * f5, -this.chestLidBigRight.rotationPointY * f5, -this.chestLidBigRight.rotationPointZ * f5
      );
      this.chestLidBigRight.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.chestBelowBigBack.offsetX, this.chestBelowBigBack.offsetY, this.chestBelowBigBack.offsetZ);
      GlStateManager.translate(
         this.chestBelowBigBack.rotationPointX * f5, this.chestBelowBigBack.rotationPointY * f5, this.chestBelowBigBack.rotationPointZ * f5
      );
      GlStateManager.scale(1.06, 1.06, 1.06);
      GlStateManager.translate(-this.chestBelowBigBack.offsetX, -this.chestBelowBigBack.offsetY, -this.chestBelowBigBack.offsetZ);
      GlStateManager.translate(
         -this.chestBelowBigBack.rotationPointX * f5, -this.chestBelowBigBack.rotationPointY * f5, -this.chestBelowBigBack.rotationPointZ * f5
      );
      this.chestBelowBigBack.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.chestLidBigBack.offsetX, this.chestLidBigBack.offsetY, this.chestLidBigBack.offsetZ);
      GlStateManager.translate(this.chestLidBigBack.rotationPointX * f5, this.chestLidBigBack.rotationPointY * f5, this.chestLidBigBack.rotationPointZ * f5);
      GlStateManager.scale(1.06, 1.06, 1.06);
      GlStateManager.translate(-this.chestLidBigBack.offsetX, -this.chestLidBigBack.offsetY, -this.chestLidBigBack.offsetZ);
      GlStateManager.translate(-this.chestLidBigBack.rotationPointX * f5, -this.chestLidBigBack.rotationPointY * f5, -this.chestLidBigBack.rotationPointZ * f5);
      this.chestLidBigBack.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.chestBelowBigLeft.offsetX, this.chestBelowBigLeft.offsetY, this.chestBelowBigLeft.offsetZ);
      GlStateManager.translate(
         this.chestBelowBigLeft.rotationPointX * f5, this.chestBelowBigLeft.rotationPointY * f5, this.chestBelowBigLeft.rotationPointZ * f5
      );
      GlStateManager.scale(1.06, 1.06, 1.06);
      GlStateManager.translate(-this.chestBelowBigLeft.offsetX, -this.chestBelowBigLeft.offsetY, -this.chestBelowBigLeft.offsetZ);
      GlStateManager.translate(
         -this.chestBelowBigLeft.rotationPointX * f5, -this.chestBelowBigLeft.rotationPointY * f5, -this.chestBelowBigLeft.rotationPointZ * f5
      );
      this.chestBelowBigLeft.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
