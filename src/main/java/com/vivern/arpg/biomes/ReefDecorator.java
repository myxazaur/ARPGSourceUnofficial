package com.vivern.arpg.biomes;

import com.vivern.arpg.dimensions.aquatica.AquaticaChunkGenerator;
import com.vivern.arpg.dimensions.generationutils.ReplaceOnlySofter;
import com.vivern.arpg.dimensions.generationutils.WorldGenCoral;
import com.vivern.arpg.dimensions.generationutils.WorldGenGroundFoliage;
import com.vivern.arpg.dimensions.generationutils.WorldGenShrubFixed;
import com.vivern.arpg.dimensions.generationutils.WorldGenSpread;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
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
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

class ReefDecorator extends BiomeDecorator {
   public WorldGenCoral coralGenerator1 = new WorldGenCoral(0.2F, 0.4F, BlocksRegister.CORALWHITE);
   public WorldGenCoral coralGenerator2 = new WorldGenCoral(0.2F, 0.3F, BlocksRegister.CORALPINK);
   public WorldGenGroundFoliage tallgrass = new WorldGenGroundFoliage(Blocks.TALLGRASS, 1, 32, 6, 4);
   public WorldGenShrubFixed shrub = new WorldGenShrubFixed(Blocks.LOG.getStateFromMeta(3), Blocks.LEAVES.getStateFromMeta(3));
   public WorldGenTrees trees1 = new WorldGenTrees(false, 6, Blocks.LOG.getStateFromMeta(3), Blocks.LEAVES.getStateFromMeta(3), true);
   public WorldGenTrees trees2 = new WorldGenTrees(false, 9, Blocks.LOG.getStateFromMeta(3), Blocks.LEAVES.getStateFromMeta(3), true);
   public WorldGenTrees trees3 = new WorldGenTrees(false, 12, Blocks.LOG.getStateFromMeta(3), Blocks.LEAVES.getStateFromMeta(3), true);
   public WorldGenSpread seagrass = new WorldGenSpread(BlocksRegister.SEAGRASS, 30, 5, 2, null, true);
   public NoiseGeneratorPerlin perlin = null;
   public long perlinseed = 0L;

   public void decorate(World world, Random random, Biome biome, BlockPos pos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         if (this.perlin == null || this.perlinseed != world.getSeed()) {
            this.perlin = new NoiseGeneratorPerlin(new Random(world.getSeed()), 2);
            this.perlinseed = world.getSeed();
         }

         if (random.nextFloat() < 0.35) {
            int l1 = random.nextInt(16) + 8;
            int i6 = random.nextInt(16) + 8;
            this.seagrass.generate(world, random, world.getTopSolidOrLiquidBlock(pos.add(l1, 0, i6)));
         }

         for (int isw = 0; isw < 3; isw++) {
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(pos.getX() + 8 + random.nextInt(16), 186, pos.getZ() + 8 + random.nextInt(16))
            );
            if (this.perlin.getValue(position.getX() / 50.0F, position.getZ() / 50.0F) > -0.8) {
               this.coralGenerator1.generate(world, random, position.up());
            } else {
               this.coralGenerator2.generate(world, random, position.up());
            }
         }

         if (random.nextFloat() < 0.18) {
            int height = 4 + random.nextInt(12);
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(pos.getX() + 8 + random.nextInt(16), 150, pos.getZ() + 8 + random.nextInt(16))
            );
            AquaticaChunkGenerator.genCellularCoral(
               world,
               position.getX(),
               position.getY(),
               position.getZ(),
               random,
               height * 2,
               height,
               AquaticaChunkGenerator.corals[random.nextInt(5)].getDefaultState()
            );
         }

         if (random.nextFloat() < 0.85) {
            BlockPos position = world.getHeight(new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16)));
            Block blockd = world.getBlockState(position.down()).getBlock();
            if (blockd == Blocks.SAND || blockd == Blocks.GRASS || blockd == Blocks.DIRT) {
               if ((blockd != Blocks.SAND || !(random.nextFloat() < 0.37)) && !(random.nextFloat() < 0.2)) {
                  if (random.nextFloat() < 0.8) {
                     WorldServer worldServer = (WorldServer)world;
                     MinecraftServer minecraftServer = world.getMinecraftServer();
                     TemplateManager templateManager = worldServer.getStructureTemplateManager();
                     int numm = random.nextInt(3) + 1;
                     Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:palm_small_" + numm));
                     int dispp = 6;
                     if (numm == 2) {
                        dispp = 4;
                     }

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

                     template.addBlocksToWorld(world, position.add(sx * dispp, 0, sz * dispp), ReplaceOnlySofter.instance, settings, 2);
                  } else {
                     WorldServer worldServerx = (WorldServer)world;
                     MinecraftServer minecraftServerx = world.getMinecraftServer();
                     TemplateManager templateManagerx = worldServerx.getStructureTemplateManager();
                     int nummx = random.nextInt(4) + 1;
                     Template templatex = templateManagerx.get(minecraftServerx, new ResourceLocation("arpg:palm_bottle_" + nummx));
                     int disppx = 3;
                     if (nummx == 3) {
                        disppx = 4;
                     }

                     if (nummx == 4) {
                        disppx = 4;
                     }

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

                     templatex.addBlocksToWorld(world, position.add(sxx * disppx, 0, szx * disppx), ReplaceOnlySofter.instance, settingsx, 2);
                  }
               } else {
                  WorldServer worldServerxx = (WorldServer)world;
                  MinecraftServer minecraftServerxx = world.getMinecraftServer();
                  TemplateManager templateManagerxx = worldServerxx.getStructureTemplateManager();
                  int nummxx = random.nextInt(3) + 1;
                  Template templatexx = templateManagerxx.get(minecraftServerxx, new ResourceLocation("arpg:palm_big_" + nummxx));
                  int disppxx = 10;
                  if (nummxx == 2) {
                     disppxx = 9;
                  }

                  if (nummxx == 3) {
                     disppxx = 8;
                  }

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

                  templatexx.addBlocksToWorld(world, position.add(sxxx * disppxx, 0, szxx * disppxx), settingsxx);
               }
            }
         }

         if (random.nextFloat() < 0.9) {
            BlockPos position = world.getHeight(new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16)));
            Block blockd = world.getBlockState(position.down()).getBlock();
            if (blockd == Blocks.GRASS || blockd == Blocks.DIRT) {
               if (random.nextFloat() < 0.1) {
                  WorldServer worldServerxxx = (WorldServer)world;
                  MinecraftServer minecraftServerxxx = world.getMinecraftServer();
                  TemplateManager templateManagerxxx = worldServerxxx.getStructureTemplateManager();
                  int nummxxx = random.nextInt(3) + 1;
                  Template templatexxx = templateManagerxxx.get(minecraftServerxxx, new ResourceLocation("arpg:palm_big_" + nummxxx));
                  int disppxxx = 10;
                  if (nummxxx == 2) {
                     disppxxx = 9;
                  }

                  if (nummxxx == 3) {
                     disppxxx = 8;
                  }

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

                  templatexxx.addBlocksToWorld(world, position.add(sxxxx * disppxxx, 0, szxxx * disppxxx), settingsxxx);
               } else if (random.nextFloat() < 0.5 && position.getY() < 213 && position.getY() > 198) {
                  WorldServer worldServerxxxx = (WorldServer)world;
                  MinecraftServer minecraftServerxxxx = world.getMinecraftServer();
                  TemplateManager templateManagerxxxx = worldServerxxxx.getStructureTemplateManager();
                  int nummxxxx = random.nextInt(3) + 4;
                  Template templatexxxx = templateManagerxxxx.get(minecraftServerxxxx, new ResourceLocation("arpg:palm_small_" + nummxxxx));
                  int disppxxxx = 6;
                  if (nummxxxx == 5) {
                     disppxxxx = 9;
                  }

                  if (nummxxxx == 6) {
                     disppxxxx = 11;
                  }

                  PlacementSettings settingsxxxx = new PlacementSettings();
                  int sxxxxx = -1;
                  int szxxxx = -1;
                  int swrxxxx = random.nextInt(4);
                  if (swrxxxx == 0) {
                     settingsxxxx.setRotation(Rotation.CLOCKWISE_180);
                     sxxxxx = 1;
                     szxxxx = 1;
                  }

                  if (swrxxxx == 1) {
                     settingsxxxx.setRotation(Rotation.CLOCKWISE_90);
                     sxxxxx = 1;
                     szxxxx = -1;
                  }

                  if (swrxxxx == 2) {
                     settingsxxxx.setRotation(Rotation.COUNTERCLOCKWISE_90);
                     sxxxxx = -1;
                     szxxxx = 1;
                  }

                  if (swrxxxx == 3) {
                     settingsxxxx.setRotation(Rotation.NONE);
                  }

                  templatexxxx.addBlocksToWorld(
                     world, position.add(sxxxxx * disppxxxx, 0, szxxxx * disppxxxx), ReplaceOnlySofter.instance, settingsxxxx, 2
                  );
               } else if (random.nextFloat() < 0.8) {
                  WorldServer worldServerxxxxx = (WorldServer)world;
                  MinecraftServer minecraftServerxxxxx = world.getMinecraftServer();
                  TemplateManager templateManagerxxxxx = worldServerxxxxx.getStructureTemplateManager();
                  int nummxxxxx = random.nextInt(3) + 1;
                  Template templatexxxxx = templateManagerxxxxx.get(minecraftServerxxxxx, new ResourceLocation("arpg:palm_small_" + nummxxxxx));
                  int disppxxxxx = 6;
                  if (nummxxxxx == 2) {
                     disppxxxxx = 4;
                  }

                  PlacementSettings settingsxxxxx = new PlacementSettings();
                  int sxxxxxx = -1;
                  int szxxxxx = -1;
                  int swrxxxxx = random.nextInt(4);
                  if (swrxxxxx == 0) {
                     settingsxxxxx.setRotation(Rotation.CLOCKWISE_180);
                     sxxxxxx = 1;
                     szxxxxx = 1;
                  }

                  if (swrxxxxx == 1) {
                     settingsxxxxx.setRotation(Rotation.CLOCKWISE_90);
                     sxxxxxx = 1;
                     szxxxxx = -1;
                  }

                  if (swrxxxxx == 2) {
                     settingsxxxxx.setRotation(Rotation.COUNTERCLOCKWISE_90);
                     sxxxxxx = -1;
                     szxxxxx = 1;
                  }

                  if (swrxxxxx == 3) {
                     settingsxxxxx.setRotation(Rotation.NONE);
                  }

                  templatexxxxx.addBlocksToWorld(
                     world, position.add(sxxxxxx * disppxxxxx, 0, szxxxxx * disppxxxxx), ReplaceOnlySofter.instance, settingsxxxxx, 2
                  );
               } else {
                  WorldServer worldServerxxxxxx = (WorldServer)world;
                  MinecraftServer minecraftServerxxxxxx = world.getMinecraftServer();
                  TemplateManager templateManagerxxxxxx = worldServerxxxxxx.getStructureTemplateManager();
                  int nummxxxxxx = random.nextInt(4) + 1;
                  Template templatexxxxxx = templateManagerxxxxxx.get(minecraftServerxxxxxx, new ResourceLocation("arpg:palm_bottle_" + nummxxxxxx));
                  int disppxxxxxx = 3;
                  if (nummxxxxxx == 3) {
                     disppxxxxxx = 4;
                  }

                  if (nummxxxxxx == 4) {
                     disppxxxxxx = 4;
                  }

                  PlacementSettings settingsxxxxxx = new PlacementSettings();
                  int sxxxxxxx = -1;
                  int szxxxxxx = -1;
                  int swrxxxxxx = random.nextInt(4);
                  if (swrxxxxxx == 0) {
                     settingsxxxxxx.setRotation(Rotation.CLOCKWISE_180);
                     sxxxxxxx = 1;
                     szxxxxxx = 1;
                  }

                  if (swrxxxxxx == 1) {
                     settingsxxxxxx.setRotation(Rotation.CLOCKWISE_90);
                     sxxxxxxx = 1;
                     szxxxxxx = -1;
                  }

                  if (swrxxxxxx == 2) {
                     settingsxxxxxx.setRotation(Rotation.COUNTERCLOCKWISE_90);
                     sxxxxxxx = -1;
                     szxxxxxx = 1;
                  }

                  if (swrxxxxxx == 3) {
                     settingsxxxxxx.setRotation(Rotation.NONE);
                  }

                  templatexxxxxx.addBlocksToWorld(
                     world, position.add(sxxxxxxx * disppxxxxxx, 0, szxxxxxx * disppxxxxxx), ReplaceOnlySofter.instance, settingsxxxxxx, 2
                  );
               }
            }
         }

         BlockPos position = world.getHeight(new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16)));
         Block blockd = world.getBlockState(position.down()).getBlock();
         if ((blockd == Blocks.GRASS || blockd == Blocks.DIRT) && position.getY() < 213 && position.getY() > 198) {
            WorldServer worldServerxxxxxxx = (WorldServer)world;
            MinecraftServer minecraftServerxxxxxxx = world.getMinecraftServer();
            TemplateManager templateManagerxxxxxxx = worldServerxxxxxxx.getStructureTemplateManager();
            int nummxxxxxxx = random.nextInt(3) + 4;
            Template templatexxxxxxx = templateManagerxxxxxxx.get(minecraftServerxxxxxxx, new ResourceLocation("arpg:palm_small_" + nummxxxxxxx));
            int disppxxxxxxx = 6;
            if (nummxxxxxxx == 5) {
               disppxxxxxxx = 9;
            }

            if (nummxxxxxxx == 6) {
               disppxxxxxxx = 11;
            }

            PlacementSettings settingsxxxxxxx = new PlacementSettings();
            int sxxxxxxxx = -1;
            int szxxxxxxx = -1;
            int swrxxxxxxx = random.nextInt(4);
            if (swrxxxxxxx == 0) {
               settingsxxxxxxx.setRotation(Rotation.CLOCKWISE_180);
               sxxxxxxxx = 1;
               szxxxxxxx = 1;
            }

            if (swrxxxxxxx == 1) {
               settingsxxxxxxx.setRotation(Rotation.CLOCKWISE_90);
               sxxxxxxxx = 1;
               szxxxxxxx = -1;
            }

            if (swrxxxxxxx == 2) {
               settingsxxxxxxx.setRotation(Rotation.COUNTERCLOCKWISE_90);
               sxxxxxxxx = -1;
               szxxxxxxx = 1;
            }

            if (swrxxxxxxx == 3) {
               settingsxxxxxxx.setRotation(Rotation.NONE);
            }

            templatexxxxxxx.addBlocksToWorld(
               world, position.add(sxxxxxxxx * disppxxxxxxx, 0, szxxxxxxx * disppxxxxxxx), ReplaceOnlySofter.instance, settingsxxxxxxx, 2
            );
         }

         this.chunkPos = pos;
         this.chunkProviderSettings = Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
         if (random.nextFloat() < 0.6) {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.trees1.generate(world, random, world.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
         }

         if (random.nextFloat() < 0.7) {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.trees2.generate(world, random, world.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
         }

         if (random.nextFloat() < 0.6) {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.trees3.generate(world, random, world.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
         }

         for (int i = 0; i < 5; i++) {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            BlockPos postop = world.getHeight(this.chunkPos.add(j, 0, k));
            Block bl = world.getBlockState(postop.down()).getBlock();
            if (bl == Blocks.GRASS || bl == Blocks.SAND && random.nextFloat() < 0.1F) {
               this.shrub.generate(world, random, postop);
            }
         }

         if (random.nextFloat() < 0.7) {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.tallgrass.generate(world, random, world.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
         }

         this.decorating = false;
      }
   }
}
