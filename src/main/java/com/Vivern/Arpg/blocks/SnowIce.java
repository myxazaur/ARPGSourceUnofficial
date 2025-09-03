//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SnowIce extends Block implements IBlockHardBreak {
   public SnowIce() {
      super(Material.GOURD);
      this.setRegistryName("snow_ice");
      this.setTranslationKey("snow_ice");
      this.blockHardness = BlocksRegister.HR_SNOWICE_GLACIER.HARDNESS;
      this.blockResistance = BlocksRegister.HR_SNOWICE_GLACIER.RESISTANCE;
      this.setHarvestLevel("shovel", BlocksRegister.HR_SNOWICE_GLACIER.LVL);
      this.setSoundType(SoundTypeShards.SHARDS);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   @Override
   public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      return BlocksRegister.HR_SNOWICE_GLACIER.getBlockBreakingSpeed(world, tool, toolLevel, state, pos, originalSpeed);
   }
}
