package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.container.GUIFrozenPuzzle;
import com.Vivern.Arpg.container.GuiHandler;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.tileentity.TilePuzzle;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPuzzle extends Block implements IBlockHardBreak {
   public BlockPuzzle() {
      super(Material.ROCK);
      this.setRegistryName("block_puzzle");
      this.setTranslationKey("block_puzzle");
      this.blockHardness = BlocksRegister.HR_PUZZLE.HARDNESS;
      this.blockResistance = BlocksRegister.HR_PUZZLE.RESISTANCE;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_PUZZLE.LVL);
      this.setCreativeTab(CreativeTabs.REDSTONE);
   }

   @Override
   public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      TilePuzzle tile = this.getTileEntity(world, pos);
      if (tile != null && tile.causesRedstone && toolLevel >= 3 && state.getBlock().isToolEffective(tool, state)) {
         return originalSpeed * BlocksRegister.HR_PUZZLE.FAST;
      } else {
         return toolLevel >= BlocksRegister.HR_PUZZLE.LVL && state.getBlock().isToolEffective(tool, state)
            ? originalSpeed * BlocksRegister.HR_PUZZLE.FAST
            : originalSpeed * BlocksRegister.HR_PUZZLE.SLOW;
      }
   }

   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
      TilePuzzle tile = this.getTileEntity(worldIn, pos);
      int i = 4;
      if (stack.hasDisplayName()) {
         String name = stack.getDisplayName();

         try {
            i = Integer.parseInt(name);
         } catch (NumberFormatException var10) {
         }
      }

      tile.setupPuzzle(i);
   }

   public boolean canProvidePower(IBlockState state) {
      return true;
   }

   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
      TilePuzzle tile = this.getTileEntity(blockAccess, pos);
      return tile.causesRedstone ? 15 : 0;
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      for (EnumFacing enumfacing : EnumFacing.values()) {
         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
      }
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (worldIn.isRemote) {
         TilePuzzle tile = this.getTileEntity(worldIn, pos);
         if (tile != null) {
            GuiHandler.displayGui(player, new GUIFrozenPuzzle(tile));
            return true;
         }
      }

      return true;
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TilePuzzle tile, int range) {
      for (EntityPlayerMP playerIn : world.getEntitiesWithinAABB(
         EntityPlayerMP.class,
         new AxisAlignedBB(
            pos.getX() + range,
            pos.getY() + range,
            pos.getZ() + range,
            pos.getX() - range,
            pos.getY() - range,
            pos.getZ() - range
         )
      )) {
         SPacketUpdateTileEntity spacketupdatetileentity = tile.getUpdatePacket();
         if (spacketupdatetileentity != null) {
            playerIn.connection.sendPacket(spacketupdatetileentity);
         }
      }
   }

   public Class<TilePuzzle> getTileEntityClass() {
      return TilePuzzle.class;
   }

   public TilePuzzle getTileEntity(IBlockAccess world, BlockPos position) {
      return (TilePuzzle)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TilePuzzle createTileEntity(World world, IBlockState blockState) {
      return new TilePuzzle();
   }
}
