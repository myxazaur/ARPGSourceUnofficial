//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.renders.ModelRendererLimited;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class HorribleEmeraldShootModel extends ModelBase {
   public ModelRenderer shape0;
   public ModelRenderer shape6;
   public ModelRenderer shape1;
   public ModelRenderer shape1_1;
   public ModelRenderer shape1_2;
   public ModelRenderer shape1_3;

   public HorribleEmeraldShootModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shape1_1 = new ModelRenderer(this, 6, 0);
      this.shape1_1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1_1.addBox(-1.0F, 3.0F, -0.5F, 2, 1, 1, 0.0F);
      this.shape0 = new ModelRenderer(this, 0, 1);
      this.shape0.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape0.addBox(-2.0F, -2.0F, -0.5F, 4, 5, 1, 0.0F);
      this.shape1_2 = new ModelRenderer(this, 12, 0);
      this.shape1_2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1_2.addBox(-3.0F, -1.0F, -0.5F, 1, 3, 1, 0.0F);
      this.shape1_3 = new ModelRenderer(this, 12, 3);
      this.shape1_3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1_3.addBox(2.0F, -1.0F, -0.5F, 1, 3, 1, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-1.0F, -3.0F, -0.5F, 2, 1, 1, 0.0F);
      this.shape6 = new ModelRendererLimited(this, -9, 7, false, false, true, false, false, false);
      this.shape6.setRotationPoint(0.0F, 0.5F, 0.0F);
      this.shape6.addBox(-4.5F, 0.0F, -4.5F, 9, 0, 9, 0.0F);
      this.shape0.addChild(this.shape1_1);
      this.shape0.addChild(this.shape1_2);
      this.shape0.addChild(this.shape1_3);
      this.shape0.addChild(this.shape1);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      boolean d = entity.getEntityId() % 2 == 0;
      this.shape0.rotateAngleY = d ? AnimationTimer.tick * 0.05F : AnimationTimer.tick * -0.05F;
      this.shape6.rotateAngleX = AnimationTimer.tick * -0.1F;
      this.shape6.rotateAngleY = d ? AnimationTimer.tick * -0.1F : AnimationTimer.tick * 0.1F;
      this.shape0.render(f5);
      AbstractMobModel.light(80, true);
      this.shape6.render(f5);
      AbstractMobModel.returnlight();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
