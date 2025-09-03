//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.MovingSoundEntity;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.AnimatedGParticle;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CeratargetShoot extends EntityThrowable implements IEntitySynchronize {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   static ResourceLocation sparkle4 = new ResourceLocation("arpg:textures/sparkle4.png");
   static ResourceLocation sparkle2 = new ResourceLocation("arpg:textures/sparkle2.png");
   static ResourceLocation textureExplode = new ResourceLocation("arpg:textures/bullet_explode2.png");
   public boolean impacted = false;
   public Vec3d impactPos;
   public Vec3d followPoint;
   public Entity impactEntity;
   public float fixedPitch = 0.0F;
   public float fixedYaw = 0.0F;
   public int ticksImpacted = 0;
   public boolean firstUpdate1 = true;
   public boolean canBlow = true;
   public MovingSoundEntity movingsound;

   public CeratargetShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.CERATARGET);
   }

   public CeratargetShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.CERATARGET);
   }

   public CeratargetShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.CERATARGET);
   }

   public CeratargetShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.world.isRemote && this.firstUpdate1) {
         this.firstUpdate1 = false;
         this.movingsound = new MovingSoundEntity(this, Sounds.ceratarget_fly, SoundCategory.PLAYERS, 1.0F, 0.9F + this.rand.nextFloat() / 5.0F, false);
         Minecraft.getMinecraft().getSoundHandler().playSound(this.movingsound);
      }

      if (this.ticksExisted % 10 == 0) {
         this.world.setEntityState(this, (byte)(this.canBlow ? 10 : 11));
      }

      if (!this.impacted) {
         if (this.ticksExisted > 80) {
            this.setDead();
         }

         if (this.ticksExisted % 2 == 0 && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            double far = 1.5;
            EntityLivingBase entity = GetMOP.findNearestEnemy(
               this.world,
               this.thrower,
               this.posX + this.motionX * far,
               this.posY + this.motionY * far,
               this.posZ + this.motionZ * far,
               5.0,
               true
            );
            if (entity != null) {
               double multMotion = parameters.get("friction");
               this.motionX *= multMotion;
               this.motionY *= multMotion;
               this.motionZ *= multMotion;
               SuperKnockback.applyMove(
                  this, -parameters.get("follow_power_entity"), entity.posX, entity.posY + entity.height / 2.0F, entity.posZ
               );
            } else if (this.followPoint != null) {
               SuperKnockback.applyMove(
                  this, -parameters.get("follow_power_point"), this.followPoint.x, this.followPoint.y, this.followPoint.z
               );
            }
         }
      } else {
         if (this.movingsound != null) {
            Minecraft.getMinecraft().getSoundHandler().stopSound(this.movingsound);
            this.movingsound = null;
         }

         if (this.impactPos != null) {
            if (this.impactEntity == null) {
               this.setPosition(this.impactPos.x, this.impactPos.y, this.impactPos.z);
            } else if (!this.impactEntity.isDead
               && (!(this.impactEntity instanceof EntityLivingBase) || !(((EntityLivingBase)this.impactEntity).getHealth() <= 0.0F))) {
               this.setPosition(
                  this.impactEntity.posX + this.impactPos.x,
                  this.impactEntity.posY + this.impactPos.y,
                  this.impactEntity.posZ + this.impactPos.z
               );
            } else {
               this.expl();
               this.setDead();
            }
         }

         this.ticksImpacted++;
         if (this.ticksImpacted > (this.canBlow ? 60 : 1000)) {
            this.expl();
            this.setDead();
         }

         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         this.lastTickPosX = this.posX;
         this.lastTickPosY = this.posY;
         this.lastTickPosZ = this.posZ;
      }
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      this.impactPos = new Vec3d(x, y, z);
      if (a != 0.0) {
         this.impactEntity = this.world.getEntityByID((int)b);
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         AnimatedGParticle anim = new AnimatedGParticle(
            textureExplode,
            0.7F + this.rand.nextFloat() * 0.4F,
            0.0F,
            8,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         anim.framecount = 8;
         anim.alphaGlowing = true;
         anim.randomDeath = false;
         this.world.spawnEntity(anim);
         Vec3d mainDirection = new Vec3d(
            this.rand.nextGaussian() / 10.0, this.rand.nextGaussian() / 10.0, this.rand.nextGaussian() / 10.0
         );

         for (int ss = 0; ss < this.rand.nextInt(6) + 10; ss++) {
            GUNParticle part = new GUNParticle(
               this.rand.nextFloat() < 0.6 ? sparkle4 : sparkle2,
               0.03F + this.rand.nextFloat() / 30.0F,
               0.025F,
               40 + this.rand.nextInt(20),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)(this.rand.nextGaussian() / 16.0 + mainDirection.x),
               (float)(this.rand.nextGaussian() / 16.0 + mainDirection.y),
               (float)(this.rand.nextGaussian() / 16.0 + mainDirection.z),
               1.0F,
               0.75F - this.rand.nextFloat() * 0.2F,
               0.3F - this.rand.nextFloat() * 0.15F,
               true,
               this.rand.nextInt(360)
            );
            part.alphaGlowing = true;
            this.world.spawnEntity(part);
         }
      }

      if (id == 8) {
         this.impacted = true;
         this.fixedPitch = this.rotationPitch;
         this.fixedYaw = this.rotationYaw;
         this.motionX = 0.0;
         this.motionY = 0.0;
         this.motionZ = 0.0;
      }

      if (id == 10) {
         this.canBlow = true;
      }

      if (id == 11) {
         this.canBlow = false;
      }
   }

   public void expl() {
      if (!this.world.isRemote) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.ceratarget_impact,
               SoundCategory.AMBIENT,
               1.2F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity) && !(entity instanceof CeratargetShoot) && Weapons.canDealDamageTo(entity)) {
                  int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
                  int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
                  Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                     parameters.getEnchanted("damage", might) * this.magicPower,
                     this.getThrower(),
                     entity,
                     true,
                     parameters.getEnchanted("knockback", impulse),
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
                  DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER, 0.25F);
               }
            }
         }

         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.impacted) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)
               && Weapons.canDealDamageTo(result.entityHit)
               && !this.world.isRemote) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.magic),
                  parameters.getEnchanted("damage", might) * this.magicPower,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  parameters.getEnchanted("knockback", impulse)
               );
               result.entityHit.hurtResistantTime = 0;
               this.impacted = true;
               this.impactEntity = result.entityHit;
               Vec3d point = GetMOP.getNearestPointInAABBtoPoint(GetMOP.entityCenterPos(this), result.entityHit.getEntityBoundingBox());
               this.impactPos = new Vec3d(
                  point.x - result.entityHit.posX,
                  point.y - result.entityHit.posY,
                  point.z - result.entityHit.posZ
               );
               IEntitySynchronize.sendSynchronize(
                  this,
                  64.0,
                  this.impactPos.x,
                  this.impactPos.y,
                  this.impactPos.z,
                  1.0,
                  result.entityHit.getEntityId(),
                  0.0
               );
               this.motionX = 0.0;
               this.motionY = 0.0;
               this.motionZ = 0.0;
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.normal_projectile,
                     SoundCategory.AMBIENT,
                     0.8F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
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
                  Sounds.normal_projectile,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.impacted = true;
               this.impactPos = result.hitVec;
               IEntitySynchronize.sendSynchronize(
                  this, 64.0, this.impactPos.x, this.impactPos.y, this.impactPos.z, 0.0, 0.0, 0.0
               );
               this.motionX = 0.0;
               this.motionY = 0.0;
               this.motionZ = 0.0;
               this.world.setEntityState(this, (byte)8);
            }
         }
      }
   }
}
