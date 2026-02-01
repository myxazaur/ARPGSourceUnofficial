package com.vivern.arpg.elements;

import com.vivern.arpg.entity.CrystalFanShoot;
import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystalFan extends ItemWeapon {
   boolean bbom = false;

   public CrystalFan() {
      this.setRegistryName("crystal_fan");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("crystal_fan");
      this.setMaxDamage(1000);
      this.setMaxStackSize(1);
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!worldIn.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            int damage = itemstack.getItemDamage();
            World world = player.getEntityWorld();
            Item itemIn = itemstack.getItem();
            EnumHand hand = player.getActiveHand();
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            float power = Mana.getMagicPowerMax(player);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            boolean spec = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0;
            int charge = NBTHelper.GetNBTint(itemstack, "charge");
            boolean powerOn = NBTHelper.GetNBTboolean(itemstack, "power");
            if (player.getHeldItemMainhand() == itemstack) {
               if (charge >= (spec ? 16 : 8) && !powerOn) {
                  NBTHelper.SetNBTboolean(itemstack, true, "power");
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.voltrident_boost,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.95F + itemRand.nextFloat() / 5.0F
                  );
               } else if (charge < 1 && powerOn) {
                  NBTHelper.SetNBTboolean(itemstack, false, "power");
               }

               if (Mana.getMana(player) > 0.7F - sor / 7.0F && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
                  if (powerOn) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.swosh_a,
                        SoundCategory.AMBIENT,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     this.bom(true);
                     NBTHelper.AddNBTint(itemstack, -1, "charge");

                     for (int i = -20; i <= 20; i += 5) {
                        CrystalFanShoot projectile = new CrystalFanShoot(world, player, itemstack, power, true);
                        projectile.shoot(player, player.rotationPitch, player.rotationYaw + i, 0.0F, 1.7F, 1.0F / (acc + 1));
                        world.spawnEntity(projectile);
                     }
                  } else {
                     NBTHelper.GiveNBTint(itemstack, 0, "charge");
                     NBTHelper.GiveNBTboolean(itemstack, false, "power");
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.crystal_fan,
                        SoundCategory.AMBIENT,
                        0.7F,
                        0.95F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     this.bom(false);
                     if (!player.capabilities.isCreativeMode) {
                        Mana.changeMana(player, -0.7F + sor / 7.0F);
                        Mana.setManaSpeed(player, 0.001F);
                     }

                     int nn = itemRand.nextInt(3) + 1;

                     for (int i = 0; i < nn; i++) {
                        CrystalFanShoot projectile = new CrystalFanShoot(world, player, itemstack, power, false);
                        projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, (2.0F + i) / (acc + 1));
                        world.spawnEntity(projectile);
                     }
                  }
               }
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   private void bom(boolean power) {
      if (power) {
         Booom.lastTick = 25;
         Booom.frequency = -0.125F;
         Booom.x = itemRand.nextFloat() / 5.0F - 0.1F;
         Booom.y = this.bbom ? 1.0F : -1.0F;
         Booom.z = 0.0F;
         Booom.power = -0.37F;
         this.bbom = !this.bbom;
      } else {
         Booom.lastTick = 15;
         Booom.frequency = -0.45F;
         Booom.x = 1.0F;
         Booom.y = itemRand.nextFloat() < 0.5 ? 1.0F : -1.0F;
         Booom.z = 0.0F;
         Booom.power = -0.17F;
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return NBTHelper.GetNBTboolean(itemstack, "power") ? 14 - rapidity * 2 : 6 - rapidity;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      return 0;
   }

   @Override
   public float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.0F;
   }

   @Override
   public boolean autoReload(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean hasZoom(ItemStack itemstack) {
      return false;
   }
}
