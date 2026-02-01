package com.vivern.arpg.elements;

import com.vivern.arpg.main.FindAmmo;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SacrificialDagger extends ItemWeapon {
   public SacrificialDagger() {
      this.setRegistryName("sacrificial_dagger");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("sacrificial_dagger");
      this.setMaxDamage(2000);
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
            boolean hascooldown2 = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
            EnumHand hand = null;
            if (click && player.getHeldItemMainhand() == itemstack && !hascooldown) {
               hand = EnumHand.MAIN_HAND;
            } else if (click2 && player.getHeldItemOffhand() == itemstack && !hascooldown2) {
               hand = EnumHand.OFF_HAND;
            }

            if (hand != null) {
               MeleeAttackResult attackresult = IWeapon.doMeleeDaggerAttack(this, itemstack, player, hand, false);
               if (attackresult.success) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.melee_dagger,
                     SoundCategory.PLAYERS,
                     0.7F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  if (attackresult.attackedEntities.size() == 1) {
                     Entity attacked = attackresult.attackedEntities.get(0);
                     if (attacked instanceof EntityLivingBase) {
                        EntityLivingBase attackedLivingBase = (EntityLivingBase)attacked;
                        if (attackedLivingBase.getHealth() <= 0.0F) {
                           int slot = FindAmmo.getSlotForEmptySoulstone(player.inventory);
                           if (slot >= 0 && slot < player.inventory.getSizeInventory()) {
                              SoulStone.catchSoul(player.inventory.getStackInSlot(slot), attackedLivingBase, player, 1);
                           }
                        }
                     }
                  }
               } else {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.melee_miss_dagger,
                     SoundCategory.PLAYERS,
                     0.6F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
               }

               Weapons.setPlayerAnimationOnServer(player, 16, hand);
               player.addExhaustion(0.04F);
               double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
               player.getCooldownTracker()
                  .setCooldown(
                     (Item)(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP), this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack))
                  );
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
      return WeaponHandleType.ONE_HANDED;
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
