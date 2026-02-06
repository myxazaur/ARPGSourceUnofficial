package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToxicNukeShard extends EntityThrowable {
   public boolean blockcollided;
   final double ssx;
   final double ssy;
   final double ssz;
   public final ItemStack weaponstack;
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");

   public ToxicNukeShard(World world) {
      super(world);
      this.ssx = 0.02F;
      this.ssy = 0.02F;
      this.ssz = 0.02F;
      this.weaponstack = new ItemStack(ItemsRegister.TOXICNUKECANNON);
   }

   public ToxicNukeShard(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.ssx = 0.02F;
      this.ssy = 0.02F;
      this.ssz = 0.02F;
      this.weaponstack = new ItemStack(ItemsRegister.TOXICNUKECANNON);
   }

   public ToxicNukeShard(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.ssx = 0.02F;
      this.ssy = 0.02F;
      this.ssz = 0.02F;
      this.weaponstack = new ItemStack(ItemsRegister.TOXICNUKECANNON);
   }

   public ToxicNukeShard(World world, EntityLivingBase thrower, double ssx, double ssy, double ssz, ItemStack itemstack) {
      super(world, thrower);
      this.ssx = ssx;
      this.ssy = ssy;
      this.ssz = ssz;
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.8;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.8;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.8;
      }
   }

   protected float getGravityVelocity() {
      return 0.015F;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         GUNParticle bigsmoke = new GUNParticle(
            this.largesmoke,
            0.9F + (float)this.rand.nextGaussian() / 3.0F,
            -0.01F,
            30,
            180,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 10.0F,
            (float)this.rand.nextGaussian() / 19.0F,
            (float)this.rand.nextGaussian() / 10.0F,
            0.3F,
            1.0F,
            0.7F + (float)this.rand.nextGaussian() / 5.0F,
            true,
            this.rand.nextInt(360),
            true,
            1.0F
         );
         bigsmoke.alphaTickAdding = -0.025F;
         bigsmoke.alphaGlowing = true;
         this.world.spawnEntity(bigsmoke);
      }
   }

   public void onUpdate() {
      super.onUpdate();
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double shard_damage_radius = parameters.getEnchanted("shard_damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
      if (this.ticksExisted > parameters.geti("shard_livetime")) {
         this.setDead();
      }

      if (this.ticksExisted % 10 == 0) {
         this.blockcollided = false;
         AxisAlignedBB aabbox = new AxisAlignedBB(
            this.posX - shard_damage_radius,
            this.posY - shard_damage_radius,
            this.posZ - shard_damage_radius,
            this.posX + shard_damage_radius,
            this.posY + shard_damage_radius,
            this.posZ + shard_damage_radius
         );
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, aabbox);
         int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (Team.checkIsOpponent(this.thrower, entitylivingbase)) {
                  if (!this.world.isRemote) {
                     PotionEffect debaff = new PotionEffect(
                        PotionEffects.TOXIN, parameters.getEnchantedi("toxin_time", witchery), parameters.getEnchantedi("toxin_power", witchery)
                     );
                     entitylivingbase.addPotionEffect(debaff);
                  }
               } else if (!this.world.isRemote) {
                  float friendlyfire = parameters.get("friendlyfire");
                  PotionEffect debaff = new PotionEffect(
                     PotionEffects.TOXIN,
                     (int)(parameters.getEnchantedi("toxin_time", witchery) * friendlyfire),
                     (int)(friendlyfire * parameters.getEnchantedi("toxin_power", witchery))
                  );
                  entitylivingbase.addPotionEffect(debaff);
               }
            }
         }
      }

      if (this.ticksExisted == 1) {
         this.setVelocity(this.ssx, this.ssy, this.ssz);
         this.motionX = this.ssx;
         this.motionY = this.ssy;
         this.motionZ = this.ssz;
      }
   }

   protected void onImpact(RayTraceResult result) {
      float frictionMultipl = 1.3F;
      if (result.typeOfHit == Type.BLOCK) {
         if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
               this.motionY = 0.0;
               this.motionX /= 1.005F * frictionMultipl;
               this.motionZ /= 1.005F * frictionMultipl;
            }

            if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
               this.motionZ = 0.0;
               this.motionX /= 1.005F * frictionMultipl;
               this.motionY /= 1.001F * frictionMultipl;
            }

            if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
               this.motionX = 0.0;
               this.motionZ /= 1.005F * frictionMultipl;
               this.motionY /= 1.001F * frictionMultipl;
            }
         }

         if (!this.world.isRemote && result.typeOfHit == Type.BLOCK && !this.blockcollided) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            if (this.rand.nextFloat() < parameters.get("dig_chance")) {
               BlockPos blockpos = result.getBlockPos();
               Block block = this.world.getBlockState(blockpos).getBlock();
               float hard = block.getBlockHardness(this.world.getBlockState(blockpos), this.world, blockpos);
               if (hard >= 0.0F && (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0 ? hard < 4.0F : hard < 2.0F)) {
                  this.world.setBlockToAir(blockpos);
                  this.world.setEntityState(this, (byte)5);
               }
            }

            this.blockcollided = true;
         }
      }
   }
}
