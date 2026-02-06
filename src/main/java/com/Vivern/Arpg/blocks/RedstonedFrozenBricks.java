package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class RedstonedFrozenBricks extends Block implements IBlockHardBreak {
   public RedstonedFrozenBricks() {
      super(Material.ROCK);
      this.setRegistryName("crackable_frozen_bricks");
      this.setTranslationKey("crackable_frozen_bricks");
      this.blockHardness = BlocksRegister.HR_FROZEN_BRICK.HARDNESS;
      this.blockResistance = BlocksRegister.HR_FROZEN_BRICK.RESISTANCE;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_FROZEN_BRICK.LVL);
      this.setCreativeTab(CreativeTabs.REDSTONE);
   }

   @Override
   public BlocksRegister.Hardres getHardres() {
      return BlocksRegister.HR_FROZEN_BRICK;
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      Block blockk = worldIn.getBlockState(pos.down()).getBlock();
      if (worldIn.isBlockPowered(pos.down()) && blockk != BlocksRegister.REDSTONEDFROZENBRICKS && blockk != BlocksRegister.FROZENBRICKS) {
         this.crackBlock(worldIn, pos, EnumFacing.DOWN);
      }
   }

   public void crackBlock(World worldIn, BlockPos pos, EnumFacing from) {
      worldIn.setBlockToAir(pos);
      worldIn.playSound(
         null,
         pos.getX(),
         pos.getY(),
         pos.getZ(),
         worldIn.rand.nextFloat() < 0.6 ? SoundEvents.BLOCK_STONE_BREAK : SoundEvents.BLOCK_GRAVEL_BREAK,
         SoundCategory.BLOCKS,
         1.0F,
         0.85F + worldIn.rand.nextFloat() / 4.0F
      );
      if (worldIn instanceof WorldServer) {
         ((WorldServer)worldIn)
            .spawnParticle(
               EnumParticleTypes.BLOCK_DUST,
               pos.getX(),
               pos.getY(),
               pos.getZ(),
               15,
               -0.5,
               -0.5,
               -0.5,
               0.08,
               new int[]{Block.getStateId(BlocksRegister.REDSTONEDFROZENBRICKS.getDefaultState())}
            );
      }

      for (EnumFacing f : EnumFacing.VALUES) {
         if (f != from) {
            BlockPos pos2 = pos.offset(f);
            if (worldIn.getBlockState(pos2).getBlock() == BlocksRegister.REDSTONEDFROZENBRICKS) {
               this.crackBlock(worldIn, pos2, f.getOpposite());
            }
         }
      }
   }
}
