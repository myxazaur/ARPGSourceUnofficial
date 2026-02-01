package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
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

public class DecorativePipe extends Block implements IBlockHardBreak {
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyBool UPPER = PropertyBool.create("up");
   public static final PropertyBool DOWN = PropertyBool.create("down");
   public double aabbLow;
   public double aabbHigh;
   public double aabbMidLow;
   public double aabbMidHigh;
   public IPipeConnectorFunction isConnected;
   protected AxisAlignedBB mid_AABB;
   protected AxisAlignedBB vertical_AABB;
   protected AxisAlignedBB horizontalSN_AABB;
   protected AxisAlignedBB horizontalWE_AABB;
   protected AxisAlignedBB horizontalALL_AABB;
   protected AxisAlignedBB SW_AABB;
   protected AxisAlignedBB WN_AABB;
   protected AxisAlignedBB NE_AABB;
   protected AxisAlignedBB ES_AABB;
   protected AxisAlignedBB SWN_AABB;
   protected AxisAlignedBB WNE_AABB;
   protected AxisAlignedBB NES_AABB;
   protected AxisAlignedBB ESW_AABB;
   protected AxisAlignedBB USD_AABB;
   protected AxisAlignedBB UWD_AABB;
   protected AxisAlignedBB UND_AABB;
   protected AxisAlignedBB UED_AABB;
   protected AxisAlignedBB S_half_AABB;
   protected AxisAlignedBB W_half_AABB;
   protected AxisAlignedBB N_half_AABB;
   protected AxisAlignedBB E_half_AABB;
   protected AxisAlignedBB U_half_AABB;
   protected AxisAlignedBB D_half_AABB;
   protected AxisAlignedBB SUND_plate_AABB;
   protected AxisAlignedBB WUED_plate_AABB;
   protected AxisAlignedBB SWNU_AABB;
   protected AxisAlignedBB WNEU_AABB;
   protected AxisAlignedBB NESU_AABB;
   protected AxisAlignedBB ESWU_AABB;
   protected AxisAlignedBB SWND_AABB;
   protected AxisAlignedBB WNED_AABB;
   protected AxisAlignedBB NESD_AABB;
   protected AxisAlignedBB ESWD_AABB;
   protected AxisAlignedBB SUN_AABB;
   protected AxisAlignedBB WUE_AABB;
   protected AxisAlignedBB SDN_AABB;
   protected AxisAlignedBB WDE_AABB;
   protected AxisAlignedBB SWDU_AABB;
   protected AxisAlignedBB WNDU_AABB;
   protected AxisAlignedBB NEDU_AABB;
   protected AxisAlignedBB ESDU_AABB;
   protected AxisAlignedBB WUN_AABB;
   protected AxisAlignedBB SUW_AABB;
   protected AxisAlignedBB NUE_AABB;
   protected AxisAlignedBB SUE_AABB;
   protected AxisAlignedBB SDE_AABB;
   protected AxisAlignedBB WDN_AABB;
   protected AxisAlignedBB NDE_AABB;
   protected AxisAlignedBB SDW_AABB;
   protected AxisAlignedBB UN_AABB;
   protected AxisAlignedBB DN_AABB;
   protected AxisAlignedBB US_AABB;
   protected AxisAlignedBB DS_AABB;
   protected AxisAlignedBB UW_AABB;
   protected AxisAlignedBB DW_AABB;
   protected AxisAlignedBB UE_AABB;
   protected AxisAlignedBB DE_AABB;
   protected AxisAlignedBB N_AABB;
   protected AxisAlignedBB S_AABB;
   protected AxisAlignedBB W_AABB;
   protected AxisAlignedBB E_AABB;
   protected AxisAlignedBB U_AABB;
   protected AxisAlignedBB D_AABB;
   public BlocksRegister.Hardres hardres;

   public DecorativePipe(
      Material material,
      String name,
      BlocksRegister.Hardres hardres,
      String tool,
      CreativeTabs tab,
      SoundType soundType,
      float aabbPipeRadius,
      float aabbCenterRadius,
      IPipeConnectorFunction isConnected
   ) {
      super(material);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hardres.HARDNESS;
      this.blockResistance = hardres.RESISTANCE;
      this.setHarvestLevel(tool, hardres.LVL);
      this.hardres = hardres;
      this.setCreativeTab(tab);
      this.setSoundType(soundType);
      this.aabbLow = 0.5F - aabbPipeRadius;
      this.aabbHigh = 0.5F + aabbPipeRadius;
      this.aabbMidLow = 0.5F - aabbCenterRadius;
      this.aabbMidHigh = 0.5F + aabbCenterRadius;
      this.isConnected = isConnected;
      this.mid_AABB = new AxisAlignedBB(this.aabbMidLow, this.aabbMidLow, this.aabbMidLow, this.aabbMidHigh, this.aabbMidHigh, this.aabbMidHigh);
      this.vertical_AABB = new AxisAlignedBB(this.aabbLow, 0.0, this.aabbLow, this.aabbHigh, 1.0, this.aabbHigh);
      this.horizontalSN_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, 0.0, this.aabbHigh, this.aabbHigh, 1.0);
      this.horizontalWE_AABB = new AxisAlignedBB(0.0, this.aabbLow, this.aabbLow, 1.0, this.aabbHigh, this.aabbHigh);
      this.horizontalALL_AABB = new AxisAlignedBB(0.0, this.aabbLow, 0.0, 1.0, this.aabbHigh, 1.0);
      this.SW_AABB = new AxisAlignedBB(0.0, this.aabbLow, this.aabbLow, this.aabbHigh, this.aabbHigh, 1.0);
      this.WN_AABB = new AxisAlignedBB(0.0, this.aabbLow, 0.0, this.aabbHigh, this.aabbHigh, this.aabbHigh);
      this.NE_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, 0.0, 1.0, this.aabbHigh, this.aabbHigh);
      this.ES_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, this.aabbLow, 1.0, this.aabbHigh, 1.0);
      this.SWN_AABB = new AxisAlignedBB(0.0, this.aabbLow, 0.0, this.aabbHigh, this.aabbHigh, 1.0);
      this.WNE_AABB = new AxisAlignedBB(0.0, this.aabbLow, 0.0, 1.0, this.aabbHigh, this.aabbHigh);
      this.NES_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, 0.0, 1.0, this.aabbHigh, 1.0);
      this.ESW_AABB = new AxisAlignedBB(0.0, this.aabbLow, this.aabbLow, 1.0, this.aabbHigh, 1.0);
      this.USD_AABB = new AxisAlignedBB(this.aabbLow, 0.0, this.aabbLow, this.aabbHigh, 1.0, 1.0);
      this.UWD_AABB = new AxisAlignedBB(0.0, 0.0, this.aabbLow, this.aabbHigh, 1.0, this.aabbHigh);
      this.UND_AABB = new AxisAlignedBB(this.aabbLow, 0.0, 0.0, this.aabbHigh, 1.0, this.aabbHigh);
      this.UED_AABB = new AxisAlignedBB(this.aabbLow, 0.0, this.aabbLow, 1.0, 1.0, this.aabbHigh);
      this.S_half_AABB = new AxisAlignedBB(0.0, 0.0, this.aabbLow, 1.0, 1.0, 1.0);
      this.W_half_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, this.aabbHigh, 1.0, 1.0);
      this.N_half_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, this.aabbHigh);
      this.E_half_AABB = new AxisAlignedBB(this.aabbLow, 0.0, 0.0, 1.0, 1.0, 1.0);
      this.U_half_AABB = new AxisAlignedBB(0.0, this.aabbLow, 0.0, 1.0, 1.0, 1.0);
      this.D_half_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, this.aabbHigh, 1.0);
      this.SUND_plate_AABB = new AxisAlignedBB(this.aabbLow, 0.0, 0.0, this.aabbHigh, 1.0, 1.0);
      this.WUED_plate_AABB = new AxisAlignedBB(0.0, 0.0, this.aabbLow, 1.0, 1.0, this.aabbHigh);
      this.SWNU_AABB = new AxisAlignedBB(0.0, this.aabbLow, 0.0, this.aabbHigh, 1.0, 1.0);
      this.WNEU_AABB = new AxisAlignedBB(0.0, this.aabbLow, 0.0, 1.0, 1.0, this.aabbHigh);
      this.NESU_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, 0.0, 1.0, 1.0, 1.0);
      this.ESWU_AABB = new AxisAlignedBB(0.0, this.aabbLow, this.aabbLow, 1.0, 1.0, 1.0);
      this.SWND_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, this.aabbHigh, this.aabbHigh, 1.0);
      this.WNED_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, this.aabbHigh, this.aabbHigh);
      this.NESD_AABB = new AxisAlignedBB(this.aabbLow, 0.0, 0.0, 1.0, this.aabbHigh, 1.0);
      this.ESWD_AABB = new AxisAlignedBB(0.0, 0.0, this.aabbLow, 1.0, this.aabbHigh, 1.0);
      this.SUN_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, 0.0, this.aabbHigh, 1.0, 1.0);
      this.WUE_AABB = new AxisAlignedBB(0.0, this.aabbLow, this.aabbLow, 1.0, 1.0, this.aabbHigh);
      this.SDN_AABB = new AxisAlignedBB(this.aabbLow, 0.0, 0.0, this.aabbHigh, this.aabbHigh, 1.0);
      this.WDE_AABB = new AxisAlignedBB(0.0, 0.0, this.aabbLow, 1.0, this.aabbHigh, this.aabbHigh);
      this.SWDU_AABB = new AxisAlignedBB(0.0, 0.0, this.aabbLow, this.aabbHigh, 1.0, 1.0);
      this.WNDU_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, this.aabbHigh, 1.0, this.aabbHigh);
      this.NEDU_AABB = new AxisAlignedBB(this.aabbLow, 0.0, 0.0, 1.0, 1.0, this.aabbHigh);
      this.ESDU_AABB = new AxisAlignedBB(this.aabbLow, 0.0, this.aabbLow, 1.0, 1.0, 1.0);
      this.WUN_AABB = new AxisAlignedBB(0.0, this.aabbLow, 0.0, this.aabbHigh, 1.0, this.aabbHigh);
      this.SUW_AABB = new AxisAlignedBB(0.0, this.aabbLow, this.aabbLow, this.aabbHigh, 1.0, 1.0);
      this.NUE_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, 0.0, 1.0, 1.0, this.aabbHigh);
      this.SUE_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, this.aabbLow, 1.0, 1.0, 1.0);
      this.SDE_AABB = new AxisAlignedBB(this.aabbLow, 0.0, this.aabbLow, 1.0, this.aabbHigh, 1.0);
      this.WDN_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, this.aabbHigh, this.aabbHigh, this.aabbHigh);
      this.NDE_AABB = new AxisAlignedBB(this.aabbLow, 0.0, 0.0, 1.0, this.aabbHigh, this.aabbHigh);
      this.SDW_AABB = new AxisAlignedBB(0.0, 0.0, this.aabbLow, this.aabbHigh, this.aabbHigh, 1.0);
      this.UN_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, 0.0, this.aabbHigh, 1.0, this.aabbHigh);
      this.DN_AABB = new AxisAlignedBB(this.aabbLow, 0.0, 0.0, this.aabbHigh, this.aabbHigh, this.aabbHigh);
      this.US_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, this.aabbLow, this.aabbHigh, 1.0, 1.0);
      this.DS_AABB = new AxisAlignedBB(this.aabbLow, 0.0, this.aabbLow, this.aabbHigh, this.aabbHigh, 1.0);
      this.UW_AABB = new AxisAlignedBB(0.0, this.aabbLow, this.aabbLow, this.aabbHigh, 1.0, this.aabbHigh);
      this.DW_AABB = new AxisAlignedBB(0.0, 0.0, this.aabbLow, this.aabbHigh, this.aabbHigh, this.aabbHigh);
      this.UE_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, this.aabbLow, 1.0, 1.0, this.aabbHigh);
      this.DE_AABB = new AxisAlignedBB(this.aabbLow, 0.0, this.aabbLow, 1.0, this.aabbHigh, this.aabbHigh);
      this.N_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, 0.0, this.aabbHigh, this.aabbHigh, 0.5);
      this.S_AABB = new AxisAlignedBB(this.aabbLow, this.aabbLow, 0.5, this.aabbHigh, this.aabbHigh, 1.0);
      this.W_AABB = new AxisAlignedBB(0.0, this.aabbLow, this.aabbLow, 0.5, this.aabbHigh, this.aabbHigh);
      this.E_AABB = new AxisAlignedBB(0.5, this.aabbLow, this.aabbLow, 1.0, this.aabbHigh, this.aabbHigh);
      this.U_AABB = new AxisAlignedBB(this.aabbLow, 0.5, this.aabbLow, this.aabbHigh, 1.0, this.aabbHigh);
      this.D_AABB = new AxisAlignedBB(this.aabbLow, 0.0, this.aabbLow, this.aabbHigh, 0.5, this.aabbHigh);
   }

   @Override
   public BlocksRegister.Hardres getHardres() {
      return this.hardres;
   }

   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return false;
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

   public boolean isFullBlock(IBlockState state) {
      return false;
   }

   public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return true;
   }

   public boolean isBlockConnecting(IBlockState state, EnumFacing facing) {
      return this.isConnected.isConnecting(state, facing);
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      BlockPos wb = pos.west();
      BlockPos eb = pos.east();
      BlockPos sb = pos.south();
      BlockPos nb = pos.north();
      BlockPos ub = pos.up();
      BlockPos db = pos.down();
      boolean west = this.isBlockConnecting(worldIn.getBlockState(wb), EnumFacing.WEST);
      boolean east = this.isBlockConnecting(worldIn.getBlockState(eb), EnumFacing.EAST);
      boolean south = this.isBlockConnecting(worldIn.getBlockState(sb), EnumFacing.SOUTH);
      boolean north = this.isBlockConnecting(worldIn.getBlockState(nb), EnumFacing.NORTH);
      boolean up = this.isBlockConnecting(worldIn.getBlockState(ub), EnumFacing.UP);
      boolean down = this.isBlockConnecting(worldIn.getBlockState(db), EnumFacing.DOWN);
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
                     return d ? FULL_BLOCK_AABB : this.U_half_AABB;
                  } else {
                     return d ? this.D_half_AABB : this.horizontalALL_AABB;
                  }
               } else if (u) {
                  return d ? this.W_half_AABB : this.SWNU_AABB;
               } else {
                  return d ? this.SWND_AABB : this.SWN_AABB;
               }
            } else if (e) {
               if (u) {
                  return d ? this.E_half_AABB : this.NESU_AABB;
               } else {
                  return d ? this.NESD_AABB : this.NES_AABB;
               }
            } else if (u) {
               return d ? this.SUND_plate_AABB : this.SUN_AABB;
            } else {
               return d ? this.SDN_AABB : this.horizontalSN_AABB;
            }
         } else if (w) {
            if (e) {
               if (u) {
                  return d ? this.N_half_AABB : this.WNEU_AABB;
               } else {
                  return d ? this.WNED_AABB : this.WNE_AABB;
               }
            } else if (u) {
               return d ? this.WNDU_AABB : this.WUN_AABB;
            } else {
               return d ? this.WDN_AABB : this.WN_AABB;
            }
         } else if (e) {
            if (u) {
               return d ? this.NEDU_AABB : this.NUE_AABB;
            } else {
               return d ? this.NDE_AABB : this.NE_AABB;
            }
         } else if (u) {
            return d ? this.UND_AABB : this.UN_AABB;
         } else {
            return d ? this.DN_AABB : this.N_AABB;
         }
      } else if (s) {
         if (w) {
            if (e) {
               if (u) {
                  return d ? this.S_half_AABB : this.ESWU_AABB;
               } else {
                  return d ? this.ESWD_AABB : this.ESW_AABB;
               }
            } else if (u) {
               return d ? this.SWDU_AABB : this.SUW_AABB;
            } else {
               return d ? this.SDW_AABB : this.SW_AABB;
            }
         } else if (e) {
            if (u) {
               return d ? this.ESDU_AABB : this.SUE_AABB;
            } else {
               return d ? this.SDE_AABB : this.ES_AABB;
            }
         } else if (u) {
            return d ? this.USD_AABB : this.US_AABB;
         } else {
            return d ? this.DS_AABB : this.S_AABB;
         }
      } else if (w) {
         if (e) {
            if (u) {
               return d ? this.WUED_plate_AABB : this.WUE_AABB;
            } else {
               return d ? this.WDE_AABB : this.horizontalWE_AABB;
            }
         } else if (u) {
            return d ? this.UWD_AABB : this.UW_AABB;
         } else {
            return d ? this.DW_AABB : this.W_AABB;
         }
      } else if (e) {
         if (u) {
            return d ? this.UED_AABB : this.UE_AABB;
         } else {
            return d ? this.DE_AABB : this.E_AABB;
         }
      } else if (u) {
         return d ? this.vertical_AABB : this.U_AABB;
      } else {
         return d ? this.D_AABB : this.mid_AABB;
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
      return this.getDefaultState();
   }

   public int getMetaFromState(IBlockState state) {
      return 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, UPPER, DOWN});
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
      boolean west = this.isBlockConnecting(worldIn.getBlockState(wb), EnumFacing.WEST);
      boolean east = this.isBlockConnecting(worldIn.getBlockState(eb), EnumFacing.EAST);
      boolean south = this.isBlockConnecting(worldIn.getBlockState(sb), EnumFacing.SOUTH);
      boolean north = this.isBlockConnecting(worldIn.getBlockState(nb), EnumFacing.NORTH);
      boolean up = this.isBlockConnecting(worldIn.getBlockState(ub), EnumFacing.UP);
      boolean down = this.isBlockConnecting(worldIn.getBlockState(db), EnumFacing.DOWN);
      return this.getDefaultState()
         .withProperty(WEST, west)
         .withProperty(EAST, east)
         .withProperty(NORTH, north)
         .withProperty(SOUTH, south)
         .withProperty(UPPER, up)
         .withProperty(DOWN, down);
   }

   public interface IPipeConnectorFunction {
      boolean isConnecting(IBlockState var1, EnumFacing var2);
   }
}
