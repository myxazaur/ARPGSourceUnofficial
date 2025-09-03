//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.tileentity.TileElectricSieve;
import javax.annotation.Nullable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockElectricSieve extends BlockSieve {
   public BlockElectricSieve() {
      super(Material.IRON, "electric_sieve");
      this.blockHardness = 3.0F;
      this.blockResistance = 10.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setHarvestLevel("pickaxe", 0);
      this.setSoundType(SoundType.METAL);
   }

   public TileElectricSieve getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileElectricSieve)world.getTileEntity(position);
   }

   @Nullable
   public TileElectricSieve createTileEntity(World world, IBlockState blockState) {
      return new TileElectricSieve();
   }

   @Override
   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      return false;
   }
}
