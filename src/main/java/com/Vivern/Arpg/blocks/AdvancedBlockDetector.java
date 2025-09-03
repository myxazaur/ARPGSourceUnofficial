//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.tileentity.TileAdvancedBlockDetector;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AdvancedBlockDetector extends Block {
   public static final PropertyDirection FACING = BlockDirectional.FACING;

   public AdvancedBlockDetector() {
      super(Material.ROCK);
      this.setRegistryName("advanced_block_detector");
      this.setTranslationKey("advanced_block_detector");
      this.blockHardness = 1.5F;
      this.blockResistance = 1.5F;
      this.setCreativeTab(CreativeTabs.REDSTONE);
      this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH));
   }

   public Class<TileAdvancedBlockDetector> getTileEntityClass() {
      return TileAdvancedBlockDetector.class;
   }

   public TileAdvancedBlockDetector getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileAdvancedBlockDetector)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileAdvancedBlockDetector createTileEntity(World world, IBlockState blockState) {
      return new TileAdvancedBlockDetector();
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      TileAdvancedBlockDetector tile = this.getTileEntity(worldIn, pos);
      if (tile != null) {
         if (playerIn.getHeldItem(hand).isEmpty() && !playerIn.isSneaking()) {
            tile.detectOnlyBlock = !tile.detectOnlyBlock;
            playerIn.swingArm(hand);
            worldIn.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.7F, 1.15F);
            if (playerIn instanceof EntityPlayerMP) {
               playerIn.sendMessage(new TextComponentString(tile.getChatMessage()));
            }

            return true;
         }

         if (playerIn.getHeldItem(hand).isEmpty() && playerIn.isSneaking()) {
            BlockPos offset = pos.offset((EnumFacing)state.getValue(FACING));
            tile.setState(worldIn.getBlockState(offset));
            playerIn.swingArm(hand);
            worldIn.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.7F, 1.15F);
            if (playerIn instanceof EntityPlayerMP) {
               playerIn.sendMessage(new TextComponentString(tile.getChatMessage()));
            }

            return true;
         }

         if (playerIn.getHeldItem(hand).getItem() instanceof ItemBlock) {
            tile.setState(((ItemBlock)playerIn.getHeldItem(hand).getItem()).getBlock().getStateFromMeta(playerIn.getHeldItem(hand).getItemDamage()));
            playerIn.swingArm(hand);
            worldIn.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.7F, 1.15F);
            if (playerIn instanceof EntityPlayerMP) {
               playerIn.sendMessage(new TextComponentString(tile.getChatMessage()));
            }

            return true;
         }

         if (playerIn instanceof EntityPlayerMP) {
            playerIn.sendMessage(new TextComponentString(tile.getChatMessage()));
         }
      }

      return false;
   }

   public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
      super.onNeighborChange(world, pos, neighbor);
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      BlockPos offset = pos.offset(((EnumFacing)state.getValue(FACING)).getOpposite());
      worldIn.neighborChanged(offset, worldIn.getBlockState(offset).getBlock(), pos);
   }

   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
      if (blockState.getValue(FACING) == side) {
         TileAdvancedBlockDetector tile = (TileAdvancedBlockDetector)blockAccess.getTileEntity(pos);
         if (tile.state == null) {
            return 0;
         }

         IBlockState scanned = blockAccess.getBlockState(pos.offset(side));
         if (!(scanned.getBlock() instanceof BlockLiquid)) {
            if (!tile.detectOnlyBlock) {
               return scanned.equals(tile.state) ? 15 : 0;
            }

            return scanned.getBlock() == tile.state.getBlock() ? 15 : 0;
         }

         BlockLiquid var10000 = (BlockLiquid)scanned.getBlock();
         BlockStaticLiquid blocklS = BlockLiquid.getStaticBlock(scanned.getMaterial());
         var10000 = (BlockLiquid)scanned.getBlock();
         BlockDynamicLiquid blocklD = BlockLiquid.getFlowingBlock(scanned.getMaterial());
         if (tile.detectOnlyBlock) {
            return blocklS != tile.state.getBlock() && blocklD != tile.state.getBlock() ? 0 : 15;
         }

         if (blocklS == tile.state.getBlock()) {
            return blocklS.getMetaFromState(tile.state) == blocklS.getMetaFromState(scanned) ? 15 : 0;
         }

         if (blocklD == tile.state.getBlock()) {
            return blocklD.getMetaFromState(tile.state) == blocklD.getMetaFromState(scanned) ? 15 : 0;
         }
      }

      return 0;
   }

   public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
      return blockState.getWeakPower(blockAccess, pos, side);
   }

   public boolean canProvidePower(IBlockState state) {
      return true;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
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
}
