//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.potions.SirenSong;
import com.Vivern.Arpg.renders.ModelRendererGlow;
import com.Vivern.Arpg.renders.ModelRendererUncompiled;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelsAquaticaMobs {
   public static class ArchelonCreationModel extends AbstractMobModel {
      public ModelRenderer shapeBombsM;
      public ModelRenderer shapeBreedsM;
      public ModelRenderer shapeJellyM;
      public ModelRenderer shapesp;
      public ModelRenderer shapesp_1;
      public ModelRenderer shapesp_2;
      public ModelRenderer shapesp_3;
      public ModelRenderer shapeBreeds;
      public ModelRenderer shapeBreeds_1;
      public ModelRenderer shapeBreeds_2;
      public ModelRenderer shapeBreeds_3;
      public ModelRenderer shapeBreeds_4;
      public ModelRenderer shapeBreeds_5;
      public ModelRenderer shapeJelly;
      public ModelRenderer shapeJelly_1;
      public ModelRenderer shapeJelly_2;
      public ModelRenderer shapeJelly_3;
      public ModelRenderer shapeJelly_4;

      public ArchelonCreationModel() {
         this.textureWidth = 96;
         this.textureHeight = 32;
         this.shapeBreeds_1 = new ModelRenderer(this, 52, 16);
         this.shapeBreeds_1.setRotationPoint(-3.0F, -1.0F, -2.0F);
         this.shapeBreeds_1.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
         this.setRotateAngle(this.shapeBreeds_1, 1.5025539F, 0.8651597F, 0.68294734F);
         this.shapeBreeds_2 = new ModelRenderer(this, 52, 16);
         this.shapeBreeds_2.setRotationPoint(1.0F, 3.0F, 0.0F);
         this.shapeBreeds_2.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
         this.setRotateAngle(this.shapeBreeds_2, 0.31869712F, 0.31869712F, 2.959555F);
         this.shapeBreedsM = new ModelRenderer(this, 32, 0);
         this.shapeBreedsM.setRotationPoint(0.0F, 16.0F, 0.0F);
         this.shapeBreedsM.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
         this.shapesp_1 = new ModelRenderer(this, 0, 2);
         this.shapesp_1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shapesp_1.addBox(0.0F, -7.0F, -7.0F, 0, 14, 14, 0.0F);
         this.setRotateAngle(this.shapesp_1, 0.0F, (float) (Math.PI / 3), 0.0F);
         this.shapeJelly_1 = new ModelRenderer(this, 52, 16);
         this.shapeJelly_1.setRotationPoint(2.0F, 1.0F, 4.0F);
         this.shapeJelly_1.addBox(0.0F, 0.0F, -6.0F, 14, 0, 16, 0.0F);
         this.setRotateAngle(this.shapeJelly_1, 0.0F, 0.0F, 0.31869712F);
         this.shapeJelly_2 = new ModelRenderer(this, 52, 16);
         this.shapeJelly_2.setRotationPoint(2.0F, -1.0F, 4.0F);
         this.shapeJelly_2.addBox(0.0F, 0.0F, -6.0F, 14, 0, 16, 0.0F);
         this.setRotateAngle(this.shapeJelly_2, 0.0F, 0.0F, -0.31869712F);
         this.shapeJelly_4 = new ModelRenderer(this, 52, 16);
         this.shapeJelly_4.setRotationPoint(-2.0F, -1.0F, 4.0F);
         this.shapeJelly_4.addBox(-14.0F, 0.0F, -6.0F, 14, 0, 16, 0.0F);
         this.setRotateAngle(this.shapeJelly_4, 0.0F, 0.0F, 0.31869712F);
         this.shapeBreeds = new ModelRenderer(this, 28, 16);
         this.shapeBreeds.setRotationPoint(1.0F, -2.0F, 1.0F);
         this.shapeBreeds.addBox(-3.0F, -8.0F, -3.0F, 6, 8, 6, 0.0F);
         this.setRotateAngle(this.shapeBreeds, -0.5462881F, 0.7740535F, 0.0F);
         this.shapesp_3 = new ModelRenderer(this, 0, 2);
         this.shapesp_3.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shapesp_3.addBox(0.0F, -7.0F, -7.0F, 0, 14, 14, 0.0F);
         this.setRotateAngle(this.shapesp_3, 0.0F, 0.0F, (float) (-Math.PI / 2));
         this.shapeJelly = new ModelRenderer(this, 56, 0);
         this.shapeJelly.setRotationPoint(0.0F, 0.0F, -2.0F);
         this.shapeJelly.addBox(-3.0F, -3.0F, -4.0F, 6, 6, 2, 0.0F);
         this.shapeBreeds_4 = new ModelRenderer(this, 52, 16);
         this.shapeBreeds_4.setRotationPoint(-1.0F, 2.0F, -3.0F);
         this.shapeBreeds_4.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
         this.setRotateAngle(this.shapeBreeds_4, 1.6845918F, -0.4553564F, 1.6845918F);
         this.shapesp = new ModelRenderer(this, 0, 2);
         this.shapesp.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shapesp.addBox(0.0F, -7.0F, -7.0F, 0, 14, 14, 0.0F);
         this.setRotateAngle(this.shapesp, 0.0F, (float) (-Math.PI / 3), 0.0F);
         this.shapeBreeds_3 = new ModelRenderer(this, 28, 16);
         this.shapeBreeds_3.setRotationPoint(-1.0F, 1.0F, 2.0F);
         this.shapeBreeds_3.addBox(-3.0F, -8.0F, -3.0F, 6, 8, 6, 0.0F);
         this.setRotateAngle(this.shapeBreeds_3, -1.2292354F, -0.7740535F, -1.0927507F);
         this.shapeJellyM = new ModelRenderer(this, 64, 0);
         this.shapeJellyM.setRotationPoint(0.0F, 16.0F, 0.0F);
         this.shapeJellyM.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
         this.setRotateAngle(this.shapeJellyM, 0.5009095F, 0.0F, 0.0F);
         this.shapesp_2 = new ModelRenderer(this, 0, 2);
         this.shapesp_2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shapesp_2.addBox(0.0F, -7.0F, -7.0F, 0, 14, 14, 0.0F);
         this.shapeJelly_3 = new ModelRenderer(this, 52, 16);
         this.shapeJelly_3.setRotationPoint(-2.0F, 1.0F, 4.0F);
         this.shapeJelly_3.addBox(-14.0F, 0.0F, -6.0F, 14, 0, 16, 0.0F);
         this.setRotateAngle(this.shapeJelly_3, 0.0F, 0.0F, -0.31869712F);
         this.shapeBreeds_5 = new ModelRenderer(this, 52, 16);
         this.shapeBreeds_5.setRotationPoint(3.0F, 1.0F, 0.0F);
         this.shapeBreeds_5.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
         this.setRotateAngle(this.shapeBreeds_5, 0.27314404F, 0.0F, 1.4114478F);
         this.shapeBombsM = new ModelRenderer(this, 0, 0);
         this.shapeBombsM.setRotationPoint(0.0F, 16.0F, 0.0F);
         this.shapeBombsM.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
         this.setRotateAngle(this.shapeBombsM, -0.59184116F, 0.0F, 0.0F);
         this.shapeBreedsM.addChild(this.shapeBreeds_1);
         this.shapeBreedsM.addChild(this.shapeBreeds_2);
         this.shapeBombsM.addChild(this.shapesp_1);
         this.shapeJellyM.addChild(this.shapeJelly_1);
         this.shapeJellyM.addChild(this.shapeJelly_2);
         this.shapeJellyM.addChild(this.shapeJelly_4);
         this.shapeBreedsM.addChild(this.shapeBreeds);
         this.shapeBombsM.addChild(this.shapesp_3);
         this.shapeJellyM.addChild(this.shapeJelly);
         this.shapeBreedsM.addChild(this.shapeBreeds_4);
         this.shapeBombsM.addChild(this.shapesp);
         this.shapeBreedsM.addChild(this.shapeBreeds_3);
         this.shapeBombsM.addChild(this.shapesp_2);
         this.shapeJellyM.addChild(this.shapeJelly_3);
         this.shapeBreedsM.addChild(this.shapeBreeds_5);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float eid = 53.0F;
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -0.6F, 0.0F);
         GlStateManager.enableBlend();
         alphaGlowDisable();
         f5 *= 1.6F;
         int type = 0;
         if (isAbstractMob) {
            type = ((AbstractMob)entity).var1;
         }

         if (type == 0) {
            this.shapeBombsM.rotateAngleY = AnimationTimer.tick * 0.01F;
            this.shapeBombsM.render(f5);
         }

         if (type == 1) {
            light(110, true);
            this.shapeBreedsM.rotateAngleY = MathHelper.sin(AnimationTimer.tick / 27.0F + eid) * 0.25F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(this.shapeBreedsM.offsetX, this.shapeBreedsM.offsetY, this.shapeBreedsM.offsetZ);
            GlStateManager.translate(this.shapeBreedsM.rotationPointX * f5, this.shapeBreedsM.rotationPointY * f5, this.shapeBreedsM.rotationPointZ * f5);
            float siz1 = MathHelper.cos(AnimationTimer.tick / 27.0F + eid) * 0.15F;
            float siz1a = MathHelper.sin(AnimationTimer.tick / 27.0F + eid) * 0.15F;
            GlStateManager.scale(1.0 + siz1, 1.0 + siz1a, 1.0 + siz1);
            GlStateManager.translate(-this.shapeBreedsM.offsetX, -this.shapeBreedsM.offsetY, -this.shapeBreedsM.offsetZ);
            GlStateManager.translate(-this.shapeBreedsM.rotationPointX * f5, -this.shapeBreedsM.rotationPointY * f5, -this.shapeBreedsM.rotationPointZ * f5);
            this.shapeBreedsM.render(f5);
            GlStateManager.popMatrix();
            this.shapeBreedsM.rotateAngleX = 3.14F;
            this.shapeBreedsM.rotateAngleY = eid * 13.267013F;
            this.shapeBreedsM.rotateAngleZ = eid * 14.362367F + MathHelper.cos(AnimationTimer.tick / 27.0F + eid * 1.35465F) * 0.25F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(this.shapeBreedsM.offsetX, this.shapeBreedsM.offsetY, this.shapeBreedsM.offsetZ);
            GlStateManager.translate(this.shapeBreedsM.rotationPointX * f5, this.shapeBreedsM.rotationPointY * f5, this.shapeBreedsM.rotationPointZ * f5);
            float siz2 = MathHelper.sin(AnimationTimer.tick / 27.0F + eid * 1.35465F) * 0.17F;
            float siz2a = MathHelper.cos(AnimationTimer.tick / 27.0F + eid * 1.35465F) * 0.17F;
            GlStateManager.scale(1.0 + siz2, 1.0 + siz2a, 1.0 + siz2);
            GlStateManager.translate(-this.shapeBreedsM.offsetX, -this.shapeBreedsM.offsetY, -this.shapeBreedsM.offsetZ);
            GlStateManager.translate(-this.shapeBreedsM.rotationPointX * f5, -this.shapeBreedsM.rotationPointY * f5, -this.shapeBreedsM.rotationPointZ * f5);
            this.shapeBreedsM.render(f5);
            GlStateManager.popMatrix();
            returnlight();
         }

         if (type == 2) {
            this.shapeJelly_1.rotateAngleY = MathHelper.sin(AnimationTimer.tick / 16.0F + eid) * 0.3F;
            this.shapeJelly_2.rotateAngleY = this.shapeJelly_1.rotateAngleY;
            this.shapeJelly_3.rotateAngleY = -this.shapeJelly_1.rotateAngleY;
            this.shapeJelly_4.rotateAngleY = -this.shapeJelly_1.rotateAngleY;
            float sizAdd = -MathHelper.cos(-(AnimationTimer.tick / 16.0F + eid)) * 0.1F;
            light(70, true);
            GlStateManager.pushMatrix();
            GlStateManager.translate(this.shapeJellyM.offsetX, this.shapeJellyM.offsetY, this.shapeJellyM.offsetZ);
            GlStateManager.translate(this.shapeJellyM.rotationPointX * f5, this.shapeJellyM.rotationPointY * f5, this.shapeJellyM.rotationPointZ * f5);
            GlStateManager.scale(1.2 + sizAdd, 1.2 + sizAdd, 1.2 + sizAdd);
            GlStateManager.translate(-this.shapeJellyM.offsetX, -this.shapeJellyM.offsetY, -this.shapeJellyM.offsetZ);
            GlStateManager.translate(-this.shapeJellyM.rotationPointX * f5, -this.shapeJellyM.rotationPointY * f5, -this.shapeJellyM.rotationPointZ * f5);
            this.shapeJellyM.render(f5);
            GlStateManager.popMatrix();
            returnlight();
         }

         GlStateManager.popMatrix();
         GlStateManager.disableBlend();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class ArchelonModel extends AbstractMobModel {
      public ModelRenderer head1;
      public ModelRenderer carapace;
      public ModelRenderer carapace_1;
      public ModelRenderer carapace_2;
      public ModelRenderer carapace_3;
      public ModelRenderer carapace_4;
      public ModelRenderer carapace_5;
      public ModelRenderer body;
      public ModelRenderer armfin1;
      public ModelRenderer armfin2;
      public ModelRenderer armfin1B;
      public ModelRenderer armfin2B;
      public ModelRenderer plast;
      public ModelRenderer base;
      public ModelRenderer base_1;
      public ModelRenderer head2;
      public ModelRenderer head3;
      public ModelRenderer head4;
      public ModelRenderer carapace_6;
      public ModelRenderer carapace_7;
      public ModelRenderer carapace_8;
      public ModelRenderer tail;
      public ModelRenderer armfin1A;
      public ModelRenderer armfin2A;

      public ArchelonModel() {
         this.textureWidth = 96;
         this.textureHeight = 80;
         this.carapace_4 = new ModelRenderer(this, 59, 0);
         this.carapace_4.setRotationPoint(4.0F, 10.0F, 1.3F);
         this.carapace_4.addBox(-0.5F, -3.0F, -3.0F, 5, 8, 13, 0.0F);
         this.setRotateAngle(this.carapace_4, 0.3642502F, 0.0F, 0.59184116F);
         this.body = new ModelRenderer(this, 20, 6);
         this.body.setRotationPoint(0.0F, 16.0F, 7.0F);
         this.body.addBox(-5.0F, -4.0F, -2.0F, 10, 6, 15, 0.0F);
         this.head2 = new ModelRenderer(this, 0, 31);
         this.head2.setRotationPoint(0.0F, -1.0F, -6.5F);
         this.head2.addBox(-4.0F, -4.0F, -7.0F, 8, 8, 8, 0.0F);
         this.carapace_8 = new ModelRenderer(this, 48, 44);
         this.carapace_8.setRotationPoint(0.0F, -1.0F, 9.0F);
         this.carapace_8.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
         this.setRotateAngle(this.carapace_8, -1.8668041F, 0.0F, -0.18203785F);
         this.head3 = new ModelRenderer(this, 63, 21);
         this.head3.setRotationPoint(0.0F, -1.0F, -6.5F);
         this.head3.addBox(-3.0F, -3.0F, -3.0F, 6, 4, 10, 0.0F);
         this.setRotateAngle(this.head3, 0.68294734F, 0.0F, 0.0F);
         this.carapace = new ModelRenderer(this, 17, 41);
         this.carapace.setRotationPoint(0.0F, 10.0F, -7.0F);
         this.carapace.addBox(-4.0F, -4.0F, -2.0F, 8, 8, 15, 0.0F);
         this.setRotateAngle(this.carapace, (float) (Math.PI / 3), 0.0F, 0.0F);
         this.armfin1A = new ModelRenderer(this, 74, 54);
         this.armfin1A.setRotationPoint(0.2F, 12.0F, 0.0F);
         this.armfin1A.addBox(-2.0F, -0.5F, -1.0F, 2, 12, 8, 0.0F);
         this.setRotateAngle(this.armfin1A, 0.8196066F, 0.18203785F, -0.18203785F);
         this.carapace_1 = new ModelRenderer(this, 17, 41);
         this.carapace_1.mirror = true;
         this.carapace_1.setRotationPoint(-6.0F, 10.0F, -6.0F);
         this.carapace_1.addBox(-5.0F, -4.0F, -5.0F, 8, 8, 15, 0.0F);
         this.setRotateAngle(this.carapace_1, 0.7740535F, 0.22759093F, -0.5009095F);
         this.carapace_6 = new ModelRenderer(this, 48, 44);
         this.carapace_6.setRotationPoint(0.0F, -1.0F, 12.0F);
         this.carapace_6.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
         this.setRotateAngle(this.carapace_6, -1.8668041F, 0.0F, 0.0F);
         this.carapace_2 = new ModelRenderer(this, 17, 41);
         this.carapace_2.setRotationPoint(6.0F, 10.0F, -6.0F);
         this.carapace_2.addBox(-3.0F, -4.0F, -5.0F, 8, 8, 15, 0.0F);
         this.setRotateAngle(this.carapace_2, 0.7740535F, -0.22759093F, 0.5009095F);
         this.armfin1B = new ModelRenderer(this, 74, 54);
         this.armfin1B.setRotationPoint(5.0F, 17.0F, 6.0F);
         this.armfin1B.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 8, 0.0F);
         this.setRotateAngle(this.armfin1B, 0.8196066F, 0.13665928F, -1.1383038F);
         this.armfin2A = new ModelRenderer(this, 74, 54);
         this.armfin2A.mirror = true;
         this.armfin2A.setRotationPoint(-0.2F, 12.0F, 0.0F);
         this.armfin2A.addBox(0.0F, -0.5F, -1.0F, 2, 12, 8, 0.0F);
         this.setRotateAngle(this.armfin2A, 0.8196066F, -0.18203785F, 0.18203785F);
         this.armfin2B = new ModelRenderer(this, 74, 54);
         this.armfin2B.mirror = true;
         this.armfin2B.setRotationPoint(-5.0F, 17.0F, 6.0F);
         this.armfin2B.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 8, 0.0F);
         this.setRotateAngle(this.armfin2B, 0.8196066F, -0.13665928F, 1.1383038F);
         this.carapace_5 = new ModelRenderer(this, 59, 0);
         this.carapace_5.mirror = true;
         this.carapace_5.setRotationPoint(-4.0F, 10.0F, 1.3F);
         this.carapace_5.addBox(-4.5F, -3.0F, -3.0F, 5, 8, 13, 0.0F);
         this.setRotateAngle(this.carapace_5, 0.3642502F, 0.0F, -0.59184116F);
         this.head4 = new ModelRenderer(this, 63, 35);
         this.head4.setRotationPoint(0.0F, 4.0F, -7.5F);
         this.head4.addBox(-3.0F, -4.0F, -3.0F, 6, 4, 10, 0.0F);
         this.setRotateAngle(this.head4, -0.18203785F, 0.0F, 0.0F);
         this.carapace_3 = new ModelRenderer(this, 20, 6);
         this.carapace_3.setRotationPoint(0.0F, 10.0F, 2.0F);
         this.carapace_3.addBox(-5.0F, -3.0F, -2.0F, 10, 6, 15, 0.0F);
         this.setRotateAngle(this.carapace_3, 0.5009095F, 0.0F, 0.0F);
         this.base_1 = new ModelRenderer(this, -26, 0);
         this.base_1.setRotationPoint(0.0F, 10.0F, 2.0F);
         this.base_1.addBox(5.0F, -2.0F, 4.0F, 10, 0, 26, 0.0F);
         this.setRotateAngle(this.base_1, 0.5009095F, 0.0F, 0.0F);
         this.base = new ModelRenderer(this, -26, 0);
         this.base.mirror = true;
         this.base.setRotationPoint(0.0F, 10.0F, 2.0F);
         this.base.addBox(-15.0F, -2.0F, 4.0F, 10, 0, 26, 0.0F);
         this.setRotateAngle(this.base, 0.5009095F, 0.0F, 0.0F);
         this.carapace_7 = new ModelRenderer(this, 48, 44);
         this.carapace_7.mirror = true;
         this.carapace_7.setRotationPoint(0.0F, -1.0F, 9.0F);
         this.carapace_7.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
         this.setRotateAngle(this.carapace_7, -1.8668041F, 0.0F, 0.18203785F);
         this.armfin2 = new ModelRenderer(this, 0, 47);
         this.armfin2.mirror = true;
         this.armfin2.setRotationPoint(-3.0F, 14.0F, -9.0F);
         this.armfin2.addBox(-1.0F, 0.0F, -1.0F, 3, 12, 5, 0.0F);
         this.setRotateAngle(this.armfin2, -0.59184116F, 0.0F, 1.1838568F);
         this.tail = new ModelRenderer(this, 32, 27);
         this.tail.setRotationPoint(0.0F, -1.0F, 13.0F);
         this.tail.addBox(-2.0F, -2.0F, -1.0F, 4, 4, 10, 0.0F);
         this.armfin1 = new ModelRenderer(this, 0, 47);
         this.armfin1.setRotationPoint(3.0F, 14.0F, -9.0F);
         this.armfin1.addBox(-2.0F, 0.0F, -1.0F, 3, 12, 5, 0.0F);
         this.setRotateAngle(this.armfin1, -0.59184116F, 0.0F, -1.1838568F);
         this.head1 = new ModelRenderer(this, 53, 67);
         this.head1.setRotationPoint(0.0F, 13.0F, -9.0F);
         this.head1.addBox(-3.0F, -3.0F, -6.0F, 6, 5, 8, 0.0F);
         this.plast = new ModelRenderer(this, 0, 64);
         this.plast.setRotationPoint(0.0F, 16.0F, -8.9F);
         this.plast.addBox(-6.0F, -3.5F, 1.0F, 12, 3, 13, 0.0F);
         this.setRotateAngle(this.plast, -0.18203785F, 0.0F, 0.0F);
         this.head1.addChild(this.head2);
         this.carapace_2.addChild(this.carapace_8);
         this.head2.addChild(this.head3);
         this.armfin1.addChild(this.armfin1A);
         this.carapace.addChild(this.carapace_6);
         this.armfin2.addChild(this.armfin2A);
         this.head2.addChild(this.head4);
         this.carapace_1.addChild(this.carapace_7);
         this.body.addChild(this.tail);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -0.5F, 0.0F);
         f5 *= 1.7F;
         float headProtect = 0.0F;
         if (an4 > 0) {
            float headProtectAn = 100 - an4 + Minecraft.getMinecraft().getRenderPartialTicks();
            headProtect = GetMOP.getfromto(headProtectAn, 0.0F, 20.0F) - GetMOP.getfromto(headProtectAn, 80.0F, 100.0F);
         }

         float unheadProtect = 1.0F - headProtect;
         this.head1.rotateAngleY = MathHelper.clamp(f3 * 0.007553292F * unheadProtect, -0.3F, 0.3F);
         this.head1.rotateAngleX = MathHelper.clamp(f4 * 0.007553292F * unheadProtect, -0.3F, 0.3F);
         this.head2.rotateAngleY = MathHelper.clamp(f3 * 0.007553292F * unheadProtect, -0.3F, 0.3F);
         this.head2.rotateAngleX = MathHelper.clamp(f4 * 0.007553292F * unheadProtect, -0.3F, 0.3F);
         this.head1.offsetZ = 1.0F * headProtect;
         this.head1.rotateAngleX += 0.6F * headProtect;
         this.head2.rotateAngleX += -0.6F * headProtect;
         if (an1 > 0) {
            float anim = 100 - an1 + Minecraft.getMinecraft().getRenderPartialTicks();
            float amountHead = GetMOP.getfromto(anim, 0.0F, 13.0F) - GetMOP.getfromto(anim, 14.0F, 20.0F);
            float amountBite = GetMOP.getfromto(anim, 0.0F, 10.0F) * 0.3F + GetMOP.getfromto(anim, 11.0F, 15.0F) * 0.7F - GetMOP.getfromto(anim, 16.0F, 19.0F);
            this.head1.offsetZ += 0.3125F * amountHead;
            this.head1.rotateAngleX += 0.3F * amountHead;
            this.head2.rotateAngleX += -0.3F * amountHead - 0.2F * amountBite;
            this.head3.rotateAngleX = 0.68294734F - 0.7F * amountBite;
            this.head4.rotateAngleX = -0.18203785F + 0.7F * amountBite;
         }

         this.setRotateAngle(this.armfin1, -0.59184116F, 0.0F, -1.1838568F);
         this.setRotateAngle(this.armfin1B, 0.8196066F, 0.13665928F, -1.1383038F);
         if (entity.isInWater()) {
            float animTime = AnimationTimer.tick / 30.0F;
            this.armfin1.rotateAngleZ = this.armfin1.rotateAngleZ - MathHelper.sin(animTime) / 4.0F;
            this.armfin1.rotateAngleY = this.armfin1.rotateAngleY - MathHelper.cos(animTime) / 4.0F;
            this.armfin1.rotateAngleX = this.armfin1.rotateAngleX + MathHelper.cos(animTime) / 6.0F;
            this.armfin2.rotateAngleZ = -this.armfin1.rotateAngleZ;
            this.armfin2.rotateAngleY = -this.armfin1.rotateAngleY;
            this.armfin2.rotateAngleX = this.armfin1.rotateAngleX;
            animTime += 30.0F;
            this.armfin1B.rotateAngleZ = this.armfin1B.rotateAngleZ - MathHelper.sin(animTime) / 6.0F;
            this.armfin1B.rotateAngleY = this.armfin1B.rotateAngleY - MathHelper.cos(animTime) / 6.0F;
            this.armfin1B.rotateAngleX = this.armfin1B.rotateAngleX + MathHelper.cos(animTime) / 7.0F;
            this.armfin2B.rotateAngleZ = -this.armfin1B.rotateAngleZ;
            this.armfin2B.rotateAngleY = -this.armfin1B.rotateAngleY;
            this.armfin2B.rotateAngleX = this.armfin1B.rotateAngleX;
            this.body.rotateAngleY = MathHelper.sin(AnimationTimer.tick / 40.0F) * 0.13F;
            this.tail.rotateAngleY = this.body.rotateAngleY;
         }

         this.carapace_4.render(f5);
         this.body.render(f5);
         this.carapace.render(f5);
         this.carapace_1.render(f5);
         this.carapace_2.render(f5);
         this.armfin1B.render(f5);
         this.armfin2B.render(f5);
         this.carapace_5.render(f5);
         this.carapace_3.render(f5);
         if (isAbstractMob && ((AbstractMob)entity).var1 == 1) {
            this.base_1.render(f5);
            this.base.render(f5);
         }

         this.armfin2.render(f5);
         this.armfin1.render(f5);
         this.head1.render(f5);
         this.plast.render(f5);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class BlisterfishModel extends AbstractMobModel {
      public ModelRenderer body;
      public ModelRenderer tail1;
      public ModelRenderer head;
      public ModelRenderer body2;
      public ModelRenderer fin;
      public ModelRenderer armfin1;
      public ModelRenderer armfin2;
      public ModelRenderer body2_1;
      public ModelRenderer head2;
      public ModelRenderer fin3;
      public ModelRenderer armfin1a;
      public ModelRenderer armfin2a;
      public ModelRenderer tail2;
      public ModelRenderer tail3;
      public ModelRenderer fin2;

      public BlisterfishModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.tail2 = new ModelRenderer(this, 28, 10);
         this.tail2.setRotationPoint(0.0F, 0.0F, 12.0F);
         this.tail2.addBox(-1.0F, -2.0F, -0.8F, 2, 2, 8, 0.0F);
         this.fin = new ModelRenderer(this, 28, 32);
         this.fin.setRotationPoint(0.0F, -4.0F, -1.0F);
         this.fin.addBox(-0.5F, -6.0F, 0.0F, 1, 6, 2, 0.0F);
         this.setRotateAngle(this.fin, -0.4553564F, 0.0F, 0.0F);
         this.armfin2a = new ModelRenderer(this, 11, 33);
         this.armfin2a.setRotationPoint(0.0F, 4.0F, 0.5F);
         this.armfin2a.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 2, 0.0F);
         this.head2 = new ModelRenderer(this, 30, 21);
         this.head2.setRotationPoint(0.0F, 2.0F, -3.0F);
         this.head2.addBox(-4.0F, -2.0F, -2.0F, 6, 3, 6, 0.0F);
         this.setRotateAngle(this.head2, 0.59184116F, 0.68294734F, 0.4098033F);
         this.fin2 = new ModelRenderer(this, 0, 31);
         this.fin2.setRotationPoint(0.0F, -2.0F, 2.0F);
         this.fin2.addBox(0.0F, 0.3F, -3.8F, 0, 6, 11, 0.0F);
         this.body = new ModelRenderer(this, 0, 16);
         this.body.setRotationPoint(0.0F, 18.0F, 3.0F);
         this.body.addBox(-3.5F, -4.0F, -6.8F, 7, 7, 8, 0.0F);
         this.tail1 = new ModelRenderer(this, 40, 4);
         this.tail1.setRotationPoint(0.0F, 17.5F, -1.0F);
         this.tail1.addBox(-2.0F, -2.5F, 4.2F, 4, 5, 8, 0.0F);
         this.fin3 = new ModelRenderer(this, 24, 32);
         this.fin3.setRotationPoint(0.0F, -1.0F, -1.0F);
         this.fin3.addBox(-0.5F, -2.5F, 0.0F, 1, 4, 1, 0.0F);
         this.tail3 = new ModelRenderer(this, 32, 0);
         this.tail3.setRotationPoint(0.0F, 0.0F, 7.0F);
         this.tail3.addBox(-0.5F, -1.7F, -0.8F, 1, 1, 7, 0.0F);
         this.setRotateAngle(this.tail3, 0.091106184F, 0.0F, 0.0F);
         this.armfin1 = new ModelRenderer(this, 0, 33);
         this.armfin1.setRotationPoint(3.0F, 3.0F, -4.0F);
         this.armfin1.addBox(0.0F, 0.0F, 0.0F, 1, 4, 4, 0.0F);
         this.setRotateAngle(this.armfin1, -0.27314404F, 0.0F, -0.8196066F);
         this.head = new ModelRenderer(this, 0, 0);
         this.head.setRotationPoint(0.0F, 0.0F, -7.4F);
         this.head.addBox(-4.0F, -4.0F, -2.0F, 6, 5, 6, 0.0F);
         this.setRotateAngle(this.head, 0.59184116F, 0.68294734F, 0.4098033F);
         this.armfin1a = new ModelRenderer(this, 11, 33);
         this.armfin1a.setRotationPoint(0.0F, 4.0F, 0.5F);
         this.armfin1a.addBox(0.0F, 0.0F, 0.0F, 1, 3, 2, 0.0F);
         this.body2 = new ModelRenderer(this, 31, 38);
         this.body2.setRotationPoint(0.0F, 1.0F, -4.0F);
         this.body2.addBox(-5.5F, -3.0F, -2.8F, 11, 5, 5, 0.0F);
         this.setRotateAngle(this.body2, -0.4553564F, 0.0F, 0.0F);
         this.armfin2 = new ModelRenderer(this, 0, 33);
         this.armfin2.setRotationPoint(-3.0F, 3.0F, -4.0F);
         this.armfin2.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 4, 0.0F);
         this.setRotateAngle(this.armfin2, -0.27314404F, 0.0F, 0.8196066F);
         this.body2_1 = new ModelRenderer(this, 35, 30);
         this.body2_1.setRotationPoint(0.0F, 1.0F, 1.0F);
         this.body2_1.addBox(-5.0F, -3.0F, -2.8F, 10, 4, 4, 0.0F);
         this.setRotateAngle(this.body2_1, -0.18203785F, 0.0F, 0.0F);
         this.tail1.addChild(this.tail2);
         this.body.addChild(this.fin);
         this.armfin2.addChild(this.armfin2a);
         this.body2.addChild(this.head2);
         this.tail3.addChild(this.fin2);
         this.fin.addChild(this.fin3);
         this.tail2.addChild(this.tail3);
         this.body.addChild(this.armfin1);
         this.body.addChild(this.head);
         this.armfin1.addChild(this.armfin1a);
         this.body.addChild(this.body2);
         this.body.addChild(this.armfin2);
         this.body.addChild(this.body2_1);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         this.body2.rotateAngleX = -0.4553564F;
         if (an1 > 80) {
            float ptan1 = 100.0F - an1 + pt;
            this.body2.rotateAngleX = -0.4553564F + (GetMOP.getfromto(ptan1, 0.0F, 10.0F) - GetMOP.getfromto(ptan1, 10.0F, 19.0F)) * 0.53F;
         }

         this.body.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.body.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.tail1.rotateAngleY = 0.0F;
         this.tail1.rotateAngleX = f4 * 0.003453292F;
         this.tail2.rotateAngleY = f3 * -0.002053292F;
         this.tail2.rotateAngleX = f4 * 0.002053292F;
         this.tail3.rotateAngleY = f3 * -0.002053292F;
         this.tail3.rotateAngleX = 0.091106184F + f4 * 0.002053292F;
         if (isAbstractMob) {
            float var4x = ((AbstractMob)entity).var4;
            float var3x = ((AbstractMob)entity).var3;
            float vars = var3x + (var4x - var3x) * pt;
            float tailSin = MathHelper.sin(vars / 3.0F);
            this.tail1.rotateAngleY += tailSin * 0.174F;
            this.tail2.rotateAngleY += tailSin * 0.3F;
            this.tail3.rotateAngleY += tailSin * 0.39F;
         }

         float finCos = MathHelper.cos(AnimationTimer.tick / 50.0F) * 0.35F;
         float finSin = MathHelper.sin(AnimationTimer.tick / 50.0F) * 0.15F;
         this.armfin1.rotateAngleY = finCos;
         this.armfin1.rotateAngleX = -0.27314404F - finSin;
         this.armfin2.rotateAngleY = -this.armfin1.rotateAngleY;
         this.armfin2.rotateAngleX = this.armfin1.rotateAngleX;
         this.tail1.render(f5);
         this.body.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class BossKrakenModel extends AbstractMobModel {
      public ModelRenderer shape4;
      public ModelRenderer shape5;
      public ModelRenderer shape6;
      public ModelRenderer shape8;
      public ModelRenderer teeth;
      public ModelRenderer shapefin1;
      public ModelRenderer shapefin2;
      public ModelRenderer shapefin3;
      public ModelRenderer shapefin4;
      public ModelRenderer shape3;
      public ModelRenderer shape2;
      public ModelRenderer shape1;
      public ModelRenderer shapeeye1;
      public ModelRenderer shapeeye2;
      public ModelRenderer column;
      public ModelRenderer column_1;
      public ModelRenderer column_2;
      public ModelRenderer column_3;
      public ModelRenderer column_4;
      public ModelRenderer column_5;
      public ModelRenderer weed2;
      public ModelRenderer shapefiller2;
      public ModelRenderer shapetail1;
      public ModelRenderer shapetail2;
      public ModelRenderer column_6;
      public ModelRenderer column_7;
      public ModelRenderer column_8;
      public ModelRenderer column_9;
      public ModelRenderer column_10;
      public ModelRenderer column_11;
      public ModelRenderer column_12;
      public ModelRenderer column_13;
      public ModelRenderer column_14;
      public ModelRenderer column_15;
      public ModelRenderer column_16;
      public ModelRenderer column_17;
      public ModelRenderer weed1;
      public ModelRenderer shape9;
      public ModelRenderer shape7;
      public ModelRenderer shapefiller1;
      public ModelRenderer shape10;
      public ModelRenderer shape11;
      public ModelRenderer shape12;
      public ModelRenderer shapeeyeGlow1;
      public ModelRenderer shapeeyeGlow2;

      public BossKrakenModel() {
         this.textureWidth = 128;
         this.textureHeight = 128;
         this.column = new ModelRenderer(this, 0, 0);
         this.column.setRotationPoint(0.0F, 3.0F, 0.0F);
         this.column.addBox(-1.0F, 0.0F, -8.4F, 2, 13, 2, 0.0F);
         this.setRotateAngle(this.column, -0.13665928F, (float) (-Math.PI / 12), 0.0F);
         this.shape7 = new ModelRenderer(this, 0, 37);
         this.shape7.setRotationPoint(0.0F, 12.0F, 0.0F);
         this.shape7.addBox(-7.0F, 0.0F, -7.0F, 14, 2, 14, 0.0F);
         this.shapetail1 = new ModelRenderer(this, 60, 0);
         this.shapetail1.mirror = true;
         this.shapetail1.setRotationPoint(0.0F, -28.0F, 0.0F);
         this.shapetail1.addBox(2.5F, -14.5F, 0.0F, 10, 16, 0, 0.0F);
         this.setRotateAngle(this.shapetail1, 0.0F, 0.0F, 0.5462881F);
         this.column_1 = new ModelRenderer(this, 0, 0);
         this.column_1.setRotationPoint(0.0F, 3.0F, 0.0F);
         this.column_1.addBox(-1.0F, 0.0F, -8.4F, 2, 13, 2, 0.0F);
         this.setRotateAngle(this.column_1, -0.13665928F, (float) (-Math.PI * 5.0 / 12.0), 0.0F);
         this.column_4 = new ModelRenderer(this, 0, 0);
         this.column_4.setRotationPoint(0.0F, 3.0F, 0.0F);
         this.column_4.addBox(-1.0F, 0.0F, -8.4F, 2, 13, 2, 0.0F);
         this.setRotateAngle(this.column_4, -0.13665928F, (float) (Math.PI * 7.0 / 12.0), 0.0F);
         this.shape5 = new ModelRenderer(this, 0, 19);
         this.shape5.setRotationPoint(0.0F, -18.0F, 0.0F);
         this.shape5.addBox(-8.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
         this.setRotateAngle(this.shape5, 0.0F, (float) (Math.PI / 4), 0.0F);
         this.shape6 = new ModelRenderer(this, 3, 52);
         this.shape6.setRotationPoint(0.0F, -26.0F, 0.0F);
         this.shape6.addBox(-5.0F, 0.0F, -5.0F, 10, 8, 10, 0.0F);
         this.column_10 = new ModelRenderer(this, 8, 0);
         this.column_10.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.column_10.addBox(-0.5F, 0.0F, -7.0F, 1, 10, 1, 0.0F);
         this.setRotateAngle(this.column_10, 0.0F, (float) (Math.PI * 2.0 / 3.0), 0.0F);
         this.column_9 = new ModelRenderer(this, 8, 0);
         this.column_9.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.column_9.addBox(-0.5F, 0.0F, -7.0F, 1, 10, 1, 0.0F);
         this.setRotateAngle(this.column_9, 0.0F, (float) (Math.PI / 2), 0.0F);
         this.column_3 = new ModelRenderer(this, 0, 0);
         this.column_3.setRotationPoint(0.0F, 3.0F, 0.0F);
         this.column_3.addBox(-1.0F, 0.0F, -8.4F, 2, 13, 2, 0.0F);
         this.setRotateAngle(this.column_3, -0.13665928F, (float) (Math.PI * 11.0 / 12.0), 0.0F);
         this.column_11 = new ModelRenderer(this, 8, 0);
         this.column_11.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.column_11.addBox(-0.5F, 0.0F, -7.0F, 1, 10, 1, 0.0F);
         this.setRotateAngle(this.column_11, 0.0F, (float) (Math.PI * 5.0 / 6.0), 0.0F);
         this.column_17 = new ModelRenderer(this, 8, 0);
         this.column_17.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.column_17.addBox(-0.5F, 0.0F, -7.0F, 1, 10, 1, 0.0F);
         this.setRotateAngle(this.column_17, 0.0F, (float) (-Math.PI * 5.0 / 6.0), 0.0F);
         this.column_12 = new ModelRenderer(this, 8, 0);
         this.column_12.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.column_12.addBox(-0.5F, 0.0F, -7.0F, 1, 10, 1, 0.0F);
         this.setRotateAngle(this.column_12, 0.0F, (float) Math.PI, 0.0F);
         this.shapefiller1 = new ModelRenderer(this, 14, 0);
         this.shapefiller1.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shapefiller1.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
         this.weed2 = new ModelRenderer(this, 64, 15);
         this.weed2.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.weed2.addBox(-8.0F, 0.0F, -8.0F, 16, 6, 16, 0.0F);
         this.column_14 = new ModelRenderer(this, 8, 0);
         this.column_14.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.column_14.addBox(-0.5F, 0.0F, -7.0F, 1, 10, 1, 0.0F);
         this.setRotateAngle(this.column_14, 0.0F, (float) (-Math.PI / 3), 0.0F);
         this.shapeeye2 = new ModelRenderer(this, 104, 0);
         this.shapeeye2.setRotationPoint(8.0F, 6.0F, 16.0F);
         this.shapeeye2.addBox(-4.0F, -4.0F, -2.0F, 8, 8, 4, 0.0F);
         this.setRotateAngle(this.shapeeye2, 0.0F, 0.0F, (float) (Math.PI / 4));
         this.shape11 = new ModelRenderer(this, 0, 112);
         this.shape11.setRotationPoint(0.0F, -5.0F, 0.0F);
         this.shape11.addBox(-3.0F, 0.0F, -3.0F, 6, 5, 6, 0.0F);
         this.setRotateAngle(this.shape11, 0.0F, -0.22759093F, 0.0F);
         this.shape8 = new ModelRenderer(this, 0, 19);
         this.shape8.setRotationPoint(0.0F, -40.0F, 0.0F);
         this.shape8.addBox(-8.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
         this.setRotateAngle(this.shape8, 0.0F, (float) (Math.PI / 4), 0.0F);
         this.column_2 = new ModelRenderer(this, 0, 0);
         this.column_2.setRotationPoint(0.0F, 3.0F, 0.0F);
         this.column_2.addBox(-1.0F, 0.0F, -8.4F, 2, 13, 2, 0.0F);
         this.setRotateAngle(this.column_2, -0.13665928F, (float) (-Math.PI * 3.0 / 4.0), 0.0F);
         this.column_16 = new ModelRenderer(this, 8, 0);
         this.column_16.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.column_16.addBox(-0.5F, 0.0F, -7.0F, 1, 10, 1, 0.0F);
         this.setRotateAngle(this.column_16, 0.0F, (float) (-Math.PI * 2.0 / 3.0), 0.0F);
         this.shape4 = new ModelRenderer(this, 0, 100);
         this.shape4.setRotationPoint(0.0F, -3.0F, 0.0F);
         this.shape4.addBox(-12.0F, 0.0F, -12.0F, 24, 4, 24, 0.0F);
         this.column_15 = new ModelRenderer(this, 8, 0);
         this.column_15.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.column_15.addBox(-0.5F, 0.0F, -7.0F, 1, 10, 1, 0.0F);
         this.setRotateAngle(this.column_15, 0.0F, (float) (-Math.PI / 2), 0.0F);
         this.shapefiller2 = new ModelRenderer(this, 80, 100);
         this.shapefiller2.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shapefiller2.addBox(-4.0F, 0.0F, -4.0F, 8, 13, 8, 0.0F);
         this.shapefin1 = new ModelRenderer(this, 0, 20);
         this.shapefin1.mirror = true;
         this.shapefin1.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shapefin1.addBox(12.0F, -6.0F, 0.0F, 6, 9, 0, 0.0F);
         this.column_6 = new ModelRenderer(this, 8, 0);
         this.column_6.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.column_6.addBox(-0.5F, 0.0F, -7.0F, 1, 10, 1, 0.0F);
         this.shapefin3 = new ModelRenderer(this, 0, 20);
         this.shapefin3.mirror = true;
         this.shapefin3.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shapefin3.addBox(12.0F, -6.0F, 0.0F, 6, 9, 0, 0.0F);
         this.setRotateAngle(this.shapefin3, 0.0F, (float) Math.PI, 0.0F);
         this.column_7 = new ModelRenderer(this, 8, 0);
         this.column_7.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.column_7.addBox(-0.5F, 0.0F, -7.0F, 1, 10, 1, 0.0F);
         this.setRotateAngle(this.column_7, 0.0F, (float) (Math.PI / 6), 0.0F);
         this.shape3 = new ModelRenderer(this, 40, 37);
         this.shape3.setRotationPoint(-9.0F, 4.0F, -9.0F);
         this.shape3.addBox(-2.0F, 0.0F, -2.0F, 22, 3, 22, 0.0F);
         this.teeth = new ModelRenderer(this, 41, -5);
         this.teeth.setRotationPoint(0.0F, 21.0F, 0.0F);
         this.teeth.addBox(0.0F, 3.0F, -7.5F, 0, 7, 5, 0.0F);
         this.setRotateAngle(this.teeth, -0.318697F, 0.0F, 0.0F);
         this.shape2 = new ModelRenderer(this, 0, 71);
         this.shape2.setRotationPoint(-8.0F, 7.0F, -8.0F);
         this.shape2.addBox(-2.0F, 0.0F, -2.0F, 20, 8, 20, 0.0F);
         this.column_8 = new ModelRenderer(this, 8, 0);
         this.column_8.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.column_8.addBox(-0.5F, 0.0F, -7.0F, 1, 10, 1, 0.0F);
         this.setRotateAngle(this.column_8, 0.0F, (float) (Math.PI / 3), 0.0F);
         this.weed1 = new ModelRenderer(this, 64, 15);
         this.weed1.setRotationPoint(0.0F, -6.0F, 0.0F);
         this.weed1.addBox(-8.0F, 0.0F, -8.0F, 16, 6, 16, 0.0F);
         this.column_5 = new ModelRenderer(this, 0, 0);
         this.column_5.setRotationPoint(0.0F, 3.0F, 0.0F);
         this.column_5.addBox(-1.0F, 0.0F, -8.4F, 2, 13, 2, 0.0F);
         this.setRotateAngle(this.column_5, -0.13665928F, (float) (Math.PI / 4), 0.0F);
         this.shapefin2 = new ModelRenderer(this, 0, 20);
         this.shapefin2.mirror = true;
         this.shapefin2.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shapefin2.addBox(12.0F, -6.0F, 0.0F, 6, 9, 0, 0.0F);
         this.setRotateAngle(this.shapefin2, 0.0F, (float) (Math.PI / 2), 0.0F);
         this.shape9 = new ModelRenderer(this, 3, 52);
         this.shape9.setRotationPoint(0.0F, -5.0F, 0.0F);
         this.shape9.addBox(-5.0F, 0.0F, -5.0F, 10, 5, 10, 0.0F);
         this.setRotateAngle(this.shape9, 0.0F, 0.5009095F, 0.0F);
         this.column_13 = new ModelRenderer(this, 8, 0);
         this.column_13.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.column_13.addBox(-0.5F, 0.0F, -7.0F, 1, 10, 1, 0.0F);
         this.setRotateAngle(this.column_13, 0.0F, (float) (-Math.PI / 6), 0.0F);
         this.shape10 = new ModelRenderer(this, 47, 18);
         this.shape10.setRotationPoint(0.0F, -5.0F, 0.0F);
         this.shape10.addBox(-4.0F, 0.0F, -4.0F, 8, 5, 8, 0.0F);
         this.setRotateAngle(this.shape10, 0.0F, -0.22759093F, 0.0F);
         this.shapeeye1 = new ModelRenderer(this, 80, 0);
         this.shapeeye1.setRotationPoint(8.0F, 6.0F, 0.0F);
         this.shapeeye1.addBox(-4.0F, -4.0F, -2.0F, 8, 8, 4, 0.0F);
         this.setRotateAngle(this.shapeeye1, 0.0F, 0.0F, (float) (Math.PI / 4));
         this.shapetail2 = new ModelRenderer(this, 60, 0);
         this.shapetail2.setRotationPoint(0.0F, -28.0F, 0.0F);
         this.shapetail2.addBox(-12.5F, -14.5F, 0.0F, 10, 16, 0, 0.0F);
         this.setRotateAngle(this.shapetail2, 0.0F, 0.0F, -0.5462881F);
         this.shapefin4 = new ModelRenderer(this, 0, 20);
         this.shapefin4.mirror = true;
         this.shapefin4.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shapefin4.addBox(12.0F, -6.0F, 0.0F, 6, 9, 0, 0.0F);
         this.setRotateAngle(this.shapefin4, 0.0F, (float) (-Math.PI / 2), 0.0F);
         this.shape1 = new ModelRenderer(this, 60, 63);
         this.shape1.setRotationPoint(-8.0F, 15.0F, -8.0F);
         this.shape1.addBox(0.0F, 0.0F, 0.0F, 16, 12, 16, 0.0F);
         this.shape12 = new ModelRenderer(this, 44, 40);
         this.shape12.setRotationPoint(0.0F, -5.0F, 0.0F);
         this.shape12.addBox(-2.0F, 0.0F, -2.0F, 4, 5, 4, 0.0F);
         this.setRotateAngle(this.shape12, 0.0F, -0.22759093F, 0.0F);
         this.shapeeyeGlow1 = new ModelRendererGlow(this, 112, 12, 190, true);
         this.shapeeyeGlow1.setRotationPoint(0.0F, 0.0F, -0.05F);
         this.shapeeyeGlow1.addBox(-4.0F, -4.0F, -2.0F, 8, 8, 0, 0.0F);
         this.shapeeyeGlow2 = new ModelRendererGlow(this, 112, 12, 190, true);
         this.shapeeyeGlow2.setRotationPoint(0.0F, 0.0F, 0.05F);
         this.shapeeyeGlow2.addBox(-4.0F, -4.0F, 2.0F, 8, 8, 0, 0.0F);
         this.shape5.addChild(this.column);
         this.shape8.addChild(this.shape7);
         this.shape6.addChild(this.shapetail1);
         this.shape5.addChild(this.column_1);
         this.shape5.addChild(this.column_4);
         this.shape8.addChild(this.column_10);
         this.shape8.addChild(this.column_9);
         this.shape5.addChild(this.column_3);
         this.shape8.addChild(this.column_11);
         this.shape8.addChild(this.column_17);
         this.shape8.addChild(this.column_12);
         this.shape8.addChild(this.shapefiller1);
         this.shape5.addChild(this.weed2);
         this.shape8.addChild(this.column_14);
         this.shape1.addChild(this.shapeeye2);
         this.shape10.addChild(this.shape11);
         this.shape5.addChild(this.column_2);
         this.shape8.addChild(this.column_16);
         this.shape8.addChild(this.column_15);
         this.shape5.addChild(this.shapefiller2);
         this.shape4.addChild(this.shapefin1);
         this.shape8.addChild(this.column_6);
         this.shape4.addChild(this.shapefin3);
         this.shape8.addChild(this.column_7);
         this.shape4.addChild(this.shape3);
         this.shape4.addChild(this.shape2);
         this.shape8.addChild(this.column_8);
         this.shape8.addChild(this.weed1);
         this.shape5.addChild(this.column_5);
         this.shape4.addChild(this.shapefin2);
         this.shape8.addChild(this.shape9);
         this.shape8.addChild(this.column_13);
         this.shape9.addChild(this.shape10);
         this.shape1.addChild(this.shapeeye1);
         this.shape6.addChild(this.shapetail2);
         this.shape4.addChild(this.shapefin4);
         this.shape4.addChild(this.shape1);
         this.shape11.addChild(this.shape12);
         this.shapeeye1.addChild(this.shapeeyeGlow1);
         this.shapeeye2.addChild(this.shapeeyeGlow2);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         GlStateManager.pushMatrix();
         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
         f5 *= 8.0F;
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         this.shapeeye1.rotateAngleX = MathHelper.clamp(MathHelper.sin(-(AnimationTimer.tick + 60) / 66.0F + 3.4378F) * 3.5F, -0.315F, 0.315F);
         this.shapeeye1.rotateAngleY = MathHelper.clamp(MathHelper.sin(-(AnimationTimer.tick + 60) / 66.0F + 2.34555F) * 3.5F, -0.315F, 0.315F);
         this.shapeeye2.rotateAngleX = MathHelper.clamp(MathHelper.sin(-(AnimationTimer.tick + 60) / 66.0F + 4.32511F) * 3.5F, -0.315F, 0.315F);
         this.shapeeye2.rotateAngleY = MathHelper.clamp(MathHelper.sin(-(AnimationTimer.tick + 60) / 66.0F + 5.23117F) * 3.5F, -0.315F, 0.315F);
         float vars = 0.0F;
         float var5x = 0.0F;
         if (isAbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            var5x = mob.var5;
            float var4x = mob.var4;
            float var3x = mob.var3;
            vars = (var3x + (var4x - var3x) * pt) * 0.1F;
            this.shape4.rotateAngleY = vars;
            this.shape5.rotateAngleY = (float) (Math.PI / 4) + vars;
            this.shape6.rotateAngleY = vars;
            this.shape8.rotateAngleY = (float) (Math.PI / 4) + vars;
            this.shapetail1.rotateAngleZ = 0.5462881F + 0.48F * var5x;
            this.shapetail2.rotateAngleZ = -0.5462881F - 0.48F * var5x;
            this.teeth.rotateAngleX = mob.var2;
         }

         this.shape5.render(f5);
         this.shape6.render(f5);
         this.shape8.render(f5);
         this.shape4.render(f5);

         for (int i = 0; i < 18; i++) {
            this.teeth.rotateAngleY = i * 20.0F * (float) (Math.PI / 180.0) + vars;
            this.teeth.render(f5);
         }

         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class BreedModel extends AbstractMobModel {
      public ModelRenderer body;
      public ModelRenderer body2;
      public ModelRenderer body2_1;
      public ModelRenderer body2_2;
      public ModelRenderer shape;
      public ModelRenderer tent;
      public ModelRenderer podia1;
      public ModelRenderer podia4;
      public ModelRenderer podia2;
      public ModelRenderer podia3;

      public BreedModel() {
         this.textureWidth = 32;
         this.textureHeight = 32;
         this.tent = new ModelRenderer(this, -6, 21);
         this.tent.setRotationPoint(0.0F, -0.6F, 0.0F);
         this.tent.addBox(-4.5F, 0.0F, -4.5F, 9, 0, 9, 0.0F);
         this.setRotateAngle(this.tent, 0.0F, 0.7740535F, 0.0F);
         this.body = new ModelRenderer(this, 0, 10);
         this.body.setRotationPoint(0.0F, 19.0F, 0.0F);
         this.body.addBox(-1.5F, -4.0F, -1.5F, 3, 6, 3, 0.0F);
         this.setRotateAngle(this.body, (float) (Math.PI / 2), 0.0F, 0.0F);
         this.podia2 = new ModelRenderer(this, 5, 22);
         this.podia2.setRotationPoint(0.0F, 0.0F, 7.5F);
         this.podia2.addBox(0.0F, -0.5F, 0.0F, 0, 1, 8, 0.0F);
         this.body2_1 = new ModelRenderer(this, 18, 0);
         this.body2_1.setRotationPoint(0.0F, 1.7F, 0.0F);
         this.body2_1.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F);
         this.shape = new ModelRenderer(this, 9, 16);
         this.shape.setRotationPoint(0.0F, -4.0F, 0.0F);
         this.shape.addBox(-1.5F, -1.0F, -1.5F, 3, 2, 3, 0.0F);
         this.podia4 = new ModelRenderer(this, 12, 3);
         this.podia4.setRotationPoint(0.0F, 6.5F, 0.0F);
         this.podia4.addBox(0.0F, -2.0F, -0.5F, 0, 4, 8, 0.0F);
         this.setRotateAngle(this.podia4, (float) (-Math.PI / 2), (float) (Math.PI / 2), 0.0F);
         this.podia3 = new ModelRenderer(this, 5, 23);
         this.podia3.setRotationPoint(0.0F, 0.0F, 8.0F);
         this.podia3.addBox(0.0F, -0.5F, 0.0F, 0, 1, 8, 0.0F);
         this.podia1 = new ModelRenderer(this, 12, 3);
         this.podia1.setRotationPoint(0.0F, 7.5F, 0.0F);
         this.podia1.addBox(0.0F, -2.0F, -0.5F, 0, 4, 8, 0.0F);
         this.setRotateAngle(this.podia1, (float) (-Math.PI / 2), 0.0F, 0.0F);
         this.body2 = new ModelRenderer(this, 21, 15);
         this.body2.setRotationPoint(0.0F, -5.0F, 0.0F);
         this.body2.addBox(-1.0F, -3.0F, -1.0F, 2, 4, 2, 0.0F);
         this.body2_2 = new ModelRenderer(this, 0, 0);
         this.body2_2.setRotationPoint(0.0F, 5.0F, 0.0F);
         this.body2_2.addBox(-2.0F, -3.0F, -2.0F, 4, 3, 4, 0.0F);
         this.shape.addChild(this.tent);
         this.podia1.addChild(this.podia2);
         this.body.addChild(this.body2_1);
         this.body2.addChild(this.shape);
         this.body2_1.addChild(this.podia4);
         this.podia2.addChild(this.podia3);
         this.body2_1.addChild(this.podia1);
         this.body.addChild(this.body2);
         this.body.addChild(this.body2_2);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         boolean blend = Minecraft.getMinecraft().getRenderViewEntity() == null || entity.getDistanceSq(Minecraft.getMinecraft().getRenderViewEntity()) < 1600.0;
         if (blend) {
            GL11.glEnable(3042);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 0.05F, 0.0F);
         this.body2.rotateAngleZ = -f3 * 0.003453292F;
         this.body2.rotateAngleX = f4 * 0.003453292F;
         this.shape.rotateAngleZ = this.body2.rotateAngleZ;
         this.shape.rotateAngleX = this.body2.rotateAngleX;
         this.tent.rotateAngleZ = this.body2.rotateAngleZ;
         if (isAbstractMob) {
            float var4x = ((AbstractMob)entity).var4;
            float var3x = ((AbstractMob)entity).var3;
            float vars = (var3x + (var4x - var3x) * pt) * -3.2F;
            this.body2_1.rotateAngleZ = MathHelper.sin(vars) * 0.35F;
            this.podia1.rotateAngleZ = MathHelper.sin(vars + 0.5F) * 0.35F;
            this.podia4.rotateAngleZ = this.podia1.rotateAngleZ;
            this.podia2.rotateAngleY = -MathHelper.sin(vars + 1.0F) * 0.35F;
            this.podia3.rotateAngleY = -MathHelper.sin(vars + 1.5F) * 0.35F;
         }

         this.body.render(f5);
         GlStateManager.popMatrix();
         if (blend) {
            GL11.glDisable(3042);
         }
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class DartfishModel extends AbstractMobModel {
      public ModelRenderer tail1;
      public ModelRenderer body;
      public ModelRendererUncompiled bag1;
      public ModelRendererUncompiled bag2;
      public ModelRenderer tail2;
      public ModelRenderer tail3;
      public ModelRenderer fin2;
      public ModelRenderer head;
      public ModelRenderer body2;
      public ModelRenderer fin;
      public ModelRenderer armfin1;
      public ModelRenderer armfin2;
      public ModelRenderer scale;
      public ModelRenderer spike;
      public ModelRenderer head2;
      public ModelRenderer fin3;
      public ModelRenderer fin4;
      public ModelRenderer armfin1a;
      public ModelRenderer armfin2a;

      public DartfishModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.fin3 = new ModelRenderer(this, 24, 37);
         this.fin3.setRotationPoint(0.0F, -1.0F, -1.0F);
         this.fin3.addBox(-0.5F, -3.5F, 0.0F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.fin3, 0.22759093F, 0.0F, 0.0F);
         this.armfin1 = new ModelRenderer(this, 0, 32);
         this.armfin1.setRotationPoint(2.5F, 1.0F, -4.0F);
         this.armfin1.addBox(0.0F, 0.0F, 0.0F, 1, 4, 3, 0.0F);
         this.setRotateAngle(this.armfin1, -0.27314404F, 0.0F, -0.8196066F);
         this.tail3 = new ModelRenderer(this, 32, 0);
         this.tail3.setRotationPoint(0.0F, 0.0F, 5.0F);
         this.tail3.addBox(-0.5F, -1.7F, -0.8F, 1, 2, 5, 0.0F);
         this.setRotateAngle(this.tail3, 0.091106184F, 0.0F, 0.0F);
         this.body2 = new ModelRenderer(this, 33, 40);
         this.body2.setRotationPoint(0.0F, 1.0F, -4.0F);
         this.body2.addBox(-5.5F, -1.5F, -2.0F, 11, 2, 2, 0.0F);
         this.setRotateAngle(this.body2, -0.31869712F, 0.0F, 0.0F);
         this.scale = new ModelRenderer(this, 0, 9);
         this.scale.setRotationPoint(0.0F, -0.4F, -4.3F);
         this.scale.addBox(-4.0F, -4.0F, -1.0F, 5, 2, 5, 0.0F);
         this.setRotateAngle(this.scale, 0.31869712F, 0.7740535F, 0.22759093F);
         this.body = new ModelRenderer(this, 0, 16);
         this.body.setRotationPoint(0.0F, 18.0F, 3.0F);
         this.body.addBox(-3.0F, -4.0F, -6.8F, 6, 6, 8, 0.0F);
         this.armfin2a = new ModelRenderer(this, 11, 33);
         this.armfin2a.setRotationPoint(0.0F, 4.0F, 0.5F);
         this.armfin2a.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 2, 0.0F);
         this.tail2 = new ModelRenderer(this, 28, 10);
         this.tail2.setRotationPoint(0.0F, 0.0F, 10.0F);
         this.tail2.addBox(-1.0F, -2.0F, -0.8F, 2, 3, 6, 0.0F);
         this.head2 = new ModelRenderer(this, 30, 21);
         this.head2.setRotationPoint(0.0F, 2.0F, -3.0F);
         this.head2.addBox(-3.0F, -2.0F, -2.0F, 5, 2, 5, 0.0F);
         this.setRotateAngle(this.head2, 0.59184116F, 0.68294734F, 0.4098033F);
         this.armfin1a = new ModelRenderer(this, 11, 33);
         this.armfin1a.setRotationPoint(0.0F, 4.0F, 0.5F);
         this.armfin1a.addBox(0.0F, 0.0F, 0.0F, 1, 3, 2, 0.0F);
         this.bag2 = new ModelRendererUncompiled(this, 33, 30);
         this.bag2.setRotationPoint(-2.5F, 0.0F, -7.3F);
         this.bag2.addBox(-1.5F, -0.5F, -0.5F, 2, 2, 2, 0.0F);
         this.spike = new ModelRenderer(this, 19, 32);
         this.spike.setRotationPoint(0.0F, 1.5F, -6.8F);
         this.spike.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
         this.setRotateAngle(this.spike, 1.6390387F, 0.0F, 0.0F);
         this.fin4 = new ModelRenderer(this, 24, 32);
         this.fin4.setRotationPoint(0.0F, 0.0F, -2.8F);
         this.fin4.addBox(-0.5F, -2.4F, 0.0F, 1, 3, 1, 0.0F);
         this.setRotateAngle(this.fin4, 0.4098033F, 0.0F, 0.0F);
         this.tail1 = new ModelRenderer(this, 40, 4);
         this.tail1.setRotationPoint(0.0F, 17.0F, -1.0F);
         this.tail1.addBox(-1.5F, -2.5F, 4.2F, 3, 4, 6, 0.0F);
         this.fin2 = new ModelRenderer(this, 0, 31);
         this.fin2.setRotationPoint(0.0F, -2.0F, 4.0F);
         this.fin2.addBox(0.0F, 0.3F, -3.8F, 0, 5, 8, 0.0F);
         this.bag1 = new ModelRendererUncompiled(this, 33, 30);
         this.bag1.setRotationPoint(2.5F, 0.0F, -7.3F);
         this.bag1.addBox(-0.5F, -0.5F, -0.5F, 2, 2, 2, 0.0F);
         this.armfin2 = new ModelRenderer(this, 0, 32);
         this.armfin2.setRotationPoint(-2.5F, 1.0F, -4.0F);
         this.armfin2.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 3, 0.0F);
         this.setRotateAngle(this.armfin2, -0.27314404F, 0.0F, 0.8196066F);
         this.fin = new ModelRenderer(this, 28, 32);
         this.fin.setRotationPoint(0.0F, -4.0F, -1.0F);
         this.fin.addBox(-0.5F, -6.0F, 1.0F, 1, 6, 1, 0.0F);
         this.setRotateAngle(this.fin, -0.4553564F, 0.0F, 0.0F);
         this.head = new ModelRenderer(this, 0, 0);
         this.head.setRotationPoint(0.0F, 0.0F, -7.4F);
         this.head.addBox(-4.0F, -2.5F, -2.0F, 6, 3, 6, 0.0F);
         this.setRotateAngle(this.head, 0.59184116F, 0.68294734F, 0.4098033F);
         this.fin.addChild(this.fin3);
         this.body.addChild(this.armfin1);
         this.tail2.addChild(this.tail3);
         this.body.addChild(this.body2);
         this.body.addChild(this.scale);
         this.armfin2.addChild(this.armfin2a);
         this.tail1.addChild(this.tail2);
         this.body2.addChild(this.head2);
         this.armfin1.addChild(this.armfin1a);
         this.body.addChild(this.spike);
         this.fin.addChild(this.fin4);
         this.tail3.addChild(this.fin2);
         this.body.addChild(this.armfin2);
         this.body.addChild(this.fin);
         this.body.addChild(this.head);
         this.body.addChild(this.bag1);
         this.body.addChild(this.bag2);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         this.body2.rotateAngleX = -0.4553564F;
         float scaleBags = 1.0F;
         float spOff = 4.0F;
         if (an1 > 75) {
            float ptan1 = 100.0F - an1 + pt;
            this.body2.rotateAngleX = -0.4553564F + (GetMOP.getfromto(ptan1, 0.0F, 13.0F) - GetMOP.getfromto(ptan1, 16.0F, 24.0F)) * 0.53F;
            scaleBags += (GetMOP.getfromto(ptan1, 0.0F, 15.0F) - GetMOP.getfromto(ptan1, 15.0F, 18.0F)) * 0.6F;
            spOff -= (GetMOP.getfromto(ptan1, 0.0F, 12.0F) - GetMOP.getfromto(ptan1, 16.0F, 23.0F)) * 4.0F;
         }

         this.spike.offsetZ = spOff * 0.1F;
         this.spike.offsetY = -spOff * 0.02F;
         this.body.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.body.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.tail1.rotateAngleY = 0.0F;
         this.tail1.rotateAngleX = f4 * 0.003453292F;
         this.tail2.rotateAngleY = f3 * -0.002053292F;
         this.tail2.rotateAngleX = f4 * 0.002053292F;
         this.tail3.rotateAngleY = f3 * -0.002053292F;
         this.tail3.rotateAngleX = 0.091106184F + f4 * 0.002053292F;
         if (isAbstractMob) {
            float var4x = ((AbstractMob)entity).var4;
            float var3x = ((AbstractMob)entity).var3;
            float vars = var3x + (var4x - var3x) * pt;
            float tailSin = MathHelper.sin(vars / 3.0F);
            this.tail1.rotateAngleY += tailSin * 0.174F;
            this.tail2.rotateAngleY += tailSin * 0.3F;
            this.tail3.rotateAngleY += tailSin * 0.39F;
         }

         float finCos = MathHelper.cos(AnimationTimer.tick / 50.0F) * 0.35F;
         float finSin = MathHelper.sin(AnimationTimer.tick / 50.0F) * 0.15F;
         this.armfin1.rotateAngleY = finCos;
         this.armfin1.rotateAngleX = -0.27314404F - finSin;
         this.armfin2.rotateAngleY = -this.armfin1.rotateAngleY;
         this.armfin2.rotateAngleX = this.armfin1.rotateAngleX;
         this.bag1.scaleFact = scaleBags;
         this.bag2.scaleFact = scaleBags;
         float scl = 1.45F;
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 1.0F, 0.0F);
         GlStateManager.scale(scl, scl, scl);
         GlStateManager.translate(0.0F, -1.0F, 0.0F);
         this.body.render(f5);
         this.tail1.render(f5);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class HydromonaModel extends AbstractMobModel {
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer pod;
      public ModelRenderer pod1;
      public ModelRenderer pod2;
      public ModelRenderer pod3;
      public ModelRenderer pod4;
      public ModelRenderer shape5;
      public ModelRenderer tent_a;
      public ModelRenderer pipe;
      public ModelRenderer tent_b;
      public ModelRenderer tent_c;

      public HydromonaModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.shape1 = new ModelRenderer(this, 23, 12);
         this.shape1.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shape1.addBox(-5.0F, 0.0F, -5.0F, 10, 8, 10, 0.0F);
         this.pod4 = new ModelRenderer(this, 24, 0);
         this.pod4.setRotationPoint(0.0F, 3.0F, 0.0F);
         this.pod4.addBox(-1.0F, 15.0F, -1.0F, 2, 3, 2, 0.0F);
         this.setRotateAngle(this.pod4, 0.0F, (float) (Math.PI / 4), 0.0F);
         this.shape4 = new ModelRenderer(this, 20, 30);
         this.shape4.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shape4.addBox(-4.0F, 1.0F, -6.0F, 8, 6, 12, 0.0F);
         this.pod3 = new ModelRenderer(this, 49, 31);
         this.pod3.setRotationPoint(0.0F, 3.0F, 0.0F);
         this.pod3.addBox(-0.5F, 9.0F, -0.5F, 1, 10, 1, 0.0F);
         this.setRotateAngle(this.pod3, 0.0F, (float) (Math.PI / 4), 0.0F);
         this.tent_c = new ModelRenderer(this, 0, 16);
         this.tent_c.setRotationPoint(0.0F, 8.75F, 5.0F);
         this.tent_c.addBox(-0.05F, -0.25F, -7.0F, 0, 7, 8, 0.0F);
         this.pod1 = new ModelRenderer(this, 16, 2);
         this.pod1.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.pod1.addBox(0.0F, 8.0F, -4.0F, 0, 12, 8, 0.0F);
         this.setRotateAngle(this.pod1, 0.0F, (float) (-Math.PI / 4), 0.0F);
         this.shape5 = new ModelRenderer(this, 20, 30);
         this.shape5.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shape5.addBox(-4.0F, 1.0F, -6.0F, 8, 6, 12, 0.0F);
         this.setRotateAngle(this.shape5, 0.0F, (float) (Math.PI / 2), 0.0F);
         this.tent_a = new ModelRenderer(this, 0, -2);
         this.tent_a.setRotationPoint(0.0F, 11.0F, 0.0F);
         this.tent_a.addBox(0.0F, -11.0F, -13.0F, 0, 9, 8, 0.0F);
         this.setRotateAngle(this.tent_a, 0.0F, (float) (-Math.PI / 4), 0.0F);
         this.tent_b = new ModelRenderer(this, 0, 7);
         this.tent_b.setRotationPoint(0.0F, -2.25F, -11.0F);
         this.tent_b.addBox(0.05F, -0.25F, -2.0F, 0, 9, 8, 0.0F);
         this.pod = new ModelRenderer(this, 28, 1);
         this.pod.setRotationPoint(0.0F, 3.0F, 0.0F);
         this.pod.addBox(-2.0F, 6.0F, -2.0F, 4, 3, 4, 0.0F);
         this.shape2 = new ModelRenderer(this, 0, 31);
         this.shape2.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shape2.addBox(-4.0F, -3.0F, -4.0F, 8, 3, 8, 0.0F);
         this.pod2 = new ModelRenderer(this, 16, 2);
         this.pod2.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.pod2.addBox(0.0F, 8.0F, -4.0F, 0, 12, 8, 0.0F);
         this.setRotateAngle(this.pod2, 0.0F, (float) (Math.PI / 4), 0.0F);
         this.pipe = new ModelRenderer(this, 53, 7);
         this.pipe.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.pipe.addBox(0.0F, -3.0F, -9.0F, 0, 10, 5, 0.0F);
         this.shape3 = new ModelRenderer(this, 38, 4);
         this.shape3.setRotationPoint(0.0F, 2.0F, 0.0F);
         this.shape3.addBox(-3.0F, -5.0F, -3.0F, 6, 2, 6, 0.0F);
         this.tent_b.addChild(this.tent_c);
         this.tent_a.addChild(this.tent_b);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         boolean blend = Minecraft.getMinecraft().getRenderViewEntity() == null || entity.getDistanceSq(Minecraft.getMinecraft().getRenderViewEntity()) < 1600.0;
         if (blend) {
            GL11.glEnable(3042);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         float vars = 0.0F;
         float var5x = 0.0F;
         if (isAbstractMob) {
            var5x = ((AbstractMob)entity).var5;
            float var4x = ((AbstractMob)entity).var4;
            float var3x = ((AbstractMob)entity).var3;
            vars = (var3x + (var4x - var3x) * pt) * 0.3F;
            this.shape1.rotateAngleY = vars;
            this.shape2.rotateAngleY = vars;
            this.shape3.rotateAngleY = vars;
            this.shape4.rotateAngleY = vars;
            this.shape5.rotateAngleY = vars + (float) (Math.PI / 2);
            this.pod.rotateAngleY = vars;
            this.pod1.rotateAngleY = vars - (float) (Math.PI / 4);
            this.pod2.rotateAngleY = vars + (float) (Math.PI / 4);
            this.pod3.rotateAngleY = vars + (float) (Math.PI / 4);
            this.pod4.rotateAngleY = vars + (float) (Math.PI / 4);
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 0.5F, 0.0F);
         this.shape1.render(f5);
         this.pod4.render(f5);
         this.shape4.render(f5);
         this.pod3.render(f5);
         this.shape5.render(f5);
         this.pod.render(f5);
         this.shape2.render(f5);
         this.shape3.render(f5);
         if (an2 > 80) {
            float ptan2 = 100.0F - an2 + pt;
            light((int)(180.0F * (GetMOP.getfromto(ptan2, 0.0F, 4.0F) - GetMOP.getfromto(ptan2, 4.0F, 19.0F))), true);
         }

         this.pod1.render(f5);
         this.pod2.render(f5);

         for (int i = 0; i < 4; i++) {
            this.tent_a.rotateAngleY = (float) (-Math.PI / 4) + (float)Math.toRadians(i * 90) + vars;
            this.tent_b.rotateAngleX = 0.2F - 0.6F * var5x;
            this.tent_c.rotateAngleX = 0.15F - 0.63F * var5x;
            this.tent_a.render(f5);
            this.pipe.rotateAngleY = (float)Math.toRadians(i * 90) + vars;
            this.pipe.render(f5);
         }

         if (blend) {
            GL11.glDisable(3042);
         }

         GlStateManager.popMatrix();
         if (an2 > 80) {
            returnlight();
         }
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class KrakenTentacleModel extends ModelBase {
      public ModelRenderer tentBite1;
      public ModelRenderer tentCrash1;
      public ModelRenderer tentShock1;
      public ModelRenderer tentMain1;
      public ModelRenderer tentGrab1;
      public ModelRenderer tentBite2;
      public ModelRenderer tentBite3;
      public ModelRenderer tentBite4;
      public ModelRenderer tentBite5;
      public ModelRenderer tentCrash2;
      public ModelRenderer tentCrash3;
      public ModelRenderer tentCrash4;
      public ModelRenderer tentCrash5;
      public ModelRenderer tentCrash6;
      public ModelRenderer tentShock2;
      public ModelRenderer tentShock3;
      public ModelRenderer tentShock4;
      public ModelRenderer tentShockGlow1;
      public ModelRenderer tentShockGlow2;
      public ModelRenderer tentShockGlow3;
      public ModelRenderer tentShock5;
      public ModelRenderer tentMain2;
      public ModelRenderer tentMain3;
      public ModelRenderer tentMain4;
      public ModelRenderer tentMain5;
      public ModelRenderer tentMain6;
      public ModelRenderer tentMain7;
      public ModelRenderer tentMain8;
      public ModelRenderer tentGrab2;
      public ModelRenderer tentGrab3;
      public ModelRenderer tentGrab4;
      public ModelRenderer tentGrab5;
      public int mode;

      public KrakenTentacleModel(int mode) {
         this.mode = mode;
         this.textureWidth = 64;
         this.textureHeight = 64;
         this.tentBite1 = new ModelRenderer(this, 0, 17);
         this.tentBite1.setRotationPoint(0.0F, 18.0F, 0.0F);
         this.tentBite1.addBox(-2.5F, -2.5F, -7.0F, 5, 5, 10, 0.0F);
         this.tentCrash2 = new ModelRenderer(this, 32, 16);
         this.tentCrash2.setRotationPoint(0.0F, 0.0F, -2.0F);
         this.tentCrash2.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
         this.tentShock3 = new ModelRendererGlow(this, 44, -10, 160, true);
         this.tentShock3.setRotationPoint(0.0F, 0.0F, -6.0F);
         this.tentShock3.addBox(0.0F, -7.0F, -8.0F, 0, 14, 10, 0.0F);
         this.setRotateAngle(this.tentShock3, 0.0F, 0.0F, (float) (Math.PI / 3));
         this.tentShock2 = new ModelRendererGlow(this, 44, -10, 160, true);
         this.tentShock2.setRotationPoint(0.0F, 0.0F, -6.0F);
         this.tentShock2.addBox(0.0F, -7.0F, -8.0F, 0, 14, 10, 0.0F);
         this.tentShock1 = new ModelRenderer(this, 0, 17);
         this.tentShock1.setRotationPoint(0.0F, 18.0F, 0.0F);
         this.tentShock1.addBox(-2.5F, -2.5F, -7.0F, 5, 5, 10, 0.0F);
         this.tentBite5 = new ModelRenderer(this, 0, -2);
         this.tentBite5.setRotationPoint(0.0F, 2.5F, -5.0F);
         this.tentBite5.addBox(0.0F, -4.0F, 0.0F, 0, 4, 11, 0.0F);
         this.setRotateAngle(this.tentBite5, 0.0F, 0.0F, (float) Math.PI);
         this.tentShock5 = new ModelRendererGlow(this, 18, 55, 160, true);
         this.tentShock5.setRotationPoint(0.0F, 0.0F, 3.0F);
         this.tentShock5.addBox(-9.0F, 0.0F, -7.0F, 18, 0, 8, 0.0F);
         this.tentGrab2 = new ModelRenderer(this, 43, 37);
         this.tentGrab2.setRotationPoint(0.0F, -2.5F, -6.0F);
         this.tentGrab2.addBox(0.0F, -7.5F, -9.5F, 0, 8, 10, 0.0F);
         this.tentGrab3 = new ModelRenderer(this, 43, 37);
         this.tentGrab3.setRotationPoint(0.0F, 2.5F, -6.0F);
         this.tentGrab3.addBox(0.0F, -7.5F, -9.5F, 0, 8, 10, 0.0F);
         this.setRotateAngle(this.tentGrab3, 0.0F, 0.0F, (float) Math.PI);
         this.tentCrash6 = new ModelRenderer(this, 20, 11);
         this.tentCrash6.setRotationPoint(-4.0F, 4.0F, -8.0F);
         this.tentCrash6.addBox(0.0F, -5.0F, 0.0F, 0, 5, 8, 0.0F);
         this.setRotateAngle(this.tentCrash6, 0.0F, 0.0F, (float) (-Math.PI * 3.0 / 4.0));
         this.tentMain5 = new ModelRendererGlow(this, 0, 32, 190, true);
         this.tentMain5.setRotationPoint(-2.0F, 3.0F, -2.7F);
         this.tentMain5.addBox(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
         this.setRotateAngle(this.tentMain5, 0.0F, 0.0F, 0.59184116F);
         this.tentShock4 = new ModelRendererGlow(this, 44, -10, 160, true);
         this.tentShock4.setRotationPoint(0.0F, 0.0F, -6.0F);
         this.tentShock4.addBox(0.0F, -7.0F, -8.0F, 0, 14, 10, 0.0F);
         this.setRotateAngle(this.tentShock4, 0.0F, 0.0F, (float) (-Math.PI / 3));
         this.tentShockGlow3 = new ModelRendererGlow(this, 0, 14, 210, true);
         this.tentShockGlow3.setRotationPoint(0.0F, 0.0F, 1.0F);
         this.tentShockGlow3.addBox(-5.0F, -0.5F, -0.5F, 10, 1, 1, 0.0F);
         this.tentMain3 = new ModelRendererGlow(this, 0, 22, 190, true);
         this.tentMain3.setRotationPoint(2.4F, 3.0F, -8.0F);
         this.tentMain3.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.tentMain3, 0.0F, 0.0F, -0.4098033F);
         this.tentCrash4 = new ModelRenderer(this, 20, 11);
         this.tentCrash4.setRotationPoint(-4.0F, -4.0F, -8.0F);
         this.tentCrash4.addBox(0.0F, -5.0F, 0.0F, 0, 5, 8, 0.0F);
         this.setRotateAngle(this.tentCrash4, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.tentMain4 = new ModelRendererGlow(this, 0, 22, 190, true);
         this.tentMain4.setRotationPoint(-2.4F, 3.0F, -6.4F);
         this.tentMain4.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.tentMain4, 0.0F, 0.0F, 0.4098033F);
         this.tentMain6 = new ModelRendererGlow(this, 0, 32, 190, true);
         this.tentMain6.setRotationPoint(2.0F, 3.0F, -4.0F);
         this.tentMain6.addBox(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
         this.setRotateAngle(this.tentMain6, 0.0F, 0.0F, -0.68294734F);
         this.tentMain8 = new ModelRendererGlow(this, 0, 37, 190, true);
         this.tentMain8.setRotationPoint(-2.0F, 3.0F, 1.4F);
         this.tentMain8.addBox(-2.0F, 0.0F, -1.5F, 4, 2, 3, 0.0F);
         this.setRotateAngle(this.tentMain8, 0.0F, 0.0F, 0.8196066F);
         this.tentShockGlow1 = new ModelRendererGlow(this, 0, 14, 210, true);
         this.tentShockGlow1.setRotationPoint(0.0F, 0.0F, -3.0F);
         this.tentShockGlow1.addBox(-3.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
         this.tentCrash1 = new ModelRenderer(this, 34, 32);
         this.tentCrash1.setRotationPoint(0.0F, 18.0F, 0.0F);
         this.tentCrash1.addBox(-2.5F, -2.5F, -2.0F, 5, 5, 10, 0.0F);
         this.tentBite2 = new ModelRenderer(this, 0, 0);
         this.tentBite2.setRotationPoint(-2.0F, 0.0F, -6.0F);
         this.tentBite2.addBox(-2.5F, -0.5F, -7.0F, 4, 1, 8, 0.0F);
         this.tentMain7 = new ModelRendererGlow(this, 0, 37, 190, true);
         this.tentMain7.setRotationPoint(2.0F, 3.0F, 0.0F);
         this.tentMain7.addBox(-2.0F, 0.0F, -1.5F, 4, 2, 3, 0.0F);
         this.setRotateAngle(this.tentMain7, 0.0F, 0.0F, -0.91053826F);
         this.tentMain1 = new ModelRenderer(this, 1, 33);
         this.tentMain1.setRotationPoint(0.0F, 18.0F, 0.0F);
         this.tentMain1.addBox(-3.5F, -3.5F, -10.0F, 7, 7, 14, 0.0F);
         this.tentBite3 = new ModelRenderer(this, 0, 0);
         this.tentBite3.mirror = true;
         this.tentBite3.setRotationPoint(2.0F, 0.0F, -6.0F);
         this.tentBite3.addBox(-1.5F, -0.5F, -7.0F, 4, 1, 8, 0.0F);
         this.tentGrab4 = new ModelRenderer(this, 43, 37);
         this.tentGrab4.setRotationPoint(2.5F, 0.0F, -6.0F);
         this.tentGrab4.addBox(0.0F, -7.5F, -9.5F, 0, 8, 10, 0.0F);
         this.setRotateAngle(this.tentGrab4, 0.0F, 0.0F, (float) (Math.PI / 2));
         this.tentGrab1 = new ModelRenderer(this, 34, 32);
         this.tentGrab1.setRotationPoint(0.0F, 18.0F, 0.0F);
         this.tentGrab1.addBox(-2.5F, -2.5F, -7.0F, 5, 5, 10, 0.0F);
         this.tentCrash3 = new ModelRenderer(this, 20, 11);
         this.tentCrash3.setRotationPoint(4.0F, -4.0F, -8.0F);
         this.tentCrash3.addBox(0.0F, -5.0F, 0.0F, 0, 5, 8, 0.0F);
         this.setRotateAngle(this.tentCrash3, 0.0F, 0.0F, (float) (Math.PI / 4));
         this.tentMain2 = new ModelRendererGlow(this, 25, 12, 160, true);
         this.tentMain2.setRotationPoint(0.0F, 1.0F, -12.0F);
         this.tentMain2.addBox(-2.5F, -2.5F, 0.0F, 5, 5, 2, 0.0F);
         this.tentGrab5 = new ModelRenderer(this, 43, 37);
         this.tentGrab5.setRotationPoint(-2.5F, 0.0F, -6.0F);
         this.tentGrab5.addBox(0.0F, -7.5F, -9.5F, 0, 8, 10, 0.0F);
         this.setRotateAngle(this.tentGrab5, 0.0F, 0.0F, (float) (-Math.PI / 2));
         this.tentCrash5 = new ModelRenderer(this, 20, 11);
         this.tentCrash5.setRotationPoint(4.0F, 4.0F, -8.0F);
         this.tentCrash5.addBox(0.0F, -5.0F, 0.0F, 0, 5, 8, 0.0F);
         this.setRotateAngle(this.tentCrash5, 0.0F, 0.0F, (float) (Math.PI * 3.0 / 4.0));
         this.tentBite4 = new ModelRenderer(this, 0, -2);
         this.tentBite4.setRotationPoint(0.0F, -2.5F, -5.0F);
         this.tentBite4.addBox(0.0F, -4.0F, 0.0F, 0, 4, 11, 0.0F);
         this.tentShockGlow2 = new ModelRendererGlow(this, 0, 14, 210, true);
         this.tentShockGlow2.setRotationPoint(0.0F, 0.0F, -1.0F);
         this.tentShockGlow2.addBox(-4.0F, -0.5F, -0.5F, 8, 1, 1, 0.0F);
         this.tentCrash1.addChild(this.tentCrash2);
         this.tentShock1.addChild(this.tentShock3);
         this.tentShock1.addChild(this.tentShock2);
         this.tentBite1.addChild(this.tentBite5);
         this.tentShock1.addChild(this.tentShock5);
         this.tentGrab1.addChild(this.tentGrab2);
         this.tentGrab1.addChild(this.tentGrab3);
         this.tentCrash2.addChild(this.tentCrash6);
         this.tentMain1.addChild(this.tentMain5);
         this.tentShock1.addChild(this.tentShock4);
         this.tentShock1.addChild(this.tentShockGlow3);
         this.tentMain1.addChild(this.tentMain3);
         this.tentCrash2.addChild(this.tentCrash4);
         this.tentMain1.addChild(this.tentMain4);
         this.tentMain1.addChild(this.tentMain6);
         this.tentMain1.addChild(this.tentMain8);
         this.tentShock1.addChild(this.tentShockGlow1);
         this.tentBite1.addChild(this.tentBite2);
         this.tentMain1.addChild(this.tentMain7);
         this.tentBite1.addChild(this.tentBite3);
         this.tentGrab1.addChild(this.tentGrab4);
         this.tentCrash2.addChild(this.tentCrash3);
         this.tentMain1.addChild(this.tentMain2);
         this.tentGrab1.addChild(this.tentGrab5);
         this.tentCrash2.addChild(this.tentCrash5);
         this.tentBite1.addChild(this.tentBite4);
         this.tentShock1.addChild(this.tentShockGlow2);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         GlStateManager.pushMatrix();
         if (this.mode == 0) {
            GlStateManager.translate(0.0F, -1.3F, 0.0F);
            f5 *= 2.0F;
            this.tentBite1.rotateAngleY = f3 * (float) (Math.PI / 180.0);
            this.tentBite1.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            this.tentBite1.render(f5);
         } else if (this.mode == 1) {
            GlStateManager.translate(0.0F, -1.8F, 0.0F);
            f5 = (float)(f5 * 2.25);
            this.tentShock1.rotateAngleY = f3 * (float) (Math.PI / 180.0);
            this.tentShock1.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            this.tentShock1.render(f5);
         } else if (this.mode == 2) {
            GlStateManager.translate(0.0F, -2.95F, 0.0F);
            f5 *= 3.0F;
            this.tentCrash1.rotateAngleY = f3 * (float) (Math.PI / 180.0);
            this.tentCrash1.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            this.tentCrash1.render(f5);
         } else if (this.mode == 3) {
            GlStateManager.translate(0.0F, -3.05F, 0.0F);
            f5 = (float)(f5 * 3.0);
            this.tentMain1.rotateAngleY = f3 * (float) (Math.PI / 180.0);
            this.tentMain1.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            this.tentMain1.render(f5);
         } else if (this.mode == 4) {
            GlStateManager.translate(0.0F, -1.3F, 0.0F);
            f5 *= 2.0F;
            this.tentGrab1.rotateAngleY = f3 * (float) (Math.PI / 180.0);
            this.tentGrab1.rotateAngleX = f4 * (float) (Math.PI / 180.0);
            this.tentGrab1.render(f5);
         }

         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class MermaidModel extends AbstractMobModel {
      public ModelRenderer head0;
      public ModelRenderer body;
      public ModelRenderer arms1;
      public ModelRenderer armr1;
      public ModelRenderer armt1;
      public ModelRenderer armb1;
      public ModelRenderer head1;
      public ModelRenderer head2;
      public ModelRenderer head3;
      public ModelRenderer body1;
      public ModelRenderer tail0;
      public ModelRenderer finbody;
      public ModelRenderer tail1;
      public ModelRenderer findown1;
      public ModelRenderer tail2;
      public ModelRenderer findown2;
      public ModelRenderer tail3;
      public ModelRenderer findown3;
      public ModelRenderer tail4;
      public ModelRenderer findown4;
      public ModelRenderer tailfin;
      public ModelRenderer arms2;
      public ModelRenderer arms3;
      public ModelRenderer staff1;
      public ModelRenderer staff2;
      public ModelRenderer staff3;
      public ModelRenderer armr2;
      public ModelRenderer armr3;
      public ModelRenderer blade;
      public ModelRenderer armt2;
      public ModelRenderer armt3;
      public ModelRenderer trident1;
      public ModelRenderer trident2;
      public ModelRenderer trident3;
      public ModelRenderer trident4;
      public ModelRenderer trident5;
      public ModelRenderer armb2;
      public ModelRenderer armb3;
      public ModelRenderer bow1;
      public ModelRenderer bow2;
      public ModelRenderer bow4;
      public ModelRenderer bow6;
      public ModelRenderer bow3;
      public ModelRenderer bow5;

      public MermaidModel() {
         this.textureWidth = 80;
         this.textureHeight = 64;
         this.staff3 = new ModelRenderer(this, 2, 0);
         this.staff3.setRotationPoint(-1.0F, -4.0F, 0.5F);
         this.staff3.addBox(0.0F, -4.5F, -3.5F, 0, 10, 7, 0.0F);
         this.head2 = new ModelRenderer(this, 55, 3);
         this.head2.setRotationPoint(-3.0F, -3.0F, 0.0F);
         this.head2.addBox(0.0F, -7.0F, 0.0F, 0, 16, 9, 0.0F);
         this.setRotateAngle(this.head2, 0.0F, -0.59184116F, 0.0F);
         this.staff2 = new ModelRendererGlow(this, 6, 0, 220, false);
         this.staff2.setRotationPoint(-1.0F, -4.0F, 0.5F);
         this.staff2.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.staff2, (float) (Math.PI / 4), 0.0F, 0.0F);
         this.armb2 = new ModelRenderer(this, 65, 54);
         this.armb2.setRotationPoint(1.0F, 7.5F, -1.0F);
         this.armb2.addBox(-1.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F);
         this.setRotateAngle(this.armb2, -1.1383038F, 0.0F, 0.0F);
         this.findown2 = new ModelRenderer(this, 38, -3);
         this.findown2.setRotationPoint(0.0F, 1.0F, -2.0F);
         this.findown2.addBox(0.0F, 0.0F, -3.0F, 0, 6, 3, 0.0F);
         this.head1 = new ModelRenderer(this, 55, 3);
         this.head1.setRotationPoint(3.0F, -3.0F, 0.0F);
         this.head1.addBox(0.0F, -7.0F, 0.0F, 0, 16, 9, 0.0F);
         this.setRotateAngle(this.head1, 0.0F, 0.59184116F, 0.0F);
         this.head3 = new ModelRenderer(this, 0, 12);
         this.head3.setRotationPoint(0.0F, -2.0F, -3.0F);
         this.head3.addBox(0.0F, -7.0F, 0.0F, 0, 8, 8, 0.0F);
         this.armb3 = new ModelRenderer(this, 62, 0);
         this.armb3.setRotationPoint(0.0F, 7.0F, 0.0F);
         this.armb3.addBox(-2.0F, 0.0F, -0.5F, 4, 8, 1, 0.0F);
         this.setRotateAngle(this.armb3, -0.22759093F, 1.2747885F, 0.0F);
         this.armb1 = new ModelRenderer(this, 66, 41);
         this.armb1.setRotationPoint(3.0F, 7.0F, 4.0F);
         this.armb1.addBox(-0.5F, -1.0F, -2.0F, 3, 8, 3, 0.0F);
         this.setRotateAngle(this.armb1, -0.22759093F, -0.3642502F, -0.13665928F);
         this.tail3 = new ModelRenderer(this, 17, 35);
         this.tail3.setRotationPoint(0.0F, 7.0F, 0.4F);
         this.tail3.addBox(-1.0F, 0.0F, -2.0F, 2, 8, 2, 0.0F);
         this.setRotateAngle(this.tail3, 0.4553564F, 0.0F, 0.0F);
         this.armt1 = new ModelRenderer(this, 66, 41);
         this.armt1.setRotationPoint(3.0F, 7.0F, 4.0F);
         this.armt1.addBox(-0.5F, -1.0F, -2.0F, 3, 8, 3, 0.0F);
         this.setRotateAngle(this.armt1, -0.22759093F, -0.3642502F, -0.13665928F);
         this.armt3 = new ModelRenderer(this, 62, 0);
         this.armt3.setRotationPoint(0.0F, 7.0F, 0.0F);
         this.armt3.addBox(-2.0F, 0.0F, -0.5F, 4, 8, 1, 0.0F);
         this.setRotateAngle(this.armt3, -0.22759093F, 1.2747885F, 0.0F);
         this.staff1 = new ModelRenderer(this, 29, 25);
         this.staff1.setRotationPoint(-1.0F, 12.0F, -11.0F);
         this.staff1.addBox(-2.0F, -3.0F, -0.5F, 2, 35, 2, 0.0F);
         this.setRotateAngle(this.staff1, 1.7301449F, 0.0F, 0.0F);
         this.finbody = new ModelRenderer(this, 37, 18);
         this.finbody.setRotationPoint(0.0F, -2.0F, 3.0F);
         this.finbody.addBox(0.0F, -7.0F, 0.0F, 0, 15, 5, 0.0F);
         this.trident4 = new ModelRenderer(this, 51, 12);
         this.trident4.setRotationPoint(0.0F, -10.0F, 0.0F);
         this.trident4.addBox(-1.0F, -12.0F, 3.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.trident4, -0.18203785F, 0.0F, 0.0F);
         this.trident5 = new ModelRenderer(this, 7, 28);
         this.trident5.setRotationPoint(0.0F, -10.0F, 0.0F);
         this.trident5.addBox(-1.5F, -14.0F, 0.0F, 2, 10, 1, 0.0F);
         this.bow4 = new ModelRendererGlow(this, 54, 35, 80, true);
         this.bow4.setRotationPoint(0.0F, -2.5F, 0.0F);
         this.bow4.addBox(0.0F, -10.0F, -3.5F, 0, 14, 5, 0.0F);
         this.setRotateAngle(this.bow4, -0.5462881F, 0.0F, 0.0F);
         this.arms1 = new ModelRenderer(this, 66, 41);
         this.arms1.setRotationPoint(3.0F, 7.0F, 4.0F);
         this.arms1.addBox(-0.5F, -1.0F, -2.0F, 3, 8, 3, 0.0F);
         this.setRotateAngle(this.arms1, -0.22759093F, -0.3642502F, -0.13665928F);
         this.arms2 = new ModelRenderer(this, 65, 54);
         this.arms2.setRotationPoint(1.0F, 7.5F, -1.0F);
         this.arms2.addBox(-1.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F);
         this.setRotateAngle(this.arms2, -1.1383038F, 0.0F, 0.0F);
         this.bow2 = new ModelRendererGlow(this, 54, 35, 80, true);
         this.bow2.setRotationPoint(0.0F, 13.5F, 0.0F);
         this.bow2.addBox(0.0F, -10.0F, -3.5F, 0, 14, 5, 0.0F);
         this.setRotateAngle(this.bow2, -0.5462881F, 0.0F, (float) Math.PI);
         this.tail1 = new ModelRenderer(this, 18, 12);
         this.tail1.setRotationPoint(0.0F, 6.0F, 0.0F);
         this.tail1.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
         this.setRotateAngle(this.tail1, 0.4553564F, 0.0F, 0.0F);
         this.trident1 = new ModelRenderer(this, 0, 27);
         this.trident1.setRotationPoint(-1.0F, 10.5F, -1.0F);
         this.trident1.addBox(-1.0F, -13.0F, -0.5F, 1, 35, 2, 0.0F);
         this.setRotateAngle(this.trident1, 1.7301449F, 0.0F, 0.0F);
         this.tailfin = new ModelRenderer(this, 55, 20);
         this.tailfin.setRotationPoint(0.0F, 1.0F, 0.0F);
         this.tailfin.addBox(0.0F, 0.0F, -4.0F, 0, 12, 8, 0.0F);
         this.bow1 = new ModelRenderer(this, 47, 23);
         this.bow1.setRotationPoint(-1.4F, 11.0F, -6.0F);
         this.bow1.addBox(-1.0F, -2.0F, -0.5F, 2, 15, 2, 0.0F);
         this.setRotateAngle(this.bow1, (float) (Math.PI / 2), -0.091106184F, 0.0F);
         this.tail4 = new ModelRenderer(this, 25, 37);
         this.tail4.setRotationPoint(0.0F, 7.5F, -1.0F);
         this.tail4.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F);
         this.setRotateAngle(this.tail4, 0.4553564F, 0.0F, 0.0F);
         this.trident2 = new ModelRenderer(this, 45, 54);
         this.trident2.setRotationPoint(0.0F, -10.0F, 0.0F);
         this.trident2.addBox(-1.5F, -4.0F, -3.5F, 2, 1, 8, 0.0F);
         this.body = new ModelRenderer(this, 5, 46);
         this.body.setRotationPoint(0.0F, 10.0F, 2.0F);
         this.body.addBox(-3.0F, -6.0F, -3.0F, 6, 12, 6, 0.0F);
         this.setRotateAngle(this.body, -0.22759093F, 0.0F, 0.0F);
         this.armr3 = new ModelRenderer(this, 62, 0);
         this.armr3.setRotationPoint(0.0F, 7.0F, 0.0F);
         this.armr3.addBox(-2.0F, 0.0F, -0.5F, 4, 8, 1, 0.0F);
         this.setRotateAngle(this.armr3, -0.22759093F, -1.548107F, 0.18203785F);
         this.trident3 = new ModelRenderer(this, 51, 12);
         this.trident3.setRotationPoint(0.0F, -10.0F, 0.0F);
         this.trident3.addBox(-1.0F, -12.0F, -3.5F, 1, 8, 1, 0.0F);
         this.setRotateAngle(this.trident3, 0.18203785F, 0.0F, 0.0F);
         this.tail0 = new ModelRenderer(this, 18, 0);
         this.tail0.setRotationPoint(0.0F, 5.0F, -1.0F);
         this.tail0.addBox(-2.5F, 0.0F, -2.0F, 5, 7, 5, 0.0F);
         this.setRotateAngle(this.tail0, 0.4553564F, 0.0F, 0.0F);
         this.tail2 = new ModelRenderer(this, 16, 24);
         this.tail2.setRotationPoint(0.0F, 7.0F, 0.0F);
         this.tail2.addBox(-1.5F, 0.0F, -2.0F, 3, 8, 3, 0.0F);
         this.setRotateAngle(this.tail2, 0.4553564F, 0.0F, 0.0F);
         this.body1 = new ModelRenderer(this, 34, 12);
         this.body1.setRotationPoint(0.0F, -6.0F, -1.0F);
         this.body1.addBox(-2.0F, -6.0F, -2.0F, 4, 7, 4, 0.0F);
         this.setRotateAngle(this.body1, 0.4553564F, 0.0F, 0.0F);
         this.findown1 = new ModelRenderer(this, 38, -3);
         this.findown1.setRotationPoint(0.0F, 1.0F, -2.0F);
         this.findown1.addBox(0.0F, 0.0F, -3.0F, 0, 6, 3, 0.0F);
         this.armr1 = new ModelRenderer(this, 66, 41);
         this.armr1.setRotationPoint(-3.0F, 7.0F, 4.0F);
         this.armr1.addBox(-2.5F, -1.0F, -2.0F, 3, 8, 3, 0.0F);
         this.setRotateAngle(this.armr1, -0.22759093F, 0.3642502F, 0.13665928F);
         this.armr2 = new ModelRenderer(this, 65, 54);
         this.armr2.setRotationPoint(-1.0F, 7.5F, -1.0F);
         this.armr2.addBox(-1.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F);
         this.setRotateAngle(this.armr2, -0.63739425F, 0.0F, 0.0F);
         this.armt2 = new ModelRenderer(this, 65, 54);
         this.armt2.setRotationPoint(1.0F, 7.5F, -1.0F);
         this.armt2.addBox(-1.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F);
         this.setRotateAngle(this.armt2, -1.1383038F, 0.0F, 0.0F);
         this.bow5 = new ModelRendererGlow(this, 0, 0, 240, false);
         this.bow5.setRotationPoint(0.0F, -8.5F, 1.0F);
         this.bow5.addBox(0.05F, 0.0F, -0.5F, 0, 15, 1, 0.0F);
         this.setRotateAngle(this.bow5, 0.5462881F, 0.0F, 0.0F);
         this.arms3 = new ModelRenderer(this, 62, 0);
         this.arms3.setRotationPoint(0.0F, 7.0F, 0.0F);
         this.arms3.addBox(-2.0F, 0.0F, -0.5F, 4, 8, 1, 0.0F);
         this.setRotateAngle(this.arms3, -0.22759093F, 1.2747885F, 0.0F);
         this.blade = new ModelRenderer(this, 45, 37);
         this.blade.setRotationPoint(-1.5F, 3.0F, 0.0F);
         this.blade.addBox(0.0F, 0.0F, -0.5F, 0, 16, 4, 0.0F);
         this.setRotateAngle(this.blade, -0.22759093F, -1.548107F, 0.18203785F);
         this.bow6 = new ModelRendererGlow(this, 33, 53, 240, false);
         this.bow6.setRotationPoint(-0.8F, 5.5F, -4.6F);
         this.bow6.addBox(-0.3F, -0.5F, -0.5F, 1, 1, 10, 0.0F);
         this.bow3 = new ModelRendererGlow(this, 0, 0, 240, false);
         this.bow3.setRotationPoint(0.0F, -8.5F, 1.0F);
         this.bow3.addBox(-0.05F, -15.0F, -0.5F, 0, 15, 1, 0.0F);
         this.setRotateAngle(this.bow3, -2.5953045F, 0.0F, 0.0F);
         this.head0 = new ModelRenderer(this, 38, 0);
         this.head0.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.head0.addBox(-3.0F, -6.0F, -4.0F, 6, 6, 6, 0.0F);
         this.findown4 = new ModelRenderer(this, 38, -3);
         this.findown4.setRotationPoint(0.0F, 1.0F, -2.0F);
         this.findown4.addBox(0.0F, 0.0F, -3.0F, 0, 6, 3, 0.0F);
         this.findown3 = new ModelRenderer(this, 38, -3);
         this.findown3.setRotationPoint(0.0F, 1.0F, -2.0F);
         this.findown3.addBox(0.0F, 0.0F, -3.0F, 0, 6, 3, 0.0F);
         this.staff1.addChild(this.staff3);
         this.head0.addChild(this.head2);
         this.staff1.addChild(this.staff2);
         this.armb1.addChild(this.armb2);
         this.tail1.addChild(this.findown2);
         this.head0.addChild(this.head1);
         this.head0.addChild(this.head3);
         this.armb2.addChild(this.armb3);
         this.tail2.addChild(this.tail3);
         this.armt2.addChild(this.armt3);
         this.arms2.addChild(this.staff1);
         this.body.addChild(this.finbody);
         this.trident1.addChild(this.trident4);
         this.trident1.addChild(this.trident5);
         this.bow1.addChild(this.bow4);
         this.arms1.addChild(this.arms2);
         this.bow1.addChild(this.bow2);
         this.tail0.addChild(this.tail1);
         this.armt2.addChild(this.trident1);
         this.tail4.addChild(this.tailfin);
         this.armb2.addChild(this.bow1);
         this.tail3.addChild(this.tail4);
         this.trident1.addChild(this.trident2);
         this.armr2.addChild(this.armr3);
         this.trident1.addChild(this.trident3);
         this.body.addChild(this.tail0);
         this.tail1.addChild(this.tail2);
         this.body.addChild(this.body1);
         this.tail0.addChild(this.findown1);
         this.armr1.addChild(this.armr2);
         this.armt1.addChild(this.armt2);
         this.bow4.addChild(this.bow5);
         this.arms2.addChild(this.arms3);
         this.armr2.addChild(this.blade);
         this.bow1.addChild(this.bow6);
         this.bow2.addChild(this.bow3);
         this.tail3.addChild(this.findown4);
         this.tail2.addChild(this.findown3);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         int WEAPON = 0;
         if (isAbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            float var4x = mob.var4;
            float var3x = mob.var3;
            float vars = var3x + (var4x - var3x) * pt;
            float tailSin = MathHelper.sin(vars / 3.0F);
            this.tail0.rotateAngleY = tailSin * 0.1F;
            this.tail1.rotateAngleY = tailSin * 0.174F;
            this.tail2.rotateAngleZ = -tailSin * 0.3F;
            this.tail3.rotateAngleZ = -tailSin * 0.39F;
            this.tail4.rotateAngleZ = -tailSin * 0.4F;
            WEAPON = mob.var1;
         }

         float speed = Math.min((float)GetMOP.getSpeed(entity) * 100.0F, 60.0F);
         this.head1.rotateAngleY = 0.59184116F + MathHelper.sin((entity.ticksExisted + pt) / 45.0F) * 0.34F;
         this.head2.rotateAngleY = -this.head1.rotateAngleY;
         this.head0.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.head0.rotateAngleX = (f4 - speed) * (float) (Math.PI / 180.0);
         this.setRotateAngle(this.armr1, -0.22759093F, 0.3642502F, 0.13665928F);
         this.setRotateAngle(this.armr2, -0.63739425F, 0.0F, 0.0F);
         this.setRotateAngle(this.armr3, -0.22759093F, -1.548107F, 0.18203785F);
         if (WEAPON == 0) {
            float animstaff = MathHelper.clamp((100.0F - an3 + pt) / 25.0F, 0.0F, 1.0F);
            this.setRotateAngle(this.arms1, -0.22759093F, -0.3642502F, -0.13665928F);
            this.setRotateAngle(this.arms2, -1.1383038F, 0.0F, 0.0F);
            this.setRotateAngle(this.arms3, -0.22759093F, 1.2747885F, 0.0F);
            float ft1 = GetMOP.getfromto(animstaff, 0.0F, 0.4F) - GetMOP.getfromto(animstaff, 0.6F, 1.0F);
            this.arms1.rotateAngleX += -47.0F * ft1 * (float) (Math.PI / 180.0);
            this.armt1.rotateAngleY = this.armt1.rotateAngleY + this.head0.rotateAngleY;
            this.arms2.rotateAngleX += 19.0F * ft1 * (float) (Math.PI / 180.0);
            this.arms2.rotateAngleZ += 15.0F * ft1 * (float) (Math.PI / 180.0);
         }

         if (WEAPON == 1) {
            float animtrident = MathHelper.clamp((100.0F - an3 + pt) / 40.0F, 0.0F, 1.0F);
            this.setRotateAngle(this.armt1, -0.22759093F, -0.3642502F, -0.13665928F);
            this.setRotateAngle(this.armt2, -1.1383038F, 0.0F, 0.0F);
            this.setRotateAngle(this.armt3, -0.22759093F, 1.2747885F, 0.0F);
            float ft1 = GetMOP.getfromto(animtrident, 0.0F, 0.2F);
            float ft2 = GetMOP.getfromto(animtrident, 0.6F, 0.7F);
            float ft3 = GetMOP.getfromto(animtrident, 0.7F, 1.0F);
            this.armt1.rotateAngleX = this.armt1.rotateAngleX
               + ((96.0F * ft1 - 143.0F * ft2 + 47.0F * ft3) * (float) (Math.PI / 180.0) + this.head0.rotateAngleX);
            this.armt1.rotateAngleY = this.armt1.rotateAngleY + ((22.0F * ft1 - 22.0F * ft3) * (float) (Math.PI / 180.0) + this.head0.rotateAngleY);
            this.armt2.rotateAngleX += (-23.0F * ft1 + 73.0F * ft2 - 50.0F * ft3) * (float) (Math.PI / 180.0);
            this.trident1.rotateAngleX = 1.7301449F + (44.0F * ft2 - 44.0F * ft3) * (float) (Math.PI / 180.0);
            this.trident1.offsetY = (16.0F * ft2 - 16.0F * ft3) * 0.0625F;
            this.trident1.offsetZ = (-14.0F * ft2 + 14.0F * ft3) * 0.0625F;
         }

         if (WEAPON == 2) {
            float animbow = MathHelper.clamp((100.0F - an3 + pt) / 30.0F, 0.0F, 1.0F);
            this.setRotateAngle(this.armb1, -0.22759093F, -0.3642502F, -0.13665928F);
            this.setRotateAngle(this.armb2, -1.1383038F, 0.0F, 0.0F);
            this.setRotateAngle(this.armb3, -0.22759093F, 1.2747885F, 0.0F);
            float ft1 = GetMOP.getfromto(animbow, 0.0F, 0.3F);
            float ft2 = GetMOP.getfromto(animbow, 0.2F, 0.5F);
            float ft3 = GetMOP.getfromto(animbow, 0.5F, 0.7F);
            float ft4 = GetMOP.getfromto(animbow, 0.7F, 0.8F);
            float ft5 = GetMOP.getfromto(animbow, 0.8F, 1.0F);
            this.armb1.rotateAngleX = this.armb1.rotateAngleX + ((-40.0F * ft1 + 40.0F * ft5) * (float) (Math.PI / 180.0) + this.head0.rotateAngleX);
            this.armb1.rotateAngleY = this.armb1.rotateAngleY + ((21.0F * ft1 - 21.0F * ft5) * (float) (Math.PI / 180.0) + this.head0.rotateAngleY);
            this.armb2.rotateAngleX += (25.0F * ft1 - 25.0F * ft5) * (float) (Math.PI / 180.0);
            this.armr1.rotateAngleX = this.armr1.rotateAngleX
               + ((-35.0F * ft2 + 60.0F * ft3 - 25.0F * ft5) * (float) (Math.PI / 180.0) + this.head0.rotateAngleX);
            this.armr1.rotateAngleY = this.armr1.rotateAngleY + ((-37.0F * ft2 + 37.0F * ft5) * (float) (Math.PI / 180.0) + this.head0.rotateAngleY);
            this.armr2.rotateAngleX += (-76.0F * ft3 + 76.0F * ft5) * (float) (Math.PI / 180.0);
            this.armr3.rotateAngleX += (-30.0F * ft3 + 30.0F * ft5) * (float) (Math.PI / 180.0);
            this.bow4.rotateAngleX = -0.5462881F + (-15.0F * ft3 + 15.0F * ft4) * (float) (Math.PI / 180.0);
            this.bow2.rotateAngleX = -0.5462881F + (-15.0F * ft3 + 15.0F * ft4) * (float) (Math.PI / 180.0);
            this.bow5.rotateAngleX = 0.5462881F + (29.0F * ft3 - 29.0F * ft4) * (float) (Math.PI / 180.0);
            this.bow3.rotateAngleX = -2.5953045F + (29.0F * ft3 - 29.0F * ft4) * (float) (Math.PI / 180.0);
            this.bow6.isHidden = ft5 > 0.0F;
            this.bow6.offsetZ = (3.0F * ft3 - 11.0F * ft4) * 0.0625F;
         }

         float animblade = MathHelper.clamp((100.0F - an1 + pt) / 20.0F, 0.0F, 1.0F);
         float fta1 = GetMOP.getfromto(animblade, 0.0F, 0.3F);
         float fta2 = GetMOP.getfromto(animblade, 0.5F, 0.6F);
         float fta3 = GetMOP.getfromto(animblade, 0.6F, 1.0F);
         this.armr1.rotateAngleX = this.armr1.rotateAngleX + ((-52.0F * fta2 + 52.0F * fta3) * (float) (Math.PI / 180.0) + this.head0.rotateAngleX);
         this.armr1.rotateAngleY += (90.0F * fta1 - 136.0F * fta2 + 46.0F * fta3) * (float) (Math.PI / 180.0);
         this.armr1.rotateAngleZ += (39.0F * fta1 - 39.0F * fta2) * (float) (Math.PI / 180.0);
         this.armr3.rotateAngleX += (-100.0F * fta1 + 100.0F * fta3) * (float) (Math.PI / 180.0);
         GlStateManager.pushMatrix();
         GlStateManager.rotate(speed, 1.0F, 0.0F, 0.0F);
         if (WEAPON == 0) {
            this.arms1.render(f5);
         }

         this.body.render(f5);
         this.head0.render(f5);
         if (WEAPON == 1) {
            this.armt1.render(f5);
         }

         this.armr1.render(f5);
         if (WEAPON == 2) {
            this.armb1.render(f5);
         }

         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class NeedletoothModel extends AbstractMobModel {
      public ModelRenderer body;
      public ModelRenderer tail1;
      public ModelRenderer head;
      public ModelRenderer body2;
      public ModelRenderer fin;
      public ModelRenderer armfin1;
      public ModelRenderer armfin2;
      public ModelRenderer head2;
      public ModelRenderer tail2;
      public ModelRenderer tail3;
      public ModelRenderer fin2;

      public NeedletoothModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.tail3 = new ModelRenderer(this, 32, 0);
         this.tail3.setRotationPoint(0.0F, 0.0F, 7.0F);
         this.tail3.addBox(-0.5F, -1.7F, -0.8F, 1, 2, 7, 0.0F);
         this.setRotateAngle(this.tail3, 0.091106184F, 0.0F, 0.0F);
         this.fin2 = new ModelRenderer(this, 0, 32);
         this.fin2.setRotationPoint(0.0F, -2.0F, 2.0F);
         this.fin2.addBox(0.0F, 0.3F, -3.8F, 0, 5, 11, 0.0F);
         this.head = new ModelRenderer(this, 0, 0);
         this.head.setRotationPoint(0.0F, 0.0F, -7.0F);
         this.head.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
         this.setRotateAngle(this.head, 0.59184116F, 0.68294734F, 0.4098033F);
         this.fin = new ModelRenderer(this, 36, 32);
         this.fin.setRotationPoint(0.0F, -4.0F, -2.0F);
         this.fin.addBox(-0.5F, -4.0F, 0.0F, 1, 4, 4, 0.0F);
         this.setRotateAngle(this.fin, -0.31869712F, 0.0F, 0.0F);
         this.armfin1 = new ModelRenderer(this, 0, 33);
         this.armfin1.setRotationPoint(3.0F, 3.0F, -4.0F);
         this.armfin1.addBox(0.0F, 0.0F, 0.0F, 1, 5, 5, 0.0F);
         this.setRotateAngle(this.armfin1, -0.27314404F, 0.0F, -0.8196066F);
         this.tail1 = new ModelRenderer(this, 40, 4);
         this.tail1.setRotationPoint(0.0F, 18.0F, -1.0F);
         this.tail1.addBox(-2.0F, -3.0F, 4.2F, 4, 6, 8, 0.0F);
         this.body2 = new ModelRenderer(this, 12, 36);
         this.body2.setRotationPoint(0.0F, 5.0F, -3.0F);
         this.body2.addBox(-4.5F, -4.0F, -2.8F, 9, 4, 3, 0.0F);
         this.setRotateAngle(this.body2, -0.4553564F, 0.0F, 0.0F);
         this.head2 = new ModelRenderer(this, 30, 21);
         this.head2.setRotationPoint(0.0F, 2.0F, -3.0F);
         this.head2.addBox(-4.0F, -4.0F, -4.0F, 8, 3, 8, 0.0F);
         this.setRotateAngle(this.head2, 0.59184116F, 0.68294734F, 0.4098033F);
         this.tail2 = new ModelRenderer(this, 28, 10);
         this.tail2.setRotationPoint(0.0F, 0.0F, 12.0F);
         this.tail2.addBox(-1.0F, -2.0F, -0.8F, 2, 3, 8, 0.0F);
         this.body = new ModelRenderer(this, 0, 16);
         this.body.setRotationPoint(0.0F, 18.0F, 3.0F);
         this.body.addBox(-3.5F, -4.0F, -6.8F, 7, 8, 8, 0.0F);
         this.armfin2 = new ModelRenderer(this, 0, 33);
         this.armfin2.setRotationPoint(-3.0F, 3.0F, -4.0F);
         this.armfin2.addBox(-1.0F, 0.0F, 0.0F, 1, 5, 5, 0.0F);
         this.setRotateAngle(this.armfin2, -0.27314404F, 0.0F, 0.8196066F);
         this.tail2.addChild(this.tail3);
         this.tail3.addChild(this.fin2);
         this.body.addChild(this.head);
         this.body.addChild(this.fin);
         this.body.addChild(this.armfin1);
         this.body.addChild(this.body2);
         this.body2.addChild(this.head2);
         this.tail1.addChild(this.tail2);
         this.body.addChild(this.armfin2);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         this.body2.rotateAngleX = -0.4553564F;
         if (an1 > 90) {
            float ptan1 = 100.0F - an1 + pt;
            this.body2.rotateAngleX = -0.4553564F + (GetMOP.getfromto(ptan1, 0.0F, 6.0F) - GetMOP.getfromto(ptan1, 6.0F, 10.0F)) * 0.37F;
         }

         this.body.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.body.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.tail1.rotateAngleY = 0.0F;
         this.tail1.rotateAngleX = f4 * 0.003453292F;
         this.tail2.rotateAngleY = f3 * -0.002053292F;
         this.tail2.rotateAngleX = f4 * 0.002053292F;
         this.tail3.rotateAngleY = f3 * -0.002053292F;
         this.tail3.rotateAngleX = 0.091106184F + f4 * 0.002053292F;
         if (isAbstractMob) {
            float var4x = ((AbstractMob)entity).var4;
            float var3x = ((AbstractMob)entity).var3;
            float vars = var3x + (var4x - var3x) * pt;
            float tailSin = MathHelper.sin(vars / 3.0F);
            this.tail1.rotateAngleY += tailSin * 0.174F;
            this.tail2.rotateAngleY += tailSin * 0.3F;
            this.tail3.rotateAngleY += tailSin * 0.39F;
         }

         float finCos = MathHelper.cos(AnimationTimer.tick / 50.0F) * 0.35F;
         float finSin = MathHelper.sin(AnimationTimer.tick / 50.0F) * 0.35F;
         this.armfin1.rotateAngleY = finCos;
         this.armfin1.rotateAngleX = -0.27314404F - finSin;
         this.armfin2.rotateAngleY = -this.armfin1.rotateAngleY;
         this.armfin2.rotateAngleX = this.armfin1.rotateAngleX;
         float scl = entity.height + 0.15F;
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 1.7F, 0.0F);
         GlStateManager.scale(scl, scl, scl);
         GlStateManager.translate(0.0F, -1.7F, 0.0F);
         this.tail1.render(f5);
         this.body.render(f5);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class PolipoidModel extends AbstractMobModel {
      public ModelRenderer body;
      public ModelRenderer truebody;
      public ModelRenderer podia1;
      public ModelRenderer body2;
      public ModelRenderer shape;
      public ModelRenderer tent;
      public ModelRenderer podia2;
      public ModelRenderer podia3;

      public PolipoidModel() {
         this.textureWidth = 22;
         this.textureHeight = 32;
         this.body = new ModelRenderer(this, 0, 10);
         this.body.setRotationPoint(0.0F, 13.0F, 0.0F);
         this.body.addBox(-1.5F, -7.0F, -1.5F, 3, 6, 3, 0.0F);
         this.podia2 = new ModelRenderer(this, 5, 22);
         this.podia2.setRotationPoint(0.0F, 0.0F, 9.5F);
         this.podia2.addBox(0.0F, -0.5F, 0.0F, 0, 1, 8, 0.0F);
         this.podia1 = new ModelRenderer(this, 5, 21);
         this.podia1.setRotationPoint(0.0F, 13.0F, 0.0F);
         this.podia1.addBox(0.0F, -0.5F, 1.5F, 0, 1, 8, 0.0F);
         this.tent = new ModelRenderer(this, -6, 20);
         this.tent.setRotationPoint(0.0F, -0.8F, 0.0F);
         this.tent.addBox(-4.5F, 0.0F, -4.5F, 9, 0, 9, 0.0F);
         this.setRotateAngle(this.tent, 0.0F, 0.7740535F, 0.0F);
         this.body2 = new ModelRenderer(this, 12, 10);
         this.body2.setRotationPoint(0.0F, -7.0F, 0.0F);
         this.body2.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
         this.podia3 = new ModelRenderer(this, 5, 23);
         this.podia3.setRotationPoint(0.0F, 0.0F, 8.0F);
         this.podia3.addBox(0.0F, -0.5F, 0.0F, 0, 1, 8, 0.0F);
         this.truebody = new ModelRenderer(this, 0, 0);
         this.truebody.setRotationPoint(0.0F, 13.0F, 0.0F);
         this.truebody.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F);
         this.shape = new ModelRenderer(this, 9, 16);
         this.shape.setRotationPoint(0.0F, -4.0F, 0.0F);
         this.shape.addBox(-1.5F, -1.0F, -1.5F, 3, 1, 3, 0.0F);
         this.podia1.addChild(this.podia2);
         this.shape.addChild(this.tent);
         this.body.addChild(this.body2);
         this.podia2.addChild(this.podia3);
         this.body2.addChild(this.shape);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         boolean blend = Minecraft.getMinecraft().getRenderViewEntity() == null || entity.getDistanceSq(Minecraft.getMinecraft().getRenderViewEntity()) < 1600.0;
         if (blend) {
            GL11.glEnable(3042);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 0.25F, 0.0F);
         int breeds = 0;
         float vars = 0.0F;
         if (isAbstractMob) {
            breeds = ((AbstractMob)entity).var1;
            float var4x = ((AbstractMob)entity).var4;
            float var3x = ((AbstractMob)entity).var3;
            vars = (var3x + (var4x - var3x) * pt) * 3.2F;
         }

         Random rand = new Random(entity.getEntityId());
         this.truebody.render(f5);

         for (int i = 0; i < breeds; i++) {
            this.body.rotateAngleX = rand.nextFloat() * 6.29F;
            this.body.rotateAngleY = rand.nextFloat() * 6.29F;
            this.body.rotateAngleZ = rand.nextFloat() * 6.29F;
            this.body2.rotateAngleX = (rand.nextFloat() - 0.5F) * 0.24F;
            this.body2.rotateAngleY = (rand.nextFloat() - 0.5F) * 0.24F;
            this.body2.rotateAngleZ = (rand.nextFloat() - 0.5F) * 0.24F;
            this.shape.rotateAngleX = this.body2.rotateAngleX;
            this.shape.rotateAngleY = this.body2.rotateAngleY;
            this.shape.rotateAngleZ = this.body2.rotateAngleZ;
            this.tent.rotateAngleY = rand.nextFloat() * 6.29F;
            this.body.render(f5);
         }

         for (int i = 0; i < breeds - 3; i++) {
            this.podia1.rotateAngleX = (rand.nextFloat() - 0.5F) * 0.35F;
            this.podia1.rotateAngleZ = (rand.nextFloat() - 0.5F) * 0.35F;
            this.podia1.rotateAngleY = MathHelper.sin(vars + 0.5F + i) * 0.35F;
            this.podia2.rotateAngleY = -MathHelper.sin(vars + 1.0F + i) * 0.35F;
            this.podia3.rotateAngleY = -MathHelper.sin(vars + 1.5F + i) * 0.35F;
            this.podia1.render(f5);
         }

         GlStateManager.popMatrix();
         if (blend) {
            GL11.glDisable(3042);
         }
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class SeaStrikerModel extends ModelBase {
      public ModelRenderer body1;
      public ModelRenderer body2;
      public ModelRenderer body3;
      public ModelRenderer head;
      public ModelRenderer head3;
      public ModelRenderer spikes1;
      public ModelRenderer spikes2;
      public ModelRenderer spikes3;
      public ModelRenderer head2;

      public SeaStrikerModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.body1 = new ModelRenderer(this, 0, 0);
         this.body1.setRotationPoint(0.0F, 24.2F, 0.0F);
         this.body1.addBox(-3.0F, -2.0F, -3.0F, 6, 2, 6, 0.0F);
         this.setRotateAngle(this.body1, -0.045553092F, 0.0F, 0.0F);
         this.body2 = new ModelRenderer(this, 16, 24);
         this.body2.setRotationPoint(0.0F, -2.5F, 0.0F);
         this.body2.addBox(-1.5F, -4.0F, -1.5F, 3, 5, 3, 0.0F);
         this.setRotateAngle(this.body2, 0.22759093F, 0.0F, 0.0F);
         this.head = new ModelRenderer(this, 0, 8);
         this.head.setRotationPoint(0.0F, -7.5F, 0.0F);
         this.head.addBox(-4.0F, -6.0F, -4.0F, 8, 8, 8, 0.0F);
         this.setRotateAngle(this.head, -0.045553092F, 0.0F, 0.0F);
         this.body3 = new ModelRenderer(this, 0, 8);
         this.body3.setRotationPoint(0.0F, -3.5F, 0.0F);
         this.body3.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.body3, -0.13665928F, 0.0F, 0.0F);
         this.spikes2 = new ModelRenderer(this, 28, -18);
         this.spikes2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.spikes2.addBox(0.0F, -12.0F, -9.0F, 0, 16, 18, 0.0F);
         this.setRotateAngle(this.spikes2, 0.0F, (float) (-Math.PI / 6), 0.0F);
         this.spikes3 = new ModelRenderer(this, 28, -18);
         this.spikes3.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.spikes3.addBox(0.0F, -12.0F, -9.0F, 0, 16, 18, 0.0F);
         this.setRotateAngle(this.spikes3, 0.0F, (float) (Math.PI / 2), 0.0F);
         this.head2 = new ModelRenderer(this, 28, 21);
         this.head2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.head2.addBox(-6.0F, -4.0F, -3.0F, 12, 5, 6, 0.0F);
         this.setRotateAngle(this.head2, 0.0F, (float) (Math.PI / 2), 0.0F);
         this.spikes1 = new ModelRenderer(this, 28, -18);
         this.spikes1.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.spikes1.addBox(0.0F, -12.0F, -9.0F, 0, 16, 18, 0.0F);
         this.setRotateAngle(this.spikes1, 0.0F, (float) (Math.PI / 6), 0.0F);
         this.head3 = new ModelRenderer(this, 28, 21);
         this.head3.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.head3.addBox(-6.0F, -4.0F, -3.0F, 12, 5, 6, 0.0F);
         this.body1.addChild(this.body2);
         this.body3.addChild(this.head);
         this.body2.addChild(this.body3);
         this.head.addChild(this.spikes2);
         this.head.addChild(this.spikes3);
         this.head.addChild(this.head2);
         this.head.addChild(this.spikes1);
         this.head.addChild(this.head3);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.body1.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class SirenModel extends AbstractMobModel {
      public ModelRenderer head;
      public ModelRenderer leg1a;
      public ModelRenderer body;
      public ModelRenderer leg2a;
      public ModelRenderer wing1;
      public ModelRenderer wing2;
      public ModelRenderer hat;
      public ModelRenderer leg1b;
      public ModelRenderer leg1c;
      public ModelRenderer tail;
      public ModelRenderer leg2b;
      public ModelRenderer leg2c;

      public SirenModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.leg1a = new ModelRenderer(this, 32, 0);
         this.leg1a.setRotationPoint(2.0F, 13.0F, 4.0F);
         this.leg1a.addBox(-1.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
         this.setRotateAngle(this.leg1a, -0.59184116F, 0.0F, 0.0F);
         this.wing1 = new ModelRenderer(this, 0, 16);
         this.wing1.setRotationPoint(2.0F, 6.0F, 2.0F);
         this.wing1.addBox(0.0F, 0.0F, 0.0F, 12, 16, 0, 0.0F);
         this.setRotateAngle(this.wing1, 0.5462881F, 0.045553092F, -0.3642502F);
         this.wing2 = new ModelRenderer(this, 0, 16);
         this.wing2.mirror = true;
         this.wing2.setRotationPoint(-2.0F, 6.0F, 2.0F);
         this.wing2.addBox(-12.0F, 0.0F, 0.0F, 12, 16, 0, 0.0F);
         this.setRotateAngle(this.wing2, 0.5462881F, 0.045553092F, 0.3642502F);
         this.leg2b = new ModelRenderer(this, 48, 24);
         this.leg2b.mirror = true;
         this.leg2b.setRotationPoint(-1.0F, 5.0F, -1.0F);
         this.leg2b.addBox(-1.0F, 0.0F, -2.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.leg2b, (float) (Math.PI / 3), 0.0F, 0.0F);
         this.leg1b = new ModelRenderer(this, 48, 24);
         this.leg1b.setRotationPoint(1.0F, 5.0F, -1.0F);
         this.leg1b.addBox(-1.0F, 0.0F, -2.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.leg1b, (float) (Math.PI / 3), 0.0F, 0.0F);
         this.tail = new ModelRenderer(this, 33, 10);
         this.tail.setRotationPoint(0.0F, 9.0F, 2.0F);
         this.tail.addBox(-6.0F, -15.0F, 0.0F, 12, 14, 0, 0.0F);
         this.setRotateAngle(this.tail, -2.276433F, 0.0F, 0.0F);
         this.hat = new ModelRenderer(this, 0, 33);
         this.hat.setRotationPoint(0.0F, -5.0F, 5.0F);
         this.hat.addBox(-6.0F, -10.0F, -4.0F, 12, 14, 0, 0.0F);
         this.setRotateAngle(this.hat, -0.5009095F, 0.0F, 0.0F);
         this.leg1c = new ModelRenderer(this, 30, 24);
         this.leg1c.setRotationPoint(-1.0F, 5.0F, -1.0F);
         this.leg1c.addBox(-1.0F, 0.0F, -2.0F, 4, 3, 4, 0.0F);
         this.setRotateAngle(this.leg1c, 0.7285004F, 0.0F, 0.0F);
         this.body = new ModelRenderer(this, 27, 32);
         this.body.setRotationPoint(0.0F, 4.0F, 1.0F);
         this.body.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
         this.setRotateAngle(this.body, 0.31869712F, 0.0F, 0.0F);
         this.head = new ModelRenderer(this, 0, 0);
         this.head.setRotationPoint(0.0F, 5.0F, -2.0F);
         this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
         this.leg2a = new ModelRenderer(this, 32, 0);
         this.leg2a.setRotationPoint(-2.0F, 13.0F, 4.0F);
         this.leg2a.addBox(-3.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
         this.setRotateAngle(this.leg2a, -0.59184116F, 0.0F, 0.0F);
         this.leg2c = new ModelRenderer(this, 30, 24);
         this.leg2c.mirror = true;
         this.leg2c.setRotationPoint(-1.0F, 5.0F, -1.0F);
         this.leg2c.addBox(-1.0F, 0.0F, -2.0F, 4, 3, 4, 0.0F);
         this.setRotateAngle(this.leg2c, 0.7285004F, 0.0F, 0.0F);
         this.leg2a.addChild(this.leg2b);
         this.leg1a.addChild(this.leg1b);
         this.body.addChild(this.tail);
         this.head.addChild(this.hat);
         this.leg1b.addChild(this.leg1c);
         this.leg2b.addChild(this.leg2c);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         this.head.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.head.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         float sinn = (float)Math.sin(-AnimationTimer.tick / 10.0);
         float coss = (float)Math.cos(-AnimationTimer.tick / 10.0);
         this.setRotateAngle(this.wing1, 0.5462881F, 0.045553092F, -0.3642502F);
         this.setRotateAngle(this.wing2, 0.5462881F, 0.045553092F, 0.3642502F);
         this.setRotateAngle(this.leg1a, -0.59184116F, 0.0F, 0.0F);
         this.setRotateAngle(this.leg1b, (float) (Math.PI / 3), 0.0F, 0.0F);
         this.setRotateAngle(this.leg1c, 0.7285004F, 0.0F, 0.0F);
         this.wing1.rotateAngleZ += -sinn * 0.5F;
         this.wing1.rotateAngleY += coss * 1.0F;
         this.tail.rotateAngleX = -2.276433F + sinn * 0.3F;
         if (an3 > 89) {
            float anim = MathHelper.clamp((100.0F - an3 + pt) / 10.0F, 0.0F, 1.0F);
            float ft1 = GetMOP.getfromto(anim, 0.0F, 0.3F);
            float ft2 = GetMOP.getfromto(anim, 0.3F, 0.7F);
            float ft3 = GetMOP.getfromto(anim, 0.7F, 1.0F);
            this.wing1.rotateAngleX += ft1 * 0.4F - ft2 * 0.55F + ft3 * 0.15F;
            this.wing1.rotateAngleY += -ft1 * 0.3F + ft2 - ft3 * 0.7F;
            this.leg1a.rotateAngleX += -ft2 * 0.5F + ft3 * 0.5F;
            this.leg1b.rotateAngleX += -ft2 * 0.6F + ft3 * 0.6F;
            this.leg1c.rotateAngleX += -ft2 * 0.8F + ft3 * 0.8F;
         }

         if (this.wing1.rotateAngleY > 1.62F) {
            this.wing1.rotateAngleY = 1.62F;
         }

         this.wing2.rotateAngleX = this.wing1.rotateAngleX;
         this.wing2.rotateAngleY = -this.wing1.rotateAngleY;
         this.wing2.rotateAngleZ = -this.wing1.rotateAngleZ;
         this.leg2a.rotateAngleX = this.leg1a.rotateAngleX;
         this.leg2b.rotateAngleX = this.leg1b.rotateAngleX;
         this.leg2c.rotateAngleX = this.leg1c.rotateAngleX;
         GlStateManager.pushMatrix();
         if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.isPotionActive(PotionEffects.SIREN_SONG)) {
            Random rand = new Random(entity.getEntityId());
            float offX = MathHelper.sin((entity.ticksExisted + pt + rand.nextInt(500)) / (35.0F + rand.nextFloat() * 15.0F));
            float offY = MathHelper.sin((entity.ticksExisted + pt + rand.nextInt(500)) / (35.0F + rand.nextFloat() * 15.0F));
            float offZ = MathHelper.sin((entity.ticksExisted + pt + rand.nextInt(500)) / (35.0F + rand.nextFloat() * 15.0F));
            float power = SirenSong.clientPotionPower / 15.0F;
            GlStateManager.translate(offX * power, offY * power, offZ * power);
         }

         this.leg2a.render(f5);
         this.leg1a.render(f5);
         GlStateManager.enableBlend();
         alphaGlowDisable();
         this.wing2.render(f5);
         this.body.render(f5);
         this.wing1.render(f5);
         this.head.render(f5);
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class TrachymonaModel extends AbstractMobModel {
      public ModelRenderer shape;
      public ModelRenderer shape2;
      public ModelRenderer tent1;
      public ModelRenderer tent2;
      public ModelRenderer tent3;
      public ModelRenderer pod1;
      public ModelRenderer pod2;
      public ModelRenderer projectiles;

      public TrachymonaModel() {
         this.textureWidth = 48;
         this.textureHeight = 32;
         this.tent3 = new ModelRenderer(this, 0, 6);
         this.tent3.setRotationPoint(0.0F, 11.0F, 0.0F);
         this.tent3.addBox(0.0F, -5.0F, -7.0F, 0, 12, 14, 0.0F);
         this.setRotateAngle(this.tent3, 0.0F, (float) (Math.PI / 3), 0.0F);
         this.shape = new ModelRenderer(this, 0, 0);
         this.shape.setRotationPoint(0.0F, 11.0F, 0.0F);
         this.shape.addBox(-4.0F, 0.0F, -4.0F, 8, 5, 8, 0.0F);
         this.projectiles = new ModelRenderer(this, 32, 2);
         this.projectiles.setRotationPoint(0.0F, 15.3F, 0.0F);
         this.projectiles.addBox(0.0F, 0.0F, -8.0F, 0, 8, 6, 0.0F);
         this.setRotateAngle(this.projectiles, 0.13665928F, 0.0F, 0.0F);
         this.pod2 = new ModelRenderer(this, 28, 10);
         this.pod2.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.pod2.addBox(0.0F, 1.0F, -3.0F, 0, 8, 6, 0.0F);
         this.setRotateAngle(this.pod2, 0.0F, (float) (Math.PI / 4), 0.0F);
         this.tent2 = new ModelRenderer(this, 0, 6);
         this.tent2.setRotationPoint(0.0F, 11.0F, 0.0F);
         this.tent2.addBox(0.0F, -5.0F, -7.0F, 0, 12, 14, 0.0F);
         this.setRotateAngle(this.tent2, 0.0F, (float) (-Math.PI / 3), 0.0F);
         this.pod1 = new ModelRenderer(this, 28, 10);
         this.pod1.setRotationPoint(0.0F, 15.0F, 0.0F);
         this.pod1.addBox(0.0F, 1.0F, -3.0F, 0, 8, 6, 0.0F);
         this.setRotateAngle(this.pod1, 0.0F, (float) (-Math.PI / 4), 0.0F);
         this.shape2 = new ModelRenderer(this, 24, 0);
         this.shape2.setRotationPoint(0.0F, 11.0F, 0.0F);
         this.shape2.addBox(-3.0F, -2.0F, -3.0F, 6, 2, 6, 0.0F);
         this.tent1 = new ModelRenderer(this, 0, 6);
         this.tent1.setRotationPoint(0.0F, 11.0F, 0.0F);
         this.tent1.addBox(0.0F, -5.0F, -7.0F, 0, 12, 14, 0.0F);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         boolean blend = Minecraft.getMinecraft().getRenderViewEntity() == null || entity.getDistanceSq(Minecraft.getMinecraft().getRenderViewEntity()) < 1600.0;
         if (blend) {
            GL11.glEnable(3042);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         float vars = 0.0F;
         float var5x = 0.0F;
         if (isAbstractMob) {
            var5x = ((AbstractMob)entity).var5;
            float var4x = ((AbstractMob)entity).var4;
            float var3x = ((AbstractMob)entity).var3;
            vars = (var3x + (var4x - var3x) * pt) * 0.3F;
            this.shape.rotateAngleY = vars;
            this.shape2.rotateAngleY = vars;
            this.pod2.rotateAngleY = vars + (float) (Math.PI / 4);
            this.pod1.rotateAngleY = vars - (float) (Math.PI / 4);
            this.tent1.rotateAngleY = vars;
            this.tent2.rotateAngleY = vars - (float) (Math.PI / 3);
            this.tent3.rotateAngleY = vars + (float) (Math.PI / 3);
         }

         this.shape.render(f5);
         this.shape2.render(f5);
         if (an2 > 80) {
            float ptan2 = 100.0F - an2 + pt;
            light((int)(180.0F * (GetMOP.getfromto(ptan2, 0.0F, 10.0F) - GetMOP.getfromto(ptan2, 10.0F, 19.0F))), true);
         }

         for (int i = 0; i < 9; i++) {
            this.projectiles.rotateAngleY = (float)Math.toRadians(i * 40 + 24) + vars;
            this.projectiles.rotateAngleX = 0.22F - 0.48F * var5x;
            this.projectiles.render(f5);
         }

         this.pod2.render(f5);
         this.pod1.render(f5);
         this.tent1.render(f5);
         this.tent2.render(f5);
         this.tent3.render(f5);
         if (an2 > 80) {
            returnlight();
         }

         if (blend) {
            GL11.glDisable(3042);
         }
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class WizardfishModel extends AbstractMobModel {
      public ModelRenderer tail1;
      public ModelRenderer body;
      public ModelRenderer tail2;
      public ModelRenderer tail3;
      public ModelRenderer fin3;
      public ModelRenderer fin2;
      public ModelRenderer fin;
      public ModelRenderer armfin1;
      public ModelRenderer armfin2;
      public ModelRenderer head;
      public ModelRenderer head2;
      public ModelRenderer fin1a;
      public ModelRenderer fin2a;
      public ModelRenderer branch1;
      public ModelRenderer branch2;

      public WizardfishModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.armfin1 = new ModelRenderer(this, 0, 26);
         this.armfin1.setRotationPoint(3.0F, 1.0F, -5.0F);
         this.armfin1.addBox(0.0F, 0.0F, -1.0F, 0, 5, 5, 0.0F);
         this.setRotateAngle(this.armfin1, 0.0F, 0.0F, -0.7740535F);
         this.branch2 = new ModelRenderer(this, 37, 22);
         this.branch2.setRotationPoint(3.0F, -1.0F, 1.0F);
         this.branch2.addBox(0.0F, -5.0F, 0.0F, 6, 9, 0, 0.0F);
         this.setRotateAngle(this.branch2, -0.091106184F, -0.5009095F, 0.0F);
         this.branch1 = new ModelRenderer(this, 37, 22);
         this.branch1.setRotationPoint(-3.0F, -1.0F, 1.0F);
         this.branch1.addBox(-6.0F, -5.0F, 0.0F, 6, 9, 0, 0.0F);
         this.setRotateAngle(this.branch1, -0.091106184F, 0.5009095F, 0.0F);
         this.tail1 = new ModelRenderer(this, 40, 4);
         this.tail1.setRotationPoint(0.0F, 17.5F, -1.0F);
         this.tail1.addBox(-2.0F, -2.5F, 4.2F, 4, 5, 6, 0.0F);
         this.fin3 = new ModelRenderer(this, 16, 36);
         this.fin3.setRotationPoint(0.0F, 2.5F, 0.0F);
         this.fin3.addBox(0.0F, -1.0F, 0.2F, 0, 5, 6, 0.0F);
         this.fin2 = new ModelRenderer(this, 0, 31);
         this.fin2.setRotationPoint(0.0F, -0.5F, 4.0F);
         this.fin2.addBox(0.0F, -4.0F, 0.2F, 0, 8, 6, 0.0F);
         this.body = new ModelRenderer(this, 0, 16);
         this.body.setRotationPoint(0.0F, 18.0F, 3.0F);
         this.body.addBox(-3.0F, -4.0F, -7.0F, 6, 7, 8, 0.0F);
         this.fin2a = new ModelRenderer(this, 34, 27);
         this.fin2a.setRotationPoint(0.0F, -4.0F, -5.0F);
         this.fin2a.addBox(2.0F, -6.0F, 0.0F, 0, 6, 7, 0.0F);
         this.setRotateAngle(this.fin2a, -0.27314404F, 0.0F, 0.5462881F);
         this.fin1a = new ModelRenderer(this, 34, 27);
         this.fin1a.setRotationPoint(0.0F, -4.0F, -5.0F);
         this.fin1a.addBox(-2.0F, -6.0F, 0.0F, 0, 6, 7, 0.0F);
         this.setRotateAngle(this.fin1a, -0.27314404F, 0.0F, -0.5462881F);
         this.tail2 = new ModelRenderer(this, 28, 10);
         this.tail2.setRotationPoint(0.0F, 0.0F, 10.0F);
         this.tail2.addBox(-1.0F, -2.0F, -0.8F, 2, 4, 6, 0.0F);
         this.head = new ModelRenderer(this, 0, 0);
         this.head.setRotationPoint(0.0F, 0.0F, -6.4F);
         this.head.addBox(-3.5F, -5.0F, -5.0F, 7, 5, 6, 0.0F);
         this.setRotateAngle(this.head, 0.18203785F, 0.0F, 0.0F);
         this.tail3 = new ModelRenderer(this, 30, 0);
         this.tail3.setRotationPoint(0.0F, 0.0F, 5.0F);
         this.tail3.addBox(-0.5F, -1.5F, -0.8F, 1, 2, 7, 0.0F);
         this.armfin2 = new ModelRenderer(this, 0, 26);
         this.armfin2.setRotationPoint(-3.0F, 1.0F, -5.0F);
         this.armfin2.addBox(0.0F, 0.0F, -1.0F, 0, 5, 5, 0.0F);
         this.setRotateAngle(this.armfin2, 0.0F, 0.0F, 0.7740535F);
         this.head2 = new ModelRenderer(this, 14, 31);
         this.head2.setRotationPoint(0.0F, 1.0F, -7.0F);
         this.head2.addBox(-2.5F, -1.0F, -3.7F, 5, 3, 4, 0.0F);
         this.setRotateAngle(this.head2, -0.13665928F, 0.0F, 0.0F);
         this.fin = new ModelRenderer(this, 28, 32);
         this.fin.setRotationPoint(0.0F, -4.0F, -4.0F);
         this.fin.addBox(0.0F, -6.0F, 0.0F, 0, 6, 8, 0.0F);
         this.setRotateAngle(this.fin, -0.27314404F, 0.0F, 0.0F);
         this.body.addChild(this.armfin1);
         this.head.addChild(this.branch2);
         this.head.addChild(this.branch1);
         this.tail2.addChild(this.fin3);
         this.tail3.addChild(this.fin2);
         this.body.addChild(this.fin2a);
         this.body.addChild(this.fin1a);
         this.tail1.addChild(this.tail2);
         this.body.addChild(this.head);
         this.tail2.addChild(this.tail3);
         this.body.addChild(this.armfin2);
         this.body.addChild(this.head2);
         this.body.addChild(this.fin);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         this.head2.rotateAngleX = -0.13665928F;
         if (an1 > 80) {
            float ptan1 = 100.0F - an1 + pt;
            this.head2.rotateAngleX = -0.13665928F + (GetMOP.getfromto(ptan1, 0.0F, 10.0F) - GetMOP.getfromto(ptan1, 10.0F, 19.0F)) * 0.53F;
         }

         this.body.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.body.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.tail1.rotateAngleY = 0.0F;
         this.tail1.rotateAngleX = f4 * 0.003453292F;
         this.tail2.rotateAngleY = f3 * -0.002053292F;
         this.tail2.rotateAngleX = f4 * 0.002053292F;
         this.tail3.rotateAngleY = f3 * -0.002053292F;
         this.tail3.rotateAngleX = f4 * 0.002053292F;
         if (isAbstractMob) {
            float var4x = ((AbstractMob)entity).var4;
            float var3x = ((AbstractMob)entity).var3;
            float vars = var3x + (var4x - var3x) * pt;
            float tailSin = MathHelper.sin(vars / 3.0F);
            this.tail1.rotateAngleY += tailSin * 0.174F;
            this.tail2.rotateAngleY += tailSin * 0.3F;
            this.tail3.rotateAngleY += tailSin * 0.39F;
         }

         float finCos = MathHelper.cos(AnimationTimer.tick / 30.0F) * 0.25F;
         float finSin = MathHelper.sin(AnimationTimer.tick / 30.0F) * 0.25F;
         this.armfin1.rotateAngleY = finCos;
         this.armfin1.rotateAngleX = -0.27314404F - finSin;
         this.armfin2.rotateAngleY = -this.armfin1.rotateAngleY;
         this.armfin2.rotateAngleX = this.armfin1.rotateAngleX;
         this.tail1.render(f5);
         this.body.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }
}
