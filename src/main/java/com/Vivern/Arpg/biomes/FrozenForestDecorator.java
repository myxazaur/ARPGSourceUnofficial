//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.blocks.FrostedWeed;
import com.Vivern.Arpg.dimensions.ethernalfrost.ChestReplacersFrozen;
import com.Vivern.Arpg.dimensions.generationutils.GenerationHelper;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenGroundFoliage;
import com.Vivern.Arpg.main.BlocksRegister;
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
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

class FrozenForestDecorator extends BiomeDecorator {
   public WorldGenGroundFoliage redberry = new WorldGenGroundFoliage(BlocksRegister.CRIMBERRY, 24, 8, 2);
   public WorldGenGroundFoliage willow = new WorldGenGroundFoliage(BlocksRegister.WINTERWILLOW, 23, 6, 2);
   public WorldGenGroundFoliage tallgrass = new WorldGenGroundFoliage(BlocksRegister.FROSTEDWEED, 32, 6, 2);
   public WorldGenGroundFoliage magicflower = new WorldGenGroundFoliage(BlocksRegister.ICEFLOWER, 20, 5, 2);

   public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         for (int ii = 0; ii < random.nextInt(7); ii++) {
            BlockPos position = worldIn.getHeight(
               new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
            );
            Block blockd = worldIn.getBlockState(position.down()).getBlock();
            if (blockd == BlocksRegister.SNOWICE || blockd == BlocksRegister.LOOSESNOW || blockd == Blocks.SNOW) {
               WorldServer worldServer = (WorldServer)worldIn;
               MinecraftServer minecraftServer = worldIn.getMinecraftServer();
               TemplateManager templateManager = worldServer.getStructureTemplateManager();
               Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:conifer_tree_" + (random.nextInt(3) + 1)));
               PlacementSettings settings = new PlacementSettings();
               int sx = -1;
               int sz = -1;
               int swr = random.nextInt(4);
               BlockPos transfpos = position;
               if (swr == 0) {
                  settings.setRotation(Rotation.CLOCKWISE_180);
                  sx = 1;
                  sz = 1;
                  transfpos = position.add(-1, 0, -1);
               }

               if (swr == 1) {
                  settings.setRotation(Rotation.CLOCKWISE_90);
                  sx = 1;
                  sz = -1;
                  transfpos = position.add(-1, 0, 0);
               }

               if (swr == 2) {
                  settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
                  sx = -1;
                  sz = 1;
                  transfpos = position.add(0, 0, -1);
               }

               if (swr == 3) {
                  settings.setRotation(Rotation.NONE);
               }

               template.addBlocksToWorld(worldIn, position.add(sx * 7, random.nextInt(6) + 4, sz * 7), settings);

               for (int rt = -4; rt < 14; rt++) {
                  BlockPos fpos = transfpos.up(rt);
                  if (!worldIn.isAirBlock(fpos) && worldIn.getBlockState(fpos).getBlock() != Blocks.SNOW) {
                     if (worldIn.getBlockState(fpos).getBlock() == BlocksRegister.CONIFERLOG) {
                        break;
                     }
                  } else {
                     worldIn.setBlockState(fpos, BlocksRegister.CONIFERLOG.getDefaultState());
                  }
               }

               for (int rtx = -2; rtx < 14; rtx++) {
                  BlockPos fpos = transfpos.add(1, 0, 1).up(rtx);
                  if (!worldIn.isAirBlock(fpos) && worldIn.getBlockState(fpos).getBlock() != Blocks.SNOW) {
                     if (worldIn.getBlockState(fpos).getBlock() == BlocksRegister.CONIFERLOG) {
                        break;
                     }
                  } else {
                     worldIn.setBlockState(fpos, BlocksRegister.CONIFERLOG.getDefaultState());
                  }
               }

               for (int rtxx = -2; rtxx < 14; rtxx++) {
                  BlockPos fpos = transfpos.add(0, 0, 1).up(rtxx);
                  if (!worldIn.isAirBlock(fpos) && worldIn.getBlockState(fpos).getBlock() != Blocks.SNOW) {
                     if (worldIn.getBlockState(fpos).getBlock() == BlocksRegister.CONIFERLOG) {
                        break;
                     }
                  } else {
                     worldIn.setBlockState(fpos, BlocksRegister.CONIFERLOG.getDefaultState());
                  }
               }

               for (int rtxxx = -2; rtxxx < 14; rtxxx++) {
                  BlockPos fpos = transfpos.add(1, 0, 0).up(rtxxx);
                  if (!worldIn.isAirBlock(fpos) && worldIn.getBlockState(fpos).getBlock() != Blocks.SNOW) {
                     if (worldIn.getBlockState(fpos).getBlock() == BlocksRegister.CONIFERLOG) {
                        break;
                     }
                  } else {
                     worldIn.setBlockState(fpos, BlocksRegister.CONIFERLOG.getDefaultState());
                  }
               }
            }
         }

         if (random.nextFloat() < 0.01) {
            BlockPos position = worldIn.getHeight(
               new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
            );
            Block block = worldIn.getBlockState(position.down()).getBlock();
            if (block == BlocksRegister.SNOWICE || block == Blocks.SNOW || block == BlocksRegister.GLACIER) {
               GenerationHelper.placeStruct(
                  worldIn, position, random, ":frozen_decor_" + (random.nextInt(5) + 1), 4, -1, random.nextInt(4), ChestReplacersFrozen.replacerStructures
               );
            }
         }

         if (random.nextFloat() < 0.005) {
            BlockPos position = worldIn.getHeight(
               new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
            );
            Block block = worldIn.getBlockState(position.down()).getBlock();
            if (block == BlocksRegister.SNOWICE || block == Blocks.SNOW || block == BlocksRegister.GLACIER) {
               GenerationHelper.placeStruct(
                  worldIn, position, random, ":frozen_grave_" + (random.nextInt(3) + 1), 4, -1, random.nextInt(4), ChestReplacersFrozen.replacerGrave
               );
            }
         }

         for (int iix = 0; iix < random.nextInt(28); iix++) {
            BlockPos uppos = worldIn.getHeight(pos.add(random.nextInt(16), 0, random.nextInt(16)));
            if (FrostedWeed.canStayAtPos(worldIn, uppos)) {
               worldIn.setBlockState(uppos, BlocksRegister.FROSTEDWEED.getDefaultState());
            }
         }

         this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
         this.chunkPos = pos;
         if (random.nextFloat() < 0.5) {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.tallgrass.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
         }

         if (random.nextFloat() < 0.14) {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.redberry.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
         }

         if (random.nextFloat() < 0.2) {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.willow.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
         }

         if (random.nextFloat() < 0.08) {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.magicflower.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
         }

         this.decorating = false;
      }
   }
}
