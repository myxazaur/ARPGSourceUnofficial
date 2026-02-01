package com.vivern.arpg.biomes;

import com.vivern.arpg.dimensions.generationutils.WorldGenCaveLiquids;
import com.vivern.arpg.dimensions.generationutils.WorldGenToxicBarrels;
import com.vivern.arpg.dimensions.toxicomania.ToxicomaniaChunkGenerator;
import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class DirtyBeach extends Biome {
   public DirtyBeach() {
      super(new BiomeProperties("Dirty beach").setBaseHeight(0.12F).setTemperature(1.0F).setWaterColor(5931421).setHeightVariation(0.02F));
      this.topBlock = Blocks.SAND.getDefaultState();
      this.fillerBlock = Blocks.SAND.getDefaultState();
      this.decorator = new DirtyBeachDecorator();
   }

   class DirtyBeachDecorator extends BiomeDecorator {
      public WorldGenBlockBlob SCRAP_GENERATOR = new WorldGenBlockBlob(BlocksRegister.SCRAP, 0);
      public WorldGenBlockBlob JUNK_GENERATOR = new WorldGenBlockBlob(BlocksRegister.JUNK, 0);
      public WorldGenToxicBarrels barrels = new WorldGenToxicBarrels(14, 8, 1);

      public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
         if (this.decorating) {
            throw new RuntimeException("Already decorating");
         } else {
            if (random.nextInt(16) == 0) {
               BlockPos position = worldIn.getHeight(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if (blockd == BlocksRegister.TOXICGRASS
                  || blockd == BlocksRegister.TOXICDIRT
                  || blockd == BlocksRegister.SLUDGE
                  || blockd == BlocksRegister.JUNK) {
                  WorldServer worldServer = (WorldServer)worldIn;
                  MinecraftServer minecraftServer = worldIn.getMinecraftServer();
                  TemplateManager templateManager = worldServer.getStructureTemplateManager();
                  Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:toxic_tree_" + (random.nextInt(3) + 1)));
                  PlacementSettings settings = new PlacementSettings();
                  int sx = -1;
                  int sz = -1;
                  int swr = random.nextInt(4);
                  if (swr == 0) {
                     settings.setRotation(Rotation.CLOCKWISE_180);
                     sx = 1;
                     sz = 1;
                  }

                  if (swr == 1) {
                     settings.setRotation(Rotation.CLOCKWISE_90);
                     sx = 1;
                     sz = -1;
                  }

                  if (swr == 2) {
                     settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
                     sx = -1;
                     sz = 1;
                  }

                  if (swr == 3) {
                     settings.setRotation(Rotation.NONE);
                  }

                  template.addBlocksToWorld(worldIn, position.add(sx * 3, 0, sz * 3), settings);
               }
            }

            this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;

            for (int k5 = 0; k5 < 50; k5++) {
               int i10 = random.nextInt(16) + 8;
               int l13 = random.nextInt(16) + 8;
               int i17 = random.nextInt(248) + 8;
               if (i17 > 0) {
                  int k19 = random.nextInt(i17);
                  BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
                  new WorldGenCaveLiquids(BlocksRegister.FLUIDTOXIN, BlocksRegister.RADIOSTONE).generate(worldIn, random, blockpos6);
               }
            }

            if (random.nextFloat() < 0.25) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.SCRAP_GENERATOR.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.16) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.JUNK_GENERATOR.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.06) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.barrels.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.04) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               ToxicomaniaChunkGenerator.genLootBlob(worldIn, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)), random);
            }

            this.decorating = false;
         }
      }
   }
}
