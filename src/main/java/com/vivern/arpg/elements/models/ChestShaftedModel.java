package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ChestShaftedModel extends ModelChest {
   public ModelRenderer chestadd;
   public ModelRenderer chestadd_1;
   public ModelRenderer chestadd_2;
   public ModelRenderer chestadd_3;
   public ModelRenderer chestKno;
   public boolean enabledGlow = false;

   public ChestShaftedModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.chestadd_1 = new ModelRenderer(this, 0, 21);
      this.chestadd_1.setRotationPoint(0.0F, 12.0F, 6.0F);
      this.chestadd_1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 4, 0.0F);
      this.chestBelow = new ModelRenderer(this, 0, 21);
      this.chestBelow.setRotationPoint(1.0F, 6.0F, 1.0F);
      this.chestBelow.addBox(0.0F, 2.0F, 0.0F, 14, 8, 14, 0.0F);
      this.chestadd = new ModelRenderer(this, 0, 21);
      this.chestadd.setRotationPoint(15.0F, 12.0F, 6.0F);
      this.chestadd.addBox(0.0F, 0.0F, 0.0F, 1, 2, 4, 0.0F);
      this.chestadd_2 = new ModelRenderer(this, 0, 49);
      this.chestadd_2.setRotationPoint(2.0F, -6.0F, -14.0F);
      this.chestadd_2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 14, 0.0F);
      this.chestLid = new ModelRenderer(this, 0, 0);
      this.chestLid.setRotationPoint(1.0F, 7.0F, 15.0F);
      this.chestLid.addBox(0.0F, -5.0F, -14.0F, 14, 6, 14, 0.0F);
      this.chestKno = new ModelRenderer(this, 0, 0);
      this.chestKno.setRotationPoint(6.0F, 0.0F, 0.0F);
      this.chestKno.addBox(-4.0F, -5.0F, -15.0F, 2, 8, 1, 0.0F);
      this.chestKnob = new ModelRenderer(this, 0, 0);
      this.chestKnob.setRotationPoint(8.0F, 7.0F, 15.0F);
      this.chestKnob.addBox(-4.0F, -5.0F, -15.0F, 2, 8, 1, 0.0F);
      this.chestadd_3 = new ModelRenderer(this, 0, 49);
      this.chestadd_3.setRotationPoint(-4.0F, -6.0F, -14.0F);
      this.chestadd_3.addBox(0.0F, 0.0F, 0.0F, 2, 1, 14, 0.0F);
      this.chestKnob.addChild(this.chestadd_2);
      this.chestKnob.addChild(this.chestKno);
      this.chestKnob.addChild(this.chestadd_3);
   }

   public ChestShaftedModel setglow() {
      this.enabledGlow = true;
      return this;
   }

   public void renderAll() {
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      this.chestKnob.rotateAngleX = this.chestLid.rotateAngleX;
      this.chestLid.render(0.0625F);
      this.chestBelow.render(0.0625F);
      if (this.enabledGlow) {
         GL11.glDisable(2896);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 190.0F, 190.0F);
      }

      this.chestKnob.render(0.0625F);
      this.chestadd_1.render(0.0625F);
      this.chestadd.render(0.0625F);
      if (this.enabledGlow) {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
         GL11.glEnable(2896);
      }
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      this.chestBelow.render(f5);
      this.chestLid.render(f5);
      if (this.enabledGlow) {
         GL11.glDisable(2896);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 190.0F, 190.0F);
      }

      this.chestadd_1.render(f5);
      this.chestadd.render(f5);
      this.chestKnob.render(f5);
      if (this.enabledGlow) {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
         GL11.glEnable(2896);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
