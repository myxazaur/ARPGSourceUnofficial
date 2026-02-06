package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.elements.armor.IItemAttacked;
import com.Vivern.Arpg.entity.EntityLiveHeart;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class RottenShield extends ItemWeapon implements IItemAttacked {
   public RottenShield() {
      this.setRegistryName("rotten_shield");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("rotten_shield");
      this.setMaxDamage(350);
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
               new AttributeModifier(UUID.fromString("916DD27B-A123-455F-8C7F-6112A1B50A4C"), "Shield speed mh", -0.05, 0)
            );
         }

         if (equipmentSlot == EntityEquipmentSlot.OFFHAND) {
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(UUID.fromString("134CA27A-B123-501F-4D8F-3782C6B52C0A"), "Shield speed oh", -0.05, 0)
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
            float acclvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            NBTHelper.GiveNBTint(itemstack, 0, "blocking");
            int blocks = NBTHelper.GetNBTint(itemstack, "blocking");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (player.getHeldItemMainhand() == itemstack && click || player.getHeldItemOffhand() == itemstack && click2) {
               if (!player.getCooldownTracker().hasCooldown(this)) {
                  if (blocks <= 0) {
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
                     player.addExhaustion(parameters.getEnchanted("exhaustion_on_use", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack)));
                  } else if (player.ticksExisted % 7 == 0) {
                     Weapons.setPlayerAnimationOnServer(player, 18, player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                  }
               }
            } else if (blocks > 0) {
               float starvedCooldownMultiplier = parameters.get("starved_cooldown_multiplier");
               float foodToStarve = parameters.getEnchanted("food_level_to_starve", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack));
               NBTHelper.SetNBTint(itemstack, 0, "blocking");
               player.getCooldownTracker()
                  .setCooldown(
                     this, (int)((player.getFoodStats().getFoodLevel() <= foodToStarve ? starvedCooldownMultiplier : 1.0F) * this.getCooldownTime(itemstack))
                  );
            }
         }
      }
   }

   @Override
   public float onAttackedWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
      if (NBTHelper.GetNBTint(stack, "blocking") > 0) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
         float damageBlocks = parameters.getEnchanted("damage_reduce", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack));
         float starvedCooldownMultiplier = parameters.get("starved_cooldown_multiplier");
         float foodToStarve = parameters.getEnchanted("food_level_to_starve", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack));
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
                     float eatChance = parameters.getEnchanted("eat_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, stack));
                     if (itemRand.nextFloat() < eatChance && player.isPotionActive(MobEffects.HUNGER)) {
                        float damage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack));
                        DamageSource wDamageSource = new WeaponDamage(stack, player, null, false, false, player, WeaponDamage.bite).setIsThornsDamage();
                        if (Weapons.dealDamage(wDamageSource, damage, player, attacker, true)) {
                           player.world
                              .playSound(
                                 (EntityPlayer)null,
                                 player.posX,
                                 player.posY,
                                 player.posZ,
                                 SoundEvents.ENTITY_GENERIC_EAT,
                                 SoundCategory.PLAYERS,
                                 0.7F,
                                 0.9F + itemRand.nextFloat() / 5.0F
                              );
                           EntityLiveHeart.spawnHearts(
                              player.world,
                              attacker.posX,
                              attacker.posY,
                              attacker.posZ,
                              2,
                              parameters.get("heart_health"),
                              true,
                              4.0F,
                              player
                           );
                        }
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
                     player.getCooldownTracker()
                        .setCooldown(
                           this,
                           (int)((player.getFoodStats().getFoodLevel() <= foodToStarve ? starvedCooldownMultiplier : 1.0F) * this.getCooldownTime(stack))
                        );
                  } else {
                     player.world
                        .playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.shield_hit_soft,
                           SoundCategory.AMBIENT,
                           0.6F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                  }

                  player.addExhaustion(parameters.getEnchanted("exhaustion_on_block", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack)));
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
