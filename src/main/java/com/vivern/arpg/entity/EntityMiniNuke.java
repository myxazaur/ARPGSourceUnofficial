package com.vivern.arpg.entity;

import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityMiniNuke extends Entity implements IEntitySynchronize {
   @Nullable
   public EntityLivingBase tntPlacedBy;
   public int fuse;
   public float power;
   public float resistanceBreaking = 119.0F;
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");
   ResourceLocation round = new ResourceLocation("arpg:textures/fire_circle.png");

   public EntityMiniNuke(World worldIn) {
      super(worldIn);
      this.fuse = 80;
      this.preventEntitySpawning = true;
      this.isImmuneToFire = true;
      this.setSize(0.98F, 0.98F);
   }

   public EntityMiniNuke(World worldIn, double x, double y, double z, EntityLivingBase igniter, float power) {
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
               Sounds.explode,
               SoundCategory.BLOCKS,
               (float)(1.0 + c * 0.5),
               0.8F + this.rand.nextFloat() * 0.4F,
               false
            );
         if (Minecraft.getMinecraft().getRenderViewEntity() != null) {
            float distapl = Minecraft.getMinecraft().getRenderViewEntity().getDistance(this);
            if (distapl < 30.0F) {
               Booom.lastTick = Math.round(35.0F - distapl);
               Booom.frequency = 2.7125F;
               Booom.x = (float)this.rand.nextGaussian();
               Booom.y = (float)this.rand.nextGaussian();
               Booom.z = (float)this.rand.nextGaussian();
               Booom.power = 0.6F - distapl / 50.0F;
            }
         }

         for (int ss = 0; ss < 13; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               this.largesmoke,
               2.0F + (float)this.rand.nextGaussian() / 2.0F,
               -0.001F,
               34,
               60,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 7.0F,
               (float)this.rand.nextGaussian() / 7.0F,
               (float)this.rand.nextGaussian() / 7.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.016F;
            this.world.spawnEntity(bigsmoke);
         }

         for (int ss = 0; ss < 17; ss++) {
            GUNParticle fire = new GUNParticle(
               this.flame,
               1.7F + (float)this.rand.nextGaussian() / 5.0F,
               -0.005F,
               20 + this.rand.nextInt(10),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 4.0F,
               (float)this.rand.nextGaussian() / 21.0F,
               (float)this.rand.nextGaussian() / 4.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            fire.alphaTickAdding = -0.03F;
            fire.alphaGlowing = true;
            this.world.spawnEntity(fire);
         }

         for (int ss = 0; ss < 17; ss++) {
            GUNParticle fire = new GUNParticle(
               this.flame,
               2.2F + (float)this.rand.nextGaussian() / 5.0F,
               0.02F,
               30 + this.rand.nextInt(10),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 27.0F,
               this.rand.nextFloat() - 0.05F,
               (float)this.rand.nextGaussian() / 27.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            fire.alphaTickAdding = -0.025F;
            fire.alphaGlowing = true;
            this.world.spawnEntity(fire);
         }

         for (int ss = 0; ss < 10; ss++) {
            GUNParticle fire = new GUNParticle(
               this.flame,
               2.0F + (float)this.rand.nextGaussian() / 5.0F,
               0.0F,
               20 + this.rand.nextInt(20),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 4.0F,
               (float)this.rand.nextGaussian() / 4.0F,
               (float)this.rand.nextGaussian() / 4.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            fire.alphaTickAdding = -0.025F;
            fire.alphaGlowing = true;
            this.world.spawnEntity(fire);
         }

         for (int ss = -1; ss < 2; ss++) {
            double yy = y + ss * 0.3 * this.power;
            float finalsize = (1.5F + (float)Math.abs(y - yy)) * 4.0F;
            GUNParticle bigboom = new GUNParticle(this.round, 1.0F, 0.0F, 20, 240, this.world, x, yy, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, 1);
            bigboom.dropped = true;
            bigboom.scaleTickAdding = finalsize / 20.0F;
            bigboom.alphaTickAdding = -0.04F;
            bigboom.alphaGlowing = true;
            this.world.spawnEntity(bigboom);
         }
      }
   }

   public void explode() {
      Set<BlockPos> set = Sets.newHashSet();
      int i = 16;

      for (int j = 0; j < 16; j++) {
         for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
               if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                  double d0 = j / 15.0F * 2.0F - 1.0F;
                  double d1 = k / 15.0F * 2.0F - 1.0F;
                  double d2 = l / 15.0F * 2.0F - 1.0F;
                  double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                  d0 /= d3;
                  d1 /= d3;
                  d2 /= d3;
                  float f = this.power * (0.7F + this.world.rand.nextFloat() * 0.6F);
                  double d4 = this.posX;
                  double d6 = this.posY;
                  double d8 = this.posZ;

                  for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                     BlockPos blockpos = new BlockPos(d4, d6, d8);
                     IBlockState iblockstate = this.world.getBlockState(blockpos);
                     if (iblockstate.getMaterial() != Material.AIR) {
                        float f2 = Math.max(Weapons.resistance(iblockstate.getBlock()) - this.resistanceBreaking, 0.0F);
                        int level = iblockstate.getBlock().getHarvestLevel(iblockstate);
                        if (level > 7) {
                           f = 0.0F;
                        }

                        f -= (f2 + 0.3F) * 0.3F;
                     }

                     if (f > 0.0F) {
                        set.add(blockpos);
                     }

                     d4 += d0 * 0.3F;
                     d6 += d1 * 0.3F;
                     d8 += d2 * 0.3F;
                  }
               }
            }
         }
      }

      for (BlockPos blockposx : set) {
         IBlockState iblockstatex = this.world.getBlockState(blockposx);
         Block block = iblockstatex.getBlock();
         if (iblockstatex.getMaterial() != Material.AIR && !Weapons.triggerExplodeBlock(this.world, blockposx, this.tntPlacedBy, this)) {
            block.dropBlockAsItemWithChance(this.world, blockposx, this.world.getBlockState(blockposx), 1.0F, 0);
            this.world.setBlockToAir(blockposx);
         }
      }

      double damageRadius = this.power;
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
      if (!list.isEmpty()) {
         for (Entity entity : list) {
            if (!this.world.isRemote) {
               if (!(entity instanceof EntityItem)) {
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
                     this.power / 2.5F + finalPower / 3.5F,
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
                  if (entity instanceof EntityPlayer) {
                     Mana.addRad((EntityPlayer)entity, 500, true);
                  }
               } else {
                  float finalPower = (float)Math.max(
                     damageRadius - Weapons.getDistanceToMobHitbox(entity, this.posX, this.posY, this.posZ), 0.0
                  );
                  SuperKnockback.applyKnockback(entity, this.power / 3.5F + finalPower / 4.5F, this.posX, this.posY, this.posZ);
               }
            }

            if (this.rand.nextFloat() < 0.6 && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0F) {
               DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER);
            }
         }
      }

      if (!this.world.isRemote) {
         IEntitySynchronize.sendSynchronize(
            this, 64.0, this.posX, this.posY + this.height / 2.0F, this.posZ, 0.0, 0.0, this.power
         );
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
