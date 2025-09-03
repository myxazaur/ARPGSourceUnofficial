//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class HadronBlasterModel extends ModelBase {
   public ModelRenderer shape0;
   public ModelRenderer shapess;
   public ModelRenderer colbshape;
   public ModelRenderer colb;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer pipe1;
   public ModelRenderer pipe2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer shape8;
   public ModelRenderer back;
   public ModelRenderer core;
   public ModelRenderer shape9;
   public ModelRenderer shape10;
   public ModelRenderer scope;
   public ModelRenderer colbpipe1a;
   public ModelRenderer colbpipe2a;
   public ModelRenderer colbpipe3a;
   public ModelRenderer colbpipe4a;
   public ModelRenderer colbpipe1b;
   public ModelRenderer colbpipe2b;
   public ModelRenderer colbpipe3b;
   public ModelRenderer colbpipe4b;

   public HadronBlasterModel() {
      this.textureWidth = 48;
      this.textureHeight = 16;
      this.shape9 = new ModelRenderer(this, 17, 0);
      this.shape9.setRotationPoint(0.0F, 0.0F, -2.5F);
      this.shape9.addBox(-3.25F, 0.6F, 0.0F, 3, 1, 1, 0.0F);
      this.colb = new ModelRenderer(this, 6, 2);
      this.colb.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.colb.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
      this.colbpipe2b = new ModelRenderer(this, 44, 0);
      this.colbpipe2b.setRotationPoint(0.0F, 2.0F, 0.0F);
      this.colbpipe2b.addBox(-0.49F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.colbpipe2b, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.colbshape = new ModelRenderer(this, 28, 0);
      this.colbshape.setRotationPoint(0.0F, 0.0F, 2.3F);
      this.colbshape.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
      this.shape3 = new ModelRenderer(this, 16, 2);
      this.shape3.setRotationPoint(0.0F, 0.0F, -11.0F);
      this.shape3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 10, 0.0F);
      this.shape0 = new ModelRenderer(this, 34, 6);
      this.shape0.setRotationPoint(-2.0F, -1.3F, 3.5F);
      this.shape0.addBox(0.0F, 0.0F, 0.0F, 4, 3, 3, 0.0F);
      this.colbpipe4a = new ModelRenderer(this, 37, 0);
      this.colbpipe4a.setRotationPoint(0.0F, 1.0F, 1.4F);
      this.colbpipe4a.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.colbpipe4a, (float) (-Math.PI * 2.0 / 3.0), 0.0F, (float) -Math.PI);
      this.shapess = new ModelRenderer(this, 0, 6);
      this.shapess.setRotationPoint(0.0F, 1.85F, 6.7F);
      this.shapess.addBox(-0.5F, -1.0F, -3.0F, 1, 4, 2, 0.0F);
      this.setRotateAngle(this.shapess, 0.27314404F, 0.0F, 0.0F);
      this.shape7 = new ModelRenderer(this, 0, 0);
      this.shape7.setRotationPoint(0.0F, 0.0F, -6.0F);
      this.shape7.addBox(0.0F, -0.5F, 0.0F, 2, 2, 2, 0.0F);
      this.pipe2 = new ModelRenderer(this, 10, 6);
      this.pipe2.setRotationPoint(0.0F, 1.5F, -3.0F);
      this.pipe2.addBox(-0.5F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
      this.setRotateAngle(this.pipe2, (float) (Math.PI / 3), 0.0F, 0.0F);
      this.shape5 = new ModelRenderer(this, 0, 0);
      this.shape5.mirror = true;
      this.shape5.setRotationPoint(0.0F, 0.0F, -14.0F);
      this.shape5.addBox(0.0F, -1.0F, 0.0F, 1, 2, 14, 0.0F);
      this.shape8 = new ModelRenderer(this, 17, 0);
      this.shape8.setRotationPoint(0.0F, 0.0F, -2.5F);
      this.shape8.addBox(0.25F, 0.6F, 0.0F, 3, 1, 1, 0.0F);
      this.colbpipe1b = new ModelRenderer(this, 44, 0);
      this.colbpipe1b.setRotationPoint(0.0F, 2.0F, 0.0F);
      this.colbpipe1b.addBox(-0.49F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.colbpipe1b, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 0);
      this.shape4.setRotationPoint(0.0F, 0.0F, -14.0F);
      this.shape4.addBox(0.0F, -1.0F, 0.0F, 1, 2, 14, 0.0F);
      this.shape10 = new ModelRenderer(this, 0, 0);
      this.shape10.mirror = true;
      this.shape10.setRotationPoint(0.0F, 0.0F, -6.0F);
      this.shape10.addBox(0.0F, -0.5F, 0.0F, 2, 2, 2, 0.0F);
      this.shape6 = new ModelRenderer(this, 16, 2);
      this.shape6.mirror = true;
      this.shape6.setRotationPoint(0.0F, 0.0F, -11.0F);
      this.shape6.addBox(0.0F, 0.0F, 0.0F, 1, 2, 10, 0.0F);
      this.colbpipe2a = new ModelRenderer(this, 37, 0);
      this.colbpipe2a.setRotationPoint(1.0F, 0.0F, 1.4F);
      this.colbpipe2a.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.colbpipe2a, (float) (-Math.PI * 2.0 / 3.0), 0.0F, (float) (Math.PI / 2));
      this.pipe1 = new ModelRenderer(this, 6, 6);
      this.pipe1.setRotationPoint(0.0F, 4.5F, 2.0F);
      this.pipe1.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.pipe1, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.back = new ModelRenderer(this, 40, 13);
      this.back.setRotationPoint(0.0F, -0.5F, 5.5F);
      this.back.addBox(-1.5F, 0.0F, 0.0F, 3, 2, 1, 0.0F);
      this.setRotateAngle(this.back, 0.27314404F, 0.0F, 0.0F);
      this.colbpipe3b = new ModelRenderer(this, 44, 0);
      this.colbpipe3b.setRotationPoint(0.0F, 2.0F, 0.0F);
      this.colbpipe3b.addBox(-0.49F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.colbpipe3b, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 16, 7);
      this.shape1.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.shape1.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
      this.shape2 = new ModelRenderer(this, 16, 2);
      this.shape2.setRotationPoint(0.0F, 0.0F, -5.0F);
      this.shape2.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3, 0.0F);
      this.core = new ModelRenderer(this, 6, 0);
      this.core.setRotationPoint(0.0F, 0.0F, 1.0F);
      this.core.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.colbpipe3a = new ModelRenderer(this, 37, 0);
      this.colbpipe3a.setRotationPoint(-1.0F, 0.0F, 1.4F);
      this.colbpipe3a.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.colbpipe3a, (float) (-Math.PI * 2.0 / 3.0), 0.0F, (float) (-Math.PI / 2));
      this.colbpipe1a = new ModelRenderer(this, 37, 0);
      this.colbpipe1a.setRotationPoint(0.0F, -1.0F, 1.4F);
      this.colbpipe1a.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.colbpipe1a, (float) (-Math.PI * 2.0 / 3.0), 0.0F, 0.0F);
      this.colbpipe4b = new ModelRenderer(this, 44, 0);
      this.colbpipe4b.setRotationPoint(0.0F, 2.0F, 0.0F);
      this.colbpipe4b.addBox(-0.49F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.colbpipe4b, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.scope = new ModelRenderer(this, 28, 10);
      this.scope.setRotationPoint(-1.0F, -2.0F, 5.0F);
      this.scope.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
      this.colbpipe2a.addChild(this.colbpipe2b);
      this.colbshape.addChild(this.colbpipe4a);
      this.colbpipe1a.addChild(this.colbpipe1b);
      this.colbshape.addChild(this.colbpipe2a);
      this.colbpipe3a.addChild(this.colbpipe3b);
      this.colbshape.addChild(this.colbpipe3a);
      this.colbshape.addChild(this.colbpipe1a);
      this.colbpipe4a.addChild(this.colbpipe4b);
   }

   public static float getfromto(float mainNumber, float from, float to) {
      if (mainNumber < from) {
         return 0.0F;
      } else if (mainNumber > to) {
         return 1.0F;
      } else {
         float n = Math.max(mainNumber - from, 0.0F);
         float f = 1.0F / (to - from);
         return n * f;
      }
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f4 != 2.0F) {
         float animVALUE1 = 0.5F * getfromto(f, 0.0F, 0.2F);
         float animVALUE4 = 0.5F * getfromto(f, 0.2F, 0.4F);
         this.shape4.offsetX = 1.0F + animVALUE1;
         this.shape7.offsetX = 1.5F + animVALUE1 + animVALUE4;
         this.shape3.offsetX = 2.0F + animVALUE1 + animVALUE4;
         this.shape4.offsetZ = -0.5F + animVALUE4;
         this.shape3.offsetZ = 2.0F * getfromto(f, 0.4F, 0.6F);
         this.shape7.offsetZ = 1.0F * getfromto(f, 0.6F, 0.8F);
         this.shape2.offsetZ = 1.5F - 1.5F * getfromto(f, 0.8F, 1.0F);
         this.shape5.offsetX = -this.shape4.offsetX - 1.0F;
         this.shape10.offsetX = -this.shape7.offsetX - 2.0F;
         this.shape6.offsetX = -this.shape3.offsetX - 1.0F;
         this.shape5.offsetZ = this.shape4.offsetZ;
         this.shape10.offsetZ = this.shape7.offsetZ;
         this.shape6.offsetZ = this.shape3.offsetZ;
         float animVALUE2 = (float) (-Math.PI * 2.0 / 3.0) - 0.8675F * f1;
         float animVALUE3 = (float) (Math.PI / 6) + 0.777F * f1;
         this.colbpipe1a.rotateAngleX = animVALUE2;
         this.colbpipe1b.rotateAngleX = animVALUE3;
         this.colbpipe2a.rotateAngleX = animVALUE2;
         this.colbpipe2b.rotateAngleX = animVALUE3;
         this.colbpipe3a.rotateAngleX = animVALUE2;
         this.colbpipe3b.rotateAngleX = animVALUE3;
         this.colbpipe4a.rotateAngleX = animVALUE2;
         this.colbpipe4b.rotateAngleX = animVALUE3;
         this.colbshape.rotateAngleZ = f2 * 0.017453F;
         this.colb.rotateAngleZ = f2 * 0.017453F;
         this.shape9.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.colbshape.offsetX, this.colbshape.offsetY, this.colbshape.offsetZ);
         GlStateManager.translate(this.colbshape.rotationPointX * f5, this.colbshape.rotationPointY * f5, this.colbshape.rotationPointZ * f5);
         GlStateManager.scale(0.8, 0.8, 0.7);
         GlStateManager.translate(-this.colbshape.offsetX, -this.colbshape.offsetY, -this.colbshape.offsetZ);
         GlStateManager.translate(-this.colbshape.rotationPointX * f5, -this.colbshape.rotationPointY * f5, -this.colbshape.rotationPointZ * f5);
         this.colbshape.render(f5);
         GlStateManager.popMatrix();
         this.shape3.render(f5);
         this.shape0.render(f5);
         this.shapess.render(f5);
         this.shape7.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.pipe2.offsetX, this.pipe2.offsetY, this.pipe2.offsetZ);
         GlStateManager.translate(this.pipe2.rotationPointX * f5, this.pipe2.rotationPointY * f5, this.pipe2.rotationPointZ * f5);
         GlStateManager.scale(0.95, 1.0, 1.0);
         GlStateManager.translate(-this.pipe2.offsetX, -this.pipe2.offsetY, -this.pipe2.offsetZ);
         GlStateManager.translate(-this.pipe2.rotationPointX * f5, -this.pipe2.rotationPointY * f5, -this.pipe2.rotationPointZ * f5);
         this.pipe2.render(f5);
         GlStateManager.popMatrix();
         this.shape5.render(f5);
         this.shape8.render(f5);
         this.shape4.render(f5);
         this.shape10.render(f5);
         this.shape6.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.pipe1.offsetX, this.pipe1.offsetY, this.pipe1.offsetZ);
         GlStateManager.translate(this.pipe1.rotationPointX * f5, this.pipe1.rotationPointY * f5, this.pipe1.rotationPointZ * f5);
         GlStateManager.scale(0.97, 1.0, 1.0);
         GlStateManager.translate(-this.pipe1.offsetX, -this.pipe1.offsetY, -this.pipe1.offsetZ);
         GlStateManager.translate(-this.pipe1.rotationPointX * f5, -this.pipe1.rotationPointY * f5, -this.pipe1.rotationPointZ * f5);
         this.pipe1.render(f5);
         GlStateManager.popMatrix();
         this.back.render(f5);
         this.shape1.render(f5);
         this.shape2.render(f5);
         this.setRotateAngle(this.core, AnimationTimer.rainbow1 * 0.28F, AnimationTimer.tick * 0.089F, AnimationTimer.rainbow2 * 0.11F);
         AbstractMobModel.alphaGlow();
         AbstractMobModel.light(240, false);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.core.offsetX, this.core.offsetY, this.core.offsetZ);
         GlStateManager.translate(this.core.rotationPointX * f5, this.core.rotationPointY * f5, this.core.rotationPointZ * f5);
         GlStateManager.scale(f3, f3, f3);
         GlStateManager.translate(-this.core.offsetX, -this.core.offsetY, -this.core.offsetZ);
         GlStateManager.translate(-this.core.rotationPointX * f5, -this.core.rotationPointY * f5, -this.core.rotationPointZ * f5);
         this.core.render(f5);
         this.setRotateAngle(this.core, AnimationTimer.rainbow2 * 0.22F, AnimationTimer.rainbow1 * 0.17F, AnimationTimer.tick * 0.059F);
         this.core.render(f5);
         GlStateManager.popMatrix();
         AbstractMobModel.returnlight();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.colb.offsetX, this.colb.offsetY, this.colb.offsetZ);
         GlStateManager.translate(this.colb.rotationPointX * f5, this.colb.rotationPointY * f5, this.colb.rotationPointZ * f5);
         GlStateManager.scale(1.2, 1.2, 1.2);
         GlStateManager.translate(-this.colb.offsetX, -this.colb.offsetY, -this.colb.offsetZ);
         GlStateManager.translate(-this.colb.rotationPointX * f5, -this.colb.rotationPointY * f5, -this.colb.rotationPointZ * f5);
         this.colb.render(f5);
         GlStateManager.popMatrix();
         if (f4 == 0.0F) {
            AbstractMobModel.alphaGlowDisable();
         }
      }

      this.scope.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
