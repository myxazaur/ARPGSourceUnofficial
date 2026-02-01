package com.vivern.arpg.dimensions.generationutils;

import com.vivern.arpg.loot.ListLootTable;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.mobs.Moonshroom;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenMushroomSpawner implements IWorldGenerator {
   public BlockMushroom mushroom1;
   public BlockMushroom mushroom2;
   public Block mycelium;
   public int radius;
   public int dimension;
   public float chance;

   public WorldGenMushroomSpawner(BlockMushroom mushroom1, BlockMushroom mushroom2, Block mycelium, int radius, int dimension, float chance) {
      this.mushroom1 = mushroom1;
      this.mushroom2 = mushroom2;
      this.mycelium = mycelium;
      this.radius = radius;
      this.dimension = dimension;
      this.chance = chance;
   }

   public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
      if (this.dimension == world.provider.getDimension() && rand.nextFloat() < this.chance * 4.0F) {
         Biome biome = world.getBiome(new BlockPos(chunkX * 16 + 8, 64, chunkZ * 16 + 8));
         boolean hasBiomeDic = BiomeDictionary.hasType(biome, Type.MUSHROOM)
            || BiomeDictionary.hasType(biome, Type.DENSE) && BiomeDictionary.hasType(biome, Type.SPOOKY) && BiomeDictionary.hasType(biome, Type.FOREST);
         if (hasBiomeDic || rand.nextFloat() < 0.25F) {
            int x = chunkX * 16 + rand.nextInt(16) + 8;
            int y = 128;
            int z = chunkZ * 16 + rand.nextInt(16) + 8;
            BlockPos pos = GetMOP.getTopBlocks(world, new BlockPos(x, y, z), Blocks.GRASS, Blocks.MYCELIUM, Blocks.DIRT);
            if (pos.getY() > 15
               && world.getBlockState(pos.up()).getBlock().isReplaceable(world, pos.up())
               && !GetMOP.IFLUID_BLOCKS.apply(world.getBlockState(pos.up()))) {
               for (int xx = -this.radius; xx <= this.radius; xx++) {
                  for (int zz = -this.radius; zz <= this.radius; zz++) {
                     double dist = Math.sqrt(xx * xx + zz * zz);
                     if (dist < this.radius - rand.nextInt(5)) {
                        for (int yy = 8; yy > -6; yy--) {
                           BlockPos finalpos = pos.add(xx, yy, zz);
                           Block bl = world.getBlockState(finalpos).getBlock();
                           if (bl == Blocks.GRASS || bl == Blocks.MYCELIUM || bl == Blocks.DIRT) {
                              world.setBlockState(finalpos, Blocks.MYCELIUM.getDefaultState(), 2);
                              if (rand.nextFloat() < 0.3F) {
                                 BlockMushroom block = rand.nextFloat() < 0.5F ? this.mushroom1 : this.mushroom2;
                                 IBlockState state = block.getDefaultState();
                                 world.setBlockState(finalpos.up(), state, 2);
                                 if (rand.nextFloat() < 0.3F) {
                                    block.grow(world, rand, finalpos.up(), state);
                                 }
                              }
                              break;
                           }
                        }
                     }
                  }
               }
            }

            for (int i = 0; i < 4; i++) {
               Block fence = i == 0 ? this.mycelium : Blocks.OAK_FENCE;
               world.setBlockState(pos.add(1, i, 0), fence.getDefaultState(), 2);
               world.setBlockState(pos.add(-1, i, 0), fence.getDefaultState(), 2);
               world.setBlockState(pos.add(0, i, 1), fence.getDefaultState(), 2);
               world.setBlockState(pos.add(0, i, -1), fence.getDefaultState(), 2);
            }

            pos = pos.up();
            world.setBlockState(pos, Blocks.CHEST.getDefaultState(), 2);
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof TileEntityChest) {
               ((TileEntityChest)tileentity)
                  .setLootTable(
                     ListLootTable.CHESTS_MUSHROOM_TREASURE,
                     GetMOP.getBestRandomBasedOnCoordinates(pos.getX(), pos.getZ(), world.getSeed()).nextLong()
                  );
            }

            pos = pos.up(2);
            world.setBlockState(pos, Blocks.MOB_SPAWNER.getDefaultState(), 2);
            tileentity = world.getTileEntity(pos);
            if (tileentity instanceof TileEntityMobSpawner) {
               TileEntityMobSpawner spawner = (TileEntityMobSpawner)tileentity;
               spawner.getSpawnerBaseLogic().setEntityId(EntityList.getKey(Moonshroom.class));
            }
         }
      }
   }
}
