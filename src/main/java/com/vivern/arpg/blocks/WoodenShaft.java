package com.vivern.arpg.blocks;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WoodenShaft extends Block {
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyBool UPPER = PropertyBool.create("up");
   public static final PropertyBool DOWN = PropertyBool.create("down");
   protected static final AxisAlignedBB ALL_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.3, 0.0, 0.3, 0.7, 1.0, 0.7);
   protected static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.0, 0.7, 0.3, 1.0, 1.0, 0.7);
   protected static final AxisAlignedBB UPR_AABB = new AxisAlignedBB(0.3, 0.7, 0.0, 0.7, 1.0, 1.0);
   protected static final AxisAlignedBB UPSLAB_AABB = new AxisAlignedBB(0.0, 0.7, 0.0, 1.0, 1.0, 1.0);

   public WoodenShaft() {
      super(Material.WOOD);
      this.setRegistryName("wooden_shaft");
      this.setTranslationKey("wooden_shaft");
      this.blockHardness = 3.0F;
      this.blockResistance = 0.3F;
      this.setSoundType(SoundType.WOOD);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setHarvestLevel("axe", 0);
   }

   public void addCollisionBoxToList(
      IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState
   ) {
      boolean n = (Boolean)state.getValue(NORTH);
      boolean e = (Boolean)state.getValue(EAST);
      boolean s = (Boolean)state.getValue(SOUTH);
      boolean w = (Boolean)state.getValue(WEST);
      boolean u = (Boolean)state.getValue(UPPER);
      boolean d = (Boolean)state.getValue(DOWN);
      if (!n && !e && !s && !w && !u && !d) {
         addCollisionBoxToList(pos, entityBox, collidingBoxes, STANDING_AABB);
      } else if (d) {
         addCollisionBoxToList(pos, entityBox, collidingBoxes, STANDING_AABB);
      }

      if (s || n) {
         addCollisionBoxToList(pos, entityBox, collidingBoxes, UP_AABB);
      }

      if (w || e) {
         addCollisionBoxToList(pos, entityBox, collidingBoxes, UPR_AABB);
      }
   }

   public boolean isFullBlock(IBlockState state) {
      return false;
   }

   public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return side == EnumFacing.UP || side == EnumFacing.DOWN && (Boolean)base_state.getValue(DOWN);
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      boolean west = worldIn.getBlockState(pos.west()).getBlock() == this;
      boolean east = worldIn.getBlockState(pos.east()).getBlock() == this;
      boolean south = worldIn.getBlockState(pos.south()).getBlock() == this;
      boolean north = worldIn.getBlockState(pos.north()).getBlock() == this;
      boolean up = worldIn.isSideSolid(pos.up(), EnumFacing.DOWN, false) || worldIn.getBlockState(pos.up()).isFullCube();
      boolean down = worldIn.isSideSolid(pos.down(), EnumFacing.UP, false) || worldIn.getBlockState(pos.down()).isFullCube();
      return state.withProperty(WEST, west)
         .withProperty(EAST, east)
         .withProperty(NORTH, north)
         .withProperty(SOUTH, south)
         .withProperty(UPPER, up)
         .withProperty(DOWN, down);
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return true;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      boolean n = (Boolean)state.getValue(NORTH);
      boolean e = (Boolean)state.getValue(EAST);
      boolean s = (Boolean)state.getValue(SOUTH);
      boolean w = (Boolean)state.getValue(WEST);
      boolean d = (Boolean)state.getValue(DOWN);
      if (!n && !e && !s && !w) {
         return STANDING_AABB;
      } else if ((s || n) && !w && !e && !d) {
         return UPR_AABB;
      } else if ((w || e) && !n && !s && !d) {
         return UP_AABB;
      } else if ((w || e) && (n || s) && !d) {
         return UPSLAB_AABB;
      } else {
         return (w || e) && (n || s) && d ? ALL_AABB : STANDING_AABB;
      }
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

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState();
   }

   public int getMetaFromState(IBlockState state) {
      return 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, UPPER, DOWN});
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      boolean west = worldIn.getBlockState(pos.west()).getBlock() == this;
      boolean east = worldIn.getBlockState(pos.east()).getBlock() == this;
      boolean south = worldIn.getBlockState(pos.south()).getBlock() == this;
      boolean north = worldIn.getBlockState(pos.north()).getBlock() == this;
      boolean up = worldIn.isSideSolid(pos.up(), EnumFacing.DOWN, false) || worldIn.getBlockState(pos.up()).isFullCube();
      boolean down = worldIn.isSideSolid(pos.down(), EnumFacing.UP, false) || worldIn.getBlockState(pos.down()).isFullCube();
      return this.getDefaultState()
         .withProperty(WEST, west)
         .withProperty(EAST, east)
         .withProperty(NORTH, north)
         .withProperty(SOUTH, south)
         .withProperty(UPPER, up)
         .withProperty(DOWN, down);
   }
}
