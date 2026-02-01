package com.vivern.arpg.blocks;

import com.vivern.arpg.dimensions.aquatica.DimensionAquatica;
import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MetallicCoral extends Block implements IBlockHardBreak {
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyBool UPPER = PropertyBool.create("up");
   public static final PropertyBool DOWN = PropertyBool.create("down");
   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
   public static final PropertyBool WET = PropertyBool.create("wet");
   public static double aabbLow = 0.2F;
   public static double aabbHigh = 0.8F;
   public static double aabbMidLow = 0.15F;
   public static double aabbMidHigh = 0.85F;
   protected static final AxisAlignedBB mid_AABB = new AxisAlignedBB(aabbMidLow, aabbMidLow, aabbMidLow, aabbMidHigh, aabbMidHigh, aabbMidHigh);
   protected static final AxisAlignedBB vertical_AABB = new AxisAlignedBB(aabbLow, 0.0, aabbLow, aabbHigh, 1.0, aabbHigh);
   protected static final AxisAlignedBB horizontalSN_AABB = new AxisAlignedBB(aabbLow, aabbLow, 0.0, aabbHigh, aabbHigh, 1.0);
   protected static final AxisAlignedBB horizontalWE_AABB = new AxisAlignedBB(0.0, aabbLow, aabbLow, 1.0, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB horizontalALL_AABB = new AxisAlignedBB(0.0, aabbLow, 0.0, 1.0, aabbHigh, 1.0);
   protected static final AxisAlignedBB SW_AABB = new AxisAlignedBB(0.0, aabbLow, aabbLow, aabbHigh, aabbHigh, 1.0);
   protected static final AxisAlignedBB WN_AABB = new AxisAlignedBB(0.0, aabbLow, 0.0, aabbHigh, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB NE_AABB = new AxisAlignedBB(aabbLow, aabbLow, 0.0, 1.0, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB ES_AABB = new AxisAlignedBB(aabbLow, aabbLow, aabbLow, 1.0, aabbHigh, 1.0);
   protected static final AxisAlignedBB SWN_AABB = new AxisAlignedBB(0.0, aabbLow, 0.0, aabbHigh, aabbHigh, 1.0);
   protected static final AxisAlignedBB WNE_AABB = new AxisAlignedBB(0.0, aabbLow, 0.0, 1.0, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB NES_AABB = new AxisAlignedBB(aabbLow, aabbLow, 0.0, 1.0, aabbHigh, 1.0);
   protected static final AxisAlignedBB ESW_AABB = new AxisAlignedBB(0.0, aabbLow, aabbLow, 1.0, aabbHigh, 1.0);
   protected static final AxisAlignedBB USD_AABB = new AxisAlignedBB(aabbLow, 0.0, aabbLow, aabbHigh, 1.0, 1.0);
   protected static final AxisAlignedBB UWD_AABB = new AxisAlignedBB(0.0, 0.0, aabbLow, aabbHigh, 1.0, aabbHigh);
   protected static final AxisAlignedBB UND_AABB = new AxisAlignedBB(aabbLow, 0.0, 0.0, aabbHigh, 1.0, aabbHigh);
   protected static final AxisAlignedBB UED_AABB = new AxisAlignedBB(aabbLow, 0.0, aabbLow, 1.0, 1.0, aabbHigh);
   protected static final AxisAlignedBB S_half_AABB = new AxisAlignedBB(0.0, 0.0, aabbLow, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB W_half_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, aabbHigh, 1.0, 1.0);
   protected static final AxisAlignedBB N_half_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, aabbHigh);
   protected static final AxisAlignedBB E_half_AABB = new AxisAlignedBB(aabbLow, 0.0, 0.0, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB U_half_AABB = new AxisAlignedBB(0.0, aabbLow, 0.0, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB D_half_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, aabbHigh, 1.0);
   protected static final AxisAlignedBB SUND_plate_AABB = new AxisAlignedBB(aabbLow, 0.0, 0.0, aabbHigh, 1.0, 1.0);
   protected static final AxisAlignedBB WUED_plate_AABB = new AxisAlignedBB(0.0, 0.0, aabbLow, 1.0, 1.0, aabbHigh);
   protected static final AxisAlignedBB SWNU_AABB = new AxisAlignedBB(0.0, aabbLow, 0.0, aabbHigh, 1.0, 1.0);
   protected static final AxisAlignedBB WNEU_AABB = new AxisAlignedBB(0.0, aabbLow, 0.0, 1.0, 1.0, aabbHigh);
   protected static final AxisAlignedBB NESU_AABB = new AxisAlignedBB(aabbLow, aabbLow, 0.0, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB ESWU_AABB = new AxisAlignedBB(0.0, aabbLow, aabbLow, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB SWND_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, aabbHigh, aabbHigh, 1.0);
   protected static final AxisAlignedBB WNED_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB NESD_AABB = new AxisAlignedBB(aabbLow, 0.0, 0.0, 1.0, aabbHigh, 1.0);
   protected static final AxisAlignedBB ESWD_AABB = new AxisAlignedBB(0.0, 0.0, aabbLow, 1.0, aabbHigh, 1.0);
   protected static final AxisAlignedBB SUN_AABB = new AxisAlignedBB(aabbLow, aabbLow, 0.0, aabbHigh, 1.0, 1.0);
   protected static final AxisAlignedBB WUE_AABB = new AxisAlignedBB(0.0, aabbLow, aabbLow, 1.0, 1.0, aabbHigh);
   protected static final AxisAlignedBB SDN_AABB = new AxisAlignedBB(aabbLow, 0.0, 0.0, aabbHigh, aabbHigh, 1.0);
   protected static final AxisAlignedBB WDE_AABB = new AxisAlignedBB(0.0, 0.0, aabbLow, 1.0, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB SWDU_AABB = new AxisAlignedBB(0.0, 0.0, aabbLow, aabbHigh, 1.0, 1.0);
   protected static final AxisAlignedBB WNDU_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, aabbHigh, 1.0, aabbHigh);
   protected static final AxisAlignedBB NEDU_AABB = new AxisAlignedBB(aabbLow, 0.0, 0.0, 1.0, 1.0, aabbHigh);
   protected static final AxisAlignedBB ESDU_AABB = new AxisAlignedBB(aabbLow, 0.0, aabbLow, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB WUN_AABB = new AxisAlignedBB(0.0, aabbLow, 0.0, aabbHigh, 1.0, aabbHigh);
   protected static final AxisAlignedBB SUW_AABB = new AxisAlignedBB(0.0, aabbLow, aabbLow, aabbHigh, 1.0, 1.0);
   protected static final AxisAlignedBB NUE_AABB = new AxisAlignedBB(aabbLow, aabbLow, 0.0, 1.0, 1.0, aabbHigh);
   protected static final AxisAlignedBB SUE_AABB = new AxisAlignedBB(aabbLow, aabbLow, aabbLow, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB SDE_AABB = new AxisAlignedBB(aabbLow, 0.0, aabbLow, 1.0, aabbHigh, 1.0);
   protected static final AxisAlignedBB WDN_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, aabbHigh, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB NDE_AABB = new AxisAlignedBB(aabbLow, 0.0, 0.0, 1.0, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB SDW_AABB = new AxisAlignedBB(0.0, 0.0, aabbLow, aabbHigh, aabbHigh, 1.0);
   protected static final AxisAlignedBB UN_AABB = new AxisAlignedBB(aabbLow, aabbLow, 0.0, aabbHigh, 1.0, aabbHigh);
   protected static final AxisAlignedBB DN_AABB = new AxisAlignedBB(aabbLow, 0.0, 0.0, aabbHigh, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB US_AABB = new AxisAlignedBB(aabbLow, aabbLow, aabbLow, aabbHigh, 1.0, 1.0);
   protected static final AxisAlignedBB DS_AABB = new AxisAlignedBB(aabbLow, 0.0, aabbLow, aabbHigh, aabbHigh, 1.0);
   protected static final AxisAlignedBB UW_AABB = new AxisAlignedBB(0.0, aabbLow, aabbLow, aabbHigh, 1.0, aabbHigh);
   protected static final AxisAlignedBB DW_AABB = new AxisAlignedBB(0.0, 0.0, aabbLow, aabbHigh, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB UE_AABB = new AxisAlignedBB(aabbLow, aabbLow, aabbLow, 1.0, 1.0, aabbHigh);
   protected static final AxisAlignedBB DE_AABB = new AxisAlignedBB(aabbLow, 0.0, aabbLow, 1.0, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB N_AABB = new AxisAlignedBB(aabbLow, aabbLow, 0.0, aabbHigh, aabbHigh, 0.5);
   protected static final AxisAlignedBB S_AABB = new AxisAlignedBB(aabbLow, aabbLow, 0.5, aabbHigh, aabbHigh, 1.0);
   protected static final AxisAlignedBB W_AABB = new AxisAlignedBB(0.0, aabbLow, aabbLow, 0.5, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB E_AABB = new AxisAlignedBB(0.5, aabbLow, aabbLow, 1.0, aabbHigh, aabbHigh);
   protected static final AxisAlignedBB U_AABB = new AxisAlignedBB(aabbLow, 0.5, aabbLow, aabbHigh, 1.0, aabbHigh);
   protected static final AxisAlignedBB D_AABB = new AxisAlignedBB(aabbLow, 0.0, aabbLow, aabbHigh, 0.5, aabbHigh);

   public MetallicCoral() {
      super(Material.ROCK);
      this.setRegistryName("metallic_coral");
      this.setTranslationKey("metallic_coral");
      this.blockHardness = BlocksRegister.HR_METALLIC_CORALS.HARDNESS;
      this.blockResistance = BlocksRegister.HR_METALLIC_CORALS.RESISTANCE;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_METALLIC_CORALS.LVL);
   }

   @Override
   public BlocksRegister.Hardres getHardres() {
      return BlocksRegister.HR_METALLIC_CORALS;
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

   public boolean isFullBlock(IBlockState state) {
      return false;
   }

   public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return true;
   }

   public boolean isBlockConnecting(IBlockState state) {
      if (state.getMaterial() == Material.AIR) {
         return false;
      } else {
         return state.getBlock() != Blocks.WATER
               && state.getBlock() != Blocks.FLOWING_WATER
               && state.getBlock() != Blocks.LAVA
               && state.getBlock() != Blocks.FLOWING_LAVA
            ? !(state.getBlock() instanceof IFluidBlock)
            : false;
      }
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      BlockPos wb = pos.west();
      BlockPos eb = pos.east();
      BlockPos sb = pos.south();
      BlockPos nb = pos.north();
      BlockPos ub = pos.up();
      BlockPos db = pos.down();
      boolean west = this.isBlockConnecting(worldIn.getBlockState(wb));
      boolean east = this.isBlockConnecting(worldIn.getBlockState(eb));
      boolean south = this.isBlockConnecting(worldIn.getBlockState(sb));
      boolean north = this.isBlockConnecting(worldIn.getBlockState(nb));
      boolean up = this.isBlockConnecting(worldIn.getBlockState(ub));
      boolean down = this.isBlockConnecting(worldIn.getBlockState(db));
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
      return this.getDefaultState().withProperty(LEVEL, 0).withProperty(WET, meta > 0);
   }

   public int getMetaFromState(IBlockState state) {
      return state.getValue(WET) ? 1 : 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, UPPER, DOWN, LEVEL, WET});
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
      boolean west = this.isBlockConnecting(worldIn.getBlockState(wb));
      boolean east = this.isBlockConnecting(worldIn.getBlockState(eb));
      boolean south = this.isBlockConnecting(worldIn.getBlockState(sb));
      boolean north = this.isBlockConnecting(worldIn.getBlockState(nb));
      boolean up = this.isBlockConnecting(worldIn.getBlockState(ub));
      boolean down = this.isBlockConnecting(worldIn.getBlockState(db));
      return this.getDefaultState()
         .withProperty(WEST, west)
         .withProperty(EAST, east)
         .withProperty(NORTH, north)
         .withProperty(SOUTH, south)
         .withProperty(UPPER, up)
         .withProperty(DOWN, down)
         .withProperty(LEVEL, 0)
         .withProperty(WET, this.isInWater(worldIn, pos));
   }

   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      return world.provider.getDimension() == 103
         ? DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks)
         : super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
   }
}
