package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityThrowedReaper;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Reaper extends ItemWeapon {
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");

   public Reaper() {
      this.setRegistryName("reaper");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("reaper");
      this.setMaxDamage(3000);
      this.setMaxStackSize(1);
      this.addPropertyOverride(new ResourceLocation("throw"), new IItemPropertyGetter() {
         @SideOnly(Side.CLIENT)
         public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
            if (entityIn == null) {
               return 0.0F;
            } else if (entityIn instanceof EntityPlayer) {
               EntityPlayer pl = (EntityPlayer)entityIn;
               return NBTHelper.GetNBTboolean(stack, "throwed") ? 1.0F : 0.0F;
            } else {
               return 0.0F;
            }
         }
      });
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

      float ch = NBTHelper.GetNBTfloat(stack, "charge");
      float dd = isCritical ? 5.0F : 0.0F;
      if (ch > 0.0F || dd > 0.0F) {
         player.world
            .playSound(
               (EntityPlayer)null,
               entity.posX,
               entity.posY,
               entity.posZ,
               Sounds.magic_e,
               SoundCategory.AMBIENT,
               0.9F,
               0.9F + itemRand.nextFloat() / 5.0F
            );
      }

      boolean ret = entity.attackEntityFrom(
         DamageSource.causePlayerDamage(player),
         (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * artropods * holy + dd + Math.min(ch, 10.0F)
      );
      entity.hurtResistantTime = 0;
      NBTHelper.SetNBTfloat(stack, 0.0F, "charge");
      int firelvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack);
      if (firelvl > 0) {
         entity.setFire(firelvl * 3);
      }

      return ret;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            NBTHelper.GiveNBTboolean(itemstack, false, "throwed");
            if (!player.getCooldownTracker().hasCooldown(this)) {
               NBTHelper.SetNBTboolean(itemstack, false, "throwed");
            }

            if (player.getHeldItemMainhand() == itemstack && !player.getCooldownTracker().hasCooldown(this)) {
               if (click2) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.reaper_throw,
                     SoundCategory.AMBIENT,
                     0.8F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  player.getCooldownTracker().setCooldown(this, 400);
                  player.addStat(StatList.getObjectUseStats(this));
                  Weapons.setPlayerAnimationOnServer(player, 1, EnumHand.MAIN_HAND);
                  if (!player.capabilities.isCreativeMode) {
                     itemstack.damageItem(1, player);
                  }

                  NBTHelper.SetNBTboolean(itemstack, true, "throwed");
                  EntityThrowedReaper treaper = new EntityThrowedReaper(world, player, itemstack);
                  treaper.shoot(
                     player,
                     player.rotationPitch,
                     player.rotationYaw,
                     0.2F,
                     1.4F,
                     0.5F - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack) / 7
                  );
                  world.spawnEntity(treaper);
               } else if (click && !NBTHelper.GetNBTboolean(itemstack, "throwed")) {
                  int sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, itemstack);
                  int sweeping = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING, itemstack);
                  int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, itemstack);
                  if (IWeapon.doMeleeSwordAttack(
                        this,
                        itemstack,
                        player,
                        EnumHand.MAIN_HAND,
                        5 + sharpness,
                        knockback * 0.4F,
                        2.8F + sweeping * 0.3F,
                        0.3F + sweeping * 0.1F,
                        0.3F + sweeping * 0.1F,
                        itemRand.nextFloat() < 0.2
                     )
                     .success) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        SoundEvents.ENTITY_PLAYER_ATTACK_STRONG,
                        SoundCategory.PLAYERS,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  } else {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        SoundEvents.ENTITY_PLAYER_ATTACK_WEAK,
                        SoundCategory.PLAYERS,
                        0.6F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  }

                  Weapons.setPlayerAnimationOnServer(player, 1, EnumHand.MAIN_HAND);
                  player.addExhaustion(0.05F);
                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
               }
            }
         }
      }
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return true;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      return 8 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) * 2;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
