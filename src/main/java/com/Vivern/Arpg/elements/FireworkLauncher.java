package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.DragonFireworkEntity;
import com.Vivern.Arpg.entity.FireworkEntity;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.FindAmmo;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import java.util.ArrayList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FireworkLauncher extends ItemWeapon {
   public static int maxammo = 41;

   public FireworkLauncher() {
      this.setRegistryName("firework_launcher");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("firework_launcher");
      this.setMaxDamage(1800);
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

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 7;
      Booom.frequency = -0.45F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = param / 100.0F;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int range = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (player.getHeldItemMainhand() == itemstack && click && !player.getCooldownTracker().hasCooldown(this)) {
               if (!itemstack.hasTagCompound()) {
                  NBTTagCompound itemCompound = new NBTTagCompound();
                  itemstack.setTagCompound(itemCompound);
               } else {
                  if (!itemstack.getTagCompound().hasKey("dragon")) {
                     itemstack.getTagCompound().setBoolean("dragon", false);
                  }

                  if (ammo > 0 && this.isReloaded(itemstack)) {
                     if (itemstack.getTagCompound().hasKey("dragon")) {
                        if (!itemstack.getTagCompound().getBoolean("dragon")) {
                           player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                           FireworkEntity projectile = new FireworkEntity(world, player, itemstack);
                           Weapons.shoot(
                              projectile,
                              EnumHand.MAIN_HAND,
                              player,
                              player.rotationPitch,
                              player.rotationYaw,
                              -2.0F,
                              parameters.getEnchanted("velocity", range),
                              parameters.getEnchanted("inaccuracy", acc),
                              -0.1F,
                              0.5F,
                              0.5F
                           );
                           projectile.setFireworkSize(itemRand.nextInt(parameters.getEnchantedi("max_firework_size", range)));
                           world.spawnEntity(projectile);
                           IWeapon.fireBomEffect(this, player, world, 20);
                           Weapons.setPlayerAnimationOnServer(player, 12, EnumHand.MAIN_HAND);
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.firework,
                              SoundCategory.AMBIENT,
                              1.0F,
                              0.9F + itemRand.nextFloat() / 5.0F
                           );
                           if (!player.capabilities.isCreativeMode) {
                              this.addAmmo(ammo, itemstack, -1);
                              itemstack.damageItem(1, player);
                           }
                        }

                        if (itemstack.getTagCompound().getBoolean("dragon")) {
                           if (!player.capabilities.isCreativeMode) {
                              this.addAmmo(ammo, itemstack, -maxammo);
                              itemstack.damageItem(2, player);
                           }

                           player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                           DragonFireworkEntity dragon = new DragonFireworkEntity(world, player, itemstack);
                           Weapons.shoot(
                              dragon,
                              EnumHand.MAIN_HAND,
                              player,
                              player.rotationPitch - 7.5F,
                              player.rotationYaw,
                              0.0F,
                              parameters.get("velocity_dragon"),
                              parameters.getEnchanted("inaccuracy_dragon", acc),
                              -0.1F,
                              0.5F,
                              0.5F
                           );
                           world.spawnEntity(dragon);
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.firework_dragon,
                              SoundCategory.AMBIENT,
                              1.0F,
                              0.9F + itemRand.nextFloat() / 5.0F
                           );
                           IWeapon.fireBomEffect(this, player, world, 30);
                           Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                        }
                     }
                  } else {
                     ArrayList<Item> ammolist = new ArrayList<>();
                     ammolist.add(ItemsRegister.FIREWORKPACK);
                     ammolist.add(ItemsRegister.FIREWORKDRAGON);
                     Item ammotype = FindAmmo.FindModulate(player.inventory, ammolist);
                     boolean isrelneed = this.isReloadNeed(itemstack);
                     if (ammotype != null || isrelneed) {
                        if (!isrelneed && ammo <= 0) {
                           NBTHelper.SetNBTboolean(itemstack, ammotype == ItemsRegister.FIREWORKDRAGON, "dragon");
                        }

                        if (this.initiateReload(itemstack, player, ammotype, maxammo)) {
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.firework_reload,
                              SoundCategory.NEUTRAL,
                              0.7F,
                              0.95F + itemRand.nextFloat() / 10.0F
                           );
                           Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
                        }
                     }
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
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int reloading = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack);
      return NBTHelper.GetNBTboolean(itemstack, "dragon")
         ? parameters.getEnchantedi("reload_dragon", reloading)
         : parameters.getEnchantedi("reload", reloading);
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int reloading = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return NBTHelper.GetNBTboolean(itemstack, "dragon")
         ? parameters.getEnchantedi("cooldown_dragon", reloading)
         : parameters.getEnchantedi("cooldown", reloading);
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
