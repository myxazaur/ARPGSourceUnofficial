package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SharkRocketModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape3_1;
   public float rotate;

   public SharkRocketModel() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 8, 0.0F);
      this.setRotateAngle(this.shape1, 0.0F, (float) Math.PI, 0.0F);
      this.shape2 = new ModelRenderer(this, 24, 0);
      this.shape2.setRotationPoint(0.0F, 0.0F, 4.0F);
      this.shape2.addBox(-0.5F, -4.0F, 0.0F, 1, 8, 1, 0.0F);
      this.shape3_1 = new ModelRenderer(this, 16, 0);
      this.shape3_1.setRotationPoint(0.0F, 3.5F, 1.0F);
      this.shape3_1.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 0);
      this.shape3.setRotationPoint(0.0F, -3.5F, 1.0F);
      this.shape3.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
      this.shape2.addChild(this.shape3_1);
      this.shape2.addChild(this.shape3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.rotate = f4;
      this.setRotateAngle(this.shape2, 0.0F, 0.0F, this.rotate);
      this.shape1.render(f5);
      this.shape2.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
