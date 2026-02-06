package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.elements.models.LaserModel;
import com.Vivern.Arpg.entity.EntityStreamLaserP;
import com.Vivern.Arpg.entity.HadronBlasterShoot;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.AnimatedGParticle;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.TEISRGuns;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HadronBlaster extends ItemWeapon implements IEnergyItem {
   public static ResourceLocation sparkle4 = new ResourceLocation("arpg:textures/sparkle4.png");
   public static ResourceLocation fire_beam2 = new ResourceLocation("arpg:textures/fire_beam2.png");
   public static ResourceLocation sparkles = new ResourceLocation("arpg:textures/sparkle.png");
   public static ResourceLocation hadronblast = new ResourceLocation("arpg:textures/hadron_blast.png");
   public static ResourceLocation circle = new ResourceLocation("arpg:textures/circle.png");

   public HadronBlaster() {
      this.setRegistryName("hadron_blaster");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("hadron_blaster");
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

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      this.setCanShoot(itemstack, entityIn);
      if (IWeapon.canShoot(itemstack)) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
         boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
         int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
         this.decreaseReload(itemstack, player);
         boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
         NBTHelper.GiveNBTint(itemstack, 0, "hadrons");
         NBTHelper.GiveNBTboolean(itemstack, false, "beam");
         NBTHelper.GiveNBTint(itemstack, 0, "capturetime");
         NBTHelper.GiveNBTint(itemstack, 0, "laserdelay");
         NBTHelper.GiveNBTboolean(itemstack, false, "sensor");
         boolean beam = NBTHelper.GetNBTboolean(itemstack, "beam");
         boolean shootbeam = false;
         int capturetime = NBTHelper.GetNBTint(itemstack, "capturetime");
         boolean inmainhand = player.getHeldItemMainhand() == itemstack;
         int lazerdel = NBTHelper.GetNBTint(itemstack, "laserdelay");
         boolean lazerdelAdded = false;
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
         int RFtoShoot = parameters.getEnchantedi("rf_to_shoot", reuse);
         int RFtoLazer = parameters.getEnchantedi("rf_to_lazer", reuse);
         int RF = this.getEnergyStored(itemstack);
         if (inmainhand) {
            if (player.ticksExisted % 5 == 0) {
               AxisAlignedBB aabb = player.getEntityBoundingBox().grow(15.0);
               List<HadronBlasterShoot.HadronBlasterBonus> list = world.getEntitiesWithinAABB(HadronBlasterShoot.HadronBlasterBonus.class, aabb);
               int bons = 0;
               if (!list.isEmpty()) {
                  for (HadronBlasterShoot.HadronBlasterBonus bon : list) {
                     if (!bon.follow && bon.getDistanceSq(player) < 220.0) {
                        bons++;
                     }
                  }
               }

               int hadron_price = parameters.getEnchantedi("hadron_price", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
               int hadrons_to_laser = parameters.geti("hadrons_to_laser");
               int points = NBTHelper.GetNBTint(itemstack, "hadrons") + bons * hadron_price;
               if (points > hadrons_to_laser) {
                  if (!NBTHelper.GetNBTboolean(itemstack, "sensor")) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.hadron_blaster_sensor,
                        SoundCategory.AMBIENT,
                        0.4F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     NBTHelper.SetNBTboolean(itemstack, true, "sensor");
                  }
               } else {
                  NBTHelper.SetNBTboolean(itemstack, false, "sensor");
               }
            }

            if (capturetime <= 0 && !beam && !hascooldown && click2) {
               int captureTimeTo = parameters.geti("capture_time");
               player.getCooldownTracker().setCooldown(this, captureTimeTo);
               NBTHelper.SetNBTint(itemstack, captureTimeTo, "capturetime");
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.plasma_accelerator_charge,
                  SoundCategory.AMBIENT,
                  0.5F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
            }

            if (capturetime > 0) {
               int hadr = NBTHelper.GetNBTint(itemstack, "hadrons");
               NBTHelper.AddNBTint(itemstack, -1, "capturetime");
               AxisAlignedBB aabbx = player.getEntityBoundingBox().grow(15.0);
               List<HadronBlasterShoot.HadronBlasterBonus> listx = world.getEntitiesWithinAABB(HadronBlasterShoot.HadronBlasterBonus.class, aabbx);
               int bonsx = 0;
               if (!listx.isEmpty()) {
                  for (HadronBlasterShoot.HadronBlasterBonus bonx : listx) {
                     if (!bonx.follow && bonx.getDistanceSq(player) < 220.0) {
                        bonsx++;
                        bonx.follow = true;
                        bonx.noClip = true;
                     }
                  }
               }

               int hadron_price = parameters.getEnchantedi("hadron_price", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
               int hadron_points_max = parameters.getEnchantedi("hadron_points_max", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
               int hadrons_to_laser = parameters.geti("hadrons_to_laser");
               NBTHelper.SetNBTint(itemstack, Math.min(hadr + bonsx * hadron_price, hadron_points_max), "hadrons");
               if (capturetime == 1) {
                  NBTHelper.SetNBTint(itemstack, 0, "laserdelay");
                  if (NBTHelper.GetNBTint(itemstack, "hadrons") > hadrons_to_laser) {
                     NBTHelper.SetNBTboolean(itemstack, true, "beam");
                  } else if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) < 1) {
                     NBTHelper.SetNBTint(itemstack, 0, "hadrons");
                     NBTHelper.SetNBTboolean(itemstack, false, "beam");
                  }
               }
            } else if (this.isReloaded(itemstack) && !hascooldown) {
               if (!beam) {
                  if (!world.isRemote && click && RF >= RFtoShoot) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.hadron_blaster,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 1);
                     Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                     if (!player.capabilities.isCreativeMode) {
                        itemstack.damageItem(1, player);
                        this.extractEnergyFromItem(itemstack, RFtoShoot, false);
                     }

                     HadronBlasterShoot projectile = new HadronBlasterShoot(world, player, itemstack);
                     Weapons.shoot(
                        projectile,
                        EnumHand.MAIN_HAND,
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        parameters.get("velocity"),
                        parameters.getEnchanted("inaccuracy", acc),
                        -0.05F,
                        0.5F,
                        0.3F
                     );
                     world.spawnEntity(projectile);
                  }
               } else if (click && RF >= RFtoLazer) {
                  if (lazerdel < 16) {
                     if (lazerdel == 1) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.hadron_blaster_charge,
                           SoundCategory.AMBIENT,
                           0.8F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                     }

                     if (lazerdel == 15) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.hadron_blaster_beam,
                           SoundCategory.AMBIENT,
                           0.7F,
                           0.95F + itemRand.nextFloat() / 10.0F
                        );
                        Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                     }

                     NBTHelper.AddNBTint(itemstack, 1, "laserdelay");
                     lazerdelAdded = true;
                  } else {
                     double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     Vec3d vec = GetMOP.PosRayTrace(edist, 1.0F, player, 0.8, 0.6);
                     if (!world.isRemote) {
                        if (!player.capabilities.isCreativeMode) {
                           NBTHelper.AddNBTint(itemstack, -2, "hadrons");
                        }

                        int hadronss = NBTHelper.GetNBTint(itemstack, "hadrons");
                        if (hadronss <= 0) {
                           NBTHelper.SetNBTboolean(itemstack, false, "beam");
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.hadron_blaster_end,
                              SoundCategory.AMBIENT,
                              0.7F,
                              0.9F + itemRand.nextFloat() / 5.0F
                           );
                           NBTHelper.SetNBTint(itemstack, -16, "laserdelay");
                        }

                        shootbeam = true;
                        if (player.ticksExisted % 3 == 0) {
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.hadron_blaster_beam,
                              SoundCategory.AMBIENT,
                              0.7F,
                              0.95F + itemRand.nextFloat() / 10.0F
                           );
                        }

                        if (player.ticksExisted % 23 == 0) {
                           Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                        }

                        double damageRadius = parameters.getEnchanted("damage_radius_laser", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                        AxisAlignedBB aabbxx = new AxisAlignedBB(
                           vec.x - damageRadius,
                           vec.y - damageRadius,
                           vec.z - damageRadius,
                           vec.x + damageRadius,
                           vec.y + damageRadius,
                           vec.z + damageRadius
                        );
                        List<Entity> listxx = world.getEntitiesWithinAABBExcludingEntity(player, aabbxx);
                        float wdamage_laser = parameters.getEnchanted("damage_laser", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                        float wknockback_laser = parameters.getEnchanted("knockback_laser", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
                        if (!listxx.isEmpty()) {
                           for (Entity entity : listxx) {
                              if (Team.checkIsOpponent(player, entity)) {
                                 Weapons.dealDamage(
                                    new WeaponDamage(itemstack, player, null, true, false, vec, WeaponDamage.plasma),
                                    wdamage_laser,
                                    player,
                                    entity,
                                    true,
                                    wknockback_laser,
                                    vec.x,
                                    vec.y,
                                    vec.z
                                 );
                                 entity.hurtResistantTime = 0;
                                 DeathEffects.applyDeathEffect(entity, DeathEffects.DE_FIRE, 0.9F);
                              }
                           }
                        }

                        if (!player.capabilities.isCreativeMode) {
                           if (itemRand.nextFloat() < 0.2F) {
                              itemstack.damageItem(1, player);
                           }

                           this.extractEnergyFromItem(itemstack, RFtoLazer, false);
                        }
                     }

                     if (player.ticksExisted % 3 == 0) {
                        IWeapon.fireBomEffect(this, player, world, 0);
                     }

                     double lazerHeight = player.posY + player.getEyeHeight() - 0.07;
                     float horizoffset = 0.0F;
                     if (player.getHeldItemMainhand() == itemstack) {
                        horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? 0.2F : -0.2F;
                     }

                     if (player.getHeldItemOffhand() == itemstack) {
                        horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? -0.2F : 0.2F;
                     }

                     boolean soundAndPlasma = itemRand.nextFloat() < 0.3;
                     if (soundAndPlasma) {
                        world.playSound(
                           (EntityPlayer)null,
                           vec.x,
                           vec.y,
                           vec.z,
                           Sounds.hadron_blaster_impact,
                           SoundCategory.AMBIENT,
                           0.5F,
                           0.9F + itemRand.nextFloat() / 4.0F
                        );
                     }

                     if (!world.isRemote) {
                        IWeapon.fireEffectExcl(
                           this,
                           player,
                           player,
                           world,
                           64.0,
                           vec.x,
                           vec.y,
                           vec.z,
                           player.rotationPitch,
                           player.rotationYaw,
                           1.0,
                           player.getEntityId(),
                           0.0,
                           0.0
                        );
                     } else {
                        Vec3d shootPoint = Weapons.getPlayerShootPoint(player, EnumHand.MAIN_HAND, 1.0F, 0.9F, 0.2F, -0.15F, false);
                        this.makeLaserParticles(
                           world,
                           player,
                           shootPoint.x,
                           shootPoint.y,
                           shootPoint.z,
                           vec.x,
                           vec.y,
                           vec.z,
                           true,
                           vec.distanceTo(shootPoint),
                           0.0F,
                           0.0F
                        );
                        this.particlesEffect(
                           player,
                           world,
                           vec.x,
                           vec.y,
                           vec.z,
                           player.rotationPitch,
                           player.rotationYaw,
                           horizoffset,
                           player.posX,
                           player.posY + player.getEyeHeight(),
                           player.posZ,
                           soundAndPlasma
                        );
                     }
                  }
               } else if (lazerdel > 0) {
                  if (lazerdel > 14) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.hadron_blaster_end,
                        SoundCategory.AMBIENT,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  }

                  NBTHelper.SetNBTint(itemstack, -16, "laserdelay");
                  player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               }
            }
         }

         if (lazerdel < 0 && !lazerdelAdded) {
            NBTHelper.AddNBTint(itemstack, 1, "laserdelay");
         }

         if (!world.isRemote && beam && !shootbeam) {
            if (!player.capabilities.isCreativeMode) {
               NBTHelper.AddNBTint(itemstack, -1, "hadrons");
            }

            int hadronssx = NBTHelper.GetNBTint(itemstack, "hadrons");
            if (hadronssx <= 0) {
               NBTHelper.SetNBTboolean(itemstack, false, "beam");
            }
         }
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      if (player.ticksExisted % 3 == 0) {
         world.playSound((EntityPlayer)null, x, y, z, Sounds.hadron_blaster_beam, SoundCategory.AMBIENT, 0.7F, 0.95F + itemRand.nextFloat() / 10.0F);
      }

      this.particlesEffect(player, world, x, y, z, a, b, c, 0.0, 0.0, 0.0, itemRand.nextFloat() < 0.3);
      Entity entity = world.getEntityByID((int)d1);
      if (entity != null && entity instanceof EntityPlayer) {
         EntityPlayer shootingPlayer = (EntityPlayer)entity;
         Vec3d shootPoint = Weapons.getPlayerShootPoint(shootingPlayer, EnumHand.MAIN_HAND, 1.0F, 0.9F, 0.2F, -0.15F, false);
         double dd0 = shootPoint.x - x;
         double dd1 = shootPoint.y - y;
         double dd2 = shootPoint.z - z;
         double dist = MathHelper.sqrt(dd0 * dd0 + dd1 * dd1 + dd2 * dd2);
         this.makeLaserParticles(
            world, shootingPlayer, shootPoint.x, shootPoint.y, shootPoint.z, x, y, z, c != 0.0, dist, (float)a, (float)b
         );
      }
   }

   public void particlesEffect(
      EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3, boolean b1
   ) {
      if (player.ticksExisted % 2 == 0) {
         GUNParticle bigsmoke1 = new GUNParticle(
            sparkle4,
            0.5F,
            0.0F,
            2,
            240,
            world,
            x,
            y,
            z,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            0.8F + itemRand.nextFloat() / 5.0F,
            0.3F + itemRand.nextFloat() / 10.0F,
            true,
            itemRand.nextInt(360)
         );
         bigsmoke1.alphaTickAdding = -0.15F;
         bigsmoke1.alphaGlowing = true;
         bigsmoke1.scaleTickAdding = 0.3F + itemRand.nextFloat() / 2.0F;
         bigsmoke1.randomDeath = false;
         world.spawnEntity(bigsmoke1);
      } else {
         GUNParticle bigsmoke1 = new GUNParticle(
            sparkle4,
            0.5F,
            0.0F,
            2,
            240,
            world,
            x,
            y,
            z,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            0.8F + itemRand.nextFloat() / 5.0F,
            0.3F + itemRand.nextFloat() / 10.0F,
            true,
            itemRand.nextInt(360)
         );
         bigsmoke1.alphaTickAdding = -0.15F;
         bigsmoke1.alphaGlowing = true;
         bigsmoke1.scaleTickAdding = 0.2F + itemRand.nextFloat() / 3.0F;
         bigsmoke1.randomDeath = false;
         bigsmoke1.rotationPitchYaw = new Vec2f(itemRand.nextInt(360), itemRand.nextInt(360));
         world.spawnEntity(bigsmoke1);
      }

      float bg = 0.6F + itemRand.nextFloat() * 0.4F;
      float scl = 0.03F + (float)itemRand.nextGaussian() / 20.0F;
      int lt = 10 + itemRand.nextInt(20);
      GUNParticle bigsmoke2 = new GUNParticle(
         sparkles,
         scl,
         0.01F,
         lt,
         240,
         world,
         x,
         y,
         z,
         (float)itemRand.nextGaussian() / 8.0F,
         (float)itemRand.nextGaussian() / 8.0F,
         (float)itemRand.nextGaussian() / 8.0F,
         1.0F,
         bg,
         bg,
         false,
         itemRand.nextInt(360),
         true,
         1.0F
      );
      bigsmoke2.scaleTickAdding = -scl / lt;
      world.spawnEntity(bigsmoke2);
      if (b1) {
         world.playSound((EntityPlayer)null, x, y, z, Sounds.hadron_blaster_impact, SoundCategory.AMBIENT, 0.5F, 0.9F + itemRand.nextFloat() / 4.0F);
         AnimatedGParticle anim = new AnimatedGParticle(
            hadronblast,
            2.2F,
            0.0F,
            13,
            240,
            world,
            x,
            y,
            z,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            0.9F + itemRand.nextFloat() / 10.0F,
            0.9F + itemRand.nextFloat() / 10.0F,
            true,
            itemRand.nextInt(360)
         );
         anim.framecount = 13;
         anim.alphaGlowing = true;
         anim.scaleTickAdding = 0.1F;
         anim.randomDeath = false;
         anim.rotationPitchYaw = new Vec2f(itemRand.nextInt(360), itemRand.nextInt(360));
         world.spawnEntity(anim);
      }
   }

   public void makeLaserParticles(
      World world,
      @Nullable EntityPlayer player,
      double xStart,
      double yStart,
      double zStart,
      double xEnd,
      double yEnd,
      double zEnd,
      boolean hit,
      double distance,
      float rotatPitch,
      float rotatYaw
   ) {
      EntityStreamLaserP laser = new EntityStreamLaserP(world, player, fire_beam2, 0.1F, 240, 1.0F, 0.9F, 0.9F, 0.0F, distance, 1, 0.2F, 2.0F);
      laser.setPosition(xStart, yStart, zStart);
      if (player == null) {
         laser.rotatPitch = rotatPitch;
         laser.rotatYaw = rotatYaw;
         laser.prevrotatPitch = rotatPitch;
         laser.prevrotatYaw = rotatYaw;
      }

      laser.useOldPositioning = false;
      laser.barrelLength = 0.2F;
      laser.shoulders = 0.18F;
      laser.yoffset = -0.15F;
      laser.hand = EnumHand.MAIN_HAND;
      laser.useModel = true;
      float[] color2 = ColorConverters.DecimaltoRGBA(16765440, 1.0F);
      float[] color3 = ColorConverters.DecimaltoRGBA(16734208, 0.0F);
      float[] color4 = new float[]{1.0F, 1.0F, 1.0F, 1.0F};
      laser.model = new LaserModel.LaserModelLinear(fire_beam2, 240, 2, 0.1F, 0.0F, color4, color4, 0.0F, 0.0F, 1.6F, -0.04F);
      laser.model.next = new LaserModel.LaserModelCircles(circle, 240, 5.0, 10, 8, 0.14F, 0.07F, color2, color3);
      world.spawnEntity(laser);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int singleShot) {
      if (singleShot > 0) {
         Booom.lastTick = 7;
         Booom.frequency = -0.45F;
         Booom.x = 1.0F;
         Booom.y = (itemRand.nextFloat() - 0.5F) / 4.0F;
         Booom.z = (itemRand.nextFloat() - 0.5F) / 4.0F;
         Booom.power = 0.29F;
         TEISRGuns.mainhandShoot = true;
      } else {
         Booom.lastTick = 7;
         Booom.frequency = 3.0F;
         Booom.x = (float)itemRand.nextGaussian();
         Booom.y = (float)itemRand.nextGaussian();
         Booom.z = (float)itemRand.nextGaussian();
         Booom.power = 0.15F;
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.55F;
   }

   @Override
   public boolean autoReload(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean hasZoom(ItemStack itemstack) {
      return true;
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "hadrons") > 0;
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      int hadrons_to_laser = parameters.geti("hadrons_to_laser");
      int hadronss = NBTHelper.GetNBTint(itemstack, "hadrons");
      return MathHelper.clamp((float)hadronss / hadrons_to_laser, 0.0F, 1.0F);
   }

   @Override
   public int getMaxEnergyStored(ItemStack stack) {
      return ItemAccumulator.LI_ION_CAPACITY * 2;
   }

   @Override
   public int getThroughput() {
      return ItemAccumulator.LI_ION_THROUGHPUT;
   }
}
