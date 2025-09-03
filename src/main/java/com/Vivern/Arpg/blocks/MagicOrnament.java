//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicOrnament extends BlockBlock {
   public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 2);

   public MagicOrnament(Material mater, String name, float hard, float resi) {
      super(mater, name, hard, resi);
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(TYPE, meta);
   }

   public int getMetaFromState(IBlockState state) {
      return (Integer)state.getValue(TYPE);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{TYPE});
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      int type = 0;
      if (facing == EnumFacing.UP) {
         type = 1;
      } else if (facing == EnumFacing.DOWN) {
         type = 2;
      }

      return this.getDefaultState().withProperty(TYPE, type);
   }
}
