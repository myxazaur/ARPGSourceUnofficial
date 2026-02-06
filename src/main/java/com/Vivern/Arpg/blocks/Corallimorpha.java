package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.dimensions.aquatica.DimensionAquatica;
import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Corallimorpha extends Block implements IBlockHardBreak {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
   public static final PropertyBool WET = PropertyBool.create("wet");
   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
   protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.5, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
   protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 1.0, 1.0);
   protected static final AxisAlignedBB UPPER_AABB = new AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0);

   public Corallimorpha(String name) {
      super(Material.WATER);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = BlocksRegister.HR_CORALS.HARDNESS;
      this.blockResistance = BlocksRegister.HR_CORALS.RESISTANCE;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_CORALS.LVL);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
   }

   @Override
   public BlocksRegister.Hardres getHardres() {
      return BlocksRegister.HR_CORALS;
   }

   public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
      return this.isAroundWater(world, pos)
         ? world.setBlockState(pos, Blocks.WATER.getDefaultState(), world.isRemote ? 10 : 2)
         : world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 10 : 2);
   }

   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return false;
   }

   public Material getMaterial(IBlockState state) {
      return state.getValue(WET) ? Material.WATER : Material.ROCK;
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!this.isInWater(world, pos)) {
         world.setBlockState(pos, state.withProperty(WET, false));
      } else {
         world.setBlockState(pos, state.withProperty(WET, true));
      }

      super.neighborChanged(state, world, pos, blockIn, fromPos);
   }

   public boolean isInWater(World worldIn, BlockPos pos) {
      IBlockState state1 = worldIn.getBlockState(pos.up());
      if (state1.getMaterial() == Material.WATER || state1.isOpaqueCube()) {
         IBlockState state2 = worldIn.getBlockState(pos.east());
         if (state2.getMaterial() == Material.WATER || state2.isOpaqueCube()) {
            IBlockState state3 = worldIn.getBlockState(pos.south());
            if (state3.getMaterial() == Material.WATER || state3.isOpaqueCube()) {
               IBlockState state4 = worldIn.getBlockState(pos.west());
               if (state4.getMaterial() == Material.WATER || state4.isOpaqueCube()) {
                  IBlockState state5 = worldIn.getBlockState(pos.north());
                  if (state5.getMaterial() == Material.WATER || state5.isOpaqueCube()) {
                     IBlockState state6 = worldIn.getBlockState(pos.down());
                     if (state6.getMaterial() == Material.WATER || state6.isOpaqueCube()) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
      return this.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.offset(side.getOpposite())).isOpaqueCube();
   }

   public boolean isAroundWater(World world, BlockPos pos) {
      int count = 0;

      for (EnumFacing facing : EnumFacing.VALUES) {
         BlockPos poss = pos.offset(facing);
         IBlockState state2 = world.getBlockState(poss);
         if (state2.getBlock() == Blocks.WATER && (Integer)state2.getValue(BlockStaticLiquid.LEVEL) == 0) {
            if (++count >= 2) {
               return true;
            }
         }
      }

      return false;
   }

   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XYZ;
   }

   public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      EnumFacing face = (EnumFacing)state.getValue(FACING);
      long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
      if (face.getAxis() == Axis.X) {
         return new Vec3d(
            ((float)(i >> 16 & 15L) / 15.0F - 0.5) * 0.1, ((float)(i >> 20 & 15L) / 15.0F - 1.0) * 0.5, ((float)(i >> 24 & 15L) / 15.0F - 0.5) * 0.5
         );
      } else if (face.getAxis() == Axis.Y) {
         return new Vec3d(
            ((float)(i >> 16 & 15L) / 15.0F - 0.5) * 0.5, ((float)(i >> 20 & 15L) / 15.0F - 1.0) * 0.1, ((float)(i >> 24 & 15L) / 15.0F - 0.5) * 0.5
         );
      } else {
         return face.getAxis() == Axis.Z
            ? new Vec3d(
               ((float)(i >> 16 & 15L) / 15.0F - 0.5) * 0.5, ((float)(i >> 20 & 15L) / 15.0F - 1.0) * 0.5, ((float)(i >> 24 & 15L) / 15.0F - 0.5) * 0.1
            )
            : Vec3d.ZERO;
      }
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      switch ((EnumFacing)state.getValue(FACING)) {
         case EAST:
            return EAST_AABB;
         case WEST:
            return WEST_AABB;
         case SOUTH:
            return SOUTH_AABB;
         case NORTH:
            return NORTH_AABB;
         case DOWN:
            return UPPER_AABB;
         default:
            return STANDING_AABB;
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      switch ((EnumFacing)blockState.getValue(FACING)) {
         case EAST:
            return EAST_AABB;
         case WEST:
            return WEST_AABB;
         case SOUTH:
            return SOUTH_AABB;
         case NORTH:
            return NORTH_AABB;
         case DOWN:
            return UPPER_AABB;
         default:
            return STANDING_AABB;
      }
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public IBlockState getStateFromMeta(int meta) {
      boolean wett = meta >= 8;
      return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(wett ? meta - 8 : meta)).withProperty(LEVEL, 0).withProperty(WET, wett);
   }

   public int getMetaFromState(IBlockState state) {
      int i = ((EnumFacing)state.getValue(FACING)).getIndex();
      return state.getValue(WET) ? i + 8 : i;
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
   }

   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
      return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{FACING, LEVEL, WET});
   }

   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
      items.add(new ItemStack(this, 1, 0));
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, facing).withProperty(LEVEL, 0).withProperty(WET, this.isInWater(worldIn, pos));
   }

   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      return world.provider.getDimension() == 103
         ? DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks)
         : super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
   }
}
