package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.Vivern.Arpg.elements.armor.IItemDamaging;
import com.Vivern.Arpg.main.IAttributedBauble;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.potions.PotionEffects;
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
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;

public class OrbOfDestroy extends Item implements IBauble, IAttributedBauble, IItemDamaging {
   public OrbOfDestroy() {
      this.setRegistryName("orb_of_destroy");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("orb_of_destroy");
      this.setMaxDamage(1000);
      this.setMaxStackSize(1);
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.TRINKET;
   }

   @Override
   public IAttribute getAttribute() {
      return PropertiesRegistry.VAMPIRISM;
   }

   @Override
   public double value() {
      return 0.05;
   }

   @Override
   public int operation() {
      return 0;
   }

   @Override
   public String itemName() {
      return "orb_of_destroy";
   }

   @Override
   public boolean useMultimap() {
      return true;
   }

   @Override
   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
      Multimap<String, AttributeModifier> multimap = HashMultimap.create();
      UUID uuid = UUID.fromString("CB2F4" + equipmentSlot + "D3-64" + equipmentSlot + "A-4F78-A497-9C56A33DB" + equipmentSlot + "BB");
      multimap.put(PropertiesRegistry.VAMPIRISM.getName(), new AttributeModifier(uuid, "vampirism modifier", 0.12, 0));
      multimap.put(PropertiesRegistry.MAGIC_POWER_MAX.getName(), new AttributeModifier(uuid, "magic power modifier", 0.2, 0));
      multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(uuid, "attack damage modifier", 2.0, 0));
      return multimap;
   }

   @Override
   public float onCauseDamageWithItem(float hurtdamage, ItemStack stack, EntityPlayer playerattacker, EntityLivingBase target, DamageSource source) {
      if (Mana.getMana(playerattacker) > Mana.getManaMax(playerattacker) * 0.8 && !target.isPotionActive(PotionEffects.BLOOD_THIRST)) {
         PotionEffect baff = new PotionEffect(PotionEffects.BLOOD_THIRST, 110);
         target.addPotionEffect(baff);
         Mana.changeMana(playerattacker, -0.5F);
         Mana.setManaSpeed(playerattacker, 0.001F);
         playerattacker.world
            .playSound(
               (EntityPlayer)null,
               target.posX,
               target.posY,
               target.posZ,
               Sounds.magic_l,
               SoundCategory.AMBIENT,
               0.7F,
               0.9F + itemRand.nextFloat() / 5.0F
            );
      }

      return hurtdamage;
   }
}
