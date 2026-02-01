package com.vivern.arpg.elements;

import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AdamantiumLongsword extends ItemWeapon {
   public AdamantiumLongsword() {
      this.setRegistryName("adamantium_longsword");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("adamantium_longsword");
      this.setMaxDamage(2500);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            EnumHand hand = null;
            if (click && player.getHeldItemMainhand() == itemstack && !hascooldown) {
               hand = EnumHand.MAIN_HAND;
            } else if (click2 && player.getHeldItemOffhand() == itemstack && !hascooldown) {
               hand = EnumHand.OFF_HAND;
            }

            if (hand != null) {
               if (IWeapon.doMeleeSwordAttack(this, itemstack, player, hand, false).success) {
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

               Weapons.setPlayerAnimationOnServer(player, 1, hand);
               player.addExhaustion(0.1F);
               double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
               player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
            }
         }
      }
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
