//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.container.GUIDebugColorBlock;
import com.Vivern.Arpg.container.GuiHandler;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DebugColorBlock extends Block {
   public DebugColorBlock() {
      super(Material.ROCK);
      this.setRegistryName("debug_color_block");
      this.setTranslationKey("debug_color_block");
      this.blockHardness = 10.0F;
      this.blockResistance = 10.0F;
      this.setCreativeTab(CreativeTabs.REDSTONE);
   }

   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return 0;
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (worldIn.isRemote) {
         GuiHandler.displayGui(player, new GUIDebugColorBlock(pos));
      }

      return true;
   }
}
