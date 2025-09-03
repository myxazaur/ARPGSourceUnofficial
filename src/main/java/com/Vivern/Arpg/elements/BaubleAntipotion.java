//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.Vivern.Arpg.elements.armor.IItemHurted;
import com.Vivern.Arpg.renders.HandBaubleRender;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.world.World;

public class BaubleAntipotion extends Item implements IBauble, IRenderBauble {
   public BaubleType btype;
   public Potion[] potion;
   public boolean removeFire = false;
   public String[] text;
   public boolean render = false;
   public int rendertype;

   public BaubleAntipotion(String name, CreativeTabs tab, Potion[] potion, BaubleType btype, boolean removeFire, String[] text) {
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxStackSize(1);
      this.btype = btype;
      this.potion = potion;
      this.removeFire = removeFire;
      this.text = text;
   }

   public BaubleAntipotion(String name, CreativeTabs tab, Potion potion, BaubleType btype, String text) {
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxStackSize(1);
      this.btype = btype;
      this.potion = new Potion[]{potion};
      this.text = new String[]{text};
   }

   public BaubleAntipotion setRender(int rendertype) {
      this.rendertype = rendertype;
      this.render = true;
      return this;
   }

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      if (this.render) {
         if (this.rendertype == 1 && type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }

         if (this.rendertype == 2 && type == RenderType.HEAD) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }

         if (this.rendertype == 3 && type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.RIGHT);
            GlStateManager.popMatrix();
         }

         if (this.rendertype == 4 && type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.LEFT);
            GlStateManager.popMatrix();
         }

         if (this.rendertype == 5 && type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            if (BaublesApi.getBaubles(player).getStackInSlot(1) == stack) {
               HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.RIGHT, TransformType.THIRD_PERSON_RIGHT_HAND);
            } else if (BaublesApi.getBaubles(player).getStackInSlot(2) == stack) {
               HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.LEFT, TransformType.THIRD_PERSON_LEFT_HAND);
            }

            GlStateManager.popMatrix();
         }
      }
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return this.btype;
   }

   @Override
   public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
      if (this.potion != null) {
         for (Potion p : this.potion) {
            player.removePotionEffect(p);
         }
      }

      if (this.removeFire) {
         player.extinguish();
      }
   }

   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      for (String s : this.text) {
         tooltip.add(s);
      }

      super.addInformation(stack, worldIn, tooltip, flagIn);
   }

   public static class BaubleAntipotionFallless extends BaubleAntipotion implements IItemHurted {
      public BaubleAntipotionFallless(String name, CreativeTabs tab, Potion[] potion, BaubleType btype, boolean removeFire, String[] text) {
         super(name, tab, potion, btype, removeFire, text);
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
         super.onWornTick(itemstack, player);
         player.fallDistance = 0.0F;
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         return source == DamageSource.FALL ? 0.0F : hurtdamage;
      }

      @Override
      public boolean cancelOnNull() {
         return true;
      }
   }
}
