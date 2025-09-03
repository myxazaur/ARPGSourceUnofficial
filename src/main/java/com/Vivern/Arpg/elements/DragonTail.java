//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DragonTail extends ItemWeapon {
   public DragonTail() {
      this.setRegistryName("dragon_tail");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("dragon_tail");
      this.setMaxDamage(1150);
      this.setMaxStackSize(1);
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            int damage = itemstack.getItemDamage();
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            NBTHelper.GiveNBTint(itemstack, 0, "atdelay");
            NBTHelper.GiveNBTint(itemstack, 0, "charge");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int delay = NBTHelper.GetNBTint(itemstack, "atdelay");
            if (delay > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "atdelay");
            }

            int charge = NBTHelper.GetNBTint(itemstack, "charge");
            if (charge < parameters.geti("max_charge")) {
               NBTHelper.AddNBTint(itemstack, 1, "charge");
            }

            EnumHand hand = EnumHand.MAIN_HAND;
            if (player.getHeldItemMainhand() == itemstack) {
               if (delay <= 0 && click && !hascooldown) {
                  Weapons.setPlayerAnimationOnServer(player, EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) > 0 ? 17 : 2, hand);
                  NBTHelper.SetNBTint(itemstack, 4, "atdelay");
                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
               }

               if (delay == 1) {
                  if (IWeapon.doMeleeSpearAttack(
                        this,
                        itemstack,
                        player,
                        hand,
                        charge >= parameters.geti("max_charge")
                           && itemRand.nextFloat()
                              < parameters.getEnchanted("cloud_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack))
                     )
                     .success) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.melee_sword,
                        SoundCategory.PLAYERS,
                        0.7F,
                        0.8F + itemRand.nextFloat() / 5.0F
                     );
                  } else {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.melee_miss_sword,
                        SoundCategory.PLAYERS,
                        0.6F,
                        0.8F + itemRand.nextFloat() / 5.0F
                     );
                  }

                  player.addExhaustion(0.1F);
               }
            }
         }
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
         if (((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            isCritical = false;
         }
      }

      boolean ret = entity.attackEntityFrom(
         DamageSource.causePlayerDamage(player), (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * artropods * holy
      );
      entity.hurtResistantTime = 0;
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      int firelvl = parameters.getEnchantedi("fire", EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack));
      if (firelvl > 0) {
         entity.setFire(firelvl);
      }

      if (isCritical) {
         int charge = NBTHelper.GetNBTint(stack, "charge");
         if (charge >= parameters.geti("max_charge")) {
            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(
               entity.world, entity.posX, entity.posY, entity.posZ
            );
            entityareaeffectcloud.setOwner(player);
            entityareaeffectcloud.setParticle(EnumParticleTypes.DRAGON_BREATH);
            entityareaeffectcloud.setRadius(parameters.getEnchantedi("cloud_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, stack)));
            entityareaeffectcloud.setDuration(parameters.geti("cloud_duration"));
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack) == 0) {
               entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / entityareaeffectcloud.getDuration());
            }

            entityareaeffectcloud.addEffect(
               parameters.getPotion("potion", MobEffects.INSTANT_DAMAGE, EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, stack))
            );
            entity.world.spawnEntity(entityareaeffectcloud);
            NBTHelper.SetNBTint(stack, 0, "charge");
            entity.world
               .playSound(
                  (EntityPlayer)null,
                  entity.posX,
                  entity.posY,
                  entity.posZ,
                  SoundEvents.ENTITY_ENDERDRAGON_SHOOT,
                  SoundCategory.PLAYERS,
                  0.6F,
                  0.93F + itemRand.nextFloat() / 5.0F
               );
         }
      }

      return ret;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }
}
