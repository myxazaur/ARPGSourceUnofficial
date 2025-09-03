//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package gloomyfolkenvivern.arpghooklib.example.coloredlightning;

import com.Vivern.Arpg.main.BlocksRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.BossInfo.Color;
import net.minecraftforge.event.world.ChunkEvent.Load;
import net.minecraftforge.event.world.ChunkEvent.Unload;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "arpg"
)
public class ColoredLightning {
   public static HashMap<Long, ColoredChunk> loadedChunks = new HashMap<>();
   public static Map<Block, ColorOfTheLitBlock> mapColors = new HashMap<>();
   public static ColoredThread colorCalculateThread;
   public static final ArrayList<BlockPos> updateRange = fillUpdateRange(new ArrayList<>(), false);
   public static final ArrayList<BlockPos> updateRangeCircle = fillUpdateRange(new ArrayList<>(), true);

   public static void doColorUpdate(int x, int y, int z, boolean isChunk, World world) {
      if (colorCalculateThread == null) {
         colorCalculateThread = new ColoredThread();
         colorCalculateThread.setName("colored_light_thread");
         colorCalculateThread.start();
      }

      colorCalculateThread.addTask(x, y, z, isChunk, world);
   }

   public static ColorOfTheLitBlock compileLightInit(int redSat, int greenSat, int blueSat, int redPower, int greenPower, int bluePower) {
      return new ColorOfTheLitBlock(redSat, greenSat, blueSat, redPower, greenPower, bluePower);
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

   public static Vec3d getAdditiveColorInPos(BlockPos pos) {
      int chunkX = pos.getX() >> 4;
      int chunkZ = pos.getZ() >> 4;
      ColoredChunk coloredChunk = getChunk(chunkX, chunkZ);
      if (coloredChunk != null) {
         int inChunkX = blockToInchunkCoords(pos.getX());
         int inChunkZ = blockToInchunkCoords(pos.getZ());
         return coloredChunk.getVec3dColorAt(inChunkX, MathHelper.clamp(pos.getY(), 0, 255), inChunkZ);
      } else {
         return Vec3d.ZERO;
      }
   }

   public static byte getSaturation(byte color) {
      return (byte)(color & 15);
   }

   public static byte getPower(byte color) {
      return (byte)(color >>> 4 & 15);
   }

   public static byte bakeColor(int saturation, int power) {
      return (byte)(saturation | power << 4);
   }

   public static void chunkload(Load e) {
      if (e.getWorld().isRemote) {
         int x = e.getChunk().x;
         int z = e.getChunk().z;
         loadedChunks.put(x | (long)z << 32, new ColoredChunk(x, z));
         doColorUpdate(x, 0, z, true, e.getWorld());
      }
   }

   public static void chunkunload(Unload e) {
      if (e.getWorld().isRemote) {
         long x = e.getChunk().x;
         long z = e.getChunk().z;
         loadedChunks.remove(x | z << 32);
      }
   }

   @Nullable
   public static ColoredChunk getChunk(int chunkX, int chunkZ) {
      return loadedChunks.get(chunkX | (long)chunkZ << 32);
   }

   @Nullable
   public static ColoredChunk getChunk(BlockPos pos) {
      int chunkX = pos.getX() >> 4;
      int chunkZ = pos.getZ() >> 4;
      return getChunk(chunkX, chunkZ);
   }

   public static boolean calculateColorsInRangeTest(World world, BlockPos centerpos) {
      ColoredChunk coloredChunk = null;

      for (BlockPos updateRelativePos : updateRangeCircle) {
         BlockPos pos = centerpos.add(updateRelativePos);
         coloredChunk = getChunk(pos);
         if (coloredChunk != null) {
            int light = Math.round(
               15.0F
                  - (float)Math.sqrt(
                     updateRelativePos.getX() * updateRelativePos.getX()
                        + updateRelativePos.getY() * updateRelativePos.getY()
                        + updateRelativePos.getZ() * updateRelativePos.getZ()
                  )
            );
            int inChunkX = blockToInchunkCoords(pos.getX());
            int inChunkZ = blockToInchunkCoords(pos.getZ());
            coloredChunk.setColorsAt(inChunkX, pos.getY(), inChunkZ, bakeColor(15, light), bakeColor(0, light), bakeColor(0, light));
         }
      }

      return true;
   }

   public static void calculateColorsInRangeAt(World world, BlockPos centerpos) {
      int chunkX = centerpos.getX() >> 4;
      int chunkZ = centerpos.getZ() >> 4;
      ColoredChunk coloredChunk = getChunk(chunkX, chunkZ);
      if (coloredChunk != null) {
         for (BlockPos updateRelativePos : updateRange) {
            BlockPos pos = centerpos.add(updateRelativePos);
            coloredChunk = getChunk(pos);
            if (coloredChunk != null) {
               IBlockState blockstate = world.getBlockState(pos);
               ColorOfTheLitBlock colorOfTheBlock = mapColors.get(blockstate.getBlock());
               int inChunkX = blockToInchunkCoords(pos.getX());
               int inChunkZ = blockToInchunkCoords(pos.getZ());
               if (colorOfTheBlock != null) {
                  coloredChunk.setColorsAt(
                     inChunkX,
                     pos.getY(),
                     inChunkZ,
                     bakeColor(colorOfTheBlock.redSat, colorOfTheBlock.redPower),
                     bakeColor(colorOfTheBlock.greenSat, colorOfTheBlock.greenPower),
                     bakeColor(colorOfTheBlock.blueSat, colorOfTheBlock.bluePower)
                  );
               } else {
                  coloredChunk.setColorsAt(inChunkX, pos.getY(), inChunkZ, (byte)0, (byte)0, (byte)0);
               }
            }
         }

         for (int i = 0; i < 14; i++) {
            for (BlockPos updateRelativePosx : updateRange) {
               BlockPos posMain = centerpos.add(updateRelativePosx);
               if (world.getBlockLightOpacity(posMain) < 255) {
                  int inChunkX = blockToInchunkCoords(posMain.getX());
                  int inChunkZ = blockToInchunkCoords(posMain.getZ());
                  ColoredChunk coloredChunkMain = getChunk(posMain);
                  if (coloredChunkMain != null) {
                     for (EnumFacing face : EnumFacing.VALUES) {
                        BlockPos posOffset = posMain.offset(face);
                        ColoredChunk coloredChunkOffset = getChunk(posOffset);
                        if (coloredChunkOffset != null) {
                           byte lastRed = coloredChunkOffset.getColorAt(posOffset, Color.RED);
                           byte lastGreen = coloredChunkOffset.getColorAt(posOffset, Color.GREEN);
                           byte lastBlue = coloredChunkOffset.getColorAt(posOffset, Color.BLUE);
                           coloredChunkMain.compareColorAt(inChunkX, posMain.getY(), inChunkZ, lastRed, lastGreen, lastBlue);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void calculateColorsInWholeChunk(World world, int chunkX, int chunkZ) {
      ColoredChunk coloredChunk = getChunk(chunkX, chunkZ);
      if (coloredChunk != null) {
         int xStart = chunkX << 4;
         int zStart = chunkZ << 4;

         for (int x = 0; x <= 15; x++) {
            for (int y = 0; y <= 255; y++) {
               for (int z = 0; z <= 15; z++) {
                  BlockPos pos = new BlockPos(xStart + x, y, zStart + z);
                  IBlockState blockstate = world.getBlockState(pos);
                  ColorOfTheLitBlock colorOfTheBlock = mapColors.get(blockstate.getBlock());
                  int inChunkX = blockToInchunkCoords(pos.getX());
                  int inChunkZ = blockToInchunkCoords(pos.getZ());
                  if (colorOfTheBlock != null) {
                     coloredChunk.setColorsAt(
                        inChunkX,
                        pos.getY(),
                        inChunkZ,
                        bakeColor(colorOfTheBlock.redSat, colorOfTheBlock.redPower),
                        bakeColor(colorOfTheBlock.greenSat, colorOfTheBlock.greenPower),
                        bakeColor(colorOfTheBlock.blueSat, colorOfTheBlock.bluePower)
                     );
                  } else {
                     coloredChunk.setColorsAt(inChunkX, pos.getY(), inChunkZ, (byte)0, (byte)0, (byte)0);
                  }
               }
            }
         }

         for (int i = 0; i < 14; i++) {
            for (int x = 0; x <= 15; x++) {
               for (int y = 0; y <= 255; y++) {
                  for (int zx = 0; zx <= 15; zx++) {
                     BlockPos posMain = new BlockPos(xStart + x, y, zStart + zx);
                     if (world.getBlockLightOpacity(posMain) < 255) {
                        for (EnumFacing face : EnumFacing.VALUES) {
                           BlockPos posOffset = posMain.offset(face);
                           ColoredChunk coloredChunkOffset = getChunk(posOffset);
                           if (coloredChunkOffset != null) {
                              byte lastRed = coloredChunkOffset.getColorAt(posOffset, Color.RED);
                              byte lastGreen = coloredChunkOffset.getColorAt(posOffset, Color.GREEN);
                              byte lastBlue = coloredChunkOffset.getColorAt(posOffset, Color.BLUE);
                              coloredChunk.compareColorAt(x, y, zx, lastRed, lastGreen, lastBlue);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static ArrayList<BlockPos> fillUpdateRange(ArrayList<BlockPos> updateRange, boolean distFunc) {
      for (int x = -15; x <= 15; x++) {
         for (int y = -15; y <= 15; y++) {
            for (int z = -15; z <= 15; z++) {
               if (distFunc) {
                  if (Math.abs(x) + Math.abs(y) + Math.abs(z) <= 15) {
                     updateRange.add(new BlockPos(x, y, z));
                  }
               } else if (x * x + y * y + z * z <= 225) {
                  updateRange.add(new BlockPos(x, y, z));
               }
            }
         }
      }

      return updateRange;
   }

   public static final int blockToInchunkCoords(int xz) {
      return xz & 15;
   }
}
