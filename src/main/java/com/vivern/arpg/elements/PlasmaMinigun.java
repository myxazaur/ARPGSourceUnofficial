package com.vivern.arpg.elements;

import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.ColorConverters;
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
import com.vivern.arpg.renders.BulletParticle;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlasmaMinigun extends ItemWeapon implements IEnergyItem {
   public static int maxammo = 430;

   public PlasmaMinigun() {
      this.setRegistryName("plasma_minigun");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("plasma_minigun");
      this.setMaxDamage(15000);
      this.setMaxStackSize(1);
   }

   public float getXpRepairRatio(ItemStack stack) {
      return 15.0F;
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
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
            this.decreaseReload(itemstack, player);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int RFtoShoot = parameters.getEnchantedi("rf_to_shoot", reuse);
            float deploy = NBTHelper.GetNBTfloat(itemstack, "deploy");
            float rotate = NBTHelper.GetNBTfloat(itemstack, "rotate");
            float speed = NBTHelper.GetNBTfloat(itemstack, "speed");
            int heat = NBTHelper.GetNBTint(itemstack, "heat");
            float maxSpeed = parameters.get("max_speed");
            float deployPart = parameters.getEnchanted("deploy_speed", rapidity);
            NBTHelper.GiveNBTfloat(itemstack, deploy, "prevdeploy");
            NBTHelper.GiveNBTfloat(itemstack, rotate, "prevrotate");
            NBTHelper.GiveNBTfloat(itemstack, 0.0F, "rotate");
            NBTHelper.SetNBTfloat(itemstack, deploy, "prevdeploy");
            NBTHelper.SetNBTfloat(itemstack, rotate, "prevrotate");
            NBTHelper.AddNBTfloat(itemstack, speed, "rotate");
            boolean heated = false;
            if (player.getHeldItemMainhand() == itemstack) {
               boolean sendAnim = true;
               if (click2) {
                  if (sendAnim && player.ticksExisted % 4 == 0) {
                     Weapons.setPlayerAnimationOnServer(player, 10, EnumHand.MAIN_HAND);
                     Weapons.setPlayerAnimationOnServer(player, 10, EnumHand.OFF_HAND);
                     sendAnim = false;
                  }

                  NBTHelper.GiveNBTfloat(itemstack, 0.0F, "deploy");
                  if (deploy <= 0.0F) {
                     if (!hascooldown) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.plasma_minigun_deploy,
                           SoundCategory.AMBIENT,
                           0.9F,
                           0.975F + itemRand.nextFloat() / 20.0F
                        );
                        NBTHelper.SetNBTfloat(itemstack, MathHelper.clamp(deploy + deployPart, 0.0F, 1.0F), "deploy");
                        player.getCooldownTracker().setCooldown(this, 10);
                        NBTHelper.SetNBTfloat(itemstack, 0.0F, "speed");
                     }
                  } else {
                     NBTHelper.SetNBTfloat(itemstack, MathHelper.clamp(deploy + deployPart, 0.0F, 1.0F), "deploy");
                  }
               } else {
                  if (deploy >= 1.0F) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.plasma_minigun_undeploy,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.975F + itemRand.nextFloat() / 20.0F
                     );
                     player.getCooldownTracker().setCooldown(this, 10);
                     NBTHelper.SetNBTfloat(itemstack, 0.0F, "speed");
                  }

                  NBTHelper.SetNBTfloat(itemstack, MathHelper.clamp(deploy - deployPart, 0.0F, 1.0F), "deploy");
               }

               boolean fullTurret = deploy >= 1.0F;
               if (!click) {
                  if (speed >= maxSpeed) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.plasma_minigun_stop,
                        SoundCategory.AMBIENT,
                        0.65F,
                        0.975F + itemRand.nextFloat() / 20.0F
                     );
                  }

                  NBTHelper.SetNBTfloat(itemstack, MathHelper.clamp(speed - 1.0F, 0.0F, maxSpeed), "speed");
               } else if (deploy <= 0.0F || fullTurret) {
                  NBTHelper.GiveNBTfloat(itemstack, 0.0F, "speed");
                  if (ammo > 0 && this.isReloaded(itemstack)) {
                     if (speed <= 0.0F) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.plasma_minigun_start,
                           SoundCategory.AMBIENT,
                           0.65F,
                           0.975F + itemRand.nextFloat() / 20.0F
                        );
                     } else if (speed >= maxSpeed / 2.0F && player.ticksExisted % 20 == 0) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.plasma_minigun_loop,
                           SoundCategory.AMBIENT,
                           0.65F,
                           1.0F - itemRand.nextFloat() / 20.0F
                        );
                     }

                     NBTHelper.SetNBTfloat(itemstack, MathHelper.clamp(speed + 1.0F, 0.0F, maxSpeed), "speed");
                     if (!hascooldown && this.getEnergyStored(itemstack) >= RFtoShoot && speed > 10.0F && heat >= 0) {
                        int turretMult = fullTurret ? 2 : 1;
                        String bulletname = NBTHelper.GetNBTstring(itemstack, "bullet");
                        ItemBullet bullet = ItemBullet.getItemBulletFromString(bulletname);
                        boolean nonullbullet = bullet != null;
                        float damageadd = 0.0F;
                        float knockbackadd = 0.0F;
                        if (nonullbullet) {
                           damageadd = bullet.damage;
                           knockbackadd = bullet.knockback;
                        }

                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           fullTurret ? Sounds.plasma_minigun_turret : Sounds.plasma_minigun,
                           SoundCategory.AMBIENT,
                           0.9F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                        player.addStat(StatList.getObjectUseStats(this));
                        IWeapon.fireBomEffect(this, player, world, turretMult);
                        if (fullTurret) {
                           NBTHelper.GiveNBTint(itemstack, 0, "heat");
                           NBTHelper.AddNBTint(itemstack, 1, "heat");
                           heated = true;
                           if (heat > parameters.geti("max_heat")) {
                              NBTHelper.SetNBTint(itemstack, -heat, "heat");
                           }
                        }

                        if (sendAnim) {
                           if (fullTurret) {
                              Weapons.setPlayerAnimationOnServer(player, 10, EnumHand.MAIN_HAND);
                              Weapons.setPlayerAnimationOnServer(player, 10, EnumHand.OFF_HAND);
                           } else {
                              Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                           }

                           sendAnim = false;
                        }

                        if (!player.capabilities.isCreativeMode) {
                           this.addAmmo(ammo, itemstack, -turretMult);
                           itemstack.damageItem(turretMult, player);
                           this.extractEnergyFromItem(itemstack, RFtoShoot, false);
                        }

                        for (int i = 0; i < turretMult; i++) {
                           double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                           double damageRadius = 0.235;
                           float inaccuracy = parameters.getEnchanted("inaccuracy", acc);
                           float rotP = player.rotationPitch + (float)itemRand.nextGaussian() * inaccuracy;
                           float rotY = player.rotationYaw + (float)itemRand.nextGaussian() * inaccuracy;
                           Vec3d vec = GetMOP.RotatedPosRayTrace(edist, 1.0F, player, 0.23, 0.2, rotP, rotY);
                           if (nonullbullet) {
                              bullet.onImpact(world, player, vec.x, vec.y, vec.z, null, null);
                           }

                           boolean collidesWithAny = false;
                           AxisAlignedBB aabb = new AxisAlignedBB(
                              vec.x - damageRadius,
                              vec.y - damageRadius,
                              vec.z - damageRadius,
                              vec.x + damageRadius,
                              vec.y + damageRadius,
                              vec.z + damageRadius
                           );
                           List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
                           if (world.collidesWithAnyBlock(aabb)) {
                              collidesWithAny = true;
                              world.playSound(
                                 (EntityPlayer)null,
                                 vec.x,
                                 vec.y,
                                 vec.z,
                                 Sounds.bullet,
                                 SoundCategory.AMBIENT,
                                 0.9F,
                                 0.9F + itemRand.nextFloat() / 5.0F
                              );
                           }

                           if (!list.isEmpty()) {
                              for (Entity entity : list) {
                                 if (Team.checkIsOpponent(player, entity)) {
                                    Weapons.dealDamage(
                                       new WeaponDamage(itemstack, player, null, false, true, player, WeaponDamage.bullet),
                                       parameters.getEnchanted("damage", might) + damageadd * parameters.get("bullet_damage"),
                                       player,
                                       entity,
                                       true,
                                       parameters.getEnchanted("knockback", impulse) + knockbackadd * parameters.get("bullet_knockback"),
                                       player.posX,
                                       player.posY,
                                       player.posZ
                                    );
                                    entity.hurtResistantTime = 0;
                                    collidesWithAny = true;
                                    if (nonullbullet) {
                                       bullet.onDamageCause(world, entity, player, null);
                                    }

                                    if (entity instanceof EntityLivingBase
                                       && ((EntityLivingBase)entity).getHealth() <= 0.0F
                                       && itemRand.nextFloat() < 0.2) {
                                       DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER);
                                    }
                                 }
                              }
                           }

                           int c = nonullbullet ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
                           IBlockState dustState = GetMOP.firstBlockStateContains(world, aabb, GetMOP.SOLID_BLOCKS);
                           Vec3d shootPoint = Weapons.getPlayerShootPoint(player, i == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                           shootPoint = shootPoint.add(0.0, fullTurret && player.ticksExisted % 2 == 0 ? -0.5 : -0.1, 0.0);
                           IWeapon.fireEffect(
                              this,
                              player,
                              world,
                              64.0,
                              shootPoint.x,
                              shootPoint.y,
                              shootPoint.z,
                              vec.x,
                              vec.y,
                              vec.z,
                              (double)c,
                              dustState == null ? -1.0 : Block.getStateId(dustState),
                              collidesWithAny ? turretMult : -turretMult
                           );
                        }
                     }
                  } else if (deploy <= 0.0F && this.initiateBulletReload(itemstack, player, ItemsRegister.PLASMAMINIGUNCLIP, maxammo, true)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.plasma_railgun_rel,
                        SoundCategory.AMBIENT,
                        0.7F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
                  }
               }
            }

            if (!heated) {
               int coolrate = parameters.getEnchantedi("cool_rate", rapidity);
               if (heat > 0) {
                  NBTHelper.SetNBTint(itemstack, Math.max(heat - coolrate, 0), "heat");
               } else if (heat < 0) {
                  NBTHelper.SetNBTint(itemstack, Math.min(heat + coolrate, 0), "heat");
               }
            }
         }
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      boolean fullTurret = Math.abs(d3) > 1.0;
      Vec3d from = new Vec3d(x, y, z);
      Vec3d to = new Vec3d(a, b, c);
      float dist = (float)from.distanceTo(to);
      Vec3d col = ColorConverters.DecimaltoRGB((int)d1);
      BulletParticle part = new BulletParticle(
         world, from, to, 0.1F, (int)MathHelper.clamp(dist / 4.0F, 1.0F, 6.0F), 6, dist, 0.2F, 0.7F, 1.0F, d3 > 0.0
      );
      part.smokeRed = 0.0F;
      part.smokeGreen = 0.15F;
      part.smokeBlue = 0.5F;
      part.Red2 = (float)col.x;
      part.Green2 = (float)col.y;
      part.Blue2 = (float)col.z;
      part.blockDustCount = fullTurret ? 2 : 3;
      part.smokesCount = part.blockDustCount;
      part.blockDustId = (int)d2;
      world.spawnEntity(part);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 3;
      Booom.frequency = 1.05F;
      Booom.x = (float)itemRand.nextGaussian();
      Booom.y = (float)itemRand.nextGaussian();
      Booom.z = (float)itemRand.nextGaussian();
      Booom.power = 0.2F * param;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "ammo") / maxammo, 0.0F, 1.0F);
   }

   @Override
   public int getMaxEnergyStored(ItemStack stack) {
      return ItemAccumulator.TOPAZITRON_CAPACITY * 2;
   }

   @Override
   public int getThroughput() {
      return ItemAccumulator.TOPAZITRON_THROUGHPUT;
   }
}
