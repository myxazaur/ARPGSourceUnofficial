package com.vivern.arpg.elements;

import com.vivern.arpg.elements.armor.IItemAttacked;
import com.vivern.arpg.entity.EntityLiveHeart;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Hellmark extends ItemWeapon implements IItemAttacked {
   public Hellmark() {
      this.setRegistryName("hellmark");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("hellmark");
      this.setMaxDamage(1666);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged;
   }

   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      if (NBTHelper.GetNBTint(stack, "blocking") > 0) {
         if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(UUID.fromString("916DD27B-A123-455F-8C7F-6112A1B50A4C"), "Shield speed mh", -0.35, 1)
            );
         }

         if (equipmentSlot == EntityEquipmentSlot.OFFHAND) {
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(UUID.fromString("134CA27A-B123-501F-4D8F-3782C6B52C0A"), "Shield speed oh", -0.35, 1)
            );
         }
      }

      return multimap;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            NBTHelper.GiveNBTint(itemstack, 0, "blocking");
            int blocks = NBTHelper.GetNBTint(itemstack, "blocking");
            if (player.getHeldItemMainhand() == itemstack && click || player.getHeldItemOffhand() == itemstack && click2) {
               if (!player.getCooldownTracker().hasCooldown(this)) {
                  if (blocks <= 0) {
                     WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.shield_block,
                        SoundCategory.AMBIENT,
                        0.4F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 18, player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                     NBTHelper.SetNBTint(itemstack, parameters.geti("max_hits"), "blocking");
                     player.addExhaustion(0.6F);
                     if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0
                        && IWeapon.doMeleeSwordAttack(
                              this, itemstack, player, player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND, false
                           )
                           .success) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.shield_hit_metall,
                           SoundCategory.AMBIENT,
                           0.8F,
                           0.95F + itemRand.nextFloat() / 10.0F
                        );
                     }
                  } else if (player.ticksExisted % 7 == 0) {
                     Weapons.setPlayerAnimationOnServer(player, 18, player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                  }
               }
            } else if (blocks > 0) {
               NBTHelper.SetNBTint(itemstack, 0, "blocking");
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
            }
         }
      }
   }

   @Override
   public float onAttackedWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
      if (NBTHelper.GetNBTint(stack, "blocking") > 0) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
         float damageBlocks = parameters.getEnchanted("damage_reduce", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack));
         Entity attacker = source.getImmediateSource() == null ? source.getTrueSource() : source.getImmediateSource();
         if (!IWeapon.checkShieldAngle(stack, player, source)) {
            return hurtdamage;
         } else {
            int blocking = NBTHelper.GetNBTint(stack, "blocking") - 1;
            if (attacker != null
               || source != DamageSource.CRAMMING
                  && source != DamageSource.DROWN
                  && source != DamageSource.FALL
                  && source != DamageSource.HOT_FLOOR
                  && source != DamageSource.IN_FIRE
                  && source != DamageSource.IN_WALL
                  && source != DamageSource.LAVA
                  && source != DamageSource.MAGIC
                  && source != DamageSource.ON_FIRE
                  && source != DamageSource.OUT_OF_WORLD
                  && source != DamageSource.STARVE
                  && source != DamageSource.WITHER) {
               if (player.hurtResistantTime > 0) {
                  return 0.0F;
               } else {
                  if (attacker != null) {
                     SuperKnockback.applyShieldBlock(
                        player,
                        attacker,
                        parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack)),
                        parameters.get("self_knockback")
                     );
                     DamageSource wDamageSource = new WeaponDamage(stack, player, null, false, false, player, WeaponDamage.pierce).setIsThornsDamage();
                     float damage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack))
                        + (attacker.isBurning() ? parameters.get("fire_damage_bonus") : 0.0F);
                     Weapons.dealDamage(wDamageSource, damage, player, attacker, true);
                     if (attacker.isBurning()) {
                        player.world
                           .playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.hellmark,
                              SoundCategory.AMBIENT,
                              0.8F,
                              0.9F + itemRand.nextFloat() / 5.0F
                           );
                     }
                  }

                  NBTHelper.AddNBTint(stack, -1, "blocking");
                  if (blocking <= 0) {
                     player.world
                        .playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.shield_break,
                           SoundCategory.AMBIENT,
                           0.6F,
                           0.95F + itemRand.nextFloat() / 10.0F
                        );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(stack));
                  } else {
                     player.world
                        .playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.shield_hit_metall,
                           SoundCategory.AMBIENT,
                           0.6F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                  }

                  player.addExhaustion(0.1F);
                  if (!player.capabilities.isCreativeMode) {
                     stack.damageItem(1, player);
                  }

                  player.hurtResistantTime = 10;
                  return hurtdamage - damageBlocks;
               }
            } else {
               return hurtdamage;
            }
         }
      } else {
         return hurtdamage;
      }
   }

   @Override
   public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      float artropods = 1.0F;
      float holy = 1.0F;
      if (entity instanceof EntityLivingBase) {
         artropods = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD
            ? EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, stack) * 0.1F + 1.0F
            : 1.0F;
         holy = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD
            ? EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, stack) * 0.1F + 1.0F
            : 1.0F;
      }

      boolean ret = entity.attackEntityFrom(
         new WeaponDamage(stack, player, null, false, false, player, WeaponDamage.blade),
         (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * artropods * holy
            + (entity.isBurning() ? WeaponParameters.getWeaponParameters(stack.getItem()).get("fire_damage_bonus") : 0.0F)
      );
      entity.hurtResistantTime = 0;
      if (ret && entity.isBurning()) {
         EntityLiveHeart.spawnHearts(
            player.world,
            entity.posX,
            entity.posY,
            entity.posZ,
            2,
            WeaponParameters.getWeaponParameters(stack.getItem()).get("heart_health"),
            true,
            5.0F,
            player
         );
      }

      int firelvl = WeaponParameters.getWeaponParameters(stack.getItem())
         .getEnchantedi("fire", EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack));
      if (firelvl > 0) {
         entity.setFire(firelvl);
      }

      return ret;
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp(
         (float)NBTHelper.GetNBTint(itemstack, "blocking") / WeaponParameters.getWeaponParameters(itemstack.getItem()).geti("max_hits"), 0.0F, 1.0F
      );
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "blocking") > 0;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }

   @Override
   public boolean cancelOnNull() {
      return true;
   }
}
