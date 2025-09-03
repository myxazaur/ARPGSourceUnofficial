//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.GetMOP;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent.Load;
import net.minecraftforge.event.world.ChunkEvent.Unload;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(
   modid = "arpg"
)
public class StaticRGBLight extends Thread {
   public static HashMap<Long, LoadedRGBChunk> loadedChunks = new HashMap<>();
   public static byte[] usedposes = new byte[33824];
   public static final int usposize = 16;
   public static int cofr = 0;
   static Random rand = new Random();
   public static Map<Block, Integer> mapColors = new HashMap<>();
   World world;
   BlockPos posup;
   LoadedRGBChunk chunkMid;
   LoadedRGBChunk chunkPlusX;
   LoadedRGBChunk chunkMinusX;
   LoadedRGBChunk chunkPlusZ;
   LoadedRGBChunk chunkMinusZ;
   LoadedRGBChunk chunkPlusXZ;
   LoadedRGBChunk chunkMinusXZ;
   LoadedRGBChunk chunkPluXMiuZ;
   LoadedRGBChunk chunkPluZMiuX;

   public StaticRGBLight(World world, BlockPos posup) {
      this.world = world;
      this.posup = posup;
   }

   public static void putOrRemoveChunk(long indx, LoadedRGBChunk loadedrgb, boolean remove) {
      if (remove) {
         loadedChunks.remove(indx);
      } else {
         loadedChunks.put(indx, loadedrgb);
      }
   }

   @Override
   public void run() {
      this.chunkMid = getActualLoadedRGBChunk(this.posup.getX(), this.posup.getZ());
      this.chunkPlusX = getActualLoadedRGBChunk(this.posup.getX() + 16, this.posup.getZ());
      this.chunkMinusX = getActualLoadedRGBChunk(this.posup.getX() - 16, this.posup.getZ());
      this.chunkPlusZ = getActualLoadedRGBChunk(this.posup.getX(), this.posup.getZ() + 16);
      this.chunkMinusZ = getActualLoadedRGBChunk(this.posup.getX(), this.posup.getZ() - 16);
      this.chunkPlusXZ = getActualLoadedRGBChunk(this.posup.getX() + 16, this.posup.getZ() + 16);
      this.chunkMinusXZ = getActualLoadedRGBChunk(this.posup.getX() - 16, this.posup.getZ() - 16);
      this.chunkPluXMiuZ = getActualLoadedRGBChunk(this.posup.getX() + 16, this.posup.getZ() - 16);
      this.chunkPluZMiuX = getActualLoadedRGBChunk(this.posup.getX() - 16, this.posup.getZ() + 16);
      List<LoadedRGBChunk> customList = new ArrayList<>();
      customList.add(this.chunkMid);
      customList.add(this.chunkPlusX);
      customList.add(this.chunkMinusX);
      customList.add(this.chunkPlusZ);
      customList.add(this.chunkMinusZ);
      customList.add(this.chunkPlusXZ);
      customList.add(this.chunkMinusXZ);
      customList.add(this.chunkPluXMiuZ);
      customList.add(this.chunkPluZMiuX);
      this.lightAbsorbingO(this.world, this.posup, customList);
      this.world
         .markBlockRangeForRenderUpdate(
            this.posup.getX() - 15,
            this.posup.getY() - 15,
            this.posup.getZ() - 15,
            this.posup.getX() + 15,
            this.posup.getY() + 15,
            this.posup.getZ() + 15
         );
   }

   public static int compileLightInit(int redSat, int greenSat, int blueSat, int redPower, int greenPower, int bluePower) {
      return redSat | greenSat << 4 | blueSat << 8 | redPower << 12 | greenPower << 16 | bluePower << 20;
   }

   public static void preInitLight() {
      mapColors.put(Blocks.TORCH, compileLightInit(8, 6, 2, 14, 14, 14));
      mapColors.put(Blocks.SEA_LANTERN, compileLightInit(6, 11, 11, 15, 15, 15));
      mapColors.put(Blocks.FIRE, compileLightInit(12, 8, 2, 15, 15, 15));
      mapColors.put(Blocks.GLOWSTONE, compileLightInit(13, 11, 4, 15, 15, 15));
      mapColors.put(Blocks.REDSTONE_TORCH, compileLightInit(10, 0, 0, 7, 0, 0));
      mapColors.put(Blocks.END_ROD, compileLightInit(9, 6, 10, 14, 14, 14));
      mapColors.put(Blocks.MAGMA, compileLightInit(15, 10, 3, 3, 3, 3));
      mapColors.put(Blocks.LAVA, compileLightInit(12, 6, 1, 15, 15, 15));
      mapColors.put(Blocks.FLOWING_LAVA, compileLightInit(12, 6, 1, 15, 15, 15));
      mapColors.put(Blocks.LIT_FURNACE, compileLightInit(9, 6, 2, 13, 13, 13));
      mapColors.put(Blocks.LIT_REDSTONE_ORE, compileLightInit(13, 0, 0, 9, 0, 0));
      mapColors.put(Blocks.PORTAL, compileLightInit(10, 0, 12, 11, 0, 11));
      mapColors.put(Blocks.LIT_PUMPKIN, compileLightInit(10, 6, 2, 15, 15, 15));
      mapColors.put(Blocks.LIT_REDSTONE_LAMP, compileLightInit(13, 10, 4, 15, 15, 15));
      mapColors.put(Blocks.ENDER_CHEST, compileLightInit(0, 6, 4, 0, 7, 7));
      mapColors.put(Blocks.BEACON, compileLightInit(4, 12, 14, 15, 15, 15));
      mapColors.put(Blocks.POWERED_COMPARATOR, compileLightInit(13, 0, 0, 9, 0, 0));
      mapColors.put(BlocksRegister.ALCHEMICLAB, compileLightInit(13, 0, 3, 7, 0, 7));
      mapColors.put(BlocksRegister.NUCLEARWASTE, compileLightInit(3, 13, 0, 6, 6, 0));
      mapColors.put(BlocksRegister.FLUIDCRYON, compileLightInit(2, 9, 15, 6, 6, 6));
      mapColors.put(BlocksRegister.OREMOLTEN, compileLightInit(13, 10, 2, 15, 15, 15));
      mapColors.put(BlocksRegister.BLUEGLOWINGMUSH, compileLightInit(3, 7, 15, 6, 6, 6));
      mapColors.put(BlocksRegister.GLOWINGCAVECRYSTALS, compileLightInit(3, 7, 15, 8, 8, 8));
      mapColors.put(BlocksRegister.MAGICSTONE, compileLightInit(13, 4, 12, 7, 7, 7));
      mapColors.put(BlocksRegister.CAVECRYSTALS, compileLightInit(13, 6, 12, 6, 6, 6));
      mapColors.put(BlocksRegister.MANABOTTLE, compileLightInit(4, 6, 8, 4, 4, 4));
      mapColors.put(BlocksRegister.DEMONICFIRE, compileLightInit(11, 0, 14, 12, 0, 12));
      mapColors.put(BlocksRegister.PORTALFROST, compileLightInit(0, 5, 7, 0, 5, 5));
      mapColors.put(BlocksRegister.FROZENCHANDELIER, compileLightInit(3, 8, 14, 10, 10, 10));
      mapColors.put(BlocksRegister.FROZENTORCH, compileLightInit(3, 8, 14, 11, 11, 11));
      mapColors.put(BlocksRegister.TOPAZCRYSTAL, compileLightInit(10, 3, 8, 5, 7, 7));
      mapColors.put(BlocksRegister.GREENGLOWINGMUSH, compileLightInit(2, 14, 4, 6, 6, 6));
      mapColors.put(BlocksRegister.LOPPYTOXIBERRY, compileLightInit(0, 6, 0, 0, 6, 0));
      mapColors.put(BlocksRegister.LOPPYTOXISTEM, compileLightInit(0, 6, 0, 0, 4, 0));
      mapColors.put(BlocksRegister.TOXICTORCH, compileLightInit(4, 15, 0, 12, 12, 0));
      mapColors.put(BlocksRegister.TOXICCHANDELIER, compileLightInit(4, 15, 0, 12, 12, 0));
      mapColors.put(BlocksRegister.CORALTORCH, compileLightInit(4, 14, 12, 14, 14, 14));
      mapColors.put(BlocksRegister.CORALCHANDELIER, compileLightInit(4, 14, 12, 12, 12, 12));
      mapColors.put(BlocksRegister.CRYSTALTORCH, compileLightInit(15, 6, 14, 15, 15, 15));
      mapColors.put(BlocksRegister.CRYSTALCHANDELIER, compileLightInit(15, 6, 14, 13, 13, 13));
      mapColors.put(BlocksRegister.BURNINGFROST, compileLightInit(0, 8, 14, 0, 6, 6));
      mapColors.put(BlocksRegister.PALMTORCH, compileLightInit(15, 7, 5, 13, 13, 13));
   }

   public static long getColor(IBlockState state) {
      if (state.getLightValue() <= 0) {
         return 0L;
      } else {
         Block block = state.getBlock();
         if (block == Blocks.TORCH) {
            return 571739713062030L;
         } else if (block == Blocks.SEA_LANTERN) {
            return 65970173379500L;
         } else if (block == Blocks.GLOWSTONE) {
            return 791639605927615L;
         } else if (block == Blocks.REDSTONE_TORCH) {
            return 219899808980000L;
         } else if (block == Blocks.END_ROD) {
            return 307859837429070L;
         } else if (block == Blocks.MAGMA) {
            return 175919880738240L;
         } else if (block == Blocks.BEACON) {
            return 131940053159330L;
         } else if (block == Blocks.LIT_PUMPKIN) {
            return 659699636654025L;
         } else if (block == Blocks.ENDER_CHEST) {
            return 65970173379485L;
         } else if (block == Blocks.END_PORTAL_FRAME) {
            return 43980010030240L;
         } else if (block == Blocks.PORTAL) {
            return 131939885388055L;
         } else if (block == BlocksRegister.FROZENCHANDELIER || block == BlocksRegister.FROZENTORCH) {
            return 153930096971570L;
         } else if (block == BlocksRegister.CAVECRYSTALS || block == BlocksRegister.MAGICSTONE) {
            return 153929887257430L;
         } else if (block == BlocksRegister.GLOWINGCAVECRYSTALS) {
            return 131940032187905L;
         } else if (block == BlocksRegister.BLUEGLOWINGMUSH) {
            return 87960038934805L;
         } else if (block == BlocksRegister.GREENGLOWINGMUSH) {
            return 65970068522437L;
         } else {
            return block == BlocksRegister.DEMONICFIRE
               ? 219899808980100L
               : ColorConverters.RGBtoDecimal2097140((int)Debugger.floats[0], (int)Debugger.floats[1], (int)Debugger.floats[2]);
         }
      }
   }

   public static void tryUpdateSunsetSunrise(ClientTickEvent event) {
   }

   public static void blockReplace(World world, IBlockState newstate, IBlockState laststate, BlockPos pos) {
   }

   public static void chunkload(Load e) {
      if (!e.getWorld().isRemote) {
         int x = e.getChunk().x;
         int z = e.getChunk().z;
         LoadedRGBChunk loadedrgb = new LoadedRGBChunk(x, z);
         putOrRemoveChunk(x | (long)z << 32, loadedrgb, false);
      }
   }

   public static void chunkunload(Unload e) {
      if (!e.getWorld().isRemote) {
         long x = e.getChunk().x;
         long z = e.getChunk().z;
         putOrRemoveChunk(x | z << 32, null, true);
      }
   }

   public static void recursiveUnglow(World world, int lastlight, BlockPos pos, BlockPos firstpos, byte lastfacing, EnumFacing backfacing) {
      int b = 0;

      for (byte i = lastfacing; b < 6; b++) {
         i = (byte)GetMOP.next(i, 1, 6);
         EnumFacing facing = EnumFacing.VALUES[i];
         if (facing != backfacing.getOpposite()) {
            BlockPos newpos = pos.offset(facing);
            IBlockState state = world.getBlockState(newpos);
            boolean canglow = lastlight
               > getValue1d3dlist(
                  newpos.getX() - firstpos.getX() + 16,
                  newpos.getY() - firstpos.getY() + 16,
                  newpos.getZ() - firstpos.getZ() + 16
               );
            if (world.getBlockLightOpacity(newpos) < 16 && lastlight > 0 && canglow && (!state.getMaterial().isLiquid() || state.getLightValue() <= 0)) {
               setValue1d3dlist(
                  newpos.getX() - firstpos.getX() + 16,
                  newpos.getY() - firstpos.getY() + 16,
                  newpos.getZ() - firstpos.getZ() + 16,
                  (byte)lastlight
               );
               recursiveUnglow(world, lastlight - 1, newpos, firstpos, i, facing);
            }
         }
      }
   }

   public static final LoadedRGBChunk getActualLoadedRGBChunk(List<LoadedRGBChunk> customList, int worldPosX, int worldPosZ) {
      int btccX = LoadedRGBChunk.blockTochunkCoord(worldPosX);
      int btccZ = LoadedRGBChunk.blockTochunkCoord(worldPosZ);

      for (LoadedRGBChunk loadedrgb : customList) {
         if (loadedrgb != null && loadedrgb.chunkPosX == btccX && loadedrgb.chunkPosZ == btccZ) {
            return loadedrgb;
         }
      }

      return null;
   }

   public static final LoadedRGBChunk getActualLoadedRGBChunk(int worldPosX, int worldPosZ) {
      long x = LoadedRGBChunk.blockTochunkCoord(worldPosX);
      long z = LoadedRGBChunk.blockTochunkCoord(worldPosZ);
      return loadedChunks.get(x | z << 32);
   }

   public static final LoadedRGBChunk getActualLoadedRGBChunk(Chunk chunk) {
      long x = chunk.x;
      long z = chunk.z;
      return loadedChunks.get(x | z << 32);
   }

   public static final LoadedRGBChunk getActualLoadedRGBChunkChpos(long chunkPosX, long chunkPosZ) {
      return loadedChunks.get(chunkPosX | chunkPosZ << 32);
   }

   public static final Vec3d getAdditiveColorInPos(BlockPos pos) {
      LoadedRGBChunk loadedrgb = getActualLoadedRGBChunk(pos.getX(), pos.getZ());
      if (loadedrgb != null) {
         float r = LoadedRGBChunk.finalColorAdditive(
            loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordRed(pos.getX(), pos.getY(), pos.getZ()))
         );
         float g = LoadedRGBChunk.finalColorAdditive(
            loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordGreen(pos.getX(), pos.getY(), pos.getZ()))
         );
         float b = LoadedRGBChunk.finalColorAdditive(
            loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordBlue(pos.getX(), pos.getY(), pos.getZ()))
         );
         return new Vec3d(r, g, b);
      } else {
         return new Vec3d(0.0, 0.0, 0.0);
      }
   }

   public final void lightAbsorbingO(World world, BlockPos posup, List<LoadedRGBChunk> customList) {
      int xin = posup.getX();
      int yin = posup.getY();
      int zin = posup.getZ();
      List<LightOnPos> lights = new ArrayList<>();

      for (int l = 0; l < 15; l++) {
         for (int xc = -14; xc < 15; xc++) {
            int X = xin + xc;
            int bccfX = blockToInchunkCoords(X);
            int xshift = 0;
            if (xc > 0 && bccfX < blockToInchunkCoords(xin)) {
               xshift = 1;
            } else if (xc < 0 && bccfX > blockToInchunkCoords(xin)) {
               xshift = -1;
            }

            for (int zc = -14; zc < 15; zc++) {
               int Z = zin + zc;
               int bccfZ = blockToInchunkCoords(Z);
               int zshift = 0;
               if (zc > 0 && bccfZ < blockToInchunkCoords(zin)) {
                  zshift = 1;
               } else if (zc < 0 && bccfZ > blockToInchunkCoords(zin)) {
                  zshift = -1;
               }

               LoadedRGBChunk chunk = xshift == 0
                  ? (zshift == 0 ? this.chunkMid : (zshift == 1 ? this.chunkPlusZ : this.chunkMinusZ))
                  : (
                     xshift == 1
                        ? (zshift == 0 ? this.chunkPlusX : (zshift == 1 ? this.chunkPlusXZ : this.chunkPluXMiuZ))
                        : (zshift == 0 ? this.chunkMinusX : (zshift == 1 ? this.chunkPluZMiuX : this.chunkMinusXZ))
                  );
               if (chunk != null) {
                  for (int yc = -14; yc < 15; yc++) {
                     if (Math.abs(xc) + Math.abs(zc) + Math.abs(yc) < 16) {
                        int Y = yin + yc;
                        int bccfY = MathHelper.clamp(Y, 0, 255);
                        if (l == 0) {
                           Block block = world.getBlockState(new BlockPos(X, Y, Z)).getBlock();
                           int lightt = mapColors.getOrDefault(block, 0);
                           int bkcoordRed = bccfX | bccfZ << 4 | bccfY << 8;
                           int bkcoordGreen = bkcoordRed | 65536;
                           int bkcoordBlue = bkcoordRed | 131072;
                           chunk.arrXYZ[bkcoordRed] = 0L;
                           chunk.arrXYZ[bkcoordGreen] = 0L;
                           chunk.arrXYZ[bkcoordBlue] = 0L;
                           if (lightt != 0) {
                              chunk.setLight(bkcoordRed, (byte)(lightt & 15), (byte)((lightt & 61440) >>> 12));
                              chunk.setLight(bkcoordGreen, (byte)((lightt & 240) >>> 4), (byte)((lightt & 983040) >>> 16));
                              chunk.setLight(bkcoordBlue, (byte)((lightt & 3840) >>> 8), (byte)((lightt & 15728640) >>> 20));
                           }
                        } else {
                           int opac = world.getChunk(X >> 4, Z >> 4).getBlockLightOpacity(new BlockPos(X, Y, Z));
                           if (opac < 250) {
                              int coordRED = bccfX | bccfZ << 4 | bccfY << 8;
                              int coordGREEN = coordRED | 65536;
                              int coordBLUE = coordRED | 131072;

                              for (byte i = 0; i < 64; i = (byte)(i + 4)) {
                                 long shifted = 15L << i;
                                 byte redOnCurrentPos = (byte)((chunk.arrXYZ[coordRED] & shifted) >>> i);
                                 byte combinedRed = 0;
                                 byte greenOnCurrentPos = (byte)((chunk.arrXYZ[coordGREEN] & shifted) >>> i);
                                 byte combinedGreen = 0;
                                 byte blueOnCurrentPos = (byte)((chunk.arrXYZ[coordBLUE] & shifted) >>> i);
                                 byte combinedBlue = 0;
                                 int offsX = X + 1;
                                 int bccofsX = blockToInchunkCoords(offsX);
                                 LoadedRGBChunk chunk2 = Math.abs(bccofsX - bccfX) > 1 ? getActualLoadedRGBChunk(customList, offsX, Z) : chunk;
                                 if (chunk2 != null) {
                                    int oFcoordRED = bccofsX | bccfZ << 4 | bccfY << 8;
                                    int oFcoordGREEN = oFcoordRED | 65536;
                                    int oFcoordBLUE = oFcoordRED | 131072;
                                    byte redOnOffsetPos = (byte)((chunk2.arrXYZ[oFcoordRED] & shifted) >>> i);
                                    byte greenOnOffsetPos = (byte)((chunk2.arrXYZ[oFcoordGREEN] & shifted) >>> i);
                                    byte blueOnOffsetPos = (byte)((chunk2.arrXYZ[oFcoordBLUE] & shifted) >>> i);
                                    if (combinedRed < redOnOffsetPos) {
                                       combinedRed = redOnOffsetPos;
                                    }

                                    if (combinedGreen < greenOnOffsetPos) {
                                       combinedGreen = greenOnOffsetPos;
                                    }

                                    if (combinedBlue < blueOnOffsetPos) {
                                       combinedBlue = blueOnOffsetPos;
                                    }
                                 }

                                 offsX = X - 1;
                                 bccofsX = blockToInchunkCoords(offsX);
                                 chunk2 = Math.abs(bccofsX - bccfX) > 1 ? getActualLoadedRGBChunk(customList, offsX, Z) : chunk;
                                 if (chunk2 != null) {
                                    int oFcoordREDx = bccofsX | bccfZ << 4 | bccfY << 8;
                                    int oFcoordGREENx = oFcoordREDx | 65536;
                                    int oFcoordBLUEx = oFcoordREDx | 131072;
                                    byte redOnOffsetPosx = (byte)((chunk2.arrXYZ[oFcoordREDx] & shifted) >>> i);
                                    byte greenOnOffsetPosx = (byte)((chunk2.arrXYZ[oFcoordGREENx] & shifted) >>> i);
                                    byte blueOnOffsetPosx = (byte)((chunk2.arrXYZ[oFcoordBLUEx] & shifted) >>> i);
                                    if (combinedRed < redOnOffsetPosx) {
                                       combinedRed = redOnOffsetPosx;
                                    }

                                    if (combinedGreen < greenOnOffsetPosx) {
                                       combinedGreen = greenOnOffsetPosx;
                                    }

                                    if (combinedBlue < blueOnOffsetPosx) {
                                       combinedBlue = blueOnOffsetPosx;
                                    }
                                 }

                                 offsX = Y + 1;
                                 bccofsX = MathHelper.clamp(offsX, 0, 255);
                                 int oFcoordREDxx = bccfX | bccfZ << 4 | bccofsX << 8;
                                 int oFcoordGREENxx = oFcoordREDxx | 65536;
                                 int oFcoordBLUExx = oFcoordREDxx | 131072;
                                 byte redOnOffsetPosxx = (byte)((chunk.arrXYZ[oFcoordREDxx] & shifted) >>> i);
                                 byte greenOnOffsetPosxx = (byte)((chunk.arrXYZ[oFcoordGREENxx] & shifted) >>> i);
                                 byte blueOnOffsetPosxx = (byte)((chunk.arrXYZ[oFcoordBLUExx] & shifted) >>> i);
                                 if (combinedRed < redOnOffsetPosxx) {
                                    combinedRed = redOnOffsetPosxx;
                                 }

                                 if (combinedGreen < greenOnOffsetPosxx) {
                                    combinedGreen = greenOnOffsetPosxx;
                                 }

                                 if (combinedBlue < blueOnOffsetPosxx) {
                                    combinedBlue = blueOnOffsetPosxx;
                                 }

                                 offsX = Y - 1;
                                 bccofsX = MathHelper.clamp(offsX, 0, 255);
                                 int oFcoordREDxxx = bccfX | bccfZ << 4 | bccofsX << 8;
                                 oFcoordGREENxx = oFcoordREDxxx | 65536;
                                 oFcoordBLUExx = oFcoordREDxxx | 131072;
                                 redOnOffsetPosxx = (byte)((chunk.arrXYZ[oFcoordREDxxx] & shifted) >>> i);
                                 greenOnOffsetPosxx = (byte)((chunk.arrXYZ[oFcoordGREENxx] & shifted) >>> i);
                                 blueOnOffsetPosxx = (byte)((chunk.arrXYZ[oFcoordBLUExx] & shifted) >>> i);
                                 if (combinedRed < redOnOffsetPosxx) {
                                    combinedRed = redOnOffsetPosxx;
                                 }

                                 if (combinedGreen < greenOnOffsetPosxx) {
                                    combinedGreen = greenOnOffsetPosxx;
                                 }

                                 if (combinedBlue < blueOnOffsetPosxx) {
                                    combinedBlue = blueOnOffsetPosxx;
                                 }

                                 offsX = Z + 1;
                                 bccofsX = blockToInchunkCoords(offsX);
                                 chunk2 = Math.abs(bccofsX - bccfZ) > 1 ? getActualLoadedRGBChunk(customList, X, offsX) : chunk;
                                 if (chunk2 != null) {
                                    oFcoordGREENxx = bccfX | bccofsX << 4 | bccfY << 8;
                                    oFcoordBLUExx = oFcoordGREENxx | 65536;
                                    redOnOffsetPosxx = (byte)(oFcoordGREENxx | 131072);
                                    greenOnOffsetPosxx = (byte)((chunk2.arrXYZ[oFcoordGREENxx] & shifted) >>> i);
                                    blueOnOffsetPosxx = (byte)((chunk2.arrXYZ[oFcoordBLUExx] & shifted) >>> i);
                                    byte blueOnOffsetPosxxx = (byte)((chunk2.arrXYZ[redOnOffsetPosxx] & shifted) >>> i);
                                    if (combinedRed < greenOnOffsetPosxx) {
                                       combinedRed = greenOnOffsetPosxx;
                                    }

                                    if (combinedGreen < blueOnOffsetPosxx) {
                                       combinedGreen = blueOnOffsetPosxx;
                                    }

                                    if (combinedBlue < blueOnOffsetPosxxx) {
                                       combinedBlue = blueOnOffsetPosxxx;
                                    }
                                 }

                                 offsX = Z - 1;
                                 bccofsX = blockToInchunkCoords(offsX);
                                 chunk2 = Math.abs(bccofsX - bccfZ) > 1 ? getActualLoadedRGBChunk(customList, X, offsX) : chunk;
                                 if (chunk2 != null) {
                                    oFcoordGREENxx = bccfX | bccofsX << 4 | bccfY << 8;
                                    oFcoordBLUExx = oFcoordGREENxx | 65536;
                                    redOnOffsetPosxx = (byte)(oFcoordGREENxx | 131072);
                                    greenOnOffsetPosxx = (byte)((chunk2.arrXYZ[oFcoordGREENxx] & shifted) >>> i);
                                    blueOnOffsetPosxx = (byte)((chunk2.arrXYZ[oFcoordBLUExx] & shifted) >>> i);
                                    byte blueOnOffsetPosxxxx = (byte)((chunk2.arrXYZ[redOnOffsetPosxx] & shifted) >>> i);
                                    if (combinedRed < greenOnOffsetPosxx) {
                                       combinedRed = greenOnOffsetPosxx;
                                    }

                                    if (combinedGreen < blueOnOffsetPosxx) {
                                       combinedGreen = blueOnOffsetPosxx;
                                    }

                                    if (combinedBlue < blueOnOffsetPosxxxx) {
                                       combinedBlue = blueOnOffsetPosxxxx;
                                    }
                                 }

                                 combinedRed--;
                                 if (combinedRed > 0 && combinedRed > redOnCurrentPos) {
                                    lights.add(new LightOnPos(coordRED, i, combinedRed, chunk));
                                 }

                                 combinedGreen--;
                                 if (combinedGreen > 0 && combinedGreen > greenOnCurrentPos) {
                                    lights.add(new LightOnPos(coordGREEN, i, combinedGreen, chunk));
                                 }

                                 combinedBlue--;
                                 if (combinedBlue > 0 && combinedBlue > blueOnCurrentPos) {
                                    lights.add(new LightOnPos(coordBLUE, i, combinedBlue, chunk));
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         for (LightOnPos lightOn : lights) {
            lightOn.chunk.arrXYZ[lightOn.bakedCoord] = lightOn.chunk.arrXYZ[lightOn.bakedCoord] & ~(15L << (int)lightOn.channel)
               | Math.max(lightOn.value, 0L) << (int)lightOn.channel;
         }

         lights.clear();
      }
   }

   public static final void lightAbsorbingChunkO(World world, Chunk chunkIn) {
      Debugger.resetPROFILING();
      Debugger.startPROFILING(1);
      int chunkX = chunkIn.x;
      int chunkZ = chunkIn.z;
      Debugger.startPROFILING(6);
      LoadedRGBChunk chunk = getActualLoadedRGBChunkChpos(chunkX, chunkZ);
      LoadedRGBChunk chunkPlusX = getActualLoadedRGBChunkChpos(chunkX + 1, chunkZ);
      LoadedRGBChunk chunkMinusX = getActualLoadedRGBChunkChpos(chunkX - 1, chunkZ);
      LoadedRGBChunk chunkPlusZ = getActualLoadedRGBChunkChpos(chunkX, chunkZ + 1);
      LoadedRGBChunk chunkMinusZ = getActualLoadedRGBChunkChpos(chunkX, chunkZ - 1);
      Debugger.endPROFILINGandADD(6);
      Stack<LightOnPos> lights = new Stack<>();
      int stacki = 0;

      for (int l = 0; l < 15; l++) {
         for (int bccfX = 0; bccfX < 16; bccfX++) {
            int X = (chunkX << 4) + bccfX;

            for (int bccfZ = 0; bccfZ < 16; bccfZ++) {
               int Z = (chunkZ << 4) + bccfZ;
               if (chunk != null) {
                  for (int Y = 0; Y < 256; Y++) {
                     if (l == 0) {
                        Debugger.startPROFILING(2);
                        int lightt = mapColors.getOrDefault(chunkIn.getBlockState(X, Y, Z).getBlock(), 0);
                        Debugger.endPROFILINGandADD(2);
                        int bkcoordRed = bccfX | bccfZ << 4 | Y << 8;
                        int bkcoordGreen = bkcoordRed | 65536;
                        int bkcoordBlue = bkcoordRed | 131072;
                        chunk.arrXYZ[bkcoordRed] = 0L;
                        chunk.arrXYZ[bkcoordGreen] = 0L;
                        chunk.arrXYZ[bkcoordBlue] = 0L;
                        Debugger.startPROFILING(5);
                        if (lightt != 0) {
                           chunk.setLight(bkcoordRed, (long)(lightt & 15), (long)((lightt & 61440) >>> 12));
                           chunk.setLight(bkcoordGreen, (long)((lightt & 240) >>> 4), (long)((lightt & 983040) >>> 16));
                           chunk.setLight(bkcoordBlue, (long)((lightt & 3840) >>> 8), (long)((lightt & 15728640) >>> 20));
                        }

                        Debugger.endPROFILINGandADD(5);
                     } else {
                        Debugger.startPROFILING(3);
                        int opac = chunkIn.getBlockState(X, Y, Z).getLightOpacity();
                        Debugger.endPROFILINGandADD(3);
                        if (opac < 250) {
                           int coordRED = bccfX | bccfZ << 4 | Y << 8;
                           int coordGREEN = coordRED | 65536;
                           int coordBLUE = coordRED | 131072;
                           Debugger.startPROFILING(7);

                           for (int i = 0; i < 64; i += 4) {
                              long shifted = 15L << i;
                              long redOnCurrentPos = (chunk.arrXYZ[coordRED] & shifted) >>> i;
                              long combinedRed = 0L;
                              long greenOnCurrentPos = (chunk.arrXYZ[coordGREEN] & shifted) >>> i;
                              long combinedGreen = 0L;
                              long blueOnCurrentPos = (chunk.arrXYZ[coordBLUE] & shifted) >>> i;
                              long combinedBlue = 0L;
                              Debugger.startPROFILING(4);
                              int offsX = X + 1;
                              int bccofsX = blockToInchunkCoords(offsX);
                              LoadedRGBChunk chunk2 = Math.abs(bccofsX - bccfX) > 1 ? chunkPlusX : chunk;
                              if (chunk2 != null) {
                                 int oFcoordRED = bccofsX | bccfZ << 4 | Y << 8;
                                 int oFcoordGREEN = oFcoordRED | 65536;
                                 int oFcoordBLUE = oFcoordRED | 131072;
                                 Debugger.startPROFILING(11);
                                 long redOnOffsetPos = (chunk2.arrXYZ[oFcoordRED] & shifted) >>> i;
                                 Debugger.endPROFILINGandADD(11);
                                 long greenOnOffsetPos = (chunk2.arrXYZ[oFcoordGREEN] & shifted) >>> i;
                                 long blueOnOffsetPos = (chunk2.arrXYZ[oFcoordBLUE] & shifted) >>> i;
                                 if (combinedRed < redOnOffsetPos) {
                                    combinedRed = redOnOffsetPos;
                                 }

                                 if (combinedGreen < greenOnOffsetPos) {
                                    combinedGreen = greenOnOffsetPos;
                                 }

                                 if (combinedBlue < blueOnOffsetPos) {
                                    combinedBlue = blueOnOffsetPos;
                                 }
                              }

                              Debugger.endPROFILINGandADD(4);
                              offsX = X - 1;
                              bccofsX = blockToInchunkCoords(offsX);
                              chunk2 = Math.abs(bccofsX - bccfX) > 1 ? chunkMinusX : chunk;
                              if (chunk2 != null) {
                                 int oFcoordREDx = bccofsX | bccfZ << 4 | Y << 8;
                                 int oFcoordGREENx = oFcoordREDx | 65536;
                                 int oFcoordBLUEx = oFcoordREDx | 131072;
                                 long redOnOffsetPosx = (chunk2.arrXYZ[oFcoordREDx] & shifted) >>> i;
                                 long greenOnOffsetPosx = (chunk2.arrXYZ[oFcoordGREENx] & shifted) >>> i;
                                 long blueOnOffsetPosx = (chunk2.arrXYZ[oFcoordBLUEx] & shifted) >>> i;
                                 if (combinedRed < redOnOffsetPosx) {
                                    combinedRed = redOnOffsetPosx;
                                 }

                                 if (combinedGreen < greenOnOffsetPosx) {
                                    combinedGreen = greenOnOffsetPosx;
                                 }

                                 if (combinedBlue < blueOnOffsetPosx) {
                                    combinedBlue = blueOnOffsetPosx;
                                 }
                              }

                              offsX = Y + 1;
                              bccofsX = MathHelper.clamp(offsX, 0, 255);
                              int oFcoordREDxx = bccfX | bccfZ << 4 | bccofsX << 8;
                              int oFcoordGREENxx = oFcoordREDxx | 65536;
                              int oFcoordBLUExx = oFcoordREDxx | 131072;
                              long redOnOffsetPosxx = (chunk.arrXYZ[oFcoordREDxx] & shifted) >>> i;
                              long greenOnOffsetPosxx = (chunk.arrXYZ[oFcoordGREENxx] & shifted) >>> i;
                              long blueOnOffsetPosxx = (chunk.arrXYZ[oFcoordBLUExx] & shifted) >>> i;
                              if (combinedRed < redOnOffsetPosxx) {
                                 combinedRed = redOnOffsetPosxx;
                              }

                              if (combinedGreen < greenOnOffsetPosxx) {
                                 combinedGreen = greenOnOffsetPosxx;
                              }

                              if (combinedBlue < blueOnOffsetPosxx) {
                                 combinedBlue = blueOnOffsetPosxx;
                              }

                              offsX = Y - 1;
                              bccofsX = MathHelper.clamp(offsX, 0, 255);
                              int oFcoordREDxxx = bccfX | bccfZ << 4 | bccofsX << 8;
                              long redOnOffsetPosxxx = (chunk.arrXYZ[oFcoordREDxxx] & shifted) >>> i;
                              redOnOffsetPosxx = (chunk.arrXYZ[oFcoordREDxxx | 65536] & shifted) >>> i;
                              greenOnOffsetPosxx = (chunk.arrXYZ[oFcoordREDxxx | 131072] & shifted) >>> i;
                              if (combinedRed < redOnOffsetPosxxx) {
                                 combinedRed = redOnOffsetPosxxx;
                              }

                              if (combinedGreen < redOnOffsetPosxx) {
                                 combinedGreen = redOnOffsetPosxx;
                              }

                              if (combinedBlue < greenOnOffsetPosxx) {
                                 combinedBlue = greenOnOffsetPosxx;
                              }

                              offsX = Z + 1;
                              bccofsX = blockToInchunkCoords(offsX);
                              chunk2 = Math.abs(bccofsX - bccfZ) > 1 ? chunkPlusZ : chunk;
                              if (chunk2 != null) {
                                 oFcoordGREENxx = bccfX | bccofsX << 4 | Y << 8;
                                 oFcoordBLUExx = oFcoordGREENxx | 65536;
                                 int oFcoordBLUExxx = oFcoordGREENxx | 131072;
                                 long redOnOffsetPosxxxx = (chunk2.arrXYZ[oFcoordGREENxx] & shifted) >>> i;
                                 long greenOnOffsetPosxxx = (chunk2.arrXYZ[oFcoordBLUExx] & shifted) >>> i;
                                 long blueOnOffsetPosxxx = (chunk2.arrXYZ[oFcoordBLUExxx] & shifted) >>> i;
                                 if (combinedRed < redOnOffsetPosxxxx) {
                                    combinedRed = redOnOffsetPosxxxx;
                                 }

                                 if (combinedGreen < greenOnOffsetPosxxx) {
                                    combinedGreen = greenOnOffsetPosxxx;
                                 }

                                 if (combinedBlue < blueOnOffsetPosxxx) {
                                    combinedBlue = blueOnOffsetPosxxx;
                                 }
                              }

                              offsX = Z - 1;
                              bccofsX = blockToInchunkCoords(offsX);
                              chunk2 = Math.abs(bccofsX - bccfZ) > 1 ? chunkMinusZ : chunk;
                              if (chunk2 != null) {
                                 oFcoordGREENxx = bccfX | bccofsX << 4 | Y << 8;
                                 oFcoordBLUExx = oFcoordGREENxx | 65536;
                                 int oFcoordBLUExxxx = oFcoordGREENxx | 131072;
                                 long redOnOffsetPosxxxxx = (chunk2.arrXYZ[oFcoordGREENxx] & shifted) >>> i;
                                 long greenOnOffsetPosxxxx = (chunk2.arrXYZ[oFcoordBLUExx] & shifted) >>> i;
                                 long blueOnOffsetPosxxxx = (chunk2.arrXYZ[oFcoordBLUExxxx] & shifted) >>> i;
                                 if (combinedRed < redOnOffsetPosxxxxx) {
                                    combinedRed = redOnOffsetPosxxxxx;
                                 }

                                 if (combinedGreen < greenOnOffsetPosxxxx) {
                                    combinedGreen = greenOnOffsetPosxxxx;
                                 }

                                 if (combinedBlue < blueOnOffsetPosxxxx) {
                                    combinedBlue = blueOnOffsetPosxxxx;
                                 }
                              }

                              combinedRed--;
                              if (combinedRed > 0L && combinedRed > redOnCurrentPos) {
                                 lights.push(new LightOnPos(coordRED, i, combinedRed, chunk));
                                 stacki++;
                              }

                              combinedGreen--;
                              if (combinedGreen > 0L && combinedGreen > greenOnCurrentPos) {
                                 lights.push(new LightOnPos(coordGREEN, i, combinedGreen, chunk));
                                 stacki++;
                              }

                              combinedBlue--;
                              if (combinedBlue > 0L && combinedBlue > blueOnCurrentPos) {
                                 lights.push(new LightOnPos(coordBLUE, i, combinedBlue, chunk));
                                 stacki++;
                              }
                           }

                           Debugger.endPROFILINGandADD(7);
                        }
                     }
                  }
               }
            }
         }

         Debugger.startPROFILING(8);

         while (stacki > 0) {
            LightOnPos lightOn = lights.pop();
            lightOn.chunk.arrXYZ[lightOn.bakedCoord] = lightOn.chunk.arrXYZ[lightOn.bakedCoord] & ~(15L << (int)lightOn.channel)
               | Math.max(lightOn.value, 0L) << (int)lightOn.channel;
            stacki--;
         }

         Debugger.endPROFILINGandADD(8);
      }

      Debugger.endPROFILINGandADD(1);
      Debugger.allPROFILINGandPRINT();
   }

   public static final int blockToInchunkCoords(int xz) {
      return xz & 15;
   }

   public static void clearLight3dList() {
      for (int xx = 0; xx < 33824; xx++) {
         usedposes[xx] = 0;
      }
   }

   public static void setLightIn3dListRelativeCenter(byte[] list, int x, int y, int z, byte light) {
   }

   public static byte getValue1d3dlist(int x, int y, int z) {
      return usedposes[x * 1024 + y * 32 + z];
   }

   public static void setValue1d3dlist(int x, int y, int z, byte light) {
      usedposes[x * 1024 + y * 32 + z] = light;
   }

   public static int getDayNightLight(World world, BlockPos pos) {
      if (world != null) {
         int i = world.getLightFor(EnumSkyBlock.SKY, pos) - world.getSkylightSubtracted();
         float f = world.getCelestialAngleRadians(1.0F);
         if (i > 0) {
            float f1 = f < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
            f += (f1 - f) * 0.2F;
            i = Math.round(i * MathHelper.cos(f));
         }

         return MathHelper.clamp(i, 0, 15);
      } else {
         return 15;
      }
   }
}
