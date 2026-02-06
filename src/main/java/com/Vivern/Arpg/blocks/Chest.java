package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.elements.models.ChestCapedModel;
import com.Vivern.Arpg.elements.models.ChestDoubleLockModel;
import com.Vivern.Arpg.elements.models.ChestDoubleLockModelLAR;
import com.Vivern.Arpg.elements.models.ChestModel;
import com.Vivern.Arpg.elements.models.ChestModelLAR;
import com.Vivern.Arpg.elements.models.ChestShaftedModel;
import com.Vivern.Arpg.tileentity.EnumChest;
import com.Vivern.Arpg.tileentity.TileChest;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Chest extends BlockContainer {
   public static final PropertyDirection FACING = BlockHorizontal.FACING;
   protected static final AxisAlignedBB NORTH_CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0, 0.9375, 0.875, 0.9375);
   protected static final AxisAlignedBB SOUTH_CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 1.0);
   protected static final AxisAlignedBB WEST_CHEST_AABB = new AxisAlignedBB(0.0, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
   protected static final AxisAlignedBB EAST_CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 1.0, 0.875, 0.9375);
   public static final AxisAlignedBB NOT_CONNECTED_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
   public final EnumChest chestType;
   public static ModelChest simpleChest = new ChestModel();
   public static ModelChest simpleChestLAR = new ChestModelLAR();
   public static ChestCapedModel capedChest = new ChestCapedModel();
   public static ModelChest doubleLockChest = new ChestDoubleLockModel();
   public static ModelChest doubleLockChestLAR = new ChestDoubleLockModelLAR();
   public static ChestShaftedModel shaftedChest = new ChestShaftedModel();
   public static ChestShaftedModel shaftedChestGlow = new ChestShaftedModel().setglow();

   public Chest(Material mater, String name, float hard, float resi, SoundType stype, String tool, int harvestlvl, EnumChest chestType) {
      super(mater);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setSoundType(stype);
      this.setHarvestLevel(tool, harvestlvl);
      this.chestType = chestType;
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileChest tile) {
      int range = 64;

      for (EntityPlayerMP playerIn : world.getEntitiesWithinAABB(
         EntityPlayerMP.class,
         new AxisAlignedBB(
            pos.getX() + 64,
            pos.getY() + 64,
            pos.getZ() + 64,
            pos.getX() - 64,
            pos.getY() - 64,
            pos.getZ() - 64
         )
      )) {
         SPacketUpdateTileEntity spacketupdatetileentity = tile.getUpdatePacket();
         if (spacketupdatetileentity != null) {
            playerIn.connection.sendPacket(spacketupdatetileentity);
         }
      }
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public boolean hasCustomBreakingProgress(IBlockState state) {
      return true;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      if (source.getBlockState(pos.north()).getBlock() == this) {
         return NORTH_CHEST_AABB;
      } else if (source.getBlockState(pos.south()).getBlock() == this) {
         return SOUTH_CHEST_AABB;
      } else if (source.getBlockState(pos.west()).getBlock() == this) {
         return WEST_CHEST_AABB;
      } else {
         return source.getBlockState(pos.east()).getBlock() == this ? EAST_CHEST_AABB : NOT_CONNECTED_AABB;
      }
   }

   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
      this.checkForSurroundingChests(worldIn, pos, state);

      for (EnumFacing enumfacing : Plane.HORIZONTAL) {
         BlockPos blockpos = pos.offset(enumfacing);
         IBlockState iblockstate = worldIn.getBlockState(blockpos);
         if (iblockstate.getBlock() == this) {
            this.checkForSurroundingChests(worldIn, blockpos, iblockstate);
         }
      }
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
   }

   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      EnumFacing enumfacing = EnumFacing.byHorizontalIndex(MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5) & 3).getOpposite();
      state = state.withProperty(FACING, enumfacing);
      BlockPos blockpos = pos.north();
      BlockPos blockpos1 = pos.south();
      BlockPos blockpos2 = pos.west();
      BlockPos blockpos3 = pos.east();
      boolean flag = this == worldIn.getBlockState(blockpos).getBlock();
      boolean flag1 = this == worldIn.getBlockState(blockpos1).getBlock();
      boolean flag2 = this == worldIn.getBlockState(blockpos2).getBlock();
      boolean flag3 = this == worldIn.getBlockState(blockpos3).getBlock();
      if (!flag && !flag1 && !flag2 && !flag3) {
         worldIn.setBlockState(pos, state, 3);
      } else if (enumfacing.getAxis() != Axis.X || !flag && !flag1) {
         if (enumfacing.getAxis() == Axis.Z && (flag2 || flag3)) {
            if (flag2) {
               worldIn.setBlockState(blockpos2, state, 3);
            } else {
               worldIn.setBlockState(blockpos3, state, 3);
            }

            worldIn.setBlockState(pos, state, 3);
         }
      } else {
         if (flag) {
            worldIn.setBlockState(blockpos, state, 3);
         } else {
            worldIn.setBlockState(blockpos1, state, 3);
         }

         worldIn.setBlockState(pos, state, 3);
      }

      if (stack.hasDisplayName()) {
         TileEntity tileentity = worldIn.getTileEntity(pos);
         if (tileentity instanceof TileChest) {
            ((TileChest)tileentity).setCustomName(stack.getDisplayName());
         }
      }
   }

   public IBlockState checkForSurroundingChests(World worldIn, BlockPos pos, IBlockState state) {
      if (worldIn.isRemote) {
         return state;
      } else {
         IBlockState iblockstate = worldIn.getBlockState(pos.north());
         IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
         IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
         IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
         EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
         if (iblockstate.getBlock() != this && iblockstate1.getBlock() != this) {
            boolean flag = iblockstate.isFullBlock();
            boolean flag1 = iblockstate1.isFullBlock();
            if (iblockstate2.getBlock() == this || iblockstate3.getBlock() == this) {
               BlockPos blockpos1 = iblockstate2.getBlock() == this ? pos.west() : pos.east();
               IBlockState iblockstate7 = worldIn.getBlockState(blockpos1.north());
               IBlockState iblockstate6 = worldIn.getBlockState(blockpos1.south());
               enumfacing = EnumFacing.SOUTH;
               EnumFacing enumfacing2;
               if (iblockstate2.getBlock() == this) {
                  enumfacing2 = (EnumFacing)iblockstate2.getValue(FACING);
               } else {
                  enumfacing2 = (EnumFacing)iblockstate3.getValue(FACING);
               }

               if (enumfacing2 == EnumFacing.NORTH) {
                  enumfacing = EnumFacing.NORTH;
               }

               if ((flag || iblockstate7.isFullBlock()) && !flag1 && !iblockstate6.isFullBlock()) {
                  enumfacing = EnumFacing.SOUTH;
               }

               if ((flag1 || iblockstate6.isFullBlock()) && !flag && !iblockstate7.isFullBlock()) {
                  enumfacing = EnumFacing.NORTH;
               }
            }
         } else {
            BlockPos blockpos = iblockstate.getBlock() == this ? pos.north() : pos.south();
            IBlockState iblockstate4 = worldIn.getBlockState(blockpos.west());
            IBlockState iblockstate5 = worldIn.getBlockState(blockpos.east());
            enumfacing = EnumFacing.EAST;
            EnumFacing enumfacing1;
            if (iblockstate.getBlock() == this) {
               enumfacing1 = (EnumFacing)iblockstate.getValue(FACING);
            } else {
               enumfacing1 = (EnumFacing)iblockstate1.getValue(FACING);
            }

            if (enumfacing1 == EnumFacing.WEST) {
               enumfacing = EnumFacing.WEST;
            }

            if ((iblockstate2.isFullBlock() || iblockstate4.isFullBlock()) && !iblockstate3.isFullBlock() && !iblockstate5.isFullBlock()) {
               enumfacing = EnumFacing.EAST;
            }

            if ((iblockstate3.isFullBlock() || iblockstate5.isFullBlock()) && !iblockstate2.isFullBlock() && !iblockstate4.isFullBlock()) {
               enumfacing = EnumFacing.WEST;
            }
         }

         state = state.withProperty(FACING, enumfacing);
         worldIn.setBlockState(pos, state, 3);
         return state;
      }
   }

   public IBlockState correctFacing(World worldIn, BlockPos pos, IBlockState state) {
      EnumFacing enumfacing = null;

      for (EnumFacing enumfacing1 : Plane.HORIZONTAL) {
         IBlockState iblockstate = worldIn.getBlockState(pos.offset(enumfacing1));
         if (iblockstate.getBlock() == this) {
            return state;
         }

         if (iblockstate.isFullBlock()) {
            if (enumfacing != null) {
               enumfacing = null;
               break;
            }

            enumfacing = enumfacing1;
         }
      }

      if (enumfacing != null) {
         return state.withProperty(FACING, enumfacing.getOpposite());
      } else {
         EnumFacing enumfacing2 = (EnumFacing)state.getValue(FACING);
         if (worldIn.getBlockState(pos.offset(enumfacing2)).isFullBlock()) {
            enumfacing2 = enumfacing2.getOpposite();
         }

         if (worldIn.getBlockState(pos.offset(enumfacing2)).isFullBlock()) {
            enumfacing2 = enumfacing2.rotateY();
         }

         if (worldIn.getBlockState(pos.offset(enumfacing2)).isFullBlock()) {
            enumfacing2 = enumfacing2.getOpposite();
         }

         return state.withProperty(FACING, enumfacing2);
      }
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      int i = 0;
      BlockPos blockpos = pos.west();
      BlockPos blockpos1 = pos.east();
      BlockPos blockpos2 = pos.north();
      BlockPos blockpos3 = pos.south();
      if (worldIn.getBlockState(blockpos).getBlock() == this) {
         if (this.isDoubleChest(worldIn, blockpos)) {
            return false;
         }

         i++;
      }

      if (worldIn.getBlockState(blockpos1).getBlock() == this) {
         if (this.isDoubleChest(worldIn, blockpos1)) {
            return false;
         }

         i++;
      }

      if (worldIn.getBlockState(blockpos2).getBlock() == this) {
         if (this.isDoubleChest(worldIn, blockpos2)) {
            return false;
         }

         i++;
      }

      if (worldIn.getBlockState(blockpos3).getBlock() == this) {
         if (this.isDoubleChest(worldIn, blockpos3)) {
            return false;
         }

         i++;
      }

      return i <= 1;
   }

   private boolean isDoubleChest(World worldIn, BlockPos pos) {
      if (worldIn.getBlockState(pos).getBlock() != this) {
         return false;
      } else {
         for (EnumFacing enumfacing : Plane.HORIZONTAL) {
            if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this) {
               return true;
            }
         }

         return false;
      }
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof TileChest) {
         tileentity.updateContainingBlockInfo();
      }
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof IInventory) {
         InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
         worldIn.updateComparatorOutputLevel(pos, this);
      }

      super.breakBlock(worldIn, pos, state);
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      boolean ret = true;
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof TileChest) {
         TileChest tileChest = (TileChest)tileentity;
         ret = tileChest.onTryOpenChest(playerIn, RANDOM);
      }

      if (worldIn.isRemote) {
         return ret;
      } else {
         ILockableContainer ilockablecontainer = this.getLockableContainer(worldIn, pos);
         if (ilockablecontainer != null) {
            playerIn.displayGUIChest(ilockablecontainer);
         }

         return ret;
      }
   }

   @Nullable
   public ILockableContainer getLockableContainer(World worldIn, BlockPos pos) {
      return this.getContainer(worldIn, pos, false);
   }

   @Nullable
   public ILockableContainer getContainer(World worldIn, BlockPos pos, boolean allowBlocking) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (!(tileentity instanceof TileChest)) {
         return null;
      } else {
         ILockableContainer ilockablecontainer = (TileChest)tileentity;
         if (((TileChest)tileentity).isLocked()) {
            return null;
         } else if (!allowBlocking && this.isBlocked(worldIn, pos)) {
            return null;
         } else {
            for (EnumFacing enumfacing : Plane.HORIZONTAL) {
               BlockPos blockpos = pos.offset(enumfacing);
               Block block = worldIn.getBlockState(blockpos).getBlock();
               if (block == this) {
                  if (!allowBlocking && this.isBlocked(worldIn, blockpos)) {
                     return null;
                  }

                  TileEntity tileentity1 = worldIn.getTileEntity(blockpos);
                  if (tileentity1 instanceof TileChest) {
                     if (enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH) {
                        ilockablecontainer = new InventoryLargeChest("container.chestDouble", ilockablecontainer, (TileChest)tileentity1);
                     } else {
                        ilockablecontainer = new InventoryLargeChest("container.chestDouble", (TileChest)tileentity1, ilockablecontainer);
                     }
                  }
               }
            }

            return ilockablecontainer;
         }
      }
   }

   public TileEntity createNewTileEntity(World worldIn, int meta) {
      TileChest tchest = new TileChest();
      tchest.type = this.chestType;
      return tchest;
   }

   public boolean isBlocked(World worldIn, BlockPos pos) {
      return this.isBelowSolidBlock(worldIn, pos) || this.isOcelotSittingOnChest(worldIn, pos);
   }

   public boolean isBelowSolidBlock(World worldIn, BlockPos pos) {
      return worldIn.getBlockState(pos.up()).doesSideBlockChestOpening(worldIn, pos.up(), EnumFacing.DOWN);
   }

   public boolean isOcelotSittingOnChest(World worldIn, BlockPos pos) {
      for (Entity entity : worldIn.getEntitiesWithinAABB(
         EntityOcelot.class,
         new AxisAlignedBB(
            pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1
         )
      )) {
         EntityOcelot entityocelot = (EntityOcelot)entity;
         if (entityocelot.isSitting()) {
            return true;
         }
      }

      return false;
   }

   public boolean hasComparatorInputOverride(IBlockState state) {
      return true;
   }

   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
      return Container.calcRedstoneFromInventory(this.getLockableContainer(worldIn, pos));
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

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
      return !this.isDoubleChest(world, pos) && super.rotateBlock(world, pos, axis);
   }
}
