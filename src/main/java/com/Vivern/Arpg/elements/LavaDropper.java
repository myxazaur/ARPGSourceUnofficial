//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.LavaDropperShoot;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
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
import net.minecraft.world.World;

public class LavaDropper extends ItemWeapon {
   public LavaDropper() {
      this.setRegistryName("lava_dropper");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("lava_dropper");
      this.setMaxDamage(760);
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

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            boolean hascooldown2 = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
            float power = Mana.getMagicPowerMax(player);
            float mana = Mana.getMana(player);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float manacost = parameters.getEnchanted("manacost", sor);
            if (click && player.getHeldItemMainhand() == itemstack && mana > manacost && !hascooldown) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.lava_dropper,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               player.addStat(StatList.getObjectUseStats(this));
               Weapons.setPlayerAnimationOnServer(player, 1, EnumHand.MAIN_HAND);
               if (!player.capabilities.isCreativeMode) {
                  Mana.changeMana(player, -manacost);
                  Mana.setManaSpeed(player, 0.001F);
                  itemstack.damageItem(1, player);
               }

               LavaDropperShoot projectile = new LavaDropperShoot(world, player, itemstack, power);
               Weapons.shoot(
                  projectile,
                  EnumHand.MAIN_HAND,
                  player,
                  player.rotationPitch,
                  player.rotationYaw,
                  0.0F,
                  parameters.get("velocity"),
                  parameters.getEnchanted("inaccuracy", acc),
                  -0.1F,
                  0.5F,
                  0.2F
               );
               world.spawnEntity(projectile);
            }

            if (click2 && player.getHeldItemOffhand() == itemstack && mana > manacost && !hascooldown2) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.lava_dropper,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               player.getCooldownTracker().setCooldown(ItemsRegister.EXP, this.getCooldownTime(itemstack));
               player.addStat(StatList.getObjectUseStats(this));
               Weapons.setPlayerAnimationOnServer(player, 1, EnumHand.OFF_HAND);
               if (!player.capabilities.isCreativeMode) {
                  Mana.changeMana(player, -manacost);
                  Mana.setManaSpeed(player, 0.001F);
                  itemstack.damageItem(1, player);
               }

               LavaDropperShoot projectile = new LavaDropperShoot(world, player, itemstack, power);
               Weapons.shoot(
                  projectile,
                  EnumHand.OFF_HAND,
                  player,
                  player.rotationPitch,
                  player.rotationYaw,
                  0.0F,
                  parameters.get("velocity"),
                  parameters.getEnchanted("inaccuracy", acc),
                  -0.1F,
                  0.5F,
                  0.2F
               );
               world.spawnEntity(projectile);
            }
         }
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
