//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.elements.animation.AnimationChannel;
import com.Vivern.Arpg.elements.animation.AnimationDefinition;
import com.Vivern.Arpg.elements.animation.Keyframe;
import com.Vivern.Arpg.elements.animation.KeyframeAnimations;
import com.Vivern.Arpg.entity.AbstractGlyphid;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.DungeonMobsPack;
import com.Vivern.Arpg.renders.ModelRendererGlow;
import com.Vivern.Arpg.renders.ModelRendererLimited;
import com.Vivern.Arpg.renders.ModelRendererUncompiled;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelsDungeonMob {
   public static ResourceLocation overlayBeholder = new ResourceLocation("arpg:textures/beholder_model_tex_overlay.png");
   public static ResourceLocation texCrystalShield = new ResourceLocation("arpg:textures/forcefield_crystal.png");
   public static ModelSphere crystalShieldModel = new ModelSphere(1.4F, 5);

   public static class BeholderModel extends AbstractMobModel {
      public ModelRenderer body1;
      public ModelRenderer eye1;
      public ModelRenderer eye2;
      public ModelRenderer eye3;
      public ModelRenderer teye1;
      public ModelRenderer teye2;
      public ModelRenderer teye3;
      public ModelRenderer teye4;
      public ModelRenderer teye5;
      public ModelRenderer teye6;
      public ModelRenderer teye7;
      public ModelRenderer teye8;
      public ModelRenderer tentC1;
      public ModelRenderer teeth;
      public ModelRenderer tentC2;
      public ModelRenderer tentC3;
      public ModelRenderer tentE1;
      public ModelRenderer tentE2;
      public ModelRenderer tentE3;
      public ModelRenderer teyeA1;
      public ModelRenderer teyeB1;
      public ModelRenderer teyeA2;
      public ModelRenderer teyeB2;
      public ModelRenderer teyeA3;
      public ModelRenderer teyeB3;
      public ModelRenderer teyeA4;
      public ModelRenderer teyeB4;
      public ModelRenderer teyeA5;
      public ModelRenderer teyeB5;
      public ModelRenderer teyeA6;
      public ModelRenderer teyeB6;
      public ModelRenderer teyeA7;
      public ModelRenderer teyeB7;
      public ModelRenderer teyeA8;
      public ModelRenderer teyeB8;
      public ModelRenderer tentD1;
      public ModelRenderer tentD2;
      public ModelRenderer tentD3;
      public ModelRenderer tentF1;
      public ModelRenderer tentF2;
      public ModelRenderer tentF3;

      public BeholderModel() {
         int glow = 155;
         boolean addd = false;
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.tentE3 = new ModelRenderer(this, 13, 0);
         this.tentE3.setRotationPoint(2.0F, 10.0F, 4.0F);
         this.tentE3.addBox(0.0F, 0.0F, -1.0F, 1, 8, 2, 0.0F);
         this.setRotateAngle(this.tentE3, -0.27314404F, -2.6406832F, -0.091106184F);
         this.teye7 = new ModelRenderer(this, 2, 14);
         this.teye7.setRotationPoint(2.0F, -1.5F, 5.0F);
         this.teye7.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teye7, 0.4553564F, -2.2310543F, 0.22759093F);
         this.tentD2 = new ModelRenderer(this, 19, 0);
         this.tentD2.setRotationPoint(-0.5F, 8.0F, 0.0F);
         this.tentD2.addBox(-1.0F, -0.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.tentD2, -0.4553564F, 0.0F, 0.0F);
         this.tentE1 = new ModelRenderer(this, 13, 0);
         this.tentE1.setRotationPoint(3.3F, 10.0F, -1.0F);
         this.tentE1.addBox(0.0F, 0.0F, -1.0F, 1, 8, 2, 0.0F);
         this.setRotateAngle(this.tentE1, -0.27314404F, -0.4098033F, -0.18203785F);
         this.tentC1 = new ModelRenderer(this, 13, 0);
         this.tentC1.setRotationPoint(-3.3F, 10.0F, -1.0F);
         this.tentC1.addBox(-1.0F, 0.0F, -1.0F, 1, 8, 2, 0.0F);
         this.setRotateAngle(this.tentC1, -0.27314404F, 0.4098033F, 0.18203785F);
         this.teye3 = new ModelRenderer(this, 2, 14);
         this.teye3.setRotationPoint(-5.4F, 1.5F, -1.0F);
         this.teye3.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teye3, 0.045553092F, 0.0F, -1.0927507F);
         this.tentF1 = new ModelRenderer(this, 19, 0);
         this.tentF1.setRotationPoint(0.5F, 8.0F, 0.0F);
         this.tentF1.addBox(-1.0F, -0.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.tentF1, -0.4553564F, 0.0F, 0.0F);
         this.teyeB6 = new ModelRendererGlow(this, 0, 0, glow, addd);
         this.teyeB6.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeB6.addBox(-1.5F, -2.0F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.teyeB6, 0.63739425F, 0.0F, 0.0F);
         this.teye2 = new ModelRenderer(this, 2, 14);
         this.teye2.setRotationPoint(2.0F, -1.5F, -2.0F);
         this.teye2.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teye2, 0.045553092F, 0.0F, 0.27314404F);
         this.teeth = new ModelRenderer(this, 24, 33);
         this.teeth.setRotationPoint(0.0F, 10.0F, -1.8F);
         this.teeth.addBox(-4.0F, -1.5F, -5.5F, 8, 4, 7, 0.0F);
         this.setRotateAngle(this.teeth, 0.18203785F, 0.0F, 0.0F);
         this.eye3 = new ModelRenderer(this, 0, 24);
         this.eye3.setRotationPoint(0.0F, 6.0F, -6.0F);
         this.eye3.addBox(-3.5F, -1.0F, -3.5F, 7, 4, 5, 0.0F);
         this.setRotateAngle(this.eye3, 0.7285004F, 0.0F, 0.0F);
         this.tentE2 = new ModelRenderer(this, 13, 0);
         this.tentE2.setRotationPoint(3.5F, 10.0F, 2.0F);
         this.tentE2.addBox(0.0F, 0.0F, -1.0F, 1, 8, 2, 0.0F);
         this.setRotateAngle(this.tentE2, 0.0F, -1.4570009F, -0.31869712F);
         this.teyeA2 = new ModelRenderer(this, 2, 6);
         this.teyeA2.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeA2.addBox(-1.0F, -5.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teyeA2, 0.8196066F, -0.091106184F, 0.31869712F);
         this.teyeB2 = new ModelRendererGlow(this, 0, 0, glow, addd);
         this.teyeB2.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeB2.addBox(-1.5F, -2.0F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.teyeB2, 0.63739425F, 0.0F, 0.0F);
         this.teyeA7 = new ModelRenderer(this, 2, 6);
         this.teyeA7.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeA7.addBox(-1.0F, -5.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teyeA7, 0.95609134F, 0.18203785F, 0.31869712F);
         this.teyeB7 = new ModelRendererGlow(this, 0, 0, glow, addd);
         this.teyeB7.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeB7.addBox(-1.5F, -2.0F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.teyeB7, 0.68294734F, 0.0F, 0.0F);
         this.tentD1 = new ModelRenderer(this, 19, 0);
         this.tentD1.setRotationPoint(-0.5F, 8.0F, 0.0F);
         this.tentD1.addBox(-1.0F, -0.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.tentD1, -0.4553564F, 0.0F, 0.0F);
         this.teyeB5 = new ModelRendererGlow(this, 0, 0, glow, addd);
         this.teyeB5.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeB5.addBox(-1.5F, -2.0F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.teyeB5, 0.63739425F, 0.0F, 0.0F);
         this.teye1 = new ModelRenderer(this, 2, 14);
         this.teye1.setRotationPoint(-2.0F, -1.5F, 2.0F);
         this.teye1.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teye1, 0.045553092F, 0.0F, -0.27314404F);
         this.tentF3 = new ModelRenderer(this, 19, 0);
         this.tentF3.setRotationPoint(0.5F, 8.0F, 0.0F);
         this.tentF3.addBox(-1.0F, -0.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.tentF3, -0.4553564F, 0.0F, 0.0F);
         this.tentC3 = new ModelRenderer(this, 13, 0);
         this.tentC3.setRotationPoint(-2.0F, 10.0F, 4.0F);
         this.tentC3.addBox(-1.0F, 0.0F, -1.0F, 1, 8, 2, 0.0F);
         this.setRotateAngle(this.tentC3, -0.27314404F, 2.6406832F, 0.091106184F);
         this.teyeB8 = new ModelRendererGlow(this, 0, 0, glow, addd);
         this.teyeB8.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeB8.addBox(-1.5F, -2.0F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.teyeB8, 0.63739425F, 0.0F, 0.0F);
         this.teyeA1 = new ModelRenderer(this, 2, 6);
         this.teyeA1.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeA1.addBox(-1.0F, -5.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teyeA1, 0.68294734F, 0.0F, -0.13665928F);
         this.eye1 = new ModelRendererUncompiled(this, 0, 33);
         this.eye1.setRotationPoint(0.0F, 4.0F, -6.0F);
         this.eye1.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.teye8 = new ModelRenderer(this, 2, 14);
         this.teye8.setRotationPoint(-2.0F, 1.5F, 6.0F);
         this.teye8.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teye8, -1.5025539F, 0.0F, -0.27314404F);
         this.tentF2 = new ModelRenderer(this, 19, 0);
         this.tentF2.setRotationPoint(0.5F, 8.0F, 0.0F);
         this.tentF2.addBox(-1.0F, -0.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.tentF2, -0.4553564F, 0.0F, 0.0F);
         this.eye2 = new ModelRenderer(this, 24, 24);
         this.eye2.setRotationPoint(0.0F, 2.0F, -6.0F);
         this.eye2.addBox(-4.0F, -3.0F, -3.5F, 8, 4, 5, 0.0F);
         this.setRotateAngle(this.eye2, -0.7740535F, 0.0F, 0.0F);
         this.body1 = new ModelRenderer(this, 15, 0);
         this.body1.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.body1.addBox(-6.0F, -6.0F, -6.0F, 12, 12, 12, 0.0F);
         this.teyeA6 = new ModelRenderer(this, 2, 6);
         this.teyeA6.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeA6.addBox(-1.0F, -5.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teyeA6, 1.548107F, 0.68294734F, -0.13665928F);
         this.teye5 = new ModelRenderer(this, 2, 14);
         this.teye5.setRotationPoint(-5.4F, 5.5F, 5.0F);
         this.teye5.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teye5, -0.7285004F, 0.22759093F, -1.0927507F);
         this.teye4 = new ModelRenderer(this, 2, 14);
         this.teye4.setRotationPoint(5.6F, 1.5F, 1.0F);
         this.teye4.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teye4, 0.045553092F, 0.0F, 1.0927507F);
         this.teyeA8 = new ModelRenderer(this, 2, 6);
         this.teyeA8.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeA8.addBox(-1.0F, -5.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teyeA8, -1.1383038F, 0.0F, -0.13665928F);
         this.teyeA5 = new ModelRenderer(this, 2, 6);
         this.teyeA5.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeA5.addBox(-1.0F, -5.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teyeA5, 1.548107F, 0.4098033F, -0.13665928F);
         this.teyeA3 = new ModelRenderer(this, 2, 6);
         this.teyeA3.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeA3.addBox(-1.0F, -5.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teyeA3, 1.0927507F, -0.22759093F, -0.13665928F);
         this.teyeB4 = new ModelRendererGlow(this, 0, 0, glow, addd);
         this.teyeB4.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeB4.addBox(-1.5F, -2.0F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.teyeB4, 0.63739425F, 0.0F, 0.0F);
         this.teyeB1 = new ModelRendererGlow(this, 0, 0, glow, addd);
         this.teyeB1.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeB1.addBox(-1.5F, -2.0F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.teyeB1, 0.63739425F, 0.0F, 0.0F);
         this.tentC2 = new ModelRenderer(this, 13, 0);
         this.tentC2.setRotationPoint(-3.5F, 10.0F, 2.0F);
         this.tentC2.addBox(-1.0F, 0.0F, -1.0F, 1, 8, 2, 0.0F);
         this.setRotateAngle(this.tentC2, 0.0F, 1.4570009F, 0.31869712F);
         this.teyeB3 = new ModelRendererGlow(this, 0, 0, glow, addd);
         this.teyeB3.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeB3.addBox(-1.5F, -2.0F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.teyeB3, 0.63739425F, 0.0F, 0.0F);
         this.tentD3 = new ModelRenderer(this, 19, 0);
         this.tentD3.setRotationPoint(-0.5F, 8.0F, 0.0F);
         this.tentD3.addBox(-1.0F, -0.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.tentD3, -0.4553564F, 0.0F, 0.0F);
         this.teye6 = new ModelRenderer(this, 2, 14);
         this.teye6.setRotationPoint(5.6F, 5.5F, 5.0F);
         this.teye6.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teye6, -0.5462881F, -0.27314404F, 2.003289F);
         this.teyeA4 = new ModelRenderer(this, 2, 6);
         this.teyeA4.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.teyeA4.addBox(-1.0F, -5.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.teyeA4, 1.3203416F, -0.22759093F, 0.0F);
         this.tentC2.addChild(this.tentD2);
         this.tentE1.addChild(this.tentF1);
         this.teyeA6.addChild(this.teyeB6);
         this.teye2.addChild(this.teyeA2);
         this.teyeA2.addChild(this.teyeB2);
         this.teye7.addChild(this.teyeA7);
         this.teyeA7.addChild(this.teyeB7);
         this.tentC1.addChild(this.tentD1);
         this.teyeA5.addChild(this.teyeB5);
         this.tentE3.addChild(this.tentF3);
         this.teyeA8.addChild(this.teyeB8);
         this.teye1.addChild(this.teyeA1);
         this.tentE2.addChild(this.tentF2);
         this.teye6.addChild(this.teyeA6);
         this.teye8.addChild(this.teyeA8);
         this.teye5.addChild(this.teyeA5);
         this.teye3.addChild(this.teyeA3);
         this.teyeA4.addChild(this.teyeB4);
         this.teyeA1.addChild(this.teyeB1);
         this.teyeA3.addChild(this.teyeB3);
         this.tentC3.addChild(this.tentD3);
         this.teye4.addChild(this.teyeA4);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         f5 *= 1.15F;
         this.eye1.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.eye1.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.eye2.rotateAngleY = f3 * 0.010453292F;
         this.eye3.rotateAngleY = f3 * 0.010453292F;
         this.eye2.rotateAngleX = -0.7740535F;
         this.eye3.rotateAngleX = 0.7285004F;
         float max1 = (float) (Math.PI / 10);
         float max2 = 0.7740535F;
         float addshoot = 0.0F;
         if (an3 > 0) {
            float pt = Minecraft.getMinecraft().getRenderPartialTicks();
            addshoot = GetMOP.getfromto(100 - an3 + pt, 0.0F, 20.0F) - GetMOP.getfromto(100 - an3 + pt, 50.0F, 70.0F);
         }

         float noshoot = 1.0F - addshoot;
         if (an2 > 0) {
            float pt = Minecraft.getMinecraft().getRenderPartialTicks();
            float add = (GetMOP.getfromto(100 - an2 + pt, 0.0F, 8.0F) - GetMOP.getfromto(100 - an2 + pt, 12.0F, 20.0F)) * noshoot;
            this.eye2.rotateAngleX = MathHelper.clamp(this.eye2.rotateAngleX + 1.012291F * add, -max2, max1);
            this.eye3.rotateAngleX = MathHelper.clamp(this.eye3.rotateAngleX + -0.9773844F * add, -max1, max2);
         }

         if (an4 > 0) {
            float pt = Minecraft.getMinecraft().getRenderPartialTicks();
            float add = (GetMOP.getfromto(100 - an4 + pt, 0.0F, 14.0F) - GetMOP.getfromto(100 - an4 + pt, 74.0F, 88.0F)) * noshoot;
            this.eye2.rotateAngleX = MathHelper.clamp(this.eye2.rotateAngleX + 0.8552113F * add, -max2, max1);
            this.eye3.rotateAngleX = MathHelper.clamp(this.eye3.rotateAngleX + -0.4886922F * add, -max1, max2);
         }

         this.teeth.rotateAngleX = 0.18203785F + (float) (Math.PI / 6) * addshoot;
         float addattack = 0.0F;
         if (an1 > 0) {
            float pt = Minecraft.getMinecraft().getRenderPartialTicks();
            addattack = GetMOP.getfromto(100 - an1 + pt, 0.0F, 8.0F) - GetMOP.getfromto(100 - an1 + pt, 20.0F, 34.0F);
         }

         this.setRotateAngle(this.tentE3, -0.27314404F, -2.6406832F, -0.091106184F);
         this.setRotateAngle(this.teye7, 0.4553564F, -2.2310543F, 0.22759093F);
         this.setRotateAngle(this.tentD2, -0.4553564F, 0.0F, 0.0F);
         this.setRotateAngle(this.tentE1, -0.27314404F, -0.4098033F, -0.18203785F);
         this.setRotateAngle(this.tentC1, -0.27314404F, 0.4098033F, 0.18203785F);
         this.setRotateAngle(this.teye3, 0.045553092F, 0.0F, -1.0927507F);
         this.setRotateAngle(this.tentF1, -0.4553564F, 0.0F, 0.0F);
         this.setRotateAngle(this.teyeB6, 0.63739425F, 0.0F, 0.0F);
         this.setRotateAngle(this.teye2, 0.045553092F, 0.0F, 0.27314404F);
         this.setRotateAngle(this.tentE2, 0.0F, -1.4570009F, -0.31869712F);
         this.setRotateAngle(this.teyeA2, 0.8196066F, -0.091106184F, 0.31869712F);
         this.setRotateAngle(this.teyeB2, 0.63739425F, 0.0F, 0.0F);
         this.setRotateAngle(this.teyeA7, 0.95609134F, 0.18203785F, 0.31869712F);
         this.setRotateAngle(this.teyeB7, 0.68294734F, 0.0F, 0.0F);
         this.setRotateAngle(this.tentD1, -0.4553564F, 0.0F, 0.0F);
         this.setRotateAngle(this.teyeB5, 0.63739425F, 0.0F, 0.0F);
         this.setRotateAngle(this.teye1, 0.045553092F, 0.0F, -0.27314404F);
         this.setRotateAngle(this.tentF3, -0.4553564F, 0.0F, 0.0F);
         this.setRotateAngle(this.tentC3, -0.27314404F, 2.6406832F, 0.091106184F);
         this.setRotateAngle(this.teyeB8, 0.63739425F, 0.0F, 0.0F);
         this.setRotateAngle(this.teyeA1, 0.68294734F, 0.0F, -0.13665928F);
         this.setRotateAngle(this.teye8, -1.5025539F, 0.0F, -0.27314404F);
         this.setRotateAngle(this.tentF2, -0.4553564F, 0.0F, 0.0F);
         this.setRotateAngle(this.teyeA6, 1.548107F, 0.68294734F, -0.13665928F);
         this.setRotateAngle(this.teye5, -0.7285004F, 0.22759093F, -1.0927507F);
         this.setRotateAngle(this.teye4, 0.045553092F, 0.0F, 1.0927507F);
         this.setRotateAngle(this.teyeA8, -1.1383038F, 0.0F, -0.13665928F);
         this.setRotateAngle(this.teyeA5, 1.548107F, 0.4098033F, -0.13665928F);
         this.setRotateAngle(this.teyeA3, 1.0927507F, -0.22759093F, -0.13665928F);
         this.setRotateAngle(this.teyeB4, 0.63739425F, 0.0F, 0.0F);
         this.setRotateAngle(this.teyeB1, 0.63739425F, 0.0F, 0.0F);
         this.setRotateAngle(this.tentC2, 0.0F, 1.4570009F, 0.31869712F);
         this.setRotateAngle(this.teyeB3, 0.63739425F, 0.0F, 0.0F);
         this.setRotateAngle(this.tentD3, -0.4553564F, 0.0F, 0.0F);
         this.setRotateAngle(this.teye6, -0.5462881F, -0.27314404F, 2.003289F);
         this.setRotateAngle(this.teyeA4, 1.3203416F, -0.22759093F, 0.0F);
         this.tentC1.rotateAngleX += (float) (-Math.PI * 2.0 / 9.0) * addattack;
         this.tentC2.rotateAngleX += (float) (-Math.PI * 2.0 / 9.0) * addattack;
         this.tentC3.rotateAngleX += (float) (-Math.PI * 2.0 / 9.0) * addattack;
         this.tentD1.rotateAngleX += (float) (-Math.PI / 3) * addattack;
         this.tentD2.rotateAngleX += (float) (-Math.PI / 3) * addattack;
         this.tentD3.rotateAngleX += (float) (-Math.PI / 3) * addattack;
         this.tentE1.rotateAngleX += (float) (-Math.PI * 2.0 / 9.0) * addattack;
         this.tentE2.rotateAngleX += (float) (-Math.PI * 2.0 / 9.0) * addattack;
         this.tentE3.rotateAngleX += (float) (-Math.PI * 2.0 / 9.0) * addattack;
         this.tentF1.rotateAngleX += (float) (-Math.PI / 3) * addattack;
         this.tentF2.rotateAngleX += (float) (-Math.PI / 3) * addattack;
         this.tentF3.rotateAngleX += (float) (-Math.PI / 3) * addattack;
         int i = 0;
         this.tentC1.rotateAngleX = this.tentC1.rotateAngleX + MathHelper.sin(-AnimationTimer.tick / 40.0F + i * 1.678F) * 0.2F;
         this.tentD1.rotateAngleX = this.tentD1.rotateAngleX + MathHelper.sin((-AnimationTimer.tick + 30) / 40.0F + i++ * 1.678F) * 0.2F;
         this.tentC2.rotateAngleX = this.tentC2.rotateAngleX + MathHelper.sin(-AnimationTimer.tick / 40.0F + i * 1.678F) * 0.2F;
         this.tentD2.rotateAngleX = this.tentD2.rotateAngleX + MathHelper.sin((-AnimationTimer.tick + 30) / 40.0F + i++ * 1.678F) * 0.2F;
         this.tentC3.rotateAngleX = this.tentC3.rotateAngleX + MathHelper.sin(-AnimationTimer.tick / 40.0F + i * 1.678F) * 0.2F;
         this.tentD3.rotateAngleX = this.tentD3.rotateAngleX + MathHelper.sin((-AnimationTimer.tick + 30) / 40.0F + i++ * 1.678F) * 0.2F;
         this.tentE1.rotateAngleX = this.tentE1.rotateAngleX + MathHelper.sin(-AnimationTimer.tick / 40.0F + i * 1.678F) * 0.2F;
         this.tentF1.rotateAngleX = this.tentF1.rotateAngleX + MathHelper.sin((-AnimationTimer.tick + 30) / 40.0F + i++ * 1.678F) * 0.2F;
         this.tentE2.rotateAngleX = this.tentE2.rotateAngleX + MathHelper.sin(-AnimationTimer.tick / 40.0F + i * 1.678F) * 0.2F;
         this.tentF2.rotateAngleX = this.tentF2.rotateAngleX + MathHelper.sin((-AnimationTimer.tick + 30) / 40.0F + i++ * 1.678F) * 0.2F;
         this.tentE3.rotateAngleX = this.tentE3.rotateAngleX + MathHelper.sin(-AnimationTimer.tick / 40.0F + i * 1.678F) * 0.2F;
         this.tentF3.rotateAngleX = this.tentF3.rotateAngleX + MathHelper.sin((-AnimationTimer.tick + 30) / 40.0F + i++ * 1.678F) * 0.2F;
         this.setTeye(this.teye1, this.teyeA1, this.teyeB1, i++);
         this.setTeye(this.teye2, this.teyeA2, this.teyeB2, i++);
         this.setTeye(this.teye3, this.teyeA3, this.teyeB3, i++);
         this.setTeye(this.teye4, this.teyeA4, this.teyeB4, i++);
         this.setTeye(this.teye5, this.teyeA5, this.teyeB5, i++);
         this.setTeye(this.teye6, this.teyeA6, this.teyeB6, i++);
         this.setTeye(this.teye7, this.teyeA7, this.teyeB7, i++);
         this.setTeye(this.teye8, this.teyeA8, this.teyeB8, i++);
         this.teye7.render(f5);
         this.teye3.render(f5);
         this.teye2.render(f5);
         this.teeth.render(f5);
         this.eye3.render(f5);
         this.teye1.render(f5);
         this.teye8.render(f5);
         this.eye2.render(f5);
         this.body1.render(f5);
         this.teye5.render(f5);
         this.teye4.render(f5);
         this.teye6.render(f5);
         this.tentC1.render(f5);
         this.tentC2.render(f5);
         this.tentC3.render(f5);
         this.tentE1.render(f5);
         this.tentE2.render(f5);
         this.tentE3.render(f5);
         light(145 + (int)(addshoot * 95.0F), false);
         this.eye1.render(f5);
         returnlight();
         if (addattack > 0.0F) {
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(ModelsDungeonMob.overlayBeholder);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.color(addattack, addattack, addattack, 1.0F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.teye7.render(f5);
            this.teye3.render(f5);
            this.teye2.render(f5);
            this.teye1.render(f5);
            this.teye8.render(f5);
            this.body1.render(f5);
            this.teye5.render(f5);
            this.teye4.render(f5);
            this.teye6.render(f5);
            this.tentC1.render(f5);
            this.tentC2.render(f5);
            this.tentC3.render(f5);
            this.tentE1.render(f5);
            this.tentE2.render(f5);
            this.tentE3.render(f5);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }
      }

      public void setTeye(ModelRenderer teye, ModelRenderer teyeA, ModelRenderer teyeB, int num) {
         teye.rotateAngleX = teye.rotateAngleX + MathHelper.sin(-AnimationTimer.tick / 30.0F + num * 1.6378F) * 0.2F;
         teye.rotateAngleY = teye.rotateAngleY + MathHelper.sin(-(AnimationTimer.tick + 15) / 30.0F + num * 1.6378F) * 0.2F;
         teyeA.rotateAngleX = teyeA.rotateAngleX + MathHelper.sin(-(AnimationTimer.tick + 30) / 30.0F + num * 1.6378F) * 0.2F;
         teyeA.rotateAngleY = teyeA.rotateAngleY + MathHelper.sin(-(AnimationTimer.tick + 45) / 30.0F + num * 1.6378F) * 0.2F;
         teyeB.rotateAngleX = teyeB.rotateAngleX
            + MathHelper.clamp(MathHelper.sin(-(AnimationTimer.tick + 60) / 66.0F + num * 3.4378F) * 3.5F, -0.46F, 0.46F);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class DegModel extends ModelBase {
      public ModelRenderer body0;
      public ModelRenderer body0_1;
      public ModelRenderer leg4;
      public ModelRenderer leg21;
      public ModelRenderer tail;
      public ModelRenderer body0_2;
      public ModelRenderer head1;
      public ModelRenderer leg3;
      public ModelRenderer leg11;
      public ModelRenderer body0_3;
      public ModelRenderer body0_4;
      public ModelRenderer leg4a;
      public ModelRenderer leg22;
      public ModelRenderer leg23;
      public ModelRenderer head2;
      public ModelRenderer head3;
      public ModelRenderer head4;
      public ModelRenderer leg3a;
      public ModelRenderer leg12;
      public ModelRenderer leg13;

      public DegModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.leg22 = new ModelRenderer(this, 49, 15);
         this.leg22.setRotationPoint(0.0F, 7.4F, -1.0F);
         this.leg22.addBox(-0.5F, 0.0F, -1.0F, 2, 7, 3, 0.0F);
         this.setRotateAngle(this.leg22, 1.1838568F, 0.0F, 0.0F);
         this.leg13 = new ModelRenderer(this, 0, 14);
         this.leg13.setRotationPoint(0.0F, 6.4F, 0.0F);
         this.leg13.addBox(-1.0F, 0.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.leg13, -1.1383038F, 0.0F, 0.0F);
         this.leg11 = new ModelRenderer(this, 21, 11);
         this.leg11.setRotationPoint(-3.0F, 7.0F, 7.0F);
         this.leg11.addBox(-2.0F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
         this.setRotateAngle(this.leg11, -0.13665928F, 0.0F, 0.0F);
         this.body0_2 = new ModelRenderer(this, 42, 0);
         this.body0_2.setRotationPoint(0.0F, 8.0F, -8.5F);
         this.body0_2.addBox(-2.0F, -3.0F, -6.0F, 4, 6, 7, 0.0F);
         this.setRotateAngle(this.body0_2, 0.5462881F, 0.0F, 0.0F);
         this.leg4 = new ModelRenderer(this, 0, 0);
         this.leg4.setRotationPoint(4.0F, 7.0F, -6.0F);
         this.leg4.addBox(-1.0F, 0.0F, -2.0F, 2, 10, 4, 0.0F);
         this.setRotateAngle(this.leg4, 0.31869712F, 0.0F, 0.0F);
         this.head3 = new ModelRenderer(this, 32, -5);
         this.head3.setRotationPoint(0.0F, 0.8F, -5.0F);
         this.head3.addBox(0.0F, -0.5F, -4.0F, 0, 1, 5, 0.0F);
         this.setRotateAngle(this.head3, -0.5462881F, 0.0F, 0.0F);
         this.head2 = new ModelRenderer(this, 37, 0);
         this.head2.setRotationPoint(0.0F, -2.8F, -6.0F);
         this.head2.addBox(-0.5F, 0.0F, -5.0F, 1, 2, 5, 0.0F);
         this.setRotateAngle(this.head2, 0.4553564F, 0.0F, 0.0F);
         this.body0_1 = new ModelRenderer(this, 30, 16);
         this.body0_1.setRotationPoint(0.0F, 8.0F, 4.0F);
         this.body0_1.addBox(-2.0F, -3.0F, -5.0F, 4, 5, 11, 0.0F);
         this.setRotateAngle(this.body0_1, -0.13665928F, 0.0F, 0.0F);
         this.head1 = new ModelRenderer(this, 12, 0);
         this.head1.setRotationPoint(0.0F, 11.0F, -13.0F);
         this.head1.addBox(-1.0F, -3.0F, -6.0F, 2, 4, 7, 0.0F);
         this.setRotateAngle(this.head1, 1.0016445F, 0.0F, 0.0F);
         this.head4 = new ModelRenderer(this, 8, -4);
         this.head4.setRotationPoint(0.0F, 1.1F, -4.5F);
         this.head4.addBox(0.0F, -2.0F, -2.0F, 0, 4, 4, 0.0F);
         this.setRotateAngle(this.head4, 0.0F, (float) (Math.PI / 2), 1.5934856F);
         this.body0_4 = new ModelRenderer(this, 26, -5);
         this.body0_4.setRotationPoint(0.0F, 3.4F, 7.0F);
         this.body0_4.addBox(0.0F, -0.5F, -4.0F, 0, 3, 7, 0.0F);
         this.setRotateAngle(this.body0_4, -0.27314404F, 0.0F, 0.0F);
         this.leg4a = new ModelRenderer(this, 35, 13);
         this.leg4a.setRotationPoint(0.0F, 9.0F, 1.0F);
         this.leg4a.addBox(-0.5F, 0.0F, -1.0F, 1, 10, 2, 0.0F);
         this.setRotateAngle(this.leg4a, -0.8196066F, 0.0F, 0.0F);
         this.body0 = new ModelRenderer(this, 0, 14);
         this.body0.setRotationPoint(0.0F, 7.0F, -4.0F);
         this.body0.addBox(-3.0F, -3.0F, -5.0F, 6, 9, 9, 0.0F);
         this.setRotateAngle(this.body0, 0.13665928F, 0.0F, 0.0F);
         this.tail = new ModelRenderer(this, 32, 8);
         this.tail.setRotationPoint(0.0F, 7.0F, 10.0F);
         this.tail.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 4, 0.0F);
         this.setRotateAngle(this.tail, -0.27314404F, 0.0F, 0.0F);
         this.body0_3 = new ModelRenderer(this, 24, -8);
         this.body0_3.setRotationPoint(0.0F, 1.1F, -5.0F);
         this.body0_3.addBox(0.0F, -0.5F, -4.0F, 0, 4, 9, 0.0F);
         this.setRotateAngle(this.body0_3, 0.13665928F, 0.0F, 0.0F);
         this.leg3 = new ModelRenderer(this, 0, 0);
         this.leg3.setRotationPoint(-4.0F, 7.0F, -6.0F);
         this.leg3.addBox(-1.0F, 0.0F, -2.0F, 2, 10, 4, 0.0F);
         this.setRotateAngle(this.leg3, 0.31869712F, 0.0F, 0.0F);
         this.leg3a = new ModelRenderer(this, 35, 13);
         this.leg3a.setRotationPoint(0.0F, 9.0F, 1.0F);
         this.leg3a.addBox(-0.5F, 0.0F, -1.0F, 1, 10, 2, 0.0F);
         this.setRotateAngle(this.leg3a, -0.8196066F, 0.0F, 0.0F);
         this.leg21 = new ModelRenderer(this, 21, 11);
         this.leg21.setRotationPoint(3.0F, 7.0F, 7.0F);
         this.leg21.addBox(-1.0F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
         this.setRotateAngle(this.leg21, -0.13665928F, 0.0F, 0.0F);
         this.leg23 = new ModelRenderer(this, 0, 14);
         this.leg23.setRotationPoint(0.0F, 6.4F, 0.0F);
         this.leg23.addBox(0.0F, 0.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.leg23, -1.1383038F, 0.0F, 0.0F);
         this.leg12 = new ModelRenderer(this, 49, 15);
         this.leg12.setRotationPoint(0.0F, 7.4F, -1.0F);
         this.leg12.addBox(-1.5F, 0.0F, -1.0F, 2, 7, 3, 0.0F);
         this.setRotateAngle(this.leg12, 1.1838568F, 0.0F, 0.0F);
         this.leg21.addChild(this.leg22);
         this.leg12.addChild(this.leg13);
         this.head2.addChild(this.head3);
         this.head1.addChild(this.head2);
         this.head2.addChild(this.head4);
         this.leg4.addChild(this.leg4a);
         this.leg3.addChild(this.leg3a);
         this.leg22.addChild(this.leg23);
         this.leg11.addChild(this.leg12);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         this.leg11.render(f5);
         this.body0_2.render(f5);
         this.leg4.render(f5);
         this.body0_1.render(f5);
         this.head1.render(f5);
         this.body0_4.render(f5);
         this.body0.render(f5);
         this.tail.render(f5);
         this.body0_3.render(f5);
         this.leg3.render(f5);
         this.leg21.render(f5);
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.head1.rotateAngleX = 1.0016445F + headPitch * 0.010453292F;
         this.head1.rotateAngleY = netHeadYaw * 0.010453292F;
         this.head2.rotateAngleX = 0.4553564F + headPitch * 0.004453292F;
         this.head2.rotateAngleY = netHeadYaw * 0.004453292F;
         this.leg11.rotateAngleX = -0.13665928F;
         this.leg12.rotateAngleX = 1.1838568F;
         this.leg13.rotateAngleX = -1.1383038F;
         this.leg21.rotateAngleX = -0.13665928F;
         this.leg22.rotateAngleX = 1.1838568F;
         this.leg23.rotateAngleX = -1.1383038F;
         float angl1L = MathHelper.cos(-limbSwing * 0.6662F) * -0.3F * limbSwingAmount;
         float angl2L = MathHelper.sin(-limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
         float angl1R = MathHelper.cos(-limbSwing * 0.6662F + (float) Math.PI) * -0.3F * limbSwingAmount;
         float angl2R = MathHelper.sin(-limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
         this.leg3.rotateAngleX = 0.31869712F + MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount;
         this.leg4.rotateAngleX = 0.31869712F + MathHelper.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount;
         this.leg21.rotateAngleX -= angl2L;
         this.leg22.rotateAngleX += angl1L;
         this.leg23.rotateAngleX -= angl1L;
         this.leg11.rotateAngleX -= angl2R;
         this.leg12.rotateAngleX += angl1R;
         this.leg13.rotateAngleX -= angl1R;
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class DevourerModel extends AbstractMobModel {
      public ModelRenderer body1;
      public ModelRenderer body2;
      public ModelRenderer body0;
      public ModelRenderer head1;
      public ModelRenderer spike1;
      public ModelRenderer spike2;
      public ModelRenderer spike3;
      public ModelRenderer spike4;
      public ModelRenderer leg3;
      public ModelRenderer leg4;
      public ModelRenderer leg2;
      public ModelRenderer leg1;
      public ModelRenderer tail1;
      public ModelRenderer tail2;
      public ModelRenderer tail3;
      public ModelRenderer tail4;
      public ModelRenderer head2;
      public ModelRenderer head3;
      public ModelRenderer head3_1;
      public ModelRenderer eye1;
      public ModelRenderer eye2;
      public ModelRenderer head3_2;
      public ModelRenderer leg2a;
      public ModelRenderer leg1a;

      public DevourerModel() {
         this.textureWidth = 96;
         this.textureHeight = 104;
         this.leg2 = new ModelRenderer(this, 30, 76);
         this.leg2.setRotationPoint(6.0F, 5.0F, 6.5F);
         this.leg2.addBox(-1.0F, -3.0F, -2.0F, 4, 14, 6, 0.0F);
         this.setRotateAngle(this.leg2, -0.4098033F, -0.091106184F, 0.0F);
         this.leg4 = new ModelRenderer(this, 0, 20);
         this.leg4.setRotationPoint(4.0F, 10.0F, -10.0F);
         this.leg4.addBox(-2.5F, 0.0F, -2.0F, 5, 14, 6, 0.0F);
         this.setRotateAngle(this.leg4, 0.0F, 0.0F, -0.091106184F);
         this.head1 = new ModelRenderer(this, 42, 90);
         this.head1.setRotationPoint(0.0F, 10.0F, -16.5F);
         this.head1.addBox(-3.0F, -4.0F, -7.0F, 6, 6, 8, 0.0F);
         this.head2 = new ModelRenderer(this, 0, 64);
         this.head2.setRotationPoint(0.0F, -3.0F, -7.5F);
         this.head2.addBox(-4.0F, -4.0F, -7.0F, 8, 6, 10, 0.0F);
         this.setRotateAngle(this.head2, 0.22759093F, 0.0F, 0.0F);
         this.spike1 = new ModelRenderer(this, 0, 0);
         this.spike1.setRotationPoint(-5.0F, 6.5F, -13.0F);
         this.spike1.addBox(-1.5F, -17.0F, -1.5F, 3, 17, 3, 0.0F);
         this.setRotateAngle(this.spike1, -0.4553564F, 0.0F, -0.5462881F);
         this.spike3 = new ModelRenderer(this, 0, 0);
         this.spike3.setRotationPoint(-2.0F, 6.5F, -14.0F);
         this.spike3.addBox(-1.5F, -20.5F, -1.5F, 3, 17, 3, 0.0F);
         this.setRotateAngle(this.spike3, -0.4553564F, 0.0F, -0.18203785F);
         this.tail1 = new ModelRenderer(this, 26, 24);
         this.tail1.setRotationPoint(0.0F, -9.0F, -2.0F);
         this.tail1.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
         this.setRotateAngle(this.tail1, 0.22759093F, 0.0F, 0.0F);
         this.leg3 = new ModelRenderer(this, 0, 20);
         this.leg3.setRotationPoint(-4.0F, 10.0F, -10.0F);
         this.leg3.addBox(-2.5F, 0.0F, -2.0F, 5, 14, 6, 0.0F);
         this.setRotateAngle(this.leg3, 0.0F, 0.0F, 0.091106184F);
         this.body0 = new ModelRenderer(this, 10, 42);
         this.body0.setRotationPoint(0.0F, 8.0F, 9.0F);
         this.body0.addBox(-5.0F, -10.0F, -7.0F, 10, 11, 9, 0.0F);
         this.setRotateAngle(this.body0, -1.821251F, 0.0F, 0.0F);
         this.body2 = new ModelRenderer(this, 48, 33);
         this.body2.setRotationPoint(0.0F, 7.0F, -7.0F);
         this.body2.addBox(-7.0F, -10.0F, -7.0F, 14, 20, 10, 0.0F);
         this.setRotateAngle(this.body2, -1.1383038F, 0.0F, 0.0F);
         this.leg1 = new ModelRenderer(this, 30, 76);
         this.leg1.setRotationPoint(-6.0F, 5.0F, 6.5F);
         this.leg1.addBox(-3.0F, -3.0F, -2.0F, 4, 14, 6, 0.0F);
         this.setRotateAngle(this.leg1, -0.4098033F, 0.091106184F, 0.0F);
         this.tail3 = new ModelRenderer(this, 0, 80);
         this.tail3.setRotationPoint(0.0F, -11.0F, 0.0F);
         this.tail3.addBox(-2.0F, -12.0F, -2.0F, 4, 12, 4, 0.0F);
         this.setRotateAngle(this.tail3, 0.22759093F, 0.0F, 0.0F);
         this.leg2a = new ModelRenderer(this, 50, 63);
         this.leg2a.setRotationPoint(0.5F, 11.0F, 0.0F);
         this.leg2a.addBox(-1.0F, -2.0F, -1.0F, 3, 11, 7, 0.0F);
         this.setRotateAngle(this.leg2a, 0.4098033F, 0.0F, 0.0F);
         this.spike2 = new ModelRenderer(this, 0, 0);
         this.spike2.setRotationPoint(5.0F, 6.5F, -13.0F);
         this.spike2.addBox(-1.5F, -17.0F, -1.5F, 3, 17, 3, 0.0F);
         this.setRotateAngle(this.spike2, -0.4553564F, 0.0F, 0.5462881F);
         this.tail2 = new ModelRenderer(this, 28, 5);
         this.tail2.setRotationPoint(0.0F, -9.0F, 0.0F);
         this.tail2.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
         this.setRotateAngle(this.tail2, 0.13665928F, 0.0F, 0.0F);
         this.spike4 = new ModelRenderer(this, 0, 0);
         this.spike4.setRotationPoint(2.0F, 6.5F, -14.0F);
         this.spike4.addBox(-1.5F, -20.5F, -1.5F, 3, 17, 3, 0.0F);
         this.setRotateAngle(this.spike4, -0.4553564F, 0.0F, 0.18203785F);
         this.leg1a = new ModelRenderer(this, 50, 63);
         this.leg1a.setRotationPoint(-0.5F, 11.0F, 0.0F);
         this.leg1a.addBox(-2.0F, -2.0F, -1.0F, 3, 11, 7, 0.0F);
         this.setRotateAngle(this.leg1a, 0.4098033F, 0.0F, 0.0F);
         this.head3 = new ModelRenderer(this, 61, 80);
         this.head3.setRotationPoint(0.0F, -1.6F, -7.5F);
         this.head3.addBox(-3.5F, 0.0F, -7.0F, 7, 4, 10, 0.0F);
         this.eye2 = new ModelRendererGlow(this, 12, 0, 150, false);
         this.eye2.setRotationPoint(4.0F, -2.0F, -7.0F);
         this.eye2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
         this.body1 = new ModelRenderer(this, 52, 0);
         this.body1.setRotationPoint(0.0F, 7.0F, 2.0F);
         this.body1.addBox(-6.0F, -10.0F, -7.0F, 12, 20, 10, 0.0F);
         this.setRotateAngle(this.body1, -1.5025539F, 0.0F, 0.0F);
         this.tail4 = new ModelRenderer(this, 26, 62);
         this.tail4.setRotationPoint(0.0F, -14.0F, 0.0F);
         this.tail4.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.tail4, 0.0F, 0.0F, (float) (Math.PI / 4));
         this.head3_1 = new ModelRenderer(this, 5, 85);
         this.head3_1.setRotationPoint(0.0F, -0.1F, 1.3F);
         this.head3_1.addBox(-1.0F, 1.0F, -6.0F, 2, 6, 12, 0.0F);
         this.setRotateAngle(this.head3_1, 0.13665928F, 0.0F, 0.0F);
         this.eye1 = new ModelRendererGlow(this, 12, 0, 150, false);
         this.eye1.setRotationPoint(-4.0F, -2.0F, -7.0F);
         this.eye1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
         this.head3_2 = new ModelRenderer(this, 70, 63);
         this.head3_2.setRotationPoint(0.0F, 3.0F, 2.5F);
         this.head3_2.addBox(-1.5F, 0.0F, -7.0F, 3, 7, 10, 0.0F);
         this.setRotateAngle(this.head3_2, -0.22759093F, 0.0F, 0.0F);
         this.head1.addChild(this.head2);
         this.body0.addChild(this.tail1);
         this.tail2.addChild(this.tail3);
         this.leg2.addChild(this.leg2a);
         this.tail1.addChild(this.tail2);
         this.leg1.addChild(this.leg1a);
         this.head1.addChild(this.head3);
         this.head2.addChild(this.eye2);
         this.tail3.addChild(this.tail4);
         this.head1.addChild(this.head3_1);
         this.head2.addChild(this.eye1);
         this.head3.addChild(this.head3_2);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         f5 *= 1.1F;
         int timer = 0;
         if (isAbstractMob) {
            timer = ((AbstractMob)entity).var1;
         }

         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         this.setRotateAngle(this.tail1, 0.22759093F, 0.0F, 0.0F);
         this.setRotateAngle(this.tail2, 0.13665928F, 0.0F, 0.0F);
         this.setRotateAngle(this.tail3, 0.22759093F, 0.0F, 0.0F);
         this.setRotateAngle(this.body0, -1.821251F, 0.0F, 0.0F);
         float rot = MathHelper.sin((timer + Minecraft.getMinecraft().getRenderPartialTicks()) / 30.239439F) * 0.4F;
         this.body0.rotateAngleY = rot;
         this.tail1.rotateAngleZ = rot;
         this.tail2.rotateAngleZ = rot;
         this.tail3.rotateAngleZ = rot;
         if (an2 > 0) {
            float an = 100 - an2 + Minecraft.getMinecraft().getRenderPartialTicks();
            float add = GetMOP.getfromto(an, 0.0F, 12.0F) * 0.3F - GetMOP.getfromto(an, 13.0F, 17.0F) * 0.55F + GetMOP.getfromto(an, 18.0F, 35.0F) * 0.25F;
            this.body0.rotateAngleX += add;
            this.tail1.rotateAngleX += add;
            this.tail2.rotateAngleX += add;
            this.tail3.rotateAngleX += add;
         }

         float attackmeleeAdd = 0.0F;
         if (an1 > 0) {
            float an = 100 - an1 + Minecraft.getMinecraft().getRenderPartialTicks();
            attackmeleeAdd = GetMOP.getfromto(an, 0.0F, 15.0F) - GetMOP.getfromto(an, 23.0F, 26.0F);
            this.leg1.rotateAngleX *= 1.0F - attackmeleeAdd;
            this.leg2.rotateAngleX *= 1.0F - attackmeleeAdd;
            this.leg1.rotateAngleX += (float) (Math.PI * 2.0 / 9.0) * attackmeleeAdd;
            this.leg2.rotateAngleX += (float) (Math.PI * 2.0 / 9.0) * attackmeleeAdd;
            this.head1.rotateAngleX = this.head1.rotateAngleX + 0.87266463F * (GetMOP.getfromto(an, 0.0F, 10.0F) - GetMOP.getfromto(an, 26.0F, 36.0F));
            float add2 = 0.61086524F * (GetMOP.getfromto(an, 8.0F, 15.0F) - GetMOP.getfromto(an, 23.0F, 26.0F));
            this.leg3.rotateAngleX += add2;
            this.leg4.rotateAngleX += add2;
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -0.15F, 0.0F);
         GlStateManager.translate(0.0F, 0.4F, 0.6F);
         GlStateManager.rotate(-50.0F * attackmeleeAdd, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0F, -0.4F, -0.6F);
         this.leg2.render(f5);
         this.leg4.render(f5);
         this.head1.render(f5);
         this.spike1.render(f5);
         this.spike3.render(f5);
         this.leg3.render(f5);
         this.body0.render(f5);
         this.body2.render(f5);
         this.leg1.render(f5);
         this.spike2.render(f5);
         this.spike4.render(f5);
         this.body1.render(f5);
         if (an4 > 85) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            float an = 100 - an4 + Minecraft.getMinecraft().getRenderPartialTicks();
            float col = GetMOP.getfromto(an, 0.0F, 10.0F) - GetMOP.getfromto(an, 11.0F, 14.0F);
            float siz = 1.15F - GetMOP.getfromto(an, 0.0F, 10.0F) * 0.145F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(siz, siz, siz);
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.color(col * 0.82F, col * 0.34F, col * 0.18F, 1.0F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.leg2.render(f5);
            this.leg4.render(f5);
            this.head1.render(f5);
            this.spike1.render(f5);
            this.spike3.render(f5);
            this.leg3.render(f5);
            this.body0.render(f5);
            this.body2.render(f5);
            this.leg1.render(f5);
            this.spike2.render(f5);
            this.spike4.render(f5);
            this.body1.render(f5);
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

         GlStateManager.popMatrix();
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.head1.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
         this.head1.rotateAngleY = MathHelper.clamp(netHeadYaw * 0.010453292F, -0.78F, 0.78F);
         this.head2.rotateAngleY = MathHelper.clamp(netHeadYaw * 0.007453292F, -0.35F, 0.35F);
         this.head3.rotateAngleY = MathHelper.clamp(netHeadYaw * 0.007453292F, -0.35F, 0.35F);
         this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount;
         this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount;
         this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount;
         this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount;
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class LarvaFlyerModel extends AbstractMobModel {
      private final ModelRenderer bone;
      private final ModelRenderer head;
      private final ModelRenderer cube_r1;
      private final ModelRenderer gloweyes;
      private final ModelRenderer bone2;
      private final ModelRenderer bone3;
      private final ModelRenderer bone4;
      private final ModelRendererGlow glowBody;
      private final ModelRendererGlow larva1;
      private final ModelRendererGlow larva2;
      private final ModelRendererGlow larva3;
      private final ModelRendererGlow larva4;
      private final ModelRendererGlow larva5;
      private final ModelRenderer wing1;
      private final ModelRenderer wing4;
      private final ModelRenderer wing2;
      private final ModelRenderer wing3;
      public AnimationDefinition flying;
      public AnimationDefinition breed;
      public AnimationDefinition ymotion;
      public AnimationDefinition attack;

      public LarvaFlyerModel() {
         this.textureWidth = 128;
         this.textureHeight = 64;
         this.bone = new ModelRenderer(this);
         this.bone.setRotationPoint(0.0F, -5.0F, 1.0F);
         this.setRotationAngle(this.bone, 0.6545F, 0.0F, 0.0F);
         this.bone.cubeList.add(new ModelBox(this.bone, 32, 8, -4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F, false));
         this.bone.cubeList.add(new ModelBox(this.bone, 0, 24, -5.0F, -3.0F, -8.1F, 10, 6, 4, 0.0F, false));
         this.head = new ModelRenderer(this);
         this.head.setRotationPoint(0.0F, -4.0F, 0.0F);
         this.bone.addChild(this.head);
         this.setRotationAngle(this.head, -0.3491F, 0.0F, 0.0F);
         this.head.cubeList.add(new ModelBox(this.head, 0, 12, -3.0F, -4.0F, -6.0F, 6, 5, 7, 0.0F, false));
         this.cube_r1 = new ModelRenderer(this);
         this.cube_r1.setRotationPoint(0.0F, -2.0F, -7.0F);
         this.head.addChild(this.cube_r1);
         this.setRotationAngle(this.cube_r1, 0.2182F, 0.0F, 0.0F);
         this.cube_r1.cubeList.add(new ModelBox(this.cube_r1, -11, 0, -5.0F, 1.0F, -10.0F, 10, 0, 11, 0.0F, false));
         this.gloweyes = new ModelRendererGlow(this, 240, false);
         this.gloweyes.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.head.addChild(this.gloweyes);
         this.gloweyes.cubeList.add(new ModelBox(this.gloweyes, 0, 36, -5.0F, -3.0F, -5.0F, 2, 3, 3, 0.0F, false));
         this.gloweyes.cubeList.add(new ModelBox(this.gloweyes, 0, 36, 3.0F, -3.0F, -5.0F, 2, 3, 3, 0.0F, false));
         this.bone2 = new ModelRenderer(this);
         this.bone2.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.bone.addChild(this.bone2);
         this.setRotationAngle(this.bone2, -0.2618F, 0.0F, 0.0F);
         this.bone2.cubeList.add(new ModelBox(this.bone2, 48, 37, -2.0F, 0.0F, 0.0F, 4, 8, 4, 0.0F, false));
         this.bone3 = new ModelRenderer(this);
         this.bone3.setRotationPoint(0.0F, 8.0F, 2.0F);
         this.bone2.addChild(this.bone3);
         this.setRotationAngle(this.bone3, -0.4363F, 0.0F, 0.0F);
         this.bone3.cubeList.add(new ModelBox(this.bone3, 60, 46, -5.0F, -1.0F, -3.1F, 10, 10, 8, 0.0F, false));
         this.bone4 = new ModelRenderer(this);
         this.bone4.setRotationPoint(1.0F, 7.0F, 2.0F);
         this.bone3.addChild(this.bone4);
         this.setRotationAngle(this.bone4, -0.7854F, 0.0F, 0.0F);
         this.bone4.cubeList.add(new ModelBox(this.bone4, 0, 36, -9.0F, 0.0F, -8.0F, 16, 14, 14, 0.0F, false));
         this.bone4.cubeList.add(new ModelBox(this.bone4, 20, 24, -7.0F, 14.0F, -6.0F, 12, 2, 10, 0.0F, false));
         this.glowBody = new ModelRendererGlow(this, 240, false);
         this.glowBody.setRotationPoint(-1.0F, 4.0F, -4.0F);
         this.bone4.addChild(this.glowBody);
         this.glowBody.cubeList.add(new ModelBox(this.glowBody, 66, 16, -8.0F, -4.0F, -4.0F, 16, 14, 14, 0.0F, false));
         this.larva1 = new ModelRendererGlow(this, 0, true);
         this.larva1.setRotationPoint(-1.0F, 16.0F, 0.0F);
         this.bone4.addChild(this.larva1);
         this.larva1.cubeList.add(new ModelBox(this.larva1, 0, 44, -5.0F, 0.0F, -1.0F, 3, 2, 3, 0.0F, false));
         this.larva2 = new ModelRendererGlow(this, 0, true);
         this.larva2.setRotationPoint(-1.0F, 16.0F, 0.0F);
         this.bone4.addChild(this.larva2);
         this.larva2.cubeList.add(new ModelBox(this.larva2, 0, 44, -4.0F, 0.0F, -5.0F, 3, 2, 3, 0.0F, false));
         this.larva3 = new ModelRendererGlow(this, 0, true);
         this.larva3.setRotationPoint(-1.0F, 16.0F, 0.0F);
         this.bone4.addChild(this.larva3);
         this.larva3.cubeList.add(new ModelBox(this.larva3, 0, 44, -1.25F, 0.0F, 0.0F, 3, 2, 3, 0.0F, false));
         this.larva4 = new ModelRendererGlow(this, 0, true);
         this.larva4.setRotationPoint(-1.0F, 16.0F, 0.0F);
         this.bone4.addChild(this.larva4);
         this.larva4.cubeList.add(new ModelBox(this.larva4, 0, 44, 0.0F, 0.0F, -5.5F, 3, 2, 3, 0.0F, false));
         this.larva5 = new ModelRendererGlow(this, 0, true);
         this.larva5.setRotationPoint(1.0F, 16.0F, 4.0F);
         this.bone4.addChild(this.larva5);
         this.larva5.cubeList.add(new ModelBox(this.larva5, 0, 44, 0.0F, 0.0F, -5.5F, 3, 2, 3, 0.0F, false));
         this.wing1 = new ModelRenderer(this);
         this.wing1.setRotationPoint(2.0F, -2.0F, 4.0F);
         this.bone.addChild(this.wing1);
         this.setRotationAngle(this.wing1, 0.0F, 0.0F, -0.48F);
         this.wing1.cubeList.add(new ModelBox(this.wing1, 21, 0, 0.0F, 0.0F, 0.0F, 26, 6, 0, 0.0F, false));
         this.wing4 = new ModelRenderer(this);
         this.wing4.setRotationPoint(-2.0F, -2.0F, 4.0F);
         this.bone.addChild(this.wing4);
         this.setRotationAngle(this.wing4, 0.0F, 0.0F, 0.48F);
         this.wing4.cubeList.add(new ModelBox(this.wing4, 21, 0, -26.0F, 0.0F, 0.0F, 26, 6, 0, 0.0F, true));
         this.wing2 = new ModelRenderer(this);
         this.wing2.setRotationPoint(2.0F, -2.0F, 4.0F);
         this.bone.addChild(this.wing2);
         this.setRotationAngle(this.wing2, 0.0F, 0.0F, 0.3054F);
         this.wing2.cubeList.add(new ModelBox(this.wing2, 21, 0, 0.0F, 0.0F, 0.0F, 26, 6, 0, 0.0F, false));
         this.wing3 = new ModelRenderer(this);
         this.wing3.setRotationPoint(-2.0F, -2.0F, 4.0F);
         this.bone.addChild(this.wing3);
         this.setRotationAngle(this.wing3, 0.0F, 0.0F, -0.3054F);
         this.wing3.cubeList.add(new ModelBox(this.wing3, 21, 0, -26.0F, 0.0F, 0.0F, 26, 6, 0, 0.0F, true));
         this.flying = AnimationDefinition.Builder
            .withLength(0.25F)
            .looping()
            .addAnimation(
               this.wing1,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-60.0F, 6.6667F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .addAnimation(
               this.wing2,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-60.0F, 6.6667F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .addAnimation(
               this.wing3,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-60.0F, -6.6667F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .addAnimation(
               this.wing4,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-60.0F, -6.6667F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .build();
         this.ymotion = AnimationDefinition.Builder
            .withLength(2.0F)
            .looping()
            .addAnimation(
               this.bone,
               new AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
               )
            )
            .build();
         this.attack = AnimationDefinition.Builder
            .withLength(1.0F)
            .addAnimation(
               this.bone,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .addAnimation(
               this.bone3,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .addAnimation(
               this.bone4,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .addAnimation(
               this.head,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .build();
         this.breed = AnimationDefinition.Builder
            .withLength(2.0F)
            .addAnimation(
               this.bone,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(1.25F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .addAnimation(
               this.bone2,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(1.25F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .addAnimation(
               this.bone3,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(1.25F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .addAnimation(
               this.bone4,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(1.25F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .build();
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         this.flying.animate(entity, 1.0F);
         this.ymotion.animate(entity, 1.0F);
         int anim3 = 100 - an3;
         int anim2 = 100 - an2;
         if (an3 > 0 && an3 < 100) {
            this.attack.animate(anim3);
         }

         if (an2 > 0 && an2 < 100) {
            this.breed.animate(anim2);
         }

         boolean blinking = an4 > 0 && an4 < 100;
         boolean hide = anim2 > 25 && anim2 < 100;
         int l = blinking ? (int)((0.5F + 0.5F * MathHelper.sin(an4)) * 180.0F) : 0;
         this.larva1.isHidden = hide;
         this.larva2.isHidden = hide;
         this.larva3.isHidden = hide;
         this.larva4.isHidden = hide;
         this.larva5.isHidden = hide;
         this.glowBody.light = blinking ? (int)((0.75F + 0.25F * MathHelper.sin(an4)) * 240.0F) : 0;
         this.larva1.light = l;
         this.larva2.light = l;
         this.larva3.light = l;
         this.larva4.light = l;
         this.larva5.light = l;
         this.bone.render(f5);
      }

      public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class LarvaModel extends ModelBase {
      private final ModelRenderer bone3;
      private final ModelRenderer bone4;
      private final ModelRenderer bone;
      private final ModelRenderer bone2;
      public AnimationDefinition animation1;

      public LarvaModel() {
         this.textureWidth = 32;
         this.textureHeight = 32;
         this.bone3 = new ModelRenderer(this);
         this.bone3.setRotationPoint(0.0F, 22.0F, -4.0F);
         this.bone3.cubeList.add(new ModelBox(this.bone3, 13, 15, -2.0F, -2.0F, -3.0F, 4, 4, 3, 0.0F, false));
         this.bone4 = new ModelRenderer(this);
         this.bone4.setRotationPoint(0.0F, 24.0F, -1.0F);
         this.bone4.cubeList.add(new ModelBox(this.bone4, 0, 0, -3.0F, -5.0F, -3.0F, 6, 5, 5, 0.0F, false));
         this.bone = new ModelRenderer(this);
         this.bone.setRotationPoint(0.0F, 22.0F, 1.0F);
         this.bone.cubeList.add(new ModelBox(this.bone, 0, 10, -2.0F, -2.0F, 0.0F, 4, 4, 4, 0.0F, false));
         this.bone2 = new ModelRenderer(this);
         this.bone2.setRotationPoint(0.0F, 0.0F, 4.0F);
         this.bone.addChild(this.bone2);
         this.bone2.cubeList.add(new ModelBox(this.bone2, 0, 18, -1.5F, -1.0F, 0.0F, 3, 3, 3, 0.0F, false));
         this.animation1 = AnimationDefinition.Builder
            .withLength(0.75F)
            .looping()
            .addAnimation(
               this.bone,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .addAnimation(
               this.bone2,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0.0F, -17.78F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 17.78F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .addAnimation(
               this.bone3,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
               )
            )
            .build();
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.animation1.animate(entity, 1.25F);
         this.bone3.render(f5);
         this.bone4.render(f5);
         this.bone.render(f5);
      }

      public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class ShadowMageModel extends AbstractMobModel {
      private final ModelRenderer legRight;
      private final ModelRenderer armRight;
      private final ModelRenderer cube_r1;
      private final ModelRenderer legLeft;
      private final ModelRenderer armLeft;
      private final ModelRenderer cube_r2;
      private final ModelRenderer Body;
      private final ModelRenderer glowbottles;
      private final ModelRenderer bone;
      private final ModelRenderer cube_r3;
      private final ModelRenderer cube_r4;
      private final ModelRenderer cube_r5;
      private final ModelRenderer cube_r6;
      private final ModelRenderer Head;
      private final ModelRenderer eyesglow;
      public AnimationDefinition cast;
      public AnimationDefinition idle;

      public ShadowMageModel() {
         this.textureWidth = 64;
         this.textureHeight = 64;
         this.legRight = new ModelRenderer(this);
         this.legRight.setRotationPoint(-2.0F, 12.0F, 0.0F);
         this.legRight.cubeList.add(new ModelBox(this.legRight, 32, 0, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
         this.armRight = new ModelRenderer(this);
         this.armRight.setRotationPoint(-4.0F, 2.0F, 0.0F);
         this.armRight.cubeList.add(new ModelBox(this.armRight, 48, 0, -4.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F, false));
         this.cube_r1 = new ModelRenderer(this);
         this.cube_r1.setRotationPoint(-4.0F, -2.0F, 0.0F);
         this.armRight.addChild(this.cube_r1);
         this.setRotationAngle(this.cube_r1, 0.0F, 0.0F, 0.1309F);
         this.cube_r1.cubeList.add(new ModelBox(this.cube_r1, 7, 37, 0.0F, 0.0F, -2.0F, 0, 13, 4, 0.0F, false));
         this.legLeft = new ModelRenderer(this);
         this.legLeft.setRotationPoint(2.0F, 12.0F, 0.0F);
         this.legLeft.cubeList.add(new ModelBox(this.legLeft, 32, 0, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
         this.armLeft = new ModelRenderer(this);
         this.armLeft.setRotationPoint(4.0F, 2.0F, 0.0F);
         this.armLeft.cubeList.add(new ModelBox(this.armLeft, 48, 0, 0.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F, true));
         this.cube_r2 = new ModelRenderer(this);
         this.cube_r2.setRotationPoint(4.0F, -2.0F, 0.0F);
         this.armLeft.addChild(this.cube_r2);
         this.setRotationAngle(this.cube_r2, 0.0F, 0.0F, -0.1309F);
         this.cube_r2.cubeList.add(new ModelBox(this.cube_r2, 16, 44, 0.0F, 0.0F, -2.0F, 0, 13, 4, 0.0F, true));
         this.Body = new ModelRenderer(this);
         this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.Body.cubeList.add(new ModelBox(this.Body, 0, 16, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F, false));
         this.glowbottles = new ModelRendererGlow(this, 160, true);
         this.glowbottles.setRotationPoint(1.0F, 10.0F, -2.0F);
         this.Body.addChild(this.glowbottles);
         this.glowbottles.cubeList.add(new ModelBox(this.glowbottles, 0, 33, 1.0F, -1.0F, -1.0F, 2, 3, 1, 0.0F, false));
         this.glowbottles.cubeList.add(new ModelBox(this.glowbottles, 7, 34, -2.0F, 0.0F, -1.0F, 2, 2, 1, 0.0F, false));
         this.bone = new ModelRenderer(this);
         this.bone.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.Body.addChild(this.bone);
         this.cube_r3 = new ModelRenderer(this);
         this.cube_r3.setRotationPoint(-2.0F, 0.0F, -2.0F);
         this.bone.addChild(this.cube_r3);
         this.setRotationAngle(this.cube_r3, -0.3491F, 0.0F, 0.0F);
         this.cube_r3
            .cubeList
            .add(
               new ModelRendererLimited.ModelBoxLimited(
                  this.cube_r3, 25, 53, -6.0F, 0.0F, 0.0F, 16, 9, 0, 0.0F, false, ModelRendererLimited.getAvaliables(false, false, false, false, true, false)
               )
            );
         this.cube_r4 = new ModelRenderer(this);
         this.cube_r4.setRotationPoint(-2.0F, 0.0F, -2.0F);
         this.bone.addChild(this.cube_r4);
         this.setRotationAngle(this.cube_r4, -0.2182F, 0.0F, 0.0F);
         this.cube_r4
            .cubeList
            .add(
               new ModelRendererLimited.ModelBoxLimited(
                  this.cube_r4, 30, 39, -6.0F, 0.0F, 0.0F, 16, 24, 0, 0.0F, false, ModelRendererLimited.getAvaliables(false, false, false, false, true, false)
               )
            );
         this.cube_r5 = new ModelRenderer(this);
         this.cube_r5.setRotationPoint(-2.5F, 0.0F, 2.0F);
         this.bone.addChild(this.cube_r5);
         this.setRotationAngle(this.cube_r5, 0.48F, 0.0F, 0.0F);
         this.cube_r5
            .cubeList
            .add(
               new ModelRendererLimited.ModelBoxLimited(
                  this.cube_r5, 19, 38, -7.0F, 0.0F, 0.0F, 19, 26, 0, 0.0F, false, ModelRendererLimited.getAvaliables(false, false, false, false, true, false)
               )
            );
         this.cube_r6 = new ModelRenderer(this);
         this.cube_r6.setRotationPoint(-2.5F, 4.0F, 2.0F);
         this.bone.addChild(this.cube_r6);
         this.setRotationAngle(this.cube_r6, 0.3491F, 0.0F, 0.0F);
         this.cube_r6
            .cubeList
            .add(
               new ModelRendererLimited.ModelBoxLimited(
                  this.cube_r6, 34, 47, -5.0F, 0.0F, 0.0F, 15, 15, 0, 0.0F, false, ModelRendererLimited.getAvaliables(false, false, false, false, true, false)
               )
            );
         this.Head = new ModelRenderer(this);
         this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.Head.cubeList.add(new ModelBox(this.Head, 0, 0, -4.0F, -9.0F, -4.0F, 8, 8, 8, 0.0F, false));
         this.Head.cubeList.add(new ModelBox(this.Head, 24, 18, -5.0F, -10.0F, -5.25F, 10, 10, 10, 0.0F, false));
         this.eyesglow = new ModelRendererGlow(this, 140, true);
         this.eyesglow.setRotationPoint(-3.0F, 3.0F, 0.0F);
         this.Head.addChild(this.eyesglow);
         this.eyesglow.cubeList.add(new ModelBox(this.eyesglow, 25, 22, 1.5F, -12.5F, -5.0F, 3, 3, 1, 0.0F, false));
         this.eyesglow.cubeList.add(new ModelBox(this.eyesglow, 25, 22, -1.5F, -9.5F, -5.0F, 3, 3, 1, 0.0F, false));
         this.eyesglow.cubeList.add(new ModelBox(this.eyesglow, 25, 22, 4.5F, -9.5F, -5.0F, 3, 3, 1, 0.0F, false));
         this.eyesglow.cubeList.add(new ModelBox(this.eyesglow, 25, 22, 1.5F, -6.5F, -5.0F, 3, 3, 1, 0.0F, false));
         this.cast = AnimationDefinition.Builder
            .withLength(1.0F)
            .looping()
            .addAnimation(
               this.armRight,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-191.1183F, 6.7177F, -18.8817F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-192.8278F, -1.9192F, -58.0114F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-162.8278F, -1.9192F, -58.0114F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(-165.9593F, -10.1685F, -28.977F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-191.1183F, 6.7177F, -18.8817F), AnimationChannel.Interpolations.CATMULLROM)
               )
            )
            .addAnimation(
               this.armLeft,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(-160.0F, 0.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(0.25F, KeyframeAnimations.degreeVec(-190.0F, 0.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(0.5F, KeyframeAnimations.degreeVec(-188.2189F, 5.7162F, 54.589F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(0.75F, KeyframeAnimations.degreeVec(-148.2189F, 5.7162F, 54.589F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(-160.0F, 0.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM)
               )
            )
            .build();
         this.idle = AnimationDefinition.Builder
            .withLength(3.0F)
            .looping()
            .addAnimation(
               this.bone,
               new AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.degreeVec(4.9811F, -0.4352F, 4.9811F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(2.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                  new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
               )
            )
            .build();
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         this.Head.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.Head.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.legRight.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
         this.legLeft.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
         this.idle.animate(entity, 1.0F);
         if (an1 > 40 && an1 < 100) {
            this.cast.animate(entity, 1.0F);
         } else {
            this.cast.returnDefaults();
            this.armRight.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.0F * f1;
            this.armLeft.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
         }

         this.legRight.render(f5);
         this.armRight.render(f5);
         this.legLeft.render(f5);
         this.armLeft.render(f5);
         this.Body.render(f5);
         this.Head.render(f5);
         if (entity instanceof DungeonMobsPack.ShadowMage && ((DungeonMobsPack.ShadowMage)entity).isShieldActive()) {
            GlStateManager.enableBlend();
            light(240, true);
            alphaGlow();
            GL11.glDepthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(ModelsDungeonMob.texCrystalShield);
            GlStateManager.translate(0.0F, 0.325F, 0.0F);
            ModelsDungeonMob.crystalShieldModel.render();
            GL11.glDepthMask(true);
            returnlight();
            alphaGlowDisable();
            GlStateManager.disableBlend();
         }
      }

      public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class StoneEaterModel extends AbstractMobModel {
      public ModelRenderer shape1;
      public ModelRenderer shape1Asegm;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer shape5;
      public ModelRenderer shape6;
      public ModelRenderer shape7;
      public ModelRenderer shape7_1;
      public ModelRenderer shape7_2;
      public ModelRenderer shape7_3;
      public ModelRenderer shape7_4;
      public ModelRenderer shape7_5;
      public ModelRenderer shape8;
      public ModelRenderer shape8_1;
      public ModelRenderer shape8_2;
      public ModelRenderer shape8_3;
      public ModelRenderer shape8_4;
      public ModelRenderer shape8_5;
      public ModelRenderer shape3A;
      public ModelRenderer shape4A;
      public ModelRenderer shape5A;
      public ModelRenderer shape6A;
      public boolean big;

      public StoneEaterModel(boolean big) {
         this.big = big;
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.shape7 = new ModelRenderer(this, 7, 0);
         this.shape7.setRotationPoint(0.0F, 0.0F, -7.5F);
         this.shape7.addBox(-1.0F, -9.5F, 2.0F, 2, 7, 2, 0.0F);
         this.setRotateAngle(this.shape7, 0.8196066F, 0.0F, 0.0F);
         this.shape2 = new ModelRenderer(this, 36, 8);
         this.shape2.setRotationPoint(0.0F, 0.0F, -8.0F);
         this.shape2.addBox(-5.5F, -5.5F, -3.0F, 11, 11, 3, 0.0F);
         this.shape8 = new ModelRenderer(this, 0, 0);
         this.shape8.setRotationPoint(0.0F, -9.0F, 2.5F);
         this.shape8.addBox(-0.5F, -6.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.shape8, 1.3203416F, 0.0F, 0.0F);
         this.shape3A = new ModelRenderer(this, 0, 12);
         this.shape3A.setRotationPoint(0.0F, 0.0F, -4.0F);
         this.shape3A.addBox(5.0F, -2.0F, -3.0F, 2, 4, 6, 0.0F);
         this.shape4 = new ModelRenderer(this, 0, 12);
         this.shape4.setRotationPoint(0.0F, 0.0F, 1.0F);
         this.shape4.addBox(5.0F, -2.0F, -3.0F, 2, 4, 6, 0.0F);
         this.setRotateAngle(this.shape4, 0.0F, 0.0F, (float) (-Math.PI / 2));
         this.shape7_2 = new ModelRenderer(this, 7, 0);
         this.shape7_2.setRotationPoint(0.0F, 0.0F, -7.5F);
         this.shape7_2.addBox(-1.0F, -9.5F, 2.0F, 2, 7, 2, 0.0F);
         this.setRotateAngle(this.shape7_2, 0.8196066F, 0.0F, (float) (Math.PI / 3));
         this.shape6 = new ModelRenderer(this, 0, 12);
         this.shape6.setRotationPoint(0.0F, 0.0F, -2.0F);
         this.shape6.addBox(5.0F, -2.0F, -3.0F, 2, 4, 6, 0.0F);
         this.setRotateAngle(this.shape6, 0.0F, 0.0F, (float) Math.PI);
         this.shape8_4 = new ModelRenderer(this, 0, 0);
         this.shape8_4.setRotationPoint(0.0F, -9.0F, 2.5F);
         this.shape8_4.addBox(-0.5F, -6.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.shape8_4, 1.3203416F, 0.0F, 0.0F);
         this.shape3 = new ModelRenderer(this, 0, 12);
         this.shape3.setRotationPoint(0.0F, 0.0F, -2.0F);
         this.shape3.addBox(5.0F, -2.0F, -3.0F, 2, 4, 6, 0.0F);
         this.shape8_2 = new ModelRenderer(this, 0, 0);
         this.shape8_2.setRotationPoint(0.0F, -9.0F, 2.5F);
         this.shape8_2.addBox(-0.5F, -6.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.shape8_2, 1.3203416F, 0.0F, 0.0F);
         this.shape8_3 = new ModelRenderer(this, 0, 0);
         this.shape8_3.setRotationPoint(0.0F, -9.0F, 2.5F);
         this.shape8_3.addBox(-0.5F, -6.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.shape8_3, 1.3203416F, 0.0F, 0.0F);
         this.shape8_1 = new ModelRenderer(this, 0, 0);
         this.shape8_1.setRotationPoint(0.0F, -9.0F, 2.5F);
         this.shape8_1.addBox(-0.5F, -6.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.shape8_1, 1.3203416F, 0.0F, 0.0F);
         this.shape4A = new ModelRenderer(this, 0, 12);
         this.shape4A.setRotationPoint(0.0F, 0.0F, -1.0F);
         this.shape4A.addBox(5.0F, -2.0F, -3.0F, 2, 4, 6, 0.0F);
         this.setRotateAngle(this.shape4A, 0.0F, 0.0F, (float) (-Math.PI / 2));
         this.shape5A = new ModelRenderer(this, 0, 12);
         this.shape5A.setRotationPoint(0.0F, 0.0F, -1.0F);
         this.shape5A.addBox(5.0F, -2.0F, -3.0F, 2, 4, 6, 0.0F);
         this.setRotateAngle(this.shape5A, 0.0F, 0.0F, (float) (Math.PI / 2));
         this.shape6A = new ModelRenderer(this, 0, 12);
         this.shape6A.setRotationPoint(0.0F, 0.0F, -4.0F);
         this.shape6A.addBox(5.0F, -2.0F, -3.0F, 2, 4, 6, 0.0F);
         this.setRotateAngle(this.shape6A, 0.0F, 0.0F, (float) Math.PI);
         this.shape7_5 = new ModelRenderer(this, 7, 0);
         this.shape7_5.setRotationPoint(0.0F, 0.0F, -7.5F);
         this.shape7_5.addBox(-1.0F, -9.5F, 2.0F, 2, 7, 2, 0.0F);
         this.setRotateAngle(this.shape7_5, 0.8196066F, 0.0F, (float) (-Math.PI * 2.0 / 3.0));
         this.shape7_3 = new ModelRenderer(this, 7, 0);
         this.shape7_3.setRotationPoint(0.0F, 0.0F, -7.5F);
         this.shape7_3.addBox(-1.0F, -9.5F, 2.0F, 2, 7, 2, 0.0F);
         this.setRotateAngle(this.shape7_3, 0.8196066F, 0.0F, (float) (Math.PI * 2.0 / 3.0));
         this.shape8_5 = new ModelRenderer(this, 0, 0);
         this.shape8_5.setRotationPoint(0.0F, -9.0F, 2.5F);
         this.shape8_5.addBox(-0.5F, -6.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.shape8_5, 1.3203416F, 0.0F, 0.0F);
         this.shape1Asegm = new ModelRenderer(this, 0, 6);
         this.shape1Asegm.setRotationPoint(0.0F, 17.0F, 0.0F);
         this.shape1Asegm.addBox(-5.0F, -5.0F, -12.0F, 10, 10, 16, 0.0F);
         this.shape7_4 = new ModelRenderer(this, 7, 0);
         this.shape7_4.setRotationPoint(0.0F, 0.0F, -7.5F);
         this.shape7_4.addBox(-1.0F, -9.5F, 2.0F, 2, 7, 2, 0.0F);
         this.setRotateAngle(this.shape7_4, 0.8196066F, 0.0F, (float) (-Math.PI / 3));
         this.shape1 = new ModelRenderer(this, 0, 6);
         this.shape1.setRotationPoint(0.0F, 17.0F, 0.0F);
         this.shape1.addBox(-5.0F, -5.0F, -8.0F, 10, 10, 16, 0.0F);
         this.shape5 = new ModelRenderer(this, 0, 12);
         this.shape5.setRotationPoint(0.0F, 0.0F, 1.0F);
         this.shape5.addBox(5.0F, -2.0F, -3.0F, 2, 4, 6, 0.0F);
         this.setRotateAngle(this.shape5, 0.0F, 0.0F, (float) (Math.PI / 2));
         this.shape7_1 = new ModelRenderer(this, 7, 0);
         this.shape7_1.setRotationPoint(0.0F, 0.0F, -7.5F);
         this.shape7_1.addBox(-1.0F, -9.5F, 2.0F, 2, 7, 2, 0.0F);
         this.setRotateAngle(this.shape7_1, 0.8196066F, 0.0F, (float) Math.PI);
         this.shape1.addChild(this.shape7);
         this.shape1.addChild(this.shape2);
         this.shape7.addChild(this.shape8);
         this.shape1Asegm.addChild(this.shape3A);
         this.shape1.addChild(this.shape4);
         this.shape1.addChild(this.shape7_2);
         this.shape1.addChild(this.shape6);
         this.shape7_4.addChild(this.shape8_4);
         this.shape1.addChild(this.shape3);
         this.shape7_2.addChild(this.shape8_2);
         this.shape7_3.addChild(this.shape8_3);
         this.shape7_1.addChild(this.shape8_1);
         this.shape1Asegm.addChild(this.shape4A);
         this.shape1Asegm.addChild(this.shape5A);
         this.shape1Asegm.addChild(this.shape6A);
         this.shape1.addChild(this.shape7_5);
         this.shape1.addChild(this.shape7_3);
         this.shape7_5.addChild(this.shape8_5);
         this.shape1.addChild(this.shape7_4);
         this.shape1.addChild(this.shape5);
         this.shape1.addChild(this.shape7_1);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         if (this.big) {
            f5 = (float)(f5 * 1.15);
         }

         boolean segm = false;
         if (isAbstractMob) {
            segm = ((AbstractMob)entity).isSubMob;
         }

         if (segm) {
            this.shape1Asegm.rotateAngleY = f3 * (float) (Math.PI / 180.0);
            this.shape1Asegm.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            this.shape1Asegm.render(f5);
         } else {
            this.shape1.rotateAngleY = f3 * (float) (Math.PI / 180.0);
            this.shape1.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            this.shape7.rotateAngleX = 0.8196066F + MathHelper.sin(AnimationTimer.tick / 8.0F) * 0.2F;
            this.shape8.rotateAngleX = 1.3203416F + MathHelper.sin((AnimationTimer.tick - 10) / 8.0F) * 0.3F;
            this.shape7_1.rotateAngleX = this.shape7.rotateAngleX;
            this.shape8_1.rotateAngleX = this.shape8.rotateAngleX;
            this.shape7_2.rotateAngleX = this.shape7.rotateAngleX;
            this.shape8_2.rotateAngleX = this.shape8.rotateAngleX;
            this.shape7_3.rotateAngleX = this.shape7.rotateAngleX;
            this.shape8_3.rotateAngleX = this.shape8.rotateAngleX;
            this.shape7_4.rotateAngleX = this.shape7.rotateAngleX;
            this.shape8_4.rotateAngleX = this.shape8.rotateAngleX;
            this.shape7_5.rotateAngleX = this.shape7.rotateAngleX;
            this.shape8_5.rotateAngleX = this.shape8.rotateAngleX;
            this.shape1.render(f5);
         }
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class UnderworldDiggerModel extends AbstractMobModel {
      public ModelRenderer shape1;
      public ModelRenderer shape1SEGM;
      public ModelRenderer shape2;
      public ModelRenderer shapeEYE1;
      public ModelRenderer shape7;
      public ModelRenderer shape7_1;
      public ModelRenderer shape7_2;
      public ModelRenderer shape7_3;
      public ModelRenderer shape7_4;
      public ModelRenderer shape7_5;
      public ModelRenderer shape2_1;
      public ModelRenderer shape9;
      public ModelRenderer shape9_1;
      public ModelRenderer shape9_2;
      public ModelRenderer shape9_3;
      public ModelRenderer shape9_4;
      public ModelRenderer shape9_5;
      public ModelRenderer shapeEYE1_1;
      public ModelRenderer shapeEYE1_2;
      public ModelRenderer shapeEYE1_3;
      public ModelRenderer shapeEYE2;
      public ModelRenderer shape8;
      public ModelRenderer shape8_1;
      public ModelRenderer shape8_2;
      public ModelRenderer shape8_3;
      public ModelRenderer shape8_4;
      public ModelRenderer shape8_5;
      public ModelRenderer shape10;
      public ModelRenderer shape10_1;
      public ModelRenderer shape10_2;
      public ModelRenderer shape10_3;
      public ModelRenderer shape10_4;
      public ModelRenderer shape10_5;
      public ModelRenderer shapeEYE2_1;
      public ModelRenderer shapeEYE2_2;
      public ModelRenderer shapeEYE2_3;
      public ModelRenderer shapeEYE1_4;
      public ModelRenderer shapeEYE1_5;
      public ModelRenderer shapeEYE1_6;
      public ModelRenderer shapeEYE1_7;
      public ModelRenderer shapeEYE2S;
      public ModelRenderer shapeEYE2S_1;
      public ModelRenderer shapeEYE2S_2;
      public ModelRenderer shapeEYE2S_3;

      public UnderworldDiggerModel() {
         int glow = 160;
         boolean add = false;
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.shape10_3 = new ModelRenderer(this, 54, 16);
         this.shape10_3.setRotationPoint(0.0F, -11.5F, 2.6F);
         this.shape10_3.addBox(-1.0F, -6.0F, -1.0F, 2, 7, 2, 0.0F);
         this.setRotateAngle(this.shape10_3, 1.0927507F, 0.0F, 0.0F);
         this.shape10_5 = new ModelRenderer(this, 54, 16);
         this.shape10_5.setRotationPoint(0.0F, -11.5F, 2.6F);
         this.shape10_5.addBox(-1.0F, -6.0F, -1.0F, 2, 7, 2, 0.0F);
         this.setRotateAngle(this.shape10_5, 1.0927507F, 0.0F, 0.0F);
         this.shapeEYE1_4 = new ModelRenderer(this, 0, 22);
         this.shapeEYE1_4.setRotationPoint(0.0F, 0.0F, -5.0F);
         this.shapeEYE1_4.addBox(6.0F, -3.0F, -2.0F, 2, 6, 8, 0.0F);
         this.shape10_2 = new ModelRenderer(this, 54, 16);
         this.shape10_2.setRotationPoint(0.0F, -11.5F, 2.6F);
         this.shape10_2.addBox(-1.0F, -6.0F, -1.0F, 2, 7, 2, 0.0F);
         this.setRotateAngle(this.shape10_2, 1.0927507F, 0.0F, 0.0F);
         this.shapeEYE2S = new ModelRendererGlow(this, 0, 14, glow, add);
         this.shapeEYE2S.setRotationPoint(8.0F, 0.0F, 2.0F);
         this.shapeEYE2S.addBox(-1.0F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.shapeEYE1_3 = new ModelRenderer(this, 0, 22);
         this.shapeEYE1_3.setRotationPoint(0.0F, 0.0F, 1.0F);
         this.shapeEYE1_3.addBox(6.0F, -3.0F, -2.0F, 2, 6, 8, 0.0F);
         this.setRotateAngle(this.shapeEYE1_3, 0.0F, 0.0F, (float) -Math.PI);
         this.shape9_5 = new ModelRenderer(this, 44, 16);
         this.shape9_5.setRotationPoint(0.0F, 0.0F, -5.5F);
         this.shape9_5.addBox(-1.5F, -11.8F, 2.0F, 3, 7, 2, 0.0F);
         this.setRotateAngle(this.shape9_5, 0.27314404F, 0.0F, (float) (-Math.PI / 2));
         this.shape7_1 = new ModelRenderer(this, 7, 0);
         this.shape7_1.setRotationPoint(0.0F, 0.0F, -7.5F);
         this.shape7_1.addBox(-1.0F, -9.5F, 2.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.shape7_1, 0.8196066F, 0.0F, (float) Math.PI);
         this.shapeEYE1_1 = new ModelRenderer(this, 0, 22);
         this.shapeEYE1_1.setRotationPoint(0.0F, 0.0F, 1.0F);
         this.shapeEYE1_1.addBox(6.0F, -3.0F, -2.0F, 2, 6, 8, 0.0F);
         this.setRotateAngle(this.shapeEYE1_1, 0.0F, 0.0F, (float) (-Math.PI / 2));
         this.shape7_2 = new ModelRenderer(this, 7, 0);
         this.shape7_2.setRotationPoint(0.0F, 0.0F, -7.5F);
         this.shape7_2.addBox(-1.0F, -9.5F, 2.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.shape7_2, 0.8196066F, 0.0F, (float) (Math.PI / 3));
         this.shape2_1 = new ModelRenderer(this, 12, 4);
         this.shape2_1.setRotationPoint(0.0F, 0.0F, -10.0F);
         this.shape2_1.addBox(-3.5F, -3.5F, -6.0F, 7, 7, 5, 0.0F);
         this.shape9 = new ModelRenderer(this, 44, 16);
         this.shape9.setRotationPoint(0.0F, 0.0F, -5.5F);
         this.shape9.addBox(-1.5F, -11.8F, 2.0F, 3, 7, 2, 0.0F);
         this.setRotateAngle(this.shape9, 0.27314404F, 0.0F, (float) (-Math.PI / 6));
         this.shapeEYE1_5 = new ModelRenderer(this, 0, 22);
         this.shapeEYE1_5.setRotationPoint(0.0F, 0.0F, -5.0F);
         this.shapeEYE1_5.addBox(6.0F, -3.0F, -2.0F, 2, 6, 8, 0.0F);
         this.setRotateAngle(this.shapeEYE1_5, 0.0F, 0.0F, (float) (-Math.PI / 2));
         this.shape8_3 = new ModelRenderer(this, 0, 0);
         this.shape8_3.setRotationPoint(0.0F, -9.0F, 2.5F);
         this.shape8_3.addBox(-0.5F, -6.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.shape8_3, 1.3203416F, 0.0F, 0.0F);
         this.shape7_3 = new ModelRenderer(this, 7, 0);
         this.shape7_3.setRotationPoint(0.0F, 0.0F, -7.5F);
         this.shape7_3.addBox(-1.0F, -9.5F, 2.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.shape7_3, 0.8196066F, 0.0F, (float) (Math.PI * 2.0 / 3.0));
         this.shape9_4 = new ModelRenderer(this, 44, 16);
         this.shape9_4.setRotationPoint(0.0F, 0.0F, -5.5F);
         this.shape9_4.addBox(-1.5F, -11.8F, 2.0F, 3, 7, 2, 0.0F);
         this.setRotateAngle(this.shape9_4, 0.27314404F, 0.0F, (float) (-Math.PI * 5.0 / 6.0));
         this.shape8_1 = new ModelRenderer(this, 0, 0);
         this.shape8_1.setRotationPoint(0.0F, -9.0F, 2.5F);
         this.shape8_1.addBox(-0.5F, -6.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.shape8_1, 1.3203416F, 0.0F, 0.0F);
         this.shape9_2 = new ModelRenderer(this, 44, 16);
         this.shape9_2.setRotationPoint(0.0F, 0.0F, -5.5F);
         this.shape9_2.addBox(-1.5F, -11.8F, 2.0F, 3, 7, 2, 0.0F);
         this.setRotateAngle(this.shape9_2, 0.27314404F, 0.0F, (float) (Math.PI / 2));
         this.shapeEYE2S_1 = new ModelRendererGlow(this, 0, 14, glow, add);
         this.shapeEYE2S_1.setRotationPoint(8.0F, 0.0F, 2.0F);
         this.shapeEYE2S_1.addBox(-1.0F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.shapeEYE1_2 = new ModelRenderer(this, 0, 22);
         this.shapeEYE1_2.setRotationPoint(0.0F, 0.0F, 1.0F);
         this.shapeEYE1_2.addBox(6.0F, -3.0F, -2.0F, 2, 6, 8, 0.0F);
         this.setRotateAngle(this.shapeEYE1_2, 0.0F, 0.0F, (float) (Math.PI / 2));
         this.shape7 = new ModelRenderer(this, 7, 0);
         this.shape7.setRotationPoint(0.0F, 0.0F, -7.5F);
         this.shape7.addBox(-1.0F, -9.5F, 2.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.shape7, 0.8196066F, 0.0F, 0.0F);
         this.shapeEYE1 = new ModelRenderer(this, 0, 22);
         this.shapeEYE1.setRotationPoint(0.0F, 0.0F, 1.0F);
         this.shapeEYE1.addBox(6.0F, -3.0F, -2.0F, 2, 6, 8, 0.0F);
         this.shape7_4 = new ModelRenderer(this, 7, 0);
         this.shape7_4.setRotationPoint(0.0F, 0.0F, -7.5F);
         this.shape7_4.addBox(-1.0F, -9.5F, 2.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.shape7_4, 0.8196066F, 0.0F, (float) (-Math.PI / 3));
         this.shape7_5 = new ModelRenderer(this, 7, 0);
         this.shape7_5.setRotationPoint(0.0F, 0.0F, -7.5F);
         this.shape7_5.addBox(-1.0F, -9.5F, 2.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.shape7_5, 0.8196066F, 0.0F, (float) (-Math.PI * 2.0 / 3.0));
         this.shape10_1 = new ModelRenderer(this, 54, 16);
         this.shape10_1.setRotationPoint(0.0F, -11.5F, 2.6F);
         this.shape10_1.addBox(-1.0F, -6.0F, -1.0F, 2, 7, 2, 0.0F);
         this.setRotateAngle(this.shape10_1, 1.0927507F, 0.0F, 0.0F);
         this.shapeEYE2_2 = new ModelRendererGlow(this, 0, 14, glow, add);
         this.shapeEYE2_2.setRotationPoint(8.0F, 0.0F, 2.0F);
         this.shapeEYE2_2.addBox(-1.0F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.shape9_3 = new ModelRenderer(this, 44, 16);
         this.shape9_3.setRotationPoint(0.0F, 0.0F, -5.5F);
         this.shape9_3.addBox(-1.5F, -11.8F, 2.0F, 3, 7, 2, 0.0F);
         this.setRotateAngle(this.shape9_3, 0.27314404F, 0.0F, (float) (Math.PI * 5.0 / 6.0));
         this.shape8_2 = new ModelRenderer(this, 0, 0);
         this.shape8_2.setRotationPoint(0.0F, -9.0F, 2.5F);
         this.shape8_2.addBox(-0.5F, -6.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.shape8_2, 1.3203416F, 0.0F, 0.0F);
         this.shape8_5 = new ModelRenderer(this, 0, 0);
         this.shape8_5.setRotationPoint(0.0F, -9.0F, 2.5F);
         this.shape8_5.addBox(-0.5F, -6.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.shape8_5, 1.3203416F, 0.0F, 0.0F);
         this.shapeEYE2S_3 = new ModelRendererGlow(this, 0, 14, glow, add);
         this.shapeEYE2S_3.setRotationPoint(8.0F, 0.0F, 2.0F);
         this.shapeEYE2S_3.addBox(-1.0F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.shape1SEGM = new ModelRenderer(this, 0, 16);
         this.shape1SEGM.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.shape1SEGM.addBox(-6.0F, -6.0F, -15.0F, 12, 12, 20, 0.0F);
         this.shapeEYE1_7 = new ModelRenderer(this, 0, 22);
         this.shapeEYE1_7.setRotationPoint(0.0F, 0.0F, -5.0F);
         this.shapeEYE1_7.addBox(6.0F, -3.0F, -2.0F, 2, 6, 8, 0.0F);
         this.setRotateAngle(this.shapeEYE1_7, 0.0F, 0.0F, (float) -Math.PI);
         this.shape1 = new ModelRenderer(this, 0, 16);
         this.shape1.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.shape1.addBox(-6.0F, -6.0F, -10.0F, 12, 12, 20, 0.0F);
         this.shape9_1 = new ModelRenderer(this, 44, 16);
         this.shape9_1.setRotationPoint(0.0F, 0.0F, -5.5F);
         this.shape9_1.addBox(-1.5F, -11.8F, 2.0F, 3, 7, 2, 0.0F);
         this.setRotateAngle(this.shape9_1, 0.27314404F, 0.0F, (float) (Math.PI / 6));
         this.shapeEYE2S_2 = new ModelRendererGlow(this, 0, 14, glow, add);
         this.shapeEYE2S_2.setRotationPoint(8.0F, 0.0F, 2.0F);
         this.shapeEYE2S_2.addBox(-1.0F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.shapeEYE1_6 = new ModelRenderer(this, 0, 22);
         this.shapeEYE1_6.setRotationPoint(0.0F, 0.0F, -5.0F);
         this.shapeEYE1_6.addBox(6.0F, -3.0F, -2.0F, 2, 6, 8, 0.0F);
         this.setRotateAngle(this.shapeEYE1_6, 0.0F, 0.0F, (float) (Math.PI / 2));
         this.shape8 = new ModelRenderer(this, 0, 0);
         this.shape8.setRotationPoint(0.0F, -9.0F, 2.5F);
         this.shape8.addBox(-0.5F, -6.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.shape8, 1.3203416F, 0.0F, 0.0F);
         this.shapeEYE2 = new ModelRendererGlow(this, 0, 14, glow, add);
         this.shapeEYE2.setRotationPoint(8.0F, 0.0F, 2.0F);
         this.shapeEYE2.addBox(-1.0F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.shape8_4 = new ModelRenderer(this, 0, 0);
         this.shape8_4.setRotationPoint(0.0F, -9.0F, 2.5F);
         this.shape8_4.addBox(-0.5F, -6.0F, -1.0F, 1, 7, 2, 0.0F);
         this.setRotateAngle(this.shape8_4, 1.3203416F, 0.0F, 0.0F);
         this.shapeEYE2_3 = new ModelRendererGlow(this, 0, 14, glow, add);
         this.shapeEYE2_3.setRotationPoint(8.0F, 0.0F, 2.0F);
         this.shapeEYE2_3.addBox(-1.0F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.shape2 = new ModelRenderer(this, 36, 0);
         this.shape2.setRotationPoint(0.0F, 0.0F, -10.0F);
         this.shape2.addBox(-5.5F, -5.5F, -3.0F, 11, 11, 3, 0.0F);
         this.shape10 = new ModelRenderer(this, 54, 16);
         this.shape10.setRotationPoint(0.0F, -11.5F, 2.6F);
         this.shape10.addBox(-1.0F, -6.0F, -1.0F, 2, 7, 2, 0.0F);
         this.setRotateAngle(this.shape10, 1.0927507F, 0.0F, 0.0F);
         this.shape10_4 = new ModelRenderer(this, 54, 16);
         this.shape10_4.setRotationPoint(0.0F, -11.5F, 2.6F);
         this.shape10_4.addBox(-1.0F, -6.0F, -1.0F, 2, 7, 2, 0.0F);
         this.setRotateAngle(this.shape10_4, 1.0927507F, 0.0F, 0.0F);
         this.shapeEYE2_1 = new ModelRendererGlow(this, 0, 14, glow, add);
         this.shapeEYE2_1.setRotationPoint(8.0F, 0.0F, 2.0F);
         this.shapeEYE2_1.addBox(-1.0F, -2.0F, -2.0F, 3, 4, 4, 0.0F);
         this.shape9_3.addChild(this.shape10_3);
         this.shape9_5.addChild(this.shape10_5);
         this.shape1SEGM.addChild(this.shapeEYE1_4);
         this.shape9_2.addChild(this.shape10_2);
         this.shapeEYE1_4.addChild(this.shapeEYE2S);
         this.shape1.addChild(this.shapeEYE1_3);
         this.shape1.addChild(this.shape9_5);
         this.shape1.addChild(this.shape7_1);
         this.shape1.addChild(this.shapeEYE1_1);
         this.shape1.addChild(this.shape7_2);
         this.shape1.addChild(this.shape2_1);
         this.shape1.addChild(this.shape9);
         this.shape1SEGM.addChild(this.shapeEYE1_5);
         this.shape7_3.addChild(this.shape8_3);
         this.shape1.addChild(this.shape7_3);
         this.shape1.addChild(this.shape9_4);
         this.shape7_1.addChild(this.shape8_1);
         this.shape1.addChild(this.shape9_2);
         this.shapeEYE1_5.addChild(this.shapeEYE2S_1);
         this.shape1.addChild(this.shapeEYE1_2);
         this.shape1.addChild(this.shape7);
         this.shape1.addChild(this.shapeEYE1);
         this.shape1.addChild(this.shape7_4);
         this.shape1.addChild(this.shape7_5);
         this.shape9_1.addChild(this.shape10_1);
         this.shapeEYE1_2.addChild(this.shapeEYE2_2);
         this.shape1.addChild(this.shape9_3);
         this.shape7_2.addChild(this.shape8_2);
         this.shape7_5.addChild(this.shape8_5);
         this.shapeEYE1_7.addChild(this.shapeEYE2S_3);
         this.shape1SEGM.addChild(this.shapeEYE1_7);
         this.shape1.addChild(this.shape9_1);
         this.shapeEYE1_6.addChild(this.shapeEYE2S_2);
         this.shape1SEGM.addChild(this.shapeEYE1_6);
         this.shape7.addChild(this.shape8);
         this.shapeEYE1.addChild(this.shapeEYE2);
         this.shape7_4.addChild(this.shape8_4);
         this.shapeEYE1_3.addChild(this.shapeEYE2_3);
         this.shape1.addChild(this.shape2);
         this.shape9.addChild(this.shape10);
         this.shape9_4.addChild(this.shape10_4);
         this.shapeEYE1_1.addChild(this.shapeEYE2_1);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -0.75F, 0.0F);
         f5 = (float)(f5 * 1.6);
         int num = entity.getEntityId();
         boolean segm = false;
         int eyes = 4;
         if (isAbstractMob) {
            segm = ((AbstractMob)entity).isSubMob;
            eyes = ((AbstractMob)entity).var1;
         }

         ArrayList<Integer> list = new ArrayList<>();
         list.add(1);
         list.add(2);
         list.add(3);
         list.add(4);
         Collections.shuffle(list, new Random(num));
         int i1 = list.get(0);
         int i2 = list.get(1);
         int i3 = list.get(2);
         int i4 = list.get(3);
         if (segm) {
            this.shapeEYE2S.rotateAngleY = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.52F, 0.52F
            );
            this.shapeEYE2S.rotateAngleZ = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.17F, 0.17F
            );
            this.shapeEYE2S_1.rotateAngleY = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.52F, 0.52F
            );
            this.shapeEYE2S_1.rotateAngleZ = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.17F, 0.17F
            );
            this.shapeEYE2S_2.rotateAngleY = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.52F, 0.52F
            );
            this.shapeEYE2S_2.rotateAngleZ = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.17F, 0.17F
            );
            this.shapeEYE2S_3.rotateAngleY = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.52F, 0.52F
            );
            this.shapeEYE2S_3.rotateAngleZ = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.17F, 0.17F
            );
            if (eyes >= i1) {
               this.shapeEYE1_4.isHidden = false;
            } else {
               this.shapeEYE1_4.isHidden = true;
            }

            if (eyes >= i2) {
               this.shapeEYE1_5.isHidden = false;
            } else {
               this.shapeEYE1_5.isHidden = true;
            }

            if (eyes >= i3) {
               this.shapeEYE1_6.isHidden = false;
            } else {
               this.shapeEYE1_6.isHidden = true;
            }

            if (eyes >= i4) {
               this.shapeEYE1_7.isHidden = false;
            } else {
               this.shapeEYE1_7.isHidden = true;
            }

            this.shape1SEGM.rotateAngleY = f3 * (float) (Math.PI / 180.0);
            this.shape1SEGM.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            this.shape1SEGM.render(f5);
         } else {
            this.shape1.rotateAngleY = f3 * (float) (Math.PI / 180.0);
            this.shape1.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            this.shape7.rotateAngleX = 0.8196066F + MathHelper.sin(AnimationTimer.tick / 10.0F) * 0.2F;
            this.shape8.rotateAngleX = 1.3203416F + MathHelper.sin((AnimationTimer.tick - 10) / 10.0F) * 0.3F;
            this.shape7_1.rotateAngleX = this.shape7.rotateAngleX;
            this.shape8_1.rotateAngleX = this.shape8.rotateAngleX;
            this.shape7_2.rotateAngleX = this.shape7.rotateAngleX;
            this.shape8_2.rotateAngleX = this.shape8.rotateAngleX;
            this.shape7_3.rotateAngleX = this.shape7.rotateAngleX;
            this.shape8_3.rotateAngleX = this.shape8.rotateAngleX;
            this.shape7_4.rotateAngleX = this.shape7.rotateAngleX;
            this.shape8_4.rotateAngleX = this.shape8.rotateAngleX;
            this.shape7_5.rotateAngleX = this.shape7.rotateAngleX;
            this.shape8_5.rotateAngleX = this.shape8.rotateAngleX;
            this.shape9.rotateAngleX = 0.27314404F + MathHelper.sin((AnimationTimer.tick - 15) / 10.0F) * 0.2F;
            this.shape10.rotateAngleX = 1.0927507F + MathHelper.sin((AnimationTimer.tick - 25) / 10.0F) * 0.3F;
            this.shape9_1.rotateAngleX = this.shape9.rotateAngleX;
            this.shape10_1.rotateAngleX = this.shape10.rotateAngleX;
            this.shape9_2.rotateAngleX = this.shape9.rotateAngleX;
            this.shape10_2.rotateAngleX = this.shape10.rotateAngleX;
            this.shape9_3.rotateAngleX = this.shape9.rotateAngleX;
            this.shape10_3.rotateAngleX = this.shape10.rotateAngleX;
            this.shape9_4.rotateAngleX = this.shape9.rotateAngleX;
            this.shape10_4.rotateAngleX = this.shape10.rotateAngleX;
            this.shape9_5.rotateAngleX = this.shape9.rotateAngleX;
            this.shape10_5.rotateAngleX = this.shape10.rotateAngleX;
            this.shapeEYE2.rotateAngleY = MathHelper.clamp(MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.52F, 0.52F);
            this.shapeEYE2.rotateAngleZ = MathHelper.clamp(MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.17F, 0.17F);
            this.shapeEYE2_1.rotateAngleY = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.52F, 0.52F
            );
            this.shapeEYE2_1.rotateAngleZ = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.17F, 0.17F
            );
            this.shapeEYE2_2.rotateAngleY = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.52F, 0.52F
            );
            this.shapeEYE2_2.rotateAngleZ = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.17F, 0.17F
            );
            this.shapeEYE2_3.rotateAngleY = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.52F, 0.52F
            );
            this.shapeEYE2_3.rotateAngleZ = MathHelper.clamp(
               MathHelper.sin(AnimationTimer.tick / 70.0F + num++ * 2.4778F) * 3.5F, -0.17F, 0.17F
            );
            if (eyes >= i1) {
               this.shapeEYE1.isHidden = false;
            } else {
               this.shapeEYE1.isHidden = true;
            }

            if (eyes >= i2) {
               this.shapeEYE1.isHidden = false;
            } else {
               this.shapeEYE1_1.isHidden = true;
            }

            if (eyes >= i3) {
               this.shapeEYE1.isHidden = false;
            } else {
               this.shapeEYE1_2.isHidden = true;
            }

            if (eyes >= i4) {
               this.shapeEYE1.isHidden = false;
            } else {
               this.shapeEYE1_3.isHidden = true;
            }

            this.shape1.render(f5);
         }

         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class UnderworldSymbioteModel extends ModelBase {
      public ModelRenderer body0;
      public ModelRenderer body1;
      public ModelRenderer body2;
      public ModelRenderer shapeEYE2;
      public ModelRenderer shapeTent1;
      public ModelRenderer shapeTent1_1;
      public ModelRenderer shapeTent1_2;
      public ModelRenderer shapeTent3;
      public ModelRenderer shapeTent3_1;
      public ModelRenderer shapeTent3_2;
      public ModelRenderer teeth;
      public ModelRenderer shapeTent2;
      public ModelRenderer shapeTent2_1;
      public ModelRenderer shapeTent2_2;
      public ModelRenderer shapeTent4;
      public ModelRenderer shapeTent4_1;
      public ModelRenderer shapeTent4_2;

      public UnderworldSymbioteModel() {
         this.textureWidth = 32;
         this.textureHeight = 32;
         this.shapeEYE2 = new ModelRendererGlow(this, 16, 14, 160, false);
         this.shapeEYE2.setRotationPoint(-1.0F, -3.5F, -1.0F);
         this.shapeEYE2.addBox(-1.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
         this.setRotateAngle(this.shapeEYE2, -0.5009095F, 0.0F, 0.0F);
         this.shapeTent3_2 = new ModelRenderer(this, 0, 14);
         this.shapeTent3_2.setRotationPoint(-2.8F, 1.5F, 1.0F);
         this.shapeTent3_2.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
         this.setRotateAngle(this.shapeTent3_2, 0.0F, -2.4586453F, -0.3642502F);
         this.shapeTent2_2 = new ModelRenderer(this, 0, 16);
         this.shapeTent2_2.setRotationPoint(2.8F, 0.0F, 0.0F);
         this.shapeTent2_2.addBox(0.0F, -0.5F, -0.45F, 3, 1, 1, 0.0F);
         this.shapeTent4 = new ModelRenderer(this, 0, 16);
         this.shapeTent4.setRotationPoint(2.8F, 0.0F, 0.0F);
         this.shapeTent4.addBox(0.0F, -0.5F, -0.55F, 3, 1, 1, 0.0F);
         this.shapeTent1 = new ModelRenderer(this, 0, 14);
         this.shapeTent1.setRotationPoint(2.8F, 1.5F, -3.0F);
         this.shapeTent1.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
         this.setRotateAngle(this.shapeTent1, 0.0F, 0.4553564F, 0.0F);
         this.shapeTent3_1 = new ModelRenderer(this, 0, 14);
         this.shapeTent3_1.setRotationPoint(-2.8F, 1.5F, -1.0F);
         this.shapeTent3_1.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
         this.setRotateAngle(this.shapeTent3_1, 0.0F, -3.0504866F, -0.18203785F);
         this.shapeTent1_1 = new ModelRenderer(this, 0, 14);
         this.shapeTent1_1.setRotationPoint(2.8F, 1.5F, -1.0F);
         this.shapeTent1_1.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
         this.setRotateAngle(this.shapeTent1_1, 0.0F, -0.091106184F, 0.18203785F);
         this.shapeTent4_1 = new ModelRenderer(this, 0, 16);
         this.shapeTent4_1.setRotationPoint(2.8F, 0.0F, 0.0F);
         this.shapeTent4_1.addBox(0.0F, -0.5F, -0.55F, 3, 1, 1, 0.0F);
         this.body0 = new ModelRenderer(this, 4, 0);
         this.body0.setRotationPoint(0.0F, 14.0F, 0.0F);
         this.body0.addBox(-3.0F, -3.0F, -5.0F, 6, 6, 8, 0.0F);
         this.setRotateAngle(this.body0, 0.5009095F, 0.0F, 0.0F);
         this.shapeTent2_1 = new ModelRenderer(this, 0, 16);
         this.shapeTent2_1.setRotationPoint(2.8F, 0.0F, 0.0F);
         this.shapeTent2_1.addBox(0.0F, -0.5F, -0.45F, 3, 1, 1, 0.0F);
         this.teeth = new ModelRenderer(this, 0, 18);
         this.teeth.setRotationPoint(0.0F, 0.0F, -5.0F);
         this.teeth.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 2, 0.0F);
         this.setRotateAngle(this.teeth, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.body2 = new ModelRenderer(this, 0, 24);
         this.body2.setRotationPoint(0.0F, 19.0F, 3.0F);
         this.body2.addBox(-2.5F, -3.0F, -1.0F, 5, 8, 0, 0.0F);
         this.setRotateAngle(this.body2, 0.5009095F, 0.0F, 0.0F);
         this.shapeTent4_2 = new ModelRenderer(this, 0, 16);
         this.shapeTent4_2.setRotationPoint(2.8F, 0.0F, 0.0F);
         this.shapeTent4_2.addBox(0.0F, -0.5F, -0.55F, 3, 1, 1, 0.0F);
         this.body1 = new ModelRenderer(this, 11, 18);
         this.body1.setRotationPoint(0.0F, 19.0F, 3.0F);
         this.body1.addBox(0.0F, -3.0F, -4.0F, 0, 8, 6, 0.0F);
         this.setRotateAngle(this.body1, 0.5009095F, 0.0F, 0.0F);
         this.shapeTent3 = new ModelRenderer(this, 0, 14);
         this.shapeTent3.setRotationPoint(-2.8F, 1.5F, -3.0F);
         this.shapeTent3.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
         this.setRotateAngle(this.shapeTent3, 0.0F, 2.6862361F, 0.0F);
         this.shapeTent2 = new ModelRenderer(this, 0, 16);
         this.shapeTent2.setRotationPoint(2.8F, 0.0F, 0.0F);
         this.shapeTent2.addBox(0.0F, -0.5F, -0.45F, 3, 1, 1, 0.0F);
         this.shapeTent1_2 = new ModelRenderer(this, 0, 14);
         this.shapeTent1_2.setRotationPoint(2.5F, 1.5F, 1.0F);
         this.shapeTent1_2.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
         this.setRotateAngle(this.shapeTent1_2, 0.0F, -0.68294734F, 0.3642502F);
         this.body0.addChild(this.shapeEYE2);
         this.body0.addChild(this.shapeTent3_2);
         this.shapeTent1_2.addChild(this.shapeTent2_2);
         this.shapeTent3.addChild(this.shapeTent4);
         this.body0.addChild(this.shapeTent1);
         this.body0.addChild(this.shapeTent3_1);
         this.body0.addChild(this.shapeTent1_1);
         this.shapeTent3_1.addChild(this.shapeTent4_1);
         this.shapeTent1_1.addChild(this.shapeTent2_1);
         this.body0.addChild(this.teeth);
         this.shapeTent3_2.addChild(this.shapeTent4_2);
         this.body0.addChild(this.shapeTent3);
         this.shapeTent1.addChild(this.shapeTent2);
         this.body0.addChild(this.shapeTent1_2);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         f5 *= 1.3F;
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, MathHelper.sin(AnimationTimer.tick / 50.0F + entity.getEntityId()) * 0.15F - 0.05F, 0.0F);
         this.shapeEYE2.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.shapeEYE2.rotateAngleX = -0.5009095F + f4 * (float) (Math.PI / 180.0);
         int i = 0;
         this.shapeTent1.rotateAngleZ = MathHelper.sin(-AnimationTimer.tick / 20.0F + i * 1.678F) * 0.2F;
         this.shapeTent2.rotateAngleZ = MathHelper.sin((-AnimationTimer.tick + 30) / 20.0F + i++ * 1.678F) * 0.2F;
         this.shapeTent1_1.rotateAngleZ = MathHelper.sin(-AnimationTimer.tick / 20.0F + i * 1.678F) * 0.2F;
         this.shapeTent2_1.rotateAngleZ = MathHelper.sin((-AnimationTimer.tick + 30) / 20.0F + i++ * 1.678F) * 0.2F;
         this.shapeTent1_2.rotateAngleZ = MathHelper.sin(-AnimationTimer.tick / 20.0F + i * 1.678F) * 0.2F;
         this.shapeTent2_2.rotateAngleZ = MathHelper.sin((-AnimationTimer.tick + 30) / 20.0F + i++ * 1.678F) * 0.2F;
         this.shapeTent3.rotateAngleZ = MathHelper.sin(-AnimationTimer.tick / 20.0F + i * 1.678F) * 0.2F;
         this.shapeTent4.rotateAngleZ = MathHelper.sin((-AnimationTimer.tick + 30) / 20.0F + i++ * 1.678F) * 0.2F;
         this.shapeTent3_1.rotateAngleZ = MathHelper.sin(-AnimationTimer.tick / 20.0F + i * 1.678F) * 0.2F;
         this.shapeTent4_1.rotateAngleZ = MathHelper.sin((-AnimationTimer.tick + 30) / 20.0F + i++ * 1.678F) * 0.2F;
         this.shapeTent3_2.rotateAngleZ = MathHelper.sin(-AnimationTimer.tick / 20.0F + i * 1.678F) * 0.2F;
         this.shapeTent4_2.rotateAngleZ = MathHelper.sin((-AnimationTimer.tick + 30) / 20.0F + i++ * 1.678F) * 0.2F;
         this.body0.render(f5);
         this.body2.render(f5);
         this.body1.render(f5);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class WeaverModel extends ModelBase {
      public ModelRenderer spiderNeck;
      public ModelRenderer spiderHead;
      public ModelRenderer spiderBody;
      public ModelRenderer spiderLegL1;
      public ModelRenderer spiderLegL2;
      public ModelRenderer spiderLegL3;
      public ModelRenderer spiderLegL4;
      public ModelRenderer spiderLegR1;
      public ModelRenderer spiderLegR2;
      public ModelRenderer spiderLegR3;
      public ModelRenderer spiderLegR4;
      public ModelRenderer spiderEyes;
      public ModelRenderer spiderLegLa1;
      public ModelRenderer spiderLegLa2;
      public ModelRenderer spiderLegLa3;
      public ModelRenderer spiderLegLa4;
      public ModelRenderer spiderLegRa1;
      public ModelRenderer spiderLegRa2;
      public ModelRenderer spiderLegRa3;
      public ModelRenderer spiderLegRa4;

      public WeaverModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.spiderLegL2 = new ModelRenderer(this, 28, 6);
         this.spiderLegL2.setRotationPoint(4.0F, 16.0F, -1.5F);
         this.spiderLegL2.addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
         this.setRotateAngle(this.spiderLegL2, -0.045553092F, 0.22759093F, -0.4553564F);
         this.spiderLegLa1 = new ModelRenderer(this, 22, 0);
         this.spiderLegLa1.setRotationPoint(12.0F, 0.0F, 0.0F);
         this.spiderLegLa1.addBox(0.0F, -2.0F, 0.0F, 16, 4, 0, 0.0F);
         this.setRotateAngle(this.spiderLegLa1, 0.0F, 0.0F, 2.3675392F);
         this.spiderLegRa3 = new ModelRenderer(this, 22, 0);
         this.spiderLegRa3.setRotationPoint(12.0F, 0.0F, 0.0F);
         this.spiderLegRa3.addBox(0.0F, -2.0F, 0.0F, 16, 4, 0, 0.0F);
         this.setRotateAngle(this.spiderLegRa3, 0.0F, 0.0F, 2.3675392F);
         this.spiderLegRa1 = new ModelRenderer(this, 22, 0);
         this.spiderLegRa1.setRotationPoint(12.0F, 0.0F, 0.0F);
         this.spiderLegRa1.addBox(0.0F, -2.0F, 0.0F, 16, 4, 0, 0.0F);
         this.setRotateAngle(this.spiderLegRa1, 0.0F, 0.0F, 2.3675392F);
         this.spiderNeck = new ModelRenderer(this, 32, 10);
         this.spiderNeck.setRotationPoint(0.0F, 15.0F, -2.0F);
         this.spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 8, 0.0F);
         this.spiderLegL1 = new ModelRenderer(this, 28, 6);
         this.spiderLegL1.setRotationPoint(4.0F, 16.0F, -3.0F);
         this.spiderLegL1.addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
         this.setRotateAngle(this.spiderLegL1, -0.4553564F, 0.91053826F, -0.59184116F);
         this.spiderEyes = new ModelRenderer(this, 44, 24);
         this.spiderEyes.setRotationPoint(0.0F, 16.0F, -5.0F);
         this.spiderEyes.addBox(-4.0F, -4.0F, -6.0F, 8, 6, 2, 0.03F);
         this.spiderLegR3 = new ModelRenderer(this, 28, 6);
         this.spiderLegR3.setRotationPoint(-4.0F, 16.0F, 0.0F);
         this.spiderLegR3.addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
         this.setRotateAngle(this.spiderLegR3, -0.18203785F, -2.8228955F, 0.4098033F);
         this.spiderLegL3 = new ModelRenderer(this, 28, 6);
         this.spiderLegL3.setRotationPoint(4.0F, 16.0F, 0.0F);
         this.spiderLegL3.addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
         this.setRotateAngle(this.spiderLegL3, 0.18203785F, -0.31869712F, -0.4098033F);
         this.spiderLegLa3 = new ModelRenderer(this, 22, 0);
         this.spiderLegLa3.setRotationPoint(12.0F, 0.0F, 0.0F);
         this.spiderLegLa3.addBox(0.0F, -2.0F, 0.0F, 16, 4, 0, 0.0F);
         this.setRotateAngle(this.spiderLegLa3, 0.0F, 0.0F, 2.3675392F);
         this.spiderLegR1 = new ModelRenderer(this, 28, 6);
         this.spiderLegR1.setRotationPoint(-4.0F, 16.0F, -3.0F);
         this.spiderLegR1.addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
         this.setRotateAngle(this.spiderLegR1, 0.4553564F, 2.2310543F, 0.59184116F);
         this.spiderHead = new ModelRenderer(this, 0, 0);
         this.spiderHead.setRotationPoint(0.0F, 16.0F, -5.0F);
         this.spiderHead.addBox(-4.0F, -4.0F, -6.0F, 8, 6, 6, 0.0F);
         this.spiderBody = new ModelRenderer(this, 0, 12);
         this.spiderBody.setRotationPoint(0.0F, 15.0F, 2.5F);
         this.spiderBody.addBox(-5.0F, -4.0F, 0.0F, 10, 8, 12, 0.0F);
         this.setRotateAngle(this.spiderBody, 0.18203785F, 0.0F, 0.0F);
         this.spiderLegR4 = new ModelRenderer(this, 28, 6);
         this.spiderLegR4.setRotationPoint(-4.0F, 16.0F, 1.5F);
         this.spiderLegR4.addBox(-1.0F, -1.0F, -1.0F, 14, 2, 2, 0.0F);
         this.setRotateAngle(this.spiderLegR4, -0.5462881F, -2.321986F, 0.59184116F);
         this.spiderLegRa2 = new ModelRenderer(this, 22, 0);
         this.spiderLegRa2.setRotationPoint(12.0F, 0.0F, 0.0F);
         this.spiderLegRa2.addBox(0.0F, -2.0F, 0.0F, 16, 4, 0, 0.0F);
         this.setRotateAngle(this.spiderLegRa2, 0.0F, 0.0F, 2.3675392F);
         this.spiderLegR2 = new ModelRenderer(this, 28, 6);
         this.spiderLegR2.setRotationPoint(-4.0F, 16.0F, -1.5F);
         this.spiderLegR2.addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
         this.setRotateAngle(this.spiderLegR2, 0.045553092F, 2.9140017F, 0.4553564F);
         this.spiderLegL4 = new ModelRenderer(this, 28, 6);
         this.spiderLegL4.setRotationPoint(4.0F, 16.0F, 1.5F);
         this.spiderLegL4.addBox(-1.0F, -1.0F, -1.0F, 14, 2, 2, 0.0F);
         this.setRotateAngle(this.spiderLegL4, 0.5462881F, -0.8196066F, -0.59184116F);
         this.spiderLegLa4 = new ModelRenderer(this, 22, 0);
         this.spiderLegLa4.setRotationPoint(14.0F, 0.0F, 0.0F);
         this.spiderLegLa4.addBox(0.0F, -2.0F, 0.0F, 16, 4, 0, 0.0F);
         this.setRotateAngle(this.spiderLegLa4, 0.0F, 0.0F, 2.3675392F);
         this.spiderLegRa4 = new ModelRenderer(this, 22, 0);
         this.spiderLegRa4.setRotationPoint(14.0F, 0.0F, 0.0F);
         this.spiderLegRa4.addBox(0.0F, -2.0F, 0.0F, 16, 4, 0, 0.0F);
         this.setRotateAngle(this.spiderLegRa4, 0.0F, 0.0F, 2.3675392F);
         this.spiderLegLa2 = new ModelRenderer(this, 22, 0);
         this.spiderLegLa2.setRotationPoint(12.0F, 0.0F, 0.0F);
         this.spiderLegLa2.addBox(0.0F, -2.0F, 0.0F, 16, 4, 0, 0.0F);
         this.setRotateAngle(this.spiderLegLa2, 0.0F, 0.0F, 2.3675392F);
         this.spiderLegL1.addChild(this.spiderLegLa1);
         this.spiderLegR3.addChild(this.spiderLegRa3);
         this.spiderLegR1.addChild(this.spiderLegRa1);
         this.spiderLegL3.addChild(this.spiderLegLa3);
         this.spiderLegR2.addChild(this.spiderLegRa2);
         this.spiderLegL4.addChild(this.spiderLegLa4);
         this.spiderLegR4.addChild(this.spiderLegRa4);
         this.spiderLegL2.addChild(this.spiderLegLa2);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         float angleX = 0.0F;
         float angleY = 0.0F;
         float angleZ = 0.0F;
         float angleGl = 0.0F;
         float angleVecGl = 0.0F;
         if (entity instanceof AbstractGlyphid) {
            AbstractGlyphid glyp = (AbstractGlyphid)entity;
            float pt = Minecraft.getMinecraft().getRenderPartialTicks();
            angleX = GetMOP.partial(glyp.renderangleX, glyp.prevrenderangleX, pt);
            angleY = GetMOP.partial(glyp.renderangleY, glyp.prevrenderangleY, pt);
            angleZ = GetMOP.partial(glyp.renderangleZ, glyp.prevrenderangleZ, pt);
            angleGl = GetMOP.partial(glyp.renderangle, glyp.prevrenderangle, pt);
            angleVecGl = GetMOP.partial(glyp.renderanglevec, glyp.prevrenderanglevec, pt);
         }

         float angle = 0.0F;
         if (!entity.isRiding()) {
            float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
            AbstractMob entitylb = (AbstractMob)entity;
            float qf = ModelsToxicomaniaMob.interpolateRotation(entitylb.prevRenderYawOffset, entitylb.renderYawOffset, partialTicks);
            angle = 180.0F - qf;
         }

         GlStateManager.pushMatrix();
         GlStateManager.rotate(angle, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(angleGl, angleX, angleY, angleZ);
         GlStateManager.translate(0.0F, -1.0F, 0.0F);
         GlStateManager.rotate(angleVecGl, 0.0F, 1.0F, 0.0F);
         float legsAnim = f * 0.8F;
         float legsV = MathHelper.clamp(MathHelper.cos(legsAnim), -0.2F, 1.0F) * 0.3F;
         float legsH = MathHelper.sin(legsAnim) * 0.3F;
         float legsV2 = MathHelper.clamp(MathHelper.cos(legsAnim + (float) Math.PI), -0.2F, 1.0F) * 0.3F;
         float legsH2 = MathHelper.sin(legsAnim + (float) Math.PI) * 0.3F;
         float legsLa = legsV * 2.0F;
         float legsLa2 = legsV2 * 2.0F;
         this.setRotateAngle(this.spiderLegL1, -0.4553564F, 0.91053826F, -0.59184116F);
         this.setRotateAngle(this.spiderLegLa1, 0.0F, 0.0F, 2.3675392F);
         this.setRotateAngle(this.spiderLegR4, -0.5462881F, -2.321986F, 0.59184116F);
         this.setRotateAngle(this.spiderLegRa2, 0.0F, 0.0F, 2.3675392F);
         this.setRotateAngle(this.spiderLegR2, 0.045553092F, 2.9140017F, 0.4553564F);
         this.setRotateAngle(this.spiderLegL4, 0.5462881F, -0.8196066F, -0.59184116F);
         this.setRotateAngle(this.spiderLegLa4, 0.0F, 0.0F, 2.3675392F);
         this.setRotateAngle(this.spiderLegRa4, 0.0F, 0.0F, 2.3675392F);
         this.setRotateAngle(this.spiderLegLa2, 0.0F, 0.0F, 2.3675392F);
         this.setRotateAngle(this.spiderLegL2, -0.045553092F, 0.22759093F, -0.4553564F);
         this.setRotateAngle(this.spiderLegRa3, 0.0F, 0.0F, 2.3675392F);
         this.setRotateAngle(this.spiderLegRa1, 0.0F, 0.0F, 2.3675392F);
         this.setRotateAngle(this.spiderLegR3, -0.18203785F, -2.8228955F, 0.4098033F);
         this.setRotateAngle(this.spiderLegL3, 0.18203785F, -0.31869712F, -0.4098033F);
         this.setRotateAngle(this.spiderLegLa3, 0.0F, 0.0F, 2.3675392F);
         this.setRotateAngle(this.spiderLegR1, 0.4553564F, 2.2310543F, 0.59184116F);
         this.spiderLegL1.rotateAngleZ -= legsV;
         this.spiderLegL1.rotateAngleY -= legsV;
         this.spiderLegL1.rotateAngleX -= legsH;
         this.spiderLegL1.rotateAngleY += legsH;
         this.spiderLegLa1.rotateAngleZ -= legsLa;
         this.spiderLegL2.rotateAngleZ -= legsV2;
         this.spiderLegL2.rotateAngleX -= legsH2;
         this.spiderLegL2.rotateAngleY += legsH2;
         this.spiderLegLa2.rotateAngleZ -= legsLa2;
         this.spiderLegL3.rotateAngleZ -= legsV;
         this.spiderLegL3.rotateAngleX -= legsH;
         this.spiderLegL3.rotateAngleY += legsH;
         this.spiderLegLa3.rotateAngleZ -= legsLa;
         this.spiderLegL4.rotateAngleZ -= legsV2;
         this.spiderLegL4.rotateAngleX += legsV2;
         this.spiderLegL4.rotateAngleX -= legsH2;
         this.spiderLegL4.rotateAngleY += legsH2;
         this.spiderLegLa4.rotateAngleZ -= legsLa2;
         this.spiderLegR1.rotateAngleZ += legsV2;
         this.spiderLegR1.rotateAngleY += legsV2;
         this.spiderLegR1.rotateAngleX += legsH2;
         this.spiderLegR1.rotateAngleY -= legsH2;
         this.spiderLegRa1.rotateAngleZ -= legsLa2;
         this.spiderLegR2.rotateAngleZ += legsV;
         this.spiderLegR2.rotateAngleX += legsH;
         this.spiderLegR2.rotateAngleY -= legsH;
         this.spiderLegRa2.rotateAngleZ -= legsLa;
         this.spiderLegR3.rotateAngleZ += legsV2;
         this.spiderLegR3.rotateAngleX += legsH2;
         this.spiderLegR3.rotateAngleY -= legsH2;
         this.spiderLegRa3.rotateAngleZ -= legsLa2;
         this.spiderLegR4.rotateAngleZ += legsV;
         this.spiderLegR4.rotateAngleX -= legsV;
         this.spiderLegR4.rotateAngleX += legsH;
         this.spiderLegR4.rotateAngleY -= legsH;
         this.spiderLegRa4.rotateAngleZ -= legsLa;
         this.spiderNeck.render(f5);
         this.spiderHead.render(f5);
         this.spiderBody.render(f5);
         this.spiderLegL1.render(f5);
         this.spiderLegL2.render(f5);
         this.spiderLegL3.render(f5);
         this.spiderLegL4.render(f5);
         this.spiderLegR1.render(f5);
         this.spiderLegR2.render(f5);
         this.spiderLegR3.render(f5);
         this.spiderLegR4.render(f5);
         AbstractMobModel.light(180, true);
         this.spiderEyes.render(f5);
         AbstractMobModel.returnlight();
         GlStateManager.popMatrix();
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.spiderHead.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.spiderHead.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
         this.spiderEyes.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.spiderEyes.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }
}
