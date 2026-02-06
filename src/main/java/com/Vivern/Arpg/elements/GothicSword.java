package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.Freezing;
import com.Vivern.Arpg.potions.PotionEffects;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GothicSword extends ItemWeapon {
   public GothicSword() {
      this.setRegistryName("gothic_sword");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("gothic_sword");
      this.setMaxDamage(1400);
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
               player.getCooldownTracker()
                  .setCooldown(
                     (Item)(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP), this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack))
                  );
            }
         }
      }
   }

   @Override
   public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      float artropods = 1.0F;
      float holy = 1.0F;
      float freezebreak = 0.0F;
      if (entity instanceof EntityLivingBase) {
         artropods = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD
            ? EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, stack) * 0.1F + 1.0F
            : 1.0F;
         holy = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD
            ? EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, stack) * 0.1F + 1.0F
            : 1.0F;
         if (Freezing.canImmobilizeEntity((EntityLivingBase)entity, ((EntityLivingBase)entity).getActivePotionEffect(PotionEffects.FREEZING))) {
            freezebreak += 3.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack) / 2.0F;
         }
      }

      boolean ret = entity.attackEntityFrom(
         DamageSource.causePlayerDamage(player),
         (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * artropods * holy + freezebreak
      );
      entity.hurtResistantTime = 0;
      return ret;
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
