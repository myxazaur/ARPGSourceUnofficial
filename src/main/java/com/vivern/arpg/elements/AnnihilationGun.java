package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityStreamLaserP;
import com.vivern.arpg.entity.GunPEmitter;
import com.vivern.arpg.main.Booom;
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
import com.vivern.arpg.renders.GUNParticle;
import java.util.List;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AnnihilationGun extends ItemWeapon {
   ResourceLocation texture = new ResourceLocation("arpg:textures/laser_sniper_laser.png");
   ResourceLocation smoke = new ResourceLocation("arpg:textures/smoke.png");
   ResourceLocation fire = new ResourceLocation("arpg:textures/bluelaser.png");
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   ResourceLocation electr = new ResourceLocation("arpg:textures/blueexplode2.png");
   ResourceLocation start = new ResourceLocation("arpg:textures/bluelazerstart.png");
   Random rand = new Random();
   public static int maxammo = 230;

   public AnnihilationGun() {
      this.setRegistryName("annihilation_gun");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("annihilation_gun");
      this.setMaxDamage(3600);
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
      Booom.lastTick = 8;
      Booom.frequency = 4.0F;
      Booom.x = (float)this.rand.nextGaussian();
      Booom.y = (float)this.rand.nextGaussian();
      Booom.z = (float)this.rand.nextGaussian();
      Booom.power = 0.14F;
   }

   @SideOnly(Side.CLIENT)
   public void sound(World world, EntityPlayer player, int intcharge, int rapidity) {
      if (intcharge == 2) {
         world.playSound(
            (EntityPlayer)null, player.posX, player.posY, player.posZ, Sounds.biglazer1, SoundCategory.AMBIENT, 1.0F, 1.0F
         );
      }

      if (intcharge == 30 - rapidity * 5) {
         world.playSound(
            (EntityPlayer)null, player.posX, player.posY, player.posZ, Sounds.biglazer2, SoundCategory.AMBIENT, 1.0F, 1.0F
         );
      }

      if (intcharge == 60 - rapidity * 10) {
         world.playSound(
            (EntityPlayer)null, player.posX, player.posY, player.posZ, Sounds.biglazer3, SoundCategory.AMBIENT, 1.0F, 1.0F
         );
      }
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      this.setCanShoot(itemstack, entityIn);
      if (IWeapon.canShoot(itemstack)) {
         EntityPlayer player = (EntityPlayer)entityIn;
         this.decreaseReload(itemstack, player);
         boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
         int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
         int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
         int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
         int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
         boolean b1 = true;
         int chargeneed = parameters.getEnchantedi("charge_need", rapidity);
         if (!itemstack.hasTagCompound()) {
            NBTTagCompound itemCompound = new NBTTagCompound();
            itemstack.setTagCompound(itemCompound);
         } else {
            if (!itemstack.getTagCompound().hasKey("charge")) {
               itemstack.getTagCompound().setInteger("charge", 0);
            }

            if ((!click || player.getHeldItemMainhand() != itemstack) && itemstack.getTagCompound().hasKey("charge")) {
               itemstack.getTagCompound().setInteger("charge", 0);
            }
         }

         int intcharge = 0;
         if (itemstack.getTagCompound().hasKey("charge")) {
            intcharge = itemstack.getTagCompound().getInteger("charge");
         }

         this.sound(world, player, intcharge, rapidity);
         if (player.getHeldItemMainhand() == itemstack && click && !player.getCooldownTracker().hasCooldown(this)) {
            if (ammo > 0 && itemstack.getTagCompound().hasKey("charge") && this.isReloaded(itemstack)) {
               itemstack.getTagCompound().setInteger("charge", itemstack.getTagCompound().getInteger("charge") + 1);
               b1 = false;
               if (!world.isRemote) {
                  int animSend = NBTHelper.GetNBTint(itemstack, "animSend");
                  if (animSend <= 0) {
                     NBTHelper.GiveNBTint(itemstack, 0, "animSend");
                     NBTHelper.SetNBTint(itemstack, 18, "animSend");
                     Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                  } else {
                     NBTHelper.AddNBTint(itemstack, -1, "animSend");
                  }
               }
            }

            float horizoffset = 0.0F;
            if (player.getHeldItemMainhand() == itemstack) {
               horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? 0.2F : -0.2F;
            }

            if (player.getHeldItemOffhand() == itemstack) {
               horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? -0.2F : 0.2F;
            }

            double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
            Vec3d vec = GetMOP.PosRayTrace(edist, 1.0F, player, 0.05, 0.05);
            if (!world.isRemote) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (intcharge > chargeneed) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.biglazer_a,
                        SoundCategory.AMBIENT,
                        0.7F,
                        1.0F
                     );
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
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
                        horizoffset,
                        player.posX,
                        player.posY,
                        player.posZ
                     );
                     double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     AxisAlignedBB aabb = new AxisAlignedBB(
                        vec.x - damageRadius,
                        vec.y - damageRadius,
                        vec.z - damageRadius,
                        vec.x + damageRadius,
                        vec.y + damageRadius,
                        vec.z + damageRadius
                     );
                     if (itemRand.nextFloat() < parameters.getEnchanted("explode_chance", rapidity)) {
                        Explosion explosion = new Explosion(
                           world, null, vec.x, vec.y, vec.z, parameters.getEnchanted("explode_power", might), true, true
                        );
                        explosion.doExplosionA();
                        explosion.doExplosionB(true);
                     }

                     List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
                     if (!list.isEmpty()) {
                        for (Entity entity : list) {
                           if (Team.checkIsOpponent(player, entity)) {
                              float kmult = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0 ? -1.0F : 1.0F;
                              Weapons.dealDamage(
                                 new WeaponDamage(itemstack, player, null, false, false, vec, WeaponDamage.explode),
                                 parameters.getEnchanted("damage", might),
                                 player,
                                 entity,
                                 true,
                                 kmult * parameters.getEnchanted("knockback", impulse),
                                 vec.x,
                                 vec.y,
                                 vec.z
                              );
                              entity.hurtResistantTime = 0;
                           }
                        }
                     }

                     if (!player.capabilities.isCreativeMode
                        && !(itemRand.nextFloat() < EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack) * 0.15F)) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }
                  }
               } else if (this.initiateReload(itemstack, player, ItemsRegister.ANTICHARGE, maxammo, ItemsRegister.STABILIZATIONCELL)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.laserpistol_rel,
                     SoundCategory.NEUTRAL,
                     0.7F,
                     0.6F / (itemRand.nextFloat() * 0.4F + 0.8F)
                  );
                  Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
                  NBTHelper.SetNBTint(itemstack, 0, "charge");
               }
            }

            if (intcharge > chargeneed && world.isRemote && ammo > 0 && this.isReloaded(itemstack)) {
               this.effect(
                  player,
                  world,
                  vec.x,
                  vec.y,
                  vec.z,
                  0.0,
                  0.0,
                  horizoffset,
                  player.posX,
                  player.posY,
                  player.posZ
               );
            }
         }

         if (b1 && !world.isRemote) {
            NBTHelper.SetNBTint(itemstack, 0, "animSend");
         }
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      if (itemRand.nextFloat() < 0.5) {
         GunPEmitter emi = new GunPEmitter(
            this.fire,
            0.3F,
            0.06F,
            30,
            240,
            world,
            x,
            y,
            z,
            (float)this.rand.nextGaussian() / 2.0F,
            (float)this.rand.nextGaussian() / 2.0F + 0.5F,
            (float)this.rand.nextGaussian() / 2.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            1,
            false,
            this.smoke,
            0.35F,
            0.0F,
            10,
            100,
            100.0F,
            100.0F,
            100.0F,
            1.0F,
            1.0F,
            1.0F,
            true
         );
         world.spawnEntity(emi);
      }

      for (int ss = 0; ss < 5; ss++) {
         Entity bigsmoke = new GUNParticle(
            this.largesmoke,
            1.5F + (float)this.rand.nextGaussian() / 5.0F,
            0.0F,
            15,
            70,
            world,
            x,
            y,
            z,
            (float)this.rand.nextGaussian() / 4.0F,
            (float)this.rand.nextGaussian() / 10.0F,
            (float)this.rand.nextGaussian() / 4.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         world.spawnEntity(bigsmoke);
      }

      Entity bigboom = new GUNParticle(
         this.electr,
         3.5F + (float)this.rand.nextGaussian(),
         0.0F,
         1,
         240,
         world,
         x,
         y,
         z,
         0.0F,
         0.0F,
         0.0F,
         1.0F,
         1.0F,
         1.0F,
         true,
         (int)Math.round(this.rand.nextGaussian() * 20.0)
      );
      world.spawnEntity(bigboom);
      double dd0 = d1 - x;
      double dd1 = d2 - y;
      double dd2 = d3 - z;
      double dist = MathHelper.sqrt(dd0 * dd0 + dd1 * dd1 + dd2 * dd2);
      if (a == 0.0 && b == 0.0) {
         EntityStreamLaserP laser = new EntityStreamLaserP(world, player, this.start, 0.2F, 240, 1.0F, 1.0F, 1.0F, 0.5F, dist, 1, 0.3F, 8.0F);
         laser.setPosition(d1, d2 + 1.55, d3);
         laser.horizOffset = (float)c;
         world.spawnEntity(laser);
      } else {
         EntityStreamLaserP laser = new EntityStreamLaserP(
            world, this.start, 0.2F, 240, 1.0F, 1.0F, 1.0F, 0.5F, dist, 1, 0.3F, 8.0F, (float)a, (float)b, player.ticksExisted
         );
         laser.setPosition(d1, d2 + 1.55, d3);
         laser.horizOffset = (float)c;
         world.spawnEntity(laser);
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
