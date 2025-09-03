package com.Vivern.Arpg.renders;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class BlockEntityFactory implements IRenderFactory {
   public float scaleX;
   public float scaleY;
   public float scaleZ;
   public int light;
   public ResourceLocation texture;
   public float Red;
   public float Green;
   public float Blue;
   public boolean opa;
   public float rotateX;
   public float rotateY;
   public float rotateZ;
   public float rotateSpeed;
   public boolean rotateRand;

   public BlockEntityFactory(
      ResourceLocation texture,
      float scaleX,
      float scaleY,
      float scaleZ,
      int light,
      float Red,
      float Green,
      float Blue,
      boolean opa,
      float rotateX,
      float rotateY,
      float rotateZ,
      float rotateSpeed,
      boolean rotateRand
   ) {
      this.scaleX = scaleX;
      this.scaleY = scaleY;
      this.scaleZ = scaleZ;
      this.light = light;
      this.texture = texture;
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
      this.opa = opa;
      this.rotateSpeed = rotateSpeed;
      this.rotateRand = rotateRand;
      this.rotateX = rotateX;
      this.rotateY = rotateY;
      this.rotateZ = rotateZ;
   }

   public Render createRenderFor(RenderManager manager) {
      return new RenderBlockEntity(
         manager,
         this.texture,
         this.scaleX,
         this.scaleY,
         this.scaleZ,
         this.light,
         this.Red,
         this.Green,
         this.Blue,
         this.opa,
         this.rotateX,
         this.rotateY,
         this.rotateZ,
         this.rotateSpeed,
         this.rotateRand
      );
   }
}
