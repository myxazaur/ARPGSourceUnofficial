//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.Vivern.Arpg.elements.armor.IItemKilling;
import com.Vivern.Arpg.main.IAttributedBauble;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.potions.AdvancedMobEffects;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumSkyBlock;

public class VampiricHeart extends Item implements IBauble, IAttributedBauble, IItemKilling {
   public VampiricHeart() {
      this.setRegistryName("vampiric_heart");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("vampiric_heart");
      this.setMaxDamage(9000);
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
      return 5.0;
   }

   @Override
   public int operation() {
      return 0;
   }

   @Override
   public String itemName() {
      return "vampiric_heart";
   }

   @Override
   public boolean useMultimap() {
      return true;
   }

   @Override
   public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
      if (player.world.getLightFor(EnumSkyBlock.SKY, player.getPosition()) > 14
         && player.world.isDaytime()
         && player.ticksExisted % 20 == 0
         && !player.world.isRemote) {
         player.attackEntityFrom(DamageSource.ON_FIRE, 4.0F);
         player.setFire(10);
      }

      if (player instanceof EntityPlayer
         && player.ticksExisted % 150 == 0
         && !((EntityPlayer)player).getCooldownTracker().hasCooldown(this)
         && !AdvancedMobEffects.hasEffect(player, AdvancedMobEffects.VAMPIRIC_HEART_EFFECT)) {
         AdvancedMobEffects.VAMPIRIC_HEART_EFFECT.addEffect(player);
      }
   }

   @Override
   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
      Multimap<String, AttributeModifier> multimap = HashMultimap.create();
      UUID uuid = UUID.fromString("CB2F4" + equipmentSlot + "D3-64" + equipmentSlot + "A-4F78-A497-9C56A33DB" + equipmentSlot + "BB");
      multimap.put(PropertiesRegistry.VAMPIRISM.getName(), new AttributeModifier(uuid, "vampirism modifier", 0.4, 0));
      multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuid, "movement speed modifier", 0.03, 0));
      multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuid, "max health modifier", 5.0, 0));
      multimap.put(SharedMonsterAttributes.FLYING_SPEED.getName(), new AttributeModifier(uuid, "flying speed modifier", 0.1, 0));
      return multimap;
   }

   @Override
   public void onKillWithItem(ItemStack stack, EntityPlayer playerattacker, EntityLivingBase target, DamageSource source) {
      AdvancedMobEffects.VAMPIRIC_HEART_EFFECT.removeEffect(playerattacker);
      playerattacker.getCooldownTracker().setCooldown(this, 150);
   }
}
