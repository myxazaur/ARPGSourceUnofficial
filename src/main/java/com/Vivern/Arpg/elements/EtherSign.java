package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.Vivern.Arpg.main.ItemsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class EtherSign extends Item implements IBauble, IRenderBauble {
   public EtherSign() {
      this.setRegistryName("ether_sign");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("ether_sign");
      this.setMaxDamage(2000);
      this.setMaxStackSize(1);
   }

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      if (type == RenderType.BODY) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.13F, 0.45F, -0.13F);
         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
         Helper.rotateIfSneaking(player);
         Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.GROUND);
         GlStateManager.popMatrix();
      }
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.CHARM;
   }

   @Override
   public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
      entityIn.fallDistance = 0.0F;
   }

   @SubscribeEvent
   public static void onLivingHurt(LivingHurtEvent e) {
      if (e.getEntityLiving() instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)e.getEntityLiving();
         IInventory inventory = BaublesApi.getBaubles(player);
         ItemStack stack = inventory.getStackInSlot(6);
         if (stack.getItem() == ItemsRegister.ETHERSIGN && stack.getItemDamage() < 2000 && e.getSource() == DamageSource.FALL) {
            e.setCanceled(true);
            inventory.getStackInSlot(6).damageItem(1, player);
         }
      }
   }
}
