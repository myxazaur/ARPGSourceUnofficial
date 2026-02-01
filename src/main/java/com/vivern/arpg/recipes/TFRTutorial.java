package com.vivern.arpg.recipes;

public class TFRTutorial {
   public static TerraformingResearchPuzzle genTutorial1(int nbtKey, int nbtBitshift) {
      System.out.println("genTutorial1");
      TerraformingResearchPuzzle puzzle = new TerraformingResearchPuzzle(4, 4);
      puzzle.whatResearchKey = nbtKey;
      puzzle.whatResearchBitshift = nbtBitshift;
      puzzle.board[1][1].wayRight = true;
      puzzle.board[1][1].wayDown = true;
      puzzle.board[1][1].isPoint = true;
      puzzle.board[2][1].wayLeft = true;
      puzzle.board[2][1].wayDown = true;
      puzzle.board[2][1].isPoint = true;
      puzzle.board[1][2].wayRight = true;
      puzzle.board[1][2].wayUp = true;
      puzzle.board[1][2].isPoint = true;
      puzzle.board[2][2].wayLeft = true;
      puzzle.board[2][2].wayUp = true;
      puzzle.board[2][2].isPoint = true;
      puzzle.inventory[3] = 1;
      puzzle.finalBalance = new TerraformingResearchPuzzle.TFRBalanceOfElements(0, 0, 900, 0, 0, 0, 0, 0, 0, 0, 0, 0);
      puzzle.recalcAllWayRenderIndexes();
      System.out.println("genTutorial end");
      return puzzle;
   }

   public static TerraformingResearchPuzzle genTutorial2(int nbtKey, int nbtBitshift) {
      System.out.println("genTutorial2");
      TerraformingResearchPuzzle puzzle = new TerraformingResearchPuzzle(5, 5);
      puzzle.whatResearchKey = nbtKey;
      puzzle.whatResearchBitshift = nbtBitshift;
      puzzle.board[1][1].wayRight = true;
      puzzle.board[1][1].isPoint = true;
      puzzle.board[2][1].wayLeft = true;
      puzzle.board[2][1].wayDown = true;
      puzzle.board[2][2].wayUp = true;
      puzzle.board[2][2].wayDown = true;
      puzzle.board[2][2].isPoint = true;
      puzzle.board[2][3].wayRight = true;
      puzzle.board[2][3].wayUp = true;
      puzzle.board[3][3].wayLeft = true;
      puzzle.board[3][3].isPoint = true;
      puzzle.inventory[1] = 1;
      puzzle.inventory[2] = 1;
      puzzle.movingOrbs = 5;
      puzzle.finalBalance = new TerraformingResearchPuzzle.TFRBalanceOfElements(450, 450, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
      puzzle.recalcAllWayRenderIndexes();
      System.out.println("genTutorial end");
      return puzzle;
   }

   public static TerraformingResearchPuzzle genTutorial3(int nbtKey, int nbtBitshift) {
      System.out.println("genTutorial3");
      TerraformingResearchPuzzle puzzle = new TerraformingResearchPuzzle(8, 8);
      puzzle.whatResearchKey = nbtKey;
      puzzle.whatResearchBitshift = nbtBitshift;

      for (int x = 1; x <= 6; x++) {
         for (int y = 1; y <= 6; y++) {
            puzzle.board[x][y].isPoint = true;
         }
      }

      for (int x = 1; x <= 6; x++) {
         for (int y = 1; y <= 6; y++) {
            if (puzzle.board[x - 1][y].isPoint) {
               puzzle.board[x][y].wayLeft = true;
            }

            if (puzzle.board[x + 1][y].isPoint) {
               puzzle.board[x][y].wayRight = true;
            }

            if (puzzle.board[x][y - 1].isPoint) {
               puzzle.board[x][y].wayUp = true;
            }

            if (puzzle.board[x][y + 1].isPoint) {
               puzzle.board[x][y].wayDown = true;
            }
         }
      }

      puzzle.inventory[1] = 2;
      puzzle.inventory[2] = 2;
      puzzle.inventory[3] = 4;
      puzzle.movingOrbs = 3;
      puzzle.finalBalance = new TerraformingResearchPuzzle.TFRBalanceOfElements(200, 200, 2100, 0, 0, 0, 0, 0, 0, 0, 0, 0);
      puzzle.recalcAllWayRenderIndexes();
      System.out.println("genTutorial end");
      return puzzle;
   }

   public static TerraformingResearchPuzzle genTutorial4(int nbtKey, int nbtBitshift) {
      System.out.println("genTutorial4");
      TerraformingResearchPuzzle puzzle = new TerraformingResearchPuzzle(5, 6);
      puzzle.whatResearchKey = nbtKey;
      puzzle.whatResearchBitshift = nbtBitshift;
      puzzle.board[2][2].isPoint = true;
      puzzle.board[2][3].isPoint = true;
      puzzle.board[2][2].wayDown = true;
      puzzle.board[2][3].wayUp = true;
      puzzle.inventory[1] = 1;
      puzzle.inventory[2] = 1;
      puzzle.movingOrbs = 4;
      puzzle.splitOrbs = 5;
      puzzle.finalBalance = new TerraformingResearchPuzzle.TFRBalanceOfElements(800, 1700, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
      puzzle.recalcAllWayRenderIndexes();
      System.out.println("genTutorial end");
      return puzzle;
   }

   public static TerraformingResearchPuzzle genTutorial5(int nbtKey, int nbtBitshift) {
      System.out.println("genTutorial5");
      TerraformingResearchPuzzle puzzle = new TerraformingResearchPuzzle(6, 4);
      puzzle.whatResearchKey = nbtKey;
      puzzle.whatResearchBitshift = nbtBitshift;
      puzzle.board[1][1].isPoint = true;
      puzzle.board[1][2].isPoint = true;
      puzzle.board[1][1].wayDown = true;
      puzzle.board[1][2].wayUp = true;
      puzzle.board[4][1].isPoint = true;
      puzzle.addPhenomenon(4, 1, 2, 12);
      puzzle.inventory[4] = 4;
      puzzle.inventory[7] = 1;
      puzzle.finalBalance = new TerraformingResearchPuzzle.TFRBalanceOfElements(0, 0, 0, 2400, 0, 0, 0, 0, 0, 0, 0, 0);
      puzzle.recalcAllWayRenderIndexes();
      System.out.println("genTutorial end");
      return puzzle;
   }
}
