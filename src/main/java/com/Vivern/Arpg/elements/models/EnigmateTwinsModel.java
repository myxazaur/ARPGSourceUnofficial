package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.renders.ModelRendererGlow;
import com.Vivern.Arpg.renders.ModelRendererLimited;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class EnigmateTwinsModel extends ModelBase {
   public ModelRenderer bodywear;
   public ModelRenderer stick1;
   public ModelRenderer stick2;
   public ModelRenderer shapeMain;
   public ModelRenderer hole1;
   public ModelRenderer sback;
   public ModelRenderer glow1;
   public ModelRenderer glow2;
   public ModelRenderer holePart1;
   public ModelRenderer holePart2;
   public ModelRenderer holePart3;

   public EnigmateTwinsModel() {
      this.textureWidth = 26;
      this.textureHeight = 16;
      this.hole1 = new ModelRenderer(this, 8, 0);
      this.hole1.setRotationPoint(0.0F, -1.0F, 1.5F);
      this.hole1.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.hole1, 0.68294734F, 0.5462881F, 0.0F);
      this.holePart2 = new ModelRenderer(this, 8, 2);
      this.holePart2.setRotationPoint(0.0F, -1.0F, 1.5F);
      this.holePart2.addBox(0.0F, -5.0F, -0.75F, 0, 2, 1, 0.0F);
      this.setRotateAngle(this.holePart2, 0.0F, 0.0F, (float) (Math.PI * 2.0 / 3.0));
      this.glow2 = new ModelRendererGlow(this, 0, 9, 220, false);
      this.glow2.setRotationPoint(0.0F, -1.0F, 0.55F);
      this.glow2.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 0, 0.0F);
      this.glow1 = new ModelRendererGlow(this, 0, 7, 220, false);
      this.glow1.setRotationPoint(0.0F, -1.0F, -3.55F);
      this.glow1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 0, 0.0F);
      this.holePart1 = new ModelRenderer(this, 8, 2);
      this.holePart1.setRotationPoint(0.0F, -1.0F, 1.5F);
      this.holePart1.addBox(0.0F, -5.0F, -0.75F, 0, 2, 1, 0.0F);
      this.sback = new ModelRenderer(this, 14, 0);
      this.sback.setRotationPoint(0.0F, -1.0F, 0.5F);
      this.sback.addBox(-1.0F, -1.0F, 2.0F, 2, 2, 2, 0.0F);
      this.shapeMain = new ModelRenderer(this, 12, 4);
      this.shapeMain.setRotationPoint(0.0F, -5.0F, 0.0F);
      this.shapeMain.addBox(-1.5F, -2.5F, -3.5F, 3, 3, 4, 0.0F);
      this.setRotateAngle(this.shapeMain, 0.0F, -0.3642502F, 0.0F);
      this.bodywear = new ModelRendererLimited(this, 0, 6, true, true, true, false, true, true);
      this.bodywear.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bodywear.addBox(-3.5F, -0.2F, -2.5F, 7, 5, 5, 0.01F);
      this.stick2 = new ModelRenderer(this, 4, 0);
      this.stick2.setRotationPoint(0.0F, -4.8F, 0.0F);
      this.stick2.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.stick2, 0.7285004F, -0.18203785F, 0.0F);
      this.stick1 = new ModelRenderer(this, 0, 0);
      this.stick1.setRotationPoint(2.8F, 2.0F, 2.0F);
      this.stick1.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.stick1, -0.91053826F, 0.5009095F, 0.18203785F);
      this.holePart3 = new ModelRenderer(this, 8, 2);
      this.holePart3.setRotationPoint(0.0F, -1.0F, 1.5F);
      this.holePart3.addBox(0.0F, -5.0F, -0.75F, 0, 2, 1, 0.0F);
      this.setRotateAngle(this.holePart3, 0.0F, 0.0F, (float) (-Math.PI * 2.0 / 3.0));
      this.shapeMain.addChild(this.hole1);
      this.shapeMain.addChild(this.holePart2);
      this.shapeMain.addChild(this.glow2);
      this.shapeMain.addChild(this.glow1);
      this.shapeMain.addChild(this.holePart1);
      this.shapeMain.addChild(this.sback);
      this.stick2.addChild(this.shapeMain);
      this.stick1.addChild(this.stick2);
      this.shapeMain.addChild(this.holePart3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float r1 = AnimationTimer.rainbow1;
      float r2 = AnimationTimer.rainbow2;
      int tick = AnimationTimer.tick;
      this.setRotateAngle(this.hole1, r1 * 0.27F, tick * 0.087F, r2 * 0.115F);
      int num = tick / 60;
      float progress1 = tick % 60 / 60.0F;
      this.holePart1.rotateAngleZ = 7.7813473F * num;
      this.holePart1.rotateAngleX = 0.52F * MathHelper.sin(11.245357F * num);
      this.holePart1.rotateAngleY = 1.57F * MathHelper.sin(13.135434F * num);
      this.holePart1.offsetY = 4.0F * Debugger.floats[0] * progress1;
      this.bodywear.render(f5);
      float anglePitch = f * 0.0122171F / 3.0F;
      float angleYaw = f1 * 0.0122171F / 2.0F;
      this.stick1.setRotationPoint(2.8F, 2.0F, 2.0F);
      this.setRotateAngle(this.stick1, -0.91053826F + anglePitch, 0.5009095F + angleYaw, 0.18203785F);
      this.setRotateAngle(this.stick2, 0.7285004F + anglePitch, -0.18203785F, 0.0F);
      this.setRotateAngle(this.shapeMain, 0.0F + anglePitch, -0.3642502F + angleYaw, 0.0F);
      this.stick1.render(f5);
      this.stick1.setRotationPoint(-2.8F, 2.0F, 2.0F);
      this.setRotateAngle(this.stick1, -0.91053826F + anglePitch, -0.5009095F + angleYaw, -0.18203785F);
      this.setRotateAngle(this.stick2, 0.7285004F + anglePitch, 0.18203785F, 0.0F);
      this.setRotateAngle(this.shapeMain, 0.0F + anglePitch, 0.3642502F + angleYaw, 0.0F);
      this.stick1.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
