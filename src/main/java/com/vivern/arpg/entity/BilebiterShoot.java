package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.projectile.EntityThrowable;
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

public class BilebiterShoot extends EntityThrowable {
   public final ItemStack weaponstack;
   ResourceLocation texture = new ResourceLocation("arpg:textures/bilebiter_shoot2.png");
   ResourceLocation textur = new ResourceLocation("arpg:textures/bilebiter_shoot3.png");
   ResourceLocation texturbubble = new ResourceLocation("arpg:textures/bilebiter_shoot4.png");
   ResourceLocation texture5 = new ResourceLocation("arpg:textures/bilebiter_shoot5.png");

   public BilebiterShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.BILEBITER);
   }

   public BilebiterShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.BILEBITER);
   }

   public BilebiterShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.BILEBITER);
   }

   public BilebiterShoot(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.3;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.3;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.3;
      }
   }

   protected float getGravityVelocity() {
      return 0.014F;
   }

   public void onUpdate() {
      super.onUpdate();
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

         for (int ss = 0; ss < 3; ss++) {
            TrailParticle trailpart = new TrailParticle(
               this.textur,
               0.1F,
               0.035F,
               25,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 8.0F,
               (float)this.rand.nextGaussian() / 8.0F + 0.15F,
               (float)this.rand.nextGaussian() / 8.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(180),
               true,
               1.2F,
               this.texture5,
               0.09F,
               240,
               1.0F,
               1.0F,
               1.0F,
               0.15F,
               5,
               0.3F,
               1.0F
            );
            this.world.spawnEntity(trailpart);
         }

         for (int ss = 0; ss < 8; ss++) {
            Entity bubble = new GUNParticle(
               this.texturbubble,
               0.06F + (float)this.rand.nextGaussian() / 25.0F,
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
               1.0F,
               0.8F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               false,
               this.rand.nextInt(180),
               true,
               1.3F
            );
            this.world.spawnEntity(bubble);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
            this.expl();
         }
      } else if (this.world
            .getBlockState(result.getBlockPos())
            .getBlock()
            .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
         != null) {
         this.expl();
      }
   }

   public void expl() {
      if (!this.world.isRemote) {
         double damageRadius = 3.0;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.thrower, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  Weapons.dealDamage(
                     null,
                     5 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack),
                     this.thrower,
                     entity,
                     true,
                     0.5F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 3.0F,
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
                  if (entity instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                     if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.4 && entitylivingbase.deathTime < 1) {
                        IAttributeInstance entityColorR = entitylivingbase.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_RED_MAX);
                        IAttributeInstance entityColorG = entitylivingbase.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_GREEN_MAX);
                        IAttributeInstance entityColorB = entitylivingbase.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX);
                        entityColorR.setBaseValue(0.9);
                        entityColorG.setBaseValue(0.8);
                        entityColorB.setBaseValue(0.15);
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_COLOREDACID);
                     }
                  }
               }
            }
         }

         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      Vec3d pos2 = this.getPositionVector();
      if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world, this.texture, 0.18F, 240, 1.0F, 1.0F, 1.0F, 0.1F, pos1.distanceTo(pos2), 5, -0.15F, 9999.0F, pos1, pos2
         );
         laser.alphaGlowing = true;
         laser.texstart = 0.1F;
         laser.offset = -0.1F;
         laser.setPosition(pos1.x, pos1.y, pos1.z);
         this.world.spawnEntity(laser);
      }
   }
}
