//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import com.Vivern.Arpg.renders.RenderModule;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WandColdWave extends EntityThrowable implements IEntitySynchronize, RenderModule.IRenderModuleOverride {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public List<Entity> impacted = new ArrayList<>();
   public Vec3d bladeNormal = new Vec3d(0.0, 1.0, 0.0);
   public float damage = 7.0F;
   public float knockback = 1.0F;
   public float rotationRoll = 0.0F;
   static ResourceLocation snowflake3 = new ResourceLocation("arpg:textures/snowflake3.png");
   static ResourceLocation mana_flow = new ResourceLocation("arpg:textures/mana_flow.png");
   static ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");
   private static final DataParameter<Float> CUTTER_SIZE = EntityDataManager.createKey(WandColdWave.class, DataSerializers.FLOAT);
   public static int ticksForMaxWidth = 10;
   static ParticleTracker.TrackerSmoothShowHide tssh = new ParticleTracker.TrackerSmoothShowHide(
      new Vec3d[]{new Vec3d(0.0, 4.0, 0.25), new Vec3d(6.0, 17.0, -0.09)}, null
   );

   public WandColdWave(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.WANDOFCOLD);
      this.setSize(0.1F, 0.1F);
   }

   public WandColdWave(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.WANDOFCOLD);
      this.setSize(0.1F, 0.1F);
   }

   public WandColdWave(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.WANDOFCOLD);
      this.setSize(0.1F, 0.1F);
   }

   public WandColdWave(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
      this.setSize(0.1F, 0.1F);
   }

   protected void entityInit() {
      this.dataManager.register(CUTTER_SIZE, 1.0F);
   }

   public float getCutterSize() {
      return (Float)this.dataManager.get(CUTTER_SIZE);
   }

   public void setCutterSize(float value) {
      this.dataManager.set(CUTTER_SIZE, value);
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      double mot = 0.3;
      this.motionX = this.motionX + entityThrower.motionX * mot;
      this.motionZ = this.motionZ + entityThrower.motionZ * mot;
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderDist(double distance) {
      double d0 = 64.0;
      return distance < d0 * d0;
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public double min(double d1, double d2, double d3, double d4) {
      return Math.min(Math.min(d1, d2), Math.min(d3, d4));
   }

   public double max(double d1, double d2, double d3, double d4) {
      return Math.max(Math.max(d1, d2), Math.max(d3, d4));
   }

   @Override
   public void rewriteModuleParameters(RenderModule module) {
      if (module instanceof RenderModule.RModCutter) {
         RenderModule.RModCutter moduleCutter = (RenderModule.RModCutter)module;
         moduleCutter.cutterSize = this.getCutterSize();
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted < 2 || this.ticksExisted % 10 == 0) {
         Vec3d look = this.getVectorForRotation(this.rotationPitch, -this.rotationYaw);
         this.bladeNormal = GetMOP.rotateVecAroundAxis(
            GetMOP.PitchYawToVec3d(this.rotationPitch - 90.0F, -this.rotationYaw), look, (float)Math.toRadians(this.rotationRoll)
         );
         IEntitySynchronize.sendSynchronize(this, 64.0, this.rotationRoll);
      }

      if (!this.world.isRemote) {
         if (this.ticksExisted > 30) {
            this.setDead();
         }

         if (!this.isDead) {
            Vec3d center = GetMOP.entityCenterPos(this);
            Vec3d centerPrev = new Vec3d(this.prevPosX, this.prevPosY, this.prevPosZ);
            Vec3d look = this.getVectorForRotation(this.rotationPitch, -this.rotationYaw);
            float beamwidth = this.getCutterSize() * GetMOP.getfromto((float)Math.max(this.ticksExisted, 4), 0.0F, (float)ticksForMaxWidth);
            Vec3d lookScaled = look.scale(beamwidth / 2.0F);
            Vec3d rightTip = GetMOP.rotateVecAroundAxis(lookScaled, this.bladeNormal, 1.570796F);
            Vec3d leftTip = GetMOP.rotateVecAroundAxis(lookScaled, this.bladeNormal, -1.570796F);
            Vec3d rightPos = center.add(rightTip);
            Vec3d leftPos = center.add(leftTip);
            Vec3d prevRightPos = centerPrev.add(rightTip);
            Vec3d prevLeftPos = centerPrev.add(leftTip);
            AxisAlignedBB aabb = new AxisAlignedBB(
               this.min(rightPos.x, leftPos.x, prevRightPos.x, prevLeftPos.x),
               this.min(rightPos.y, leftPos.y, prevRightPos.y, prevLeftPos.y),
               this.min(rightPos.z, leftPos.z, prevRightPos.z, prevLeftPos.z),
               this.max(rightPos.x, leftPos.x, prevRightPos.x, prevLeftPos.x),
               this.max(rightPos.y, leftPos.y, prevRightPos.y, prevLeftPos.y),
               this.max(rightPos.z, leftPos.z, prevRightPos.z, prevLeftPos.z)
            );
            aabb = aabb.grow(0.25);

            for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, aabb)) {
               if (GetMOP.isBoxInPlane(center, this.bladeNormal, entity.getEntityBoundingBox().grow(0.2F))
                  && Team.checkIsOpponent(this.thrower, entity)
                  && !this.impacted.contains(entity)) {
                  WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
                  int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
                  Vec3d damageFrom = GetMOP.entityCenterPos(entity).subtract(look);
                  Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, false, false, damageFrom, WeaponDamage.blade),
                     this.damage * this.magicPower,
                     this.thrower,
                     entity,
                     true,
                     this.knockback,
                     damageFrom.x,
                     damageFrom.y,
                     damageFrom.z
                  );
                  entity.hurtResistantTime = 0;
                  if (entity.onGround) {
                     entity.motionY /= 2.0;
                     entity.motionY += 0.4;
                  }

                  DeathEffects.applyDeathEffect(entity, DeathEffects.DE_ICING, 0.3F);
                  Weapons.setPotionIfEntityLB(
                     entity, PotionEffects.COLD_SOUL, parameters.getEnchantedi("potion_time", witchery), parameters.getEnchantedi("potion_power", witchery)
                  );
                  this.impacted.add(entity);
               }
            }
         }
      } else {
         Vec3d center = GetMOP.entityCenterPos(this);
         float ft1 = GetMOP.getfromto((float)Math.max(this.ticksExisted, 3), 0.0F, (float)ticksForMaxWidth);
         float beamwidth = this.getCutterSize() * ft1;
         Vec3d look = this.getVectorForRotation(this.rotationPitch, -this.rotationYaw);
         Vec3d lookScaled = look.scale(beamwidth / 2.0F);
         Vec3d rightTip = GetMOP.rotateVecAroundAxis(lookScaled, this.bladeNormal, 1.570796F);
         Vec3d partpos = center.add(rightTip.scale((this.rand.nextFloat() - 0.5) * beamwidth / 2.0));
         float scl = 0.18F + this.rand.nextFloat() * 0.11F;
         int lt = this.rand.nextInt(7) + 13;
         GUNParticle bigsmoke = new GUNParticle(
            snowflake3,
            scl,
            0.0F,
            lt,
            200,
            this.world,
            partpos.x,
            partpos.y,
            partpos.z,
            0.0F,
            0.0F,
            0.0F,
            0.6F + this.rand.nextFloat() * 0.2F,
            0.9F + this.rand.nextFloat() / 10.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke.alphaTickAdding = -1.0F / lt;
         bigsmoke.alphaGlowing = true;
         bigsmoke.scaleTickAdding = -scl / lt * 0.95F;
         bigsmoke.rotationPitchYaw = new Vec2f(this.rotationPitch + 90.0F, -this.rotationYaw);
         bigsmoke.randomDeath = false;
         this.world.spawnEntity(bigsmoke);
         partpos = center.add(rightTip.scale((this.rand.nextFloat() - 0.5) * beamwidth / 2.0));
         scl = 0.08F + this.rand.nextFloat() * 0.08F;
         lt = this.rand.nextInt(7) + 13;
         bigsmoke = new GUNParticle(
            mana_flow,
            scl,
            0.0F,
            lt,
            200,
            this.world,
            partpos.x,
            partpos.y,
            partpos.z,
            0.0F,
            0.0F,
            0.0F,
            0.4F + this.rand.nextFloat() * 0.2F,
            0.8F + this.rand.nextFloat() * 0.2F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke.alphaTickAdding = -0.5F / lt;
         bigsmoke.alphaGlowing = true;
         bigsmoke.scaleTickAdding = -scl / lt * 0.95F;
         bigsmoke.randomDeath = false;
         this.world.spawnEntity(bigsmoke);
         if (this.ticksExisted % 2 == 0) {
            partpos = center.add(rightTip.scale((this.rand.nextFloat() - 0.5) * beamwidth / 3.5));
            scl = (0.9F + this.rand.nextFloat() * 0.5F) * ft1;
            lt = 17;
            bigsmoke = new GUNParticle(
               largecloud,
               scl,
               0.0F,
               lt,
               200,
               this.world,
               partpos.x,
               partpos.y,
               partpos.z,
               0.0F,
               0.0F,
               0.0F,
               0.4F + this.rand.nextFloat() * 0.2F,
               0.7F + this.rand.nextFloat() / 10.0F,
               0.8F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alpha = 0.0F;
            bigsmoke.alphaGlowing = true;
            bigsmoke.randomDeath = false;
            bigsmoke.tracker = tssh;
            this.world.spawnEntity(bigsmoke);
         }
      }
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         this.rotationRoll = (float)args[0];
      } else if (args.length == 3) {
         Vec3d center = new Vec3d(args[0], args[1], args[2]);
         Vec3d look = this.getVectorForRotation(this.rotationPitch, -this.rotationYaw);
         Vec3d lookScaled = look.scale(1.3);
         Vec3d rightTip = GetMOP.rotateVecAroundAxis(lookScaled, this.bladeNormal, 1.570796F);
         float beamwidth = this.getCutterSize() * GetMOP.getfromto((float)this.ticksExisted, 0.0F, (float)ticksForMaxWidth);

         for (int ss = 0; ss < 21; ss++) {
            Vec3d partpos = center.add(rightTip.scale((this.rand.nextFloat() - 0.5) * beamwidth));
            float scl = 0.1F + this.rand.nextFloat() * 0.08F;
            int lt = this.rand.nextInt(7) + 12;
            GUNParticle bigsmoke = new GUNParticle(
               mana_flow,
               scl,
               0.0F,
               lt,
               200,
               this.world,
               partpos.x,
               partpos.y,
               partpos.z,
               (float)this.rand.nextGaussian() / 25.0F,
               (float)this.rand.nextGaussian() / 25.0F,
               (float)this.rand.nextGaussian() / 25.0F,
               0.8F,
               0.9F + this.rand.nextFloat() / 10.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.5F / lt;
            bigsmoke.alphaGlowing = true;
            bigsmoke.scaleTickAdding = -scl / lt * 0.95F;
            bigsmoke.randomDeath = false;
            this.world.spawnEntity(bigsmoke);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.whispers_blade_impact,
               SoundCategory.AMBIENT,
               1.1F,
               0.9F + this.rand.nextFloat() * 0.2F,
               false
            );
      }
   }

   public boolean handleWaterMovement() {
      return false;
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.world.isRemote
         && result.entityHit == null
         && this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
         if (result.hitVec != null) {
            IEntitySynchronize.sendSynchronize(
               this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z
            );
         }

         this.world.setEntityState(this, (byte)8);
         this.setDead();
      }
   }
}
