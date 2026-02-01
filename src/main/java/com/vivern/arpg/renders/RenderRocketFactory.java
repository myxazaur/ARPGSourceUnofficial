package com.vivern.arpg.renders;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderRocketFactory<ModelT extends ModelBase> implements IRenderFactory {
   public final String reslocation;
   private Class myClass;
   private float scale;
   private boolean opaq;
   public float melting = 0.0F;
   public boolean checkAdvanced = false;

   public RenderRocketFactory(String location, Class ModelT, float scale, boolean opaq) {
      this.reslocation = location;
      this.myClass = ModelT;
      this.scale = scale;
      this.opaq = opaq;
   }

   public RenderRocketFactory(String location, Class ModelT, float scale, boolean opaq, float melting, boolean checkAdvanced) {
      this.reslocation = location;
      this.myClass = ModelT;
      this.scale = scale;
      this.opaq = opaq;
      this.melting = melting;
      this.checkAdvanced = checkAdvanced;
   }

   public Render createRenderFor(RenderManager manager) {
      return new RenderRocket(manager, new ResourceLocation(this.reslocation), this.myClass, this.scale, this.opaq, this.melting, this.checkAdvanced);
   }
}
