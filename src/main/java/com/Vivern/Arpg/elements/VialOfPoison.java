//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.Vivern.Arpg.elements.armor.IItemDamaging;
import com.Vivern.Arpg.elements.armor.IItemHurted;
import com.Vivern.Arpg.main.IAttributedBauble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class VialOfPoison extends Item implements IBauble, IAttributedBauble, IItemDamaging, IItemHurted, IRenderBauble {
   public VialOfPoison() {
      this.setRegistryName("vial_of_poison");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("vial_of_poison");
      this.setMaxDamage(1450);
      this.setMaxStackSize(1);
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.AMULET;
   }

   @Override
   public IAttribute getAttribute() {
      return SharedMonsterAttributes.MAX_HEALTH;
   }

   @Override
   public double value() {
      return 4.0;
   }

   @Override
   public int operation() {
      return 0;
   }

   @Override
   public String itemName() {
      return "vial_of_poison";
   }

   @Override
   public boolean useMultimap() {
      return false;
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
   public float onCauseDamageWithItem(float hurtdamage, ItemStack stack, EntityPlayer playerattacker, EntityLivingBase target, DamageSource source) {
      PotionEffect baff = new PotionEffect(MobEffects.POISON, 230);
      target.addPotionEffect(baff);
      stack.damageItem(1, playerattacker);
      return hurtdamage;
   }

   @Override
   public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
      if (source.getTrueSource() instanceof EntityLivingBase) {
         PotionEffect baff = new PotionEffect(MobEffects.POISON, 230);
         ((EntityLivingBase)source.getTrueSource()).addPotionEffect(baff);
         stack.damageItem(1, player);
      }

      return hurtdamage;
   }
}
