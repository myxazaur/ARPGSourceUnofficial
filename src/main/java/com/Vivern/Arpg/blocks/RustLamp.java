//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RustLamp extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyBool ON = PropertyBool.create("on");
   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.5, 0.875);
   protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.125, 0.125, 0.5, 0.875, 0.875, 1.0);
   protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.125, 0.125, 0.0, 0.875, 0.875, 0.5);
   protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.5, 0.125, 0.125, 1.0, 0.875, 0.875);
   protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0, 0.125, 0.125, 0.5, 0.875, 0.875);
   protected static final AxisAlignedBB UPPER_AABB = new AxisAlignedBB(0.125, 0.5, 0.125, 0.875, 1.0, 0.875);

   public RustLamp() {
      super(Material.IRON);
      this.setRegistryName("rust_lamp");
      this.setTranslationKey("rust_lamp");
      this.blockHardness = BlocksRegister.HR_BUNKER.HARDNESS;
      this.blockResistance = BlocksRegister.HR_BUNKER.RESISTANCE;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
      this.setSoundType(SoundType.METAL);
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!worldIn.isRemote) {
         boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
         if (flag) {
            worldIn.setBlockState(pos, state.withProperty(ON, !(Boolean)state.getValue(ON)));
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
      return state.getValue(ON) ? 14 : 0;
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
      return meta >= 6
         ? this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta)).withProperty(ON, true)
         : this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta)).withProperty(ON, false);
   }

   public int getMetaFromState(IBlockState state) {
      return ((EnumFacing)state.getValue(FACING)).getIndex() + (state.getValue(ON) ? 6 : 0);
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
   }

   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
      return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{FACING, ON});
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, facing).withProperty(ON, false);
   }
}
