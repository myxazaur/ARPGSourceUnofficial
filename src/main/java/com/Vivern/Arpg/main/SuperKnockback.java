//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.MathHelper;

public class SuperKnockback {
   public static void applyKnockback(Entity entity, float power, double fromX, double fromY, double fromZ) {
      if (entity instanceof EntityLivingBase) {
         EntityLivingBase base = (EntityLivingBase)entity;
         double resist = base.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue();
         power = (float)(power * (1.0 - resist));
      }

      if (power != 0.0F) {
         float dist = (float)entity.getDistance(fromX, fromY, fromZ);
         float prunex = (float)((fromX - entity.posX) / dist / 2.0 * power);
         float pruney = (float)((fromY - entity.posY) / dist / 2.0 * power);
         float prunez = (float)((fromZ - entity.posZ) / dist / 2.0 * power);
         entity.motionX += -prunex;
         entity.motionY += -pruney;
         entity.motionZ += -prunez;
         entity.velocityChanged = true;
      }
   }

   public static void applyMove(Entity entity, float power, double fromX, double fromY, double fromZ) {
      float dist = (float)entity.getDistance(fromX, fromY, fromZ);
      float prunex = (float)((fromX - entity.posX) / dist / 2.0 * power);
      float pruney = (float)((fromY - entity.posY) / dist / 2.0 * power);
      float prunez = (float)((fromZ - entity.posZ) / dist / 2.0 * power);
      entity.motionX += -prunex;
      entity.motionY += -pruney;
      entity.motionZ += -prunez;
   }

   public static void setMove(Entity entity, float power, double fromX, double fromY, double fromZ) {
      float dist = (float)entity.getDistance(fromX, fromY, fromZ);
      float prunex = (float)((fromX - entity.posX) / dist / 2.0 * power);
      float pruney = (float)((fromY - entity.posY) / dist / 2.0 * power);
      float prunez = (float)((fromZ - entity.posZ) / dist / 2.0 * power);
      entity.motionX = -prunex;
      entity.motionY = -pruney;
      entity.motionZ = -prunez;
   }

   public static void applyShieldBlock(EntityLivingBase entity, Entity attacker, float basepower, float toSelfPower) {
      if (attacker instanceof EntityLivingBase) {
         applyShieldBlock(entity, (EntityLivingBase)attacker, basepower, toSelfPower);
      }
   }

   public static void applyShieldBlock(EntityLivingBase entity, EntityLivingBase attacker, float basepower, float toSelfPower) {
      attacker.knockBack(entity, basepower, entity.posX - attacker.posX, entity.posZ - attacker.posZ);
      entity.knockBack(
         attacker,
         toSelfPower * (1.0F - (float)entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue()),
         attacker.posX - entity.posX,
         attacker.posZ - entity.posZ
      );
      entity.velocityChanged = true;
   }

   public static void shoot(
      Entity projectile, Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy
   ) {
      float x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float y = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f = MathHelper.sqrt(x * x + y * y + z * z);
      x /= f;
      y /= f;
      z /= f;
      x = (float)(x + GetMOP.rand.nextGaussian() * 0.0075F * inaccuracy);
      y = (float)(y + GetMOP.rand.nextGaussian() * 0.0075F * inaccuracy);
      z = (float)(z + GetMOP.rand.nextGaussian() * 0.0075F * inaccuracy);
      x *= velocity;
      y *= velocity;
      z *= velocity;
      projectile.motionX = x;
      projectile.motionY = y;
      projectile.motionZ = z;
      float f1 = MathHelper.sqrt(x * x + z * z);
      projectile.rotationYaw = (float)(MathHelper.atan2(x, z) * (180.0 / Math.PI));
      projectile.rotationPitch = (float)(MathHelper.atan2(y, f1) * (180.0 / Math.PI));
      projectile.prevRotationYaw = projectile.rotationYaw;
      projectile.prevRotationPitch = projectile.rotationPitch;
      projectile.motionX = projectile.motionX + entityThrower.motionX;
      projectile.motionZ = projectile.motionZ + entityThrower.motionZ;
      if (!entityThrower.onGround) {
         projectile.motionY = projectile.motionY + entityThrower.motionY * 0.5;
      }
   }
}
