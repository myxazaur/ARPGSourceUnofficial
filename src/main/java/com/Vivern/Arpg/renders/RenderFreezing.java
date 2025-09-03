package com.Vivern.Arpg.renders;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFreezing extends RenderLiving<EntityLiving> {
   private static final ResourceLocation textur = new ResourceLocation("arpg:textures/sobig-4.png");

   public RenderFreezing(RenderManager rendermanagerIn, ModelBase modelbaseIn) {
      super(rendermanagerIn, modelbaseIn, 0.0F);
   }

   protected ResourceLocation getEntityTexture(EntityLiving entity) {
      return textur;
   }
}
