//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.aquatica;

import com.Vivern.Arpg.blocks.Chair;
import com.Vivern.Arpg.blocks.Table;
import com.Vivern.Arpg.dimensions.generationutils.GenerationHelper;
import com.Vivern.Arpg.loot.ListLootTable;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.mobs.SpawnerTuners;
import com.Vivern.Arpg.tileentity.EnumChest;
import net.minecraft.block.BlockChest;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

public class ReplacersAquatica {
   public static ITemplateProcessor replacerSunkenTown = new ITemplateProcessor() {
      public BlockInfo processBlock(World world, BlockPos pos, BlockInfo blockInfoIn) {
         if (blockInfoIn.blockState.getBlock() == Blocks.PURPUR_BLOCK) {
            world.setBlockState(pos, BlocksRegister.MOBSPAWNERAQUATIC.getDefaultState());
            SpawnerTuners.SUNKENTOWN.setupSpawner(world, pos, world.rand);
            return null;
         } else if (blockInfoIn.blockState.getBlock() == Blocks.PURPUR_PILLAR) {
            world.setBlockState(pos, BlocksRegister.TIDEBEACON.getDefaultState());
            return null;
         } else if (blockInfoIn.blockState.getBlock() != Blocks.PURPUR_SLAB) {
            if (blockInfoIn.blockState.getBlock() == Blocks.CHEST) {
               GenerationHelper.setChestWithLoot(
                  world,
                  pos,
                  EnumChest.CORAL,
                  ListLootTable.CHESTS_SUNKEN_TOWN,
                  (EnumFacing)blockInfoIn.blockState.getValue(BlockChest.FACING)
               );
               return null;
            } else {
               return blockInfoIn.blockState.getBlock() == Blocks.AIR && pos.getY() <= AquaticaChunkGenerator.sealvl
                  ? new BlockInfo(pos, Blocks.WATER.getDefaultState(), null)
                  : blockInfoIn;
            }
         } else {
            if (world.rand.nextFloat() < 0.25F) {
               world.setBlockState(
                  pos, pos.getY() > AquaticaChunkGenerator.sealvl ? Blocks.AIR.getDefaultState() : Blocks.WATER.getDefaultState()
               );
            } else if (world.rand.nextFloat() < 0.27F) {
               world.setBlockState(pos, BlocksRegister.CORALCHANDELIER.getStateFromMeta(world.rand.nextInt(2)));
            } else if (world.rand.nextFloat() < 0.53F) {
               world.setBlockState(pos, BlocksRegister.CORALTABLE.getDefaultState().withProperty(Table.FLAT, false));

               for (EnumFacing face : EnumFacing.HORIZONTALS) {
                  if (world.rand.nextFloat() < 0.5F && !world.getBlockState(pos.offset(face)).getMaterial().blocksMovement()) {
                     world.setBlockState(pos.offset(face), BlocksRegister.CORALCHAIR.getDefaultState().withProperty(Chair.FACING, face.getOpposite()));
                  }
               }
            } else if (world.rand.nextFloat() < 0.6F) {
               world.setBlockState(pos, BlocksRegister.CORALVASE.getDefaultState());
            } else if (world.rand.nextFloat() < 0.4F) {
               world.setBlockState(pos, BlocksRegister.CORALTORCH.getDefaultState());
            } else {
               GenerationHelper.setChestWithLoot(
                  world, pos, EnumChest.CORAL, ListLootTable.CHESTS_SUNKEN_TOWN, EnumFacing.HORIZONTALS[world.rand.nextInt(4)]
               );
            }

            return null;
         }
      }
   };
}
