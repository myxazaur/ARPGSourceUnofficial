//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class SummonedBlazeModel extends ModelBase {
   public ModelRenderer blazeHead;
   public ModelRenderer stick1;
   public ModelRenderer stick2;
   public ModelRenderer stick3;
   public ModelRenderer stickbbb1;
   public ModelRenderer stickbbb2;
   public ModelRenderer stickbbb3;
   public ModelRenderer stickccc1;
   public ModelRenderer stickccc2;
   public ModelRenderer blazeHorn1;
   public ModelRenderer blazeHorn2;
   public ModelRenderer blazeHorn3;

   public SummonedBlazeModel() {
      this.textureWidth = 54;
      this.textureHeight = 18;
      this.stick2 = new ModelRenderer(this, 24, 0);
      this.stick2.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.stick2.addBox(6.0F, -2.0F, -2.0F, 4, 6, 4, -0.5F);
      this.setRotateAngle(this.stick2, 0.0F, (float) (-Math.PI * 2.0 / 3.0), 0.0F);
      this.stickccc2 = new ModelRenderer(this, 24, 0);
      this.stickccc2.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.stickccc2.addBox(0.0F, 11.0F, -2.0F, 4, 6, 4, -0.5F);
      this.setRotateAngle(this.stickccc2, 0.0F, 2.276433F, 0.0F);
      this.stick3 = new ModelRenderer(this, 24, 0);
      this.stick3.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.stick3.addBox(5.0F, 0.0F, -2.0F, 4, 6, 4, -0.5F);
      this.setRotateAngle(this.stick3, 0.0F, (float) (Math.PI * 2.0 / 3.0), 0.0F);
      this.stickbbb3 = new ModelRenderer(this, 24, 0);
      this.stickbbb3.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.stickbbb3.addBox(3.0F, 0.0F, -2.0F, 4, 6, 4, -0.5F);
      this.setRotateAngle(this.stickbbb3, 0.0F, (float) (-Math.PI * 2.0 / 3.0), 0.0F);
      this.stickccc1 = new ModelRenderer(this, 24, 0);
      this.stickccc1.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.stickccc1.addBox(0.0F, 9.0F, -2.0F, 4, 6, 4, -0.5F);
      this.blazeHorn3 = new ModelRenderer(this, 32, 2);
      this.blazeHorn3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.blazeHorn3.addBox(-1.5F, -3.0F, 2.0F, 3, 3, 8, 0.0F);
      this.setRotateAngle(this.blazeHorn3, 0.5462881F, 0.0F, 0.0F);
      this.stick1 = new ModelRenderer(this, 24, 0);
      this.stick1.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.stick1.addBox(5.0F, -3.0F, -2.0F, 4, 6, 4, -0.5F);
      this.blazeHead = new ModelRenderer(this, 0, 2);
      this.blazeHead.setRotationPoint(0.0F, 8.0F, 0.0F);
      this.blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
      this.stickbbb1 = new ModelRenderer(this, 24, 0);
      this.stickbbb1.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.stickbbb1.addBox(2.0F, 2.0F, -2.0F, 4, 6, 4, -0.5F);
      this.stickbbb2 = new ModelRenderer(this, 24, 0);
      this.stickbbb2.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.stickbbb2.addBox(1.0F, 5.0F, -2.0F, 4, 6, 4, -0.5F);
      this.setRotateAngle(this.stickbbb2, 0.0F, (float) (Math.PI * 2.0 / 3.0), 0.0F);
      this.blazeHorn1 = new ModelRenderer(this, 32, 2);
      this.blazeHorn1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.blazeHorn1.addBox(-3.0F, -3.0F, 2.0F, 3, 3, 8, 0.0F);
      this.setRotateAngle(this.blazeHorn1, 0.4553564F, -0.4553564F, -0.18203785F);
      this.blazeHorn2 = new ModelRenderer(this, 32, 2);
      this.blazeHorn2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.blazeHorn2.addBox(0.0F, -3.0F, 2.0F, 3, 3, 8, 0.0F);
      this.setRotateAngle(this.blazeHorn2, 0.4553564F, 0.4553564F, 0.18203785F);
      this.blazeHead.addChild(this.blazeHorn3);
      this.blazeHead.addChild(this.blazeHorn1);
      this.blazeHead.addChild(this.blazeHorn2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 170.0F, 170.0F);
      GlStateManager.translate(this.stick2.offsetX, this.stick2.offsetY, this.stick2.offsetZ);
      GlStateManager.translate(this.stick2.rotationPointX * f5, this.stick2.rotationPointY * f5, this.stick2.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.stick2.offsetX, -this.stick2.offsetY, -this.stick2.offsetZ);
      GlStateManager.translate(-this.stick2.rotationPointX * f5, -this.stick2.rotationPointY * f5, -this.stick2.rotationPointZ * f5);
      this.stick2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.stickccc2.offsetX, this.stickccc2.offsetY, this.stickccc2.offsetZ);
      GlStateManager.translate(this.stickccc2.rotationPointX * f5, this.stickccc2.rotationPointY * f5, this.stickccc2.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.stickccc2.offsetX, -this.stickccc2.offsetY, -this.stickccc2.offsetZ);
      GlStateManager.translate(-this.stickccc2.rotationPointX * f5, -this.stickccc2.rotationPointY * f5, -this.stickccc2.rotationPointZ * f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.stick3.offsetX, this.stick3.offsetY, this.stick3.offsetZ);
      GlStateManager.translate(this.stick3.rotationPointX * f5, this.stick3.rotationPointY * f5, this.stick3.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.stick3.offsetX, -this.stick3.offsetY, -this.stick3.offsetZ);
      GlStateManager.translate(-this.stick3.rotationPointX * f5, -this.stick3.rotationPointY * f5, -this.stick3.rotationPointZ * f5);
      this.stick3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.stickbbb3.offsetX, this.stickbbb3.offsetY, this.stickbbb3.offsetZ);
      GlStateManager.translate(this.stickbbb3.rotationPointX * f5, this.stickbbb3.rotationPointY * f5, this.stickbbb3.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.stickbbb3.offsetX, -this.stickbbb3.offsetY, -this.stickbbb3.offsetZ);
      GlStateManager.translate(-this.stickbbb3.rotationPointX * f5, -this.stickbbb3.rotationPointY * f5, -this.stickbbb3.rotationPointZ * f5);
      this.stickbbb3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.stickccc1.offsetX, this.stickccc1.offsetY, this.stickccc1.offsetZ);
      GlStateManager.translate(this.stickccc1.rotationPointX * f5, this.stickccc1.rotationPointY * f5, this.stickccc1.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.stickccc1.offsetX, -this.stickccc1.offsetY, -this.stickccc1.offsetZ);
      GlStateManager.translate(-this.stickccc1.rotationPointX * f5, -this.stickccc1.rotationPointY * f5, -this.stickccc1.rotationPointZ * f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.stick1.offsetX, this.stick1.offsetY, this.stick1.offsetZ);
      GlStateManager.translate(this.stick1.rotationPointX * f5, this.stick1.rotationPointY * f5, this.stick1.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.stick1.offsetX, -this.stick1.offsetY, -this.stick1.offsetZ);
      GlStateManager.translate(-this.stick1.rotationPointX * f5, -this.stick1.rotationPointY * f5, -this.stick1.rotationPointZ * f5);
      this.stick1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.blazeHead.offsetX, this.blazeHead.offsetY, this.blazeHead.offsetZ);
      GlStateManager.translate(this.blazeHead.rotationPointX * f5, this.blazeHead.rotationPointY * f5, this.blazeHead.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.blazeHead.offsetX, -this.blazeHead.offsetY, -this.blazeHead.offsetZ);
      GlStateManager.translate(-this.blazeHead.rotationPointX * f5, -this.blazeHead.rotationPointY * f5, -this.blazeHead.rotationPointZ * f5);
      this.blazeHead.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.stickbbb1.offsetX, this.stickbbb1.offsetY, this.stickbbb1.offsetZ);
      GlStateManager.translate(this.stickbbb1.rotationPointX * f5, this.stickbbb1.rotationPointY * f5, this.stickbbb1.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.stickbbb1.offsetX, -this.stickbbb1.offsetY, -this.stickbbb1.offsetZ);
      GlStateManager.translate(-this.stickbbb1.rotationPointX * f5, -this.stickbbb1.rotationPointY * f5, -this.stickbbb1.rotationPointZ * f5);
      this.stickbbb1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.stickbbb2.offsetX, this.stickbbb2.offsetY, this.stickbbb2.offsetZ);
      GlStateManager.translate(this.stickbbb2.rotationPointX * f5, this.stickbbb2.rotationPointY * f5, this.stickbbb2.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.stickbbb2.offsetX, -this.stickbbb2.offsetY, -this.stickbbb2.offsetZ);
      GlStateManager.translate(-this.stickbbb2.rotationPointX * f5, -this.stickbbb2.rotationPointY * f5, -this.stickbbb2.rotationPointZ * f5);
      this.stickbbb2.render(f5);
      GL11.glEnable(2896);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      this.blazeHead.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.blazeHead.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      this.stick1.rotateAngleY = AnimationTimer.tick * 0.154F;
      this.stick2.rotateAngleY = (AnimationTimer.tick / 1.2F + 120.0F) * 0.15F;
      this.stick3.rotateAngleY = (AnimationTimer.tick / 1.1F + 240.0F) * 0.145F;
      this.stickbbb1.rotateAngleY = AnimationTimer.tick * 0.14F;
      this.stickbbb2.rotateAngleY = (AnimationTimer.tick / 0.9F + 120.0F) * 0.13F;
      this.stickbbb3.rotateAngleY = (AnimationTimer.tick / 0.95F + 240.0F) * 0.143F;
   }
}
