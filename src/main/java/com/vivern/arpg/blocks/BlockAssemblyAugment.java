package com.vivern.arpg.blocks;

import com.vivern.arpg.tileentity.TileAssemblyAugment;
import com.vivern.arpg.tileentity.TileAssemblyTable;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAssemblyAugment extends Block {
   public boolean opaque = true;
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public final AxisAlignedBB[] AABB;
   public boolean useBBOne = false;
   public boolean fullcube = true;

   public BlockAssemblyAugment(Material mater, String name, float hard, float resi) {
      super(mater);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.AABB = null;
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
   }

   public BlockAssemblyAugment(Material mater, String name, float hard, float resi, AxisAlignedBB[] AABB) {
      super(mater);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.AABB = AABB;
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
   }

   public BlockAssemblyAugment(Material mater, String name, float hard, float resi, AxisAlignedBB aabb) {
      super(mater);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.AABB = new AxisAlignedBB[]{aabb};
      this.useBBOne = true;
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      if (worldIn.getBlockState(pos.offset(facing)).getBlock() instanceof BlockAssemblyAugment) {
         return this.getDefaultState().withProperty(FACING, facing);
      } else {
         for (EnumFacing f : EnumFacing.HORIZONTALS) {
            if (worldIn.getBlockState(pos.offset(f)).getBlock() instanceof BlockAssemblyAugment) {
               return this.getDefaultState().withProperty(FACING, f);
            }
         }

         return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
      }
   }

   public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      super.onBlockPlacedBy(world, pos, state, placer, stack);
      BlockPos nextpos = pos;
      IBlockState next = state;

      for (int i = 0; i < 36; i++) {
         nextpos = nextpos.offset((EnumFacing)next.getValue(FACING));
         next = world.getBlockState(nextpos);
         if (world.getTileEntity(nextpos) instanceof TileAssemblyTable) {
            TileAssemblyTable tile = (TileAssemblyTable)world.getTileEntity(nextpos);
            tile.augments = tile.findAugments();
            break;
         }

         if (!(next.getBlock() instanceof BlockAssemblyAugment)) {
            break;
         }
      }
   }

   public void breakBlock(World world, BlockPos pos, IBlockState state) {
      BlockPos nextpos = pos;
      IBlockState next = state;

      for (int i = 0; i < 36; i++) {
         nextpos = nextpos.offset((EnumFacing)next.getValue(FACING));
         next = world.getBlockState(nextpos);
         if (world.getTileEntity(nextpos) instanceof TileAssemblyTable) {
            ((TileAssemblyTable)world.getTileEntity(nextpos)).onAugmentsChange();
            break;
         }

         if (!(next.getBlock() instanceof BlockAssemblyAugment)) {
            break;
         }
      }

      super.breakBlock(world, pos, state);
   }

   public IBlockState getStateFromMeta(int meta) {
      EnumFacing enumfacing = EnumFacing.byIndex(meta);
      if (enumfacing.getAxis() == Axis.Y) {
         enumfacing = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, enumfacing);
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

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      if (this.useBBOne) {
         return this.AABB[0];
      } else {
         return this.AABB == null ? FULL_BLOCK_AABB : this.AABB[((EnumFacing)state.getValue(FACING)).getIndex() - 2];
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      if (this.useBBOne) {
         return this.AABB[0];
      } else {
         return this.AABB == null ? FULL_BLOCK_AABB : this.AABB[((EnumFacing)blockState.getValue(FACING)).getIndex() - 2];
      }
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return this.opaque;
   }

   public BlockAssemblyAugment setOpaque(boolean b) {
      this.opaque = b;
      return this;
   }

   public BlockAssemblyAugment setFullCube(boolean b) {
      this.fullcube = b;
      return this;
   }

   public BlockAssemblyAugment setSound(SoundType sound) {
      this.setSoundType(sound);
      return this;
   }

   public boolean isFullCube(IBlockState state) {
      return this.fullcube;
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileAssemblyAugment createTileEntity(World world, IBlockState blockState) {
      return new TileAssemblyAugment();
   }
}
