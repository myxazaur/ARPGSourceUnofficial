package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySand extends EntityThrowable {
   public double scaleaabb = 0.7;
   public boolean blockcollided;
   public boolean doknock;
   public float magicPower = 1.0F;
   List<Entity> attackedsand = new ArrayList<>();
   public final ItemStack weaponstack;
   public int livetime = 13;
   public int crystalCharge = 0;

   public EntitySand(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.SCEPTEROFSANDS);
   }

   public EntitySand(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.SCEPTEROFSANDS);
   }

   public EntitySand(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.SCEPTEROFSANDS);
   }

   public EntitySand(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
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
      return 0.01F;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote && this.ticksExisted > this.livetime) {
         this.setDead();
      }

      if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0) {
         if (this.ticksExisted == 3) {
            this.blockcollided = false;
         }

         if (this.ticksExisted == 6) {
            this.blockcollided = false;
         }

         if (this.ticksExisted == 9) {
            this.blockcollided = false;
         }

         if (this.ticksExisted == 12) {
            this.blockcollided = false;
         }
      }

      if (!this.world.isRemote) {
         AxisAlignedBB aabbox = new AxisAlignedBB(
            this.posX - this.scaleaabb,
            this.posY - this.scaleaabb,
            this.posZ - this.scaleaabb,
            this.posX + this.scaleaabb,
            this.posY + this.scaleaabb,
            this.posZ + this.scaleaabb
         );
         this.scaleaabb += 0.05;
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, aabbox);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity) && !this.attackedsand.contains(entity)) {
                  WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
                  int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
                  int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
                  Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, false, false, this, WeaponDamage.sand),
                     parameters.getEnchanted("damage", might) * this.magicPower,
                     this.getThrower(),
                     entity,
                     true,
                     parameters.getEnchanted("knockback", impulse),
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 1;
                  if (entity instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                     if (entitylivingbase.getHealth() <= 0.0F) {
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_SAND);
                     }
                  }

                  this.attackedsand.add(entity);
               }
            }
         }
      }
   }

   public int getBlockSandTurnType(IBlockState blockState, BlockPos pos) {
      Block block = blockState.getBlock();
      Material material = blockState.getMaterial();
      if (material == Material.ROCK
         || material == Material.CORAL
         || material == Material.GROUND
         || material == Material.CLAY
         || material == Material.GRASS
         || material == Material.LEAVES
         || material == Material.CACTUS
         || material == Material.PLANTS
         || material == Material.VINE
         || material == Material.GOURD
         || material == Material.WOOD
         || material == Material.GLASS
         || material == Material.CIRCUITS
         || material == Material.PISTON
         || material == Material.REDSTONE_LIGHT
         || material == Material.SPONGE
         || material == Material.WEB
         || material == Material.CLOTH
         || material == Material.CARPET) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         float hardnessBreaks = parameters.get("hardness_breaks");
         if (Weapons.easyBreakBlockFor(this.world, hardnessBreaks, pos, blockState)) {
            if (blockState.isFullBlock()) {
               return 1;
            }

            return 2;
         }
      }

      return 0;
   }

   protected void onImpact(RayTraceResult result) {
      int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
      float frictionMultipl = 1.1F;
      if (!this.world.isRemote && result.entityHit != null && result.entityHit instanceof EntityFallingBlock && !this.doknock) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         EntityFallingBlock block = (EntityFallingBlock)result.entityHit;
         SuperKnockback.applyKnockback(
            block,
            parameters.getEnchanted("knockback_to_blocks", impulse),
            this.getThrower().posX,
            this.getThrower().posY,
            this.getThrower().posZ
         );
         block.velocityChanged = true;
         this.doknock = true;
      }

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

         if (!this.world.isRemote && !this.blockcollided) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            float chargeFullness = this.crystalCharge / parameters.get("max_crystal_charge");
            if (this.rand.nextFloat() < 0.5F + chargeFullness * 0.5F) {
               BlockPos blockpos = result.getBlockPos();
               IBlockState blockState = this.world.getBlockState(blockpos);
               int type = this.getBlockSandTurnType(blockState, blockpos);
               if (type == 1) {
                  this.world.setBlockToAir(blockpos);
                  this.world.setBlockState(blockpos, Blocks.SAND.getDefaultState());
               } else if (type == 2) {
                  if (this.rand.nextFloat() < 0.8) {
                     this.world.setBlockToAir(blockpos);
                  } else {
                     this.world.setBlockToAir(blockpos);
                     this.world.setBlockState(blockpos, Blocks.SAND.getDefaultState());
                  }
               }

               if (this.rand.nextFloat() < parameters.getEnchanted("fallingblock_chance", impulse)
                  && blockState.getBlock() == Blocks.SAND
                  && blockpos.getY() >= 0) {
                  int i = 32;
                  if (!BlockSand.fallInstantly && this.world.isAreaLoaded(blockpos.add(-32, -32, -32), blockpos.add(32, 32, 32))) {
                     if (!this.world.isRemote) {
                        EntityFallingBlock entityfallingblock = new EntityFallingBlock(
                           this.world,
                           blockpos.getX() + 0.5,
                           blockpos.getY() + 0.2,
                           blockpos.getZ() + 0.5,
                           this.world.getBlockState(blockpos)
                        );
                        this.world.spawnEntity(entityfallingblock);
                        if (this.world.getBlockState(blockpos.up()).getBlock() == Blocks.SAND
                           && blockpos.up().getY() >= 0) {
                           if (BlockSand.fallInstantly
                              || !this.world
                                 .isAreaLoaded(blockpos.up().add(-32, -32, -32), blockpos.up().add(32, 32, 32))) {
                              IBlockState state = this.world.getBlockState(blockpos.up());
                              this.world.setBlockToAir(blockpos.up());
                              BlockPos blockposss = blockpos.up().down();

                              while (
                                 (this.world.isAirBlock(blockposss) || BlockSand.canFallThrough(this.world.getBlockState(blockposss)))
                                    && blockposss.getY() > 0
                              ) {
                                 blockposss = blockposss.down();
                              }

                              if (blockposss.getY() > 0) {
                                 this.world.setBlockState(blockposss.up(), state);
                              }
                           } else if (!this.world.isRemote) {
                              EntityFallingBlock entityfallingblock2 = new EntityFallingBlock(
                                 this.world,
                                 blockpos.up().getX() + 0.5,
                                 blockpos.up().getY(),
                                 blockpos.up().getZ() + 0.5,
                                 this.world.getBlockState(blockpos.up())
                              );
                              this.world.spawnEntity(entityfallingblock2);
                           }
                        }
                     }
                  } else {
                     IBlockState state = this.world.getBlockState(blockpos);
                     this.world.setBlockToAir(blockpos);
                     BlockPos blockposs = blockpos.down();

                     while (
                        (this.world.isAirBlock(blockposs) || BlockSand.canFallThrough(this.world.getBlockState(blockposs)))
                           && blockposs.getY() > 0
                     ) {
                        blockposs = blockposs.down();
                     }

                     if (blockposs.getY() > 0) {
                        this.world.setBlockState(blockposs.up(), state);
                     }
                  }
               }
            }

            this.blockcollided = true;
         }
      }
   }
}
