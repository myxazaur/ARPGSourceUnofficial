package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityRestlessSkull;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.PotionEffects;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class RestlessSkull extends ItemWeapon {
   public RestlessSkull() {
      this.setRegistryName("restless_skull");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("restless_skull");
      this.setMaxDamage(5000);
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

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack stack) {
      int charges = NBTHelper.GetNBTint(stack, "charges");
      return charges > 0;
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack stack) {
      float maxcharges = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack) > 0 ? 50.0F : 20.0F;
      float charges = NBTHelper.GetNBTint(stack, "charges");
      return charges / maxcharges;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
            float power = Mana.getMagicPowerMax(player);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            NBTHelper.GiveNBTboolean(itemstack, false, "challengeblock");
            NBTHelper.GiveNBTboolean(itemstack, false, "challengemob");
            NBTHelper.GiveNBTboolean(itemstack, false, "challengeflame");
            NBTHelper.GiveNBTint(itemstack, 0, "charges");
            boolean challengeblock = NBTHelper.GetNBTboolean(itemstack, "challengeblock");
            boolean challengemob = NBTHelper.GetNBTboolean(itemstack, "challengemob");
            boolean challengeflame = NBTHelper.GetNBTboolean(itemstack, "challengeflame");
            int charges = NBTHelper.GetNBTint(itemstack, "charges");
            if (player.getHeldItemMainhand() == itemstack) {
               if (player.ticksExisted % 10 == 0) {
                  List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(5.0, 4.0, 5.0));
                  int ghostflames = 0;

                  for (EntityLivingBase base : list) {
                     if (base.isPotionActive(PotionEffects.INCORPOREITY)) {
                        ghostflames++;
                     }
                  }

                  if (ghostflames >= 2) {
                     NBTHelper.SetNBTboolean(itemstack, true, "challengeflame");
                  }
               }

               if (Mana.getMana(player) > 1.8F - sor / 3.1F && !player.getCooldownTracker().hasCooldown(this)) {
                  if (click) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.restless_skull,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                     EntityRestlessSkull projectile = new EntityRestlessSkull(world, player, itemstack, power);
                     projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.65F, 2 / (acc + 1));
                     if (itemRand.nextFloat() < 0.3 + reuse / 3.0F) {
                        projectile.flaming = true;
                     }

                     world.spawnEntity(projectile);
                     if (!player.capabilities.isCreativeMode) {
                        Mana.changeMana(player, -1.8F + sor / 3.1F);
                        Mana.setManaSpeed(player, 0.001F);
                        itemstack.damageItem(1, player);
                     }
                  } else if (click2 && charges > 0) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.restless_skull,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, 9 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) * 2);
                     player.addStat(StatList.getObjectUseStats(this));
                     Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                     EntityRestlessSkull projectilex = new EntityRestlessSkull(world, player, itemstack, power);
                     projectilex.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.65F, 2 / (acc + 1));
                     projectilex.powered = true;
                     projectilex.active = true;
                     world.spawnEntity(projectilex);
                     if (!player.capabilities.isCreativeMode) {
                        Mana.changeMana(player, -1.2F + sor / 4.1F);
                        Mana.setManaSpeed(player, 0.001F);
                        itemstack.damageItem(1, player);
                     }

                     NBTHelper.AddNBTint(itemstack, -1, "charges");
                  }
               }

               if (challengeblock && challengemob && challengeflame) {
                  int maxcharges = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0 ? 50 : 20;
                  NBTHelper.SetNBTboolean(itemstack, false, "challengeblock");
                  NBTHelper.SetNBTboolean(itemstack, false, "challengemob");
                  NBTHelper.SetNBTboolean(itemstack, false, "challengeflame");
                  NBTHelper.SetNBTint(itemstack, MathHelper.clamp(charges + 10, 0, maxcharges), "charges");
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.restless_skull_boost,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
               }
            }
         }
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 13 - rapidity * 3;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }
}
