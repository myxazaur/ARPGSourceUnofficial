package com.Vivern.Arpg.dimensions.dungeon;

import com.Vivern.Arpg.blocks.Pilaster;
import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

public class DecorReplacerDungeon implements ITemplateProcessor {
   public DimensionDungeon.EnumCaveDecorType mode = DimensionDungeon.EnumCaveDecorType.NORMAL;
   public IBlockState ore;

   public void randomizeOre(Random rand) {
      int i = rand.nextInt(21);
      if (i <= 2) {
         this.ore = BlocksRegister.ORERHINESTONE.getDefaultState();
      } else if (i <= 4) {
         this.ore = BlocksRegister.OREAMETHYST.getDefaultState();
      } else if (i <= 6) {
         this.ore = BlocksRegister.ORETOPAZ.getDefaultState();
      } else if (i <= 7) {
         this.ore = BlocksRegister.ORESAPPHIRE.getDefaultState();
      } else if (i <= 8) {
         this.ore = BlocksRegister.ORERUBY.getDefaultState();
      } else if (i <= 9) {
         this.ore = BlocksRegister.ORECITRINE.getDefaultState();
      } else if (i <= 10) {
         this.ore = Blocks.EMERALD_ORE.getDefaultState();
      } else if (i <= 11) {
         this.ore = Blocks.DIAMOND_ORE.getDefaultState();
      } else if (i <= 12) {
         this.ore = BlocksRegister.OREADAMANTIUM.getDefaultState();
      } else if (i <= 15) {
         this.ore = Blocks.COAL_ORE.getDefaultState();
      } else if (i <= 17) {
         this.ore = Blocks.GOLD_ORE.getDefaultState();
      } else if (i <= 18) {
         this.ore = Blocks.IRON_ORE.getDefaultState();
      } else if (i <= 19) {
         this.ore = Blocks.REDSTONE_ORE.getDefaultState();
      } else if (i <= 20) {
         this.ore = BlocksRegister.ORECHROMIUM.getDefaultState();
      }
   }

   public BlockInfo processBlock(World world, BlockPos pos, BlockInfo blockInfoIn) {
      if (this.mode == DimensionDungeon.EnumCaveDecorType.MAGIC) {
         if (blockInfoIn.blockState.getBlock() == BlocksRegister.SELENITE) {
            world.setBlockState(pos, BlocksRegister.MAGICSTONE.getDefaultState());
            return null;
         }

         if (blockInfoIn.blockState.getBlock() == BlocksRegister.SELENITECRYSTALS) {
            world.setBlockState(pos, BlocksRegister.CAVECRYSTALS.getDefaultState());
            return null;
         }
      } else if (this.mode == DimensionDungeon.EnumCaveDecorType.GLOWING) {
         if (blockInfoIn.blockState.getBlock() == Blocks.STONE || blockInfoIn.blockState.getBlock() == Blocks.BONE_BLOCK) {
            world.setBlockState(pos, BlocksRegister.DOLERITE.getDefaultState());
            return null;
         }

         if (blockInfoIn.blockState.getBlock() == Blocks.COBBLESTONE) {
            world.setBlockState(pos, BlocksRegister.DEEPROCK.getDefaultState());
            return null;
         }

         if (blockInfoIn.blockState.getBlock() == Blocks.STONEBRICK) {
            if (Blocks.STONEBRICK.getMetaFromState(blockInfoIn.blockState) == 3) {
               world.setBlockState(pos, BlocksRegister.DOLERITECOLUMN.getDefaultState());
            } else {
               world.setBlockState(pos, BlocksRegister.DOLERITEBRICKS.getDefaultState());
            }

            return null;
         }

         if (blockInfoIn.blockState.getBlock() == Blocks.STONE_SLAB) {
            if (blockInfoIn.blockState.getValue(BlockSlab.HALF) == EnumBlockHalf.TOP) {
               world.setBlockState(pos, BlocksRegister.DOLERITEPILASTER.getDefaultState().withProperty(Pilaster.FACING, EnumFacing.DOWN));
            }

            if (blockInfoIn.blockState.getValue(BlockSlab.HALF) == EnumBlockHalf.BOTTOM) {
               world.setBlockState(pos, BlocksRegister.DOLERITEPILASTER.getDefaultState().withProperty(Pilaster.FACING, EnumFacing.UP));
            }

            return null;
         }

         if (blockInfoIn.blockState.getBlock() == BlocksRegister.SELENITE) {
            world.setBlockState(pos, BlocksRegister.GLOWINGVEIN.getDefaultState());
            return null;
         }

         if (blockInfoIn.blockState.getBlock() == BlocksRegister.SELENITECRYSTALS) {
            world.setBlockState(pos, BlocksRegister.GLOWINGCAVECRYSTALS.getDefaultState());
            return null;
         }

         if (blockInfoIn.blockState.getBlock() == Blocks.COBBLESTONE_WALL) {
            world.setBlockState(pos, BlocksRegister.DOLERITECOLUMN.getDefaultState());
            return null;
         }

         if (blockInfoIn.blockState.getBlock() == BlocksRegister.GREENGLOWINGMUSH) {
            if (world.rand.nextFloat() < 0.95) {
               return new BlockInfo(
                  blockInfoIn.pos,
                  BlocksRegister.BLUEGLOWINGMUSH.getStateFromMeta(BlocksRegister.GREENGLOWINGMUSH.getMetaFromState(blockInfoIn.blockState)),
                  blockInfoIn.tileentityData
               );
            }

            return blockInfoIn;
         }

         if (blockInfoIn.blockState.getBlock() == Blocks.STONE_BRICK_STAIRS) {
            return new BlockInfo(
               blockInfoIn.pos,
               BlocksRegister.DOLERITESTAIRS.getStateFromMeta(Blocks.STONE_BRICK_STAIRS.getMetaFromState(blockInfoIn.blockState)),
               blockInfoIn.tileentityData
            );
         }

         if (blockInfoIn.blockState.getBlock() == Blocks.STONE_STAIRS) {
            return new BlockInfo(
               blockInfoIn.pos,
               BlocksRegister.DOLERITESTAIRS.getStateFromMeta(Blocks.STONE_STAIRS.getMetaFromState(blockInfoIn.blockState)),
               blockInfoIn.tileentityData
            );
         }
      } else if (this.mode == DimensionDungeon.EnumCaveDecorType.CALCITE
         && (blockInfoIn.blockState.getBlock() == Blocks.STONE || blockInfoIn.blockState.getBlock() == Blocks.COBBLESTONE)) {
         world.setBlockState(pos, BlocksRegister.CALCITE.getDefaultState());
         return null;
      }

      if (blockInfoIn.blockState.getBlock() == BlocksRegister.ORERUBY) {
         world.setBlockState(pos, this.ore);
         return null;
      } else {
         return blockInfoIn;
      }
   }
}
