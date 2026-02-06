package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.Main;
import com.Vivern.Arpg.tileentity.TileItemCharger;
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

public class BlockItemCharger extends Block {
   public BlockItemCharger() {
      super(Material.IRON);
      this.setRegistryName("block_item_charger");
      this.setTranslationKey("block_item_charger");
      this.blockHardness = 6.5F;
      this.blockResistance = 10.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setSoundType(SoundTypeShards.METAL);
      this.setHarvestLevel("pickaxe", 1);
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote) {
         TileItemCharger tile = this.getTileEntity(worldIn, pos);
         if (tile != null) {
            player.openGui(Main.instance, 12, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
         }
      }

      return true;
   }

   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
      TileItemCharger tile = this.getTileEntity(worldIn, pos);
      tile.rotation = (byte)placer.getHorizontalFacing().getOpposite().getHorizontalIndex();
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileItemCharger tile, int range) {
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

   public Class<TileItemCharger> getTileEntityClass() {
      return TileItemCharger.class;
   }

   public TileItemCharger getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileItemCharger)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileItemCharger createTileEntity(World world, IBlockState blockState) {
      return new TileItemCharger();
   }
}
