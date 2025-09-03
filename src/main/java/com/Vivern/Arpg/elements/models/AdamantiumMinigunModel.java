//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class AdamantiumMinigunModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shapess2;
   public ModelRenderer shapess3;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape5;
   public ModelRenderer roundBase;
   public ModelRenderer shape6;
   public ModelRenderer shape4;
   public ModelRenderer drum1;
   public ModelRenderer barrel1;
   public ModelRenderer barrel2;
   public ModelRenderer barrel3;
   public ModelRenderer barrel4;
   public ModelRenderer round1;
   public ModelRenderer round2;
   public ModelRenderer drum2;

   public AdamantiumMinigunModel() {
      this.textureWidth = 48;
      this.textureHeight = 32;
      this.barrel2 = new ModelRenderer(this, 0, 19);
      this.barrel2.setRotationPoint(-1.0F, -1.0F, -2.0F);
      this.barrel2.addBox(-0.5F, -0.5F, -12.0F, 1, 1, 12, 0.0F);
      this.barrel4 = new ModelRenderer(this, 0, 19);
      this.barrel4.mirror = true;
      this.barrel4.setRotationPoint(-1.0F, 1.0F, -2.0F);
      this.barrel4.addBox(-0.5F, -0.5F, -12.0F, 1, 1, 12, 0.0F);
      this.shape1 = new ModelRenderer(this, 26, 19);
      this.shape1.setRotationPoint(0.0F, 9.0F, 3.0F);
      this.shape1.addBox(-2.0F, -0.5F, 0.0F, 4, 6, 7, 0.0F);
      this.round1 = new ModelRenderer(this, 14, 25);
      this.round1.setRotationPoint(0.0F, 0.0F, -6.0F);
      this.round1.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 2, 0.0F);
      this.roundBase = new ModelRenderer(this, 14, 25);
      this.roundBase.setRotationPoint(0.0F, 11.5F, 3.0F);
      this.roundBase.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 2, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 7);
      this.shape4.setRotationPoint(0.0F, 12.0F, 10.0F);
      this.shape4.addBox(-1.0F, -0.5F, 0.0F, 2, 2, 2, 0.0F);
      this.shapess2 = new ModelRenderer(this, 0, 0);
      this.shapess2.setRotationPoint(0.0F, 6.0F, 7.0F);
      this.shapess2.addBox(-3.0F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
      this.round2 = new ModelRenderer(this, 14, 25);
      this.round2.setRotationPoint(0.0F, 0.0F, -13.5F);
      this.round2.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 2, 0.0F);
      this.drum2 = new ModelRenderer(this, 34, 0);
      this.drum2.setRotationPoint(4.0F, -3.0F, 3.0F);
      this.drum2.addBox(-2.0F, 0.0F, 0.0F, 2, 5, 2, 0.0F);
      this.shapess1 = new ModelRenderer(this, 0, 11);
      this.shapess1.setRotationPoint(0.0F, 5.0F, 7.0F);
      this.shapess1.addBox(-3.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
      this.drum1 = new ModelRenderer(this, 28, 9);
      this.drum1.setRotationPoint(0.0F, 14.5F, 2.0F);
      this.drum1.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 6, 0.0F);
      this.shape6 = new ModelRenderer(this, 0, 14);
      this.shape6.mirror = true;
      this.shape6.setRotationPoint(0.0F, 9.5F, 4.0F);
      this.shape6.addBox(-3.0F, 0.0F, 0.0F, 1, 7, 5, 0.0F);
      this.barrel1 = new ModelRenderer(this, 0, 19);
      this.barrel1.mirror = true;
      this.barrel1.setRotationPoint(1.0F, -1.0F, -2.0F);
      this.barrel1.addBox(-0.5F, -0.5F, -12.0F, 1, 1, 12, 0.0F);
      this.shape3 = new ModelRenderer(this, 11, 4);
      this.shape3.setRotationPoint(0.0F, 10.0F, 12.0F);
      this.shape3.addBox(-1.0F, -0.5F, 0.0F, 2, 3, 1, 0.0F);
      this.shapess3 = new ModelRenderer(this, 0, 0);
      this.shapess3.setRotationPoint(0.0F, 6.0F, 7.0F);
      this.shapess3.addBox(2.0F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
      this.barrel3 = new ModelRenderer(this, 0, 19);
      this.barrel3.setRotationPoint(1.0F, 1.0F, -2.0F);
      this.barrel3.addBox(-0.5F, -0.5F, -12.0F, 1, 1, 12, 0.0F);
      this.shape5 = new ModelRenderer(this, 0, 14);
      this.shape5.setRotationPoint(0.0F, 9.5F, 4.0F);
      this.shape5.addBox(2.0F, 0.0F, 0.0F, 1, 4, 5, 0.0F);
      this.shape2 = new ModelRenderer(this, 17, 0);
      this.shape2.setRotationPoint(0.0F, 9.0F, 10.0F);
      this.shape2.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 3, 0.0F);
      this.roundBase.addChild(this.barrel2);
      this.roundBase.addChild(this.barrel4);
      this.roundBase.addChild(this.round1);
      this.roundBase.addChild(this.round2);
      this.drum1.addChild(this.drum2);
      this.roundBase.addChild(this.barrel1);
      this.roundBase.addChild(this.barrel3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float ft1 = GetMOP.getfromto(f2, 0.0F, 0.1F) - GetMOP.getfromto(f2, 0.9F, 1.0F);
      float ft2 = GetMOP.getfromto(f2, 0.2F, 0.3F);
      float ft3 = GetMOP.getfromto(f2, 0.3F, 0.4F);
      float ft4 = GetMOP.getfromto(f2, 0.6F, 0.8F);
      float ft5 = GetMOP.getfromto(f2, 0.7F, 0.8F);
      float ft6 = GetMOP.getfromto(f2, 0.8F, 0.86F);
      float ft7 = GetMOP.getfromto(f2, 0.86F, 0.9F);
      this.drum1.rotateAngleX = -0.35F * ft1;
      this.drum1.offsetX = 4.0F * ft2 + 4.0F * ft3 - 4.0F * ft4 - 2.0F * ft5 + 0.3F * ft6 - 2.3F * ft7;
      this.drum1.offsetY = 2.0F * ft2 - 2.0F * ft3 + 2.0F * ft4 - 1.0F * ft5 + 0.6F * ft6 - 1.6F * ft7;
      if (f2 <= 0.4F || f2 >= 0.6F) {
         this.drum1.render(f5);
      }

      this.roundBase.rotateAngleZ = f * -0.017453F;
      this.shape1.render(f5);
      this.roundBase.render(f5);
      this.shape4.render(f5);
      this.shapess2.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
      GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.2, 1.2);
      GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
      GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
      this.shapess1.render(f5);
      GlStateManager.popMatrix();
      this.shape6.render(f5);
      this.shape3.render(f5);
      this.shapess3.render(f5);
      this.shape5.render(f5);
      this.shape2.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
