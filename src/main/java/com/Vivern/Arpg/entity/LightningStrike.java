//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Sounds;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class LightningStrike extends Entity implements IEntitySynchronize {
   public static ResourceLocation texture = new ResourceLocation("arpg:textures/lightning1.png");
   public List<Vec3d> points = new ArrayList<>();
   public Vec3d mainPoint = null;
   public EntityLivingBase thrower;
   public float damage = 5.0F;
   public Vec3d target;
   public int lastBranches = 8;
   public int maxLength = 16;
   public int length = 16;
   public float sizeY = 8.0F;
   public float endChance = 0.15F;
   public PotionEffect shock;
   public float homingPowerAdd = 0.07F;
   public float raySize = 1.2F;
   public float soundVolume = 3.0F;

   public LightningStrike(World worldIn) {
      super(worldIn);
      this.target = Vec3d.ZERO;
   }

   public LightningStrike(World worldIn, double x, double y, double z, Vec3d target) {
      super(worldIn);
      this.setPosition(x, y, z);
      this.target = target;
   }

   public LightningStrike(World worldIn, double x, double y, double z, EntityLivingBase thrower, Vec3d target) {
      super(worldIn);
      this.setPosition(x, y, z);
      this.thrower = thrower;
      this.target = target;
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      Vec3d pos2 = new Vec3d(x, y, z);
      Vec3d pos1 = new Vec3d(a, b, c);
      BetweenParticle laser = new BetweenParticle(
         this.world,
         texture,
         0.3F + this.rand.nextFloat(),
         240,
         1.0F,
         1.0F,
         1.0F,
         0.1F,
         pos1.distanceTo(pos2),
         10,
         0.0F,
         9999.0F,
         pos1,
         pos2
      );
      laser.alphaGlowing = true;
      laser.setPosition(pos1.x, pos1.y, pos1.z);
      this.world.spawnEntity(laser);
   }

   public boolean isInRangeToRenderDist(double distance) {
      return false;
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         List<Vec3d> newpoints = new ArrayList<>();
         if (this.mainPoint == null) {
            this.mainPoint = this.getPositionVector();
         } else {
            this.length--;
            Vec3d nextMain = this.nextMainStrike(this.mainPoint);
            RayTraceResult result = GetMOP.fixedRayTraceBlocks(
               this.world, this.thrower, this.raySize, 0.5, false, this.mainPoint, nextMain, false, true, false
            );
            if (result != null && result.hitVec != null) {
               IEntitySynchronize.sendSynchronize(
                  this,
                  64.0,
                  this.mainPoint.x,
                  this.mainPoint.y,
                  this.mainPoint.z,
                  result.hitVec.x,
                  result.hitVec.y,
                  result.hitVec.z
               );
            } else {
               IEntitySynchronize.sendSynchronize(
                  this,
                  64.0,
                  this.mainPoint.x,
                  this.mainPoint.y,
                  this.mainPoint.z,
                  nextMain.x,
                  nextMain.y,
                  nextMain.z
               );
            }

            if (result != null && result.entityHit != null) {
               result.entityHit.attackEntityFrom(this.thrower == null ? DamageSource.LIGHTNING_BOLT : DamageSource.causeMobDamage(this.thrower), this.damage);
               if (this.shock != null && result.entityHit instanceof EntityLivingBase) {
                  ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(this.shock));
               }
            }

            if (result != null && result.typeOfHit != Type.MISS && result.typeOfHit != Type.ENTITY) {
               this.setDead();
            } else {
               this.mainPoint = nextMain;
               if (this.lastBranches > 0) {
                  newpoints.add(nextMain);
                  this.lastBranches--;
               }
            }
         }

         for (Vec3d vec : this.points) {
            if (this.rand.nextFloat() > this.endChance) {
               Vec3d next = this.nextStrike(vec);
               RayTraceResult resultx = GetMOP.fixedRayTraceBlocks(this.world, this.thrower, this.raySize, 0.5, false, vec, next, false, true, false);
               if (resultx != null && resultx.typeOfHit != Type.MISS && resultx.typeOfHit != Type.ENTITY) {
                  if (resultx != null && resultx.entityHit != null) {
                     resultx.entityHit
                        .attackEntityFrom(this.thrower == null ? DamageSource.LIGHTNING_BOLT : DamageSource.causeMobDamage(this.thrower), this.damage);
                     if (this.shock != null && resultx.entityHit instanceof EntityLivingBase) {
                        ((EntityLivingBase)resultx.entityHit).addPotionEffect(new PotionEffect(this.shock));
                     }
                  }
               } else {
                  if (resultx != null && resultx.entityHit != null) {
                     resultx.entityHit
                        .attackEntityFrom(this.thrower == null ? DamageSource.LIGHTNING_BOLT : DamageSource.causeMobDamage(this.thrower), this.damage);
                     if (this.shock != null && resultx.entityHit instanceof EntityLivingBase) {
                        ((EntityLivingBase)resultx.entityHit).addPotionEffect(new PotionEffect(this.shock));
                     }
                  }

                  newpoints.add(next);
               }

               if (resultx != null && resultx.hitVec != null) {
                  IEntitySynchronize.sendSynchronize(
                     this,
                     64.0,
                     vec.x,
                     vec.y,
                     vec.z,
                     resultx.hitVec.x,
                     resultx.hitVec.y,
                     resultx.hitVec.z
                  );
               } else {
                  IEntitySynchronize.sendSynchronize(
                     this, 64.0, vec.x, vec.y, vec.z, next.x, next.y, next.z
                  );
               }
            }
         }

         this.points = newpoints;
         if (this.length <= 0) {
            this.setDead();
         }
      }

      if (this.ticksExisted == 1) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.lightning,
               SoundCategory.WEATHER,
               this.soundVolume,
               0.85F + this.rand.nextFloat() / 4.0F,
               false
            );
      }
   }

   public Vec3d nextStrike(Vec3d vec) {
      return new Vec3d(
         vec.x + this.rand.nextGaussian(),
         vec.y - this.rand.nextFloat() * this.sizeY,
         vec.z + this.rand.nextGaussian()
      );
   }

   public Vec3d nextMainStrike(Vec3d vec) {
      float displMult = this.length / this.maxLength;
      float powerMult = 1.0F + this.homingPowerAdd * (this.maxLength - this.length);
      Vec3d ret = new Vec3d(
         vec.x + this.rand.nextGaussian() * displMult,
         vec.y - this.rand.nextFloat() * this.sizeY,
         vec.z + this.rand.nextGaussian() * displMult
      );
      float dist = (float)vec.distanceTo(this.target);
      float prunex = (float)((this.target.x - vec.x) / dist / 2.0 * powerMult);
      float pruney = (float)((this.target.y - vec.y) / dist / 2.0 * powerMult);
      float prunez = (float)((this.target.z - vec.z) / dist / 2.0 * powerMult);
      return ret.add(-prunex, -pruney, -prunez);
   }

   protected void entityInit() {
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
   }
}
