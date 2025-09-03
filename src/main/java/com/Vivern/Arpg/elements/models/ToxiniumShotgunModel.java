//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ToxiniumShotgunModel extends ModelBase {
   public ModelRenderer shapess;
   public ModelRenderer shape1;
   public ModelRenderer shapebarr;
   public ModelRenderer shapebarr_1;
   public ModelRenderer shapebarr_2;
   public ModelRenderer shapebarr_3;
   public ModelRenderer shapebarr_4;
   public ModelRenderer shapebarr_5;
   public ModelRenderer shapebarr_6;
   public ModelRenderer shaperel;
   public ModelRenderer shape2;
   public ModelRenderer shape3;

   public ToxiniumShotgunModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.shape2 = new ModelRenderer(this, 18, 0);
      this.shape2.setRotationPoint(-1.0F, 0.4F, 7.0F);
      this.shape2.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.shape2, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shapebarr_4 = new ModelRenderer(this, 0, 0);
      this.shapebarr_4.setRotationPoint(-3.3F, -0.5F, -8.7F);
      this.shapebarr_4.addBox(0.0F, 0.0F, 0.0F, 2, 2, 7, 0.0F);
      this.shapebarr_6 = new ModelRenderer(this, 0, 0);
      this.shapebarr_6.setRotationPoint(0.2F, 1.7F, -8.0F);
      this.shapebarr_6.addBox(0.0F, 0.0F, 0.0F, 2, 2, 7, 0.0F);
      this.shapess = new ModelRenderer(this, 18, 20);
      this.shapess.setRotationPoint(-1.0F, 2.2F, 5.1F);
      this.shapess.addBox(0.0F, -0.5F, 0.0F, 2, 6, 2, 0.0F);
      this.setRotateAngle(this.shapess, 0.27314404F, 0.0F, 0.0F);
      this.shapebarr_3 = new ModelRenderer(this, 0, 0);
      this.shapebarr_3.setRotationPoint(-1.0F, -0.5F, -8.7F);
      this.shapebarr_3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 7, 0.0F);
      this.shapebarr = new ModelRenderer(this, 0, 0);
      this.shapebarr.setRotationPoint(-2.2F, -2.7F, -9.4F);
      this.shapebarr.addBox(0.0F, 0.0F, 0.0F, 2, 2, 7, 0.0F);
      this.shaperel = new ModelRenderer(this, 0, 10);
      this.shaperel.setRotationPoint(-2.5F, 1.5F, -3.5F);
      this.shaperel.addBox(0.0F, 0.0F, 0.0F, 5, 3, 5, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 18);
      this.shape1.setRotationPoint(-2.0F, -1.6F, -3.0F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 4, 4, 10, 0.0F);
      this.shapebarr_2 = new ModelRenderer(this, 0, 0);
      this.shapebarr_2.setRotationPoint(1.3F, -0.5F, -8.7F);
      this.shapebarr_2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 7, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 22);
      this.shape3.setRotationPoint(-1.0F, -1.6F, 15.0F);
      this.shape3.addBox(0.0F, 0.0F, 0.0F, 2, 5, 1, 0.0F);
      this.shapebarr_1 = new ModelRenderer(this, 0, 0);
      this.shapebarr_1.setRotationPoint(0.2F, -2.7F, -9.4F);
      this.shapebarr_1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 7, 0.0F);
      this.shapebarr_5 = new ModelRenderer(this, 0, 0);
      this.shapebarr_5.setRotationPoint(-2.2F, 1.7F, -8.0F);
      this.shapebarr_5.addBox(0.0F, 0.0F, 0.0F, 2, 2, 7, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape2.render(f5);
      this.shapebarr_4.render(f5);
      this.shapebarr_6.render(f5);
      this.shapess.render(f5);
      this.shapebarr_3.render(f5);
      this.shapebarr.render(f5);
      this.shaperel.render(f5);
      this.shape1.render(f5);
      this.shapebarr_2.render(f5);
      this.shape3.render(f5);
      this.shapebarr_1.render(f5);
      this.shapebarr_5.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
