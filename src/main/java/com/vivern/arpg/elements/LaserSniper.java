package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityLaserParticle;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LaserSniper extends ItemWeapon {
   ResourceLocation texture = new ResourceLocation("arpg:textures/laser_sniper_laser.png");

   public LaserSniper() {
      this.setRegistryName("laser_sniper");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("laser_sniper");
      this.setMaxDamage(400);
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
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            float acclvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (click && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!player.getCooldownTracker().hasCooldown(this)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.lasergun,
                        SoundCategory.AMBIENT,
                        0.8F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                     float siz = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     Vec3d vec = GetMOP.PosRayTrace(edist, 1.0F, player, siz, siz);
                     AxisAlignedBB aabb = new AxisAlignedBB(
                        vec.x - siz,
                        vec.y - siz,
                        vec.z - siz,
                        vec.x + siz,
                        vec.y + siz,
                        vec.z + siz
                     );
                     List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
                     float wdamage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                     float wknockback = parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
                     if (!list.isEmpty()) {
                        for (Entity entitylivingbase : list) {
                           if (Team.checkIsOpponent(player, entitylivingbase)) {
                              Weapons.dealDamage(
                                 new WeaponDamage(itemstack, player, null, false, false, player.getPositionEyes(1.0F), WeaponDamage.laser),
                                 wdamage,
                                 player,
                                 entitylivingbase,
                                 true,
                                 wknockback
                              );
                              entitylivingbase.hurtResistantTime = 0;
                              DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_FIRE, 0.5F);
                           }
                        }
                     }

                     IWeapon.fireEffect(
                        this,
                        player,
                        world,
                        64.0,
                        (double)player.getEntityId(),
                        0.0,
                        0.0,
                        vec.x,
                        vec.y,
                        vec.z,
                        0.0,
                        0.0,
                        0.0
                     );
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }
                  }
               } else if (this.initiateMetadataReload(
                  itemstack, player, new ItemStack(ItemsRegister.IONBATTERY, 1, 1), this.getMaxAmmo(itemstack), new ItemStack(ItemsRegister.IONBATTERY, 1, 0)
               )) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.lasergun_rel,
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

   @Override
   public void effect(EntityPlayer clientplayer, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      Entity playe = world.getEntityByID((int)x);
      if (playe instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)playe;
         EntityLaserParticle laser = new EntityLaserParticle(world, player, this.texture, 0.1F, 4, 240, 1.0F, 1.0F, 1.0F, 0.5F);
         laser.setPosition(player.posX, player.posY + 1.55, player.posZ);
         world.spawnEntity(laser);
      }
   }

   public int getMaxAmmo(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(this).getEnchantedi("clipsize", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack));
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "ammo") / this.getMaxAmmo(itemstack), 0.0F, 1.0F);
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0;
   }

   @Override
   public boolean hasZoom(ItemStack itemstack) {
      return true;
   }

   @Override
   public float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.6F;
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
