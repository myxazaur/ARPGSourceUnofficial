package com.vivern.arpg.blocks;

import com.vivern.arpg.entity.ChlorineCloud;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.Sounds;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ChlorineBelcher extends BlockBlockHard {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 0.6, 0.9);
   protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.1, 0.1, 0.4, 0.9, 0.9, 1.0);
   protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.1, 0.1, 0.0, 0.9, 0.9, 0.6);
   protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.4, 0.1, 0.1, 1.0, 0.9, 0.9);
   protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0, 0.1, 0.1, 0.6, 0.9, 0.9);
   protected static final AxisAlignedBB UPPER_AABB = new AxisAlignedBB(0.1, 0.4, 0.1, 0.9, 1.0, 0.9);

   public ChlorineBelcher() {
      super(Material.PLANTS, "chlorine_belcher", BlocksRegister.HR_CHLORINE_BELCHER, "axe", true);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
      this.setSoundType(SoundType.SLIME);
      this.setTickRandomly(true);
   }

   public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
      if (!world.isRemote
         && RANDOM.nextFloat() < 0.9
         && world.isAnyPlayerWithinRangeAt(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16.0)) {
         world.playSound(null, pos, Sounds.chlorine_belcher, SoundCategory.BLOCKS, 0.7F, 0.85F + world.rand.nextFloat() / 4.0F);
         EnumFacing f = (EnumFacing)state.getValue(FACING);
         ChlorineCloud cloud = new ChlorineCloud(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
         cloud.motionX = f.getXOffset() / 5.0 + RANDOM.nextGaussian() / 12.0;
         cloud.motionY = f.getYOffset() / 5.0 + RANDOM.nextGaussian() / 12.0;
         cloud.motionZ = f.getZOffset() / 5.0 + RANDOM.nextGaussian() / 12.0;
         cloud.moveX = cloud.motionX * 0.5;
         cloud.moveX = cloud.motionX * 0.5;
         cloud.moveX = cloud.motionX * 0.5;
         world.spawnEntity(cloud);
         cloud.velocityChanged = true;
      }

      super.randomTick(world, pos, state, random);
   }

   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
      IBlockState st = worldIn.getBlockState(pos.offset(side.getOpposite()));
      Block bloc = st.getBlock();
      return this.canPlaceBlockAt(worldIn, pos)
         && (
            bloc == BlocksRegister.TOXICGRASS
               || bloc == BlocksRegister.TOXICDIRT
               || bloc == BlocksRegister.SLUDGE
               || bloc == BlocksRegister.JUNK
               || bloc == BlocksRegister.NUCLEARWASTE
               || bloc == BlocksRegister.TOXIBERRYLOG
               || bloc == BlocksRegister.TOXIBERRYLEAVES
         );
   }

   public void breakBlock(World world, BlockPos pos, IBlockState state) {
      super.breakBlock(world, pos, state);
   }

   @Override
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

   @Override
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

   @Override
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

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
   }

   public int getMetaFromState(IBlockState state) {
      return ((EnumFacing)state.getValue(FACING)).getIndex();
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
   }

   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
      return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{FACING});
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, facing);
   }
}
