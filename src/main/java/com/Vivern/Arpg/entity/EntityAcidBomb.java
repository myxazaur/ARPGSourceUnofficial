//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class EntityAcidBomb extends Entity implements IEntitySynchronize {
   @Nullable
   public EntityLivingBase tntPlacedBy;
   public int fuse = 80;
   public float power;
   public static ResourceLocation sptex = new ResourceLocation("arpg:textures/scatter.png");
   public static ResourceLocation splash = new ResourceLocation("arpg:textures/acid_splash2.png");
   public static ResourceLocation bubble = new ResourceLocation("arpg:textures/acid_splash3.png");
   public static ResourceLocation tox = new ResourceLocation("arpg:textures/toxic_spell.png");
   public static ParticleTracker.TrackerSmoothShowHide ssh = new ParticleTracker.TrackerSmoothShowHide(
      null, new Vec3d[]{new Vec3d(0.0, 7.0, 0.6), new Vec3d(8.0, 12.0, -0.9)}
   );

   public EntityAcidBomb(World worldIn) {
      super(worldIn);
      this.preventEntitySpawning = true;
      this.isImmuneToFire = true;
      this.setSize(0.98F, 0.98F);
   }

   public EntityAcidBomb(World worldIn, double x, double y, double z, EntityLivingBase igniter, float power) {
      this(worldIn);
      this.setPosition(x, y, z);
      float f = (float)(Math.random() * (Math.PI * 2));
      this.motionX = -((float)Math.sin(f)) * 0.02F;
      this.motionY = 0.2F;
      this.motionZ = -((float)Math.cos(f)) * 0.02F;
      this.fuse = 80;
      this.prevPosX = x;
      this.prevPosY = y;
      this.prevPosZ = z;
      this.tntPlacedBy = igniter;
      this.power = power;
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (!this.hasNoGravity()) {
         this.motionY -= 0.04F;
      }

      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.98F;
      this.motionY *= 0.98F;
      this.motionZ *= 0.98F;
      if (this.onGround) {
         this.motionX *= 0.7F;
         this.motionZ *= 0.7F;
         this.motionY *= -0.5;
      }

      this.fuse--;
      if (this.fuse <= 0) {
         this.setDead();
         if (!this.world.isRemote) {
            this.explode();
         }
      } else {
         this.handleWaterMovement();
         this.world
            .spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0, new int[0]);
      }
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      if (a != 0.0) {
         this.width = (float)a;
         this.height = (float)b;
      } else {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.frozen_explode,
               SoundCategory.BLOCKS,
               (float)(1.0 + c * 0.5),
               0.8F + this.rand.nextFloat() * 0.4F,
               false
            );

         for (int ss = 0; ss < 2.0 * c; ss++) {
            float size = (float)((0.3 + this.rand.nextGaussian() / 20.0) * c);
            int lt = 20 + this.rand.nextInt(10);
            GUNParticle bigsmoke = new GUNParticle(
               splash,
               (float)((0.3 + this.rand.nextGaussian() / 20.0) * c),
               -0.005F,
               lt,
               200,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)(this.rand.nextGaussian() * c / 30.0),
               (float)(this.rand.nextGaussian() * c / 30.0),
               (float)(this.rand.nextGaussian() * c / 30.0),
               0.75F + this.rand.nextFloat() / 5.0F,
               1.0F,
               0.65F + this.rand.nextFloat() / 5.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.scaleTickAdding = -size / lt / 2.0F;
            bigsmoke.alphaTickAdding = -1.0F / lt;
            bigsmoke.acidRender = this.rand.nextFloat() < 0.5;
            this.world.spawnEntity(bigsmoke);
         }

         for (int ss = 0; ss < c; ss++) {
            float size = (float)((0.08 + this.rand.nextGaussian() / 60.0) * c);
            int lt = 40 + this.rand.nextInt(20);
            GUNParticle bigsmoke = new GUNParticle(
               bubble,
               size,
               -0.01F,
               lt,
               180,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)(this.rand.nextGaussian() * c / 40.0),
               (float)(this.rand.nextGaussian() * c / 40.0),
               (float)(this.rand.nextGaussian() * c / 40.0),
               0.9F + this.rand.nextFloat() / 10.0F,
               1.0F,
               0.8F + this.rand.nextFloat() / 10.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.scaleTickAdding = -size / lt;
            this.world.spawnEntity(bigsmoke);
         }

         int countOfParticles = (int)(2.0 * c);
         float R = (float)((0.04 + this.rand.nextGaussian() / 60.0) * c);

         for (int i = 0; i < countOfParticles; i++) {
            float rand1 = this.rand.nextFloat() * 2.0F - 1.0F;
            float rand2 = this.rand.nextFloat() * 2.0F - 1.0F;
            float X = rand1 * R;
            float new_R = (float)Math.sqrt(R * R - X * X);
            float Y = rand2 * new_R;
            float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
            if (this.rand.nextBoolean()) {
               Z *= -1.0F;
            }

            GUNParticle particle = new GUNParticle(
               tox,
               0.2F + (float)this.rand.nextGaussian() / 30.0F,
               0.01F,
               22 + this.rand.nextInt(10),
               200,
               this.world,
               x,
               y,
               z,
               X,
               Y,
               Z,
               0.9F + this.rand.nextFloat() / 10.0F,
               1.0F,
               0.9F + this.rand.nextFloat() / 10.0F,
               true,
               this.rand.nextInt(360)
            );
            particle.alphaGlowing = true;
            this.world.spawnEntity(particle);
         }

         for (int ss = 0; ss < 6; ss++) {
            GUNParticle part = new GUNParticle(
               sptex,
               (float)(0.1 * c),
               0.0F,
               12,
               200,
               this.world,
               this.posX + this.rand.nextGaussian(),
               this.posY + this.rand.nextGaussian(),
               this.posZ + this.rand.nextGaussian(),
               (float)(this.rand.nextGaussian() * c / 25.0),
               (float)(this.rand.nextGaussian() * c / 25.0),
               (float)(this.rand.nextGaussian() * c / 25.0),
               0.4F + this.rand.nextFloat() / 4.0F,
               1.0F,
               0.35F + this.rand.nextFloat() / 4.0F,
               true,
               this.rand.nextInt(360)
            );
            part.tracker = ssh;
            this.world.spawnEntity(part);
         }

         if (Minecraft.getMinecraft().getRenderViewEntity() != null) {
            float distr = Minecraft.getMinecraft().getRenderViewEntity().getDistance(this);
            float inc = (float)(c / 2.0);
            if (distr < 4.0F * inc) {
               Booom.lastTick = Math.round(5.0F * inc - distr);
               Booom.frequency = 3.5F;
               Booom.x = (float)this.rand.nextGaussian();
               Booom.y = (float)this.rand.nextGaussian();
               Booom.z = (float)this.rand.nextGaussian();
               Booom.power = 0.06F * inc;
            }
         }
      }
   }

   public void explode() {
      double damageRadius = this.power * 0.25;
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
      if (!list.isEmpty()) {
         for (Entity entity : list) {
            if (!this.world.isRemote) {
               float finalPower = (float)Math.max(
                  damageRadius - Weapons.getDistanceToMobHitbox(entity, this.posX, this.posY, this.posZ), 0.0
               );
               entity.hurtResistantTime = 0;
               Weapons.dealDamage(
                  DamageSource.causeExplosionDamage(this.tntPlacedBy),
                  10.0F * finalPower,
                  this.tntPlacedBy,
                  entity,
                  true,
                  finalPower / 2.0F,
                  this.posX,
                  this.posY,
                  this.posZ
               );
               entity.hurtResistantTime = 0;
               Weapons.setPotionIfEntityLB(entity, MobEffects.POISON, (int)(finalPower * 20.0F), 1);
            }

            if (this.rand.nextFloat() < 0.6 && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0F) {
               IAttributeInstance red = ((EntityLivingBase)entity).getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_RED_MAX);
               IAttributeInstance blue = ((EntityLivingBase)entity).getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX);
               red.applyModifier(new AttributeModifier("acid.explode.red.modif", 0.5, 1));
               blue.applyModifier(new AttributeModifier("acid.explode.blue.modif", 0.5, 1));
               DeathEffects.applyDeathEffect((EntityLivingBase)entity, DeathEffects.DE_COLOREDACID);
            }
         }
      }

      if (!this.world.isRemote) {
         IEntitySynchronize.sendSynchronize(
            this, 64.0, this.posX, this.posY + this.height / 2.0F, this.posZ, 0.0, 0.0, this.power
         );
      }

      if (this.world.isAreaLoaded(this.getPosition().add(-16, -16, -16), this.getPosition().add(16, 16, 16))) {
         for (BlockPos blockpos : GetMOP.getPosesInsideSphere(
            (int)this.posX, (int)this.posY, (int)this.posZ, (int)(this.power * 0.3)
         )) {
            Weapons.triggerExplodeBlock(this.world, blockpos, this.tntPlacedBy, this);
         }

         int radius = (int)(this.power * 0.4);
         Vec3d pos1 = this.getPositionVector();

         for (int i = 0; i <= this.power * 3.0F; i++) {
            Vec3d pos2 = new Vec3d(
               this.posX + this.rand.nextInt(radius + 1) - radius,
               this.posY + this.rand.nextInt(radius + 1) - radius,
               this.posZ + this.rand.nextInt(radius + 1) - radius
            );
            RayTraceResult res = this.world.rayTraceBlocks(pos1, pos2, false);
            if (res != null
               && res.typeOfHit == Type.BLOCK
               && res.getBlockPos() != null
               && Weapons.easyBreakBlockFor(this.world, this.power * 0.5F, res.getBlockPos())) {
               this.world
                  .setBlockState(res.getBlockPos(), BlocksRegister.FLUIDBIOGENICACID.getDefaultState().withProperty(BlockFluidClassic.LEVEL, 1));
            }
         }

         if (this.power > 0.0F) {
            float R = (float)(0.1 * this.power);

            for (int ix = 0; ix < this.power; ix++) {
               float rand1 = this.rand.nextFloat() * 2.0F - 1.0F;
               float rand2 = this.rand.nextFloat() * 2.0F - 1.0F;
               float X = rand1 * R;
               float new_R = (float)Math.sqrt(R * R - X * X);
               float Y = rand2 * new_R;
               float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
               if (this.rand.nextBoolean()) {
                  Z *= -1.0F;
               }

               AcidBombBlob expl = new AcidBombBlob(
                  this.world, this.tntPlacedBy, this.posX, this.posY + this.height / 2.0F, this.posZ
               );
               expl.motionX = X;
               expl.motionY = Y;
               expl.motionZ = Z;
               this.world.spawnEntity(expl);
               expl.velocityChanged = true;
            }
         }
      }
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
      compound.setShort("Fuse", (short)this.fuse);
      compound.setFloat("power", this.power);
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("Fuse")) {
         this.fuse = compound.getShort("Fuse");
      }

      if (compound.hasKey("power")) {
         this.power = compound.getFloat("power");
      }
   }

   @Nullable
   public EntityLivingBase getTntPlacedBy() {
      return this.tntPlacedBy;
   }

   public float getEyeHeight() {
      return 0.0F;
   }

   protected void entityInit() {
   }

   public static class AcidBombBlob extends EntityThrowable {
      public float damage = 0.0F;

      public AcidBombBlob(World world) {
         super(world);
      }

      public AcidBombBlob(World world, EntityLivingBase thrower) {
         super(world, thrower);
      }

      public AcidBombBlob(World world, double x, double y, double z) {
         super(world, x, y, z);
      }

      public AcidBombBlob(World world, EntityLivingBase thrower, double x, double y, double z) {
         super(world, x, y, z);
         this.thrower = thrower;
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

      public boolean handleWaterMovement() {
         return false;
      }

      protected float getGravityVelocity() {
         return 0.015F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 25) {
            this.setDead();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (!this.world.isRemote && !(result.entityHit instanceof AcidBombBlob)) {
               Weapons.dealDamage(
                  DamageSource.causeExplosionDamage(this.thrower),
                  this.damage,
                  this.thrower,
                  result.entityHit,
                  true,
                  1.5F,
                  this.posX,
                  this.posY,
                  this.posZ
               );
               result.entityHit.hurtResistantTime = 0;
               BlockPos pos = result.entityHit.getPosition();
               boolean isair = this.world.isAirBlock(pos);
               if ((!isair || isair && !this.world.isAirBlock(pos.down())) && Weapons.easyBreakBlockFor(this.world, 5.0F, pos)) {
                  this.world.setBlockState(pos, BlocksRegister.FLUIDBIOGENICACID.getDefaultState());
               }

               this.setDead();
            }
         } else if (this.world
                  .getBlockState(result.getBlockPos())
                  .getBlock()
                  .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
               != null
            && !this.world.isRemote) {
            if (Weapons.easyBreakBlockFor(this.world, 5.0F, result.getBlockPos())) {
               this.world.setBlockState(result.getBlockPos(), BlocksRegister.FLUIDBIOGENICACID.getDefaultState());
            } else {
               BlockPos pos = new BlockPos(this.prevPosX, this.prevPosY, this.prevPosZ);
               if (GetMOP.collidesWithAnyBlock(this.world, pos)
                  && (this.world.isAirBlock(pos) || Weapons.easyBreakBlockFor(this.world, 5.0F, pos))) {
                  this.world.setBlockState(pos, BlocksRegister.FLUIDBIOGENICACID.getDefaultState());
               }
            }

            this.setDead();
         }
      }
   }
}
