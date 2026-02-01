package com.vivern.arpg.blocks;

import com.vivern.arpg.dimensions.aquatica.DimensionAquatica;
import com.vivern.arpg.tileentity.TileTritonHearth;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TritonHearth extends Block {
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.875, 0.875);
   public static final PropertyBool WET = PropertyBool.create("wet");

   public TritonHearth() {
      super(Material.WATER);
      this.setRegistryName("triton_hearth");
      this.setTranslationKey("triton_hearth");
      this.blockHardness = 10.0F;
      this.blockResistance = 10.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public void onFallenUpon(World world, BlockPos pos, Entity entityIn, float fallDistance) {
      TileEntity tile = world.getTileEntity(pos);
      if (tile instanceof TileTritonHearth) {
         IBlockState state = world.getBlockState(pos);
         TileTritonHearth tritonHearth = (TileTritonHearth)tile;
         tritonHearth.checkMaterials((Boolean)state.getValue(WET));
      }

      super.onFallenUpon(world, pos, entityIn, fallDistance);
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

   public void breakBlock(World world, BlockPos pos, IBlockState state) {
      if ((Boolean)state.getValue(WET) && this.isAroundWater(world, pos)) {
         world.setBlockState(pos, Blocks.WATER.getDefaultState());
      }

      super.breakBlock(world, pos, state);
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public Class<TileTritonHearth> getTileEntityClass() {
      return TileTritonHearth.class;
   }

   public TileTritonHearth getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileTritonHearth)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileTritonHearth createTileEntity(World world, IBlockState blockState) {
      return new TileTritonHearth();
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(WET, meta > 0);
   }

   public int getMetaFromState(IBlockState state) {
      return state.getValue(WET) ? 1 : 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{BlockLiquid.LEVEL, WET});
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(BlockLiquid.LEVEL, 0).withProperty(WET, this.isInWater(worldIn, pos));
   }

   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      return world.provider.getDimension() == 103
         ? DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks)
         : super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
   }
}
