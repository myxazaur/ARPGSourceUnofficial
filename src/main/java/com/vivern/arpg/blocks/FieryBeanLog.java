package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class FieryBeanLog extends BlockRotatedPillar {
   public static final PropertyEnum<EnumAxis> LOG_AXIS = PropertyEnum.create("axis", EnumAxis.class);

   public FieryBeanLog() {
      super(Material.WOOD);
      this.setRegistryName("fiery_bean_log");
      this.setTranslationKey("fiery_bean_log");
      this.setHardness(0.9F);
      this.setResistance(2.0F);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
      this.setSoundType(SoundType.WOOD);
      this.setHarvestLevel("axe", 0);
      this.setTickRandomly(true);
   }

   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      if (rand.nextFloat() < 0.5) {
         float chance = 0.0F;

         for (int y = 1; y < 25; y++) {
            BlockPos posd = pos.down(y);
            IBlockState stated = world.getBlockState(posd);
            if (stated.getBlock() == Blocks.DIRT || stated.getBlock() == Blocks.GRASS) {
               chance = y / 12.0F;
               break;
            }

            if (stated.getBlock() != this) {
               return;
            }
         }

         for (int y = 1; y < 25; y++) {
            BlockPos posdx = pos.up(y);
            IBlockState statedx = world.getBlockState(posdx);
            if (this.isnaturalLeaf(world, posdx)) {
               int lights = this.checkLeaves(world, posdx, rand, 0, 3);
               chance *= MathHelper.clamp(lights / 15.0F, 0.0F, 1.0F);
               break;
            }

            if (statedx.getBlock() != this) {
               return;
            }
         }

         if (rand.nextFloat() < chance) {
            EnumFacing face = EnumFacing.HORIZONTALS[rand.nextInt(4)];
            BlockPos posf = pos.offset(face);
            if (world.getBlockState(posf.up()).getBlock() != BlocksRegister.FIERYBEANBLOCK
               && world.getBlockState(posf).getBlock().isReplaceable(world, posf)) {
               world.setBlockState(posf, BlocksRegister.FIERYBEANBLOCK.getDefaultState().withProperty(FieryBeanBlock.FACING, face));
            }
         }
      }
   }

   public int checkLeaves(World world, BlockPos pos, Random rand, int value, int bound) {
      boolean recurse = bound > 0;

      for (EnumFacing face : EnumFacing.VALUES) {
         if (face != EnumFacing.DOWN && this.checkLeaf(world, pos.offset(face), rand)) {
            value++;
            if (recurse) {
               recurse = false;
               value = this.checkLeaves(world, pos.offset(face), rand, value, bound - 1);
            }
         }
      }

      return value;
   }

   public boolean checkLeaf(World world, BlockPos pos, Random rand) {
      if (this.isnaturalLeaf(world, pos)) {
         int alllight = world.getLightFromNeighbors(pos);
         if (rand.nextFloat() < alllight / 14.0F) {
            return true;
         }
      }

      return false;
   }

   public boolean isnaturalLeaf(World world, BlockPos pos) {
      IBlockState s = world.getBlockState(pos);
      return s.getBlock() == BlocksRegister.FIERYBEANLEAVES;
   }

   public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
      return true;
   }

   public boolean isWood(IBlockAccess world, BlockPos pos) {
      return true;
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      int i = 4;
      int j = 5;
      if (worldIn.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5))) {
         for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
            IBlockState iblockstate = worldIn.getBlockState(blockpos);
            if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos)) {
               iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
            }
         }
      }
   }

   public IBlockState getStateFromMeta(int meta) {
      IBlockState iblockstate = this.getDefaultState().withProperty(LOG_AXIS, EnumAxis.Y);
      switch (meta) {
         case 0:
            iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Y);
            break;
         case 4:
            iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.X);
            break;
         case 8:
            iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Z);
            break;
         default:
            iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.NONE);
      }

      return iblockstate;
   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      switch ((EnumAxis)state.getValue(LOG_AXIS)) {
         case X:
            i = 4;
            break;
         case Z:
            i = 8;
            break;
         case NONE:
            i = 12;
      }

      return i;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{LOG_AXIS});
   }

   public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return MapColor.BROWN;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getStateFromMeta(meta).withProperty(LOG_AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      switch (rot) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            switch ((EnumAxis)state.getValue(LOG_AXIS)) {
               case X:
                  return state.withProperty(LOG_AXIS, EnumAxis.Z);
               case Z:
                  return state.withProperty(LOG_AXIS, EnumAxis.X);
               default:
                  return state;
            }
         default:
            return state;
      }
   }
}
