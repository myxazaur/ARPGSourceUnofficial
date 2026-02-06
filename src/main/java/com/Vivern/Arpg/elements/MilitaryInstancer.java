package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.AnimatedGParticle;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MilitaryInstancer extends Instancer {
   public static ResourceLocation toxic_spell = new ResourceLocation("arpg:textures/toxic_spell.png");
   public static ResourceLocation radiation = new ResourceLocation("arpg:textures/radiation.png");

   public MilitaryInstancer() {
      super("military_instancer", 1.3F, 1.1F, 7500);
   }

   @Override
   public boolean canCarry(ItemStack stack, EntityPlayer player, EntityLiving entity, int leadershipNeed) {
      if (leadershipNeed < 10) {
         float maxH = entity.getMaxHealth();
         if (maxH != 0.0F) {
            float healthRatio = entity.getHealth() / (maxH * 0.4F);
            if (healthRatio < 1.0F && itemRand.nextFloat() > 0.9 * healthRatio) {
               player.world
                  .playSound(
                     (EntityPlayer)null,
                     entity.posX,
                     entity.posY,
                     entity.posZ,
                     Sounds.walking_bomb_explode,
                     SoundCategory.AMBIENT,
                     1.8F,
                     1.15F + itemRand.nextFloat() / 5.0F
                  );
               double damageRadius = 2.6;
               AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox()
                  .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
                  .offset(-damageRadius, -damageRadius, -damageRadius);
               List<Entity> list = player.world.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb);
               if (!list.isEmpty()) {
                  for (Entity entit : list) {
                     if (Team.checkIsOpponent(player, entit)) {
                        Weapons.dealDamage(
                           new WeaponDamage(stack, player, entity, true, false, entity, WeaponDamage.radiation),
                           leadershipNeed * 2,
                           player,
                           entit,
                           true,
                           0.6F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack) / 2.0F,
                           entity.posX,
                           entity.posY - 1.0,
                           entity.posZ
                        );
                        entit.hurtResistantTime = 0;
                        if (entit instanceof EntityPlayer) {
                           Mana.addRad((EntityPlayer)entit, 20, true);
                        }
                     } else {
                        SuperKnockback.applyKnockback(
                           entit,
                           0.4F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack) / 4.0F,
                           entity.posX,
                           entity.posY,
                           entity.posZ
                        );
                        if (entit instanceof EntityPlayer) {
                           Mana.addRad((EntityPlayer)entit, 10, true);
                        }
                     }
                  }
               }

               entity.hurtResistantTime = 0;
               entity.attackEntityFrom(new WeaponDamage(stack, player, null, false, false, null, WeaponDamage.radiation), entity.getHealth() * 4.0F);
               DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER);
               IWeapon.fireEffect(this, player, player.world, 64.0, (double)entity.getEntityId(), 3.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      super.effect(player, world, x, y, z, a, b, c, d1, d2, d3);
      if (y == 3.0) {
         Entity entity = world.getEntityByID((int)x);
         float amount = Math.min((entity.width + entity.height) * 3.0F, 20.0F);

         for (int i = 0; i < amount; i++) {
            AnimatedGParticle rad = new AnimatedGParticle(
               radiation,
               0.5F,
               0.0F,
               16,
               240,
               world,
               entity.posX + itemRand.nextGaussian() * entity.width,
               entity.posY + itemRand.nextFloat() * entity.height,
               entity.posZ + itemRand.nextGaussian() * entity.width,
               (float)itemRand.nextGaussian() / 8.0F,
               (float)itemRand.nextGaussian() / 8.0F + 0.1F,
               (float)itemRand.nextGaussian() / 8.0F,
               1.0F,
               1.0F,
               0.92F + itemRand.nextFloat() * 0.08F,
               true,
               itemRand.nextInt(360)
            );
            rad.framecount = 16;
            rad.scaleTickAdding = 0.05F + itemRand.nextFloat() * 0.05F;
            rad.randomDeath = false;
            rad.alphaGlowing = true;
            world.spawnEntity(rad);
         }
      }
   }

   @Override
   public void shootTick(ItemStack stack, EntityPlayer player, int ready) {
      if (ready == 15) {
         if (player.ticksExisted % 10 == 0) {
            Mana.addRad(player, 1, true);
         }
      } else {
         Mana.addRad(player, 1, true);
      }
   }

   @Override
   public void spawnPartickles(World world, EntityPlayer player, boolean deploy) {
      if (deploy) {
         if (player.ticksExisted % 4 == 0) {
            this.bom(0);
         }

         Vec3d partpos = this.getInstancerCorePoint(player, player.getPrimaryHand());

         for (int i = 0; i < 4; i++) {
            float scl = 0.05F + itemRand.nextFloat() * 0.05F;
            int lt = 5 + itemRand.nextInt(7);
            GUNParticle part = new GUNParticle(
               toxic_spell,
               scl,
               0.0F,
               lt,
               200,
               world,
               partpos.x + (itemRand.nextFloat() - 0.5F) / 2.0F,
               partpos.y + (itemRand.nextFloat() - 0.5F) / 2.0F,
               partpos.z + (itemRand.nextFloat() - 0.5F) / 2.0F,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               itemRand.nextInt(360),
               true,
               1.0F
            );
            part.alphaGlowing = true;
            part.scaleTickAdding = -scl / lt;
            part.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.0F, 10.0F);
            world.spawnEntity(part);
            if (itemRand.nextFloat() < 0.17F) {
               float len = 1.0F + itemRand.nextFloat() * 3.5F;
               AnimatedGParticle rad = new AnimatedGParticle(
                  radiation,
                  0.2F,
                  0.0F,
                  16,
                  240,
                  world,
                  partpos.x + (itemRand.nextFloat() - 0.5F) / 2.0F + part.motionX * len,
                  partpos.y + (itemRand.nextFloat() - 0.5F) / 2.0F + part.motionY * len,
                  partpos.z + (itemRand.nextFloat() - 0.5F) / 2.0F + part.motionZ * len,
                  (float)(part.motionX / 5.0),
                  (float)(part.motionY / 5.0),
                  (float)(part.motionZ / 5.0),
                  1.0F,
                  1.0F,
                  0.92F + itemRand.nextFloat() * 0.08F,
                  true,
                  itemRand.nextInt(360)
               );
               rad.framecount = 16;
               rad.scaleTickAdding = 0.015F + itemRand.nextFloat() * 0.03F;
               rad.randomDeath = false;
               rad.alphaGlowing = true;
               world.spawnEntity(rad);
            }
         }
      } else {
         if (player.ticksExisted % 4 == 0) {
            this.bom(1);
         }

         Vec3d partpos = this.getInstancerCorePoint(player, player.getPrimaryHand());
         ParticleTracker tracker = new ParticleTracker.TrackerFollowStaticPoint(partpos, true, 0.3F, 0.001F, 0.2F);

         for (int ix = 0; ix < 4; ix++) {
            RayTraceResult result = GetMOP.fixedRayTraceBlocks(
               world,
               player,
               6.0,
               0.4,
               0.4,
               false,
               false,
               true,
               false,
               player.rotationPitch + itemRand.nextInt(17) - 8.0F,
               player.rotationYaw + itemRand.nextInt(17) - 8.0F
            );
            if (result != null && result.hitVec != null) {
               float scl = 0.05F + itemRand.nextFloat() * 0.05F;
               int lt = 15 + itemRand.nextInt(10);
               GUNParticle part = new GUNParticle(
                  toxic_spell,
                  scl,
                  0.0F,
                  lt,
                  200,
                  world,
                  result.hitVec.x,
                  result.hitVec.y,
                  result.hitVec.z,
                  (float)itemRand.nextGaussian() / 20.0F,
                  (float)itemRand.nextGaussian() / 20.0F,
                  (float)itemRand.nextGaussian() / 20.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  itemRand.nextInt(360)
               );
               part.alphaGlowing = true;
               part.scaleTickAdding = -scl / lt;
               part.tracker = tracker;
               part.alpha = 0.0F;
               part.alphaTickAdding = 0.12F;
               world.spawnEntity(part);
               if (itemRand.nextFloat() < 0.2F) {
                  AnimatedGParticle rad = new AnimatedGParticle(
                     radiation,
                     0.2F,
                     0.0F,
                     16,
                     240,
                     world,
                     result.hitVec.x,
                     result.hitVec.y,
                     result.hitVec.z,
                     0.0F,
                     0.0F,
                     0.0F,
                     1.0F,
                     1.0F,
                     0.92F + itemRand.nextFloat() * 0.08F,
                     true,
                     itemRand.nextInt(360)
                  );
                  rad.framecount = 16;
                  rad.scaleTickAdding = 0.015F + itemRand.nextFloat() * 0.03F;
                  rad.randomDeath = false;
                  rad.alphaGlowing = true;
                  world.spawnEntity(rad);
               }
            }
         }
      }
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 5 - rapidity;
   }

   @Override
   public int getMaxLeadership(ItemStack itemstack) {
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
      return 450 + might * 35;
   }
}
