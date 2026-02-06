package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.renders.GUNParticle;
import com.google.common.base.Predicate;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FrozenTorch extends Block {
   public static final ResourceLocation res = new ResourceLocation("arpg:textures/frostlight.png");
   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>() {
      public boolean apply(@Nullable EnumFacing p_apply_1_) {
         return p_apply_1_ != EnumFacing.DOWN;
      }
   });
   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.4F, 0.0, 0.4F, 0.6F, 0.6F, 0.6F);
   protected static final AxisAlignedBB TORCH_NORTH_AABB = new AxisAlignedBB(0.35F, 0.2F, 0.7F, 0.65F, 0.8F, 1.0);
   protected static final AxisAlignedBB TORCH_SOUTH_AABB = new AxisAlignedBB(0.35F, 0.2F, 0.0, 0.65F, 0.8F, 0.3F);
   protected static final AxisAlignedBB TORCH_WEST_AABB = new AxisAlignedBB(0.7F, 0.2F, 0.35F, 1.0, 0.8F, 0.65F);
   protected static final AxisAlignedBB TORCH_EAST_AABB = new AxisAlignedBB(0.0, 0.2F, 0.35F, 0.3F, 0.8F, 0.65F);

   public FrozenTorch() {
      super(Material.GLASS);
      this.setRegistryName("frozen_torch");
      this.setTranslationKey("frozen_torch");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.0F;
      this.setSoundType(SoundType.WOOD);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setLightLevel(0.75F);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      for (EnumFacing enumfacing : FACING.getAllowedValues()) {
         if (this.canPlaceAt(worldIn, pos, enumfacing)) {
            return true;
         }
      }

      return false;
   }

   private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
      BlockPos blockpos = pos.offset(facing.getOpposite());
      IBlockState iblockstate = worldIn.getBlockState(blockpos);
      Block block = iblockstate.getBlock();
      BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);
      if (facing.equals(EnumFacing.UP) && this.canPlaceOn(worldIn, blockpos)) {
         return true;
      } else if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
         return false;
      } else {
         return block != Blocks.ICE && block != Blocks.PACKED_ICE ? !isExceptBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID : true;
      }
   }

   private boolean canPlaceOn(World worldIn, BlockPos pos) {
      Block block = worldIn.getBlockState(pos).getBlock();
      return block.canPlaceTorchOnTop(worldIn.getBlockState(pos), worldIn, pos) || block == Blocks.ICE || block == Blocks.PACKED_ICE;
   }

   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
      this.checkForDrop(worldIn, pos, state);
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      this.onNeighborChangeInternal(worldIn, pos, state);
   }

   protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
      if (!this.checkForDrop(worldIn, pos, state)) {
         return true;
      } else {
         EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
         Axis enumfacing$axis = enumfacing.getAxis();
         EnumFacing enumfacing1 = enumfacing.getOpposite();
         BlockPos blockpos = pos.offset(enumfacing1);
         boolean flag = false;
         if (enumfacing$axis.isHorizontal() && worldIn.getBlockState(blockpos).getBlockFaceShape(worldIn, blockpos, enumfacing) != BlockFaceShape.SOLID) {
            flag = true;
         } else if (enumfacing$axis.isVertical() && !this.canPlaceOn(worldIn, blockpos)) {
            flag = true;
         }

         if (flag) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return true;
         } else {
            return false;
         }
      }
   }

   protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
      if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, (EnumFacing)state.getValue(FACING))) {
         return true;
      } else {
         if (worldIn.getBlockState(pos).getBlock() == this) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
         }

         return false;
      }
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      switch ((EnumFacing)state.getValue(FACING)) {
         case EAST:
            return TORCH_EAST_AABB;
         case WEST:
            return TORCH_WEST_AABB;
         case SOUTH:
            return TORCH_SOUTH_AABB;
         case NORTH:
            return TORCH_NORTH_AABB;
         default:
            return STANDING_AABB;
      }
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
      IBlockState iblockstate = this.getDefaultState();
      switch (meta) {
         case 1:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST);
            break;
         case 2:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST);
            break;
         case 3:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH);
            break;
         case 4:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
            break;
         case 5:
         default:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP);
      }

      return iblockstate;
   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      switch ((EnumFacing)state.getValue(FACING)) {
         case EAST:
            i |= 1;
            break;
         case WEST:
            i |= 2;
            break;
         case SOUTH:
            i |= 3;
            break;
         case NORTH:
            i |= 4;
            break;
         case DOWN:
         case UP:
         default:
            i |= 5;
      }

      return i;
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

   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
      items.add(new ItemStack(this, 1, 0));
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      if (this.canPlaceAt(worldIn, pos, facing)) {
         return this.getDefaultState().withProperty(FACING, facing);
      } else {
         for (EnumFacing enumfacing : Plane.HORIZONTAL) {
            if (this.canPlaceAt(worldIn, pos, enumfacing)) {
               return this.getDefaultState().withProperty(FACING, enumfacing);
            }
         }

         return this.getDefaultState();
      }
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
      double d0 = pos.getX() + 0.5;
      double d1 = pos.getY() + 0.64;
      double d2 = pos.getZ() + 0.5;
      double d3 = 0.22;
      double d4 = 0.27;
      if (enumfacing.getAxis().isHorizontal()) {
         EnumFacing enumfacing1 = enumfacing.getOpposite();
         GUNParticle spelll = new GUNParticle(
            res,
            0.14F + rand.nextFloat() / 20.0F,
            -0.001F,
            20 + rand.nextInt(10),
            240,
            worldIn,
            d0 + 0.27 * enumfacing1.getXOffset(),
            d1 + 0.22,
            d2 + 0.27 * enumfacing1.getZOffset(),
            0.0F,
            0.0F,
            0.0F,
            0.9F + rand.nextFloat() / 10.0F,
            1.0F,
            1.0F,
            false,
            0
         );
         spelll.scaleTickAdding = -0.0037F;
         worldIn.spawnEntity(spelll);
      } else {
         GUNParticle spelll = new GUNParticle(
            res,
            0.14F + rand.nextFloat() / 20.0F,
            -0.001F,
            20 + rand.nextInt(10),
            240,
            worldIn,
            d0,
            d1,
            d2,
            0.0F,
            0.0F,
            0.0F,
            0.9F + rand.nextFloat() / 10.0F,
            1.0F,
            1.0F,
            false,
            0
         );
         spelll.scaleTickAdding = -0.0037F;
         worldIn.spawnEntity(spelll);
      }
   }
}
