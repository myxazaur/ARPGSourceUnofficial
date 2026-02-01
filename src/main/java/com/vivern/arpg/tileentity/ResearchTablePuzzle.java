package com.vivern.arpg.tileentity;

import com.vivern.arpg.container.GUIResearchTable;
import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public class ResearchTablePuzzle {
   public Random rand;
   public int sizeX;
   public int sizeY;
   public int chipX;
   public int chipY;
   public RTPElement pickedByChip;
   public RTPElement[][] board;
   public boolean[][] terraIncognito;
   public int generationStepsLeft;
   public boolean isGenerating;
   public float difficulty;

   public ResearchTablePuzzle(int sizeX, int sizeY, Random rand, float difficulty) {
      if (sizeX * sizeY < 16) {
         sizeX = 4;
         sizeY = 4;
      }

      if (sizeX > 10) {
         sizeX = 10;
      }

      if (sizeY > 10) {
         sizeY = 10;
      }

      this.sizeX = sizeX;
      this.sizeY = sizeY;
      this.board = new RTPElement[sizeX][sizeY];
      this.terraIncognito = new boolean[sizeX][sizeY];

      for (int xx = 0; xx < sizeX; xx++) {
         for (int yy = 0; yy < sizeY; yy++) {
            this.terraIncognito[xx][yy] = true;
         }
      }

      this.rand = rand;
      this.difficulty = difficulty;
   }

   public void renderPuzzleInGui(GUIResearchTable gui, int xCoord, int yCoord) {
      int xboardpixels = this.sizeX * 14;
      int xtranslate = (140 - xboardpixels) / 2;
      int yboardpixels = this.sizeY * 14;
      int ytranslate = (140 - yboardpixels) / 2;
      xCoord += xtranslate;
      yCoord += ytranslate;

      for (int xx = 0; xx < this.sizeX; xx++) {
         for (int yy = 0; yy < this.sizeY; yy++) {
            if (this.terraIncognito[xx][yy]) {
               gui.drawTexturedModalRect(xCoord + xx * 14 - 2, yCoord + yy * 14 - 2, 238, 28, 18, 18);
            } else if (this.board[xx][yy] != null) {
               this.board[xx][yy].renderInGui(this, gui, xCoord, yCoord);
            }
         }
      }

      if (this.getFromBoard(this.chipX, this.chipY) == null && this.pickedByChip == null) {
         gui.drawTexturedModalRect(xCoord + this.chipX * 14, yCoord + this.chipY * 14, 238, 0, 14, 14);
      } else {
         if (this.pickedByChip != null) {
            this.pickedByChip.renderInGui(this, gui, xCoord, yCoord);
         }

         gui.drawTexturedModalRect(xCoord + this.chipX * 14, yCoord + this.chipY * 14, 238, 14, 14, 14);
      }
   }

   public void onMouseClick(GUIResearchTable gui, int xCoord, int yCoord, int mouseButton) {
      int xboardpixels = this.sizeX * 14;
      int xtranslate = (140 - xboardpixels) / 2;
      int yboardpixels = this.sizeY * 14;
      int ytranslate = (140 - yboardpixels) / 2;
      xCoord -= xtranslate;
      yCoord -= ytranslate;
      int slotX = xCoord / 14;
      int slotY = yCoord / 14;
      if (mouseButton == 1) {
         if (this.pickedByChip != null) {
            if (this.getFromBoard(this.pickedByChip.posX, this.pickedByChip.posY) == null) {
               this.setToBoard(this.pickedByChip);
               this.pickedByChip = null;
            }
         } else {
            RTPElement element = this.getFromBoard(this.chipX, this.chipY);
            if (element != null && element.blocked <= 0) {
               this.setNullToBoard(this.chipX, this.chipY);
               this.pickedByChip = element;
            }
         }
      }

      if (mouseButton == 0 && !this.terraIncognito[MathHelper.clamp(slotX, 0, this.sizeX - 1)][MathHelper.clamp(slotY, 0, this.sizeY - 1)]) {
         RTPElement element = this.getFromBoard(slotX, slotY);
         if (element != null && element.blocked <= 0 && element.onUse(this)) {
            this.setNullToBoard(slotX, slotY);
         }
      }
   }

   public void generate() {
      this.isGenerating = true;
      this.generationStepsLeft = this.rand.nextInt((int)(50.0F + 300.0F * this.difficulty));
      this.chipX = this.rand.nextInt(this.sizeX);
      this.chipY = this.rand.nextInt(this.sizeY);

      for (int i = 0; i < 6; i++) {
         RTPElementRunePiece runePiece = new RTPElementRunePiece(
            this.rand.nextInt(this.sizeX), this.rand.nextInt(this.sizeY), i
         );
         if (this.rand.nextFloat() < this.difficulty) {
            runePiece.rotation = this.rand.nextInt(4);
         }

         while (this.board[runePiece.posX][runePiece.posY] != null) {
            runePiece.posX = this.rand.nextInt(this.sizeX);
            runePiece.posY = this.rand.nextInt(this.sizeY);
         }

         this.board[runePiece.posX][runePiece.posY] = runePiece;
      }

      this.recursiveGenerate();
      this.setIncognitoInRange(this.chipX, this.chipY, 1, false);
      this.isGenerating = false;
   }

   public void recursiveGenerate() {
      this.generationStepsLeft--;
      if (this.generationStepsLeft > 0) {
         boolean move = true;
         if (this.pickedByChip == null && this.getFromBoard(this.chipX, this.chipY) == null) {
            RTPElement newelement = this.createRandomFunctionalElement(this.difficulty);
            if (newelement.onGenerate(this)) {
               this.pickedByChip = newelement;
               move = false;
            }
         }

         if (move) {
            this.moveChipRandom();
            if (this.pickedByChip != null && this.rand.nextFloat() < 0.1F && this.getFromBoard(this.pickedByChip.posX, this.pickedByChip.posY) == null) {
               if (this.rand.nextFloat() < this.difficulty && this.pickedByChip instanceof RTPElementRotated) {
                  ((RTPElementRotated)this.pickedByChip).rotation = this.rand.nextInt(4);
               }

               this.setToBoard(this.pickedByChip);
               this.pickedByChip = null;
            }
         }

         this.recursiveGenerate();
      }
   }

   public RTPElement createRandomFunctionalElement(float difficulty) {
      if (this.rand.nextFloat() < 0.33F) {
         return new RTPElementCross(this.chipX, this.chipY);
      } else {
         return (RTPElement)(this.rand.nextFloat() < 0.5F
            ? new RTPElementQuad(this.chipX, this.chipY)
            : new RTPElementLine(this.chipX, this.chipY));
      }
   }

   public void moveChip(int direction) {
      if (direction == 0 && this.chipX + 1 < this.sizeX && this.isPassable(this.chipX + 1, this.chipY)) {
         this.chipX++;
      } else if (direction == 1 && this.chipY + 1 < this.sizeY && this.isPassable(this.chipX, this.chipY + 1)) {
         this.chipY++;
      } else if (direction == 2 && this.chipX > 0 && this.isPassable(this.chipX - 1, this.chipY)) {
         this.chipX--;
      } else if (direction == 3 && this.chipY > 0 && this.isPassable(this.chipX, this.chipY - 1)) {
         this.chipY--;
      }

      if (this.pickedByChip != null) {
         this.pickedByChip.posX = this.chipX;
         this.pickedByChip.posY = this.chipY;
      }

      if (!this.isGenerating) {
         this.setIncognitoInRange(this.chipX, this.chipY, 1, false);
      }
   }

   public void setIncognitoInRange(int posX, int posY, int range, boolean toSet) {
      for (int x = -range; x <= range; x++) {
         for (int y = -range; y <= range; y++) {
            int newPosx = posX + x;
            int newPosy = posY + y;
            if (newPosx >= 0 && newPosx < this.sizeX && newPosy >= 0 && newPosy < this.sizeY) {
               this.terraIncognito[newPosx][newPosy] = toSet;
            }
         }
      }
   }

   public void moveChipRandom() {
      this.moveChip(this.rand.nextInt(4));
   }

   public RTPElement getFromBoard(int posX, int posY) {
      return posX >= 0 && posX < this.sizeX && posY >= 0 && posY < this.sizeY ? this.board[posX][posY] : null;
   }

   public void setToBoard(RTPElement element) {
      if (element.posX >= 0 && element.posX < this.sizeX && element.posY >= 0 && element.posY < this.sizeY) {
         if (element instanceof RTPElementBlock) {
            if (this.board[element.posX][element.posY] != null && this.board[element.posX][element.posY].canBeBlocked(this.difficulty)) {
               this.board[element.posX][element.posY].blocked++;
            } else {
               this.board[element.posX][element.posY] = element;
            }
         } else {
            this.board[element.posX][element.posY] = element;
         }
      }
   }

   public void setNullToBoard(int posX, int posY) {
      if (posX >= 0 && posX < this.sizeX && posY >= 0 && posY < this.sizeY) {
         this.board[posX][posY] = null;
      }
   }

   public void destroy(int posX, int posY) {
      if (posX >= 0 && posX < this.sizeX && posY >= 0 && posY < this.sizeY && this.board[posX][posY] != null) {
         if (this.board[posX][posY].blocked <= 0) {
            this.board[posX][posY] = null;
         } else {
            this.board[posX][posY].blocked--;
         }
      }
   }

   public boolean isPassable(int posX, int posY) {
      if (posX >= 0 && posX < this.sizeX && posY >= 0 && posY < this.sizeY) {
         return this.board[posX][posY] == null ? true : this.board[posX][posY].canWalkthrough();
      } else {
         return false;
      }
   }

   public boolean canPlaceBlock(int posX, int posY) {
      if (posX >= 0 && posX < this.sizeX && posY >= 0 && posY < this.sizeY) {
         return this.board[posX][posY] == null ? true : this.board[posX][posY].canBeBlocked(this.difficulty);
      } else {
         return false;
      }
   }

   public static class RTPElement {
      public int posX;
      public int posY;
      public byte blocked;

      public RTPElement(int posX, int posY) {
         this.posX = posX;
         this.posY = posY;
      }

      public boolean canGrab() {
         return true;
      }

      public boolean canWalkthrough() {
         return true;
      }

      public boolean onUse(ResearchTablePuzzle puzzle) {
         return false;
      }

      public boolean onGenerate(ResearchTablePuzzle puzzle) {
         return false;
      }

      public void renderInGui(ResearchTablePuzzle puzzle, GUIResearchTable gui, int xCoord, int yCoord) {
         gui.drawTexturedModalRect(xCoord + this.posX * 14, yCoord + this.posY * 14, 224, 0, 14, 14);
         this.renderBlocked(puzzle, gui, xCoord, yCoord);
      }

      public boolean canBeBlocked(float difficulty) {
         return this.blocked < 1;
      }

      public void renderBlocked(ResearchTablePuzzle puzzle, GUIResearchTable gui, int xCoord, int yCoord) {
         if (this.blocked > 0) {
            gui.drawTexturedModalRect(xCoord + this.posX * 14, yCoord + this.posY * 14, 224, 56 + this.blocked * 14, 14, 14);
         }
      }
   }

   public static class RTPElementBlock extends RTPElement {
      public RTPElementBlock(int posX, int posY) {
         super(posX, posY);
      }

      @Override
      public boolean canGrab() {
         return false;
      }

      @Override
      public boolean canWalkthrough() {
         return false;
      }

      @Override
      public boolean canBeBlocked(float difficulty) {
         return false;
      }

      @Override
      public void renderInGui(ResearchTablePuzzle puzzle, GUIResearchTable gui, int xCoord, int yCoord) {
         gui.drawTexturedModalRect(xCoord + this.posX * 14, yCoord + this.posY * 14, 224, 28, 14, 14);
      }
   }

   public static class RTPElementCross extends RTPElement {
      public RTPElementCross(int posX, int posY) {
         super(posX, posY);
      }

      @Override
      public boolean onGenerate(ResearchTablePuzzle puzzle) {
         if (puzzle.canPlaceBlock(this.posX + 1, this.posY + 1)
            && puzzle.canPlaceBlock(this.posX - 1, this.posY + 1)
            && puzzle.canPlaceBlock(this.posX + 1, this.posY - 1)
            && puzzle.canPlaceBlock(this.posX - 1, this.posY - 1)) {
            puzzle.setToBoard(new RTPElementBlock(this.posX + 1, this.posY + 1));
            puzzle.setToBoard(new RTPElementBlock(this.posX - 1, this.posY + 1));
            puzzle.setToBoard(new RTPElementBlock(this.posX + 1, this.posY - 1));
            puzzle.setToBoard(new RTPElementBlock(this.posX - 1, this.posY - 1));
            return true;
         } else {
            return false;
         }
      }

      @Override
      public boolean onUse(ResearchTablePuzzle puzzle) {
         puzzle.destroy(this.posX + 1, this.posY + 1);
         puzzle.destroy(this.posX - 1, this.posY + 1);
         puzzle.destroy(this.posX + 1, this.posY - 1);
         puzzle.destroy(this.posX - 1, this.posY - 1);
         return true;
      }

      @Override
      public void renderInGui(ResearchTablePuzzle puzzle, GUIResearchTable gui, int xCoord, int yCoord) {
         gui.drawTexturedModalRect(xCoord + this.posX * 14, yCoord + this.posY * 14, 224, 14, 14, 14);
         this.renderBlocked(puzzle, gui, xCoord, yCoord);
      }
   }

   public static class RTPElementLine extends RTPElementRotated {
      public RTPElementLine(int posX, int posY) {
         super(posX, posY);
      }

      @Override
      public boolean onGenerate(ResearchTablePuzzle puzzle) {
         this.rotation = 0;
         if (!this.tryGenerate(puzzle)) {
            this.rotation = 1;
            return this.tryGenerate(puzzle);
         } else {
            return true;
         }
      }

      public boolean tryGenerate(ResearchTablePuzzle puzzle) {
         if (this.rotation != 0 && this.rotation != 2) {
            for (int u = 0; u < puzzle.sizeY; u++) {
               if (u != this.posY && !puzzle.canPlaceBlock(this.posX, u)) {
                  return false;
               }
            }

            for (int ux = 0; ux < puzzle.sizeY; ux++) {
               if (ux != this.posY) {
                  puzzle.setToBoard(new RTPElementBlock(this.posX, ux));
               }
            }

            return true;
         } else {
            for (int uxx = 0; uxx < puzzle.sizeX; uxx++) {
               if (uxx != this.posX && !puzzle.canPlaceBlock(uxx, this.posY)) {
                  return false;
               }
            }

            for (int uxxx = 0; uxxx < puzzle.sizeX; uxxx++) {
               if (uxxx != this.posX) {
                  puzzle.setToBoard(new RTPElementBlock(uxxx, this.posY));
               }
            }

            return true;
         }
      }

      @Override
      public boolean onUse(ResearchTablePuzzle puzzle) {
         if (this.rotation != 0 && this.rotation != 2) {
            for (int u = 0; u < puzzle.sizeY; u++) {
               if (u != this.posY) {
                  puzzle.destroy(this.posX, u);
               }
            }
         } else {
            for (int ux = 0; ux < puzzle.sizeX; ux++) {
               if (ux != this.posX) {
                  puzzle.destroy(ux, this.posY);
               }
            }
         }

         return true;
      }

      @Override
      public void renderInGui(ResearchTablePuzzle puzzle, GUIResearchTable gui, int xCoord, int yCoord) {
         int x = xCoord + this.posX * 14;
         int y = yCoord + this.posY * 14;
         int displace = 7;
         GlStateManager.pushMatrix();
         GlStateManager.translate(x + displace, y + displace, 0.0F);
         GlStateManager.rotate(this.rotation * 90, 0.0F, 0.0F, 1.0F);
         GlStateManager.translate(-x - displace, -y - displace, 0.0F);
         gui.drawTexturedModalRect(x, y, 224, 56, 14, 14);
         GlStateManager.popMatrix();
         this.renderBlocked(puzzle, gui, xCoord, yCoord);
      }
   }

   public static class RTPElementQuad extends RTPElement {
      public RTPElementQuad(int posX, int posY) {
         super(posX, posY);
      }

      @Override
      public boolean onGenerate(ResearchTablePuzzle puzzle) {
         if (puzzle.canPlaceBlock(this.posX + 1, this.posY + 1)
            && puzzle.canPlaceBlock(this.posX - 1, this.posY + 1)
            && puzzle.canPlaceBlock(this.posX + 1, this.posY - 1)
            && puzzle.canPlaceBlock(this.posX - 1, this.posY - 1)
            && puzzle.canPlaceBlock(this.posX + 1, this.posY)
            && puzzle.canPlaceBlock(this.posX - 1, this.posY)
            && puzzle.canPlaceBlock(this.posX, this.posY + 1)
            && puzzle.canPlaceBlock(this.posX, this.posY - 1)) {
            puzzle.setToBoard(new RTPElementBlock(this.posX + 1, this.posY + 1));
            puzzle.setToBoard(new RTPElementBlock(this.posX - 1, this.posY + 1));
            puzzle.setToBoard(new RTPElementBlock(this.posX + 1, this.posY - 1));
            puzzle.setToBoard(new RTPElementBlock(this.posX - 1, this.posY - 1));
            int randExit = puzzle.rand.nextInt(4);
            if (randExit != 0) {
               puzzle.setToBoard(new RTPElementBlock(this.posX + 1, this.posY));
            }

            if (randExit != 1) {
               puzzle.setToBoard(new RTPElementBlock(this.posX, this.posY + 1));
            }

            if (randExit != 2) {
               puzzle.setToBoard(new RTPElementBlock(this.posX - 1, this.posY));
            }

            if (randExit != 3) {
               puzzle.setToBoard(new RTPElementBlock(this.posX, this.posY - 1));
            }

            return true;
         } else {
            return false;
         }
      }

      @Override
      public boolean onUse(ResearchTablePuzzle puzzle) {
         puzzle.destroy(this.posX + 1, this.posY + 1);
         puzzle.destroy(this.posX - 1, this.posY + 1);
         puzzle.destroy(this.posX + 1, this.posY - 1);
         puzzle.destroy(this.posX - 1, this.posY - 1);
         puzzle.destroy(this.posX + 1, this.posY);
         puzzle.destroy(this.posX - 1, this.posY);
         puzzle.destroy(this.posX, this.posY + 1);
         puzzle.destroy(this.posX, this.posY - 1);
         return true;
      }

      @Override
      public void renderInGui(ResearchTablePuzzle puzzle, GUIResearchTable gui, int xCoord, int yCoord) {
         gui.drawTexturedModalRect(xCoord + this.posX * 14, yCoord + this.posY * 14, 224, 42, 14, 14);
         this.renderBlocked(puzzle, gui, xCoord, yCoord);
      }
   }

   public static class RTPElementRotated extends RTPElement {
      public int rotation = 0;

      public RTPElementRotated(int posX, int posY) {
         super(posX, posY);
      }

      public void rotate(boolean clockwise) {
         if (clockwise) {
            this.rotation++;
            if (this.rotation > 3) {
               this.rotation = 0;
            }
         } else {
            this.rotation--;
            if (this.rotation < 0) {
               this.rotation = 3;
            }
         }
      }

      @Override
      public void renderInGui(ResearchTablePuzzle puzzle, GUIResearchTable gui, int xCoord, int yCoord) {
         int x = xCoord + this.posX * 14;
         int y = yCoord + this.posY * 14;
         int displace = 7;
         GlStateManager.pushMatrix();
         GlStateManager.translate(x + displace, y + displace, 0.0F);
         GlStateManager.rotate(this.rotation * 90, 0.0F, 0.0F, 1.0F);
         GlStateManager.translate(-x - displace, -y - displace, 0.0F);
         gui.drawTexturedModalRect(xCoord + this.posX * 14, yCoord + this.posY * 14, 224, 0, 14, 14);
         GlStateManager.popMatrix();
         this.renderBlocked(puzzle, gui, xCoord, yCoord);
      }
   }

   public static class RTPElementRunePiece extends RTPElementRotated {
      public int pieceNumber;

      public RTPElementRunePiece(int posX, int posY, int pieceNumber) {
         super(posX, posY);
         this.pieceNumber = pieceNumber;
      }
   }
}
