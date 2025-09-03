//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.mobs.EverfrostMobsPack;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NiveousHole extends Block implements IBlockHardBreak {
   public NiveousHole() {
      super(Material.ROCK);
      this.setRegistryName("niveous_hole");
      this.setTranslationKey("niveous_hole");
      this.blockHardness = BlocksRegister.HR_NIVEOUS_HALL.HARDNESS;
      this.blockResistance = BlocksRegister.HR_NIVEOUS_HALL.RESISTANCE;
      this.setCreativeTab(CreativeTabs.REDSTONE);
      this.slipperiness = 0.999F;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_NIVEOUS_HALL.LVL);
   }

   public void onLanded(World worldIn, Entity entityIn) {
      if (!worldIn.isRemote && entityIn instanceof EverfrostMobsPack.NiveousSlider) {
         ((EverfrostMobsPack.NiveousSlider)entityIn).onFallToHole();
      }
   }

   @Override
   public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      return BlocksRegister.HR_NIVEOUS_HALL.getBlockBreakingSpeed(world, tool, toolLevel, state, pos, originalSpeed);
   }
}
