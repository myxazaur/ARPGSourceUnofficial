package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.blocks.BlockFluidPoison;
import com.Vivern.Arpg.blocks.FrostfireExplosive;
import com.Vivern.Arpg.entity.CannonSnowball;
import com.Vivern.Arpg.entity.EnderSeerProjectile;
import com.Vivern.Arpg.entity.EntityArcaneExplode;
import com.Vivern.Arpg.entity.EntityCubicParticle;
import com.Vivern.Arpg.entity.EntityFrostBolt;
import com.Vivern.Arpg.entity.EntityFrostfireExplosive;
import com.Vivern.Arpg.entity.EntitySlimeBullet;
import com.Vivern.Arpg.entity.GemStaffShoot;
import com.Vivern.Arpg.entity.IEntitySynchronize;
import com.Vivern.Arpg.entity.LavaDropperShoot;
import com.Vivern.Arpg.entity.ShellShard;
import com.Vivern.Arpg.entity.ViolenceShoot;
import com.Vivern.Arpg.entity.WandColdShoot;
import com.Vivern.Arpg.entity.XmassBall;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Ln;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.mobs.HostileProjectiles;
import com.Vivern.Arpg.network.PacketBulletEffectToClients;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class ItemRocket extends Item {
   public static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   public static ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");
   public static ResourceLocation frostlight = new ResourceLocation("arpg:textures/frostlight.png");
   public static ResourceLocation snow = new ResourceLocation("arpg:textures/snowflake3.png");
   public static ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");
   public static ResourceLocation frostexpl = new ResourceLocation("arpg:textures/frozen_explode.png");
   public static ResourceLocation frostcircle = new ResourceLocation("arpg:textures/frozen_circle.png");
   public static ResourceLocation firecircle = new ResourceLocation("arpg:textures/fire_circle.png");
   public static ResourceLocation toxicspell = new ResourceLocation("arpg:textures/toxic_spell.png");
   public static ResourceLocation sparkle4 = new ResourceLocation("arpg:textures/sparkle4.png");
   public static ResourceLocation purple_smoke = new ResourceLocation("arpg:textures/purple_smoke.png");
   public static ResourceLocation blob = new ResourceLocation("arpg:textures/blob.png");
   public static ResourceLocation slimesplash = new ResourceLocation("arpg:textures/slimesplash.png");
   public static ResourceLocation void_explode = new ResourceLocation("arpg:textures/void_explode.png");
   public static ResourceLocation magic_rocket = new ResourceLocation("arpg:textures/magic_rocket.png");
   public static ResourceLocation star2 = new ResourceLocation("arpg:textures/star2.png");
   public static ResourceLocation arcane_rocket = new ResourceLocation("arpg:textures/arcane_rocket.png");
   public static ResourceLocation snow_cloth = new ResourceLocation("arpg:textures/snow_cloth.png");
   public static ResourceLocation snow2 = new ResourceLocation("arpg:textures/snowflake2.png");
   public static ParticleTracker trackerExplode1 = new ParticleTracker.TrackerSmoothShowHide(
      null, new Vec3d[]{new Vec3d(-1.0, 4.0, 0.2F), new Vec3d(3.0, 7.0, 0.1F), new Vec3d(7.0, 100.0, -0.025F)}
   );
   public static ParticleTracker trackerRandom1 = new ParticleTracker.TrackerRandomMotion(null, 0.04F, 6);
   public static ParticleTracker trackerExplode2 = new ParticleTracker.TrackerSmoothShowHide(
      null, new Vec3d[]{new Vec3d(-1.0, 6.0, 0.2F), new Vec3d(5.0, 10.0, 0.07F), new Vec3d(10.0, 20.0, -0.2F)}
   );
   public static List<ItemRocket> rocketsRegister = new ArrayList<>();
   public float colorR = 1.0F;
   public float colorG = 1.0F;
   public float colorB = 1.0F;
   public String NBTname = "";
   public int id;

   public ItemRocket(String name, String NBTname, int maxstacksize, float damage, float knockback, float colorR, float colorG, float colorB, int id) {
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey(name);
      this.setMaxStackSize(maxstacksize);
      this.colorR = colorR;
      this.colorG = colorG;
      this.colorB = colorB;
      this.NBTname = NBTname;
      this.id = id;
   }

   public static void init() {
      registerRocket((ItemRocket)ItemsRegister.ROCKETCOMMON);
      registerRocket((ItemRocket)ItemsRegister.ROCKETFROSTFIRE);
      registerRocket((ItemRocket)ItemsRegister.ROCKETCHEMICAL);
      registerRocket((ItemRocket)ItemsRegister.ROCKETNAPALM);
      registerRocket((ItemRocket)ItemsRegister.ROCKETDEMOLISHING);
      registerRocket((ItemRocket)ItemsRegister.ROCKETMINING);
      registerRocket((ItemRocket)ItemsRegister.ROCKETVOID);
      registerRocket((ItemRocket)ItemsRegister.ROCKETWATERBLAST);
      registerRocket((ItemRocket)ItemsRegister.ROCKETARCANE);
      registerRocket((ItemRocket)ItemsRegister.ROCKETSURPRISE);
      registerRocket((ItemRocket)ItemsRegister.ROCKETSHELL);
   }

   public static void registerRocket(ItemRocket item) {
      if (!rocketsRegister.contains(item)) {
         rocketsRegister.add(item);
      }
   }

   @Nullable
   public static ItemRocket getItemRocketFromString(String st) {
      for (ItemRocket r : rocketsRegister) {
         if (r.getNbtName().equals(st)) {
            return r;
         }
      }

      return null;
   }

   @Nullable
   public static ItemRocket getItemRocketFromId(int id) {
      for (ItemRocket r : rocketsRegister) {
         if (r.id == id) {
            return r;
         }
      }

      return null;
   }

   public float damage() {
      return WeaponParameters.getWeaponParameters(this).get("damage");
   }

   public float knockback() {
      return WeaponParameters.getWeaponParameters(this).get("knockback");
   }

   public float damageRadius(ItemStack weaponstack) {
      return WeaponParameters.getWeaponParameters(this).getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, weaponstack));
   }

   public String getNbtName() {
      return this.NBTname;
   }

   public boolean explode(
      World world,
      EntityLivingBase player,
      double x,
      double y,
      double z,
      @Nullable RayTraceResult result,
      @Nullable Entity projectile,
      ItemStack weaponstack,
      float weaponDamage,
      float weaponKnockback
   ) {
      return true;
   }

   public void onProjectileUpdate(Entity projectile, ItemStack weaponstack) {
   }

   public static void onEffectPacket(World world, double x, double y, double z, double a, double b, double c, int id) {
      if (id == -1) {
         world.playSound(x, y, z, Sounds.explode, SoundCategory.PLAYERS, 1.2F, 0.9F + itemRand.nextFloat() / 5.0F, false);

         for (int ss = 0; ss < 7; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               largesmoke,
               0.6F + itemRand.nextFloat() / 2.0F,
               -0.001F,
               40,
               60,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 13.0F,
               (float)itemRand.nextGaussian() / 20.0F,
               (float)itemRand.nextGaussian() / 13.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               itemRand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.01F;
            world.spawnEntity(bigsmoke);
         }

         for (int ss = 0; ss < 10; ss++) {
            int lt = 19 + itemRand.nextInt(10);
            GUNParticle fire = new GUNParticle(
               flame,
               0.1F + (float)itemRand.nextGaussian() / 6.0F,
               -0.003F,
               lt,
               240,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 9.0F,
               (float)itemRand.nextGaussian() / 9.0F,
               (float)itemRand.nextGaussian() / 9.0F,
               1.0F - itemRand.nextFloat() * 0.2F,
               1.0F - itemRand.nextFloat() * 0.2F,
               0.7F,
               true,
               itemRand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -1.0F / lt;
            fire.alphaGlowing = true;
            fire.tracker = trackerExplode1;
            world.spawnEntity(fire);
         }
      }

      if (id == -2) {
         world.playSound(x, y, z, Sounds.frozen_explode, SoundCategory.PLAYERS, 1.3F, 1.0F + itemRand.nextFloat() * 0.4F, false);

         for (int ss = 0; ss < 5; ss++) {
            int lt = 30 + itemRand.nextInt(20);
            GUNParticle bigsmoke = new GUNParticle(
               largecloud,
               (float)(1.0 + itemRand.nextGaussian() / 6.0),
               -0.005F,
               lt,
               240,
               world,
               x,
               y,
               z,
               (float)(itemRand.nextGaussian() / 10.0),
               (float)(itemRand.nextGaussian() / 10.0),
               (float)(itemRand.nextGaussian() / 10.0),
               0.5F + itemRand.nextFloat() / 10.0F,
               1.0F,
               1.0F,
               true,
               itemRand.nextInt(360)
            );
            bigsmoke.alphaGlowing = true;
            bigsmoke.alphaTickAdding = -1.0F / lt;
            world.spawnEntity(bigsmoke);
         }

         int countOfParticles = 8;
         float R = (float)((0.08 + itemRand.nextGaussian() / 50.0) * 3.0);

         for (int i = 0; i < countOfParticles; i++) {
            float rand1 = itemRand.nextFloat() * 2.0F - 1.0F;
            float rand2 = itemRand.nextFloat() * 2.0F - 1.0F;
            float X = rand1 * R;
            float new_R = (float)Math.sqrt(R * R - X * X);
            float Y = rand2 * new_R;
            float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
            if (itemRand.nextBoolean()) {
               Z *= -1.0F;
            }

            GUNParticle particle = new GUNParticle(
               snow,
               0.35F + (float)itemRand.nextGaussian() / 30.0F,
               0.01F,
               22 + itemRand.nextInt(10),
               180,
               world,
               x,
               y,
               z,
               X,
               Y,
               Z,
               0.75F + itemRand.nextFloat() / 10.0F,
               0.9F,
               1.0F,
               false,
               itemRand.nextInt(360),
               true,
               1.0F
            );
            world.spawnEntity(particle);
         }

         for (int ss = 0; ss < 2; ss++) {
            float fsize = (float)((1.0 + itemRand.nextGaussian() / 6.0) * 2.0);
            int lt = 10 + itemRand.nextInt(10);
            GUNParticle part = new GUNParticle(
               frostcircle,
               1.0F,
               0.0F,
               lt,
               240,
               world,
               x,
               y,
               z,
               0.0F,
               0.0F,
               0.0F,
               0.35F + itemRand.nextFloat() / 5.0F,
               0.8F,
               1.0F,
               true,
               itemRand.nextInt(360)
            );
            part.scaleTickAdding = fsize / lt;
            part.alphaGlowing = true;
            part.alphaTickAdding = -0.025F;
            part.rotationPitchYaw = new Vec2f(itemRand.nextInt(360), itemRand.nextInt(360));
            world.spawnEntity(part);
         }

         float fsize = (float)((0.7F + itemRand.nextGaussian() / 7.0) * 2.0);
         int lt = 8 + itemRand.nextInt(5);
         GUNParticle part = new GUNParticle(
            frostexpl,
            1.0F,
            0.0F,
            lt,
            240,
            world,
            x,
            y,
            z,
            0.0F,
            0.0F,
            0.0F,
            0.75F + itemRand.nextFloat() / 5.0F,
            0.8F,
            1.0F,
            true,
            itemRand.nextInt(360)
         );
         part.scaleTickAdding = fsize / lt;
         part.alphaGlowing = true;
         part.alphaTickAdding = -0.025F;
         world.spawnEntity(part);
      }

      if (id == -3) {
         world.playSound(x, y, z, Sounds.explode, SoundCategory.PLAYERS, 1.2F, 0.9F + itemRand.nextFloat() / 5.0F, false);

         for (int ss = 0; ss < 7; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               largecloud,
               0.6F + itemRand.nextFloat() / 2.0F,
               -0.005F,
               40,
               170,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 33.0F,
               (float)itemRand.nextGaussian() / 20.0F,
               (float)itemRand.nextGaussian() / 33.0F,
               1.0F,
               0.35F + itemRand.nextFloat() * 0.2F,
               0.0F,
               true,
               itemRand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.02F;
            world.spawnEntity(bigsmoke);
         }

         for (int ss = 0; ss < 10; ss++) {
            int lt = 19 + itemRand.nextInt(10);
            GUNParticle fire = new GUNParticle(
               flame,
               0.1F + (float)itemRand.nextGaussian() / 6.0F,
               -0.003F,
               lt,
               240,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 9.0F,
               (float)itemRand.nextGaussian() / 9.0F,
               (float)itemRand.nextGaussian() / 9.0F,
               0.15F + itemRand.nextFloat() * 0.1F,
               1.0F,
               1.0F - itemRand.nextFloat() * 0.15F,
               true,
               itemRand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -1.0F / lt;
            fire.alphaGlowing = true;
            fire.tracker = trackerExplode1;
            world.spawnEntity(fire);
            int lt2 = 25 + itemRand.nextInt(15);
            float scl = 0.07F + itemRand.nextFloat() * 0.1F;
            GUNParticle part = new GUNParticle(
               toxicspell,
               scl,
               -0.004F,
               lt2,
               200,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 13.0F,
               (float)itemRand.nextGaussian() / 16.0F,
               (float)itemRand.nextGaussian() / 13.0F,
               1.0F,
               0.8F + itemRand.nextFloat() * 0.2F,
               1.0F,
               true,
               itemRand.nextInt(360)
            );
            part.tracker = trackerRandom1;
            part.alphaGlowing = true;
            part.scaleTickAdding = -scl / lt2;
            world.spawnEntity(part);
         }
      }

      if (id == -4) {
         world.playSound(x, y, z, Sounds.explode4, SoundCategory.PLAYERS, 1.2F, 0.9F + itemRand.nextFloat() / 5.0F, false);

         for (int ss = 0; ss < 7; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               largesmoke,
               0.6F + itemRand.nextFloat() / 2.0F,
               -0.001F,
               40,
               60,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 13.0F,
               (float)itemRand.nextGaussian() / 20.0F,
               (float)itemRand.nextGaussian() / 13.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               itemRand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.01F;
            world.spawnEntity(bigsmoke);
         }

         for (int ss = 0; ss < 10; ss++) {
            int lt = 13 + itemRand.nextInt(10);
            GUNParticle fire = new GUNParticle(
               flame,
               0.1F + (float)itemRand.nextGaussian() / 6.0F,
               -0.003F,
               lt,
               240,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 9.0F,
               (float)itemRand.nextGaussian() / 9.0F,
               (float)itemRand.nextGaussian() / 9.0F,
               1.0F - itemRand.nextFloat() * 0.2F,
               1.0F - itemRand.nextFloat() * 0.2F,
               0.7F,
               true,
               itemRand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -1.0F / lt;
            fire.alphaGlowing = true;
            fire.tracker = trackerExplode1;
            world.spawnEntity(fire);
         }

         for (int ss = 0; ss < itemRand.nextInt(10) + 4; ss++) {
            world.spawnParticle(
               EnumParticleTypes.LAVA,
               x,
               y,
               z,
               itemRand.nextGaussian() / 13.0,
               0.4 + itemRand.nextGaussian() / 13.0,
               itemRand.nextGaussian() / 13.0,
               new int[0]
            );
         }

         float fsize = (float)(1.8 + itemRand.nextGaussian() / 6.0);
         int lt = 4 + itemRand.nextInt(3);
         GUNParticle part = new GUNParticle(
            firecircle, 0.4F, 0.0F, lt, 240, world, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, itemRand.nextInt(360)
         );
         part.scaleTickAdding = fsize / lt;
         part.alphaGlowing = true;
         part.alphaTickAdding = -0.8F / lt;
         part.randomDeath = false;
         world.spawnEntity(part);
      }

      if (id == -6) {
         world.playSound(x, y, z, Sounds.explode3, SoundCategory.PLAYERS, 1.2F, 0.8F + itemRand.nextFloat() / 5.0F, false);

         for (int ss = 0; ss < 8; ss++) {
            float fsize = (float)(1.5 + itemRand.nextGaussian() / 10.0);
            int lt = 5 + itemRand.nextInt(4);
            GUNParticle part = new GUNParticle(
               sparkle4,
               0.4F,
               0.0F,
               lt,
               240,
               world,
               x + itemRand.nextGaussian() / 2.0,
               y + itemRand.nextGaussian() / 2.0,
               z + itemRand.nextGaussian() / 2.0,
               (float)itemRand.nextGaussian() / 9.0F,
               (float)itemRand.nextGaussian() / 9.0F,
               (float)itemRand.nextGaussian() / 9.0F,
               1.0F,
               1.0F,
               0.8F,
               true,
               itemRand.nextInt(360)
            );
            part.scaleTickAdding = fsize / lt;
            part.alphaGlowing = true;
            part.alphaTickAdding = -0.6F / lt;
            part.randomDeath = false;
            world.spawnEntity(part);
         }

         for (int ss = 0; ss < 3; ss++) {
            world.spawnParticle(
               EnumParticleTypes.EXPLOSION_LARGE,
               x + itemRand.nextGaussian() / 4.0,
               y + itemRand.nextGaussian() / 4.0,
               z + itemRand.nextGaussian() / 4.0,
               0.0,
               0.0,
               0.0,
               new int[0]
            );
         }
      }

      if (id == -7) {
         world.playSound(x, y, z, Sounds.explode5, SoundCategory.PLAYERS, 1.2F, 0.9F + itemRand.nextFloat() / 5.0F, false);

         for (int ss = 0; ss < 10; ss++) {
            int lt = 16;
            GUNParticle fire = new GUNParticle(
               purple_smoke,
               0.1F,
               0.0F,
               lt,
               240,
               world,
               x + itemRand.nextGaussian() / 2.0,
               y + itemRand.nextGaussian() / 2.0,
               z + itemRand.nextGaussian() / 2.0,
               (float)itemRand.nextGaussian() / 9.0F,
               (float)itemRand.nextGaussian() / 9.0F,
               (float)itemRand.nextGaussian() / 9.0F,
               0.7F + itemRand.nextFloat() * 0.2F,
               0.5F,
               0.8F + itemRand.nextFloat() * 0.2F,
               true,
               itemRand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -1.0F / lt;
            fire.alphaGlowing = true;
            fire.tracker = trackerExplode2;
            fire.randomDeath = false;
            SuperKnockback.applyMove(fire, -0.2F, x, y, z);
            world.spawnEntity(fire);
         }

         for (int i = 0; i < 20; i++) {
            world.spawnParticle(
               EnumParticleTypes.PORTAL,
               x + (itemRand.nextDouble() - 0.5),
               y + itemRand.nextDouble() - 0.25,
               z + (itemRand.nextDouble() - 0.5),
               (itemRand.nextDouble() - 0.5) * 2.0,
               -itemRand.nextDouble(),
               (itemRand.nextDouble() - 0.5) * 2.0,
               new int[0]
            );
         }

         float fsize = (float)(1.0 + itemRand.nextGaussian() / 6.0);
         int lt = 4 + itemRand.nextInt(3);
         GUNParticle part = new GUNParticle(
            void_explode, 0.4F, 0.0F, lt, 240, world, x, y, z, 0.0F, 0.0F, 0.0F, 0.5F, 0.8F, 1.0F, true, itemRand.nextInt(360)
         );
         part.scaleTickAdding = fsize / lt;
         part.alphaGlowing = true;
         part.alphaTickAdding = -0.8F / lt;
         part.randomDeath = false;
         world.spawnEntity(part);
         int ltx = 6 + itemRand.nextInt(4);
         GUNParticle partx = new GUNParticle(
            void_explode, 4.5F, 0.0F, ltx, 240, world, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, itemRand.nextInt(360)
         );
         partx.scaleTickAdding = -4.5F / ltx;
         partx.alphaGlowing = true;
         partx.alphaTickAdding = -0.8F / ltx;
         partx.randomDeath = false;
         world.spawnEntity(partx);
      }

      if (id == -8) {
         world.playSound(
            x, y, z, a == 0.0 ? Sounds.explode_water_a : Sounds.explode_water_b, SoundCategory.PLAYERS, 1.3F, 0.9F + itemRand.nextFloat() / 5.0F, false
         );
         if (a == 0.0) {
            for (int ss = 0; ss < 5; ss++) {
               GUNParticle bubble = new GUNParticle(
                  slimesplash,
                  0.6F + itemRand.nextFloat() / 2.0F,
                  -0.001F,
                  7 + itemRand.nextInt(7),
                  -1,
                  world,
                  x + itemRand.nextGaussian() / 2.0,
                  y + itemRand.nextGaussian() / 2.0,
                  z + itemRand.nextGaussian() / 2.0,
                  (float)itemRand.nextGaussian() / 15.0F,
                  (float)itemRand.nextGaussian() / 16.0F + 0.05F,
                  (float)itemRand.nextGaussian() / 15.0F,
                  0.3F,
                  0.8F,
                  1.0F,
                  true,
                  itemRand.nextInt(180)
               );
               bubble.alphaTickAdding = -0.04F;
               bubble.alphaGlowing = true;
               bubble.scaleTickAdding = 0.14F;
               world.spawnEntity(bubble);
            }
         } else {
            int lt = 15 + itemRand.nextInt(5);
            GUNParticle part = new GUNParticle(
               blob, 0.4F, 0.0F, lt, 240, world, x, y, z, 0.0F, 0.0F, 0.0F, 0.5F, 0.8F, 1.0F, true, itemRand.nextInt(11) - 10
            );
            part.randomDeath = false;
            part.tracker = trackerExplode2;
            world.spawnEntity(part);
         }

         int countOfParticles = 26;
         float R = (float)(0.28 + itemRand.nextGaussian() / 30.0);

         for (int i = 0; i < countOfParticles; i++) {
            float rand1 = itemRand.nextFloat() * 2.0F - 1.0F;
            float rand2 = itemRand.nextFloat() * 2.0F - 1.0F;
            float X = rand1 * R;
            float new_R = (float)Math.sqrt(R * R - X * X);
            float Y = rand2 * new_R;
            float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
            if (itemRand.nextBoolean()) {
               Z *= -1.0F;
            }

            GUNParticle particle = new GUNParticle(
               blob,
               0.1F + itemRand.nextFloat() * 0.05F,
               a > 0.0 ? -0.001F : 0.01F,
               22 + itemRand.nextInt(20),
               180,
               world,
               x,
               y,
               z,
               X,
               Y,
               Z,
               0.6F,
               0.8F,
               1.0F,
               true,
               itemRand.nextInt(360),
               true,
               1.5F
            );
            world.spawnEntity(particle);
         }
      }

      if (id == -9) {
         world.playSound(x, y, z, Sounds.normal_projectile, SoundCategory.PLAYERS, 0.75F, 0.9F + itemRand.nextFloat() / 5.0F, false);

         for (int i = 0; i < 10; i++) {
            GUNParticle particle = new GUNParticle(
               star2,
               0.1F,
               0.003F,
               10,
               240,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 8.0F,
               (float)itemRand.nextGaussian() / 8.0F,
               (float)itemRand.nextGaussian() / 8.0F,
               0.15F + itemRand.nextFloat() * 0.1F,
               0.3F - itemRand.nextFloat() * 0.2F,
               1.0F,
               true,
               itemRand.nextInt(360)
            );
            particle.alphaGlowing = true;
            particle.scaleTickAdding = -0.01F;
            world.spawnEntity(particle);
         }

         for (int ss = 0; ss < 6; ss++) {
            EntityCubicParticle part = new EntityCubicParticle(
               arcane_rocket,
               0.007F + itemRand.nextFloat() * 0.005F,
               0.017F,
               12 + itemRand.nextInt(10),
               -1,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 11.0F,
               (float)itemRand.nextGaussian() / 11.0F + 0.08F,
               (float)itemRand.nextGaussian() / 11.0F,
               1.0F,
               1.0F,
               1.0F,
               false,
               itemRand.nextFloat(),
               itemRand.nextFloat(),
               itemRand.nextFloat(),
               0.14F,
               true,
               0.0F
            );
            world.spawnEntity(part);
         }
      }

      if (id == -10) {
         world.playSound(x, y, z, Sounds.surprise_rocket, SoundCategory.PLAYERS, 1.2F, 0.9F + itemRand.nextFloat() / 5.0F, false);

         for (int ss = 0; ss < 3; ss++) {
            int lt = 30 + itemRand.nextInt(20);
            GUNParticle bigsmoke = new GUNParticle(
               largecloud,
               (float)(0.75 + itemRand.nextGaussian() / 7.0),
               -0.005F,
               lt,
               240,
               world,
               x,
               y,
               z,
               (float)(itemRand.nextGaussian() / 10.0),
               (float)(itemRand.nextGaussian() / 10.0),
               (float)(itemRand.nextGaussian() / 10.0),
               0.5F + itemRand.nextFloat() / 10.0F,
               1.0F,
               1.0F,
               true,
               itemRand.nextInt(360)
            );
            bigsmoke.alphaGlowing = true;
            bigsmoke.alphaTickAdding = -1.0F / lt;
            world.spawnEntity(bigsmoke);
         }

         int countOfParticles = 4;
         float R = (float)((0.08 + itemRand.nextGaussian() / 50.0) * 3.0);

         for (int i = 0; i < countOfParticles; i++) {
            float rand1 = itemRand.nextFloat() * 2.0F - 1.0F;
            float rand2 = itemRand.nextFloat() * 2.0F - 1.0F;
            float X = rand1 * R;
            float new_R = (float)Math.sqrt(R * R - X * X);
            float Y = rand2 * new_R;
            float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
            if (itemRand.nextBoolean()) {
               Z *= -1.0F;
            }

            GUNParticle particle = new GUNParticle(
               snow2,
               0.35F + (float)itemRand.nextGaussian() / 30.0F,
               0.01F,
               26 + itemRand.nextInt(10),
               200,
               world,
               x,
               y,
               z,
               X,
               Y,
               Z,
               0.75F + itemRand.nextFloat() / 10.0F,
               0.9F,
               1.0F,
               false,
               itemRand.nextInt(360),
               true,
               1.0F
            );
            particle.dropMode = true;
            world.spawnEntity(particle);
         }

         for (int ss = 0; ss < itemRand.nextInt(3) + 5; ss++) {
            GUNParticle part = new GUNParticle(
               snow_cloth,
               0.2F + itemRand.nextFloat() * 0.1F,
               0.03F,
               40 + itemRand.nextInt(15),
               -1,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 8.0F,
               (float)itemRand.nextGaussian() / 8.0F + 0.1F,
               (float)itemRand.nextGaussian() / 8.0F,
               0.77F,
               0.11F,
               0.09F,
               false,
               itemRand.nextInt(360),
               true,
               3.0F
            );
            part.dropMode = true;
            part.rotationPitchYaw = new Vec2f(itemRand.nextInt(360), itemRand.nextInt(360));
            part.tracker = new ParticleTracker.TrackerGlassShard(
               (float)itemRand.nextGaussian() * 18.0F, (float)itemRand.nextGaussian() * 18.0F, (int)itemRand.nextGaussian() * 2, false
            );
            world.spawnEntity(part);
         }
      }

      if (id == -11) {
         world.playSound(x, y, z, Sounds.explode3, SoundCategory.PLAYERS, 1.2F, 1.1F + itemRand.nextFloat() / 5.0F, false);
         HostileProjectiles.SeaBomb.spawnSeabombParticles(world, itemRand, x, y, z);
      }
   }

   public static void sendEffectPacket(
      World world, double distance, double sendX, double sendY, double sendZ, double x, double y, double z, double a, double b, double c, int id
   ) {
      if (!world.isRemote) {
         PacketBulletEffectToClients packet = new PacketBulletEffectToClients();
         packet.writeargs(x, y, z, a, b, c, id);
         PacketHandler.sendToAllAround(packet, world, sendX, sendY, sendZ, distance);
      }
   }

   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      tooltip.add("пїЅ7" + Ln.translate("damage") + ": " + parameters.get("damage"));
      String name = this.getRegistryName().getPath();
      tooltip.add("пїЅf" + Ln.translate("description." + name));
   }

   public static class ArcaneRocket extends ItemRocket {
      public ArcaneRocket() {
         super("arcane_rocket", "arcane", 64, 8.0F, 0.7F, 0.15F, 0.184F, 1.0F, 9);
      }

      @Override
      public void onProjectileUpdate(Entity projectile, ItemStack weaponstack) {
         if (projectile.world.isRemote) {
            GUNParticle fire2 = new GUNParticle(
               magic_rocket,
               0.1F,
               0.0F,
               11,
               240,
               projectile.world,
               projectile.posX,
               projectile.posY,
               projectile.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.15F,
               0.2F + itemRand.nextFloat() / 5.0F,
               0.8F + itemRand.nextFloat() / 5.0F,
               true,
               itemRand.nextInt(360)
            );
            fire2.alphaTickAdding = -0.09F;
            fire2.alphaGlowing = true;
            fire2.scaleTickAdding = -0.009F;
            projectile.world.spawnEntity(fire2);
         }
      }

      @Override
      public boolean explode(
         World world,
         EntityLivingBase player,
         double x,
         double y,
         double z,
         RayTraceResult result,
         Entity projectile,
         ItemStack weaponstack,
         float weaponDamage,
         float weaponKnockback
      ) {
         if (!world.isRemote) {
            EntityArcaneExplode entityexpl = new EntityArcaneExplode(world);
            entityexpl.damage = this.damage() + weaponDamage;
            entityexpl.knockback = this.knockback() + weaponKnockback;
            entityexpl.damageRadius = this.damageRadius(weaponstack);
            entityexpl.thrower = player;
            entityexpl.setPosition(x, y, z);
            entityexpl.weaponstack = weaponstack;
            world.spawnEntity(entityexpl);
            sendEffectPacket(world, 64.0, x, y, z, x, y, z, 0.0, 0.0, 0.0, -9);
            return true;
         } else {
            return false;
         }
      }
   }

   public static class ChemicalRocket extends ItemRocket {
      public ChemicalRocket() {
         super("chemical_rocket", "chemical", 64, 7.0F, 0.7F, 0.3F, 1.0F, 0.3F, 3);
      }

      @Override
      public void onProjectileUpdate(Entity projectile, ItemStack weaponstack) {
         if (projectile.world.isRemote) {
            GUNParticle fire2 = new GUNParticle(
               flame,
               0.15F,
               0.0F,
               14,
               240,
               projectile.world,
               projectile.posX,
               projectile.posY,
               projectile.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.1F + itemRand.nextFloat() / 5.0F,
               1.0F,
               0.3F + itemRand.nextFloat() / 5.0F,
               true,
               itemRand.nextInt(360)
            );
            fire2.alphaTickAdding = -0.06F;
            fire2.alphaGlowing = true;
            fire2.scaleTickAdding = -0.01F;
            projectile.world.spawnEntity(fire2);
         }
      }

      @Override
      public boolean explode(
         World world,
         EntityLivingBase player,
         double x,
         double y,
         double z,
         RayTraceResult result,
         Entity projectile,
         ItemStack weaponstack,
         float weaponDamage,
         float weaponKnockback
      ) {
         double damageRadius = this.damageRadius(weaponstack);
         AxisAlignedBB axisalignedbb = projectile.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         if (world.isRemote) {
            return false;
         } else {
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            if (!list.isEmpty()) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, weaponstack);
               int toxtime = parameters.getEnchantedi("toxin_time", witchery);
               int FRtoxtime = parameters.getEnchantedi("friendlyfire_toxin_time", witchery);
               int toxpower = parameters.getEnchantedi("toxin_power", witchery);
               int poistime = parameters.getEnchantedi("poison_time", witchery);
               int FRpoistime = parameters.getEnchantedi("friendlyfire_poison_time", witchery);
               int poispower = parameters.getEnchantedi("poison_power", witchery);
               int slimtime = parameters.getEnchantedi("slime_time", witchery);
               int FRslimtime = parameters.getEnchantedi("friendlyfire_slime_time", witchery);
               int slimpower = parameters.getEnchantedi("slime_power", witchery);

               for (Entity entity : list) {
                  boolean opponent = Team.checkIsOpponent(player, entity);
                  if (opponent) {
                     Weapons.dealDamage(
                        new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.toxin),
                        weaponDamage + this.damage(),
                        player,
                        entity,
                        true,
                        weaponKnockback + this.knockback(),
                        x,
                        y - entity.height / 2.0F,
                        z
                     );
                     entity.hurtResistantTime = 0;
                  } else {
                     if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, weaponstack) == 0) {
                        Weapons.dealDamage(
                           new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.toxin),
                           weaponDamage + this.damage(),
                           player,
                           entity,
                           true
                        );
                     }

                     SuperKnockback.applyKnockback(entity, weaponKnockback + this.knockback(), x, y - entity.height / 2.0F, z);
                  }

                  if (entity instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                     entitylivingbase.addPotionEffect(new PotionEffect(PotionEffects.TOXIN, opponent ? toxtime : FRtoxtime, toxpower));
                     entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.POISON, opponent ? poistime : FRpoistime, poispower));
                     if (opponent && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, weaponstack) > 0) {
                        entitylivingbase.addPotionEffect(new PotionEffect(PotionEffects.SLIME, opponent ? slimtime : FRslimtime, slimpower));
                     }

                     if (entitylivingbase.getHealth() <= 0.0F && itemRand.nextFloat() < 0.35 && entitylivingbase.deathTime < 1) {
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_COLOREDACID);
                     }
                  }
               }
            }

            if (world.isAreaLoaded(new BlockPos(x - 16.0, y - 16.0, z - 16.0), new BlockPos(x + 16.0, y + 16.0, z + 16.0))) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               int radiusBlocks = parameters.getEnchantedi("radius_turn_water", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, weaponstack));
               float chance = parameters.getEnchanted("turn_water_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, weaponstack));

               for (BlockPos blockpos : GetMOP.getPosesInsideSphere((int)x, (int)y, (int)z, radiusBlocks)) {
                  if (itemRand.nextFloat() < chance) {
                     IBlockState state = world.getBlockState(blockpos);
                     if (state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.FLOWING_WATER) {
                        world.setBlockState(
                           blockpos,
                           BlocksRegister.FLUIDPOISON.getDefaultState().withProperty(BlockFluidPoison.LEVEL, state.getValue(BlockLiquid.LEVEL))
                        );
                     }
                  }
               }
            }

            sendEffectPacket(world, 64.0, x, y, z, x, y, z, 0.0, 0.0, 0.0, -3);
            return true;
         }
      }
   }

   public static class CommonRocket extends ItemRocket {
      public CommonRocket() {
         super("common_rocket", "common", 64, 5.0F, 1.0F, 0.9F, 0.83F, 0.7F, 1);
      }

      @Override
      public void onProjectileUpdate(Entity projectile, ItemStack weaponstack) {
         if (projectile.world.isRemote) {
            GUNParticle fire2 = new GUNParticle(
               flame,
               0.15F,
               0.0F,
               14,
               240,
               projectile.world,
               projectile.posX,
               projectile.posY,
               projectile.posZ,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               0.8F + itemRand.nextFloat() / 5.0F,
               0.8F + itemRand.nextFloat() / 5.0F,
               true,
               itemRand.nextInt(360)
            );
            fire2.alphaTickAdding = -0.06F;
            fire2.alphaGlowing = true;
            fire2.scaleTickAdding = -0.01F;
            projectile.world.spawnEntity(fire2);
         }
      }

      @Override
      public boolean explode(
         World world,
         EntityLivingBase player,
         double x,
         double y,
         double z,
         RayTraceResult result,
         Entity projectile,
         ItemStack weaponstack,
         float weaponDamage,
         float weaponKnockback
      ) {
         double damageRadius = this.damageRadius(weaponstack);
         AxisAlignedBB axisalignedbb = projectile.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         if (world.isRemote) {
            return false;
         } else {
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  if (Team.checkIsOpponent(player, entity)) {
                     Weapons.dealDamage(
                        new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.explode),
                        weaponDamage + this.damage(),
                        player,
                        entity,
                        true,
                        weaponKnockback + this.knockback(),
                        x,
                        y - entity.height / 2.0F,
                        z
                     );
                     entity.hurtResistantTime = 0;
                  } else {
                     if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, weaponstack) == 0) {
                        Weapons.dealDamage(
                           new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.explode),
                           weaponDamage + this.damage(),
                           player,
                           entity,
                           true
                        );
                     }

                     SuperKnockback.applyKnockback(entity, weaponKnockback + this.knockback(), x, y - entity.height / 2.0F, z);
                  }

                  if (entity instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                     if (entitylivingbase.getHealth() <= 0.0F && itemRand.nextFloat() < 0.55 && entitylivingbase.deathTime < 1) {
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
                     }
                  }
               }
            }

            if (result != null && result.getBlockPos() != null) {
               Weapons.triggerExplodeBlock(world, result.getBlockPos(), player, projectile);
            }

            sendEffectPacket(world, 64.0, x, y, z, x, y, z, 0.0, 0.0, 0.0, -1);
            return true;
         }
      }
   }

   public static class DemolishingRocket extends ItemRocket {
      public DemolishingRocket() {
         super("demolishing_rocket", "demolishing", 64, 2.0F, 0.4F, 1.0F, 0.6F, 0.6F, 5);
      }

      @Override
      public void onProjectileUpdate(Entity projectile, ItemStack weaponstack) {
         if (projectile.world.isRemote) {
            projectile.world
               .spawnParticle(
                  EnumParticleTypes.CLOUD,
                  projectile.posX,
                  projectile.posY,
                  projectile.posZ,
                  itemRand.nextGaussian() / 40.0,
                  itemRand.nextGaussian() / 40.0,
                  itemRand.nextGaussian() / 40.0,
                  new int[0]
               );
         }
      }

      @Override
      public boolean explode(
         World world,
         EntityLivingBase player,
         double x,
         double y,
         double z,
         RayTraceResult result,
         Entity projectile,
         ItemStack weaponstack,
         float weaponDamage,
         float weaponKnockback
      ) {
         if (!world.isRemote) {
            float weapondamageMultiplier = WeaponParameters.getWeaponParameters(this).get("rooted_weapondamage_explode_multiplier");
            world.newExplosion(
               player,
               x,
               y,
               z,
               WeaponParameters.getWeaponParameters(this)
                     .getEnchanted("base_explosion_size", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, weaponstack))
                  + (float)(Math.sqrt(weaponDamage) * weapondamageMultiplier),
               EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, weaponstack) > 0,
               true
            );
            return true;
         } else {
            return false;
         }
      }
   }

   public static class FrostfireRocket extends ItemRocket {
      public FrostfireRocket() {
         super("frostfire_rocket", "frostfire", 64, 6.0F, 0.4F, 0.5F, 0.83F, 1.0F, 2);
      }

      @Override
      public void onProjectileUpdate(Entity projectile, ItemStack weaponstack) {
         if (projectile.world.isRemote) {
            if (itemRand.nextFloat() < 0.7) {
               GUNParticle fire2 = new GUNParticle(
                  largecloud,
                  0.2F,
                  0.0F,
                  14,
                  240,
                  projectile.world,
                  projectile.posX,
                  projectile.posY,
                  projectile.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.6F,
                  0.75F + itemRand.nextFloat() / 9.0F,
                  1.0F,
                  true,
                  itemRand.nextInt(360)
               );
               fire2.alphaTickAdding = -0.06F;
               fire2.alphaGlowing = true;
               fire2.scaleTickAdding = -0.01F;
               projectile.world.spawnEntity(fire2);
            } else {
               GUNParticle fire2 = new GUNParticle(
                  frostlight,
                  0.13F,
                  -0.01F,
                  30,
                  200,
                  projectile.world,
                  projectile.posX,
                  projectile.posY,
                  projectile.posZ,
                  (float)itemRand.nextGaussian() / 40.0F,
                  (float)itemRand.nextGaussian() / 40.0F,
                  (float)itemRand.nextGaussian() / 40.0F,
                  0.9F + itemRand.nextFloat() / 10.0F,
                  1.0F,
                  1.0F,
                  false,
                  0
               );
               fire2.scaleTickAdding = -0.004F;
               projectile.world.spawnEntity(fire2);
            }
         }
      }

      @Override
      public boolean explode(
         World world,
         EntityLivingBase player,
         double x,
         double y,
         double z,
         RayTraceResult result,
         Entity projectile,
         ItemStack weaponstack,
         float weaponDamage,
         float weaponKnockback
      ) {
         double damageRadius = this.damageRadius(weaponstack);
         AxisAlignedBB axisalignedbb = projectile.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         if (world.isRemote) {
            return false;
         } else {
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            if (!list.isEmpty()) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, weaponstack);
               int ptime = parameters.getEnchantedi("potion_time", witchery);
               int ftime = parameters.getEnchantedi("friendlyfire_potion_time", witchery);
               int ppower = parameters.getEnchantedi("potion_power", witchery);

               for (Entity entity : list) {
                  entity.extinguish();
                  boolean opponent = Team.checkIsOpponent(player, entity);
                  if (opponent) {
                     Weapons.dealDamage(
                        new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.frost),
                        weaponDamage + this.damage(),
                        player,
                        entity,
                        true,
                        weaponKnockback + this.knockback(),
                        x,
                        y - entity.height / 2.0F,
                        z
                     );
                     entity.hurtResistantTime = 0;
                  } else {
                     if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, weaponstack) == 0) {
                        Weapons.dealDamage(
                           new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.frost),
                           weaponDamage + this.damage(),
                           player,
                           entity,
                           true
                        );
                     }

                     SuperKnockback.applyKnockback(entity, weaponKnockback + this.knockback(), x, y - entity.height / 2.0F, z);
                  }

                  if (entity instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                     Weapons.mixPotion(
                        entitylivingbase,
                        PotionEffects.FROSTBURN,
                        opponent ? ptime : ftime,
                        (float)ppower,
                        Weapons.EnumPotionMix.GREATEST,
                        Weapons.EnumPotionMix.GREATEST,
                        Weapons.EnumMathOperation.NONE,
                        Weapons.EnumMathOperation.NONE,
                        0,
                        0
                     );
                     if (entitylivingbase.getHealth() <= 0.0F && itemRand.nextFloat() < 0.55 && entitylivingbase.deathTime < 1) {
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING);
                     }
                  }
               }
            }

            if (world.isAreaLoaded(new BlockPos(x - 16.0, y - 16.0, z - 16.0), new BlockPos(x + 16.0, y + 16.0, z + 16.0))) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               int radiusBlocks = parameters.getEnchantedi("radius_freeze", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, weaponstack));

               for (BlockPos blockpos : GetMOP.getPosesInsideSphere((int)x, (int)y, (int)z, radiusBlocks)) {
                  IBlockState state = world.getBlockState(blockpos);
                  if (!Weapons.freezeBlock(world, blockpos, state, state.getBlock()) && state.getBlock() == BlocksRegister.FROSTEXPLOSIVE) {
                     world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
                     ((FrostfireExplosive)state.getBlock()).blockexploded(world, blockpos, player, projectile);
                  }
               }
            }

            sendEffectPacket(world, 64.0, x, y, z, x, y, z, 0.0, 0.0, 0.0, -2);
            return true;
         }
      }
   }

   public static class MiningRocket extends ItemRocket {
      public MiningRocket() {
         super("mining_rocket", "mining", 64, 4.0F, 1.2F, 0.9F, 0.9F, 0.5F, 6);
      }

      @Override
      public void onProjectileUpdate(Entity projectile, ItemStack weaponstack) {
         if (projectile.world.isRemote && projectile.ticksExisted % 2 == 0) {
            GUNParticle fire2 = new GUNParticle(
               flame,
               0.15F,
               0.0F,
               14,
               240,
               projectile.world,
               projectile.posX,
               projectile.posY,
               projectile.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.7F,
               0.8F + itemRand.nextFloat() / 5.0F,
               0.7F + itemRand.nextFloat() / 5.0F,
               true,
               itemRand.nextInt(360)
            );
            fire2.alphaTickAdding = -0.06F;
            fire2.alphaGlowing = true;
            fire2.scaleTickAdding = -0.01F;
            projectile.world.spawnEntity(fire2);
         }

         projectile.world
            .spawnParticle(
               EnumParticleTypes.CRIT_MAGIC, projectile.posX, projectile.posY, projectile.posZ, 0.0, 0.0, 0.0, new int[0]
            );
      }

      @Override
      public boolean explode(
         World world,
         EntityLivingBase player,
         double x,
         double y,
         double z,
         RayTraceResult result,
         Entity projectile,
         ItemStack weaponstack,
         float weaponDamage,
         float weaponKnockback
      ) {
         double damageRadius = this.damageRadius(weaponstack);
         AxisAlignedBB axisalignedbb = projectile.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         if (world.isRemote) {
            return false;
         } else {
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  if (Team.checkIsOpponent(player, entity)) {
                     Weapons.dealDamage(
                        new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.explode),
                        weaponDamage + this.damage(),
                        player,
                        entity,
                        true,
                        weaponKnockback + this.knockback(),
                        x,
                        y - entity.height / 2.0F,
                        z
                     );
                     entity.hurtResistantTime = 0;
                  } else {
                     if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, weaponstack) == 0) {
                        Weapons.dealDamage(
                           new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.explode),
                           weaponDamage + this.damage(),
                           player,
                           entity,
                           true
                        );
                     }

                     SuperKnockback.applyKnockback(entity, weaponKnockback + this.knockback(), x, y - entity.height / 2.0F, z);
                  }

                  if (entity instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                     if (entitylivingbase.getHealth() <= 0.0F && itemRand.nextFloat() < 0.25 && entitylivingbase.deathTime < 1) {
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
                     }
                  }
               }
            }

            if (world.isAreaLoaded(new BlockPos(x - 16.0, y - 16.0, z - 16.0), new BlockPos(x + 16.0, y + 16.0, z + 16.0))) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               int radiusBlocks = parameters.getEnchantedi("radius_block_destroy", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, weaponstack));
               float mult = parameters.get("damage_to_hardness_breaks");
               List<BlockPos> blocks = GetMOP.getPosesInsideSphere((int)x, (int)y, (int)z, radiusBlocks);
               Collections.shuffle(blocks);
               int lastSounds = 4;
               EntityPlayer entityplayer = player instanceof EntityPlayer ? (EntityPlayer)player : null;

               for (BlockPos blockpos : blocks) {
                  IBlockState state = world.getBlockState(blockpos);
                  if (!Weapons.triggerExplodeBlock(world, blockpos, player, projectile)
                     && Weapons.easyBreakBlockFor(world, (this.damage() + weaponDamage) * mult, blockpos, state)) {
                     Block block = state.getBlock();
                     if (entityplayer != null
                        && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, weaponstack) > 0
                        && block.canSilkHarvest(world, blockpos, state, entityplayer)) {
                        Weapons.destroyBlock(world, blockpos, false, lastSounds-- > 0);
                        Block.spawnAsEntity(world, blockpos, new ItemStack(block));
                     } else {
                        Weapons.destroyBlock(world, blockpos, true, lastSounds-- > 0);
                     }
                  }
               }
            }

            sendEffectPacket(world, 64.0, x, y, z, x, y, z, 0.0, 0.0, 0.0, -6);
            return true;
         }
      }
   }

   public static class NapalmRocket extends ItemRocket {
      public NapalmRocket() {
         super("napalm_rocket", "napalm", 64, 5.0F, 0.8F, 1.0F, 0.8F, 0.3F, 4);
      }

      @Override
      public void onProjectileUpdate(Entity projectile, ItemStack weaponstack) {
         if (projectile.world.isRemote) {
            GUNParticle fire2 = new GUNParticle(
               flame,
               0.15F,
               0.0F,
               14,
               240,
               projectile.world,
               projectile.posX,
               projectile.posY,
               projectile.posZ,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               0.8F + itemRand.nextFloat() / 5.0F,
               0.8F + itemRand.nextFloat() / 5.0F,
               true,
               itemRand.nextInt(360)
            );
            fire2.alphaTickAdding = -0.06F;
            fire2.alphaGlowing = true;
            fire2.scaleTickAdding = -0.01F;
            projectile.world.spawnEntity(fire2);
            projectile.world
               .spawnParticle(
                  EnumParticleTypes.SMOKE_NORMAL,
                  projectile.posX,
                  projectile.posY,
                  projectile.posZ,
                  itemRand.nextGaussian() / 40.0,
                  itemRand.nextGaussian() / 40.0,
                  itemRand.nextGaussian() / 40.0,
                  new int[0]
               );
         }
      }

      @Override
      public boolean explode(
         World world,
         EntityLivingBase player,
         double x,
         double y,
         double z,
         RayTraceResult result,
         Entity projectile,
         ItemStack weaponstack,
         float weaponDamage,
         float weaponKnockback
      ) {
         double damageRadius = this.damageRadius(weaponstack);
         AxisAlignedBB axisalignedbb = projectile.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         if (world.isRemote) {
            return false;
         } else {
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            if (!list.isEmpty()) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);

               for (Entity entity : list) {
                  boolean opponent = Team.checkIsOpponent(player, entity);
                  if (opponent) {
                     Weapons.dealDamage(
                        new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.fire),
                        weaponDamage + this.damage(),
                        player,
                        entity,
                        true,
                        weaponKnockback + this.knockback(),
                        x,
                        y - entity.height / 2.0F,
                        z
                     );
                     entity.hurtResistantTime = 0;
                     entity.setFire(parameters.getEnchantedi("fire", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, weaponstack)));
                  } else {
                     if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, weaponstack) == 0) {
                        Weapons.dealDamage(
                           new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.fire),
                           weaponDamage + this.damage(),
                           player,
                           entity,
                           true
                        );
                     }

                     SuperKnockback.applyKnockback(entity, weaponKnockback + this.knockback(), x, y - entity.height / 2.0F, z);
                     entity.setFire(parameters.geti("fire"));
                  }

                  if (entity instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                     if (entitylivingbase.getHealth() <= 0.0F && entitylivingbase.deathTime < 1) {
                        if (itemRand.nextFloat() < 0.35) {
                           DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
                        } else if (entitylivingbase.isBurning() && itemRand.nextFloat() < 0.2) {
                           DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_FIRE);
                        }
                     }
                  }
               }
            }

            if (world.isAreaLoaded(new BlockPos(x - 16.0, y - 16.0, z - 16.0), new BlockPos(x + 16.0, y + 16.0, z + 16.0))) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               float chanceIgnite = parameters.get("chance_ignite");
               int radiusBlocks = parameters.getEnchantedi("radius_ignite", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, weaponstack));

               for (BlockPos blockpos : GetMOP.getPosesInsideSphere((int)x, (int)y, (int)z, radiusBlocks)) {
                  IBlockState state = world.getBlockState(blockpos);
                  if (!(itemRand.nextFloat() < chanceIgnite) || !state.getBlock().isReplaceable(world, blockpos)) {
                     Weapons.triggerExplodeBlock(world, blockpos, player, projectile);
                  } else if (Blocks.FIRE.canPlaceBlockAt(world, blockpos)) {
                     world.setBlockState(blockpos, Blocks.FIRE.getActualState(Blocks.FIRE.getDefaultState(), world, blockpos));
                  }
               }
            }

            sendEffectPacket(world, 64.0, x, y, z, x, y, z, 0.0, 0.0, 0.0, -4);
            return true;
         }
      }
   }

   public static class ShellRocket extends ItemRocket {
      public ShellRocket() {
         super("shell_rocket", "shell", 64, 6.0F, 0.6F, 0.8F, 0.71F, 0.66F, 11);
      }

      @Override
      public void onProjectileUpdate(Entity projectile, ItemStack weaponstack) {
         if (projectile.world.getBlockState(projectile.getPosition()).getMaterial() == Material.WATER) {
            double acs = 1.15;
            projectile.motionX *= acs;
            projectile.motionY *= acs;
            projectile.motionZ *= acs;
         }

         if (projectile.world.isRemote) {
            GUNParticle fire2 = new GUNParticle(
               largecloud,
               0.15F,
               0.0F,
               14,
               240,
               projectile.world,
               projectile.posX,
               projectile.posY,
               projectile.posZ,
               0.0F,
               0.0F,
               0.0F,
               this.colorR + 0.15F + itemRand.nextFloat() / 10.0F,
               this.colorG + 0.15F + itemRand.nextFloat() / 20.0F,
               this.colorB + 0.15F + itemRand.nextFloat() / 5.0F,
               true,
               itemRand.nextInt(360)
            );
            fire2.alphaTickAdding = -0.06F;
            fire2.alphaGlowing = true;
            fire2.scaleTickAdding = -0.01F;
            projectile.world.spawnEntity(fire2);
         }
      }

      @Override
      public boolean explode(
         World world,
         EntityLivingBase player,
         double x,
         double y,
         double z,
         RayTraceResult result,
         Entity projectile,
         ItemStack weaponstack,
         float weaponDamage,
         float weaponKnockback
      ) {
         if (world.isRemote) {
            return false;
         } else {
            Vec3d poss = new Vec3d(x, y, z);
            double damageRadius = this.damageRadius(weaponstack);
            HostileProjectiles.SeaBomb.explodeSeabomb(
               world, damageRadius, itemRand.nextInt(2) + 1, itemRand, poss, player, this.damage() + weaponDamage, true
            );
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float shellsDamage = parameters.get("shells_damage") + weaponDamage;
            int max = parameters.geti("shells_min") + itemRand.nextInt(parameters.geti("shells_max") - parameters.geti("shells_min") + 1);
            boolean block = result != null && result.typeOfHit == Type.BLOCK && result.sideHit != null;
            float power = 0.5F + Debugger.floats[0];
            float blockoffset = 0.1F;
            if (block) {
               for (int i = 0; i < max; i++) {
                  ShellShard newarrow = new ShellShard(
                     world,
                     result.sideHit == EnumFacing.WEST ? poss.x - blockoffset : poss.x,
                     result.sideHit == EnumFacing.DOWN ? poss.y - blockoffset : poss.y,
                     result.sideHit == EnumFacing.NORTH ? poss.z - blockoffset : poss.z
                  );
                  newarrow.motionX = result.sideHit.getAxis() == Axis.X
                     ? result.sideHit.getXOffset() * 0.5F * power
                     : (itemRand.nextFloat() - 0.5F) * power;
                  newarrow.motionY = result.sideHit.getAxis() == Axis.Y
                     ? result.sideHit.getYOffset() * 0.5F * power
                     : (itemRand.nextFloat() - 0.5F) * power;
                  newarrow.motionZ = result.sideHit.getAxis() == Axis.Z
                     ? result.sideHit.getZOffset() * 0.5F * power
                     : (itemRand.nextFloat() - 0.5F) * power;
                  world.spawnEntity(newarrow);
                  newarrow.shootingEntity = player;
                  newarrow.setDamage(shellsDamage);
                  newarrow.velocityChanged = true;
               }
            } else {
               for (int i = 0; i < max; i++) {
                  ShellShard newarrow = new ShellShard(world, x, y, z);
                  newarrow.motionX = (itemRand.nextFloat() - 0.5F) * power;
                  newarrow.motionY = (itemRand.nextFloat() - 0.4F) * power;
                  newarrow.motionZ = (itemRand.nextFloat() - 0.5F) * power;
                  world.spawnEntity(newarrow);
                  newarrow.shootingEntity = player;
                  newarrow.setDamage(shellsDamage);
                  newarrow.velocityChanged = true;
               }
            }

            sendEffectPacket(world, 64.0, x, y, z, x, y, z, 0.0, 0.0, 0.0, -11);
            return true;
         }
      }
   }

   public static class SurpriseRocket extends ItemRocket {
      public SurpriseRocket() {
         super("surprise_rocket", "surprise", 64, 3.0F, 0.0F, 0.77F, 0.11F, 0.09F, 10);
      }

      @Override
      public void onProjectileUpdate(Entity projectile, ItemStack weaponstack) {
         if (projectile.world.isRemote) {
            GUNParticle fire2 = new GUNParticle(
               magic_rocket,
               0.1F,
               0.0F,
               11,
               240,
               projectile.world,
               projectile.posX,
               projectile.posY,
               projectile.posZ,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               0.8F + itemRand.nextFloat() / 5.0F,
               0.0F,
               true,
               itemRand.nextInt(360)
            );
            fire2.alphaTickAdding = -0.09F;
            fire2.alphaGlowing = true;
            fire2.scaleTickAdding = -0.009F;
            projectile.world.spawnEntity(fire2);
         }
      }

      public void setMotionWithSide(Entity entity, int index, RayTraceResult result, boolean hasgravity, double posX, double posY, double posZ) {
         double mult = 0.4 + Debugger.floats[0];
         double posfix = 3.0F + Debugger.floats[2];
         if (result != null && result.sideHit != null) {
            EnumFacing facing = result.sideHit;
            Vec3d mainVec;
            if (facing.getAxis() == Axis.X) {
               mainVec = GetMOP.PitchYawToVec3d(45 * index, 0.0F);
            } else if (facing.getAxis() == Axis.Z) {
               mainVec = GetMOP.PitchYawToVec3d(45 * index, 90.0F);
            } else {
               mainVec = GetMOP.PitchYawToVec3d(0.0F, 45 * index);
            }

            posX += facing.getXOffset() * 0.3;
            posY += facing.getYOffset() * 0.3;
            posZ += facing.getZOffset() * 0.3;
            if (hasgravity) {
               double gravAdd = 0.3 + Debugger.floats[1];
               entity.motionX = mainVec.x * mult;
               entity.motionY = mainVec.y * mult;
               entity.motionZ = mainVec.z * mult;
               posX -= entity.motionX * posfix;
               posY -= entity.motionY * posfix;
               posZ -= entity.motionZ * posfix;
               entity.motionX = entity.motionX + facing.getXOffset() * gravAdd;
               entity.motionY = entity.motionY + facing.getYOffset() * gravAdd;
               entity.motionZ = entity.motionZ + facing.getZOffset() * gravAdd;
            } else {
               entity.motionX = mainVec.x * mult;
               entity.motionY = mainVec.y * mult;
               entity.motionZ = mainVec.z * mult;
               posX -= entity.motionX * posfix;
               posY -= entity.motionY * posfix;
               posZ -= entity.motionZ * posfix;
            }
         } else {
            Vec3d mainVecx = GetMOP.PitchYawToVec3d(itemRand.nextInt(13) - 6, 45 * index);
            entity.motionX = mainVecx.x * mult;
            entity.motionY = mainVecx.y * mult;
            entity.motionZ = mainVecx.z * mult;
            posX -= entity.motionX * posfix;
            posY -= entity.motionY * posfix;
            posZ -= entity.motionZ * posfix;
         }

         entity.setPosition(posX, posY, posZ);
      }

      @Override
      public boolean explode(
         World world,
         EntityLivingBase player,
         double x,
         double y,
         double z,
         RayTraceResult result,
         Entity projectile,
         ItemStack weaponstack,
         float weaponDamage,
         float weaponKnockback
      ) {
         if (world.isRemote) {
            return false;
         } else {
            int randProjectile = itemRand.nextInt(12);
            if (randProjectile >= 10 && itemRand.nextFloat() < 0.3F) {
               randProjectile = itemRand.nextInt(12);
            }

            int type = itemRand.nextInt(8);
            float magicpower = 1.0F;
            if (randProjectile <= 4 && player instanceof EntityPlayer) {
               magicpower = Mana.getMagicPowerMax((EntityPlayer)player);
            }

            int amount = WeaponParameters.getWeaponParameters(this)
               .getEnchantedi("surprises", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, weaponstack));

            for (int i = 0; i < amount; i++) {
               if (randProjectile == 0) {
                  WandColdShoot shoot = new WandColdShoot(world, player, weaponstack, magicpower);
                  this.setMotionWithSide(shoot, i, result, false, x, y, z);
                  world.spawnEntity(shoot);
               }

               if (randProjectile == 1) {
                  GemStaffShoot shoot = new GemStaffShoot(world, player, weaponstack, magicpower);
                  shoot.damage = this.damage() + 1.0F + weaponDamage;
                  shoot.type = type;
                  shoot.livetime = 30;
                  this.setMotionWithSide(shoot, i, result, false, x, y, z);
                  world.spawnEntity(shoot);
               }

               if (randProjectile == 2) {
                  EntityFrostBolt shoot = new EntityFrostBolt(world, player, weaponstack, magicpower);
                  this.setMotionWithSide(shoot, i, result, false, x, y, z);
                  world.spawnEntity(shoot);
               }

               if (randProjectile == 3) {
                  LavaDropperShoot shoot = new LavaDropperShoot(world, player, weaponstack, magicpower);
                  this.setMotionWithSide(shoot, i, result, true, x, y, z);
                  world.spawnEntity(shoot);
               }

               if (randProjectile == 4) {
                  ViolenceShoot shoot = new ViolenceShoot(world, player, weaponstack, magicpower);
                  this.setMotionWithSide(shoot, i, result, true, x, y, z);
                  world.spawnEntity(shoot);
               }

               if (randProjectile == 5) {
                  EntitySlimeBullet shoot = new EntitySlimeBullet(world, player, weaponstack);
                  this.setMotionWithSide(shoot, i, result, true, x, y, z);
                  world.spawnEntity(shoot);
               }

               if (randProjectile == 6) {
                  CannonSnowball shoot = new CannonSnowball(world, player, weaponstack);
                  this.setMotionWithSide(shoot, i, result, true, x, y, z);
                  world.spawnEntity(shoot);
               }

               if (randProjectile == 7) {
                  XmassBall shoot = new XmassBall(world, player, weaponstack);
                  shoot.damage = this.damage() + weaponDamage;
                  this.setMotionWithSide(shoot, i, result, true, x, y, z);
                  world.spawnEntity(shoot);
               }

               if (randProjectile == 8) {
                  EntityWitherSkull shoot = new EntityWitherSkull(world, player, 0.0, 0.0, 0.0);
                  this.setMotionWithSide(shoot, i, result, false, x, y, z);
                  shoot.accelerationX = shoot.motionX * 0.06;
                  shoot.accelerationY = shoot.motionY * 0.06;
                  shoot.accelerationZ = shoot.motionZ * 0.06;
                  shoot.motionX = 0.0;
                  shoot.motionY = 0.0;
                  shoot.motionZ = 0.0;
                  world.spawnEntity(shoot);
               }

               if (randProjectile == 10) {
                  EntityTNTPrimed shoot = new EntityTNTPrimed(world, x, y, z, player);
                  world.spawnEntity(shoot);
                  break;
               }

               if (randProjectile == 11) {
                  if (i >= 3) {
                     break;
                  }

                  EntityFrostfireExplosive shoot = new EntityFrostfireExplosive(world, x, y, z, player, 3.0F);
                  shoot.fuse = 28 + itemRand.nextInt(4);
                  shoot.setSize(0.5F, 0.5F);
                  shoot.motionX = itemRand.nextGaussian() / 4.0;
                  shoot.motionY = itemRand.nextGaussian() / 5.0 + 0.3;
                  shoot.motionZ = itemRand.nextGaussian() / 4.0;
                  world.spawnEntity(shoot);
                  shoot.velocityChanged = true;
                  IEntitySynchronize.sendSynchronize(shoot, 64.0, 0.0, 0.0, 0.0, 0.5, 0.5, 0.0);
               }
            }

            if (randProjectile == 9) {
               List<EntityLivingBase> list = GetMOP.getHostilesInAABBto(world, new Vec3d(x, y, z), 9.0, 7.0, player, player);

               for (int i = 0; i < 5; i++) {
                  EnderSeerProjectile shoot = new EnderSeerProjectile(world, player, list.isEmpty() ? null : list.get(itemRand.nextInt(list.size())));
                  this.setMotionWithSide(shoot, i, result, false, x, y, z);
                  world.spawnEntity(shoot);
               }
            }

            sendEffectPacket(world, 64.0, x, y, z, x, y, z, 0.0, 0.0, 0.0, -10);
            return true;
         }
      }
   }

   public static class VoidRocket extends ItemRocket {
      public VoidRocket() {
         super("void_rocket", "void", 64, 6.0F, 1.0F, 0.9F, 0.5F, 1.0F, 7);
      }

      @Override
      public void onProjectileUpdate(Entity projectile, ItemStack weaponstack) {
         if (projectile.world.isRemote) {
            GUNParticle fire2 = new GUNParticle(
               purple_smoke,
               0.15F,
               0.0F,
               14,
               240,
               projectile.world,
               projectile.posX,
               projectile.posY,
               projectile.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.8F,
               0.6F,
               0.8F + itemRand.nextFloat() * 0.2F,
               true,
               itemRand.nextInt(360)
            );
            fire2.alphaTickAdding = -0.06F;
            fire2.alphaGlowing = true;
            fire2.scaleTickAdding = -0.01F;
            projectile.world.spawnEntity(fire2);
         }
      }

      public void teleportAway(Vec3d from, EntityLivingBase entity) {
         double d0 = entity.posX;
         double d1 = entity.posY;
         double d2 = entity.posZ;

         for (int i = 0; i < 16; i++) {
            double d3 = entity.posX + entity.getRNG().nextDouble() * MathHelper.clamp(8.0 * (d0 - from.x), -8.0, 8.0);
            double d4 = MathHelper.clamp(entity.posY + (entity.getRNG().nextInt(12) - 6), 0.0, entity.world.getActualHeight() - 1);
            double d5 = entity.posZ + entity.getRNG().nextDouble() * MathHelper.clamp(8.0 * (d2 - from.z), -8.0, 8.0);
            if (entity.isRiding()) {
               entity.dismountRidingEntity();
            }

            if (entity.attemptTeleport(d3, d4, d5)) {
               entity.world.playSound((EntityPlayer)null, d0, d1, d2, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
               entity.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
               break;
            }
         }
      }

      @Override
      public boolean explode(
         World world,
         EntityLivingBase player,
         double x,
         double y,
         double z,
         RayTraceResult result,
         Entity projectile,
         ItemStack weaponstack,
         float weaponDamage,
         float weaponKnockback
      ) {
         double damageRadius = this.damageRadius(weaponstack);
         AxisAlignedBB axisalignedbb = projectile.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         if (world.isRemote) {
            return false;
         } else {
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  boolean opponent = Team.checkIsOpponent(player, entity);
                  if (opponent) {
                     Weapons.dealDamage(
                        new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.portal),
                        weaponDamage + this.damage(),
                        player,
                        entity,
                        true,
                        -(weaponKnockback + this.knockback()),
                        x,
                        y - entity.height / 2.0F,
                        z
                     );
                     entity.hurtResistantTime = 0;
                  } else {
                     if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, weaponstack) == 0) {
                        Weapons.dealDamage(
                           new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.portal),
                           weaponDamage + this.damage(),
                           player,
                           entity,
                           true
                        );
                     }

                     SuperKnockback.applyKnockback(entity, -(weaponKnockback + this.knockback()), x, y - entity.height / 2.0F, z);
                  }

                  if (entity instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                     if (entitylivingbase.isPotionActive(PotionEffects.DEMONIC_BURN)) {
                        this.teleportAway(opponent ? player.getPositionVector() : new Vec3d(x, y, z), entitylivingbase);
                     }

                     if (entitylivingbase.getHealth() <= 0.0F
                        && itemRand.nextFloat() < 0.25
                        && entitylivingbase.deathTime < 1
                        && entitylivingbase.getDistanceSq(x, y, z) < 1.0) {
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
                     }
                  }
               }
            }

            sendEffectPacket(world, 64.0, x, y, z, x, y, z, 0.0, 0.0, 0.0, -7);
            return true;
         }
      }
   }

   public static class WaterblastRocket extends ItemRocket {
      public WaterblastRocket() {
         super("waterblast_rocket", "waterblast", 64, 3.5F, 0.5F, 0.5F, 1.0F, 0.87F, 8);
      }

      @Override
      public void onProjectileUpdate(Entity projectile, ItemStack weaponstack) {
         if (projectile.world.getBlockState(projectile.getPosition()).getMaterial() == Material.WATER) {
            double acs = 1.2;
            projectile.motionX *= acs;
            projectile.motionY *= acs;
            projectile.motionZ *= acs;
         }

         if (projectile.world.isRemote) {
            GUNParticle fire2 = new GUNParticle(
               blob,
               0.12F,
               0.0F,
               14,
               240,
               projectile.world,
               projectile.posX,
               projectile.posY,
               projectile.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.4F,
               0.5F + itemRand.nextFloat() / 5.0F,
               0.9F + itemRand.nextFloat() / 10.0F,
               true,
               itemRand.nextInt(360)
            );
            fire2.alphaTickAdding = -0.06F;
            fire2.alphaGlowing = true;
            fire2.scaleTickAdding = -0.01F;
            projectile.world.spawnEntity(fire2);
         }
      }

      @Override
      public boolean explode(
         World world,
         EntityLivingBase player,
         double x,
         double y,
         double z,
         RayTraceResult result,
         Entity projectile,
         ItemStack weaponstack,
         float weaponDamage,
         float weaponKnockback
      ) {
         double damageRadius = this.damageRadius(weaponstack);
         AxisAlignedBB axisalignedbb = projectile.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         if (world.isRemote) {
            return false;
         } else {
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  boolean opponent = Team.checkIsOpponent(player, entity);
                  Weapons.doWetEntity(entity);
                  if (opponent) {
                     Weapons.dealDamage(
                        new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.water),
                        weaponDamage + this.damage(),
                        player,
                        entity,
                        true,
                        weaponKnockback + this.knockback(),
                        x,
                        y - entity.height / 2.0F,
                        z
                     );
                     entity.hurtResistantTime = 0;
                  } else {
                     if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, weaponstack) == 0) {
                        Weapons.dealDamage(
                           new WeaponDamage(weaponstack, player, projectile, true, false, projectile, WeaponDamage.water),
                           weaponDamage + this.damage(),
                           player,
                           entity,
                           true
                        );
                     }

                     SuperKnockback.applyKnockback(entity, weaponKnockback + this.knockback(), x, y - entity.height / 2.0F, z);
                  }

                  DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER, 0.05F);
               }
            }

            if (world.isAreaLoaded(new BlockPos(x - 16.0, y - 16.0, z - 16.0), new BlockPos(x + 16.0, y + 16.0, z + 16.0))) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               int radiusBlocks = parameters.getEnchantedi("wet_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, weaponstack));

               for (BlockPos blockpos : GetMOP.getPosesInsideSphere((int)x, (int)y, (int)z, radiusBlocks)) {
                  Weapons.doWetBlock(world, blockpos);
               }
            }

            boolean bw = world.getBlockState(projectile.getPosition()).getMaterial() == Material.WATER;
            sendEffectPacket(world, 64.0, x, y, z, x, y, z, bw ? 1.0 : 0.0, 0.0, 0.0, -8);
            return true;
         }
      }
   }
}
