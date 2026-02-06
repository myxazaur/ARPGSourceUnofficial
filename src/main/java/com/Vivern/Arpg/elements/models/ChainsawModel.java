package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ChainsawModel extends ModelBase {
   public ModelRenderer shapess2;
   public ModelRenderer shapess3;
   public ModelRenderer shapess1;
   public ModelRenderer bottom1;
   public ModelRenderer corpus;
   public ModelRenderer cap;
   public ModelRenderer mechanism;
   public ModelRenderer bottom2;
   public ModelRenderer grip2;
   public ModelRenderer chainsawblade;
   public ModelRenderer fangs1;
   public ModelRenderer fangs2;
   public ModelRenderer fangs3;
   public ModelRenderer engineFuel;
   public ModelRenderer engineElectric;
   public ModelRenderer buttonsGlow;
   public ModelRenderer grip3;
   public ModelRenderer grip1;
   public ModelRenderer engineftube2a;
   public ModelRenderer engineftube3a;
   public ModelRenderer gear;
   public ModelRenderer belt;
   public ModelRenderer enginee;
   public ModelRenderer enginee_1;
   public ModelRenderer enginee_2;
   public ModelRenderer enginee_3;
   public ModelRenderer enginee_4;
   public ModelRenderer enginee_5;
   public ModelRenderer enginee_6;

   public ChainsawModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.shapess2 = new ModelRenderer(this, 38, 38);
      this.shapess2.setRotationPoint(0.5F, 1.0F, 4.0F);
      this.shapess2.addBox(-1.0F, 0.3F, -1.0F, 1, 5, 2, 0.0F);
      this.setRotateAngle(this.shapess2, (float) (Math.PI / 4), 0.0F, 0.0F);
      this.engineftube2a = new ModelRenderer(this, 12, 25);
      this.engineftube2a.setRotationPoint(-1.5F, 1.0F, -2.5F);
      this.engineftube2a.addBox(0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.engineftube2a, 0.0F, 0.0F, (float) (-Math.PI / 4));
      this.shapess1 = new ModelRenderer(this, 48, 24);
      this.shapess1.setRotationPoint(0.0F, 0.5F, 1.0F);
      this.shapess1.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 4, 0.0F);
      this.enginee_4 = new ModelRenderer(this, 0, 18);
      this.enginee_4.setRotationPoint(0.0F, -1.0F, -5.0F);
      this.enginee_4.addBox(2.0F, -1.0F, 0.0F, 1, 2, 4, 0.0F);
      this.setRotateAngle(this.enginee_4, 0.0F, 0.0F, (float) (-Math.PI * 3.0 / 4.0));
      this.enginee_2 = new ModelRenderer(this, 0, 18);
      this.enginee_2.setRotationPoint(0.0F, -1.0F, -5.0F);
      this.enginee_2.addBox(2.0F, -1.0F, 0.0F, 1, 2, 4, 0.0F);
      this.setRotateAngle(this.enginee_2, 0.0F, 0.0F, (float) (-Math.PI / 4));
      this.fangs1 = new ModelRenderer(this, 0, 33);
      this.fangs1.setRotationPoint(0.0F, 5.0F, -16.5F);
      this.fangs1.addBox(0.0F, -1.0F, -1.0F, 0, 5, 16, 0.0F);
      this.enginee_1 = new ModelRenderer(this, 0, 18);
      this.enginee_1.setRotationPoint(0.0F, -1.0F, -5.0F);
      this.enginee_1.addBox(2.0F, -1.0F, 0.0F, 1, 2, 4, 0.0F);
      this.setRotateAngle(this.enginee_1, 0.0F, 0.0F, (float) (Math.PI / 4));
      this.enginee_5 = new ModelRenderer(this, 0, 18);
      this.enginee_5.setRotationPoint(0.0F, -1.0F, -5.0F);
      this.enginee_5.addBox(2.0F, -1.0F, 0.0F, 1, 2, 4, 0.0F);
      this.setRotateAngle(this.enginee_5, 0.0F, 0.0F, (float) (-Math.PI / 2));
      this.corpus = new ModelRenderer(this, 16, 22);
      this.corpus.setRotationPoint(0.0F, 1.0F, -2.0F);
      this.corpus.addBox(-2.5F, 0.0F, 0.0F, 5, 5, 5, 0.0F);
      this.engineftube3a = new ModelRenderer(this, 12, 25);
      this.engineftube3a.setRotationPoint(-1.5F, 1.0F, -4.02F);
      this.engineftube3a.addBox(0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.engineftube3a, 0.0F, 0.0F, (float) (-Math.PI / 4));
      this.mechanism = new ModelRenderer(this, 0, 25);
      this.mechanism.setRotationPoint(0.0F, 1.0F, -6.0F);
      this.mechanism.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 4, 0.0F);
      this.bottom2 = new ModelRenderer(this, 7, 5);
      this.bottom2.setRotationPoint(0.0F, 6.0F, -5.5F);
      this.bottom2.addBox(-1.5F, 0.5F, 0.0F, 3, 1, 7, 0.0F);
      this.setRotateAngle(this.bottom2, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.grip2 = new ModelRenderer(this, 51, 21);
      this.grip2.setRotationPoint(2.5F, 4.5F, 0.1F);
      this.grip2.addBox(1.0F, 0.0F, 0.0F, 4, 1, 2, 0.0F);
      this.setRotateAngle(this.grip2, (float) (Math.PI / 4), 0.0F, 0.0F);
      this.chainsawblade = new ModelRenderer(this, 0, 32);
      this.chainsawblade.setRotationPoint(0.0F, 5.0F, -16.5F);
      this.chainsawblade.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 14, 0.0F);
      this.cap = new ModelRenderer(this, 5, 19);
      this.cap.setRotationPoint(0.0F, 0.0F, -5.0F);
      this.cap.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 5, 0.0F);
      this.grip1 = new ModelRenderer(this, 43, 12);
      this.grip1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.grip1.addBox(0.0F, -0.5F, -0.5F, 1, 2, 3, 0.0F);
      this.enginee_6 = new ModelRenderer(this, 17, 15);
      this.enginee_6.setRotationPoint(0.0F, -1.0F, -4.5F);
      this.enginee_6.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 3, 0.0F);
      this.setRotateAngle(this.enginee_6, 0.0F, 0.0F, (float) Math.PI);
      this.bottom1 = new ModelRenderer(this, 36, 22);
      this.bottom1.setRotationPoint(0.0F, 6.0F, -1.5F);
      this.bottom1.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 8, 0.0F);
      this.fangs3 = new ModelRenderer(this, 0, 43);
      this.fangs3.setRotationPoint(0.0F, 5.0F, -16.5F);
      this.fangs3.addBox(0.0F, -1.0F, -1.0F, 0, 5, 16, 0.0F);
      this.enginee_3 = new ModelRenderer(this, 0, 18);
      this.enginee_3.setRotationPoint(0.0F, -1.0F, -5.0F);
      this.enginee_3.addBox(2.0F, -1.0F, 0.0F, 1, 2, 4, 0.0F);
      this.setRotateAngle(this.enginee_3, 0.0F, 0.0F, (float) (Math.PI * 3.0 / 4.0));
      this.buttonsGlow = new ModelRenderer(this, 44, 54);
      this.buttonsGlow.setRotationPoint(0.0F, 1.0F, -2.0F);
      this.buttonsGlow.addBox(2.51F, 0.0F, 0.0F, 0, 5, 5, 0.0F);
      this.enginee = new ModelRenderer(this, 0, 18);
      this.enginee.setRotationPoint(0.0F, -1.0F, -5.0F);
      this.enginee.addBox(2.0F, -1.0F, 0.0F, 1, 2, 4, 0.0F);
      this.setRotateAngle(this.enginee, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.engineElectric = new ModelRenderer(this, 20, 0);
      this.engineElectric.setRotationPoint(-0.98F, 7.5F, -3.5F);
      this.engineElectric.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 0, 0.0F);
      this.setRotateAngle(this.engineElectric, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.engineFuel = new ModelRenderer(this, 20, 0);
      this.engineFuel.setRotationPoint(0.0F, 6.5F, -0.5F);
      this.engineFuel.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 0, 0.0F);
      this.grip3 = new ModelRenderer(this, 56, 24);
      this.grip3.setRotationPoint(5.0F, 0.0F, 0.0F);
      this.grip3.addBox(0.0F, -0.5F, 0.0F, 1, 2, 2, 0.0F);
      this.shapess3 = new ModelRenderer(this, 36, 24);
      this.shapess3.setRotationPoint(0.0F, 4.0F, 6.5F);
      this.shapess3.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
      this.fangs2 = new ModelRenderer(this, 0, 38);
      this.fangs2.setRotationPoint(0.0F, 5.0F, -16.5F);
      this.fangs2.addBox(0.0F, -1.0F, -1.0F, 0, 5, 16, 0.0F);
      this.belt = new ModelRenderer(this, 34, 18);
      this.belt.setRotationPoint(0.0F, 0.0F, -3.5F);
      this.belt.addBox(0.0F, -1.0F, -1.0F, 1, 2, 4, 0.0F);
      this.setRotateAngle(this.belt, (float) (Math.PI / 4), 0.0F, 0.0F);
      this.gear = new ModelRenderer(this, 41, 19);
      this.gear.setRotationPoint(0.5F, 0.0F, -3.5F);
      this.gear.addBox(0.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.gear, (float) (Math.PI / 4), 0.0F, 0.0F);
      this.engineFuel.addChild(this.engineftube2a);
      this.engineElectric.addChild(this.enginee_4);
      this.engineElectric.addChild(this.enginee_2);
      this.engineElectric.addChild(this.enginee_1);
      this.engineElectric.addChild(this.enginee_5);
      this.engineFuel.addChild(this.engineftube3a);
      this.grip2.addChild(this.grip1);
      this.engineElectric.addChild(this.enginee_6);
      this.engineElectric.addChild(this.enginee_3);
      this.engineElectric.addChild(this.enginee);
      this.grip2.addChild(this.grip3);
      this.engineFuel.addChild(this.belt);
      this.engineFuel.addChild(this.gear);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      int i = (int)(f * 0.25F) % 3;
      this.fangs1.isHidden = i != 0;
      this.fangs2.isHidden = i != 1;
      this.fangs3.isHidden = i != 2;
      this.shapess2.render(f5);
      this.shapess1.render(f5);
      this.fangs1.render(f5);
      this.corpus.render(f5);
      this.mechanism.render(f5);
      this.bottom2.render(f5);
      this.grip2.render(f5);
      this.chainsawblade.render(f5);
      this.cap.render(f5);
      this.bottom1.render(f5);
      this.fangs3.render(f5);
      if (f2 == 1.0F) {
         this.engineElectric.render(f5);
      }

      if (f2 == 2.0F) {
         this.engineFuel.render(f5);
      }

      this.shapess3.render(f5);
      this.fangs2.render(f5);
      if (f1 == 0.0F && f2 == 1.0F) {
         AbstractMobModel.light(240, false);
         this.buttonsGlow.render(f5);
         AbstractMobModel.returnlight();
      }

      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
