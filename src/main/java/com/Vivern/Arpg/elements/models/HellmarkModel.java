//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class HellmarkModel extends ModelBase {
   public ModelRenderer shapessh;
   public ModelRenderer shapessh_1;
   public ModelRenderer shapessh_2;
   public ModelRenderer shape;
   public ModelRenderer shape_1;
   public ModelRenderer shape_2;
   public ModelRenderer shape_3;
   public ModelRenderer shape_4;
   public ModelRenderer rune;
   public ModelRenderer spike;
   public ModelRenderer spike_1;
   public ModelRenderer spike_2;
   public ModelRenderer spike_3;
   public ModelRenderer spike_4;
   public ModelRenderer spike_5;
   public ModelRenderer spike_6;
   public ModelRenderer spike_7;
   public ModelRenderer spike_8;

   public HellmarkModel() {
      this.textureWidth = 36;
      this.textureHeight = 32;
      this.spike_5 = new ModelRenderer(this, 0, 0);
      this.spike_5.setRotationPoint(-6.0F, 7.5F, 4.5F);
      this.spike_5.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.spike_5, 0.0F, 0.0F, 1.1838568F);
      this.spike_7 = new ModelRenderer(this, 0, 0);
      this.spike_7.setRotationPoint(-6.0F, 4.5F, 2.0F);
      this.spike_7.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.spike_7, -0.4553564F, 0.0F, (float) (Math.PI / 2));
      this.shape_1 = new ModelRenderer(this, 28, 0);
      this.shape_1.setRotationPoint(-5.5F, 0.0F, -1.5F);
      this.shape_1.addBox(0.0F, 0.0F, 0.0F, 2, 9, 2, 0.0F);
      this.rune = new ModelRenderer(this, 0, 17);
      this.rune.setRotationPoint(-6.6F, 1.0F, 0.5F);
      this.rune.addBox(0.0F, 0.0F, 0.0F, 0, 7, 8, 0.0F);
      this.spike_2 = new ModelRenderer(this, 0, 0);
      this.spike_2.setRotationPoint(-6.0F, 7.5F, 7.0F);
      this.spike_2.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.spike_2, 0.31869712F, 0.0F, 1.2292354F);
      this.shapessh_1 = new ModelRenderer(this, 20, 18);
      this.shapessh_1.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh_1.addBox(-0.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh_1, 0.13665928F, 0.0F, 0.0F);
      this.shape_4 = new ModelRenderer(this, 16, 15);
      this.shape_4.setRotationPoint(-6.5F, 0.0F, 0.5F);
      this.shape_4.addBox(0.0F, 0.0F, 0.0F, 1, 9, 8, 0.0F);
      this.spike_4 = new ModelRenderer(this, 0, 0);
      this.spike_4.setRotationPoint(-6.0F, 1.5F, 4.5F);
      this.spike_4.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.spike_4, 0.0F, 0.0F, 1.8668041F);
      this.shapessh = new ModelRenderer(this, 20, 18);
      this.shapessh.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh.addBox(-1.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh, 0.0F, -0.13665928F, (float) (Math.PI / 2));
      this.shape_2 = new ModelRenderer(this, 26, 12);
      this.shape_2.setRotationPoint(-5.5F, 0.0F, 8.5F);
      this.shape_2.addBox(0.0F, 0.0F, 0.0F, 2, 9, 2, 0.0F);
      this.spike = new ModelRenderer(this, 0, 0);
      this.spike.setRotationPoint(-6.0F, 1.5F, 7.0F);
      this.spike.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.spike, 0.31869712F, 0.0F, 1.775698F);
      this.spike_1 = new ModelRenderer(this, 0, 0);
      this.spike_1.setRotationPoint(-6.0F, 4.5F, 7.0F);
      this.spike_1.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.spike_1, 0.4553564F, 0.0F, (float) (Math.PI / 2));
      this.shape_3 = new ModelRenderer(this, 12, 0);
      this.shape_3.setRotationPoint(-5.5F, 11.0F, 1.5F);
      this.shape_3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 6, 0.0F);
      this.spike_8 = new ModelRenderer(this, 0, 0);
      this.spike_8.setRotationPoint(-6.0F, 7.5F, 2.0F);
      this.spike_8.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.spike_8, -0.31869712F, 0.0F, 1.2292354F);
      this.shapessh_2 = new ModelRenderer(this, 20, 18);
      this.shapessh_2.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh_2.addBox(3.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh_2, 0.0F, -0.13665928F, (float) (Math.PI / 2));
      this.spike_3 = new ModelRenderer(this, 0, 0);
      this.spike_3.setRotationPoint(-6.0F, 4.5F, 4.5F);
      this.spike_3.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.spike_3, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.shape = new ModelRenderer(this, 0, 2);
      this.shape.setRotationPoint(-5.5F, -2.0F, 0.5F);
      this.shape.addBox(0.0F, 0.0F, 0.0F, 2, 13, 8, 0.0F);
      this.spike_6 = new ModelRenderer(this, 0, 0);
      this.spike_6.setRotationPoint(-6.0F, 1.5F, 2.0F);
      this.spike_6.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.spike_6, -0.31869712F, 0.0F, 1.775698F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      this.shape_1.render(f5);
      this.shapessh_1.render(f5);
      this.shape_4.render(f5);
      this.shapessh.render(f5);
      this.shape_2.render(f5);
      this.shapessh_2.render(f5);
      this.shape.render(f5);
      this.shape_3.render(f5);
      AbstractMobModel.light(150, true);
      this.spike_1.render(f5);
      this.spike_8.render(f5);
      this.spike_6.render(f5);
      this.spike.render(f5);
      this.spike_4.render(f5);
      this.spike_3.render(f5);
      this.spike_2.render(f5);
      this.spike_5.render(f5);
      this.spike_7.render(f5);
      if (f == 0.0F) {
         this.rune.render(f5);
      }

      AbstractMobModel.returnlight();
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
