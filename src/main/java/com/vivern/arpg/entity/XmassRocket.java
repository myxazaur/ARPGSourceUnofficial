package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class XmassRocket extends EntityThrowable implements IEntitySynchronize {
   public final ItemStack weaponstack;
   static ResourceLocation sparkle3 = new ResourceLocation("arpg:textures/sparkle3.png");
   public static ResourceLocation star2 = new ResourceLocation("arpg:textures/star2.png");
   public static ResourceLocation circle2 = new ResourceLocation("arpg:textures/circle2.png");
   public boolean explodeInstantly;
   public static ParticleTracker.TrackerSinusScaling ptss = new ParticleTracker.TrackerSinusScaling(0.3F, 0.7F);

   public XmassRocket(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.XMASSLAUNCHER);
   }

   public XmassRocket(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.XMASSLAUNCHER);
   }

   public XmassRocket(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.XMASSLAUNCHER);
   }

   public XmassRocket(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.4;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.4;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.2;
      }
   }

   protected float getGravityVelocity() {
      return 0.01F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 300) {
         this.setDead();
      }

      if (this.world.isRemote) {
         if (this.rand.nextFloat() < Math.max(1.0F - this.ticksExisted / 20.0F, 0.1F)) {
            int lt = 8 + this.rand.nextInt(4);
            float scl = 0.1F + this.rand.nextFloat() * 0.07F;
            GUNParticle part = new GUNParticle(
               star2,
               scl,
               0.0F,
               lt,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 60.0F,
               (float)this.rand.nextGaussian() / 60.0F,
               (float)this.rand.nextGaussian() / 60.0F,
               0.7F + this.rand.nextFloat() * 0.3F,
               0.7F + this.rand.nextFloat() * 0.3F,
               0.25F,
               true,
               this.rand.nextInt(360)
            );
            part.scaleTickAdding = -scl / lt;
            part.alphaGlowing = true;
            this.world.spawnEntity(part);
         }
      } else if (this.explodeInstantly && this.ticksExisted > 3) {
         float damage = this.getDamage();
         this.expl(null, damage * WeaponParameters.getWeaponParameters(this.weaponstack.getItem()).get("damage_explode_mult"));
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.xmass_launcher_impact,
               SoundCategory.AMBIENT,
               1.4F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY, this.posZ, 1.0);
         this.spawnBalls(true);
         this.setDead();
      }
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 3 || args.length == 4) {
         double x = args[0];
         double y = args[1];
         double z = args[2];
         boolean alternative = args.length == 4;
         int leavesId = Block.getStateId(Blocks.LEAVES.getStateFromMeta(1));
         if (!alternative) {
            for (int ss = 0; ss < 50; ss++) {
               this.world
                  .spawnParticle(
                     EnumParticleTypes.BLOCK_CRACK,
                     x + this.rand.nextDouble() - 0.5,
                     y + this.rand.nextDouble() - 0.5,
                     z + this.rand.nextDouble() - 0.5,
                     this.rand.nextGaussian() / 2.0,
                     this.rand.nextGaussian() / 2.0,
                     this.rand.nextGaussian() / 2.0,
                     new int[]{leavesId}
                  );
            }
         }

         float scalemult = alternative ? 0.2F : 1.0F;

         for (int ss = 0; ss < 4; ss++) {
            GUNParticle part1 = new GUNParticle(
               sparkle3,
               (0.5F + this.rand.nextFloat() * 0.6F) * scalemult,
               0.0F,
               5,
               240,
               this.world,
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
               this.rand.nextInt(360)
            );
            part1.scaleTickAdding = 0.1F * scalemult;
            part1.alphaGlowing = true;
            if (ss != 0) {
               part1.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
            }

            this.world.spawnEntity(part1);
         }

         int starlt = alternative ? 20 : 10;

         for (int ss = 0; ss < 14; ss++) {
            int lt = starlt + this.rand.nextInt(10);
            float scl = 0.1F + this.rand.nextFloat() * 0.07F;
            GUNParticle part = new GUNParticle(
               star2,
               scl,
               0.0F,
               lt,
               240,
               this.world,
               x,
               y,
               z,
               (float)this.rand.nextGaussian() / 10.0F,
               (float)this.rand.nextGaussian() / 10.0F,
               (float)this.rand.nextGaussian() / 10.0F,
               0.7F + this.rand.nextFloat() * 0.3F,
               0.7F + this.rand.nextFloat() * 0.3F,
               0.25F,
               true,
               this.rand.nextInt(360)
            );
            part.scaleTickAdding = -scl / lt;
            part.tracker = ptss;
            part.alphaGlowing = true;
            if (alternative) {
               part.motionX = part.motionX + this.motionX;
               part.motionY = part.motionY + this.motionY;
               part.motionZ = part.motionZ + this.motionZ;
            }

            this.world.spawnEntity(part);
         }

         if (!alternative) {
            GUNParticle part1 = new GUNParticle(
               circle2,
               0.5F + this.rand.nextFloat() * 0.6F,
               0.0F,
               6,
               240,
               this.world,
               x,
               y,
               z,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               0.8F,
               true,
               this.rand.nextInt(360)
            );
            part1.scaleTickAdding = 0.5F;
            part1.alphaTickAdding = -0.15F;
            part1.alphaGlowing = true;
            this.world.spawnEntity(part1);
         }
      }
   }

   public void spawnBalls(boolean alternative) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
      int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, this.weaponstack);
      float balldamage = parameters.getEnchanted("ball_damage", might);
      int balls = parameters.getEnchantedi("balls", reuse);
      if (alternative) {
         for (int i = 0; i < balls; i++) {
            XmassBall ball = this.thrower == null
               ? new XmassBall(this.world, this.weaponstack)
               : new XmassBall(this.world, this.thrower, this.weaponstack);
            Vec3d start = new Vec3d(
               this.posX + (this.rand.nextDouble() - 0.5) * 0.5,
               this.posY + (this.rand.nextDouble() - 0.5) * 0.5,
               this.posZ + (this.rand.nextDouble() - 0.5) * 0.5
            );
            ball.motionX = this.motionX + (this.rand.nextDouble() - 0.5) * 0.3;
            ball.motionY = this.motionY + (this.rand.nextDouble() - 0.5) * 0.3;
            ball.motionZ = this.motionZ + (this.rand.nextDouble() - 0.5) * 0.3;
            ball.damage = balldamage;
            ball.setPosition(start.x, start.y, start.z);
            ball.alternative = true;
            this.world.spawnEntity(ball);
         }
      } else {
         for (int i = 0; i < balls; i++) {
            XmassBall ball = this.thrower == null
               ? new XmassBall(this.world, this.weaponstack)
               : new XmassBall(this.world, this.thrower, this.weaponstack);
            boolean success = false;

            for (int j = 0; j < 16; j++) {
               double veclength = success ? 7.0 : 2.5;
               Vec3d start = new Vec3d(
                  this.posX + (this.rand.nextDouble() - 0.5) * 0.5,
                  this.posY + (this.rand.nextDouble() - 0.5) * 0.5,
                  this.posZ + (this.rand.nextDouble() - 0.5) * 0.5
               );
               Vec3d end = start.add(
                  (this.rand.nextDouble() - 0.5) * veclength,
                  (this.rand.nextDouble() - 0.5) * veclength,
                  (this.rand.nextDouble() - 0.5) * veclength
               );
               RayTraceResult rayTraceResult = GetMOP.fixedRayTraceBlocks(
                  this.world, this.thrower, 0.1, 0.1, true, start, end, false, true, false
               );
               boolean hitEntity = rayTraceResult != null
                  && rayTraceResult.entityHit != null
                  && Team.checkIsOpponent(this.thrower, rayTraceResult.entityHit);
               if ((!success || hitEntity) && (rayTraceResult == null || rayTraceResult.typeOfHit != Type.BLOCK)) {
                  ball.setPosition(start.x, start.y, start.z);
                  SuperKnockback.setMove(ball, -0.7F, end.x, end.y, end.z);
                  success = true;
                  if (hitEntity) {
                     break;
                  }
               }
            }

            if (!success) {
               ball.setPosition(this.posX, this.posY, this.posZ);
               ball.motionX = this.rand.nextDouble() - 0.5;
               ball.motionY = this.rand.nextDouble() - 0.5;
               ball.motionZ = this.rand.nextDouble() - 0.5;
            }

            ball.damage = balldamage;
            this.world.spawnEntity(ball);
         }
      }
   }

   public void expl(Entity mainTarget, float damage) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
      if (!list.isEmpty()) {
         for (Entity entity : list) {
            if (entity != mainTarget) {
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                  damage,
                  this.getThrower(),
                  entity,
                  true,
                  parameters.getEnchanted("knockback", impulse),
                  this.posX,
                  this.posY,
                  this.posZ
               );
               entity.hurtResistantTime = 0;
            }
         }
      }
   }

   public float getDamage() {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
      return parameters.getEnchanted("damage", might);
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            float damage = this.getDamage();
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, true, true, this, WeaponDamage.explode),
               damage,
               this.getThrower(),
               result.entityHit,
               true,
               parameters.getEnchanted("knockback", impulse),
               this.posX,
               this.posY,
               this.posZ
            );
            result.entityHit.hurtResistantTime = 0;
            DeathEffects.applyDeathEffect(result.entityHit, DeathEffects.DE_DISMEMBER, 0.35F);
            this.expl(result.entityHit, damage * parameters.get("damage_explode_mult"));
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.xmass_launcher_impact,
                  SoundCategory.AMBIENT,
                  1.4F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY, this.posZ);
            this.spawnBalls(false);
            this.setDead();
         }
      } else if (this.world
            .getBlockState(result.getBlockPos())
            .getBlock()
            .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
         != null) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.xmass_launcher_impact,
               SoundCategory.AMBIENT,
               1.4F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (!this.world.isRemote) {
            float damage = this.getDamage();
            this.expl(result.entityHit, damage * WeaponParameters.getWeaponParameters(this.weaponstack.getItem()).get("damage_explode_mult"));
            if (result.hitVec != null) {
               IEntitySynchronize.sendSynchronize(
                  this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z
               );
            }

            this.spawnBalls(false);
            this.setDead();
         }
      }
   }
}
