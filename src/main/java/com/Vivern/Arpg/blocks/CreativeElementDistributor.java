package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.Main;
import com.Vivern.Arpg.tileentity.TileElementDistributor;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CreativeElementDistributor extends Block {
   public CreativeElementDistributor() {
      super(Material.CIRCUITS);
      this.setRegistryName("creative_element_distributor");
      this.setTranslationKey("creative_element_distributor");
      this.blockHardness = 10.0F;
      this.blockResistance = 10.0F;
      this.setCreativeTab(CreativeTabs.MISC);
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote) {
         TileElementDistributor tile = this.getTileEntity(worldIn, pos);
         if (tile != null) {
            player.openGui(Main.instance, 15, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
         }
      }

      return false;
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileElementDistributor tile) {
      int range = 8;

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

   public Class<TileElementDistributor> getTileEntityClass() {
      return TileElementDistributor.class;
   }

   public TileElementDistributor getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileElementDistributor)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileElementDistributor createTileEntity(World world, IBlockState blockState) {
      return new TileElementDistributor();
   }
}
