package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class HealthFlowerLeaves extends Block implements IPlantable {
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.25, 1.0);

   public HealthFlowerLeaves() {
      super(Material.PLANTS);
      this.setRegistryName("health_flower_leaves");
      this.setTranslationKey("health_flower_leaves");
      this.blockHardness = 0.1F;
      this.blockResistance = 0.1F;
      this.setSoundType(SoundType.PLANT);
      this.setHarvestLevel("shears", 0);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
   }

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (!worldIn.isRemote && rand.nextFloat() < 0.04F && worldIn.isAirBlock(pos.up())) {
         worldIn.setBlockState(pos.up(), BlocksRegister.HEALTHFLOWER.getDefaultState());
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      entityIn.motionX *= 0.85;
      entityIn.motionZ *= 0.85;
   }

   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return super.canPlaceBlockAt(worldIn, pos) && this.canStayAtPos(worldIn, pos);
   }

   public boolean canStayAtPos(World worldIn, BlockPos pos) {
      IBlockState state = worldIn.getBlockState(pos.down());
      Block blockd = state.getBlock();
      return HealthFlower.isFertileOre(blockd)
         || blockd == Blocks.STONE
         || blockd == Blocks.GRAVEL
         || blockd == Blocks.COBBLESTONE
         || blockd == Blocks.MOSSY_COBBLESTONE;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!this.canStayAtPos(worldIn, pos)) {
         worldIn.destroyBlock(pos, true);
      }
   }

   public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
      return EnumPlantType.Cave;
   }

   public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
      return world.getBlockState(pos);
   }

   public static void growForGeneration(World world, BlockPos pos, Random rand) {
      if (!world.isRemote && rand.nextFloat() < 0.4F && world.isAirBlock(pos.up())) {
         world.setBlockState(
            pos.up(), BlocksRegister.HEALTHFLOWER.getDefaultState().withProperty(HealthFlower.AGE, rand.nextInt(3))
         );
      }
   }

   public static class HealthFlower extends PlantWithStages {
      public HealthFlower() {
         super(
            Material.PLANTS,
            "health_flower",
            new Block[]{BlocksRegister.HEALTHFLOWERLEAVES},
            0.2F,
            0.2F,
            SoundType.PLANT,
            2,
            new AxisAlignedBB[]{GetMOP.newAABB(10, 7, 0), GetMOP.newAABB(12, 10, 0), GetMOP.newAABB(14, 12, 0)},
            0,
            5,
            0.3F,
            "arpg:health_berry,1,1,0"
         );
         this.setLightLevel(0.3F);
         this.randOffsetAmountXZ = 0.3;
         this.randOffsetAmountY = 0.1;
      }

      @Override
      public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
         if (rand.nextFloat() < this.growChance) {
            if (this.canStayAtPos(worldIn, pos)) {
               int agehas = (Integer)state.getValue(AGE);
               if (agehas < this.maxStage) {
                  worldIn.setBlockState(pos, state.withProperty(AGE, Math.min(agehas + 1, this.maxStage)));
               } else {
                  for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                     if (rand.nextFloat() < 0.5) {
                        for (int i = 0; i >= -2; i--) {
                           BlockPos poss = pos.offset(facing).add(0, i, 0);
                           if (worldIn.isAirBlock(poss)
                              && BlocksRegister.HEALTHFLOWERLEAVES.canPlaceBlockAt(worldIn, poss)
                              && isFertileOre(worldIn.getBlockState(poss.down()).getBlock())) {
                              worldIn.setBlockState(poss.down(), Blocks.STONE.getDefaultState());
                              worldIn.setBlockState(poss, BlocksRegister.HEALTHFLOWERLEAVES.getDefaultState());
                              break;
                           }
                        }
                     }
                  }

                  worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
               }
            } else {
               this.dropBlockAsItem(worldIn, pos, state, 0);
               worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            }
         }
      }

      public static boolean isFertileOre(Block block) {
         return block == Blocks.COAL_ORE || block == Blocks.COAL_BLOCK || block == Blocks.REDSTONE_ORE;
      }
   }
}
