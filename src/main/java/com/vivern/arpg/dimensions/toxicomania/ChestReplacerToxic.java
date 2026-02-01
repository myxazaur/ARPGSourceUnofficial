package com.vivern.arpg.dimensions.toxicomania;

import com.vivern.arpg.blocks.RustLamp;
import com.vivern.arpg.dimensions.generationutils.GenerationHelper;
import com.vivern.arpg.loot.ListLootTable;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.mobs.SpawnerTuners;
import com.vivern.arpg.tileentity.EnumChest;
import com.vivern.arpg.tileentity.TileMonsterSpawner;
import net.minecraft.block.BlockChest;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

public class ChestReplacerToxic implements ITemplateProcessor {
   public int type = 0;
   public static ChestReplacerToxic instance = new ChestReplacerToxic();
   public static ReplacerBunker instanceReplBunker = new ReplacerBunker();
   public static ReplacerLab instanceReplLab = new ReplacerLab();

   public BlockInfo processBlock(World world, BlockPos pos, BlockInfo blockInfoIn) {
      if (blockInfoIn.blockState.getBlock() == Blocks.CHEST) {
         GenerationHelper.setChestWithLoot(
            world, pos, EnumChest.TOXIC, ListLootTable.CHESTS_TOXIC_COMMON, (EnumFacing)blockInfoIn.blockState.getValue(BlockChest.FACING)
         );
         return null;
      } else {
         return blockInfoIn;
      }
   }

   public static class ReplacerBunker implements ITemplateProcessor {
      public BlockInfo processBlock(World world, BlockPos pos, BlockInfo blockInfoIn) {
         if (blockInfoIn.blockState.getBlock() == Blocks.PURPUR_BLOCK) {
            world.setBlockState(pos, BlocksRegister.MOBSPAWNERRUSTED.getDefaultState());
            TileMonsterSpawner spawner = (TileMonsterSpawner)world.getTileEntity(pos);
            SpawnerTuners.BUNKER.setupSpawner(world, spawner, world.rand);
            return null;
         } else if (blockInfoIn.blockState.getBlock() == BlocksRegister.RUSTLAMP) {
            return world.rand.nextFloat() < 0.25
               ? new BlockInfo(blockInfoIn.pos, blockInfoIn.blockState.withProperty(RustLamp.ON, true), blockInfoIn.tileentityData)
               : blockInfoIn;
         } else if (blockInfoIn.blockState.getBlock() == Blocks.CHEST) {
            if (world.rand.nextFloat() < 0.5) {
               world.setBlockToAir(pos);
            } else {
               GenerationHelper.setChestWithLoot(
                  world,
                  pos,
                  EnumChest.RUSTED,
                  ListLootTable.CHESTS_RUSTED_BUNKER,
                  (EnumFacing)blockInfoIn.blockState.getValue(BlockChest.FACING)
               );
            }

            return null;
         } else {
            return blockInfoIn;
         }
      }
   }

   public static class ReplacerLab implements ITemplateProcessor {
      public BlockInfo processBlock(World world, BlockPos pos, BlockInfo blockInfoIn) {
         if (blockInfoIn.blockState.getBlock() == Blocks.PURPUR_BLOCK) {
            world.setBlockState(pos, BlocksRegister.MOBSPAWNERRUSTED.getDefaultState());
            TileMonsterSpawner spawner = (TileMonsterSpawner)world.getTileEntity(pos);
            SpawnerTuners.LABORATORY.setupSpawner(world, spawner, world.rand);
            return null;
         } else if (blockInfoIn.blockState.getBlock() == BlocksRegister.RUSTLAMP) {
            return world.rand.nextFloat() < 0.25
               ? new BlockInfo(blockInfoIn.pos, blockInfoIn.blockState.withProperty(RustLamp.ON, true), blockInfoIn.tileentityData)
               : blockInfoIn;
         } else if (blockInfoIn.blockState.getBlock() == Blocks.CHEST) {
            if (world.rand.nextFloat() < 0.5) {
               world.setBlockToAir(pos);
            } else {
               GenerationHelper.setChestWithLoot(
                  world,
                  pos,
                  EnumChest.RUSTED,
                  ListLootTable.CHESTS_RUSTED_LAB,
                  (EnumFacing)blockInfoIn.blockState.getValue(BlockChest.FACING)
               );
            }

            return null;
         } else {
            return blockInfoIn;
         }
      }
   }
}
