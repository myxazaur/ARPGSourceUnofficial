package com.vivern.arpg.elements.models;

import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.mobs.AbstractMob;
import com.vivern.arpg.mobs.BossOphanim;
import com.vivern.arpg.renders.ModelRendererGlow;
import com.vivern.arpg.renders.ModelRendererLimited;
import com.vivern.arpg.shader.ShaderMain;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

public class ModelsStormledgeMob {
   public static ResourceLocation texPOWER = new ResourceLocation("arpg:textures/lightnings.png");
   public static ResourceLocation texFIELD = new ResourceLocation("arpg:textures/forcefield.png");
   public static ResourceLocation texFIELD2 = new ResourceLocation("arpg:textures/forcefield2.png");
   public static ResourceLocation texOPHANIM_WINDS = new ResourceLocation("arpg:textures/boss_ophanim_model_tex_overlay.png");
   public static ModelSphere forcefieldModel = new ModelSphere(1.5F, 6);

   public static class BossOphanimModel extends AbstractMobModel {
      int imax1 = 13;
      int imax2 = 14;
      int imax3 = 14;
      int imax4 = 14;
      public ModelRenderer core1;
      public ModelRenderer core2;
      public ModelRenderer core3;
      public ModelRenderer core4;
      public ModelRenderer core0;
      public ModelRenderer orb;
      public ModelRenderer circleCore1;
      public ModelRenderer circleCore2;
      public ModelRenderer circleCore3;
      public ModelRenderer circleCore4;
      public ModelRenderer wing1a;
      public ModelRenderer wing2a;
      public ModelRenderer wing3a;
      public ModelRenderer wing3aR;
      public ModelRenderer wing2aR;
      public ModelRenderer wing1aR;
      public ModelRenderer winds1;
      public ModelRenderer winds2;
      public ModelRenderer winds3;
      public ModelRenderer winds4;
      public ModelRenderer winds6;
      public ModelRenderer winds5;
      public ModelRenderer eye1;
      public ModelRenderer eye2;
      public ModelRenderer eye3;
      public ModelRenderer eye4;
      public ModelRenderer eye5;
      public ModelRenderer eye6;
      public ModelRenderer eye7;
      public ModelRenderer eye8;
      public ModelRenderer eye9;
      public ModelRendererLimited[] circle1;
      public ModelRenderer[] circleEye1;
      public ModelRenderer[] circleEyeC1;
      public ModelRendererLimited[] circle2;
      public ModelRenderer[] circleEye2;
      public ModelRenderer[] circleEyeC2;
      public ModelRendererLimited[] circle3;
      public ModelRenderer[] circleEye3;
      public ModelRenderer[] circleEyeC3;
      public ModelRendererLimited[] circle4;
      public ModelRenderer[] circleEye4;
      public ModelRenderer[] circleEyea4;
      public ModelRenderer[] circleEyeC4;
      public ModelRenderer[] circleEyeaC4;
      public ModelRenderer wing1core;
      public ModelRenderer wing1b;
      public ModelRenderer wing1c;
      public ModelRenderer wing1bAdd;
      public ModelRenderer feather1b;
      public ModelRenderer wing1d;
      public ModelRenderer feather1c;
      public ModelRenderer feather1d;
      public ModelRenderer wing2core;
      public ModelRenderer wing2b;
      public ModelRenderer wing2c;
      public ModelRenderer wing2bAdd;
      public ModelRenderer feather2b;
      public ModelRenderer wing2d;
      public ModelRenderer feather2c;
      public ModelRenderer feather2d;
      public ModelRenderer wing3core;
      public ModelRenderer wing3b;
      public ModelRenderer wing3c;
      public ModelRenderer wing3bAdd;
      public ModelRenderer feather3b;
      public ModelRenderer wing3d;
      public ModelRenderer feather3c;
      public ModelRenderer feather3d;
      public ModelRenderer wing3coreR;
      public ModelRenderer wing3bR;
      public ModelRenderer wing3cR;
      public ModelRenderer wing3bAddR;
      public ModelRenderer feather3bR;
      public ModelRenderer wing3dR;
      public ModelRenderer feather3cR;
      public ModelRenderer feather3dR;
      public ModelRenderer wing2coreR;
      public ModelRenderer wing2bR;
      public ModelRenderer wing2cR;
      public ModelRenderer wing2bAddR;
      public ModelRenderer feather2bR;
      public ModelRenderer wing2dR;
      public ModelRenderer feather2cR;
      public ModelRenderer feather2dR;
      public ModelRenderer wing1coreR;
      public ModelRenderer wing1bR;
      public ModelRenderer wing1cR;
      public ModelRenderer wing1bAddR;
      public ModelRenderer feather1bR;
      public ModelRenderer wing1dR;
      public ModelRenderer feather1cR;
      public ModelRenderer feather1dR;
      public ModelRenderer winds7;

      public BossOphanimModel() {
         this.textureWidth = 128;
         this.textureHeight = 64;
         this.eye3 = new ModelRenderer(this, 20, 0);
         this.eye3.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.eye3.addBox(4.0F, -1.0F, -6.0F, 2, 2, 0, 0.0F);
         this.setRotateAngle(this.eye3, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.wing2core = new ModelRendererGlow(this, 31, 1, 240, false);
         this.wing2core.setRotationPoint(0.0F, 3.5F, 0.0F);
         this.wing2core.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.wing2core, -1.2747885F, 0.63739425F, -1.4570009F);
         this.wing1bR = new ModelRenderer(this, 57, 44);
         this.wing1bR.setRotationPoint(0.0F, 2.5F, 0.0F);
         this.wing1bR.addBox(0.0F, 4.5F, -0.5F, 1, 15, 2, 0.0F);
         this.setRotateAngle(this.wing1bR, 0.13665928F, 0.045553092F, -0.18203785F);
         this.wing1aR = new ModelRenderer(this, 96, 54);
         this.wing1aR.setRotationPoint(-54.0F, -14.3F, 0.0F);
         this.wing1aR.addBox(-4.0F, -1.0F, -4.0F, 8, 2, 8, 0.0F);
         this.setRotateAngle(this.wing1aR, 0.0F, -1.1383038F, -1.8668041F);
         this.winds1 = new ModelRendererLimited(this, 3, 3, false, false, false, false, true, false);
         this.winds1.setRotationPoint(0.0F, 0.0F, -4.0F);
         this.winds1.addBox(-11.0F, -64.0F, 0.0F, 22, 58, 0, 0.0F);
         this.setRotateAngle(this.winds1, 0.091106184F, 0.0F, 0.0F);
         this.wing3core = new ModelRendererGlow(this, 31, 1, 240, false);
         this.wing3core.setRotationPoint(0.0F, 3.5F, 0.0F);
         this.wing3core.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.wing3core, -1.2747885F, 0.63739425F, -1.4570009F);
         this.core4 = new ModelRenderer(this, 0, 0);
         this.core4.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.core4.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.core4, 0.045553092F, 0.8651597F, 0.4553564F);
         this.wing2bAdd = new ModelRenderer(this, 35, 55);
         this.wing2bAdd.setRotationPoint(-1.0F, 3.5F, -1.0F);
         this.wing2bAdd.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
         this.feather3b = new ModelRendererLimited(this, 99, 26, false, true, false, false, false, false);
         this.feather3b.setRotationPoint(0.5F, 10.0F, 1.5F);
         this.feather3b.addBox(0.0F, 0.0F, 0.0F, 0, 16, 10, 0.0F);
         this.wing3aR = new ModelRenderer(this, 96, 54);
         this.wing3aR.setRotationPoint(-19.0F, 53.0F, 0.0F);
         this.wing3aR.addBox(-4.0F, -1.0F, -4.0F, 8, 2, 8, 0.0F);
         this.setRotateAngle(this.wing3aR, 0.0F, -1.1383038F, -0.5462881F);
         this.eye6 = new ModelRenderer(this, 20, 0);
         this.eye6.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.eye6.addBox(5.0F, -0.5F, -5.0F, 1, 1, 0, 0.0F);
         this.orb = new ModelRenderer(this, 0, 21);
         this.orb.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.orb.addBox(-11.0F, -11.0F, 0.0F, 22, 22, 0, 0.0F);
         this.eye7 = new ModelRenderer(this, 20, 0);
         this.eye7.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.eye7.addBox(5.0F, -0.5F, -5.0F, 1, 1, 0, 0.0F);
         this.setRotateAngle(this.eye7, 0.0F, 0.0F, (float) (Math.PI / 2));
         this.feather3c = new ModelRendererLimited(this, 98, -26, false, true, false, false, false, false);
         this.feather3c.setRotationPoint(0.0F, -5.0F, 1.5F);
         this.feather3c.addBox(0.0F, 0.0F, 0.0F, 0, 26, 26, 0.0F);
         this.wing1dR = new ModelRenderer(this, 52, 32);
         this.wing1dR.setRotationPoint(-0.2F, 21.5F, 0.2F);
         this.wing1dR.addBox(-0.5F, 0.5F, -0.5F, 1, 31, 1, 0.0F);
         this.setRotateAngle(this.wing1dR, 0.5462881F, -0.13665928F, 0.0F);
         this.feather2cR = new ModelRendererLimited(this, 98, -26, false, true, false, false, false, false);
         this.feather2cR.setRotationPoint(0.0F, -5.0F, 1.5F);
         this.feather2cR.addBox(0.0F, 0.0F, 0.0F, 0, 26, 26, 0.0F);
         this.wing3a = new ModelRenderer(this, 96, 54);
         this.wing3a.setRotationPoint(19.0F, 53.0F, 0.0F);
         this.wing3a.addBox(-4.0F, -1.0F, -4.0F, 8, 2, 8, 0.0F);
         this.setRotateAngle(this.wing3a, 0.0F, -1.1383038F, -0.5462881F);
         this.winds4 = new ModelRendererLimited(this, 52, 2, false, false, false, false, true, false);
         this.winds4.setRotationPoint(-5.0F, 0.0F, 0.0F);
         this.winds4.addBox(-32.0F, -16.0F, 0.0F, 32, 32, 0, 0.0F);
         this.setRotateAngle(this.winds4, 0.0F, 1.2747885F, -0.18203785F);
         this.winds7 = new ModelRendererLimited(this, 114, 3, false, false, false, false, true, false);
         this.winds7.setRotationPoint(0.0F, 58.0F, 0.0F);
         this.winds7.addBox(-6.0F, 0.0F, 0.0F, 12, 58, 0, 0.0F);
         this.setRotateAngle(this.winds7, -0.091106184F, 0.0F, 0.0F);
         this.wing2b = new ModelRenderer(this, 57, 44);
         this.wing2b.setRotationPoint(0.0F, 2.5F, 0.0F);
         this.wing2b.addBox(0.0F, 4.5F, -0.5F, 1, 15, 2, 0.0F);
         this.setRotateAngle(this.wing2b, -0.091106184F, 0.045553092F, -0.27314404F);
         this.wing1b = new ModelRenderer(this, 57, 44);
         this.wing1b.setRotationPoint(0.0F, 2.5F, 0.0F);
         this.wing1b.addBox(0.0F, 4.5F, -0.5F, 1, 15, 2, 0.0F);
         this.setRotateAngle(this.wing1b, 0.13665928F, 0.045553092F, -0.18203785F);
         this.feather1b = new ModelRendererLimited(this, 99, 26, false, true, false, false, false, false);
         this.feather1b.setRotationPoint(0.5F, 10.0F, 1.5F);
         this.feather1b.addBox(0.0F, 0.0F, 0.0F, 0, 16, 10, 0.0F);
         this.eye8 = new ModelRenderer(this, 20, 0);
         this.eye8.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.eye8.addBox(5.0F, -0.5F, -5.0F, 1, 1, 0, 0.0F);
         this.setRotateAngle(this.eye8, 0.0F, 0.0F, (float) (-Math.PI / 2));
         this.eye4 = new ModelRenderer(this, 20, 0);
         this.eye4.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.eye4.addBox(4.0F, -1.0F, -6.0F, 2, 2, 0, 0.0F);
         this.setRotateAngle(this.eye4, 0.0F, 0.0F, (float) (Math.PI / 4));
         this.core1 = new ModelRenderer(this, 0, 0);
         this.core1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.core1.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.wing2aR = new ModelRenderer(this, 96, 54);
         this.wing2aR.setRotationPoint(-51.0F, 24.0F, 0.0F);
         this.wing2aR.addBox(-4.0F, -1.0F, -4.0F, 8, 2, 8, 0.0F);
         this.setRotateAngle(this.wing2aR, 0.0F, -1.1383038F, -1.0927507F);
         this.winds2 = new ModelRendererLimited(this, 29, 4, false, false, false, false, true, false);
         this.winds2.setRotationPoint(0.0F, 0.0F, -4.0F);
         this.winds2.addBox(-22.0F, -60.0F, 0.0F, 19, 58, 0, 0.0F);
         this.setRotateAngle(this.winds2, -0.091106184F, 0.59184116F, -0.18203785F);
         this.wing1core = new ModelRendererGlow(this, 31, 1, 240, false);
         this.wing1core.setRotationPoint(0.0F, 3.5F, 0.0F);
         this.wing1core.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.wing1core, -1.2747885F, 0.63739425F, -1.4570009F);
         this.wing2cR = new ModelRenderer(this, 45, 33);
         this.wing2cR.setRotationPoint(0.5F, 18.5F, 0.5F);
         this.wing2cR.addBox(-0.5F, 0.5F, -0.5F, 1, 22, 2, 0.0F);
         this.setRotateAngle(this.wing2cR, -1.2292354F, 0.27314404F, 0.045553092F);
         this.winds5 = new ModelRendererLimited(this, 52, 2, false, false, false, false, true, false);
         this.winds5.mirror = true;
         this.winds5.setRotationPoint(5.0F, 0.0F, 0.0F);
         this.winds5.addBox(0.0F, -16.0F, 0.0F, 32, 32, 0, 0.0F);
         this.setRotateAngle(this.winds5, 0.0F, -1.2747885F, 0.18203785F);
         this.wing2bAddR = new ModelRenderer(this, 35, 55);
         this.wing2bAddR.setRotationPoint(-1.0F, 3.5F, -1.0F);
         this.wing2bAddR.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
         this.eye5 = new ModelRenderer(this, 20, 0);
         this.eye5.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.eye5.addBox(4.0F, -1.0F, -6.0F, 2, 2, 0, 0.0F);
         this.setRotateAngle(this.eye5, 0.0F, 0.0F, (float) (Math.PI * 3.0 / 4.0));
         this.feather2c = new ModelRendererLimited(this, 98, -26, false, true, false, false, false, false);
         this.feather2c.setRotationPoint(0.0F, -5.0F, 1.5F);
         this.feather2c.addBox(0.0F, 0.0F, 0.0F, 0, 26, 26, 0.0F);
         this.feather1dR = new ModelRendererLimited(this, 64, -32, false, true, false, false, false, false);
         this.feather1dR.setRotationPoint(0.0F, 0.0F, 0.5F);
         this.feather1dR.addBox(0.0F, 0.0F, 0.0F, 0, 64, 32, 0.0F);
         this.wing1a = new ModelRenderer(this, 96, 54);
         this.wing1a.setRotationPoint(54.0F, -14.3F, 0.0F);
         this.wing1a.addBox(-4.0F, -1.0F, -4.0F, 8, 2, 8, 0.0F);
         this.setRotateAngle(this.wing1a, 0.0F, -1.1383038F, -1.8668041F);
         this.feather1c = new ModelRendererLimited(this, 98, -26, false, true, false, false, false, false);
         this.feather1c.setRotationPoint(0.0F, -5.0F, 1.5F);
         this.feather1c.addBox(0.0F, 0.0F, 0.0F, 0, 26, 26, 0.0F);
         this.feather1cR = new ModelRendererLimited(this, 98, -26, false, true, false, false, false, false);
         this.feather1cR.setRotationPoint(0.0F, -5.0F, 1.5F);
         this.feather1cR.addBox(0.0F, 0.0F, 0.0F, 0, 26, 26, 0.0F);
         this.wing3d = new ModelRenderer(this, 52, 32);
         this.wing3d.setRotationPoint(-0.2F, 21.5F, 0.2F);
         this.wing3d.addBox(-0.5F, 0.5F, -0.5F, 1, 31, 1, 0.0F);
         this.setRotateAngle(this.wing3d, 0.68294734F, 0.0F, -0.18203785F);
         this.wing3coreR = new ModelRendererGlow(this, 31, 1, 240, false);
         this.wing3coreR.setRotationPoint(0.0F, 3.5F, 0.0F);
         this.wing3coreR.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.wing3coreR, -1.2747885F, 0.63739425F, -1.4570009F);
         this.core2 = new ModelRenderer(this, 0, 0);
         this.core2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.core2.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.core2, 0.7285004F, 0.8651597F, 0.0F);
         this.wing1d = new ModelRenderer(this, 52, 32);
         this.wing1d.setRotationPoint(-0.2F, 21.5F, 0.2F);
         this.wing1d.addBox(-0.5F, 0.5F, -0.5F, 1, 31, 1, 0.0F);
         this.setRotateAngle(this.wing1d, 0.5462881F, -0.13665928F, 0.0F);
         this.wing2dR = new ModelRenderer(this, 52, 32);
         this.wing2dR.setRotationPoint(-0.2F, 21.5F, 0.2F);
         this.wing2dR.addBox(-0.5F, 0.5F, -0.5F, 1, 31, 1, 0.0F);
         this.setRotateAngle(this.wing2dR, 0.5462881F, -0.13665928F, 0.0F);
         this.feather1d = new ModelRendererLimited(this, 64, -32, false, true, false, false, false, false);
         this.feather1d.setRotationPoint(0.0F, 0.0F, 0.5F);
         this.feather1d.addBox(0.0F, 0.0F, 0.0F, 0, 64, 32, 0.0F);
         this.wing2d = new ModelRenderer(this, 52, 32);
         this.wing2d.setRotationPoint(-0.2F, 21.5F, 0.2F);
         this.wing2d.addBox(-0.5F, 0.5F, -0.5F, 1, 31, 1, 0.0F);
         this.setRotateAngle(this.wing2d, 0.5462881F, -0.13665928F, 0.0F);
         this.wing1cR = new ModelRenderer(this, 45, 33);
         this.wing1cR.setRotationPoint(0.5F, 18.5F, 0.5F);
         this.wing1cR.addBox(-0.5F, 0.5F, -0.5F, 1, 22, 2, 0.0F);
         this.setRotateAngle(this.wing1cR, -1.2292354F, 0.27314404F, 0.045553092F);
         this.wing2a = new ModelRenderer(this, 96, 54);
         this.wing2a.setRotationPoint(51.0F, 24.0F, 0.0F);
         this.wing2a.addBox(-4.0F, -1.0F, -4.0F, 8, 2, 8, 0.0F);
         this.setRotateAngle(this.wing2a, 0.0F, -1.1383038F, -1.0927507F);
         this.eye9 = new ModelRenderer(this, 20, 0);
         this.eye9.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.eye9.addBox(5.0F, -0.5F, -5.0F, 1, 1, 0, 0.0F);
         this.setRotateAngle(this.eye9, 0.0F, 0.0F, (float) Math.PI);
         this.wing2coreR = new ModelRendererGlow(this, 31, 1, 240, false);
         this.wing2coreR.setRotationPoint(0.0F, 3.5F, 0.0F);
         this.wing2coreR.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.wing2coreR, -1.2747885F, 0.63739425F, -1.4570009F);
         this.winds6 = new ModelRendererLimited(this, 88, 1, false, false, false, false, true, false);
         this.winds6.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.winds6.addBox(-11.0F, 0.0F, 0.0F, 22, 58, 0, 0.0F);
         this.setRotateAngle(this.winds6, 0.18203785F, 0.0F, 0.0F);
         this.wing3bAdd = new ModelRenderer(this, 35, 55);
         this.wing3bAdd.setRotationPoint(-1.0F, 3.5F, -1.0F);
         this.wing3bAdd.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
         this.wing1bAdd = new ModelRenderer(this, 35, 55);
         this.wing1bAdd.setRotationPoint(-1.0F, 3.5F, -1.0F);
         this.wing1bAdd.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
         this.eye2 = new ModelRenderer(this, 20, 0);
         this.eye2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.eye2.addBox(4.0F, -1.0F, -6.0F, 2, 2, 0, 0.0F);
         this.setRotateAngle(this.eye2, 0.0F, 0.0F, (float) (-Math.PI * 3.0 / 4.0));
         this.feather3bR = new ModelRendererLimited(this, 99, 26, false, true, false, false, false, false);
         this.feather3bR.setRotationPoint(0.5F, 10.0F, 1.5F);
         this.feather3bR.addBox(0.0F, 0.0F, 0.0F, 0, 16, 10, 0.0F);
         this.winds3 = new ModelRendererLimited(this, 29, 4, false, false, false, false, true, false);
         this.winds3.mirror = true;
         this.winds3.setRotationPoint(0.0F, 0.0F, -4.0F);
         this.winds3.addBox(3.0F, -60.0F, 0.0F, 19, 58, 0, 0.0F);
         this.setRotateAngle(this.winds3, -0.091106184F, -0.59184116F, 0.18203785F);
         this.wing2bR = new ModelRenderer(this, 57, 44);
         this.wing2bR.setRotationPoint(0.0F, 2.5F, 0.0F);
         this.wing2bR.addBox(0.0F, 4.5F, -0.5F, 1, 15, 2, 0.0F);
         this.setRotateAngle(this.wing2bR, -0.091106184F, 0.045553092F, -0.27314404F);
         this.core3 = new ModelRenderer(this, 0, 0);
         this.core3.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.core3.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.core3, -1.4114478F, 1.8668041F, -0.7285004F);
         this.wing1bAddR = new ModelRenderer(this, 35, 55);
         this.wing1bAddR.setRotationPoint(-1.0F, 3.5F, -1.0F);
         this.wing1bAddR.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
         this.feather3d = new ModelRendererLimited(this, 64, -32, false, true, false, false, false, false);
         this.feather3d.setRotationPoint(0.0F, 0.0F, 0.5F);
         this.feather3d.addBox(0.0F, 0.0F, 0.0F, 0, 64, 32, 0.0F);
         this.eye1 = new ModelRenderer(this, 18, 0);
         this.eye1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.eye1.addBox(-3.0F, -3.0F, -7.0F, 6, 6, 0, 0.0F);
         this.setRotateAngle(this.eye1, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.wing1coreR = new ModelRendererGlow(this, 31, 1, 240, false);
         this.wing1coreR.setRotationPoint(0.0F, 3.5F, 0.0F);
         this.wing1coreR.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.wing1coreR, -1.2747885F, 0.63739425F, -1.4570009F);
         this.feather2b = new ModelRendererLimited(this, 99, 26, false, true, false, false, false, false);
         this.feather2b.setRotationPoint(0.5F, 10.0F, 1.5F);
         this.feather2b.addBox(0.0F, 0.0F, 0.0F, 0, 16, 10, 0.0F);
         this.wing3bR = new ModelRenderer(this, 57, 44);
         this.wing3bR.setRotationPoint(0.0F, 2.5F, 0.0F);
         this.wing3bR.addBox(0.0F, 4.5F, -0.5F, 1, 15, 2, 0.0F);
         this.setRotateAngle(this.wing3bR, 0.7285004F, 0.22759093F, 0.59184116F);
         this.wing3bAddR = new ModelRenderer(this, 35, 55);
         this.wing3bAddR.setRotationPoint(-1.0F, 3.5F, -1.0F);
         this.wing3bAddR.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
         this.wing3dR = new ModelRenderer(this, 52, 32);
         this.wing3dR.setRotationPoint(-0.2F, 21.5F, 0.2F);
         this.wing3dR.addBox(-0.5F, 0.5F, -0.5F, 1, 31, 1, 0.0F);
         this.setRotateAngle(this.wing3dR, 0.68294734F, 0.0F, -0.18203785F);
         this.feather2d = new ModelRendererLimited(this, 64, -32, false, true, false, false, false, false);
         this.feather2d.setRotationPoint(0.0F, 0.0F, 0.5F);
         this.feather2d.addBox(0.0F, 0.0F, 0.0F, 0, 64, 32, 0.0F);
         this.feather2bR = new ModelRendererLimited(this, 99, 26, false, true, false, false, false, false);
         this.feather2bR.setRotationPoint(0.5F, 10.0F, 1.5F);
         this.feather2bR.addBox(0.0F, 0.0F, 0.0F, 0, 16, 10, 0.0F);
         this.feather1bR = new ModelRendererLimited(this, 99, 26, false, true, false, false, false, false);
         this.feather1bR.setRotationPoint(0.5F, 10.0F, 1.5F);
         this.feather1bR.addBox(0.0F, 0.0F, 0.0F, 0, 16, 10, 0.0F);
         this.core0 = new ModelRenderer(this, 48, 0);
         this.core0.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.core0.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
         this.wing3cR = new ModelRenderer(this, 45, 33);
         this.wing3cR.setRotationPoint(0.5F, 18.5F, 0.5F);
         this.wing3cR.addBox(-0.5F, 0.5F, -0.5F, 1, 22, 2, 0.0F);
         this.setRotateAngle(this.wing3cR, -0.68294734F, -0.091106184F, 0.18203785F);
         this.feather2dR = new ModelRendererLimited(this, 64, -32, false, true, false, false, false, false);
         this.feather2dR.setRotationPoint(0.0F, 0.0F, 0.5F);
         this.feather2dR.addBox(0.0F, 0.0F, 0.0F, 0, 64, 32, 0.0F);
         this.feather3cR = new ModelRendererLimited(this, 98, -26, false, true, false, false, false, false);
         this.feather3cR.setRotationPoint(0.0F, -5.0F, 1.5F);
         this.feather3cR.addBox(0.0F, 0.0F, 0.0F, 0, 26, 26, 0.0F);
         this.wing3b = new ModelRenderer(this, 57, 44);
         this.wing3b.setRotationPoint(0.0F, 2.5F, 0.0F);
         this.wing3b.addBox(0.0F, 4.5F, -0.5F, 1, 15, 2, 0.0F);
         this.setRotateAngle(this.wing3b, 0.7285004F, 0.22759093F, 0.59184116F);
         this.wing1c = new ModelRenderer(this, 45, 33);
         this.wing1c.setRotationPoint(0.5F, 18.5F, 0.5F);
         this.wing1c.addBox(-0.5F, 0.5F, -0.5F, 1, 22, 2, 0.0F);
         this.setRotateAngle(this.wing1c, -1.2292354F, 0.27314404F, 0.045553092F);
         this.wing3c = new ModelRenderer(this, 45, 33);
         this.wing3c.setRotationPoint(0.5F, 18.5F, 0.5F);
         this.wing3c.addBox(-0.5F, 0.5F, -0.5F, 1, 22, 2, 0.0F);
         this.setRotateAngle(this.wing3c, -0.68294734F, -0.091106184F, 0.18203785F);
         this.feather3dR = new ModelRendererLimited(this, 64, -32, false, true, false, false, false, false);
         this.feather3dR.setRotationPoint(0.0F, 0.0F, 0.5F);
         this.feather3dR.addBox(0.0F, 0.0F, 0.0F, 0, 64, 32, 0.0F);
         this.wing2c = new ModelRenderer(this, 45, 33);
         this.wing2c.setRotationPoint(0.5F, 18.5F, 0.5F);
         this.wing2c.addBox(-0.5F, 0.5F, -0.5F, 1, 22, 2, 0.0F);
         this.setRotateAngle(this.wing2c, -1.2292354F, 0.27314404F, 0.045553092F);
         this.circleCore1 = new ModelRenderer(this, 0, 0);
         this.circleCore1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.circleCore1.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
         int eyelight = 150;
         boolean eyeaddlight = true;
         this.circle1 = new ModelRendererLimited[this.imax1];
         this.circleEye1 = new ModelRenderer[this.imax1];
         this.circleEyeC1 = new ModelRenderer[this.imax1];

         for (int i = 0; i < this.imax1; i++) {
            this.circle1[i] = new ModelRendererLimited(this, 97, 27);
            this.circle1[i].setRotationPoint(0.0F, 0.0F, 0.0F);
            this.circle1[i].addBox(-5.0F, -3.0F, -23.0F, 10, 6, 3, 0.0F);
            this.circleEye1[i] = new ModelRendererGlow(this, 50, 8, eyelight, eyeaddlight);
            this.circleEye1[i].setRotationPoint(0.0F, 0.0F, -23.0F);
            this.circleEye1[i].addBox(-2.0F, -2.0F, -2.0F, 4, 4, 3, 0.0F);
            this.circleEyeC1[i] = new ModelRenderer(this, 0, 3);
            this.circleEyeC1[i].setRotationPoint(0.0F, 0.0F, -2.0F);
            this.circleEyeC1[i].addBox(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
            this.setRotateAngle(this.circleEyeC1[i], 0.0F, 0.0F, (float) (Math.PI / 4));
            this.circle1[i].addChild(this.circleEye1[i]);
            this.circleEye1[i].addChild(this.circleEyeC1[i]);
            this.circleCore1.addChild(this.circle1[i]);
         }

         this.circleCore2 = new ModelRenderer(this, 0, 0);
         this.circleCore2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.circleCore2.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
         this.circle2 = new ModelRendererLimited[this.imax2];
         this.circleEye2 = new ModelRenderer[this.imax2];
         this.circleEyeC2 = new ModelRenderer[this.imax2];

         for (int i = 0; i < this.imax2; i++) {
            this.circle2[i] = new ModelRendererLimited(this, 0, 55);
            this.circle2[i].setRotationPoint(0.0F, 0.0F, 0.0F);
            this.circle2[i].addBox(-7.0F, -3.0F, -32.0F, 14, 6, 3, 0.0F);
            this.circleEye2[i] = new ModelRendererGlow(this, 47, 18, eyelight, eyeaddlight);
            this.circleEye2[i].setRotationPoint(0.0F, 0.0F, -32.0F);
            this.circleEye2[i].addBox(-2.5F, -2.0F, -2.0F, 5, 4, 3, 0.0F);
            this.circleEyeC2[i] = new ModelRenderer(this, 0, 3);
            this.circleEyeC2[i].setRotationPoint(0.0F, 0.0F, -2.0F);
            this.circleEyeC2[i].addBox(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
            this.setRotateAngle(this.circleEyeC2[i], 0.0F, 0.0F, (float) (Math.PI / 4));
            this.circle2[i].addChild(this.circleEye2[i]);
            this.circleEye2[i].addChild(this.circleEyeC2[i]);
            this.circleCore2.addChild(this.circle2[i]);
         }

         this.circleCore3 = new ModelRenderer(this, 0, 0);
         this.circleCore3.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.circleCore3.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
         this.circle3 = new ModelRendererLimited[this.imax3];
         this.circleEye3 = new ModelRenderer[this.imax3];
         this.circleEyeC3 = new ModelRenderer[this.imax3];

         for (int i = 0; i < this.imax3; i++) {
            this.circle3[i] = new ModelRendererLimited(this, 0, 45);
            this.circle3[i].setRotationPoint(0.0F, 0.0F, 0.0F);
            this.circle3[i].addBox(-9.0F, -3.0F, -41.0F, 18, 6, 3, 0.0F);
            this.circleEye3[i] = new ModelRendererGlow(this, 46, 25, eyelight, eyeaddlight);
            this.circleEye3[i].setRotationPoint(0.0F, 0.0F, -41.0F);
            this.circleEye3[i].addBox(-3.0F, -2.0F, -2.0F, 6, 4, 3, 0.0F);
            this.circleEyeC3[i] = new ModelRenderer(this, 0, 3);
            this.circleEyeC3[i].setRotationPoint(0.0F, 0.0F, -2.0F);
            this.circleEyeC3[i].addBox(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
            this.setRotateAngle(this.circleEyeC3[i], 0.0F, 0.0F, (float) (Math.PI / 4));
            this.circle3[i].addChild(this.circleEye3[i]);
            this.circleEye3[i].addChild(this.circleEyeC3[i]);
            this.circleCore3.addChild(this.circle3[i]);
         }

         this.circleCore4 = new ModelRenderer(this, 0, 0);
         this.circleCore4.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.circleCore4.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
         this.circle4 = new ModelRendererLimited[this.imax4];
         this.circleEye4 = new ModelRenderer[this.imax4];
         this.circleEyeC4 = new ModelRenderer[this.imax4];
         this.circleEyea4 = new ModelRenderer[this.imax4];
         this.circleEyeaC4 = new ModelRenderer[this.imax4];

         for (int i = 0; i < this.imax4; i++) {
            this.circle4[i] = new ModelRendererLimited(this, 0, 12);
            this.circle4[i].setRotationPoint(0.0F, 0.0F, 0.0F);
            this.circle4[i].addBox(-11.0F, -3.0F, -50.0F, 22, 6, 3, 0.0F);
            this.circleEye4[i] = new ModelRendererGlow(this, 50, 8, eyelight, eyeaddlight);
            this.circleEye4[i].setRotationPoint(-5.0F, 0.0F, -50.0F);
            this.circleEye4[i].addBox(-2.0F, -2.0F, -2.0F, 4, 4, 3, 0.0F);
            this.circleEyea4[i] = new ModelRendererGlow(this, 50, 8, eyelight, eyeaddlight);
            this.circleEyea4[i].setRotationPoint(5.0F, 0.0F, -50.0F);
            this.circleEyea4[i].addBox(-2.0F, -2.0F, -2.0F, 4, 4, 3, 0.0F);
            this.circleEyeC4[i] = new ModelRenderer(this, 0, 3);
            this.circleEyeC4[i].setRotationPoint(0.0F, 0.0F, -2.0F);
            this.circleEyeC4[i].addBox(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
            this.setRotateAngle(this.circleEyeC4[i], 0.0F, 0.0F, (float) (Math.PI / 4));
            this.circleEyeaC4[i] = new ModelRenderer(this, 0, 3);
            this.circleEyeaC4[i].setRotationPoint(0.0F, 0.0F, -2.0F);
            this.circleEyeaC4[i].addBox(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
            this.setRotateAngle(this.circleEyeaC4[i], 0.0F, 0.0F, (float) (Math.PI / 4));
            this.circle4[i].addChild(this.circleEye4[i]);
            this.circle4[i].addChild(this.circleEyea4[i]);
            this.circleEye4[i].addChild(this.circleEyeC4[i]);
            this.circleEyea4[i].addChild(this.circleEyeaC4[i]);
            this.circleCore4.addChild(this.circle4[i]);
         }

         this.core0.addChild(this.eye3);
         this.wing2a.addChild(this.wing2core);
         this.wing1aR.addChild(this.wing1bR);
         this.wing3a.addChild(this.wing3core);
         this.wing2b.addChild(this.wing2bAdd);
         this.wing3b.addChild(this.feather3b);
         this.core0.addChild(this.eye6);
         this.core0.addChild(this.eye7);
         this.wing3c.addChild(this.feather3c);
         this.wing1cR.addChild(this.wing1dR);
         this.wing2cR.addChild(this.feather2cR);
         this.winds6.addChild(this.winds7);
         this.wing2a.addChild(this.wing2b);
         this.wing1a.addChild(this.wing1b);
         this.wing1b.addChild(this.feather1b);
         this.core0.addChild(this.eye8);
         this.core0.addChild(this.eye4);
         this.wing1a.addChild(this.wing1core);
         this.wing2bR.addChild(this.wing2cR);
         this.wing2bR.addChild(this.wing2bAddR);
         this.core0.addChild(this.eye5);
         this.wing2c.addChild(this.feather2c);
         this.wing1dR.addChild(this.feather1dR);
         this.wing1c.addChild(this.feather1c);
         this.wing1cR.addChild(this.feather1cR);
         this.wing3c.addChild(this.wing3d);
         this.wing3aR.addChild(this.wing3coreR);
         this.wing1c.addChild(this.wing1d);
         this.wing2cR.addChild(this.wing2dR);
         this.wing1d.addChild(this.feather1d);
         this.wing2c.addChild(this.wing2d);
         this.wing1bR.addChild(this.wing1cR);
         this.core0.addChild(this.eye9);
         this.wing2aR.addChild(this.wing2coreR);
         this.wing3b.addChild(this.wing3bAdd);
         this.wing1b.addChild(this.wing1bAdd);
         this.core0.addChild(this.eye2);
         this.wing3bR.addChild(this.feather3bR);
         this.wing2aR.addChild(this.wing2bR);
         this.wing1bR.addChild(this.wing1bAddR);
         this.wing3d.addChild(this.feather3d);
         this.core0.addChild(this.eye1);
         this.wing1aR.addChild(this.wing1coreR);
         this.wing2b.addChild(this.feather2b);
         this.wing3aR.addChild(this.wing3bR);
         this.wing3bR.addChild(this.wing3bAddR);
         this.wing3cR.addChild(this.wing3dR);
         this.wing2d.addChild(this.feather2d);
         this.wing2bR.addChild(this.feather2bR);
         this.wing1bR.addChild(this.feather1bR);
         this.wing3bR.addChild(this.wing3cR);
         this.wing2dR.addChild(this.feather2dR);
         this.wing3cR.addChild(this.feather3cR);
         this.wing3a.addChild(this.wing3b);
         this.wing1b.addChild(this.wing1c);
         this.wing3b.addChild(this.wing3c);
         this.wing3dR.addChild(this.feather3dR);
         this.wing2b.addChild(this.wing2c);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         f5 = (float)(f5 * 1.2);
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         float timer = 0.0F;
         float interpR = 0.0F;
         int currentCircle = 0;
         float laserPitch = 0.0F;
         float laserYaw = 0.0F;
         boolean shield = false;
         boolean renderCircle1 = true;
         boolean renderCircle2 = true;
         boolean renderCircle3 = true;
         boolean renderCircle4 = true;
         float redCircle1 = 0.0F;
         float redCircle2 = 0.0F;
         float redCircle3 = 0.0F;
         float redCircle4 = 0.0F;
         if (isAbstractMob && entity instanceof BossOphanim) {
            BossOphanim ophanim = (BossOphanim)entity;
            timer = ophanim.circlesRotation + ophanim.circlesSpeed * pt;
            interpR = this.interpolateRotation(ophanim.prevRenderYawOffset, ophanim.renderYawOffset, pt);
            laserPitch = GetMOP.partial(ophanim.laserRotationPitch, ophanim.prevlaserPitch, pt);
            laserYaw = GetMOP.partial(ophanim.laserRotationYaw, ophanim.prevlaserYaw, pt);
            shield = ophanim.hasShield;
            renderCircle1 = ophanim.circlesHealth[0] > 0.0F;
            renderCircle2 = ophanim.circlesHealth[1] > 0.0F;
            renderCircle3 = ophanim.circlesHealth[2] > 0.0F;
            renderCircle4 = ophanim.circlesHealth[3] > 0.0F;
            redCircle1 = ophanim.circlesRedRender[0] / 13.0F;
            redCircle2 = ophanim.circlesRedRender[1] / 13.0F;
            redCircle3 = ophanim.circlesRedRender[2] / 13.0F;
            redCircle4 = ophanim.circlesRedRender[3] / 13.0F;

            for (int i = 3; i >= 0; i--) {
               if (ophanim.circlesHealth[i] > 0.0F) {
                  currentCircle = i + 1;
                  break;
               }
            }
         }

         float anim = an1 <= 70 ? 0.0F : 100.0F - an1 + pt;
         if (currentCircle == 4 && anim > 0.0F) {
            int imax = this.imax4;

            for (int ix = 0; ix < Math.min(100 - an1, imax); ix++) {
               ModelRendererLimited mr = this.circle4[ix];
               float ft1 = GetMOP.getfromto(anim, 0.0F, (float)imax) - GetMOP.getfromto(anim, (float)imax, 17.0F);
               mr.setColor(1.0F, 1.0F - 0.1F * ft1, 1.0F - 0.6F * ft1, 1.0F);
               mr.uselight = ft1 > 0.0F;
               mr.addlight = ft1 > 0.0F;
               mr.light = (int)(ft1 * 240.0F);
            }
         } else {
            for (int ix = 0; ix < this.imax4; ix++) {
               ModelRendererLimited mr = this.circle4[ix];
               mr.setColor(1.0F, 1.0F - redCircle4, 1.0F - redCircle4, 1.0F);
            }
         }

         if (currentCircle == 3 && anim > 0.0F) {
            int imax = this.imax3;

            for (int ix = 0; ix < Math.min(100 - an1, imax); ix++) {
               ModelRendererLimited mr = this.circle3[ix];
               float ft1 = GetMOP.getfromto(anim, 0.0F, (float)imax) - GetMOP.getfromto(anim, (float)imax, 17.0F);
               mr.setColor(1.0F, 1.0F - 0.1F * ft1, 1.0F - 0.6F * ft1, 1.0F);
               mr.uselight = ft1 > 0.0F;
               mr.addlight = ft1 > 0.0F;
               mr.light = (int)(ft1 * 240.0F);
            }
         } else {
            for (int ix = 0; ix < this.imax3; ix++) {
               ModelRendererLimited mr = this.circle3[ix];
               mr.setColor(1.0F, 1.0F - redCircle3, 1.0F - redCircle3, 1.0F);
            }
         }

         if (currentCircle == 2 && anim > 0.0F) {
            int imax = this.imax2;

            for (int ix = 0; ix < Math.min(100 - an1, imax); ix++) {
               ModelRendererLimited mr = this.circle2[ix];
               float ft1 = GetMOP.getfromto(anim, 0.0F, (float)imax) - GetMOP.getfromto(anim, (float)imax, 17.0F);
               mr.setColor(1.0F, 1.0F - 0.1F * ft1, 1.0F - 0.6F * ft1, 1.0F);
               mr.uselight = ft1 > 0.0F;
               mr.addlight = ft1 > 0.0F;
               mr.light = (int)(ft1 * 240.0F);
            }
         } else {
            for (int ix = 0; ix < this.imax2; ix++) {
               ModelRendererLimited mr = this.circle2[ix];
               mr.setColor(1.0F, 1.0F - redCircle2, 1.0F - redCircle2, 1.0F);
            }
         }

         if (currentCircle == 1 && anim > 0.0F) {
            int imax = this.imax1;

            for (int ix = 0; ix < Math.min(100 - an1, imax); ix++) {
               ModelRendererLimited mr = this.circle1[ix];
               float ft1 = GetMOP.getfromto(anim, 0.0F, (float)imax) - GetMOP.getfromto(anim, (float)imax, 17.0F);
               mr.setColor(1.0F, 1.0F - 0.1F * ft1, 1.0F - 0.6F * ft1, 1.0F);
               mr.uselight = ft1 > 0.0F;
               mr.addlight = ft1 > 0.0F;
               mr.light = (int)(ft1 * 240.0F);
            }
         } else {
            for (int ix = 0; ix < this.imax1; ix++) {
               ModelRendererLimited mr = this.circle1[ix];
               mr.setColor(1.0F, 1.0F - redCircle1, 1.0F - redCircle1, 1.0F);
            }
         }

         anim = 2.2F;
         float lazerFromTo = 0.0F;
         float noLazer = 1.0F;
         if (an2 > 0) {
            float anim2 = 100.0F - an2 + pt;
            lazerFromTo = GetMOP.getfromto(anim2, 0.0F, 20.0F) - GetMOP.getfromto(anim2, 85.0F, 100.0F);
            anim += 2.5F * (GetMOP.getfromto(anim2, 0.0F, 20.0F) - GetMOP.getfromto(anim2, 30.0F, 100.0F));
         }

         noLazer = 1.0F - lazerFromTo;
         this.core0.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.core0.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.setRotateAngle(this.wing1b, 0.13665928F, 0.045553092F, -0.18203785F);
         this.setRotateAngle(this.wing1c, -1.2292354F, 0.27314404F, 0.045553092F);
         this.setRotateAngle(this.wing1d, 0.5462881F, -0.13665928F, 0.0F);
         this.setRotateAngle(this.wing1bR, 0.13665928F, 0.045553092F, -0.18203785F);
         this.setRotateAngle(this.wing1cR, -1.2292354F, 0.27314404F, 0.045553092F);
         this.setRotateAngle(this.wing1dR, 0.5462881F, -0.13665928F, 0.0F);
         this.setRotateAngle(this.wing2b, -0.091106184F, 0.045553092F, -0.27314404F);
         this.setRotateAngle(this.wing2c, -1.2292354F, 0.27314404F, 0.045553092F);
         this.setRotateAngle(this.wing2d, 0.5462881F, -0.13665928F, 0.0F);
         this.setRotateAngle(this.wing2bR, -0.091106184F, 0.045553092F, -0.27314404F);
         this.setRotateAngle(this.wing2cR, -1.2292354F, 0.27314404F, 0.045553092F);
         this.setRotateAngle(this.wing2dR, 0.5462881F, -0.13665928F, 0.0F);
         float wingsTime = AnimationTimer.tick % 80 / 80.0F;
         this.animateWing(this.wing1b, this.wing1c, this.wing1d, wingsTime, (float) (Math.PI / 180.0));
         this.animateWing(this.wing1bR, this.wing1cR, this.wing1dR, wingsTime, (float) (Math.PI / 180.0));
         float wingsTime2 = (AnimationTimer.tick + 20) % 80 / 80.0F;
         this.animateWing(this.wing2b, this.wing2c, this.wing2d, wingsTime2, 0.015453292F);
         this.animateWing(this.wing2bR, this.wing2cR, this.wing2dR, wingsTime2, 0.015453292F);
         float upForce = GetMOP.softfromto(wingsTime, 0.1F, 0.35F)
            - GetMOP.softfromto(wingsTime, 0.35F, 0.8F)
            + (GetMOP.softfromto(wingsTime2, 0.1F, 0.35F) - GetMOP.softfromto(wingsTime2, 0.35F, 0.8F));
         int ix = 0;
         Random rand = new Random(entity.getEntityId());

         for (ModelRenderer eye : this.circleEye1) {
            this.setReye(eye, ix++, rand);
         }

         for (ModelRenderer eye : this.circleEye2) {
            this.setReye(eye, ix++, rand);
         }

         for (ModelRenderer eye : this.circleEye3) {
            this.setReye(eye, ix++, rand);
         }

         for (ModelRenderer eye : this.circleEye4) {
            this.setReye(eye, ix++, rand);
         }

         for (ModelRenderer eye : this.circleEyea4) {
            this.setReye(eye, ix++, rand);
         }

         float oneAngl1 = 360.0F / this.imax1;
         float oneAngl2 = 360.0F / this.imax2;
         float oneAngl3 = 360.0F / this.imax3;
         float oneAngl4 = 360.0F / this.imax4;

         for (int m = 0; m < this.imax1; m++) {
            this.circle1[m].rotateAngleY = (oneAngl1 * m + AnimationTimer.tick * 1.3F) * (float) (Math.PI / 180.0);
         }

         for (int m = 0; m < this.imax2; m++) {
            this.circle2[m].rotateAngleY = (oneAngl2 * m - AnimationTimer.tick * 1.11F) * (float) (Math.PI / 180.0);
         }

         for (int m = 0; m < this.imax3; m++) {
            this.circle3[m].rotateAngleY = (oneAngl3 * m + AnimationTimer.tick * 0.9F) * (float) (Math.PI / 180.0);
         }

         for (int m = 0; m < this.imax4; m++) {
            this.circle4[m].rotateAngleY = (oneAngl4 * m - AnimationTimer.tick) * (float) (Math.PI / 180.0);
         }

         Vec3d circleCoreAngles1 = BossOphanim.anglesOfCircles(1, timer, lazerFromTo, laserPitch, laserYaw);
         Vec3d circleCoreAngles2 = BossOphanim.anglesOfCircles(2, timer, lazerFromTo, laserPitch, laserYaw);
         Vec3d circleCoreAngles3 = BossOphanim.anglesOfCircles(3, timer, lazerFromTo, laserPitch, laserYaw);
         Vec3d circleCoreAngles4 = BossOphanim.anglesOfCircles(4, timer, lazerFromTo, laserPitch, laserYaw);
         this.circleCore1.rotateAngleZ = (float)circleCoreAngles1.z;
         this.circleCore1.rotateAngleX = (float)circleCoreAngles1.x;
         this.circleCore1.rotateAngleY = (float)circleCoreAngles1.y;
         this.circleCore2.rotateAngleX = (float)circleCoreAngles2.x;
         this.circleCore2.rotateAngleY = (float)circleCoreAngles2.y;
         this.circleCore3.rotateAngleY = (float)circleCoreAngles3.y;
         this.circleCore3.rotateAngleX = (float)circleCoreAngles3.x;
         this.circleCore3.rotateAngleZ = (float)circleCoreAngles3.z;
         this.circleCore4.rotateAngleX = (float)circleCoreAngles4.x;
         this.circleCore4.rotateAngleY = (float)circleCoreAngles4.y;
         float r1 = AnimationTimer.rainbow1;
         float r2 = AnimationTimer.rainbow2;
         float r3 = AnimationTimer.rainbow3;
         int tick = AnimationTimer.tick;
         this.setRotateAngle(this.core1, r1 * 0.27F, tick * 0.087F, r2 * 0.115F);
         this.setRotateAngle(this.core2, r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         this.setRotateAngle(this.core3, tick * 0.087F, r3 * 0.185F, r2 * 0.165F);
         this.setRotateAngle(this.core4, r2 * 0.057F, r1 * 0.385F, tick * 0.071F);
         this.setRotateAngle(this.wing1core, tick * 0.087F, r3 * 0.185F, r2 * 0.165F);
         this.setRotateAngle(this.wing2core, r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         this.setRotateAngle(this.wing3core, tick * 0.087F, r3 * 0.185F, r2 * 0.165F);
         this.setRotateAngle(this.wing1coreR, r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         this.setRotateAngle(this.wing2coreR, -r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         this.setRotateAngle(this.wing3coreR, tick * 0.087F, -r3 * 0.185F, r2 * 0.165F);
         float offEyes = -0.4F;
         this.eye1.offsetZ = offEyes;
         this.eye2.offsetZ = offEyes;
         this.eye3.offsetZ = offEyes;
         this.eye4.offsetZ = offEyes;
         this.eye5.offsetZ = offEyes;
         this.eye6.offsetZ = offEyes;
         this.eye7.offsetZ = offEyes;
         this.eye8.offsetZ = offEyes;
         this.eye9.offsetZ = offEyes;
         int dashAnim = 100 - BossOphanim.dashTimeMax;
         if (an3 >= dashAnim) {
            float animx = 100.0F - an3 + pt;
            float ft1 = GetMOP.getfromto(animx, 0.0F, (float)BossOphanim.dashTimeReady)
               - GetMOP.getfromto(animx, (float)(BossOphanim.dashTimeMax - 4), (float)BossOphanim.dashTimeMax);
            GlStateManager.enableBlend();
            AbstractMobModel.alphaGlow();
            GlStateManager.color(1.0F - 0.5F * ft1, 1.0F - 0.1F * ft1, 1.0F, 1.0F);
            AbstractMobModel.light((int)(ft1 * 240.0F), false);
         } else {
            alphaGlowDisable();
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, upForce * -0.3F + 1.0F, 0.0F);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.wing1aR.offsetX, this.wing1aR.offsetY, this.wing1aR.offsetZ);
         GlStateManager.translate(this.wing1aR.rotationPointX * f5, this.wing1aR.rotationPointY * f5, this.wing1aR.rotationPointZ * f5);
         GlStateManager.scale(-1.2, 1.2, 1.2);
         GlStateManager.translate(-this.wing1aR.offsetX, -this.wing1aR.offsetY, -this.wing1aR.offsetZ);
         GlStateManager.translate(-this.wing1aR.rotationPointX * f5, -this.wing1aR.rotationPointY * f5, -this.wing1aR.rotationPointZ * f5);
         this.wing1aR.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.wing3aR.offsetX, this.wing3aR.offsetY, this.wing3aR.offsetZ);
         GlStateManager.translate(this.wing3aR.rotationPointX * f5, this.wing3aR.rotationPointY * f5, this.wing3aR.rotationPointZ * f5);
         GlStateManager.scale(-1.0, 1.0, 1.0);
         GlStateManager.translate(-this.wing3aR.offsetX, -this.wing3aR.offsetY, -this.wing3aR.offsetZ);
         GlStateManager.translate(-this.wing3aR.rotationPointX * f5, -this.wing3aR.rotationPointY * f5, -this.wing3aR.rotationPointZ * f5);
         this.wing3aR.render(f5);
         GlStateManager.popMatrix();
         this.wing3a.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.wing2aR.offsetX, this.wing2aR.offsetY, this.wing2aR.offsetZ);
         GlStateManager.translate(this.wing2aR.rotationPointX * f5, this.wing2aR.rotationPointY * f5, this.wing2aR.rotationPointZ * f5);
         GlStateManager.scale(-1.1, 1.1, 1.1);
         GlStateManager.translate(-this.wing2aR.offsetX, -this.wing2aR.offsetY, -this.wing2aR.offsetZ);
         GlStateManager.translate(-this.wing2aR.rotationPointX * f5, -this.wing2aR.rotationPointY * f5, -this.wing2aR.rotationPointZ * f5);
         this.wing2aR.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.wing1a.offsetX, this.wing1a.offsetY, this.wing1a.offsetZ);
         GlStateManager.translate(this.wing1a.rotationPointX * f5, this.wing1a.rotationPointY * f5, this.wing1a.rotationPointZ * f5);
         GlStateManager.scale(1.2, 1.2, 1.2);
         GlStateManager.translate(-this.wing1a.offsetX, -this.wing1a.offsetY, -this.wing1a.offsetZ);
         GlStateManager.translate(-this.wing1a.rotationPointX * f5, -this.wing1a.rotationPointY * f5, -this.wing1a.rotationPointZ * f5);
         this.wing1a.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.wing2a.offsetX, this.wing2a.offsetY, this.wing2a.offsetZ);
         GlStateManager.translate(this.wing2a.rotationPointX * f5, this.wing2a.rotationPointY * f5, this.wing2a.rotationPointZ * f5);
         GlStateManager.scale(1.1, 1.1, 1.1);
         GlStateManager.translate(-this.wing2a.offsetX, -this.wing2a.offsetY, -this.wing2a.offsetZ);
         GlStateManager.translate(-this.wing2a.rotationPointX * f5, -this.wing2a.rotationPointY * f5, -this.wing2a.rotationPointZ * f5);
         this.wing2a.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.rotate(180.0F - interpR, 0.0F, 1.0F, 0.0F);
         if (renderCircle1) {
            this.circleCore1.render(f5);
         }

         if (renderCircle2) {
            this.circleCore2.render(f5);
         }

         if (renderCircle3) {
            this.circleCore3.render(f5);
         }

         if (renderCircle4) {
            this.circleCore4.render(f5);
         }

         GlStateManager.popMatrix();
         if (an3 >= dashAnim) {
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            AbstractMobModel.returnlight();
            AbstractMobModel.alphaGlowDisable();
         }

         GlStateManager.enableBlend();
         light(240, true);
         this.core0.render(f5);
         alphaGlow();
         this.scaleAndRender(this.core1, anim, f5);
         this.scaleAndRender(this.core2, anim, f5);
         this.scaleAndRender(this.core3, anim, f5);
         this.scaleAndRender(this.core4, anim, f5);
         this.orb.render(f5);
         if (shield) {
            GlStateManager.pushMatrix();
            GL11.glDepthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(ModelsStormledgeMob.texFIELD2);
            float sclShield = 1.4F;
            GlStateManager.scale(sclShield, sclShield, sclShield);
            GlStateManager.rotate(AnimationTimer.tick / 2.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.color(0.8F, 0.75F, 0.45F);
            ModelsStormledgeMob.forcefieldModel.renderScaledtexture();
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            GL11.glDepthMask(true);
            GlStateManager.popMatrix();
         }

         Minecraft.getMinecraft().getTextureManager().bindTexture(ModelsStormledgeMob.texOPHANIM_WINDS);
         this.winds6.render(f5);
         returnlight();
         light(150, true);
         boolean shader = false;
         if (ShaderMain.shaderCounter < ShaderMain.maxShaderCounter) {
            ShaderMain.WindShader.start();
            ARBShaderObjects.glUniform1fARB(ShaderMain.MoltenShader.getUniform("time"), AnimationTimer.tick / 40.0F);
            ShaderMain.shaderCounter++;
            shader = true;
         }

         this.winds1.render(f5);
         this.winds2.render(f5);
         this.winds3.render(f5);
         this.winds4.render(f5);
         this.winds5.render(f5);
         GlStateManager.popMatrix();
         if (shader) {
            ShaderMain.WindShader.stop();
         }

         returnlight();
         alphaGlowDisable();
         GlStateManager.disableBlend();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }

      public void animateWing(ModelRenderer wing1, ModelRenderer wing2, ModelRenderer wing3, float progress, float multiplier) {
         float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F);
         float ft2 = GetMOP.getfromto(progress, 0.1F, 0.2F);
         float ft3 = GetMOP.softfromto(progress, 0.1F, 0.35F);
         float ft4 = GetMOP.getfromto(progress, 0.1F, 0.25F) - GetMOP.getfromto(progress, 0.25F, 0.35F);
         float ft5 = GetMOP.getfromto(progress, 0.35F, 0.5F);
         float ft6 = GetMOP.getfromto(progress, 0.5F, 0.7F);
         float ft7 = GetMOP.softfromto(progress, 0.7F, 1.0F);
         float ft8 = GetMOP.getfromto(progress, 0.05F, 0.23F) - GetMOP.getfromto(progress, 0.27F, 0.43F);
         wing1.rotateAngleX += (-30.0F * ft1 + 70.0F * ft3 + 30.0F * ft5 - 31.0F * ft6 - 39.0F * ft7 - 15.0F * ft8) * multiplier;
         wing1.rotateAngleY += (-12.0F * ft1 + 45.0F * ft2 - 8.0F * ft6 - 25.0F * ft7) * multiplier;
         wing1.rotateAngleZ += (-13.0F * ft2 + 73.0F * ft3 - 67.0F * ft6 + 7.0F * ft7) * multiplier;
         wing2.rotateAngleX += (30.0F * ft1 - 39.0F * ft5 + 11.0F * ft6 - 2.0F * ft7) * multiplier;
         wing2.rotateAngleY += (5.0F * ft1 - 5.0F * ft5) * multiplier;
         wing2.rotateAngleZ += (-25.0F * ft4 + 28.0F * ft5 - 28.0F * ft7) * multiplier;
         wing3.rotateAngleX += (-20.0F * ft1 + 73.0F * ft5 - 53.0F * ft7) * multiplier;
         wing3.rotateAngleY += (14.0F * ft1 - 14.0F * ft5) * multiplier;
         wing2.rotateAngleZ += (-20.0F * ft4 + 10.0F * ft5 - 10.0F * ft7) * multiplier;
      }

      public void scaleAndRender(ModelRenderer modelRenderer, float amount, float f5) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(modelRenderer.offsetX, modelRenderer.offsetY, modelRenderer.offsetZ);
         GlStateManager.translate(modelRenderer.rotationPointX * f5, modelRenderer.rotationPointY * f5, modelRenderer.rotationPointZ * f5);
         GlStateManager.scale(amount, amount, amount);
         GlStateManager.translate(-modelRenderer.offsetX, -modelRenderer.offsetY, -modelRenderer.offsetZ);
         GlStateManager.translate(-modelRenderer.rotationPointX * f5, -modelRenderer.rotationPointY * f5, -modelRenderer.rotationPointZ * f5);
         modelRenderer.render(f5);
         GlStateManager.popMatrix();
      }

      public void setReye(ModelRenderer eye, int num, Random rand) {
         eye.rotateAngleX = MathHelper.clamp(MathHelper.sin(-(AnimationTimer.tick + 60) / 50.0F + num * 3.72542F) * 3.5F, -0.52F, 0.52F);
         eye.rotateAngleZ = (rand.nextInt(181) - 90) * (float) (Math.PI / 180.0);
      }

      public float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
         float f = yawOffset - prevYawOffset;

         while (f < -180.0F) {
            f += 360.0F;
         }

         while (f >= 180.0F) {
            f -= 360.0F;
         }

         return prevYawOffset + partialTicks * f;
      }
   }

   public static class CloudEaterModel extends AbstractMobModel {
      public ModelRenderer core1;
      public ModelRenderer core2;
      public ModelRenderer core3;
      public ModelRenderer teeth1;
      public ModelRenderer teeth2;
      public ModelRenderer camera1b;
      public ModelRenderer camera2b;
      public ModelRenderer head1;
      public ModelRenderer head2;
      public ModelRenderer head3;
      public ModelRenderer head0;
      public ModelRenderer body1;
      public ModelRenderer camera3b;
      public ModelRenderer camera5b;
      public ModelRenderer camera4b;
      public ModelRenderer camera6b;
      public ModelRenderer camera1c;
      public ModelRenderer camera1d;
      public ModelRenderer camera2c;
      public ModelRenderer camera2d;
      public ModelRenderer body2;
      public ModelRenderer finB1;
      public ModelRenderer finA1;
      public ModelRenderer body3;
      public ModelRenderer finA2;
      public ModelRenderer finB2;
      public ModelRenderer finA3;
      public ModelRenderer finB3;
      public ModelRenderer camera3c;
      public ModelRenderer camera3d;
      public ModelRenderer camera5c;
      public ModelRenderer camera5d;
      public ModelRenderer camera4c;
      public ModelRenderer camera4d;
      public ModelRenderer camera6c;
      public ModelRenderer camera6d;

      public CloudEaterModel() {
         this.textureWidth = 80;
         this.textureHeight = 64;
         this.body2 = new ModelRenderer(this, 25, 0);
         this.body2.setRotationPoint(0.0F, -9.0F, 0.0F);
         this.body2.addBox(-2.0F, -8.5F, -2.0F, 4, 8, 3, 0.0F);
         this.setRotateAngle(this.body2, -0.31869712F, 0.0F, 0.0F);
         this.head1 = new ModelRenderer(this, 32, 40);
         this.head1.setRotationPoint(0.0F, 8.0F, -9.0F);
         this.head1.addBox(-6.0F, -6.0F, -6.0F, 12, 12, 12, 0.0F);
         this.setRotateAngle(this.head1, -1.1838568F, -0.3642502F, 0.7285004F);
         this.finB2 = new ModelRendererGlow(this, 0, 0, 240, false);
         this.finB2.mirror = true;
         this.finB2.setRotationPoint(-2.0F, -2.0F, -0.5F);
         this.finB2.addBox(-10.0F, -3.5F, 0.0F, 10, 5, 0, 0.0F);
         this.setRotateAngle(this.finB2, 0.0F, 0.0F, 0.27314404F);
         this.finA3 = new ModelRendererGlow(this, 0, 0, 240, false);
         this.finA3.setRotationPoint(1.0F, -2.0F, -0.5F);
         this.finA3.addBox(0.0F, -3.5F, 0.0F, 10, 5, 0, 0.0F);
         this.setRotateAngle(this.finA3, 0.0F, 0.0F, -0.27314404F);
         this.camera3c = new ModelRenderer(this, 4, 6);
         this.camera3c.setRotationPoint(0.05F, 8.0F, 0.0F);
         this.camera3c.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
         this.setRotateAngle(this.camera3c, 1.4114478F, 0.18203785F, 0.0F);
         this.finB3 = new ModelRendererGlow(this, 0, 0, 240, false);
         this.finB3.mirror = true;
         this.finB3.setRotationPoint(-1.0F, -2.0F, -0.5F);
         this.finB3.addBox(-10.0F, -3.5F, 0.0F, 10, 5, 0, 0.0F);
         this.setRotateAngle(this.finB3, 0.0F, 0.0F, 0.27314404F);
         this.finA1 = new ModelRendererGlow(this, 0, 0, 240, false);
         this.finA1.setRotationPoint(2.0F, 0.0F, -0.5F);
         this.finA1.addBox(0.0F, -3.5F, 0.0F, 10, 5, 0, 0.0F);
         this.setRotateAngle(this.finA1, 0.0F, 0.0F, -0.27314404F);
         this.camera4b = new ModelRenderer(this, 0, 6);
         this.camera4b.setRotationPoint(-3.05F, 11.5F, -6.5F);
         this.camera4b.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.camera4b, (float) -Math.PI, -0.13665928F, -1.4570009F);
         this.camera6d = new ModelRendererGlow(this, 24, 12, 200, false);
         this.camera6d.setRotationPoint(-0.05F, 6.0F, 0.0F);
         this.camera6d.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.camera6d, 0.95609134F, 0.22759093F, 0.0F);
         this.camera1b = new ModelRenderer(this, 0, 6);
         this.camera1b.setRotationPoint(3.05F, 11.5F, -8.5F);
         this.camera1b.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.camera1b, -2.959555F, 0.045553092F, 1.9123572F);
         this.camera5b = new ModelRenderer(this, 0, 6);
         this.camera5b.setRotationPoint(3.05F, 11.5F, -4.5F);
         this.camera5b.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.camera5b, 2.9140017F, 0.27314404F, 1.0016445F);
         this.camera1c = new ModelRenderer(this, 4, 6);
         this.camera1c.setRotationPoint(0.05F, 8.0F, 0.0F);
         this.camera1c.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
         this.setRotateAngle(this.camera1c, 1.6845918F, 0.18203785F, 0.0F);
         this.camera3d = new ModelRendererGlow(this, 24, 12, 200, false);
         this.camera3d.setRotationPoint(0.05F, 6.0F, 0.0F);
         this.camera3d.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.camera3d, 0.4098033F, 0.0F, 0.0F);
         this.core1 = new ModelRenderer(this, 55, 0);
         this.core1.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.core1.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.head0 = new ModelRenderer(this, 0, 8);
         this.head0.setRotationPoint(0.0F, 14.0F, -10.0F);
         this.head0.addBox(-4.0F, -11.0F, -4.0F, 8, 16, 8, 0.0F);
         this.setRotateAngle(this.head0, -1.7301449F, 0.13665928F, 0.7285004F);
         this.camera2d = new ModelRendererGlow(this, 24, 12, 200, false);
         this.camera2d.setRotationPoint(-0.05F, 6.0F, 0.0F);
         this.camera2d.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
         this.body1 = new ModelRenderer(this, 39, 0);
         this.body1.setRotationPoint(0.0F, 10.5F, 7.0F);
         this.body1.addBox(-2.5F, -8.5F, -2.0F, 5, 10, 3, 0.0F);
         this.setRotateAngle(this.body1, -1.3658947F, 0.0F, 0.0F);
         this.camera5c = new ModelRenderer(this, 4, 6);
         this.camera5c.setRotationPoint(0.05F, 8.0F, 0.0F);
         this.camera5c.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
         this.setRotateAngle(this.camera5c, 1.1383038F, 0.4098033F, 0.091106184F);
         this.finA2 = new ModelRendererGlow(this, 0, 0, 240, false);
         this.finA2.setRotationPoint(2.0F, -2.0F, -0.5F);
         this.finA2.addBox(0.0F, -3.5F, 0.0F, 10, 5, 0, 0.0F);
         this.setRotateAngle(this.finA2, 0.0F, 0.0F, -0.27314404F);
         this.core2 = new ModelRenderer(this, 55, 0);
         this.core2.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.core2.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.core2, 0.7285004F, 0.91053826F, 0.18203785F);
         this.camera2c = new ModelRenderer(this, 4, 6);
         this.camera2c.setRotationPoint(-0.05F, 8.0F, 0.0F);
         this.camera2c.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
         this.setRotateAngle(this.camera2c, 1.6845918F, -0.18203785F, 0.0F);
         this.camera3b = new ModelRenderer(this, 0, 6);
         this.camera3b.setRotationPoint(3.05F, 11.5F, -6.5F);
         this.camera3b.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.camera3b, (float) -Math.PI, 0.13665928F, 1.4570009F);
         this.core3 = new ModelRenderer(this, 55, 0);
         this.core3.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.core3.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.core3, -0.8651597F, 1.2747885F, -1.2747885F);
         this.camera4d = new ModelRendererGlow(this, 24, 12, 200, false);
         this.camera4d.setRotationPoint(-0.05F, 6.0F, 0.0F);
         this.camera4d.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.camera4d, 0.4098033F, 0.0F, 0.0F);
         this.camera6c = new ModelRenderer(this, 4, 6);
         this.camera6c.setRotationPoint(-0.05F, 8.0F, 0.0F);
         this.camera6c.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
         this.setRotateAngle(this.camera6c, 1.1383038F, -0.4098033F, -0.091106184F);
         this.teeth1 = new ModelRendererLimited(this, 0, 45, true, true, false, false, true, true);
         this.teeth1.setRotationPoint(0.0F, 12.0F, -9.0F);
         this.teeth1.addBox(-4.0F, -4.0F, -7.0F, 8, 5, 8, 0.0F);
         this.teeth2 = new ModelRendererLimited(this, 0, 51, true, true, false, false, true, true);
         this.teeth2.setRotationPoint(0.0F, 12.0F, -9.0F);
         this.teeth2.addBox(-4.0F, -1.0F, -7.0F, 8, 5, 8, 0.0F);
         this.camera2b = new ModelRenderer(this, 0, 6);
         this.camera2b.setRotationPoint(-3.05F, 11.5F, -8.5F);
         this.camera2b.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.camera2b, -2.959555F, -0.045553092F, -1.9123572F);
         this.body3 = new ModelRenderer(this, 33, 16);
         this.body3.setRotationPoint(0.0F, -9.0F, 0.0F);
         this.body3.addBox(-1.5F, -8.5F, -1.5F, 3, 8, 2, 0.0F);
         this.setRotateAngle(this.body3, -0.31869712F, 0.0F, 0.0F);
         this.camera5d = new ModelRendererGlow(this, 24, 12, 200, false);
         this.camera5d.setRotationPoint(0.05F, 6.0F, 0.0F);
         this.camera5d.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.camera5d, 0.95609134F, -0.22759093F, 0.0F);
         this.finB1 = new ModelRendererGlow(this, 0, 0, 240, false);
         this.finB1.mirror = true;
         this.finB1.setRotationPoint(-2.0F, 0.0F, -0.5F);
         this.finB1.addBox(-10.0F, -3.5F, 0.0F, 10, 5, 0, 0.0F);
         this.setRotateAngle(this.finB1, 0.0F, 0.0F, 0.27314404F);
         this.camera1d = new ModelRendererGlow(this, 24, 12, 200, false);
         this.camera1d.setRotationPoint(0.05F, 6.0F, 0.0F);
         this.camera1d.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
         this.head2 = new ModelRenderer(this, 32, 16);
         this.head2.setRotationPoint(0.0F, 7.0F, 1.7F);
         this.head2.addBox(-6.0F, -6.0F, -6.0F, 12, 12, 12, 0.0F);
         this.setRotateAngle(this.head2, -1.3658947F, (float) (-Math.PI / 15), 0.7740535F);
         this.camera4c = new ModelRenderer(this, 4, 6);
         this.camera4c.setRotationPoint(-0.05F, 8.0F, 0.0F);
         this.camera4c.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
         this.setRotateAngle(this.camera4c, 1.4114478F, -0.18203785F, 0.0F);
         this.head3 = new ModelRenderer(this, 0, 32);
         this.head3.setRotationPoint(0.0F, 8.5F, 10.0F);
         this.head3.addBox(-5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F);
         this.setRotateAngle(this.head3, -1.6845918F, 0.091106184F, 0.7740535F);
         this.camera6b = new ModelRenderer(this, 0, 6);
         this.camera6b.setRotationPoint(-3.05F, 11.5F, -4.5F);
         this.camera6b.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.camera6b, 2.9140017F, -0.27314404F, -1.0016445F);
         this.body1.addChild(this.body2);
         this.body2.addChild(this.finB2);
         this.body3.addChild(this.finA3);
         this.camera3b.addChild(this.camera3c);
         this.body3.addChild(this.finB3);
         this.body1.addChild(this.finA1);
         this.camera6c.addChild(this.camera6d);
         this.camera1b.addChild(this.camera1c);
         this.camera3c.addChild(this.camera3d);
         this.camera2c.addChild(this.camera2d);
         this.camera5b.addChild(this.camera5c);
         this.body2.addChild(this.finA2);
         this.camera2b.addChild(this.camera2c);
         this.camera4c.addChild(this.camera4d);
         this.camera6b.addChild(this.camera6c);
         this.body2.addChild(this.body3);
         this.camera5c.addChild(this.camera5d);
         this.body1.addChild(this.finB1);
         this.camera1c.addChild(this.camera1d);
         this.camera4b.addChild(this.camera4c);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         f5 *= 1.2F;
         this.teeth1.rotateAngleY = f3 * 0.007453292F;
         this.teeth1.rotateAngleX = f4 * 0.007453292F;
         this.teeth2.rotateAngleY = f3 * 0.007453292F;
         this.teeth2.rotateAngleX = f4 * 0.007453292F;
         if (an1 > 0) {
            float anim = 100 - an1 + Minecraft.getMinecraft().getRenderPartialTicks();
            float transl = GetMOP.getfromto(anim, 0.0F, 10.0F) - GetMOP.getfromto(anim, 11.0F, 25.0F);
            float ang = (GetMOP.getfromto(anim, 4.0F, 8.0F) - GetMOP.getfromto(anim, 9.0F, 11.0F)) * -0.85F;
            this.teeth1.rotateAngleX += ang;
            this.teeth2.rotateAngleX -= ang;
            this.teeth1.offsetZ = -0.6875F * transl;
            this.teeth2.offsetZ = -0.6875F * transl;
         }

         this.setRotateAngle(this.body1, -1.3658947F, 0.0F, 0.0F);
         this.setRotateAngle(this.body2, -0.31869712F, 0.0F, 0.0F);
         this.setRotateAngle(this.body3, -0.31869712F, 0.0F, 0.0F);
         this.body1.rotateAngleX = this.body1.rotateAngleX + MathHelper.sin(AnimationTimer.tick / 40.0F) * 0.16F;
         this.body2.rotateAngleX = this.body2.rotateAngleX + MathHelper.sin((AnimationTimer.tick - 30) / 40.0F) * 0.16F;
         this.body3.rotateAngleX = this.body3.rotateAngleX + MathHelper.sin((AnimationTimer.tick - 60) / 40.0F) * 0.16F;
         float animTime = AnimationTimer.tick / 30.0F;
         this.setRotateFin(this.finA1, this.finB1, animTime);
         this.setRotateFin(this.finA2, this.finB2, animTime + 2.0F);
         this.setRotateFin(this.finA3, this.finB3, animTime + 4.0F);
         float r1 = AnimationTimer.rainbow1;
         float r2 = AnimationTimer.rainbow2;
         float r3 = AnimationTimer.rainbow3;
         int tick = AnimationTimer.tick;
         this.setRotateAngle(this.core1, r1 * 0.27F, tick * 0.087F, r2 * 0.115F);
         this.setRotateAngle(this.core2, r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         this.setRotateAngle(this.core3, tick * 0.087F, r3 * 0.185F, r2 * 0.165F);
         this.head1.render(f5);
         this.camera4b.render(f5);
         this.camera1b.render(f5);
         this.camera5b.render(f5);
         this.head0.render(f5);
         this.camera3b.render(f5);
         this.camera2b.render(f5);
         this.head2.render(f5);
         this.head3.render(f5);
         this.camera6b.render(f5);
         GlStateManager.enableBlend();
         alphaGlowDisable();
         this.body1.render(f5);
         light(240, false);
         this.teeth1.render(f5);
         this.teeth2.render(f5);
         alphaGlow();
         this.core1.render(f5);
         this.core2.render(f5);
         this.core3.render(f5);
         returnlight();
         alphaGlowDisable();
         GlStateManager.disableBlend();
      }

      public void setRotateFin(ModelRenderer renderer, ModelRenderer rendererOpposite, float animTime) {
         renderer.rotateAngleZ = -0.27314404F - MathHelper.sin(animTime) / 4.0F;
         renderer.rotateAngleY = -MathHelper.cos(animTime) / 4.0F;
         renderer.rotateAngleX = MathHelper.cos(animTime) / 6.0F;
         rendererOpposite.rotateAngleZ = -renderer.rotateAngleZ;
         rendererOpposite.rotateAngleY = -renderer.rotateAngleY;
         rendererOpposite.rotateAngleX = renderer.rotateAngleX;
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class CloudbugModel extends AbstractMobModel {
      public ModelRenderer core1;
      public ModelRenderer core2;
      public ModelRenderer shape1;
      public ModelRenderer leg1;
      public ModelRenderer leg2;
      public ModelRenderer leg3;
      public ModelRenderer cloud;
      public ModelRenderer cloud_1;
      public ModelRenderer cloud_2;
      public ModelRenderer cloud_3;
      public ModelRenderer cloud_4;
      public ModelRenderer cloud_5;
      public ModelRenderer cloud_6;
      public ModelRenderer cloud_7;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer camera1a;
      public ModelRenderer camera2a;
      public ModelRenderer camera3a;
      public ModelRenderer camera1b;
      public ModelRenderer camera1c;
      public ModelRenderer camera1d;
      public ModelRenderer camera2b;
      public ModelRenderer camera2c;
      public ModelRenderer camera2d;
      public ModelRenderer camera3b;
      public ModelRenderer camera3c;
      public ModelRenderer camera3d;
      public ModelRenderer lega1;
      public ModelRenderer lega2;
      public ModelRenderer lega3;

      public CloudbugModel() {
         this.textureWidth = 32;
         this.textureHeight = 32;
         this.shape2 = new ModelRenderer(this, 0, 6);
         this.shape2.setRotationPoint(0.0F, 5.5F, 0.0F);
         this.shape2.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
         this.setRotateAngle(this.shape2, -0.63739425F, 0.0F, 0.0F);
         this.cloud_4 = new ModelRenderer(this, 0, 16);
         this.cloud_4.setRotationPoint(3.0F, -1.0F, 1.0F);
         this.cloud_4.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
         this.cloud = new ModelRenderer(this, 0, 16);
         this.cloud.setRotationPoint(1.0F, -2.0F, 2.0F);
         this.cloud.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
         this.cloud_5 = new ModelRenderer(this, 0, 16);
         this.cloud_5.setRotationPoint(4.0F, 1.0F, 4.0F);
         this.cloud_5.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
         this.cloud_2 = new ModelRenderer(this, 0, 16);
         this.cloud_2.setRotationPoint(-4.0F, -1.0F, 4.0F);
         this.cloud_2.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
         this.shape3 = new ModelRenderer(this, 16, 0);
         this.shape3.setRotationPoint(0.0F, 3.5F, 0.0F);
         this.shape3.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
         this.setRotateAngle(this.shape3, -0.95609134F, 0.0F, 0.0F);
         this.camera3c = new ModelRenderer(this, 15, 0);
         this.camera3c.setRotationPoint(0.05F, 4.0F, 0.0F);
         this.camera3c.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
         this.setRotateAngle(this.camera3c, 1.9123572F, 0.0F, 0.0F);
         this.leg3 = new ModelRenderer(this, 28, 0);
         this.leg3.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.leg3.addBox(-0.5F, 4.5F, 1.2F, 1, 11, 1, 0.1F);
         this.setRotateAngle(this.leg3, -2.1399481F, (float) Math.PI, 0.0F);
         this.cloud_6 = new ModelRenderer(this, 0, 16);
         this.cloud_6.setRotationPoint(0.0F, 0.0F, 5.0F);
         this.cloud_6.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
         this.camera1d = new ModelRenderer(this, 6, 5);
         this.camera1d.setRotationPoint(0.05F, 1.8F, 0.0F);
         this.camera1d.addBox(0.0F, -1.1F, -0.8F, 0, 2, 1, 0.0F);
         this.setRotateAngle(this.camera1d, 0.68294734F, 0.0F, 0.0F);
         this.camera3b = new ModelRenderer(this, 16, 7);
         this.camera3b.setRotationPoint(0.05F, 3.0F, 0.0F);
         this.camera3b.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.camera3b, 1.7301449F, 0.0F, 0.0F);
         this.lega1 = new ModelRenderer(this, 28, 0);
         this.lega1.setRotationPoint(0.0F, 15.5F, 2.0F);
         this.lega1.addBox(-0.5F, 0.0F, -0.5F, 1, 11, 1, 0.0F);
         this.setRotateAngle(this.lega1, -1.6845918F, 0.0F, 0.0F);
         this.lega3 = new ModelRenderer(this, 28, 0);
         this.lega3.setRotationPoint(0.0F, 15.5F, 2.0F);
         this.lega3.addBox(-0.5F, 0.0F, -0.5F, 1, 11, 1, 0.0F);
         this.setRotateAngle(this.lega3, -1.6845918F, 0.0F, 0.0F);
         this.camera3d = new ModelRenderer(this, 6, 5);
         this.camera3d.setRotationPoint(0.05F, 1.8F, 0.0F);
         this.camera3d.addBox(0.0F, -1.1F, -0.8F, 0, 2, 1, 0.0F);
         this.setRotateAngle(this.camera3d, 0.68294734F, 0.0F, 0.0F);
         this.camera2a = new ModelRenderer(this, 12, 2);
         this.camera2a.setRotationPoint(1.0F, 3.8F, -1.0F);
         this.camera2a.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
         this.setRotateAngle(this.camera2a, -1.9123572F, (float) (-Math.PI / 3), 0.0F);
         this.lega2 = new ModelRenderer(this, 28, 0);
         this.lega2.setRotationPoint(0.0F, 15.5F, 2.0F);
         this.lega2.addBox(-0.5F, 0.0F, -0.5F, 1, 11, 1, 0.0F);
         this.setRotateAngle(this.lega2, -1.6845918F, 0.0F, 0.0F);
         this.camera1c = new ModelRenderer(this, 15, 0);
         this.camera1c.setRotationPoint(0.05F, 4.0F, 0.0F);
         this.camera1c.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
         this.setRotateAngle(this.camera1c, 1.9123572F, 0.0F, 0.0F);
         this.camera2c = new ModelRenderer(this, 15, 0);
         this.camera2c.setRotationPoint(0.05F, 4.0F, 0.0F);
         this.camera2c.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
         this.setRotateAngle(this.camera2c, 1.9123572F, 0.0F, 0.0F);
         this.leg1 = new ModelRenderer(this, 28, 0);
         this.leg1.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.leg1.addBox(-0.5F, 4.5F, 1.2F, 1, 11, 1, 0.1F);
         this.setRotateAngle(this.leg1, -2.1399481F, 1.2292354F, 0.0F);
         this.camera3a = new ModelRenderer(this, 12, 2);
         this.camera3a.setRotationPoint(-1.0F, 3.8F, -1.0F);
         this.camera3a.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
         this.setRotateAngle(this.camera3a, -1.9123572F, (float) (Math.PI / 3), 0.0F);
         this.camera1a = new ModelRenderer(this, 12, 2);
         this.camera1a.setRotationPoint(0.0F, 3.8F, 1.0F);
         this.camera1a.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
         this.setRotateAngle(this.camera1a, -1.9123572F, (float) Math.PI, 0.0F);
         this.camera2d = new ModelRenderer(this, 6, 5);
         this.camera2d.setRotationPoint(0.05F, 1.8F, 0.0F);
         this.camera2d.addBox(0.0F, -1.1F, -0.8F, 0, 2, 1, 0.0F);
         this.setRotateAngle(this.camera2d, 0.68294734F, 0.0F, 0.0F);
         this.camera2b = new ModelRenderer(this, 16, 7);
         this.camera2b.setRotationPoint(0.05F, 3.0F, 0.0F);
         this.camera2b.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.camera2b, 1.7301449F, 0.0F, 0.0F);
         this.cloud_7 = new ModelRenderer(this, 0, 16);
         this.cloud_7.setRotationPoint(-3.0F, 0.0F, 0.0F);
         this.cloud_7.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
         this.leg2 = new ModelRenderer(this, 28, 0);
         this.leg2.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.leg2.addBox(-0.5F, 4.5F, 1.2F, 1, 11, 1, 0.1F);
         this.setRotateAngle(this.leg2, -2.1399481F, -1.2292354F, 0.0F);
         this.core1 = new ModelRenderer(this, 16, 8);
         this.core1.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.core1.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
         this.shape1 = new ModelRenderer(this, 0, 0);
         this.shape1.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.shape1.addBox(-1.0F, 2.0F, -1.0F, 2, 4, 2, -0.1F);
         this.setRotateAngle(this.shape1, 0.27314404F, 0.0F, 0.0F);
         this.cloud_3 = new ModelRenderer(this, 0, 16);
         this.cloud_3.setRotationPoint(-1.0F, 2.0F, 3.0F);
         this.cloud_3.addBox(-4.0F, -6.0F, -4.0F, 8, 8, 8, 0.0F);
         this.core2 = new ModelRenderer(this, 16, 8);
         this.core2.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.core2.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
         this.setRotateAngle(this.core2, 0.7285004F, 0.91053826F, 0.18203785F);
         this.cloud_1 = new ModelRenderer(this, 0, 16);
         this.cloud_1.setRotationPoint(1.0F, 1.0F, -2.0F);
         this.cloud_1.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
         this.camera1b = new ModelRenderer(this, 16, 7);
         this.camera1b.setRotationPoint(0.05F, 3.0F, 0.0F);
         this.camera1b.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.camera1b, 1.7301449F, 0.0F, 0.0F);
         this.shape1.addChild(this.shape2);
         this.shape2.addChild(this.shape3);
         this.camera3b.addChild(this.camera3c);
         this.camera1c.addChild(this.camera1d);
         this.camera3a.addChild(this.camera3b);
         this.leg1.addChild(this.lega1);
         this.leg3.addChild(this.lega3);
         this.camera3c.addChild(this.camera3d);
         this.shape3.addChild(this.camera2a);
         this.leg2.addChild(this.lega2);
         this.camera1b.addChild(this.camera1c);
         this.camera2b.addChild(this.camera2c);
         this.shape3.addChild(this.camera3a);
         this.shape3.addChild(this.camera1a);
         this.camera2c.addChild(this.camera2d);
         this.camera2a.addChild(this.camera2b);
         this.camera1a.addChild(this.camera1b);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float r1 = AnimationTimer.rainbow1;
         float r2 = AnimationTimer.rainbow2;
         float r3 = AnimationTimer.rainbow3;
         int tick = AnimationTimer.tick;
         this.setRotateAngle(this.core1, r1 * 0.27F, tick * 0.087F, r2 * 0.115F);
         this.setRotateAngle(this.core2, r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         float angl1 = 0.0F;
         float angl2 = 0.0F;
         float angl3 = 0.0F;
         int clouds = 10;
         if (isAbstractMob) {
            int i1111 = 15;
            int v2 = (int)((AbstractMob)entity).var2;
            clouds = ((AbstractMob)entity).var1;
            angl1 = ((v2 & i1111) - 8) * 0.02453292F;
            angl2 = ((v2 >> 4 & i1111) - 8) * 0.02453292F;
            angl3 = ((v2 >> 8 & i1111) - 8) * 0.02453292F;
         }

         float motionAdd = (float)entity.motionY * 0.42F;
         float y = f3 * (float) (Math.PI / 180.0);
         float x = f4 * 0.00581776F;
         this.shape1.rotateAngleY = y;
         this.shape1.rotateAngleX = 0.27314404F + x;
         this.shape2.rotateAngleX = -0.63739425F + x;
         this.shape3.rotateAngleX = -0.95609134F + x;
         this.leg1.rotateAngleX = -2.1399481F + angl1;
         this.lega1.rotateAngleX = -1.6845918F + angl1 - motionAdd;
         this.leg2.rotateAngleX = -2.1399481F + angl2;
         this.lega2.rotateAngleX = -1.6845918F + angl2 - motionAdd;
         this.leg3.rotateAngleX = -2.1399481F + angl3;
         this.lega3.rotateAngleX = -1.6845918F + angl3 - motionAdd;
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.leg3.offsetX, this.leg3.offsetY, this.leg3.offsetZ);
         GlStateManager.translate(this.leg3.rotationPointX * f5, this.leg3.rotationPointY * f5, this.leg3.rotationPointZ * f5);
         GlStateManager.scale(0.8, 0.8, 0.5);
         GlStateManager.translate(-this.leg3.offsetX, -this.leg3.offsetY, -this.leg3.offsetZ);
         GlStateManager.translate(-this.leg3.rotationPointX * f5, -this.leg3.rotationPointY * f5, -this.leg3.rotationPointZ * f5);
         this.leg3.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.leg1.offsetX, this.leg1.offsetY, this.leg1.offsetZ);
         GlStateManager.translate(this.leg1.rotationPointX * f5, this.leg1.rotationPointY * f5, this.leg1.rotationPointZ * f5);
         GlStateManager.scale(0.5, 0.8, 0.8);
         GlStateManager.translate(-this.leg1.offsetX, -this.leg1.offsetY, -this.leg1.offsetZ);
         GlStateManager.translate(-this.leg1.rotationPointX * f5, -this.leg1.rotationPointY * f5, -this.leg1.rotationPointZ * f5);
         this.leg1.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.leg2.offsetX, this.leg2.offsetY, this.leg2.offsetZ);
         GlStateManager.translate(this.leg2.rotationPointX * f5, this.leg2.rotationPointY * f5, this.leg2.rotationPointZ * f5);
         GlStateManager.scale(0.5, 0.8, 0.8);
         GlStateManager.translate(-this.leg2.offsetX, -this.leg2.offsetY, -this.leg2.offsetZ);
         GlStateManager.translate(-this.leg2.rotationPointX * f5, -this.leg2.rotationPointY * f5, -this.leg2.rotationPointZ * f5);
         this.leg2.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
         GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
         GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
         this.shape1.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.enableBlend();
         light(240, false);
         alphaGlow();
         this.core1.render(f5);
         this.core2.render(f5);
         returnlight();
         alphaGlowDisable();
         if ((clouds & 1) > 0) {
            this.cloud_6.render(f5);
         }

         if ((clouds & 2) > 0) {
            this.cloud_7.render(f5);
         }

         if ((clouds & 4) > 0) {
            this.cloud_3.render(f5);
         }

         if ((clouds & 8) > 0) {
            this.cloud_1.render(f5);
         }

         if ((clouds & 16) > 0) {
            this.cloud_4.render(f5);
         }

         if ((clouds & 32) > 0) {
            this.cloud.render(f5);
         }

         if ((clouds & 64) > 0) {
            this.cloud_5.render(f5);
         }

         if ((clouds & 128) > 0) {
            this.cloud_2.render(f5);
         }

         GlStateManager.disableBlend();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class GustModel extends AbstractMobModel {
      public ModelRenderer headbase;
      public ModelRenderer shape;
      public ModelRenderer shape2;
      public ModelRenderer wind2;
      public ModelRenderer wind3;
      public ModelRenderer winddown1;
      public ModelRenderer winddown2;
      public ModelRenderer winddown3;
      public ModelRenderer shape4;
      public ModelRenderer shape6;
      public ModelRenderer headbase2;
      public ModelRenderer head;
      public ModelRenderer shapea;
      public ModelRenderer sting;
      public ModelRenderer shape3;
      public ModelRenderer sting3;
      public ModelRenderer sting2;
      public ModelRenderer shape5;
      public ModelRenderer sting5;
      public ModelRenderer sting4;
      public ModelRenderer shapea2;
      public ModelRenderer sting6;
      public ModelRenderer wind1;
      public ModelRenderer windhead;
      public ModelRenderer windhead2;

      public GustModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.windhead2 = new ModelRendererLimited(this, 31, 2, false, false, false, false, true, false);
         this.windhead2.mirror = true;
         this.windhead2.setRotationPoint(0.0F, 1.0F, -1.9F);
         this.windhead2.addBox(-2.5F, -7.5F, -3.8F, 14, 10, 0, 0.0F);
         this.setRotateAngle(this.windhead2, 0.13665928F, -1.821251F, -0.3642502F);
         this.wind2 = new ModelRendererLimited(this, 50, 4, false, false, false, false, true, false);
         this.wind2.setRotationPoint(-2.0F, 8.0F, -0.9F);
         this.wind2.addBox(-5.0F, -25.0F, 2.0F, 10, 25, 0, 0.0F);
         this.setRotateAngle(this.wind2, -0.13665928F, 0.3642502F, -0.27314404F);
         this.headbase = new ModelRenderer(this, 35, 22);
         this.headbase.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.headbase.addBox(-1.0F, -0.5F, -1.0F, 2, 2, 2, 0.0F);
         this.shape = new ModelRenderer(this, 0, 5);
         this.shape.setRotationPoint(-0.2F, 8.0F, 0.0F);
         this.shape.addBox(-4.0F, -4.0F, -2.0F, 2, 4, 2, 0.0F);
         this.setRotateAngle(this.shape, -0.091106184F, 0.045553092F, -0.91053826F);
         this.sting5 = new ModelRenderer(this, 6, 24);
         this.sting5.mirror = true;
         this.sting5.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.sting5.addBox(-4.1F, -0.5F, 0.5F, 3, 8, 0, 0.0F);
         this.windhead = new ModelRendererLimited(this, 31, 2, false, false, false, false, true, false);
         this.windhead.setRotationPoint(0.0F, 1.0F, -1.9F);
         this.windhead.addBox(-11.5F, -7.5F, -3.8F, 14, 10, 0, 0.0F);
         this.setRotateAngle(this.windhead, 0.13665928F, 1.821251F, 0.3642502F);
         this.sting4 = new ModelRenderer(this, 6, 13);
         this.sting4.mirror = true;
         this.sting4.setRotationPoint(0.0F, 1.0F, 0.0F);
         this.sting4.addBox(-4.5F, -0.5F, 0.5F, 4, 11, 0, 0.0F);
         this.sting3 = new ModelRenderer(this, 6, 24);
         this.sting3.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.sting3.addBox(1.1F, -0.5F, 0.5F, 3, 8, 0, 0.0F);
         this.shape4 = new ModelRenderer(this, 0, 11);
         this.shape4.mirror = true;
         this.shape4.setRotationPoint(1.0F, 11.0F, -1.0F);
         this.shape4.addBox(-1.5F, 1.0F, 0.0F, 2, 8, 1, 0.0F);
         this.setRotateAngle(this.shape4, 0.0F, 0.0F, -0.7285004F);
         this.headbase2 = new ModelRenderer(this, 35, 22);
         this.headbase2.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.headbase2.addBox(-1.0F, -0.5F, -1.0F, 2, 2, 2, 0.2F);
         this.shapea2 = new ModelRenderer(this, 0, 0);
         this.shapea2.mirror = true;
         this.shapea2.setRotationPoint(2.0F, -2.0F, 0.0F);
         this.shapea2.addBox(0.0F, -4.0F, -1.5F, 2, 4, 1, 0.0F);
         this.setRotateAngle(this.shapea2, -0.091106184F, 0.0F, -0.31869712F);
         this.sting6 = new ModelRenderer(this, 8, 5);
         this.sting6.mirror = true;
         this.sting6.setRotationPoint(2.0F, -2.0F, 0.0F);
         this.sting6.addBox(-4.2F, -2.5F, -1.0F, 3, 8, 0, 0.0F);
         this.sting = new ModelRenderer(this, 8, 5);
         this.sting.setRotationPoint(-2.0F, -2.0F, 0.0F);
         this.sting.addBox(1.2F, -2.5F, -1.0F, 3, 8, 0, 0.0F);
         this.winddown1 = new ModelRendererLimited(this, 24, 14, false, false, false, false, true, false);
         this.winddown1.mirror = true;
         this.winddown1.setRotationPoint(0.0F, 12.0F, 0.1F);
         this.winddown1.addBox(-5.0F, 3.0F, -0.5F, 4, 18, 0, 0.0F);
         this.setRotateAngle(this.winddown1, 0.0F, 0.0F, 0.045553092F);
         this.winddown2 = new ModelRendererLimited(this, 16, 5, false, false, false, false, true, false);
         this.winddown2.setRotationPoint(0.0F, 12.0F, 0.1F);
         this.winddown2.addBox(-2.0F, 0.0F, -0.2F, 4, 25, 0, 0.0F);
         this.winddown3 = new ModelRendererLimited(this, 24, 14, false, false, false, false, true, false);
         this.winddown3.setRotationPoint(0.0F, 12.0F, 0.1F);
         this.winddown3.addBox(1.0F, 3.0F, -0.5F, 4, 18, 0, 0.0F);
         this.setRotateAngle(this.winddown3, 0.0F, 0.0F, -0.045553092F);
         this.wind3 = new ModelRendererLimited(this, 50, 4, false, false, false, false, true, false);
         this.wind3.mirror = true;
         this.wind3.setRotationPoint(2.0F, 8.0F, -0.9F);
         this.wind3.addBox(-5.0F, -25.0F, 2.0F, 10, 25, 0, 0.0F);
         this.setRotateAngle(this.wind3, -0.13665928F, -0.3642502F, 0.27314404F);
         this.sting2 = new ModelRenderer(this, 6, 13);
         this.sting2.setRotationPoint(0.0F, 1.0F, 0.0F);
         this.sting2.addBox(0.5F, -0.5F, 0.5F, 4, 11, 0, 0.0F);
         this.wind1 = new ModelRendererLimited(this, 50, 4, false, false, false, false, true, false);
         this.wind1.setRotationPoint(0.0F, 0.0F, -0.9F);
         this.wind1.addBox(-5.0F, -25.0F, 0.0F, 10, 25, 0, 0.0F);
         this.setRotateAngle(this.wind1, 0.045553092F, 0.0F, 0.0F);
         this.head = new ModelRenderer(this, 35, 26);
         this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.head.addBox(-3.5F, -3.5F, -4.0F, 5, 5, 0, 0.0F);
         this.setRotateAngle(this.head, 0.0F, 0.0F, (float) (Math.PI / 4));
         this.shape5 = new ModelRenderer(this, 0, 20);
         this.shape5.mirror = true;
         this.shape5.setRotationPoint(0.0F, 8.5F, 0.1F);
         this.shape5.addBox(-2.0F, 0.2F, 0.0F, 2, 8, 1, 0.0F);
         this.setRotateAngle(this.shape5, 0.0F, 0.0F, 0.27314404F);
         this.shape6 = new ModelRenderer(this, 0, 5);
         this.shape6.mirror = true;
         this.shape6.setRotationPoint(0.2F, 8.0F, 0.0F);
         this.shape6.addBox(2.0F, -4.0F, -2.0F, 2, 4, 2, 0.0F);
         this.setRotateAngle(this.shape6, -0.091106184F, -0.045553092F, 0.91053826F);
         this.shape2 = new ModelRenderer(this, 0, 11);
         this.shape2.setRotationPoint(-1.0F, 11.0F, -1.0F);
         this.shape2.addBox(-0.5F, 1.0F, 0.0F, 2, 8, 1, 0.0F);
         this.setRotateAngle(this.shape2, 0.0F, 0.0F, 0.7285004F);
         this.shape3 = new ModelRenderer(this, 0, 20);
         this.shape3.setRotationPoint(0.0F, 8.5F, 0.1F);
         this.shape3.addBox(0.0F, 0.2F, 0.0F, 2, 8, 1, 0.0F);
         this.setRotateAngle(this.shape3, 0.0F, 0.0F, -0.27314404F);
         this.shapea = new ModelRenderer(this, 0, 0);
         this.shapea.setRotationPoint(-2.0F, -2.0F, 0.0F);
         this.shapea.addBox(-2.0F, -4.0F, -1.5F, 2, 4, 1, 0.0F);
         this.setRotateAngle(this.shapea, -0.091106184F, 0.0F, 0.31869712F);
         this.headbase2.addChild(this.windhead2);
         this.shape4.addChild(this.sting5);
         this.headbase2.addChild(this.windhead);
         this.shape5.addChild(this.sting4);
         this.shape2.addChild(this.sting3);
         this.shape6.addChild(this.shapea2);
         this.shapea2.addChild(this.sting6);
         this.shapea.addChild(this.sting);
         this.shape3.addChild(this.sting2);
         this.headbase2.addChild(this.wind1);
         this.headbase.addChild(this.head);
         this.shape4.addChild(this.shape5);
         this.shape2.addChild(this.shape3);
         this.shape.addChild(this.shapea);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float y = f3 * (float) (Math.PI / 180.0);
         float x = f4 * (float) (Math.PI / 180.0);
         this.headbase.rotateAngleY = y;
         this.headbase.rotateAngleX = x;
         this.headbase2.rotateAngleY = y;
         this.headbase2.rotateAngleX = x;
         float add = MathHelper.sin(AnimationTimer.tick / 30.0F) * 0.15F;
         float add2 = add * 2.0F;
         this.wind1.rotateAngleX = add2;
         this.wind2.rotateAngleX = -0.13665928F + add2;
         this.wind3.rotateAngleX = -0.13665928F + add2;
         this.winddown1.rotateAngleX = add;
         this.winddown2.rotateAngleX = add * 1.15F;
         this.winddown3.rotateAngleX = add;
         this.shape2.rotateAngleY = -add2;
         this.shape4.rotateAngleY = add2;
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape4.offsetX, this.shape4.offsetY, this.shape4.offsetZ);
         GlStateManager.translate(this.shape4.rotationPointX * f5, this.shape4.rotationPointY * f5, this.shape4.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.9, 0.8);
         GlStateManager.translate(-this.shape4.offsetX, -this.shape4.offsetY, -this.shape4.offsetZ);
         GlStateManager.translate(-this.shape4.rotationPointX * f5, -this.shape4.rotationPointY * f5, -this.shape4.rotationPointZ * f5);
         this.shape4.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape.offsetX, this.shape.offsetY, this.shape.offsetZ);
         GlStateManager.translate(this.shape.rotationPointX * f5, this.shape.rotationPointY * f5, this.shape.rotationPointZ * f5);
         GlStateManager.scale(0.6, 1.1, 0.9);
         GlStateManager.translate(-this.shape.offsetX, -this.shape.offsetY, -this.shape.offsetZ);
         GlStateManager.translate(-this.shape.rotationPointX * f5, -this.shape.rotationPointY * f5, -this.shape.rotationPointZ * f5);
         this.shape.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
         GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
         GlStateManager.scale(0.6, 0.9, 0.8);
         GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
         GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
         this.shape2.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape6.offsetX, this.shape6.offsetY, this.shape6.offsetZ);
         GlStateManager.translate(this.shape6.rotationPointX * f5, this.shape6.rotationPointY * f5, this.shape6.rotationPointZ * f5);
         GlStateManager.scale(0.6, 1.1, 0.9);
         GlStateManager.translate(-this.shape6.offsetX, -this.shape6.offsetY, -this.shape6.offsetZ);
         GlStateManager.translate(-this.shape6.rotationPointX * f5, -this.shape6.rotationPointY * f5, -this.shape6.rotationPointZ * f5);
         this.shape6.render(f5);
         GlStateManager.popMatrix();
         this.headbase.render(f5);
         GlStateManager.enableBlend();
         light(150, true);
         alphaGlow();
         boolean shader = false;
         if (ShaderMain.shaderCounter < ShaderMain.maxShaderCounter) {
            ShaderMain.WindShader.start();
            ARBShaderObjects.glUniform1fARB(ShaderMain.MoltenShader.getUniform("time"), AnimationTimer.tick / 40.0F);
            ShaderMain.shaderCounter++;
            shader = true;
         }

         this.winddown1.render(f5);
         this.winddown3.render(f5);
         this.headbase2.render(f5);
         this.wind3.render(f5);
         this.wind2.render(f5);
         this.winddown2.render(f5);
         if (shader) {
            ShaderMain.WindShader.stop();
         }

         returnlight();
         alphaGlowDisable();
         GlStateManager.disableBlend();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class HomingbirdModel extends AbstractMobModel {
      public ModelRenderer bodyA;
      public ModelRenderer bodyB;
      public ModelRenderer finA;
      public ModelRenderer bodyC;
      public ModelRenderer finB1;
      public ModelRenderer finB2;
      public ModelRenderer finB3;
      public ModelRenderer head;
      public ModelRenderer finC1;
      public ModelRenderer finC2;
      public ModelRenderer finC3;
      public ModelRenderer blade1;
      public ModelRenderer blade2;
      public ModelRenderer blade3;
      public ModelRenderer eye1;
      public ModelRenderer eye2;
      public ModelRenderer eye3;

      public HomingbirdModel() {
         this.textureWidth = 32;
         this.textureHeight = 16;
         this.blade1 = new ModelRendererLimited(this, 0, 0, false, true, false, false, false, false);
         this.blade1.setRotationPoint(0.0F, 0.0F, -2.0F);
         this.blade1.addBox(0.0F, -5.0F, -10.0F, 0, 5, 11, 0.0F);
         this.setRotateAngle(this.blade1, 0.045553092F, 0.0F, 0.0F);
         this.finB1 = new ModelRendererLimited(this, 24, -4, true, 240, false, false, true, false, false, false, false);
         this.finB1.setRotationPoint(0.0F, 0.5F, -2.5F);
         this.finB1.addBox(0.0F, -7.5F, -1.0F, 0, 7, 4, 0.0F);
         this.finB3 = new ModelRenderer(this, 24, -4);
         this.finB3.setRotationPoint(0.0F, 0.5F, -2.5F);
         this.finB3.addBox(0.0F, -7.5F, -1.0F, 0, 7, 4, 0.0F);
         this.setRotateAngle(this.finB3, 0.0F, 0.0F, (float) (-Math.PI * 2.0 / 3.0));
         this.bodyA = new ModelRenderer(this, 0, 0);
         this.bodyA.setRotationPoint(0.0F, 20.0F, 10.0F);
         this.bodyA.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 3, 0.0F);
         this.head = new ModelRenderer(this, 7, 4);
         this.head.setRotationPoint(0.0F, 0.0F, -4.0F);
         this.head.addBox(-1.5F, -1.5F, -2.0F, 3, 3, 2, 0.0F);
         this.finA = new ModelRendererLimited(this, 11, -1, true, 240, false, false, true, false, false, false, false);
         this.finA.setRotationPoint(0.0F, 0.0F, -0.5F);
         this.finA.addBox(0.0F, -3.5F, -1.0F, 0, 7, 10, 0.0F);
         this.eye1 = new ModelRendererGlow(this, 9, 0, 240, false);
         this.eye1.setRotationPoint(0.0F, 0.0F, -3.1F);
         this.eye1.addBox(0.0F, -2.0F, -1.5F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.eye1, -0.31869712F, -0.31869712F, (float) (Math.PI / 12));
         this.eye2 = new ModelRendererGlow(this, 9, 0, 240, false);
         this.eye2.mirror = true;
         this.eye2.setRotationPoint(0.0F, 0.0F, -3.1F);
         this.eye2.addBox(-2.0F, -2.0F, -1.5F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.eye2, -0.31869712F, 0.31869712F, (float) (-Math.PI / 12));
         this.bodyC = new ModelRenderer(this, 0, 6);
         this.bodyC.setRotationPoint(0.0F, 0.0F, -4.0F);
         this.bodyC.addBox(-1.0F, -1.0F, -3.5F, 2, 2, 3, 0.0F);
         this.finB2 = new ModelRendererLimited(this, 24, -4, true, 240, false, false, true, false, false, false, false);
         this.finB2.setRotationPoint(0.0F, 0.5F, -2.5F);
         this.finB2.addBox(0.0F, -7.5F, -1.0F, 0, 7, 4, 0.0F);
         this.setRotateAngle(this.finB2, 0.0F, 0.0F, (float) (Math.PI * 2.0 / 3.0));
         this.bodyB = new ModelRenderer(this, 22, 12);
         this.bodyB.setRotationPoint(0.0F, 0.0F, -2.5F);
         this.bodyB.addBox(-1.0F, -0.5F, -3.5F, 2, 1, 3, 0.0F);
         this.blade2 = new ModelRendererLimited(this, 0, 0, false, true, false, false, false, false);
         this.blade2.setRotationPoint(0.0F, 0.0F, -2.0F);
         this.blade2.addBox(0.0F, -5.0F, -10.0F, 0, 5, 11, 0.0F);
         this.setRotateAngle(this.blade2, 0.045553092F, 0.0F, (float) (Math.PI * 2.0 / 3.0));
         this.blade3 = new ModelRendererLimited(this, 0, 0, false, true, false, false, false, false);
         this.blade3.setRotationPoint(0.0F, 0.0F, -2.0F);
         this.blade3.addBox(0.0F, -5.0F, -10.0F, 0, 5, 11, 0.0F);
         this.setRotateAngle(this.blade3, 0.045553092F, 0.0F, (float) (-Math.PI * 2.0 / 3.0));
         this.eye3 = new ModelRendererGlow(this, 9, 0, 240, false);
         this.eye3.setRotationPoint(0.0F, -0.2F, -3.1F);
         this.eye3.addBox(0.0F, -2.0F, -1.5F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.eye3, -0.31869712F, -0.31869712F, (float) (Math.PI * 3.0 / 4.0));
         this.finC1 = new ModelRendererLimited(this, 17, -6, true, 240, false, false, true, false, false, false, false);
         this.finC1.setRotationPoint(0.0F, 0.0F, -2.5F);
         this.finC1.addBox(0.0F, -10.0F, -1.0F, 0, 9, 6, 0.0F);
         this.finC3 = new ModelRendererLimited(this, 17, -6, true, 240, false, false, true, false, false, false, false);
         this.finC3.setRotationPoint(0.0F, 0.0F, -2.5F);
         this.finC3.addBox(0.0F, -10.0F, -1.0F, 0, 9, 6, 0.0F);
         this.setRotateAngle(this.finC3, 0.0F, 0.0F, (float) (-Math.PI * 2.0 / 3.0));
         this.finC2 = new ModelRendererLimited(this, 17, -6, true, 240, false, false, true, false, false, false, false);
         this.finC2.setRotationPoint(0.0F, 0.0F, -2.5F);
         this.finC2.addBox(0.0F, -10.0F, -1.0F, 0, 9, 6, 0.0F);
         this.setRotateAngle(this.finC2, 0.0F, 0.0F, (float) (Math.PI * 2.0 / 3.0));
         this.head.addChild(this.blade1);
         this.bodyB.addChild(this.finB1);
         this.bodyB.addChild(this.finB3);
         this.bodyC.addChild(this.head);
         this.bodyA.addChild(this.finA);
         this.head.addChild(this.eye1);
         this.head.addChild(this.eye2);
         this.bodyB.addChild(this.bodyC);
         this.bodyB.addChild(this.finB2);
         this.bodyA.addChild(this.bodyB);
         this.head.addChild(this.blade2);
         this.head.addChild(this.blade3);
         this.head.addChild(this.eye3);
         this.bodyC.addChild(this.finC1);
         this.bodyC.addChild(this.finC3);
         this.bodyC.addChild(this.finC2);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float animHead = 0.0F;
         if (isAbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            if (mob.var2 < mob.var3) {
               mob.var2 = mob.var2 + Math.min(mob.var3 - mob.var2, 2.0F);
            } else if (mob.var2 > mob.var3) {
               mob.var2 = mob.var2 - Math.min(mob.var2 - mob.var3, 2.0F);
            }

            animHead = mob.var2 - 180.0F;
         }

         this.bodyC.rotateAngleY = f3 * 0.008453292F;
         this.bodyB.rotateAngleY = f3 * 0.007453292F;
         this.finA.rotateAngleY = f3 * -0.005453292F;
         this.bodyC.rotateAngleX = f4 * 0.008453292F;
         this.bodyB.rotateAngleX = f4 * 0.007453292F;
         this.head.rotateAngleZ = animHead * (float) (Math.PI / 180.0);
         AbstractMobModel.alphaGlowDisable();
         GlStateManager.enableBlend();
         this.bodyA.render(f5);
         GlStateManager.disableBlend();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class OphanimGuardModel extends AbstractMobModel {
      public ModelRenderer core1;
      public ModelRenderer core2;
      public ModelRenderer shape1;
      public ModelRenderer shapefire1;
      public ModelRenderer shapefire2;
      public ModelRenderer poison1;
      public ModelRenderer heat1;
      public ModelRenderer heat2;
      public ModelRenderer ice1;
      public ModelRenderer truster1;
      public ModelRenderer truster2;
      public ModelRenderer shapefire3;
      public ModelRenderer shapefire4;
      public ModelRenderer shape2;
      public ModelRenderer shapeeye;
      public ModelRenderer shapeeye2;
      public ModelRenderer shape2_1;
      public ModelRenderer poison2;
      public ModelRenderer ice2;

      public OphanimGuardModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.core2 = new ModelRenderer(this, 0, 0);
         this.core2.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.core2.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 1.0F);
         this.setRotateAngle(this.core2, -1.4114478F, 1.8668041F, -0.7285004F);
         this.heat1 = new ModelRenderer(this, 16, 20);
         this.heat1.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.heat1.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.heat1, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.shapefire3 = new ModelRenderer(this, 0, 23);
         this.shapefire3.setRotationPoint(-4.5F, 26.0F, 0.0F);
         this.shapefire3.addBox(-3.0F, 0.0F, 0.0F, 6, 9, 0, 0.0F);
         this.shapeeye2 = new ModelRenderer(this, 12, 23);
         this.shapeeye2.setRotationPoint(0.0F, 0.0F, -10.0F);
         this.shapeeye2.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 1, 0.0F);
         this.ice1 = new ModelRenderer(this, 28, 0);
         this.ice1.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.ice1.addBox(-3.0F, -13.0F, -6.0F, 6, 6, 0, 0.0F);
         this.shapefire4 = new ModelRenderer(this, 0, 17);
         this.shapefire4.setRotationPoint(-4.5F, 26.0F, 0.0F);
         this.shapefire4.addBox(0.0F, 0.0F, -3.0F, 0, 9, 6, 0.0F);
         this.shapefire2 = new ModelRenderer(this, 0, 17);
         this.shapefire2.setRotationPoint(4.5F, 26.0F, 0.0F);
         this.shapefire2.addBox(0.0F, 0.0F, -3.0F, 0, 9, 6, 0.0F);
         this.shapeeye = new ModelRendererGlow(this, 12, 0, 220, false);
         this.shapeeye.setRotationPoint(0.0F, 0.0F, -9.0F);
         this.shapeeye.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 2, 0.0F);
         this.setRotateAngle(this.shapeeye, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.heat2 = new ModelRenderer(this, 22, 8);
         this.heat2.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.heat2.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.1F);
         this.setRotateAngle(this.heat2, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.shapefire1 = new ModelRenderer(this, 0, 23);
         this.shapefire1.setRotationPoint(4.5F, 26.0F, 0.0F);
         this.shapefire1.addBox(-3.0F, 0.0F, 0.0F, 6, 9, 0, 0.0F);
         this.poison2 = new ModelRendererGlow(this, 52, 9, 220, false);
         this.poison2.setRotationPoint(0.0F, -12.0F, -6.0F);
         this.poison2.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 2, 0.0F);
         this.setRotateAngle(this.poison2, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.ice2 = new ModelRenderer(this, 40, 0);
         this.ice2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.ice2.addBox(-1.75F, -17.7F, -6.0F, 4, 12, 0, 0.0F);
         this.setRotateAngle(this.ice2, -0.22759093F, 0.0F, 0.0F);
         this.poison1 = new ModelRendererLimited(this, 48, 15, false, false, false, false, true, false);
         this.poison1.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.poison1.addBox(-8.0F, -16.0F, -5.0F, 16, 17, 0, 0.0F);
         this.shape2 = new ModelRenderer(this, 13, 7);
         this.shape2.setRotationPoint(0.0F, 4.0F, -7.0F);
         this.shape2.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 3, 0.0F);
         this.shape2_1 = new ModelRenderer(this, 34, 21);
         this.shape2_1.setRotationPoint(0.0F, -6.0F, -7.0F);
         this.shape2_1.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 3, 0.0F);
         this.core1 = new ModelRenderer(this, 0, 0);
         this.core1.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.core1.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 1.0F);
         this.shape1 = new ModelRenderer(this, 0, 12);
         this.shape1.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.shape1.addBox(-4.0F, -4.0F, -7.0F, 8, 8, 3, 0.0F);
         this.truster1 = new ModelRenderer(this, 52, 0);
         this.truster1.setRotationPoint(5.0F, 20.0F, 0.0F);
         this.truster1.addBox(-2.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
         this.truster2 = new ModelRenderer(this, 52, 0);
         this.truster2.setRotationPoint(-5.0F, 20.0F, 0.0F);
         this.truster2.addBox(-1.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
         this.shape1.addChild(this.shapeeye2);
         this.shape1.addChild(this.shapeeye);
         this.poison1.addChild(this.poison2);
         this.ice1.addChild(this.ice2);
         this.shape1.addChild(this.shape2);
         this.shape1.addChild(this.shape2_1);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         f5 = (float)(f5 * 2.4);
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -2.7F, 0.0F);
         int type = 0;
         if (isAbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            type = mob.var1;
         }

         float r1 = AnimationTimer.rainbow1;
         float r2 = AnimationTimer.rainbow2;
         float r3 = AnimationTimer.rainbow3;
         int tick = AnimationTimer.tick;
         this.setRotateAngle(this.core1, r1 * 0.27F, tick * 0.087F, r2 * 0.115F);
         this.setRotateAngle(this.core2, r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         float y = f3 * (float) (Math.PI / 180.0);
         float x = f4 * (float) (Math.PI / 180.0);
         this.shape1.rotateAngleY = y;
         this.shape1.rotateAngleX = x;
         this.ice1.rotateAngleY = y;
         this.ice1.rotateAngleX = x;
         this.poison1.rotateAngleY = y;
         this.poison1.rotateAngleX = x;
         this.shape1.render(f5);
         this.truster1.render(f5);
         this.truster2.render(f5);
         if (type == 1) {
            this.heat1.render(f5);
         }

         if (type == 2) {
            light(80, true);
            this.poison1.render(f5);
            returnlight();
         }

         this.shapefire1.offsetY = MathHelper.sin(AnimationTimer.tick / 6.0F) * 1.2F * 0.0625F;
         this.shapefire2.offsetY = MathHelper.sin((AnimationTimer.tick + 34) / 6.0F) * 1.2F * 0.0625F;
         this.shapefire3.offsetY = MathHelper.sin((AnimationTimer.tick + 15) / 6.0F) * 1.2F * 0.0625F;
         this.shapefire4.offsetY = MathHelper.sin((AnimationTimer.tick + 57) / 6.0F) * 1.2F * 0.0625F;
         light(240, false);
         if (type == 3) {
            this.ice1.render(f5);
         }

         GlStateManager.enableBlend();
         alphaGlow();
         this.core1.render(f5);
         this.core2.render(f5);
         this.shapefire2.render(f5);
         this.shapefire1.render(f5);
         this.shapefire4.render(f5);
         this.shapefire3.render(f5);
         if (type == 1) {
            float col = 0.6F + MathHelper.sin(AnimationTimer.tick / 90.0F) * 0.4F;
            GlStateManager.color(col, col, col, 1.0F);
            this.heat2.render(f5);
         }

         GlStateManager.disableBlend();
         returnlight();
         alphaGlowDisable();
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class ScreenGuardModel extends AbstractMobModel {
      public ModelRenderer core1;
      public ModelRenderer core2;
      public ModelRenderer lightning;
      public ModelRenderer head;
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape2_1;
      public ModelRenderer shape2_2;
      public ModelRenderer head_1;
      public ModelRenderer head_2;

      public ScreenGuardModel() {
         this.textureWidth = 32;
         this.textureHeight = 32;
         this.shape1 = new ModelRenderer(this, 14, 18);
         this.shape1.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.shape1.addBox(-2.0F, -6.5F, -2.0F, 4, 2, 4, 0.0F);
         this.head = new ModelRenderer(this, 14, 24);
         this.head.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.head.addBox(-2.5F, -3.0F, -7.0F, 5, 6, 2, 0.0F);
         this.head_2 = new ModelRenderer(this, 14, 24);
         this.head_2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.head_2.addBox(-2.5F, -3.0F, -7.0F, 5, 6, 2, 0.0F);
         this.setRotateAngle(this.head_2, 0.0F, (float) (Math.PI * 2.0 / 3.0), 0.0F);
         this.shape3 = new ModelRenderer(this, 14, 18);
         this.shape3.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.shape3.addBox(-2.0F, 4.0F, -2.0F, 4, 2, 4, 0.0F);
         this.lightning = new ModelRendererLimited(this, 0, 24, false, false, false, false, true, false);
         this.lightning.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.lightning.addBox(-7.0F, -4.0F, 0.0F, 14, 8, 0, 0.0F);
         this.shape2_2 = new ModelRenderer(this, 26, 0);
         this.shape2_2.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.shape2_2.addBox(-0.5F, -16.0F, -0.5F, 1, 10, 1, 0.0F);
         this.shape2_1 = new ModelRenderer(this, 26, 0);
         this.shape2_1.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.shape2_1.addBox(-0.5F, -16.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.shape2_1, 0.0F, 0.0F, (float) Math.PI);
         this.head_1 = new ModelRenderer(this, 14, 24);
         this.head_1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.head_1.addBox(-2.5F, -3.0F, -7.0F, 5, 6, 2, 0.0F);
         this.setRotateAngle(this.head_1, 0.0F, (float) (-Math.PI * 2.0 / 3.0), 0.0F);
         this.core1 = new ModelRenderer(this, 0, 2);
         this.core1.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.core1.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.core2 = new ModelRenderer(this, 0, 2);
         this.core2.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.core2.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.core2, -1.0016445F, -1.0016445F, 0.0F);
         this.shape2 = new ModelRenderer(this, 18, 0);
         this.shape2.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.shape2.addBox(-1.0F, -12.0F, -1.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.shape2, 0.0F, 0.0F, (float) Math.PI);
         this.head.addChild(this.head_2);
         this.head.addChild(this.head_1);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         f5 *= 1.2F;
         float ydispl = 0.1F;
         float r1 = AnimationTimer.rainbow1;
         float r2 = AnimationTimer.rainbow2;
         float r3 = AnimationTimer.rainbow3;
         int tick = AnimationTimer.tick;
         this.setRotateAngle(this.core1, r1 * 0.27F, tick * 0.087F, r2 * 0.115F);
         this.setRotateAngle(this.core2, r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         this.setRotateAngle(this.lightning, tick * 0.087F, r3 * 0.185F, r2 * 0.165F);
         boolean shield = false;
         float angl = 0.0F;
         if (isAbstractMob) {
            angl = ((AbstractMob)entity).var2;
            shield = ((AbstractMob)entity).var1 > 0;
         }

         this.head.rotateAngleY = angl * (float) (Math.PI / 180.0);
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, ydispl, 0.0F);
         this.shape1.render(f5);
         this.head.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
         GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
         GlStateManager.scale(1.2, 1.2, 1.2);
         GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
         GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
         this.shape3.render(f5);
         GlStateManager.popMatrix();
         this.shape2_2.render(f5);
         this.shape2_1.render(f5);
         this.shape2.render(f5);
         GlStateManager.enableBlend();
         light(240, false);
         alphaGlow();
         if (entity.ticksExisted % 69 == 0 || entity.ticksExisted % 43 == 0 || entity.ticksExisted % 69 == 1 || entity.ticksExisted % 43 == 1) {
            GlStateManager.translate(0.0, -0.5, 0.0);
            this.lightning.render(f5 * 1.5F);
            GlStateManager.translate(0.0, 0.5, 0.0);
         }

         this.core1.render(f5);
         this.core2.render(f5);
         if (shield) {
            GL11.glDepthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(ModelsStormledgeMob.texFIELD);
            GlStateManager.scale(0.7, 0.7, 0.7);
            GlStateManager.translate(0.0F, 1.25F, 0.0F);
            ModelsStormledgeMob.forcefieldModel.renderAnimated(8.0F, AnimationTimer.normaltick);
            GL11.glDepthMask(true);
         }

         returnlight();
         alphaGlowDisable();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class ShieldModel extends ModelBase {
      public ModelRenderer shape1;
      public ModelRenderer shape1_1;

      public ShieldModel() {
         this.textureWidth = 20;
         this.textureHeight = 16;
         this.shape1_1 = new ModelRenderer(this, 0, 8);
         this.shape1_1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape1_1.addBox(-3.0F, 2.4F, -3.4F, 6, 5, 2, 0.0F);
         this.setRotateAngle(this.shape1_1, 0.091106184F, 0.0F, 0.0F);
         this.shape1 = new ModelRenderer(this, 0, 0);
         this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shape1.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 2, 0.0F);
         this.setRotateAngle(this.shape1, -0.091106184F, 0.0F, 0.0F);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.shape1_1.render(f5);
         this.shape1.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class SkyGuardModel extends AbstractMobModel {
      public ModelRenderer core1;
      public ModelRenderer core2;
      public ModelRenderer head;
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer lightning;
      public ModelRenderer shape4;

      public SkyGuardModel() {
         this.textureWidth = 32;
         this.textureHeight = 32;
         this.head = new ModelRenderer(this, 0, 16);
         this.head.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.head.addBox(-2.5F, -3.0F, -7.0F, 5, 6, 2, 0.0F);
         this.shape4 = new ModelRenderer(this, 18, 0);
         this.shape4.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.shape4.addBox(-1.0F, -12.5F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.shape4, 0.0F, 0.0F, (float) Math.PI);
         this.shape1 = new ModelRenderer(this, 14, 18);
         this.shape1.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.shape1.addBox(-2.0F, -6.5F, -2.0F, 4, 2, 4, 0.0F);
         this.shape2 = new ModelRenderer(this, 18, 0);
         this.shape2.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.shape2.addBox(-1.0F, -12.5F, -1.0F, 2, 6, 2, 0.0F);
         this.core1 = new ModelRenderer(this, 0, 2);
         this.core1.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.core1.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.core2 = new ModelRenderer(this, 0, 2);
         this.core2.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.core2.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.core2, -1.0016445F, -1.0016445F, 0.0F);
         this.shape3 = new ModelRenderer(this, 14, 18);
         this.shape3.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.shape3.addBox(-2.0F, 4.5F, -2.0F, 4, 2, 4, 0.0F);
         this.lightning = new ModelRendererLimited(this, 0, 24, false, false, false, false, true, false);
         this.lightning.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.lightning.addBox(-7.0F, -4.0F, 0.0F, 14, 8, 0, 0.0F);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         f5 *= 1.2F;
         float ydispl = 0.1F;
         float r1 = AnimationTimer.rainbow1;
         float r2 = AnimationTimer.rainbow2;
         float r3 = AnimationTimer.rainbow3;
         int tick = AnimationTimer.tick;
         this.setRotateAngle(this.core1, r1 * 0.27F, tick * 0.087F, r2 * 0.115F);
         this.setRotateAngle(this.core2, r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         this.setRotateAngle(this.lightning, tick * 0.087F, r3 * 0.185F, r2 * 0.165F);
         float y = f3 * (float) (Math.PI / 180.0);
         float x = f4 * (float) (Math.PI / 180.0);
         this.head.rotateAngleY = y;
         this.head.rotateAngleX = x;
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, ydispl, 0.0F);
         this.head.render(f5);
         this.shape4.render(f5);
         this.shape1.render(f5);
         this.shape2.render(f5);
         this.shape3.render(f5);
         GlStateManager.enableBlend();
         light(240, false);
         alphaGlow();
         this.core1.render(f5);
         this.core2.render(f5);
         if (entity.ticksExisted % 69 == 0 || entity.ticksExisted % 43 == 0 || entity.ticksExisted % 69 == 1 || entity.ticksExisted % 43 == 1) {
            GlStateManager.translate(0.0, -0.5, 0.0);
            this.lightning.render(f5 * 1.5F);
            GlStateManager.translate(0.0, 0.5, 0.0);
         }

         returnlight();
         alphaGlowDisable();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class ThunderbirdModel extends AbstractMobModel {
      public ModelRenderer Head0;
      public ModelRenderer body;
      public ModelRenderer Head1;
      public ModelRenderer Head2;
      public ModelRenderer Head3;
      public ModelRenderer camera1a;
      public ModelRenderer mainCore;
      public ModelRenderer mainCoreB;
      public ModelRenderer camera2a;
      public ModelRenderer camera3a;
      public ModelRenderer camera1b;
      public ModelRenderer camera1c;
      public ModelRenderer camera1d;
      public ModelRenderer camera1gl;
      public ModelRenderer camera2b;
      public ModelRenderer camera2c;
      public ModelRenderer camera2d;
      public ModelRenderer camera2gl;
      public ModelRenderer camera3b;
      public ModelRenderer camera3c;
      public ModelRenderer camera3d;
      public ModelRenderer camera3gl;
      public ModelRenderer tail;
      public ModelRenderer wing1a;
      public ModelRenderer ribs1;
      public ModelRenderer ribs2;
      public ModelRenderer ribs3;
      public ModelRenderer wing2a;
      public ModelRenderer tail2;
      public ModelRenderer wing1core;
      public ModelRenderer wing1coreB;
      public ModelRenderer wing1b;
      public ModelRenderer wing1c;
      public ModelRenderer wing1bAdd;
      public ModelRenderer feather1b;
      public ModelRenderer wing1d;
      public ModelRenderer feather1c;
      public ModelRenderer feather1d;
      public ModelRenderer wing2core;
      public ModelRenderer wing2coreB;
      public ModelRenderer wing2b;
      public ModelRenderer wing2c;
      public ModelRenderer wing2bAdd;
      public ModelRenderer feather2b;
      public ModelRenderer wing2d;
      public ModelRenderer feather2c;
      public ModelRenderer feather2d;

      public ThunderbirdModel() {
         this.textureWidth = 80;
         this.textureHeight = 64;
         int feathGlow = 80;
         this.feather2b = new ModelRendererGlow(this, 0, 44, feathGlow, true);
         this.feather2b.setRotationPoint(0.5F, 10.0F, 1.5F);
         this.feather2b.addBox(0.0F, 0.0F, 0.0F, 0, 10, 10, 0.0F);
         this.wing2core = new ModelRendererGlow(this, 28, 1, 240, false);
         this.wing2core.setRotationPoint(0.0F, 15.5F, 0.5F);
         this.wing2core.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.wing2core, -1.2747885F, 0.63739425F, -1.4570009F);
         this.Head0 = new ModelRenderer(this, 0, 25);
         this.Head0.setRotationPoint(0.0F, 17.0F, 0.0F);
         this.Head0.addBox(-1.0F, -1.0F, -10.0F, 2, 2, 10, 0.0F);
         this.setRotateAngle(this.Head0, -0.31869712F, 0.0F, 0.0F);
         this.body = new ModelRenderer(this, 0, 37);
         this.body.setRotationPoint(0.0F, 15.8F, -0.4F);
         this.body.addBox(-1.5F, 0.0F, 0.0F, 3, 3, 14, 0.0F);
         this.feather1d = new ModelRendererGlow(this, 55, 0, feathGlow, true);
         this.feather1d.setRotationPoint(0.0F, 0.0F, 0.5F);
         this.feather1d.addBox(0.0F, 0.0F, 0.0F, 0, 26, 12, 0.0F);
         this.mainCore = new ModelRendererGlow(this, 15, 18, 240, false);
         this.mainCore.setRotationPoint(0.0F, -1.0F, -12.0F);
         this.mainCore.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.mainCore, -0.7285004F, 1.0016445F, 1.2292354F);
         this.feather1c = new ModelRendererGlow(this, 55, 26, feathGlow, true);
         this.feather1c.setRotationPoint(0.0F, -5.0F, 1.5F);
         this.feather1c.addBox(0.0F, 0.0F, 0.0F, 0, 26, 12, 0.0F);
         this.wing1b = new ModelRenderer(this, 7, 0);
         this.wing1b.setRotationPoint(-0.5F, 15.5F, 0.5F);
         this.wing1b.addBox(0.0F, 4.5F, -0.5F, 1, 15, 2, 0.0F);
         this.setRotateAngle(this.wing1b, 1.4570009F, 0.091106184F, 0.0F);
         this.Head2 = new ModelRenderer(this, 29, 24);
         this.Head2.setRotationPoint(0.0F, -1.0F, -6.5F);
         this.Head2.addBox(-2.5F, -4.0F, -7.0F, 5, 6, 8, 0.0F);
         this.setRotateAngle(this.Head2, 0.31869712F, 0.0F, 0.0F);
         this.wing2bAdd = new ModelRenderer(this, 0, 47);
         this.wing2bAdd.setRotationPoint(-1.0F, 3.5F, -1.0F);
         this.wing2bAdd.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
         this.ribs3 = new ModelRenderer(this, 55, 0);
         this.ribs3.setRotationPoint(0.0F, -1.8F, 5.6F);
         this.ribs3.addBox(0.0F, 0.0F, 0.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.ribs3, 0.091106184F, -0.091106184F, (float) (Math.PI / 4));
         this.wing2a = new ModelRenderer(this, 19, 0);
         this.wing2a.setRotationPoint(0.0F, 1.7F, 9.6F);
         this.wing2a.addBox(-1.0F, 0.5F, 0.0F, 2, 11, 2, 0.0F);
         this.setRotateAngle(this.wing2a, (float) (-Math.PI / 3), 0.31869712F, 1.548107F);
         this.Head1 = new ModelRenderer(this, 33, 38);
         this.Head1.setRotationPoint(0.0F, 1.0F, -10.0F);
         this.Head1.addBox(-2.0F, -4.0F, -7.0F, 4, 6, 7, 0.0F);
         this.setRotateAngle(this.Head1, -0.13665928F, 0.0F, 0.0F);
         this.wing2coreB = new ModelRendererGlow(this, 39, 0, 240, false);
         this.wing2coreB.setRotationPoint(0.0F, 15.5F, 0.5F);
         this.wing2coreB.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.wing2coreB, 0.22759093F, 1.0016445F, -1.4570009F);
         this.ribs2 = new ModelRenderer(this, 55, 0);
         this.ribs2.setRotationPoint(0.0F, -2.5F, 1.6F);
         this.ribs2.addBox(0.0F, 0.0F, 0.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.ribs2, 0.0F, 0.0F, (float) (Math.PI / 4));
         this.camera1a = new ModelRenderer(this, 20, 54);
         this.camera1a.setRotationPoint(0.0F, -1.0F, -4.0F);
         this.camera1a.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F);
         this.setRotateAngle(this.camera1a, -2.1399481F, 0.0F, 0.0F);
         this.mainCoreB = new ModelRendererGlow(this, 15, 24, 240, false);
         this.mainCoreB.setRotationPoint(0.0F, -1.0F, -12.0F);
         this.mainCoreB.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.mainCoreB, -0.63739425F, 0.091106184F, 1.2747885F);
         this.tail2 = new ModelRendererGlow(this, 25, 54, 180, true);
         this.tail2.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.tail2.addBox(-5.0F, 0.0F, 0.0F, 10, 0, 10, 0.0F);
         this.setRotateAngle(this.tail2, -1.775698F, 0.0F, 0.0F);
         this.tail = new ModelRenderer(this, 47, 0);
         this.tail.setRotationPoint(0.0F, 1.5F, 13.7F);
         this.tail.addBox(-1.0F, -0.5F, -1.0F, 2, 14, 2, 0.0F);
         this.setRotateAngle(this.tail, 1.9123572F, 0.0F, 0.0F);
         this.Head3 = new ModelRenderer(this, 27, 10);
         this.Head3.setRotationPoint(0.0F, -1.0F, -6.5F);
         this.Head3.addBox(-3.0F, -4.0F, -7.0F, 6, 6, 8, 0.0F);
         this.setRotateAngle(this.Head3, 0.22759093F, 0.0F, 0.0F);
         this.camera2gl = new ModelRendererGlow(this, 25, 55, 240, false);
         this.camera2gl.setRotationPoint(0.0F, 2.0F, 2.0F);
         this.camera2gl.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
         this.setRotateAngle(this.camera2gl, 0.3642502F, 0.95609134F, 1.1383038F);
         this.wing1bAdd = new ModelRenderer(this, 0, 47);
         this.wing1bAdd.setRotationPoint(-1.0F, 3.5F, -1.0F);
         this.wing1bAdd.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
         this.wing1d = new ModelRenderer(this, 14, 0);
         this.wing1d.setRotationPoint(-0.2F, 21.5F, 0.2F);
         this.wing1d.addBox(-0.5F, 0.5F, -0.5F, 1, 14, 1, 0.0F);
         this.setRotateAngle(this.wing1d, 0.5462881F, -0.13665928F, 0.0F);
         this.camera3c = new ModelRenderer(this, 20, 54);
         this.camera3c.setRotationPoint(0.05F, 7.0F, 0.0F);
         this.camera3c.addBox(-0.5F, 0.0F, -0.5F, 1, 9, 1, 0.0F);
         this.setRotateAngle(this.camera3c, 2.4586453F, 0.0F, 0.0F);
         this.feather2c = new ModelRendererGlow(this, 55, 26, feathGlow, true);
         this.feather2c.setRotationPoint(0.0F, -5.0F, 1.5F);
         this.feather2c.addBox(0.0F, 0.0F, 0.0F, 0, 26, 12, 0.0F);
         this.wing2d = new ModelRenderer(this, 14, 0);
         this.wing2d.setRotationPoint(0.2F, 21.5F, 0.2F);
         this.wing2d.addBox(-0.5F, 0.5F, -0.5F, 1, 14, 1, 0.0F);
         this.setRotateAngle(this.wing2d, 0.5462881F, 0.13665928F, 0.0F);
         this.camera2b = new ModelRenderer(this, 20, 54);
         this.camera2b.setRotationPoint(0.05F, 7.0F, 0.0F);
         this.camera2b.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F);
         this.setRotateAngle(this.camera2b, -1.6390387F, 0.0F, 0.0F);
         this.camera2c = new ModelRenderer(this, 20, 54);
         this.camera2c.setRotationPoint(0.05F, 7.0F, 0.0F);
         this.camera2c.addBox(-0.5F, 0.0F, -0.5F, 1, 9, 1, 0.0F);
         this.setRotateAngle(this.camera2c, 2.4586453F, 0.0F, 0.0F);
         this.camera1d = new ModelRenderer(this, 20, 43);
         this.camera1d.setRotationPoint(0.05F, 7.6F, 0.0F);
         this.camera1d.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 4, 0.0F);
         this.setRotateAngle(this.camera1d, -0.7285004F, 0.0F, 0.0F);
         this.camera3d = new ModelRenderer(this, 20, 43);
         this.camera3d.setRotationPoint(0.05F, 7.6F, 0.0F);
         this.camera3d.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 4, 0.0F);
         this.setRotateAngle(this.camera3d, -0.7285004F, 0.0F, 0.0F);
         this.camera1gl = new ModelRendererGlow(this, 25, 55, 240, false);
         this.camera1gl.setRotationPoint(0.0F, 2.0F, 2.0F);
         this.camera1gl.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
         this.setRotateAngle(this.camera1gl, 0.3642502F, 0.95609134F, 1.1383038F);
         this.camera3gl = new ModelRendererGlow(this, 25, 55, 240, false);
         this.camera3gl.setRotationPoint(0.0F, 2.0F, 2.0F);
         this.camera3gl.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
         this.setRotateAngle(this.camera3gl, 0.3642502F, 0.95609134F, 1.1383038F);
         this.wing2b = new ModelRenderer(this, 7, 0);
         this.wing2b.setRotationPoint(-0.5F, 15.5F, 0.5F);
         this.wing2b.addBox(0.0F, 4.5F, -0.5F, 1, 15, 2, 0.0F);
         this.setRotateAngle(this.wing2b, 1.4570009F, -0.091106184F, 0.0F);
         this.camera2a = new ModelRenderer(this, 20, 54);
         this.camera2a.setRotationPoint(0.0F, -1.0F, -4.0F);
         this.camera2a.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F);
         this.setRotateAngle(this.camera2a, -2.1399481F, 0.0F, 2.1855013F);
         this.feather2d = new ModelRendererGlow(this, 55, 0, feathGlow, true);
         this.feather2d.setRotationPoint(0.0F, 0.0F, 0.5F);
         this.feather2d.addBox(0.0F, 0.0F, 0.0F, 0, 26, 12, 0.0F);
         this.wing1a = new ModelRenderer(this, 19, 0);
         this.wing1a.setRotationPoint(0.0F, 1.7F, 9.6F);
         this.wing1a.addBox(-1.0F, 0.5F, 0.0F, 2, 11, 2, 0.0F);
         this.setRotateAngle(this.wing1a, (float) (-Math.PI / 3), -0.31869712F, -1.548107F);
         this.wing1c = new ModelRenderer(this, 0, 0);
         this.wing1c.setRotationPoint(0.5F, 18.5F, 0.5F);
         this.wing1c.addBox(-0.5F, 0.5F, -0.5F, 1, 22, 2, 0.0F);
         this.setRotateAngle(this.wing1c, -0.8651597F, 0.27314404F, 0.045553092F);
         this.camera2d = new ModelRenderer(this, 20, 43);
         this.camera2d.setRotationPoint(0.05F, 7.6F, 0.0F);
         this.camera2d.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 4, 0.0F);
         this.setRotateAngle(this.camera2d, -0.7285004F, 0.0F, 0.0F);
         this.feather1b = new ModelRendererGlow(this, 0, 44, feathGlow, true);
         this.feather1b.setRotationPoint(0.5F, 10.0F, 1.5F);
         this.feather1b.addBox(0.0F, 0.0F, 0.0F, 0, 10, 10, 0.0F);
         this.camera3a = new ModelRenderer(this, 20, 54);
         this.camera3a.setRotationPoint(0.0F, -1.0F, -4.0F);
         this.camera3a.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F);
         this.setRotateAngle(this.camera3a, -2.1399481F, 0.0F, -2.1855013F);
         this.wing1core = new ModelRendererGlow(this, 28, 1, 240, false);
         this.wing1core.setRotationPoint(0.0F, 15.5F, 0.5F);
         this.wing1core.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.wing1core, -1.2747885F, 0.63739425F, -1.4570009F);
         this.camera1b = new ModelRenderer(this, 20, 54);
         this.camera1b.setRotationPoint(0.05F, 7.0F, 0.0F);
         this.camera1b.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F);
         this.setRotateAngle(this.camera1b, -1.6390387F, 0.0F, 0.0F);
         this.ribs1 = new ModelRenderer(this, 55, 0);
         this.ribs1.setRotationPoint(0.0F, -4.0F, -1.4F);
         this.ribs1.addBox(0.0F, 0.0F, 0.0F, 6, 6, 6, 0.0F);
         this.setRotateAngle(this.ribs1, -0.13665928F, 0.13665928F, (float) (Math.PI / 4));
         this.camera3b = new ModelRenderer(this, 20, 54);
         this.camera3b.setRotationPoint(0.05F, 7.0F, 0.0F);
         this.camera3b.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F);
         this.setRotateAngle(this.camera3b, -1.6390387F, 0.0F, 0.0F);
         this.wing2c = new ModelRenderer(this, 0, 0);
         this.wing2c.setRotationPoint(0.5F, 18.5F, 0.5F);
         this.wing2c.addBox(-0.5F, 0.5F, -0.5F, 1, 22, 2, 0.0F);
         this.setRotateAngle(this.wing2c, -0.8651597F, -0.27314404F, -0.045553092F);
         this.camera1c = new ModelRenderer(this, 20, 54);
         this.camera1c.setRotationPoint(0.05F, 7.0F, 0.0F);
         this.camera1c.addBox(-0.5F, 0.0F, -0.5F, 1, 9, 1, 0.0F);
         this.setRotateAngle(this.camera1c, 2.4586453F, 0.0F, 0.0F);
         this.wing1coreB = new ModelRendererGlow(this, 39, 0, 240, false);
         this.wing1coreB.setRotationPoint(0.0F, 15.5F, 0.5F);
         this.wing1coreB.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.wing1coreB, 0.22759093F, 1.0016445F, -1.4570009F);
         this.wing2b.addChild(this.feather2b);
         this.wing2a.addChild(this.wing2core);
         this.wing1d.addChild(this.feather1d);
         this.Head3.addChild(this.mainCore);
         this.wing1c.addChild(this.feather1c);
         this.wing1a.addChild(this.wing1b);
         this.Head1.addChild(this.Head2);
         this.wing2b.addChild(this.wing2bAdd);
         this.body.addChild(this.ribs3);
         this.body.addChild(this.wing2a);
         this.Head0.addChild(this.Head1);
         this.wing2a.addChild(this.wing2coreB);
         this.body.addChild(this.ribs2);
         this.Head3.addChild(this.camera1a);
         this.Head3.addChild(this.mainCoreB);
         this.tail.addChild(this.tail2);
         this.body.addChild(this.tail);
         this.Head2.addChild(this.Head3);
         this.camera2d.addChild(this.camera2gl);
         this.wing1b.addChild(this.wing1bAdd);
         this.wing1c.addChild(this.wing1d);
         this.camera3b.addChild(this.camera3c);
         this.wing2c.addChild(this.feather2c);
         this.wing2c.addChild(this.wing2d);
         this.camera2a.addChild(this.camera2b);
         this.camera2b.addChild(this.camera2c);
         this.camera1c.addChild(this.camera1d);
         this.camera3c.addChild(this.camera3d);
         this.camera1d.addChild(this.camera1gl);
         this.camera3d.addChild(this.camera3gl);
         this.wing2a.addChild(this.wing2b);
         this.Head3.addChild(this.camera2a);
         this.wing2d.addChild(this.feather2d);
         this.body.addChild(this.wing1a);
         this.wing1b.addChild(this.wing1c);
         this.camera2c.addChild(this.camera2d);
         this.wing1b.addChild(this.feather1b);
         this.Head3.addChild(this.camera3a);
         this.wing1a.addChild(this.wing1core);
         this.camera1a.addChild(this.camera1b);
         this.body.addChild(this.ribs1);
         this.camera3a.addChild(this.camera3b);
         this.wing2b.addChild(this.wing2c);
         this.camera1b.addChild(this.camera1c);
         this.wing1a.addChild(this.wing1coreB);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         f5 *= 1.4F;
         float ydispl = -0.7F;
         float r1 = AnimationTimer.rainbow1;
         float r2 = AnimationTimer.rainbow2;
         float r3 = AnimationTimer.rainbow3;
         int tick = AnimationTimer.tick;
         this.setRotateAngle(this.mainCore, r1 * 0.27F, tick * 0.087F, r2 * 0.115F);
         this.setRotateAngle(this.mainCoreB, r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         this.setRotateAngle(this.wing1core, tick * 0.087F, r3 * 0.185F, r2 * 0.165F);
         this.setRotateAngle(this.wing1coreB, r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         this.setRotateAngle(this.wing2core, tick * 0.087F, r3 * 0.185F, r2 * 0.165F);
         this.setRotateAngle(this.wing2coreB, r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         float coreT = AnimationTimer.tick * 0.2F;
         this.camera1gl.rotateAngleZ = coreT;
         this.camera2gl.rotateAngleZ = coreT;
         this.camera3gl.rotateAngleZ = coreT;
         float angYaw = f3 * 0.25F;
         float angPitch = f4 * 0.25F;
         if (isAbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            float cx = (100 - an4 + Minecraft.getMinecraft().getRenderPartialTicks()) / (3.3F - mob.var3 * 1.5F);
            float camersAnim = (float)Math.sin(MathHelper.clamp(100 - an3 + Minecraft.getMinecraft().getRenderPartialTicks(), 0.0F, 15.0F) * 0.2F) / 2.0F;
            float add = (float)this.getWingAngle1(cx) * 0.3F;
            float add2 = (float)this.getWingAngle2(cx) * 0.3F;
            ydispl = (float)(ydispl + add * (2.0F - mob.var3) * 0.5);
            float w1bx = 0.0F;
            float w1by = 0.0F;
            float w1bz = 0.0F;
            float w1cx = 0.0F;
            float w1cy = 0.0F;
            float w1cz = 0.0F;
            float w1dx = 0.0F;
            float w1dy = 0.0F;
            float w1dz = 0.0F;
            float swing1 = 0.0F;
            float swing2 = 0.0F;
            float head0x = -0.31869712F;
            float head1x = -0.13665928F;
            float head2x = 0.31869712F;
            float head3x = 0.22759093F;
            if (mob.var2 > 0.0F) {
               float cf = mob.var2;
               w1bx = 0.65F * cf;
               w1by = 0.47F * cf;
               w1bz = 0.75F * cf;
               w1cx = -0.88F * cf;
               w1cy = 0.52F * cf;
               w1cz = 0.3F * cf;
               w1dx = 1.06F * cf;
               w1dy = 0.13665928F * cf;
               w1dz = -0.08F * cf;
               float swingMult = 0.4362F;
               swing1 = MathHelper.cos(f * swingMult) * 0.65F * f1 * cf;
               swing2 = MathHelper.sin(f * swingMult) * 0.65F * f1 * cf;
               ydispl += -MathHelper.sin(f * swingMult * 2.0F) * cf * 0.17F;
               head0x += -0.4F * cf;
               head1x += -0.2F * cf;
               head2x += 0.3F * cf;
               head3x += 0.4F * cf;
            }

            this.Head0.rotateAngleX = head0x;
            this.Head1.rotateAngleX = head1x;
            this.Head2.rotateAngleX = head2x;
            this.Head3.rotateAngleX = head3x;
            this.wing1b.rotateAngleY = 0.091106184F + add + w1by - swing1;
            this.wing2b.rotateAngleY = -0.091106184F - add - w1by - swing1;
            this.wing1c.rotateAngleY = 0.27314404F - add + w1cy;
            this.wing2c.rotateAngleY = -0.27314404F + add - w1cy;
            this.wing1d.rotateAngleX = 0.5462881F - add + add2 + w1dx;
            this.wing2d.rotateAngleX = 0.5462881F - add + add2 + w1dx;
            this.wing1b.rotateAngleX = 1.4570009F + add2 + w1bx + swing2;
            this.wing2b.rotateAngleX = 1.4570009F + add2 + w1bx - swing2;
            this.wing1c.rotateAngleX = -0.8651597F - add2 + w1cx;
            this.wing2c.rotateAngleX = -0.8651597F - add2 + w1cx;
            this.wing1b.rotateAngleZ = w1bz;
            this.wing2b.rotateAngleZ = -w1bz;
            this.wing1c.rotateAngleZ = 0.045553092F + w1bz;
            this.wing2c.rotateAngleZ = -0.045553092F - w1bz;
            this.wing1d.rotateAngleY = -0.13665928F + w1dy;
            this.wing2d.rotateAngleY = 0.13665928F - w1dy;
            this.wing1d.rotateAngleZ = w1dz;
            this.wing2d.rotateAngleZ = -w1dz;
            this.tail.rotateAngleX = 1.9123572F + (float)Math.sin(AnimationTimer.tick / 14.0F) / 3.0F;
            this.tail2.rotateAngleX = -1.775698F + (float)Math.sin(AnimationTimer.tick / 14.0F) / 4.0F;
            if (mob.var3 > 0.0F) {
               this.body.rotateAngleX = -0.97F * mob.var3;
               this.wing1b.rotateAngleZ = 0.44F * mob.var3;
               this.wing2b.rotateAngleZ = -0.44F * mob.var3;
            } else {
               this.body.rotateAngleX = f4 * (float) (Math.PI / 180.0);
               this.wing1b.rotateAngleZ = 0.0F;
               this.wing2b.rotateAngleZ = 0.0F;
            }

            if (mob.var1 == 2) {
               angYaw = -angYaw;
            }

            this.camera1b.rotateAngleX = -1.6390387F - camersAnim;
            this.camera2b.rotateAngleX = -1.6390387F - camersAnim;
            this.camera3b.rotateAngleX = -1.6390387F - camersAnim;
            this.camera1c.rotateAngleX = 2.4586453F - camersAnim;
            this.camera2c.rotateAngleX = 2.4586453F - camersAnim;
            this.camera3c.rotateAngleX = 2.4586453F - camersAnim;
         }

         this.Head0.rotateAngleY = angYaw * (float) (Math.PI / 180.0);
         this.Head0.rotateAngleX += angPitch * (float) (Math.PI / 180.0);
         this.Head1.rotateAngleY = angYaw * (float) (Math.PI / 180.0);
         this.Head1.rotateAngleX += angPitch * (float) (Math.PI / 180.0);
         this.Head2.rotateAngleY = angYaw * (float) (Math.PI / 180.0);
         this.Head2.rotateAngleX += angPitch * (float) (Math.PI / 180.0);
         this.Head3.rotateAngleY = angYaw * (float) (Math.PI / 180.0);
         this.Head3.rotateAngleX += angPitch * (float) (Math.PI / 180.0);
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, ydispl, 0.0F);
         this.body.render(f5);
         this.Head0.render(f5);
         if (an2 > 80) {
            float col = (100 - an2) / 10.0F;
            GlStateManager.pushMatrix();
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(ModelsStormledgeMob.texPOWER);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float anf = AnimationTimer.tick;
            float anf1 = MathHelper.cos(anf * 0.1F) / 2.0F;
            float anf2 = f * 0.08F;
            GlStateManager.translate(anf1, anf2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.color(Math.min(0.6F * col, 0.6F), col, col, 1.0F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.body.render(f5 * 1.1F);
            this.Head0.render(f5 * 1.1F);
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

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class WindbreakModel extends AbstractMobModel {
      public ModelRenderer headbase;
      public ModelRenderer wind1;
      public ModelRenderer shape;
      public ModelRenderer shapea;
      public ModelRenderer shapef;
      public ModelRenderer winda1;
      public ModelRenderer shapea2;
      public ModelRenderer shape2;
      public ModelRenderer winda2;
      public ModelRenderer shapef2;
      public ModelRenderer wind2;
      public ModelRenderer wind3;
      public ModelRenderer wind4;
      public ModelRenderer windtail1;
      public ModelRenderer head;
      public ModelRenderer shap;
      public ModelRenderer shap2;
      public ModelRenderer sting;
      public ModelRenderer sting2;
      public ModelRenderer sting_1;
      public ModelRenderer shapeb;
      public ModelRenderer shapec;
      public ModelRenderer shaped;
      public ModelRenderer shapeg;
      public ModelRenderer shapev;
      public ModelRenderer shapeb2;
      public ModelRenderer shapec2;
      public ModelRenderer shaped2;
      public ModelRenderer sting2_1;
      public ModelRenderer shapeg2;
      public ModelRenderer shapev2;
      public ModelRenderer windtail2;
      public ModelRenderer windtail3;
      public ModelRenderer windtail4;

      public WindbreakModel() {
         this.textureWidth = 64;
         this.textureHeight = 40;
         this.windtail3 = new ModelRendererLimited(this, 43, 4, false, false, false, false, true, false);
         this.windtail3.setRotationPoint(0.0F, -10.0F, 0.5F);
         this.windtail3.addBox(-8.5F, -18.0F, 0.0F, 17, 18, 0, 0.0F);
         this.shape2 = new ModelRenderer(this, 0, 21);
         this.shape2.setRotationPoint(2.0F, 11.4F, -5.0F);
         this.shape2.addBox(0.0F, -4.0F, -0.5F, 2, 4, 1, 0.0F);
         this.setRotateAngle(this.shape2, -1.2747885F, 0.27314404F, 0.0F);
         this.wind3 = new ModelRendererLimited(this, 43, 4, false, false, false, false, true, false);
         this.wind3.setRotationPoint(0.0F, 13.0F, -3.9F);
         this.wind3.addBox(-8.5F, -27.0F, 0.0F, 17, 30, 0, 0.0F);
         this.setRotateAngle(this.wind3, -1.5025539F, 0.0F, -0.91053826F);
         this.shapea2 = new ModelRenderer(this, 0, 14);
         this.shapea2.setRotationPoint(4.9F, 9.4F, -1.0F);
         this.shapea2.addBox(-1.0F, -4.0F, -0.5F, 3, 4, 3, 0.0F);
         this.setRotateAngle(this.shapea2, -1.4114478F, -0.13665928F, 0.0F);
         this.headbase = new ModelRenderer(this, 0, 26);
         this.headbase.setRotationPoint(0.0F, 13.0F, -4.0F);
         this.headbase.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
         this.shapea = new ModelRenderer(this, 0, 14);
         this.shapea.setRotationPoint(-4.9F, 9.4F, -1.0F);
         this.shapea.addBox(-2.0F, -4.0F, -0.5F, 3, 4, 3, 0.0F);
         this.setRotateAngle(this.shapea, -1.4114478F, 0.13665928F, 0.0F);
         this.windtail2 = new ModelRendererLimited(this, 43, 4, false, false, false, false, true, false);
         this.windtail2.setRotationPoint(0.0F, 0.0F, 1.0F);
         this.windtail2.addBox(-8.5F, -18.0F, 0.0F, 17, 18, 0, 0.0F);
         this.setRotateAngle(this.windtail2, 0.0F, (float) (-Math.PI / 2), 0.0F);
         this.winda2 = new ModelRendererLimited(this, 25, 4, false, false, false, false, true, false);
         this.winda2.setRotationPoint(4.0F, 13.5F, -5.9F);
         this.winda2.addBox(-3.0F, -25.0F, 0.0F, 12, 27, 0, 0.0F);
         this.setRotateAngle(this.winda2, -1.5025539F, 0.27314404F, 0.7740535F);
         this.shap = new ModelRenderer(this, 0, 30);
         this.shap.setRotationPoint(-2.0F, 1.7F, 0.0F);
         this.shap.addBox(-2.0F, -4.0F, -0.5F, 2, 4, 1, 0.0F);
         this.setRotateAngle(this.shap, 1.5025539F, 0.5009095F, -1.2292354F);
         this.wind4 = new ModelRendererLimited(this, 43, 4, false, false, false, false, true, false);
         this.wind4.setRotationPoint(0.0F, 14.0F, -4.9F);
         this.wind4.addBox(-8.5F, -27.0F, 0.0F, 17, 30, 0, 0.0F);
         this.setRotateAngle(this.wind4, (float) (-Math.PI / 2), 0.0F, (float) (-Math.PI / 2));
         this.sting_1 = new ModelRenderer(this, 6, 21);
         this.sting_1.setRotationPoint(-2.0F, -2.0F, 0.0F);
         this.sting_1.addBox(0.5F, -5.2F, 0.0F, 3, 7, 0, 0.0F);
         this.winda1 = new ModelRendererLimited(this, 25, 4, false, false, false, false, true, false);
         this.winda1.mirror = true;
         this.winda1.setRotationPoint(-4.0F, 13.5F, -5.9F);
         this.winda1.addBox(-9.0F, -25.0F, 0.0F, 12, 27, 0, 0.0F);
         this.setRotateAngle(this.winda1, -1.5025539F, -0.27314404F, -0.7740535F);
         this.windtail4 = new ModelRendererLimited(this, 43, 4, false, false, false, false, true, false);
         this.windtail4.setRotationPoint(0.0F, 0.0F, 1.0F);
         this.windtail4.addBox(-8.5F, -18.0F, 0.0F, 17, 18, 0, 0.0F);
         this.setRotateAngle(this.windtail4, 0.0F, (float) (-Math.PI / 2), 0.0F);
         this.shapec = new ModelRenderer(this, 10, 0);
         this.shapec.setRotationPoint(-0.8F, -1.4F, 0.0F);
         this.shapec.addBox(-1.7F, -6.0F, -0.5F, 2, 4, 2, 0.0F);
         this.setRotateAngle(this.shapec, -0.091106184F, 0.13665928F, 0.3642502F);
         this.shapeb2 = new ModelRenderer(this, 2, 7);
         this.shapeb2.setRotationPoint(0.9F, -1.0F, 0.4F);
         this.shapeb2.addBox(-1.0F, -4.0F, -0.5F, 3, 4, 2, 0.0F);
         this.setRotateAngle(this.shapeb2, -0.091106184F, -0.13665928F, 0.5009095F);
         this.shapeb = new ModelRenderer(this, 2, 7);
         this.shapeb.setRotationPoint(-0.9F, -1.0F, 0.4F);
         this.shapeb.addBox(-2.0F, -4.0F, -0.5F, 3, 4, 2, 0.0F);
         this.setRotateAngle(this.shapeb, -0.091106184F, 0.13665928F, -0.5009095F);
         this.shapec2 = new ModelRenderer(this, 10, 0);
         this.shapec2.setRotationPoint(0.8F, -1.4F, 0.0F);
         this.shapec2.addBox(-0.3F, -6.0F, -0.5F, 2, 4, 2, 0.0F);
         this.setRotateAngle(this.shapec2, -0.091106184F, -0.13665928F, -0.3642502F);
         this.head = new ModelRenderer(this, 4, 35);
         this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.head.addBox(-2.5F, -2.5F, -3.0F, 5, 5, 0, 0.0F);
         this.setRotateAngle(this.head, 0.0F, 0.0F, (float) (Math.PI / 4));
         this.sting = new ModelRenderer(this, 6, 30);
         this.sting.setRotationPoint(-2.0F, -2.0F, 0.0F);
         this.sting.addBox(0.59F, -4.2F, 0.0F, 3, 5, 0, 0.0F);
         this.shapeg2 = new ModelRenderer(this, 0, 0);
         this.shapeg2.setRotationPoint(0.8F, -5.4F, 0.0F);
         this.shapeg2.addBox(-0.5F, -8.0F, -0.4F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.shapeg2, 0.13665928F, 0.0F, -0.3642502F);
         this.shapef2 = new ModelRenderer(this, 4, 0);
         this.shapef2.setRotationPoint(2.0F, 12.5F, 5.0F);
         this.shapef2.addBox(0.0F, -6.0F, -0.5F, 2, 6, 1, 0.0F);
         this.setRotateAngle(this.shapef2, -1.775698F, 0.31869712F, 0.045553092F);
         this.shape = new ModelRenderer(this, 0, 21);
         this.shape.setRotationPoint(-2.0F, 11.4F, -5.0F);
         this.shape.addBox(-2.0F, -4.0F, -0.5F, 2, 4, 1, 0.0F);
         this.setRotateAngle(this.shape, -1.2747885F, -0.27314404F, 0.0F);
         this.windtail1 = new ModelRendererLimited(this, 43, 4, false, false, false, false, true, false);
         this.windtail1.setRotationPoint(0.0F, 13.0F, 16.0F);
         this.windtail1.addBox(-8.5F, -18.0F, 0.0F, 17, 18, 0, 0.0F);
         this.setRotateAngle(this.windtail1, (float) (-Math.PI / 2), 0.0F, 0.0F);
         this.shaped2 = new ModelRenderer(this, 0, 35);
         this.shaped2.setRotationPoint(0.8F, -1.4F, 0.0F);
         this.shaped2.addBox(0.7F, -8.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.shaped2, -0.091106184F, -0.13665928F, -0.3642502F);
         this.sting2 = new ModelRenderer(this, 6, 30);
         this.sting2.setRotationPoint(-2.0F, -2.0F, 0.0F);
         this.sting2.addBox(0.59F, -4.2F, 0.0F, 3, 5, 0, 0.0F);
         this.sting2_1 = new ModelRenderer(this, 6, 21);
         this.sting2_1.mirror = true;
         this.sting2_1.setRotationPoint(2.0F, -2.0F, 0.0F);
         this.sting2_1.addBox(-3.5F, -5.2F, 0.0F, 3, 7, 0, 0.0F);
         this.wind1 = new ModelRendererLimited(this, 43, 4, false, false, false, false, true, false);
         this.wind1.setRotationPoint(0.0F, 13.0F, -4.9F);
         this.wind1.addBox(-8.5F, -27.0F, 0.0F, 17, 30, 0, 0.0F);
         this.setRotateAngle(this.wind1, -1.5025539F, 0.0F, 0.0F);
         this.shapev2 = new ModelRenderer(this, 9, 13);
         this.shapev2.setRotationPoint(0.8F, -7.4F, 0.0F);
         this.shapev2.addBox(-1.8F, -3.5F, 0.0F, 3, 4, 0, 0.0F);
         this.setRotateAngle(this.shapev2, -0.18203785F, -0.27314404F, 0.31869712F);
         this.shapef = new ModelRenderer(this, 4, 0);
         this.shapef.setRotationPoint(-2.0F, 12.5F, 5.0F);
         this.shapef.addBox(-2.0F, -6.0F, -0.5F, 2, 6, 1, 0.0F);
         this.setRotateAngle(this.shapef, -1.775698F, -0.31869712F, -0.045553092F);
         this.shapeg = new ModelRenderer(this, 0, 0);
         this.shapeg.setRotationPoint(-0.8F, -5.4F, 0.0F);
         this.shapeg.addBox(-0.5F, -8.0F, -0.4F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.shapeg, 0.13665928F, 0.0F, 0.3642502F);
         this.shapev = new ModelRenderer(this, 9, 13);
         this.shapev.mirror = true;
         this.shapev.setRotationPoint(-0.8F, -7.4F, 0.0F);
         this.shapev.addBox(-1.2F, -3.5F, 0.0F, 3, 4, 0, 0.0F);
         this.setRotateAngle(this.shapev, -0.18203785F, 0.27314404F, -0.31869712F);
         this.shap2 = new ModelRenderer(this, 0, 30);
         this.shap2.setRotationPoint(2.0F, 1.7F, 0.0F);
         this.shap2.addBox(-2.0F, -4.0F, -0.5F, 2, 4, 1, 0.0F);
         this.setRotateAngle(this.shap2, 1.6390387F, 0.5009095F, -1.9123572F);
         this.wind2 = new ModelRendererLimited(this, 43, 4, false, false, false, false, true, false);
         this.wind2.setRotationPoint(0.0F, 13.0F, -3.9F);
         this.wind2.addBox(-8.5F, -27.0F, 0.0F, 17, 30, 0, 0.0F);
         this.setRotateAngle(this.wind2, -1.5025539F, 0.0F, 0.91053826F);
         this.shaped = new ModelRenderer(this, 0, 35);
         this.shaped.setRotationPoint(-0.8F, -1.4F, 0.0F);
         this.shaped.addBox(-1.7F, -8.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.shaped, -0.091106184F, 0.13665928F, 0.3642502F);
         this.windtail1.addChild(this.windtail3);
         this.windtail1.addChild(this.windtail2);
         this.headbase.addChild(this.shap);
         this.shape.addChild(this.sting_1);
         this.windtail3.addChild(this.windtail4);
         this.shapeb.addChild(this.shapec);
         this.shapea2.addChild(this.shapeb2);
         this.shapea.addChild(this.shapeb);
         this.shapeb2.addChild(this.shapec2);
         this.headbase.addChild(this.head);
         this.shap.addChild(this.sting);
         this.shapef2.addChild(this.shapeg2);
         this.shapec2.addChild(this.shaped2);
         this.shap2.addChild(this.sting2);
         this.shape2.addChild(this.sting2_1);
         this.shapeg2.addChild(this.shapev2);
         this.shapef.addChild(this.shapeg);
         this.shapeg.addChild(this.shapev);
         this.headbase.addChild(this.shap2);
         this.shapec.addChild(this.shaped);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         f5 *= 2.1F;
         float ydispl = -0.7F;
         float y = f3 * (float) (Math.PI / 180.0);
         float x = f4 * (float) (Math.PI / 180.0);
         this.headbase.rotateAngleY = y;
         this.headbase.rotateAngleX = x - 0.261795F;
         float add = MathHelper.sin(AnimationTimer.tick / 30.0F) * 0.15F;
         this.shapef.rotateAngleY = -0.31869712F + add;
         this.shapef2.rotateAngleY = 0.31869712F - add;
         this.windtail3.rotateAngleY = add;
         float rotateUp = 0.0F;
         float rotateSide = 0.0F;
         if (isAbstractMob) {
            rotateUp = ((AbstractMob)entity).var2;
            rotateSide = ((AbstractMob)entity).var3;
         }

         this.windtail1.rotateAngleX = (float) (-Math.PI / 2) + rotateUp;
         this.windtail3.rotateAngleX = rotateUp;
         this.windtail1.rotateAngleY = rotateSide;
         this.windtail3.rotateAngleZ = rotateSide;
         GlStateManager.pushMatrix();
         GlStateManager.rotate(x * 40.0F + 15.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0F, ydispl, 0.0F);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
         GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
         GlStateManager.scale(0.7, 1.0, 1.1);
         GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
         GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
         this.shape2.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapea2.offsetX, this.shapea2.offsetY, this.shapea2.offsetZ);
         GlStateManager.translate(this.shapea2.rotationPointX * f5, this.shapea2.rotationPointY * f5, this.shapea2.rotationPointZ * f5);
         GlStateManager.scale(0.7, 1.0, 1.1);
         GlStateManager.translate(-this.shapea2.offsetX, -this.shapea2.offsetY, -this.shapea2.offsetZ);
         GlStateManager.translate(-this.shapea2.rotationPointX * f5, -this.shapea2.rotationPointY * f5, -this.shapea2.rotationPointZ * f5);
         this.shapea2.render(f5);
         GlStateManager.popMatrix();
         this.headbase.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapea.offsetX, this.shapea.offsetY, this.shapea.offsetZ);
         GlStateManager.translate(this.shapea.rotationPointX * f5, this.shapea.rotationPointY * f5, this.shapea.rotationPointZ * f5);
         GlStateManager.scale(0.7, 1.0, 1.1);
         GlStateManager.translate(-this.shapea.offsetX, -this.shapea.offsetY, -this.shapea.offsetZ);
         GlStateManager.translate(-this.shapea.rotationPointX * f5, -this.shapea.rotationPointY * f5, -this.shapea.rotationPointZ * f5);
         this.shapea.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapef2.offsetX, this.shapef2.offsetY, this.shapef2.offsetZ);
         GlStateManager.translate(this.shapef2.rotationPointX * f5, this.shapef2.rotationPointY * f5, this.shapef2.rotationPointZ * f5);
         GlStateManager.scale(0.7, 1.0, 1.1);
         GlStateManager.translate(-this.shapef2.offsetX, -this.shapef2.offsetY, -this.shapef2.offsetZ);
         GlStateManager.translate(-this.shapef2.rotationPointX * f5, -this.shapef2.rotationPointY * f5, -this.shapef2.rotationPointZ * f5);
         this.shapef2.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape.offsetX, this.shape.offsetY, this.shape.offsetZ);
         GlStateManager.translate(this.shape.rotationPointX * f5, this.shape.rotationPointY * f5, this.shape.rotationPointZ * f5);
         GlStateManager.scale(0.7, 1.0, 1.1);
         GlStateManager.translate(-this.shape.offsetX, -this.shape.offsetY, -this.shape.offsetZ);
         GlStateManager.translate(-this.shape.rotationPointX * f5, -this.shape.rotationPointY * f5, -this.shape.rotationPointZ * f5);
         this.shape.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapef.offsetX, this.shapef.offsetY, this.shapef.offsetZ);
         GlStateManager.translate(this.shapef.rotationPointX * f5, this.shapef.rotationPointY * f5, this.shapef.rotationPointZ * f5);
         GlStateManager.scale(0.7, 1.0, 1.1);
         GlStateManager.translate(-this.shapef.offsetX, -this.shapef.offsetY, -this.shapef.offsetZ);
         GlStateManager.translate(-this.shapef.rotationPointX * f5, -this.shapef.rotationPointY * f5, -this.shapef.rotationPointZ * f5);
         this.shapef.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.enableBlend();
         light(150, true);
         alphaGlow();
         boolean shader = false;
         if (ShaderMain.shaderCounter < ShaderMain.maxShaderCounter) {
            ShaderMain.WindShader.start();
            ARBShaderObjects.glUniform1fARB(ShaderMain.MoltenShader.getUniform("time"), AnimationTimer.tick / 40.0F);
            ShaderMain.shaderCounter++;
            shader = true;
         }

         this.wind3.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.winda2.offsetX, this.winda2.offsetY, this.winda2.offsetZ);
         GlStateManager.translate(this.winda2.rotationPointX * f5, this.winda2.rotationPointY * f5, this.winda2.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.winda2.offsetX, -this.winda2.offsetY, -this.winda2.offsetZ);
         GlStateManager.translate(-this.winda2.rotationPointX * f5, -this.winda2.rotationPointY * f5, -this.winda2.rotationPointZ * f5);
         this.winda2.render(f5);
         GlStateManager.popMatrix();
         this.wind4.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.winda1.offsetX, this.winda1.offsetY, this.winda1.offsetZ);
         GlStateManager.translate(this.winda1.rotationPointX * f5, this.winda1.rotationPointY * f5, this.winda1.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.winda1.offsetX, -this.winda1.offsetY, -this.winda1.offsetZ);
         GlStateManager.translate(-this.winda1.rotationPointX * f5, -this.winda1.rotationPointY * f5, -this.winda1.rotationPointZ * f5);
         this.winda1.render(f5);
         GlStateManager.popMatrix();
         this.windtail1.render(f5);
         this.wind1.render(f5);
         this.wind2.render(f5);
         if (shader) {
            ShaderMain.WindShader.stop();
         }

         returnlight();
         alphaGlowDisable();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class ZarpionModel extends AbstractMobModel {
      public ModelRenderer head;
      public ModelRenderer head2;
      public ModelRenderer core1;
      public ModelRenderer core2;
      public ModelRenderer core3;
      public ModelRenderer shape1;
      public ModelRenderer camera1a;
      public ModelRenderer camera2a;
      public ModelRenderer camera3a;
      public ModelRenderer weara1;
      public ModelRenderer armor1;
      public ModelRenderer armor2;
      public ModelRenderer armor3;
      public ModelRenderer blaster1;
      public ModelRenderer blaster2;
      public ModelRenderer grenade1;
      public ModelRenderer forcefield1;
      public ModelRenderer wear1;
      public ModelRenderer weara2;
      public ModelRenderer wear2;
      public ModelRenderer wear3;
      public ModelRenderer wear4;
      public ModelRenderer wear5;
      public ModelRenderer wear6;
      public ModelRenderer armor4;
      public ModelRenderer armor5;
      public ModelRenderer armor6;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer camera1b;
      public ModelRenderer camera1c;
      public ModelRenderer camera1d;
      public ModelRenderer camera1gl;
      public ModelRenderer camera2b;
      public ModelRenderer camera2c;
      public ModelRenderer camera2d;
      public ModelRenderer camera2gl;
      public ModelRenderer camera3b;
      public ModelRenderer camera3c;
      public ModelRenderer camera3d;
      public ModelRenderer camera3gl;
      public ModelRenderer blaster3;
      public ModelRenderer blaster4;
      public ModelRenderer blaster5;
      public ModelRenderer grenade2;
      public ModelRenderer grenade3;
      public ModelRenderer forcefield2;

      public ZarpionModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.camera2a = new ModelRenderer(this, 0, 10);
         this.camera2a.setRotationPoint(0.0F, -0.2F, -0.3F);
         this.camera2a.addBox(-0.5F, -1.5F, 4.0F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.camera2a, -0.91053826F, 0.5462881F, -2.003289F);
         this.core3 = new ModelRenderer(this, 0, 0);
         this.core3.setRotationPoint(0.0F, -2.0F, 0.0F);
         this.core3.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F);
         this.setRotateAngle(this.core3, 0.13665928F, -0.95609134F, 1.2292354F);
         this.camera3gl = new ModelRendererGlow(this, 9, 10, 240, false);
         this.camera3gl.setRotationPoint(0.0F, 2.0F, 2.0F);
         this.camera3gl.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
         this.setRotateAngle(this.camera3gl, 0.3642502F, 0.95609134F, 1.1383038F);
         this.armor1 = new ModelRenderer(this, 18, 17);
         this.armor1.setRotationPoint(0.0F, 5.0F, 1.5F);
         this.armor1.addBox(-3.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
         this.setRotateAngle(this.armor1, -0.3642502F, -0.27314404F, 0.18203785F);
         this.camera2gl = new ModelRendererGlow(this, 9, 10, 240, false);
         this.camera2gl.setRotationPoint(0.0F, 2.0F, 2.0F);
         this.camera2gl.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
         this.setRotateAngle(this.camera2gl, 0.3642502F, 0.95609134F, 1.1383038F);
         this.camera2c = new ModelRenderer(this, 0, 10);
         this.camera2c.setRotationPoint(0.05F, 8.0F, 0.0F);
         this.camera2c.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.camera2c, 1.7301449F, 0.0F, 0.0F);
         this.blaster4 = new ModelRenderer(this, 18, 30);
         this.blaster4.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.blaster4.addBox(-4.0F, -0.5F, -5.0F, 8, 1, 1, 0.0F);
         this.setRotateAngle(this.blaster4, 0.0F, (float) Math.PI, 0.0F);
         this.camera2d = new ModelRenderer(this, 4, 10);
         this.camera2d.setRotationPoint(0.05F, 3.6F, 0.0F);
         this.camera2d.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
         this.setRotateAngle(this.camera2d, -0.7285004F, 0.0F, 0.0F);
         this.blaster5 = new ModelRenderer(this, 18, 30);
         this.blaster5.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.blaster5.addBox(-4.0F, -0.5F, -5.0F, 8, 1, 1, 0.0F);
         this.setRotateAngle(this.blaster5, 0.0F, (float) (-Math.PI / 2), 0.0F);
         this.core2 = new ModelRenderer(this, 0, 0);
         this.core2.setRotationPoint(0.0F, -2.0F, 0.0F);
         this.core2.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F);
         this.setRotateAngle(this.core2, -0.22759093F, 1.0016445F, 0.0F);
         this.forcefield2 = new ModelRenderer(this, 0, 24);
         this.forcefield2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.forcefield2.addBox(-1.5F, -1.5F, -6.0F, 3, 3, 1, 0.0F);
         this.setRotateAngle(this.forcefield2, (float) Math.PI, 0.0F, 0.0F);
         this.head = new ModelRenderer(this, 20, 4);
         this.head.setRotationPoint(0.0F, -2.0F, 0.0F);
         this.head.addBox(-2.0F, -2.0F, -6.0F, 4, 4, 2, 0.0F);
         this.camera2b = new ModelRenderer(this, 0, 10);
         this.camera2b.setRotationPoint(0.05F, 2.5F, 4.5F);
         this.camera2b.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.camera2b, 1.5025539F, 0.0F, 0.0F);
         this.camera1b = new ModelRenderer(this, 0, 10);
         this.camera1b.setRotationPoint(0.05F, 2.5F, 4.5F);
         this.camera1b.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.camera1b, 1.5025539F, 0.0F, 0.0F);
         this.shape1 = new ModelRenderer(this, 0, 0);
         this.shape1.setRotationPoint(0.0F, 2.0F, 1.0F);
         this.shape1.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
         this.setRotateAngle(this.shape1, 0.27314404F, 0.0F, 0.0F);
         this.camera3b = new ModelRenderer(this, 0, 10);
         this.camera3b.setRotationPoint(0.05F, 2.5F, 4.5F);
         this.camera3b.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.camera3b, 1.5025539F, 0.0F, 0.0F);
         this.blaster3 = new ModelRenderer(this, 18, 30);
         this.blaster3.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.blaster3.addBox(-4.0F, -0.5F, -5.0F, 8, 1, 1, 0.0F);
         this.setRotateAngle(this.blaster3, 0.0F, (float) (Math.PI / 2), 0.0F);
         this.blaster2 = new ModelRenderer(this, 18, 30);
         this.blaster2.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.blaster2.addBox(-4.0F, -0.5F, -5.0F, 8, 1, 1, 0.0F);
         this.armor2 = new ModelRenderer(this, 10, 19);
         this.armor2.setRotationPoint(0.0F, 11.1F, 0.0F);
         this.armor2.addBox(-3.4F, -0.2F, 0.0F, 1, 3, 6, 0.0F);
         this.setRotateAngle(this.armor2, -0.5009095F, -0.091106184F, 0.31869712F);
         this.camera1c = new ModelRenderer(this, 0, 10);
         this.camera1c.setRotationPoint(0.05F, 8.0F, 0.0F);
         this.camera1c.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.camera1c, 1.7301449F, 0.0F, 0.0F);
         this.weara2 = new ModelRenderer(this, 32, 13);
         this.weara2.setRotationPoint(1.0F, 0.1F, 2.0F);
         this.weara2.addBox(0.0F, 0.0F, 0.0F, 0, 6, 5, 0.0F);
         this.setRotateAngle(this.weara2, 0.091106184F, 0.5009095F, 0.0F);
         this.camera3d = new ModelRenderer(this, 4, 10);
         this.camera3d.setRotationPoint(0.05F, 3.6F, 0.0F);
         this.camera3d.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
         this.setRotateAngle(this.camera3d, -0.7285004F, 0.0F, 0.0F);
         this.core1 = new ModelRenderer(this, 0, 0);
         this.core1.setRotationPoint(0.0F, -2.0F, 0.0F);
         this.core1.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F);
         this.setRotateAngle(this.core1, 0.5009095F, -0.7740535F, 0.0F);
         this.camera3a = new ModelRenderer(this, 0, 10);
         this.camera3a.setRotationPoint(0.0F, -0.2F, -0.3F);
         this.camera3a.addBox(-0.5F, -1.5F, 4.0F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.camera3a, 2.2310543F, -2.5953045F, -1.1383038F);
         this.wear3 = new ModelRenderer(this, 32, -7);
         this.wear3.setRotationPoint(-1.0F, -3.5F, 0.0F);
         this.wear3.addBox(0.0F, -18.0F, 0.0F, 0, 18, 7, 0.0F);
         this.setRotateAngle(this.wear3, -0.18203785F, -0.3642502F, 0.0F);
         this.armor4 = new ModelRenderer(this, 18, 17);
         this.armor4.mirror = true;
         this.armor4.setRotationPoint(0.0F, 5.0F, 1.5F);
         this.armor4.addBox(2.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
         this.setRotateAngle(this.armor4, -0.3642502F, 0.27314404F, -0.18203785F);
         this.wear2 = new ModelRenderer(this, 32, -7);
         this.wear2.setRotationPoint(-1.0F, -2.5F, 1.0F);
         this.wear2.addBox(0.0F, -18.0F, 0.0F, 0, 18, 7, 0.0F);
         this.setRotateAngle(this.wear2, -0.4553564F, -0.7285004F, -0.045553092F);
         this.armor5 = new ModelRenderer(this, 10, 19);
         this.armor5.mirror = true;
         this.armor5.setRotationPoint(0.0F, 11.1F, 0.0F);
         this.armor5.addBox(2.4F, -0.2F, 0.0F, 1, 3, 6, 0.0F);
         this.setRotateAngle(this.armor5, -0.5009095F, 0.091106184F, -0.31869712F);
         this.blaster1 = new ModelRenderer(this, 20, 24);
         this.blaster1.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.blaster1.addBox(-1.0F, -1.0F, -7.0F, 2, 2, 4, 0.0F);
         this.forcefield1 = new ModelRenderer(this, 0, 24);
         this.forcefield1.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.forcefield1.addBox(-1.5F, -1.5F, -6.0F, 3, 3, 1, 0.0F);
         this.setRotateAngle(this.forcefield1, 0.0F, 0.0F, (float) (Math.PI / 4));
         this.grenade3 = new ModelRenderer(this, 32, 24);
         this.grenade3.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.grenade3.addBox(-1.0F, -1.0F, -4.0F, 5, 2, 1, 0.0F);
         this.setRotateAngle(this.grenade3, 0.0F, -0.68294734F, 0.0F);
         this.shape3 = new ModelRenderer(this, 0, 0);
         this.shape3.setRotationPoint(0.0F, 3.7F, -0.2F);
         this.shape3.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
         this.setRotateAngle(this.shape3, -0.27314404F, 0.0F, 0.0F);
         this.camera1a = new ModelRenderer(this, 0, 10);
         this.camera1a.setRotationPoint(0.0F, -2.0F, 0.0F);
         this.camera1a.addBox(-0.5F, -1.5F, 4.0F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.camera1a, 2.5041983F, 0.0F, 0.0F);
         this.camera1gl = new ModelRendererGlow(this, 9, 10, 240, false);
         this.camera1gl.setRotationPoint(0.0F, 2.0F, 2.0F);
         this.camera1gl.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
         this.setRotateAngle(this.camera1gl, 0.3642502F, 0.95609134F, 1.1383038F);
         this.armor3 = new ModelRenderer(this, 0, 20);
         this.armor3.setRotationPoint(0.0F, 17.5F, 0.1F);
         this.armor3.addBox(-4.3F, 1.0F, 0.0F, 1, 4, 8, 0.0F);
         this.setRotateAngle(this.armor3, -0.63739425F, 0.045553092F, 0.4553564F);
         this.armor6 = new ModelRenderer(this, 0, 20);
         this.armor6.mirror = true;
         this.armor6.setRotationPoint(0.0F, 17.5F, 0.1F);
         this.armor6.addBox(3.3F, 1.0F, 0.0F, 1, 4, 8, 0.0F);
         this.setRotateAngle(this.armor6, -0.63739425F, -0.045553092F, -0.4553564F);
         this.wear1 = new ModelRenderer(this, 32, -7);
         this.wear1.setRotationPoint(-1.0F, -0.5F, 1.0F);
         this.wear1.addBox(0.0F, -18.0F, 0.0F, 0, 18, 7, 0.0F);
         this.setRotateAngle(this.wear1, -0.68294734F, -0.8651597F, -0.091106184F);
         this.camera3c = new ModelRenderer(this, 0, 10);
         this.camera3c.setRotationPoint(0.05F, 8.0F, 0.0F);
         this.camera3c.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.camera3c, 1.7301449F, 0.0F, 0.0F);
         this.shape2 = new ModelRenderer(this, 0, 0);
         this.shape2.setRotationPoint(0.0F, 3.8F, 0.0F);
         this.shape2.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
         this.setRotateAngle(this.shape2, -0.4098033F, 0.0F, 0.0F);
         this.shape4 = new ModelRenderer(this, 0, 0);
         this.shape4.setRotationPoint(0.0F, 3.7F, 0.2F);
         this.shape4.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
         this.setRotateAngle(this.shape4, 0.22759093F, 0.0F, 0.0F);
         this.camera1d = new ModelRenderer(this, 4, 10);
         this.camera1d.setRotationPoint(0.05F, 3.6F, 0.0F);
         this.camera1d.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
         this.setRotateAngle(this.camera1d, -0.7285004F, 0.0F, 0.0F);
         this.head2 = new ModelRenderer(this, 15, 0);
         this.head2.setRotationPoint(0.0F, -2.0F, 0.0F);
         this.head2.addBox(-1.0F, -1.0F, -8.0F, 2, 2, 2, 0.0F);
         this.wear5 = new ModelRenderer(this, 32, -7);
         this.wear5.setRotationPoint(1.0F, -2.5F, 1.0F);
         this.wear5.addBox(0.0F, -18.0F, 0.0F, 0, 18, 7, 0.0F);
         this.setRotateAngle(this.wear5, -0.4553564F, 0.7285004F, 0.045553092F);
         this.wear6 = new ModelRenderer(this, 32, -7);
         this.wear6.setRotationPoint(1.0F, -0.5F, 1.0F);
         this.wear6.addBox(0.0F, -18.0F, 0.0F, 0, 18, 7, 0.0F);
         this.setRotateAngle(this.wear6, -0.68294734F, 0.8651597F, 0.091106184F);
         this.grenade1 = new ModelRenderer(this, 20, 10);
         this.grenade1.setRotationPoint(0.0F, 10.0F, 0.0F);
         this.grenade1.addBox(-1.5F, -1.5F, -6.0F, 3, 3, 3, 0.0F);
         this.grenade2 = new ModelRenderer(this, 32, 24);
         this.grenade2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.grenade2.addBox(-4.0F, -1.0F, -4.0F, 5, 2, 1, 0.0F);
         this.setRotateAngle(this.grenade2, 0.0F, 0.68294734F, 0.0F);
         this.weara1 = new ModelRenderer(this, 32, 13);
         this.weara1.setRotationPoint(-1.0F, 0.1F, 2.0F);
         this.weara1.addBox(0.0F, 0.0F, 0.0F, 0, 6, 5, 0.0F);
         this.setRotateAngle(this.weara1, 0.091106184F, -0.5009095F, 0.0F);
         this.wear4 = new ModelRenderer(this, 32, -7);
         this.wear4.setRotationPoint(1.0F, -3.5F, 0.0F);
         this.wear4.addBox(0.0F, -18.0F, 0.0F, 0, 18, 7, 0.0F);
         this.setRotateAngle(this.wear4, -0.18203785F, 0.3642502F, 0.0F);
         this.camera1a.addChild(this.camera2a);
         this.camera3d.addChild(this.camera3gl);
         this.camera2d.addChild(this.camera2gl);
         this.camera2b.addChild(this.camera2c);
         this.blaster2.addChild(this.blaster4);
         this.camera2c.addChild(this.camera2d);
         this.blaster2.addChild(this.blaster5);
         this.forcefield1.addChild(this.forcefield2);
         this.camera2a.addChild(this.camera2b);
         this.camera1a.addChild(this.camera1b);
         this.camera3a.addChild(this.camera3b);
         this.blaster2.addChild(this.blaster3);
         this.camera1b.addChild(this.camera1c);
         this.camera3c.addChild(this.camera3d);
         this.camera1a.addChild(this.camera3a);
         this.grenade1.addChild(this.grenade3);
         this.shape2.addChild(this.shape3);
         this.camera1d.addChild(this.camera1gl);
         this.camera3b.addChild(this.camera3c);
         this.shape1.addChild(this.shape2);
         this.shape3.addChild(this.shape4);
         this.camera1c.addChild(this.camera1d);
         this.grenade1.addChild(this.grenade2);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         int weapon = 0;
         boolean shield = false;
         float y = f3 * (float) (Math.PI / 180.0);
         float x = f4 * (float) (Math.PI / 180.0);
         this.head.rotateAngleY = y;
         this.head.rotateAngleX = x;
         this.head2.rotateAngleY = y;
         this.head2.rotateAngleX = x;
         if (isAbstractMob) {
            weapon = ((AbstractMob)entity).var1;
            shield = ((AbstractMob)entity).var2 > 0.0F;
         }

         if (weapon == 1) {
            this.blaster1.rotateAngleY = y;
            this.blaster1.rotateAngleX = x;
            this.blaster2.rotateAngleY = y;
            this.blaster2.rotateAngleX = x;
            this.blaster1.render(f5);
            this.blaster2.render(f5);
         }

         if (weapon == 2) {
            this.grenade1.rotateAngleY = y;
            this.grenade1.rotateAngleX = x;
            this.grenade1.render(f5);
         }

         if (weapon == 3) {
            this.forcefield1.render(f5);
         }

         float armAdd = MathHelper.sin(AnimationTimer.tick / 20.0F) * 0.1F;
         this.camera1a.rotateAngleY = y;
         this.camera1a.rotateAngleX = 2.5041983F + x;
         this.armor1.rotateAngleX = -0.3642502F + armAdd;
         this.armor2.rotateAngleX = -0.5009095F + armAdd;
         this.armor3.rotateAngleX = -0.63739425F + armAdd;
         this.armor4.rotateAngleX = -0.3642502F + armAdd;
         this.armor5.rotateAngleX = -0.5009095F + armAdd;
         this.armor6.rotateAngleX = -0.63739425F + armAdd;
         float r1 = AnimationTimer.rainbow1;
         float r2 = AnimationTimer.rainbow2;
         float r3 = AnimationTimer.rainbow3;
         int tick = AnimationTimer.tick;
         this.setRotateAngle(this.core1, r1 * 0.27F, tick * 0.087F, r2 * 0.115F);
         this.setRotateAngle(this.core2, r3 * 0.23F, r1 * 0.15F, tick * 0.087F);
         this.setRotateAngle(this.core3, tick * 0.087F, r3 * 0.185F, r2 * 0.165F);
         float coreT = AnimationTimer.tick * 0.2F;
         this.camera1gl.rotateAngleZ = coreT;
         this.camera2gl.rotateAngleZ = coreT;
         this.camera3gl.rotateAngleZ = coreT;
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.armor1.offsetX, this.armor1.offsetY, this.armor1.offsetZ);
         GlStateManager.translate(this.armor1.rotationPointX * f5, this.armor1.rotationPointY * f5, this.armor1.rotationPointZ * f5);
         GlStateManager.scale(0.8, 1.5, 0.6);
         GlStateManager.translate(-this.armor1.offsetX, -this.armor1.offsetY, -this.armor1.offsetZ);
         GlStateManager.translate(-this.armor1.rotationPointX * f5, -this.armor1.rotationPointY * f5, -this.armor1.rotationPointZ * f5);
         this.armor1.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.armor4.offsetX, this.armor4.offsetY, this.armor4.offsetZ);
         GlStateManager.translate(this.armor4.rotationPointX * f5, this.armor4.rotationPointY * f5, this.armor4.rotationPointZ * f5);
         GlStateManager.scale(0.8, 1.5, 0.6);
         GlStateManager.translate(-this.armor4.offsetX, -this.armor4.offsetY, -this.armor4.offsetZ);
         GlStateManager.translate(-this.armor4.rotationPointX * f5, -this.armor4.rotationPointY * f5, -this.armor4.rotationPointZ * f5);
         this.armor4.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.armor2.offsetX, this.armor2.offsetY, this.armor2.offsetZ);
         GlStateManager.translate(this.armor2.rotationPointX * f5, this.armor2.rotationPointY * f5, this.armor2.rotationPointZ * f5);
         GlStateManager.scale(0.8, 1.5, 0.6);
         GlStateManager.translate(-this.armor2.offsetX, -this.armor2.offsetY, -this.armor2.offsetZ);
         GlStateManager.translate(-this.armor2.rotationPointX * f5, -this.armor2.rotationPointY * f5, -this.armor2.rotationPointZ * f5);
         this.armor2.render(f5);
         GlStateManager.popMatrix();
         this.head.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.armor5.offsetX, this.armor5.offsetY, this.armor5.offsetZ);
         GlStateManager.translate(this.armor5.rotationPointX * f5, this.armor5.rotationPointY * f5, this.armor5.rotationPointZ * f5);
         GlStateManager.scale(0.8, 1.5, 0.6);
         GlStateManager.translate(-this.armor5.offsetX, -this.armor5.offsetY, -this.armor5.offsetZ);
         GlStateManager.translate(-this.armor5.rotationPointX * f5, -this.armor5.rotationPointY * f5, -this.armor5.rotationPointZ * f5);
         this.armor5.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
         GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
         GlStateManager.scale(1.1, 0.9, 1.1);
         GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
         GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
         this.shape1.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.armor3.offsetX, this.armor3.offsetY, this.armor3.offsetZ);
         GlStateManager.translate(this.armor3.rotationPointX * f5, this.armor3.rotationPointY * f5, this.armor3.rotationPointZ * f5);
         GlStateManager.scale(0.8, 1.5, 0.6);
         GlStateManager.translate(-this.armor3.offsetX, -this.armor3.offsetY, -this.armor3.offsetZ);
         GlStateManager.translate(-this.armor3.rotationPointX * f5, -this.armor3.rotationPointY * f5, -this.armor3.rotationPointZ * f5);
         this.armor3.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.camera1a.offsetX, this.camera1a.offsetY, this.camera1a.offsetZ);
         GlStateManager.translate(this.camera1a.rotationPointX * f5, this.camera1a.rotationPointY * f5, this.camera1a.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.camera1a.offsetX, -this.camera1a.offsetY, -this.camera1a.offsetZ);
         GlStateManager.translate(-this.camera1a.rotationPointX * f5, -this.camera1a.rotationPointY * f5, -this.camera1a.rotationPointZ * f5);
         this.camera1a.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.armor6.offsetX, this.armor6.offsetY, this.armor6.offsetZ);
         GlStateManager.translate(this.armor6.rotationPointX * f5, this.armor6.rotationPointY * f5, this.armor6.rotationPointZ * f5);
         GlStateManager.scale(0.8, 1.5, 0.6);
         GlStateManager.translate(-this.armor6.offsetX, -this.armor6.offsetY, -this.armor6.offsetZ);
         GlStateManager.translate(-this.armor6.rotationPointX * f5, -this.armor6.rotationPointY * f5, -this.armor6.rotationPointZ * f5);
         this.armor6.render(f5);
         GlStateManager.popMatrix();
         this.head2.render(f5);
         GlStateManager.enableBlend();
         light(240, false);
         alphaGlow();
         this.core1.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.wear1.offsetX, this.wear1.offsetY, this.wear1.offsetZ);
         GlStateManager.translate(this.wear1.rotationPointX * f5, this.wear1.rotationPointY * f5, this.wear1.rotationPointZ * f5);
         GlStateManager.scale(0.7, 0.7, 0.7);
         GlStateManager.translate(-this.wear1.offsetX, -this.wear1.offsetY, -this.wear1.offsetZ);
         GlStateManager.translate(-this.wear1.rotationPointX * f5, -this.wear1.rotationPointY * f5, -this.wear1.rotationPointZ * f5);
         this.wear1.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.wear3.offsetX, this.wear3.offsetY, this.wear3.offsetZ);
         GlStateManager.translate(this.wear3.rotationPointX * f5, this.wear3.rotationPointY * f5, this.wear3.rotationPointZ * f5);
         GlStateManager.scale(0.8, 0.8, 0.8);
         GlStateManager.translate(-this.wear3.offsetX, -this.wear3.offsetY, -this.wear3.offsetZ);
         GlStateManager.translate(-this.wear3.rotationPointX * f5, -this.wear3.rotationPointY * f5, -this.wear3.rotationPointZ * f5);
         this.wear3.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.wear4.offsetX, this.wear4.offsetY, this.wear4.offsetZ);
         GlStateManager.translate(this.wear4.rotationPointX * f5, this.wear4.rotationPointY * f5, this.wear4.rotationPointZ * f5);
         GlStateManager.scale(0.8, 0.8, 0.8);
         GlStateManager.translate(-this.wear4.offsetX, -this.wear4.offsetY, -this.wear4.offsetZ);
         GlStateManager.translate(-this.wear4.rotationPointX * f5, -this.wear4.rotationPointY * f5, -this.wear4.rotationPointZ * f5);
         this.wear4.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.weara2.offsetX, this.weara2.offsetY, this.weara2.offsetZ);
         GlStateManager.translate(this.weara2.rotationPointX * f5, this.weara2.rotationPointY * f5, this.weara2.rotationPointZ * f5);
         GlStateManager.scale(0.7, 0.7, 0.7);
         GlStateManager.translate(-this.weara2.offsetX, -this.weara2.offsetY, -this.weara2.offsetZ);
         GlStateManager.translate(-this.weara2.rotationPointX * f5, -this.weara2.rotationPointY * f5, -this.weara2.rotationPointZ * f5);
         this.weara2.render(f5);
         GlStateManager.popMatrix();
         this.core3.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.wear5.offsetX, this.wear5.offsetY, this.wear5.offsetZ);
         GlStateManager.translate(this.wear5.rotationPointX * f5, this.wear5.rotationPointY * f5, this.wear5.rotationPointZ * f5);
         GlStateManager.scale(0.75, 0.75, 0.75);
         GlStateManager.translate(-this.wear5.offsetX, -this.wear5.offsetY, -this.wear5.offsetZ);
         GlStateManager.translate(-this.wear5.rotationPointX * f5, -this.wear5.rotationPointY * f5, -this.wear5.rotationPointZ * f5);
         this.wear5.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.wear2.offsetX, this.wear2.offsetY, this.wear2.offsetZ);
         GlStateManager.translate(this.wear2.rotationPointX * f5, this.wear2.rotationPointY * f5, this.wear2.rotationPointZ * f5);
         GlStateManager.scale(0.75, 0.75, 0.75);
         GlStateManager.translate(-this.wear2.offsetX, -this.wear2.offsetY, -this.wear2.offsetZ);
         GlStateManager.translate(-this.wear2.rotationPointX * f5, -this.wear2.rotationPointY * f5, -this.wear2.rotationPointZ * f5);
         this.wear2.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.weara1.offsetX, this.weara1.offsetY, this.weara1.offsetZ);
         GlStateManager.translate(this.weara1.rotationPointX * f5, this.weara1.rotationPointY * f5, this.weara1.rotationPointZ * f5);
         GlStateManager.scale(0.7, 0.7, 0.7);
         GlStateManager.translate(-this.weara1.offsetX, -this.weara1.offsetY, -this.weara1.offsetZ);
         GlStateManager.translate(-this.weara1.rotationPointX * f5, -this.weara1.rotationPointY * f5, -this.weara1.rotationPointZ * f5);
         this.weara1.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.wear6.offsetX, this.wear6.offsetY, this.wear6.offsetZ);
         GlStateManager.translate(this.wear6.rotationPointX * f5, this.wear6.rotationPointY * f5, this.wear6.rotationPointZ * f5);
         GlStateManager.scale(0.7, 0.7, 0.7);
         GlStateManager.translate(-this.wear6.offsetX, -this.wear6.offsetY, -this.wear6.offsetZ);
         GlStateManager.translate(-this.wear6.rotationPointX * f5, -this.wear6.rotationPointY * f5, -this.wear6.rotationPointZ * f5);
         this.wear6.render(f5);
         GlStateManager.popMatrix();
         this.core2.render(f5);
         if (shield) {
            GL11.glDepthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(ModelsStormledgeMob.texFIELD);
            GlStateManager.translate(0.0F, 0.325F, 0.0F);
            ModelsStormledgeMob.forcefieldModel.renderAnimated(8.0F, AnimationTimer.normaltick);
            GL11.glDepthMask(true);
         }

         returnlight();
         alphaGlowDisable();
         GlStateManager.disableBlend();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }
}
