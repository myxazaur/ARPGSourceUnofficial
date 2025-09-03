//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ResearchTableResModel extends ModelBase {
   public ModelRenderer shapeBlank1;
   public ModelRenderer shapeBlank2;
   public ModelRenderer shapeF3;
   public ModelRenderer shapeF2;
   public ModelRenderer shapeF1;
   public ModelRenderer shape9;
   public ModelRenderer shape7;
   public ModelRenderer shape11;
   public ModelRenderer shapeLa;
   public ModelRenderer shapeSphere;
   public ModelRenderer shapeL;

   public ResearchTableResModel() {
      this.textureWidth = 24;
      this.textureHeight = 24;
      this.shapeF1 = new ModelRenderer(this, 0, 0);
      this.shapeF1.setRotationPoint(-6.5F, 7.5F, -4.0F);
      this.shapeF1.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
      this.setRotateAngle(this.shapeF1, 0.0F, -0.3642502F, 0.0F);
      this.shapeBlank1 = new ModelRenderer(this, -4, 0);
      this.shapeBlank1.setRotationPoint(0.0F, 7.9F, -7.5F);
      this.shapeBlank1.addBox(0.0F, 0.0F, 0.0F, 8, 0, 12, 0.0F);
      this.setRotateAngle(this.shapeBlank1, 0.0F, -0.31869712F, 0.0F);
      this.shapeLa = new ModelRenderer(this, 16, 17);
      this.shapeLa.setRotationPoint(-7.2F, 7.4F, 1.8F);
      this.shapeLa.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
      this.setRotateAngle(this.shapeLa, 0.0F, 0.7285004F, 0.0F);
      this.shape9 = new ModelRenderer(this, 0, 17);
      this.shape9.setRotationPoint(5.0F, 7.0F, 3.0F);
      this.shape9.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
      this.shapeSphere = new ModelRenderer(this, 0, 9);
      this.shapeSphere.setRotationPoint(1.0F, -1.05F, 1.0F);
      this.shapeSphere.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.shapeSphere, 0.0F, -0.4553564F, 0.0F);
      this.shapeF3 = new ModelRenderer(this, 0, 6);
      this.shapeF3.setRotationPoint(-2.5F, 7.5F, -2.0F);
      this.shapeF3.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
      this.setRotateAngle(this.shapeF3, 0.0F, -0.95609134F, 0.0F);
      this.shapeF2 = new ModelRenderer(this, 0, 3);
      this.shapeF2.setRotationPoint(-5.5F, 7.5F, -1.0F);
      this.shapeF2.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
      this.shapeBlank2 = new ModelRenderer(this, -4, 0);
      this.shapeBlank2.setRotationPoint(-6.5F, 7.95F, -4.5F);
      this.shapeBlank2.addBox(0.0F, 0.0F, 0.0F, 8, 0, 12, 0.0F);
      this.setRotateAngle(this.shapeBlank2, 0.0F, 0.091106184F, 0.0F);
      this.shape7 = new ModelRenderer(this, 5, 12);
      this.shape7.setRotationPoint(-1.0F, 7.85F, -7.0F);
      this.shape7.addBox(0.0F, 0.0F, 0.0F, 7, 0, 5, 0.0F);
      this.setRotateAngle(this.shape7, 0.0F, -0.045553092F, 0.0F);
      this.shapeL = new ModelRenderer(this, 7, 18);
      this.shapeL.setRotationPoint(0.0F, 0.0F, 3.0F);
      this.shapeL.addBox(-1.5F, 0.0F, 0.03F, 3, 1, 3, 0.0F);
      this.shape11 = new ModelRenderer(this, 0, 13);
      this.shape11.setRotationPoint(6.5F, 6.3F, 1.0F);
      this.shape11.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, -0.1F);
      this.setRotateAngle(this.shape11, 0.0F, 0.13665928F, 0.0F);
      this.shape9.addChild(this.shapeSphere);
      this.shapeLa.addChild(this.shapeL);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shapeF1.render(f5);
      this.shapeBlank1.render(f5);
      this.shapeLa.render(f5);
      this.shape9.render(f5);
      this.shapeF3.render(f5);
      this.shapeF2.render(f5);
      this.shapeBlank2.render(f5);
      this.shape7.render(f5);
      this.shape11.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
