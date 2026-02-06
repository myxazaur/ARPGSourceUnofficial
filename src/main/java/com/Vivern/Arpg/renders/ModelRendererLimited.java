package com.Vivern.Arpg.renders;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class ModelRendererLimited extends ModelRenderer {
   public int light = 0;
   public boolean addlight = false;
   public boolean compiled2;
   public int displayList2;
   public int avaliables;
   public int texxOffsetX;
   public int texxOffsetY;
   public boolean uselight;
   public float colorR;
   public float colorG;
   public float colorB;
   public float colorA;
   public boolean useColor;

   public ModelRendererLimited(ModelBase model, int texOffX, int texOffY) {
      this(model, texOffX, texOffY, false, 0, false, true, true, true, true, true, true);
   }

   public ModelRendererLimited(ModelBase model, int texOffX, int texOffY, boolean right, boolean left, boolean up, boolean down, boolean face, boolean back) {
      this(model, texOffX, texOffY, false, 0, false, right, left, up, down, face, back);
   }

   public ModelRendererLimited(ModelBase model, boolean right, boolean left, boolean up, boolean down, boolean face, boolean back) {
      super(model);
      this.avaliables = getAvaliables(right, left, up, down, face, back);
   }

   public ModelRendererLimited(
      ModelBase model,
      int texOffX,
      int texOffY,
      boolean uselight,
      int light,
      boolean addlight,
      boolean right,
      boolean left,
      boolean up,
      boolean down,
      boolean face,
      boolean back
   ) {
      super(model, texOffX, texOffY);
      this.light = light;
      this.addlight = addlight;
      this.uselight = uselight;
      this.avaliables = getAvaliables(right, left, up, down, face, back);
   }

   public static int getAvaliables(boolean right, boolean left, boolean up, boolean down, boolean face, boolean back) {
      int avaliables = 0;
      if (right) {
         avaliables |= 1;
      }

      if (left) {
         avaliables |= 2;
      }

      if (up) {
         avaliables |= 4;
      }

      if (down) {
         avaliables |= 8;
      }

      if (face) {
         avaliables |= 16;
      }

      if (back) {
         avaliables |= 32;
      }

      return avaliables;
   }

   public ModelRendererLimited setColor(float r, float g, float b, float a) {
      this.colorR = r;
      this.colorG = g;
      this.colorB = b;
      this.colorA = a;
      this.useColor = true;
      return this;
   }

   public ModelRenderer setTextureOffset(int x, int y) {
      this.texxOffsetX = x;
      this.texxOffsetY = y;
      return super.setTextureOffset(x, y);
   }

   public ModelRenderer addBox(float offX, float offY, float offZ, int width, int height, int depth) {
      this.cubeList
         .add(new ModelBoxLimited(this, this.texxOffsetX, this.texxOffsetY, offX, offY, offZ, width, height, depth, 0.0F, this.avaliables));
      return this;
   }

   public ModelRenderer addBox(float offX, float offY, float offZ, int width, int height, int depth, boolean mirrored) {
      this.cubeList
         .add(
            new ModelBoxLimited(
               this, this.texxOffsetX, this.texxOffsetY, offX, offY, offZ, width, height, depth, 0.0F, mirrored, this.avaliables
            )
         );
      return this;
   }

   public void addBox(float offX, float offY, float offZ, int width, int height, int depth, float scaleFactor) {
      this.cubeList
         .add(
            new ModelBoxLimited(
               this, this.texxOffsetX, this.texxOffsetY, offX, offY, offZ, width, height, depth, scaleFactor, this.avaliables
            )
         );
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

         if (this.useColor) {
            GlStateManager.color(this.colorR, this.colorG, this.colorB, this.colorA);
         }

         if (!this.compiled2) {
            this.compileDisplayList(scale);
         }

         GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
         if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
            if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
               GlStateManager.callList(this.displayList2);
               if (this.childModels != null) {
                  for (int k = 0; k < this.childModels.size(); k++) {
                     ((ModelRenderer)this.childModels.get(k)).render(scale);
                  }
               }
            } else {
               GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
               GlStateManager.callList(this.displayList2);
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

            GlStateManager.callList(this.displayList2);
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

         if (this.useColor) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void renderWithRotation(float scale) {
      if (!this.isHidden && this.showModel) {
         if (!this.compiled2) {
            this.compileDisplayList(scale);
         }

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

         GlStateManager.callList(this.displayList2);
         GlStateManager.popMatrix();
      }
   }

   @SideOnly(Side.CLIENT)
   public void postRender(float scale) {
      if (!this.isHidden && this.showModel) {
         if (!this.compiled2) {
            this.compileDisplayList(scale);
         }

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
   private void compileDisplayList(float scale) {
      this.displayList2 = GLAllocation.generateDisplayLists(1);
      GlStateManager.glNewList(this.displayList2, 4864);
      BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

      for (int i = 0; i < this.cubeList.size(); i++) {
         ((ModelBox)this.cubeList.get(i)).render(bufferbuilder, scale);
      }

      GlStateManager.glEndList();
      this.compiled2 = true;
   }

   public static class ModelBoxLimited extends ModelBox {
      public TexturedQuad[] quadList2;
      public int availables;

      public ModelBoxLimited(ModelRenderer renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta, int availables) {
         this(renderer, texU, texV, x, y, z, dx, dy, dz, delta, renderer.mirror, availables);
      }

      public ModelBoxLimited(
         ModelRenderer renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta, boolean mirror, int availables
      ) {
         super(renderer, texU, texV, x, y, z, dx, dy, dz, delta, mirror);
         PositionTextureVertex[] vertexPositions = new PositionTextureVertex[8];
         this.quadList2 = new TexturedQuad[6];
         float f = x + dx;
         float f1 = y + dy;
         float f2 = z + dz;
         x -= delta;
         y -= delta;
         z -= delta;
         f += delta;
         f1 += delta;
         f2 += delta;
         if (mirror) {
            float f3 = f;
            f = x;
            x = f3;
         }

         PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);
         PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, y, z, 0.0F, 8.0F);
         PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, z, 8.0F, 8.0F);
         PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(x, f1, z, 8.0F, 0.0F);
         PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(x, y, f2, 0.0F, 0.0F);
         PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, y, f2, 0.0F, 8.0F);
         PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
         PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(x, f1, f2, 8.0F, 0.0F);
         this.quadList2[0] = new TexturedQuad(
            new PositionTextureVertex[]{positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5},
            texU + dz + dx,
            texV + dz,
            texU + dz + dx + dz,
            texV + dz + dy,
            renderer.textureWidth,
            renderer.textureHeight
         );
         this.quadList2[1] = new TexturedQuad(
            new PositionTextureVertex[]{positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2},
            texU,
            texV + dz,
            texU + dz,
            texV + dz + dy,
            renderer.textureWidth,
            renderer.textureHeight
         );
         this.quadList2[2] = new TexturedQuad(
            new PositionTextureVertex[]{positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex},
            texU + dz,
            texV,
            texU + dz + dx,
            texV + dz,
            renderer.textureWidth,
            renderer.textureHeight
         );
         this.quadList2[3] = new TexturedQuad(
            new PositionTextureVertex[]{positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5},
            texU + dz + dx,
            texV + dz,
            texU + dz + dx + dx,
            texV,
            renderer.textureWidth,
            renderer.textureHeight
         );
         this.quadList2[4] = new TexturedQuad(
            new PositionTextureVertex[]{positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1},
            texU + dz,
            texV + dz,
            texU + dz + dx,
            texV + dz + dy,
            renderer.textureWidth,
            renderer.textureHeight
         );
         this.quadList2[5] = new TexturedQuad(
            new PositionTextureVertex[]{positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6},
            texU + dz + dx + dz,
            texV + dz,
            texU + dz + dx + dz + dx,
            texV + dz + dy,
            renderer.textureWidth,
            renderer.textureHeight
         );
         if (mirror) {
            for (TexturedQuad texturedquad : this.quadList2) {
               texturedquad.flipFace();
            }
         }

         this.availables = availables;
      }

      @SideOnly(Side.CLIENT)
      public void render(BufferBuilder renderer, float scale) {
         int i = 0;

         for (TexturedQuad texturedquad : this.quadList2) {
            if ((this.availables & 1 << i) > 0) {
               texturedquad.draw(renderer, scale);
            }

            i++;
         }
      }
   }
}
