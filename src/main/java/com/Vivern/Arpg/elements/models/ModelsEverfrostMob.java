//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.EverfrostMobsPack;
import com.Vivern.Arpg.renders.ModelRendererGlow;
import com.Vivern.Arpg.renders.ModelRendererLimited;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class ModelsEverfrostMob {
   public static class AtorpidModel extends ModelBiped {
      public ModelRenderer bipedspine;
      public ModelRenderer bipedBody2;
      public ModelRenderer horn1;
      public ModelRenderer horn3;
      public ModelRenderer horn2;
      public ModelRenderer horn4;

      public AtorpidModel() {
         this.textureWidth = 64;
         this.textureHeight = 64;
         this.bipedBody2 = new ModelRenderer(this, 32, 16);
         this.bipedBody2.setRotationPoint(0.0F, 6.9F, 0.6F);
         this.bipedBody2.addBox(-3.5F, 0.0F, -2.6F, 7, 5, 4, 0.0F);
         this.bipedHeadwear = new ModelRenderer(this, 32, 0);
         this.bipedHeadwear.setRotationPoint(0.0F, -1.0F, 0.0F);
         this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.2F);
         this.bipedHead = new ModelRenderer(this, 0, 0);
         this.bipedHead.setRotationPoint(0.0F, -1.0F, 0.0F);
         this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, -0.1F);
         this.bipedspine = new ModelRenderer(this, 16, 16);
         this.bipedspine.setRotationPoint(0.0F, -1.0F, 0.0F);
         this.bipedspine.addBox(-1.0F, 0.0F, 0.8F, 2, 13, 2, 0.0F);
         this.horn3 = new ModelRenderer(this, 25, 0);
         this.horn3.setRotationPoint(-0.3F, -11.5F, -2.0F);
         this.horn3.addBox(-7.0F, -9.0F, -2.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.horn3, -0.045553092F, 0.3642502F, -1.6845918F);
         this.bipedRightLeg = new ModelRenderer(this, 0, 16);
         this.bipedRightLeg.mirror = true;
         this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
         this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
         this.horn4 = new ModelRenderer(this, 0, 0);
         this.horn4.setRotationPoint(-6.5F, -9.0F, -2.0F);
         this.horn4.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
         this.setRotateAngle(this.horn4, -1.821251F, 0.0F, 0.0F);
         this.horn2 = new ModelRenderer(this, 0, 0);
         this.horn2.setRotationPoint(-6.5F, -9.0F, -2.0F);
         this.horn2.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
         this.setRotateAngle(this.horn2, -1.821251F, 0.0F, 0.0F);
         this.bipedRightArm = new ModelRenderer(this, 54, 16);
         this.bipedRightArm.mirror = true;
         this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
         this.bipedRightArm.addBox(-1.0F, -2.0F, -2.0F, 2, 14, 3, 0.0F);
         this.setRotateAngle(this.bipedRightArm, (float) (-Math.PI * 4.0 / 9.0), -0.10000736F, 0.10000736F);
         this.bipedLeftArm = new ModelRenderer(this, 54, 16);
         this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 2, 14, 3, 0.0F);
         this.setRotateAngle(this.bipedLeftArm, -1.4570009F, 0.10000736F, -0.10000736F);
         this.bipedBody = new ModelRenderer(this, 0, 32);
         this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedBody.addBox(-4.0F, 0.0F, -2.6F, 8, 7, 5, 0.0F);
         this.horn1 = new ModelRenderer(this, 25, 0);
         this.horn1.setRotationPoint(-0.5F, -0.5F, 2.0F);
         this.horn1.addBox(-7.0F, -9.0F, -2.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.horn1, 0.0F, -0.3642502F, 1.5934856F);
         this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
         this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
         this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
         this.horn3.addChild(this.horn4);
         this.horn1.addChild(this.horn2);
         this.bipedHead.addChild(this.horn1);
         this.bipedHead.addChild(this.horn3);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         int anim1 = 0;
         if (entity instanceof AbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            anim1 = mob.animations[0];
         }

         float anim = (float)Math.sin(Math.max(0, anim1 - 80) * 0.16);
         this.bipedRightArm.rotateAngleX += anim;
         this.bipedLeftArm.rotateAngleX += anim;
         this.bipedBody2.render(f5);
         this.bipedHeadwear.render(f5);
         this.bipedHead.render(f5);
         this.bipedspine.render(f5);
         this.bipedRightLeg.render(f5);
         this.bipedRightArm.render(f5);
         this.bipedLeftArm.render(f5);
         this.bipedBody.render(f5);
         this.bipedLeftLeg.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
         if (entityIn instanceof EntityZombie && ((EntityZombie)entityIn).isArmsRaised()) {
            boolean var11 = true;
         } else {
            boolean var10000 = false;
         }

         float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
         float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
         this.bipedRightArm.rotateAngleZ = 0.0F;
         this.bipedLeftArm.rotateAngleZ = 0.0F;
         this.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
         this.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
         this.bipedRightArm.rotateAngleX = (float) (-Math.PI * 4.0 / 9.0);
         this.bipedLeftArm.rotateAngleX = -1.4570009F;
         this.bipedRightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
         this.bipedLeftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
         this.bipedRightArm.rotateAngleZ = this.bipedRightArm.rotateAngleZ + (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
         this.bipedLeftArm.rotateAngleZ = this.bipedLeftArm.rotateAngleZ - (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
         this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX + MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
         this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX - MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
      }

      public void postRenderArm(float scale, EnumHandSide side) {
         super.postRenderArm(scale, side);
         GlStateManager.translate(side == EnumHandSide.RIGHT ? 0.07F : -0.07F, 0.1F, 0.0F);
      }
   }

   public static class AurorasPhantasmModel extends ModelBase {
      public ModelRenderer core;
      public ModelRenderer tent1;
      public ModelRenderer tent2;
      public ModelRenderer tent3;
      public ModelRenderer ice1;
      public ModelRenderer ice2;
      public ModelRenderer ice3;
      public ModelRenderer icicle1;
      public ModelRenderer icicle2;
      public ModelRenderer tentaaa1;
      public ModelRenderer tentbbb1;
      public ModelRenderer tentaaa2;
      public ModelRenderer tentbbb2;
      public ModelRenderer tentaaa3;
      public ModelRenderer tentbbb3;
      public ResourceLocation additionalTexture;

      public AurorasPhantasmModel(ResourceLocation additionalTexture) {
         this.additionalTexture = additionalTexture;
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.tent1 = new ModelRenderer(this, 21, 13);
         this.tent1.setRotationPoint(-1.0F, -0.5F, 2.0F);
         this.tent1.addBox(-0.5F, -0.5F, -0.5F, 1, 18, 1, 0.0F);
         this.setRotateAngle(this.tent1, 1.5025539F, -0.13665928F, 0.0F);
         this.ice3 = new ModelRendererLimited(this, 0, 0, false, false, false, false, true, false);
         this.ice3.setRotationPoint(0.0F, 0.0F, 1.0F);
         this.ice3.addBox(-10.5F, -10.5F, 0.0F, 21, 21, 0, 0.0F);
         this.ice1 = new ModelRendererLimited(this, 0, 0, false, false, false, false, true, false);
         this.ice1.setRotationPoint(0.0F, 0.0F, -1.0F);
         this.ice1.addBox(-10.5F, -10.5F, 0.0F, 21, 21, 0, 0.0F);
         this.tentbbb3 = new ModelRenderer(this, 55, 18);
         this.tentbbb3.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.tentbbb3.addBox(0.0F, 2.5F, -0.5F, 0, 10, 4, 0.0F);
         this.setRotateAngle(this.tentbbb3, 0.091106184F, 0.0F, 0.0F);
         this.tent3 = new ModelRenderer(this, 21, 13);
         this.tent3.setRotationPoint(1.0F, -0.5F, 2.0F);
         this.tent3.addBox(-0.5F, -0.5F, -0.5F, 1, 18, 1, 0.0F);
         this.setRotateAngle(this.tent3, 1.5025539F, 0.13665928F, 0.0F);
         this.tentaaa1 = new ModelRenderer(this, 21, 19);
         this.tentaaa1.setRotationPoint(0.0F, 17.0F, 0.0F);
         this.tentaaa1.addBox(-0.5F, -0.5F, -0.5F, 1, 12, 1, 0.0F);
         this.setRotateAngle(this.tentaaa1, 0.22759093F, 0.0F, 0.0F);
         this.core = new ModelRenderer(this, 0, 23);
         this.core.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.core.addBox(-2.5F, -2.5F, -2.0F, 5, 5, 4, 0.0F);
         this.tentaaa2 = new ModelRenderer(this, 21, 19);
         this.tentaaa2.setRotationPoint(0.0F, 17.0F, 0.0F);
         this.tentaaa2.addBox(-0.5F, -0.5F, -0.5F, 1, 12, 1, 0.0F);
         this.setRotateAngle(this.tentaaa2, 0.22759093F, 0.0F, 0.0F);
         this.icicle1 = new ModelRendererLimited(this, 55, 0, false, false, false, false, true, false);
         this.icicle1.setRotationPoint(0.0F, 0.0F, -1.0F);
         this.icicle1.addBox(-4.5F, -23.0F, 0.0F, 9, 21, 0, 0.0F);
         this.setRotateAngle(this.icicle1, 0.4098033F, 0.0F, 0.0F);
         this.tentbbb1 = new ModelRenderer(this, 55, 18);
         this.tentbbb1.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.tentbbb1.addBox(0.0F, 2.5F, -0.5F, 0, 10, 4, 0.0F);
         this.setRotateAngle(this.tentbbb1, 0.091106184F, 0.0F, 0.0F);
         this.tentbbb2 = new ModelRenderer(this, 55, 18);
         this.tentbbb2.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.tentbbb2.addBox(0.0F, 2.5F, -0.5F, 0, 10, 4, 0.0F);
         this.setRotateAngle(this.tentbbb2, 0.091106184F, 0.0F, 0.0F);
         this.icicle2 = new ModelRendererLimited(this, 55, 0, false, false, false, false, true, false);
         this.icicle2.setRotationPoint(0.0F, 0.0F, 1.0F);
         this.icicle2.addBox(-4.5F, -23.0F, 0.0F, 9, 21, 0, 0.0F);
         this.setRotateAngle(this.icicle2, -0.4098033F, 0.0F, 0.0F);
         this.tent2 = new ModelRenderer(this, 21, 13);
         this.tent2.setRotationPoint(0.0F, 0.5F, 2.0F);
         this.tent2.addBox(-0.5F, -0.5F, -0.5F, 1, 18, 1, 0.0F);
         this.setRotateAngle(this.tent2, 1.3658947F, 0.0F, 0.0F);
         this.tentaaa3 = new ModelRenderer(this, 21, 19);
         this.tentaaa3.setRotationPoint(0.0F, 17.0F, 0.0F);
         this.tentaaa3.addBox(-0.5F, -0.5F, -0.5F, 1, 12, 1, 0.0F);
         this.setRotateAngle(this.tentaaa3, 0.22759093F, 0.0F, 0.0F);
         this.ice2 = new ModelRendererLimited(this, 25, 0, false, false, false, false, true, false);
         this.ice2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.ice2.addBox(-14.5F, -14.5F, 0.0F, 29, 29, 0, 0.0F);
         this.setRotateAngle(this.ice2, 0.0F, 0.0F, 0.7740535F);
         this.tentaaa3.addChild(this.tentbbb3);
         this.tent1.addChild(this.tentaaa1);
         this.tent2.addChild(this.tentaaa2);
         this.tentaaa1.addChild(this.tentbbb1);
         this.tentaaa2.addChild(this.tentbbb2);
         this.tent3.addChild(this.tentaaa3);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 1.0F, 0.0F);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

         for (int k = 0; k < 2; k++) {
            float time = (entity.ticksExisted + Minecraft.getMinecraft().getRenderPartialTicks()) * 1.8F;
            if (k == 1) {
               Vec3d rainbow = EverfrostMobsPack.AurorasPhantasm.getPhantasmColor(entity);
               GlStateManager.pushMatrix();
               AbstractMobModel.light(240, false);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               if (this.additionalTexture != null) {
                  Minecraft.getMinecraft().getTextureManager().bindTexture(this.additionalTexture);
               }

               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.color((float)rainbow.x, (float)rainbow.y, (float)rainbow.z, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            }

            this.ice1.rotateAngleZ = time * 0.0281F;
            this.ice2.rotateAngleZ = time * 0.01771F;
            this.ice3.rotateAngleZ = -time * 0.0253F;
            this.core.render(f5);
            this.tent1.render(f5);
            this.tent2.render(f5);
            this.tent3.render(f5);
            this.ice1.render(f5);
            this.ice2.render(f5);
            this.ice3.render(f5);

            for (int i = 0; i < 6; i++) {
               float a = i * 1.047198F;
               this.icicle1.rotateAngleZ = a + -time * 0.03F;
               this.icicle2.rotateAngleZ = a + time * 0.0331F;
               this.icicle1.render(f5);
               this.icicle2.render(f5);
            }

            if (k == 1) {
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class FentralModel extends ModelBase {
      public ModelRenderer wolfMane;
      public ModelRenderer wolfBody;
      public ModelRenderer wolfLeg4;
      public ModelRenderer wolfHeadMain0;
      public ModelRenderer wolfLeg1;
      public ModelRenderer wolfLeg2;
      public ModelRenderer wolfLeg3;
      public ModelRenderer wolfTail;
      public ModelRenderer wolfMane2;
      public ModelRenderer spine;
      public ModelRenderer wolfHeadMain3;
      public ModelRenderer wolfHeadMain1;
      public ModelRenderer wolfHeadMain2;
      public ModelRenderer wolfHorn1;
      public ModelRenderer wolfHorn2;
      public ModelRenderer eyes;
      public ModelRenderer wolfHorna1;
      public ModelRenderer wolfHorna2;

      public FentralModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.wolfHeadMain1 = new ModelRenderer(this, 16, 14);
         this.wolfHeadMain1.setRotationPoint(1.0F, 0.0F, -2.0F);
         this.wolfHeadMain1.addBox(-3.0F, -5.0F, 0.0F, 2, 3, 1, 0.0F);
         this.setRotateAngle(this.wolfHeadMain1, 0.27314404F, 0.0F, -0.22759093F);
         this.wolfHorna1 = new ModelRenderer(this, 0, 10);
         this.wolfHorna1.setRotationPoint(0.1F, 2.6F, 0.0F);
         this.wolfHorna1.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.wolfHorna1, -1.548107F, 0.091106184F, -0.045553092F);
         this.wolfLeg2 = new ModelRenderer(this, 0, 18);
         this.wolfLeg2.setRotationPoint(1.0F, 16.0F, 7.0F);
         this.wolfLeg2.addBox(0.0F, 0.0F, -1.5F, 2, 8, 3, 0.0F);
         this.wolfLeg3 = new ModelRenderer(this, 1, 19);
         this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
         this.wolfLeg3.addBox(-0.5F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
         this.wolfHeadMain2 = new ModelRenderer(this, 16, 14);
         this.wolfHeadMain2.setRotationPoint(1.0F, 0.0F, -2.0F);
         this.wolfHeadMain2.addBox(1.0F, -5.0F, 0.0F, 2, 3, 1, 0.0F);
         this.setRotateAngle(this.wolfHeadMain2, 0.27314404F, 0.0F, 0.22759093F);
         this.wolfTail = new ModelRenderer(this, 11, 21);
         this.wolfTail.setRotationPoint(0.0F, 12.0F, 9.0F);
         this.wolfTail.addBox(-1.5F, 0.0F, -1.0F, 3, 8, 3, 0.0F);
         this.setRotateAngle(this.wolfTail, 0.7285004F, 0.0F, 0.0F);
         this.wolfLeg1 = new ModelRenderer(this, 0, 18);
         this.wolfLeg1.setRotationPoint(-3.0F, 16.0F, 7.0F);
         this.wolfLeg1.addBox(0.0F, 0.0F, -1.5F, 2, 8, 3, 0.0F);
         this.wolfHorn2 = new ModelRenderer(this, 0, 10);
         this.wolfHorn2.setRotationPoint(-0.3F, 1.8F, -4.6F);
         this.wolfHorn2.addBox(-0.5F, 0.0F, -1.0F, 1, 3, 1, 0.0F);
         this.setRotateAngle(this.wolfHorn2, -0.5462881F, -0.18203785F, 0.7285004F);
         this.wolfHorna2 = new ModelRenderer(this, 0, 10);
         this.wolfHorna2.setRotationPoint(0.1F, 2.6F, 0.0F);
         this.wolfHorna2.addBox(-0.7F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.wolfHorna2, -1.548107F, -0.091106184F, 0.045553092F);
         this.wolfMane2 = new ModelRenderer(this, 31, 2);
         this.wolfMane2.setRotationPoint(0.0F, 14.0F, -5.0F);
         this.wolfMane2.addBox(-4.0F, -3.0F, -3.0F, 8, 2, 7, 0.0F);
         this.setRotateAngle(this.wolfMane2, (float) (Math.PI / 2), 0.0F, 0.0F);
         this.wolfLeg4 = new ModelRenderer(this, 1, 19);
         this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
         this.wolfLeg4.addBox(0.5F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
         this.wolfHeadMain3 = new ModelRenderer(this, 0, 11);
         this.wolfHeadMain3.setRotationPoint(1.0F, 0.0F, 0.0F);
         this.wolfHeadMain3.addBox(-1.5F, 0.0F, -9.0F, 3, 2, 4, 0.0F);
         this.wolfBody = new ModelRenderer(this, 25, 16);
         this.wolfBody.setRotationPoint(0.0F, 13.0F, 2.0F);
         this.wolfBody.addBox(-3.0F, -2.0F, -3.0F, 6, 10, 6, 0.0F);
         this.setRotateAngle(this.wolfBody, (float) (Math.PI / 2), 0.0F, 0.0F);
         this.spine = new ModelRenderer(this, 49, 14);
         this.spine.setRotationPoint(0.0F, 10.0F, -5.0F);
         this.spine.addBox(0.0F, 0.0F, -6.0F, 0, 12, 6, 0.0F);
         this.setRotateAngle(this.spine, (float) (Math.PI / 2), 0.0F, 0.0F);
         this.wolfHorn1 = new ModelRenderer(this, 0, 10);
         this.wolfHorn1.setRotationPoint(2.3F, 1.8F, -4.6F);
         this.wolfHorn1.addBox(-0.5F, 0.0F, -1.0F, 1, 3, 1, 0.0F);
         this.setRotateAngle(this.wolfHorn1, -0.5462881F, 0.18203785F, -0.7285004F);
         this.wolfMane = new ModelRenderer(this, 28, 0);
         this.wolfMane.setRotationPoint(0.0F, 14.0F, -3.0F);
         this.wolfMane.addBox(-5.0F, -3.0F, -3.0F, 10, 6, 8, 0.0F);
         this.setRotateAngle(this.wolfMane, (float) (Math.PI / 2), 0.0F, 0.0F);
         this.wolfHeadMain0 = new ModelRenderer(this, -1, 0);
         this.wolfHeadMain0.setRotationPoint(-1.0F, 13.5F, -8.0F);
         this.wolfHeadMain0.addBox(-2.0F, -2.0F, -5.0F, 6, 5, 5, 0.0F);
         this.eyes = new ModelRendererGlow(this, 21, 0, 180, false);
         this.eyes.setRotationPoint(0.0F, 0.0F, -0.05F);
         this.eyes.addBox(-2.0F, -2.0F, -5.0F, 6, 5, 0, 0.0F);
         this.wolfHeadMain0.addChild(this.wolfHeadMain1);
         this.wolfHorn1.addChild(this.wolfHorna1);
         this.wolfHeadMain0.addChild(this.wolfHeadMain2);
         this.wolfHeadMain0.addChild(this.wolfHorn2);
         this.wolfHorn2.addChild(this.wolfHorna2);
         this.wolfHeadMain0.addChild(this.wolfHeadMain3);
         this.wolfHeadMain0.addChild(this.wolfHorn1);
         this.wolfHeadMain0.addChild(this.eyes);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         float health = 45.0F;
         float maxhealth = 45.0F;
         if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            health = entityLivingBase.getHealth();
            maxhealth = entityLivingBase.getMaxHealth();
         }

         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         float progr = health < maxhealth / 2.0F ? 1.0F : 0.0F;
         this.wolfHeadMain1.rotateAngleY = 0.87F * progr;
         this.wolfHeadMain2.rotateAngleY = -0.87F * progr;
         this.spine.offsetY = 6.0F * progr * -0.0625F;
         this.eyes.isHidden = progr == 0.0F;
         this.wolfBody.render(f5);
         this.wolfMane2.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.wolfMane.offsetX, this.wolfMane.offsetY, this.wolfMane.offsetZ);
         GlStateManager.translate(this.wolfMane.rotationPointX * f5, this.wolfMane.rotationPointY * f5, this.wolfMane.rotationPointZ * f5);
         GlStateManager.scale(1.0, 1.0 + progr * 0.1, 1.0 + progr * 0.1);
         GlStateManager.translate(-this.wolfMane.offsetX, -this.wolfMane.offsetY, -this.wolfMane.offsetZ);
         GlStateManager.translate(-this.wolfMane.rotationPointX * f5, -this.wolfMane.rotationPointY * f5, -this.wolfMane.rotationPointZ * f5);
         this.wolfMane.render(f5);
         GlStateManager.popMatrix();
         this.spine.render(f5);
         this.wolfHeadMain0.render(f5);
         this.wolfLeg1.render(f5);
         this.wolfLeg4.render(f5);
         this.wolfLeg3.render(f5);
         this.wolfLeg2.render(f5);
         this.wolfTail.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.wolfHeadMain0.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.wolfHeadMain0.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
         this.wolfLeg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
         this.wolfLeg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
         this.wolfLeg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
         this.wolfLeg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
         this.wolfTail.rotateAngleX = (float)Math.sin(AnimationTimer.tick / 40.0) / 3.0F + 0.7285004F;
         this.wolfTail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      }
   }

   public static class GargoyleModel extends AbstractMobModel {
      public ModelRenderer body;
      public ModelRenderer head;
      public ModelRenderer wing1;
      public ModelRenderer wing2;
      public ModelRenderer leg1a;
      public ModelRenderer leg2a;
      public ModelRenderer bipedRightArm;
      public ModelRenderer bipedLeftArm;
      public ModelRenderer tail;
      public ModelRenderer hat;
      public ModelRenderer hat2;
      public ModelRenderer leg1b;
      public ModelRenderer leg2b;
      public ModelRenderer spear;
      public ModelRenderer spear2;

      public GargoyleModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.leg2a = new ModelRenderer(this, 32, 0);
         this.leg2a.setRotationPoint(-2.0F, 13.0F, 4.0F);
         this.leg2a.addBox(-3.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
         this.setRotateAngle(this.leg2a, -0.59184116F, 0.0F, 0.0F);
         this.leg1b = new ModelRenderer(this, 12, 34);
         this.leg1b.setRotationPoint(1.0F, 5.0F, -1.0F);
         this.leg1b.addBox(-1.5F, 0.0F, -2.0F, 3, 6, 3, 0.0F);
         this.setRotateAngle(this.leg1b, (float) (Math.PI / 3), 0.0F, 0.0F);
         this.spear2 = new ModelRenderer(this, 37, 9);
         this.spear2.setRotationPoint(-2.0F, 8.3F, 0.0F);
         this.spear2.addBox(0.5F, 7.0F, -2.0F, 0, 12, 8, 0.0F);
         this.setRotateAngle(this.spear2, -1.821251F, 0.0F, 0.0F);
         this.hat = new ModelRenderer(this, 48, 0);
         this.hat.setRotationPoint(0.0F, -5.0F, 5.0F);
         this.hat.addBox(-6.0F, -7.0F, -4.0F, 4, 9, 2, 0.0F);
         this.setRotateAngle(this.hat, -0.7740535F, 0.0F, 0.0F);
         this.leg2b = new ModelRenderer(this, 12, 34);
         this.leg2b.setRotationPoint(-1.0F, 5.0F, -1.0F);
         this.leg2b.addBox(-1.5F, 0.0F, -2.0F, 3, 6, 3, 0.0F);
         this.setRotateAngle(this.leg2b, (float) (Math.PI / 3), 0.0F, 0.0F);
         this.spear = new ModelRenderer(this, 53, 21);
         this.spear.setRotationPoint(-3.0F, 7.0F, 0.0F);
         this.spear.addBox(0.5F, -8.0F, -0.4F, 2, 22, 2, 0.0F);
         this.setRotateAngle(this.spear, -1.775698F, 0.0F, 0.0F);
         this.body = new ModelRenderer(this, 27, 29);
         this.body.setRotationPoint(0.0F, 4.0F, 1.0F);
         this.body.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
         this.setRotateAngle(this.body, 0.31869712F, 0.0F, 0.0F);
         this.wing1 = new ModelRenderer(this, 0, 16);
         this.wing1.setRotationPoint(2.0F, 6.0F, 2.0F);
         this.wing1.addBox(0.0F, 0.0F, 0.0F, 12, 16, 2, 0.0F);
         this.setRotateAngle(this.wing1, 0.5462881F, 0.045553092F, -0.3642502F);
         this.head = new ModelRenderer(this, 0, 0);
         this.head.setRotationPoint(0.0F, 5.0F, -2.0F);
         this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
         this.hat2 = new ModelRenderer(this, 48, 0);
         this.hat2.setRotationPoint(0.0F, -5.0F, 5.0F);
         this.hat2.addBox(2.0F, -7.0F, -4.0F, 4, 9, 2, 0.0F);
         this.setRotateAngle(this.hat2, -0.7740535F, 0.0F, 0.0F);
         this.bipedLeftArm = new ModelRenderer(this, 0, 34);
         this.bipedLeftArm.mirror = true;
         this.bipedLeftArm.setRotationPoint(3.0F, 7.0F, 0.0F);
         this.bipedLeftArm.addBox(0.0F, -2.0F, -2.0F, 3, 11, 3, 0.0F);
         this.setRotateAngle(this.bipedLeftArm, -0.95609134F, -0.13665928F, -0.10000736F);
         this.bipedRightArm = new ModelRenderer(this, 0, 34);
         this.bipedRightArm.setRotationPoint(-3.0F, 7.0F, 0.0F);
         this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 3, 11, 3, 0.0F);
         this.setRotateAngle(this.bipedRightArm, -0.91053826F, 0.4553564F, 0.13665928F);
         this.wing2 = new ModelRenderer(this, 0, 16);
         this.wing2.mirror = true;
         this.wing2.setRotationPoint(-2.0F, 6.0F, 2.0F);
         this.wing2.addBox(-12.0F, 0.0F, 0.0F, 12, 16, 2, 0.0F);
         this.setRotateAngle(this.wing2, 0.5462881F, 0.045553092F, 0.3642502F);
         this.leg1a = new ModelRenderer(this, 32, 0);
         this.leg1a.setRotationPoint(2.0F, 13.0F, 4.0F);
         this.leg1a.addBox(-1.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
         this.setRotateAngle(this.leg1a, -0.59184116F, 0.0F, 0.0F);
         this.tail = new ModelRenderer(this, 28, 17);
         this.tail.setRotationPoint(0.0F, 7.0F, 1.0F);
         this.tail.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
         this.setRotateAngle(this.tail, 0.63739425F, 0.0F, 0.0F);
         this.leg1a.addChild(this.leg1b);
         this.bipedRightArm.addChild(this.spear2);
         this.head.addChild(this.hat);
         this.leg2a.addChild(this.leg2b);
         this.bipedRightArm.addChild(this.spear);
         this.head.addChild(this.hat2);
         this.body.addChild(this.tail);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         float anim = (float)MathHelper.clamp(Math.sin(Math.max(0, an1 - 85) * 0.2) * 1.6, 0.0, 1.0);
         this.bipedRightArm.rotateAngleX = -0.91053826F + anim * -0.91053826F;
         this.bipedRightArm.rotateAngleY = 0.4553564F + anim * 0.31053826F;
         this.leg2a.render(f5);
         this.body.render(f5);
         this.wing1.render(f5);
         this.head.render(f5);
         this.bipedLeftArm.render(f5);
         this.bipedRightArm.render(f5);
         this.wing2.render(f5);
         this.leg1a.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.head.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.head.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
         float sinn = (float)Math.sin(AnimationTimer.tick / 10.0);
         this.wing1.rotateAngleX = sinn / 1.6F + 0.5462881F;
         this.wing2.rotateAngleX = sinn / 1.6F + 0.5462881F;
         this.wing1.rotateAngleY = sinn / 3.0F;
         this.wing2.rotateAngleY = sinn / -3.0F;
      }
   }

   public static class GelumModel extends ModelBase {
      public ModelRenderer core;
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer shape5;
      public ModelRenderer shape6;
      public ModelRenderer shape7;
      public ModelRenderer shape8;
      public ModelRenderer shape9;
      public ModelRenderer shape10;
      public ModelRenderer shape11;
      public ModelRenderer shape12;
      public ModelRenderer shape13;
      public ModelRenderer tent1;
      public ModelRenderer tent2;
      public ModelRenderer tent3;
      public ModelRenderer tentaaa1;
      public ModelRenderer tentbbb1;
      public ModelRenderer tentaaa2;
      public ModelRenderer tentbbb2;
      public ModelRenderer tentaaa3;
      public ModelRenderer tentbbb3;

      public GelumModel() {
         this.textureWidth = 44;
         this.textureHeight = 32;
         this.tent1 = new ModelRenderer(this, 20, 13);
         this.tent1.setRotationPoint(-1.0F, -0.5F, 2.0F);
         this.tent1.addBox(-0.5F, -0.5F, -0.5F, 1, 16, 1, 0.0F);
         this.setRotateAngle(this.tent1, 1.5025539F, -0.13665928F, 0.0F);
         this.tentbbb2 = new ModelRenderer(this, 16, 17);
         this.tentbbb2.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.tentbbb2.addBox(0.0F, 2.5F, 0.0F, 0, 7, 2, 0.0F);
         this.setRotateAngle(this.tentbbb2, 0.091106184F, 0.0F, 0.0F);
         this.shape5 = new ModelRenderer(this, 24, 0);
         this.shape5.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape5.addBox(-1.0F, -19.0F, -1.0F, 2, 13, 2, 0.0F);
         this.core = new ModelRenderer(this, 0, 22);
         this.core.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.core.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 4, 0.0F);
         this.tent3 = new ModelRenderer(this, 20, 13);
         this.tent3.setRotationPoint(1.0F, -0.5F, 2.0F);
         this.tent3.addBox(-0.5F, -0.5F, -0.5F, 1, 16, 1, 0.0F);
         this.setRotateAngle(this.tent3, 1.5025539F, 0.13665928F, 0.0F);
         this.shape2 = new ModelRenderer(this, 4, 0);
         this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape2.addBox(-1.0F, -18.0F, -1.0F, 2, 13, 2, 0.0F);
         this.tentbbb3 = new ModelRenderer(this, 16, 17);
         this.tentbbb3.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.tentbbb3.addBox(0.0F, 2.5F, 0.0F, 0, 7, 2, 0.0F);
         this.setRotateAngle(this.tentbbb3, 0.091106184F, 2.321986F, 0.0F);
         this.shape9 = new ModelRenderer(this, 16, 0);
         this.shape9.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape9.addBox(-1.0F, -15.0F, -1.0F, 2, 10, 2, 0.0F);
         this.shape13 = new ModelRenderer(this, 36, 17);
         this.shape13.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape13.addBox(-1.0F, -19.0F, -1.0F, 2, 13, 2, 0.0F);
         this.tent2 = new ModelRenderer(this, 20, 13);
         this.tent2.setRotationPoint(0.0F, 0.5F, 2.0F);
         this.tent2.addBox(-0.5F, -0.5F, -0.5F, 1, 16, 1, 0.0F);
         this.setRotateAngle(this.tent2, 1.3658947F, 0.0F, 0.0F);
         this.shape7 = new ModelRenderer(this, 28, 0);
         this.shape7.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape7.addBox(-1.0F, -17.0F, -1.5F, 2, 12, 3, 0.0F);
         this.shape11 = new ModelRenderer(this, 32, 14);
         this.shape11.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape11.addBox(-1.0F, -21.0F, -1.0F, 2, 16, 2, 0.0F);
         this.shape1 = new ModelRenderer(this, 0, 0);
         this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape1.addBox(-1.0F, -18.0F, -1.0F, 2, 13, 2, 0.0F);
         this.shape3 = new ModelRenderer(this, 27, 14);
         this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape3.addBox(-1.0F, -20.0F, -1.5F, 2, 15, 3, 0.0F);
         this.shape6 = new ModelRenderer(this, 28, 0);
         this.shape6.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape6.addBox(-1.0F, -20.0F, -1.0F, 2, 14, 2, 0.0F);
         this.tentaaa3 = new ModelRenderer(this, 24, 17);
         this.tentaaa3.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.tentaaa3.addBox(-0.5F, -0.5F, -1.0F, 1, 10, 2, 0.0F);
         this.setRotateAngle(this.tentaaa3, 0.22759093F, 0.0F, 0.0F);
         this.tentaaa1 = new ModelRenderer(this, 24, 17);
         this.tentaaa1.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.tentaaa1.addBox(-0.5F, -0.5F, -1.0F, 1, 10, 2, 0.0F);
         this.setRotateAngle(this.tentaaa1, 0.22759093F, 0.0F, 0.0F);
         this.shape4 = new ModelRenderer(this, 12, 0);
         this.shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape4.addBox(-1.0F, -19.0F, -1.0F, 2, 14, 2, 0.0F);
         this.tentaaa2 = new ModelRenderer(this, 24, 17);
         this.tentaaa2.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.tentaaa2.addBox(-0.5F, -0.5F, -1.0F, 1, 10, 2, 0.0F);
         this.setRotateAngle(this.tentaaa2, 0.22759093F, 0.0F, 0.0F);
         this.shape8 = new ModelRenderer(this, 36, 0);
         this.shape8.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape8.addBox(-1.0F, -17.0F, -1.0F, 2, 12, 2, 0.0F);
         this.shape12 = new ModelRenderer(this, 28, 16);
         this.shape12.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape12.addBox(-1.0F, -18.8F, -1.0F, 2, 14, 2, 0.0F);
         this.shape10 = new ModelRenderer(this, 27, 17);
         this.shape10.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape10.addBox(-1.0F, -18.0F, -1.5F, 2, 13, 3, 0.0F);
         this.tentbbb1 = new ModelRenderer(this, 16, 17);
         this.tentbbb1.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.tentbbb1.addBox(0.0F, 2.5F, 0.0F, 0, 7, 2, 0.0F);
         this.setRotateAngle(this.tentbbb1, 0.091106184F, -2.321986F, 0.0F);
         this.tentaaa2.addChild(this.tentbbb2);
         this.tentaaa3.addChild(this.tentbbb3);
         this.tent3.addChild(this.tentaaa3);
         this.tent1.addChild(this.tentaaa1);
         this.tent2.addChild(this.tentaaa2);
         this.tentaaa1.addChild(this.tentbbb1);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         f5 *= 1.3F;
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 1.0F, 0.0F);
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         this.core.render(f5);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         this.tent1.render(f5);
         this.shape5.render(f5);
         this.tent3.render(f5);
         this.shape2.render(f5);
         this.shape9.render(f5);
         this.shape13.render(f5);
         this.tent2.render(f5);
         this.shape7.render(f5);
         this.shape11.render(f5);
         this.shape1.render(f5);
         this.shape3.render(f5);
         this.shape6.render(f5);
         this.shape4.render(f5);
         this.shape8.render(f5);
         this.shape12.render(f5);
         this.shape10.render(f5);
         GL11.glDisable(3042);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.core.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.core.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
         float anglee = (float)Math.sin(AnimationTimer.tick / 40.0);
         int ticks = entityIn.ticksExisted;
         float random1 = AnimationTimer.rainbow1;
         int id = entityIn.getEntityId();
         int pl = 13;
         this.shape1.rotateAngleZ = (ticks + id + 5) * 0.08745329F * (float)Math.sin(id + pl++);
         this.shape1.rotateAngleY = random1 * (float)Math.cos(id) * (float) (Math.PI / 18);
         this.shape1.rotateAngleX = random1 * (float)Math.sin(id) * 0.11453292F;
         this.shape2.rotateAngleZ = (ticks + id + 8) * 0.085453294F * (float)Math.sin(id + pl++);
         this.shape2.rotateAngleY = random1 * (float)Math.cos(id + pl++) * (float) (Math.PI / 18);
         this.shape2.rotateAngleX = random1 * (float)Math.sin(id + pl++) * 0.12453292F;
         this.shape3.rotateAngleZ = (ticks + id + 15) * 0.09745329F * (float)Math.sin(id + pl++);
         this.shape3.rotateAngleY = random1 * (float)Math.cos(id + pl++) * 0.15453292F;
         this.shape3.rotateAngleX = random1 * (float)Math.sin(id + pl++) * 0.11453292F;
         this.shape4.rotateAngleZ = (ticks + id + 17) * 0.09545329F * (float)Math.sin(id + pl++);
         this.shape4.rotateAngleY = random1 * (float)Math.cos(id + pl++) * 0.15453292F;
         this.shape4.rotateAngleX = random1 * (float)Math.sin(id + pl++) * 0.12453292F;
         this.shape5.rotateAngleZ = (ticks + id + 23) * 0.07645329F * (float)Math.sin(id + pl++);
         this.shape5.rotateAngleY = random1 * (float)Math.cos(id + pl++) * 0.15453292F;
         this.shape5.rotateAngleX = random1 * (float)Math.sin(id + pl++) * 0.11453292F;
         this.shape6.rotateAngleZ = (ticks + id + 26) * 0.08745329F * (float)Math.sin(id + pl++);
         this.shape6.rotateAngleY = random1 * (float)Math.cos(id + pl++) * 0.15453292F;
         this.shape6.rotateAngleX = random1 * (float)Math.sin(id + pl++) * 0.11153292F;
         this.shape7.rotateAngleZ = (ticks + id + 29) * 0.096453294F * (float)Math.sin(id + pl++);
         this.shape7.rotateAngleY = random1 * (float)Math.cos(id + pl++) * 0.15453292F;
         this.shape7.rotateAngleX = random1 * (float)Math.sin(id + pl++) * 0.12253292F;
         this.shape8.rotateAngleZ = (ticks + id + 32) * 0.093453296F * (float)Math.sin(id + pl++);
         this.shape8.rotateAngleY = random1 * (float)Math.cos(id + pl++) * 0.15453292F;
         this.shape8.rotateAngleX = random1 * (float)Math.sin(id + pl++) * 0.13453291F;
         this.shape9.rotateAngleZ = (ticks + id + 35) * 0.089453295F * (float)Math.sin(id + pl++);
         this.shape9.rotateAngleY = random1 * (float)Math.cos(id + pl++) * 0.15453292F;
         this.shape9.rotateAngleX = random1 * (float)Math.sin(id + pl++) * 0.12153292F;
         this.shape10.rotateAngleZ = (ticks + id + 37) * 0.078453295F * (float)Math.sin(id + pl++);
         this.shape10.rotateAngleY = random1 * (float)Math.cos(id + pl++) * 0.15453292F;
         this.shape10.rotateAngleX = random1 * (float)Math.sin(id + pl++) * 0.13153292F;
         this.shape11.rotateAngleZ = (ticks + id + 40) * 0.08445329F * (float)Math.sin(id + pl++);
         this.shape11.rotateAngleY = random1 * (float)Math.cos(id + pl++) * 0.15453292F;
         this.shape11.rotateAngleX = random1 * (float)Math.sin(id + pl++) * 0.1253292F;
         this.shape12.rotateAngleZ = (ticks + id + 44) * 0.09945329F * (float)Math.sin(id + pl++);
         this.shape12.rotateAngleY = random1 * (float)Math.cos(id + pl++) * 0.15453292F;
         this.shape12.rotateAngleX = random1 * (float)Math.sin(id + pl++) * 0.1153292F;
         this.shape13.rotateAngleZ = (ticks + id + 46) * (float) (Math.PI / 18) * (float)Math.sin(id + pl++);
         this.shape13.rotateAngleY = random1 * (float)Math.cos(id + pl++) * 0.15453292F;
         this.shape13.rotateAngleX = random1 * (float)Math.sin(id + pl++) * 0.1453292F;
         this.tent1.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 20) / 40.0) / 3.0F + 1.5025539F;
         this.tent2.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 38) / 40.0) / 3.0F + 1.3658947F;
         this.tent3.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 61) / 40.0) / 3.0F + 1.5025539F;
         this.tent1.rotateAngleY = -anglee / 5.0F - 0.13665928F;
         this.tent2.rotateAngleZ = anglee / 6.0F;
         this.tent3.rotateAngleY = anglee / 4.0F + 0.13665928F;
         this.tentaaa1.rotateAngleZ = anglee / 5.0F;
         this.tentaaa2.rotateAngleZ = anglee / 8.0F;
         this.tentaaa3.rotateAngleZ = -anglee / 5.0F;
      }
   }

   public static class HailWraithModel extends AbstractMobModel {
      public ModelRenderer head;
      public ModelRenderer storm1;
      public ModelRenderer storm2;
      public ModelRenderer storm3;
      public ModelRenderer storm4;
      public ModelRenderer hail1;
      public ModelRenderer hail2;
      public ModelRenderer hail3;
      public ModelRenderer hail4;
      public ModelRenderer hail5;
      public ModelRenderer headin;

      public HailWraithModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.storm2 = new ModelRenderer(this, 3, 0);
         this.storm2.setRotationPoint(0.0F, 9.0F, 0.0F);
         this.storm2.addBox(-2.5F, 0.0F, 2.0F, 5, 6, 5, 0.0F);
         this.storm1 = new ModelRenderer(this, 3, 0);
         this.storm1.setRotationPoint(0.0F, 7.0F, 0.0F);
         this.storm1.addBox(-2.5F, 0.0F, 3.0F, 5, 6, 5, 0.0F);
         this.storm3 = new ModelRenderer(this, 3, 0);
         this.storm3.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.storm3.addBox(-2.5F, 0.0F, 1.0F, 5, 6, 5, 0.0F);
         this.hail4 = new ModelRenderer(this, 0, 0);
         this.hail4.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.hail4.addBox(-2.5F, 0.0F, 3.0F, 2, 2, 2, 0.0F);
         this.headin = new ModelRenderer(this, 23, 0);
         this.headin.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.headin.addBox(-2.5F, -7.0F, -2.5F, 5, 5, 5, 0.0F);
         this.head = new ModelRenderer(this, 0, 11);
         this.head.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 7, 8, 0.0F);
         this.hail2 = new ModelRenderer(this, 0, 0);
         this.hail2.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.hail2.addBox(-2.5F, 0.0F, 3.7F, 2, 2, 2, 0.0F);
         this.hail3 = new ModelRenderer(this, 0, 0);
         this.hail3.setRotationPoint(0.0F, 10.0F, 0.0F);
         this.hail3.addBox(-2.5F, 0.0F, 1.9F, 2, 2, 2, 0.0F);
         this.storm4 = new ModelRenderer(this, 3, 0);
         this.storm4.setRotationPoint(0.0F, 16.0F, 0.0F);
         this.storm4.addBox(-2.5F, 0.0F, 0.0F, 5, 6, 5, 0.0F);
         this.hail1 = new ModelRenderer(this, 0, 0);
         this.hail1.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.hail1.addBox(-2.5F, 0.0F, 2.3F, 2, 2, 2, 0.0F);
         this.hail5 = new ModelRenderer(this, 0, 0);
         this.hail5.setRotationPoint(0.0F, 14.0F, 0.0F);
         this.hail5.addBox(-2.5F, 0.0F, 1.3F, 2, 2, 2, 0.0F);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -0.15F, 0.0F);
         f5 *= 1.5F;
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         this.hail1.rotateAngleY = AnimationTimer.tick * -0.135F - 0.435F;
         this.hail2.rotateAngleY = AnimationTimer.tick * 0.14F - 0.135F;
         this.hail3.rotateAngleY = AnimationTimer.tick * -0.125F - 0.835F;
         this.hail4.rotateAngleY = AnimationTimer.tick * 0.15F - 0.335F;
         this.hail5.rotateAngleY = AnimationTimer.tick * -0.145F - 1.225F;
         light((int)(190.0 + Math.sin(entity.ticksExisted * 0.15F) * 25.0), false);
         this.headin.render(f5);
         returnlight();
         this.hail4.render(f5);
         this.head.render(f5);
         this.hail2.render(f5);
         this.hail3.render(f5);
         this.hail1.render(f5);
         this.hail5.render(f5);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         this.storm1.rotateAngleY = AnimationTimer.tick * 0.12F - 0.155F;
         this.storm2.rotateAngleY = AnimationTimer.tick * 0.16F - 1.335F;
         this.storm3.rotateAngleY = AnimationTimer.tick * 0.1F - 1.135F;
         this.storm4.rotateAngleY = AnimationTimer.tick * 0.09F - 0.635F;
         this.storm1.render(f5);
         this.storm2.render(f5);
         this.storm3.render(f5);
         this.storm4.render(f5);
         this.storm1.rotateAngleY = AnimationTimer.tick * -0.09F - 0.335F;
         this.storm2.rotateAngleY = AnimationTimer.tick * -0.07F - 0.75F;
         this.storm3.rotateAngleY = AnimationTimer.tick * -0.06F - 1.735F;
         this.storm4.rotateAngleY = AnimationTimer.tick * -0.14F - 0.235F;
         f5 *= 1.15F;
         this.storm1.render(f5);
         this.storm2.render(f5);
         this.storm3.render(f5);
         this.storm4.render(f5);
         GL11.glDisable(3042);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.head.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.head.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
         this.headin.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.headin.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      }
   }

   public static class HarridanOfIceModel extends ModelBase {
      public ModelRenderer headneck;
      public ModelRenderer snowball;
      public ModelRenderer quad1;
      public ModelRenderer quad2;
      public ModelRenderer quad3;
      public ModelRenderer quad4;
      public ModelRenderer quad5;
      public ModelRenderer shard;
      public ModelRenderer headeye1;
      public ModelRenderer head1;
      public ModelRenderer head2;
      public ModelRenderer head3;
      public ModelRenderer head4;
      public ModelRenderer headeye2;
      public ModelRenderer headup1;
      public ModelRenderer headup2;

      public HarridanOfIceModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.shard = new ModelRenderer(this, 11, 26);
         this.shard.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shard.addBox(-0.5F, 3.5F, 3.5F, 1, 5, 5, 0.0F);
         this.setRotateAngle(this.shard, (float) (Math.PI / 4), 1.5934856F, 0.0F);
         this.headneck = new ModelRenderer(this, 19, 0);
         this.headneck.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.headneck.addBox(-2.0F, -5.0F, -2.0F, 4, 9, 4, 0.0F);
         this.snowball = new ModelRenderer(this, 8, 16);
         this.snowball.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.snowball.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F);
         this.setRotateAngle(this.snowball, 0.0F, (float) (Math.PI / 4), 0.0F);
         this.headup2 = new ModelRenderer(this, 0, 33);
         this.headup2.setRotationPoint(0.0F, -14.0F, 2.0F);
         this.headup2.addBox(-2.0F, -2.0F, -1.0F, 4, 4, 1, 0.0F);
         this.setRotateAngle(this.headup2, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.head1 = new ModelRenderer(this, 0, 0);
         this.head1.mirror = true;
         this.head1.setRotationPoint(0.0F, -6.0F, -1.0F);
         this.head1.addBox(0.0F, -9.5F, -1.0F, 1, 2, 1, 0.0F);
         this.setRotateAngle(this.head1, -0.31869712F, 0.0F, (float) (-Math.PI / 4));
         this.quad4 = new ModelRenderer(this, 24, 24);
         this.quad4.setRotationPoint(0.0F, 18.0F, 0.0F);
         this.quad4.addBox(-5.0F, -1.0F, -5.0F, 10, 1, 10, 0.0F);
         this.setRotateAngle(this.quad4, 0.0F, (float) (Math.PI / 4), 0.0F);
         this.head3 = new ModelRenderer(this, 0, 0);
         this.head3.setRotationPoint(0.0F, -6.0F, -1.0F);
         this.head3.addBox(-1.0F, -9.5F, -1.0F, 1, 2, 1, 0.0F);
         this.setRotateAngle(this.head3, -0.31869712F, 0.0F, (float) (Math.PI / 4));
         this.quad3 = new ModelRenderer(this, 31, 15);
         this.quad3.setRotationPoint(0.0F, 14.0F, 0.0F);
         this.quad3.addBox(-4.0F, -1.0F, -4.0F, 8, 1, 8, 0.0F);
         this.setRotateAngle(this.quad3, 0.0F, (float) (Math.PI / 4), 0.0F);
         this.quad5 = new ModelRenderer(this, 16, 35);
         this.quad5.setRotationPoint(0.0F, 22.0F, 0.0F);
         this.quad5.addBox(-6.0F, -1.0F, -6.0F, 12, 1, 12, 0.0F);
         this.setRotateAngle(this.quad5, 0.0F, (float) (Math.PI / 4), 0.0F);
         this.quad2 = new ModelRenderer(this, 36, 7);
         this.quad2.setRotationPoint(0.0F, 10.0F, 0.0F);
         this.quad2.addBox(-3.5F, -1.0F, -3.5F, 7, 1, 7, 0.0F);
         this.setRotateAngle(this.quad2, 0.0F, (float) (Math.PI / 4), 0.0F);
         this.headup1 = new ModelRenderer(this, 0, 7);
         this.headup1.setRotationPoint(0.0F, -10.0F, 0.0F);
         this.headup1.addBox(-4.0F, -4.0F, -1.0F, 8, 8, 1, 0.0F);
         this.setRotateAngle(this.headup1, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.head4 = new ModelRenderer(this, 13, 0);
         this.head4.setRotationPoint(0.0F, -3.0F, -4.0F);
         this.head4.addBox(2.0F, -11.5F, 1.0F, 1, 6, 1, 0.0F);
         this.setRotateAngle(this.head4, -0.31869712F, 0.0F, (float) (Math.PI / 4));
         this.quad1 = new ModelRenderer(this, 39, 0);
         this.quad1.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.quad1.addBox(-3.0F, -1.0F, -3.0F, 6, 1, 6, 0.0F);
         this.setRotateAngle(this.quad1, 0.0F, (float) (Math.PI / 4), 0.0F);
         this.headeye2 = new ModelRenderer(this, 0, 27);
         this.headeye2.setRotationPoint(0.0F, -3.0F, -3.0F);
         this.headeye2.addBox(-2.0F, -2.0F, -1.0F, 4, 4, 1, 0.0F);
         this.setRotateAngle(this.headeye2, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.headeye1 = new ModelRenderer(this, 0, 38);
         this.headeye1.setRotationPoint(0.0F, -3.0F, -2.0F);
         this.headeye1.addBox(-4.0F, -4.0F, -1.0F, 8, 8, 1, 0.0F);
         this.setRotateAngle(this.headeye1, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.head2 = new ModelRenderer(this, 13, 0);
         this.head2.mirror = true;
         this.head2.setRotationPoint(0.0F, -3.0F, -4.0F);
         this.head2.addBox(-3.0F, -11.5F, 1.0F, 1, 6, 1, 0.0F);
         this.setRotateAngle(this.head2, -0.31869712F, 0.0F, (float) (-Math.PI / 4));
         this.headneck.addChild(this.headup2);
         this.headneck.addChild(this.head1);
         this.headneck.addChild(this.head3);
         this.headneck.addChild(this.headup1);
         this.headneck.addChild(this.head4);
         this.headneck.addChild(this.headeye2);
         this.headneck.addChild(this.headeye1);
         this.headneck.addChild(this.head2);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         float sin1 = MathHelper.sin(AnimationTimer.tick / 50.0F) * 0.124F;
         float cos1 = MathHelper.cos(AnimationTimer.tick / 50.0F) * 0.124F;
         float sin2 = MathHelper.sin(AnimationTimer.tick / 44.0F) * 0.124F;
         float cos2 = MathHelper.cos(AnimationTimer.tick / 44.0F) * 0.124F;
         float sin3 = MathHelper.sin(AnimationTimer.tick / 66.0F) * 0.124F;
         float cos3 = MathHelper.cos(AnimationTimer.tick / 66.0F) * 0.124F;
         this.quad5.rotateAngleX = sin1;
         this.quad5.rotateAngleZ = cos1;
         this.quad4.rotateAngleX = -sin2;
         this.quad4.rotateAngleZ = -cos2;
         this.quad3.rotateAngleX = sin3;
         this.quad3.rotateAngleZ = cos3;
         this.quad2.rotateAngleX = -sin1;
         this.quad2.rotateAngleZ = -cos1;
         this.quad1.rotateAngleX = sin2;
         this.quad1.rotateAngleZ = cos2;
         this.headneck.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.headneck.rotateAngleX = f4 * 0.007453292F;
         float rotat = AnimationTimer.tick * 0.03F;

         for (int i = 0; i < 6; i++) {
            this.shard.rotateAngleY = 1.047198F * i + rotat;
            this.shard.render(f5);
         }

         this.snowball.render(f5);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         this.headneck.render(f5);
         this.quad4.render(f5);
         this.quad3.render(f5);
         this.quad5.render(f5);
         this.quad2.render(f5);
         this.quad1.render(f5);
         GL11.glDisable(3042);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class IceApparitionModel extends AbstractMobModel {
      public ModelRenderer body;
      public ModelRenderer head;
      public ModelRenderer head3;
      public ModelRenderer spike1;
      public ModelRenderer spike2;
      public ModelRenderer spike3;
      public ModelRenderer spike4;
      public ModelRenderer righthand;
      public ModelRenderer lefthand;
      public ModelRenderer cloth1;
      public ModelRenderer cloth2;
      public ModelRenderer cloth3;
      public ModelRenderer head2;
      public ModelRenderer horn1;
      public ModelRenderer horn4;
      public ModelRenderer horn2;
      public ModelRenderer horn3;
      public ModelRenderer horn5;
      public ModelRenderer horn6;
      public ModelRenderer righthanda;
      public ModelRenderer righthandb;
      public ModelRenderer magic;
      public ModelRenderer lefthanda;
      public ModelRenderer lefthandb;

      public IceApparitionModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.head3 = new ModelRenderer(this, 0, 14);
         this.head3.setRotationPoint(0.0F, -4.4F, 2.0F);
         this.head3.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.head3, -0.13665928F, 0.0F, 0.0F);
         this.horn2 = new ModelRenderer(this, 60, 0);
         this.horn2.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.horn2.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.horn2, -1.0927507F, 2.048842F, -0.31869712F);
         this.horn5 = new ModelRenderer(this, 60, 0);
         this.horn5.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.horn5.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.horn5, -1.0927507F, -2.048842F, 0.31869712F);
         this.righthandb = new ModelRenderer(this, 43, 0);
         this.righthandb.setRotationPoint(0.0F, 10.0F, -1.2F);
         this.righthandb.addBox(-2.0F, -2.0F, -2.0F, 4, 3, 4, 0.0F);
         this.setRotateAngle(this.righthandb, 2.003289F, (float) Math.PI, -0.13665928F);
         this.body = new ModelRenderer(this, 0, 0);
         this.body.setRotationPoint(0.0F, 1.0F, 1.0F);
         this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 10, 4, 0.0F);
         this.setRotateAngle(this.body, 0.18203785F, 0.0F, 0.0F);
         this.horn6 = new ModelRenderer(this, 60, 0);
         this.horn6.setRotationPoint(-1.0F, 6.0F, 0.0F);
         this.horn6.addBox(-0.5F, -0.5F, -0.5F, 1, 6, 1, 0.0F);
         this.setRotateAngle(this.horn6, 0.5462881F, -1.6845918F, 0.18203785F);
         this.lefthanda = new ModelRenderer(this, 8, 20);
         this.lefthanda.setRotationPoint(0.0F, 8.0F, -0.2F);
         this.lefthanda.addBox(0.0F, 0.0F, -1.0F, 1, 9, 3, 0.0F);
         this.setRotateAngle(this.lefthanda, -0.8651597F, 0.0F, 0.27314404F);
         this.lefthandb = new ModelRenderer(this, 43, 0);
         this.lefthandb.mirror = true;
         this.lefthandb.setRotationPoint(0.0F, 10.0F, -0.2F);
         this.lefthandb.addBox(-2.0F, -2.0F, -2.0F, 4, 3, 4, 0.0F);
         this.setRotateAngle(this.lefthandb, 0.59184116F, 0.13665928F, -0.045553092F);
         this.head = new ModelRenderer(this, 24, 0);
         this.head.setRotationPoint(0.0F, -1.5F, 2.0F);
         this.head.addBox(-3.0F, -8.0F, -4.0F, 6, 5, 7, 0.0F);
         this.cloth3 = new ModelRenderer(this, 38, 16);
         this.cloth3.setRotationPoint(0.0F, 7.5F, -0.5F);
         this.cloth3.addBox(-4.5F, 0.0F, -2.0F, 9, 12, 4, 0.0F);
         this.setRotateAngle(this.cloth3, -0.13665928F, 0.0F, 0.0F);
         this.spike4 = new ModelRenderer(this, 16, 15);
         this.spike4.setRotationPoint(-3.0F, 6.0F, 3.5F);
         this.spike4.addBox(-0.5F, 0.0F, -0.5F, 1, 16, 1, 0.0F);
         this.setRotateAngle(this.spike4, 2.8228955F, 0.0F, -0.7285004F);
         this.head2 = new ModelRenderer(this, 45, 7);
         this.head2.setRotationPoint(0.0F, -2.0F, 0.0F);
         this.head2.addBox(-2.0F, -1.0F, -4.0F, 4, 4, 5, 0.0F);
         this.lefthand = new ModelRenderer(this, 0, 22);
         this.lefthand.setRotationPoint(4.0F, 2.0F, 0.8F);
         this.lefthand.addBox(0.0F, 0.0F, -0.5F, 1, 8, 2, 0.0F);
         this.setRotateAngle(this.lefthand, -0.091106184F, 0.0F, -0.59184116F);
         this.spike3 = new ModelRenderer(this, 16, 15);
         this.spike3.setRotationPoint(-3.0F, 2.0F, 2.8F);
         this.spike3.addBox(-0.5F, 0.0F, -0.5F, 1, 16, 1, 0.0F);
         this.setRotateAngle(this.spike3, 2.9140017F, 0.0F, -0.27314404F);
         this.spike1 = new ModelRenderer(this, 16, 15);
         this.spike1.setRotationPoint(3.0F, 2.0F, 2.8F);
         this.spike1.addBox(-0.5F, 0.0F, -0.5F, 1, 16, 1, 0.0F);
         this.setRotateAngle(this.spike1, 2.9140017F, 0.0F, 0.27314404F);
         this.righthanda = new ModelRenderer(this, 8, 20);
         this.righthanda.setRotationPoint(0.0F, 8.0F, -0.2F);
         this.righthanda.addBox(-1.0F, 0.0F, -1.0F, 1, 9, 3, 0.0F);
         this.setRotateAngle(this.righthanda, -0.8651597F, 0.0F, -0.27314404F);
         this.cloth1 = new ModelRenderer(this, 22, 14);
         this.cloth1.mirror = true;
         this.cloth1.setRotationPoint(0.0F, 9.0F, 1.0F);
         this.cloth1.addBox(0.5F, 0.0F, -2.5F, 4, 14, 4, 0.0F);
         this.setRotateAngle(this.cloth1, 0.0F, -0.22759093F, 0.0F);
         this.horn4 = new ModelRenderer(this, 60, 0);
         this.horn4.setRotationPoint(3.0F, -7.0F, 0.0F);
         this.horn4.addBox(-1.0F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.horn4, -2.9140017F, 0.045553092F, 0.31869712F);
         this.spike2 = new ModelRenderer(this, 16, 15);
         this.spike2.setRotationPoint(3.0F, 6.0F, 3.5F);
         this.spike2.addBox(-0.5F, 0.0F, -0.5F, 1, 16, 1, 0.0F);
         this.setRotateAngle(this.spike2, 2.8228955F, 0.0F, 0.7285004F);
         this.magic = new ModelRendererGlow(this, 23, 0, 180, true);
         this.magic.setRotationPoint(0.0F, 3.5F, 0.0F);
         this.magic.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.magic, 1.8668041F, (float) Math.PI, -0.13665928F);
         this.righthand = new ModelRenderer(this, 0, 22);
         this.righthand.setRotationPoint(-4.0F, 2.0F, 0.8F);
         this.righthand.addBox(-1.0F, 0.0F, -0.5F, 1, 8, 2, 0.0F);
         this.setRotateAngle(this.righthand, -0.091106184F, 0.0F, 0.59184116F);
         this.cloth2 = new ModelRenderer(this, 22, 14);
         this.cloth2.setRotationPoint(0.0F, 9.0F, 1.0F);
         this.cloth2.addBox(-4.5F, 0.0F, -2.5F, 4, 14, 4, 0.0F);
         this.setRotateAngle(this.cloth2, 0.0F, 0.22759093F, 0.0F);
         this.horn3 = new ModelRenderer(this, 60, 0);
         this.horn3.setRotationPoint(1.0F, 6.0F, 0.0F);
         this.horn3.addBox(-0.5F, -0.5F, -0.5F, 1, 6, 1, 0.0F);
         this.setRotateAngle(this.horn3, 0.5462881F, 1.6845918F, -0.18203785F);
         this.horn1 = new ModelRenderer(this, 60, 0);
         this.horn1.setRotationPoint(-3.0F, -7.0F, 0.0F);
         this.horn1.addBox(0.0F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.horn1, -2.9140017F, -0.045553092F, -0.31869712F);
         this.horn1.addChild(this.horn2);
         this.horn4.addChild(this.horn5);
         this.righthanda.addChild(this.righthandb);
         this.horn4.addChild(this.horn6);
         this.lefthand.addChild(this.lefthanda);
         this.lefthanda.addChild(this.lefthandb);
         this.body.addChild(this.cloth3);
         this.head.addChild(this.head2);
         this.righthand.addChild(this.righthanda);
         this.body.addChild(this.cloth1);
         this.head.addChild(this.horn4);
         this.righthandb.addChild(this.magic);
         this.body.addChild(this.cloth2);
         this.horn1.addChild(this.horn3);
         this.head.addChild(this.horn1);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         f5 *= 1.4F;
         this.righthand.rotateAngleY = 0.0F;
         this.righthand.rotateAngleZ = 0.59184116F;
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         float animProgress = (float)Math.sin(Math.max(0, an3 - 85) * 0.16F);
         if (animProgress != 0.0F) {
            this.righthand.rotateAngleX -= animProgress * 1.74F;
            this.righthand.rotateAngleY += animProgress * 0.3F;
            this.righthand.rotateAngleZ -= animProgress * 0.4F;
            this.righthanda.rotateAngleX += animProgress * 0.34F;
            this.righthandb.rotateAngleX -= animProgress;
         }

         this.head3.render(f5);
         this.body.render(f5);
         this.head.render(f5);
         this.spike4.render(f5);
         this.lefthand.render(f5);
         this.spike3.render(f5);
         this.spike1.render(f5);
         this.spike2.render(f5);
         this.righthand.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.head.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.head.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
         this.body.rotateAngleX = (float)Math.sin(AnimationTimer.tick / 50.0) / 5.0F + 0.18203785F;
         this.cloth1.rotateAngleX = (float)Math.sin(AnimationTimer.tick / 60.0) / 4.5F;
         this.cloth2.rotateAngleX = (float)Math.sin(AnimationTimer.tick / 63.0) / 4.5F;
         this.cloth3.rotateAngleX = (float)Math.sin(AnimationTimer.tick / 55.0) / 4.2F - 0.13665928F;
         this.lefthand.rotateAngleX = (float)Math.sin(AnimationTimer.tick / 40.0) / 5.2F - 0.091106184F;
         this.lefthanda.rotateAngleX = (float)Math.sin(AnimationTimer.tick / 40.0) / 5.2F - 0.8651597F;
         this.lefthandb.rotateAngleX = (float)Math.sin(AnimationTimer.tick / 40.0) / 5.2F + 0.59184116F;
         this.righthand.rotateAngleX = (float)Math.sin(AnimationTimer.tick / 40.0) / -5.2F - 0.091106184F;
         this.righthanda.rotateAngleX = (float)Math.sin(AnimationTimer.tick / 40.0) / -5.2F - 0.8651597F;
         this.righthandb.rotateAngleX = (float)Math.sin(AnimationTimer.tick / 40.0) / -5.2F + 2.003289F;
         this.magic.rotateAngleX = AnimationTimer.tick * 0.2F;
         this.magic.rotateAngleZ = AnimationTimer.tick * 0.1F;
         this.magic.offsetY = (float)Math.sin(AnimationTimer.tick / 30.0) / 4.0F;
      }
   }

   public static class IceWarriorModel extends ModelBiped {
      public ModelRenderer bipedBody2;
      public ModelRenderer horn1;
      public ModelRenderer horn3;
      public ModelRenderer horn2;
      public ModelRenderer horn4;

      public IceWarriorModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.bipedRightArm = new ModelRenderer(this, 40, 16);
         this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
         this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
         this.setRotateAngle(this.bipedRightArm, -0.4553564F, 0.0F, 0.0F);
         this.bipedRightLeg = new ModelRenderer(this, 0, 16);
         this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
         this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.2F);
         this.bipedBody2 = new ModelRenderer(this, 19, 20);
         this.bipedBody2.setRotationPoint(0.0F, 1.0F, 0.0F);
         this.bipedBody2.addBox(-4.0F, 0.0F, -3.0F, 8, 7, 1, 0.0F);
         this.bipedHeadwear = new ModelRenderer(this, 32, 0);
         this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.4F);
         this.bipedHead = new ModelRenderer(this, 0, 0);
         this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
         this.bipedBody = new ModelRenderer(this, 16, 16);
         this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
         this.horn2 = new ModelRenderer(this, 0, 0);
         this.horn2.setRotationPoint(-6.5F, -9.0F, -2.0F);
         this.horn2.addBox(0.0F, 0.0F, 0.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.horn2, (float) (-Math.PI / 2), 0.0F, 0.0F);
         this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
         this.bipedLeftLeg.mirror = true;
         this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
         this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.2F);
         this.bipedLeftArm = new ModelRenderer(this, 40, 16);
         this.bipedLeftArm.mirror = true;
         this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
         this.setRotateAngle(this.bipedLeftArm, -0.18203785F, 0.0F, 0.0F);
         this.horn1 = new ModelRenderer(this, 25, 0);
         this.horn1.setRotationPoint(-0.5F, -0.5F, 2.0F);
         this.horn1.addBox(-7.0F, -9.0F, -2.0F, 3, 5, 3, 0.0F);
         this.setRotateAngle(this.horn1, 0.0F, -0.3642502F, 1.5934856F);
         this.horn3 = new ModelRenderer(this, 25, 0);
         this.horn3.setRotationPoint(0.0F, -10.5F, -2.0F);
         this.horn3.addBox(-7.0F, -9.0F, -2.0F, 3, 5, 3, 0.0F);
         this.setRotateAngle(this.horn3, 0.0F, 0.3642502F, -1.5934856F);
         this.horn4 = new ModelRenderer(this, 0, 0);
         this.horn4.setRotationPoint(-6.5F, -9.0F, -2.0F);
         this.horn4.addBox(0.0F, 0.0F, 0.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.horn4, (float) (-Math.PI / 2), 0.0F, 0.0F);
         this.horn1.addChild(this.horn2);
         this.bipedHead.addChild(this.horn1);
         this.bipedHead.addChild(this.horn3);
         this.horn3.addChild(this.horn4);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         int anim1 = 0;
         if (entity instanceof AbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            anim1 = mob.animations[0];
         }

         this.bipedLeftArm.rotateAngleY = 0.0F;
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         float anim = (float)MathHelper.clamp(Math.sin(Math.max(0, anim1 - 85) * 0.2) * 1.75, 0.0, 1.0);
         this.bipedRightArm.rotateAngleX += anim * -1.7F;
         this.bipedRightArm.rotateAngleY += anim * -0.31053826F;
         this.bipedLeftArm.rotateAngleX += anim * -1.57F;
         this.bipedLeftArm.rotateAngleY += anim * 0.48053828F;
         GlStateManager.pushMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bipedRightArm.offsetX, this.bipedRightArm.offsetY, this.bipedRightArm.offsetZ);
         GlStateManager.translate(this.bipedRightArm.rotationPointX * f5, this.bipedRightArm.rotationPointY * f5, this.bipedRightArm.rotationPointZ * f5);
         GlStateManager.scale(1.1, 1.0, 1.1);
         GlStateManager.translate(-this.bipedRightArm.offsetX, -this.bipedRightArm.offsetY, -this.bipedRightArm.offsetZ);
         GlStateManager.translate(-this.bipedRightArm.rotationPointX * f5, -this.bipedRightArm.rotationPointY * f5, -this.bipedRightArm.rotationPointZ * f5);
         this.bipedRightArm.render(f5);
         GlStateManager.popMatrix();
         this.bipedRightLeg.render(f5);
         this.bipedBody2.render(f5);
         this.bipedHeadwear.render(f5);
         this.bipedHead.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bipedBody.offsetX, this.bipedBody.offsetY, this.bipedBody.offsetZ);
         GlStateManager.translate(this.bipedBody.rotationPointX * f5, this.bipedBody.rotationPointY * f5, this.bipedBody.rotationPointZ * f5);
         GlStateManager.scale(1.1, 1.0, 1.1);
         GlStateManager.translate(-this.bipedBody.offsetX, -this.bipedBody.offsetY, -this.bipedBody.offsetZ);
         GlStateManager.translate(-this.bipedBody.rotationPointX * f5, -this.bipedBody.rotationPointY * f5, -this.bipedBody.rotationPointZ * f5);
         this.bipedBody.render(f5);
         GlStateManager.popMatrix();
         this.bipedLeftLeg.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bipedLeftArm.offsetX, this.bipedLeftArm.offsetY, this.bipedLeftArm.offsetZ);
         GlStateManager.translate(this.bipedLeftArm.rotationPointX * f5, this.bipedLeftArm.rotationPointY * f5, this.bipedLeftArm.rotationPointZ * f5);
         GlStateManager.scale(1.1, 1.0, 1.1);
         GlStateManager.translate(-this.bipedLeftArm.offsetX, -this.bipedLeftArm.offsetY, -this.bipedLeftArm.offsetZ);
         GlStateManager.translate(-this.bipedLeftArm.rotationPointX * f5, -this.bipedLeftArm.rotationPointY * f5, -this.bipedLeftArm.rotationPointZ * f5);
         this.bipedLeftArm.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class NiveousSliderModel extends ModelBase {
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer shape5;

      public NiveousSliderModel() {
         this.textureWidth = 48;
         this.textureHeight = 32;
         this.shape1 = new ModelRenderer(this, 0, 5);
         this.shape1.setRotationPoint(0.0F, 18.0F, 0.0F);
         this.shape1.addBox(-6.0F, 0.0F, -6.0F, 12, 6, 12, 0.0F);
         this.shape4 = new ModelRenderer(this, 0, 0);
         this.shape4.setRotationPoint(0.0F, 1.0F, 0.0F);
         this.shape4.addBox(-4.0F, 0.0F, -7.0F, 8, 4, 1, 0.0F);
         this.setRotateAngle(this.shape4, 0.0F, (float) Math.PI, 0.0F);
         this.shape5 = new ModelRenderer(this, 0, 0);
         this.shape5.setRotationPoint(0.0F, 1.0F, 0.0F);
         this.shape5.addBox(-4.0F, 0.0F, -7.0F, 8, 4, 1, 0.0F);
         this.setRotateAngle(this.shape5, 0.0F, (float) (-Math.PI / 2), 0.0F);
         this.shape3 = new ModelRenderer(this, 0, 0);
         this.shape3.setRotationPoint(0.0F, 1.0F, 0.0F);
         this.shape3.addBox(-4.0F, 0.0F, -7.0F, 8, 4, 1, 0.0F);
         this.setRotateAngle(this.shape3, 0.0F, (float) (Math.PI / 2), 0.0F);
         this.shape2 = new ModelRenderer(this, 0, 0);
         this.shape2.setRotationPoint(0.0F, 1.0F, 0.0F);
         this.shape2.addBox(-4.0F, 0.0F, -7.0F, 8, 4, 1, 0.0F);
         this.shape1.addChild(this.shape4);
         this.shape1.addChild(this.shape5);
         this.shape1.addChild(this.shape3);
         this.shape1.addChild(this.shape2);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.shape1.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class SlimeModel extends AbstractMobModel {
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;

      public SlimeModel() {
         this.textureWidth = 48;
         this.textureHeight = 24;
         this.shape1 = new ModelRenderer(this, 0, 0);
         this.shape1.setRotationPoint(0.0F, 23.0F, 0.0F);
         this.shape1.addBox(-6.0F, -11.0F, -6.0F, 12, 12, 12, 0.0F);
         this.shape2 = new ModelRenderer(this, 0, 0);
         this.shape2.setRotationPoint(0.0F, 23.0F, 0.0F);
         this.shape2.addBox(-4.0F, -6.0F, -5.0F, 2, 2, 2, 0.0F);
         this.shape3 = new ModelRenderer(this, 0, 4);
         this.shape3.setRotationPoint(0.0F, 23.0F, 0.0F);
         this.shape3.addBox(2.0F, -6.0F, -5.0F, 2, 2, 2, 0.0F);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float jumpscale = (float)(Math.sin(Math.max(0, an3 - 85) * 0.16F) * 0.35F);
         float size = entity.height;
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
         GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
         GlStateManager.scale(size, size, size);
         GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
         GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
         this.shape2.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
         GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
         GlStateManager.scale(size, size, size);
         GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
         GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
         this.shape3.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
         GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
         GlStateManager.scale(size, size, size);
         GlStateManager.scale(1.0 - jumpscale / 3.0F, 1.0 + jumpscale, 1.0 - jumpscale / 3.0F);
         GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
         GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
         this.shape1.render(f5);
         GL11.glDisable(3042);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class SnowroverModel extends AbstractMobModel {
      public ModelRenderer headneck;
      public ModelRenderer body;
      public ModelRenderer snowball;
      public ModelRenderer headeye;
      public ModelRenderer head1;
      public ModelRenderer head2;
      public ModelRenderer head3;
      public ModelRenderer head4;
      public ModelRenderer armR1;
      public ModelRenderer armL1;
      public ModelRenderer armR2;
      public ModelRenderer cannon1;
      public ModelRenderer cannon2;
      public ModelRenderer cannon3;
      public ModelRenderer armL2;

      public SnowroverModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.body = new ModelRenderer(this, 0, 8);
         this.body.setRotationPoint(0.0F, 8.5F, 0.0F);
         this.body.addBox(-4.0F, -3.0F, -4.0F, 8, 6, 8, 0.0F);
         this.setRotateAngle(this.body, 0.27314404F, 0.0F, 0.0F);
         this.armR2 = new ModelRenderer(this, 0, -4);
         this.armR2.setRotationPoint(0.0F, 0.0F, -8.0F);
         this.armR2.addBox(0.0F, -2.0F, -8.0F, 0, 4, 8, 0.0F);
         this.setRotateAngle(this.armR2, 0.0F, -1.3658947F, 0.0F);
         this.armL1 = new ModelRenderer(this, 0, 14);
         this.armL1.setRotationPoint(4.0F, -1.0F, 1.0F);
         this.armL1.addBox(0.0F, -1.0F, -8.0F, 0, 2, 8, 0.0F);
         this.setRotateAngle(this.armL1, 0.27314404F, -0.091106184F, 0.0F);
         this.head1 = new ModelRenderer(this, 0, 9);
         this.head1.mirror = true;
         this.head1.setRotationPoint(0.0F, -3.0F, -1.0F);
         this.head1.addBox(0.0F, -8.0F, -1.0F, 2, 4, 1, 0.0F);
         this.setRotateAngle(this.head1, -0.31869712F, 0.0F, (float) (-Math.PI / 4));
         this.head3 = new ModelRenderer(this, 0, 9);
         this.head3.setRotationPoint(0.0F, -3.0F, -1.0F);
         this.head3.addBox(-2.0F, -8.0F, -1.0F, 2, 4, 1, 0.0F);
         this.setRotateAngle(this.head3, -0.31869712F, 0.0F, (float) (Math.PI / 4));
         this.armL2 = new ModelRenderer(this, 0, -4);
         this.armL2.setRotationPoint(0.0F, 0.0F, -8.0F);
         this.armL2.addBox(0.0F, -2.0F, -8.0F, 0, 4, 8, 0.0F);
         this.setRotateAngle(this.armL2, 0.31869712F, 0.63739425F, 0.95609134F);
         this.headeye = new ModelRenderer(this, 0, 25);
         this.headeye.setRotationPoint(0.0F, -3.0F, -2.0F);
         this.headeye.addBox(-3.0F, -3.0F, -1.0F, 6, 6, 1, 0.0F);
         this.setRotateAngle(this.headeye, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.armR1 = new ModelRenderer(this, 0, 14);
         this.armR1.setRotationPoint(-4.0F, -1.0F, 1.0F);
         this.armR1.addBox(0.0F, -1.0F, -8.0F, 0, 2, 8, 0.0F);
         this.setRotateAngle(this.armR1, 0.27314404F, 0.5462881F, 0.0F);
         this.cannon2 = new ModelRenderer(this, 0, 0);
         this.cannon2.setRotationPoint(1.5F, 3.0F, -2.0F);
         this.cannon2.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 1, 0.0F);
         this.cannon3 = new ModelRenderer(this, 38, 0);
         this.cannon3.setRotationPoint(0.5F, -4.0F, -2.0F);
         this.cannon3.addBox(0.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
         this.headneck = new ModelRenderer(this, 48, 2);
         this.headneck.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.headneck.addBox(-2.0F, -5.0F, -2.0F, 4, 6, 4, 0.0F);
         this.head4 = new ModelRenderer(this, 17, 0);
         this.head4.setRotationPoint(0.0F, -3.0F, -1.0F);
         this.head4.addBox(1.0F, -9.0F, 1.0F, 2, 6, 1, 0.0F);
         this.setRotateAngle(this.head4, -0.31869712F, 0.0F, (float) (Math.PI / 4));
         this.head2 = new ModelRenderer(this, 17, 0);
         this.head2.mirror = true;
         this.head2.setRotationPoint(0.0F, -3.0F, -1.0F);
         this.head2.addBox(-3.0F, -9.0F, 1.0F, 2, 6, 1, 0.0F);
         this.setRotateAngle(this.head2, -0.31869712F, 0.0F, (float) (-Math.PI / 4));
         this.snowball = new ModelRenderer(this, 22, 12);
         this.snowball.setRotationPoint(0.0F, 19.0F, 0.0F);
         this.snowball.addBox(-5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F);
         this.cannon1 = new ModelRenderer(this, 24, 1);
         this.cannon1.setRotationPoint(0.0F, 0.0F, -8.0F);
         this.cannon1.addBox(0.0F, -3.0F, -5.0F, 3, 3, 8, 0.0F);
         this.setRotateAngle(this.cannon1, -0.59184116F, 0.7740535F, 0.13665928F);
         this.armR1.addChild(this.armR2);
         this.body.addChild(this.armL1);
         this.headneck.addChild(this.head1);
         this.headneck.addChild(this.head3);
         this.armL1.addChild(this.armL2);
         this.headneck.addChild(this.headeye);
         this.body.addChild(this.armR1);
         this.cannon1.addChild(this.cannon2);
         this.cannon1.addChild(this.cannon3);
         this.headneck.addChild(this.head4);
         this.headneck.addChild(this.head2);
         this.armR2.addChild(this.cannon1);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         this.setRotateAngle(this.armR1, 0.27314404F, 0.5462881F, 0.0F);
         this.setRotateAngle(this.armL1, 0.27314404F, -0.091106184F, 0.0F);
         this.body.rotateAngleY = 0.0F;
         this.headneck.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.headneck.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.setRotateAngle(this.armR2, 0.0F, -1.3658947F, 0.0F);
         this.setRotateAngle(this.armL2, 0.31869712F, 0.63739425F, 0.95609134F);
         this.cannon2.offsetZ = 0.0F;
         if (an2 > 0) {
            float anim = 100.0F - an2 + Minecraft.getMinecraft().getRenderPartialTicks();
            float ft1 = GetMOP.getfromto(anim, 0.0F, 5.0F) - GetMOP.getfromto(anim, 6.0F, 10.0F);
            this.armR2.rotateAngleY += -0.38F * ft1;
            this.armL2.rotateAngleX += 0.77F * ft1;
            this.cannon2.offsetZ += 4.0F * ft1 * 0.0625F;
         }

         if (entity instanceof EverfrostMobsPack.Snowrover) {
            EverfrostMobsPack.Snowrover snowr = (EverfrostMobsPack.Snowrover)entity;
            this.armR1.isHidden = !snowr.hasGun;
            this.armL1.isHidden = !snowr.hasGun;
            if (snowr.hasGun) {
               this.armR1.rotateAngleY = this.armR1.rotateAngleY + this.headneck.rotateAngleY * 0.6F;
               this.armR1.rotateAngleX = this.armR1.rotateAngleX + this.headneck.rotateAngleX * 0.8F;
               this.armL1.rotateAngleY = this.armL1.rotateAngleY + this.headneck.rotateAngleY * 0.6F;
               this.armL1.rotateAngleX = this.armL1.rotateAngleX + this.headneck.rotateAngleX * 0.8F;
               this.body.rotateAngleY = this.headneck.rotateAngleY * 0.2F;
            }
         }

         this.snowball.rotateAngleX = GetMOP.partial(entity.distanceWalkedModified, entity.prevDistanceWalkedModified) * 3.0F;
         this.body.render(f5);
         this.snowball.render(f5);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         this.headneck.render(f5);
         GL11.glDisable(3042);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class WinterFuryModel extends AbstractMobModel {
      public ModelRenderer body;
      public ModelRenderer head1;
      public ModelRenderer decor1;
      public ModelRenderer shape1;
      public ModelRenderer wing1a;
      public ModelRenderer wing2a;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer decor2;
      public ModelRenderer wing1b;
      public ModelRenderer wing1x;
      public ModelRenderer wing1y;
      public ModelRenderer wing1c;
      public ModelRenderer wing1d;
      public ModelRenderer wing2b;
      public ModelRenderer wing2x;
      public ModelRenderer wing2y;
      public ModelRenderer wing2c;
      public ModelRenderer wing2d;
      public ModelRenderer head2;
      public ModelRenderer head3;
      public ModelRenderer head4;
      public ModelRenderer eye1;
      public ModelRenderer eye2;
      public ModelRenderer decor3;
      public ModelRenderer decor4;

      public WinterFuryModel() {
         this.textureWidth = 64;
         this.textureHeight = 64;
         this.wing1d = new ModelRenderer(this, 0, 32);
         this.wing1d.mirror = true;
         this.wing1d.setRotationPoint(-0.1F, 16.0F, 0.1F);
         this.wing1d.addBox(0.0F, 0.0F, 0.0F, 1, 9, 1, 0.0F);
         this.setRotateAngle(this.wing1d, 0.4553564F, 0.0F, 0.0F);
         this.decor3 = new ModelRenderer(this, 38, 39);
         this.decor3.setRotationPoint(0.0F, -0.5F, 0.7F);
         this.decor3.addBox(-3.0F, -1.0F, -1.0F, 6, 5, 7, 0.0F);
         this.setRotateAngle(this.decor3, 0.27314404F, 0.0F, 0.0F);
         this.body = new ModelRenderer(this, 0, 18);
         this.body.setRotationPoint(0.0F, 12.5F, -1.0F);
         this.body.addBox(-3.0F, -1.0F, -2.0F, 6, 5, 9, 0.0F);
         this.head1 = new ModelRenderer(this, 6, 43);
         this.head1.setRotationPoint(0.0F, 12.8F, -2.4F);
         this.head1.addBox(-2.0F, -1.0F, -7.0F, 4, 4, 7, 0.0F);
         this.setRotateAngle(this.head1, 0.18203785F, 0.0F, 0.0F);
         this.eye1 = new ModelRendererGlow(this, 8, 0, 240, false);
         this.eye1.setRotationPoint(1.6F, 1.0F, -4.0F);
         this.eye1.addBox(0.0F, -1.0F, -1.0F, 2, 1, 2, 0.0F);
         this.setRotateAngle(this.eye1, 0.13665928F, -0.5009095F, -0.091106184F);
         this.wing2b = new ModelRenderer(this, 0, 0);
         this.wing2b.setRotationPoint(0.1F, 15.2F, 0.1F);
         this.wing2b.addBox(-1.0F, 0.0F, 0.0F, 1, 16, 2, 0.0F);
         this.setRotateAngle(this.wing2b, -0.5009095F, 0.0F, 0.0F);
         this.wing1y = new ModelRendererGlow(this, 44, 6, 70, true);
         this.wing1y.setRotationPoint(0.5F, -5.0F, 2.0F);
         this.wing1y.addBox(0.0F, 0.0F, 0.0F, 0, 20, 10, 0.0F);
         this.wing1x = new ModelRendererGlow(this, 44, -10, 70, true);
         this.wing1x.setRotationPoint(0.5F, -1.0F, 2.0F);
         this.wing1x.addBox(0.0F, 0.0F, 0.0F, 0, 16, 10, 0.0F);
         this.wing2y = new ModelRendererGlow(this, 44, 6, 70, true);
         this.wing2y.mirror = true;
         this.wing2y.setRotationPoint(0.5F, -5.0F, 2.0F);
         this.wing2y.addBox(-1.0F, 0.0F, 0.0F, 0, 20, 10, 0.0F);
         this.decor4 = new ModelRenderer(this, 38, 52);
         this.decor4.setRotationPoint(0.0F, -0.5F, -0.9F);
         this.decor4.addBox(-3.5F, -1.0F, -1.0F, 7, 6, 6, 0.0F);
         this.setRotateAngle(this.decor4, 0.4098033F, 0.0F, 0.0F);
         this.head4 = new ModelRenderer(this, 4, 54);
         this.head4.setRotationPoint(0.0F, 0.5F, 0.0F);
         this.head4.addBox(-3.0F, 1.0F, -7.0F, 6, 3, 7, 0.0F);
         this.setRotateAngle(this.head4, 0.045553092F, 0.0F, 0.0F);
         this.wing1b = new ModelRenderer(this, 0, 0);
         this.wing1b.mirror = true;
         this.wing1b.setRotationPoint(-0.1F, 15.2F, 0.1F);
         this.wing1b.addBox(0.0F, 0.0F, 0.0F, 1, 16, 2, 0.0F);
         this.setRotateAngle(this.wing1b, -0.5009095F, 0.0F, 0.0F);
         this.decor2 = new ModelRenderer(this, 8, -5);
         this.decor2.setRotationPoint(0.0F, -0.5F, -2.0F);
         this.decor2.addBox(0.0F, -4.3F, 0.0F, 0, 5, 11, 0.0F);
         this.eye2 = new ModelRendererGlow(this, 8, 0, 240, false);
         this.eye2.mirror = true;
         this.eye2.setRotationPoint(-1.6F, 1.0F, -4.0F);
         this.eye2.addBox(-2.0F, -1.0F, -1.0F, 2, 1, 2, 0.0F);
         this.setRotateAngle(this.eye2, 0.13665928F, 0.5009095F, 0.091106184F);
         this.shape4 = new ModelRenderer(this, 24, 27);
         this.shape4.setRotationPoint(0.0F, -0.2F, 6.6F);
         this.shape4.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 7, 0.0F);
         this.wing2a = new ModelRenderer(this, 0, 43);
         this.wing2a.setRotationPoint(-3.0F, 1.5F, -1.0F);
         this.wing2a.addBox(-1.0F, 0.0F, 0.0F, 1, 16, 2, 0.0F);
         this.setRotateAngle(this.wing2a, 0.091106184F, 0.0F, 1.5934856F);
         this.wing2x = new ModelRendererGlow(this, 44, -10, 70, true);
         this.wing2x.mirror = true;
         this.wing2x.setRotationPoint(0.5F, -1.0F, 2.0F);
         this.wing2x.addBox(-1.0F, 0.0F, 0.0F, 0, 16, 10, 0.0F);
         this.head3 = new ModelRenderer(this, 23, 49);
         this.head3.setRotationPoint(0.0F, -1.0F, -6.5F);
         this.head3.addBox(-2.0F, -1.0F, -6.0F, 4, 3, 6, 0.0F);
         this.decor1 = new ModelRenderer(this, 17, -13);
         this.decor1.setRotationPoint(0.0F, -1.0F, -2.0F);
         this.decor1.addBox(0.0F, -4.3F, 0.0F, 0, 5, 13, 0.0F);
         this.wing1c = new ModelRenderer(this, 6, 32);
         this.wing1c.mirror = true;
         this.wing1c.setRotationPoint(-0.1F, 16.0F, 0.1F);
         this.wing1c.addBox(0.0F, 0.0F, 0.0F, 1, 16, 1, 0.0F);
         this.setRotateAngle(this.wing1c, 1.5934856F, 0.0F, 0.0F);
         this.wing2c = new ModelRenderer(this, 6, 32);
         this.wing2c.setRotationPoint(0.1F, 16.0F, 0.1F);
         this.wing2c.addBox(-1.0F, 0.0F, 0.0F, 1, 16, 1, 0.0F);
         this.setRotateAngle(this.wing2c, 1.5934856F, 0.0F, 0.0F);
         this.head2 = new ModelRenderer(this, 10, 32);
         this.head2.setRotationPoint(0.0F, 0.2F, -6.5F);
         this.head2.addBox(-1.5F, -1.0F, -7.0F, 3, 4, 7, 0.0F);
         this.setRotateAngle(this.head2, -0.091106184F, 0.0F, 0.0F);
         this.shape3 = new ModelRenderer(this, 33, 29);
         this.shape3.setRotationPoint(0.0F, 0.2F, 6.8F);
         this.shape3.addBox(-1.0F, -0.9F, 0.0F, 2, 2, 7, 0.0F);
         this.wing1a = new ModelRenderer(this, 0, 43);
         this.wing1a.mirror = true;
         this.wing1a.setRotationPoint(3.0F, 1.5F, -1.0F);
         this.wing1a.addBox(0.0F, 0.0F, 0.0F, 1, 16, 2, 0.0F);
         this.setRotateAngle(this.wing1a, 0.091106184F, 0.0F, -1.5934856F);
         this.shape2 = new ModelRenderer(this, 24, 6);
         this.shape2.setRotationPoint(0.0F, 0.2F, 6.8F);
         this.shape2.addBox(-1.5F, -0.9F, 0.0F, 3, 3, 7, 0.0F);
         this.shape1 = new ModelRenderer(this, 22, 16);
         this.shape1.setRotationPoint(0.0F, 0.5F, 6.5F);
         this.shape1.addBox(-2.0F, -1.0F, 0.0F, 4, 4, 7, 0.0F);
         this.wing2d = new ModelRenderer(this, 0, 32);
         this.wing2d.setRotationPoint(0.1F, 16.0F, 0.1F);
         this.wing2d.addBox(-1.0F, 0.0F, 0.0F, 1, 9, 1, 0.0F);
         this.setRotateAngle(this.wing2d, 0.4553564F, 0.0F, 0.0F);
         this.wing1c.addChild(this.wing1d);
         this.head3.addChild(this.decor3);
         this.head3.addChild(this.eye1);
         this.wing2a.addChild(this.wing2b);
         this.wing1b.addChild(this.wing1y);
         this.wing1a.addChild(this.wing1x);
         this.wing2b.addChild(this.wing2y);
         this.head3.addChild(this.decor4);
         this.head3.addChild(this.head4);
         this.wing1a.addChild(this.wing1b);
         this.shape4.addChild(this.decor2);
         this.head3.addChild(this.eye2);
         this.shape3.addChild(this.shape4);
         this.body.addChild(this.wing2a);
         this.wing2a.addChild(this.wing2x);
         this.head2.addChild(this.head3);
         this.body.addChild(this.decor1);
         this.wing1b.addChild(this.wing1c);
         this.wing2b.addChild(this.wing2c);
         this.head1.addChild(this.head2);
         this.shape2.addChild(this.shape3);
         this.body.addChild(this.wing1a);
         this.shape1.addChild(this.shape2);
         this.body.addChild(this.shape1);
         this.wing2c.addChild(this.wing2d);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         boolean blend = false;
         if (entity.noClip) {
            GL11.glEnable(3042);
            blend = true;
         }

         float ydispl = -0.7F;
         f5 *= 1.5F;
         float angYaw = f3 * 0.25F;
         float angPitch = f4 * 0.25F;
         float head1x = 0.18203785F;
         float head2x = -0.091106184F;
         float head3x = 0.0F;
         this.head1.rotateAngleY = angYaw * (float) (Math.PI / 180.0);
         this.head1.rotateAngleX = head1x + angPitch * (float) (Math.PI / 180.0);
         this.head2.rotateAngleY = angYaw * (float) (Math.PI / 180.0);
         this.head2.rotateAngleX = head2x + angPitch * (float) (Math.PI / 180.0);
         this.head3.rotateAngleY = angYaw * (float) (Math.PI / 180.0);
         this.head3.rotateAngleX = angPitch * (float) (Math.PI / 180.0);
         this.decor3.rotateAngleY = this.head3.rotateAngleY * -0.7F;
         this.decor4.rotateAngleY = this.head3.rotateAngleY * -0.5F;
         float breath = Math.min(
            MathHelper.sin(MathHelper.clamp(100 - an2 + Minecraft.getMinecraft().getRenderPartialTicks(), 0.0F, 60.0F) / 19.0F), 0.9F
         );
         this.head4.rotateAngleX = 0.045553092F + breath;
         this.head3.rotateAngleX -= breath / 10.0F;
         if (isAbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            if (mob.var3 > 0.0F) {
               this.body.rotateAngleX = -0.93F * mob.var3;
               this.wing1a.rotateAngleY = 0.6F * mob.var3;
               this.wing2a.rotateAngleY = -0.6F * mob.var3;
               this.shape1.rotateAngleX = this.body.rotateAngleX * -0.2F;
               this.shape2.rotateAngleX = this.shape1.rotateAngleX;
               this.shape3.rotateAngleX = this.shape1.rotateAngleX;
               this.shape4.rotateAngleX = this.shape1.rotateAngleX;
            } else {
               this.body.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            }

            float cx = (100 - an4 + Minecraft.getMinecraft().getRenderPartialTicks()) / (3.3F - mob.var3 * 1.5F);
            float add = (float)this.getWingAngle1(cx) * 0.2F;
            float add2 = (float)this.getWingAngle2(cx) * 0.2F;
            this.wing1a.rotateAngleZ = -1.5934856F - add + this.head3.rotateAngleY;
            this.wing1b.rotateAngleY = -add;
            this.wing1c.rotateAngleX = 1.5934856F + (add2 - add) / 7.0F;
            this.wing2a.rotateAngleZ = 1.5934856F + add + this.head3.rotateAngleY;
            this.wing2b.rotateAngleY = add;
            this.wing2c.rotateAngleX = 1.5934856F + (add2 - add) / 7.0F;
            this.wing1a.rotateAngleX = 0.091106184F + (add2 - add) * 2.0F;
            this.wing1b.rotateAngleX = -0.5009095F - add2 * 2.0F;
            this.wing2a.rotateAngleX = 0.091106184F + (add2 - add) * 2.0F;
            this.wing2b.rotateAngleX = -0.5009095F - add2 * 2.0F;
            ydispl = (float)(ydispl + add * (2.0F - mob.var3) * 0.5);
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, ydispl, 0.0F);
         this.head1.render(f5);
         this.body.render(f5);
         if (an3 > 40) {
            this.renderStar(-0.5, 1.0, -0.5);
         }

         GlStateManager.popMatrix();
         if (blend) {
            GL11.glDisable(3042);
         }
      }

      public double getWingAngle1(float x) {
         if (x <= 2.0F) {
            float f = x - 2.0F;
            return Math.pow(2.0, -(f * f));
         } else if (x <= 6.0F) {
            float f = x - 2.0F;
            return Math.pow(1.5, -(f * f)) * 5.0 - 4.0;
         } else {
            float f = x - 6.0F;
            return Math.pow(1.2, -(f * f)) * -4.0;
         }
      }

      public double getWingAngle2(float x) {
         if (x <= 1.8) {
            float f = 1.32F * x - 1.25F;
            return Math.pow(7.0, -(f * f)) * 1.5;
         } else if (x <= 5.5) {
            float f = x - 5.5F;
            return f * f * 0.1 - 1.25;
         } else {
            float f = x - 5.5F;
            return Math.pow(1.2, -(f * f)) * -1.25;
         }
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }

      public void renderStar(double xx, double yy, double zz) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(xx, yy, zz);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         RenderHelper.disableStandardItemLighting();
         float f = AnimationTimer.tick / 700.0F;
         float fcount = 0.5F;
         float f1 = 0.0F;
         f1 = (fcount - 0.8F) / 0.2F;
         Random random = new Random(4L);
         GlStateManager.disableTexture2D();
         GlStateManager.shadeModel(7425);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         GlStateManager.disableAlpha();
         GlStateManager.enableCull();
         GlStateManager.depthMask(false);
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.5F, 0.5F, 0.5F);
         float red = 0.4F;
         float green = 0.8F;
         float blue = 1.0F;

         for (int i = 0; i < (fcount + fcount * fcount) / 2.0F * 60.0F; i++) {
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
            float f2 = (random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F) * 0.5F;
            float f3 = (random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F) * 0.5F;
            bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(0.0, 0.0, 0.0).color(255, 255, 255, (int)(255.0F * (1.0F - f1))).endVertex();
            bufferbuilder.pos(-0.866 * f3, f2, -0.5F * f3).color(red, green, blue, 0.0F).endVertex();
            bufferbuilder.pos(0.866 * f3, f2, -0.5F * f3).color(red, green, blue, 0.0F).endVertex();
            bufferbuilder.pos(0.0, f2, 1.0F * f3).color(red, green, blue, 0.0F).endVertex();
            bufferbuilder.pos(-0.866 * f3, f2, -0.5F * f3).color(red, green, blue, 0.0F).endVertex();
            tessellator.draw();
         }

         GlStateManager.popMatrix();
         GlStateManager.depthMask(true);
         GlStateManager.disableCull();
         GlStateManager.disableBlend();
         GlStateManager.shadeModel(7424);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableTexture2D();
         GlStateManager.enableAlpha();
         RenderHelper.enableStandardItemLighting();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.popMatrix();
      }
   }
}
