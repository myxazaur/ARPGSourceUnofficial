//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySlimeBullet extends StandardBullet {
   public final ItemStack weaponstack;
   public Vec3d pos1 = this.getPositionVector();
   public Vec3d pos2 = this.getPositionVector();
   public Vec3d pos3 = this.getPositionVector();
   public Vec3d pos4 = this.getPositionVector();
   public Vec3d pos5 = this.getPositionVector();
   public boolean impacted = false;
   public int impactTick = 0;
   ResourceLocation trail = new ResourceLocation("arpg:textures/slimetrail.png");
   ResourceLocation slime = new ResourceLocation("arpg:textures/slimesplash.png");
   ResourceLocation texturbubble = new ResourceLocation("arpg:textures/bilebiter_shoot4.png");

   public EntitySlimeBullet(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.SLIMESHOTGUN);
      this.setSize(0.32F, 0.32F);
   }

   public EntitySlimeBullet(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.SLIMESHOTGUN);
      this.setSize(0.32F, 0.32F);
   }

   public EntitySlimeBullet(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.SLIMESHOTGUN);
      this.setSize(0.32F, 0.32F);
   }

   public EntitySlimeBullet(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.setSize(0.32F, 0.32F);
   }

   @Override
   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.5;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.5;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.5;
      }
   }

   @Override
   public float getGravityVelocity() {
      return 0.017F;
   }

   @Override
   public boolean canCollideWithBlocks() {
      return true;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         if (this.ticksExisted > 40) {
            this.explode();
         }

         if (this.impacted) {
            this.impactTick++;
         }

         if (this.impactTick > 25) {
            this.explode();
         }
      }

      if (this.pos4 != Vec3d.ZERO) {
         this.pos5 = this.pos4;
      } else {
         this.pos5 = this.getPositionVector();
      }

      if (this.pos3 != Vec3d.ZERO) {
         this.pos4 = this.pos3;
      } else {
         this.pos4 = this.getPositionVector();
      }

      if (this.pos2 != Vec3d.ZERO) {
         this.pos3 = this.pos2;
      } else {
         this.pos3 = this.getPositionVector();
      }

      if (this.pos1 != Vec3d.ZERO) {
         this.pos2 = this.pos1;
      } else {
         this.pos2 = this.getPositionVector();
      }

      if (this.getPositionVector() != Vec3d.ZERO) {
         this.pos1 = this.getPositionVector();
      } else {
         this.pos1 = this.thrower.getPositionVector();
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.bb_impact,
               SoundCategory.AMBIENT,
               1.0F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );

         for (int ss = 0; ss < 5; ss++) {
            Entity bubble = new GUNParticle(
               this.texturbubble,
               0.08F + this.rand.nextFloat() / 20.0F,
               0.03F,
               50 + this.rand.nextInt(20),
               200,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 9.0F,
               (float)this.rand.nextGaussian() / 9.0F + 0.15F,
               (float)this.rand.nextGaussian() / 9.0F,
               0.5F,
               0.8F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               false,
               this.rand.nextInt(180),
               true,
               1.3F
            );
            this.world.spawnEntity(bubble);
         }

         for (int ss = 0; ss < 4; ss++) {
            GUNParticle bubble = new GUNParticle(
               this.slime,
               0.5F + this.rand.nextFloat() / 3.0F,
               -0.001F,
               10 + this.rand.nextInt(10),
               -1,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 19.0F,
               (float)this.rand.nextGaussian() / 22.0F + 0.05F,
               (float)this.rand.nextGaussian() / 19.0F,
               0.55F + (float)this.rand.nextGaussian() / 10.0F,
               0.9F + (float)this.rand.nextGaussian() / 10.0F,
               0.65F + (float)this.rand.nextGaussian() / 10.0F,
               true,
               this.rand.nextInt(180)
            );
            bubble.alphaTickAdding = -0.04F;
            bubble.alphaGlowing = true;
            this.world.spawnEntity(bubble);
         }
      }
   }

   @Override
   public void onImpact(RayTraceResult result) {
      if (!this.world.isRemote && !this.impacted && result.entityHit != null && Team.checkIsOpponent(this.thrower, result.entityHit)) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
         int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
         Weapons.dealDamage(
            new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.acid),
            parameters.getEnchanted("impact_damage", might),
            this.thrower,
            result.entityHit,
            true,
            parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack)),
            this.posX,
            this.posY,
            this.posZ
         );
         result.entityHit.hurtResistantTime = 4;
         Weapons.setPotionIfEntityLB(
            result.entityHit,
            PotionEffects.SLIME,
            parameters.getEnchantedi("slime_duration_impact", witchery),
            Math.round(parameters.getEnchanted("slime_power", witchery))
         );
      }
   }

   public void explode() {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
      if (!list.isEmpty()) {
         for (Entity entity : list) {
            if (Team.checkIsOpponent(this.thrower, entity)) {
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
               int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, false, false, this, WeaponDamage.acid),
                  parameters.getEnchanted("damage", might),
                  this.thrower,
                  entity,
                  true,
                  parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack)),
                  this.posX,
                  this.posY,
                  this.posZ
               );
               entity.hurtResistantTime = 0;
               Weapons.setPotionIfEntityLB(
                  entity,
                  PotionEffects.SLIME,
                  parameters.getEnchantedi("slime_duration_explode", witchery),
                  Math.round(parameters.getEnchanted("slime_power", witchery))
               );
            }
         }
      }

      this.world.setEntityState(this, (byte)5);
      this.setDead();
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      if (this.ticksExisted > 2 && this.world.isRemote && this.pos1.lengthSquared() > 1.0E-6 && this.pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world,
            this.trail,
            0.1F,
            240,
            1.0F,
            1.0F,
            1.0F,
            0.15F,
            this.pos1.distanceTo(this.pos2),
            Math.min(2, this.ticksExisted - 2),
            0.3F,
            1.0F,
            this.pos1,
            this.pos2
         );
         laser.setPosition(this.posX, this.posY, this.posZ);
         laser.alphaGlowing = true;
         this.world.spawnEntity(laser);
      }
   }
}
