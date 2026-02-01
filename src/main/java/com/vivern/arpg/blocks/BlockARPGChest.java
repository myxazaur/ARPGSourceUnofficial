package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.tileentity.EnumChest;
import com.vivern.arpg.tileentity.TileARPGChest;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockARPGChest extends BlockBlockHard {
   protected static final AxisAlignedBB NORTH_CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0, 0.9375, 0.875, 0.9375);
   protected static final AxisAlignedBB SOUTH_CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 1.0);
   protected static final AxisAlignedBB WEST_CHEST_AABB = new AxisAlignedBB(0.0, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
   protected static final AxisAlignedBB EAST_CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 1.0, 0.875, 0.9375);
   public static final AxisAlignedBB NOT_CONNECTED_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
   public final EnumChest chestType;

   public BlockARPGChest(Material mater, String name, BlocksRegister.Hardres hardres, String tool, SoundType stype, EnumChest chestType) {
      super(mater, name, hardres, tool, true);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(stype);
      this.chestType = chestType;
      this.setOpacity(0);
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!world.isRemote) {
         TileEntity tileentity = world.getTileEntity(pos);
         if (tileentity instanceof TileARPGChest) {
            TileARPGChest tileChest = (TileARPGChest)tileentity;
            if (world.getBlockState(fromPos).getBlock() != this) {
               TileARPGChest.tryRemoveDoubleChest(tileChest);
            }
         }
      }
   }

   public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
      super.eventReceived(state, worldIn, pos, id, param);
      TileEntity tileentity = worldIn.getTileEntity(pos);
      return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
   }

   @Override
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack, EnumFacing facing) {
      if (!world.isRemote) {
         TileEntity tileentity = world.getTileEntity(pos);
         if (tileentity instanceof TileARPGChest) {
            TileARPGChest tileChest = (TileARPGChest)tileentity;
            tileChest.onPlace(placer, facing);
         }
      }
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileARPGChest createTileEntity(World world, IBlockState blockState) {
      TileARPGChest tchest = new TileARPGChest();
      tchest.type = this.chestType;
      return tchest;
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
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

   @Override
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

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof IInventory) {
         InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
         worldIn.updateComparatorOutputLevel(pos, this);
      }

      super.breakBlock(worldIn, pos, state);
   }

   public boolean onBlockActivated(
      World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!world.isRemote) {
         TileEntity tileentity = world.getTileEntity(pos);
         if (tileentity instanceof TileARPGChest) {
            TileARPGChest tileChest = (TileARPGChest)tileentity;
            if (tileChest.canOpen()) {
               IInventory iInventory = tileChest.getOpenedInventory();
               playerIn.displayGUIChest(iInventory);
            }
         }
      }

      return true;
   }

   public boolean hasComparatorInputOverride(IBlockState state) {
      return true;
   }

   public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
      TileEntity tileentity = world.getTileEntity(pos);
      if (tileentity instanceof TileARPGChest) {
         TileARPGChest tileChest = (TileARPGChest)tileentity;
         if (tileChest.canOpen()) {
            return Container.calcRedstoneFromInventory(tileChest.getOpenedInventory());
         }
      }

      return 0;
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }
}
