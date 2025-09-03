//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoltenGreataxe extends ItemWeapon {
   public MoltenGreataxe() {
      this.setRegistryName("molten_greataxe");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("molten_greataxe");
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
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            NBTHelper.GiveNBTint(itemstack, 0, "duration");
            NBTHelper.GiveNBTint(itemstack, -1, "amplifier");
            NBTHelper.GiveNBTint(itemstack, -1, "potion");
            NBTHelper.GiveNBTint(itemstack, 0, "atdelay");
            int delay = NBTHelper.GetNBTint(itemstack, "atdelay");
            if (delay > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "atdelay");
            }

            if (player.getHeldItemMainhand() == itemstack) {
               if (delay <= 0 && click && !hascooldown) {
                  NBTHelper.SetNBTint(itemstack, 4, "atdelay");
                  Weapons.setPlayerAnimationOnServer(player, 15, EnumHand.MAIN_HAND);
                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
               }

               if (delay == 1) {
                  if (IWeapon.doMeleeHammerAttack(this, itemstack, player, EnumHand.MAIN_HAND, !player.onGround && player.motionY < -0.02, 90, 8).success
                     )
                   {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.melee_axe,
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
                        Sounds.melee_miss_axe,
                        SoundCategory.PLAYERS,
                        0.6F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  }

                  player.addExhaustion(0.2F);
               }
            }
         }
      }
   }

   @Override
   public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      int duration = NBTHelper.GetNBTint(stack, "duration");
      int amplifier = NBTHelper.GetNBTint(stack, "amplifier");
      int potion = NBTHelper.GetNBTint(stack, "potion");
      float artropods = 1.0F;
      float holy = 1.0F;
      if (entity instanceof EntityLivingBase) {
         artropods = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD
            ? EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, stack) * 0.1F + 1.0F
            : 1.0F;
         holy = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD
            ? EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, stack) * 0.1F + 1.0F
            : 1.0F;
         if (duration > 0) {
            Potion potione = Potion.getPotionById(potion);
            if (potione != null) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
               PotionEffect baff = new PotionEffect(
                  potione, parameters.getEnchantedi("potion_time", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, stack)), amplifier
               );
               ((EntityLivingBase)entity).addPotionEffect(baff);
               NBTHelper.AddNBTint(stack, -parameters.getEnchantedi("potion_consume", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, stack)), "duration");
            }
         }
      }

      boolean ret = entity.attackEntityFrom(
         new WeaponDamage(stack, player, null, false, false, player, WeaponDamage.blade),
         (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * artropods * holy + (isCritical ? 3 : 0)
      );
      entity.hurtResistantTime = 0;
      int firelvl = WeaponParameters.getWeaponParameters(stack.getItem())
         .getEnchantedi("fire", EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack));
      if (firelvl > 0) {
         entity.setFire(firelvl);
      }

      if (isCritical) {
         entity.world
            .playSound(
               (EntityPlayer)null,
               entity.posX,
               entity.posY,
               entity.posZ,
               SoundEvents.ENTITY_PLAYER_ATTACK_CRIT,
               SoundCategory.PLAYERS,
               0.7F,
               0.9F + itemRand.nextFloat() / 5.0F
            );
      }

      return ret;
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp(NBTHelper.GetNBTint(itemstack, "duration") / 2400.0F, 0.0F, 1.0F);
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "duration") > 0;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
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
