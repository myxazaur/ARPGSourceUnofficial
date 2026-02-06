package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.google.common.base.Predicate;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PalmFruitBunch extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>() {
      public boolean apply(@Nullable EnumFacing p_apply_1_) {
         return p_apply_1_ != EnumFacing.DOWN && p_apply_1_ != EnumFacing.UP;
      }
   });
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.875, 0.875);

   public PalmFruitBunch() {
      super(Material.WOOD);
      this.setRegistryName("palm_fruit_bunch");
      this.setTranslationKey("palm_fruit_bunch");
      this.blockHardness = 1.2F;
      this.blockResistance = 0.5F;
      this.setSoundType(SoundTypeCrunchy.CRUNCHY);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      for (EnumFacing enumfacing : EnumFacing.HORIZONTALS) {
         if (this.canPlaceAt(worldIn, pos, enumfacing)) {
            return true;
         }
      }

      return false;
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
         } else if (enumfacing$axis.isVertical()) {
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

   private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
      BlockPos blockpos = pos.offset(facing.getOpposite());
      IBlockState iblockstate = worldIn.getBlockState(blockpos);
      return facing != EnumFacing.UP && facing != EnumFacing.DOWN
         ? iblockstate.getMaterial() == Material.WOOD || iblockstate.getBlock() instanceof PalmLeaves
         : false;
   }

   public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
      Random rand = world instanceof World ? ((World)world).rand : new Random();
      drops.add(new ItemStack(BlocksRegister.PALMSAPLING));
      if (rand.nextInt(3) == 0) {
         drops.add(new ItemStack(BlocksRegister.PALMSAPLING));
      }

      if (rand.nextInt(3) == 0) {
         drops.add(new ItemStack(BlocksRegister.PALMSAPLING));
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
      return AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return AABB;
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
         default:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
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
         default:
            i |= 4;
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
}
