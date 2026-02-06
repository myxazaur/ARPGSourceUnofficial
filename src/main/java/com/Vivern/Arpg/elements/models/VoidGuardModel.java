package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.mobs.OtherMobsPack;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class VoidGuardModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public VoidGuardPartModel subModel = new VoidGuardPartModel();

   public VoidGuardModel() {
      this.textureWidth = 36;
      this.textureHeight = 18;
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(0.0F, -2.0F, -8.0F);
      this.shape2.addBox(-0.5F, -10.0F, -0.5F, 1, 11, 1, 0.0F);
      this.setRotateAngle(this.shape2, -1.1383038F, 0.0F, 0.0F);
      this.shape6 = new ModelRenderer(this, 24, 0);
      this.shape6.setRotationPoint(0.0F, 0.0F, -7.0F);
      this.shape6.addBox(-1.5F, -1.5F, -3.0F, 3, 3, 3, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 22.0F, 4.0F);
      this.shape1.addBox(-3.0F, -3.0F, -8.0F, 6, 6, 12, 0.0F);
      this.shape3 = new ModelRenderer(this, 4, 0);
      this.shape3.setRotationPoint(2.0F, 0.0F, -8.0F);
      this.shape3.addBox(-0.5F, -10.0F, -0.5F, 1, 11, 1, 0.0F);
      this.setRotateAngle(this.shape3, -1.1383038F, 0.0F, (float) (Math.PI / 2));
      this.shape4 = new ModelRenderer(this, 8, 0);
      this.shape4.setRotationPoint(-2.0F, 0.0F, -8.0F);
      this.shape4.addBox(-0.5F, -10.0F, -0.5F, 1, 11, 1, 0.0F);
      this.setRotateAngle(this.shape4, -1.1383038F, 0.0F, (float) (-Math.PI / 2));
      this.shape5 = new ModelRenderer(this, 6, 0);
      this.shape5.setRotationPoint(0.0F, 2.0F, -8.0F);
      this.shape5.addBox(-0.5F, -10.0F, -0.5F, 1, 11, 1, 0.0F);
      this.setRotateAngle(this.shape5, -2.003289F, 0.0F, 0.0F);
      this.shape1.addChild(this.shape2);
      this.shape1.addChild(this.shape6);
      this.shape1.addChild(this.shape3);
      this.shape1.addChild(this.shape4);
      this.shape1.addChild(this.shape5);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 0.15F, 0.0F);
      if (entity instanceof OtherMobsPack.VoidGuard) {
         if (((OtherMobsPack.VoidGuard)entity).isSubMob) {
            this.subModel.render(entity, f, f1, f2, f3, f4, f5);
         } else {
            this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
            GlStateManager.pushMatrix();
            GL11.glEnable(3042);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            this.shape1.render(f5);
            GL11.glDisable(3042);
            GlStateManager.popMatrix();
         }
      }

      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      this.shape1.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.shape1.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
   }
}
