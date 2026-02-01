package com.vivern.arpg.tileentity;

import com.vivern.arpg.blocks.BlockPuzzle;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.network.PacketHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class TilePuzzle extends TileEntity implements TileEntityClicked {
   public static Random rand = new Random();
   public Stack<PuzzleElement> elements = new Stack<>();
   public int puzzleWidth = 0;
   public int puzzleHeight = 0;
   public PuzzleElement[][] puzzlePlate;
   public EnumFacing chestOpened;
   public boolean causesRedstone = false;

   public void setupPuzzle(int hardnessFrom1To6) {
      this.puzzleWidth = rand.nextInt(hardnessFrom1To6) + 3;
      this.puzzleHeight = rand.nextInt(hardnessFrom1To6) + 3;
      this.elements = createPuzzle(this.puzzleWidth, this.puzzleHeight, rand);
      this.puzzlePlate = this.createPlate(this.puzzleWidth, this.puzzleHeight);
   }

   public PuzzleElement[][] createPlate(int width, int height) {
      PuzzleElement[][] plate = new PuzzleElement[width][height];

      for (int w = 0; w < width; w++) {
         for (int h = 0; h < height; h++) {
            plate[w][h] = new PuzzleElement(false, false);
         }
      }

      return plate;
   }

   @Override
   public void mouseClick(int mouseX, int mouseY, int mouseButton) {
      if (!this.world.isRemote) {
         if (mouseX >= 0 && mouseY >= 0 && mouseX < this.puzzleWidth && mouseY < this.puzzleHeight) {
            if (!this.puzzlePlate[mouseX][mouseY].filled && this.canFit(mouseX, mouseY)) {
               this.placeToPuzzleSlot(mouseX, mouseY);
               this.world
                  .playSound((EntityPlayer)null, this.pos, Sounds.puzzle_place, SoundCategory.BLOCKS, 0.3F, 0.85F + rand.nextFloat() / 4.0F);
            } else if (this.puzzlePlate[mouseX][mouseY].center) {
               this.removeFromPuzzleSlot(mouseX, mouseY);
               this.world
                  .playSound((EntityPlayer)null, this.pos, Sounds.puzzle_remove, SoundCategory.BLOCKS, 0.3F, 0.85F + rand.nextFloat() / 4.0F);
            }

            BlockPuzzle.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }

         if (mouseY == 9 || mouseY == 10) {
            if (mouseX == 0 || mouseX == 1) {
               for (int i = 0; i < this.puzzleWidth; i++) {
                  for (int u = 0; u < this.puzzleHeight; u++) {
                     if (this.puzzlePlate[i][u].center) {
                        this.removeFromPuzzleSlot(i, u);
                     }
                  }
               }

               this.world
                  .playSound((EntityPlayer)null, this.pos, Sounds.puzzle_remove, SoundCategory.BLOCKS, 0.3F, 0.85F + rand.nextFloat() / 4.0F);
               BlockPuzzle.trySendPacketUpdate(this.world, this.getPos(), this, 8);
            }

            if ((mouseX == 6 || mouseX == 7) && this.checkForFill()) {
               if (this.chestOpened != null) {
                  BlockPos chestpos = this.getPos().offset(this.chestOpened);
                  TileEntity tile = this.world.getTileEntity(chestpos);
                  if (tile != null && tile instanceof TileARPGChest) {
                     TileARPGChest chest = (TileARPGChest)tile;
                     if (chest.isLockedWith(ChestLock.PUZZLE)) {
                        this.world.playSound(null, chestpos, Sounds.item_misc_b, SoundCategory.BLOCKS, 0.5F, 0.9F + rand.nextFloat() / 5.0F);
                        chest.lockOrUnlockWith(ChestLock.PUZZLE, false);
                        PacketHandler.trySendPacketUpdate(this.world, chestpos, chest, 64.0);
                     }
                  }

                  this.chestOpened = null;
               }

               this.causesRedstone = true;
               this.world
                  .playSound((EntityPlayer)null, this.pos, Sounds.puzzle_unlock, SoundCategory.BLOCKS, 0.9F, 0.9F + rand.nextFloat() / 5.0F);
               this.world.notifyNeighborsOfStateChange(this.pos, BlocksRegister.PUZZLE, false);
            }
         }
      }
   }

   public boolean canFit(int slotWcoord, int slotHcoord) {
      if (this.elements.isEmpty()) {
         return false;
      } else {
         PuzzleElement elem = this.elements.peek();

         for (int[] ints : elem.list) {
            if (slotWcoord + ints[0] < this.puzzleWidth && slotHcoord + ints[1] < this.puzzleHeight) {
               if (slotWcoord + ints[0] >= 0 && slotHcoord + ints[1] >= 0) {
                  if (this.puzzlePlate[slotWcoord + ints[0]][slotHcoord + ints[1]].filled) {
                     return false;
                  }
                  continue;
               }

               return false;
            }

            return false;
         }

         return true;
      }
   }

   public void placeToPuzzleSlot(int slotWcoord, int slotHcoord) {
      PuzzleElement elem = this.elements.pop();

      for (int[] ints : elem.list) {
         if (ints[0] != 0 || ints[1] != 0) {
            this.puzzlePlate[slotWcoord + ints[0]][slotHcoord + ints[1]] = new PuzzleElement(false, true);
         }
      }

      this.puzzlePlate[slotWcoord][slotHcoord] = elem;
   }

   public void removeFromPuzzleSlot(int slotWcoord, int slotHcoord) {
      PuzzleElement elem = this.puzzlePlate[slotWcoord][slotHcoord];

      for (int[] ints : elem.list) {
         this.puzzlePlate[slotWcoord + ints[0]][slotHcoord + ints[1]] = new PuzzleElement(false, false);
      }

      this.elements.push(elem);
   }

   public boolean checkForFill() {
      for (int w = 0; w < this.puzzleWidth; w++) {
         for (int h = 0; h < this.puzzleHeight; h++) {
            if (!this.puzzlePlate[w][h].filled) {
               return false;
            }
         }
      }

      return true;
   }

   public void write(NBTTagCompound compound) {
      if (this.puzzleWidth != 0 && this.puzzleHeight != 0) {
         compound.setInteger("puzzleWidth", this.puzzleWidth);
         compound.setInteger("puzzleHeight", this.puzzleHeight);
         int elnumber = 0;
         if (!this.elements.isEmpty()) {
            for (PuzzleElement el : this.elements) {
               compound.setTag("instack" + elnumber, el.toNBT());
               elnumber++;
            }
         }

         int onplateNum = 0;

         for (int w = 0; w < this.puzzleWidth; w++) {
            for (int h = 0; h < this.puzzleHeight; h++) {
               compound.setTag("inplate" + onplateNum, this.puzzlePlate[w][h].toNBT(w, h));
               onplateNum++;
            }
         }
      }

      if (this.chestOpened != null) {
         compound.setInteger("chestOpened", this.chestOpened.getIndex());
      }

      compound.setBoolean("causesRedstone", this.causesRedstone);
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("puzzleWidth") && compound.hasKey("puzzleHeight")) {
         this.elements.clear();
         this.puzzleWidth = compound.getInteger("puzzleWidth");
         this.puzzleHeight = compound.getInteger("puzzleHeight");

         for (int elnumber = 0; compound.hasKey("instack" + elnumber); elnumber++) {
            this.elements.push(PuzzleElement.fromNBT(compound.getCompoundTag("instack" + elnumber)));
         }

         this.puzzlePlate = this.createPlate(this.puzzleWidth, this.puzzleHeight);

         for (int onplateNum = 0; compound.hasKey("inplate" + onplateNum); onplateNum++) {
            NBTTagCompound tagg = compound.getCompoundTag("inplate" + onplateNum);
            if (tagg.hasKey("posx") && tagg.hasKey("posy")) {
               int xx = tagg.getInteger("posx");
               int yy = tagg.getInteger("posy");
               this.puzzlePlate[xx][yy] = PuzzleElement.fromNBT(tagg);
            }
         }
      }

      if (compound.hasKey("chestOpened")) {
         int ch = compound.getInteger("chestOpened");
         if (ch < 6) {
            this.chestOpened = EnumFacing.byIndex(ch);
         } else {
            this.chestOpened = null;
         }
      } else {
         this.chestOpened = null;
      }

      if (compound.hasKey("causesRedstone")) {
         this.causesRedstone = compound.getBoolean("causesRedstone");
      }
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      this.write(compound);
      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.read(compound);
      super.readFromNBT(compound);
   }

   public NBTTagCompound getUpdateTag() {
      NBTTagCompound compound = super.getUpdateTag();
      this.write(compound);
      return compound;
   }

   public void handleUpdateTag(NBTTagCompound compound) {
      this.read(compound);
      super.handleUpdateTag(compound);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      this.read(compound);
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound compound = new NBTTagCompound();
      this.write(compound);
      return new SPacketUpdateTileEntity(this.pos, 1, compound);
   }

   public static Stack<PuzzleElement> createPuzzle(int width, int height, Random random) {
      Stack<PuzzleElement> stack = new Stack<>();
      boolean[][] cells = new boolean[width][height];

      for (int w = 0; w < width; w++) {
         for (int h = 0; h < height; h++) {
            if (!cells[w][h]) {
               int size = random.nextInt(7) + 1;
               PuzzleElement element = new PuzzleElement(true, true);
               element.list.add(new int[]{0, 0});
               cells[w][h] = true;
               int coordW = w;
               int coordH = h;
               int relatcoordW = 0;
               int relatcoordH = 0;

               for (int s = 1; s <= size; s++) {
                  int direction = random.nextInt(4);

                  for (int d = 0; d < 4; d++) {
                     direction = GetMOP.next(direction, 1, 4);
                     if (direction == 0 && coordW + 1 < width && !cells[coordW + 1][coordH]) {
                        coordW++;
                        relatcoordW++;
                        cells[coordW][coordH] = true;
                        element.list.add(new int[]{relatcoordW, relatcoordH});
                        break;
                     }

                     if (direction == 1 && coordH - 1 >= 0 && !cells[coordW][coordH - 1]) {
                        coordH--;
                        relatcoordH--;
                        cells[coordW][coordH] = true;
                        element.list.add(new int[]{relatcoordW, relatcoordH});
                        break;
                     }

                     if (direction == 2 && coordW - 1 >= 0 && !cells[coordW - 1][coordH]) {
                        coordW--;
                        relatcoordW--;
                        cells[coordW][coordH] = true;
                        element.list.add(new int[]{relatcoordW, relatcoordH});
                        break;
                     }

                     if (direction == 3 && coordH + 1 < height && !cells[coordW][coordH + 1]) {
                        coordH++;
                        relatcoordH++;
                        cells[coordW][coordH] = true;
                        element.list.add(new int[]{relatcoordW, relatcoordH});
                        break;
                     }
                  }
               }

               stack.push(element);
            }
         }
      }

      return stack;
   }

   public static class PuzzleElement {
      public final boolean center;
      public final boolean filled;
      public List<int[]> list = new ArrayList<>();

      public PuzzleElement(boolean center, boolean filled) {
         this.center = center;
         this.filled = filled;
      }

      @Override
      public String toString() {
         String s = " PE: ";

         for (int[] ints : this.list) {
            s = s + ints[0] + "_" + ints[1] + "   ";
         }

         return s;
      }

      public NBTTagCompound toNBT() {
         NBTTagCompound nbt = new NBTTagCompound();
         int i = 0;

         for (int[] ints : this.list) {
            nbt.setInteger("x" + i, ints[0]);
            nbt.setInteger("y" + i, ints[1]);
            i++;
         }

         nbt.setBoolean("center", this.center);
         nbt.setBoolean("filled", this.filled);
         return nbt;
      }

      public NBTTagCompound toNBT(int posx, int posy) {
         NBTTagCompound nbt = new NBTTagCompound();
         int i = 0;

         for (int[] ints : this.list) {
            nbt.setInteger("x" + i, ints[0]);
            nbt.setInteger("y" + i, ints[1]);
            i++;
         }

         nbt.setInteger("posx", posx);
         nbt.setInteger("posy", posy);
         nbt.setBoolean("center", this.center);
         nbt.setBoolean("filled", this.filled);
         return nbt;
      }

      public static PuzzleElement fromNBT(NBTTagCompound nbt) {
         PuzzleElement elem = new PuzzleElement(nbt.getBoolean("center"), nbt.getBoolean("filled"));

         for (int i = 0; nbt.hasKey("x" + i) && nbt.hasKey("y" + i); i++) {
            elem.list.add(new int[]{nbt.getInteger("x" + i), nbt.getInteger("y" + i)});
         }

         return elem;
      }
   }
}
