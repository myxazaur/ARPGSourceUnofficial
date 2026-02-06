package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.Main;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.tileentity.TileCrystallizer;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Crystallizer extends Block {
   public Crystallizer() {
      super(Material.GLASS);
      this.setRegistryName("geomantic_crystallizer");
      this.setTranslationKey("geomantic_crystallizer");
      this.blockHardness = 4.5F;
      this.blockResistance = 16.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setSoundType(SoundType.GLASS);
      this.setHarvestLevel("pickaxe", 0);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote) {
         TileCrystallizer tile = this.getTileEntity(worldIn, pos);
         worldIn.playSound((EntityPlayer)null, pos, Sounds.vessel_hit, SoundCategory.PLAYERS, 0.5F, 0.9F + RANDOM.nextFloat() / 5.0F);
         if (tile != null) {
            player.openGui(Main.instance, 7, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
         }
      }

      return false;
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileCrystallizer tile, int range) {
      if (!world.isRemote) {
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
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
      if (worldIn.getTileEntity(pos) instanceof TileCrystallizer) {
         TileCrystallizer tileentity = (TileCrystallizer)worldIn.getTileEntity(pos);
         if (flag) {
            tileentity.redstone++;
         }

         if (!flag) {
            tileentity.redstone = 0;
         }
      }
   }

   public Class<TileCrystallizer> getTileEntityClass() {
      return TileCrystallizer.class;
   }

   public TileCrystallizer getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileCrystallizer)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileCrystallizer createTileEntity(World world, IBlockState blockState) {
      return new TileCrystallizer();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }
}
