package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ItemChargerModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shapeGlow;
   public ModelRenderer lamp1;
   public ModelRenderer lamp2;
   public ModelRenderer shapes1;
   public ModelRenderer shape3;
   public ModelRenderer lamp1glow;
   public ModelRenderer lamp2glow;
   public ModelRenderer shapes3;
   public ModelRenderer shapes2;
   public ModelRenderer shapes4;
   public ModelRenderer shapes5;

   public ItemChargerModel() {
      this.textureWidth = 64;
      this.textureHeight = 48;
      this.shape1 = new ModelRenderer(this, 0, 24);
      this.shape1.setRotationPoint(-8.0F, 16.0F, -8.0F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 16, 8, 16, 0.0F);
      this.shapes1 = new ModelRenderer(this, 0, 7);
      this.shapes1.setRotationPoint(-8.0F, 13.0F, -7.99F);
      this.shapes1.addBox(0.0F, 0.0F, 0.0F, 16, 1, 2, 0.0F);
      this.lamp2 = new ModelRenderer(this, 30, 0);
      this.lamp2.setRotationPoint(5.0F, 6.0F, 5.0F);
      this.lamp2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
      this.lamp2glow = new ModelRenderer(this, 40, 0);
      this.lamp2glow.setRotationPoint(5.0F, 6.0F, 5.0F);
      this.lamp2glow.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.3F);
      this.shape2 = new ModelRenderer(this, 23, 12);
      this.shape2.setRotationPoint(-8.0F, 8.0F, 4.0F);
      this.shape2.addBox(0.0F, 0.0F, 0.0F, 16, 8, 4, 0.0F);
      this.lamp1 = new ModelRenderer(this, 0, 0);
      this.lamp1.setRotationPoint(2.0F, 6.0F, 5.0F);
      this.lamp1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
      this.shapeGlow = new ModelRenderer(this, 48, 6);
      this.shapeGlow.setRotationPoint(0.0F, 11.0F, 0.0F);
      this.shapeGlow.addBox(-3.0F, 1.0F, -8.3F, 6, 3, 1, -0.01F);
      this.shapes2 = new ModelRenderer(this, 0, 32);
      this.shapes2.setRotationPoint(6.0F, 13.0F, -5.99F);
      this.shapes2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 3, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 15);
      this.shape3.setRotationPoint(-4.0F, 11.0F, -8.0F);
      this.shape3.addBox(0.0F, 0.0F, 0.0F, 8, 5, 3, 0.0F);
      this.shapes3 = new ModelRenderer(this, 8, 27);
      this.shapes3.setRotationPoint(6.0F, 14.0F, -3.99F);
      this.shapes3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
      this.shapes4 = new ModelRenderer(this, 0, 32);
      this.shapes4.setRotationPoint(-8.0F, 13.0F, -5.99F);
      this.shapes4.addBox(0.0F, 0.0F, 0.0F, 2, 1, 3, 0.0F);
      this.shapes5 = new ModelRenderer(this, 8, 27);
      this.shapes5.setRotationPoint(-8.0F, 14.0F, -3.99F);
      this.shapes5.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
      this.lamp1glow = new ModelRenderer(this, 10, 0);
      this.lamp1glow.setRotationPoint(2.0F, 6.0F, 5.0F);
      this.lamp1glow.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.3F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape1.render(f5);
      this.shapes1.render(f5);
      this.shape2.render(f5);
      this.shapeGlow.render(f5);
      this.shapes2.render(f5);
      this.shape3.render(f5);
      this.shapes3.render(f5);
      this.shapes4.render(f5);
      this.shapes5.render(f5);
      GlStateManager.enableBlend();
      float passivecolor = 0.35F;
      if (f3 == 1.0F) {
         GlStateManager.color(1.0F, passivecolor, passivecolor, 1.0F);
         AbstractMobModel.light(240, false);
      } else {
         GlStateManager.color(passivecolor, passivecolor, passivecolor, 1.0F);
      }

      this.lamp1.render(f5);
      AbstractMobModel.alphaGlow();
      this.lamp1glow.render(f5);
      AbstractMobModel.alphaGlowDisable();
      if (f3 == 1.0F) {
         AbstractMobModel.returnlight();
      }

      if (f4 == 1.0F) {
         GlStateManager.color(passivecolor, 1.0F, passivecolor, 1.0F);
         AbstractMobModel.light(240, false);
      } else {
         GlStateManager.color(passivecolor, passivecolor, passivecolor, 1.0F);
      }

      this.lamp2.render(f5);
      AbstractMobModel.alphaGlow();
      this.lamp2glow.render(f5);
      AbstractMobModel.alphaGlowDisable();
      if (f4 == 1.0F) {
         AbstractMobModel.returnlight();
      }

      GlStateManager.disableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
