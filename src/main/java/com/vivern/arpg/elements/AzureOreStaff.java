package com.vivern.arpg.elements;

import com.vivern.arpg.entity.AzureOreShoot;
import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
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

public class AzureOreStaff extends ItemWeapon {
   public static ShardType shardneed = ShardType.WATER;

   public AzureOreStaff() {
      this.setRegistryName("staff_of_the_azure_ore");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("staff_of_the_azure_ore");
      this.setMaxDamage(6500);
      this.setMaxStackSize(1);
   }

   public float getXpRepairRatio(ItemStack stack) {
      return 3.0F;
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

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.FOVlastTick = 10;
      Booom.FOVfrequency = -0.315F;
      Booom.FOVpower = 0.009F;
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
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            float power = Mana.getMagicPowerMax(player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float manacost = parameters.getEnchanted("manacost", sor);
            if (player.getHeldItemMainhand() == itemstack) {
               if (click && Mana.getMana(player) > manacost && !player.getCooldownTracker().hasCooldown(this)) {
                  Weapons.setPlayerAnimationOnServer(player, 26, EnumHand.MAIN_HAND);
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     level_stop_at != -1 ? Sounds.azure_ore_staff_powered : Sounds.azure_ore_staff,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                  player.addStat(StatList.getObjectUseStats(this));
                  IWeapon.fireBomEffect(this, player, world, 0);
                  int shots = level_stop_at != -1 ? parameters.geti("shots_powered") : parameters.geti("shots");

                  for (int i = 0; i < shots; i++) {
                     AzureOreShoot shoot = new AzureOreShoot(world, player, itemstack, power);
                     shoot.setPosition(player.posX, player.posY + player.getEyeHeight() - 0.5, player.posZ);
                     float velocityMult = level_stop_at != -1 ? 0.8F + itemRand.nextFloat() * 0.3F : 1.0F;
                     shoot.shoot(
                        player,
                        player.rotationPitch - 1.0F,
                        player.rotationYaw,
                        0.0F,
                        parameters.get("velocity") * velocityMult,
                        parameters.getEnchanted("inaccuracy", acc)
                     );
                     shoot.powered = level_stop_at != -1;
                     world.spawnEntity(shoot);
                  }

                  if (!player.capabilities.isCreativeMode) {
                     Mana.changeMana(player, -manacost);
                     Mana.setManaSpeed(player, 0.001F);
                     itemstack.damageItem(1, player);
                  }
               }

               if (click2 && level_stop_at == -1) {
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
            }
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

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
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
