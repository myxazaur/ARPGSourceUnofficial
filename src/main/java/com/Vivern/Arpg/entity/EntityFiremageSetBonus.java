//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFiremageSetBonus extends EntityThrowable {
   ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");
   public float magicPower = 1.0F;

   public EntityFiremageSetBonus(World world) {
      super(world);
   }

   public EntityFiremageSetBonus(World world, EntityLivingBase thrower) {
      super(world, thrower);
   }

   public EntityFiremageSetBonus(World world, double x, double y, double z) {
      super(world, x, y, z);
   }

   public EntityFiremageSetBonus(World world, EntityLivingBase thrower, float power) {
      super(world, thrower);
      this.magicPower = power;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.1;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.1;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.2;
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 300) {
         this.setDead();
      }

      double radius = 2.3;
      double movex = this.posX;
      double movey = this.posY;
      double movez = this.posZ;
      if (this.getThrower() != null) {
         movex = this.getThrower().posX + radius * Math.sin(this.ticksExisted / 9.5);
         movey = this.getThrower().posY + 0.5;
         movez = this.getThrower().posZ + radius * Math.cos(this.ticksExisted / 9.5);
      }

      this.motionX /= 3.0;
      this.motionY /= 3.0;
      this.motionZ /= 3.0;
      float kb = (float)this.getDistance(movex, movey, movez) / 2.0F;
      SuperKnockback.applyKnockback(this, (float)(-Math.min((double)kb, 0.4)), movex, movey, movez);
      this.world.setEntityState(this, (byte)8);
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               SoundEvents.ENTITY_GENERIC_EXPLODE,
               SoundCategory.AMBIENT,
               2.0F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );

         for (int ss = 0; ss < 8; ss++) {
            GUNParticle fire = new GUNParticle(
               this.flame,
               0.2F + (float)this.rand.nextGaussian(),
               -0.005F,
               23 + this.rand.nextInt(10),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 17.0F,
               (float)this.rand.nextGaussian() / 17.0F + 0.05F,
               (float)this.rand.nextGaussian() / 17.0F,
               1.0F,
               0.8F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               true,
               this.rand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -0.04F;
            fire.alphaGlowing = true;
            this.world.spawnEntity(fire);
         }
      }

      if (id == 8) {
         GUNParticle fire2 = new GUNParticle(
            this.flame,
            0.1F + (float)this.rand.nextGaussian() / 15.0F,
            -0.009F,
            10 + this.rand.nextInt(5),
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            0.8F + (float)this.rand.nextGaussian() / 5.0F,
            1.0F,
            true,
            this.rand.nextInt(100) - 50
         );
         fire2.alphaTickAdding = -0.1F;
         fire2.alphaGlowing = true;
         this.world.spawnEntity(fire2);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (Team.checkIsOpponent(this.thrower, result.entityHit)
         && result.entityHit != null
         && (result.entityHit instanceof EntityLivingBase || result.entityHit instanceof MultiPartEntityPart)
         && result.entityHit.isCreatureType(EnumCreatureType.MONSTER, false)) {
         this.expl();
      }
   }

   public void expl() {
      double damageRadius = 2.0;
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (!this.world.isRemote) {
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  Weapons.dealDamage(
                     null, 8.0F * this.magicPower, this.thrower, entity, true, 1.5F, this.posX, this.posY - 0.3, this.posZ
                  );
                  entity.hurtResistantTime = 0;
                  entity.setFire(4);
               } else if (entity == this.getThrower() && this.getThrower() instanceof EntityPlayer) {
                  Mana.giveMana((EntityPlayer)this.getThrower(), 5.0F);
               }
            }
         }
      }

      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }
   }
}
