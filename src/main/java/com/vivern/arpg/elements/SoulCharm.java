package com.vivern.arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.vivern.arpg.main.IAttributedBauble;
import com.vivern.arpg.main.PropertiesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SoulCharm extends Item implements IBauble, IAttributedBauble, IRenderBauble {
   public SoulCharm() {
      this.setRegistryName("soul_charm");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("soul_charm");
      this.setMaxStackSize(1);
   }

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      if (type == RenderType.BODY) {
         GlStateManager.pushMatrix();
         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
         Helper.rotateIfSneaking(player);
         Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
         GlStateManager.popMatrix();
      }
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.AMULET;
   }

   @Override
   public IAttribute getAttribute() {
      return PropertiesRegistry.LEADERSHIP;
   }

   @Override
   public double value() {
      return 5.0;
   }

   @Override
   public int operation() {
      return 0;
   }

   @Override
   public String itemName() {
      return "soul_charm";
   }
}
