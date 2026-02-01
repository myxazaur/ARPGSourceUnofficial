package com.vivern.arpg.biomes;

import baubles.api.BaublesApi;
import com.vivern.arpg.dimensions.generationutils.WorldGenCaveLiquids;
import com.vivern.arpg.dimensions.generationutils.WorldGenGroundFoliage;
import com.vivern.arpg.dimensions.generationutils.WorldGenToxicBarrels;
import com.vivern.arpg.dimensions.toxicomania.ToxicomaniaChunkGenerator;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Mana;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
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

public class ToxicSwamp extends BiomeControlled {
   public ToxicSwamp() {
      super(new BiomeProperties("Toxic swamp").setBaseHeight(-0.2F).setHeightVariation(0.1F).setTemperature(0.8F).setRainfall(0.9F).setWaterColor(5931421));
      this.topBlock = BlocksRegister.JUNK.getDefaultState();
      this.fillerBlock = BlocksRegister.TOXICDIRT.getDefaultState();
      this.decorator = new ToxicSwampDecorator();
   }

   @Override
   public IBlockState[] controlSurface(IBlockState top, IBlockState filler, double tgc) {
      if (tgc < 0.5) {
         top = ToxicomaniaChunkGenerator.JUNK;
         filler = ToxicomaniaChunkGenerator.JUNK;
      } else if (tgc < 0.7) {
         top = ToxicomaniaChunkGenerator.WASTE;
         filler = ToxicomaniaChunkGenerator.WASTE;
      } else if (tgc < 2.0) {
         top = ToxicomaniaChunkGenerator.JUNK;
         filler = ToxicomaniaChunkGenerator.JUNK;
      } else if (tgc < 2.2) {
         top = ToxicomaniaChunkGenerator.WASTE;
         filler = ToxicomaniaChunkGenerator.WASTE;
      } else if (tgc < 3.5) {
         top = ToxicomaniaChunkGenerator.SLUDGE;
         filler = ToxicomaniaChunkGenerator.SLUDGE;
      } else if (tgc < 4.5) {
         top = ToxicomaniaChunkGenerator.SCRAP;
         filler = ToxicomaniaChunkGenerator.SCRAP;
      } else if (tgc < 5.0) {
         top = ToxicomaniaChunkGenerator.WASTE;
         filler = ToxicomaniaChunkGenerator.WASTE;
      }

      return new IBlockState[]{top, filler};
   }

   @Override
   public void onPlayer60ticksInBiome(BiomeControlled biome, EntityPlayer player) {
      if (BaublesApi.isBaubleEquipped(player, ItemsRegister.ANTIRADPACK) > -1) {
         if (player.getRNG().nextFloat() < 0.25) {
            Mana.addRad(player, 2, true);
         }
      } else {
         Mana.addRad(player, 2, true);
      }
   }

   class ToxicSwampDecorator extends BiomeDecorator {
      public WorldGenGroundFoliage arelia = new WorldGenGroundFoliage(BlocksRegister.ARELIA, 50, 5, 4);
      public WorldGenGroundFoliage toxedge = new WorldGenGroundFoliage(BlocksRegister.TOXEDGE, 64, 10, 4);
      public WorldGenGroundFoliage deceidus = new WorldGenGroundFoliage(BlocksRegister.DECEIDUS, 20, 8, 5);
      public WorldGenGroundFoliage brownmuc = new WorldGenGroundFoliage(BlocksRegister.MUCOPHILLUSBROWN, 10, 8, 4);
      public WorldGenGroundFoliage mucoph = new WorldGenGroundFoliage(BlocksRegister.MUCOPHILLUS, 5, 8, 4);
      public WorldGenGroundFoliage viscosa = new WorldGenGroundFoliage(BlocksRegister.VISCOSA, 5, 8, 4);
      public WorldGenGroundFoliage junkweed = new WorldGenGroundFoliage(BlocksRegister.JUNKWEED, 64, 7, 4);
      public WorldGenGroundFoliage weep = new WorldGenGroundFoliage(BlocksRegister.TOXIBERRYWEEPING, 20, 8, 4);
      public WorldGenGroundFoliage toxinia = new WorldGenGroundFoliage(BlocksRegister.TOXINIA, 32, 8, 4);
      public WorldGenToxicBarrels barrels = new WorldGenToxicBarrels(22, 8, 4);
      public WorldGenGroundFoliage tallgrass = new WorldGenGroundFoliage(BlocksRegister.TOXICTALLGRASS, 48, 9, 4);
      public WorldGenGroundFoliage toxiBULB = new WorldGenGroundFoliage(BlocksRegister.TOXIBULB, 6, 7, 3);

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
                  Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:toxic_bore_1"));
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

                  template.addBlocksToWorld(worldIn, position.add(sx * 3, -9, sz * 3), settings);
               }
            }

            if (random.nextInt(70) == 0) {
               BlockPos position = worldIn.getTopSolidOrLiquidBlock(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if (blockd.isFullCube(worldIn.getBlockState(position.down()))) {
                  WorldServer worldServerx = (WorldServer)worldIn;
                  MinecraftServer minecraftServerx = worldIn.getMinecraftServer();
                  TemplateManager templateManagerx = worldServerx.getStructureTemplateManager();
                  Template templatex = templateManagerx.get(minecraftServerx, new ResourceLocation("arpg:toxic_water_tower_a_1"));
                  Template template2 = templateManagerx.get(minecraftServerx, new ResourceLocation("arpg:toxic_water_tower_b_1"));
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

                  templatex.addBlocksToWorld(worldIn, position.add(sxx * 8, -5, szx * 8), settingsx);
                  template2.addBlocksToWorld(worldIn, position.add(sxx * 8, 25, szx * 8), settingsx);
               }
            }

            if (random.nextInt(6) == 0) {
               BlockPos position = worldIn.getTopSolidOrLiquidBlock(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if (blockd == BlocksRegister.TOXICGRASS
                  || blockd == BlocksRegister.TOXICDIRT
                  || blockd == BlocksRegister.SLUDGE
                  || blockd == BlocksRegister.JUNK) {
                  WorldServer worldServerxx = (WorldServer)worldIn;
                  MinecraftServer minecraftServerxx = worldIn.getMinecraftServer();
                  TemplateManager templateManagerxx = worldServerxx.getStructureTemplateManager();
                  Template templatexx = templateManagerxx.get(minecraftServerxx, new ResourceLocation("arpg:toxic_tree_" + (random.nextInt(3) + 1)));
                  PlacementSettings settingsxx = new PlacementSettings();
                  int sxxx = -1;
                  int szxx = -1;
                  int swrxx = random.nextInt(4);
                  if (swrxx == 0) {
                     settingsxx.setRotation(Rotation.CLOCKWISE_180);
                     sxxx = 1;
                     szxx = 1;
                  }

                  if (swrxx == 1) {
                     settingsxx.setRotation(Rotation.CLOCKWISE_90);
                     sxxx = 1;
                     szxx = -1;
                  }

                  if (swrxx == 2) {
                     settingsxx.setRotation(Rotation.COUNTERCLOCKWISE_90);
                     sxxx = -1;
                     szxx = 1;
                  }

                  if (swrxx == 3) {
                     settingsxx.setRotation(Rotation.NONE);
                  }

                  templatexx.addBlocksToWorld(worldIn, position.add(sxxx * 3, 0, szxx * 3), settingsxx);
               }
            }

            if (random.nextFloat() < 0.8F) {
               BlockPos position = worldIn.getTopSolidOrLiquidBlock(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if (blockd == BlocksRegister.JUNK
                  || blockd == BlocksRegister.TOXICDIRT
                  || blockd == BlocksRegister.SLUDGE
                  || blockd == BlocksRegister.TOXICGRASS
                  || blockd == BlocksRegister.NUCLEARWASTE) {
                  WorldServer worldServerxxx = (WorldServer)worldIn;
                  MinecraftServer minecraftServerxxx = worldIn.getMinecraftServer();
                  TemplateManager templateManagerxxx = worldServerxxx.getStructureTemplateManager();
                  Template templatexxx = templateManagerxxx.get(
                     minecraftServerxxx, new ResourceLocation("arpg:toxic_swamp_tree_" + (random.nextInt(3) + 1))
                  );
                  PlacementSettings settingsxxx = new PlacementSettings();
                  int sxxxx = -1;
                  int szxxx = -1;
                  int swrxxx = random.nextInt(4);
                  if (swrxxx == 0) {
                     settingsxxx.setRotation(Rotation.CLOCKWISE_180);
                     sxxxx = 1;
                     szxxx = 1;
                  }

                  if (swrxxx == 1) {
                     settingsxxx.setRotation(Rotation.CLOCKWISE_90);
                     sxxxx = 1;
                     szxxx = -1;
                  }

                  if (swrxxx == 2) {
                     settingsxxx.setRotation(Rotation.COUNTERCLOCKWISE_90);
                     sxxxx = -1;
                     szxxx = 1;
                  }

                  if (swrxxx == 3) {
                     settingsxxx.setRotation(Rotation.NONE);
                  }

                  templatexxx.addBlocksToWorld(worldIn, position.add(sxxxx * 2, 0, szxxx * 2), settingsxxx);
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

            for (int k5x = 0; k5x < 40; k5x++) {
               int i10 = random.nextInt(16) + 8;
               int l13 = random.nextInt(16) + 8;
               int i17 = random.nextInt(248) + 8;
               if (i17 > 0) {
                  int k19 = random.nextInt(i17);
                  BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
                  new WorldGenCaveLiquids(BlocksRegister.FLUIDSLIME, BlocksRegister.RADIOSTONE).generate(worldIn, random, blockpos6);
               }
            }

            if (random.nextFloat() < 0.4) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.barrels.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.3) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.arelia.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.8) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               this.toxedge.generate(worldIn, random, postop);
            }

            if (random.nextFloat() < 0.4) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               this.viscosa.generate(worldIn, random, postop);
            }

            if (random.nextFloat() < 0.3) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.deceidus.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            for (int i = 0; i < 3; i++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               if (worldIn.getBlockState(postop.down()).getBlock() == BlocksRegister.SLUDGE || random.nextFloat() < 0.7) {
                  this.brownmuc.generate(worldIn, random, postop);
               }
            }

            for (int ix = 0; ix < 3; ix++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               if (worldIn.getBlockState(postop.down()).getBlock() == BlocksRegister.SLUDGE || random.nextFloat() < 0.6) {
                  this.mucoph.generate(worldIn, random, postop);
               }
            }

            if (random.nextFloat() < 0.5) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               if (worldIn.getBlockState(postop.down()).getBlock() == BlocksRegister.JUNK || random.nextFloat() < 0.2) {
                  this.junkweed.generate(worldIn, random, postop);
               }
            }

            if (random.nextFloat() < 0.3) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.weep.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.2) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.toxinia.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            for (int ixx = 0; ixx < 3; ixx++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.tallgrass.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.15) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.toxiBULB.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
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
