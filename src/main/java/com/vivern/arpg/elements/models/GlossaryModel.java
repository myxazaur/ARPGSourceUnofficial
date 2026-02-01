package com.vivern.arpg.elements.models;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.renders.ModelRendererLimited;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class GlossaryModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer book1;
   public ModelRenderer runes;
   public ModelRenderer shape2;
   public ModelRenderer book2;
   public ModelRenderer book3;
   public ModelRenderer paper1;
   public ModelRenderer paper2;
   public ModelRenderer paper3;
   public ModelRenderer paper4;
   public ModelRenderer book4;
   public ModelRenderer book5;

   public GlossaryModel() {
      this.textureWidth = 64;
      this.textureHeight = 48;
      this.paper2 = new ModelRendererLimited(this, -4, 0, false, false, true, true, false, false);
      this.paper2.setRotationPoint(-0.2F, -2.0F, 0.0F);
      this.paper2.addBox(-6.0F, 0.0F, 0.0F, 6, 0, 10, 0.0F);
      this.setRotateAngle(this.paper2, 0.0F, 0.0F, 0.3642502F);
      this.book5 = new ModelRenderer(this, 51, 12);
      this.book5.setRotationPoint(-3.0F, -1.0F, 0.0F);
      this.book5.addBox(0.0F, 0.0F, -3.0F, 1, 0, 3, 0.0F);
      this.setRotateAngle(this.book5, 0.5009095F, -0.091106184F, 0.0F);
      this.book1 = new ModelRenderer(this, 0, 10);
      this.book1.setRotationPoint(0.0F, 12.5F, -2.5F);
      this.book1.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 10, 0.0F);
      this.setRotateAngle(this.book1, (float) (Math.PI / 3), 0.0F, 0.0F);
      this.shape2 = new ModelRenderer(this, 28, 8);
      this.shape2.setRotationPoint(-4.0F, 20.0F, -4.0F);
      this.shape2.addBox(0.0F, 0.0F, 0.0F, 8, 3, 9, 0.0F);
      this.paper3 = new ModelRendererLimited(this, 2, 0, false, false, true, true, false, false);
      this.paper3.setRotationPoint(0.2F, -2.0F, 0.0F);
      this.paper3.addBox(-6.0F, 0.0F, 0.0F, 6, 0, 10, 0.0F);
      this.setRotateAngle(this.paper3, 0.0F, 0.0F, 2.7773426F);
      this.shape1 = new ModelRenderer(this, 0, 35);
      this.shape1.setRotationPoint(-6.0F, 23.0F, -6.0F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 12, 1, 12, 0.0F);
      this.runes = new ModelRenderer(this, 30, -2);
      this.runes.setRotationPoint(-4.0F, 21.0F, -4.0F);
      this.runes.addBox(0.0F, 0.0F, 0.0F, 8, 1, 9, 0.02F);
      this.book4 = new ModelRenderer(this, 58, 13);
      this.book4.setRotationPoint(1.0F, -1.0F, 0.0F);
      this.book4.addBox(0.0F, 0.0F, -1.0F, 2, 0, 1, 0.0F);
      this.setRotateAngle(this.book4, 0.5009095F, 0.0F, 0.091106184F);
      this.book3 = new ModelRenderer(this, 32, 20);
      this.book3.mirror = true;
      this.book3.setRotationPoint(-1.0F, 0.0F, 0.0F);
      this.book3.addBox(-6.0F, -2.0F, 0.0F, 6, 2, 10, 0.0F);
      this.setRotateAngle(this.book3, 0.0F, 0.0F, 0.18203785F);
      this.book2 = new ModelRenderer(this, 32, 20);
      this.book2.setRotationPoint(1.0F, 0.0F, 0.0F);
      this.book2.addBox(0.0F, -2.0F, 0.0F, 6, 2, 10, 0.0F);
      this.setRotateAngle(this.book2, 0.0F, 0.0F, -0.18203785F);
      this.paper1 = new ModelRendererLimited(this, -10, 0, false, false, true, true, false, false);
      this.paper1.setRotationPoint(-0.5F, -2.0F, 0.0F);
      this.paper1.addBox(-6.0F, 0.0F, 0.0F, 6, 0, 10, 0.0F);
      this.setRotateAngle(this.paper1, 0.0F, 0.0F, 0.27314404F);
      this.paper4 = new ModelRendererLimited(this, 8, 0, false, false, true, true, false, false);
      this.paper4.setRotationPoint(0.5F, -2.0F, 0.0F);
      this.paper4.addBox(-6.0F, 0.0F, 0.0F, 6, 0, 10, 0.0F);
      this.setRotateAngle(this.paper4, 0.0F, 0.0F, 2.8684487F);
      this.shape4 = new ModelRenderer(this, 0, 22);
      this.shape4.setRotationPoint(0.0F, 11.0F, -1.7F);
      this.shape4.addBox(-5.0F, 0.0F, 0.0F, 10, 4, 6, 0.0F);
      this.setRotateAngle(this.shape4, (float) (Math.PI / 3), 0.0F, 0.0F);
      this.shape3 = new ModelRenderer(this, 36, 33);
      this.shape3.setRotationPoint(-3.0F, 11.0F, -1.0F);
      this.shape3.addBox(0.0F, 0.0F, 0.0F, 6, 9, 4, 0.0F);
      this.book1.addChild(this.paper2);
      this.book3.addChild(this.book5);
      this.book1.addChild(this.paper3);
      this.book2.addChild(this.book4);
      this.book1.addChild(this.book3);
      this.book1.addChild(this.book2);
      this.book1.addChild(this.paper1);
      this.book1.addChild(this.paper4);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f2 == 0.0F) {
         this.shape2.render(f5);
         this.shape1.render(f5);
         this.shape4.render(f5);
         this.shape3.render(f5);
         if (f1 > 0.0F) {
            float ft5 = GetMOP.getfromto(f1, 0.0F, 0.5F) - GetMOP.getfromto(f1, 0.5F, 1.0F);
            AbstractMobModel.alphaGlow();
            GlStateManager.color(ft5, ft5, ft5);
            this.runes.render(f5);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            AbstractMobModel.alphaGlowDisable();
         }
      } else {
         float ft1 = GetMOP.getfromto(f, 0.0F, 0.25F);
         float ft2 = GetMOP.getfromto(f, 0.25F, 0.5F);
         float ft3 = GetMOP.getfromto(f, 0.5F, 0.75F);
         float ft4 = GetMOP.getfromto(f, 0.75F, 1.0F);
         float angleStart = 0.18203785F - f3;
         float angleStop = 2.959555F + f3;
         float angleAdd = angleStop - angleStart;
         this.paper4.rotateAngleZ = angleStart + angleAdd * ft1;
         this.paper3.rotateAngleZ = angleStart + angleAdd * ft2;
         this.paper2.rotateAngleZ = angleStart + angleAdd * ft3;
         this.paper1.rotateAngleZ = angleStart + angleAdd * ft4;
         this.book2.rotateAngleZ = -angleStart;
         this.book3.rotateAngleZ = angleStart;
         this.book1.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
