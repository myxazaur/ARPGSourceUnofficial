//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RunicMirrorModel extends ModelBase {
   public ModelRenderer plate;
   public ModelRenderer pod;
   public ModelRenderer stick;
   public ModelRenderer mirror;
   public ModelRenderer mirroreffect;
   public ModelRenderer framedown;
   public ModelRenderer frameright;
   public ModelRenderer frameleft;
   public ResourceLocation texeffect = new ResourceLocation("arpg:textures/plasma_cloud.png");

   public RunicMirrorModel() {
      this.textureWidth = 48;
      this.textureHeight = 32;
      this.frameleft = new ModelRenderer(this, 0, 0);
      this.frameleft.setRotationPoint(5.0F, -8.0F, -1.0F);
      this.frameleft.addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, 0.0F);
      this.framedown = new ModelRenderer(this, 6, 0);
      this.framedown.setRotationPoint(-5.0F, -1.0F, -1.0F);
      this.framedown.addBox(0.0F, 0.0F, 0.0F, 10, 1, 2, 0.0F);
      this.pod = new ModelRenderer(this, 0, 10);
      this.pod.setRotationPoint(-2.0F, 17.0F, -2.0F);
      this.pod.addBox(0.0F, 0.0F, 0.0F, 4, 5, 4, 0.0F);
      this.stick = new ModelRenderer(this, 0, 23);
      this.stick.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.stick.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
      this.mirror = new ModelRenderer(this, 16, 9);
      this.mirror.setRotationPoint(0.0F, 8.0F, 0.0F);
      this.mirror.addBox(-5.0F, -5.0F, -0.5F, 10, 10, 1, 0.0F);
      this.frameright = new ModelRenderer(this, 0, 0);
      this.frameright.setRotationPoint(-6.0F, -8.0F, -1.0F);
      this.frameright.addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, 0.0F);
      this.mirroreffect = new ModelRenderer(this, 30, 0);
      this.mirroreffect.setRotationPoint(0.0F, 8.0F, 0.0F);
      this.mirroreffect.addBox(-4.0F, -4.0F, -0.5F, 8, 8, 1, 0.2F);
      this.plate = new ModelRenderer(this, 0, 20);
      this.plate.setRotationPoint(-5.0F, 23.0F, -5.0F);
      this.plate.addBox(0.0F, -1.0F, 0.0F, 10, 2, 10, 0.0F);
      this.stick.addChild(this.frameleft);
      this.stick.addChild(this.framedown);
      this.stick.addChild(this.frameright);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.stick.rotateAngleY = f;
      this.mirror.rotateAngleY = f;
      this.mirroreffect.rotateAngleY = f;
      this.mirror.rotateAngleX = f1;
      this.mirroreffect.rotateAngleX = f1;
      this.stick.render(f5);
      this.pod.render(f5);
      this.plate.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.mirror.offsetX, this.mirror.offsetY, this.mirror.offsetZ);
      GlStateManager.translate(this.mirror.rotationPointX * f5, this.mirror.rotationPointY * f5, this.mirror.rotationPointZ * f5);
      GlStateManager.scale(0.99, 1.0, 1.0);
      GlStateManager.translate(-this.mirror.offsetX, -this.mirror.offsetY, -this.mirror.offsetZ);
      GlStateManager.translate(-this.mirror.rotationPointX * f5, -this.mirror.rotationPointY * f5, -this.mirror.rotationPointZ * f5);
      this.mirror.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      GlStateManager.depthMask(false);
      Minecraft.getMinecraft().renderEngine.bindTexture(this.texeffect);
      GlStateManager.matrixMode(5890);
      GlStateManager.loadIdentity();
      float ef = AnimationTimer.tick;
      float ef2 = ef * 0.05F;
      GlStateManager.rotate(ef2, 0.0F, 0.0F, 1.0F);
      GlStateManager.matrixMode(5888);
      GlStateManager.enableBlend();
      GlStateManager.color(f2, f3, f4, 1.0F);
      GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
      Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
      this.mirroreffect.render(f5);
      Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
      GlStateManager.matrixMode(5890);
      GlStateManager.loadIdentity();
      GlStateManager.matrixMode(5888);
      GlStateManager.disableBlend();
      GlStateManager.depthMask(true);
      GL11.glEnable(2896);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
