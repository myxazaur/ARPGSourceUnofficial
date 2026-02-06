package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.BubbleFishShoot;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BubbleFish extends ItemWeapon {
   public static int maxammo = 180;

   public BubbleFish() {
      this.setRegistryName("bubble_fish");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("bubble_fish");
      this.setMaxDamage(7300);
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

   public void spawnEatParticles(World world, Entity player, Item eaten, int eatingParticleCount) {
      for (int i = 0; i < eatingParticleCount; i++) {
         float rotYaw = player.rotationYaw - 30.0F;
         Vec3d vec3d = new Vec3d((itemRand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
         vec3d = vec3d.rotatePitch(-player.rotationPitch * (float) (Math.PI / 180.0));
         vec3d = vec3d.rotateYaw(-rotYaw * (float) (Math.PI / 180.0));
         double d0 = -itemRand.nextFloat() * 0.6 - 0.3;
         Vec3d vec3d1 = new Vec3d((itemRand.nextFloat() - 0.5) * 0.3, d0, 0.6);
         vec3d1 = vec3d1.rotatePitch(-player.rotationPitch * (float) (Math.PI / 180.0));
         vec3d1 = vec3d1.rotateYaw(-rotYaw * (float) (Math.PI / 180.0));
         vec3d1 = vec3d1.add(player.posX, player.posY + player.getEyeHeight(), player.posZ);
         world.spawnParticle(
            EnumParticleTypes.ITEM_CRACK,
            vec3d1.x,
            vec3d1.y,
            vec3d1.z,
            vec3d.x,
            vec3d.y + 0.05,
            vec3d.z,
            new int[]{Item.getIdFromItem(eaten)}
         );
      }
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (world.isRemote) {
         if (itemRand.nextFloat() < 0.15F && !this.isReloaded(itemstack) && Weapons.getPlayerAnimationId(entityIn, EnumHand.MAIN_HAND) == 4) {
            this.spawnEatParticles(world, entityIn, ItemsRegister.FISHFEED, 4);
         }
      } else {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            float mana = Mana.getMana(player);
            float spee = Mana.getManaSpeed(player);
            int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            float power = Mana.getMagicPowerMax(player);
            this.decreaseReload(itemstack, player);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            boolean b1 = true;
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float manacost = parameters.getEnchanted("manacost", sor);
            if (click && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!hascooldown && mana > manacost) {
                     b1 = false;
                     int animSend = NBTHelper.GetNBTint(itemstack, "animSend");
                     if (animSend <= 0) {
                        NBTHelper.GiveNBTint(itemstack, 0, "animSend");
                        NBTHelper.SetNBTint(itemstack, 18, "animSend");
                        Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                     } else {
                        NBTHelper.AddNBTint(itemstack, -1, "animSend");
                     }

                     int shots = GetMOP.floatToIntWithChance(parameters.getEnchanted("shots", rapidity), itemRand);
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.bubble_fish,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.addStat(StatList.getObjectUseStats(this));
                     if (!player.capabilities.isCreativeMode) {
                        itemstack.damageItem(1, player);
                        Mana.changeMana(player, -manacost * shots);
                        Mana.setManaSpeed(player, 0.001F);
                        if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) == 0) {
                           this.addAmmo(ammo, itemstack, -1);
                        }
                     }

                     for (int i = 0; i < shots; i++) {
                        BubbleFishShoot projectile = new BubbleFishShoot(world, player, itemstack, power);
                        Weapons.shoot(
                           projectile,
                           EnumHand.MAIN_HAND,
                           player,
                           player.rotationPitch,
                           player.rotationYaw,
                           0.0F,
                           parameters.get("velocity"),
                           parameters.getEnchanted("inaccuracy", acc),
                           -0.05F,
                           0.5F,
                           0.5F
                        );
                        projectile.livetime = parameters.getEnchantedi("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack))
                           + itemRand.nextInt(parameters.geti("livetime_random_add"));
                        world.spawnEntity(projectile);
                     }
                  }
               } else if (this.initiateReload(itemstack, player, ItemsRegister.FISHFEED, maxammo)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.bubble_fish_rel,
                     SoundCategory.AMBIENT,
                     0.7F,
                     0.95F + itemRand.nextFloat() / 10.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
               }
            }

            if (b1 && !world.isRemote) {
               NBTHelper.SetNBTint(itemstack, 0, "animSend");
            }
         }
      }
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "ammo") / maxammo, 0.0F, 1.0F);
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) == 0;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public boolean autoReload(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }
}
