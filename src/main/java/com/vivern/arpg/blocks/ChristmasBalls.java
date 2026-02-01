package com.vivern.arpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChristmasBalls extends Block {
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyBool UPPER = PropertyBool.create("up");
   public static final PropertyInteger COLORTYPE = PropertyInteger.create("colortype", 0, 2);
   protected static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.2000000059604645, 0.9, 0.2000000059604645, 0.8000000238418579, 1.0, 0.8000000238418579);
   protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.2, 0.2, 0.9, 0.8, 0.8, 1.0);
   protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.2, 0.2, 0.0, 0.8, 0.8, 0.1);
   protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.9, 0.2, 0.2, 1.0, 0.8, 0.8);
   protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0, 0.2, 0.2, 0.1, 0.8, 0.8);

   public ChristmasBalls() {
      super(Material.GLASS);
      this.setRegistryName("christmas_balls");
      this.setTranslationKey("christmas_balls");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.0F;
      this.setSoundType(SoundTypeShards.SHARDS);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      boolean west = worldIn.isSideSolid(pos.west(), EnumFacing.EAST, false) || worldIn.getBlockState(pos.west()).isFullCube();
      boolean east = worldIn.isSideSolid(pos.east(), EnumFacing.WEST, false) || worldIn.getBlockState(pos.east()).isFullCube();
      boolean south = worldIn.isSideSolid(pos.south(), EnumFacing.NORTH, false) || worldIn.getBlockState(pos.south()).isFullCube();
      boolean north = worldIn.isSideSolid(pos.north(), EnumFacing.SOUTH, false) || worldIn.getBlockState(pos.north()).isFullCube();
      boolean up = worldIn.isSideSolid(pos.up(), EnumFacing.DOWN, false) || worldIn.getBlockState(pos.up()).isFullCube();
      return state.withProperty(WEST, west).withProperty(EAST, east).withProperty(NORTH, north).withProperty(SOUTH, south).withProperty(UPPER, up);
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      boolean west = worldIn.isSideSolid(pos.west(), EnumFacing.EAST) || worldIn.isBlockFullCube(pos.west());
      boolean east = worldIn.isSideSolid(pos.east(), EnumFacing.WEST) || worldIn.isBlockFullCube(pos.east());
      boolean south = worldIn.isSideSolid(pos.south(), EnumFacing.NORTH) || worldIn.isBlockFullCube(pos.south());
      boolean north = worldIn.isSideSolid(pos.north(), EnumFacing.SOUTH) || worldIn.isBlockFullCube(pos.north());
      boolean up = worldIn.isSideSolid(pos.up(), EnumFacing.DOWN) || worldIn.isBlockFullCube(pos.up());
      return east || north || south || west || up;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
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
      return this.getDefaultState().withProperty(COLORTYPE, meta);
   }

   public int getMetaFromState(IBlockState state) {
      return (Integer)state.getValue(COLORTYPE);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, UPPER, COLORTYPE});
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      boolean west;
      boolean east;
      boolean south;
      boolean north;
      boolean up;
      if (placer != null && placer.isSneaking()) {
         west = facing == EnumFacing.EAST;
         east = facing == EnumFacing.WEST;
         south = facing == EnumFacing.NORTH;
         north = facing == EnumFacing.SOUTH;
         up = facing == EnumFacing.DOWN;
      } else {
         west = worldIn.isSideSolid(pos.west(), EnumFacing.EAST) || worldIn.isBlockFullCube(pos.west());
         east = worldIn.isSideSolid(pos.east(), EnumFacing.WEST) || worldIn.isBlockFullCube(pos.east());
         south = worldIn.isSideSolid(pos.south(), EnumFacing.NORTH) || worldIn.isBlockFullCube(pos.south());
         north = worldIn.isSideSolid(pos.north(), EnumFacing.SOUTH) || worldIn.isBlockFullCube(pos.north());
         up = worldIn.isSideSolid(pos.up(), EnumFacing.DOWN) || worldIn.isBlockFullCube(pos.up());
      }

      return this.getDefaultState()
         .withProperty(COLORTYPE, worldIn.rand.nextInt(3))
         .withProperty(WEST, west)
         .withProperty(EAST, east)
         .withProperty(NORTH, north)
         .withProperty(SOUTH, south)
         .withProperty(UPPER, up);
   }
}
