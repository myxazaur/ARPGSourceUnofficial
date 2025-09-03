//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.elements.armor.IItemHurted;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class HolographicShield extends ItemWeapon implements IItemHurted {
   private static final UUID[] SHIELD_MODIFIERS = new UUID[]{
      UUID.fromString("845FB26B-F624-465F-3C4F-6020A9A49B6B"),
      UUID.fromString("E8499B04-0E55-2136-BA29-64469C734C0C"),
      UUID.fromString("9F3E436E-C458-4444-3365-14256304D48D"),
      UUID.fromString("2AE3C246-FDF3-4E67-B334-69FD380CC150")
   };

   public HolographicShield() {
      this.setRegistryName("holographic_shield");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("holographic_shield");
      this.setMaxDamage(3500);
      this.setMaxStackSize(1);
   }

   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack itemstack) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      if (IWeapon.canShoot(itemstack)) {
         if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(SHIELD_MODIFIERS[0], "Shield knockrez modifier", 0.4, 0));
            multimap.put(PropertiesRegistry.ARMOR_PROTECTION.getName(), new AttributeModifier(SHIELD_MODIFIERS[1], "Shield protection modifier", 3.0, 0));
         }

         if (equipmentSlot == EntityEquipmentSlot.OFFHAND) {
            multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(SHIELD_MODIFIERS[2], "Shield knockrez modifier", 0.4, 0));
            multimap.put(PropertiesRegistry.ARMOR_PROTECTION.getName(), new AttributeModifier(SHIELD_MODIFIERS[3], "Shield protection modifier", 3.0, 0));
         }
      }

      return multimap;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!worldIn.isRemote) {
         this.setCanShoot(itemstack, entityIn);
      }
   }

   @Override
   public boolean autoReload(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean hasZoom(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 60 - rapidity * 10;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      return 0;
   }

   @Override
   public float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.0F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
      if (source != DamageSource.DROWN
         && source != DamageSource.ON_FIRE
         && source != DamageSource.MAGIC
         && source != DamageSource.OUT_OF_WORLD
         && source != DamageSource.STARVE
         && source != DamageSource.WITHER
         && source != DamageSource.FALL) {
         if (IWeapon.canShoot(stack)) {
            stack.damageItem(1, player);
            if (!player.getCooldownTracker().hasCooldown(stack.getItem())) {
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack) * 2;
               player.getCooldownTracker().setCooldown(stack.getItem(), this.getCooldownTime(stack));
               if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityLivingBase && source.getTrueSource().getDistance(player) < 10.0F) {
                  source.getTrueSource().attackEntityFrom(DamageSource.causePlayerDamage(player), 8 + might);
               }

               return Math.max(0.0F, hurtdamage - 20.0F - might);
            }
         }

         return hurtdamage;
      } else {
         return hurtdamage;
      }
   }
}
