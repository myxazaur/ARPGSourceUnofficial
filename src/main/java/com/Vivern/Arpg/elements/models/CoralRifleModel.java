//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class CoralRifleModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer riflee;
   public ModelRenderer shape;
   public ModelRenderer shape_1;
   public ModelRenderer shape_2;
   public ModelRenderer shape_3;
   public ModelRenderer shape_4;
   public ModelRenderer shape_5;
   public ModelRenderer shape_6;
   public ModelRenderer shape_7;
   public ModelRenderer shape_8;
   public ModelRenderer shape_9;
   public ModelRenderer shape_10;
   public ModelRenderer riflee_1;
   public ModelRenderer riflee_2;
   public ModelRenderer cora;
   public ModelRenderer riflee_3;
   public ModelRenderer riflee_4;
   public ModelRenderer riflee_5;
   public ModelRenderer riflee_6;

   public CoralRifleModel() {
      this.textureWidth = 24;
      this.textureHeight = 24;
      this.riflee_1 = new ModelRenderer(this, 1, 7);
      this.riflee_1.setRotationPoint(0.0F, -2.3F, -4.3F);
      this.riflee_1.addBox(-1.0F, -0.9F, -1.0F, 2, 1, 7, 0.0F);
      this.riflee = new ModelRenderer(this, 0, 15);
      this.riflee.setRotationPoint(0.0F, -1.8F, 1.7F);
      this.riflee.addBox(-1.5F, -0.9F, -1.0F, 3, 3, 2, 0.0F);
      this.shape_9 = new ModelRenderer(this, 8, 0);
      this.shape_9.setRotationPoint(0.9F, -1.4F, 4.4F);
      this.shape_9.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.2F);
      this.setRotateAngle(this.shape_9, -1.3203416F, 0.7285004F, -0.4098033F);
      this.riflee_4 = new ModelRenderer(this, 0, 9);
      this.riflee_4.setRotationPoint(0.0F, -1.2F, -8.3F);
      this.riflee_4.addBox(-0.5F, -0.5F, -1.0F, 1, 1, 3, 0.0F);
      this.setRotateAngle(this.riflee_4, 0.0F, 0.0F, (float) (Math.PI / 4));
      this.shapess1 = new ModelRenderer(this, 12, 7);
      this.shapess1.setRotationPoint(0.0F, 1.9F, 4.4F);
      this.shapess1.addBox(-1.0F, -0.5F, 0.0F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.shapess1, 0.5462881F, 0.0F, 0.0F);
      this.shape_10 = new ModelRenderer(this, 8, 0);
      this.shape_10.setRotationPoint(-0.2F, 0.5F, 4.8F);
      this.shape_10.addBox(-0.5F, -2.6F, -0.5F, 1, 3, 1, -0.2F);
      this.setRotateAngle(this.shape_10, 1.1838568F, -0.18203785F, 0.045553092F);
      this.shape_3 = new ModelRenderer(this, 8, 0);
      this.shape_3.setRotationPoint(-0.7F, -3.3F, 6.9F);
      this.shape_3.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.15F);
      this.setRotateAngle(this.shape_3, 0.4098033F, 0.27314404F, -0.13665928F);
      this.cora = new ModelRenderer(this, 7, 0);
      this.cora.setRotationPoint(0.0F, -1.3F, -3.3F);
      this.cora.addBox(-0.5F, -0.9F, -1.0F, 1, 2, 5, 0.0F);
      this.shape_2 = new ModelRenderer(this, 8, 0);
      this.shape_2.setRotationPoint(-0.5F, -3.3F, 7.3F);
      this.shape_2.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
      this.setRotateAngle(this.shape_2, -0.22759093F, -0.27314404F, 0.045553092F);
      this.shape_5 = new ModelRenderer(this, 8, 0);
      this.shape_5.setRotationPoint(-1.1F, -1.7F, 4.4F);
      this.shape_5.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, -0.2F);
      this.setRotateAngle(this.shape_5, -0.13665928F, 0.18203785F, -0.13665928F);
      this.shape_4 = new ModelRenderer(this, 4, 0);
      this.shape_4.setRotationPoint(-0.2F, -0.5F, 4.8F);
      this.shape_4.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, -0.1F);
      this.setRotateAngle(this.shape_4, -0.63739425F, -2.1855013F, 0.0F);
      this.shape_7 = new ModelRenderer(this, 4, 0);
      this.shape_7.setRotationPoint(0.2F, 0.1F, 4.8F);
      this.shape_7.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, -0.1F);
      this.setRotateAngle(this.shape_7, -0.63739425F, 2.048842F, 0.0F);
      this.riflee_6 = new ModelRenderer(this, 12, 16);
      this.riflee_6.setRotationPoint(0.0F, -1.8F, 9.7F);
      this.riflee_6.addBox(0.0F, -0.9F, -1.0F, 1, 1, 4, 0.0F);
      this.setRotateAngle(this.riflee_6, (float) (-Math.PI / 3), 0.0F, 0.0F);
      this.shape_8 = new ModelRenderer(this, 8, 0);
      this.shape_8.setRotationPoint(1.2F, -1.2F, 4.3F);
      this.shape_8.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.2F);
      this.setRotateAngle(this.shape_8, 1.5934856F, -0.27314404F, 0.4098033F);
      this.shape = new ModelRenderer(this, 0, 0);
      this.shape.setRotationPoint(0.0F, 1.4F, 4.7F);
      this.shape.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.shape, -0.18203785F, 0.0F, 0.0F);
      this.shape_1 = new ModelRenderer(this, 4, 0);
      this.shape_1.setRotationPoint(0.0F, -1.3F, 5.3F);
      this.shape_1.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, -0.05F);
      this.setRotateAngle(this.shape_1, -0.63739425F, -0.27314404F, 0.0F);
      this.riflee_2 = new ModelRenderer(this, 1, 7);
      this.riflee_2.setRotationPoint(0.0F, 0.7F, -4.3F);
      this.riflee_2.addBox(-1.0F, -0.9F, -1.0F, 2, 1, 7, 0.0F);
      this.riflee_3 = new ModelRenderer(this, 0, 5);
      this.riflee_3.setRotationPoint(0.0F, -1.2F, -5.3F);
      this.riflee_3.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.riflee_3, 0.0F, 0.0F, (float) (Math.PI / 4));
      this.riflee_5 = new ModelRenderer(this, 3, 15);
      this.riflee_5.setRotationPoint(0.0F, -1.8F, 3.7F);
      this.riflee_5.addBox(0.0F, -0.9F, -1.0F, 1, 1, 7, 0.0F);
      this.shape_6 = new ModelRenderer(this, 8, 0);
      this.shape_6.setRotationPoint(-1.4F, -2.2F, 3.8F);
      this.shape_6.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.15F);
      this.setRotateAngle(this.shape_6, 1.1838568F, -0.4098033F, -0.31869712F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.riflee_1.render(f5);
      this.shape_9.render(f5);
      this.riflee_4.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
      GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
      GlStateManager.scale(0.9, 1.0, 0.9);
      GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
      GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
      this.shapess1.render(f5);
      GlStateManager.popMatrix();
      this.shape_10.render(f5);
      this.shape_3.render(f5);
      this.cora.render(f5);
      this.shape_2.render(f5);
      this.shape_5.render(f5);
      this.shape_4.render(f5);
      this.shape_7.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.riflee_6.offsetX, this.riflee_6.offsetY, this.riflee_6.offsetZ);
      GlStateManager.translate(this.riflee_6.rotationPointX * f5, this.riflee_6.rotationPointY * f5, this.riflee_6.rotationPointZ * f5);
      GlStateManager.scale(1.05, 1.0, 1.0);
      GlStateManager.translate(-this.riflee_6.offsetX, -this.riflee_6.offsetY, -this.riflee_6.offsetZ);
      GlStateManager.translate(-this.riflee_6.rotationPointX * f5, -this.riflee_6.rotationPointY * f5, -this.riflee_6.rotationPointZ * f5);
      this.riflee_6.render(f5);
      GlStateManager.popMatrix();
      this.shape_8.render(f5);
      this.shape.render(f5);
      this.shape_1.render(f5);
      this.riflee_2.render(f5);
      this.riflee_3.render(f5);
      this.riflee_5.render(f5);
      this.shape_6.render(f5);
      this.riflee.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
