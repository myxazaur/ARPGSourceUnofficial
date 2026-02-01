package com.vivern.arpg.container;

import com.vivern.arpg.elements.models.AbstractMobModel;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.CreateItemFile;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Spell;
import com.vivern.arpg.main.Vec2i;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.PacketTFRPuzzleToServer;
import com.vivern.arpg.network.PacketTileClickToServer;
import com.vivern.arpg.recipes.ExploringField;
import com.vivern.arpg.recipes.Phenomenons;
import com.vivern.arpg.recipes.SpellsRedactors;
import com.vivern.arpg.recipes.TerraformingPlayerCommand;
import com.vivern.arpg.recipes.TerraformingResearchPuzzle;
import com.vivern.arpg.recipes.WriteGraph;
import com.vivern.arpg.renders.ManaBar;
import com.vivern.arpg.renders.RenderTerraformingResearch;
import com.vivern.arpg.tileentity.ITileEntitySynchronize;
import com.vivern.arpg.tileentity.TileResearchTable;
import com.vivern.arpg.tileentity.WriteBlank;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GUIResearchTable extends GuiContainer {
   public static final ResourceLocation TEXTURE_EXPLORING = new ResourceLocation("arpg:textures/gui_research_table_exploring.png");
   public static final ResourceLocation TEXTURE_RESEARCH = new ResourceLocation("arpg:textures/gui_research_table_puzzle.png");
   public static final ResourceLocation TEXTURE_WRITING = new ResourceLocation("arpg:textures/gui_research_table_writing.png");
   public static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation("arpg:textures/gui_research_table_overlay.png");
   public static final ResourceLocation gui_test200_175 = new ResourceLocation("arpg:textures/gui_test200_175.png");
   public static final ResourceLocation MAIN_RUNES = new ResourceLocation("arpg:textures/gui_main_runes.png");
   public static final ResourceLocation ADDITIONAL_RUNES = new ResourceLocation("arpg:textures/gui_additional_runes.png");
   public static final ResourceLocation TEXTURE_RESEARCH_BARS = new ResourceLocation("arpg:textures/gui_research_table_bars.png");
   public static int tfrDescriptionColor = ColorConverters.RGBAtoDecimal(0.0F, 0.0F, 0.0F, 0.65F);
   private final InventoryPlayer playerInventory;
   private final IInventory tileinv;
   private TileResearchTable tile;
   public static TerraformingResearchPuzzle puzzle;
   public static RenderTerraformingResearch puzzleRender;
   public static boolean shouldLoadPosition;
   public TerraformingPlayerCommand puzzleCommand;
   public int dragAndDropInventoryCell = -1;
   public int dragAndDropRenderedElem = 0;
   public int blankSelected = 0;
   public float blankSelectedFloat = 0.0F;
   public String analyzeText = "";
   public HashMap<Spell, AnalyzedSpell> analyzedSpells;
   public ArrayList<AnalyzedSpell> toSort;
   public int nextAnalyzeId = 0;
   public Spell currentWritePattern;
   public int inkConsumed = 0;
   public ArrayList<Spell> spellsKnown;
   public ExploringField exploringField;
   public int specialization = 0;
   public float animatedDirtAmount;
   public float animatedCompleteAmount;
   float pt = 0.0F;
   public static int dataset_save_number = 0;
   public float distorsionX;
   public float distorsionY;
   public float prevMouseX;
   public float prevMouseY;
   public boolean updatePrevMouses = true;
   public boolean shouldAnalyze = false;
   public DistortedBlank[] distortedBlanks;
   private int ticksupdate = 0;

   public GUIResearchTable(InventoryPlayer playerInv, IInventory iInv) {
      super(new ContainerResearchTable(playerInv, iInv));
      this.playerInventory = playerInv;
      this.tileinv = iInv;
      this.xSize = 224;
      this.ySize = 256;
      this.specialization = this.tileinv.getField(3);
      if (this.specialization == 1) {
         this.exploringField = ExploringField.generateExploringField(ExploringField.getExploringTagCompound(playerInv.player));
         this.xSize = 256;
         this.ySize = 243;
      }

      if (this.specialization == 2) {
         this.xSize = 256;
         this.ySize = 256;
         if (iInv instanceof TileResearchTable) {
            this.tile = (TileResearchTable)iInv;
         }
      } else if (this.specialization == 3) {
         if (iInv instanceof TileResearchTable) {
            this.tile = (TileResearchTable)iInv;
            this.createWriteBlanks(false);
            this.spellsKnown = Spell.getAllSpellsKnownByPlayer(playerInv.player);
         } else {
            this.specialization = 0;
         }
      }
   }

   public static boolean isInfiniteWriteEnabled() {
      return TileResearchTable.isInfiniteWriteEnabled();
   }

   public void onGuiClosed() {
      super.onGuiClosed();
      if (puzzleRender != null) {
         this.tileinv.setField(4, (int)puzzleRender.posx);
         this.tileinv.setField(5, (int)puzzleRender.posy);
         this.tileinv.setField(6, (int)(puzzleRender.scale * 100.0F));
      }

      puzzle = null;
      puzzleRender = null;
      this.puzzleCommand = null;
   }

   public static void setPuzzleFromTag(NBTTagCompound compound) {
      puzzle = new TerraformingResearchPuzzle();
      puzzle.readFromNbt(compound, true);
      puzzleRender = puzzle.linkedRender;
      shouldLoadPosition = true;
   }

   public void createWriteBlanks(boolean neww) {
      if (this.tile.blanks == null || neww) {
         this.tile.blanks = new WriteBlank.WriteBlankSpell[]{null, null, new WriteBlank.WriteBlankSpell(50, 75), null, null};
      }

      this.blankSelected = 2;
      this.blankSelectedFloat = this.blankSelected;
      this.analyzedSpells = new HashMap<>();
      this.toSort = new ArrayList<>();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.pt = partialTicks;
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.renderHoveredToolTip(mouseX, mouseY);
   }

   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      if (this.specialization == 1 && this.exploringField != null) {
         mouseX -= this.getGuiLeft();
         mouseY -= this.getGuiTop();
         String text = this.exploringField.getText(mouseX, mouseY, ExploringField.getExploringTagCompound(this.playerInventory.player));
         if (text != null) {
            String[] texts = text.split(":");

            for (int i = 0; i < texts.length; i++) {
               if (texts[i] != null) {
                  this.fontRenderer.drawString(texts[i], mouseX + 5, mouseY + 3 + 10 * i, 13816530);
               }
            }
         }
      }

      if (this.specialization == 2 && puzzleRender != null && puzzle != null) {
         GlStateManager.disableDepth();
         if (isAltKeyDown()) {
            this.fontRenderer.drawString("x: " + ManaBar.asString(puzzleRender.posx) + "  y: " + ManaBar.asString(puzzleRender.posy), 30, 20, 4210752);
            this.fontRenderer.drawString("scale: " + ManaBar.asString(puzzleRender.scale), 30, 30, 4210752);
            this.fontRenderer.drawString("delay: " + puzzle.delayAfterStep, 30, 40, 4210752);
         }

         if (isShiftKeyDown()) {
            mouseX -= this.getGuiLeft();
            mouseY -= this.getGuiTop();
            float[] boardPos = puzzleRender.screenPosToBoardPos(mouseX - 110, mouseY - 97);
            int[] cellPos = puzzleRender.boardPosToCellPos(boardPos[0], boardPos[1]);
            if (puzzle.checkBounds(cellPos[0], cellPos[1])) {
               TerraformingResearchPuzzle.TerraformingResearchCell cell = puzzle.board[cellPos[0]][cellPos[1]];
               int[] amounts = new int[13];
               int fullAmounts = 0;

               for (int ix = 1; ix <= 12; ix++) {
                  int amount = cell.getCombinedElement(ix);
                  amounts[ix] = amount;
                  if (amount > 0) {
                     fullAmounts++;
                  }
               }

               if (fullAmounts > 0) {
                  drawRect(mouseX + 3, mouseY + 3, mouseX + 46, mouseY + 9 + fullAmounts * 12, tfrDescriptionColor);
                  GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                  int yOffset = 6;

                  for (int ixx = 1; ixx <= 12; ixx++) {
                     int amount = amounts[ixx];
                     if (amount > 0) {
                        Minecraft.getMinecraft().getTextureManager().bindTexture(MAIN_RUNES);
                        GuiScreen.drawModalRectWithCustomSizedTexture(mouseX + 6, mouseY + yOffset, 0.0F, ixx * 12, 12, 12, 12.0F, 156.0F);
                        yOffset += 12;
                     }
                  }

                  int var23 = 6;

                  for (int ixxx = 1; ixxx <= 12; ixxx++) {
                     int amount = amounts[ixxx];
                     if (amount > 0) {
                        ShardType shardType = ShardType.byId(ixxx);
                        this.fontRenderer
                           .drawString(
                              amount + "%", mouseX + 20, mouseY + 3 + var23, ColorConverters.RGBtoDecimal(shardType.colorR, shardType.colorG, shardType.colorB)
                           );
                        var23 += 12;
                     }
                  }
               }
            }
         }

         GlStateManager.enableDepth();
      }

      if (this.specialization == 3) {
         if (this.tile.blanks != null && this.blankSelected < this.tile.blanks.length && this.blankSelected >= 0) {
            WriteBlank blank = this.tile.blanks[this.blankSelected];
            if (blank != null && this.currentWritePattern != null) {
               int ch1 = this.currentWritePattern.configuration.checkLines(blank);
               int ch2 = this.currentWritePattern.configuration.checkDirt(blank);
               String text = ch1 + "/" + this.currentWritePattern.configuration.countOfAll;
               this.fontRenderer.drawString(text, 112 - this.fontRenderer.getStringWidth(text) / 2, 53, 2560);
            }
         }

         if (this.spellsKnown.size() == 0) {
            String text = "You don't know any spells";
            this.fontRenderer.drawString(text, 112 - this.fontRenderer.getStringWidth(text) / 2, 147, 9186606);
         }
      }
   }

   public void drawTexturedModalRectFromTo(int x, int y, int textureX, int textureY, int height, int renderFromX, int renderToX) {
      this.drawTexturedModalRect(x + renderFromX, y, textureX + renderFromX, textureY, renderToX - renderFromX, height);
   }

   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      if (this.specialization == 1) {
         GlStateManager.pushMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(TEXTURE_EXPLORING);
         int i = (this.width - this.xSize) / 2;
         int j = (this.height - this.ySize) / 2;
         this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
         if (this.exploringField != null) {
            GL11.glClear(1024);
            GL11.glEnable(2960);
            GL11.glStencilOp(7680, 7680, 7681);
            GL11.glStencilFunc(519, 1, 255);
            GL11.glStencilMask(255);
            this.mc.getTextureManager().bindTexture(TEXTURE_OVERLAY);
            this.drawTexturedModalRect(i + 3, j + 3, 0, 87, 250, 150);
            GL11.glStencilFunc(514, 1, 255);
            GL11.glStencilMask(0);
            this.exploringField.renderExploringField(this, i, j, partialTicks, ExploringField.getExploringTagCompound(this.playerInventory.player));
            GL11.glStencilMask(255);
            GL11.glDisable(2960);
         }

         GlStateManager.popMatrix();
      }

      if (this.specialization == 2) {
         GlStateManager.pushMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(TEXTURE_RESEARCH);
         int i = (this.width - this.xSize) / 2;
         int j = (this.height - this.ySize) / 2;
         this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
         if (puzzle != null && puzzleRender != null) {
            GL11.glClear(1024);
            GL11.glEnable(2960);
            GL11.glStencilOp(7680, 7680, 7681);
            GL11.glStencilFunc(519, 1, 255);
            GL11.glStencilMask(255);
            this.drawTexturedModalRect(i + 10, j + 10, 10, 10, 200, 175);
            GL11.glStencilFunc(514, 1, 255);
            GL11.glStencilMask(0);
            puzzleRender.renderAndBind(puzzle, i + 110, j + 97, partialTicks);
            GL11.glStencilMask(255);
            GL11.glDisable(2960);
            GlStateManager.disableDepth();
            int invI = 0;

            for (int e = 1; e <= 12; e++) {
               if (puzzle.inventory[e] > 0) {
                  int xI = i + 217 + invI % 2 * 15;
                  int yI = j + 9 + invI / 2 * 15;
                  this.mc.getTextureManager().bindTexture(Phenomenons.tfr_elements);
                  this.drawTexturedModalRect(xI, yI, (e - 1) * 14, 0, 14, 14);
                  this.fontRenderer.drawString("" + puzzle.inventory[e], xI + 8, yI + 8, 16777215);
                  invI++;
               }
            }

            float orbsStart = 113.333336F;
            float orbsOne = 6.6666665F;
            if (puzzle.movingOrbs > 0) {
               int xI = i + 217;
               int yI = j + 104;
               this.mc.getTextureManager().bindTexture(Phenomenons.tfr_sprites);
               drawModalRectWithCustomSizedTexture2(
                  xI, yI, orbsStart, 0.0F, orbsOne, orbsOne, Phenomenons.tfr_sprites_sizeX / 3.0F, Phenomenons.tfr_sprites_sizeY / 3.0F
               );
               this.fontRenderer.drawString("" + puzzle.movingOrbs, xI + 9, yI, 16777215);
            }

            if (puzzle.splitOrbs > 0) {
               int xI = i + 217;
               int yI = j + 114;
               this.mc.getTextureManager().bindTexture(Phenomenons.tfr_sprites);
               drawModalRectWithCustomSizedTexture2(
                  xI, yI, orbsStart + orbsOne + orbsOne, 0.0F, orbsOne, orbsOne, Phenomenons.tfr_sprites_sizeX / 3.0F, Phenomenons.tfr_sprites_sizeY / 3.0F
               );
               this.fontRenderer.drawString("" + puzzle.splitOrbs, xI + 9, yI, 16777215);
               if (puzzle.selected != null && puzzle.selected.phenomenon != null) {
                  int inerflowBit = Phenomenons.getInterflowBit(puzzle.selected.phenomenon.getId());
                  int offset = 0;

                  for (int b = 2048; b > 0; b >>>= 1) {
                     if ((inerflowBit & b) > 0) {
                        offset += 14;
                     }
                  }

                  int center = 110 - offset / 2;
                  int var52 = 0;
                  this.mc.getTextureManager().bindTexture(TEXTURE_OVERLAY);

                  for (int bx = 2048; bx > 0; bx >>>= 1) {
                     if ((inerflowBit & bx) > 0) {
                        this.drawTexturedModalRect(i + center + var52, j + 10, 0, 242, 14, 14);
                        var52 += 14;
                     }
                  }

                  var52 = 0;
                  Minecraft.getMinecraft().getTextureManager().bindTexture(MAIN_RUNES);
                  int idElement = 1;

                  for (int bxx = 2048; bxx > 0; bxx >>>= 1) {
                     if ((inerflowBit & bxx) > 0) {
                        GuiScreen.drawModalRectWithCustomSizedTexture(i + center + var52 + 1, j + 11, 0.0F, idElement * 12, 12, 12, 12.0F, 156.0F);
                        var52 += 14;
                     }

                     idElement++;
                  }
               }
            }

            if (this.dragAndDropRenderedElem > 0 && this.dragAndDropRenderedElem <= 12) {
               this.mc.getTextureManager().bindTexture(Phenomenons.tfr_elements);
               GlStateManager.color(1.0F, 1.0F, 1.0F, 0.6F);
               this.drawTexturedModalRect(mouseX - 7, mouseY - 7, (this.dragAndDropRenderedElem - 1) * 14, 0, 14, 14);
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }

            int x = i + 10;
            int y = j + 10;
            int width = 200;
            int height = 175;
            float display1 = 800.0F / this.mc.displayWidth;
            float display2 = 800.0F / this.mc.displayHeight;
            float uvX = display1 / 2.0F;
            float uvY = display2 / 2.0F;
            GlStateManager.enableDepth();
            this.drawEnergyBars(puzzle.currentBalance.amounts, puzzle.finalBalance.amounts, i, j);
         }

         GlStateManager.popMatrix();
      }

      if (this.specialization == 3) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(TEXTURE_WRITING);
         int i = (this.width - this.xSize) / 2;
         int j = (this.height - this.ySize) / 2;
         this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
         this.mc.getTextureManager().bindTexture(TEXTURE_OVERLAY);
         if (this.tile.blanks != null) {
            float blMin = Math.max(this.blankSelectedFloat - 2.0F, 0.0F);
            float blMax = Math.min(this.blankSelectedFloat + 3.0F, (float)this.tile.blanks.length);

            for (int bl = (int)blMin; bl < blMax; bl++) {
               float xPos = bl - this.blankSelectedFloat;
               int absToX = 201 - (int)(xPos * 52.0F) - 86;
               int absFromX = 23 - (int)(xPos * 52.0F) - 86;
               this.drawTexturedModalRectFromTo(
                  i + 86 + (int)(xPos * 52.0F), j + 65, 0, 0, 75, MathHelper.clamp(absFromX, 0, 52), MathHelper.clamp(absToX, 0, 52)
               );
            }
         }

         this.drawTexturedModalRect(i + 81, j + 57, 52, 0, 62, 91);
         if (SpellsRedactors.isRedactorEnabled() && SpellsRedactors.writeGraph != null) {
            AbstractMobModel.alphaGlow();
            this.drawGraph(SpellsRedactors.writeGraph, i + 87, j + 65);
            AbstractMobModel.alphaGlowDisable();
         } else if (this.currentWritePattern != null) {
            AbstractMobModel.alphaGlow();
            this.drawGraph(this.currentWritePattern.configuration, i + 87, j + 65);
            AbstractMobModel.alphaGlowDisable();
         }

         if (this.toSort != null && !this.toSort.isEmpty()) {
            int max = Math.min(this.toSort.size(), isInfiniteWriteEnabled() ? this.toSort.size() : 10);
            this.mc.getTextureManager().bindTexture(ADDITIONAL_RUNES);

            for (int k = 0; k < max; k++) {
               Spell spell = this.toSort.get(k).spell;
               int texId = spell.id - 1;
               int xcell = texId % 14;
               int ycell = texId / 14;
               if (spell == this.currentWritePattern) {
                  GlStateManager.color(0.0F, 1.0F, 1.0F, 1.0F);
               } else {
                  GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
               }

               drawModalRectWithCustomSizedTexture(i + 24 + k * 11, j + 30, xcell * 9, ycell * 13, 9, 13, 128.0F, 128.0F);
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }

            this.mc.getTextureManager().bindTexture(TEXTURE_OVERLAY);
            AnalyzedSpell firstSpell = this.toSort.get(0);
            float dirtAmount = MathHelper.clamp((float)firstSpell.dirt / maxWriteDirtAllowed(), 0.0F, 1.0F);
            this.animatedDirtAmount = GetMOP.followNumber(this.animatedDirtAmount, dirtAmount, 0.02F);
            this.drawTexturedModalRect(i + 137, j + 30, 114, 0, Math.round(64.0F * this.animatedDirtAmount), 4);
            if (this.animatedDirtAmount < dirtAmount) {
               float difference = dirtAmount - this.animatedDirtAmount;
               GlStateManager.color(1.0F, 1.0F, 1.0F, difference);
               float tremor = MathHelper.sin(AnimationTimer.tick * 0.7F) * difference;
               this.drawTexturedModalRect(i + 137 + tremor, j + 29 + tremor * 2.0F, 114, 14, 64, 6);
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }

            float completeAmount = MathHelper.clamp((float)firstSpell.fullness / firstSpell.spell.configuration.countOfAll, 0.0F, 1.0F);
            this.animatedCompleteAmount = GetMOP.followNumber(this.animatedCompleteAmount, completeAmount, 0.02F);
            this.drawTexturedModalRect(i + 137, j + 37, 114, 7, Math.round(64.0F * this.animatedCompleteAmount), 6);
         }

         if (this.tile.blanks != null) {
            float blMin = Math.max(this.blankSelectedFloat - 2.0F, 0.0F);
            float blMax = Math.min(this.blankSelectedFloat + 3.0F, (float)this.tile.blanks.length);

            for (int bl = (int)blMin; bl < blMax; bl++) {
               float xPos = bl - this.blankSelectedFloat;
               WriteBlank blank = this.tile.blanks[bl];
               if (blank != null) {
                  int absToX = 201 - (int)(xPos * 52.0F) - 87;
                  int absFromX = 23 - (int)(xPos * 52.0F) - 87;
                  this.tile.blanks[bl]
                     .renderInGui(
                        this,
                        i + 87 + (int)(xPos * 52.0F),
                        j + 65,
                        MathHelper.clamp(absFromX, 0, blank.sizeX),
                        MathHelper.clamp(absToX, 0, blank.sizeX),
                        0,
                        blank.sizeY
                     );
               }
            }
         }
      }
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      super.keyTyped(typedChar, keyCode);
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      if (this.specialization == 2 && puzzleRender != null) {
         int i2 = Mouse.getEventDWheel();
         if (i2 != 0) {
            puzzleRender.mouseWhellInput(i2);
         }
      }
   }

   public int[] mouseToCellPos(int mouseX, int mouseY) {
      mouseX -= this.getGuiLeft();
      mouseY -= this.getGuiTop();
      float[] boardPos = puzzleRender.screenPosToBoardPos(mouseX - 110, mouseY - 97);
      return puzzleRender.boardPosToCellPos(boardPos[0], boardPos[1]);
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      mouseX -= this.getGuiLeft();
      mouseY -= this.getGuiTop();
      if (SpellsRedactors.isRedactorEnabled()) {
         SpellsRedactors.handleMouseClick(mouseX, mouseY, mouseButton, this.specialization, isShiftKeyDown());
      }

      if (this.specialization == 1 && this.exploringField != null) {
         this.exploringField.click(mouseX, mouseY, mouseButton);
      }

      if (this.specialization == 2 && puzzle != null && puzzleRender != null && mouseButton == 0) {
         boolean splitNoPressed = true;
         if (puzzle.selected != null && puzzle.selected.phenomenon != null && puzzle.splitOrbs > 0) {
            int inerflowBit = Phenomenons.getInterflowBit(puzzle.selected.phenomenon.getId());
            int offset = 0;

            for (int b = 2048; b > 0; b >>>= 1) {
               if ((inerflowBit & b) > 0) {
                  offset += 14;
               }
            }

            int center = 110 - offset / 2;
            int var26 = 0;
            int idElement = 1;

            for (int bx = 2048; bx > 0; bx >>>= 1) {
               if ((inerflowBit & bx) > 0) {
                  int minx = center + var26;
                  int maxx = minx + 14;
                  if (mouseX > minx && mouseY > 10 && mouseX < maxx && mouseY < 24) {
                     this.puzzleCommand = new TerraformingPlayerCommand(
                        TerraformingPlayerCommand.TRPlayerCommandType.SPLIT, puzzle.selected.phenomenon.posX, puzzle.selected.phenomenon.posY, idElement
                     );
                     splitNoPressed = false;
                     break;
                  }

                  var26 += 14;
               }

               idElement++;
            }
         }

         if (splitNoPressed && mouseX > 10 && mouseY > 10 && mouseX < 210 && mouseY < 185) {
            float[] boardPos = puzzleRender.screenPosToBoardPos(mouseX - 110, mouseY - 97);
            int[] cellPos = puzzleRender.boardPosToCellPos(boardPos[0], boardPos[1]);
            boolean moveClicked = false;
            if (puzzle.selected != null
               && puzzle.selected.phenomenon != null
               && (puzzle.movingOrbs > 0 || puzzle.canMove(puzzle.selected.phenomenon.posX, puzzle.selected.phenomenon.posY))) {
               float relativeMouseX = boardPos[0] - (puzzleRender.cellWidth * puzzle.selected.phenomenon.posX + puzzleRender.cellWidth / 2);
               float relativeMouseY = boardPos[1] - (puzzleRender.cellHeight * puzzle.selected.phenomenon.posY + puzzleRender.cellHeight / 2);
               if (relativeMouseY >= -7.0F && relativeMouseY <= 7.0F) {
                  if (relativeMouseX >= 22.0F && relativeMouseX <= 54.0F) {
                     this.puzzleCommand = new TerraformingPlayerCommand(TerraformingPlayerCommand.TRPlayerCommandType.MOVE_SELECTED, cellPos[0], cellPos[1], 1);
                     moveClicked = true;
                  }

                  if (relativeMouseX >= -54.0F && relativeMouseX <= -22.0F) {
                     this.puzzleCommand = new TerraformingPlayerCommand(TerraformingPlayerCommand.TRPlayerCommandType.MOVE_SELECTED, cellPos[0], cellPos[1], 3);
                     moveClicked = true;
                  }
               }

               if (relativeMouseX >= -7.0F && relativeMouseX <= 7.0F) {
                  if (relativeMouseY >= 22.0F && relativeMouseY <= 54.0F) {
                     this.puzzleCommand = new TerraformingPlayerCommand(TerraformingPlayerCommand.TRPlayerCommandType.MOVE_SELECTED, cellPos[0], cellPos[1], 2);
                     moveClicked = true;
                  }

                  if (relativeMouseY >= -54.0F && relativeMouseY <= -22.0F) {
                     this.puzzleCommand = new TerraformingPlayerCommand(TerraformingPlayerCommand.TRPlayerCommandType.MOVE_SELECTED, cellPos[0], cellPos[1], 0);
                     moveClicked = true;
                  }
               }
            }

            if (puzzle.checkBounds(cellPos[0], cellPos[1]) && !moveClicked) {
               boolean boardSelect = true;
               TerraformingResearchPuzzle.TerraformingResearchCell cell = puzzle.board[cellPos[0]][cellPos[1]];
               if (cell.orb != 0 && (cell.surfaceTerrain != null || cell.surfaceAtmosphere != null || cell.surfaceCreature != null)) {
                  float relativeMouseXx = boardPos[0] - (puzzleRender.cellWidth * cellPos[0] + puzzleRender.cellWidth / 2);
                  float relativeMouseYx = boardPos[1] - (puzzleRender.cellHeight * cellPos[1] + puzzleRender.cellHeight / 2);
                  if (relativeMouseXx <= 10.0F && relativeMouseXx >= -10.0F && relativeMouseYx <= 10.0F && relativeMouseYx >= -10.0F) {
                     TerraformingPlayerCommand command = new TerraformingPlayerCommand(
                        TerraformingPlayerCommand.TRPlayerCommandType.GET_ORB, cellPos[0], cellPos[1], 0
                     );
                     this.puzzleCommand = command;
                     boardSelect = false;
                  }
               }

               if (boardSelect) {
                  TerraformingPlayerCommand command = new TerraformingPlayerCommand(
                     TerraformingPlayerCommand.TRPlayerCommandType.BOARD_SELECT, cellPos[0], cellPos[1], 0
                  );
                  this.puzzleCommand = command;
               }
            }
         }

         if (splitNoPressed && mouseX > 217 && mouseY > 9 && mouseX < 247 && mouseY < 99) {
            int xcell = (mouseX - 217) / 15;
            int ycell = (mouseY - 9) / 15;
            int cellx = ycell * 2 + xcell;
            this.dragAndDropInventoryCell = cellx;
            int invI = 0;

            for (int e = 1; e <= 12; e++) {
               if (puzzle.inventory[e] > 0) {
                  if (invI == this.dragAndDropInventoryCell) {
                     this.dragAndDropRenderedElem = e;
                     break;
                  }

                  invI++;
               }
            }
         }

         if (mouseX > 235 && mouseY > 174 && mouseX < 249 && mouseY < 188) {
            PacketTileClickToServer packet = new PacketTileClickToServer();
            packet.writeints(
               this.tile.getPos().getX(),
               this.tile.getPos().getY(),
               this.tile.getPos().getZ(),
               mouseX,
               mouseY,
               mouseButton
            );
            PacketHandler.NETWORK.sendToServer(packet);
            this.mc.player.closeScreen();
         }
      }

      if (this.specialization == 3) {
         if (mouseX > 201 && mouseY > 90 && mouseX < 216 && mouseY < 115) {
            int newSelection = this.blankSelected + 1;
            if (this.tile.blanks[newSelection] != null) {
               this.changeSelectedBlank(newSelection);
            } else {
               WriteBlank.WriteBlankSpell[] newblanks = new WriteBlank.WriteBlankSpell[this.tile.blanks.length + 1];

               for (int i = 0; i < this.tile.blanks.length; i++) {
                  newblanks[i] = this.tile.blanks[i];
               }

               newblanks[newSelection] = new WriteBlank.WriteBlankSpell(50, 75);
               this.tile.blanks = newblanks;
               this.changeSelectedBlank(newSelection);
            }
         }

         if (mouseX > 8 && mouseY > 90 && mouseX < 23 && mouseY < 115) {
            int newSelection = this.blankSelected - 1;
            if (newSelection >= 2) {
               this.changeSelectedBlank(newSelection);
            }
         }

         if (mouseX >= 189 && mouseY >= 147 && mouseX <= 218 && mouseY <= 165 && TileResearchTable.checkInventoryForRollResources(this.tileinv)) {
            this.changeSelectedBlank(this.blankSelected);
            int spellsCount = 0;

            for (int i = 0; i < this.tile.blanks.length; i++) {
               if (this.tile.blanks[i] != null && this.tile.blanks[i].averageSuitableSpell != null && !this.tile.blanks[i].wasted) {
                  spellsCount++;
               }
            }

            double[] spellsIds = new double[spellsCount];
            int spellIndx = 0;

            for (int ix = 0; ix < this.tile.blanks.length; ix++) {
               if (this.tile.blanks[ix] != null && this.tile.blanks[ix].averageSuitableSpell != null && !this.tile.blanks[ix].wasted) {
                  spellsIds[spellIndx] = this.tile.blanks[ix].averageSuitableSpell.id;
                  spellIndx++;
               }
            }

            ITileEntitySynchronize.sendDataToServer(this.tileinv.getField(0), this.tileinv.getField(1), this.tileinv.getField(2), spellsIds);
            this.createWriteBlanks(true);
         }

         if (this.toSort != null && !this.toSort.isEmpty() && mouseX > 23 && mouseY >= 29 && mouseY <= 45) {
            int mx = (mouseX - 23) / 11;
            if (mx >= 0 && mx < this.toSort.size()) {
               AnalyzedSpell anSpell = this.toSort.get(mx);
               if (anSpell != null && anSpell.spell != null) {
                  this.currentWritePattern = anSpell.spell;
                  if (isInfiniteWriteEnabled()) {
                     if (mouseButton == 2) {
                        this.currentWritePattern.configuration.makeDebugBlank(this.tile.blanks[this.blankSelected], false);
                     }

                     if (mouseButton == 1) {
                        this.currentWritePattern.configuration.makeDebugBlank(this.tile.blanks[this.blankSelected], true);
                     }
                  }
               }
            }
         }
      }
   }

   public void handleDistortion(float dirX, float dirY, float length) {
      if (this.distortedBlanks == null) {
         Random rand = new Random();
         this.distortedBlanks = new DistortedBlank[this.getImagesGeneratedCount()];

         for (int i = 0; i < this.distortedBlanks.length; i++) {
            this.distortedBlanks[i] = new DistortedBlank(rand.nextInt());
         }
      }

      for (int i = 0; i < this.distortedBlanks.length; i++) {
         this.distortedBlanks[i].distortDirection(dirX, dirY, length);
      }
   }

   public void handleSpots(float x, float y, int radius) {
      if (this.distortedBlanks == null) {
         Random rand = new Random();
         this.distortedBlanks = new DistortedBlank[this.getImagesGeneratedCount()];

         for (int i = 0; i < this.distortedBlanks.length; i++) {
            this.distortedBlanks[i] = new DistortedBlank(rand.nextInt());
         }
      }

      for (int i = 0; i < this.distortedBlanks.length; i++) {
         this.distortedBlanks[i].spot(x, y, radius);
      }
   }

   public int getImagesGeneratedCount() {
      return (int)Math.max(Debugger.floats[5], 1.0F);
   }

   public void handleSaveDistorted(boolean wasted) {
      if (Debugger.floats[6] > -1.0F) {
         dataset_save_number = (int)Debugger.floats[6];
         Debugger.floats[6] = -1.0F;
      }

      if (this.distortedBlanks != null) {
         for (int i = 0; i < this.distortedBlanks.length; i++) {
            String namefolder = wasted ? Debugger.string + "_w" : Debugger.string;
            File file = new File("/Users/Vivern/Desktop/Modding/dataset/" + namefolder);
            if (!file.exists()) {
               file.mkdirs();
            }

            String relativePath = "/Users/Vivern/Desktop/Modding/dataset/" + namefolder + "/" + dataset_save_number + ".png";
            CreateItemFile.saveWriteBlankToFile(relativePath, this.distortedBlanks[i].blank);
            dataset_save_number++;
         }
      }
   }

   public void drawEnergyBar(int shardTypeId, float percentFilled, int x, int y, float percentIdeal) {
      this.mc.getTextureManager().bindTexture(TEXTURE_RESEARCH_BARS);
      this.drawTexturedModalRect(x, y, 0, 0, 6, 196);
      if (shardTypeId != 0 && percentFilled > 0.0F) {
         this.drawTexturedModalRect(
            x,
            Math.round(y + 196.0F * (1.0F - percentFilled)),
            6 * shardTypeId,
            Math.round(196.0F * (1.0F - percentFilled)),
            6,
            Math.round(196.0F * percentFilled)
         );
      }

      this.drawTexturedModalRect(x - 1, y + Math.round(196.0F * (1.0F - percentIdeal)) - 2, 78, 0, 8, 5);
      this.mc.getTextureManager().bindTexture(MAIN_RUNES);
      GuiScreen.drawModalRectWithCustomSizedTexture(x - 3, y + 200, 0.0F, shardTypeId * 12, 12, 12, 12.0F, 156.0F);
   }

   public void drawEnergyBars(int[] amounts, int[] mightBe, int mainGuiXPosition, int mainGuiYPosition) {
      float x1 = mainGuiXPosition / 12.0F;
      int minDistanceBetween = 4;
      int maxDistanceBetween = 9;
      int distanceBetween = MathHelper.floor(MathHelper.clamp(x1, minDistanceBetween, maxDistanceBetween));
      int startX = mainGuiXPosition - distanceBetween * 2;
      int highestFinall = 0;
      int lowestFinall = Integer.MAX_VALUE;

      for (int i = 12; i >= 1; i--) {
         if (highestFinall < mightBe[i]) {
            highestFinall = mightBe[i];
         }

         if (lowestFinall > mightBe[i]) {
            lowestFinall = mightBe[i];
         }
      }

      for (int i = 12; i >= 1; i--) {
         int amount = amounts[i];
         int finall = mightBe[i];
         if (amount > 0 || finall > 0) {
            float percentIdeal = GetMOP.getfromto((float)finall, (float)lowestFinall, (float)highestFinall) * 0.875F + 0.0625F;
            this.drawEnergyBar(i, MathHelper.clamp(amount / (finall / percentIdeal), 0.0F, 1.0F), startX, mainGuiYPosition + 10, percentIdeal);
            startX -= distanceBetween;
         }
      }
   }

   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
      super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
      int interpMouseX = mouseX - this.getGuiLeft();
      int interpMouseY = mouseY - this.getGuiTop();
      if (this.updatePrevMouses) {
         this.updatePrevMouses = false;
         this.prevMouseX = interpMouseX;
         this.prevMouseY = interpMouseY;
      }

      if (this.specialization == 3 && this.tile.blanks != null && this.blankSelected < this.tile.blanks.length && this.blankSelected >= 0) {
         WriteBlank blank = this.tile.blanks[this.blankSelected];
         if (blank != null && TileResearchTable.checkInkInSlot(this.tileinv)) {
            float xx = this.prevMouseX - interpMouseX;
            float yy = this.prevMouseY - interpMouseY;
            float distToPrevMouse = (float)Math.sqrt(xx * xx + yy * yy);
            int spotRadius;
            if (distToPrevMouse > 6.0F) {
               spotRadius = 1;
            } else if (distToPrevMouse > 3.0F) {
               spotRadius = 2;
            } else {
               spotRadius = 3;
            }

            if (distToPrevMouse > 1.6F) {
               int iterations = (int)(distToPrevMouse / 1.45F);
               float oneLenX = xx / (iterations + 1);
               float oneLenY = yy / (iterations + 1);

               for (int i = 1; i <= iterations; i++) {
                  float nx = interpMouseX + oneLenX * i;
                  float ny = interpMouseY + oneLenY * i;
                  this.handleSpots(nx, ny, spotRadius);
                  blank.spot(Math.round(nx) - 87, Math.round(ny) - 65, spotRadius);
               }

               this.inkConsumed += iterations;
            }

            this.inkConsumed += 2;
            blank.spot(interpMouseX - 87, interpMouseY - 65, spotRadius);
            if (this.inkConsumed >= 35) {
               ITileEntitySynchronize.sendDataToServer(this.tileinv.getField(0), this.tileinv.getField(1), this.tileinv.getField(2));
               this.inkConsumed = 0;
            }
         }
      }

      if (this.specialization == 2 && (clickedMouseButton == 2 || clickedMouseButton == 1) && puzzleRender != null) {
         float xxx = this.prevMouseX - interpMouseX;
         float yyx = this.prevMouseY - interpMouseY;
         puzzleRender.posx = puzzleRender.posx + xxx / puzzleRender.scale;
         puzzleRender.posy = puzzleRender.posy + yyx / puzzleRender.scale;
      }

      if (this.specialization == 1 && (clickedMouseButton == 2 || clickedMouseButton == 1) && this.exploringField != null) {
         float xxx = this.prevMouseX - interpMouseX;
         this.exploringField.xDisplace = (int)(this.exploringField.xDisplace - xxx);
      }

      this.prevMouseX = interpMouseX;
      this.prevMouseY = interpMouseY;
   }

   protected void mouseReleased(int mouseX, int mouseY, int state) {
      super.mouseReleased(mouseX, mouseY, state);
      this.updatePrevMouses = true;
      if (this.specialization == 2 && this.dragAndDropInventoryCell >= 0 && this.dragAndDropInventoryCell < 12 && puzzle != null && puzzleRender != null) {
         int[] pos = this.mouseToCellPos(mouseX, mouseY);
         int invI = 0;

         for (int e = 1; e <= 12; e++) {
            if (puzzle.inventory[e] > 0) {
               if (invI == this.dragAndDropInventoryCell) {
                  TerraformingPlayerCommand command = new TerraformingPlayerCommand(
                     TerraformingPlayerCommand.TRPlayerCommandType.PLACE_FROM_INVENTORY, pos[0], pos[1], e
                  );
                  this.puzzleCommand = command;
                  break;
               }

               invI++;
            }
         }

         this.dragAndDropInventoryCell = -1;
         this.dragAndDropRenderedElem = 0;
      }
   }

   public static int maxWriteDirtAllowed() {
      return 1000;
   }

   public void changeSelectedBlank(int newSelection) {
      if (this.tile.blanks.length > this.blankSelected) {
         WriteBlank.WriteBlankSpell blank = this.tile.blanks[this.blankSelected];
         if (this.toSort != null && !this.toSort.isEmpty() && !this.analyzedSpells.isEmpty() && this.analyzedSpells != null) {
            Spell spell = this.toSort.get(0).spell;
            AnalyzedSpell analyzedSpell = this.analyzedSpells.get(spell);
            if (analyzedSpell.fullness == spell.configuration.countOfAll && analyzedSpell.dirt <= maxWriteDirtAllowed()) {
               blank.averageSuitableSpell = spell;
               blank.wasted = false;
            } else if (blank.averageSuitableSpell == null) {
               blank.wasted = true;
            }
         } else {
            blank.averageSuitableSpell = null;
            blank.wasted = true;
         }

         this.blankSelected = newSelection;
         this.analyzedSpells.clear();
         this.toSort.clear();
         this.currentWritePattern = null;
      }
   }

   public void updateScreen() {
      super.updateScreen();
      if (SpellsRedactors.isRedactorEnabled()) {
         SpellsRedactors.update();
      }

      if (this.specialization == 2 && puzzle != null) {
         if (shouldLoadPosition && puzzleRender != null) {
            puzzleRender.posx = this.tileinv.getField(4);
            puzzleRender.posy = this.tileinv.getField(5);
            puzzleRender.scale = this.tileinv.getField(6) / 100.0F;
            shouldLoadPosition = false;
         }

         if (puzzle.queue == null) {
            puzzle = null;
         } else {
            puzzle.update(this.puzzleCommand);
            if (this.puzzleCommand != null) {
               PacketTFRPuzzleToServer packet = new PacketTFRPuzzleToServer();
               packet.writeints(this.tileinv.getField(0), this.tileinv.getField(1), this.tileinv.getField(2), this.puzzleCommand);
               PacketHandler.NETWORK.sendToServer(packet);
               this.puzzleCommand = null;
            }

            if (puzzleRender != null) {
               puzzleRender.updateTerrainAnimation();
            }
         }
      }

      if (this.specialization == 3) {
         this.ticksupdate++;
         if (this.blankSelectedFloat != this.blankSelected) {
            this.blankSelectedFloat = this.blankSelectedFloat + MathHelper.clamp(this.blankSelected - this.blankSelectedFloat, -0.1F, 0.1F);
         }

         if (this.tile.blanks != null && this.blankSelected < this.tile.blanks.length && this.blankSelected >= 0) {
            WriteBlank blank = this.tile.blanks[this.blankSelected];
            if (blank != null && this.spellsKnown.size() > 0) {
               Spell nextAnalyzeSpell = this.spellsKnown.get(this.nextAnalyzeId);
               if (nextAnalyzeSpell != null && nextAnalyzeSpell.configuration != null) {
                  AnalyzedSpell analyzedSpell = nextAnalyzeSpell.getAnalyzed(blank);
                  this.analyzedSpells.put(nextAnalyzeSpell, analyzedSpell);
               }

               if (this.currentWritePattern != null && nextAnalyzeSpell != this.currentWritePattern) {
                  AnalyzedSpell analyzedSpell = this.currentWritePattern.getAnalyzed(blank);
                  this.analyzedSpells.put(this.currentWritePattern, analyzedSpell);
               }

               if (this.ticksupdate % 15 == 0) {
                  this.toSort.clear();
                  this.toSort.addAll(this.analyzedSpells.values());
                  if (!this.toSort.isEmpty()) {
                     Collections.sort(this.toSort);
                  }
               }

               this.nextAnalyzeId++;
               if (this.nextAnalyzeId >= this.spellsKnown.size()) {
                  this.nextAnalyzeId = 0;
               }
            }
         }
      }
   }

   public static void drawModalRectWithCustomSizedTexture2(
      float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight
   ) {
      float f = 1.0F / textureWidth;
      float f1 = 1.0F / textureHeight;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(x, y + height, 0.0).tex(u * f, (v + height) * f1).endVertex();
      bufferbuilder.pos(x + width, y + height, 0.0).tex((u + width) * f, (v + height) * f1).endVertex();
      bufferbuilder.pos(x + width, y, 0.0).tex((u + width) * f, v * f1).endVertex();
      bufferbuilder.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
      tessellator.draw();
   }

   public void drawLine(float x, float y, float toX, float toY, float Red, float Green, float Blue) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
      bufferbuilder.pos(x, y, this.zLevel).color(Red, Green, Blue, 1.0F).endVertex();
      bufferbuilder.pos(toX, toY, this.zLevel).color(Red, Green, Blue, 1.0F).endVertex();
      tessellator.draw();
   }

   public void drawGraph(WriteGraph graph, int x, int y) {
      for (Vec2i link : graph.links) {
         Vec2i vert1 = graph.vertexes[link.x];
         Vec2i vert2 = graph.vertexes[link.y];
         this.drawLine(vert1.x + x, vert1.y + y, vert2.x + x, vert2.y + y, 0.0F, 1.0F, 1.0F);
      }

      this.mc.getTextureManager().bindTexture(TEXTURE_WRITING);
      int i = 0;

      for (Vec2i vertex : graph.vertexes) {
         drawModalRectWithCustomSizedTexture2(
            vertex.x + x - 3.75F, vertex.y + y - 3.75F, 113.0F, (AnimationTimer.tick / 10 + i) % 11 * 7.5F, 7.5F, 7.5F, 128.0F, 128.0F
         );
         if (i % 2 == 0) {
            i += 7;
         } else if (i % 3 == 0) {
            i += 2;
         } else if (i % 5 == 0) {
            i += 5;
         } else {
            i++;
         }
      }
   }

   public static class AnalyzedSpell implements Comparable<AnalyzedSpell> {
      public Spell spell;
      public int similarity;
      public int fullness;
      public int dirt;

      public AnalyzedSpell(Spell spell, int similarity, int fullness, int dirt) {
         this.spell = spell;
         this.similarity = similarity;
         this.fullness = fullness;
         this.dirt = dirt;
      }

      public int compareTo(AnalyzedSpell other) {
         return other.similarity - this.similarity;
      }
   }

   public static class DistortedBlank {
      public WriteBlank.WriteBlankSpell blank = new WriteBlank.WriteBlankSpell(50, 75);
      public float distorsionX;
      public float distorsionY;
      public NoiseGeneratorPerlin perlin;
      public float angleMult;

      public DistortedBlank(int distorsionSeed) {
         Random rand = new Random(distorsionSeed);
         this.perlin = new NoiseGeneratorPerlin(rand, 2);
         this.distorsionX = (rand.nextFloat() - 0.5F) * 8.0F;
         this.distorsionY = (rand.nextFloat() - 0.5F) * 8.0F;
         this.angleMult = 0.1F + rand.nextFloat() * 2.0F;
         if (rand.nextFloat() < 0.25F) {
            this.angleMult = this.angleMult * (1.0F + rand.nextFloat() * 4.0F);
         }
      }

      public void distortDirection(float dirX, float dirY, float length) {
         if (length > 0.0F) {
            dirX /= length;
            dirY /= length;
            float angle = (float)(MathHelper.atan2(dirX, -dirY) * (180.0 / Math.PI)) * this.angleMult;
            float scale = 0.1F + Debugger.floats[4];
            this.distorsionX = this.distorsionX + (float)this.perlin.getValue(angle, 0.0) * scale * length;
            this.distorsionY = this.distorsionY + (float)this.perlin.getValue(0.0, angle) * scale * length;
         }
      }

      public void spot(float x, float y, int radius) {
         this.blank.spot(Math.round(x + this.distorsionX) - 87, Math.round(y + this.distorsionY) - 65, radius);
      }
   }
}
