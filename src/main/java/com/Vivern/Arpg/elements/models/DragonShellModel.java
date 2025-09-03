//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class DragonShellModel extends ModelBase {
   public ModelRenderer shapessh;
   public ModelRenderer shapessh_1;
   public ModelRenderer shapessh_2;
   public ModelRenderer shape;
   public ModelRenderer shape_1;
   public ModelRenderer shape_2;
   public ModelRenderer shape_3;
   public ModelRenderer eye;
   public ModelRenderer eye1;
   public ModelRenderer eye2;
   public ModelRenderer shape_4;

   public DragonShellModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.eye1 = new ModelRenderer(this, 0, 21);
      this.eye1.setRotationPoint(-5.5F, 2.0F, 4.5F);
      this.eye1.addBox(-2.0F, -1.0F, -2.0F, 3, 3, 4, 0.0F);
      this.setRotateAngle(this.eye1, 0.0F, 0.0F, 1.1838568F);
      this.shapessh = new ModelRenderer(this, 28, 27);
      this.shapessh.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh.addBox(-1.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh, 0.0F, -0.13665928F, (float) (Math.PI / 2));
      this.shapessh_2 = new ModelRenderer(this, 28, 27);
      this.shapessh_2.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh_2.addBox(3.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh_2, 0.0F, -0.13665928F, (float) (Math.PI / 2));
      this.shape_3 = new ModelRenderer(this, 26, 0);
      this.shape_3.setRotationPoint(-5.0F, -1.0F, 8.5F);
      this.shape_3.addBox(0.0F, 0.0F, 0.0F, 1, 7, 2, 0.0F);
      this.shape_1 = new ModelRenderer(this, 0, 3);
      this.shape_1.setRotationPoint(-5.0F, 7.0F, 0.5F);
      this.shape_1.addBox(0.0F, 0.0F, 0.0F, 2, 6, 8, 0.0F);
      this.shape_2 = new ModelRenderer(this, 0, 0);
      this.shape_2.setRotationPoint(-5.0F, -1.0F, -1.5F);
      this.shape_2.addBox(0.0F, 0.0F, 0.0F, 1, 9, 2, 0.0F);
      this.shapessh_1 = new ModelRenderer(this, 28, 27);
      this.shapessh_1.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh_1.addBox(-0.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh_1, 0.13665928F, 0.0F, 0.0F);
      this.eye = new ModelRenderer(this, 20, 9);
      this.eye.setRotationPoint(-7.5F, 2.0F, 3.0F);
      this.eye.addBox(0.0F, 0.0F, 0.0F, 2, 3, 3, 0.0F);
      this.shape = new ModelRenderer(this, 0, 3);
      this.shape.setRotationPoint(-5.5F, -3.0F, 0.5F);
      this.shape.addBox(0.0F, 0.0F, 0.0F, 2, 10, 8, 0.0F);
      this.eye2 = new ModelRenderer(this, 12, 0);
      this.eye2.setRotationPoint(-5.5F, 5.0F, 4.5F);
      this.eye2.addBox(-2.0F, -1.0F, -2.0F, 2, 3, 4, 0.0F);
      this.setRotateAngle(this.eye2, 0.0F, 0.0F, 0.4098033F);
      this.shape_4 = new ModelRenderer(this, 14, 15);
      this.shape_4.setRotationPoint(-6.0F, -0.5F, 1.5F);
      this.shape_4.addBox(0.0F, 0.0F, 0.0F, 1, 10, 6, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      int tick = (AnimationTimer.tick + (int)f1) % 1000;
      float rotAdd = GetMOP.getfromto((float)tick, 10.0F, 20.0F)
         - GetMOP.getfromto((float)tick, 20.0F, 30.0F)
         + GetMOP.getfromto((float)tick, 400.0F, 410.0F)
         - GetMOP.getfromto((float)tick, 410.0F, 425.0F)
         + GetMOP.getfromto((float)tick, 670.0F, 678.0F)
         - GetMOP.getfromto((float)tick, 680.0F, 689.0F);
      if (f <= 0.0F) {
         this.eye1.rotateAngleZ = (67.0F - rotAdd * 52.0F) * 0.017453F;
         this.eye2.rotateAngleZ = (23.0F + rotAdd * 52.0F) * 0.017453F;
      } else {
         this.eye1.rotateAngleZ = (31.3F - rotAdd * 16.0F) * 0.017453F;
         this.eye2.rotateAngleZ = (60.0F + rotAdd * 15.0F) * 0.017453F;
      }

      this.eye1.render(f5);
      this.shapessh.render(f5);
      this.shapessh_2.render(f5);
      this.shape_3.render(f5);
      this.shape_1.render(f5);
      this.shape_2.render(f5);
      this.shapessh_1.render(f5);
      this.shape.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.eye2.offsetX, this.eye2.offsetY, this.eye2.offsetZ);
      GlStateManager.translate(this.eye2.rotationPointX * f5, this.eye2.rotationPointY * f5, this.eye2.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, 0.95);
      GlStateManager.translate(-this.eye2.offsetX, -this.eye2.offsetY, -this.eye2.offsetZ);
      GlStateManager.translate(-this.eye2.rotationPointX * f5, -this.eye2.rotationPointY * f5, -this.eye2.rotationPointZ * f5);
      this.eye2.render(f5);
      GlStateManager.popMatrix();
      this.shape_4.render(f5);
      AbstractMobModel.light(200, false);
      this.eye.render(f5);
      AbstractMobModel.returnlight();
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
