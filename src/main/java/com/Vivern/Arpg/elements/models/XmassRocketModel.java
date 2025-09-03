//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.renders.ModelRendererGlow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class XmassRocketModel extends ModelBase {
   public ModelRenderer shape4;
   public ModelRenderer shape4_1;
   public ModelRenderer shape4_2;
   public ModelRenderer shape4_3;
   public ModelRenderer shape4_4;
   public ModelRenderer shape4_5;
   public ModelRenderer ball;
   public ModelRenderer ball_1;
   public ModelRenderer ball_2;
   public ModelRenderer ball_3;
   public ModelRenderer ball_4;
   public ModelRenderer ball_5;
   public ModelRenderer ball_6;
   public ModelRenderer ball_7;

   public XmassRocketModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.shape4_2 = new ModelRenderer(this, 0, 16);
      this.shape4_2.setRotationPoint(0.0F, 0.0F, -4.0F);
      this.shape4_2.addBox(-2.5F, -2.5F, 0.0F, 5, 5, 2, 0.0F);
      this.setRotateAngle(this.shape4_2, 0.0F, 0.0F, 0.5009095F);
      this.ball_7 = new ModelRendererGlow(this, 14, 4, 200, false);
      this.ball_7.setRotationPoint(0.0F, 0.0F, -7.9F);
      this.ball_7.addBox(1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.ball_7, 0.0F, 0.0F, 2.4130921F);
      this.shape4_5 = new ModelRenderer(this, 0, 0);
      this.shape4_5.setRotationPoint(0.0F, 0.0F, -10.0F);
      this.shape4_5.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.shape4_5, 0.0F, 0.0F, 0.68294734F);
      this.shape4_3 = new ModelRenderer(this, 0, 10);
      this.shape4_3.setRotationPoint(0.0F, 0.0F, -6.0F);
      this.shape4_3.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 2, 0.0F);
      this.setRotateAngle(this.shape4_3, 0.0F, 0.0F, -0.4553564F);
      this.shape4_4 = new ModelRenderer(this, 0, 4);
      this.shape4_4.setRotationPoint(0.0F, 0.0F, -8.0F);
      this.shape4_4.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 2, 0.0F);
      this.setRotateAngle(this.shape4_4, 0.0F, 0.0F, -0.045553092F);
      this.ball_1 = new ModelRendererGlow(this, 14, 4, 200, false);
      this.ball_1.setRotationPoint(0.0F, 0.0F, -3.0F);
      this.ball_1.addBox(2.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.ball_1, 0.0F, 0.0F, (float) (-Math.PI / 2));
      this.ball_3 = new ModelRendererGlow(this, 14, 12, 200, false);
      this.ball_3.setRotationPoint(0.0F, 0.0F, -3.0F);
      this.ball_3.addBox(2.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.ball_3, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.shape4 = new ModelRenderer(this, 10, 26);
      this.shape4.setRotationPoint(0.0F, 0.0F, 2.0F);
      this.shape4.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 4, 0.0F);
      this.shape4_1 = new ModelRenderer(this, 0, 23);
      this.shape4_1.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.shape4_1.addBox(-2.5F, -2.5F, 0.0F, 5, 5, 2, 0.0F);
      this.ball_5 = new ModelRendererGlow(this, 14, 8, 200, false);
      this.ball_5.setRotationPoint(0.0F, 0.0F, -5.5F);
      this.ball_5.addBox(1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.ball_5, 0.0F, 0.0F, 0.7740535F);
      this.ball = new ModelRendererGlow(this, 14, 0, 200, false);
      this.ball.setRotationPoint(0.0F, 0.0F, -3.0F);
      this.ball.addBox(2.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
      this.ball_6 = new ModelRendererGlow(this, 14, 12, 200, false);
      this.ball_6.setRotationPoint(0.0F, 0.0F, -7.9F);
      this.ball_6.addBox(1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.ball_6, 0.0F, 0.0F, -0.7285004F);
      this.ball_2 = new ModelRendererGlow(this, 14, 8, 200, false);
      this.ball_2.setRotationPoint(0.0F, 0.0F, -3.0F);
      this.ball_2.addBox(2.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.ball_2, 0.0F, 0.0F, (float) -Math.PI);
      this.ball_4 = new ModelRendererGlow(this, 14, 0, 200, false);
      this.ball_4.setRotationPoint(0.0F, 0.0F, -5.7F);
      this.ball_4.addBox(1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.ball_4, 0.0F, 0.0F, -2.321986F);
      this.shape4.addChild(this.shape4_2);
      this.shape4.addChild(this.ball_7);
      this.shape4.addChild(this.shape4_5);
      this.shape4.addChild(this.shape4_3);
      this.shape4.addChild(this.shape4_4);
      this.shape4.addChild(this.ball_1);
      this.shape4.addChild(this.ball_3);
      this.shape4.addChild(this.shape4_1);
      this.shape4.addChild(this.ball_5);
      this.shape4.addChild(this.ball);
      this.shape4.addChild(this.ball_6);
      this.shape4.addChild(this.ball_2);
      this.shape4.addChild(this.ball_4);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (entity != null) {
         this.shape4.rotateAngleZ = entity.getEntityId() * 0.45723543F + AnimationTimer.tick * (entity.getEntityId() % 2 == 0 ? 0.03F : -0.03F);
      }

      this.shape4.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
