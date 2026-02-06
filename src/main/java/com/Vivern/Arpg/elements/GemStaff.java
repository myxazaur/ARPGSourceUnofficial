package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.GemStaffShoot;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GemStaff extends ItemWeapon {
   public GemStaff() {
      this.setRegistryName("gem_staff");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("gem_staff");
      this.setMaxDamage(320);
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

   public static ItemStack getStackWithGem(int gem) {
      ItemStack stack = new ItemStack(ItemsRegister.GEMSTAFF);
      NBTHelper.GiveNBTint(stack, gem, "type");
      NBTHelper.SetNBTint(stack, gem, "type");
      NBTHelper.GiveNBTint(stack, gem, "type");
      NBTHelper.SetNBTint(stack, gem, "type");
      return stack;
   }

   public static WeaponParameters getWeaponParameter(int gem) {
      return WeaponParameters.getWeaponParameters("gem_staff_" + gem);
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            float power = Mana.getMagicPowerMax(player);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            int type = NBTHelper.GetNBTint(itemstack, "type");
            WeaponParameters parameters = getWeaponParameter(type);
            EnumHand hand = player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : (player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
            Item cooldownItem = (Item)(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP);
            if (hand != null
               && (click && hand == EnumHand.MAIN_HAND || click2 && hand == EnumHand.OFF_HAND)
               && Mana.getMana(player) > parameters.getEnchanted("manacost", sor)
               && !player.getCooldownTracker().hasCooldown(cooldownItem)) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.magic_m,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               player.getCooldownTracker().setCooldown(cooldownItem, this.getCooldownTime(itemstack));
               player.addStat(StatList.getObjectUseStats(this));
               Weapons.setPlayerAnimationOnServer(player, 14, hand);
               GemStaffShoot bolt = new GemStaffShoot(world, player, itemstack, power);
               bolt.type = type;
               bolt.damage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
               bolt.knockback = parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
               bolt.livetime = parameters.getEnchantedi("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
               bolt.red = parameters.get("red");
               bolt.green = parameters.get("green");
               bolt.blue = parameters.get("blue");
               Weapons.shoot(
                  bolt,
                  hand,
                  player,
                  player.rotationPitch,
                  player.rotationYaw,
                  0.0F,
                  parameters.get("velocity"),
                  parameters.getEnchanted("inaccuracy", acc),
                  -0.1F,
                  0.5F,
                  0.2F
               );
               world.spawnEntity(bolt);
               if (!player.capabilities.isCreativeMode) {
                  Mana.changeMana(player, -parameters.getEnchanted("manacost", sor));
                  Mana.setManaSpeed(player, 0.001F);
                  itemstack.damageItem(1, player);
               }
            }
         }
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int type = NBTHelper.GetNBTint(itemstack, "type");
      WeaponParameters parameters = getWeaponParameter(type);
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return parameters.getEnchantedi("cooldown", rapidity);
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
      if (this.isInCreativeTab(tab)) {
         for (int i = 0; i <= 7; i++) {
            ItemStack stack = new ItemStack(this);

            while (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("type")) {
               NBTHelper.GiveNBTint(stack, i, "type");
               NBTHelper.SetNBTint(stack, i, "type");
            }

            items.add(stack);
         }
      }
   }

   public String getItemStackDisplayName(ItemStack stack) {
      if (stack.getItem() == ItemsRegister.GEMSTAFF) {
         int type = NBTHelper.GetNBTint(stack, "type");
         switch (type) {
            case 0:
               return "diamond staff";
            case 1:
               return "ruby staff";
            case 2:
               return "sapphire staff";
            case 3:
               return "emerald staff";
            case 4:
               return "citrine staff";
            case 5:
               return "amethyst staff";
            case 6:
               return "topaz staff";
            case 7:
               return "rhinestone staff";
         }
      }

      return super.getItemStackDisplayName(stack);
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
