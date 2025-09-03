//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class DungeonPortalModel2 extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape1_1;
   public ModelRenderer shape1_2;
   public ModelRenderer shape1_3;
   public ModelRenderer shape1_4;
   public ModelRenderer shape1_5;
   public ModelRenderer shape1_6;
   public ModelRenderer shape1_7;

   public DungeonPortalModel2() {
      this.textureWidth = 16;
      this.textureHeight = 48;
      this.shape1_7 = new ModelRenderer(this, 0, 0);
      this.shape1_7.setRotationPoint(48.0F, -24.0F, -16.0F);
      this.shape1_7.addBox(-8.0F, 0.0F, -8.0F, 48, 32, 16, 0.0F);
      this.setRotateAngle(this.shape1_7, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.shape1_3 = new ModelRenderer(this, 0, 0);
      this.shape1_3.setRotationPoint(32.0F, -24.0F, 32.0F);
      this.shape1_3.addBox(-8.0F, 0.0F, -8.0F, 16, 32, 16, 0.0F);
      this.shape1_4 = new ModelRenderer(this, 0, 0);
      this.shape1_4.setRotationPoint(-32.0F, -24.0F, -32.0F);
      this.shape1_4.addBox(-8.0F, 0.0F, -8.0F, 16, 32, 16, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(-16.0F, -24.0F, 48.0F);
      this.shape1.addBox(-8.0F, 0.0F, -8.0F, 48, 32, 16, 0.0F);
      this.shape1_6 = new ModelRenderer(this, 0, 0);
      this.shape1_6.setRotationPoint(-48.0F, -24.0F, -16.0F);
      this.shape1_6.addBox(-8.0F, 0.0F, -8.0F, 48, 32, 16, 0.0F);
      this.setRotateAngle(this.shape1_6, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.shape1_1 = new ModelRenderer(this, 0, 0);
      this.shape1_1.setRotationPoint(-16.0F, -24.0F, -48.0F);
      this.shape1_1.addBox(-8.0F, 0.0F, -8.0F, 48, 32, 16, 0.0F);
      this.shape1_2 = new ModelRenderer(this, 0, 0);
      this.shape1_2.setRotationPoint(-32.0F, -24.0F, 32.0F);
      this.shape1_2.addBox(-8.0F, 0.0F, -8.0F, 16, 32, 16, 0.0F);
      this.shape1_5 = new ModelRenderer(this, 0, 0);
      this.shape1_5.setRotationPoint(32.0F, -24.0F, -32.0F);
      this.shape1_5.addBox(-8.0F, 0.0F, -8.0F, 16, 32, 16, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape1_7.render(f5);
      this.shape1_3.render(f5);
      this.shape1_4.render(f5);
      this.shape1.render(f5);
      this.shape1_6.render(f5);
      this.shape1_1.render(f5);
      this.shape1_2.render(f5);
      this.shape1_5.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
