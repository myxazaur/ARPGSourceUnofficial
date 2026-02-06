package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.generationutils.WorldGenCaveLiquids;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenGroundFoliage;
import com.Vivern.Arpg.dimensions.toxicomania.ChestReplacerToxic;
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
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class ToxicPlains extends BiomeControlled {
   public ToxicPlains() {
      super(new BiomeProperties("Toxic plains").setBaseHeight(0.25F).setTemperature(1.0F).setWaterColor(5931421));
      this.topBlock = BlocksRegister.TOXICGRASS.getDefaultState();
      this.fillerBlock = BlocksRegister.TOXICDIRT.getDefaultState();
      this.decorator = new ToxicPlainsDecorator();
   }

   @Override
   public IBlockState[] controlSurface(IBlockState top, IBlockState filler, double tgc) {
      if (tgc < 0.8) {
         top = ToxicomaniaChunkGenerator.GRASS;
         filler = ToxicomaniaChunkGenerator.DIRT;
      } else if (tgc < 2.5) {
         top = ToxicomaniaChunkGenerator.SLUDGE;
         filler = ToxicomaniaChunkGenerator.JUNK;
      } else if (tgc < 6.0) {
         top = ToxicomaniaChunkGenerator.SLIME;
         filler = ToxicomaniaChunkGenerator.SLUDGE;
      }

      return new IBlockState[]{top, filler};
   }

   class ToxicPlainsDecorator extends BiomeDecorator {
      public WorldGenGroundFoliage foliage1 = new WorldGenGroundFoliage(BlocksRegister.ARELIA, 64, 6, 4);
      public WorldGenGroundFoliage foliage2 = new WorldGenGroundFoliage(BlocksRegister.TOXEDGE, 64, 9, 4);
      public WorldGenGroundFoliage foliage3 = new WorldGenGroundFoliage(BlocksRegister.TOXIBERRYARCANO, 64, 8, 4);
      public WorldGenGroundFoliage foliage4 = new WorldGenGroundFoliage(BlocksRegister.DECEIDUS, 40, 12, 5);
      public WorldGenGroundFoliage foliage5 = new WorldGenGroundFoliage(BlocksRegister.MUCOPHILLUSBROWN, 10, 8, 4);
      public WorldGenGroundFoliage foliage6 = new WorldGenGroundFoliage(BlocksRegister.MUCOPHILLUS, 5, 8, 4);
      public WorldGenGroundFoliage foliage7 = new WorldGenGroundFoliage(BlocksRegister.CONTEMPLANT, 50, 8, 4);
      public WorldGenGroundFoliage toxiBULB = new WorldGenGroundFoliage(BlocksRegister.TOXIBULB, 4, 8, 3);
      public WorldGenGroundFoliage tallgrass = new WorldGenGroundFoliage(BlocksRegister.TOXICTALLGRASS, 48, 9, 4);

      public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
         if (this.decorating) {
            throw new RuntimeException("Already decorating");
         } else {
            if (random.nextInt(70) == 0) {
               BlockPos position = worldIn.getTopSolidOrLiquidBlock(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if (blockd.isFullCube(worldIn.getBlockState(position.down()))) {
                  WorldServer worldServer = (WorldServer)worldIn;
                  MinecraftServer minecraftServer = worldIn.getMinecraftServer();
                  TemplateManager templateManager = worldServer.getStructureTemplateManager();
                  Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:toxic_home_1"));
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

                  template.addBlocksToWorld(worldIn, position.add(sx * 6, -3, sz * 6), ChestReplacerToxic.instance, settings, 2);
               }
            }

            if (random.nextInt(3) == 0) {
               BlockPos position = worldIn.getHeight(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if (blockd == BlocksRegister.TOXICGRASS
                  || blockd == BlocksRegister.TOXICDIRT
                  || blockd == BlocksRegister.SLUDGE
                  || blockd == BlocksRegister.JUNK) {
                  WorldServer worldServerx = (WorldServer)worldIn;
                  MinecraftServer minecraftServerx = worldIn.getMinecraftServer();
                  TemplateManager templateManagerx = worldServerx.getStructureTemplateManager();
                  Template templatex = templateManagerx.get(minecraftServerx, new ResourceLocation("arpg:toxic_tree_" + (random.nextInt(3) + 1)));
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

                  templatex.addBlocksToWorld(worldIn, position.add(sxx * 3, 0, szx * 3), settingsx);
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

            if (random.nextFloat() < 0.3) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.foliage1.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.7) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               if (worldIn.getBlockState(postop.down()).getBlock() == BlocksRegister.TOXICGRASS || random.nextFloat() < 0.3) {
                  this.foliage2.generate(worldIn, random, postop);
               }
            }

            if (random.nextFloat() < 0.4) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               if (worldIn.getBlockState(postop.down()).getBlock() == BlocksRegister.TOXICGRASS || random.nextFloat() < 0.2) {
                  this.foliage3.generate(worldIn, random, postop);
               }
            }

            if (random.nextFloat() < 0.4) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.foliage4.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            for (int i = 0; i < 4; i++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               if (worldIn.getBlockState(postop.down()).getBlock() == BlocksRegister.SLUDGE || random.nextFloat() < 0.3) {
                  this.foliage5.generate(worldIn, random, postop);
               }
            }

            for (int ix = 0; ix < 4; ix++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               if (worldIn.getBlockState(postop.down()).getBlock() == BlocksRegister.SLUDGE || random.nextFloat() < 0.3) {
                  this.foliage6.generate(worldIn, random, postop);
               }
            }

            if (random.nextFloat() < 0.2) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               if (worldIn.getBlockState(postop.down()).getBlock() == BlocksRegister.TOXICGRASS || random.nextFloat() < 0.1) {
                  this.foliage7.generate(worldIn, random, postop);
               }
            }

            if (random.nextFloat() < 0.9) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.tallgrass.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.1) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.toxiBULB.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.06) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               ToxicomaniaChunkGenerator.genLootBlob(worldIn, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)), random);
            }

            this.decorating = false;
         }
      }
   }
}
