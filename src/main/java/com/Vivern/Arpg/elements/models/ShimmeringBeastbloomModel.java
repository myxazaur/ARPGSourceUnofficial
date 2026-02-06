package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ShimmeringBeastbloomModel extends ModelBase {
   public ModelRenderer pod1;
   public ModelRenderer pod2;
   public ModelRenderer pod3;
   public ModelRenderer core;
   public ModelRenderer leafa;
   public ModelRenderer leafc;
   public ModelRenderer leafb;
   public ModelRenderer leafd;
   public int opening = 0;
   public int growstage = 0;

   public ShimmeringBeastbloomModel() {
      this.textureWidth = 48;
      this.textureHeight = 32;
      this.core = new ModelRenderer(this, 2, 20);
      this.core.setRotationPoint(0.0F, -2.0F, 0.0F);
      this.core.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
      this.pod1 = new ModelRenderer(this, 0, 0);
      this.pod1.setRotationPoint(-2.0F, 18.0F, -2.0F);
      this.pod1.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
      this.pod3 = new ModelRenderer(this, 0, 8);
      this.pod3.setRotationPoint(-1.0F, 3.0F, -1.0F);
      this.pod3.addBox(0.0F, 0.0F, 0.0F, 2, 15, 2, 0.0F);
      this.leafb = new ModelRenderer(this, 4, 10);
      this.leafb.setRotationPoint(11.0F, 0.0F, 0.0F);
      this.leafb.addBox(0.0F, 0.0F, -2.0F, 10, 0, 4, 0.0F);
      this.leafc = new ModelRenderer(this, 24, 14);
      this.leafc.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.leafc.addBox(1.0F, 0.0F, -3.0F, 8, 0, 6, 0.0F);
      this.pod2 = new ModelRenderer(this, 8, 0);
      this.pod2.setRotationPoint(-4.0F, 22.0F, -4.0F);
      this.pod2.addBox(0.0F, 0.0F, 0.0F, 8, 2, 8, 0.0F);
      this.leafa = new ModelRenderer(this, 4, 14);
      this.leafa.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.leafa.addBox(1.0F, 0.0F, -3.0F, 10, 0, 6, 0.0F);
      this.leafd = new ModelRenderer(this, 16, 22);
      this.leafd.setRotationPoint(9.0F, 0.0F, 0.0F);
      this.leafd.addBox(0.0F, 0.0F, -2.0F, 8, 0, 4, 0.0F);
      this.leafa.addChild(this.leafb);
      this.leafc.addChild(this.leafd);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float openval = 0.014F * (MathHelper.clamp(Math.abs(this.opening), 20, 70) - 20);
      GlStateManager.pushMatrix();

      for (int i = 0; i < f; i++) {
         float ang = 360.0F / f;
         GlStateManager.rotate(ang, 0.0F, 1.0F, 0.0F);
         this.leafa.offsetY = f2 / 17.0F;
         this.leafa.rotateAngleZ = -0.92F + 0.047F * f2 + openval;
         this.leafb.rotateAngleZ = -1.72F + 0.094F * f2 + openval;
         this.leafa.render(f5);
      }

      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();

      for (int i = 0; i < f1; i++) {
         float ang = 360.0F / f1;
         GlStateManager.rotate(ang, 0.0F, 1.0F, 0.0F);
         this.leafc.offsetY = f3 / 17.0F;
         this.leafc.rotateAngleZ = -0.68F + 0.053F * f3 + openval;
         this.leafd.rotateAngleZ = -1.47F + 0.1F * f3 + openval;
         this.leafc.render(f5);
      }

      GlStateManager.popMatrix();
      this.pod1.render(f5);
      this.pod3.render(f5);
      this.pod2.render(f5);
      AbstractMobModel.alphaGlow();
      AbstractMobModel.light(180, true);
      float r1 = AnimationTimer.rainbow1;
      float r2 = AnimationTimer.rainbow2;
      float r3 = AnimationTimer.rainbow3;
      int tick = AnimationTimer.tick;
      GlStateManager.pushMatrix();
      GlStateManager.scale(f4, f4, f4);
      this.setRotateAngle(this.core, r1 * 0.27F, tick * 0.067F, r2 * 0.115F);
      this.core.render(f5);
      this.setRotateAngle(this.core, r3 * 0.23F, r1 * 0.15F, tick * 0.067F);
      this.core.render(f5);
      GlStateManager.popMatrix();
      AbstractMobModel.alphaGlowDisable();
      AbstractMobModel.returnlight();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
