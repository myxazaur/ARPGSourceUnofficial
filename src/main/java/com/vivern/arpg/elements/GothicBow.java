package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityMinigunIcicle;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.mobs.HostileProjectiles;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GothicBow extends AbstractBow {
   public GothicBow() {
      super("gothic_bow", 1600, 3.3F, 0.9F, 18, 12, 2.5F, 0.6F);
   }

   @Override
   public SoundEvent getShootSound() {
      return Sounds.gothic_bow;
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      if (b == 0.0) {
         player.world
            .playSound(
               x, y, z, this.getShootSound(), SoundCategory.PLAYERS, 0.7F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + (float)a * 0.5F, false
            );
      } else {
         player.world.playSound(x, y, z, Sounds.icicle_minigun, SoundCategory.PLAYERS, 0.9F, 1.0F + itemRand.nextFloat() * 0.3F, false);
      }
   }

   @Override
   public boolean inUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected, boolean[] removePull) {
      EntityPlayer player = (EntityPlayer)entityIn;
      boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      if (player.getHeldItemMainhand() == itemstack) {
         int ice = NBTHelper.GetNBTint(itemstack, "ice");
         if (ice < 10 || !click2) {
            if (ice >= 0) {
               return true;
            }

            if (click2) {
               if (!player.getCooldownTracker().hasCooldown(this)) {
                  int smin = parameters.geti("shards_min");
                  int smax = parameters.geti("shards_max");
                  int nn = itemRand.nextInt(smax - smin + 1) + smin;
                  int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);

                  for (int i = 0; i < nn; i++) {
                     HostileProjectiles.IceShardShoot projectile = new HostileProjectiles.IceShardShoot(world, player);
                     Weapons.shoot(
                        projectile,
                        EnumHand.MAIN_HAND,
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        parameters.get("shard_velocity"),
                        parameters.getEnchanted("shard_inaccuracy", acc) + i * 2,
                        -0.1F,
                        0.4F,
                        0.1F
                     );
                     projectile.damage = parameters.getEnchanted("shard_damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                     world.spawnEntity(projectile);
                  }

                  if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
                     for (int i = -20; i <= 20; i += 40) {
                        EntityMinigunIcicle projectile = new EntityMinigunIcicle(world, player);
                        Vec3d look = GetMOP.PosRayTrace(48.0, 1.0F, player, true, 0.6, 0.5);
                        Vec3d randlook = GetMOP.PitchYawToVec3d(
                              player.rotationPitch + (itemRand.nextFloat() - 0.5F) * 22.0F,
                              player.rotationYaw + i + (itemRand.nextFloat() - 0.5F) * 12.0F
                           )
                           .scale(2.0);
                        Vec3d randpos = randlook.add(player.getPositionEyes(1.0F));
                        projectile.setPosition(randpos.x, randpos.y - 0.3, randpos.z);
                        SuperKnockback.setMove(projectile, -2.8F, look.x, look.y, look.z);
                        ProjectileHelper.rotateTowardsMovement(projectile, 1.0F);
                        world.spawnEntity(projectile);
                     }
                  }

                  IWeapon.fireBomEffect(this, player, world, 10);
                  Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                  IWeapon.fireEffect(this, player, world, 64.0, player.posX, player.posY, player.posZ, 10.0, 1.0, 0.0, 0.0, 0.0, 0.0);
                  NBTHelper.AddNBTint(itemstack, 1, "ice");
                  int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
                  int cd = (int)(GetMOP.softfromto(-ice, 0.0F, 11.0F) * 6.0F + 2.0F);
                  player.getCooldownTracker().setCooldown(this, cd - rapidity);
               } else {
                  int add = 2;
                  if (ice > -3) {
                     add = 4;
                  } else if (ice > -6) {
                     add = 3;
                  }

                  NBTHelper.AddNBTint(itemstack, add, "pulling");
                  removePull[0] = false;
               }
            } else if (player.ticksExisted % parameters.getEnchantedi("charges_decay_rate", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack))
               == 0) {
               NBTHelper.AddNBTint(itemstack, 1, "ice");
            }

            return false;
         }

         NBTHelper.SetNBTint(itemstack, -ice, "ice");
         Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
      }

      return true;
   }

   public int getMaxIceCharges(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(this).getEnchantedi("max_charges", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack));
   }

   public int getChargesToShoot() {
      return WeaponParameters.getWeaponParameters(this).geti("charges_to_shoot");
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)Math.abs(NBTHelper.GetNBTint(itemstack, "ice")) / this.getChargesToShoot(), 0.0F, 1.0F);
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "ice") != 0;
   }

   @Override
   public boolean createAndShootArrow(ItemStack ammo, World world, EntityPlayer player, ItemStack bow, int pulling, float arrowvelocity, boolean isArrowUnlimit) {
      NBTHelper.GiveNBTint(bow, 0, "ice");
      NBTHelper.SetNBTint(bow, Math.min(NBTHelper.GetNBTint(bow, "ice") + 1, this.getMaxIceCharges(bow)), "ice");
      return super.createAndShootArrow(ammo, world, player, bow, pulling, arrowvelocity, isArrowUnlimit);
   }
}
