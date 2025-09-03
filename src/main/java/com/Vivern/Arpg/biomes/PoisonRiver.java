//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.generationutils.WorldGenCaveLiquids;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenGroundFoliage;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenSpread;
import com.Vivern.Arpg.dimensions.toxicomania.ToxicomaniaChunkGenerator;
import com.Vivern.Arpg.main.BlocksRegister;
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

public class PoisonRiver extends Biome {
   public PoisonRiver() {
      super(new BiomeProperties("Poison river").setBaseHeight(-0.5F).setHeightVariation(0.0F).setTemperature(1.0F).setWaterColor(5931421));
      this.topBlock = BlocksRegister.SLUDGE.getDefaultState();
      this.fillerBlock = BlocksRegister.TOXICDIRT.getDefaultState();
      this.decorator = new PoisonRiverDecorator();
   }

   class PoisonRiverDecorator extends BiomeDecorator {
      public WorldGenGroundFoliage poisonlily = new WorldGenGroundFoliage(BlocksRegister.POISONLILY, 8, 4, 0);
      public WorldGenSpread junk = new WorldGenSpread(BlocksRegister.JUNK, 30, 6, 2, BlocksRegister.TOXICDIRT, true);
      public WorldGenSpread scrap = new WorldGenSpread(BlocksRegister.SCRAP, 15, 5, 2, BlocksRegister.TOXICDIRT, true);
      public WorldGenSpread logs = new WorldGenSpread(BlocksRegister.TOXIBERRYLOG, 7, 5, 1, BlocksRegister.TOXICDIRT, true);
      public WorldGenSpread slimeglobs = new WorldGenSpread(BlocksRegister.SLIMEGLOB, 15, 6, 3, BlocksRegister.TOXICDIRT, true);

      public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
         if (this.decorating) {
            throw new RuntimeException("Already decorating");
         } else {
            if (random.nextFloat() < 0.8F) {
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

            if (random.nextFloat() < 0.2) {
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

            if (random.nextFloat() < 0.4) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.junk.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.3) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.scrap.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.15) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.logs.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
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
