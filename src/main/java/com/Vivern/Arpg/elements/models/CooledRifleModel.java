//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class CooledRifleModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shapess2;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer shape8;
   public ModelRenderer ammo1;
   public ModelRenderer ammo2;
   public ModelRenderer rotator;
   public ModelRenderer ammo1a;
   public ModelRenderer ammo2a;

   public CooledRifleModel() {
      this.textureWidth = 64;
      this.textureHeight = 16;
      this.shape2 = new ModelRenderer(this, 21, 0);
      this.shape2.setRotationPoint(0.0F, -1.5F, -7.0F);
      this.shape2.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 15, 0.0F);
      this.ammo2 = new ModelRenderer(this, 42, 11);
      this.ammo2.setRotationPoint(-0.5F, 0.5F, 7.0F);
      this.ammo2.addBox(2.0F, -0.5F, -8.0F, 4, 1, 2, 0.0F);
      this.shape5 = new ModelRenderer(this, 22, 0);
      this.shape5.setRotationPoint(0.0F, -0.05F, -10.0F);
      this.shape5.addBox(-1.0F, -1.5F, -1.5F, 2, 3, 3, 0.0F);
      this.setRotateAngle(this.shape5, (float) (Math.PI / 4), 0.0F, 0.0F);
      this.rotator = new ModelRenderer(this, 22, 6);
      this.rotator.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.rotator.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 5, 0.0F);
      this.shape8 = new ModelRenderer(this, 0, 0);
      this.shape8.setRotationPoint(0.0F, 0.0F, 11.0F);
      this.shape8.addBox(-0.5F, -0.5F, 0.0F, 1, 3, 1, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 5);
      this.shape3.setRotationPoint(0.0F, 1.5F, -5.0F);
      this.shape3.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 6, 0.0F);
      this.shape1 = new ModelRenderer(this, 3, 0);
      this.shape1.setRotationPoint(0.0F, -0.5F, -6.0F);
      this.shape1.addBox(-1.5F, 0.0F, 0.0F, 3, 2, 13, 0.0F);
      this.ammo2a = new ModelRenderer(this, 10, 7);
      this.ammo2a.setRotationPoint(1.5F, 0.5F, -7.5F);
      this.ammo2a.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
      this.ammo1a = new ModelRenderer(this, 10, 7);
      this.ammo1a.setRotationPoint(1.5F, 0.5F, -11.5F);
      this.ammo1a.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
      this.shapess1 = new ModelRenderer(this, 43, 0);
      this.shapess1.setRotationPoint(0.0F, 1.0F, 4.0F);
      this.shapess1.addBox(-1.0F, 0.4F, -1.0F, 2, 6, 2, 0.0F);
      this.setRotateAngle(this.shapess1, 0.13665928F, 0.0F, 0.0F);
      this.shape4 = new ModelRenderer(this, 54, 10);
      this.shape4.setRotationPoint(0.5F, 0.0F, 7.0F);
      this.shape4.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 4, 0.0F);
      this.shape7 = new ModelRenderer(this, 42, 0);
      this.shape7.setRotationPoint(0.0F, -2.0F, -10.0F);
      this.shape7.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 9, 0.0F);
      this.shapess2 = new ModelRenderer(this, 2, 0);
      this.shapess2.setRotationPoint(0.0F, 7.0F, 2.2F);
      this.shapess2.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 4, 0.0F);
      this.shape6 = new ModelRenderer(this, 32, 0);
      this.shape6.setRotationPoint(0.0F, 0.0F, -15.0F);
      this.shape6.addBox(-0.5F, 0.0F, -0.5F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.shape6, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.ammo1 = new ModelRenderer(this, 42, 11);
      this.ammo1.setRotationPoint(-0.5F, 0.5F, 7.0F);
      this.ammo1.addBox(2.0F, -0.5F, -12.0F, 4, 1, 2, 0.0F);
      this.ammo2.addChild(this.ammo2a);
      this.ammo1.addChild(this.ammo1a);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f1 == 1.0F) {
         f1 = 0.0F;
      }

      this.ammo2.isHidden = f1 == 0.0F;
      float ft1 = 1.0F - GetMOP.getfromto(f1, 0.0F, 0.35F);
      float ft2 = GetMOP.getfromto(f1, 0.4F, 0.6F);
      float ft3 = GetMOP.getfromto(f1, 0.5F, 0.6F);
      float ft4 = GetMOP.getfromto(f1, 0.5F, 1.0F);
      this.ammo2.rotateAngleY = -1.04F * ft1;
      this.ammo2.rotateAngleZ = 0.54F * ft1;
      this.ammo2.offsetZ = -4.0F * ft2;
      this.ammo1.offsetZ = -2.0F * ft3;
      this.ammo1.rotateAngleY = -1.04F * ft4;
      this.ammo1.rotateAngleZ = 0.54F * ft4;
      GlStateManager.disableCull();
      this.rotator.rotateAngleZ = 0.017F * AnimationTimer.tick;
      AbstractMobModel.light(170, false);
      this.rotator.render(f5);
      AbstractMobModel.returnlight();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.0);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      this.shape2.render(f5);
      this.ammo2.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape5.offsetX, this.shape5.offsetY, this.shape5.offsetZ);
      GlStateManager.translate(this.shape5.rotationPointX * f5, this.shape5.rotationPointY * f5, this.shape5.rotationPointZ * f5);
      GlStateManager.scale(1.15, 0.95, 1.0);
      GlStateManager.translate(-this.shape5.offsetX, -this.shape5.offsetY, -this.shape5.offsetZ);
      GlStateManager.translate(-this.shape5.rotationPointX * f5, -this.shape5.rotationPointY * f5, -this.shape5.rotationPointZ * f5);
      this.shape5.render(f5);
      GlStateManager.popMatrix();
      this.shape8.render(f5);
      this.shape3.render(f5);
      this.shapess1.render(f5);
      this.shape4.render(f5);
      this.shape7.render(f5);
      this.shapess2.render(f5);
      this.shape6.render(f5);
      this.ammo1.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
