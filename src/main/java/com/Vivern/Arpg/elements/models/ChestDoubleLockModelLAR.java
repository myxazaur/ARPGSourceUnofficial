//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ChestDoubleLockModelLAR extends ModelChest {
   public ModelRenderer chestKno;
   public ModelRenderer chestKno_1;
   public ModelRenderer chestKno_2;
   public ModelRenderer chestKno_3;
   public ModelRenderer chestKno_4;
   public ModelRenderer chestKno_5;

   public ChestDoubleLockModelLAR() {
      this.textureWidth = 128;
      this.textureHeight = 64;
      this.chestKno = new ModelRenderer(this, 0, 0);
      this.chestKno.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chestKno.addBox(0.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
      this.setRotateAngle(this.chestKno, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.chestKno_4 = new ModelRenderer(this, 0, 0);
      this.chestKno_4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chestKno_4.addBox(6.0F, -3.0F, -15.0F, 2, 4, 1, 0.0F);
      this.chestKno_3 = new ModelRenderer(this, 0, 0);
      this.chestKno_3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chestKno_3.addBox(-12.0F, -3.0F, -15.0F, 2, 4, 1, 0.0F);
      this.chestKno_5 = new ModelRenderer(this, 0, 0);
      this.chestKno_5.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chestKno_5.addBox(10.0F, -3.0F, -15.0F, 2, 4, 1, 0.0F);
      this.chestLid = new ModelRenderer(this, 0, 0);
      this.chestLid.setRotationPoint(1.0F, 7.0F, 15.0F);
      this.chestLid.addBox(0.0F, -6.0F, -14.0F, 30, 5, 14, 0.0F);
      this.chestKnob = new ModelRenderer(this, 0, 0);
      this.chestKnob.setRotationPoint(16.0F, 7.0F, 15.0F);
      this.chestKnob.addBox(-3.0F, -2.0F, -15.0F, 1, 4, 1, 0.0F);
      this.chestKno_1 = new ModelRenderer(this, 0, 0);
      this.chestKno_1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chestKno_1.addBox(2.0F, -2.0F, -15.0F, 1, 4, 1, 0.0F);
      this.chestKno_2 = new ModelRenderer(this, 0, 0);
      this.chestKno_2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chestKno_2.addBox(-8.0F, -3.0F, -15.0F, 2, 4, 1, 0.0F);
      this.chestBelow = new ModelRenderer(this, 0, 19);
      this.chestBelow.setRotationPoint(1.0F, 6.0F, 1.0F);
      this.chestBelow.addBox(0.0F, 0.0F, 0.0F, 30, 10, 14, 0.0F);
      this.chestKnob.addChild(this.chestKno);
      this.chestKnob.addChild(this.chestKno_4);
      this.chestKnob.addChild(this.chestKno_3);
      this.chestKnob.addChild(this.chestKno_5);
      this.chestKnob.addChild(this.chestKno_1);
      this.chestKnob.addChild(this.chestKno_2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.chestLid.render(f5);
      this.chestKnob.render(f5);
      this.chestBelow.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
