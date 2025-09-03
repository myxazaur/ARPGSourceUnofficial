//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ChestModel extends ModelChest {
   public ChestModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.chestBelow = new ModelRenderer(this, 0, 19);
      this.chestBelow.setRotationPoint(1.0F, 6.0F, 1.0F);
      this.chestBelow.addBox(0.0F, 0.0F, 0.0F, 14, 10, 14, 0.0F);
      this.chestKnob = new ModelRenderer(this, 0, 0);
      this.chestKnob.setRotationPoint(8.0F, 7.0F, 15.0F);
      this.chestKnob.addBox(-1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
      this.chestLid = new ModelRenderer(this, 0, 0);
      this.chestLid.setRotationPoint(1.0F, 7.0F, 15.0F);
      this.chestLid.addBox(-0.01F, -5.0F, -13.99F, 14, 5, 14, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.chestBelow.render(f5);
      this.chestKnob.render(f5);
      this.chestLid.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
