package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityLaserParticle;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import java.util.Random;
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

public class LaserRifle extends ItemWeapon {
   ResourceLocation texture = new ResourceLocation("arpg:textures/laser_rifle_laser.png");
   ResourceLocation res = new ResourceLocation("arpg:textures/redlaser.png");

   public LaserRifle() {
      this.setRegistryName("laser_rifle");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("laser_rifle");
      this.setMaxDamage(850);
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

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         boolean removecharge = true;
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            float acclvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            float mightlvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
            int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            if (player.getHeldItemMainhand() == itemstack && click) {
               removecharge = false;
               if (!player.getCooldownTracker().hasCooldown(this)) {
                  if (ammo > 0 && this.isReloaded(itemstack)) {
                     int charge = NBTHelper.GetNBTint(itemstack, "charge");
                     if (charge == 0) {
                        int shoots = parameters.getEnchantedi("shoots", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack));
                        player.addStat(StatList.getObjectUseStats(this));
                        Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                        NBTHelper.GiveNBTint(itemstack, shoots, "charge");
                        NBTHelper.SetNBTint(itemstack, shoots, "charge");
                        if (!player.capabilities.isCreativeMode) {
                           this.addAmmo(ammo, itemstack, -1);
                           itemstack.damageItem(1, player);
                        }
                     }

                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.laser_rifle,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     float siz = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     float inaccuracy = parameters.getEnchanted("inaccuracy", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack));
                     float pit = player.rotationPitch + (itemRand.nextFloat() - 0.5F) * inaccuracy;
                     float yaw = player.rotationYaw + (itemRand.nextFloat() - 0.5F) * inaccuracy;
                     Vec3d vec = GetMOP.RotatedPosRayTrace(edist, 1.0F, player, siz, 0.03, pit, yaw);
                     float s = Math.max(siz, 0.3F);
                     AxisAlignedBB aabb = new AxisAlignedBB(
                        vec.x - s,
                        vec.y - s,
                        vec.z - s,
                        vec.x + s,
                        vec.y + s,
                        vec.z + s
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
                              DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_FIRE, 0.1F);
                           }
                        }
                     }

                     IWeapon.fireEffect(
                        this,
                        player,
                        world,
                        64.0,
                        (double)player.getEntityId(),
                        (double)pit,
                        (double)yaw,
                        vec.x,
                        vec.y,
                        vec.z,
                        0.0,
                        0.0,
                        0.0
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     NBTHelper.AddNBTint(itemstack, -1, "charge");
                  } else if (this.initiateMetadataReload(
                     itemstack,
                     player,
                     new ItemStack(ItemsRegister.IONBATTERY, 1, 1),
                     this.getMaxAmmo(itemstack),
                     new ItemStack(ItemsRegister.IONBATTERY, 1, 0)
                  )) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.laserpistol_rel,
                        SoundCategory.NEUTRAL,
                        0.7F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
                  }
               }
            }
         }

         if (removecharge) {
            NBTHelper.SetNBTint(itemstack, 0, "charge");
         }
      }
   }

   @Override
   public void effect(EntityPlayer clientplayer, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      Entity playe = world.getEntityByID((int)x);
      if (playe instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)playe;
         Random rand = new Random();

         for (int ss = 0; ss < 5; ss++) {
            Entity spelll = new GUNParticle(
               this.res,
               0.1F,
               0.02F,
               5,
               240,
               world,
               a,
               b,
               c,
               (float)rand.nextGaussian() / 14.0F,
               (float)rand.nextGaussian() / 14.0F,
               (float)rand.nextGaussian() / 14.0F,
               0.7F + (float)rand.nextGaussian() / 4.0F,
               0.7F,
               0.7F,
               true,
               0
            );
            world.spawnEntity(spelll);
         }

         EntityLaserParticle laser = new EntityLaserParticle(world, player, this.texture, 0.1F, 3, 240, 1.0F, 0.6F, 0.6F, 1.0F);
         laser.setPosition(player.posX, player.posY + 1.55, player.posZ);
         laser.rotP = (float)y;
         laser.rotW = (float)z;
         world.spawnEntity(laser);
      }
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int charge = NBTHelper.GetNBTint(itemstack, "charge");
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return charge == 1 ? parameters.getEnchantedi("cooldown", rapidity) : Math.round(parameters.getEnchanted("cooldown_small", rapidity));
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
