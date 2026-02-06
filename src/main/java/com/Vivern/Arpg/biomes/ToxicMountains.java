package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.generationutils.WorldGenCaveLiquids;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenGroundFoliage;
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
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class ToxicMountains extends BiomeControlled {
   public ToxicMountains() {
      super(new BiomeProperties("Toxic mountains").setBaseHeight(1.4F).setHeightVariation(1.3F).setTemperature(0.6F).setRainfall(0.6F).setWaterColor(5931421));
      this.topBlock = BlocksRegister.TOXICGRASS.getDefaultState();
      this.fillerBlock = BlocksRegister.TOXICDIRT.getDefaultState();
      this.decorator = new ToxicMountainsDecorator();
   }

   @Override
   public IBlockState[] controlSurface(IBlockState top, IBlockState filler, double tgc) {
      if (tgc < 1.0) {
         top = ToxicomaniaChunkGenerator.GRASS;
         filler = ToxicomaniaChunkGenerator.DIRT;
      } else if (tgc < 4.0) {
         top = ToxicomaniaChunkGenerator.STONE;
         filler = ToxicomaniaChunkGenerator.STONE;
      } else if (tgc < 5.0) {
         top = ToxicomaniaChunkGenerator.COBBLE;
         filler = ToxicomaniaChunkGenerator.COBBLE;
      }

      return new IBlockState[]{top, filler};
   }

   class ToxicMountainsDecorator extends BiomeDecorator {
      public WorldGenGroundFoliage junkweed = new WorldGenGroundFoliage(BlocksRegister.JUNKWEED, 64, 8, 7);
      public WorldGenGroundFoliage weep = new WorldGenGroundFoliage(BlocksRegister.TOXIBERRYWEEPING, 20, 8, 5);
      public WorldGenGroundFoliage vibrant = new WorldGenGroundFoliage(BlocksRegister.TOXIBERRYVIBRANT, 15, 6, 5);
      public WorldGenGroundFoliage mosspl = new WorldGenGroundFoliage(BlocksRegister.MOSSPLANT, 32, 8, 5);
      public WorldGenBlockBlob SCRAP_GENERATOR = new WorldGenBlockBlob(BlocksRegister.SCRAP, 0);
      public WorldGenBlockBlob JUNK_GENERATOR = new WorldGenBlockBlob(BlocksRegister.JUNK, 0);
      public WorldGenGroundFoliage tallgrass = new WorldGenGroundFoliage(BlocksRegister.TOXICTALLGRASS, 38, 6, 5);

      public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
         if (this.decorating) {
            throw new RuntimeException("Already decorating");
         } else {
            if (random.nextInt(6) == 0) {
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

            if (random.nextFloat() < 0.6) {
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
