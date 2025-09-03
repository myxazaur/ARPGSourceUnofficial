//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AdamantiumBattleAxe extends ItemWeapon {
   public AdamantiumBattleAxe() {
      this.setRegistryName("adamantium_battle_axe");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("adamantium_battle_axe");
      this.setMaxDamage(4000);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      if (param == 0) {
         Booom.lastTick = 32;
         Booom.frequency = -Booom.getFrequencyForTicks(Booom.lastTick);
         Booom.x = -1.0F;
         Booom.y = (itemRand.nextFloat() - 0.5F) * 0.5F;
         Booom.z = (itemRand.nextFloat() - 0.5F) * 0.5F;
         Booom.power = 0.35F;
      }

      if (param == 2) {
         Booom.lastTick = 22;
         Booom.frequency = 0.43F;
         Booom.x = itemRand.nextFloat() < 0.5 ? 0.1F : -0.1F;
         Booom.y = 0.0F;
         Booom.z = 1.0F;
         Booom.power = itemRand.nextFloat() < 0.5 ? -0.1F : 0.1F;
      }

      if (param == 3) {
         Booom.lastTick = 25;
         Booom.frequency = 0.126F;
         Booom.x = 1.0F;
         Booom.y = 0.0F;
         Booom.z = 0.0F;
         Booom.power = 0.1F;
      }
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            NBTHelper.GiveNBTint(itemstack, 0, "atdelay");
            int delay = NBTHelper.GetNBTint(itemstack, "atdelay");
            if (delay > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "atdelay");
            }

            if (player.getHeldItemMainhand() == itemstack) {
               if (delay <= 0 && click && !hascooldown) {
                  NBTHelper.SetNBTint(itemstack, 5, "atdelay");
                  Weapons.setPlayerAnimationOnServer(player, 15, EnumHand.MAIN_HAND);
                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
               } else if (delay <= 0
                  && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0
                  && Keys.isKeyPressed(player, Keys.SECONDARYATTACK)
                  && !hascooldown) {
                  NBTHelper.SetNBTint(itemstack, 11, "atdelay");
                  NBTHelper.GiveNBTboolean(itemstack, true, "specattack");
                  NBTHelper.SetNBTboolean(itemstack, true, "specattack");
                  Weapons.setPlayerAnimationOnServer(player, 46, EnumHand.MAIN_HAND);
                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.swosh_d,
                     SoundCategory.PLAYERS,
                     0.7F,
                     0.7F + itemRand.nextFloat() / 5.0F
                  );
                  IWeapon.fireBomEffect(this, player, world, 0);
               }

               if (delay == 1) {
                  if (NBTHelper.GetNBTboolean(itemstack, "specattack")) {
                     if (doAlternativeHammerAttack(this, itemstack, player, EnumHand.MAIN_HAND, false, 70, 7).success) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.melee_block,
                           SoundCategory.PLAYERS,
                           0.9F,
                           0.6F + itemRand.nextFloat() / 5.0F
                        );
                        IWeapon.fireBomEffect(this, player, world, 2);
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           SoundEvents.ENTITY_PLAYER_ATTACK_CRIT,
                           SoundCategory.PLAYERS,
                           0.9F,
                           0.8F + itemRand.nextFloat() / 5.0F
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
                           0.7F + itemRand.nextFloat() / 5.0F
                        );
                        IWeapon.fireBomEffect(this, player, world, 3);
                     }

                     player.addExhaustion(0.2F);
                     NBTHelper.SetNBTboolean(itemstack, false, "specattack");
                  } else {
                     if (IWeapon.doMeleeHammerAttack(this, itemstack, player, EnumHand.MAIN_HAND, false, 70, 7).success) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.melee_axe,
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
                           Sounds.melee_miss_axe,
                           SoundCategory.PLAYERS,
                           0.6F,
                           0.8F + itemRand.nextFloat() / 5.0F
                        );
                     }

                     player.addExhaustion(0.2F);
                  }
               }
            }
         }
      }
   }

   public static MeleeAttackResult doAlternativeHammerAttack(
      IWeapon iweapon, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical, int sweepAngle, int sweepStepAngle
   ) {
      int sharpness = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack);
      int sweeping = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, stack);
      int knockback = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      return IWeapon.doMeleeHammerAttack(
         iweapon,
         stack,
         player,
         hand,
         parameters.getEnchanted("damage_special", sharpness),
         parameters.getEnchanted("knockback", knockback),
         parameters.getEnchanted("length", sweeping),
         parameters.getEnchanted("size", sweeping),
         parameters.getEnchanted("end_size", sweeping),
         isCritical,
         sweepAngle,
         sweepStepAngle
      );
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return parameters.getEnchantedi(NBTHelper.GetNBTboolean(itemstack, "specattack") ? "cooldown_special" : "cooldown", rapidity);
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
