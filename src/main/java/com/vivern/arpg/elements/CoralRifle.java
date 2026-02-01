package com.vivern.arpg.elements;

import com.vivern.arpg.elements.armor.IItemAttacked;
import com.vivern.arpg.entity.CoralPolyp;
import com.vivern.arpg.entity.CoralRifleBullet;
import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CoralRifle extends ItemWeapon implements IItemAttacked {
   public static int maxammo = 50;

   public CoralRifle() {
      this.setRegistryName("coral_rifle");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("coral_rifle");
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

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 7;
      Booom.frequency = -0.45F;
      Booom.x = 1.0F;
      Booom.y = (itemRand.nextFloat() - 0.5F) / 4.0F;
      Booom.z = (itemRand.nextFloat() - 0.5F) / 4.0F;
      Booom.power = 0.27F;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            this.decreaseReload(itemstack, player);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (click && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!player.getCooldownTracker().hasCooldown(this)) {
                     ItemBullet bullet = ItemBullet.getItemBulletFromString(NBTHelper.GetNBTstring(itemstack, "bullet"));
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.coral_rifle,
                        SoundCategory.AMBIENT,
                        0.9F,
                        1.2F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 12, EnumHand.MAIN_HAND);
                     CoralRifleBullet projectile = new CoralRifleBullet(world, player, itemstack);
                     Weapons.shoot(
                        projectile,
                        EnumHand.MAIN_HAND,
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
                     if (bullet != null) {
                        projectile.setColor(bullet.colorR, bullet.colorG, bullet.colorB);
                     }

                     projectile.bullet = bullet;
                     projectile.livetime = parameters.getEnchantedi("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     world.spawnEntity(projectile);
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }
                  }
               } else if (this.initiateBulletReload(itemstack, player, ItemsRegister.CORALRIFLECLIP, maxammo, true)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.coral_rifle_rel,
                     SoundCategory.AMBIENT,
                     0.8F,
                     1.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
                  if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
                     NBTHelper.GiveNBTboolean(itemstack, true, "polyps");
                     NBTHelper.SetNBTboolean(itemstack, true, "polyps");
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
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return GetMOP.floatToIntWithChance(parameters.getEnchanted("cooldown", rapidity), itemRand);
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }

   @Override
   public float onAttackedWithItem(float damage, ItemStack stack, EntityPlayer player, DamageSource source) {
      if (NBTHelper.GetNBTboolean(stack, "polyps") && source.getTrueSource() instanceof EntityLiving) {
         EntityLiving entityLiving = (EntityLiving)source.getTrueSource();
         if (entityLiving.getDistance(player) < 5.0F) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
            int maxi = parameters.geti("polyps");

            for (int i = 0; i < maxi; i++) {
               CoralPolyp polyp = new CoralPolyp(
                  player.world, player, parameters.getEnchanted("polyps_damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack))
               );
               polyp.setPosition(player.posX, player.posY + player.height / 2.0F, player.posZ);
               polyp.motionX = (itemRand.nextFloat() - 0.5F) * 0.25F;
               polyp.motionY = (itemRand.nextFloat() - 0.5F) * 0.15F;
               polyp.motionZ = (itemRand.nextFloat() - 0.5F) * 0.25F;
               polyp.enemy = entityLiving;
               player.world.spawnEntity(polyp);
            }

            NBTHelper.SetNBTboolean(stack, false, "polyps");
         }
      }

      return 0.0F;
   }
}
