package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.ModelRendererGlow;
import com.Vivern.Arpg.tileentity.TileAssemblyTable;
import java.util.Random;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AssemblyTableModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer manA;
   public ModelRenderer shape2;
   public ModelRenderer shapeGlow;
   public ModelRenderer manB;
   public ModelRenderer manC;
   public ModelRenderer manD;
   public ModelRenderer manE;
   public ModelRenderer tool1;
   public ModelRenderer tool2;
   public ModelRenderer tool3;
   public ModelRenderer tool1a;
   public ModelRenderer tool2a;
   public ModelRenderer tool2b;
   public ModelRenderer tool2c;
   public ModelRenderer tool2d;
   public ModelRenderer tool3a;

   public AssemblyTableModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.tool2 = new ModelRenderer(this, 0, 13);
      this.tool2.setRotationPoint(0.0F, -4.5F, 0.5F);
      this.tool2.addBox(-0.5F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
      this.manA = new ModelRenderer(this, 0, 27);
      this.manA.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.manA.addBox(-2.0F, -8.0F, 6.0F, 4, 18, 4, -0.01F);
      this.tool3 = new ModelRenderer(this, 0, 5);
      this.tool3.setRotationPoint(0.0F, -4.5F, 0.5F);
      this.tool3.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
      this.shape2 = new ModelRenderer(this, 16, 10);
      this.shape2.setRotationPoint(0.0F, 11.0F, 0.0F);
      this.shape2.addBox(-6.0F, 0.0F, -6.0F, 12, 12, 12, -0.01F);
      this.tool2a = new ModelRenderer(this, 0, 9);
      this.tool2a.setRotationPoint(0.5F, -0.5F, 0.0F);
      this.tool2a.addBox(0.0F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
      this.tool2b = new ModelRenderer(this, 0, 9);
      this.tool2b.setRotationPoint(-0.5F, -0.5F, 0.0F);
      this.tool2b.addBox(-1.0F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
      this.manC = new ModelRenderer(this, 11, 0);
      this.manC.setRotationPoint(0.0F, -7.5F, 1.0F);
      this.manC.addBox(-0.5F, -7.0F, -0.5F, 1, 7, 1, 0.0F);
      this.setRotateAngle(this.manC, 1.2292354F, 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 34);
      this.shape1.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shape1.addBox(-8.0F, 0.0F, -8.0F, 16, 14, 16, -0.01F);
      this.manE = new ModelRenderer(this, 17, 0);
      this.manE.setRotationPoint(0.0F, -6.5F, 0.0F);
      this.manE.addBox(-1.0F, -4.5F, -0.5F, 2, 6, 2, 0.0F);
      this.setRotateAngle(this.manE, 1.3658947F, 0.0F, 0.0F);
      this.tool1a = new ModelRenderer(this, 4, 0);
      this.tool1a.setRotationPoint(0.0F, -2.8F, 0.0F);
      this.tool1a.addBox(-0.5F, 0.0F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.tool1a, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.tool2d = new ModelRenderer(this, 0, 13);
      this.tool2d.setRotationPoint(-0.5F, -3.0F, 0.0F);
      this.tool2d.addBox(0.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
      this.tool3a = new ModelRenderer(this, 11, 7);
      this.tool3a.setRotationPoint(0.0F, -2.0F, 0.0F);
      this.tool3a.addBox(0.0F, -1.5F, -1.5F, 0, 3, 3, 0.0F);
      this.tool2c = new ModelRenderer(this, 0, 13);
      this.tool2c.setRotationPoint(-0.5F, -3.0F, 0.0F);
      this.tool2c.addBox(0.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
      this.manB = new ModelRenderer(this, 0, 15);
      this.manB.setRotationPoint(0.0F, -7.5F, 8.0F);
      this.manB.addBox(-1.0F, -8.0F, -1.5F, 2, 8, 3, 0.0F);
      this.setRotateAngle(this.manB, 0.5462881F, 0.0F, 0.0F);
      this.manD = new ModelRenderer(this, 11, 0);
      this.manD.setRotationPoint(0.0F, -7.0F, -1.0F);
      this.manD.addBox(-0.5F, -6.2F, -0.5F, 1, 7, 1, 0.0F);
      this.setRotateAngle(this.manD, 1.2292354F, 0.0F, 0.0F);
      this.shapeGlow = new ModelRendererGlow(this, 20, 50, 60, true);
      this.shapeGlow.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shapeGlow.addBox(-3.0F, 1.0F, -8.3F, 6, 3, 1, -0.01F);
      this.tool1 = new ModelRenderer(this, 0, 0);
      this.tool1.setRotationPoint(0.0F, -4.5F, 0.5F);
      this.tool1.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
      this.manE.addChild(this.tool2);
      this.manE.addChild(this.tool3);
      this.tool2.addChild(this.tool2a);
      this.tool2.addChild(this.tool2b);
      this.manB.addChild(this.manC);
      this.manC.addChild(this.manE);
      this.tool1.addChild(this.tool1a);
      this.tool2b.addChild(this.tool2d);
      this.tool3.addChild(this.tool3a);
      this.tool2a.addChild(this.tool2c);
      this.manA.addChild(this.manB);
      this.manB.addChild(this.manD);
      this.manE.addChild(this.tool1);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      int tool = (int)f;
      if (tool != 0) {
         this.tool1.isHidden = tool != 1;
         this.tool2.isHidden = tool != 2;
         this.tool3.isHidden = tool != 3;
         this.manA.rotateAngleY = 1.570796F * (tool - 2);
         this.manA.render(f5);
      } else {
         this.shape2.render(f5);
         this.shape1.render(f5);
         this.shapeGlow.render(f5);
      }
   }

   public void setManipulatorAngle(float progress) {
      this.manB.rotateAngleX = GetMOP.partial(0.5462881F, -0.45F, progress);
      this.manC.rotateAngleX = GetMOP.partial(1.2292354F, 0.728F, progress);
      this.manD.rotateAngleX = GetMOP.partial(1.2292354F, 0.728F, progress);
      this.manE.rotateAngleX = GetMOP.partial(1.3658947F, 1.68F, progress);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public static class AssemblyManipulatorAnimation {
      public float targetangleY1;
      public float targetangleY2;
      public int workTime = 30;
      public int prevworkTime = 30;
      public int maxworkTime = 30;
      public int tool;

      public AssemblyManipulatorAnimation(int tool) {
         this.tool = tool;
      }

      public void updateAnimation() {
         this.prevworkTime = this.workTime;
         if (this.workTime < this.maxworkTime) {
            this.workTime++;
         }
      }

      public void setAnimation(AssemblyTableModel model, float partialTicks) {
         float ft1 = GetMOP.getfromto(GetMOP.partial((float)this.workTime, (float)this.prevworkTime, partialTicks), 0.0F, (float)this.maxworkTime);
         float ft2 = GetMOP.getfromto(ft1, 0.0F, 0.05F);
         float ft3 = GetMOP.getfromto(ft1, 0.1F, 0.15F) - GetMOP.getfromto(ft1, 0.4F, 0.45F) * 0.5F + GetMOP.getfromto(ft1, 0.6F, 0.65F) * 0.5F;
         float ft5 = GetMOP.getfromto(ft1, 0.5F, 0.55F);
         float ft7 = GetMOP.getfromto(ft1, 0.9F, 1.0F);
         model.manB.rotateAngleY = this.targetangleY1 * (ft2 - ft5) + this.targetangleY2 * (ft5 - ft7);
         model.setManipulatorAngle(ft3 - ft7);
         if (this.tool == 1) {
            float ft4 = GetMOP.getfromto(ft1, 0.15F, 0.4F);
            float ft6 = GetMOP.getfromto(ft1, 0.65F, 0.9F);
            model.tool1.rotateAngleY = 6.283185F * ft4 + 6.283185F * ft6;
         }

         if (this.tool == 2) {
            float ft4 = GetMOP.getfromto(ft1, 0.2F, 0.25F);
            float ft6 = GetMOP.getfromto(ft1, 0.7F, 0.75F);
            float ft8 = GetMOP.getfromto(ft1, 0.85F, 1.0F);
            model.tool2a.rotateAngleZ = 0.54F * (ft2 - ft4) + 0.54F * (ft6 - ft7);
            model.tool2b.rotateAngleZ = -model.tool2a.rotateAngleZ;
            model.tool2.rotateAngleY = -3.141593F * ft8;
         }

         if (this.tool == 3) {
            float ft4 = GetMOP.getfromto(ft1, 0.15F, 0.4F);
            float ft6 = GetMOP.getfromto(ft1, 0.65F, 0.9F);
            model.tool3a.rotateAngleX = 37.69911F * ft4 + 37.69911F * ft6;
         }
      }

      public void startWorking(Random rand, TileAssemblyTable table) {
         this.targetangleY1 = (rand.nextFloat() - 0.5F) * 1.25F;
         this.targetangleY2 = (rand.nextFloat() - 0.5F) * 1.25F;
         this.workTime = 0;
         this.prevworkTime = 0;
         this.maxworkTime = 30 + rand.nextInt(20);
         if (this.tool == 1) {
            table.playsound(Sounds.assembly_table_sd, 40, 20, this.maxworkTime);
         }

         if (this.tool == 2) {
            table.playsound(Sounds.assembly_table_cl, 40, 20, this.maxworkTime);
         }

         if (this.tool == 3) {
            table.playsound(Sounds.assembly_table_sa, 40, 20, this.maxworkTime);
         }
      }

      public boolean isWorkingNow() {
         float ft1 = GetMOP.getfromto((float)this.workTime, 0.0F, (float)this.maxworkTime);
         return ft1 < 0.8F;
      }
   }
}
