package com.vivern.arpg.elements;

import com.vivern.arpg.elements.animation.EnumFlick;
import com.vivern.arpg.elements.animation.Flicks;
import com.vivern.arpg.entity.XmassRocket;
import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class XmassLauncher extends ItemWeapon {
   public static int maxammo = 8;

   public XmassLauncher() {
      this.setRegistryName("xmass_launcher");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("xmass_launcher");
      this.setMaxDamage(1000);
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
      Booom.lastTick = 25;
      Booom.frequency = -0.126F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.3F;
   }

   @Override
   public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
      if (state == 1) {
         int cooldown = this.getCooldownTime(itemstack);
         Flicks.instance.setClientAnimation(player, slot, EnumFlick.SHOOT, 0, cooldown, -1, cooldown);
      }

      if (state == 2) {
         int cooldown = this.getCooldownTime(itemstack);
         Flicks.instance.setClientAnimation(player, slot, EnumFlick.SHOOT, 0, cooldown, -1, cooldown);
         Flicks.instance.setClientAnimation(player, slot, EnumFlick.ROCKET, 0, 14, -1, 14);
      }

      if (state == 3) {
         Flicks.instance.setClientAnimation(player, slot, EnumFlick.RELOAD, 0, 60, -1, 60);
         Flicks.instance.setClientAnimation(player, slot, EnumFlick.ROCKET, 0, 14, -1, 60);
      }
   }

   public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
      return new AnimationCapabilityProvider();
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
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if ((click || click2) && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!player.getCooldownTracker().hasCooldown(this)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.xmass_launcher,
                        SoundCategory.AMBIENT,
                        1.0F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                     IWeapon.sendIWeaponState(itemstack, player, ammo > 1 ? 2 : 1, itemSlot, EnumHand.MAIN_HAND);
                     XmassRocket projectile = new XmassRocket(world, player, itemstack);
                     Weapons.shoot(
                        projectile,
                        EnumHand.MAIN_HAND,
                        player,
                        player.rotationPitch - 2.0F,
                        player.rotationYaw,
                        0.0F,
                        parameters.get("velocity"),
                        parameters.getEnchanted("inaccuracy", acc),
                        -0.11F,
                        0.5F,
                        0.6F
                     );
                     projectile.explodeInstantly = click2;
                     world.spawnEntity(projectile);
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }
                  }
               } else if (this.initiateReload(itemstack, player, ItemsRegister.XMASSBUNDLE, maxammo)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.xmass_launcher_rel,
                     SoundCategory.NEUTRAL,
                     0.75F,
                     0.95F + itemRand.nextFloat() / 10.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack) * 0.1F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 34, EnumHand.MAIN_HAND);
                  IWeapon.sendIWeaponState(itemstack, player, 3, itemSlot, EnumHand.MAIN_HAND);
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
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
