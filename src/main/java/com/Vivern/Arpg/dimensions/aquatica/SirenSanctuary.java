//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.aquatica;

import com.Vivern.Arpg.dimensions.generationutils.GenerationHelper;
import com.Vivern.Arpg.loot.ListLootTable;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.mobs.SpawnerTuners;
import com.Vivern.Arpg.tileentity.ChestLock;
import com.Vivern.Arpg.tileentity.EnumChest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SirenSanctuary {
   public boolean[][] mainArray;
   public boolean mazeMode = false;
   public int size;
   public Random rand;
   public World world;
   public int pyramidHeight = 30;
   public int wallsHeight = 2;
   public int holesSize = 26;
   public int holesCount = 225;
   public IBlockState bricks;
   public int stretch;
   public float contentGenerationChance;

   public SirenSanctuary(World world, int size, Random rand, int pyramidHeight, int wallsHeight, int stretch, float contentGenerationChance) {
      this.mainArray = new boolean[size][size];
      this.size = size;
      this.rand = rand;
      this.world = world;
      this.pyramidHeight = pyramidHeight;
      this.wallsHeight = wallsHeight;
      int lowsize = size / 4;

      for (int i = lowsize; i < size - lowsize; i++) {
         for (int j = lowsize; j < size - lowsize; j++) {
            this.mainArray[i][j] = rand.nextFloat() < 8.0E-4F;
         }
      }

      this.mainArray[size / 2][size / 2] = true;
      this.bricks = BlocksRegister.SANCTUARYBRICKS.getDefaultState();
      this.stretch = stretch;
      this.contentGenerationChance = contentGenerationChance;
   }

   public void generate(BlockPos pos) {
      pos = pos.add(-this.size * this.stretch / 2, 0, -this.size * this.stretch / 2);
      int lowsize = this.size / 8;
      int halfStretch = this.stretch / 2;
      int bigroomBonus = this.rand.nextInt(Math.round(lowsize * 0.6F) + 1) - this.rand.nextInt(Math.round(lowsize * 0.4F) + 1);
      int lowsizeBig = lowsize + bigroomBonus;
      int lowsizeSmall = lowsize - bigroomBonus;

      for (int s = 0; s < lowsizeBig; s++) {
         boolean[][] newarray = new boolean[this.size][this.size];

         for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
               newarray[i][j] = this.processCell(i, j);
            }
         }

         this.mainArray = newarray;
      }

      this.mazeMode = true;

      for (int s = 0; s < lowsizeSmall; s++) {
         boolean[][] newarray = new boolean[this.size][this.size];

         for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
               newarray[i][j] = this.processCell(i, j);
            }
         }

         this.mainArray = newarray;
      }

      byte[][] newarray1 = new byte[this.size][this.size];

      for (int i = 0; i < this.size; i++) {
         for (int j = 0; j < this.size; j++) {
            newarray1[i][j] = this.processRemoveMuchStone(i, j);
         }
      }

      for (int i = 0; i < this.holesCount; i++) {
         int i1 = this.rand.nextInt(this.size);
         int i2 = this.rand.nextInt(this.size);
         if (newarray1[i1][i2] == 1) {
            newarray1[i1][i2] = 4;
         }
      }

      for (int s = 0; s < this.holesSize; s++) {
         byte[][] newarray = new byte[this.size][this.size];

         for (int ix = 0; ix < this.size; ix++) {
            for (int j = 0; j < this.size; j++) {
               newarray[ix][j] = this.processDoHoles(newarray1, ix, j);
            }
         }

         newarray1 = newarray;
      }

      int longSize = this.size * this.stretch;
      byte[][] newarray2 = new byte[longSize][longSize];

      for (int ix = 0; ix < this.size; ix++) {
         for (int j = 0; j < this.size; j++) {
            for (int x = 0; x < this.stretch; x++) {
               for (int z = 0; z < this.stretch; z++) {
                  newarray2[ix * this.stretch + x][j * this.stretch + z] = newarray1[ix][j];
               }
            }
         }
      }

      byte[][] newarray3 = new byte[longSize][longSize];

      for (int ix = 0; ix < longSize; ix++) {
         for (int j = 0; j < longSize; j++) {
            newarray3[ix][j] = this.processConstriction(newarray2, ix, j, longSize);
         }
      }

      byte[][] newarray4 = new byte[longSize][longSize];

      for (int ix = 0; ix < longSize; ix++) {
         for (int j = 0; j < longSize; j++) {
            newarray4[ix][j] = this.processConstriction(newarray3, ix, j, longSize);
         }
      }

      int halflongSize = this.size * this.stretch / 2;

      for (int ix = 0; ix < longSize; ix++) {
         for (int j = 0; j < longSize; j++) {
            byte cell = this.getCell(newarray4, ix, j, longSize);
            if (cell == 1) {
               this.setFloor(pos.add(ix, 0, j), ix, j, halflongSize, false);
            } else if (cell == 0) {
               this.setPillar(pos.add(ix, 0, j), ix, j, halflongSize);
            } else if (cell == 4) {
               this.setFloor(pos.add(ix, 0, j), ix, j, halflongSize, false);
            }
         }
      }

      ArrayList<BlockPos> somePoses = new ArrayList<>();

      for (int ix = 0; ix < this.size; ix++) {
         for (int jx = 0; jx < this.size; jx++) {
            if (this.getCell(ix, jx) && this.rand.nextFloat() < this.contentGenerationChance) {
               BlockPos somePos = new BlockPos(ix, 1, jx);
               somePoses.add(somePos);
            }
         }
      }

      if (!somePoses.isEmpty()) {
         Collections.shuffle(somePoses, this.rand);

         for (int ix = 0; ix < somePoses.size(); ix++) {
            BlockPos somePos = somePoses.get(ix);
            BlockPos posToSet = pos.add(somePos.getX() * this.stretch + halfStretch, 1, somePos.getZ() * this.stretch + halfStretch);
            if (ix == 0) {
               GenerationHelper.setChestWithItemStack(
                  this.world,
                  posToSet,
                  EnumChest.CORAL,
                  new ItemStack(ItemsRegister.SACRIFICIALDAGGER),
                  EnumFacing.HORIZONTALS[this.rand.nextInt(4)],
                  ChestLock.SIREN
               );
            } else if (this.rand.nextFloat() < 0.4) {
               GenerationHelper.setChestWithLoot(
                  this.world, posToSet, EnumChest.CORAL, ListLootTable.CHESTS_SIREN_SANCTUARY, EnumFacing.HORIZONTALS[this.rand.nextInt(4)], ChestLock.SIREN
               );
            } else {
               int dist = Math.max(Math.abs(somePos.getX() - halflongSize), Math.abs(somePos.getZ() - halflongSize));
               float bound = (1.0F - (float)dist / halflongSize) * this.pyramidHeight + this.wallsHeight;
               int boundedbound = (int)MathHelper.clamp(bound, 0.0F, 12.0F);
               BlockPos newpos = posToSet.up(this.rand.nextInt(boundedbound));
               this.world.setBlockState(newpos, BlocksRegister.MOBSPAWNERAQUATIC.getDefaultState(), 2);
               SpawnerTuners.SIRENSANCTUARY.setupSpawner(this.world, newpos, this.world.rand);
               float vmax = Math.min(bound, (float)(this.rand.nextInt(9) + 1));
               if (vmax > 0.99F) {
                  boolean hasScaffold = false;

                  for (EnumFacing facing : GenerationHelper.shuffledHORIZONTALS()) {
                     GetMOP.BlockTraceResult result = GetMOP.blockTrace(this.world, newpos.up((int)vmax), facing, 14, GetMOP.SOLID_BLOCKS);
                     if (result != null) {
                        for (int g = 0; g <= 14; g++) {
                           BlockPos offposG = newpos.up((int)vmax).offset(facing, g);
                           if (offposG.equals(result.pos)) {
                              break;
                           }

                           this.world.setBlockState(offposG, this.bricks, 2);
                        }

                        hasScaffold = true;
                        break;
                     }
                  }

                  if (hasScaffold) {
                     for (int v = 1; v < (int)vmax; v++) {
                        this.world.setBlockState(newpos.up(v), BlocksRegister.DECORATIVECHAIN.getDefaultState(), 2);
                     }
                  }
               }
            }
         }
      }
   }

   public void setFloor(BlockPos pos, int coord1, int coord2, int halflongSize, boolean roof) {
      this.world.setBlockState(pos, this.bricks, 2);
      if (roof) {
         int dist = Math.max(Math.abs(coord1 - halflongSize), Math.abs(coord2 - halflongSize));
         float bound = (1.0F - (float)dist / halflongSize) * this.pyramidHeight + this.wallsHeight;
         this.world.setBlockState(pos.up(MathHelper.floor(bound)), this.bricks, 2);
      }

      for (int yy = 1; yy < 64; yy++) {
         BlockPos posdown = pos.down(yy);
         if (GetMOP.SOLID_BLOCKS.apply(this.world.getBlockState(posdown))) {
            break;
         }

         this.world.setBlockState(posdown, this.bricks, 2);
      }
   }

   public void setPillar(BlockPos pos, int coord1, int coord2, int halflongSize) {
      int dist = Math.max(Math.abs(coord1 - halflongSize), Math.abs(coord2 - halflongSize));
      float bound = (1.0F - (float)dist / halflongSize) * this.pyramidHeight + this.wallsHeight;

      for (int y = 0; y < bound; y++) {
         this.world.setBlockState(pos.up(y), this.bricks, 2);
      }
   }

   public boolean processCell(int coord1, int coord2) {
      int eightSum = 0;
      if (this.getCell(coord1 - 1, coord2)) {
         eightSum++;
      }

      if (this.getCell(coord1 + 1, coord2)) {
         eightSum++;
      }

      if (this.getCell(coord1 - 1, coord2 - 1)) {
         eightSum++;
      }

      if (this.getCell(coord1 + 1, coord2 - 1)) {
         eightSum++;
      }

      if (this.getCell(coord1 - 1, coord2 + 1)) {
         eightSum++;
      }

      if (this.getCell(coord1 + 1, coord2 + 1)) {
         eightSum++;
      }

      if (this.getCell(coord1, coord2 - 1)) {
         eightSum++;
      }

      if (this.getCell(coord1, coord2 + 1)) {
         eightSum++;
      }

      return eightSum != 1 && (this.mazeMode || eightSum != 6) ? this.mainArray[coord1][coord2] : true;
   }

   public byte processRemoveMuchStone(int coord1, int coord2) {
      int eightSum = 0;
      if (this.getCell(coord1 - 1, coord2)) {
         eightSum++;
      }

      if (this.getCell(coord1 + 1, coord2)) {
         eightSum++;
      }

      if (this.getCell(coord1 - 1, coord2 - 1)) {
         eightSum++;
      }

      if (this.getCell(coord1 + 1, coord2 - 1)) {
         eightSum++;
      }

      if (this.getCell(coord1 - 1, coord2 + 1)) {
         eightSum++;
      }

      if (this.getCell(coord1 + 1, coord2 + 1)) {
         eightSum++;
      }

      if (this.getCell(coord1, coord2 - 1)) {
         eightSum++;
      }

      if (this.getCell(coord1, coord2 + 1)) {
         eightSum++;
      }

      if (eightSum == 0) {
         return 2;
      } else {
         return (byte)(this.mainArray[coord1][coord2] ? 1 : 0);
      }
   }

   public byte processConstriction(byte[][] bytes, int coord1, int coord2, int size) {
      byte self = bytes[coord1][coord2];
      if (self == 2) {
         return self;
      } else {
         if (self == 0) {
            int eightSum = 0;
            if (this.getCell(bytes, coord1 - 1, coord2, size) == 0) {
               eightSum++;
            }

            if (this.getCell(bytes, coord1 + 1, coord2, size) == 0) {
               eightSum++;
            }

            if (this.getCell(bytes, coord1 - 1, coord2 - 1, size) == 0) {
               eightSum++;
            }

            if (this.getCell(bytes, coord1 + 1, coord2 - 1, size) == 0) {
               eightSum++;
            }

            if (this.getCell(bytes, coord1 - 1, coord2 + 1, size) == 0) {
               eightSum++;
            }

            if (this.getCell(bytes, coord1 + 1, coord2 + 1, size) == 0) {
               eightSum++;
            }

            if (this.getCell(bytes, coord1, coord2 - 1, size) == 0) {
               eightSum++;
            }

            if (this.getCell(bytes, coord1, coord2 + 1, size) == 0) {
               eightSum++;
            }

            if (eightSum == 8) {
               return 0;
            }
         }

         return 1;
      }
   }

   public byte processDoHoles(byte[][] bytes, int coord1, int coord2) {
      byte self = bytes[coord1][coord2];
      if (self == 1) {
         int eightSum = 0;
         if (this.getCell(bytes, coord1 - 1, coord2, this.size) == 4) {
            eightSum++;
         }

         if (this.getCell(bytes, coord1 + 1, coord2, this.size) == 4) {
            eightSum++;
         }

         if (this.getCell(bytes, coord1 - 1, coord2 - 1, this.size) == 4) {
            eightSum++;
         }

         if (this.getCell(bytes, coord1 + 1, coord2 - 1, this.size) == 4) {
            eightSum++;
         }

         if (this.getCell(bytes, coord1 - 1, coord2 + 1, this.size) == 4) {
            eightSum++;
         }

         if (this.getCell(bytes, coord1 + 1, coord2 + 1, this.size) == 4) {
            eightSum++;
         }

         if (this.getCell(bytes, coord1, coord2 - 1, this.size) == 4) {
            eightSum++;
         }

         if (this.getCell(bytes, coord1, coord2 + 1, this.size) == 4) {
            eightSum++;
         }

         if (eightSum > 0) {
            return 4;
         }
      }

      return self;
   }

   public boolean getCell(int coord1, int coord2) {
      while (coord1 < 0) {
         coord1 += this.size;
      }

      while (coord2 < 0) {
         coord2 += this.size;
      }

      coord1 %= this.size;
      coord2 %= this.size;
      return this.mainArray[coord1][coord2];
   }

   public byte getCell(byte[][] bytes, int coord1, int coord2, int size) {
      while (coord1 < 0) {
         coord1 += size;
      }

      while (coord2 < 0) {
         coord2 += size;
      }

      coord1 %= size;
      coord2 %= size;
      return bytes[coord1][coord2];
   }
}
