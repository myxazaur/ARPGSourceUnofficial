package com.vivern.arpg.elements;

import com.vivern.arpg.entity.StingingCellEntity;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StingingCell extends ItemWeapon {
   public StingingCell() {
      this.setRegistryName("stinging_cell");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("stinging_cell");
      this.setMaxDamage(2500);
      this.setMaxStackSize(1);
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
            boolean click = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
            float power = Mana.getMagicPowerMax(player);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float manacost = parameters.getEnchanted("manacost", sor);
            if (player.getHeldItemMainhand() == itemstack && Mana.getMana(player) > manacost && click && !player.getCooldownTracker().hasCooldown(this)) {
               Weapons.setPlayerAnimationOnServer(player, 14, EnumHand.MAIN_HAND);
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.stinging_cell,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               player.addStat(StatList.getObjectUseStats(this));
               int shots = parameters.geti("shots");

               for (int i = 0; i < shots; i++) {
                  StingingCellEntity projectile = new StingingCellEntity(world, player, itemstack, power);
                  projectile.livetime = parameters.geti("livetime");
                  Weapons.shoot(
                     projectile,
                     EnumHand.MAIN_HAND,
                     player,
                     player.rotationPitch,
                     player.rotationYaw,
                     0.0F,
                     parameters.get("velocity"),
                     parameters.getEnchanted("inaccuracy", acc),
                     -0.35F,
                     0.75F,
                     0.1F
                  );
                  world.spawnEntity(projectile);
               }

               if (!player.capabilities.isCreativeMode) {
                  Mana.changeMana(player, -manacost);
                  Mana.setManaSpeed(player, 0.001F);
                  itemstack.damageItem(1, player);
               }
            }
         }
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }
}
