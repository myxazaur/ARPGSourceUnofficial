package com.vivern.arpg.blocks;

import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.tileentity.TileCombinationLock;
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

public class SeaLock extends Block {
   public SeaLock() {
      super(Material.ROCK);
      this.setRegistryName("sea_lock");
      this.setTranslationKey("sea_lock");
      this.blockHardness = -1.0F;
      this.blockResistance = 99.0F;
      this.setCreativeTab(CreativeTabs.REDSTONE);
   }

   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
      TileCombinationLock tile = this.getTileEntity(worldIn, pos);
      tile.setupQuestion(RANDOM);
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote) {
         TileCombinationLock tile = this.getTileEntity(worldIn, pos);
         if (tile != null) {
            player.openGui(AbstractRPG.instance, 5, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
         }
      }

      return false;
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileCombinationLock tile, int range) {
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

   public Class<TileCombinationLock> getTileEntityClass() {
      return TileCombinationLock.class;
   }

   public TileCombinationLock getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileCombinationLock)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileCombinationLock createTileEntity(World world, IBlockState blockState) {
      return new TileCombinationLock();
   }
}
