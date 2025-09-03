//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.AnimatedGParticle;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.SparkleSubparticle;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class Mauler extends Whip {
   public static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");

   public Mauler() {
      super("mauler", 850);
      this.soundSwosh = Sounds.mauler_swosh;
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
      int whipMaxCharge = WeaponParameters.getWeaponParameters(this).geti("max_charge");
      NBTHelper.SetNBTint(itemstack, -whipMaxCharge, "charge");
      Vec3d poseyes = player.getPositionEyes(1.0F);
      Vec3d scaledVec = new Vec3d(
         poseyes.x * 0.25 + pos.x * 0.75,
         poseyes.y * 0.25 + pos.y * 0.75,
         poseyes.z * 0.25 + pos.z * 0.75
      );
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
      if (killSomeone && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0 && !player.world.isRemote) {
         float explosionSize = WeaponParameters.getWeaponParameters(this).get("special_explosion_size");
         Explosion explosion = new Explosion(player.world, player, pos.x, pos.y, pos.z, explosionSize, true, true);
         explosion.doExplosionA();
         explosion.doExplosionB(true);
      }
   }

   @Override
   public void onSpecAttackDamage(Entity entity, Vec3d pos, IWeapon iweapon, ItemStack itemstack, EntityPlayer player, EnumHand hand, float damage) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      int time = parameters.getEnchantedi("potion_time", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack));
      int power = parameters.geti("potion_power");
      Weapons.setPotionIfEntityLB(entity, PotionEffects.BERSERK, time, power);
   }

   @Override
   public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      if (entity.isBurning()) {
         NBTHelper.AddNBTint(stack, 15, "charge");
      }

      return super.attackEntityMelee(entity, stack, player, hand, isCritical);
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      if (a != 2.0 && a != 3.0) {
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
            1.0F,
            0.8F,
            0.4F,
            true,
            itemRand.nextInt(360)
         );
         part1.scaleTickAdding = 0.07F;
         part1.alphaGlowing = true;
         world.spawnEntity(part1);
         if (a == 1.0) {
            for (int ss = 0; ss < 3; ss++) {
               int lt = 15 + itemRand.nextInt(13);
               GUNParticle bigsmoke = new GUNParticle(
                  largesmoke,
                  0.16F + itemRand.nextFloat() * 0.16F,
                  -0.004F,
                  lt,
                  -1,
                  world,
                  x,
                  y,
                  z,
                  (float)itemRand.nextGaussian() / 26.0F,
                  (float)itemRand.nextGaussian() / 26.0F,
                  (float)itemRand.nextGaussian() / 26.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  itemRand.nextInt(360)
               );
               bigsmoke.alpha = 0.7F;
               bigsmoke.alphaTickAdding = -0.7F / lt;
               bigsmoke.scaleTickAdding = 0.01F;
               world.spawnEntity(bigsmoke);
               SparkleSubparticle sparklepart = new SparkleSubparticle(
                  x,
                  y,
                  z,
                  0.02F + itemRand.nextFloat() * 0.00625F,
                  40 + itemRand.nextInt(40),
                  (float)itemRand.nextGaussian() / 26.0F,
                  (float)itemRand.nextGaussian() / 26.0F,
                  (float)itemRand.nextGaussian() / 26.0F,
                  0.0F
               );
               SparkleSubparticle.particles.add(sparklepart);
            }
         }
      } else {
         boolean lh = a == 3.0;
         int lt = 42;
         float scl = 1.6F + itemRand.nextFloat() * 0.3F;
         AnimatedGParticle part = new AnimatedGParticle(
            sweep_cloud, scl, 0.0F, lt, 220, world, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 0.8F, 0.31F, true, lh ? 45 : 225
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
               1.0F,
               0.85F,
               0.36F,
               false,
               0
            );
            part1.scaleTickAdding = -5.0E-4F;
            world.spawnEntity(part1);
            SparkleSubparticle sparklepart = new SparkleSubparticle(
               x + itemRand.nextGaussian() * 0.7,
               y,
               z + itemRand.nextGaussian() * 0.7,
               0.02F + itemRand.nextFloat() * 0.00625F,
               60 + itemRand.nextInt(30),
               (float)itemRand.nextGaussian() / 25.0F,
               (float)itemRand.nextGaussian() / 17.0F + 0.12F,
               (float)itemRand.nextGaussian() / 25.0F,
               0.0F
            );
            SparkleSubparticle.particles.add(sparklepart);
         }
      }
   }
}
