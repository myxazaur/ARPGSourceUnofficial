//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ManaPumpModel extends ModelBase {
   public ModelRenderer shape3;
   public ModelRenderer pipe1;
   public ModelRenderer pipeMain;
   public ModelRenderer pipe2;
   public ModelRenderer shape4;
   public ModelRenderer pipe2a;
   public ModelRenderer pipe1a;

   public ManaPumpModel() {
      this.textureWidth = 64;
      this.textureHeight = 16;
      this.pipe1 = new ModelRenderer(this, 40, 0);
      this.pipe1.setRotationPoint(-11.0F, 19.0F, 0.0F);
      this.pipe1.addBox(0.0F, -2.0F, -2.0F, 3, 4, 4, 0.01F);
      this.pipeMain = new ModelRenderer(this, -1, 10);
      this.pipeMain.setRotationPoint(-8.0F, 19.0F, 0.0F);
      this.pipeMain.addBox(0.0F, -1.5F, -1.5F, 16, 3, 3, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 0);
      this.shape3.setRotationPoint(-2.0F, 19.0F, 0.0F);
      this.shape3.addBox(-2.0F, -2.5F, -2.5F, 4, 5, 5, 0.0F);
      this.pipe2 = new ModelRenderer(this, 40, 8);
      this.pipe2.mirror = true;
      this.pipe2.setRotationPoint(8.0F, 19.0F, 0.0F);
      this.pipe2.addBox(0.0F, -2.0F, -2.0F, 3, 4, 4, 0.01F);
      this.shape4 = new ModelRenderer(this, 18, 0);
      this.shape4.setRotationPoint(-2.0F, 19.0F, 0.0F);
      this.shape4.addBox(-2.0F, -2.5F, -2.5F, 4, 5, 5, 0.0F);
      this.pipe1a = new ModelRenderer(this, 50, 4);
      this.pipe1a.setRotationPoint(-11.0F, 19.0F, 0.0F);
      this.pipe1a.addBox(0.0F, -2.0F, -2.0F, 3, 4, 4, 0.01F);
      this.pipe2a = new ModelRenderer(this, 50, 4);
      this.pipe2a.mirror = true;
      this.pipe2a.setRotationPoint(8.0F, 19.0F, 0.0F);
      this.pipe2a.addBox(0.0F, -2.0F, -2.0F, 3, 4, 4, 0.01F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f == 0.0F) {
         this.shape4.render(f5);
         this.pipe1a.render(f5);
         this.pipe2a.render(f5);
      } else {
         this.pipe1.render(f5);
         this.shape3.render(f5);
         this.pipe2.render(f5);
         this.pipeMain.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
