package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class DungeonLadderModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer ladder0;
   public ModelRenderer cape;
   public ModelRenderer ladder1;
   public ModelRenderer ladder1_1;
   public ModelRenderer ladder1_2;
   public ModelRenderer ladder1_3;
   public ModelRenderer ladder1_4;
   public ModelRenderer ladder1_5;
   public ModelRenderer ladder1_6;
   public ModelRenderer ladder1_7;
   public ModelRenderer ladder1_8;
   public ModelRenderer ladder2;
   public ModelRenderer ladder2_1;
   public ModelRenderer ladder2_2;
   public ModelRenderer ladder2_3;
   public ModelRenderer ladder2_4;
   public ModelRenderer ladder2_5;
   public ModelRenderer ladder2_6;
   public ModelRenderer ladder2_7;
   public ModelRenderer ladder2_8;
   public ModelRenderer ladder2_9;

   public DungeonLadderModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.ladder1_7 = new ModelRenderer(this, 0, 0);
      this.ladder1_7.setRotationPoint(-2.0F, 99.0F, -8.0F);
      this.ladder1_7.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 13, 0.0F);
      this.setRotateAngle(this.ladder1_7, 0.13665928F, -0.3642502F, 0.0F);
      this.ladder1_5 = new ModelRenderer(this, 0, 0);
      this.ladder1_5.setRotationPoint(-11.0F, 55.0F, 5.0F);
      this.ladder1_5.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 14, 0.0F);
      this.setRotateAngle(this.ladder1_5, -0.7285004F, 1.8668041F, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 0);
      this.shape3.setRotationPoint(-12.0F, 8.0F, -12.0F);
      this.shape3.addBox(0.0F, 0.0F, 0.0F, 4, 128, 24, 0.0F);
      this.ladder2_8 = new ModelRenderer(this, 0, 0);
      this.ladder2_8.setRotationPoint(-6.0F, 99.0F, -8.0F);
      this.ladder2_8.addBox(-1.0F, -1.0F, 0.0F, 3, 3, 16, 0.0F);
      this.ladder1_8 = new ModelRenderer(this, 0, 0);
      this.ladder1_8.setRotationPoint(9.0F, 112.0F, 1.0F);
      this.ladder1_8.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 13, 0.0F);
      this.setRotateAngle(this.ladder1_8, 0.091106184F, -0.91053826F, 0.0F);
      this.ladder2_1 = new ModelRenderer(this, 0, 0);
      this.ladder2_1.setRotationPoint(-6.0F, 22.0F, -8.0F);
      this.ladder2_1.addBox(-1.0F, -1.0F, 0.0F, 3, 3, 16, 0.0F);
      this.ladder2 = new ModelRenderer(this, 0, 0);
      this.ladder2.setRotationPoint(-6.0F, 11.0F, -8.0F);
      this.ladder2.addBox(-1.0F, -1.0F, 0.0F, 3, 3, 16, 0.0F);
      this.ladder2_6 = new ModelRenderer(this, 0, 0);
      this.ladder2_6.setRotationPoint(-6.0F, 77.0F, -8.0F);
      this.ladder2_6.addBox(-1.0F, -1.0F, 0.0F, 3, 3, 16, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 0);
      this.shape4.setRotationPoint(8.0F, 8.0F, -12.0F);
      this.shape4.addBox(0.0F, 0.0F, 0.0F, 4, 128, 24, 0.0F);
      this.ladder2_2 = new ModelRenderer(this, 0, 0);
      this.ladder2_2.setRotationPoint(-6.0F, 33.0F, -8.0F);
      this.ladder2_2.addBox(-1.0F, -1.0F, 0.0F, 3, 3, 16, 0.0F);
      this.ladder0 = new ModelRenderer(this, 0, 0);
      this.ladder0.setRotationPoint(-7.0F, 8.0F, -8.0F);
      this.ladder0.addBox(0.0F, -4.0F, 0.0F, 0, 132, 16, 0.0F);
      this.ladder1 = new ModelRenderer(this, 0, 0);
      this.ladder1.setRotationPoint(-6.0F, 11.0F, -8.0F);
      this.ladder1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 16, 0.0F);
      this.setRotateAngle(this.ladder1, 0.13665928F, 0.18203785F, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(-8.0F, 8.0F, -12.0F);
      this.shape2.addBox(0.0F, 0.0F, 0.0F, 16, 128, 4, 0.0F);
      this.ladder2_7 = new ModelRenderer(this, 0, 0);
      this.ladder2_7.setRotationPoint(-6.0F, 88.0F, -8.0F);
      this.ladder2_7.addBox(-1.0F, -1.0F, 0.0F, 3, 3, 16, 0.0F);
      this.ladder2_9 = new ModelRenderer(this, 0, 0);
      this.ladder2_9.setRotationPoint(-6.0F, 110.0F, -8.0F);
      this.ladder2_9.addBox(-1.0F, -1.0F, 0.0F, 3, 3, 16, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(-8.0F, 8.0F, 8.0F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 16, 128, 4, 0.0F);
      this.ladder1_1 = new ModelRenderer(this, 0, 0);
      this.ladder1_1.setRotationPoint(-2.0F, 21.0F, -8.0F);
      this.ladder1_1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 16, 0.0F);
      this.setRotateAngle(this.ladder1_1, 0.31869712F, -0.3642502F, 0.0F);
      this.cape = new ModelRenderer(this, 0, 0);
      this.cape.setRotationPoint(-8.0F, 136.0F, -8.0F);
      this.cape.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16, 0.0F);
      this.ladder1_6 = new ModelRenderer(this, 0, 0);
      this.ladder1_6.setRotationPoint(11.0F, 81.0F, 0.0F);
      this.ladder1_6.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 13, 0.0F);
      this.setRotateAngle(this.ladder1_6, 0.4553564F, -1.8668041F, 0.0F);
      this.ladder1_2 = new ModelRenderer(this, 0, 0);
      this.ladder1_2.setRotationPoint(3.0F, 27.0F, -8.0F);
      this.ladder1_2.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 16, 0.0F);
      this.setRotateAngle(this.ladder1_2, 0.0F, 0.3642502F, 0.0F);
      this.ladder2_3 = new ModelRenderer(this, 0, 0);
      this.ladder2_3.setRotationPoint(-6.0F, 44.0F, -8.0F);
      this.ladder2_3.addBox(-1.0F, -1.0F, 0.0F, 3, 3, 16, 0.0F);
      this.ladder2_4 = new ModelRenderer(this, 0, 0);
      this.ladder2_4.setRotationPoint(-6.0F, 55.0F, -8.0F);
      this.ladder2_4.addBox(-1.0F, -1.0F, 0.0F, 3, 3, 16, 0.0F);
      this.ladder1_3 = new ModelRenderer(this, 0, 0);
      this.ladder1_3.setRotationPoint(-8.0F, 35.0F, -1.0F);
      this.ladder1_3.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 16, 0.0F);
      this.setRotateAngle(this.ladder1_3, 0.0F, 0.8196066F, 0.0F);
      this.ladder1_4 = new ModelRenderer(this, 0, 0);
      this.ladder1_4.setRotationPoint(-8.0F, 48.0F, -1.0F);
      this.ladder1_4.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 16, 0.0F);
      this.setRotateAngle(this.ladder1_4, -0.68294734F, 2.1855013F, 0.0F);
      this.ladder2_5 = new ModelRenderer(this, 0, 0);
      this.ladder2_5.setRotationPoint(-6.0F, 66.0F, -8.0F);
      this.ladder2_5.addBox(-1.0F, -1.0F, 0.0F, 3, 3, 16, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f == 0.0F) {
         this.shape3.render(f5);
         this.shape4.render(f5);
         this.shape2.render(f5);
         this.shape1.render(f5);
         this.cape.render(f5);
      } else {
         if (f1 == 0.0F) {
            this.ladder0.render(f5);
         }

         if (f1 == 1.0F) {
            this.ladder1.render(f5);
            this.ladder1_1.render(f5);
            this.ladder1_2.render(f5);
            this.ladder1_3.render(f5);
            this.ladder1_4.render(f5);
            this.ladder1_5.render(f5);
            this.ladder1_6.render(f5);
            this.ladder1_7.render(f5);
            this.ladder1_8.render(f5);
         }

         if (f1 == 2.0F) {
            this.ladder2.render(f5);
            this.ladder2_1.render(f5);
            this.ladder2_2.render(f5);
            this.ladder2_3.render(f5);
            this.ladder2_4.render(f5);
            this.ladder2_5.render(f5);
            this.ladder2_6.render(f5);
            this.ladder2_7.render(f5);
            this.ladder2_8.render(f5);
            this.ladder2_9.render(f5);
         }
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
