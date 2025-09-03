//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.blocks.FrostfireExplosive;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;

public class EntityFrostfireExplosive extends Entity implements IEntitySynchronize {
   @Nullable
   public EntityLivingBase tntPlacedBy;
   public int fuse = 80;
   public float power;
   public static ResourceLocation snow = new ResourceLocation("arpg:textures/snowflake3.png");
   public static ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");
   public static ResourceLocation expl = new ResourceLocation("arpg:textures/frozen_explode.png");
   public static ResourceLocation circle = new ResourceLocation("arpg:textures/frozen_circle.png");

   public EntityFrostfireExplosive(World worldIn) {
      super(worldIn);
      this.preventEntitySpawning = true;
      this.isImmuneToFire = true;
      this.setSize(0.98F, 0.98F);
   }

   public EntityFrostfireExplosive(World worldIn, double x, double y, double z, EntityLivingBase igniter, float power) {
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

   public void setSize(float width, float height) {
      super.setSize(width, height);
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

         for (int ss = 0; ss < 3.0 * c; ss++) {
            int lt = 30 + this.rand.nextInt(20);
            GUNParticle bigsmoke = new GUNParticle(
               largecloud,
               (float)((0.6 + this.rand.nextGaussian() / 10.0) * c),
               -0.005F,
               lt,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)(this.rand.nextGaussian() * c / 20.0),
               (float)(this.rand.nextGaussian() * c / 20.0),
               (float)(this.rand.nextGaussian() * c / 20.0),
               0.5F + this.rand.nextFloat() / 10.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaGlowing = true;
            bigsmoke.alphaTickAdding = -1.0F / lt;
            this.world.spawnEntity(bigsmoke);
         }

         int countOfParticles = (int)(6.0 * c);
         float R = (float)((0.08 + this.rand.nextGaussian() / 50.0) * c);

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
               snow,
               0.55F + (float)this.rand.nextGaussian() / 30.0F,
               0.01F,
               22 + this.rand.nextInt(10),
               180,
               this.world,
               x,
               y,
               z,
               X,
               Y,
               Z,
               0.75F + this.rand.nextFloat() / 10.0F,
               0.9F,
               1.0F,
               false,
               this.rand.nextInt(360),
               true,
               1.0F
            );
            this.world.spawnEntity(particle);
         }

         for (int ss = 0; ss < 3; ss++) {
            float fsize = (float)((1.0 + this.rand.nextGaussian() / 6.0) * c);
            int lt = 10 + this.rand.nextInt(10);
            GUNParticle part = new GUNParticle(
               circle,
               1.0F,
               0.0F,
               lt,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.35F + this.rand.nextFloat() / 5.0F,
               0.8F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            part.scaleTickAdding = fsize / lt;
            part.alphaGlowing = true;
            part.alphaTickAdding = -0.025F;
            part.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
            this.world.spawnEntity(part);
         }

         for (int ss = 0; ss < 2; ss++) {
            float fsize = (float)((0.7F + this.rand.nextGaussian() / 7.0) * c);
            int lt = 8 + this.rand.nextInt(5);
            GUNParticle part = new GUNParticle(
               expl,
               1.0F,
               0.0F,
               lt,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.75F + this.rand.nextFloat() / 5.0F,
               0.8F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            part.scaleTickAdding = fsize / lt;
            part.alphaGlowing = true;
            part.alphaTickAdding = -0.025F;
            this.world.spawnEntity(part);
         }

         if (Minecraft.getMinecraft().getRenderViewEntity() != null) {
            float distr = Minecraft.getMinecraft().getRenderViewEntity().getDistance(this);
            if (distr < 4.0 * c) {
               Booom.lastTick = (int)Math.round(5.0 * c - distr);
               Booom.frequency = 3.0F;
               Booom.x = (float)this.rand.nextGaussian();
               Booom.y = (float)this.rand.nextGaussian();
               Booom.z = (float)this.rand.nextGaussian();
               Booom.power = (float)(0.06F * c);
            }
         }
      }
   }

   public void explode() {
      if (!this.world.isRemote) {
         double damageRadius = this.power;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               float finalPower = Math.max(
                  this.power - (float)Weapons.getDistanceToMobHitbox(entity, this.posX, this.posY, this.posZ), 0.0F
               );
               if (entity.isBurning()) {
                  finalPower = (float)(finalPower * 0.7);
               }

               entity.hurtResistantTime = 0;
               Weapons.dealDamage(
                  DamageSource.causeExplosionDamage(this.tntPlacedBy),
                  20.0F * finalPower,
                  this.tntPlacedBy,
                  entity,
                  true,
                  finalPower / 2.0F,
                  this.posX,
                  this.posY,
                  this.posZ
               );
               entity.hurtResistantTime = 0;
               entity.extinguish();
               if (this.rand.nextFloat() < finalPower / 2.0F) {
                  Weapons.setPotionIfEntityLB(entity, PotionEffects.FROSTBURN, (int)(finalPower * 50.0F), (int)(finalPower / 3.0F));
                  Weapons.setPotionIfEntityLB(entity, PotionEffects.FREEZING, (int)(finalPower * 20.0F), 0);
               }

               if (this.rand.nextFloat() < 0.6 && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0F) {
                  DeathEffects.applyDeathEffect((EntityLivingBase)entity, DeathEffects.DE_ICING);
               }
            }
         }

         IEntitySynchronize.sendSynchronize(
            this, 64.0, this.posX, this.posY + this.height / 2.0F, this.posZ, 0.0, 0.0, this.power
         );
      }

      if (this.world.isAreaLoaded(this.getPosition().add(-16, -16, -16), this.getPosition().add(16, 16, 16))) {
         for (BlockPos blockpos : GetMOP.getPosesInsideSphere((int)this.posX, (int)this.posY, (int)this.posZ, (int)this.power)) {
            IBlockState state = this.world.getBlockState(blockpos);
            if (Weapons.easyBreakBlockFor(this.world, this.power * 2.0F, blockpos, state)) {
               if (state.getMaterial().isLiquid()) {
                  Weapons.freezeBlock(this.world, blockpos, state, state.getBlock());
               } else if (state.getBlock() == BlocksRegister.FROSTEXPLOSIVE) {
                  this.world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
                  ((FrostfireExplosive)state.getBlock()).blockexploded(this.world, blockpos, this.tntPlacedBy, this);
               } else if (state.getBlock().hasTileEntity(state) || this.rand.nextFloat() < 0.7F) {
                  state.getBlock().dropBlockAsItem(this.world, blockpos, state, 0);
                  this.world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
               } else if (!state.getMaterial().isLiquid() && state.getMaterial() != Material.AIR) {
                  EntityFallingBlock entityfallingblock = new EntityFallingBlock(
                     this.world, blockpos.getX() + 0.5, blockpos.getY() + 0.2, blockpos.getZ() + 0.5, state
                  );
                  this.world.spawnEntity(entityfallingblock);
                  SuperKnockback.applyKnockback(entityfallingblock, 1.3F + this.power / 5.0F, this.posX, this.posY - 0.2, this.posZ);
                  entityfallingblock.velocityChanged = true;
               }
            }
         }

         if (this.power > 3.0F) {
            int i = 3;
            if (this.power > 12.0F) {
               i = 1;
            } else if (this.power > 6.0F) {
               i = 2;
            }

            for (int ii = 0; ii < i; ii++) {
               EntityFrostfireExplosive expl = new EntityFrostfireExplosive(
                  this.world, this.posX, this.posY, this.posZ, this.tntPlacedBy, this.power - 2.0F
               );
               expl.fuse = 28 + this.rand.nextInt(4);
               expl.setSize(this.width * 0.5F, this.height * 0.5F);
               expl.motionX = this.rand.nextGaussian() / 4.0;
               expl.motionY = this.rand.nextGaussian() / 5.0 + 0.3;
               expl.motionZ = this.rand.nextGaussian() / 4.0;
               this.world.spawnEntity(expl);
               expl.velocityChanged = true;
               IEntitySynchronize.sendSynchronize(expl, 64.0, 0.0, 0.0, 0.0, this.width * 0.5F, this.height * 0.5F, 0.0);
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
}
