//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StormRack extends Block {
   public static final PropertyDirection FACING = BlockHorizontal.FACING;
   public static final AxisAlignedBB LADDER_EAST_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.1875, 1.0, 1.0);
   public static final AxisAlignedBB LADDER_WEST_AABB = new AxisAlignedBB(0.8125, 0.0, 0.0, 1.0, 1.0, 1.0);
   public static final AxisAlignedBB LADDER_SOUTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.1875);
   public static final AxisAlignedBB LADDER_NORTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.8125, 1.0, 1.0, 1.0);

   public StormRack() {
      super(Material.CIRCUITS);
      this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH));
      this.setRegistryName("storm_rack");
      this.setTranslationKey("storm_rack");
      this.setHardness(10.0F);
      this.setResistance(10.0F);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.METAL);
      this.setHarvestLevel("pickaxe", 0);
      this.setLightLevel(0.2F);
   }

   @SideOnly(Side.CLIENT)
   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
      return 15728880;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      switch ((EnumFacing)state.getValue(FACING)) {
         case NORTH:
            return LADDER_NORTH_AABB;
         case SOUTH:
            return LADDER_SOUTH_AABB;
         case WEST:
            return LADDER_WEST_AABB;
         case EAST:
         default:
            return LADDER_EAST_AABB;
      }
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
      if (this.canAttachTo(worldIn, pos.west(), side)) {
         return true;
      } else if (this.canAttachTo(worldIn, pos.east(), side)) {
         return true;
      } else {
         return this.canAttachTo(worldIn, pos.north(), side) ? true : this.canAttachTo(worldIn, pos.south(), side);
      }
   }

   private boolean canAttachTo(World p_193392_1_, BlockPos p_193392_2_, EnumFacing p_193392_3_) {
      IBlockState iblockstate = p_193392_1_.getBlockState(p_193392_2_);
      boolean flag = isExceptBlockForAttachWithPiston(iblockstate.getBlock());
      return !flag && iblockstate.getBlockFaceShape(p_193392_1_, p_193392_2_, p_193392_3_) == BlockFaceShape.SOLID && !iblockstate.canProvidePower();
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      if (facing.getAxis().isHorizontal() && this.canAttachTo(worldIn, pos.offset(facing.getOpposite()), facing)) {
         return this.getDefaultState().withProperty(FACING, facing);
      } else {
         for (EnumFacing enumfacing : Plane.HORIZONTAL) {
            if (this.canAttachTo(worldIn, pos.offset(enumfacing.getOpposite()), enumfacing)) {
               return this.getDefaultState().withProperty(FACING, enumfacing);
            }
         }

         return this.getDefaultState();
      }
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
      if (!this.canAttachTo(worldIn, pos.offset(enumfacing.getOpposite()), enumfacing)) {
         this.dropBlockAsItem(worldIn, pos, state, 0);
         worldIn.setBlockToAir(pos);
      }

      super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
   }

   public IBlockState getStateFromMeta(int meta) {
      EnumFacing enumfacing = EnumFacing.byIndex(meta);
      if (enumfacing.getAxis() == Axis.Y) {
         enumfacing = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, enumfacing);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
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

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }
}
