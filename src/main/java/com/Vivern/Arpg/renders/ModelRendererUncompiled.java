//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class ModelRendererUncompiled extends ModelRenderer {
   public int light = 0;
   public boolean addlight = false;
   public boolean uselight;
   public float scaleFact = 1.0F;

   public ModelRendererUncompiled(ModelBase model, int texOffX, int texOffY, int light, boolean addlight) {
      super(model, texOffX, texOffY);
      this.light = light;
      this.addlight = addlight;
      this.uselight = true;
   }

   public ModelRendererUncompiled(ModelBase model, int texOffX, int texOffY) {
      super(model, texOffX, texOffY);
      this.uselight = false;
   }

   @SideOnly(Side.CLIENT)
   public void render(float scale) {
      if (!this.isHidden && this.showModel) {
         float lbX = 0.0F;
         float lbY = 0.0F;
         if (this.uselight) {
            lbX = OpenGlHelper.lastBrightnessX;
            lbY = OpenGlHelper.lastBrightnessY;
            GL11.glDisable(2896);
            if (this.addlight) {
               OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240.0F, this.light + lbX), Math.min(240.0F, this.light + lbY));
            } else {
               OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240, this.light), Math.min(240, this.light));
            }
         }

         GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
         if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
            if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
               this.renderUncompiled(scale * this.scaleFact);
               if (this.childModels != null) {
                  for (int k = 0; k < this.childModels.size(); k++) {
                     ((ModelRenderer)this.childModels.get(k)).render(scale);
                  }
               }
            } else {
               GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
               this.renderUncompiled(scale * this.scaleFact);
               if (this.childModels != null) {
                  for (int j = 0; j < this.childModels.size(); j++) {
                     ((ModelRenderer)this.childModels.get(j)).render(scale);
                  }
               }

               GlStateManager.translate(-this.rotationPointX * scale, -this.rotationPointY * scale, -this.rotationPointZ * scale);
            }
         } else {
            GlStateManager.pushMatrix();
            GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
            if (this.rotateAngleZ != 0.0F) {
               GlStateManager.rotate(this.rotateAngleZ * (180.0F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
            }

            if (this.rotateAngleY != 0.0F) {
               GlStateManager.rotate(this.rotateAngleY * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (this.rotateAngleX != 0.0F) {
               GlStateManager.rotate(this.rotateAngleX * (180.0F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
            }

            this.renderUncompiled(scale * this.scaleFact);
            if (this.childModels != null) {
               for (int i = 0; i < this.childModels.size(); i++) {
                  ((ModelRenderer)this.childModels.get(i)).render(scale);
               }
            }

            GlStateManager.popMatrix();
         }

         GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
         if (this.uselight) {
            GL11.glEnable(2896);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void renderWithRotation(float scale) {
      if (!this.isHidden && this.showModel) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
         if (this.rotateAngleY != 0.0F) {
            GlStateManager.rotate(this.rotateAngleY * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
         }

         if (this.rotateAngleX != 0.0F) {
            GlStateManager.rotate(this.rotateAngleX * (180.0F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
         }

         if (this.rotateAngleZ != 0.0F) {
            GlStateManager.rotate(this.rotateAngleZ * (180.0F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
         }

         this.renderUncompiled(scale);
         GlStateManager.popMatrix();
      }
   }

   @SideOnly(Side.CLIENT)
   public void postRender(float scale) {
      if (!this.isHidden && this.showModel) {
         if (this.rotateAngleX != 0.0F || this.rotateAngleY != 0.0F || this.rotateAngleZ != 0.0F) {
            GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
            if (this.rotateAngleZ != 0.0F) {
               GlStateManager.rotate(this.rotateAngleZ * (180.0F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
            }

            if (this.rotateAngleY != 0.0F) {
               GlStateManager.rotate(this.rotateAngleY * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (this.rotateAngleX != 0.0F) {
               GlStateManager.rotate(this.rotateAngleX * (180.0F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
            }
         } else if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
            GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void renderUncompiled(float scale) {
      BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

      for (int i = 0; i < this.cubeList.size(); i++) {
         ((ModelBox)this.cubeList.get(i)).render(bufferbuilder, scale);
      }
   }
}
