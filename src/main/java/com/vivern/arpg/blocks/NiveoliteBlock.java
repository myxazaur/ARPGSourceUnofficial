package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NiveoliteBlock extends Block implements IBlockHardBreak {
   public NiveoliteBlock() {
      super(Material.ROCK);
      this.setRegistryName("niveolite_block");
      this.setTranslationKey("niveolite_block");
      this.blockHardness = BlocksRegister.HR_NIVEOLITE.HARDNESS;
      this.blockResistance = BlocksRegister.HR_NIVEOLITE.RESISTANCE;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_NIVEOLITE.LVL);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   public EnumPushReaction getPushReaction(IBlockState state) {
      return EnumPushReaction.IGNORE;
   }

   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return false;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      boolean drop = true;

      for (EnumFacing face : EnumFacing.VALUES) {
         IBlockState stat = worldIn.getBlockState(pos.offset(face));
         if (stat.isFullCube()) {
            drop = false;
            break;
         }
      }

      if (drop) {
         worldIn.destroyBlock(pos, false);
         if (RANDOM.nextFloat() < 0.5) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.NIVEOLITE));
            if (RANDOM.nextFloat() < 0.85) {
               spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.NIVEOLITE));
            }

            if (RANDOM.nextFloat() < 0.3) {
               spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.NIVEOLITE));
            }
         } else {
            spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this)));
         }
      }
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   @Override
   public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      return BlocksRegister.HR_NIVEOLITE.getBlockBreakingSpeed(world, tool, toolLevel, state, pos, originalSpeed);
   }
}
