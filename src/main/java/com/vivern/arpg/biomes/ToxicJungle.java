package com.vivern.arpg.biomes;

import baubles.api.BaublesApi;
import com.vivern.arpg.blocks.ChlorineBelcher;
import com.vivern.arpg.dimensions.generationutils.WorldGenCaveLiquids;
import com.vivern.arpg.dimensions.generationutils.WorldGenGroundFoliage;
import com.vivern.arpg.dimensions.generationutils.WorldGenShrubFixed;
import com.vivern.arpg.dimensions.generationutils.WorldGenToxicBarrels;
import com.vivern.arpg.dimensions.toxicomania.ToxicomaniaChunkGenerator;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Mana;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
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

public class ToxicJungle extends BiomeControlled {
   public ToxicJungle() {
      super(new BiomeProperties("Toxic jungle").setBaseHeight(1.3F).setHeightVariation(0.9F).setTemperature(1.25F).setRainfall(0.9F).setWaterColor(5931421));
      this.topBlock = BlocksRegister.TOXICGRASS.getDefaultState();
      this.fillerBlock = BlocksRegister.TOXICDIRT.getDefaultState();
      this.decorator = new ToxicJungleDecorator();
   }

   @Override
   public IBlockState[] controlSurface(IBlockState top, IBlockState filler, double tgc) {
      top = ToxicomaniaChunkGenerator.GRASS;
      filler = ToxicomaniaChunkGenerator.DIRT;
      return new IBlockState[]{top, filler};
   }

   @Override
   public void onPlayer60ticksInBiome(BiomeControlled biome, EntityPlayer player) {
      if (BaublesApi.isBaubleEquipped(player, ItemsRegister.ANTIRADPACK) > -1) {
         if (player.getRNG().nextFloat() < 0.25) {
            Mana.addRad(player, 3, true);
         }
      } else {
         Mana.addRad(player, 3, true);
      }
   }

   class ToxicJungleDecorator extends BiomeDecorator {
      public WorldGenGroundFoliage arelia = new WorldGenGroundFoliage(BlocksRegister.ARELIA, 64, 7, 4);
      public WorldGenGroundFoliage deceidus = new WorldGenGroundFoliage(BlocksRegister.DECEIDUS, 20, 8, 5);
      public WorldGenGroundFoliage brownmuc = new WorldGenGroundFoliage(BlocksRegister.MUCOPHILLUSBROWN, 10, 8, 4);
      public WorldGenGroundFoliage mucoph = new WorldGenGroundFoliage(BlocksRegister.MUCOPHILLUS, 5, 8, 4);
      public WorldGenGroundFoliage viscosa = new WorldGenGroundFoliage(BlocksRegister.VISCOSA, 5, 8, 4);
      public WorldGenGroundFoliage weep = new WorldGenGroundFoliage(BlocksRegister.TOXIBERRYWEEPING, 20, 8, 4);
      public WorldGenGroundFoliage toxinia = new WorldGenGroundFoliage(BlocksRegister.TOXINIA, 32, 8, 4);
      public WorldGenGroundFoliage vibrant = new WorldGenGroundFoliage(BlocksRegister.TOXIBERRYVIBRANT, 15, 6, 5);
      public WorldGenGroundFoliage mosspl = new WorldGenGroundFoliage(BlocksRegister.MOSSPLANT, 32, 8, 5);
      public WorldGenToxicBarrels barrels = new WorldGenToxicBarrels(15, 4, 3);
      public WorldGenGroundFoliage tallgrass = new WorldGenGroundFoliage(BlocksRegister.TOXICTALLGRASS, 64, 8, 4);
      public WorldGenShrubFixed shrub = new WorldGenShrubFixed(BlocksRegister.TOXIBERRYLOG.getDefaultState(), BlocksRegister.TOXIBERRYLEAVES.getDefaultState());
      public WorldGenGroundFoliage poisonlily = new WorldGenGroundFoliage(BlocksRegister.POISONLILY, 10, 8, 0);

      public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
         if (this.decorating) {
            throw new RuntimeException("Already decorating");
         } else {
            for (int ji = 0; ji < 3; ji++) {
               if (random.nextFloat() < 0.5F) {
                  BlockPos position = worldIn.getTopSolidOrLiquidBlock(
                     new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
                  );
                  Block blockd = worldIn.getBlockState(position.down()).getBlock();
                  if (blockd == BlocksRegister.TOXICGRASS || blockd == BlocksRegister.TOXICDIRT || blockd == BlocksRegister.TOXIBERRYLOG) {
                     WorldServer worldServer = (WorldServer)worldIn;
                     MinecraftServer minecraftServer = worldIn.getMinecraftServer();
                     TemplateManager templateManager = worldServer.getStructureTemplateManager();
                     Template template = templateManager.get(
                        minecraftServer, new ResourceLocation("arpg:toxic_tree_jungle_" + (random.nextInt(2) + 1))
                     );
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

                     template.addBlocksToWorld(worldIn, position.add(sx * 8, 0, sz * 8), settings);
                  }
               }
            }

            if (random.nextFloat() < 0.25F) {
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

            if (random.nextFloat() < 0.5F) {
               BlockPos position = worldIn.getTopSolidOrLiquidBlock(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if (blockd == BlocksRegister.TOXICGRASS || blockd == BlocksRegister.TOXICDIRT || blockd == BlocksRegister.TOXIBERRYLOG) {
                  WorldServer worldServerxx = (WorldServer)worldIn;
                  MinecraftServer minecraftServerxx = worldIn.getMinecraftServer();
                  TemplateManager templateManagerxx = worldServerxx.getStructureTemplateManager();
                  int t = random.nextInt(4) + 1;
                  Template templatexx = templateManagerxx.get(minecraftServerxx, new ResourceLocation("arpg:toxic_tree_flower_" + t));
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

                  int disp = 3;
                  if (t == 1) {
                     disp = 5;
                  }

                  if (t == 2) {
                     disp = 8;
                  }

                  templatexx.addBlocksToWorld(worldIn, position.add(sxxx * disp, 0, szxx * disp), settingsxx);
               }
            }

            this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;

            for (int k5 = 0; k5 < 27; k5++) {
               int i10 = random.nextInt(16) + 8;
               int l13 = random.nextInt(16) + 8;
               int i17 = random.nextInt(248) + 8;
               if (i17 > 0) {
                  int k19 = random.nextInt(i17);
                  BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
                  new WorldGenCaveLiquids(BlocksRegister.FLUIDTOXIN, BlocksRegister.RADIOSTONE).generate(worldIn, random, blockpos6);
               }
            }

            for (int k5x = 0; k5x < 17; k5x++) {
               int i10 = random.nextInt(16) + 8;
               int l13 = random.nextInt(16) + 8;
               int i17 = random.nextInt(248) + 8;
               if (i17 > 0) {
                  int k19 = random.nextInt(i17);
                  BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
                  new WorldGenCaveLiquids(BlocksRegister.FLUIDSLIME, BlocksRegister.RADIOSTONE).generate(worldIn, random, blockpos6);
               }
            }

            if (random.nextFloat() < 0.25) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.barrels.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            for (int i = 0; i < 8; i++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getHeight(this.chunkPos.add(j, 0, k));
               Block bl = worldIn.getBlockState(postop.down()).getBlock();
               if (bl == BlocksRegister.TOXICGRASS || bl == BlocksRegister.TOXIBERRYLOG && random.nextFloat() < 0.3F) {
                  this.shrub.generate(worldIn, random, postop);
               }
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
               this.mosspl.generate(worldIn, random, postop);
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

            for (int ix = 0; ix < 3; ix++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos postop = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               if (worldIn.getBlockState(postop.down()).getBlock() == BlocksRegister.SLUDGE || random.nextFloat() < 0.7) {
                  this.brownmuc.generate(worldIn, random, postop);
               }
            }

            for (int ixx = 0; ixx < 3; ixx++) {
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
                  this.vibrant.generate(worldIn, random, postop);
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

            for (int ixxx = 0; ixxx < 2; ixxx++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.tallgrass.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.6) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.poisonlily
                  .generate(worldIn, random, new BlockPos(this.chunkPos.getX() + j, 63, this.chunkPos.getZ() + k));
            }

            if (random.nextFloat() < 0.04) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               ToxicomaniaChunkGenerator.genLootBlob(worldIn, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)), random);
            }

            for (int ixxx = 0; ixxx < 18; ixxx++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               GetMOP.BlockTraceResult res = random.nextFloat() < 0.5
                  ? GetMOP.blockTrace(
                     worldIn,
                     this.chunkPos.add(j, 62 + random.nextInt(40), k),
                     random.nextFloat() < 0.5 ? EnumFacing.UP : EnumFacing.DOWN,
                     30,
                     null
                  )
                  : GetMOP.blockTrace(
                     worldIn, this.chunkPos.add(j, 62 + random.nextInt(40), k), EnumFacing.byHorizontalIndex(random.nextInt(4)), 6, null
                  );
               if (res != null) {
                  if (res.prevState.getMaterial() == Material.AIR) {
                     if (GetMOP.SOLID_BLOCKS.apply(res.impactState)) {
                        worldIn.setBlockState(
                           res.prevPos, BlocksRegister.CHLORINEBELCHER.getDefaultState().withProperty(ChlorineBelcher.FACING, res.facing.getOpposite()), 2
                        );
                     }
                  } else if (res.impactState.getMaterial() == Material.AIR && GetMOP.SOLID_BLOCKS.apply(res.prevState)) {
                     worldIn.setBlockState(res.pos, BlocksRegister.CHLORINEBELCHER.getDefaultState().withProperty(ChlorineBelcher.FACING, res.facing), 2);
                  }
               }
            }

            if (random.nextFloat() < 0.02) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos poss = GetMOP.getTopBlock(worldIn, this.chunkPos.add(j, 90, k), BlocksRegister.TOXICGRASS);
               if (poss.getY() > 30) {
                  ToxicomaniaChunkGenerator.genNectarFlowerPlace(worldIn, poss.up(2), random);
               }

               if (random.nextFloat() < 0.35) {
                  this.barrels.generate(worldIn, random, poss.add(random.nextGaussian() * 2.0, 0.0, random.nextGaussian() * 2.0));
               }
            }

            this.decorating = false;
         }
      }
   }
}
