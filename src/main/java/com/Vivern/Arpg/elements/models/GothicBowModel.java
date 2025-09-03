//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.renders.ModelRendererLimited;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class GothicBowModel extends ModelBase {
   public ModelRenderer shapeMAIN;
   public ModelRenderer shapeUP1;
   public ModelRenderer shapeDOWN1;
   public ModelRenderer shapeUP2a;
   public ModelRenderer shapeUP2b;
   public ModelRenderer shapeUP3;
   public ModelRenderer ropeUP;
   public ModelRenderer shapeDOWN2a;
   public ModelRenderer shapeDOWN2b;
   public ModelRenderer shapeDOWN3;
   public ModelRenderer ropeDOWN;

   public GothicBowModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.ropeUP = new ModelRenderer(this, 6, -1);
      this.ropeUP.setRotationPoint(0.0F, -2.5F, 3.2F);
      this.ropeUP.addBox(0.0F, 0.0F, -0.5F, 0, 16, 1, 0.0F);
      this.setRotateAngle(this.ropeUP, 0.045553092F, 0.0F, 0.0F);
      this.shapeUP2b = new ModelRendererLimited(this, -4, 1, true, false, false, false, false, false);
      this.shapeUP2b.setRotationPoint(0.0F, -5.0F, 0.0F);
      this.shapeUP2b.addBox(0.2F, -5.0F, -0.5F, 0, 5, 4, 0.0F);
      this.setRotateAngle(this.shapeUP2b, -0.4553564F, 0.13665928F, -0.091106184F);
      this.shapeDOWN2b = new ModelRendererLimited(this, -4, 1, true, false, false, false, false, false);
      this.shapeDOWN2b.setRotationPoint(0.0F, -5.0F, 0.0F);
      this.shapeDOWN2b.addBox(0.2F, -5.0F, -0.5F, 0, 5, 4, 0.0F);
      this.setRotateAngle(this.shapeDOWN2b, -0.4553564F, 0.13665928F, -0.091106184F);
      this.shapeDOWN2a = new ModelRendererLimited(this, -4, 1, true, false, false, false, false, false);
      this.shapeDOWN2a.setRotationPoint(0.0F, -5.0F, 0.0F);
      this.shapeDOWN2a.addBox(-0.2F, -5.0F, -0.5F, 0, 5, 4, 0.0F);
      this.setRotateAngle(this.shapeDOWN2a, -0.4553564F, -0.13665928F, 0.091106184F);
      this.shapeUP1 = new ModelRendererLimited(this, -3, 7, true, false, false, false, false, false);
      this.shapeUP1.setRotationPoint(0.0F, 0.5F, 4.5F);
      this.shapeUP1.addBox(0.0F, -6.0F, -2.5F, 0, 6, 3, 0.05F);
      this.setRotateAngle(this.shapeUP1, 0.13665928F, 0.0F, 0.0F);
      this.shapeMAIN = new ModelRenderer(this, 8, 0);
      this.shapeMAIN.setRotationPoint(0.0F, 3.5F, 5.0F);
      this.shapeMAIN.addBox(-1.0F, -3.0F, -1.5F, 2, 6, 2, 0.0F);
      this.ropeDOWN = new ModelRenderer(this, 6, -1);
      this.ropeDOWN.setRotationPoint(0.0F, -2.5F, 3.2F);
      this.ropeDOWN.addBox(0.0F, 0.0F, -0.5F, 0, 16, 1, 0.0F);
      this.setRotateAngle(this.ropeDOWN, 0.045553092F, 0.0F, 0.0F);
      this.shapeDOWN1 = new ModelRendererLimited(this, -3, 7, true, false, false, false, false, false);
      this.shapeDOWN1.setRotationPoint(0.0F, 6.5F, 4.5F);
      this.shapeDOWN1.addBox(0.0F, -6.0F, -2.5F, 0, 6, 3, 0.05F);
      this.setRotateAngle(this.shapeDOWN1, 0.13665928F, 0.0F, (float) Math.PI);
      this.shapeUP3 = new ModelRendererLimited(this, -5, -5, true, false, false, false, false, false);
      this.shapeUP3.setRotationPoint(0.0F, -4.6F, 0.0F);
      this.shapeUP3.addBox(-0.1F, -4.0F, -0.5F, 0, 5, 5, 0.0F);
      this.setRotateAngle(this.shapeUP3, 0.27314404F, 0.13665928F, -0.034906585F);
      this.shapeUP2a = new ModelRendererLimited(this, -4, 1, true, false, false, false, false, false);
      this.shapeUP2a.setRotationPoint(0.0F, -5.0F, 0.0F);
      this.shapeUP2a.addBox(-0.2F, -5.0F, -0.5F, 0, 5, 4, 0.0F);
      this.setRotateAngle(this.shapeUP2a, -0.4553564F, -0.13665928F, 0.091106184F);
      this.shapeDOWN3 = new ModelRendererLimited(this, -5, -5, true, false, false, false, false, false);
      this.shapeDOWN3.setRotationPoint(0.0F, -4.6F, 0.0F);
      this.shapeDOWN3.addBox(-0.1F, -4.0F, -0.5F, 0, 5, 5, 0.0F);
      this.setRotateAngle(this.shapeDOWN3, 0.27314404F, 0.13665928F, -0.034906585F);
      this.shapeUP3.addChild(this.ropeUP);
      this.shapeUP1.addChild(this.shapeUP2b);
      this.shapeDOWN1.addChild(this.shapeDOWN2b);
      this.shapeDOWN1.addChild(this.shapeDOWN2a);
      this.shapeDOWN3.addChild(this.ropeDOWN);
      this.shapeUP2a.addChild(this.shapeUP3);
      this.shapeUP1.addChild(this.shapeUP2a);
      this.shapeDOWN2a.addChild(this.shapeDOWN3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shapeUP1.rotateAngleX = GetMOP.partial(0.227591F, 0.13665928F, f);
      this.shapeUP2a.rotateAngleX = GetMOP.partial(-0.86516F, -0.4553564F, f);
      this.shapeUP2a.rotateAngleY = GetMOP.partial(-0.045553F, -0.13665928F, f);
      this.shapeUP2a.rotateAngleZ = GetMOP.partial(0.136659F, 0.091106184F, f);
      this.shapeUP2b.rotateAngleX = this.shapeUP2a.rotateAngleX;
      this.shapeUP2b.rotateAngleY = GetMOP.partial(0.091106F, 0.13665928F, f);
      this.shapeUP2b.rotateAngleZ = -this.shapeUP2a.rotateAngleZ;
      this.shapeUP3.rotateAngleX = GetMOP.partial(0.182038F, 0.27314404F, f);
      this.shapeUP3.rotateAngleY = GetMOP.partial(0.119206F, 0.13665928F, f);
      this.ropeUP.rotateAngleX = GetMOP.partial(1.029744F, 0.045553092F, f);
      this.ropeDOWN.rotateAngleX = this.ropeUP.rotateAngleX;
      this.shapeDOWN1.rotateAngleX = this.shapeUP1.rotateAngleX;
      this.shapeDOWN2a.rotateAngleX = this.shapeUP2a.rotateAngleX;
      this.shapeDOWN2a.rotateAngleY = this.shapeUP2a.rotateAngleY;
      this.shapeDOWN2a.rotateAngleZ = this.shapeUP2a.rotateAngleZ;
      this.shapeDOWN2b.rotateAngleX = this.shapeUP2b.rotateAngleX;
      this.shapeDOWN2b.rotateAngleY = this.shapeUP2b.rotateAngleY;
      this.shapeDOWN2b.rotateAngleZ = this.shapeUP2b.rotateAngleZ;
      this.shapeDOWN3.rotateAngleX = this.shapeUP3.rotateAngleX;
      this.shapeDOWN3.rotateAngleY = this.shapeUP3.rotateAngleY;
      boolean ench = f1 > 0.0F;
      this.shapeUP1.isHidden = ench;
      this.shapeUP2a.isHidden = ench;
      this.shapeUP2b.isHidden = ench;
      this.shapeUP3.isHidden = ench;
      this.shapeDOWN1.isHidden = ench;
      this.shapeDOWN2a.isHidden = ench;
      this.shapeDOWN2b.isHidden = ench;
      this.shapeDOWN3.isHidden = ench;
      GlStateManager.disableCull();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapeUP1.offsetX, this.shapeUP1.offsetY, this.shapeUP1.offsetZ);
      GlStateManager.translate(this.shapeUP1.rotationPointX * f5, this.shapeUP1.rotationPointY * f5, this.shapeUP1.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.shapeUP1.offsetX, -this.shapeUP1.offsetY, -this.shapeUP1.offsetZ);
      GlStateManager.translate(-this.shapeUP1.rotationPointX * f5, -this.shapeUP1.rotationPointY * f5, -this.shapeUP1.rotationPointZ * f5);
      this.shapeUP1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapeMAIN.offsetX, this.shapeMAIN.offsetY, this.shapeMAIN.offsetZ);
      GlStateManager.translate(this.shapeMAIN.rotationPointX * f5, this.shapeMAIN.rotationPointY * f5, this.shapeMAIN.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 1.0);
      GlStateManager.translate(-this.shapeMAIN.offsetX, -this.shapeMAIN.offsetY, -this.shapeMAIN.offsetZ);
      GlStateManager.translate(-this.shapeMAIN.rotationPointX * f5, -this.shapeMAIN.rotationPointY * f5, -this.shapeMAIN.rotationPointZ * f5);
      this.shapeMAIN.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapeDOWN1.offsetX, this.shapeDOWN1.offsetY, this.shapeDOWN1.offsetZ);
      GlStateManager.translate(this.shapeDOWN1.rotationPointX * f5, this.shapeDOWN1.rotationPointY * f5, this.shapeDOWN1.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.shapeDOWN1.offsetX, -this.shapeDOWN1.offsetY, -this.shapeDOWN1.offsetZ);
      GlStateManager.translate(-this.shapeDOWN1.rotationPointX * f5, -this.shapeDOWN1.rotationPointY * f5, -this.shapeDOWN1.rotationPointZ * f5);
      this.shapeDOWN1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
