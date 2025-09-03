//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.entity.EntityCubicParticle;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class WalkingBomb extends AdvancedPotion {
   private static final ResourceLocation gore = new ResourceLocation("arpg:textures/gore.png");
   private static final ResourceLocation drop = new ResourceLocation("arpg:textures/normaldrop.png");
   private static final ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");

   public WalkingBomb() {
      super(true, 5536888, 0, false);
      this.setRegistryName("arpg:walking_bomb");
      this.setPotionName("Walking_Bomb");
      this.setIconIndex(17, 1);
   }

   public void onUpdate(EntityLivingBase entityOnEffect, World world, int duration, int amplifier) {
      if (!world.isRemote && entityOnEffect.ticksExisted % 40 == 0 && duration > 0) {
         entityOnEffect.attackEntityFrom(DamageSource.MAGIC, 3 * (amplifier + 1));
      }

      if (entityOnEffect.deathTime == 1) {
         double x = entityOnEffect.posX;
         double y = entityOnEffect.posY;
         double z = entityOnEffect.posZ;
         Vec3d color = DeathEffects.getBloodColor(entityOnEffect);
         if (world != null && world.isRemote) {
            for (int i = 0; i < Math.min(entityOnEffect.getMaxHealth() / 4.0F, 13.0F); i++) {
               Entity spel = new EntityCubicParticle(
                  gore,
                  0.01F + entityOnEffect.height / 100.0F,
                  0.02F,
                  20 + rand.nextInt(10),
                  40 + rand.nextInt(9),
                  world,
                  x,
                  y + 0.5,
                  z,
                  (float)rand.nextGaussian() / 8.0F,
                  (float)rand.nextGaussian() / 20.0F + 0.2F,
                  (float)rand.nextGaussian() / 8.0F,
                  (float)color.x,
                  (float)color.y,
                  (float)color.z,
                  false,
                  rand.nextFloat(),
                  rand.nextFloat(),
                  rand.nextFloat(),
                  0.05F,
                  true,
                  -3.0E-4F
               );
               world.spawnEntity(spel);
            }

            for (int i = 0; i < Math.min(entityOnEffect.getMaxHealth() / 2.0F, 18.0F); i++) {
               GUNParticle sp = new GUNParticle(
                  drop,
                  0.07F + rand.nextFloat() / 20.0F,
                  0.02F,
                  50,
                  -1,
                  world,
                  x,
                  y + 0.5,
                  z,
                  (float)rand.nextGaussian() / 10.0F,
                  (float)rand.nextGaussian() / 10.0F + 0.3F,
                  (float)rand.nextGaussian() / 10.0F,
                  (float)color.x,
                  (float)color.y,
                  (float)color.z,
                  false,
                  (int)Math.round(rand.nextGaussian() * 20.0),
                  true,
                  5.0F
               );
               sp.dropMode = true;
               world.spawnEntity(sp);
               GUNParticle bigsmoke = new GUNParticle(
                  largecloud,
                  1.1F + (float)rand.nextGaussian() / 8.0F,
                  0.003F,
                  20 + rand.nextInt(10),
                  -1,
                  world,
                  x,
                  y + 0.5,
                  z,
                  (float)rand.nextGaussian() / 13.0F,
                  (float)rand.nextGaussian() / 16.0F,
                  (float)rand.nextGaussian() / 13.0F + 0.1F,
                  (float)color.x,
                  (float)color.y,
                  (float)color.z,
                  true,
                  rand.nextInt(360)
               );
               bigsmoke.alphaTickAdding = -0.02F;
               world.spawnEntity(bigsmoke);
            }
         }
      }
   }

   @Override
   public void onThisDeath(LivingDeathEvent event, PotionEffect effect) {
      EntityLivingBase entityOnEffect = event.getEntityLiving();
      double x = entityOnEffect.posX;
      double y = entityOnEffect.posY;
      double z = entityOnEffect.posZ;
      double damageRadius = 3.5 + effect.getAmplifier();
      entityOnEffect.world
         .playSound((EntityPlayer)null, x, y, z, Sounds.walking_bomb_explode, SoundCategory.NEUTRAL, 1.2F, 0.8F + rand.nextFloat() / 5.0F);
      AxisAlignedBB axisalignedbb = entityOnEffect.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (!entityOnEffect.world.isRemote) {
         List<EntityLivingBase> list = entityOnEffect.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (entitylivingbase != entityOnEffect) {
                  SuperKnockback.applyKnockback(entitylivingbase, 1.7F, x, y - 0.1, z);
                  entitylivingbase.attackEntityFrom(DamageSource.causeExplosionDamage(entityOnEffect), 10 + effect.getAmplifier() * 5);
                  entitylivingbase.hurtResistantTime = 0;
                  if (effect.getDuration() > 0 && entitylivingbase.getHealth() > 0.0F && !entitylivingbase.isDead) {
                     PotionEffect baff = new PotionEffect(PotionEffects.WALKING_BOMB, effect.getDuration(), effect.getAmplifier());
                     entitylivingbase.addPotionEffect(baff);
                  }
               }
            }
         }
      }
   }
}
