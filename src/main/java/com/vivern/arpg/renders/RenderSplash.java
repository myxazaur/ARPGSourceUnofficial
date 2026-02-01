package com.vivern.arpg.renders;

import com.vivern.arpg.main.Render2D;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSplash implements IRenderFactory {
   public final float scaleX;
   public final int FrameCount;
   public final float scaleZ;
   public final String Res;
   public final float rotationx;
   public final float rotationy;
   public final float rotationz;
   public final boolean opaq;
   public int light;
   public DestFactor dstFactor;
   public final boolean checkAdv;
   public float red = 1.0F;
   public float green = 1.0F;
   public float blue = 1.0F;

   public RenderSplash(
      String resource,
      float scaleX,
      int FrameCount,
      float scaleY,
      float rotationx,
      float rotationy,
      float rotationz,
      boolean opaq,
      int light,
      DestFactor dstFactor
   ) {
      this.Res = resource;
      this.scaleX = scaleX;
      this.FrameCount = FrameCount;
      this.scaleZ = scaleY;
      this.rotationx = rotationx;
      this.rotationy = rotationy;
      this.rotationz = rotationz;
      this.opaq = opaq;
      this.light = light;
      this.dstFactor = dstFactor;
      this.checkAdv = false;
   }

   public RenderSplash(
      String resource,
      float scaleX,
      int FrameCount,
      float scaleY,
      float rotationx,
      float rotationy,
      float rotationz,
      boolean opaq,
      int light,
      DestFactor dstFactor,
      boolean checkAdvanced
   ) {
      this.Res = resource;
      this.scaleX = scaleX;
      this.FrameCount = FrameCount;
      this.scaleZ = scaleY;
      this.rotationx = rotationx;
      this.rotationy = rotationy;
      this.rotationz = rotationz;
      this.opaq = opaq;
      this.light = light;
      this.dstFactor = dstFactor;
      this.checkAdv = checkAdvanced;
   }

   public RenderSplash setColor(float r, float g, float b) {
      this.red = r;
      this.green = g;
      this.blue = b;
      return this;
   }

   public Render createRenderFor(RenderManager manager) {
      return new Render2D(
         manager,
         new ResourceLocation(this.Res),
         this.scaleX,
         this.FrameCount,
         this.scaleZ,
         this.rotationx,
         this.rotationy,
         this.rotationz,
         this.opaq,
         this.light,
         this.dstFactor,
         this.checkAdv,
         this.red,
         this.green,
         this.blue
      );
   }
}
