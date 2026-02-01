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

public class ManaFlowerLeaves extends Block implements IPlantable {
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.25, 1.0);

   public ManaFlowerLeaves() {
      super(Material.PLANTS);
      this.setRegistryName("mana_flower_leaves");
      this.setTranslationKey("mana_flower_leaves");
      this.blockHardness = 0.1F;
      this.blockResistance = 0.1F;
      this.setSoundType(SoundType.PLANT);
      this.setHarvestLevel("shears", 0);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
   }

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (!worldIn.isRemote && rand.nextFloat() < 0.1F && worldIn.isAirBlock(pos.up())) {
         worldIn.setBlockState(pos.up(), BlocksRegister.MANAFLOWER.getDefaultState());
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
      entityIn.motionX *= 0.9;
      entityIn.motionZ *= 0.9;
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
      return blockd.canSustainPlant(state, worldIn, pos.down(), EnumFacing.UP, this);
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!this.canStayAtPos(worldIn, pos)) {
         worldIn.destroyBlock(pos, true);
      }
   }

   public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
      return EnumPlantType.Plains;
   }

   public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
      return world.getBlockState(pos);
   }

   public static void growForGeneration(World world, BlockPos pos, Random rand) {
      if (!world.isRemote && rand.nextFloat() < 0.4F && world.isAirBlock(pos.up())) {
         world.setBlockState(pos.up(), BlocksRegister.MANAFLOWER.getDefaultState().withProperty(ManaFlower.AGE, rand.nextInt(3)));
      }
   }

   public static class ManaFlower extends PlantWithStages {
      public ManaFlower() {
         super(
            Material.PLANTS,
            "mana_flower",
            new Block[]{BlocksRegister.MANAFLOWERLEAVES},
            0.1F,
            0.1F,
            SoundType.PLANT,
            2,
            new AxisAlignedBB[]{GetMOP.newAABB(10, 7, 0), GetMOP.newAABB(12, 10, 0), GetMOP.newAABB(14, 12, 0)},
            13,
            15,
            0.6F,
            "arpg:mana_berry,1,1,0"
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
                     if (rand.nextFloat() < 0.6) {
                        for (int i = 0; i >= -2; i--) {
                           BlockPos poss = pos.offset(facing).add(0, i, 0);
                           if (worldIn.isAirBlock(poss)
                              && BlocksRegister.MANAFLOWERLEAVES.canPlaceBlockAt(worldIn, poss)
                              && worldIn.getBlockState(poss.down()).getBlock().isFertile(worldIn, poss.down())) {
                              if (worldIn.getBlockState(poss.down()).getBlock() == Blocks.FARMLAND) {
                                 worldIn.setBlockState(poss.down(), Blocks.DIRT.getDefaultState());
                              }

                              worldIn.setBlockState(poss, BlocksRegister.MANAFLOWERLEAVES.getDefaultState());
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
   }
}
