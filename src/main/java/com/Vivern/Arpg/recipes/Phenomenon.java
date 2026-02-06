package com.Vivern.Arpg.recipes;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.renders.RenderTerraformingResearch;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec2f;

public abstract class Phenomenon {
   public int posX;
   public int posY;
   public int subselectedX;
   public int subselectedY;
   public boolean subselected;

   public boolean isPlayer() {
      return false;
   }

   public abstract int getId();

   public void writeToNbt(NBTTagCompound tagCompound) {
      tagCompound.setInteger("posX", this.posX);
      tagCompound.setInteger("posY", this.posY);
   }

   public void readFromNbt(NBTTagCompound tagCompound) {
      if (tagCompound.hasKey("posX")) {
         this.posX = tagCompound.getInteger("posX");
      }

      if (tagCompound.hasKey("posY")) {
         this.posY = tagCompound.getInteger("posY");
      }
   }

   public void render(RenderTerraformingResearch renderTFR, float posx, float posy, int arrayX, int arrayY, float fullness) {
      int id = this.getId() - 1;
      Phenomenons.phenomenonsRender.texx = id % 18 * 42;
      Phenomenons.phenomenonsRender.texy = id / 18 * 42;
      Phenomenons.phenomenonsRender.render(renderTFR, posx, posy, arrayX, arrayY, 0L, fullness);
   }

   public void updateAnimation(TerraformingResearchPuzzle puzzle, RenderTerraformingResearch renderTFR, float x, float y, int arrayX, int arrayY) {
   }

   public boolean clickOtherDuringSelected(TerraformingResearchPuzzle puzzle, int x, int y) {
      if (puzzle.selected != null && puzzle.selected.phenomenon != null && puzzle.selected.phenomenon.posX == x && puzzle.selected.phenomenon.posY == y) {
         this.subselected = false;
         this.onUnselected(puzzle);
         puzzle.selected = null;
      } else if (puzzle.checkBounds(x, y) && this.canUse()) {
         if (this.hasSubselectionWhenUse()) {
            if (this.subselected) {
               if (this.getUsePossibles().isPossible(puzzle, x, y) && this.checkUseReachDistance(puzzle, x, y)) {
                  boolean us = this.use(puzzle, x, y);
                  if (us) {
                     this.subselected = false;
                     this.onUnselected(puzzle);
                     puzzle.selected = null;
                  }

                  return us;
               }
            } else if (this.getSubselectionPossibles().isPossible(puzzle, x, y) && this.checkSubselectReachDistance(puzzle, x, y)) {
               this.subselectedX = x;
               this.subselectedY = y;
               this.subselected = true;
            }
         } else if (this.getUsePossibles().isPossible(puzzle, x, y) && this.checkUseReachDistance(puzzle, x, y)) {
            boolean us = this.use(puzzle, x, y);
            if (us) {
               this.subselected = false;
               this.onUnselected(puzzle);
               puzzle.selected = null;
            }

            return us;
         }
      }

      return false;
   }

   public boolean use(TerraformingResearchPuzzle puzzle, int x, int y) {
      return false;
   }

   @Nullable
   public TerraformingPlayerCommand getGenerationCommand(TerraformingResearchPuzzle puzzle, Random rand) {
      List<Integer> list = new ArrayList<>();

      for (int i = 0; i < 4; i++) {
         Phenomenon phenomenon = puzzle.getPhenomenonInDirection(this.posX, this.posY, i);
         if (phenomenon != null && Phenomenons.getInterflowId(this, phenomenon) > 0) {
            list.add(i);
         }
      }

      if (!list.isEmpty()) {
         int direction = list.get(rand.nextInt(list.size()));
         return new TerraformingPlayerCommand(TerraformingPlayerCommand.TRPlayerCommandType.MOVE_SELECTED, this.posX, this.posY, direction);
      } else {
         int direction = rand.nextInt(4);
         return puzzle.board[this.posX][this.posY].hasWay(direction)
            ? new TerraformingPlayerCommand(TerraformingPlayerCommand.TRPlayerCommandType.MOVE_SELECTED, this.posX, this.posY, direction)
            : null;
      }
   }

   public void onUnselected(TerraformingResearchPuzzle puzzle) {
   }

   public int onStep(TerraformingResearchPuzzle puzzle) {
      return this.getDelayAfterStep(false);
   }

   public void onCreated(TerraformingResearchPuzzle puzzle) {
      puzzle.queue.addLast(this);
   }

   public void onRemoved(TerraformingResearchPuzzle puzzle) {
      puzzle.queue.remove(this);
   }

   public void onDelayTick(TerraformingResearchPuzzle puzzle, int delayLast) {
   }

   public int getDelayAfterStep(boolean success) {
      return success ? 10 : 1;
   }

   public void setFields(TerraformingResearchPuzzle puzzle, boolean[][][] fields) {
   }

   public boolean split(TerraformingResearchPuzzle puzzle, int idElementToSave) {
      if (this.getId() <= 12) {
         return false;
      } else {
         Phenomenon tosave = Phenomenons.createById(idElementToSave);
         if (tosave == null) {
            return false;
         } else {
            int inerflowBit = Phenomenons.getInterflowBit(this.getId());
            int idElement = 1;

            for (int b = 2048; b > 0; b >>>= 1) {
               if ((inerflowBit & b) > 0) {
                  if (idElement == idElementToSave) {
                     this.onRemoved(puzzle);
                     puzzle.setPhenomenon(this.posX, this.posY, tosave);
                  } else {
                     puzzle.inventory[idElement]++;
                  }
               }

               idElement++;
            }

            return true;
         }
      }
   }

   public static void setFieldsInRadius(TerraformingResearchPuzzle puzzle, boolean[][][] fields, int x, int y, int radius, int field) {
      if (radius == 0) {
         fields[x][y][field] = true;
      } else {
         for (int xx = -radius; xx <= radius; xx++) {
            for (int yy = -radius; yy <= radius; yy++) {
               int finalX = x + xx;
               int finalY = y + yy;
               if (puzzle.checkBounds(finalX, finalY)) {
                  fields[finalX][finalY][field] = true;
               }
            }
         }
      }
   }

   public int radiusTo(int xTo, int yTo) {
      int r1 = Math.abs(this.posX - xTo);
      int r2 = Math.abs(this.posY - yTo);
      return Math.max(r1, r2);
   }

   public boolean checkUseReachDistance(TerraformingResearchPuzzle puzzle, int xTo, int yTo) {
      int reach = this.getBaseUseReach();
      if (reach == 0) {
         return false;
      } else {
         reach = this.getReachModified(puzzle, reach);
         int r = this.radiusTo(xTo, yTo);
         return r > 0 && r <= reach;
      }
   }

   public boolean checkSubselectReachDistance(TerraformingResearchPuzzle puzzle, int xTo, int yTo) {
      int reach = this.getBaseSubselectReach();
      if (reach == 0) {
         return false;
      } else {
         reach = this.getReachModified(puzzle, reach);
         int r = this.radiusTo(xTo, yTo);
         return r > 0 && r <= reach;
      }
   }

   public int getBaseUseReach() {
      return 0;
   }

   public int getBaseSubselectReach() {
      return 0;
   }

   public int getReachModified(TerraformingResearchPuzzle puzzle, int baseRadius) {
      return puzzle.board[this.posX][this.posY].surfaceTerrain == Phenomenons.surfaceCrystal ? baseRadius + 1 : baseRadius;
   }

   public boolean canUse() {
      return true;
   }

   public boolean hasSubselectionWhenUse() {
      return false;
   }

   public EnumClickPossible getSubselectionPossibles() {
      return EnumClickPossible.NONE;
   }

   public EnumClickPossible getUsePossibles() {
      return EnumClickPossible.NONE;
   }

   public static Vec2f getParticlePosInQuad(float progress, float radius) {
      float diameter = radius * 2.0F;
      if (progress >= 0.0F && progress < 0.25F) {
         progress = GetMOP.getfromto(progress, 0.0F, 0.25F);
         return new Vec2f(-radius + progress * diameter, radius);
      } else if (progress >= 0.25F && progress < 0.5F) {
         progress = GetMOP.getfromto(progress, 0.25F, 0.5F);
         return new Vec2f(radius, radius - progress * diameter);
      } else if (progress >= 0.5F && progress < 0.75F) {
         progress = GetMOP.getfromto(progress, 0.5F, 0.75F);
         return new Vec2f(radius - progress * diameter, -radius);
      } else {
         progress = GetMOP.getfromto(progress, 0.75F, 1.0F);
         return new Vec2f(-radius, -radius + progress * diameter);
      }
   }

   public static enum EnumClickPossible {
      NONE,
      ANY,
      SURFACE,
      PHENOMENON,
      POINT,
      NO_POINT,
      EMPTY_POINT;

      public boolean isPossible(TerraformingResearchPuzzle puzzle, int x, int y) {
         if (this == NONE) {
            return false;
         } else if (this == ANY) {
            return true;
         } else if (this == SURFACE) {
            return puzzle.getSurface(x, y, TerraformingResearchSurface.TRSurfaceType.TERRAIN) != null
               || puzzle.getSurface(x, y, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE) != null
               || puzzle.getSurface(x, y, TerraformingResearchSurface.TRSurfaceType.CREATURE) != null;
         } else if (this == PHENOMENON) {
            return puzzle.getPhenomenon(x, y) != null;
         } else if (this == POINT) {
            if (x >= 0 && y >= 0 && x < puzzle.width && y < puzzle.height) {
               TerraformingResearchPuzzle.TerraformingResearchCell cell = puzzle.board[x][y];
               return cell.isPoint;
            } else {
               return false;
            }
         } else if (this == NO_POINT) {
            if (x >= 0 && y >= 0 && x < puzzle.width && y < puzzle.height) {
               TerraformingResearchPuzzle.TerraformingResearchCell cell = puzzle.board[x][y];
               return !cell.isPoint;
            } else {
               return false;
            }
         } else if (this != EMPTY_POINT) {
            return false;
         } else if (x >= 0 && y >= 0 && x < puzzle.width && y < puzzle.height) {
            TerraformingResearchPuzzle.TerraformingResearchCell cell = puzzle.board[x][y];
            return cell.canFitPhenomenon() && cell.phenomenon == null;
         } else {
            return false;
         }
      }
   }
}
