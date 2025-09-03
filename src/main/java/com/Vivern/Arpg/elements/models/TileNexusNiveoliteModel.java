//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.tileentity.TileNexusNiveolite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TileNexusNiveoliteModel extends ModelBase {
   public ModelRenderer shape2;
   public ModelRenderer shape1;
   public ModelRenderer shape3;
   public ModelRenderer circle0;
   public ModelRenderer shapeSide1;
   public ModelRenderer circle;
   public ModelRenderer circle_1;
   public ModelRenderer circle_2;
   public ModelRenderer circle_3;
   public ModelRenderer circle_4;
   public ModelRenderer circle_5;
   public ModelRenderer circle_6;
   public ModelRenderer shapeSide2;

   public TileNexusNiveoliteModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.circle_4 = new ModelRenderer(this, 1, 24);
      this.circle_4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.circle_4.addBox(-4.0F, -0.5F, -4.0F, 2, 2, 2, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 41);
      this.shape1.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape1.addBox(-8.0F, 1.0F, -8.0F, 16, 7, 16, 0.0F);
      this.circle_2 = new ModelRenderer(this, 0, 24);
      this.circle_2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.circle_2.addBox(-2.0F, -0.5F, 3.0F, 4, 2, 2, 0.0F);
      this.circle = new ModelRenderer(this, 0, 24);
      this.circle.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.circle.addBox(3.0F, -0.5F, -2.0F, 2, 2, 4, 0.0F);
      this.circle_3 = new ModelRenderer(this, 0, 24);
      this.circle_3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.circle_3.addBox(-4.0F, -0.5F, 2.0F, 2, 2, 2, 0.0F);
      this.shapeSide2 = new ModelRenderer(this, 0, 10);
      this.shapeSide2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shapeSide2.addBox(-3.0F, -4.0F, -8.0F, 6, 2, 1, 0.0F);
      this.circle_1 = new ModelRenderer(this, 0, 25);
      this.circle_1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.circle_1.addBox(-2.0F, -0.5F, -5.0F, 4, 2, 2, 0.0F);
      this.shape2 = new ModelRenderer(this, 8, 20);
      this.shape2.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape2.addBox(-7.0F, -6.0F, -7.0F, 14, 7, 14, 0.0F);
      this.circle_6 = new ModelRenderer(this, 0, 24);
      this.circle_6.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.circle_6.addBox(2.0F, -0.5F, 2.0F, 2, 2, 2, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 2);
      this.shape3.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape3.addBox(-8.0F, -8.0F, -8.0F, 16, 2, 16, 0.0F);
      this.shapeSide1 = new ModelRenderer(this, 0, 20);
      this.shapeSide1.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shapeSide1.addBox(-5.0F, -6.0F, -8.0F, 10, 2, 1, 0.0F);
      this.circle0 = new ModelRenderer(this, 0, 24);
      this.circle0.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.circle0.addBox(-5.0F, -0.5F, -2.0F, 2, 2, 4, 0.0F);
      this.circle_5 = new ModelRenderer(this, 2, 24);
      this.circle_5.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.circle_5.addBox(2.0F, -0.5F, -4.0F, 2, 2, 2, 0.0F);
      this.circle0.addChild(this.circle_4);
      this.circle0.addChild(this.circle_2);
      this.circle0.addChild(this.circle);
      this.circle0.addChild(this.circle_3);
      this.shapeSide1.addChild(this.shapeSide2);
      this.circle0.addChild(this.circle_1);
      this.circle0.addChild(this.circle_6);
      this.circle0.addChild(this.circle_5);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (Minecraft.getMinecraft().world != null) {
         TileEntity tileEntity = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(f, f1, f2));
         if (tileEntity != null && tileEntity instanceof TileNexusNiveolite) {
            TileNexusNiveolite tileNexusNiveolite = (TileNexusNiveolite)tileEntity;
            float ft1 = GetMOP.softfromto(GetMOP.partial((float)tileNexusNiveolite.clientUprise, (float)tileNexusNiveolite.clientUprisePrev), 0.0F, 40.0F);
            this.circle0.offsetY = (ft1 * -4.0F + 3.0F) * 0.0625F;
            float anim = GetMOP.partial(tileNexusNiveolite.clientAnimation, tileNexusNiveolite.clientAnimationPrev);
            this.circle0.rotateAngleY = anim * 0.022F;
            this.circle0.rotateAngleX = MathHelper.sin(anim * 0.04F) * 0.1F * ft1;
            this.circle0.rotateAngleZ = MathHelper.cos(anim * 0.04F) * 0.1F * ft1;
         }
      }

      this.shape1.render(f5);
      this.shape2.render(f5);
      this.shape3.render(f5);
      this.circle0.render(f5);
      this.shapeSide1.rotateAngleY = 1.570796F;
      this.shapeSide1.render(f5);
      this.shapeSide1.rotateAngleY = 3.141592F;
      this.shapeSide1.render(f5);
      this.shapeSide1.rotateAngleY = 4.712388F;
      this.shapeSide1.render(f5);
      this.shapeSide1.rotateAngleY = 6.283184F;
      this.shapeSide1.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
