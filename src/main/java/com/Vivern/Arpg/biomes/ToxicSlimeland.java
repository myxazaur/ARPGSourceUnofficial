//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import baubles.api.BaublesApi;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenCaveLiquids;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenFluidLake;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenGroundFoliage;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenSpread;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenToxicBarrels;
import com.Vivern.Arpg.dimensions.toxicomania.ToxicomaniaChunkGenerator;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Mana;
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

public class ToxicSlimeland extends BiomeControlled {
   public ToxicSlimeland() {
      super(new BiomeProperties("Slime lands").setBaseHeight(1.0F).setHeightVariation(0.2F).setTemperature(1.35F).setRainfall(0.9F).setWaterColor(5931421));
      this.topBlock = BlocksRegister.BROWNSLIME.getDefaultState();
      this.fillerBlock = BlocksRegister.BROWNSLIME.getDefaultState();
      this.decorator = new ToxicSlimelandDecorator();
   }

   @Override
   public IBlockState[] controlSurface(IBlockState top, IBlockState filler, double tgc) {
      if (tgc < 6.0) {
         top = ToxicomaniaChunkGenerator.SLIME;
         filler = ToxicomaniaChunkGenerator.SLIME;
      } else if (tgc < 7.0) {
         top = ToxicomaniaChunkGenerator.SLUDGE;
         filler = ToxicomaniaChunkGenerator.SLUDGE;
      }

      return new IBlockState[]{top, filler};
   }

   @Override
   public void onPlayer60ticksInBiome(BiomeControlled biome, EntityPlayer player) {
      Mana.addRad(player, BaublesApi.isBaubleEquipped(player, ItemsRegister.ANTIRADPACK) > -1 ? 10 : 40, true);
   }

   class ToxicSlimelandDecorator extends BiomeDecorator {
      public WorldGenToxicBarrels barrels = new WorldGenToxicBarrels(13, 4, 3);
      public WorldGenGroundFoliage poisonlily = new WorldGenGroundFoliage(BlocksRegister.POISONLILY, 8, 4, 0);
      public WorldGenSpread slimeblob = new WorldGenSpread(BlocksRegister.SLIMEBLOB, 16, 8, 4, BlocksRegister.BROWNSLIME);
      public WorldGenFluidLake lakeslime = new WorldGenFluidLake(BlocksRegister.FLUIDSLIME, null, null, BlocksRegister.BROWNSLIME, false, false);

      public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
         if (this.decorating) {
            throw new RuntimeException("Already decorating");
         } else {
            if (random.nextFloat() < 0.9F) {
               BlockPos position = worldIn.getTopSolidOrLiquidBlock(
                  new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
               );
               Block blockd = worldIn.getBlockState(position.down()).getBlock();
               if ((!worldIn.getBlockState(position).getMaterial().isLiquid() || random.nextFloat() < 0.3F) && blockd == BlocksRegister.BROWNSLIME) {
                  WorldServer worldServer = (WorldServer)worldIn;
                  MinecraftServer minecraftServer = worldIn.getMinecraftServer();
                  TemplateManager templateManager = worldServer.getStructureTemplateManager();
                  int t = random.nextInt(5) + 1;
                  Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:toxic_big_slime_" + t));
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

                  int disp = 1;
                  if (t == 3) {
                     disp = 3;
                  }

                  if (t == 4 || t == 5) {
                     disp = 5;
                  }

                  template.addBlocksToWorld(worldIn, position.add(sx * disp, 0, sz * disp), settings);
               }
            }

            this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;

            for (int k5 = 0; k5 < 20; k5++) {
               int i10 = random.nextInt(16) + 8;
               int l13 = random.nextInt(16) + 8;
               int i17 = random.nextInt(248) + 8;
               if (i17 > 0) {
                  int k19 = random.nextInt(i17);
                  BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
                  new WorldGenCaveLiquids(BlocksRegister.FLUIDTOXIN, BlocksRegister.RADIOSTONE).generate(worldIn, random, blockpos6);
               }
            }

            for (int k5x = 0; k5x < 45; k5x++) {
               int i10 = random.nextInt(16) + 8;
               int l13 = random.nextInt(16) + 8;
               int i17 = random.nextInt(248) + 8;
               if (i17 > 0) {
                  int k19 = random.nextInt(i17);
                  BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
                  new WorldGenCaveLiquids(BlocksRegister.FLUIDSLIME, BlocksRegister.RADIOSTONE).generate(worldIn, random, blockpos6);
               }
            }

            if (random.nextFloat() < 0.3) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.lakeslime.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)).down(4));
            }

            if (random.nextFloat() < 0.15) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.barrels.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.4) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.poisonlily
                  .generate(worldIn, random, new BlockPos(this.chunkPos.getX() + j, 63, this.chunkPos.getZ() + k));
            }

            for (int i = 0; i < 4; i++) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               BlockPos posup = worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
               Block block = worldIn.getBlockState(posup.down()).getBlock();
               if (block == BlocksRegister.BROWNSLIME || block == BlocksRegister.SLUDGE) {
                  this.slimeblob.generate(worldIn, random, posup);
               }
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
