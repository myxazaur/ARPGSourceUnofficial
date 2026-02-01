package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StormConductor extends BlockRotatedPillar implements IBlockHardBreak {
   public static final PropertyInteger AXIS = PropertyInteger.create("axis", 0, 3);
   public static final PropertyBool FORWARD = PropertyBool.create("forw");
   public static final PropertyBool BACKWARD = PropertyBool.create("back");

   public StormConductor() {
      super(Material.ROCK);
      this.setRegistryName("storm_conductor");
      this.setTranslationKey("storm_conductor");
      this.blockHardness = BlocksRegister.HR_ZARPION_ROCKS.HARDNESS;
      this.blockResistance = BlocksRegister.HR_ZARPION_ROCKS.RESISTANCE;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_ZARPION_ROCKS.LVL);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, 2));
      this.setSoundType(SoundType.STONE);
      this.setLightLevel(0.35F);
   }

   @Override
   public BlocksRegister.Hardres getHardres() {
      return BlocksRegister.HR_ZARPION_ROCKS;
   }

   public boolean isFullBlock(IBlockState state) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      int axis = (Integer)state.getValue(AXIS);
      if (axis == 1) {
         BlockPos poss = pos.offset(EnumFacing.EAST);
         BlockPos posn = pos.offset(EnumFacing.WEST);
         IBlockState stateP = worldIn.getBlockState(poss);
         IBlockState stateN = worldIn.getBlockState(posn);
         return state.withProperty(FORWARD, stateP.getBlock() == this && (Integer)stateP.getValue(AXIS) != axis)
            .withProperty(BACKWARD, stateN.getBlock() == this && (Integer)stateN.getValue(AXIS) != axis);
      } else if (axis == 2) {
         BlockPos poss = pos.offset(EnumFacing.UP);
         BlockPos posn = pos.offset(EnumFacing.DOWN);
         IBlockState stateP = worldIn.getBlockState(poss);
         IBlockState stateN = worldIn.getBlockState(posn);
         return state.withProperty(FORWARD, stateP.getBlock() == this && (Integer)stateP.getValue(AXIS) != axis)
            .withProperty(BACKWARD, stateN.getBlock() == this && (Integer)stateN.getValue(AXIS) != axis);
      } else if (axis != 3) {
         return state;
      } else {
         BlockPos poss = pos.offset(EnumFacing.SOUTH);
         BlockPos posn = pos.offset(EnumFacing.NORTH);
         IBlockState stateP = worldIn.getBlockState(poss);
         IBlockState stateN = worldIn.getBlockState(posn);
         return state.withProperty(FORWARD, stateP.getBlock() == this && (Integer)stateP.getValue(AXIS) != axis)
            .withProperty(BACKWARD, stateN.getBlock() == this && (Integer)stateN.getValue(AXIS) != axis);
      }
   }

   public IBlockState getStateFromMeta(int meta) {
      if (meta == 0) {
         return this.getDefaultState().withProperty(AXIS, 0);
      } else {
         if (meta <= 4) {
            int i2 = meta - 1;
            if (i2 == 0) {
               return this.getDefaultState().withProperty(AXIS, 1).withProperty(FORWARD, false).withProperty(BACKWARD, false);
            }

            if (i2 == 1) {
               return this.getDefaultState().withProperty(AXIS, 1).withProperty(FORWARD, true).withProperty(BACKWARD, false);
            }

            if (i2 == 2) {
               return this.getDefaultState().withProperty(AXIS, 1).withProperty(FORWARD, true).withProperty(BACKWARD, true);
            }

            if (i2 == 3) {
               return this.getDefaultState().withProperty(AXIS, 1).withProperty(FORWARD, false).withProperty(BACKWARD, true);
            }
         }

         if (meta <= 8) {
            int i2x = meta - 5;
            if (i2x == 0) {
               return this.getDefaultState().withProperty(AXIS, 2).withProperty(FORWARD, false).withProperty(BACKWARD, false);
            }

            if (i2x == 1) {
               return this.getDefaultState().withProperty(AXIS, 2).withProperty(FORWARD, true).withProperty(BACKWARD, false);
            }

            if (i2x == 2) {
               return this.getDefaultState().withProperty(AXIS, 2).withProperty(FORWARD, true).withProperty(BACKWARD, true);
            }

            if (i2x == 3) {
               return this.getDefaultState().withProperty(AXIS, 2).withProperty(FORWARD, false).withProperty(BACKWARD, true);
            }
         }

         if (meta <= 12) {
            int i2xx = meta - 9;
            if (i2xx == 0) {
               return this.getDefaultState().withProperty(AXIS, 3).withProperty(FORWARD, false).withProperty(BACKWARD, false);
            }

            if (i2xx == 1) {
               return this.getDefaultState().withProperty(AXIS, 3).withProperty(FORWARD, true).withProperty(BACKWARD, false);
            }

            if (i2xx == 2) {
               return this.getDefaultState().withProperty(AXIS, 3).withProperty(FORWARD, true).withProperty(BACKWARD, true);
            }

            if (i2xx == 3) {
               return this.getDefaultState().withProperty(AXIS, 3).withProperty(FORWARD, false).withProperty(BACKWARD, true);
            }
         }

         return this.getDefaultState().withProperty(AXIS, 0);
      }
   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      boolean b1f = (Boolean)state.getValue(FORWARD);
      boolean b1b = (Boolean)state.getValue(BACKWARD);
      int i2 = 0;
      if (!b1f && !b1b) {
         i2 = 0;
      }

      if (b1f && !b1b) {
         i2 = 1;
      }

      if (b1f && b1b) {
         i2 = 2;
      }

      if (!b1f && b1b) {
         i2 = 3;
      }

      switch (state.getValue(AXIS)) {
         case 0:
            i = 0;
            break;
         case 1:
            i = 1 + i2;
            break;
         case 2:
            i = 5 + i2;
            break;
         case 3:
            i = 9 + i2;
      }

      return i;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{AXIS, FORWARD, BACKWARD});
   }

   public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return MapColor.OBSIDIAN;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      if (EnumAxis.fromFacingAxis(facing.getAxis()) == EnumAxis.X) {
         return this.getStateFromMeta(meta).withProperty(AXIS, 1);
      } else if (EnumAxis.fromFacingAxis(facing.getAxis()) == EnumAxis.Y) {
         return this.getStateFromMeta(meta).withProperty(AXIS, 2);
      } else {
         return EnumAxis.fromFacingAxis(facing.getAxis()) == EnumAxis.Z
            ? this.getStateFromMeta(meta).withProperty(AXIS, 3)
            : this.getStateFromMeta(meta).withProperty(AXIS, 0);
      }
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      switch (rot) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            switch (state.getValue(AXIS)) {
               case 1:
                  return state.withProperty(AXIS, 3);
               case 3:
                  return state.withProperty(AXIS, 1);
               default:
                  return state;
            }
         default:
            return state;
      }
   }
}
