package com.vivern.arpg.elements;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.AnimatedGParticle;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Snakewhip extends Whip {
   public static ResourceLocation slimesplash = new ResourceLocation("arpg:textures/slimesplash.png");

   public Snakewhip() {
      super("snakewhip", 1200);
      this.soundSwosh = Sounds.snakewhip_swosh;
   }

   @Override
   public void onWhipUpdateInHand(World world, ItemStack itemstack, EntityPlayer player, EnumHand hand, int itemSlot, boolean keypressed) {
      NBTHelper.GiveNBTint(itemstack, 0, "charge");
   }

   @Override
   public void onSpecAttack(
      Vec3d pos, IWeapon iweapon, ItemStack itemstack, EntityPlayer player, EnumHand hand, float damage, boolean hitSomeone, boolean killSomeone
   ) {
      player.world
         .playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.whip_specattack,
            SoundCategory.PLAYERS,
            1.8F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
      Vec3d poseyes = player.getPositionEyes(1.0F);
      Vec3d scaledVec = new Vec3d(
         poseyes.x * 0.25 + pos.x * 0.75,
         poseyes.y * 0.25 + pos.y * 0.75,
         poseyes.z * 0.25 + pos.z * 0.75
      );
      if (hitSomeone) {
         int whipMaxCharge = WeaponParameters.getWeaponParameters(this)
            .getEnchantedi("max_charge", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack));
         NBTHelper.SetNBTint(itemstack, -whipMaxCharge, "charge");
         if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0 && !player.world.isRemote) {
            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(
               player.world, pos.x, pos.y, pos.z
            );
            entityareaeffectcloud.setOwner(player);
            entityareaeffectcloud.setParticle(EnumParticleTypes.SLIME);
            float splashAttackRadius = WeaponParameters.getWeaponParameters(this)
               .getEnchanted("splash_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
            entityareaeffectcloud.setRadius(splashAttackRadius);
            entityareaeffectcloud.setDuration(500);
            entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / entityareaeffectcloud.getDuration());
            entityareaeffectcloud.addEffect(new PotionEffect(PotionEffects.SLIME, 40, 2));
            player.world.spawnEntity(entityareaeffectcloud);
         }

         IWeapon.fireEffect(
            this,
            player,
            player.world,
            64.0,
            scaledVec.x,
            scaledVec.y,
            scaledVec.z,
            hand == EnumHand.MAIN_HAND ? 2.0 : 3.0,
            (double)player.rotationPitch,
            (double)player.rotationYaw,
            0.0,
            0.0,
            0.0
         );
      } else {
         IWeapon.fireEffect(
            this,
            player,
            player.world,
            64.0,
            scaledVec.x,
            scaledVec.y,
            scaledVec.z,
            hand == EnumHand.MAIN_HAND ? 4.0 : 5.0,
            (double)player.rotationPitch,
            (double)player.rotationYaw,
            0.0,
            0.0,
            0.0
         );
      }
   }

   @Override
   public void onSpecAttackDamage(Entity entity, Vec3d pos, IWeapon iweapon, ItemStack itemstack, EntityPlayer player, EnumHand hand, float damage) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      int time = parameters.getEnchantedi("slime_time", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack));
      int power = parameters.geti("slime_power");
      Weapons.setPotionIfEntityLB(entity, PotionEffects.SLIME, time, power);
   }

   @Override
   public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      if (entity.isEntityAlive() && entity instanceof EntityLivingBase) {
         EntityLivingBase livingBase = (EntityLivingBase)entity;
         int charge = NBTHelper.GetNBTint(stack, "charge");
         if (charge < 0) {
            NBTHelper.AddNBTint(stack, 1, "charge");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int timemin = parameters.getEnchantedi("toxin_time_min", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, stack));
            int timemax = parameters.getEnchantedi("toxin_time_max", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, stack));
            int power = parameters.geti("toxin_power");
            PotionEffect eff = new PotionEffect(PotionEffects.TOXIN, timemin + itemRand.nextInt(timemax - timemin + 1), power);
            livingBase.addPotionEffect(eff);
         }
      }

      return super.attackEntityMelee(entity, stack, player, hand, isCritical);
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      if (a >= 2.0) {
         boolean lh = a == 3.0 || a == 5.0;
         int lt = 42;
         float scl = 1.6F + itemRand.nextFloat() * 0.3F;
         AnimatedGParticle part = new AnimatedGParticle(
            sweep_cloud, scl, 0.0F, lt, 220, world, x, y, z, 0.0F, 0.0F, 0.0F, 0.5F, 0.82F, 0.47F, true, lh ? 45 : 225
         );
         part.framecount = 11;
         part.animDelay = 3;
         part.disableOnEnd = true;
         if (lh) {
            part.rotationPitchYaw = new Vec2f((float)b + 60.0F, (float)c);
         } else {
            part.rotationPitchYaw = new Vec2f((float)b + 240.0F, (float)c);
         }

         world.spawnEntity(part);

         for (int ss = 0; ss < 8; ss++) {
            GUNParticle part1 = new GUNParticle(
               pixel,
               0.025F + itemRand.nextFloat() * 0.00625F,
               0.01F,
               40 + itemRand.nextInt(20),
               200 + itemRand.nextInt(40),
               world,
               x + itemRand.nextGaussian() * 0.7,
               y,
               z + itemRand.nextGaussian() * 0.7,
               (float)itemRand.nextGaussian() / 25.0F,
               (float)itemRand.nextGaussian() / 17.0F + 0.16F,
               (float)itemRand.nextGaussian() / 25.0F,
               0.46F,
               0.86F,
               0.44F,
               false,
               0
            );
            part1.scaleTickAdding = -5.0E-4F;
            world.spawnEntity(part1);
         }

         if (a < 4.0) {
            for (int ss = 0; ss < 16; ss++) {
               int lt2 = 80 + itemRand.nextInt(40);
               float scl2 = 0.25F + itemRand.nextFloat() * 0.2F;
               GUNParticle part1 = new GUNParticle(
                  slimesplash,
                  scl2,
                  0.025F,
                  lt2,
                  -1,
                  world,
                  x,
                  y,
                  z,
                  (float)itemRand.nextGaussian() / 12.0F,
                  (float)itemRand.nextGaussian() / 12.0F + 0.08F,
                  (float)itemRand.nextGaussian() / 12.0F,
                  0.5F,
                  0.78F,
                  0.45F,
                  true,
                  itemRand.nextInt(360),
                  true,
                  10.0F
               );
               part1.scaleTickAdding = -0.8F * scl2 / lt2;
               part1.alphaTickAdding = -0.8F / lt2;
               part1.sticky = true;
               world.spawnEntity(part1);
            }
         }
      } else {
         GUNParticle part1 = new GUNParticle(
            forge_hit_a,
            0.12F + itemRand.nextFloat() * 0.1F,
            0.0F,
            4,
            240,
            world,
            x,
            y,
            z,
            0.0F,
            0.0F,
            0.0F,
            0.45F,
            0.85F,
            0.5F,
            true,
            itemRand.nextInt(360)
         );
         part1.scaleTickAdding = 0.07F;
         part1.alphaGlowing = true;
         world.spawnEntity(part1);
         if (a == 1.0) {
            for (int ss = 0; ss < 3; ss++) {
               int ltx = 15 + itemRand.nextInt(13);
               GUNParticle bigsmoke = new GUNParticle(
                  largecloud,
                  0.16F + itemRand.nextFloat() * 0.16F,
                  -0.004F,
                  ltx,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)itemRand.nextGaussian() / 26.0F,
                  (float)itemRand.nextGaussian() / 26.0F,
                  (float)itemRand.nextGaussian() / 26.0F,
                  0.5F,
                  1.0F,
                  0.5F,
                  true,
                  itemRand.nextInt(360)
               );
               bigsmoke.alphaGlowing = true;
               bigsmoke.alpha = 0.7F;
               bigsmoke.alphaTickAdding = -0.7F / ltx;
               bigsmoke.scaleTickAdding = 0.01F;
               world.spawnEntity(bigsmoke);
            }
         }
      }
   }
}
