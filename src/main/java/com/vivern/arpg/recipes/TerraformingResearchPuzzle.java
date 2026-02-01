package com.vivern.arpg.recipes;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.renders.RenderTerraformingResearch;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;

public class TerraformingResearchPuzzle {
   public TerraformingResearchCell[][] board;
   public int width;
   public int height;
   public ArrayDeque<Phenomenon> queue;
   public int ticksExisted = 0;
   public int currentStep = 0;
   public int delayAfterStep;
   public int[] inventory;
   public boolean skipAll = false;
   public boolean surfacesStepped = false;
   public TerraformingResearchCell selected;
   public int movingOrbs;
   public int interflowOrbs;
   public int splitOrbs;
   public TFRBalanceOfElements currentBalance;
   public TFRBalanceOfElements finalBalance;
   public int whatResearchBitshift;
   public int whatResearchKey;
   public boolean ISWINNED;
   public boolean isGENERATING;
   public float genChanceSplit;
   public boolean[][][] fields;
   public boolean isOnClient;
   public RenderTerraformingResearch linkedRender;
   public EntityPlayer researchingPlayer;
   public static Random random = new Random();
   public int generationSamples;
   public static TFRBalanceOfElements EMPTY_BALANCE = new TFRBalanceOfElements();

   public TerraformingResearchPuzzle(int width, int height) {
      this.currentBalance = EMPTY_BALANCE;
      this.finalBalance = EMPTY_BALANCE;
      this.ISWINNED = false;
      this.isGENERATING = false;
      this.isOnClient = false;
      this.width = width;
      this.height = height;
      this.clear();
   }

   public TerraformingResearchPuzzle() {
      this.currentBalance = EMPTY_BALANCE;
      this.finalBalance = EMPTY_BALANCE;
      this.ISWINNED = false;
      this.isGENERATING = false;
      this.isOnClient = false;
   }

   public void clear() {
      this.selected = null;
      this.inventory = new int[13];
      this.queue = new ArrayDeque<>();
      this.queue.add(new Phenomenons.PlayerAvatar());
      this.fields = new boolean[this.width][this.height][4];
      this.board = new TerraformingResearchCell[this.width][this.height];

      for (int x = 0; x < this.width; x++) {
         for (int y = 0; y < this.height; y++) {
            this.board[x][y] = new TerraformingResearchCell();
         }
      }
   }

   public void writeToNbt(NBTTagCompound tagCompound) {
      tagCompound.setInteger("width", this.width);
      tagCompound.setInteger("height", this.height);
      tagCompound.setInteger("movingOrbs", this.movingOrbs);
      tagCompound.setInteger("interflowOrbs", this.interflowOrbs);
      tagCompound.setInteger("splitOrbs", this.splitOrbs);
      tagCompound.setInteger("currentStep", this.currentStep);
      tagCompound.setInteger("delayAfterStep", this.delayAfterStep);
      tagCompound.setInteger("whatResearchKey", this.whatResearchKey);
      tagCompound.setInteger("whatResearchBitshift", this.whatResearchBitshift);
      tagCompound.setBoolean("surfacesStepped", this.surfacesStepped);
      NBTTagCompound tagCells = new NBTTagCompound();

      for (int x = 0; x < this.width; x++) {
         for (int y = 0; y < this.height; y++) {
            TerraformingResearchCell cell = this.board[x][y];
            String num = x + "_" + y;
            NBTTagCompound tagCell = new NBTTagCompound();
            cell.writeToNbt(tagCell);
            tagCells.setTag(num, tagCell);
         }
      }

      tagCompound.setTag("cells", tagCells);
      NBTTagCompound tagQueue = new NBTTagCompound();
      int i = 0;

      for (Phenomenon phenomenon : this.queue) {
         NBTTagCompound tagphenomenon = new NBTTagCompound();
         tagphenomenon.setInteger("phenomenonid", phenomenon.getId());
         phenomenon.writeToNbt(tagphenomenon);
         tagQueue.setTag(i + "", tagphenomenon);
         i++;
      }

      tagCompound.setTag("queue", tagQueue);
      tagCompound.setInteger("queueSize", i);
      NBTTagCompound tagInventory = new NBTTagCompound();

      for (int j = 1; j < this.inventory.length; j++) {
         tagInventory.setInteger("" + j, this.inventory[j]);
      }

      tagCompound.setTag("inventoryElements", tagInventory);
      if (this.finalBalance != null) {
         NBTTagCompound balanceFinal = new NBTTagCompound();
         this.finalBalance.writeToNbt(balanceFinal);
         tagCompound.setTag("balanceFinal", balanceFinal);
      }
   }

   public void readFromNbt(NBTTagCompound tagCompound, boolean onClient) {
      this.selected = null;
      this.inventory = new int[13];
      if (tagCompound.hasKey("width")) {
         this.width = tagCompound.getInteger("width");
      }

      if (tagCompound.hasKey("height")) {
         this.height = tagCompound.getInteger("height");
      }

      if (onClient) {
         this.setClient(new RenderTerraformingResearch(this.width, this.height));
      }

      this.fields = new boolean[this.width][this.height][4];
      this.board = new TerraformingResearchCell[this.width][this.height];

      for (int x = 0; x < this.width; x++) {
         for (int y = 0; y < this.height; y++) {
            this.board[x][y] = new TerraformingResearchCell();
         }
      }

      if (tagCompound.hasKey("movingOrbs")) {
         this.movingOrbs = tagCompound.getInteger("movingOrbs");
      }

      if (tagCompound.hasKey("interflowOrbs")) {
         this.interflowOrbs = tagCompound.getInteger("interflowOrbs");
      }

      if (tagCompound.hasKey("splitOrbs")) {
         this.splitOrbs = tagCompound.getInteger("splitOrbs");
      }

      if (tagCompound.hasKey("currentStep")) {
         this.currentStep = tagCompound.getInteger("currentStep");
      }

      if (tagCompound.hasKey("delayAfterStep")) {
         this.delayAfterStep = tagCompound.getInteger("delayAfterStep");
      }

      if (tagCompound.hasKey("whatResearchKey")) {
         this.whatResearchKey = tagCompound.getInteger("whatResearchKey");
      }

      if (tagCompound.hasKey("whatResearchBitshift")) {
         this.whatResearchBitshift = tagCompound.getInteger("whatResearchBitshift");
      }

      if (tagCompound.hasKey("surfacesStepped")) {
         this.surfacesStepped = tagCompound.getBoolean("surfacesStepped");
      }

      if (tagCompound.hasKey("cells")) {
         NBTTagCompound tagCells = tagCompound.getCompoundTag("cells");

         for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
               String num = x + "_" + y;
               if (tagCells.hasKey(num)) {
                  NBTTagCompound tagCell = tagCells.getCompoundTag(num);
                  this.board[x][y].readFromNbt(tagCell, this.linkedRender, x, y);
               }
            }
         }
      }

      this.queue = new ArrayDeque<>();
      if (tagCompound.hasKey("queue") && tagCompound.hasKey("queueSize")) {
         NBTTagCompound tagQueue = tagCompound.getCompoundTag("queue");
         int queueSize = tagCompound.getInteger("queueSize");

         for (int i = 0; i < queueSize; i++) {
            String iii = i + "";
            if (tagQueue.hasKey(iii)) {
               NBTTagCompound tagphenomenon = tagQueue.getCompoundTag(iii);
               if (tagphenomenon.hasKey("phenomenonid")) {
                  int pId = tagphenomenon.getInteger("phenomenonid");
                  if (pId > 0) {
                     Phenomenon phenomenon = Phenomenons.createById(pId);
                     phenomenon.readFromNbt(tagphenomenon);
                     this.queue.addLast(phenomenon);
                     if (this.checkBounds(phenomenon.posX, phenomenon.posY)) {
                        this.board[phenomenon.posX][phenomenon.posY].phenomenon = phenomenon;
                     }
                  } else {
                     this.queue.addLast(new Phenomenons.PlayerAvatar());
                  }
               }
            }
         }
      }

      if (this.queue.isEmpty()) {
         this.queue.add(new Phenomenons.PlayerAvatar());
      }

      if (tagCompound.hasKey("inventoryElements")) {
         NBTTagCompound tagInventory = tagCompound.getCompoundTag("inventoryElements");

         for (int j = 1; j < this.inventory.length; j++) {
            this.inventory[j] = tagInventory.getInteger("" + j);
         }
      }

      if (tagCompound.hasKey("balanceFinal")) {
         NBTTagCompound balanceFinal = tagCompound.getCompoundTag("balanceFinal");
         this.finalBalance = new TFRBalanceOfElements(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
         this.finalBalance.readFromNbt(balanceFinal);
      }
   }

   public void setClient(RenderTerraformingResearch linkedRender) {
      this.isOnClient = true;
      this.linkedRender = linkedRender;
   }

   public void generate(long seed, ExploringField exploringField) {
      System.out.println("genNONTutorial");
      ExploringField.ExploringPoint exploringPoint = exploringField.getExploringPoint(this.whatResearchKey, this.whatResearchBitshift);
      Random rand = new Random(seed);
      System.out.println("genNONTutorial generateRTInventory");
      exploringPoint.generateRTInventory(rand, this.inventory);
      this.movingOrbs = 10;
      this.interflowOrbs = 5;
      this.splitOrbs = 3;
      System.out.println("genNONTutorial generateWays");
      this.generateWays(rand);
      System.out.println("genNONTutorial generateResearchBalance");
      this.finalBalance = this.generateResearchBalance(rand);
      System.out.println("genNONTutorial clear");
      rand = new Random(seed);
      this.clear();
      System.out.println("genNONTutorial generateRTInventory 2");
      exploringPoint.generateRTInventory(rand, this.inventory);
      this.movingOrbs = 10;
      this.interflowOrbs = 5;
      this.splitOrbs = 3;
      System.out.println("genNONTutorial generateWays 2");
      this.generateWays(rand);
      System.out.println("genNONTutorial end");
   }

   public void generateWays(Random rand) {
      this.generationSamples = 50 + rand.nextInt(20) + Math.max(this.width - 10, 1) * Math.max(this.height - 10, 1);
      this.genChanceSplit = 0.25F - Math.min(0.1F * ((this.width + this.height) / 2.0F - 10.0F) / 10.0F, 0.1F);
      int xx = this.width / 2;
      int yy = this.height / 2;
      this.recursiveGenerate(xx, yy, rand.nextInt(4), rand);
      this.board[xx][yy].isPoint = true;

      while (this.generationSamples > 0) {
         int xxx = 1 + rand.nextInt(this.width - 1);
         int yyy = 1 + rand.nextInt(this.height - 1);
         this.recursiveGenerate(xxx, yyy, rand.nextInt(4), rand);
         this.board[xxx][yyy].isPoint = true;
      }

      for (int x = 0; x < this.width; x++) {
         for (int y = 0; y < this.height; y++) {
            if (this.board[x][y].isPoint) {
               if (!this.board[x][y].wayUp && rand.nextFloat() < 0.5F) {
                  int max = MathHelper.floor(rand.nextInt(4) * rand.nextFloat());
                  if (this.tryGenerateCross(x, y, 0, max, true)) {
                     this.tryGenerateCross(x, y, 0, max, false);
                  }
               }

               if (!this.board[x][y].wayRight && rand.nextFloat() < 0.5F) {
                  int max = MathHelper.floor(rand.nextInt(4) * rand.nextFloat());
                  if (this.tryGenerateCross(x, y, 1, max, true)) {
                     this.tryGenerateCross(x, y, 1, max, false);
                  }
               }

               if (!this.board[x][y].wayDown && rand.nextFloat() < 0.5F) {
                  int max = MathHelper.floor(rand.nextInt(4) * rand.nextFloat());
                  if (this.tryGenerateCross(x, y, 2, max, true)) {
                     this.tryGenerateCross(x, y, 2, max, false);
                  }
               }

               if (!this.board[x][y].wayLeft && rand.nextFloat() < 0.5F) {
                  int max = MathHelper.floor(rand.nextInt(4) * rand.nextFloat());
                  if (this.tryGenerateCross(x, y, 3, max, true)) {
                     this.tryGenerateCross(x, y, 3, max, false);
                  }
               }
            }
         }
      }

      this.recalcAllWayRenderIndexes();
      int rrr = rand.nextInt(10);

      for (int i = 0; i < rrr; i++) {
         this.board[rand.nextInt(this.width)][rand.nextInt(this.height)].orb = 1;
      }
   }

   public void recalcAllWayRenderIndexes() {
      for (int x = 0; x < this.width; x++) {
         for (int y = 0; y < this.height; y++) {
            this.board[x][y].recalcWayRenderIndex();
         }
      }
   }

   public void recursiveGenerate(int posx, int posy, int direction, Random rand) {
      this.generationSamples--;
      if (direction == 0) {
         if (posy < 1) {
            this.board[posx][posy].isPoint = true;
            return;
         }

         this.board[posx][posy].wayUp = true;
         this.board[posx][--posy].wayDown = true;
      } else if (direction == 1) {
         if (posx + 1 >= this.width) {
            this.board[posx][posy].isPoint = true;
            return;
         }

         this.board[posx][posy].wayRight = true;
         this.board[++posx][posy].wayLeft = true;
      } else if (direction == 2) {
         if (posy + 1 >= this.height) {
            this.board[posx][posy].isPoint = true;
            return;
         }

         this.board[posx][posy].wayDown = true;
         this.board[posx][++posy].wayUp = true;
      } else if (direction == 3) {
         if (posx < 1) {
            this.board[posx][posy].isPoint = true;
            return;
         }

         this.board[posx][posy].wayLeft = true;
         this.board[--posx][posy].wayRight = true;
      }

      if (this.generationSamples > 0 && rand.nextFloat() < 0.95F) {
         int opposite = directionOpposite(direction);
         int randDirection1 = rand.nextInt(4);
         if (randDirection1 == opposite) {
            randDirection1 = rand.nextInt(4);
         }

         if (randDirection1 == opposite) {
            this.board[posx][posy].isPoint = true;
            return;
         }

         this.recursiveGenerate(posx, posy, randDirection1, rand);
         if (rand.nextFloat() < this.genChanceSplit) {
            int randDirection2 = rand.nextInt(4);
            if (randDirection2 != randDirection1 && randDirection2 != opposite) {
               this.recursiveGenerate(posx, posy, randDirection2, rand);
               if (rand.nextFloat() < 0.4F) {
                  int randDirection3 = rand.nextInt(4);
                  if (randDirection3 != randDirection1 && randDirection3 != randDirection2 && randDirection3 != opposite) {
                     this.recursiveGenerate(posx, posy, randDirection3, rand);
                  }
               }
            }
         }
      } else {
         this.board[posx][posy].isPoint = true;
      }
   }

   public boolean tryGenerateCross(int posx, int posy, int direction, int maxNext, boolean simulate) {
      int offX = offsetX(posx, direction);
      int offY = offsetY(posy, direction);
      if (this.checkBounds(offX, offY) && !this.board[offX][offY].hasWays()) {
         this.tryGenerateBranch(posx, posy, direction, simulate, false);
         if (!this.tryGenerateBranch(offX, offY, directionRotate(direction, false), simulate, true)
            || !this.tryGenerateBranch(offX, offY, directionRotate(direction, true), simulate, true)) {
            return false;
         } else {
            return maxNext > 0
               ? this.tryGenerateCross(offX, offY, direction, maxNext - 1, simulate)
               : this.tryGenerateBranch(offX, offY, direction, simulate, true);
         }
      } else {
         if (!simulate) {
            this.board[posx][posy].isPoint = true;
         }

         return false;
      }
   }

   public boolean tryGenerateBranch(int posx, int posy, int direction, boolean simulate, boolean setPoint) {
      if (direction == 0) {
         if (posy < 1) {
            return false;
         }

         if (!simulate) {
            this.board[posx][posy].wayUp = true;
         }

         if (this.board[posx][--posy].hasWays()) {
            return false;
         }

         if (!simulate) {
            this.board[posx][posy].wayDown = true;
         }
      } else if (direction == 1) {
         if (posx + 1 >= this.width) {
            return false;
         }

         if (!simulate) {
            this.board[posx][posy].wayRight = true;
         }

         if (this.board[++posx][posy].hasWays()) {
            return false;
         }

         if (!simulate) {
            this.board[posx][posy].wayLeft = true;
         }
      } else if (direction == 2) {
         if (posy + 1 >= this.height) {
            return false;
         }

         if (!simulate) {
            this.board[posx][posy].wayDown = true;
         }

         if (this.board[posx][++posy].hasWays()) {
            return false;
         }

         if (!simulate) {
            this.board[posx][posy].wayUp = true;
         }
      } else if (direction == 3) {
         if (posx < 1) {
            return false;
         }

         if (!simulate) {
            this.board[posx][posy].wayLeft = true;
         }

         if (this.board[--posx][posy].hasWays()) {
            return false;
         }

         if (!simulate) {
            this.board[posx][posy].wayRight = true;
         }
      }

      if (!simulate && setPoint) {
         this.board[posx][posy].isPoint = true;
      }

      return true;
   }

   public static int directionOpposite(int d) {
      if (d == 0) {
         return 2;
      } else if (d == 1) {
         return 3;
      } else if (d == 2) {
         return 0;
      } else {
         return d == 3 ? 1 : 0;
      }
   }

   public static int directionRotate(int d, boolean clockwise) {
      if (clockwise) {
         if (d == 0) {
            return 1;
         }

         if (d == 1) {
            return 2;
         }

         if (d == 2) {
            return 3;
         }

         if (d == 3) {
            return 0;
         }
      } else {
         if (d == 0) {
            return 3;
         }

         if (d == 1) {
            return 0;
         }

         if (d == 2) {
            return 1;
         }

         if (d == 3) {
            return 2;
         }
      }

      return 0;
   }

   public static int offsetX(int posx, int direction) {
      if (direction == 1) {
         return posx + 1;
      } else {
         return direction == 3 ? posx - 1 : posx;
      }
   }

   public static int offsetY(int posy, int direction) {
      if (direction == 2) {
         return posy + 1;
      } else {
         return direction == 0 ? posy - 1 : posy;
      }
   }

   public void onPlayerStepEnd() {
      this.currentStep++;
      this.surfacesStepped = false;
   }

   public void doStepSurfaces() {
      TerraformingResearchSurface.TFRSurfaceChange[][] changes = new TerraformingResearchSurface.TFRSurfaceChange[this.width][this.height];

      for (int x = 0; x < this.width; x++) {
         for (int y = 0; y < this.height; y++) {
            this.board[x][y].onStep(this, changes, x, y);
         }
      }

      for (int x = 0; x < this.width; x++) {
         for (int y = 0; y < this.height; y++) {
            if (changes[x][y] != null) {
               changes[x][y].applyChange(this, x, y);
            }
         }
      }

      for (int x = 0; x < this.width; x++) {
         for (int yx = 0; yx < this.height; yx++) {
            for (int i = 0; i < 4; i++) {
               this.fields[x][yx][i] = false;
            }
         }
      }

      for (Phenomenon phenomenon : this.queue) {
         phenomenon.setFields(this, this.fields);
      }
   }

   public boolean checkForPlayer() {
      for (Phenomenon phenomenon : this.queue) {
         if (phenomenon.isPlayer()) {
            return true;
         }
      }

      return false;
   }

   public void update(@Nullable TerraformingPlayerCommand playerCommand) {
      if (!this.ISWINNED && this.queue != null) {
         if (!this.checkForPlayer()) {
            throw new RuntimeException("NO PLAYER AT QUEUE IN TerraformingResearchPuzzle");
         } else {
            this.ticksExisted++;
            if (this.researchingPlayer == null && playerCommand != null && playerCommand.player != null) {
               this.researchingPlayer = playerCommand.player;
            }

            if (this.linkedRender != null) {
               for (Phenomenon phenomenon : this.queue) {
                  float xx = phenomenon.posX * this.linkedRender.cellWidth;
                  float yy = phenomenon.posY * this.linkedRender.cellHeight;
                  phenomenon.updateAnimation(this, this.linkedRender, xx, yy, phenomenon.posX, phenomenon.posY);
               }
            }

            if (this.skipAll) {
               for (Phenomenon phenomenon = this.queue.peekFirst(); !phenomenon.isPlayer(); phenomenon = this.queue.peekFirst()) {
                  phenomenon.onStep(this);
                  this.queue.addLast(this.queue.pollFirst());
               }

               this.skipAll = false;
            } else {
               Phenomenon phenomenon = this.queue.peekFirst();
               if (this.delayAfterStep > 0) {
                  if (playerCommand != null
                     && (
                        playerCommand.commandType == TerraformingPlayerCommand.TRPlayerCommandType.SKIP
                           || playerCommand.commandType == TerraformingPlayerCommand.TRPlayerCommandType.SKIP_ALL
                     )) {
                     this.delayAfterStep = 0;
                     if (playerCommand.commandType == TerraformingPlayerCommand.TRPlayerCommandType.SKIP_ALL) {
                        this.skipAll = true;
                     }
                  } else {
                     this.delayAfterStep--;
                  }

                  phenomenon.onDelayTick(this, this.delayAfterStep);
                  if (this.delayAfterStep == 0) {
                     this.queue.addLast(this.queue.pollFirst());
                  }
               } else if (phenomenon.isPlayer()) {
                  if (playerCommand != null && playerCommand.player != this.researchingPlayer) {
                     playerCommand = null;
                  }

                  if (!this.surfacesStepped) {
                     this.doStepSurfaces();
                     this.surfacesStepped = true;
                     if (!this.isGENERATING) {
                        this.currentBalance = this.calculateBalanceOfElements();
                        if (!this.isOnClient && this.currentBalance.isWin(this.finalBalance) && this.researchingPlayer != null) {
                           ExploringField.onResearchWinOnServer(this, this.researchingPlayer);
                           this.researchingPlayer
                              .world
                              .playSound(
                                 (EntityPlayer)null, this.researchingPlayer.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 0.9F, 1.0F
                              );
                           this.ISWINNED = true;
                           System.out.println("WINNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
                        }
                     }
                  }

                  if (playerCommand != null) {
                     if (playerCommand.commandType == TerraformingPlayerCommand.TRPlayerCommandType.BOARD_SELECT) {
                        if (this.selected != null && this.selected.phenomenon != null) {
                           if (this.selected.phenomenon.clickOtherDuringSelected(this, playerCommand.boardSelectX, playerCommand.boardSelectY)) {
                              this.delayAfterStep = 10;
                              this.onPlayerStepEnd();
                           }
                        } else if (this.checkBounds(playerCommand.boardSelectX, playerCommand.boardSelectY)) {
                           this.selected = this.board[playerCommand.boardSelectX][playerCommand.boardSelectY];
                        }
                     }

                     if (this.isGENERATING) {
                        System.out.println("playerCommand != null");
                     }

                     if (playerCommand.commandType == TerraformingPlayerCommand.TRPlayerCommandType.PLACE_FROM_INVENTORY) {
                        if (this.isGENERATING) {
                           System.out.println("commandType == TRPlayerCommandType");
                        }

                        if (this.checkBounds(playerCommand.boardSelectX, playerCommand.boardSelectY)) {
                           if (this.isGENERATING) {
                              System.out.println("checkBounds");
                           }

                           TerraformingResearchCell cell = this.board[playerCommand.boardSelectX][playerCommand.boardSelectY];
                           if (cell.phenomenon == null && cell.canFitPhenomenon()) {
                              if (this.isGENERATING) {
                                 System.out.println("canFitPhenomenon");
                              }

                              if (playerCommand.data >= 0 && playerCommand.data < this.inventory.length && this.inventory[playerCommand.data] > 0) {
                                 if (this.isGENERATING) {
                                    System.out.println("[playerCommand.data] > 0");
                                 }

                                 Phenomenon created = Phenomenons.createById(playerCommand.data);
                                 if (created != null) {
                                    if (this.isGENERATING) {
                                       System.out.println("created");
                                    }

                                    cell.phenomenon = created;
                                    cell.phenomenon.posX = playerCommand.boardSelectX;
                                    cell.phenomenon.posY = playerCommand.boardSelectY;
                                    cell.phenomenon.onCreated(this);
                                    this.inventory[playerCommand.data]--;
                                    this.delayAfterStep = 10;
                                    this.onPlayerStepEnd();
                                 }
                              }
                           }
                        }
                     }

                     if (playerCommand.commandType == TerraformingPlayerCommand.TRPlayerCommandType.MOVE_SELECTED
                        && this.selected != null
                        && this.selected.phenomenon != null) {
                        boolean canMoveFree = this.canMove(this.selected.phenomenon.posX, this.selected.phenomenon.posY);
                        if ((this.movingOrbs > 0 || canMoveFree) && playerCommand.data >= 0 && playerCommand.data < 4) {
                           Phenomenon selectedPhenomenon = this.selected.phenomenon;
                           if (this.move(selectedPhenomenon.posX, selectedPhenomenon.posY, playerCommand.data)) {
                              selectedPhenomenon.onUnselected(this);
                              this.selected = null;
                              this.delayAfterStep = 8;
                              if (!canMoveFree) {
                                 this.movingOrbs--;
                              }

                              this.onPlayerStepEnd();
                           }
                        }
                     }

                     if (playerCommand.commandType == TerraformingPlayerCommand.TRPlayerCommandType.GET_ORB
                        && this.checkBounds(playerCommand.boardSelectX, playerCommand.boardSelectY)) {
                        TerraformingResearchCell cell = this.board[playerCommand.boardSelectX][playerCommand.boardSelectY];
                        if (cell.orb != 0 && (cell.surfaceTerrain != null || cell.surfaceAtmosphere != null || cell.surfaceCreature != null)) {
                           if (cell.orb == 1) {
                              this.movingOrbs++;
                           }

                           if (cell.orb == 2) {
                              this.interflowOrbs++;
                           }

                           if (cell.orb == 3) {
                              this.splitOrbs++;
                           }

                           cell.orb = 0;
                        }
                     }

                     if (playerCommand.commandType == TerraformingPlayerCommand.TRPlayerCommandType.SPLIT
                        && this.splitOrbs > 0
                        && this.checkBounds(playerCommand.boardSelectX, playerCommand.boardSelectY)) {
                        TerraformingResearchCell cell = this.board[playerCommand.boardSelectX][playerCommand.boardSelectY];
                        if (this.selected != null && this.selected.phenomenon != null) {
                           this.selected.phenomenon.onUnselected(this);
                           this.selected = null;
                        }

                        if (cell.phenomenon.split(this, playerCommand.data)) {
                           this.delayAfterStep = 8;
                           this.splitOrbs--;
                           this.onPlayerStepEnd();
                        }
                     }

                     if (playerCommand.commandType == TerraformingPlayerCommand.TRPlayerCommandType.CANCEL && this.selected != null) {
                        this.selected.phenomenon.onUnselected(this);
                        this.selected = null;
                     }
                  }
               } else {
                  this.delayAfterStep = phenomenon.onStep(this);
               }
            }
         }
      }
   }

   public void addSurfaces(int x, int y, int radius, TerraformingResearchSurface surface) {
      if (radius == 0) {
         this.addSurface(x, y, surface);
      } else {
         for (int xx = -radius; xx <= radius; xx++) {
            for (int yy = -radius; yy <= radius; yy++) {
               this.addSurface(x + xx, y + yy, surface);
            }
         }
      }
   }

   public void setSurfaces(int x, int y, int radius, TerraformingResearchSurface surface) {
      if (radius == 0) {
         this.setSurface(x, y, surface);
      } else {
         for (int xx = -radius; xx <= radius; xx++) {
            for (int yy = -radius; yy <= radius; yy++) {
               this.setSurface(x + xx, y + yy, surface);
            }
         }
      }
   }

   public void removeSurfaces(int x, int y, int radius, TerraformingResearchSurface surface) {
      if (radius == 0) {
         this.removeSurface(x, y, surface);
      } else {
         for (int xx = -radius; xx <= radius; xx++) {
            for (int yy = -radius; yy <= radius; yy++) {
               this.removeSurface(x + xx, y + yy, surface);
            }
         }
      }
   }

   public void removeSurfaces(int x, int y, int radius, TerraformingResearchSurface.TRSurfaceType surfaceType) {
      if (radius == 0) {
         this.removeSurface(x, y, surfaceType);
      } else {
         for (int xx = -radius; xx <= radius; xx++) {
            for (int yy = -radius; yy <= radius; yy++) {
               this.removeSurface(x + xx, y + yy, surfaceType);
            }
         }
      }
   }

   public void setSurface(int x, int y, TerraformingResearchSurface surface) {
      if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
         if (surface.type == TerraformingResearchSurface.TRSurfaceType.TERRAIN) {
            this.board[x][y].surfaceTerrain = surface;
            if (this.isOnClient) {
               this.linkedRender.setTerrainAnimation(surface, surface.type, x, y, false);
            }
         }

         if (surface.type == TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE) {
            this.board[x][y].surfaceAtmosphere = surface;
            if (this.isOnClient) {
               this.linkedRender.setTerrainAnimation(surface, surface.type, x, y, false);
            }
         }

         if (surface.type == TerraformingResearchSurface.TRSurfaceType.CREATURE) {
         }
      }
   }

   public void addSurface(int x, int y, TerraformingResearchSurface surface) {
      if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
         if (surface.type == TerraformingResearchSurface.TRSurfaceType.TERRAIN && surface.canReplace(this.board[x][y].surfaceTerrain)) {
            this.board[x][y].surfaceTerrain = surface;
            if (this.isOnClient) {
               this.linkedRender.setTerrainAnimation(surface, surface.type, x, y, false);
            }
         }

         if (surface.type == TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE && surface.canReplace(this.board[x][y].surfaceAtmosphere)) {
            this.board[x][y].surfaceAtmosphere = surface;
            if (this.isOnClient) {
               this.linkedRender.setTerrainAnimation(surface, surface.type, x, y, false);
            }
         }

         if (surface.type == TerraformingResearchSurface.TRSurfaceType.CREATURE) {
         }
      }
   }

   public void removeSurface(int x, int y, TerraformingResearchSurface surface) {
      if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
         if (surface.type == TerraformingResearchSurface.TRSurfaceType.TERRAIN && this.board[x][y].surfaceTerrain == surface) {
            this.board[x][y].surfaceTerrain = null;
            if (this.isOnClient) {
               this.linkedRender.setTerrainAnimation(null, surface.type, x, y, false);
            }
         }

         if (surface.type == TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE && this.board[x][y].surfaceAtmosphere == surface) {
            this.board[x][y].surfaceAtmosphere = null;
            if (this.isOnClient) {
               this.linkedRender.setTerrainAnimation(null, surface.type, x, y, false);
            }
         }

         if (surface.type == TerraformingResearchSurface.TRSurfaceType.CREATURE) {
         }
      }
   }

   public void removeSurface(int x, int y, TerraformingResearchSurface.TRSurfaceType surfaceType) {
      if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
         if (surfaceType == TerraformingResearchSurface.TRSurfaceType.TERRAIN) {
            this.board[x][y].surfaceTerrain = null;
            if (this.isOnClient) {
               this.linkedRender.setTerrainAnimation(null, surfaceType, x, y, false);
            }
         }

         if (surfaceType == TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE) {
            this.board[x][y].surfaceAtmosphere = null;
            if (this.isOnClient) {
               this.linkedRender.setTerrainAnimation(null, surfaceType, x, y, false);
            }
         }

         if (surfaceType == TerraformingResearchSurface.TRSurfaceType.CREATURE) {
         }
      }
   }

   @Nullable
   public TerraformingResearchSurface getSurface(int x, int y, TerraformingResearchSurface.TRSurfaceType surfaceType) {
      if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
         return null;
      } else if (surfaceType == TerraformingResearchSurface.TRSurfaceType.TERRAIN) {
         return this.board[x][y].surfaceTerrain;
      } else if (surfaceType == TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE) {
         return this.board[x][y].surfaceAtmosphere;
      } else {
         if (surfaceType == TerraformingResearchSurface.TRSurfaceType.CREATURE) {
         }

         return null;
      }
   }

   public TerraformingResearchCell cell(int[] pos_arrayTwoInts) {
      return this.board[pos_arrayTwoInts[0]][pos_arrayTwoInts[1]];
   }

   public boolean checkBounds(int x, int y) {
      return x >= 0 && y >= 0 && x < this.width && y < this.height;
   }

   public boolean addPhenomenon(int x, int y, int phenomenonId, int maxInterflowLvl) {
      if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
         TerraformingResearchCell cell = this.board[x][y];
         if (cell.phenomenon == null && cell.canFitPhenomenon()) {
            cell.phenomenon = Phenomenons.createById(phenomenonId);
            if (cell.phenomenon != null) {
               cell.phenomenon.posX = x;
               cell.phenomenon.posY = y;
               cell.phenomenon.onCreated(this);
               return true;
            }
         }

         if (cell.phenomenon != null && cell.canFitPhenomenon() && maxInterflowLvl > 1) {
            int interflowId = Phenomenons.getInterflowId(cell.phenomenon.getId(), phenomenonId);
            if (interflowId > 0) {
               cell.phenomenon.onRemoved(this);
               cell.phenomenon = Phenomenons.createById(interflowId);
               if (cell.phenomenon != null) {
                  cell.phenomenon.posX = x;
                  cell.phenomenon.posY = y;
                  cell.phenomenon.onCreated(this);
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean removePhenomenon(int x, int y, int phenomenonId) {
      if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
         TerraformingResearchCell cell = this.board[x][y];
         if (cell.phenomenon == null) {
            return false;
         } else if (phenomenonId == cell.phenomenon.getId()) {
            cell.phenomenon.onRemoved(this);
            cell.phenomenon = null;
            return true;
         } else {
            int bitsHas = Phenomenons.getInterflowBit(cell.phenomenon.getId());
            int bitToRemove = Phenomenons.getInterflowBit(phenomenonId);
            int bitsResult = bitsHas & ~bitToRemove;
            if (bitsResult == bitsHas) {
               return false;
            } else {
               int idResult = Phenomenons.getIdByBit(bitsResult);
               if (idResult > 0) {
                  cell.phenomenon.onRemoved(this);
                  cell.phenomenon = Phenomenons.createById(idResult);
                  cell.phenomenon.posX = x;
                  cell.phenomenon.posY = y;
                  cell.phenomenon.onCreated(this);
                  return true;
               } else {
                  return false;
               }
            }
         }
      } else {
         return false;
      }
   }

   public boolean addPhenomenon(int x, int y, Phenomenon phenomenon, int maxInterflowLvl) {
      if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
         TerraformingResearchCell cell = this.board[x][y];
         if (cell.phenomenon == null && cell.canFitPhenomenon() && maxInterflowLvl <= 1) {
            cell.phenomenon = phenomenon;
            if (cell.phenomenon != null) {
               cell.phenomenon.posX = x;
               cell.phenomenon.posY = y;
               cell.phenomenon.onCreated(this);
               return true;
            }
         }

         if (cell.phenomenon != null && cell.canFitPhenomenon() && maxInterflowLvl > 1) {
            int interflowId = Phenomenons.getInterflowId(cell.phenomenon, phenomenon);
            if (interflowId > 0) {
               phenomenon.onRemoved(this);
               cell.phenomenon.onRemoved(this);
               cell.phenomenon = Phenomenons.createById(interflowId);
               if (cell.phenomenon != null) {
                  cell.phenomenon.posX = x;
                  cell.phenomenon.posY = y;
                  cell.phenomenon.onCreated(this);
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean setPhenomenon(int x, int y, Phenomenon phenomenon) {
      if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
         TerraformingResearchCell cell = this.board[x][y];
         if (cell.canFitPhenomenon()) {
            cell.phenomenon = phenomenon;
            if (cell.phenomenon != null) {
               cell.phenomenon.posX = x;
               cell.phenomenon.posY = y;
               cell.phenomenon.onCreated(this);
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean destroyPhenomenon(int x, int y) {
      if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
         TerraformingResearchCell cell = this.board[x][y];
         if (cell.phenomenon != null) {
            cell.phenomenon.onRemoved(this);
            cell.phenomenon = null;
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean canMove(int x, int y) {
      return this.fields[x][y][0];
   }

   public boolean move(int x, int y, int direction) {
      boolean pleasureOnStart = this.fields[x][y][1];
      TerraformingResearchCell cell = this.board[x][y];
      if (direction == 0 && !cell.wayUp) {
         return false;
      } else if (direction == 1 && !cell.wayRight) {
         return false;
      } else if (direction == 2 && !cell.wayDown) {
         return false;
      } else if (direction == 3 && !cell.wayLeft) {
         return false;
      } else {
         if (cell.phenomenon != null) {
            for (int i = 0; i < 100; i++) {
               if (direction == 0) {
                  if (--y < 0) {
                     return false;
                  }
               }

               if (direction == 1) {
                  if (++x >= this.width) {
                     return false;
                  }
               }

               if (direction == 2) {
                  if (++y >= this.height) {
                     return false;
                  }
               }

               if (direction == 3) {
                  if (--x < 0) {
                     return false;
                  }
               }

               TerraformingResearchCell target = this.board[x][y];
               if (target.canFitPhenomenon()) {
                  if (target.phenomenon == null) {
                     cell.phenomenon.onRemoved(this);
                     target.phenomenon = cell.phenomenon;
                     cell.phenomenon = null;
                     target.phenomenon.posX = x;
                     target.phenomenon.posY = y;
                     target.phenomenon.onCreated(this);
                     return true;
                  }

                  int maxInterflowLvl = !pleasureOnStart && !this.fields[x][y][1] ? 2 : 12;
                  if (this.addPhenomenon(x, y, cell.phenomenon, maxInterflowLvl)) {
                     cell.phenomenon = null;
                     return true;
                  }

                  return false;
               }

               direction = target.transformMoveDirection(direction);
               if (direction < 0) {
                  return false;
               }
            }

            System.out.println("WARNING! TerraformingResearchPuzzle move() is iterate over 100!");
         }

         return false;
      }
   }

   public boolean teleportPhenomenon(int fromX, int flomY, int toX, int toY) {
      TerraformingResearchCell cell = this.board[fromX][flomY];
      if (cell.phenomenon == null) {
         return false;
      } else {
         TerraformingResearchCell target = this.board[toX][toY];
         if (target.canFitPhenomenon()) {
            if (target.phenomenon == null) {
               cell.phenomenon.onRemoved(this);
               target.phenomenon = cell.phenomenon;
               cell.phenomenon = null;
               target.phenomenon.posX = toX;
               target.phenomenon.posY = toY;
               target.phenomenon.onCreated(this);
               return true;
            } else {
               boolean pleasureOnStart = this.fields[fromX][flomY][1];
               int maxInterflowLvl = !pleasureOnStart && !this.fields[toX][toY][1] ? 2 : 12;
               if (this.addPhenomenon(toX, toY, cell.phenomenon, maxInterflowLvl)) {
                  cell.phenomenon = null;
                  return true;
               } else {
                  return false;
               }
            }
         } else {
            return false;
         }
      }
   }

   public boolean addPhenomenonToDirection(int x, int y, int direction, int phenomenonId, int maxInterflowLvl) {
      TerraformingResearchCell cell = this.board[x][y];
      if (direction == 0 && !cell.wayUp) {
         return false;
      } else if (direction == 1 && !cell.wayRight) {
         return false;
      } else if (direction == 2 && !cell.wayDown) {
         return false;
      } else if (direction == 3 && !cell.wayLeft) {
         return false;
      } else {
         for (int i = 0; i < 100; i++) {
            if (direction == 0) {
               if (--y < 0) {
                  return false;
               }
            }

            if (direction == 1) {
               if (++x >= this.width) {
                  return false;
               }
            }

            if (direction == 2) {
               if (++y >= this.height) {
                  return false;
               }
            }

            if (direction == 3) {
               if (--x < 0) {
                  return false;
               }
            }

            TerraformingResearchCell target = this.board[x][y];
            if (target.canFitPhenomenon()) {
               if (target.phenomenon == null) {
                  target.phenomenon = Phenomenons.createById(phenomenonId);
                  target.phenomenon.posX = x;
                  target.phenomenon.posY = y;
                  target.phenomenon.onCreated(this);
                  return true;
               }

               if (this.addPhenomenon(x, y, phenomenonId, maxInterflowLvl)) {
                  return true;
               }

               return false;
            }

            direction = target.transformMoveDirection(direction);
            if (direction < 0) {
               return false;
            }
         }

         System.out.println("WARNING! PhenomenVoid addMove() is iterate over 100!");
         return false;
      }
   }

   @Nullable
   public Phenomenon getPhenomenon(int x, int y) {
      if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
         TerraformingResearchCell cell = this.board[x][y];
         return cell.phenomenon;
      } else {
         return null;
      }
   }

   @Nullable
   public Phenomenon getPhenomenonInDirection(int x, int y, int direction) {
      TerraformingResearchCell cell = this.board[x][y];
      if (direction == 0 && !cell.wayUp) {
         return null;
      } else if (direction == 1 && !cell.wayRight) {
         return null;
      } else if (direction == 2 && !cell.wayDown) {
         return null;
      } else if (direction == 3 && !cell.wayLeft) {
         return null;
      } else {
         for (int i = 0; i < 100; i++) {
            if (direction == 0) {
               if (--y < 0) {
                  return null;
               }
            }

            if (direction == 1) {
               if (++x >= this.width) {
                  return null;
               }
            }

            if (direction == 2) {
               if (++y >= this.height) {
                  return null;
               }
            }

            if (direction == 3) {
               if (--x < 0) {
                  return null;
               }
            }

            TerraformingResearchCell target = this.board[x][y];
            if (target.phenomenon != null) {
               return target.phenomenon;
            }

            direction = target.transformMoveDirection(direction);
            if (direction < 0) {
               return null;
            }
         }

         System.out.println("WARNING! PhenomenVoid addMove() is iterate over 100!");
         return null;
      }
   }

   public TFRBalanceOfElements generateResearchBalance(Random rand) {
      this.isGENERATING = true;
      this.skipAll = true;
      this.update(null);
      boolean canPlace = true;
      boolean canLookInInventory = true;
      boolean canSelect = true;

      for (int i = 0; i < 100; i++) {
         TerraformingPlayerCommand randomCommand = null;
         if (rand.nextFloat() < 0.6F && canLookInInventory && canPlace) {
            int randElement = rand.nextInt(12);
            int element = 0;
            int s = 0;

            for (int e = randElement; s < 12; e = GetMOP.next(e, 1, 12)) {
               if (this.inventory[e + 1] > 0) {
                  element = e + 1;
                  break;
               }

               s++;
            }

            if (element == 0) {
               System.out.println("canLookInInventory = false");
               canLookInInventory = false;
            } else {
               ArrayList<int[]> listCells = this.getAllFitableCells();
               if (!listCells.isEmpty()) {
                  int[] ints = listCells.get(rand.nextInt(listCells.size()));
                  randomCommand = new TerraformingPlayerCommand(TerraformingPlayerCommand.TRPlayerCommandType.PLACE_FROM_INVENTORY, ints[0], ints[1], element);
                  canSelect = true;
               } else {
                  System.out.println("canPlace = false");
                  canPlace = false;
               }
            }
         } else if (canSelect) {
            if (this.selected != null && this.selected.phenomenon != null) {
               randomCommand = this.selected.phenomenon.getGenerationCommand(this, rand);
               if (randomCommand == null) {
                  this.selected.phenomenon.onUnselected(this);
                  this.selected = null;
               }
            } else {
               ArrayList<int[]> listCells = this.getAllSelectableCells();
               if (!listCells.isEmpty()) {
                  int[] ints = listCells.get(rand.nextInt(listCells.size()));
                  randomCommand = new TerraformingPlayerCommand(TerraformingPlayerCommand.TRPlayerCommandType.BOARD_SELECT, ints[0], ints[1], 0);
               } else {
                  canSelect = false;
               }
            }
         }

         System.out.println("update " + i);
         if (randomCommand != null) {
            System.out.println(randomCommand.toString());
         }

         this.delayAfterStep = 0;
         this.update(randomCommand);
         this.skipAll = true;
         this.delayAfterStep = 0;
         this.update(null);
      }

      TFRBalanceOfElements balance = this.calculateBalanceOfElements();
      this.isGENERATING = false;
      return balance;
   }

   public ArrayList<int[]> getAllFitableCells() {
      ArrayList<int[]> list = new ArrayList<>();

      for (int x = 0; x < this.width; x++) {
         for (int y = 0; y < this.height; y++) {
            TerraformingResearchCell cell = this.board[x][y];
            if (cell.canFitPhenomenon() && cell.phenomenon == null) {
               list.add(new int[]{x, y});
            }
         }
      }

      return list;
   }

   public ArrayList<int[]> getAllFitableOrSelectableCells() {
      ArrayList<int[]> list = new ArrayList<>();

      for (int x = 0; x < this.width; x++) {
         for (int y = 0; y < this.height; y++) {
            TerraformingResearchCell cell = this.board[x][y];
            if (cell.canFitPhenomenon()) {
               list.add(new int[]{x, y});
            }
         }
      }

      return list;
   }

   public ArrayList<int[]> getAllSelectableCells() {
      ArrayList<int[]> list = new ArrayList<>();

      for (int x = 0; x < this.width; x++) {
         for (int y = 0; y < this.height; y++) {
            TerraformingResearchCell cell = this.board[x][y];
            if (cell.phenomenon != null) {
               list.add(new int[]{x, y});
            }
         }
      }

      return list;
   }

   public ArrayList<int[]> getAllSelectableInRadius(int x, int y, int radius) {
      ArrayList<int[]> list = new ArrayList<>();
      int minX = Math.max(x - radius, 0);
      int minY = Math.max(y - radius, 0);
      int maxX = Math.min(x + radius, this.width - 1);
      int maxY = Math.min(y + radius, this.height - 1);

      for (int xx = minX; xx <= maxX; xx++) {
         for (int yy = minY; yy <= maxY; yy++) {
            TerraformingResearchCell cell = this.board[xx][yy];
            if (cell.phenomenon != null) {
               list.add(new int[]{xx, yy});
            }
         }
      }

      return list;
   }

   public int getBestWayToGeneration(int x, int y, Random rand) {
      return rand.nextInt(4);
   }

   public TFRBalanceOfElements calculateBalanceOfElements() {
      TFRBalanceOfElements balanceOfElements = new TFRBalanceOfElements();
      int fullAmounts = 0;
      float allOfAll = 0.0F;

      for (int x = 0; x < this.width; x++) {
         for (int y = 0; y < this.height; y++) {
            TerraformingResearchCell cell = this.board[x][y];

            for (int i = 1; i <= 12; i++) {
               int amount = cell.getCombinedElement(i);
               balanceOfElements.amounts[i] = balanceOfElements.amounts[i] + amount;
               if (amount > 0) {
                  fullAmounts++;
               }

               allOfAll += amount;
            }
         }
      }

      for (int i = 1; i <= 12; i++) {
         balanceOfElements.percentage[i] = balanceOfElements.amounts[i] / allOfAll;
      }

      balanceOfElements.activeCellsCount = fullAmounts;
      balanceOfElements.allElementsSumm = (int)allOfAll;
      return balanceOfElements;
   }

   public static class TFRBalanceOfElements {
      public int[] amounts = new int[13];
      public float[] percentage = new float[13];
      public int activeCellsCount;
      public int allElementsSumm;

      public TFRBalanceOfElements(int... elements) {
         if (elements.length == 12) {
            for (int i = 0; i < 12; i++) {
               this.amounts[i + 1] = elements[i];
            }
         }
      }

      public boolean isWin(TFRBalanceOfElements finalBalance) {
         if (SpellsRedactors.isInstantResearchWin()) {
            return true;
         } else {
            for (int i = 0; i < this.amounts.length; i++) {
               if (this.amounts[i] != finalBalance.amounts[i]) {
                  return false;
               }
            }

            return true;
         }
      }

      public void writeToNbt(NBTTagCompound tagCompound) {
         for (int i = 1; i <= 12; i++) {
            tagCompound.setInteger(i + "", this.amounts[i]);
         }
      }

      public void readFromNbt(NBTTagCompound tagCompound) {
         for (int i = 1; i <= 12; i++) {
            if (tagCompound.hasKey(i + "")) {
               this.amounts[i] = tagCompound.getInteger(i + "");
            }
         }
      }
   }

   public static class TerraformingResearchCell {
      public TerraformingResearchSurface surfaceTerrain;
      public TerraformingResearchSurface surfaceAtmosphere;
      public TerraformingResearchSurface surfaceCreature;
      public int orb = 0;
      public Phenomenon phenomenon;
      public boolean isPoint;
      public boolean wayUp;
      public boolean wayRight;
      public boolean wayDown;
      public boolean wayLeft;
      public int wayRenderIndex = -1;

      public void writeToNbt(NBTTagCompound tagCompound) {
         if (this.surfaceTerrain != null) {
            tagCompound.setInteger("terrain", this.surfaceTerrain.id);
         }

         if (this.surfaceAtmosphere != null) {
            tagCompound.setInteger("atmosphere", this.surfaceAtmosphere.id);
         }

         if (this.surfaceCreature != null) {
            tagCompound.setInteger("creature", this.surfaceCreature.id);
         }

         tagCompound.setByte("orb", (byte)this.orb);
         int i = this.isPoint ? 1 : 0;
         if (this.wayUp) {
            i |= 2;
         }

         if (this.wayRight) {
            i |= 4;
         }

         if (this.wayDown) {
            i |= 8;
         }

         if (this.wayLeft) {
            i |= 16;
         }

         if (i > 0) {
            tagCompound.setByte("ways", (byte)i);
         }
      }

      public void readFromNbt(NBTTagCompound tagCompound, RenderTerraformingResearch linkedRender, int x, int y) {
         if (tagCompound.hasKey("terrain")) {
            this.surfaceTerrain = Phenomenons.tfrSurfaceRegister.get(tagCompound.getInteger("terrain"));
            if (this.surfaceTerrain != null && linkedRender != null) {
               linkedRender.setTerrainAnimation(this.surfaceTerrain, this.surfaceTerrain.type, x, y, true);
            }
         }

         if (tagCompound.hasKey("atmosphere")) {
            this.surfaceAtmosphere = Phenomenons.tfrSurfaceRegister.get(tagCompound.getInteger("atmosphere"));
            if (this.surfaceAtmosphere != null && linkedRender != null) {
               linkedRender.setTerrainAnimation(this.surfaceAtmosphere, this.surfaceAtmosphere.type, x, y, true);
            }
         }

         if (tagCompound.hasKey("creature")) {
            this.surfaceCreature = Phenomenons.tfrSurfaceRegister.get(tagCompound.getInteger("creature"));
            if (this.surfaceCreature != null && linkedRender != null) {
               linkedRender.setTerrainAnimation(this.surfaceCreature, this.surfaceCreature.type, x, y, true);
            }
         }

         if (tagCompound.hasKey("orb")) {
            this.orb = tagCompound.getByte("orb");
         }

         if (tagCompound.hasKey("ways")) {
            int i = tagCompound.getByte("ways");
            this.isPoint = (i & 1) > 0;
            this.wayUp = (i & 2) > 0;
            this.wayRight = (i & 4) > 0;
            this.wayDown = (i & 8) > 0;
            this.wayLeft = (i & 16) > 0;
            this.recalcWayRenderIndex();
         }
      }

      public void onStep(TerraformingResearchPuzzle puzzle, TerraformingResearchSurface.TFRSurfaceChange[][] changes, int x, int y) {
         if (this.surfaceTerrain != null) {
            this.surfaceTerrain.onStep(puzzle, changes, x, y);
         }

         if (this.surfaceAtmosphere != null) {
            this.surfaceAtmosphere.onStep(puzzle, changes, x, y);
         }
      }

      public int getCombinedElement(int elementId) {
         int amount = 0;
         if (this.surfaceTerrain != null) {
            amount += this.surfaceTerrain.getElement(elementId);
         }

         if (this.surfaceAtmosphere != null) {
            amount += this.surfaceAtmosphere.getElement(elementId);
         }

         return amount;
      }

      public void setWays(boolean wayUp, boolean wayRight, boolean wayDown, boolean wayLeft) {
         this.wayUp = wayUp;
         this.wayRight = wayRight;
         this.wayDown = wayDown;
         this.wayLeft = wayLeft;
         this.recalcWayRenderIndex();
      }

      public boolean hasWays() {
         return this.wayUp || this.wayDown || this.wayRight || this.wayLeft || this.isPoint;
      }

      public boolean hasWay(int way) {
         if (way == 0) {
            return this.wayUp;
         } else if (way == 1) {
            return this.wayRight;
         } else if (way == 2) {
            return this.wayDown;
         } else {
            return way == 3 ? this.wayLeft : false;
         }
      }

      public void recalcWayRenderIndex() {
         if (this.wayUp && this.wayRight && this.wayDown && this.wayLeft) {
            this.wayRenderIndex = 0;
         } else if (this.wayRight && this.wayDown && this.wayLeft) {
            this.wayRenderIndex = 1;
         } else if (this.wayUp && this.wayDown && this.wayLeft) {
            this.wayRenderIndex = 2;
         } else if (this.wayUp && this.wayRight && this.wayLeft) {
            this.wayRenderIndex = 3;
         } else if (this.wayUp && this.wayRight && this.wayDown) {
            this.wayRenderIndex = 4;
         } else if (this.wayUp && this.wayDown) {
            this.wayRenderIndex = 5;
         } else if (this.wayRight && this.wayLeft) {
            this.wayRenderIndex = 6;
         } else {
            if (!this.isPoint) {
               if (this.wayUp && this.wayRight) {
                  this.wayRenderIndex = 7;
                  return;
               }

               if (this.wayRight && this.wayDown) {
                  this.wayRenderIndex = 8;
                  return;
               }

               if (this.wayDown && this.wayLeft) {
                  this.wayRenderIndex = 9;
                  return;
               }

               if (this.wayUp && this.wayLeft) {
                  this.wayRenderIndex = 10;
                  return;
               }
            } else {
               if (this.wayUp && this.wayRight) {
                  this.wayRenderIndex = 15;
                  return;
               }

               if (this.wayRight && this.wayDown) {
                  this.wayRenderIndex = 16;
                  return;
               }

               if (this.wayDown && this.wayLeft) {
                  this.wayRenderIndex = 17;
                  return;
               }

               if (this.wayUp && this.wayLeft) {
                  this.wayRenderIndex = 18;
                  return;
               }
            }

            if (this.wayUp) {
               this.wayRenderIndex = 11;
            } else if (this.wayRight) {
               this.wayRenderIndex = 12;
            } else if (this.wayDown) {
               this.wayRenderIndex = 13;
            } else if (this.wayLeft) {
               this.wayRenderIndex = 14;
            } else {
               this.wayRenderIndex = -1;
            }
         }
      }

      public boolean canFitPhenomenon() {
         if (this.isPoint) {
            return true;
         } else {
            int a = 0;
            if (this.wayUp) {
               a++;
            }

            if (this.wayRight) {
               a++;
            }

            if (this.wayDown) {
               a++;
            }

            if (this.wayLeft) {
               a++;
            }

            return a >= 3;
         }
      }

      public int transformMoveDirection(int lastDirection) {
         if (lastDirection == 0 && this.wayDown) {
            if (this.wayUp) {
               return lastDirection;
            }

            if (this.wayRight) {
               return 1;
            }

            if (this.wayLeft) {
               return 3;
            }
         }

         if (lastDirection == 1 && this.wayLeft) {
            if (this.wayRight) {
               return lastDirection;
            }

            if (this.wayUp) {
               return 0;
            }

            if (this.wayDown) {
               return 2;
            }
         }

         if (lastDirection == 2 && this.wayUp) {
            if (this.wayDown) {
               return lastDirection;
            }

            if (this.wayRight) {
               return 1;
            }

            if (this.wayLeft) {
               return 3;
            }
         }

         if (lastDirection == 3 && this.wayRight) {
            if (this.wayLeft) {
               return lastDirection;
            }

            if (this.wayUp) {
               return 0;
            }

            if (this.wayDown) {
               return 2;
            }
         }

         return -1;
      }
   }
}
