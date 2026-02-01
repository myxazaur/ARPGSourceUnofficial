package com.vivern.arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.vivern.arpg.elements.models.AimLensModel;
import com.vivern.arpg.main.ColorConverters;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class AimLens extends Item implements IBauble, IRenderBauble {
   public static AimLensModel almodel = new AimLensModel();
   static ResourceLocation tex = new ResourceLocation("arpg:textures/aim_lens_model_tex.png");

   public AimLens() {
      this.setRegistryName("aim_lens");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("aim_lens");
      this.setMaxStackSize(1);
   }

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      if (type == RenderType.HEAD) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 0.03F, 0.0F);
         GlStateManager.scale(0.065F, 0.065F, 0.065F);
         GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
         Helper.rotateIfSneaking(player);
         Minecraft.getMinecraft().renderEngine.bindTexture(tex);
         Vec3d color;
         if (player.getTeam() != null) {
            color = ColorConverters.getTeamColor(player.getTeam().getColor());
         } else {
            color = new Vec3d(1.0, 0.3, 0.4);
         }

         almodel.render(player, (float)color.x, (float)color.y, (float)color.z, 1.0F, 1.0F, 1.0F);
         GlStateManager.popMatrix();
      }
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.HEAD;
   }
}
