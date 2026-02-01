package com.vivern.arpg.biomes;

import com.vivern.arpg.dimensions.generationutils.WorldGenCaveLiquids;
import com.vivern.arpg.dimensions.generationutils.WorldGenSpread;
import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
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

public class PoisonOcean extends Biome {
   public PoisonOcean() {
      super(new BiomeProperties("Poison ocean").setBaseHeight(-1.2F).setHeightVariation(0.1F).setTemperature(0.8F).setWaterColor(5931421));
      this.topBlock = BlocksRegister.TOXICDIRT.getDefaultState();
      this.fillerBlock = BlocksRegister.SLUDGE.getDefaultState();
      this.decorator = new PoisonOceanDecorator();
   }

   class PoisonOceanDecorator extends BiomeDecorator {
      public WorldGenSpread junk = new WorldGenSpread(BlocksRegister.JUNK, 30, 6, 2, BlocksRegister.SLUDGE, true);
      public WorldGenSpread scrap = new WorldGenSpread(BlocksRegister.SCRAP, 15, 5, 2, BlocksRegister.SLUDGE, true);
      public WorldGenSpread slimeglobs = new WorldGenSpread(BlocksRegister.SLIMEGLOB, 15, 6, 3, BlocksRegister.SLUDGE, true);

      public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
         if (this.decorating) {
            throw new RuntimeException("Already decorating");
         } else {
            if (random.nextFloat() < 0.6F) {
               BlockPos position = worldIn.getTopSolidOrLiquidBlock(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if (worldIn.getBlockState(position.down()).isOpaqueCube()
                  && worldIn.getBlockState(position).getBlock() == BlocksRegister.FLUIDPOISON
                  && worldIn.getBlockState(position.up(3)).getBlock() == BlocksRegister.FLUIDPOISON) {
                  WorldServer worldServer = (WorldServer)worldIn;
                  MinecraftServer minecraftServer = worldIn.getMinecraftServer();
                  TemplateManager templateManager = worldServer.getStructureTemplateManager();
                  Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:poison_river_" + (random.nextInt(10) + 1)));
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

                  template.addBlocksToWorld(worldIn, position.add(sx * 3, 0, sz * 3), settings, 2);
               }
            }

            if (random.nextFloat() < 0.6F) {
               BlockPos position = worldIn.getTopSolidOrLiquidBlock(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if (worldIn.getBlockState(position.down()).isOpaqueCube()
                  && worldIn.getBlockState(position).getBlock() == BlocksRegister.FLUIDPOISON
                  && worldIn.getBlockState(position.up(5)).getBlock() == BlocksRegister.FLUIDPOISON) {
                  WorldServer worldServerx = (WorldServer)worldIn;
                  MinecraftServer minecraftServerx = worldIn.getMinecraftServer();
                  TemplateManager templateManagerx = worldServerx.getStructureTemplateManager();
                  Template templatex = templateManagerx.get(minecraftServerx, new ResourceLocation("arpg:poison_ocean_" + (random.nextInt(13) + 1)));
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

                  templatex.addBlocksToWorld(worldIn, position.add(sxx * 6, 0, szx * 6), settingsx, 2);
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

            if (random.nextFloat() < 0.4) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.junk.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.2) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.scrap.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.32) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.slimeglobs.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            this.decorating = false;
         }
      }
   }
}
