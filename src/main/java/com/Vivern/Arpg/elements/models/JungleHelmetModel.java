//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class JungleHelmetModel extends ModelBiped {
   public ModelRenderer leaf2;
   public ModelRenderer leaf1;
   public ModelRenderer leaf3;
   public ModelRenderer leaf4;
   public ModelRenderer leaf5;

   public JungleHelmetModel() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.leaf3 = new ModelRenderer(this, 58, 22);
      this.leaf3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.leaf3.addBox(-1.0F, -15.0F, -6.6F, 2, 9, 1, 0.0F);
      this.setRotateAngle(this.leaf3, -0.31869712F, -0.13665928F, 0.27314404F);
      this.leaf1 = new ModelRenderer(this, 58, 21);
      this.leaf1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.leaf1.addBox(-1.0F, -16.0F, -6.6F, 2, 10, 1, 0.0F);
      this.setRotateAngle(this.leaf1, -0.31869712F, 0.0F, 0.0F);
      this.leaf4 = new ModelRenderer(this, 58, 26);
      this.leaf4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.leaf4.addBox(3.2F, -9.3F, -6.2F, 2, 5, 1, 0.0F);
      this.setRotateAngle(this.leaf4, -0.5462881F, 0.27314404F, -1.0016445F);
      this.leaf5 = new ModelRenderer(this, 58, 26);
      this.leaf5.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.leaf5.addBox(-5.2F, -9.3F, -6.2F, 2, 5, 1, 0.0F);
      this.setRotateAngle(this.leaf5, -0.5462881F, -0.27314404F, 1.0016445F);
      this.leaf2 = new ModelRenderer(this, 58, 22);
      this.leaf2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.leaf2.addBox(-1.0F, -15.0F, -6.6F, 2, 9, 1, 0.0F);
      this.setRotateAngle(this.leaf2, -0.31869712F, 0.13665928F, -0.27314404F);
      this.bipedHead.addChild(this.leaf1);
      this.bipedHead.addChild(this.leaf2);
      this.bipedHead.addChild(this.leaf3);
      this.bipedHead.addChild(this.leaf4);
      this.bipedHead.addChild(this.leaf5);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5 * 1.13F);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
