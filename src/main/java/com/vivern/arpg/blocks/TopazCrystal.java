package com.vivern.arpg.blocks;

import com.vivern.arpg.tileentity.TileTopazCrystal;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TopazCrystal extends Block {
   public static final AxisAlignedBB ALL_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);

   public TopazCrystal() {
      super(Material.GLASS);
      this.setRegistryName("topaz_crystal");
      this.setTranslationKey("topaz_crystal");
      this.blockHardness = 4.5F;
      this.blockResistance = 10.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setLightLevel(0.5F);
      this.setSoundType(SoundTypeShards.SHARDS);
      this.setHarvestLevel("pickaxe", 1);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return ALL_AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return ALL_AABB;
   }

   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (rand.nextFloat() < 0.4F) {
         TileTopazCrystal tile = this.getTileEntity(worldIn, pos);
         if (tile != null && tile.givePos != null) {
         }
      }
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileTopazCrystal tile) {
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

   public Class<TileTopazCrystal> getTileEntityClass() {
      return TileTopazCrystal.class;
   }

   public TileTopazCrystal getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileTopazCrystal)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileTopazCrystal createTileEntity(World world, IBlockState blockState) {
      return new TileTopazCrystal();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }
}
