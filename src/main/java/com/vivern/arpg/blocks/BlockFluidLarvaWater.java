package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.FluidsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.renders.GUNParticle;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidLarvaWater extends BlockFluidClassic {
   public static final ResourceLocation reslarva1 = new ResourceLocation("arpg:textures/larva_inwater1.png");
   public static final ResourceLocation reslarva2 = new ResourceLocation("arpg:textures/larva_inwater2.png");

   public BlockFluidLarvaWater() {
      super(FluidsRegister.LARVAWATER, Material.WATER);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_larva_water_block");
      this.setRegistryName("fluid_larva_water_block");
   }

   public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      BlockPos posoff = pos.offset(side);
      IBlockState neighbor = world.getBlockState(posoff);
      return neighbor.getBlock() instanceof IUnderwater && world.getBlockState(posoff.up()).getMaterial() == state.getMaterial()
         ? false
         : super.shouldSideBeRendered(state, world, pos, side);
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      if (entityIn instanceof EntityLivingBase && !((EntityLivingBase)entityIn).isEntityUndead()) {
         if (entityIn.ticksExisted % 20 == 0 && !worldIn.isRemote && RANDOM.nextInt(3) == 0) {
            float health = ((EntityLivingBase)entityIn).getHealth();
            if (health + 0.5 < ((EntityLivingBase)entityIn).getMaxHealth()) {
               ((EntityLivingBase)entityIn).attackEntityFrom(DamageSource.CRAMMING, 2.0F);
               worldIn.playSound(
                  null, pos, Sounds.larva_water_attack, SoundCategory.BLOCKS, RANDOM.nextFloat() * 0.25F + 0.55F, RANDOM.nextFloat() * 0.4F + 0.8F
               );
            }
         }

         if (worldIn.isRemote && entityIn.ticksExisted % 5 == 0) {
            float health = ((EntityLivingBase)entityIn).getHealth();
            if (health + 0.5 < ((EntityLivingBase)entityIn).getMaxHealth()) {
               this.effectattack(worldIn, entityIn.posX, entityIn.posY + entityIn.getEyeHeight(), entityIn.posZ);
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void effectattack(World world, double x, double y, double z) {
      int countOfParticles = 2;
      float R = 0.288F + (float)RANDOM.nextGaussian() / 30.0F;

      for (int i = 0; i < countOfParticles; i++) {
         float rand1 = RANDOM.nextFloat() * 2.0F - 1.0F;
         float rand2 = RANDOM.nextFloat() * 2.0F - 1.0F;
         float X = rand1 * R;
         float new_R = (float)Math.sqrt(R * R - X * X);
         float Y = rand2 * new_R;
         float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
         if (RANDOM.nextBoolean()) {
            Z *= -1.0F;
         }

         if (world.getBlockState(new BlockPos(x + X * 15.0F, y + Y * 15.0F, z + Z * 15.0F)).getBlock() == this) {
            if (RANDOM.nextBoolean()) {
               GUNParticle spelll = new GUNParticle(
                  reslarva1,
                  0.029F + RANDOM.nextFloat() / 20.0F,
                  0.0F,
                  30 + RANDOM.nextInt(30),
                  -1,
                  world,
                  x + X * 15.0F,
                  y + Y * 15.0F,
                  z + Z * 15.0F,
                  -X / 2.0F,
                  -Y / 2.0F,
                  -Z / 2.0F,
                  1.0F,
                  1.0F,
                  0.8F + RANDOM.nextFloat() / 5.0F,
                  true,
                  RANDOM.nextInt(360)
               );
               spelll.alpha = 0.0F;
               spelll.alphaTickAdding = 0.05F;
               world.spawnEntity(spelll);
            } else {
               GUNParticle spelll = new GUNParticle(
                  reslarva2,
                  0.027F + RANDOM.nextFloat() / 20.0F,
                  0.0F,
                  30 + RANDOM.nextInt(30),
                  -1,
                  world,
                  x + X * 15.0F,
                  y + Y * 15.0F,
                  z + Z * 15.0F,
                  -X / 2.0F,
                  -Y / 2.0F,
                  -Z / 2.0F,
                  1.0F,
                  1.0F,
                  RANDOM.nextFloat(),
                  true,
                  RANDOM.nextInt(360)
               );
               spelll.alpha = 0.0F;
               spelll.alphaTickAdding = 0.05F;
               world.spawnEntity(spelll);
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      double d0 = pos.getX();
      double d1 = pos.getY();
      double d2 = pos.getZ();
      int i = (Integer)stateIn.getValue(LEVEL);
      if (i > 0 && i < 8) {
         if (rand.nextInt(64) == 0) {
            worldIn.playSound(
               d0 + 0.5,
               d1 + 0.5,
               d2 + 0.5,
               SoundEvents.BLOCK_WATER_AMBIENT,
               SoundCategory.BLOCKS,
               rand.nextFloat() * 0.25F + 0.75F,
               rand.nextFloat() + 0.5F,
               false
            );
         }
      } else if (rand.nextInt(9) == 0) {
         int r = rand.nextInt(4);
         if (r < 2) {
            worldIn.spawnParticle(EnumParticleTypes.SUSPENDED, d0 + rand.nextFloat(), d1 + rand.nextFloat(), d2 + rand.nextFloat(), 0.0, 0.0, 0.0, new int[0]);
         } else if (r == 2) {
            GUNParticle spelll = new GUNParticle(
               reslarva1,
               0.027F + rand.nextFloat() / 20.0F,
               (float)rand.nextGaussian() / 8000.0F,
               50 + rand.nextInt(30),
               -1,
               worldIn,
               d0 + rand.nextFloat(),
               d1 + rand.nextFloat(),
               d2 + rand.nextFloat(),
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               0.8F + rand.nextFloat() / 5.0F,
               true,
               rand.nextInt(360)
            );
            spelll.alpha = 0.0F;
            spelll.alphaTickAdding = 0.05F;
            spelll.isPushedByLiquids = false;
            worldIn.spawnEntity(spelll);
         } else {
            GUNParticle spelll = new GUNParticle(
               reslarva2,
               0.025F + rand.nextFloat() / 20.0F,
               (float)rand.nextGaussian() / 9500.0F,
               50 + rand.nextInt(30),
               -1,
               worldIn,
               d0 + rand.nextFloat(),
               d1 + rand.nextFloat(),
               d2 + rand.nextFloat(),
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               rand.nextFloat(),
               true,
               rand.nextInt(360)
            );
            spelll.alpha = 0.0F;
            spelll.alphaTickAdding = 0.05F;
            spelll.isPushedByLiquids = false;
            worldIn.spawnEntity(spelll);
         }
      }

      if (rand.nextInt(10) == 0 && worldIn.getBlockState(pos.down()).isTopSolid()) {
         Material material = worldIn.getBlockState(pos.down(2)).getMaterial();
         if (!material.blocksMovement() && !material.isLiquid()) {
            double d3 = d0 + rand.nextFloat();
            double d5 = d1 - 1.05;
            double d7 = d2 + rand.nextFloat();
            worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d3, d5, d7, 0.0, 0.0, 0.0, new int[0]);
         }
      }
   }

   public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
      super.onBlockAdded(world, pos, state);
      this.mergerFluids(pos, world);
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
      super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
      this.mergerFluids(pos, world);
   }

   private void mergerFluids(BlockPos pos, World world) {
      int i = (Integer)world.getBlockState(pos).getValue(LEVEL);
      boolean full = i == 0;
      if (!world.isRemote) {
         for (EnumFacing facing : EnumFacing.values()) {
            BlockPos frompos = pos.offset(facing);
            Block block = world.getBlockState(frompos).getBlock();
            if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
               if (full) {
                  world.setBlockState(pos, Blocks.SLIME_BLOCK.getDefaultState());
               } else {
                  world.setBlockState(pos, BlocksRegister.SLIMEGLOB.getDefaultState());
               }

               world.playSound(null, frompos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 0.85F + world.rand.nextFloat() / 4.0F);
            }

            if (facing == EnumFacing.UP && (block == Blocks.WATER || block == Blocks.FLOWING_WATER)) {
               if (full) {
                  world.setBlockState(pos, Blocks.SLIME_BLOCK.getDefaultState());
               } else {
                  world.setBlockState(pos, BlocksRegister.SLIMEGLOB.getDefaultState());
               }

               world.playSound(null, frompos, Sounds.bubble_fish, SoundCategory.BLOCKS, 1.0F, 0.85F + world.rand.nextFloat() / 4.0F);
            }
         }
      }
   }
}
