package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.tileentity.TileGlowingVein;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GlowingVein extends Block implements IBlockHardBreak {
   public GlowingVein() {
      super(Material.ROCK);
      this.setRegistryName("glowing_vein");
      this.setTranslationKey("glowing_vein");
      this.blockHardness = 8.0F;
      this.blockResistance = 15.0F;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   @Override
   public BlocksRegister.Hardres getHardres() {
      return BlocksRegister.HR_DUNGEON_STONES;
   }

   public Class<TileGlowingVein> getTileEntityClass() {
      return TileGlowingVein.class;
   }

   public TileGlowingVein getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileGlowingVein)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileGlowingVein createTileEntity(World world, IBlockState blockState) {
      return new TileGlowingVein();
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }
}
