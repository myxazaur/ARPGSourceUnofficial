//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.CrystalStarPoweredShoot;
import com.Vivern.Arpg.entity.CrystalStarShoot;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystalStar extends ItemWeapon {
   public static ShardType shardneed = ShardType.EARTH;

   public CrystalStar() {
      this.setRegistryName("crystal_star");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("crystal_star");
      this.setMaxDamage(4000);
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

   public static int getLevelToStopEmpower(EntityPlayer player, ItemStack itemstack) {
      if (player.experienceLevel >= 10) {
         float ft1 = GetMOP.getfromto((float)player.experienceLevel, 10.0F, 50.0F);
         float a = GetMOP.partial(-3.0F, -10.0F, ft1);
         return player.experienceLevel + MathHelper.floor(a);
      } else {
         return -1;
      }
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

         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            float power = Mana.getMagicPowerMax(player);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            NBTHelper.GiveNBTint(itemstack, 0, "fdelay");
            int firedelay = NBTHelper.GetNBTint(itemstack, "fdelay");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float manacost = parameters.getEnchanted("manacost", sor);
            EnumHand hand = player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : (player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
            if (hand != null) {
               if (firedelay > 0) {
                  NBTHelper.AddNBTint(itemstack, -1, "fdelay");
               }

               if ((!click || hand != EnumHand.MAIN_HAND) && (!click2 || hand != EnumHand.OFF_HAND)) {
                  boolean powerOn = click2 && hand == EnumHand.MAIN_HAND || click && hand == EnumHand.OFF_HAND;
                  if (powerOn && level_stop_at == -1) {
                     int levelStopAtNew = getLevelToStopEmpower(player, itemstack);
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
                     level_stop_at == -1 ? Sounds.crystal_star : Sounds.crystal_star_powered,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.95F + itemRand.nextFloat() / 5.0F
                  );
                  player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                  player.addStat(StatList.getObjectUseStats(this));
                  IWeapon.fireBomEffect(this, player, world, 0);
                  NBTHelper.SetNBTint(itemstack, 4, "fdelay");
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
               if (level_stop_at == -1) {
                  int nn = parameters.geti("shots");

                  for (int i = 0; i < nn; i++) {
                     CrystalStarShoot projectile = new CrystalStarShoot(world, player, itemstack, power);
                     projectile.shoot(
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        parameters.get("velocity"),
                        parameters.get("inaccuracy") + i / 2.0F - parameters.get("inaccuracy_ench") * acc
                     );
                     world.spawnEntity(projectile);
                  }
               } else {
                  CrystalStarPoweredShoot projectile = new CrystalStarPoweredShoot(world, player, itemstack, power);
                  projectile.shoot(
                     player, player.rotationPitch, player.rotationYaw, 0.0F, parameters.get("velocity"), parameters.getEnchanted("inaccuracy", acc)
                  );
                  world.spawnEntity(projectile);
               }
            }
         } else {
            NBTHelper.SetNBTint(itemstack, 0, "fdelay");
         }
      }
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int level_stop_at = NBTHelper.GetNBTint(itemstack, "level_stop_at");
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return parameters.getEnchantedi(level_stop_at == -1 ? "cooldown" : "cooldown_powered", rapidity);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 15;
      Booom.frequency = -0.21F;
      Booom.x = 1.0F;
      Booom.y = itemRand.nextFloat() < 0.5 ? 1.0F : -1.0F;
      Booom.z = 0.0F;
      Booom.power = -0.32F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }
}
