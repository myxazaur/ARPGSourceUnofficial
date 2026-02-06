package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.generationutils.WorldGenCaveLiquids;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenGroundFoliage;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenWateredMountains;
import com.Vivern.Arpg.dimensions.toxicomania.ToxicomaniaChunkGenerator;
import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class ToxicHighRocks extends BiomeControlled {
   public ToxicHighRocks() {
      super(new BiomeProperties("Toxic high rocks").setBaseHeight(-0.2F).setHeightVariation(1.0F).setTemperature(0.6F).setRainfall(0.6F).setWaterColor(5931421));
      this.topBlock = BlocksRegister.TOXICGRASS.getDefaultState();
      this.fillerBlock = BlocksRegister.TOXICDIRT.getDefaultState();
      this.decorator = new ToxicHighRocksDecorator();
   }

   @Override
   public IBlockState[] controlSurface(IBlockState top, IBlockState filler, double tgc) {
      if (tgc < 1.0) {
         top = ToxicomaniaChunkGenerator.GRASS;
         filler = ToxicomaniaChunkGenerator.DIRT;
      } else if (tgc < 1.4) {
         top = ToxicomaniaChunkGenerator.SLUDGE;
         filler = ToxicomaniaChunkGenerator.SCRAP;
      } else if (tgc < 4.0) {
         top = ToxicomaniaChunkGenerator.STONE;
         filler = ToxicomaniaChunkGenerator.STONE;
      } else if (tgc < 5.0) {
         top = ToxicomaniaChunkGenerator.COBBLE;
         filler = ToxicomaniaChunkGenerator.COBBLE;
      }

      return new IBlockState[]{top, filler};
   }

   class ToxicHighRocksDecorator extends BiomeDecorator {
      public WorldGenGroundFoliage toxinia = new WorldGenGroundFoliage(BlocksRegister.TOXINIA, 32, 8, 4);
      public WorldGenGroundFoliage viscosa = new WorldGenGroundFoliage(BlocksRegister.VISCOSA, 10, 6, 4);
      public WorldGenGroundFoliage junkweed = new WorldGenGroundFoliage(BlocksRegister.JUNKWEED, 64, 8, 7);
      public WorldGenGroundFoliage weep = new WorldGenGroundFoliage(BlocksRegister.TOXIBERRYWEEPING, 20, 8, 5);
      public WorldGenGroundFoliage vibrant = new WorldGenGroundFoliage(BlocksRegister.TOXIBERRYVIBRANT, 15, 6, 5);
      public WorldGenGroundFoliage mosspl = new WorldGenGroundFoliage(BlocksRegister.MOSSPLANT, 32, 8, 5);
      public WorldGenBlockBlob SCRAP_GENERATOR = new WorldGenBlockBlob(BlocksRegister.SCRAP, 2);
      public WorldGenBlockBlob JUNK_GENERATOR = new WorldGenBlockBlob(BlocksRegister.JUNK, 2);
      public WorldGenGroundFoliage tallgrass = new WorldGenGroundFoliage(BlocksRegister.TOXICTALLGRASS, 38, 6, 5);

      public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
         if (this.decorating) {
            throw new RuntimeException("Already decorating");
         } else {
            if (random.nextInt(3) == 0) {
               NoiseGeneratorPerlin noise = new NoiseGeneratorPerlin(random, 4);
               WorldGenWateredMountains rocks = new WorldGenWateredMountains(
                  BlocksRegister.RADIOSTONE.getDefaultState(), BlocksRegister.TOXICDIRT.getDefaultState(), BlocksRegister.TOXICGRASS.getDefaultState(), noise
               );
               BlockPos position = worldIn.getTopSolidOrLiquidBlock(
                     new BlockPos(pos.getX() + 12 + random.nextInt(8), 0, pos.getZ() + 12 + random.nextInt(8))
                  )
                  .down(8);
               rocks.height = random.nextInt(80) + 50;
               if (random.nextInt(4) == 0) {
                  rocks.startradius = random.nextInt(4) + 1;
                  rocks.maxradius = rocks.startradius + random.nextInt(10) + 3;
               } else {
                  rocks.startradius = random.nextInt(2) + 3;
                  rocks.maxradius = rocks.startradius + random.nextInt(8) + 5;
               }

               rocks.mult1 = random.nextFloat() + 1.0F;
               rocks.mult2 = random.nextFloat() + 2.0F;
               rocks.generate(worldIn, random, position);
            }

            if (random.nextInt(2) == 0) {
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

            if (random.nextInt(2) == 0) {
               BlockPos position = worldIn.getHeight(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if (blockd == BlocksRegister.TOXICGRASS || blockd == BlocksRegister.TOXICDIRT || blockd == BlocksRegister.SLUDGE) {
                  WorldServer worldServerx = (WorldServer)worldIn;
                  MinecraftServer minecraftServerx = worldIn.getMinecraftServer();
                  TemplateManager templateManagerx = worldServerx.getStructureTemplateManager();
                  int ty = random.nextInt(3) + 1;
                  Template templatex = templateManagerx.get(minecraftServerx, new ResourceLocation("arpg:toxic_high_tree_" + ty));
                  PlacementSettings settingsx = new PlacementSettings();
                  int sxx = -1;
                  int szx = -1;
                  int swrx = random.nextInt(4);
                  if (swrx == 0) {
                     settingsx.setRotation(Rotation.CLOCKWISE_180);
                     sxx = 1;
                     szx = 1;
                  }

                  if (swrx == 1) {
                     settingsx.setRotation(Rotation.CLOCKWISE_90);
                     sxx = 1;
                     szx = -1;
                  }

                  if (swrx == 2) {
                     settingsx.setRotation(Rotation.COUNTERCLOCKWISE_90);
                     sxx = -1;
                     szx = 1;
                  }

                  if (swrx == 3) {
                     settingsx.setRotation(Rotation.NONE);
                  }

                  if (ty == 1) {
                     templatex.addBlocksToWorld(worldIn, position.add(sxx * 10, -5, szx * 10), settingsx);
                  }

                  if (ty == 2) {
                     templatex.addBlocksToWorld(worldIn, position.add(sxx * 4, 0, szx * 4), settingsx);
                  }

                  if (ty == 3) {
                     templatex.addBlocksToWorld(worldIn, position.add(sxx * 6, 0, szx * 6), settingsx);
                  }
               }
            }

            this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;

            for (int k5 = 0; k5 < 60; k5++) {
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

            for (int i = 0; i < 2; i++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               if (worldIn.getBlockState(postop.down()).getBlock() == BlocksRegister.JUNK || random.nextFloat() < 0.4) {
                  this.junkweed.generate(worldIn, random, postop);
               }
            }

            if (random.nextFloat() < 0.3) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.weep.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.4) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.vibrant.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.6) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.mosspl.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.4) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.toxinia.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.4) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               this.viscosa.generate(worldIn, random, postop);
            }

            for (int ix = 0; ix < 3; ix++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.tallgrass.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
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
