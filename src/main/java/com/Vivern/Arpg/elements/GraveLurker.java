//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.GraveLurkerProjectile;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
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

public class GraveLurker extends ItemWeapon {
   public GraveLurker() {
      this.setRegistryName("grave_lurker");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("grave_lurker");
      this.setMaxDamage(3500);
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
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            float mana = Mana.getMana(player);
            float spee = Mana.getManaSpeed(player);
            float power = Mana.getMagicPowerMax(player);
            int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            if (player.getHeldItemMainhand() == itemstack && mana > 3.0F - sor / 2 && click && !player.getCooldownTracker().hasCooldown(this)) {
               boolean iscritt = false;
               if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
                  iscritt = NBTHelper.GetNBTboolean(itemstack, "crit");
               } else {
                  iscritt = itemRand.nextFloat() < ColorConverters.InBorder(0.2F, 0.4F, 0.2F * power);
               }

               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               player.addStat(StatList.getObjectUseStats(this));
               IWeapon.fireBomEffect(this, player, world, 0);
               Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
               if (!iscritt) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.gravelurker,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  GraveLurkerProjectile projectile = new GraveLurkerProjectile(world, player, itemstack, power, false);
                  projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.2F, 2.4F / (acc + 1));
                  world.spawnEntity(projectile);
               } else {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.gravelurker_crit,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  NBTHelper.SetNBTboolean(itemstack, false, "crit");
                  GraveLurkerProjectile projectile = new GraveLurkerProjectile(world, player, itemstack, power, true);
                  projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.2F, 2.4F / (acc + 1));
                  world.spawnEntity(projectile);
               }

               if (!player.capabilities.isCreativeMode) {
                  Mana.changeMana(player, -3.0F + sor / 2);
                  Mana.setManaSpeed(player, 0.001F);
                  itemstack.damageItem(1, player);
               }

               if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0
                  && itemRand.nextFloat() < ColorConverters.InBorder(0.2F, 0.4F, 0.2F * power)) {
                  NBTHelper.GiveNBTboolean(itemstack, false, "crit");
                  NBTHelper.SetNBTboolean(itemstack, true, "crit");
               }
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 14;
      Booom.frequency = -0.225F;
      Booom.x = 1.0F;
      Booom.y = (itemRand.nextFloat() - 0.5F) / 4.0F;
      Booom.z = (itemRand.nextFloat() - 0.5F) / 4.0F;
      Booom.power = 0.3F;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 9 - rapidity * 2;
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
