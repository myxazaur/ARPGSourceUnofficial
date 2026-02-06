package com.Vivern.Arpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
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

public class ColliderPipe extends Block {
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyBool UPPER = PropertyBool.create("up");
   public static final PropertyBool DOWN = PropertyBool.create("down");
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   public static final PropertyBool HERMETIC = PropertyBool.create("hermetic");
   protected static final AxisAlignedBB mid_AABB = new AxisAlignedBB(0.1875, 0.1875, 0.1875, 0.8125, 0.8125, 0.8125);
   protected static final AxisAlignedBB vertical_AABB = new AxisAlignedBB(0.3125, 0.0, 0.3125, 0.6875, 1.0, 0.6875);
   protected static final AxisAlignedBB horizontalSN_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.0, 0.6875, 0.6875, 1.0);
   protected static final AxisAlignedBB horizontalWE_AABB = new AxisAlignedBB(0.0, 0.3125, 0.3125, 1.0, 0.6875, 0.6875);
   protected static final AxisAlignedBB horizontalALL_AABB = new AxisAlignedBB(0.0, 0.3125, 0.0, 1.0, 0.6875, 1.0);
   protected static final AxisAlignedBB SW_AABB = new AxisAlignedBB(0.0, 0.3125, 0.3125, 0.6875, 0.6875, 1.0);
   protected static final AxisAlignedBB WN_AABB = new AxisAlignedBB(0.0, 0.3125, 0.0, 0.6875, 0.6875, 0.6875);
   protected static final AxisAlignedBB NE_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.0, 1.0, 0.6875, 0.6875);
   protected static final AxisAlignedBB ES_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.3125, 1.0, 0.6875, 1.0);
   protected static final AxisAlignedBB SWN_AABB = new AxisAlignedBB(0.0, 0.3125, 0.0, 0.6875, 0.6875, 1.0);
   protected static final AxisAlignedBB WNE_AABB = new AxisAlignedBB(0.0, 0.3125, 0.0, 1.0, 0.6875, 0.6875);
   protected static final AxisAlignedBB NES_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.0, 1.0, 0.6875, 1.0);
   protected static final AxisAlignedBB ESW_AABB = new AxisAlignedBB(0.0, 0.3125, 0.3125, 1.0, 0.6875, 1.0);
   protected static final AxisAlignedBB USD_AABB = new AxisAlignedBB(0.3125, 0.0, 0.3125, 0.6875, 1.0, 1.0);
   protected static final AxisAlignedBB UWD_AABB = new AxisAlignedBB(0.0, 0.0, 0.3125, 0.6875, 1.0, 0.6875);
   protected static final AxisAlignedBB UND_AABB = new AxisAlignedBB(0.3125, 0.0, 0.0, 0.6875, 1.0, 0.6875);
   protected static final AxisAlignedBB UED_AABB = new AxisAlignedBB(0.3125, 0.0, 0.3125, 1.0, 1.0, 0.6875);
   protected static final AxisAlignedBB S_half_AABB = new AxisAlignedBB(0.0, 0.0, 0.3125, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB W_half_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.6875, 1.0, 1.0);
   protected static final AxisAlignedBB N_half_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.6875);
   protected static final AxisAlignedBB E_half_AABB = new AxisAlignedBB(0.3125, 0.0, 0.0, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB U_half_AABB = new AxisAlignedBB(0.0, 0.3125, 0.0, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB D_half_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.6875, 1.0);
   protected static final AxisAlignedBB SUND_plate_AABB = new AxisAlignedBB(0.3125, 0.0, 0.0, 0.6875, 1.0, 1.0);
   protected static final AxisAlignedBB WUED_plate_AABB = new AxisAlignedBB(0.0, 0.0, 0.3125, 1.0, 1.0, 0.6875);
   protected static final AxisAlignedBB SWNU_AABB = new AxisAlignedBB(0.0, 0.3125, 0.0, 0.6875, 1.0, 1.0);
   protected static final AxisAlignedBB WNEU_AABB = new AxisAlignedBB(0.0, 0.3125, 0.0, 1.0, 1.0, 0.6875);
   protected static final AxisAlignedBB NESU_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.0, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB ESWU_AABB = new AxisAlignedBB(0.0, 0.3125, 0.3125, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB SWND_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.6875, 0.6875, 1.0);
   protected static final AxisAlignedBB WNED_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.6875, 0.6875);
   protected static final AxisAlignedBB NESD_AABB = new AxisAlignedBB(0.3125, 0.0, 0.0, 1.0, 0.6875, 1.0);
   protected static final AxisAlignedBB ESWD_AABB = new AxisAlignedBB(0.0, 0.0, 0.3125, 1.0, 0.6875, 1.0);
   protected static final AxisAlignedBB SUN_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.0, 0.6875, 1.0, 1.0);
   protected static final AxisAlignedBB WUE_AABB = new AxisAlignedBB(0.0, 0.3125, 0.3125, 1.0, 1.0, 0.6875);
   protected static final AxisAlignedBB SDN_AABB = new AxisAlignedBB(0.3125, 0.0, 0.0, 0.6875, 0.6875, 1.0);
   protected static final AxisAlignedBB WDE_AABB = new AxisAlignedBB(0.0, 0.0, 0.3125, 1.0, 0.6875, 0.6875);
   protected static final AxisAlignedBB SWDU_AABB = new AxisAlignedBB(0.0, 0.0, 0.3125, 0.6875, 1.0, 1.0);
   protected static final AxisAlignedBB WNDU_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.6875, 1.0, 0.6875);
   protected static final AxisAlignedBB NEDU_AABB = new AxisAlignedBB(0.3125, 0.0, 0.0, 1.0, 1.0, 0.6875);
   protected static final AxisAlignedBB ESDU_AABB = new AxisAlignedBB(0.3125, 0.0, 0.3125, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB WUN_AABB = new AxisAlignedBB(0.0, 0.3125, 0.0, 0.6875, 1.0, 0.6875);
   protected static final AxisAlignedBB SUW_AABB = new AxisAlignedBB(0.0, 0.3125, 0.3125, 0.6875, 1.0, 1.0);
   protected static final AxisAlignedBB NUE_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.0, 1.0, 1.0, 0.6875);
   protected static final AxisAlignedBB SUE_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.3125, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB SDE_AABB = new AxisAlignedBB(0.3125, 0.0, 0.3125, 1.0, 0.6875, 1.0);
   protected static final AxisAlignedBB WDN_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.6875, 0.6875, 0.6875);
   protected static final AxisAlignedBB NDE_AABB = new AxisAlignedBB(0.3125, 0.0, 0.0, 1.0, 0.6875, 0.6875);
   protected static final AxisAlignedBB SDW_AABB = new AxisAlignedBB(0.0, 0.0, 0.3125, 0.6875, 0.6875, 1.0);
   protected static final AxisAlignedBB UN_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.0, 0.6875, 1.0, 0.6875);
   protected static final AxisAlignedBB DN_AABB = new AxisAlignedBB(0.3125, 0.0, 0.0, 0.6875, 0.6875, 0.6875);
   protected static final AxisAlignedBB US_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.3125, 0.6875, 1.0, 1.0);
   protected static final AxisAlignedBB DS_AABB = new AxisAlignedBB(0.3125, 0.0, 0.3125, 0.6875, 0.6875, 1.0);
   protected static final AxisAlignedBB UW_AABB = new AxisAlignedBB(0.0, 0.3125, 0.3125, 0.6875, 1.0, 0.6875);
   protected static final AxisAlignedBB DW_AABB = new AxisAlignedBB(0.0, 0.0, 0.3125, 0.6875, 0.6875, 0.6875);
   protected static final AxisAlignedBB UE_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.3125, 1.0, 1.0, 0.6875);
   protected static final AxisAlignedBB DE_AABB = new AxisAlignedBB(0.3125, 0.0, 0.3125, 1.0, 0.6875, 0.6875);
   protected static final AxisAlignedBB N_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.0, 0.6875, 0.6875, 0.5);
   protected static final AxisAlignedBB S_AABB = new AxisAlignedBB(0.3125, 0.3125, 0.5, 0.6875, 0.6875, 1.0);
   protected static final AxisAlignedBB W_AABB = new AxisAlignedBB(0.0, 0.3125, 0.3125, 0.5, 0.6875, 0.6875);
   protected static final AxisAlignedBB E_AABB = new AxisAlignedBB(0.5, 0.3125, 0.3125, 1.0, 0.6875, 0.6875);
   protected static final AxisAlignedBB U_AABB = new AxisAlignedBB(0.3125, 0.5, 0.3125, 0.6875, 1.0, 0.6875);
   protected static final AxisAlignedBB D_AABB = new AxisAlignedBB(0.3125, 0.0, 0.3125, 0.6875, 0.5, 0.6875);

   public ColliderPipe() {
      super(Material.IRON);
      this.setRegistryName("collider_pipe");
      this.setTranslationKey("collider_pipe");
      this.blockHardness = 4.0F;
      this.blockResistance = 3.0F;
      this.setSoundType(SoundType.METAL);
      this.setCreativeTab(CreativeTabs.REDSTONE);
      this.setDefaultState(this.getDefaultState().withProperty(POWERED, false).withProperty(HERMETIC, false));
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
      boolean flag = world.isBlockPowered(pos) || world.isBlockPowered(pos.up());
      world.setBlockState(pos, state.withProperty(POWERED, flag));
   }

   public boolean isFullBlock(IBlockState state) {
      return false;
   }

   public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return side == EnumFacing.UP || side == EnumFacing.DOWN;
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      BlockPos wb = pos.west();
      BlockPos eb = pos.east();
      BlockPos sb = pos.south();
      BlockPos nb = pos.north();
      BlockPos ub = pos.up();
      BlockPos db = pos.down();
      boolean west = worldIn.getBlockState(wb).getBlock() == this || worldIn.isSideSolid(wb, EnumFacing.WEST, false);
      boolean east = worldIn.getBlockState(eb).getBlock() == this || worldIn.isSideSolid(eb, EnumFacing.EAST, false);
      boolean south = worldIn.getBlockState(sb).getBlock() == this || worldIn.isSideSolid(sb, EnumFacing.SOUTH, false);
      boolean north = worldIn.getBlockState(nb).getBlock() == this || worldIn.isSideSolid(nb, EnumFacing.NORTH, false);
      boolean up = worldIn.getBlockState(ub).getBlock() == this || worldIn.isSideSolid(ub, EnumFacing.UP, false);
      boolean down = worldIn.getBlockState(db).getBlock() == this || worldIn.isSideSolid(db, EnumFacing.DOWN, false);
      return state.withProperty(WEST, west)
         .withProperty(EAST, east)
         .withProperty(NORTH, north)
         .withProperty(SOUTH, south)
         .withProperty(UPPER, up)
         .withProperty(DOWN, down);
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return true;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return this.getAABB(blockState);
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return this.getAABB(this.getActualState(state, source, pos));
   }

   public AxisAlignedBB getAABB(IBlockState state) {
      boolean n = (Boolean)state.getValue(NORTH);
      boolean e = (Boolean)state.getValue(EAST);
      boolean s = (Boolean)state.getValue(SOUTH);
      boolean w = (Boolean)state.getValue(WEST);
      boolean u = (Boolean)state.getValue(UPPER);
      boolean d = (Boolean)state.getValue(DOWN);
      if (n) {
         if (s) {
            if (w) {
               if (e) {
                  if (u) {
                     return d ? FULL_BLOCK_AABB : U_half_AABB;
                  } else {
                     return d ? D_half_AABB : horizontalALL_AABB;
                  }
               } else if (u) {
                  return d ? W_half_AABB : SWNU_AABB;
               } else {
                  return d ? SWND_AABB : SWN_AABB;
               }
            } else if (e) {
               if (u) {
                  return d ? E_half_AABB : NESU_AABB;
               } else {
                  return d ? NESD_AABB : NES_AABB;
               }
            } else if (u) {
               return d ? SUND_plate_AABB : SUN_AABB;
            } else {
               return d ? SDN_AABB : horizontalSN_AABB;
            }
         } else if (w) {
            if (e) {
               if (u) {
                  return d ? N_half_AABB : WNEU_AABB;
               } else {
                  return d ? WNED_AABB : WNE_AABB;
               }
            } else if (u) {
               return d ? WNDU_AABB : WUN_AABB;
            } else {
               return d ? WDN_AABB : WN_AABB;
            }
         } else if (e) {
            if (u) {
               return d ? NEDU_AABB : NUE_AABB;
            } else {
               return d ? NDE_AABB : NE_AABB;
            }
         } else if (u) {
            return d ? UND_AABB : UN_AABB;
         } else {
            return d ? DN_AABB : N_AABB;
         }
      } else if (s) {
         if (w) {
            if (e) {
               if (u) {
                  return d ? S_half_AABB : ESWU_AABB;
               } else {
                  return d ? ESWD_AABB : ESW_AABB;
               }
            } else if (u) {
               return d ? SWDU_AABB : SUW_AABB;
            } else {
               return d ? SDW_AABB : SW_AABB;
            }
         } else if (e) {
            if (u) {
               return d ? ESDU_AABB : SUE_AABB;
            } else {
               return d ? SDE_AABB : ES_AABB;
            }
         } else if (u) {
            return d ? USD_AABB : US_AABB;
         } else {
            return d ? DS_AABB : S_AABB;
         }
      } else if (w) {
         if (e) {
            if (u) {
               return d ? WUED_plate_AABB : WUE_AABB;
            } else {
               return d ? WDE_AABB : horizontalWE_AABB;
            }
         } else if (u) {
            return d ? UWD_AABB : UW_AABB;
         } else {
            return d ? DW_AABB : W_AABB;
         }
      } else if (e) {
         if (u) {
            return d ? UED_AABB : UE_AABB;
         } else {
            return d ? DE_AABB : E_AABB;
         }
      } else if (u) {
         return d ? vertical_AABB : U_AABB;
      } else {
         return d ? D_AABB : mid_AABB;
      }
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
      return this.getDefaultState().withProperty(POWERED, (meta & 1) > 0).withProperty(HERMETIC, (meta & 2) > 0);
   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      if ((Boolean)state.getValue(POWERED)) {
         i |= 1;
      }

      if ((Boolean)state.getValue(HERMETIC)) {
         i |= 2;
      }

      return i;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, UPPER, DOWN, POWERED, HERMETIC});
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      BlockPos wb = pos.west();
      BlockPos eb = pos.east();
      BlockPos sb = pos.south();
      BlockPos nb = pos.north();
      BlockPos ub = pos.up();
      BlockPos db = pos.down();
      boolean west = worldIn.getBlockState(wb).getBlock() == this || worldIn.isSideSolid(wb, EnumFacing.WEST, false);
      boolean east = worldIn.getBlockState(eb).getBlock() == this || worldIn.isSideSolid(eb, EnumFacing.EAST, false);
      boolean south = worldIn.getBlockState(sb).getBlock() == this || worldIn.isSideSolid(sb, EnumFacing.SOUTH, false);
      boolean north = worldIn.getBlockState(nb).getBlock() == this || worldIn.isSideSolid(nb, EnumFacing.NORTH, false);
      boolean up = worldIn.getBlockState(ub).getBlock() == this || worldIn.isSideSolid(ub, EnumFacing.UP, false);
      boolean down = worldIn.getBlockState(db).getBlock() == this || worldIn.isSideSolid(db, EnumFacing.DOWN, false);
      return this.getDefaultState()
         .withProperty(WEST, west)
         .withProperty(EAST, east)
         .withProperty(NORTH, north)
         .withProperty(SOUTH, south)
         .withProperty(UPPER, up)
         .withProperty(DOWN, down);
   }
}
