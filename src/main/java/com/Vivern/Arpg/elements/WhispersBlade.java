//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.WhispersShoot;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.ShardType;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WhispersBlade extends ItemWeapon {
   public static ShardType shardneed = ShardType.AIR;

   public WhispersBlade() {
      this.setRegistryName("whispers_blade");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("whispers_blade");
      this.setMaxDamage(5200);
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
         NBTHelper.GiveNBTint(itemstack, -1, "level_stop_at");
         int level_stop_at = NBTHelper.GetNBTint(itemstack, "level_stop_at");
         if (level_stop_at != -1 && entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            if (player.experienceLevel > level_stop_at) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               Weapons.addOrRemoveExperience(player, -parameters.geti("xp_decrease"));
            } else {
               NBTHelper.SetNBTint(itemstack, -1, "level_stop_at");
               world.playSound(
                  (EntityPlayer)null, player.posX, player.posY, player.posZ, Sounds.ae_unpower, SoundCategory.AMBIENT, 1.0F, 1.0F
               );
            }
         }

         boolean powered = level_stop_at != -1;
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            float power = Mana.getMagicPowerMax(player);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            int range = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack);
            NBTHelper.GiveNBTint(itemstack, 0, "fdelay");
            int firedelay = NBTHelper.GetNBTint(itemstack, "fdelay");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float manacost = parameters.getEnchanted("manacost", sor);
            if (powered) {
               manacost *= 2.0F;
            }

            EnumHand hand = player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : (player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
            if (hand != null) {
               if (firedelay > 0) {
                  NBTHelper.AddNBTint(itemstack, -1, "fdelay");
               }

               if ((!click || hand != EnumHand.MAIN_HAND) && (!click2 || hand != EnumHand.OFF_HAND)) {
                  boolean powerOn = click2 && hand == EnumHand.MAIN_HAND || click && hand == EnumHand.OFF_HAND;
                  if (powerOn && level_stop_at == -1) {
                     int levelStopAtNew = CrystalStar.getLevelToStopEmpower(player, itemstack);
                     if (levelStopAtNew != -1) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.ae_power,
                           SoundCategory.AMBIENT,
                           1.0F,
                           1.0F
                        );
                        NBTHelper.SetNBTint(itemstack, levelStopAtNew, "level_stop_at");
                     }
                  }
               } else if (firedelay == 0 && Mana.getMana(player) > manacost && !player.getCooldownTracker().hasCooldown(this)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     powered ? Sounds.whispers_blade_powered : Sounds.whispers_blade,
                     SoundCategory.AMBIENT,
                     1.0F,
                     0.9F + itemRand.nextFloat() * 0.2F
                  );
                  player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                  player.addStat(StatList.getObjectUseStats(this));
                  IWeapon.fireBomEffect(this, player, world, 0);
                  NBTHelper.SetNBTint(itemstack, 5, "fdelay");
                  Weapons.setPlayerAnimationOnServer(player, 24, hand);
                  if (!player.capabilities.isCreativeMode) {
                     Mana.changeMana(player, -manacost);
                     Mana.setManaSpeed(player, 0.001F);
                     itemstack.damageItem(1, player);
                  }
               }
            } else {
               NBTHelper.SetNBTint(itemstack, 0, "fdelay");
            }

            if (firedelay == 1) {
               WhispersShoot projectile = new WhispersShoot(world, player, itemstack, power);
               projectile.shoot(
                  player,
                  player.rotationPitch,
                  player.rotationYaw,
                  0.0F,
                  powered ? parameters.get("velocity_powered") : parameters.get("velocity"),
                  parameters.getEnchanted("inaccuracy", acc)
               );
               projectile.setPosition(player.posX, player.posY + player.getEyeHeight() - 0.2, player.posZ);
               projectile.rotationRoll = itemRand.nextInt(23) - 11;
               projectile.cutterSize = powered ? parameters.getEnchanted("cutter_size_powered", range) : parameters.getEnchanted("cutter_size", range);
               projectile.powered = powered;
               if (powered) {
                  projectile.lastBounces = parameters.getEnchantedi("bounces", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack));
               }

               world.spawnEntity(projectile);
            }
         } else {
            NBTHelper.SetNBTint(itemstack, 0, "fdelay");
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 17;
      Booom.frequency = -0.185F;
      Booom.x = 1.0F;
      Booom.y = itemRand.nextFloat() < 0.5 ? 1.0F : -1.0F;
      Booom.z = 0.0F;
      Booom.power = -0.27F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      boolean powered = NBTHelper.GetNBTint(itemstack, "level_stop_at") != -1;
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return powered ? parameters.getEnchantedi("cooldown_powered", rapidity) : parameters.getEnchantedi("cooldown", rapidity);
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }
}
