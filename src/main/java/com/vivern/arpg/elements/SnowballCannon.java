package com.vivern.arpg.elements;

import com.vivern.arpg.entity.CannonSnowball;
import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.FindAmmo;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import java.util.ArrayList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SnowballCannon extends ItemWeapon {
   public static int maxammo = 32;

   public SnowballCannon() {
      this.setRegistryName("snowball_cannon");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("snowball_cannon");
      this.setMaxDamage(2300);
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
      return oldStack.getItem() != newStack.getItem();
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 7;
      Booom.frequency = -0.45F;
      Booom.x = 1.0F;
      Booom.y = (itemRand.nextFloat() - 0.5F) / 2.0F;
      Booom.z = (itemRand.nextFloat() - 0.5F) / 2.0F;
      Booom.power = 0.25F + param / 3.5F;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            ArrayList<Item> ammolist = new ArrayList<>();
            ammolist.add(Items.SNOWBALL);
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
               ammolist.add(Item.getItemFromBlock(Blocks.SNOW));
            }

            Item ammotype = FindAmmo.FindModulate(player.inventory, ammolist);
            EnumHand hand = player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : (player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
            Item cooldownItem = (Item)(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP);
            if (ammotype != null && hand != null && (click && hand == EnumHand.MAIN_HAND || click2 && hand == EnumHand.OFF_HAND)) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!player.getCooldownTracker().hasCooldown(cooldownItem)) {
                     WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                     if (ammotype == Items.SNOWBALL) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.snowball_cannon,
                           SoundCategory.AMBIENT,
                           0.8F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        player.getCooldownTracker().setCooldown(cooldownItem, this.getCooldownTime(itemstack));
                        player.addStat(StatList.getObjectUseStats(this));
                        IWeapon.fireBomEffect(this, player, world, 0);
                        Weapons.setPlayerAnimationOnServer(player, 5, hand);
                        CannonSnowball bullet = new CannonSnowball(world, player, itemstack);
                        Weapons.shoot(
                           bullet,
                           hand,
                           player,
                           player.rotationPitch - 3.5F,
                           player.rotationYaw,
                           0.2F,
                           parameters.get("velocity"),
                           parameters.getEnchanted("inaccuracy", acc),
                           -0.25F,
                           0.5F,
                           0.3F
                        );
                        bullet.damage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                        world.spawnEntity(bullet);
                        if (!player.capabilities.isCreativeMode) {
                           if (itemRand.nextFloat() < parameters.getEnchanted("ammo_consume_chance", reuse)) {
                              this.addAmmo(ammo, itemstack, -1);
                              player.inventory.clearMatchingItems(Items.SNOWBALL, -1, 1, null);
                           }

                           itemstack.damageItem(1, player);
                        }
                     } else if (ammotype == Item.getItemFromBlock(Blocks.SNOW)) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.snowball_cannon,
                           SoundCategory.AMBIENT,
                           0.8F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        int cooldown2 = parameters.getEnchantedi("cooldown_special", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack));
                        player.getCooldownTracker().setCooldown(cooldownItem, cooldown2);
                        player.addStat(StatList.getObjectUseStats(this));
                        IWeapon.fireBomEffect(this, player, world, 1);
                        Weapons.setPlayerAnimationOnServer(player, 5, hand);
                        int shots = parameters.geti("shots_special");

                        for (int ss = 0; ss < shots; ss++) {
                           CannonSnowball bullet = new CannonSnowball(world, player, itemstack);
                           Weapons.shoot(
                              bullet,
                              hand,
                              player,
                              player.rotationPitch - 3.5F,
                              player.rotationYaw,
                              0.2F,
                              parameters.get("velocity"),
                              parameters.getEnchanted("inaccuracy_special", acc),
                              -0.25F,
                              0.5F,
                              0.3F
                           );
                           bullet.damage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                           world.spawnEntity(bullet);
                        }

                        if (!player.capabilities.isCreativeMode) {
                           if (itemRand.nextFloat() < parameters.getEnchanted("ammo_consume_chance", reuse)) {
                              this.addAmmo(ammo, itemstack, -1);
                              player.inventory.clearMatchingItems(Item.getItemFromBlock(Blocks.SNOW), -1, 1, null);
                           }

                           itemstack.damageItem(3, player);
                        }
                     }
                  }
               } else if (!player.getCooldownTracker().hasCooldown(cooldownItem)) {
                  if (NBTHelper.GetNBTint(itemstack, "ammo") == 0) {
                     this.startReload(itemstack);
                     NBTHelper.GiveNBTint(itemstack, 0, "ammo");
                     NBTHelper.SetNBTint(itemstack, maxammo, "ammo");
                  } else if (this.isReloadNeed(itemstack)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.snowball_cannon_rel,
                        SoundCategory.AMBIENT,
                        0.7F,
                        0.95F + itemRand.nextFloat() / 20.0F
                     );
                     this.startReload(itemstack);
                     Weapons.setPlayerAnimationOnServer(player, 6, hand);
                  }
               }
            }
         }
      }
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "ammo") / maxammo, 0.0F, 1.0F);
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      return 40 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack) * 10;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 3 - rapidity;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
