package com.vivern.arpg.biomes;

import com.vivern.arpg.dimensions.generationutils.WorldGenCaveLiquids;
import com.vivern.arpg.dimensions.generationutils.WorldGenGroundFoliage;
import com.vivern.arpg.dimensions.toxicomania.ToxicomaniaChunkGenerator;
import com.vivern.arpg.main.BlocksRegister;
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

public class ToxicFlowerIslands extends BiomeControlled {
   public ToxicFlowerIslands() {
      super(
         new BiomeProperties("Toxic flower island").setBaseHeight(-0.35F).setHeightVariation(0.2F).setTemperature(1.25F).setRainfall(0.9F).setWaterColor(5931421)
      );
      this.topBlock = BlocksRegister.TOXICGRASS.getDefaultState();
      this.fillerBlock = BlocksRegister.TOXICDIRT.getDefaultState();
      this.decorator = new ToxicFlowerIslandsDecorator();
   }

   @Override
   public IBlockState[] controlSurface(IBlockState top, IBlockState filler, double tgc) {
      top = ToxicomaniaChunkGenerator.GRASS;
      filler = ToxicomaniaChunkGenerator.DIRT;
      return new IBlockState[]{top, filler};
   }

   class ToxicFlowerIslandsDecorator extends BiomeDecorator {
      public WorldGenGroundFoliage arelia = new WorldGenGroundFoliage(BlocksRegister.ARELIA, 64, 8, 3);
      public WorldGenGroundFoliage deceidus = new WorldGenGroundFoliage(BlocksRegister.DECEIDUS, 25, 8, 5);
      public WorldGenGroundFoliage toxinia = new WorldGenGroundFoliage(BlocksRegister.TOXINIA, 22, 8, 4);
      public WorldGenGroundFoliage mosspl = new WorldGenGroundFoliage(BlocksRegister.MOSSPLANT, 12, 8, 5);
      public WorldGenGroundFoliage tallgrass = new WorldGenGroundFoliage(BlocksRegister.TOXICTALLGRASS, 22, 8, 4);
      public WorldGenGroundFoliage poisonlily = new WorldGenGroundFoliage(BlocksRegister.POISONLILY, 10, 8, 0);

      public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
         if (this.decorating) {
            throw new RuntimeException("Already decorating");
         } else {
            if (random.nextFloat() < 0.8F) {
               BlockPos position = worldIn.getTopSolidOrLiquidBlock(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if ((!worldIn.getBlockState(position).getMaterial().isLiquid() || random.nextFloat() < 0.3F)
                  && (blockd == BlocksRegister.TOXICGRASS || blockd == BlocksRegister.TOXICDIRT || blockd == BlocksRegister.TOXIBERRYLOG)) {
                  WorldServer worldServer = (WorldServer)worldIn;
                  MinecraftServer minecraftServer = worldIn.getMinecraftServer();
                  TemplateManager templateManager = worldServer.getStructureTemplateManager();
                  int t = random.nextInt(4) + 1;
                  Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:toxic_tree_flower_" + t));
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

                  int disp = 3;
                  if (t == 1) {
                     disp = 5;
                  }

                  if (t == 2) {
                     disp = 8;
                  }

                  template.addBlocksToWorld(worldIn, position.add(sx * disp, 0, sz * disp), settings);
               }
            }

            if (random.nextFloat() < 0.8F) {
               BlockPos position = worldIn.getTopSolidOrLiquidBlock(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if (blockd == BlocksRegister.TOXICGRASS
                  || blockd == BlocksRegister.TOXICDIRT
                  || blockd == BlocksRegister.TOXIBERRYLOG
                  || blockd == BlocksRegister.SLUDGE
                  || blockd == BlocksRegister.RADIOSTONE) {
                  WorldServer worldServerx = (WorldServer)worldIn;
                  MinecraftServer minecraftServerx = worldIn.getMinecraftServer();
                  TemplateManager templateManagerx = worldServerx.getStructureTemplateManager();
                  Template templatex = templateManagerx.get(minecraftServerx, new ResourceLocation("arpg:toxic_log_" + (random.nextInt(5) + 1)));
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

            for (int k5 = 0; k5 < 40; k5++) {
               int i10 = random.nextInt(16) + 8;
               int l13 = random.nextInt(16) + 8;
               int i17 = random.nextInt(248) + 8;
               if (i17 > 0) {
                  int k19 = random.nextInt(i17);
                  BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
                  new WorldGenCaveLiquids(BlocksRegister.FLUIDTOXIN, BlocksRegister.RADIOSTONE).generate(worldIn, random, blockpos6);
               }
            }

            for (int k5x = 0; k5x < 20; k5x++) {
               int i10 = random.nextInt(16) + 8;
               int l13 = random.nextInt(16) + 8;
               int i17 = random.nextInt(248) + 8;
               if (i17 > 0) {
                  int k19 = random.nextInt(i17);
                  BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
                  new WorldGenCaveLiquids(BlocksRegister.FLUIDSLIME, BlocksRegister.RADIOSTONE).generate(worldIn, random, blockpos6);
               }
            }

            for (int i = 0; i < 2; i++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.arelia.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.4) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               this.mosspl.generate(worldIn, random, postop);
            }

            if (random.nextFloat() < 0.5) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.deceidus.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.2) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.toxinia.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.6) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.tallgrass.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.4) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.poisonlily
                  .generate(worldIn, random, new BlockPos(this.chunkPos.getX() + j, 63, this.chunkPos.getZ() + k));
            }

            if (random.nextFloat() < 0.03) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               ToxicomaniaChunkGenerator.genLootBlob(worldIn, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)), random);
            }

            this.decorating = false;
         }
      }
   }
}
