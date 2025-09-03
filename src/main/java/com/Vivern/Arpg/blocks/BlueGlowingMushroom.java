//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
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

public class BlueGlowingMushroom extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>() {
      public boolean apply(@Nullable EnumFacing p_apply_1_) {
         return p_apply_1_ != EnumFacing.DOWN;
      }
   });
   public static final PropertyInteger MTYPE = PropertyInteger.create("mtype", 0, 2);
   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.3, 0.0, 0.3, 0.7, 0.7, 0.7);
   protected static final AxisAlignedBB M_NORTH_AABB = new AxisAlignedBB(0.25, 0.2, 0.6, 0.75, 0.9, 1.0);
   protected static final AxisAlignedBB M_SOUTH_AABB = new AxisAlignedBB(0.25, 0.2, 0.0, 0.75, 0.9, 0.4);
   protected static final AxisAlignedBB M_WEST_AABB = new AxisAlignedBB(0.6, 0.2, 0.25, 1.0, 0.9, 0.75);
   protected static final AxisAlignedBB M_EAST_AABB = new AxisAlignedBB(0.0, 0.2, 0.25, 0.4, 0.9, 0.75);

   public BlueGlowingMushroom() {
      super(Material.GLASS);
      this.setRegistryName("blue_glowing_mushroom");
      this.setTranslationKey("blue_glowing_mushroom");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.0F;
      this.setSoundType(SoundType.PLANT);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setLightLevel(0.45F);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
   }

   public static IBlockState getRandomStateWorldgen(World worldIn, BlockPos pos) {
      int i = worldIn.rand.nextInt(6);
      EnumFacing[] facingval = EnumFacing.values();

      for (int rr = 0; rr < 6; rr++) {
         EnumFacing facing = facingval[i];
         if (canPlaceAt(worldIn, pos, facing)) {
            return BlocksRegister.BLUEGLOWINGMUSH.getDefaultState().withProperty(FACING, facing).withProperty(MTYPE, worldIn.rand.nextInt(3));
         }

         if (i < 5) {
            i++;
         } else if (i == 5) {
            i = 0;
         }
      }

      System.out.println("DEFAULT STATE ERROR IN BlueGlowingMushroom.getRandomStateWorldgen   ");
      return BlocksRegister.BLUEGLOWINGMUSH.getDefaultState();
   }

   @SideOnly(Side.CLIENT)
   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
      return 15728880;
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      for (EnumFacing enumfacing : FACING.getAllowedValues()) {
         if (canPlaceAt(worldIn, pos, enumfacing)) {
            return true;
         }
      }

      return false;
   }

   public static boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
      BlockPos blockpos = pos.offset(facing.getOpposite());
      IBlockState iblockstate = worldIn.getBlockState(blockpos);
      Block block = iblockstate.getBlock();
      BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);
      if (facing.equals(EnumFacing.UP) && canPlaceOn(worldIn, blockpos)) {
         return true;
      } else {
         return facing != EnumFacing.UP && facing != EnumFacing.DOWN ? !isExceptBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID : false;
      }
   }

   private static boolean canPlaceOn(World worldIn, BlockPos pos) {
      Block block = worldIn.getBlockState(pos).getBlock();
      return block.canPlaceTorchOnTop(worldIn.getBlockState(pos), worldIn, pos);
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
         } else if (enumfacing$axis.isVertical() && !canPlaceOn(worldIn, blockpos)) {
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
      if (state.getBlock() == this && canPlaceAt(worldIn, pos, (EnumFacing)state.getValue(FACING))) {
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
            return M_EAST_AABB;
         case WEST:
            return M_WEST_AABB;
         case SOUTH:
            return M_SOUTH_AABB;
         case NORTH:
            return M_NORTH_AABB;
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
      if (meta < 6) {
         switch (meta) {
            case 1:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST).withProperty(MTYPE, 0);
               break;
            case 2:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(MTYPE, 0);
               break;
            case 3:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH).withProperty(MTYPE, 0);
               break;
            case 4:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH).withProperty(MTYPE, 0);
               break;
            case 5:
            default:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP).withProperty(MTYPE, 0);
         }
      }

      if (meta > 5 && meta < 11) {
         switch (meta) {
            case 6:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST).withProperty(MTYPE, 1);
               break;
            case 7:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(MTYPE, 1);
               break;
            case 8:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH).withProperty(MTYPE, 1);
               break;
            case 9:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH).withProperty(MTYPE, 1);
               break;
            case 10:
            default:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP).withProperty(MTYPE, 1);
         }
      }

      if (meta > 10) {
         switch (meta) {
            case 11:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST).withProperty(MTYPE, 2);
               break;
            case 12:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(MTYPE, 2);
               break;
            case 13:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH).withProperty(MTYPE, 2);
               break;
            case 14:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH).withProperty(MTYPE, 2);
               break;
            case 15:
            default:
               iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP).withProperty(MTYPE, 2);
         }
      }

      return iblockstate;
   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      switch (state.getValue(MTYPE)) {
         case 1:
            i += 5;
            break;
         case 2:
            i += 10;
      }

      switch ((EnumFacing)state.getValue(FACING)) {
         case EAST:
            i++;
            break;
         case WEST:
            i += 2;
            break;
         case SOUTH:
            i += 3;
            break;
         case NORTH:
            i += 4;
            break;
         default:
            i += 5;
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
      return new BlockStateContainer(this, new IProperty[]{FACING, MTYPE});
   }

   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
      items.add(new ItemStack(this, 1, 0));
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      if (canPlaceAt(worldIn, pos, facing)) {
         return this.getDefaultState().withProperty(FACING, facing).withProperty(MTYPE, worldIn.rand.nextInt(3));
      } else {
         for (EnumFacing enumfacing : Plane.HORIZONTAL) {
            if (canPlaceAt(worldIn, pos, enumfacing)) {
               return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(MTYPE, worldIn.rand.nextInt(3));
            }
         }

         return this.getDefaultState();
      }
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }
}
