//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ShellShardModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;

   public ShellShardModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-1.5F, -0.5F, -1.5F, 3, 1, 4, -0.1F);
      this.shape3 = new ModelRenderer(this, 0, 9);
      this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape3.addBox(-1.0F, -0.5F, -1.5F, 2, 1, 3, -0.1F);
      this.shape2 = new ModelRenderer(this, 0, 5);
      this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape2.addBox(-1.5F, -0.5F, -1.0F, 3, 1, 3, -0.1F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      int i = entity.getEntityId() % 3;
      if (i == 0) {
         this.shape1.render(f5);
      }

      if (i == 1) {
         this.shape3.render(f5);
      }

      if (i == 2) {
         this.shape2.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
