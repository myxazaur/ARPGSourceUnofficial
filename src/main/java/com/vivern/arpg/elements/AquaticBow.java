package com.vivern.arpg.elements;

import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.EntityInfluence;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.MovingSoundEntity;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.mobs.HostileProjectiles;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AquaticBow extends AbstractBow {
   public static EntityInfluence swimmingArrowInfluence = new EntityInfluence(true) {
      @Override
      public void onUpdate(Entity entity) {
         if (entity.isInWater()) {
            float speedMustBe = 4.2F - entity.ticksExisted / 40.0F;
            double speed = GetMOP.getSpeed(entity);
            if (speed < speedMustBe) {
               double mult = speedMustBe / speed;
               entity.motionX *= mult;
               entity.motionY *= mult;
               entity.motionZ *= mult;
               entity.velocityChanged = true;
            }
         }
      }

      @Override
      public void onImpact(Entity entity, RayTraceResult result) {
      }

      @Override
      public void clientTick(Entity entity) {
      }
   };

   public AquaticBow() {
      super("aquatic_bow", 5000, 4.5F, 1.85F, 25, 6, 8.0F, 2.0F);
   }

   @Override
   public void bom(int param) {
      if (param >= 0) {
         Booom.lastTick = 16;
         Booom.frequency = -0.196F;
         Booom.x = -1.0F;
         Booom.y = (itemRand.nextFloat() - 0.5F) * 0.5F;
         Booom.z = (itemRand.nextFloat() - 0.5F) * 0.5F;
         Booom.power = 0.15F * (param / 10.0F);
         if (this.clientPullSound != null) {
            if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this.clientPullSound)) {
               Minecraft.getMinecraft().getSoundHandler().stopSound(this.clientPullSound);
            }

            this.clientPullSound = null;
         }
      } else if (param == -2) {
         if (this.clientPullSound != null) {
            if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this.clientPullSound)) {
               Minecraft.getMinecraft().getSoundHandler().stopSound(this.clientPullSound);
            }

            this.clientPullSound = null;
         }
      } else if (param == -1 && Minecraft.getMinecraft().player != null) {
         this.clientPullSound = new MovingSoundEntity(
            Minecraft.getMinecraft().player, this.getPullSound(), SoundCategory.PLAYERS, 1.0F, this.pullSoundPitch, false
         );
         Minecraft.getMinecraft().getSoundHandler().playSound(this.clientPullSound);
      } else if (param == -3 && Minecraft.getMinecraft().player != null) {
         this.clientPullSound = new MovingSoundEntity(
            Minecraft.getMinecraft().player, Sounds.aquatic_bow_charge, SoundCategory.PLAYERS, 1.0F, this.pullSoundPitch, false
         );
         Minecraft.getMinecraft().getSoundHandler().playSound(this.clientPullSound);
      }
   }

   @Override
   public boolean inUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected, boolean[] removePull) {
      EntityPlayer player = (EntityPlayer)entityIn;
      boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
      boolean specialAttack = NBTHelper.GetNBTboolean(itemstack, "specattack");
      if (specialAttack || click2 && player.getHeldItemMainhand() == itemstack) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
         NBTHelper.GiveNBTboolean(itemstack, false, "specattack");
         int pulling = NBTHelper.GetNBTint(itemstack, "pulling");
         if (!specialAttack) {
            NBTHelper.SetNBTboolean(itemstack, true, "specattack");
         } else if (!click2) {
            ItemStack ammo = this.findAmmo(player);
            boolean creative = player.capabilities.isCreativeMode;
            if (!ammo.isEmpty() || creative) {
               if (ammo.isEmpty()) {
                  ammo = new ItemStack(Items.ARROW);
               }

               if (pulling >= this.getCooldownTime(itemstack)) {
                  int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
                  int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.ocean_shoot,
                     SoundCategory.AMBIENT,
                     1.1F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  HostileProjectiles.OceanSpiritShoot projectile = new HostileProjectiles.OceanSpiritShoot(world, player);
                  projectile.shoot(
                     player,
                     player.rotationPitch,
                     player.rotationYaw,
                     0.0F,
                     parameters.get("waterblast_velocity"),
                     parameters.getEnchanted("waterblast_inaccuracy", acc)
                  );
                  projectile.damage = parameters.getEnchanted("waterblast_damage", might);
                  world.spawnEntity(projectile);
                  Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                  ItemArrow itemarrow = (ItemArrow)ammo.getItem();
                  int amountSpin = Math.min(ammo.getCount(), parameters.geti("arrows_spin"));

                  for (int i = 0; i < amountSpin; i++) {
                     EntityArrow entityarrow = itemarrow.createArrow(world, ammo, player);
                     if (entityarrow != null) {
                        entityarrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, parameters.get("waterblast_velocity"), 0.0F);
                        this.setDamageToArrow(entityarrow, ammo, world, player, itemstack, pulling, 0.0F, false);
                        int k = GetMOP.floatToIntWithChance(
                           parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack)), itemRand
                        );
                        if (k > 0) {
                           entityarrow.setKnockbackStrength(k);
                        }

                        if (player.capabilities.isCreativeMode) {
                           entityarrow.pickupStatus = PickupStatus.CREATIVE_ONLY;
                        }

                        world.spawnEntity(entityarrow);
                        EntityInfluence.addEntityInfluence(
                           entityarrow, new EntityInfluenceFollowRotate(false, projectile, i, parameters.get("angle_between_arrows")), 64.0
                        );
                     }
                  }

                  itemstack.damageItem(amountSpin, player);
                  if (!creative) {
                     ammo.shrink(amountSpin);
                     if (ammo.isEmpty()) {
                        player.inventory.deleteStack(ammo);
                     }
                  }

                  player.addStat(StatList.getObjectUseStats(this));
               }
            }

            NBTHelper.SetNBTboolean(itemstack, false, "specattack");
            return false;
         }

         if (pulling == 0) {
            IWeapon.fireBomEffect(this, player, world, -3);
         }

         if (pulling == 0 || player.ticksExisted % 10 == 0) {
            Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
            NBTHelper.GiveNBTint(itemstack, -10000, "arrowUsing");
            ItemStack ammo = this.findAmmo(player);
            NBTHelper.SetNBTint(itemstack, ammo.isEmpty() ? -10000 : Item.getIdFromItem(ammo.getItem()), "arrowUsing");
         }

         if (pulling < this.getCooldownTime(itemstack)) {
            NBTHelper.AddNBTint(itemstack, 1, "pulling");
         }

         removePull[0] = false;
         return false;
      } else {
         return true;
      }
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int maxPullTime = parameters.geti("max_pull_time");
      boolean specialAttack = NBTHelper.GetNBTboolean(itemstack, "specattack");
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      if (specialAttack) {
         return parameters.getEnchantedi("waterblast_cooldown", rapidity);
      } else {
         float mult = 1.0F - 0.2F * rapidity;
         return Math.round(maxPullTime * mult);
      }
   }

   @Override
   public SoundEvent getShootSound() {
      return Sounds.aquatic_bow;
   }

   @Override
   public void customizeArrow(
      EntityArrow arrow, ItemStack ammo, World world, EntityPlayer player, ItemStack bow, int pulling, float arrowvelocity, boolean isArrowUnlimit
   ) {
      if (pulling >= 17) {
         EntityInfluence.addEntityInfluence(arrow, swimmingArrowInfluence, 64.0);
      }
   }

   public static class EntityInfluenceFollowRotate extends EntityInfluence {
      public Entity toFollow;
      public int num;
      public float angleBetween = 120.0F;

      public EntityInfluenceFollowRotate(boolean synchronizeToClients, Entity toFollow, int num, float angleBetween) {
         super(synchronizeToClients);
         this.toFollow = toFollow;
         this.num = num;
         this.angleBetween = angleBetween;
      }

      @Override
      public void onUpdate(Entity entity) {
         if (this.toFollow.isEntityAlive() && this.toFollow.isAddedToWorld()) {
            double friction = 0.89;
            entity.motionX *= friction;
            entity.motionY *= friction;
            entity.motionZ *= friction;
            Vec3d motionvec = new Vec3d(-this.toFollow.motionX, -this.toFollow.motionY, -this.toFollow.motionZ);
            Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(motionvec);
            Vec3d angledVec = GetMOP.PitchYawToVec3d((float)pitchYaw.x + 30.0F, (float)pitchYaw.y);
            Vec3d rotatedVec = GetMOP.rotateVecAroundAxis(
               angledVec, motionvec, (this.angleBetween * this.num + this.toFollow.ticksExisted * 9.6F) * 0.017453F
            );
            Vec3d posTo = rotatedVec.add(this.toFollow.getPositionVector());
            SuperKnockback.applyMove(entity, -0.2F, posTo.x, posTo.y, posTo.z);
            entity.velocityChanged = true;
         }
      }

      @Override
      public void onImpact(Entity entity, RayTraceResult result) {
      }

      @Override
      public void clientTick(Entity entity) {
      }
   }
}
