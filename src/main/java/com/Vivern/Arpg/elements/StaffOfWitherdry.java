//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.ShootOfWitherdry;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.AnimatedGParticle;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StaffOfWitherdry extends ItemWeapon {
   public static ResourceLocation flame_big = new ResourceLocation("arpg:textures/flame_big2.png");
   public static ResourceLocation flamethrow = new ResourceLocation("arpg:textures/flamethrow.png");
   public static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   public static Predicate<EntityLivingBase> BURNING_ENTITIES = new Predicate<EntityLivingBase>() {
      public boolean apply(EntityLivingBase input) {
         return input.isBurning();
      }
   };
   public static ParticleTracker.TrackerSmoothShowHide tracker = new ParticleTracker.TrackerSmoothShowHide(
      new Vec3d[]{new Vec3d(5.0, 20.0, 0.066), new Vec3d(23.0, 38.0, -0.066)}, null
   );
   public static ParticleTracker.TrackerSmoothShowHide tracker2 = new ParticleTracker.TrackerSmoothShowHide(null, new Vec3d[]{new Vec3d(0.0, 3.0, 0.066)});

   public StaffOfWitherdry() {
      this.setRegistryName("staff_of_witherdry");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("staff_of_witherdry");
      this.setMaxDamage(13600);
      this.setMaxStackSize(1);
   }

   public float getXpRepairRatio(ItemStack stack) {
      return 14.0F;
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
      if (param == -1) {
         Booom.lastTick = 30;
         Booom.frequency = -0.39F;
         Booom.x = 0.0F;
         Booom.y = (float)(itemRand.nextGaussian() / 8.0);
         Booom.z = 1.0F;
         Booom.power = 0.2F;
         Booom.FOVlastTick = 20;
         Booom.FOVfrequency = -0.157F;
         Booom.FOVpower = 0.02F;
      } else if (param == -2) {
         Booom.lastTick = 18;
         Booom.frequency = -0.175F;
         Booom.x = 0.0F;
         Booom.y = 0.0F;
         Booom.z = itemRand.nextFloat() < 0.5 ? 1.0F : -1.0F;
         Booom.power = 0.22F;
      } else {
         Booom.lastTick = 16;
         Booom.frequency = -0.39F;
         Booom.x = 0.6F;
         Booom.y = (float)(itemRand.nextGaussian() / 8.0);
         Booom.z = (float)(itemRand.nextGaussian() / 3.0);
         Booom.power = 0.055F * param / 32.0F;
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
   }

   public static int getMaxFiretime(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(itemstack.getItem())
         .getEnchantedi("maxfire_time", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack));
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (world.isRemote) {
         int fire = NBTHelper.GetNBTint(itemstack, "fire");
         if (fire > 0) {
            float fireValue = (float)fire / getMaxFiretime(itemstack);
            float fireBig = GetMOP.softfromto(fireValue, 0.4F, 0.5F);
            float fireExponent = GetMOP.softfromto(fireValue, 0.0F, 0.15F) * 5.0F
               + GetMOP.softfromto(fireValue, 0.15F, 0.5F) * 7.0F
               + GetMOP.softfromto(fireValue, 0.5F, 1.0F) * 20.0F;
            float fireExtra = fireBig - GetMOP.softfromto(fireValue, 0.5F, 0.6F);
            Vec3d shootPoint = entityIn.getPositionVector()
               .add(0.0, 1.2, 0.0)
               .add(GetMOP.PitchYawToVec3d(entityIn.rotationPitch, entityIn.rotationYaw));
            int lt = (int)fireExponent;
            GUNParticle particle = new GUNParticle(
               flame_big,
               0.1F + 0.1F * fireExtra,
               -0.003F,
               lt,
               190 + (int)(50.0F * fireBig),
               world,
               shootPoint.x,
               shootPoint.y,
               shootPoint.z,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F - fireExtra * 0.3F,
               1.0F - fireExtra * 0.3F,
               true,
               itemRand.nextInt(360),
               true,
               1.4F
            );
            particle.alphaGlowing = true;
            particle.scaleTickAdding = 0.04347F + 0.015F * fireBig;
            particle.alphaTickAdding = -1.0F / lt;
            particle.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, 0.0F, 1.0F, 3.0F, 0.3F);
            world.spawnEntity(particle);
            lt = (int)fireExponent / 2;
            float colRG = 0.7F + fireBig * 0.3F;
            AnimatedGParticle anim = new AnimatedGParticle(
               flamethrow,
               0.02F + 0.1F * fireExtra,
               -0.003F,
               lt,
               240,
               world,
               shootPoint.x,
               shootPoint.y,
               shootPoint.z,
               0.0F,
               0.0F,
               0.0F,
               colRG,
               colRG,
               1.0F,
               true,
               itemRand.nextInt(360),
               true,
               1.4F
            );
            anim.framecount = 16;
            anim.scaleTickAdding = 0.035F + 0.01F * fireExtra;
            anim.alphaGlowing = true;
            anim.randomDeath = false;
            anim.animDelay = 1;
            anim.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, 0.0F, 1.0F, 1.0F, 0.3F);
            anim.tracker = tracker2;
            world.spawnEntity(anim);
            lt = (int)(fireExponent * 1.1875F);
            particle = new GUNParticle(
               largesmoke,
               0.1F,
               -0.0045F,
               lt,
               -1,
               world,
               shootPoint.x,
               shootPoint.y,
               shootPoint.z,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               itemRand.nextInt(360),
               true,
               1.4F
            );
            particle.scaleTickAdding = 0.044F;
            particle.alpha = 0.0F;
            particle.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, 0.0F, 0.95F, 2.0F, 0.3F);
            particle.tracker = tracker;
            world.spawnEntity(particle);
         }
      } else {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            int ran = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack);
            float power = Mana.getMagicPowerMax(player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int fire = NBTHelper.GetNBTint(itemstack, "fire");
            NBTHelper.GiveNBTint(itemstack, 0, "fdelay");
            int firedelay = NBTHelper.GetNBTint(itemstack, "fdelay");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (player.getHeldItemMainhand() == itemstack) {
               if (firedelay > 0) {
                  NBTHelper.AddNBTint(itemstack, -1, "fdelay");
               }

               if (firedelay == 1) {
                  double raduis = 10.0;
                  Vec3d vec1 = GetMOP.PitchYawToVec3d(player.rotationPitch, player.rotationYaw);
                  Vec3d vec2 = entityIn.getPositionVector().add(0.0, 1.2, 0.0);
                  Vec3d shootPoint = vec2.add(vec1);
                  Vec3d aabbPoint = vec2.add(vec1.scale(raduis));
                  List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, GetMOP.newAABB(aabbPoint, raduis), BURNING_ENTITIES);
                  int amountTargets = 3 + MathHelper.clamp((list.size() - 2) / 2, 0, 5);
                  EntityLivingBase[] targets = new EntityLivingBase[amountTargets];
                  double[] distances = new double[amountTargets];

                  for (int i = 0; i < amountTargets; i++) {
                     distances[i] = Double.MAX_VALUE;
                  }

                  for (EntityLivingBase elb : list) {
                     if (Team.checkIsOpponent(player, elb)) {
                        double distSq = player.getDistanceSq(elb);
                        this.placeToDistArray(targets, elb, distances, distSq, 0, amountTargets);
                     }
                  }

                  for (int i = 0; i < amountTargets; i++) {
                     double yy;
                     double zz;
                     double xx;
                     if (targets[i] != null) {
                        xx = targets[i].posX;
                        yy = targets[i].posY + targets[i].height / 2.0F;
                        zz = targets[i].posZ;
                     } else {
                        Vec3d vec3 = GetMOP.PitchYawToVec3d(
                           player.rotationPitch + (float)itemRand.nextGaussian() * 2.0F, player.rotationYaw + (float)itemRand.nextGaussian() * 4.0F
                        );
                        Vec3d vec4 = vec2.add(vec3.scale(8.0));
                        xx = vec4.x;
                        yy = vec4.y;
                        zz = vec4.z;
                     }

                     double d3 = xx - shootPoint.x;
                     double d4 = yy - shootPoint.y;
                     double d5 = zz - shootPoint.z;
                     EntityWitherSkull shoot = new EntityWitherSkull(world, player, d3, d4, d5);
                     shoot.setPosition(
                        shootPoint.x + (itemRand.nextFloat() - 0.5) / 2.0,
                        shootPoint.y + (itemRand.nextFloat() - 0.5) / 4.0,
                        shootPoint.z + (itemRand.nextFloat() - 0.5) / 2.0
                     );
                     world.spawnEntity(shoot);
                  }
               }

               if (!player.getCooldownTracker().hasCooldown(this)) {
                  if (click) {
                     if (player.ticksExisted % 2 == 0) {
                        if (!(Mana.getMana(player) > parameters.getEnchanted("manacost", sor))) {
                           NBTHelper.SetNBTint(itemstack, 0, "fire");
                        } else {
                           NBTHelper.GiveNBTint(itemstack, 0, "fire");
                           NBTHelper.GiveNBTint(itemstack, 0, "charge");
                           int maxFire = getMaxFiretime(itemstack);
                           if (fire < maxFire) {
                              NBTHelper.AddNBTint(itemstack, 1, "fire");
                           }

                           float fireValue = (float)fire / maxFire;
                           float fireExponent = GetMOP.softfromto(fireValue, 0.0F, 0.15F) * 5.0F
                              + GetMOP.softfromto(fireValue, 0.15F, 0.5F) * 7.0F
                              + GetMOP.softfromto(fireValue, 0.5F, 1.0F) * parameters.getEnchanted("livetime", ran);
                           if (fire == maxFire / 2) {
                              IWeapon.fireBomEffect(this, player, world, -1);
                              world.playSound(
                                 (EntityPlayer)null,
                                 player.posX,
                                 player.posY,
                                 player.posZ,
                                 Sounds.staff_of_witherdry_flare,
                                 SoundCategory.AMBIENT,
                                 0.9F,
                                 0.9F + itemRand.nextFloat() / 5.0F
                              );
                           } else if (player.ticksExisted % 4 == 0) {
                              IWeapon.fireBomEffect(this, player, world, (int)fireExponent);
                           }

                           if (fire == 0 || fire < maxFire && fire % 5 == 0 || fire >= maxFire && player.ticksExisted % 10 == 0) {
                              Weapons.setPlayerAnimationOnServer(player, 22, EnumHand.MAIN_HAND);
                           }

                           if (fire == 0) {
                              world.playSound(
                                 (EntityPlayer)null,
                                 player.posX,
                                 player.posY,
                                 player.posZ,
                                 Sounds.staff_of_witherdry_start,
                                 SoundCategory.AMBIENT,
                                 0.9F,
                                 0.9F + itemRand.nextFloat() / 5.0F
                              );
                           } else if (fire > 5 && player.ticksExisted % 14 == 0) {
                              if (fire < maxFire / 2) {
                                 world.playSound(
                                    (EntityPlayer)null,
                                    player.posX,
                                    player.posY,
                                    player.posZ,
                                    Sounds.staff_of_witherdry_low,
                                    SoundCategory.AMBIENT,
                                    0.9F,
                                    0.9F + itemRand.nextFloat() / 5.0F
                                 );
                              } else if (fire > maxFire / 2) {
                                 world.playSound(
                                    (EntityPlayer)null,
                                    player.posX,
                                    player.posY,
                                    player.posZ,
                                    Sounds.staff_of_witherdry_high,
                                    SoundCategory.AMBIENT,
                                    1.1F,
                                    0.9F + itemRand.nextFloat() / 5.0F
                                 );
                              }
                           }

                           player.addStat(StatList.getObjectUseStats(this));
                           Vec3d shootPoint = entityIn.getPositionVector()
                              .add(0.0, 1.2, 0.0)
                              .add(GetMOP.PitchYawToVec3d(entityIn.rotationPitch, entityIn.rotationYaw));
                           ShootOfWitherdry shoot = new ShootOfWitherdry(world, player, itemstack, power);
                           shoot.setPosition(shootPoint.x, shootPoint.y, shootPoint.z);
                           shoot.shoot(
                              player, player.rotationPitch, player.rotationYaw, 0.0F, parameters.get("velocity"), parameters.getEnchanted("inaccuracy", acc)
                           );
                           shoot.livetime = (int)fireExponent;
                           world.spawnEntity(shoot);
                           if (!player.capabilities.isCreativeMode) {
                              Mana.changeMana(player, -parameters.getEnchanted("manacost", sor));
                              Mana.setManaSpeed(player, 0.001F);
                              itemstack.damageItem(1, player);
                           }
                        }
                     }
                  } else if (click2) {
                     int charge = NBTHelper.GetNBTint(itemstack, "charge");
                     if (charge > 0) {
                        NBTHelper.SetNBTint(itemstack, 5, "fdelay");
                        player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.staff_of_witherdry_skull,
                           SoundCategory.AMBIENT,
                           1.0F,
                           0.95F + itemRand.nextFloat() / 10.0F
                        );
                        Weapons.setPlayerAnimationOnServer(player, 24, EnumHand.MAIN_HAND);
                        IWeapon.fireBomEffect(this, player, world, -2);
                        if (!player.capabilities.isCreativeMode) {
                           itemstack.damageItem(1, player);
                           if (itemRand.nextFloat()
                              < parameters.getEnchanted("charge_consume_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack))) {
                              NBTHelper.AddNBTint(itemstack, -1, "charge");
                           }
                        }
                     }
                  }
               }

               if (!click) {
                  NBTHelper.SetNBTint(itemstack, 0, "fire");
               }
            } else {
               NBTHelper.SetNBTint(itemstack, 0, "fire");
               NBTHelper.SetNBTint(itemstack, 0, "fdelay");
            }
         } else {
            NBTHelper.SetNBTint(itemstack, 0, "fire");
            NBTHelper.SetNBTint(itemstack, 0, "fdelay");
         }
      }
   }

   public void placeToDistArray(EntityLivingBase[] targets, EntityLivingBase elb, double[] distances, double distSq, int index, int amountTargets) {
      if (index < amountTargets) {
         if (distSq < distances[index]) {
            EntityLivingBase lastElb = targets[index];
            double lastDistSq = distances[index];
            distances[index] = distSq;
            targets[index] = elb;
            this.placeToDistArray(targets, lastElb, distances, lastDistSq, index + 1, amountTargets);
         } else {
            this.placeToDistArray(targets, elb, distances, distSq, index + 1, amountTargets);
         }
      }
   }

   public static int maxcharge(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(itemstack.getItem())
         .getEnchantedi("max_charge", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "charge") / maxcharge(itemstack), 0.0F, 1.0F);
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "charge") > 0;
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
